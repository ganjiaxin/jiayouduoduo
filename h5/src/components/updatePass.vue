<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_scroll">
      <div class="page">
        <div class="reg_box pd">
          <h2>找回密码</h2>
          <div class="item">
            <cube-input type="tel" :maxlength="11" placeholder="手机号码" v-model="phone"></cube-input>
          </div>
          <div class="item">
            <cube-button @click="handleNext">下一步</cube-button>
          </div>
          <!--<div class="login_type">-->
          <!--<div class="title">-->
          <!--<p></p>-->
          <!--<label>第三方登录</label>-->
          <!--</div>-->
          <!--<dl>-->
          <!--<dt></dt>-->
          <!--<dd>微信</dd>-->
          <!--</dl>-->
          <!--</div>-->
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {required} from 'vuelidate/lib/validators'
  import {phone} from '../assets/js/validators'
  import {postRequest} from '../axios'

  export default {
    name: 'user-join',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        },
        phone: ''
      }
    },
    validations: {
      phone: {required, phone}
    },
    methods: {
      handleNext () {
        let _this = this
        if (!_this.checkPhone()) {
          return false
        }
        postRequest(_this.HOST + '/f/app/checkPhone', {
          phone: _this.phone
        }, false).then(resp => {
          let data = resp.data
          if (data.code === '402') {
            _this.$router.replace('/setPass?phone=' + _this.phone + '&type=2')
          } else {
            _this.showToast(data.msg)
          }
        })
      },
      checkPhone () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!verify.phone.required) {
          _this.showToast('请输入手机号')
          return false
        } else if (!verify.phone.phone) {
          _this.showToast('手机号不正确')
          return false
        }
        return true
      },
      // getCode () {
      //   let _this = this
      //   postRequest(_this.HOST + '/f/app/getCode', {
      //     phone: _this.phone,
      //     type: 2
      //   }, false).then(resp => {
      //     let data = resp.data
      //     if (data.code !== '200') {
      //       _this.showToast(data.msg)
      //       return
      //     }
      //
      //   })
      // },
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
