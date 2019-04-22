<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg">
      <div class="page order_page">
        <div class="order_detail">
          <dl>
            <dt v-lazy:background-image="`${hykOrder.img}`">
            </dt>
            <dd>{{hykOrder.goodsName}}</dd>
          </dl>
          <ul>
            <li><span>{{hykOrder.num}}</span>订单数量</li>
            <li><span>{{hykOrder.money}}元</span>订单总额</li>
          </ul>
          <p><label>￥{{hykOrder.money}}</label>支付金额</p>
        </div>
        <div class="order_status mb10">
          <ul>
            <li><span>{{hykOrder.orderno}}</span>订单编号</li>
            <li><span>{{hykOrder.createDateStr}}</span>下单时间</li>
            <li v-if="hykOrder.status==='2'"><span>{{hykOrder.payDateStr}}</span>付款时间</li>
            <li v-if="hykOrder.goodsType==='1' && hykOrder.remark!==''"><span>{{hykOrder.remark}}</span>买家备注</li>
            <li class="status">
              <span class="success" v-if="hykOrder.status==='2'">{{hykOrder.goodsType==='0'?`已完成`:hykOrder.sendStatus==='2'?`已完成`:`已支付`}}</span>
              <span class="wait" v-else-if="hykOrder.status==='0'">待支付</span>
              <!--<span class="cancel" v-else-if="hykOrder.orderStatus==='4'">已失效</span>-->
              <span class="error" v-else>订单超时</span>
              订单状态
            </li>
            <!--<li class="status" v-if="hykOrder.orderStatus==='2'">-->
            <!--<span :class="hykOrder.oilStatus==='0'?`wait`:`success`">{{hykOrder.oilStatus==='0'?hykOrder.goodsType==0?'加油计划执行中':'待加油':`已完成`}}</span>-->
            <!--加油状态</li>-->
          </ul>
        </div>
        <div class="order_status" v-if="hykOrder.goodsType==='1' && hykOrder.status==='2'">
          <ul>
            <li><span>普通快递</span>配送方式</li>
            <li><span
              v-html="`<label>${hykOrder.addressName}</label><label>${hykOrder.addressPhone}</label><br>${hykOrder.address}`"></span>收货地址
              <div class="celar"></div>
            </li>
            <li v-if="hykOrder.sendStatus==='0'" v-cloak><span class="success">待发货</span>快递状态</li>
            <li v-else v-cloak><span
              class="success">{{hykOrder.sendStatus==='1'?'查看最新物流信息':'已签收'}}<em></em></span>快递状态<a
              :href="`/web/mobile/service/express.html?id=${hykOrder.postid}&type=${hykOrder.wltype}&orderno=${hykOrder.orderno}&typeStr=${encodeURI(encodeURI(hykOrder.wltypeStr))}`"></a>
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
        id: this.$route.query.goodsId,
        token: localStorage.getItem('hykToken')
      }
    },
    mounted () {
      this.loadOrder()
    },
    methods: {
      loadOrder () {
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
         // _this.showToast(this.id)
          let list = data.mallOrder
          list.money = list.money.toFixed(2)
          _this.hykOrder = list
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
<style lang="less">
  .order_page {
    padding: 10px !important;
    .mb10 {
      margin-bottom: 13px;
    }
    .order_status {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      padding: 15px;
      border-radius:5px;
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
          position: relative;
          text-align: left;
          a {
            width: 100%;
            height: 100%;
            display: block;
            position: absolute;
            top: 0;
          }
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
            em {
              width: 13px;
              height: 13px;
              display: inline-block;
              vertical-align: -0.03rem;
              margin-left: 5px;
              background: url("../../static/images/more_icon.png") no-repeat center center;
              background-size: contain;
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
      padding: 0 15px;
      border-radius:5px;
      p {
        font-size: 15px;
        line-height: 1;
        padding: 18px 0;
        label {
          color: #FF4456;
          float: right;
          font-size: 16px;
        }
      }
      dl {
        width: 100%;
        height: auto;
        padding: 50px 0 30px 0;
        dt {
          width: 72px;
          height: 72px;
          border: solid 1px #f5f5f5;
          border-radius: 2px;
          margin: 0 auto;
          background-size: cover;
          background-position: center center;
          background-repeat: no-repeat;
        }
        dd {
          padding-top: 18px;
          text-align: center;
          font-size: 15px;
        }
      }
      ul {
        width: 100%;
        padding-bottom: 10px;
        border-bottom: solid 1px #ebebeb;
        li {
          line-height: 1;
          padding: 9px 0;
          font-size: 14px;
          color: #999999;
          text-align: left;
          span {
            text-align: right;
            font-size: 15px;
            color: #333333;
            float: right;
          }
        }
      }
    }
  }
</style>

