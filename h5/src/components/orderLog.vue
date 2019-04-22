<template>
  <div class="content_panel orderPanel">
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="items"
      @pulling-down="onPullingDown"
      @pulling-up="onPullingUp"
      class="page_full bodyBg">
      <div class="order_list">
        <div class="noDataTip order" v-if="items.length===0 && isLoadDom">
          <dl>
            <dt></dt>
            <dd>暂无加油订单</dd>
          </dl>
        </div>
        <div class="order_box" v-for="(data,index) in items" :key="index">
          <router-link
            :to="data.orderStatus==='0'?`/pay?orderNo=${data.orderNo}`:`/orderDetail?orderNo=${data.orderNo}`">
            <h4>
              <span v-if="data.orderStatus==='2'" class="success">{{data.oilStatus==='0'?data.goodsType==0?'加油计划执行中':'待加油':`已完成`}}</span>
              <span v-else-if="data.orderStatus==='1'">充值中</span>
              <span class="wait_pay" v-else-if="data.orderStatus==='0'">待支付</span>
              <span class="cancel" v-else-if="data.orderStatus==='4'">已失效</span>
              <span class="error" v-else>订单超时</span>
              {{data.goodsType==='0'?'套餐充值':'即时充值'}}</h4>
            <dl>
              <dt v-lazy:background-image="`${data.goodsImg}`"></dt>
              <dd>
                <h2><span>￥<label>{{data.amt}}</label></span>{{data.goodsName}}</h2>
                <p>{{data.cardNo===''?'当前账户暂未绑定油卡':data.cardNo}}</p>
              </dd>
              <div class="clear"></div>
            </dl>
          </router-link>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  export default {
    name: 'order-log',
    data () {
      return {
        items: [],
        pageNum: 2,
        isLoadDom: false,
        isLoading: true,
        cardId: this.$route.query.id,
        token: localStorage.getItem('hykToken'),
        options: {
          pullDownRefresh: {
            threshold: 80,
            txt: '刷新成功'
          },
          pullUpLoad: {
            threshold: 80,
            txt: {
              more: '加载更多',
              noMore: '没有更多了'
            }
          }
        }
      }
    },
    mounted () {
      this.loadPage(1, 1)
    },
    methods: {
      loadPage (pageNum, type) {
        let _this = this
        postRequest(this.HOST + '/mine/allSuccessOrder', {
          token: _this.token,
          cardId: _this.cardId,
          currPage: pageNum
        }, _this.isLoading).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let list = data.list
          _this.isLoadDom = true
          list.forEach(function (item, i) {
            let oliCard = item.cardNo
            item.cardNo = oliCard.replace(/(.{4})/g, '$1 ')
          })
          if (type === 1) {
            _this.items = list
            _this.pageNum = 2
          } else {
            if (list.length > 0) {
              _this.items = _this.items.concat(list)
              _this.pageNum = _this.pageNum + 1
            }
          }
          setTimeout(function () {
            _this.$refs.scroll.forceUpdate()
          }, 300)
        })
      },
      onPullingDown () {
        this.isLoading = false
        this.loadPage(1, 1)
      },
      onPullingUp () {
        let _this = this
        _this.isLoading = false
        setTimeout(function () {
          _this.loadPage(_this.pageNum, 2)
        }, 800)
      },
      formatDate (now, fmt) {
        let date = new Date(now * 1000)
        let o = {
          'M+': date.getMonth() + 1, // 月份
          'd+': date.getDate(), // 日
          'h+': date.getHours(), // 小时
          'm+': date.getMinutes(), // 分
          's+': date.getSeconds() // 秒
        }
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
        for (let k in o) {
          if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
        }
        return fmt
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
  .orderPanel {
    .tab{
      width:100%;
      padding:0;
      background:#f5f5f5;
      position:absolute;
      top:0;
      z-index:99;
      height:auto;
      ul{
         width:100%;
         height:40px;
         background:#ffffff;
         box-shadow: 0 2px 6px rgba(0,0,0,0.06);
         li{
           width:33.33333%;
           height:auto;
           font-size:14px;
           color:#333333;
           line-height:40px;
           text-align:center;
           position:relative;
           float:left;
         }
        li.active{
          color:#FF4456;
        }
         li.active:after{
           content:'';
           width:33px;height:3px;
           background:#FF4456;
           position:absolute;
           bottom:0;
           left:50%;
           margin-left:-16.5px;
         }
      }
    }
    .order_list {
      width: 100%;
      height: auto;
      padding: 15px 15px 0 15px !important;
      color: #333;
      .order_box {
        width: 100%;
        height: auto;
        background: #FFFFFF;
        border-radius: 4px;
        box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.05);
        margin-bottom: 12px;
        padding: 10px 15px;
        a {
          color: #333;
        }
        h4 {
          width: 100%;
          height: auto;
          border-bottom: solid 1px #EBEBEB;
          font-size: 14px;
          color: #999;
          text-align: left;
          padding: 5px 0 10px;
          span {
            float: right;
            color: #333;

          }
          span.wait_pay {
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
        dl {
          width: 100%;
          position: relative;
          padding: 10px 0 5px 60px;
          dt {
            width: 60px;
            height: 60px;
            border-radius: 3px;
            overflow: hidden;
            position: absolute;
            left: 0;
            background-position: center center;
            background-size: cover;
            background-repeat: no-repeat;
            background-color:#f5f5f5;
          }
          dt[lazy=error],dt[lazy=loading]{
            background-size:30px 36px !important;
          }
          dd {
            width: 100%;
            padding-left: 14px;
            height: 60px;
            h2 {
              font-size: 14px;
              line-height: 30px;
              padding-bottom: 10px;
              text-align: left;
              span {
                float: right;
                font-size: 14px;
                label {
                  font-size: 20px;
                }
              }
            }
            p {
              font-size: 14px;
              color: #666666;
            }
          }
        }
      }
    }
  }
</style>
