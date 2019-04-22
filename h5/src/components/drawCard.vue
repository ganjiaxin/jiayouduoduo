<template>
  <div class="content_panel draw_card">
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
          <div class="storeOrder_2 white">
            <p>油卡类型</p>
            <ul>
              <li v-for="(data,index) in items" :key="index" :class="data.goodsName==='中石油加油卡'?'zsy':'zsh'">
                <div class="card" :class="{active:oilType===index}" @click="selectType(index,data.id)">
                  <p>剩余库存 {{data.stock}}</p>
                </div>
              </li>
              <div class="clear"></div>
            </ul>
          </div>

          <div class="storeOrder_2_bottom">
            <div>
              数量
            </div>
            <div>
              1张
            </div>
          </div>
        </div>
        <div class="sendType">
          <p><span><span class="sendType_or">￥9.9</span>（油卡0元+运费9.9元）</span>总计</p>
        </div>
        <div class="mark_rule">
          <span class="mark_top">
               领取规则
          </span>
          <div class="mark_main">
            1. 领取时间:从即日起，长期有效；
            <br>
            2. 活动期间，每位用户每次限领一张，最多可领4张（中石化和中石油各两张）；
            <br>
            3. 油卡均为百德网公司副卡，每张副卡均单独使用，但不具备在其他地方充值的功能，仅限在加油多多内进行充值;用户购买油卡发货时，自动绑定；
            <br>
            4. 活动期间，用户需要支付固定的9.9元运费，付款成功后暂不支持退款；
            <br>
            5. 用户选择领取的油卡类型，添加收货地址；用户需要手动输入正确的收货人姓名、电话和地址，确保加油卡准确送达；
            <br>
            6. 油卡在用户付款成功后的3-5个工作日内寄出，寄出后以短信和站内信等形式通知用户，请注意查收；
            <br>
            7. 如有任何疑问请咨询客服人员:4008-123-511；
            <br>
            8. 本次领取油卡的最终解释权归加油多多所有。
          </div>
        </div>
      </div>
    </cube-scroll>
    <select-address :addressList="list" :addressId="addressId" v-if="isShow"
                    v-on:listenChildEvent="getChildVal"></select-address>
    <div class="buyBars">
      <dl @click="handleBuy">
        <dt>领取油卡</dt>
      </dl>
      <div class="clear"></div>
    </div>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  import selectAddress from './selectAddress'

  export default {
    name: 'draw-card',
    data () {
      return {
        oilType: 0,
        items: [],
        list: [],
        addressId: '',
        addressName: '',
        address: '',
        addressPhone: '',
        total: '',
        remark: '',
        num: 1,
        isShow: false,
        token: localStorage.getItem('hykToken'),
        goodsId: '',
        options: {
          swipeBounceTime: 400
        }
      }
    },
    mounted () {
      this.loadAddress()
    },
    methods: {
      selectType (type, id) {
        let _this = this
        _this.oilType = type
        _this.goodsId = id
      },
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
        this.$router.push(`/addAddress?type=drawcard`)
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
        postRequest(this.HOST + '/f/web/oil/oilPage', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.items = data.data
          _this.goodsId = data.data[0].id
        })
      },
      handleBuy () {
        let _this = this
        postRequest(this.HOST + '/shop/downOrderOilCard', {
          goodsId: _this.goodsId,
          num: _this.num,
          addressId: _this.addressId,
          remark: _this.remark,
          channel: 'H5',
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
  .draw_card {
    .storeOrder_2 {
      width: 100%;
      padding: 10px 0 20px 0;
      font-size: 14px;
      margin-bottom: 15px;
      color: #333333;
      border-bottom: 1px solid #f5f5f5;
      p {
        line-height: 1;
        padding: 0 15px 13px 0;
        font-size: 16px;
      }
      ul {
        width: 100%;
        height: auto;
        /*padding: 0 7.5px;*/
        li.zsh {
          width: 50%;
          height: auto;
          float: left;
          padding-right:5px;
          .card {
            width: 100%;
            height: 98px;
            /*background: -webkit-linear-gradient(left top, #E6E6E6, #A3A3A3);*/
            /*background: -o-linear-gradient(bottom right, #E6E6E6, #A3A3A3);*/
            /*background: -moz-linear-gradient(bottom right, #E6E6E6, #A3A3A3);*/
            /*background: linear-gradient(to bottom right, #E6E6E6, #A3A3A3);*/
            background: url("../../static/images/zhongshihua_h.png");
            background-size: 100% 100%;
            border-radius: 4px;
            position: relative;
            overflow: hidden;
            svg {
              width: 80px;
              height: 70px;
              position: absolute;
              bottom: -20px;
              right: -15px;
              fill: rgba(255, 255, 255, 0.4);
            }
            p {
              font-size: 14px;
              color: white;
              padding: 50px 15px 15px 15px;
              letter-spacing: 1px;
            }
          }
          .card.active {
            /*background: -webkit-linear-gradient(left top, #FF8D3D, #FF3548);*/
            /*background: -o-linear-gradient(bottom right, #FF8D3D, #FF3548);*/
            /*background: -moz-linear-gradient(bottom right, #FF8D3D, #FF3548);*/
            /*background: linear-gradient(to bottom right, #FF8D3D, #FF3548);*/
            background: url("../../static/images/zhongshihua_b.png");
            background-size: 100% 100%;
            p {
              color: #FFFFFF;
            }
          }
        }
        li.zsy {
          width: 50%;
          height: auto;
          padding-left:5px;
          float: left;
          .card {
            width: 100%;
            height: 98px;
            /*background: -webkit-linear-gradient(left top, #E6E6E6, #A3A3A3);*/
            /*background: -o-linear-gradient(bottom right, #E6E6E6, #A3A3A3);*/
            /*background: -moz-linear-gradient(bottom right, #E6E6E6, #A3A3A3);*/
            /*background: linear-gradient(to bottom right, #E6E6E6, #A3A3A3);*/
            background: url("../../static/images/zhongshiyou_h.png");
            background-size: 100% 100%;
            border-radius: 4px;
            position: relative;
            overflow: hidden;
            svg {
              width: 80px;
              height: 70px;
              position: absolute;
              bottom: -20px;
              right: -15px;
              fill: rgba(255, 255, 255, 0.4);
            }
            p {
              font-size: 14px;
              color: white;
              padding: 50px 15px 15px 15px;
              letter-spacing: 1px;
            }
          }
          .card.active {
            /*background: -webkit-linear-gradient(left top, #FF8D3D, #FF3548);*/
            /*background: -o-linear-gradient(bottom right, #FF8D3D, #FF3548);*/
            /*background: -moz-linear-gradient(bottom right, #FF8D3D, #FF3548);*/
            /*background: linear-gradient(to bottom right, #FF8D3D, #FF3548);*/
            background: url("../../static/images/zhongshiyou_b.png");
            background-size: 100% 100%;
            p {
              color: #FFFFFF;
            }
          }
        }
      }
    }
    .storeOrder_2_bottom {
      height: 20px;
      div {
        color: #333;
        font-size: 16px;
        display: inline-block;
        float: left;
      }
      div:last-child {
        color: #333;
        font-size: 16px;
        display: inline-block;
        float: right;
      }
    }

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
        width: 100%;
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
        border-bottom: 1px solid #f5f5f5;
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
      .mark_rule {
        width: 100%;
        padding: 12px 15px 12px 12px;
        height: auto;
        background: #ffffff;
        position: relative;
        color: #333333;
        text-align: left;
        .mark_top {
          color: #333333;
          font-size: 16px;
          margin-bottom: 15px;
          display: inline-block;
        }
        .mark_main {
          color: #999999;
          font-size: 14px;
          text-align: left;
          line-height: 24px;
        }
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
          font-size: 16px;
          line-height: 54px;
          color: #333333;
          span {
            float: right;
            color: #999999;
            span {
              color: #FF4456;
              font-size: 18px;
              float: left;
            }
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
  }
</style>

