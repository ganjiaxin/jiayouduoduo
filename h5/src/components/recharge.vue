<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full">
      <div class="page recharge_page">
        <div class="select_ticket">
          <p class="title">
            <template v-if="items.length===0"><router-link to="/drawCard" tag="span">免费领卡</router-link></template>
            <template v-if="items.length>0">
              <router-link to="/addTicket" tag="span">+ 添加卡片</router-link>
            </template>
            选择充值油卡
          </p>
          <div class="add_ticket" :class="{show:items.length<1}">
            <router-link to="/addTicket">
              <p>暂无加油卡，请添加</p>
              <p class="btn"><i></i>添加油卡</p>
            </router-link>
          </div>
          <div id="ticketList" v-if="items.length>0">
            <div class="swiper-container swiper-recharge">
              <div class="swiper-wrapper">
                <div class="swiper-slide" v-for="(data,index) in items" :key="index">
                  <div class="card_box">
                    <div class="logo">
                      <svg class="icon" aria-hidden="true">
                        <use :xlink:href="data.oilType==='1'?'#zhongshiyou':'#zhongshihua'"></use>
                      </svg>
                      <p>{{data.oilType==='1'?'中国石油':'中国石化'}}</p>
                    </div>
                    <dl>
                      <dt>{{data.name}}</dt>
                      <dd>{{data.oliCardNo}}</dd>
                    </dl>
                  </div>
                </div>
              </div>
              <div class="swiper-pagination"></div>
            </div>
          </div>
          <div class="shadow">
          </div>
        </div>
        <div class="month_money">
          <p>每月加油金额</p>
          <div class="select_money">
            <div class="button reduce" @click="handleReduce">
            </div>
            <div class="money" @click="handleInput">
              {{money}}<label>元</label>
            </div>
            <div class="button add" @click="handleAddmoney">
            </div>
          </div>
        </div>
        <div class="select_type">
          <p>选择加油套餐</p>
          <div class="type_list">
            <div class="box" v-for="(data,index) in list" :key="index" :class="data.id===selectId?'active':''"
                 @click="selectMeal(data.id,data.cycle,data.activityDiscount)">
              <dl>
                <!--<div class="checked">-->
                  <!--<svg class="icon" aria-hidden="true" fill="#FFFFFF">-->
                    <!--<use xlink:href="#duigou"></use>-->
                  <!--</svg>-->
                <!--</div>-->
                <i class="flag hot" v-if="data.label==='1'"></i>
                <i class="flag limit" v-else-if="data.label==='0'"></i>
                <dt>{{data.Int}}<span>{{data.Last}}</span><label>折</label><s v-if="data.activityDiscount<data.discount">{{data.discount}}折</s>
                </dt>
                <dd>{{data.goodsName}}</dd>
              </dl>
            </div>
            <div class="clear"></div>
          </div>
        </div>
        <div class="recharge_info">
          <ul>
            <li @click="handleCoupon"><i class="more"></i><span :class="{usedCoupon:selectCoupon.length!==0}">{{selectCoupon.length===0?`有 ${count} 张可用优惠券`:`${selectCoupon.money}元 红包`}}</span>优惠券
            </li>
            <li @click="handleLayer"><i class="more"></i><span>查看计划</span>加油计划</li>
          </ul>
        </div>
        <p class="total">总计：<span>{{detail}}<label>{{totalMoney.toFixed(2)}}元</label></span></p>
        <div class="submit-button">
          <cube-button @click="handleRecharge(0)">确定</cube-button>
          <p>完成充值后，1个工作日内到账</p>
        </div>
      </div>
    </cube-scroll>
    <use-coupon :couponList="coupon" :couponId="couponId" :selectId="selectId" :amount="totalMoney" v-if="isCouponShow"
                v-on:listenChildEvent="getChildVal"></use-coupon>
    <div class="layer" v-if="isShow">
      <div class="dialog" :style="dialogCss">
        <div class="close" @click="handleClose">
          <div class="icon"></div>
        </div>
        <div class="title br">
          加油计划
        </div>
        <div class="content">
          <div class="layer_box">
            <cube-scroll
              ref="scroll_list"
              :options="options2"
              :data="plan"
              class="layer_list">
              <table>
                <tr>
                  <th>充值金额</th>
                  <th>到帐时间</th>
                </tr>
                <tr v-for="(data,index) in plan" :key="index">
                  <td>{{data.head}}期</td>
                  <td>{{data.foot}}</td>
                </tr>
                <tr>
                  <td colspan="2" class="planTip">到账时间为预期到账时间，如遇非工作日则顺延</td>
                </tr>
              </table>
            </cube-scroll>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Swiper from 'swiper'
  import {postRequest} from '../axios'
  import useCoupon from './useCoupon'

  export default {
    name: 'recharge',
    data () {
      return {
        swiper: null,
        items: [],
        list: [],
        detail: '',
        selectId: this.$route.query.id,
        options: {
          swipeBounceTime: 400
        },
        options2: {
          swipeBounceTime: 400
        },
        cardId: '',
        cardNo: '',
        token: localStorage.getItem('hykToken'),
        isShow: false,
        isCouponShow: false,
        money: this.$route.query.money === undefined ? 500 : +this.$route.query.money,
        totalMoney: 100,
        dialogCss: {
          top: '17%',
          bottom: '17%'
        },
        selectCoupon: [],
        count: 0,
        zk: this.$route.query.zk,
        zq: this.$route.query.zq,
        plan: [],
        coupon: [],
        couponId: ''
      }
    },
    watch: {
      items (n) {
        if (n.length <= 1) {
          if (n.length === 1) {
            this.cardId = n[0].id
            this.cardNo = n[0].oliCardNo.replace(/\s+/g, '')
          }
          return false
        }
        this.$nextTick(function () {
          this.initSwiper()
        })
      },
      money (value) {
        let _this = this
        if (_this.zk !== undefined || _this.zq !== undefined) {
          _this.detail = `${value}*${_this.zq}*${(_this.zk * 10) / 100}=`
          _this.totalMoney = value * _this.zq * (_this.zk * 10) / 100
        } else {
          _this.totalMoney = value
        }
        _this.loadCoupon(_this.totalMoney)
      }
    },
    mounted () {
      let _this = this
      _this.loadMeal()
      if (_this.selectId === undefined) {
        _this.selectId = ''
      }
      if (_this.zk !== undefined || _this.zq !== undefined) {
        _this.detail = `${_this.money}*${_this.zq}*${(_this.zk * 10) / 100}=`
        _this.totalMoney = _this.money * _this.zq * (_this.zk * 10) / 100
      }
      _this.loadCoupon(_this.totalMoney)
    },
    methods: {
      goBack () {
        let _this = this
        if (_this.isCouponShow) {
          _this.isCouponShow = false
          return false
        }
        _this.$router.go(-1)
      },
      getChildVal (data) {
        let _this = this
        _this.selectCoupon = data
        _this.isCouponShow = false
        if (data.length !== 0) {
          _this.couponId = data.id
        } else {
          _this.couponId = ''
        }
      },
      selectMeal (id, zq, zk) {
        let _this = this
        _this.selectId = id
        _this.zq = zq
        _this.zk = zk
        _this.detail = `${_this.money}*${zq}*${(zk * 10) / 100}=`
        _this.totalMoney = _this.money * zq * (zk * 10) / 100
        _this.loadCoupon(_this.totalMoney)
      },
      loadMeal () {
        let _this = this
        postRequest(this.HOST + '/oil/integration', {
          token: _this.token,
          type: 0,
          redType: 0
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let list = data.list1
          let oilCardList = data.oilCardList
          list.forEach(function (item, i) {
            let discount = item.activityDiscount.toString()
            if (discount.indexOf('.') !== -1) {
              let Int = discount.split('.')[0]
              let last = discount.split('.')[1]
              _this.$set(list[i], 'Int', Int)
              _this.$set(list[i], 'Last', `.${last}`)
            } else {
              _this.$set(list[i], 'Int', discount)
              _this.$set(list[i], 'Last', '')
            }
          })
          oilCardList.forEach(function (item, i) {
            let oliCard = item.oliCardNo
            item.oliCardNo = oliCard.replace(/(.{4})/g, '$1 ')
          })
          _this.$nextTick(function () {
            _this.list = list
            _this.items = oilCardList
          })
        })
      },
      loadCoupon (totalAmount) {
        let _this = this
        _this.selectCoupon = []
        _this.couponId = ''
        postRequest(this.HOST + '/mine/redPackage', {
          token: _this.token,
          minAmount: totalAmount,
          goodsId: _this.selectId,
          redType: 0
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let backList = data.list
          backList.forEach(function (item, i) {
            let overTimeStr = item.overTimeStr
            item.overTimeStr = _this.formatDate(overTimeStr, 'yyyy-MM-dd hh:mm:ss')
          })
          _this.coupon = backList
          _this.count = data.size
        })
      },
      handleCoupon () {
        this.isCouponShow = true
        if (window.history && window.history.pushState) {
          history.pushState(null, null, document.URL)
          window.addEventListener('popstate', this.goBack, false)
        }
      },
      handleLayer () {
        let _this = this
        if (_this.selectId === '') {
          _this.showToast('请选择充值套餐')
          return false
        } else if (_this.money === 0) {
          _this.showToast('每月加油金额不正确')
          return false
        }
        postRequest(this.HOST + '/mine/oilManager', {
          cycel: _this.zq,
          stagesAmt: _this.money
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.plan = data.hykOrder
          this.isShow = true
        })
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
      handleClose () {
        this.isShow = false
      },
      handleReduce () {
        let _this = this
        if (_this.money >= 200) {
          _this.money -= 100
        } else {
          _this.money = 100
        }
      },
      handleAddmoney () {
        let _this = this
        if (_this.money >= 10000) {
          _this.money = 10000
          return false
        }
        _this.money += 100
      },
      handleRecharge (type) {
        let _this = this
        if (_this.cardId === '' && type === 0) {
          _this.showDialog()
          return false
        }
        if (_this.selectId === '') {
          _this.showToast('请选择充值套餐')
          return false
        } else if (_this.money === 0) {
          _this.showToast('每月加油金额不正确')
          return false
        }
        postRequest(this.HOST + '/mine/packageRecharge', {
          token: _this.token,
          cardId: _this.cardId,
          cardNo: _this.cardNo,
          stagesAmt: _this.money,
          goodsId: _this.selectId,
          redpackageId: _this.couponId
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.$router.replace('/pay?orderNo=' + data.orderNo)
        })
      },
      handleInput () {
        let _this = this
        _this.dialog = _this.$createDialog({
          type: 'prompt',
          title: '自定义金额',
          prompt: {
            value: _this.money,
            placeholder: '请输入加油金额',
            type: 'tel'
          },
          onConfirm: (e, promptValue) => {
            if (!(/(^[1-9]\d*$)/.test(promptValue))) {
              _this.showToast('自定义金额不正确')
            } else if (+promptValue < 100) {
              this.showToast('金额为100的整数倍')
              _this.money = 100
            } else if (+promptValue > 10000) {
              this.showToast('金额最高上限1万元')
              _this.money = 10000
            } else if (+promptValue % 100 !== 0) {
              this.showToast('金额为100的整数倍')
              _this.money = parseInt(+promptValue / 100) * 100
            } else {
              _this.money = +promptValue
            }
          }
        }).show()
      },
      initSwiper () {
        let _this = this
        _this.swiper = new Swiper('.swiper-recharge', {
          watchSlidesProgress: true,
          slidesPerView: 'auto',
          centeredSlides: true,
          loop: true,
          onSlideChangeStart: function (swiper) {
            let index = swiper.realIndex
            _this.cardId = _this.items[index].id
            _this.cardNo = _this.items[index].oliCardNo.replace(/\s+/g, '')
          },
          onProgress: function (swiper, progress) {
            for (let i = 0; i < swiper.slides.length; i++) {
              let slide = swiper.slides.eq(i)
              let slideProgress = swiper.slides[i].progress
              let modify = 1
              if (Math.abs(slideProgress) > 1) {
                modify = (Math.abs(slideProgress) - 1) * 0.3 + 1
              }
              let translate = slideProgress * modify * 3 + 'px'
              let scale = 1 - Math.abs(slideProgress) / 7
              let zIndex = 999 - Math.abs(Math.round(10 * slideProgress))
              slide.transform('translateX(' + translate + ') scale(' + scale + ')')
              slide.css('zIndex', zIndex)
              slide.css('opacity', 1)
              if (Math.abs(slideProgress) > 2) {
                slide.css('opacity', 0)
              }
            }
          },
          onSetTransition: function (swiper, transition) {
            for (var i = 0; i < swiper.slides.length; i++) {
              var slide = swiper.slides.eq(i)
              slide.transition(transition)
            }
          }
        })
      },
      showDialog () {
        let _this = this
        _this.$createDialog({
          type: 'confirm',
          title: '提示',
          content: '当前账户暂未添加油卡，是否继续下单？',
          confirmBtn: {
            text: '确定',
            active: true,
            disabled: false,
            href: 'javascript:;'
          },
          cancelBtn: {
            text: '取消',
            active: false,
            disabled: false,
            href: 'javascript:;'
          },
          onConfirm: () => {
            _this.handleRecharge(1)
          }
        }).show()
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
      useCoupon
    },
    destroyed () {
      window.removeEventListener('popstate', this.goBack, false)
    }
  }
</script>
<style lang="less" src="../assets/recharege.less"></style>
<style lang="css" src="swiper/dist/css/swiper.min.css" scoped></style>
