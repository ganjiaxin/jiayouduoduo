<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg page_set">
      <div class="page address_page">
        <div class="address_box add_box">
          <div class="list">
            <div class="item">
              <cube-input type="text" placeholder="收货人" v-model="name"></cube-input>
            </div>
            <div class="item">
              <cube-input type="tel" :maxlength="11" placeholder="手机号码" v-model="phone"></cube-input>
            </div>
            <div class="item" style="padding-bottom:10px;">
              <textarea placeholder="收货地址" v-model="address"></textarea>
            </div>
          </div>
        </div>
        <div class="default">
          <cube-switch v-model="isDefault">
            设为默认地址
          </cube-switch>
        </div>
        <div class="submit-button">
          <cube-button @click="handleAdd">确定</cube-button>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  import {required} from 'vuelidate/lib/validators'
  import {phone} from '../assets/js/validators'

  export default {
    name: 'add-address',
    data () {
      return {
        isDefault: true,
        isDefaultStr: 1,
        token: localStorage.getItem('hykToken'),
        name: '',
        phone: '',
        address: '',
        type: this.$route.query.type,
        options: {
          swipeBounceTime: 400
        }
      }
    },
    validations: {
      phone: {required, phone},
      address: {required},
      name: {required}
    },
    watch: {
      isDefault (val) {
        let _this = this
        if (val) {
          _this.isDefaultStr = 1
        } else {
          _this.isDefaultStr = 0
        }
      }
    },
    methods: {
      handleAdd () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (verify.name.$error) {
          _this.showToast('请输入收货人姓名')
          return false
        }
        if (!verify.phone.required) {
          _this.showToast('请输入收货人手机号')
          return false
        } else if (!verify.phone.phone) {
          _this.showToast('手机号不正确')
          return false
        }
        if (verify.address.$error) {
          _this.showToast('请输入收货人地址')
          return false
        }

        postRequest(this.HOST + '/mine/addAddress', {
          token: _this.token,
          name: _this.name,
          phone: _this.phone,
          address: _this.address,
          isDefault: _this.isDefaultStr
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          if (_this.type === 'select') {
            _this.$router.replace('/storeOrder?id=' + _this.$route.query.goodsId + '&num=' + _this.$route.query.num)
            return false
          } else if (_this.type === 'drawcard') {
            _this.$router.replace('/drawCard')
            return false
          }
          _this.$router.replace('/addressList')
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

<style lang="less" src="../assets/addressSet.less"></style>
