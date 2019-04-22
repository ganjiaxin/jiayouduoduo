<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full">
      <div class="page recharge_page">
        <div class="select_ticket">
          <p class="title"><template v-if="items.length===0"><router-link to="/drawCard" tag="span">免费领卡</router-link></template><template v-if="items.length>0"><router-link to="/addTicket" tag="span">+ 添加卡片</router-link></template>选择充值油卡</p>
          <div class="add_ticket" :class="{show:items.length<1}">
            <router-link to="/addTicket">
              <p>暂无加油卡，请添加</p>
              <p class="btn"><i></i>添加油卡</p>
            </router-link>
          </div>
          <div id="ticketList" v-if="items.length>0">
            <div class="swiper-container swiper-recharge2">
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
        <div class="select_type">
          <p>选择加油套餐</p>
          <div class="type_list">
            <div class="box"  v-for="(data,index) in list" :key="index" :class="data.id===selectId?'active':''"
                 @click="selectMeal(data.id)">
              <dl>
                <!--<div class="checked">-->
                  <!--<svg class="icon" aria-hidden="true" fill="#FFFFFF">-->
                    <!--<use xlink:href="#duigou"></use>-->
                  <!--</svg>-->
                <!--</div>-->
                <i class="flag hot" v-if="data.label==='1'"></i>
                <i class="flag limit" v-else-if="data.label==='0'"></i>
                <dt>{{data.val}}<label>元</label></dt>
                <dd>售价{{data.discountMoney}}元</dd>
              </dl>
            </div>
            <!--<div class="box custom">-->
              <!--<dl>-->
                <!--<dd>自定义</dd>-->
              <!--</dl>-->
            <!--</div>-->
            <div class="clear"></div>
          </div>
        </div>
        <!--<div class="month_money">-->
          <!--<p>自定义金额</p>-->
          <!--<div class="select_money">-->
            <!--<div class="button reduce">-->
            <!--</div>-->
            <!--<div class="money">-->
              <!--{{money}}<label>元</label>-->
            <!--</div>-->
            <!--<div class="button add">-->
            <!--</div>-->
          <!--</div>-->
          <!--<p class="custom_money">售价99元</p>-->
        <!--</div>-->
        <div class="submit-button">
          <cube-button  @click="handleRecharge(0)">确定</cube-button>
          <p>完成充值后，1个工作日内到账</p>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import Swiper from 'swiper'
  import {postRequest} from '../axios'
  export default {
    name: 'recharge2',
    data () {
      return {
        swiper: null,
        items: [],
        list: [],
        selectId: this.$route.query.id,
        options: {
          swipeBounceTime: 400
        },
        cardId: '',
        cardNo: '',
        token: localStorage.getItem('hykToken'),
        money: 100
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
      }
    },
    mounted () {
      let _this = this
      _this.loadMeal()
      if (_this.selectId === undefined) {
        _this.selectId = ''
      }
    },
    methods: {
      selectMeal (id) {
        let _this = this
        _this.selectId = id
      },
      loadMeal () {
        let _this = this
        postRequest(this.HOST + '/oil/integration', {
          token: _this.token,
          type: 1,
          redType: 1
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let oilCardList = data.oilCardList
          oilCardList.forEach(function (item, i) {
            let oliCard = item.oliCardNo
            item.oliCardNo = oliCard.replace(/(.{4})/g, '$1 ')
          })
          _this.$nextTick(function () {
            _this.list = data.list1
            _this.items = oilCardList
          })
        })
      },
      handleRecharge (type) {
        let _this = this
        if (_this.cardId === '' && type === 0) {
          _this.showDialog()
          return false
        }
        if (_this.selectId === '') {
          _this.showToast('请选择充值金额')
          return false
        }
        postRequest(this.HOST + '/mine/recharge', {
          token: _this.token,
          cardId: _this.cardId,
          cardNo: _this.cardNo,
          // stagesAmt: _this.money,
          isCustom: 0,
          goodsId: _this.selectId
          // redpackageId: _this.couponId
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.$router.replace('/pay?orderNo=' + data.orderNo)
        })
      },
      initSwiper () {
        let _this = this
        _this.swiper = new Swiper('.swiper-recharge2', {
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
        this.$createDialog({
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
            this.handleRecharge(1)
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
    }
  }
</script>
<style lang="less" src="../assets/recharege.less"></style>
<style lang="css" src="swiper/dist/css/swiper.min.css" scoped></style>
