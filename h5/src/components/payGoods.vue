<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg">
      <div class="page pay_page">
        <div class="pay_box">
          <dl>
            <dt>￥{{hykOrder.Int}}<span>{{hykOrder.Last}}</span></dt>
            <dd>
              订单金额
              <p>剩余时间 <span>{{countTime}}</span></p>
            </dd>
          </dl>
          <div class="more">
            <ul v-show="ulShow">
              <li><span>{{hykOrder.goodsName}}</span>商品名称</li>
              <li><span>{{hykOrder.num}}</span>商品数量</li>
              <li><span>{{hykOrder.orderno}}</span>订单编号</li>
              <li><span>{{hykOrder.money}}元</span>订单总额</li>
              <template v-if="hykOrder.goodsType==='1'">
                <li><span>普通快递</span>配送方式</li>
                <li><span v-html="`<label>${hykOrder.addressName}</label><label>${hykOrder.addressPhone}</label><br>${hykOrder.address}`"></span>收货地址</li>
              </template>
              <div class="clear"></div>
            </ul>
            <p @click="handleShow" v-show="!ulShow"><i class="down"></i>查看订单详情</p>
          </div>
        </div>
        <div class="pay">
          <p>选择支付方式，并支付</p>
          <ul>
            <!--<li>-->
              <!--<i class="wechat"></i>微信支付-->
              <!--<div class="checkbox">-->
                <!--<div>-->
                  <!--<svg class="icon" aria-hidden="true">-->
                    <!--<use xlink:href="#duigou"></use>-->
                  <!--</svg>-->
                <!--</div>-->
              <!--</div>-->
            <!--</li>-->
            <!--<li>-->
              <!--<i class="alipay"></i>支付宝支付-->
              <!--<div class="checkbox">-->
                <!--<div>-->
                  <!--<svg class="icon" aria-hidden="true">-->
                    <!--<use xlink:href="#duigou"></use>-->
                  <!--</svg>-->
                <!--</div>-->
              <!--</div>-->
            <!--</li>-->

            <li>
              <i class="pay"></i>快捷支付
              <div class="checkbox active">
                <div>
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#duigou"></use>
                  </svg>
                </div>
              </div>
            </li>
            <!--<li>-->
              <!--<i class="bank"></i>网银支付-->
              <!--<div class="checkbox">-->
                <!--<div>-->
                  <!--<svg class="icon" aria-hidden="true" fill="#FFFFFF">-->
                    <!--<use xlink:href="#duigou"></use>-->
                  <!--</svg>-->
                <!--</div>-->
              <!--</div>-->
            <!--</li>-->
          </ul>
          <div class="submit-button">
            <cube-button @click="handlePay" :disabled="isDisabled">{{btnTxt}}</cube-button>
          </div>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  let count = 0
  export default {
    name: 'pay',
    data () {
      return {
        options: {
          swipeBounceTime: 400,
          stopPropagation: true
        },
        hykOrder: [],
        id: this.$route.query.goodsId,
        ulShow: true,
        token: localStorage.getItem('hykToken'),
        countTime: '',
        myCount: '',
        isDisabled: false,
        btnTxt: '下一步'
      }
    },
    mounted () {
      this.loadPage()
    },
    methods: {
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/shop/seeOrder', {
          token: _this.token,
          id: _this.id
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let list = data.mallOrder
          let total = list.money.toString()
          if (total.indexOf('.') !== -1) {
            let Int = total.split('.')[0]
            let last = total.split('.')[1]
            _this.$set(list, 'Int', Int)
            _this.$set(list, 'Last', `.${last}`)
          } else {
            _this.$set(list, 'Int', total)
            _this.$set(list, 'Last', '.00')
          }
          list.money = list.money.toFixed(2)
          _this.hykOrder = list
          let time = (+list.createTime + 1800) - list.sysTime
          if (time > 0) {
            _this.getCountDown(time)
          } else {
            _this.isDisabled = true
            _this.btnTxt = '订单已过期'
            _this.countTime = '00:00'
          }
        })
      },
      handleShow () {
        this.ulShow = true
      },
      handlePay () {
        let _this = this
        postRequest(this.HOST + '/f/web/payShop/createShopOrder', {
          token: _this.token,
          id: _this.hykOrder.id,
          chnl: 3
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          event.preventDefault()
          location.replace(data.url)
        })
      },
      getCountDown (obsolete) {
        let _this = this
        _this.myCount = setInterval(function () {
          count++
          let t = obsolete - count
          let min = Math.floor(t / 60 % 60)
          let sec = Math.floor(t % 60)
          if (min === 0 && sec === 0) {
            clearInterval(_this.myCount)
            _this.isDisabled = true
            _this.btnTxt = '订单已过期'
          }
          if (min < 10) {
            min = '0' + min
          }
          if (sec < 10) {
            sec = '0' + sec
          }
          _this.countTime = min + ':' + sec
        }, 1000)
      },
      showToast (tip) {
        const toast = this.$createToast({
          txt: tip,
          type: 'warn',
          time: 1500
        })
        toast.show()
      }
    },
    beforeDestroy () {
      clearInterval(this.myCount)
    }
  }
</script>
<style lang="less" src="../assets/pay.less"></style>
