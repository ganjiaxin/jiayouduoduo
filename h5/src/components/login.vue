<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full">
      <div class="page">
        <div class="reg_box pd">
          <h2 style="padding-bottom:2%;">账号登录</h2>
          <input type="password" style="position:absolute;top:-50px;opacity:0;filter:alpha(opacity=0)"/>
          <div class="item">
            <div class="view_phone" style="padding-bottom:20%;">
              <p>{{phone}}</p>
            </div>
            <cube-input type="password" :maxlength="16" :eye="eye" @blur="listenBlur" placeholder="登录密码" v-model="password"
                        class="pass"></cube-input>
            <div class="link">
              <p>
                <router-link to="/updatePass">找回密码</router-link>
                <router-link to="/userJoin">更换账户</router-link>
              </p>
            </div>
          </div>
          <div class="item">
            <cube-button @click="handleNext">确定</cube-button>
          </div>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {required, minLength} from 'vuelidate/lib/validators'
  import {postRequest} from '../axios'

  export default {
    name: 'login',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        },
        redirect: this.$route.query.redirect,
        phone: this.$route.query.phone,
        labelPhone: '',
        password: '',
        eye: {
          open: false,
          reverse: false
        }
      }
    },
    validations: {
      password: {required, minLength: minLength(6)}
    },
    // mounted () {
    //   this.fomartPhone()
    // },
    methods: {
      // fomartPhone () {
      //   let str = this.phone
      //   this.labelPhone = str.substr(0, 3) + '****' + str.substr(7)
      // },
      listenBlur () {
        setTimeout(function () {
          window.scrollTo(0, 0)
        }, 100)
      },
      handleNext () {
        let _this = this
        if (!_this.checkPass()) {
          return false
        }
        postRequest(this.HOST + '/f/app/login', {
          phone: _this.phone,
          password: _this.password
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          localStorage.setItem('hykUserId', data.id)
          localStorage.setItem('hykToken', data.token)
          localStorage.setItem('hykPhone', data.phone)
          if (_this.redirect === '') {
            _this.$router.replace('/home')
          } else {
            if (_this.redirect.indexOf('hykweb') > 0) {
              event.preventDefault()
              location.replace(_this.redirect)
              return false
            }
            _this.$router.replace(_this.redirect)
          }
        })
      },
      checkPass () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!verify.password.required) {
          _this.showToast('请输入密码')
          return false
        } else if (!verify.password.minLength) {
          _this.showToast('密码长度必须为6-16位')
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
      }
    }
  }
</script>
<style lang="less" src="../assets/reg.less"></style>
