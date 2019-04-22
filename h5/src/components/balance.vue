<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      class="page_full bodyBg">
      <div class="page">
        <div class="balance">
          <dl>
            <dt></dt>
            <dd>￥<label>{{balance}}</label></dd>
          </dl>
        </div>
        <div class="submit-button">
          <cube-button @click="handleExchange(balance>0?0:1)">{{balance>0?'立即加油':'有线下充值卡？立即兑换'}}</cube-button>
          <router-link to="/exchange">
            <p v-if="balance>0">有线下充值卡？立即兑换</p>
          </router-link>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'balance',
    data () {
      return {
        balance: '0.00',
        options: {
          swipeBounceTime: 400
        },
        token: localStorage.getItem('hykToken')
      }
    },
    mounted () {
      this.loadPage()
    },
    methods: {
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/mine/info', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.balance = data.hykUser.accountBalance.toFixed(2)
        })
      },
      handleExchange (type) {
        if (type === 0) {
          this.$router.push('/')
        } else {
          this.$router.push('/exchange')
        }
      },
      showToast (tip) {
        console.log(this)
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
<style lang="less" scoped>
  .submit-button {
    padding: 20px 30px 15px;
    button {
      letter-spacing: 4px;
    }
    p {
      text-align: center;
      color: #FF4456;
      font-size: 15px;
      line-height: 1;
      padding-top: 30px;
      letter-spacing: 1px;
    }
  }

  .balance {
    width: 100%;
    height: auto;
    padding: 40px 0;
    margin-bottom: 40px;
    background: #fff;
    dl {
      width: 100%;
      height: auto;
      dt {
        width: 74px;
        height: 73px;
        background: url("../../static/images/balance_icon.png") no-repeat center center;
        background-size: contain;
        margin: 0 auto 25px auto;
      }
      dd {
        font-size: 20px;
        text-align: center;
        color: #333333;
        label {
          font-size: 30px;
        }
      }
    }
  }
</style>
