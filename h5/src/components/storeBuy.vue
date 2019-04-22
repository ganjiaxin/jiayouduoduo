<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="[items]"
      class="page_store bodyBg">
      <div class="page" style="padding-bottom:0;">
        <div class="storeImg">
          <div class="img" v-lazy:background-image="`${items.img}`"></div>
        </div>
        <div class="storeInfo">
          <h2>{{items.goodsName}}</h2>
          <div class="desc">
            <p>{{items.goodsTitle}}</p>
            <span v-if="items.prices>0">￥<label>{{items.Int}}</label>{{items.Last}}</span>
            <span v-else><label style="font-size:0.5rem">限免</label></span>
          </div>
        </div>
        <div class="detail">
          <h2><i></i>商品介绍</h2>
          <div class="con" v-html="items.introcture">

          </div>
        </div>
        <div class="detail">
          <h2><i></i>购买说明</h2>
          <div class="con" v-html="items.mess">

          </div>
        </div>
      </div>
    </cube-scroll>
    <div class="layer" v-if="isShow">
      <div class="dialog" :style="dialogCss">
        <div class="close" @click="handleClose">
          <div class="icon"></div>
        </div>
        <div class="title">
          购买信息
        </div>
        <div class="content">
          <div class="ticket_ul">
            <ul>
              <li>
                <label>{{items.goodsName}}</label>商品名称
              </li>
              <li>
                <label>{{num}}</label>商品数量
              </li>
              <li>
                <label>{{items.Int+items.Last}}元</label>商品价格
              </li>
              <li>
                <label>{{total}}元</label>订单总额
              </li>
              <li>
                <label>虚拟商品，系统自动发货</label>收货地址
              </li>
            </ul>
            <cube-button @click="handleBuy" class="dialog_btn">确认订单</cube-button>
          </div>
        </div>
      </div>
    </div>
    <div class="buyBar">
      <div class="tool">
        <p>商品库存：{{items.stock}}</p>
        <div class="select">
          <div class="reduce btn" @click="handleReduce"><i></i></div>
          <div class="num">{{num}}</div>
          <div class="add btn" @click="handleAddmoney"><i></i></div>
        </div>
      </div>
      <dl @click="handleLayer">
        <dt>立即购买</dt>
        <dd>￥{{items.category==='5'?'0.00（运费9.9）':total}}</dd>
      </dl>
      <div class="clear"></div>
    </div>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'stroe-buy',
    data () {
      return {
        items: [],
        price: '',
        total: '',
        num: 1,
        id: this.$route.query.id,
        token: localStorage.getItem('hykToken'),
        isShow: false,
        dialogCss: {
          top: '12%',
          bottom: '14%'
        },
        options: {
          swipeBounceTime: 400
        }
      }
    },
    mounted () {
      this.loadPage()
    },
    watch: {
      num (value) {
        let _this = this
        _this.total = (_this.items.prices * 100 * value / 100).toFixed(2)
      }
    },
    methods: {
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/shop/seeMallGoods', {
          id: _this.id,
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let detail = data.hykMallGoods
          var prices = detail.prices
          detail.introcture = _this.escape2Html(detail.introcture)
          detail.mess = _this.escape2Html(detail.mess)
          prices = prices.toString()
          if (prices.indexOf('.') !== -1) {
            let Int = prices.split('.')[0]
            let last = prices.split('.')[1]
            _this.$set(detail, 'Int', Int + '.')
            _this.$set(detail, 'Last', `${last}`)
          } else {
            _this.$set(detail, 'Int', prices + '.')
            _this.$set(detail, 'Last', '00')
          }
          _this.$nextTick(function () {
            _this.items = detail
            _this.total = detail.prices.toFixed(2)
          })
        })
      },
      escape2Html (str) {
        let arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"'}
        return str.replace(/&(lt|gt|nbsp|amp|quot);/ig, function (all, t) {
          return arrEntities[t]
        })
      },
      handleReduce () {
        let _this = this
        if (_this.num > 1) {
          _this.num -= 1
        } else {
          _this.num = 1
        }
      },
      handleLayer () {
        let _this = this
        if (_this.items.goodsType !== '1') {
          _this.isShow = true
        } else {
          _this.$router.push('/storeOrder?id=' + _this.id + '&num=' + _this.num)
        }
      },
      handleAddmoney () {
        let _this = this
        if (_this.items.stock > _this.num) {
          if (_this.items.category === '5') {
            _this.showToast('每个用户限领一张')
            return
          }
          _this.num += 1
        }
      },
      handleClose () {
        this.isShow = false
      },
      handleBuy () {
        let _this = this
        postRequest(this.HOST + '/shop/downOrder', {
          goodsId: _this.id,
          num: _this.num,
          token: _this.token
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.$router.replace('/payGoods?goodsId=' + data.hykMallOrder.id)
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
  .ticket_ul {
    width: 100%;
    height: auto;
    ul {
      li {
        width: 100%;
        height: auto;
        font-size: 14px;
        color: #999;
        text-align: left;
        line-height: 1;
        padding: 17px 0;
        border-bottom: solid 1px #ebebeb;
        label {
          float: right;
          color: #333;
          font-size: 15px;
        }
      }
      li:last-child {
        border: none;
      }
    }
  }

  .buyBar {
    width: 100%;
    position: fixed;
    height: 60px;
    bottom: 0;
    z-index: 888;
    background: #ffffff;
    box-shadow: 1px 0 8px rgba(0, 0, 0, 0.05);
    .tool {
      width: 66%;
      height: auto;
      padding-left: 0.3rem;
      position: relative;
      float: left;
      p {
        font-size: 13px;
        color: #999999;
        height: 36px;
        line-height: 36px;
        text-align: center;
        padding: 12px 0 12px 116px;
      }
      .select {
        width: 116px;
        height: 36px;
        position: absolute;
        top: 12px;
        left: 0.3rem;
        border: solid 1PX #dddddd;
        border-radius: 18px;
        .btn {
          width: 35px;
          height: 34px;
          line-height: 34px;
          position: relative;
          float: left;
          i {
            width: 13px;
            height: 13px;
            display: block;
            position: absolute;
            left: 50%;
            top: 50%;
            margin-top: -7px;
            margin-left: -7px;
            background: url("../../static/images/jian.png") center center no-repeat;
            background-size: contain;
          }
        }
        .num {
          width: 41px;
          height: 34px;
          font-size: 18px;
          color: #333333;
          background: #eeeeee;
          text-align: center;
          float: left;
          line-height: 34px;
        }
        .btn.add i {
          background: url("../../static/images/add.png") no-repeat center center;
          background-size: contain;
        }
      }
    }
    dl {
      width: 34%;
      height: 100%;
      background: #FF4456;
      float: right;
      padding: 8px 0;
      color: rgba(255, 255, 255, 0.95);
      dt {
        font-size: 18px;
        line-height: 1.4em;
        letter-spacing: 0.05rem;
      }
      dd {
        font-size: 14px;
        line-height: 1.4em;
      }
    }
  }

  .page {
    .storeImg {
      width: 100%;
      height: 300px;
      background: #ffffff;
      border-bottom: solid 1px #ebebeb;
      padding: 1rem 0;
      .img {
        width: 100%;
        height: 100%;
        background-color: #ffffff;
        background-repeat: no-repeat;
        background-size: contain;
        background-position: center center;
      }
    }
    .detail {
      width: 100%;
      height: auto;
      background: #ffffff;
      padding: 15px 0.4rem;
      margin-bottom: 14px;
      h2 {
        font-size: 16px;
        color: #333333;
        text-align: left;
        padding-bottom: 12px;
        font-weight: bold;
        margin-bottom: 0.06rem;
        clear: both;
        i {
          width: 3px;
          height: 15px;
          background: #FF4456;
          display: inline-block;
          border-radius: 0.1rem;
          margin-right: 0.15rem;
          vertical-align: -0.04rem;
        }
      }
      .con {
        width: 100%;
        height: auto;
        text-align: left;
        line-height: 1.5em;
        font-size: 14px;
        color: #666666;
        p {
          text-align: left;
          line-height: 1.5em;
          font-size: 14px;
          color: #666666;
          padding: 4px 0;
        }
        img {
          width: 100% !important;
          height: auto !important
        }
      }
    }
    .storeInfo {
      width: 100%;
      height: auto;
      background: #ffffff;
      margin-bottom: 14px;
      padding: 15px 0.4rem;
      h2 {
        text-align: left;
        font-size: 18px;
        line-height: 1.4em;
        padding-bottom: 8px;
      }
      .desc {
        width: 100%;
        position: relative;
        height: auto;
        p {
          width: 70%;
          font-size: 15px;
          color: #999999;
          line-height: 1.5em;
        }
        span {
          position: absolute;
          right: 0;
          top: 2px;
          font-size: 14px;
          color: #FF4456;
          label {
            font-size: 24px;
          }
        }
      }
    }
  }
</style>

