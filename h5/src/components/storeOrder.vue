<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="[items]"
      class="page_store bodyBg">
      <div class="page storePage" style="padding-bottom:0;">
        <div class="selectAddress">
          <div class="icon"></div>
          <p v-if="list.length===0" @click="handleAdd" v-cloak>请添加收货地址</p>
          <dl v-else @click="handleSelect" v-cloak>
            <dt>{{addressName}}<label>{{addressPhone}}</label></dt>
            <dd>{{address}}</dd>
          </dl>
        </div>
        <div class="goodsInfo">
          <dl>
            <dt v-lazy:background-image="`${items.img}`"></dt>
            <dd>
              <h2>{{items.goodsName}}</h2>
              <p>{{items.goodsTitle}}</p>
              <p class="price">￥{{prices}}<label>x</label>{{num}}</p>
            </dd>
          </dl>
        </div>
        <div class="sendType">
          <p><span>普通快递&nbsp;&nbsp;&nbsp;{{items.category==='5'?'运费9.9元':'包邮'}}</span>配送方式</p>
        </div>
        <div class="mark">
          <label>买家备注</label>
          <textarea placeholder="如有特殊要求请备注" v-model="remark"></textarea>
        </div>
      </div>
    </cube-scroll>
    <select-address :addressList="list" :addressId="addressId" v-if="isShow"
                    v-on:listenChildEvent="getChildVal"></select-address>
    <div class="buyBars">
      <div class="tool">
        <p>合计金额：<span>￥{{items.category==='5'?'9.9':total}}</span></p>
      </div>
      <dl @click="handleBuy">
        <dt>立即购买</dt>
      </dl>
      <div class="clear"></div>
    </div>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  import selectAddress from './selectAddress'

  export default {
    name: 'stroe-order',
    data () {
      return {
        items: [],
        list: [],
        addressId: '',
        addressName: '',
        address: '',
        addressPhone: '',
        total: '',
        remark: '',
        isShow: false,
        id: this.$route.query.id,
        token: localStorage.getItem('hykToken'),
        num: this.$route.query.num,
        options: {
          swipeBounceTime: 400
        }
      }
    },
    mounted () {
      this.loadAddress()
    },
    methods: {
      goBack () {
        let _this = this
        if (_this.isShow) {
          _this.isShow = false
          return false
        }
        _this.$router.go(-1)
      },
      getChildVal (data) {
        let _this = this
        _this.addressId = data.id
        _this.addressName = data.name
        _this.addressPhone = data.phone
        _this.address = data.address
        _this.isShow = false
      },
      handleAdd () {
        this.$router.push(`/addAddress?type=select&goodsId=${this.id}&num=${this.num}`)
      },
      handleSelect () {
        this.isShow = true
        if (window.history && window.history.pushState) {
          history.pushState(null, null, document.URL)
          window.addEventListener('popstate', this.goBack, false)
        }
      },
      loadAddress () {
        let _this = this
        postRequest(this.HOST + '/mine/seeAddress', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.loadPage()
          _this.$nextTick(function () {
            if (data.list.length > 0) {
              _this.list = data.list
              _this.addressId = data.list[0].id
              _this.addressName = data.list[0].name
              _this.addressPhone = data.list[0].phone
              _this.address = data.list[0].address
            }
          })
        })
      },
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
          _this.$nextTick(function () {
            _this.items = data.hykMallGoods
            _this.prices = data.hykMallGoods.prices.toFixed(2)
            _this.total = (_this.prices * _this.num).toFixed(2)
          })
        })
      },
      handleBuy () {
        let _this = this
        let url = '/shop/downOrder'
        if (_this.items.category === '5') {
          url = '/shop/downOrderOilCard'
        }
        postRequest(this.HOST + url, {
          goodsId: _this.id,
          num: _this.num,
          addressId: _this.addressId,
          remark: _this.remark,
          channal: 'H5',
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
    },
    components: {
      selectAddress
    },
    destroyed () {
      window.removeEventListener('popstate', this.goBack, false)
    }
  }
</script>

<style lang="less">
  .buyBars {
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
        height: 60px;
        line-height: 60px;
        text-align: left;
        span {
          font-size: 24px;
          color: #FF4456;
        }
      }
    }
    dl {
      width: 34%;
      height: 100%;
      background: #FF4456;
      float: right;
      color: rgba(255, 255, 255, 0.95);
      dt {
        font-size: 18px;
        line-height: 60px;
        letter-spacing: 0.05rem;
      }
    }
  }

  .storePage {
    padding-top: 10px;
    .goodsInfo {
      width: 100%;
      height: auto;
      background: #ffffff;
      padding: 15px;
      margin-bottom: 15px;
      dl {
        width: 100%;
        position: relative;
        height: auto;
        dd {
          width: 100%;
          height: auto;
          padding-left: 95px;
          color: #333333;
          h2 {
            font-size: 18px;
            text-align: left;
            line-height: 1.5em;
          }
          p {
            font-size: 13px;
            color: #666666;
            line-height: 1.5em;
            height: 35px;
          }
          p.price {
            font-size: 20px;
            color: #333333;
            height: auto;
            label {
              color: #999999;
              padding: 0 8px;
            }
          }
        }
        dt {
          width: 80px;
          height: 80px;
          position: absolute;
          top: 5px;
          background-size: cover;
          background-position: center center;
          background-repeat: no-repeat;
        }
        dt[lazy=loading], dt[lazy=error] {
          background-size: 35px 40px !important;
          background-color: #f5f5f5;
        }
      }
    }
    .mark {
      width: 100%;
      padding: 12px 15px 12px 85px;
      height: auto;
      background: #ffffff;
      position: relative;
      color: #333333;
      label {
        font-size: 14px;
        position: absolute;
        left: 15px;
        top: 18px;
      }
      textarea {
        border: none;
        width: 100%;
        height: 60px;
        outline: none;
        font-size: 14px;
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
      }
    }
    .sendType {
      width: 100%;
      height: 54px;
      padding: 0 15px;
      margin-bottom: 15px;
      background: #ffffff;
      p {
        font-size: 14px;
        line-height: 54px;
        color: #333333;
        span {
          float: right;
        }
      }
    }
    .selectAddress {
      width: 100%;
      height: 85px;
      position: relative;
      background: #ffffff url("../../static/images/more_icon.png") no-repeat 97.5% center;
      background-size: 13px 13px;
      margin-bottom: 15px;
      .icon {
        width: 17px;
        height: 20px;
        background: url("../../static/images/porint.png") no-repeat center center;
        background-size: contain;
        position: absolute;
        top: 50%;
        left: 15px;
        margin-top: -10px;
      }
      p {
        width: 100%;
        line-height: 85px;
        padding-left: 45px;
        font-size: 18px;
        letter-spacing: 0.03rem;
      }
      dl {
        width: 100%;
        height: auto;
        padding: 19px 0 0 45px;
        dt {
          color: #333333;
          font-size: 18px;
          line-height: 1.5em;
          text-align: left;
          label {
            font-size: 16px;
            padding-left: 15px;
          }
        }
        dd {
          text-align: left;
          line-height: 1.5em;
          font-size: 13px;
          color: #666666;
        }
      }
    }
  }
</style>

