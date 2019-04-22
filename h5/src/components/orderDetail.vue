<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg">
      <div class="page order_page">
        <div class="order_detail">
          <h2>
            <span :class="hykOrder.oilStatus==='0'?`wait`:`success`" v-if="hykOrder.orderStatus==='2'">{{hykOrder.oilStatus==='0'?hykOrder.goodsType==0?'加油计划执行中':'待加油':`已完成`}}</span>
            {{hykOrder.goodsName}}</h2>
          <ul>
            <li v-if="hykOrder.cardNo!==''"><span>{{hykOrder.cardNo}}</span>充值油卡号</li>
            <li><span>{{hykOrder.goodsType==0?`${hykOrder.activityDiscount}折/${hykOrder.cycel}个月`:`${hykOrder.val}元/即时充值`}}</span>充值套餐
            </li>
            <li><span>{{hykOrder.amt}}元</span>订单总额</li>
            <li v-if="hykOrder.redpackageAmt>0"><span style="color:red">-{{hykOrder.redpackageAmt}}</span>优惠减免</li>
          </ul>
          <p><label>￥{{hykOrder.redpackageAmt>0?hykOrder.payMoney:hykOrder.amt}}</label>支付金额</p>
        </div>
        <div class="order_status">
          <ul>
            <li><span>{{hykOrder.orderNo}}</span>订单编号</li>
            <li><span>{{hykOrder.createDateStr}}</span>下单时间</li>
            <li v-if="hykOrder.orderStatus==='2'"><span style="text-align:right;"
                                                        v-html="hykOrder.balancePayment>0 && hykOrder.payableMoney>0?`账户余额（${hykOrder.balancePayment}元）+ <br><i>快捷支付（${hykOrder.payableMoney}元）</i>`:hykOrder.balancePayment>0?'账户余额':'快捷支付'"></span>付款方式
              <div class="clear"></div>
            </li>
            <li v-if="hykOrder.orderStatus==='2'"><span>{{hykOrder.payDateStr}}</span>付款时间</li>
            <li class="status">
              <span class="success" v-if="hykOrder.orderStatus==='2'">已支付</span>
              <!--<span v-else-if="hykOrder.orderStatus==='1'">充值中</span>-->
              <span class="wait" v-else-if="hykOrder.orderStatus==='0'">待支付</span>
              <span class="error" v-else-if="hykOrder.orderStatus==='5'">已暂停</span>
              <span class="error" v-else-if="hykOrder.orderStatus==='6'">已退款</span>
              <!--<span class="cancel" v-else-if="hykOrder.orderStatus==='4'">已失效</span>-->
              <span class="error" v-else>订单超时</span>
              订单状态
            </li>
          </ul>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'order-detail',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        },
        hykOrder: [],
        orderNo: this.$route.query.orderNo,
        token: localStorage.getItem('hykToken')
      }
    },
    mounted () {
      this.loadOrder()
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
          let list = data.hykOrder
          _this.$set(list, 'payMoney', (list.amt - list.redpackageAmt).toFixed(2))
          list.amt = list.amt.toFixed(2)
          _this.hykOrder = list
        })
      }
    }
  }
</script>
<style lang="less">
  .order_page {
    padding: 10px !important;
    .order_status {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      padding: 15px;
      border-radius: 5px;
      ul {
        width: 100%;
        li.status {
          color: #333;
          font-size: 15px;
          span {
            font-size: 15px;
          }
        }
        li {
          line-height: 1.5em;
          padding: 6px 0;
          font-size: 14px;
          color: #999999;
          clear: both;
          text-align: left;
          span {
            font-size: 0.38rem;
            width: 74%;
            text-align: right;
            color: #333333;
            line-height: 1.5em;
            float: right;
            label {
              padding-left: 18px;
            }
            i {
              font-style: normal;
            }
          }
          span.wait {
            color: #FF4456;
          }
          span.cancel {
            color: #999999;
          }
          span.success {
            color: #36A94C;
          }
          span.error {
            color: red;
          }
        }
      }
    }
    .order_detail {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      margin-bottom: 13px;
      border-radius: 5px;
      p {
        font-size: 15px;
        line-height: 1;
        padding: 18px 15px;
        label {
          color: #FF4456;
          float: right;
          font-size: 16px;
        }
      }
      h2 {
        width: 100%;
        height: auto;
        font-size: 18px;
        padding: 20px 15px 15px;
        line-height: 1;
        text-align: left;
        position: relative;
        border-bottom: solid 1px #EEEEEE;
        span {
          position: absolute;
          width: auto;
          height: 24px;
          display: inline-block;
          font-size: 14px;
          background: rgb(16, 191, 103);
          color: #ffffff;
          padding: 0 8px 0 13px;
          right: 0;
          line-height:24px;
          top:50%;
          margin-top:-10px;
          border-radius: 12px 0 0 12px;
        }
        span.wait{
          background: rgb(80, 151, 255);
        }
      }
      ul {
        width: 100%;
        padding: 10px 15px;
        border-bottom: solid 1px #ebebeb;
        li {
          line-height: 1;
          padding: 9px 0;
          font-size: 14px;
          clear: both;
          color: #999999;
          text-align: left;
          span {
            font-size: 15px;
            color: #333333;
            float: right;
          }
        }
      }
    }
  }
</style>

