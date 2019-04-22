<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full">
      <div class="page pay_page">
        <div class="pay_status" v-if="status==='2'">
          <dl>
            <dt>
              <svg class="icon" aria-hidden="true">
                <use xlink:href="#duigou"></use>
              </svg>
            </dt>
            <dd>订单已支付</dd>
          </dl>
          <p>将在1个工作日内到帐</p>
          <div class="submit-button">
            <cube-button @click="handleAgain">继续充值</cube-button>
            <p @click="seeOrder">查看订单</p>
          </div>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  export default {
    name: 'pay-callback',
    data () {
      return {
        orderNo: this.$route.query.orderNo,
        options: {
          swipeBounceTime: 400
        },
        status: '',
        token: localStorage.getItem('hykToken')
      }
    },
    mounted () {
      let _this = this
      const toast = this.$createToast({
        txt: '努力加载中~',
        mask: true,
        time: 3
      })
      toast.show()
      setTimeout(function () {
        _this.loadOrder()
      }, 3000)
    },
    watch: {
      status (v) {
        if (v === '0') {
          this.$router.replace(`/pay?orderNo=${this.orderNo}`)
        }
      }
    },
    methods: {
      loadOrder () {
        let _this = this
        postRequest(this.HOST + '/order/seeOrder', {
          token: _this.token,
          orderNo: _this.orderNo
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.status = data.hykOrder.orderStatus
        })
      },
      seeOrder () {
        this.$router.push('/orderDetail?orderNo=' + this.orderNo)
      },
      handleAgain () {
        this.$router.push('/recharge')
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
<style lang="less" src="../assets/pay.less"></style>
