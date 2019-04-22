<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full">
      <div class="page">
        <div class="reg_box pd">
          <h2 style="padding-bottom:2%;">{{type===1?'注册账号':'修改密码'}}</h2>
          <input type="password" style="position:absolute;top:-50px;opacity:0;filter:alpha(opacity=0)"/>
          <div class="item">
            <div class="view_phone" style="padding-bottom:20%;">
              <p>{{phone}}</p>
            </div>
            <div class="getCode">
              <cube-input type="tel" placeholder="请输入验证码" :maxlength="4" v-model="code"></cube-input>
              <button :class="{disabled:!is_sendCode}" @click="getCode">{{tip}}</button>
            </div>
            <cube-input type="password" placeholder="设置密码" :maxlength="16" :eye="eye" v-model="password"
                        class="setPass"></cube-input>
            <template v-if="type==1">
              <cube-input type="text" placeholder="推荐人邀请码（选填）" v-model="tjcode"></cube-input>
            </template>
          </div>
          <div class="item" v-if="type==1">
            <cube-button @click="handleReg">确定</cube-button>
          </div>
          <div class="item" v-else>
            <cube-button @click="handleRepass">重置密码</cube-button>
          </div>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {required, minLength} from 'vuelidate/lib/validators'
  import {hasBlank, onlyNumber} from '../assets/js/validators'
  import {postRequest} from '../axios'

  export default {
    name: 'setPass',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        },
        phone: this.$route.query.phone,
        code: '',
        type: this.$route.query.type,
        password: '',
        tjcode: '',
        is_sendCode: true,
        eye: {
          open: false,
          reverse: false
        },
        tip: '获取验证码'
      }
    },
    validations: {
      password: {required, hasBlank, minLength: minLength(8)},
      code: {onlyNumber}
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
              _this.tip = count + 's'
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
            type: _this.type
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
        if (!verify.code.onlyNumber) {
          return false
        }
        postRequest(_this.HOST + '/f/app/checkCode', {
          phone: _this.phone,
          userCode: _this.code,
          type: _this.type
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
        })
        return true
      },
      handleReg () {
        let _this = this
        if (!_this.checkCode()) {
          return false
        }
        if (!_this.checkPass()) {
          return false
        }
        postRequest(this.HOST + '/f/app/register', {
          phone: _this.phone,
          password: _this.password,
          inviterId: _this.tjcode,
          channel: 'H5'
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          localStorage.setItem('hykUserId', data.id)
          localStorage.setItem('hykToken', data.token)
          localStorage.setItem('hykPhone', data.phone)
          _this.$router.replace('/home')
        })
      },
      handleRepass () {
        let _this = this
        if (!_this.checkCode()) {
          return false
        }
        if (!_this.checkPass()) {
          return false
        }
        postRequest(this.HOST + '/f/app/updatepwd', {
          phone: _this.phone,
          password: _this.password
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.showConfirm('密码已修改')
        })
      },
      checkPass () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!verify.password.required) {
          _this.showToast('请输入密码')
          return false
        } if (verify.password.hasBlank) {
          _this.showToast('密码不能包含空格')
          return false
        } else if (!verify.password.minLength) {
          _this.showToast('密码长度必须为8-16位')
          return false
        }
        return true
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
          _this.$router.replace('/login?phone=' + _this.phone + '&redirect=')
          localStorage.removeItem('hykToken')
          localStorage.removeItem('hykUserId')
        }, 1600)
      }
    }
  }
</script>
<style lang="less" src="../assets/reg.less"></style>
