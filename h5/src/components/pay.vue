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
              <li><span>{{hykOrder.cardNo===''?'当前账户暂未绑定油卡':hykOrder.cardNo}}</span>充值油卡号</li>
              <li><span>{{hykOrder.orderNo}}</span>订单编号</li>
              <!--<li><span>{{hykOrder.createDateStr}}</span>订单生成时间</li>-->
              <li v-if="hykOrder.goodsType==='0'"><span>{{hykOrder.activityDiscount}}折/{{hykOrder.cycel}}个月</span>充值套餐
              </li>
              <li v-else><span>{{hykOrder.val}}元/即时充值</span>充值套餐</li>
              <li><span>{{hykOrder.amt}}元</span>订单总额</li>
              <li v-if="hykOrder.redpackageAmt>0"><span style="color:red">-{{hykOrder.redpackageAmt}}</span>优惠减免</li>
            </ul>
            <p @click="handleShow" v-show="!ulShow"><i class="down"></i>查看订单详情</p>
          </div>
        </div>
        <div class="pay">
          <p>选择支付方式，并支付</p>
          <ul>
            <li :class="{disabled:balancePay}" @click="handleOtherPay(4)">
              <i class="balance"></i>
              <dl>
                <dt>余额支付<span v-if="isGroup">（组合支付：{{balance}}元）</span>
                </dt>
                <dd><label v-if="balancePay">余额不足,</label>可用余额（{{balance}}元）</dd>
              </dl>
              <div class="checkbox" v-if="!balancePay" :class="{active: selectBalance}">
                <div>
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#duigou"></use>
                  </svg>
                </div>
              </div>
              <div class="clear"></div>
            </li>
            <li v-for="(data,index) in otherPay" :key="index" @click="handleOtherPay(index)" v-if="goodsType==='0'">
              <i :class="data.class"></i>{{data.type}}<span v-if="isGroup">（组合支付：{{((hykOrder.orderMoney*100)-(balance*100))/100}}元）</span>
              <div class="checkbox" :class="{active:selectIndex===index}">
                <div>
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#duigou"></use>
                  </svg>
                </div>
              </div>
            </li>
            <li class="disabled">
              <i class="alipay"></i>支付宝支付（暂仅支持APP使用）
            </li>
            <li class="disabled">
              <i class="wechat"></i>微信支付（暂仅支持APP使用）
            </li>
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
        orderNo: this.$route.query.orderNo,
        ulShow: false,
        token: localStorage.getItem('hykToken'),
        countTime: '',
        myCount: '',
        goodsType: '',
        isDisabled: false,
        btnTxt: '下一步',
        balance: '',
        otherPay: [
          {'type': '快捷支付', 'class': 'pay'}
          // {'type': '支付宝支付', 'class': 'alipay', 'isSelect': false},
          // {'type': '微信支付', 'class': 'wechat', 'isSelect': false}
        ],
        selectIndex: '',  // 其他支付参数
        selectBalance: false, // 是否选中余额支付
        isGroup: false,  // 是否显示组合支付提示
        dataGroup: 1,    // 余额支付 1  快捷支付 0 组合支付 1
        balancePay: true // 余额是否可用
      }
    },
    mounted () {
      this.loadPage()
    },
    watch: {

    },
    methods: {
      loadPage () {
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
          let total = list.orderMoney.toString()
          if (list.redpackageAmt > 0) {
            total = list.orderMoney.toString()
          }
          if (total.indexOf('.') !== -1) {
            let Int = total.split('.')[0]
            let last = total.split('.')[1]
            _this.$set(list, 'Int', Int)
            _this.$set(list, 'Last', `.${last}`)
          } else {
            _this.$set(list, 'Int', total)
            _this.$set(list, 'Last', '.00')
          }
          _this.hykOrder = list
          _this.balance = list.balancePayment === 0 ? data.balance : list.balancePayment
          _this.goodsType = list.goodsType
          if (list.goodsType === '0') {
            if (_this.balance === 0) {
              _this.selectIndex = 0
              _this.dataGroup = 0
              return false
            } else if (_this.balance < list.orderMoney) {
              _this.balancePay = false
              _this.selectIndex = 0
              _this.selectBalance = true
              _this.isGroup = true
            } else {
              _this.balancePay = false
              _this.selectBalance = true
            }
          } else {
            if (_this.balance < list.orderMoney) {
              _this.isDisabled = true
              return false
            }
            _this.balancePay = false
            _this.dataGroup = 1
            _this.selectBalance = true
          }
          let time = (list.createTime + 1800) - list.sysTime
          if (time > 0) {
            _this.getCountDown(time)
          } else {
            _this.isDisabled = true
            _this.btnTxt = '订单已过期'
            _this.countTime = '00:00'
          }
        })
      },
      handleOtherPay (index) {
        let _this = this
        if (_this.hykOrder.balancePayment !== 0) {
          return false
        }
        if (_this.goodsType === '0') {
          if (_this.balance === 0) {
            _this.dataGroup = 0
            return false
          } else if (_this.balance < _this.hykOrder.orderMoney && _this.balance > 0) {
            if (index === 4) {
              _this.selectBalance = !_this.selectBalance
              _this.isGroup = !_this.isGroup
              _this.dataGroup = _this.isGroup ? 1 : 0
            }
            return false
          } else {
            _this.selectIndex = index
            if (index < 4) {
              _this.selectIndex = index
              _this.selectBalance = false
              _this.dataGroup = 0
            } else {
              _this.dataGroup = 1
              _this.selectBalance = true
            }
          }
        } else {
          if (_this.balance < _this.hykOrder.orderMoney) {
            _this.isDisabled = true
            return false
          }
          _this.balancePay = false
          _this.dataGroup = 1
          _this.selectBalance = true
        }
      },
      handleShow () {
        this.ulShow = true
      },
      handlePay () {
        let _this = this

        postRequest(this.HOST + '/f/web/pay/createOrder', {
          token: _this.token,
          id: _this.hykOrder.id,
          payType: 3,
          type: _this.dataGroup,
          chnl: 3
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            if (data.code === '201') {
              event.preventDefault()
              location.replace(data.url)
              return false
            }
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
