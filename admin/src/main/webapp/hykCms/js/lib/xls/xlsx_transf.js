/**
 * Created by Administrator on 2017/4/1.
 */
/*jshint browser:true */
/*global XLSX */
var X = XLSX;
var XW = {
    /* worker message */
    msg: 'xlsx',
    /* worker scripts */
    rABS: 'js/lib/xls/xlsxworker2.js',
    norABS: 'js/lib/xls/xlsxworker1.js',
    noxfer: 'js/lib/xls/xlsxworker.js'
};

var rABS = typeof FileReader !== "undefined" && typeof FileReader.prototype !== "undefined" && typeof FileReader.prototype.readAsBinaryString !== "undefined";
if (!rABS) {
    document.getElementsByName("userabs")[0].disabled = true;
    document.getElementsByName("userabs")[0].checked = false;
}

var use_worker = typeof Worker !== 'undefined';
if (!use_worker) {
    document.getElementsByName("useworker")[0].disabled = true;
    document.getElementsByName("useworker")[0].checked = false;
}

var transferable = use_worker;
if (!transferable) {
    document.getElementsByName("xferable")[0].disabled = true;
    document.getElementsByName("xferable")[0].checked = false;
}

var wtf_mode = false;

function fixdata(data) {
    var o = "", l = 0, w = 10240;
    for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
    o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
    return o;
}

function ab2str(data) {
    var o = "", l = 0, w = 10240;
    for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w, l * w + w)));
    o += String.fromCharCode.apply(null, new Uint16Array(data.slice(l * w)));
    return o;
}

function s2ab(s) {
    var b = new ArrayBuffer(s.length * 2), v = new Uint16Array(b);
    for (var i = 0; i != s.length; ++i) v[i] = s.charCodeAt(i);
    return [v, b];
}

function xw_noxfer(data, cb) {
    var worker = new Worker(XW.noxfer);
    worker.onmessage = function (e) {
        switch (e.data.t) {
            case 'ready':
                break;
            case 'e':
                console.error(e.data.d);
                break;
            case XW.msg:
                cb(JSON.parse(e.data.d));
                break;
        }
    };
    var arr = rABS ? data : btoa(fixdata(data));
    worker.postMessage({d: arr, b: rABS});
}

function xw_xfer(data, cb) {
    var worker = new Worker(rABS ? XW.rABS : XW.norABS);
    worker.onmessage = function (e) {
        switch (e.data.t) {
            case 'ready':
                break;
            case 'e':
                console.error(e.data.d);
                break;
            default:
                xx = ab2str(e.data).replace(/\n/g, "\\n").replace(/\r/g, "\\r");
                cb(JSON.parse(xx));
                break;
        }
    };
    if (rABS) {
        var val = s2ab(data);
        worker.postMessage(val[1], [val[1]]);
    } else {
        worker.postMessage(data, [data]);
    }
}

function xw(data, cb) {

    transferable = true;
    if (transferable) xw_xfer(data, cb);
    else xw_noxfer(data, cb);
}


function to_json(workbook) {
    var result = {};
    workbook.SheetNames.forEach(function (sheetName) {
        var roa = X.utils.sheet_to_json(workbook.Sheets[sheetName]);
        if (roa.length > 0) {
            result[sheetName] = roa;
        }
    });
    return result;
}

function process_wb(wb) {
    // var output = to_json(wb),json=[],array = output.Sheet1;
    // if (array == '' || array == null || array == 'undefined') {
    //     return;
    // }
    // console.log(array);
    // for(var i=0;i<array.length;i++){
    //     var item=array[i];
    //     var key= Object.keys(item);
    //
    //     for(var j=0;j<key.length;j++){
    //         console.log(key[j]);
    //          json.push(item[key[j]]);
    //     }
    // }
    // console.log(json);
    //
    // document.getElementById('import').value = '';

    var output = to_json(wb), keys = [], arr = [], len = output.Sheet1.length;
    if (output.Sheet1 == '' || output.Sheet1 == null || output.Sheet1 == 'undefined') {
        return;
    }
    for (var key in output.Sheet1[0]) {
        keys.push(key);
    }
    for (var i = 0; i < keys.length; i++) {
        arr[i]='';
        for (var j = 0; j < len; j++) {
            if(len>10000){
                layer.msg('导入上限10000条数据，对不起！');
                return false;
            }
            if(output.Sheet1[j][keys[i]]==undefined){
                output.Sheet1[j][keys[i]]=' ';
            }
            if(j==len-1){
                arr[i] += output.Sheet1[j][keys[i]];
            }else{
                arr[i] += output.Sheet1[j][keys[i]] + ',';
            }
            if(document.getElementById('out_' + keys[i]))
            {
                document.getElementById('out_' + keys[i]).value = arr[i];
            }else{
                layer.msg(keys[i]+'值找不到导入对象');
            }

        }
    }
    document.getElementById('import').value = '';
}


function handleFile(e) {
    use_worker = true;
    var files = e.target.files;

    if (!(/\.(?:xls|xlsx)$/.test(files[0].name))) {
        layer.msg('只支持后缀名xls和xlsx文件');
        return;
    }
    var f = files[0];
    {
        var reader = new FileReader();
        var name = f.name;
        var id = e.srcElement.id;

        reader.async = true;
        reader.onload = function (e) {
            var data = e.target.result;
            xw(data, process_wb, id.split("_")[1]);
        };
        if (rABS) reader.readAsBinaryString(f);
        else reader.readAsArrayBuffer(f);
    }
}


