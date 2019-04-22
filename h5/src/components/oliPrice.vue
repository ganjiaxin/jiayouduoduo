<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      class="page_full bodyBg">
      <div class="page oli_page">
        <div class="oli_list">
          <h2 @click="selectCity">{{oliList.city}}<i></i></h2>
          <div class="table_th">
            <ul>
              <li>成品油类型</li>
              <li>价格(元/升)</li>
            </ul>
            <div class="clear"></div>
          </div>
          <div class="table_tr">
            <ul>
              <li>92<i>#</i><label>汽油</label></li>
              <li>{{oliList.h92}}</li>
            </ul>
            <ul>
              <li>95<i>#</i><label>汽油</label></li>
              <li>{{oliList.h95}}</li>
            </ul>
            <ul>
              <li>98<i>#</i><label>汽油</label></li>
              <li>{{oliList.h98}}</li>
            </ul>
            <ul>
              <li>0<i>#</i><label>柴油</label></li>
              <li>{{oliList.h0}}</li>
            </ul>
            <div class="clear"></div>
          </div>
        </div>
        <div class="tip">
          <p>数据内容仅供参考，以实际油站价格为准</p>
          <p>{{oliList.createDateStr}}</p>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  const city = [
    {text: '北京市', value: '北京'},
    {text: '天津市', value: '天津'},
    {text: '上海市', value: '上海'},
    {text: '重庆市', value: '重庆'},
    {text: '河北省', value: '河北'},
    {text: '山西省', value: '山西'},
    {text: '辽宁省', value: '辽宁'},
    {text: '吉林省', value: '吉林'},
    {text: '黑龙江省', value: '黑龙江'},
    {text: '江苏省', value: '江苏'},
    {text: '浙江省', value: '浙江'},
    {text: '安徽省', value: '安徽'},
    {text: '福建省', value: '福建'},
    {text: '江西省', value: '江西'},
    {text: '山东省', value: '山东'},
    {text: '河南省', value: '河南'},
    {text: '湖北省', value: '湖北'},
    {text: '湖南省', value: '湖南'},
    {text: '广东省', value: '广东'},
    {text: '海南省', value: '海南'},
    {text: '四川省', value: '四川'},
    {text: '贵州省', value: '贵州'},
    {text: '云南省', value: '云南'},
    {text: '陕西省', value: '陕西'},
    {text: '甘肃省', value: '甘肃'},
    {text: '青海省', value: '青海'},
    {text: '内蒙古自治区', value: '内蒙古'},
    {text: '广西壮族自治区', value: '广西'},
    {text: '西藏自治区', value: '西藏'},
    {text: '宁夏回族自治区', value: '宁夏'},
    {text: '新疆维吾尔自治区', value: '新疆'}]
  export default {
    name: 'oli-price',
    data () {
      return {
        isLoadDom: false,
        options: {
          swipeBounceTime: 400
        },
        prov: this.$route.query.prov,
        oliList: {}
      }
    },
    mounted () {
      this.loadOli()
    },
    methods: {
      selectCity () {
        let _this = this
        if (!_this.picker) {
          _this.picker = _this.$createPicker({
            title: '请选择地区',
            data: [city],
            onSelect: _this.selectHandleCity
          })
        }
        _this.picker.show()
      },
      selectHandleCity (selectedVal, selectedIndex, selectedText) {
        this.prov = selectedVal
        this.loadOli()
      },
      loadOli () {
        let _this = this
        postRequest(_this.HOST + '/find/oilPrice',
          {
            prov: _this.prov
          }
        ).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.oliList = data.hykOilPrice
        })
      },
      showToast (tip) {
        const toast = this.$createToast({
          txt: tip,
          type: 'warn',
          time: 1500
        })
        toast.show()
      }
    }
  }
</script>
<style lang="less">
  .oli_page {
    width: 100%;
    height: auto;
    .tip {
      padding-bottom: 30px;
      p {
        font-size: 14px;
        color: #cccccc;
        text-align: center;
        line-height: 1.8em;
      }
    }
    .oli_list {
      width: 100%;
      height: auto;
      padding: 20px;
      background: #ffffff;
      margin-bottom: 15px;
      h2 {
        font-size: 18px;
        padding: 30px 0;
        letter-spacing: 2px;
        i {
          display: inline-block;
          margin-left: 4px;
          width: 9px;
          height: 15px;
          vertical-align: -1px;
          background: url("../../static/images/1@3x.png") no-repeat center bottom;
          background-size: contain;
        }
      }
      .table_th {
        width: 100%;
        height: auto;
        ul {
          li {
            padding: 10px 0;
            width: 50%;
            height: auto;
            float: left;
            font-size: 14px;
            color: #999999;
          }
        }
      }
      .table_tr {
        width: 100%;
        height: auto;
        ul {
          width: 100%;
          clear: both;
          li {
            padding: 18px 0;
            width: 50%;
            height: auto;
            float: left;
            font-size: 18px;
            color: #000000;
            line-height: 1.5em;
            i {
              font-size: 10px;
              display: inline;
              vertical-align: 5px;
            }
            label {
              font-size: 14px;
              color: #666666;
              padding-left: 2px;
            }
          }
        }
      }
    }
  }
</style>
