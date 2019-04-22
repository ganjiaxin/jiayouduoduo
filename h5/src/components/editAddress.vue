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
        <div class="default" style="margin-bottom:15px;">
          <cube-switch v-model="isDefault">
            设为默认地址
          </cube-switch>
        </div>
        <div class="delete" @click="handleDelete" v-if="!isDefault">
          删除该地址
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
        isDefault: false,
        isDefaultStr: 0,
        token: localStorage.getItem('hykToken'),
        name: '',
        phone: '',
        address: '',
        addressId: this.$route.query.addressId,
        options: {
          swipeBounceTime: 400
        },
        type: this.$route.query.type
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
    mounted () {
      this.loadAddress()
    },
    methods: {
      handleDelete () {
        let _this = this
        postRequest(this.HOST + '/mine/delAdd', {
          token: _this.token,
          addressId: _this.addressId
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.showConfirm('地址已移除')
        })
      },
      loadAddress () {
        let _this = this
        postRequest(this.HOST + '/mine/oneAddress', {
          token: _this.token,
          addressId: _this.addressId
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let item = data.hykAddress
          _this.name = item.name
          _this.address = item.address
          _this.phone = item.phone
          if (item.isDefault === '1') {
            _this.isDefault = true
          } else {
            _this.isDefault = false
          }
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
          _this.$router.replace('/addressList')
        }, 1600)
      },
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
        postRequest(this.HOST + '/mine/updateAdd', {
          token: _this.token,
          name: _this.name,
          phone: _this.phone,
          address: _this.address,
          addressId: _this.addressId,
          isDefault: _this.isDefaultStr
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.$router.replace('/addressList')
        })
      }
    }
  }
</script>

<style lang="less" src="../assets/addressSet.less"></style>
