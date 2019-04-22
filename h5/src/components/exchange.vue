<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg" style="padding-top:15px;">
      <div class="page ticket_page">
        <input type="password" style="position:absolute;top:-50px;opacity:0;filter:alpha(opacity=0)"/>
        <div class="ticket_info white">
          <div class="item">
            <label>充值卡号</label>
            <cube-input type="tel" :maxlength="19" @blur="listenBlur" placeholder="请输入充值卡号" v-model="cardNo"
                        class="inline"></cube-input>
          </div>
          <div class="item">
            <label>充值密钥</label>
            <cube-input type="text"  :maxlength="6" placeholder="请输入充值密钥" v-model="password" @blur="listenBlur" class="inline"></cube-input>
          </div>
        </div>
        <div class="submit-button">
          <cube-button @click="handleSubmit">确认兑换</cube-button>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {required} from 'vuelidate/lib/validators'
  import {oliCard} from '../assets/js/validators'
  import {postRequest} from '../axios'
  export default {
    name: 'exchange',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        },
        password: '',
        token: localStorage.getItem('hykToken'),
        cardNo: '',
        cardNum: ''
      }
    },
    watch: {
      cardNo (value) {
        let _this = this
        if (/\S{5}/.test(value)) {
          _this.cardNo = value.replace(/\s/g, '').replace(/(.{4})/g, '$1 ')
        }
        _this.cardNum = value.replace(/\s+/g, '')
      }
    },
    validations: {
      cardNum: {required, oliCard},
      password: {required}
    },
    methods: {
      listenBlur () {
        setTimeout(function () {
          window.scrollTo(0, 0)
        }, 100)
      },
      checkCardNum () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!verify.cardNum.required) {
          _this.showToast('请输入充值卡号')
          return false
        } else if (!verify.cardNum.oliCard) {
          _this.showToast('充值卡号不正确')
          return false
        }

        if (_this.cardNum.length < 16) {
          _this.showToast('充值卡号为16位')
          return false
        }

        return true
      },
      handleSubmit () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!_this.checkCardNum()) {
          return false
        }
        if (!verify.password.required) {
          _this.showToast('请输入充值密钥')
          return false
        }
        postRequest(this.HOST + '/balance/recharge', {
          token: _this.token,
          caredno: _this.cardNum,
          password: _this.password
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.showConfirm('充值成功')
        })
      },
      showToast (tip) {
        const toast = this.$createToast({
          txt: tip,
          type: 'warn',
          time: 1500
        })
        toast.show()
      },
      showConfirm (tip) {
        let _this = this
        const toast = this.$createToast({
          txt: tip,
          type: 'txt',
          time: 1500
        })
        toast.show()
        setTimeout(() => {
          _this.$router.replace('/balance')
        }, 1600)
      }
    }
  }
</script>

<style lang="less">
  .ticket_ul{
    width:100%;
    height:auto;
    ul{
      li{
        width:100%;
        height:auto;
        font-size:14px;
        color:#999;
        text-align:left;
        line-height:1;
        padding:18px 0;
        border-bottom:solid 1px #ebebeb;
        label{
          float:right;
          color:#333;
          font-size:15px;
        }
      }
      li:last-child{
        border:none;
      }
    }
  }
  .ticket_page {
    color: #333;
    padding-bottom: 30px !important;
    .cube-input-append {
      display: none;
    }
    .cube-input::after {
      border: none !important;
    }
    .submit-button {
      padding: 50px 30px 0 30px;
      button {
        letter-spacing: 4px;
      }
    }
    .inline {
      width: 100%;
      display: inline-block;
    }
    .eyeNo input {
      letter-spacing: 3px;
    }
    svg {
      width: 100%;
      height: 100%;
    }
    .ticket_info {
      padding: 5px 15px;
      box-shadow: 0 1px 6px #E8E8E8;
      .item {
        width: 100%;
        height: auto;
        position: relative;
        padding: 6px 0 6px 65px;
        border-bottom: solid 1px #ebebeb;
        label {
          width: 70px;
          height: auto;
          display: inline-block;
          text-align: left;
          font-size: 14px;
          color: #666;
          position: absolute;
          left: 0;
          top: 6px;
          padding: 14px 0;
        }
        input {
          font-size: 15px;
          color: #333;
        }
      }
      .item:last-child {
        border: none;
      }
    }
  }
</style>
