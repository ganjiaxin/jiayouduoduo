<template>
  <cube-scroll
    ref="scroll"
    :options="options"
    :data="couponList"
    class="page_full bodyBg" style="z-index:999;">
    <div class="coupon_list">
      <button @click="sendParent('')">不使用红包</button>

      <div class="coupon_box" v-for="(data,index) in couponList" :key="index"
           :class="{over_time:amount<data.minAmount || data.goodsId!==selectId &&　data.goodsName!==undefined}"
           @click="sendParent(data)">
        <div class="coupon_info">
          <dl>
            <dt>{{data.title}}</dt>
            <dd>
              <p v-if="data.redType==='0' && data.goodsName===undefined">仅限加油套餐使用</p>
              <p v-else-if="data.redType==='1'">仅限即时充值使用</p>
              <p v-if="data.goodsName!==undefined" >仅限{{data.goodsName}}使用</p>
              <!--<p v-else>无限制</p>-->
            </dd>
          </dl>
          <dl class="fright">
            <dt><span>￥</span>{{data.money}}</dt>
            <dd>
              <p>{{data.minAmount===undefined || data.minAmount===0?'无门槛优惠券':`加油金额满${data.minAmount}元可用`}}</p>
            </dd>
          </dl>
        </div>
        <div class="coupon_time">
          过期时间：{{data.overTimeStr}}
          <div class="flag" v-if="activeId===data.id || couponId===data.id"></div>
          <p v-if="data.goodsId!==selectId && data.goodsName!==undefined"><label>不可用原因：</label>仅限{{data.goodsName}}可用
          </p>
          <p v-else-if="amount<data.minAmount"><label>不可用原因：</label>加油金额满{{data.minAmount}}可用</p>
        </div>
      </div>
    </div>
    <div class="noDataTip coupon" v-if="couponList.length===0">
      <dl>
        <dt></dt>
        <dd>暂可用红包</dd>
      </dl>
    </div>
  </cube-scroll>
</template>

<script>
  export default {
    name: 'coupon',
    data () {
      return {
        items: [],
        isLoadDom: false,
        options: {
          swipeBounceTime: 400
        },
        activeId: ''
      }
    },
    props: ['couponList', 'couponId', 'amount', 'selectId'],
    methods: {
      sendParent (data) {
        if (data.goodsId !== this.selectId && data.goodsName !== undefined) {
          return false
        }
        if (this.amount < data.minAmount) {
          return false
        }
        this.activeIndex = data.id
        this.$emit('listenChildEvent', data)
      }
    }
  }
</script>
<style lang="less" scoped>
  .bodyBg {
    animation: fadeInUpBig 0.8s;
    -webkit-animation: fadeInUpBig 0.8s;
  }

  @-webkit-keyframes fadeInUpBig {
    0% {
      opacity: 0;
      -webkit-transform: translate3d(0, 2000px, 0);
      transform: translate3d(0, 2000px, 0);
    }

    100% {
      opacity: 1;
      -webkit-transform: none;
      transform: none;
    }
  }

  @keyframes fadeInUpBig {
    0% {
      opacity: 0;
      -webkit-transform: translate3d(0, 2000px, 0);
      transform: translate3d(0, 2000px, 0);
    }

    100% {
      opacity: 1;
      -webkit-transform: none;
      transform: none;
    }
  }

  .fadeInUpBig {
    -webkit-animation-name: fadeInUpBig;
    animation-name: fadeInUpBig;
  }

  .coupon_list {
    width: 100%;
    height: auto;
    padding: 13px 13px 0 13px !important;
    button {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      box-shadow: 0 2px 3px #E8E8E8;
      border: none;
      border-radius: 3px !important;
      font-size: 15px;
      color: #333;
      padding: 10px 0;
      margin-bottom: 15px;
      outline: none;
    }
    .coupon_box {
      width: 100%;
      height: auto;
      background: #ffffff;
      padding: 0 15px;
      margin-bottom: 14px;
      border-radius: 2px;
      position: relative;
      box-shadow: 0 2px 4px #E8E8E8;
      .flag {
        width: 50px;
        height: 50px;
        position: absolute;
        bottom: 10px;
        right: 15px;
      }
      .coupon_info {
        width: 100%;
        height: auto;
        border-bottom: dashed 1px #DDDDDD;
        overflow: hidden;
        dl {
          width: 40%;
          float: left;
          padding: 12px 0;
          height: auto;
          dt {
            color: #000000;
            height: 44px;
            line-height: 44px;
            text-align: left;
            width: 100%;
            font-weight: bold;
            font-size: 18px;
          }
          dd {
            color: #999999;
            text-align: left;
            p {
              font-size: 12px;
              line-height: 1;
            }
          }
        }
        dl.fright {
          width: 60%;
          dt {
            font-size: 30px;
            color: #FF4456;
            text-align: right;
            span {
              font-size: 18px;
            }
          }
          dd {
            p {
              text-align: right;
            }
          }
        }
      }
      .coupon_time {
        width: 100%;
        clear: both;
        height: auto;
        position: relative;
        padding: 13px 0;
        font-size: 13px;
        text-align: left;
        p {
          padding-top: 8px;
          line-height: 1;
          color: #333333;
          label {
            color: #FF3140;
          }
        }
        .flag {
          width: 29px;
          height: 26px;
          background: url("../../static/images/active_icon.png") no-repeat center center;
          background-size: contain;
          position: absolute;
          bottom: 0;
          right: -15px;
        }
      }
    }
    .over_time {
      dl {
        dt {
          color: #999999 !important;
        }
      }
      .coupon_time {
        color: #999999;
      }
    }
  }
</style>
