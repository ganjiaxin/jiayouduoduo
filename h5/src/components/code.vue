<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full">
      <div class="page">
        <div class="reg_box pd">
          <div class="item">
            <p class="code_txt">短信验证码已发送至{{phone}}</p>
            <ul>
              <input type="tel" v-model="telcode" maxlength="4">
              <li :class="{active:codeLen>=1}">{{codeTxt[0]}}</li>
              <li :class="{active:codeLen>=2}">{{codeTxt[1]}}</li>
              <li :class="{active:codeLen>=3}">{{codeTxt[2]}}</li>
              <li :class="{active:codeLen===4}">{{codeTxt[3]}}</li>
              <div class="clear"></div>
            </ul>
            <p class="error">{{error}}</p>
            <p class="send_again" :class="{disabled:!is_sendCode}" @click="getCode">{{tip}}</p>
          </div>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {onlyNumber} from '../assets/js/validators'
  import {postRequest} from '../axios'

  export default {
    name: 'code',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        },
        phone: this.$route.query.phone,
        telcode: '',
        codeLen: 0,
        codeTxt: [],
        tip: '',
        error: '',
        type: this.$route.query.type,
        is_sendCode: false
      }
    },
    validations: {
      telcode: {onlyNumber}
    },
    mounted () {
      this.countStatus()
    },
    watch: {
      telcode (value) {
        let _this = this
        let Len = value.length
        if (_this.checkCode()) {
          _this.codeLen = Len
          _this.codeTxt = value.replace(/(.)(?=[^$])/g, '$1,').split(',')
          if (Len === 4) {
            postRequest(_this.HOST + '/f/app/checkCode', {
              phone: _this.phone,
              userCode: value,
              type: _this.type
            }, false).then(resp => {
              let data = resp.data
              if (data.code !== '200') {
                _this.error = data.msg
                return false
              }
              _this.$router.replace('/setPass?phone=' + _this.phone + '&code=' + _this.telcode + '&type=' + _this.type)
            })
          }
        } else {
          _this.telcode = ''
        }
      }
    },
    methods: {
      countStatus () {
        let _this = this
        _this.is_sendCode = false
        setTimeout(function () {
          let count = 61
          let resend = setInterval(function () {
            count--
            if (count > 0 && count < 61) {
              _this.tip = count + 's重新获取'
            } else {
              clearInterval(resend)
              _this.tip = '重新发送'
              _this.is_sendCode = true
            }
          }, 1000)
        }, 500)
      },
      getCode () {
        let _this = this
        if (_this.is_sendCode) {
          postRequest(_this.HOST + '/f/app/getCode', {
            phone: _this.phone,
            type: 1
          }, false).then(resp => {
            let data = resp.data
            if (data.code !== '200') {
              _this.showToast(data.msg)
              return false
            }
            _this.countStatus()
          })
        }
      },
      checkCode () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!verify.telcode.onlyNumber) {
          return false
        }
        return true
      }
      // showToast (tip) {
      //   const toast = this.$createToast({
      //     txt: tip,
      //     type: 'warn',
      //     time: 1500
      //   })
      //   toast.show()
      // }
    }
  }
</script>
<style lang="less" src="../assets/reg.less"></style>
