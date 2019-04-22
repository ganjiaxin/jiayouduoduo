<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg page_set">
      <div class="page set_page">
        <div class="tool_list">
          <ul>
            <li><span>{{phoneTxt}}</span>手机号码</li>
            <li><span>{{shareId}}</span>邀请码</li>
            <li @click="goQR"><i class="more"></i>我的二维码</li>
            <li @click="selectSex"><i class="more"></i><span>{{sex==='0'?'保密':sex==='2'?'女':'男'}}</span>性别</li>
            <li @click="selectBirthday"><i class="more"></i><span>{{birthday}}</span>生日</li>
            <router-link to="/addressList" tag="li"><i class="more"></i>地址管理</router-link>
            <li @click="downApp"><i class="more"></i><span>下载</span>客户端App</li>
            <!--<router-link to="/invoiceLog" tag="li"><i class="more"></i>发票记录</router-link>-->
          </ul>
        </div>
        <div class="submit-button">
          <cube-button  @click="handleOutlogin">退出当前账户</cube-button>
        </div>
      </div>
    </cube-scroll>
    <app-down v-if="isShow" :isSafari="isSafari" class="downTip"></app-down>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  import appDown from './downApp'

  const column1 = [{text: '保密', value: 0}, {text: '男', value: 1},
    {text: '女', value: 2}]
  export default {
    name: 'user-set',
    data () {
      return {
        phone: localStorage.getItem('hykPhone'),
        phoneTxt: '',
        shareId: localStorage.getItem('hykUserId'),
        sex: '0',
        birthday: '1970-1-1',
        options: {
          swipeBounceTime: 400
        },
        isLoading: true,
        isSafari: '',
        isShow: false,
        token: localStorage.getItem('hykToken')
      }
    },
    mounted () {
      let _this = this
      _this.phoneTxt = _this.phone.substr(0, 3) + '****' + _this.phone.substr(7)
      this.loadPage()
    },
    components: {
      appDown
    },
    methods: {
      downApp () {
        let ua = navigator.userAgent.toLowerCase()
        let isiOS = !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)
        let isAndroid = navigator.userAgent.indexOf('Android') > -1 || navigator.userAgent.indexOf('Adr') > -1
        let _this = this
        if (isiOS) {
          if (ua.match(/MicroMessenger\/[0-9]/i) || ua.match(/QQ\/[0-9]/i) || ua.indexOf('safari') > 0) {
            window.location.href = 'https://itunes.apple.com/app/id1446394113'
            return false
          }
          _this.isSafari = 'Safari'
          _this.isShow = true
        } else if (isAndroid) {
          if (ua.match(/MicroMessenger\/[0-9]/i) || ua.match(/QQ\/[0-9]/i)) {
            _this.isShow = true
            return false
          }
          window.location.href = 'https://www.huiucard.com/web/apk/hyk.apk'
        } else {
          if (ua.indexOf('safari') > 0 && ua.indexOf('aliapp') > 0) {
            _this.isSafari = 'Safari'
            _this.isShow = true
            return false
          }
          window.location.href = 'https://www.huiucard.com/web/apk/hyk.apk'
        }
      },
      goQR () {
        window.location.href = '/web/mobile/activity/qrCode/index.html'
      },
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/mine/info', {
          token: _this.token
        }, _this.isLoading).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.isLoading = false
          if (data.hykUser) {
            _this.sex = data.hykUser.sex
            _this.birthday = data.hykUser.registerDateStr2
          }
        })
      },
      updataSex (sex) {
        let _this = this
        postRequest(this.HOST + '/mine/updateInfo', {
          token: _this.token,
          sex: sex
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.loadPage()
        })
      },
      updataTime (date) {
        let _this = this
        postRequest(this.HOST + '/mine/updateInfo', {
          token: _this.token,
          birthday: date
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.loadPage()
        })
      },
      handleOutlogin () {
        localStorage.removeItem('hykToken')
        localStorage.removeItem('hykUserId')
        this.$router.replace('/userJoin')
      },
      selectSex () {
        let _this = this
        if (!_this.picker) {
          _this.picker = _this.$createPicker({
            title: '请选择性别',
            data: [column1],
            value: _this.sex,
            onSelect: _this.selectHandlesex
          })
        }
        _this.picker.show()
      },
      selectBirthday () {
        let _this = this
        if (!_this.datePicker) {
          _this.datePicker = _this.$createDatePicker({
            title: '请选择生日',
            min: new Date(1970, 0, 1),
            max: new Date(2020, 9, 20),
            value: new Date(_this.birthday),
            onSelect: _this.selectHandle
          })
        }
        _this.datePicker.show()
      },
      selectHandlesex (selectedVal, selectedIndex, selectedText) {
        this.updataSex(selectedVal[0])
      },
      selectHandle (date, selectedVal, selectedText) {
        // let dateStr = selectedText[0] + '-' + selectedText[1] + '-' + selectedText[2]
        this.updataTime(date)
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
  .downTip {
    position: fixed;
    z-index: 998;
    background: #fff;
    top: 0;
    animation: fadeIn 0.3s;
    -webkit-animation: fadeIn 0.3s;
  }

  .page_set {
    padding-top: 15px;
    .set_page {
      color: #333;
      .submit-button {
        padding: 20px 30px;
        button {
          letter-spacing: 4px;
        }
      }
      .tool_list {
        width: 100%;
        height: auto;
        background: #FFFFFF;
        padding: 0 15px;
        ul {
          width: 100%;
          margin-bottom: 30px;
          li {
            font-size: 14px;
            color: #666;
            line-height: 1;
            padding: 20px 1px;
            text-align: left;
            border-bottom: solid 1px #ebebeb;
            span {
              font-size: 15px;
              float: right;
              color: #333;
            }
          }
          li:last-child {
            border: none;
          }
          .more {
            width: 15px;
            height: 13px;
            background: url("../../static/images/more_icon.png") no-repeat center center;
            background-size: contain;
            display: block;
            float: right;
            margin-left: 5px;
          }
        }
      }
    }
  }
</style>
