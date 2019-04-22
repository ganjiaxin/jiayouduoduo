<template>
  <div class="content_panel coupon_panel">
    <div class="tab">
      <ul class="oli">
        <li :class="{active:isCheck===0}" @click="handleCheck(0)">可使用</li>
        <li :class="{active:isCheck===1}" @click="handleCheck(1)">已使用</li>
        <li :class="{active:isCheck===2}" @click="handleCheck(2)">已过期</li>
        <div class="clear"></div>
      </ul>
    </div>
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="items"
      @pulling-down="onPullingDown"
      @pulling-up="onPullingUp"
      class="page_order bodyBg">
      <div class="noDataTip coupon" v-if="items.length===0 && isLoadDom">
        <dl>
          <dt></dt>
          <dd>暂无红包</dd>
        </dl>
      </div>
      <div class="coupon_list">
        <div class="coupon_box" v-for="(data,index) in items" :key="index"
             :class="data.status==='0'?'':data.status==='3'?'dong':data.status==='1'? 'used':'over_time'">
          <div class="coupon_info">
            <dl>
              <dt>{{data.title}}</dt>
              <dd>
                <p v-if="data.redType==='0' &&　data.goodsName===undefined">仅限加油套餐使用</p>
                <p v-else-if="data.redType==='1'">仅限即时充值使用</p>
                <!--<p v-else>无限制</p>-->
                <p v-if="data.goodsName!==undefined" style="padding-top:5px;">仅限{{data.goodsName}}使用</p>
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
            <template v-if="data.status==='0'">
              <router-link tag="div" class="use" to="/recharge">去使用</router-link>
            </template>
          </div>
          <div class="flag" v-if="data.status!=='0'">
          </div>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'coupon',
    data () {
      return {
        items: [],
        pageNum: 2,
        isLoadDom: false,
        isLoading: true,
        isCheck: 0,
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
      handleCheck (item) {
        this.isCheck = item
        this.loadPage(1, 1)
      },
      loadPage (pageNum, type) {
        let _this = this
        postRequest(this.HOST + '/mine/allRedPackage', {
          token: _this.token,
          status: _this.isCheck,
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
            let overTimeStr = item.overTimeStr
            item.overTimeStr = _this.formatDate(overTimeStr, 'yyyy-MM-dd hh:mm:ss')
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
  .coupon_panel {
    .tab {
      width: 100%;
      padding: 0;
      background: #f5f5f5;
      position: absolute;
      top: 0;
      z-index: 99;
      height: auto;
      ul.oli {
        width: 100%;
        height: 40px;
        background: #ffffff;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.06);
        li {
          width: 33.33333%;
          height: auto;
          font-size: 14px;
          color: #333333;
          line-height: 40px;
          text-align: center;
          position: relative;
          float: left;
        }
        li.active {
          color: #FF4456;
        }
        li.active:after {
          content: '';
          width: 33px;
          height: 3px;
          background: #FF4456;
          position: absolute;
          bottom: 0;
          left: 50%;
          margin-left: -16.5px;
        }
      }
    }

    .coupon_list {
      width: 100%;
      height: auto;
      padding: 13px 13px 0 13px !important;
      .coupon_box {
        width: 100%;
        height: auto;
        background: #ffffff;
        padding: 0 15px;
        margin-bottom: 14px;
        border-radius: 2px;
        position:relative;
        box-shadow: 0 2px 4px #E8E8E8;
        .flag {
          width: 50px;
          height: 50px;
          position: absolute;
          bottom:10px;
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
            padding:12px 0;
            height: auto;
            dt {
              color: #000000;
              height:44px;
              line-height:44px;
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
          dl.fright{
            width:60%;
            dt{
               font-size:30px;
               color:#FF4456;
               text-align: right;
               span{
                  font-size:18px;
               }
            }
            dd{
              p{
                text-align: right;
              }
            }
          }
        }
        .coupon_time {
          width: 100%;
          clear: both;
          height: 43px;
          position:relative;
          overflow: hidden;
          line-height: 43px;
          font-size: 13px;
          text-align: left;
          .use{
             width:52px;
             height:22px;
             border-radius:11px;
             border:solid 1px #FF4456;
             text-align:center;
             color:#FF4456;
             line-height:21px;
             font-size:12px;
             position:absolute;
             right:0;
             top:9px;
          }
        }
      }
      .used, .over_time, .dong {
         dl{
            dt{
               color:#999999 !important;
            }
         }
         .coupon_time{
           color:#999999;
         }
      }
      .used {
        .flag {
          background: url("../../static/images/userd.png") no-repeat center center;
          background-size: contain;
        }
      }
      .dong {
        .flag {
          background: url("../../static/images/dong.png") no-repeat center center;
          background-size: contain;
        }
      }
      .over_time {
        .flag {
          background: url("../../static/images/overtime.png") no-repeat center center;
          background-size: contain;
        }
      }
    }
  }
</style>
