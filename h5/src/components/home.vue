<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      class="page_scroll bodyBg">
      <div class="page user_page">
        <div class="top_bar">
          <router-link tag="div" class="msg" to="/msgCenter">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#xiaoxizhongxin"></use>
            </svg>
            <div class="tip" v-if="count>0">{{count>=100?'99+':count}}</div>
          </router-link>
          <router-link tag="dl" to="/userSet">
            <dt class="defaultLitpic" v-if="token!=null"></dt>
            <dd :class="{islogin:token!=null}">{{token==null?'登录/注册':phoneTxt}}</dd>
            <div class="clear"></div>
          </router-link>
        </div>
        <div class="ticket_banner">
          <router-link to="/addTicket" tag="div" class="add_ticket" :class="{show:items.length<1}">
            <div class="pd">
              <p :class="{noPlan:userReadyMoney===0 || token==null}"><i></i><span
                style="display:inline-block">添加油卡</span></p>
              <p class="warning" v-if="userReadyMoney>0"><label>（暂无油卡，加油计划无法执行，请添加或领取油卡）</label></p>
            </div>
            <ul>
              <li>本月应加：{{token==null?0:userReadyMoney}}元</li>
              <li>本月已加：0元</li>
              <div class="clear"></div>
            </ul>
          </router-link>
          <div id="ticketList" v-if="items.length>0">
            <div class="swiper-container swiper-home">
              <div class="swiper-wrapper">
                <div class="swiper-slide" v-for="(data,index) in items" :key="index">
                  <div class="card_box">
                    <router-link to="/myCard">
                      <div class="logo">
                        <p>
                          <svg class="icon" aria-hidden="true">
                            <use :xlink:href="data.oilType==='1'?'#zhongshiyou':'#zhongshihua'"></use>
                          </svg>
                          {{data.oilType==='1'?'中国石油':'中国石化'}}
                        </p>
                      </div>
                      <dl>
                        <dt>{{data.name}}</dt>
                        <dd>{{data.oliCardNo}}</dd>
                      </dl>
                      <ul>
                        <li>本月应加：{{data.ydzMoney}}元</li>
                        <li>本月已加：{{data.sjdzMoney}}元</li>
                        <div class="clear"></div>
                      </ul>

                    </router-link>
                  </div>
                </div>
              </div>
              <div class="swiper-pagination"></div>
            </div>
          </div>
          <!--<div class="shadow">-->
          <!--</div>-->
        </div>
        <div class="tab_menu">
          <div class="menus">
            <dl class="jh">
              <a href="/web/mobile/activity/payment_calender/index.html"></a>
              <dt></dt>
              <dd>加油计划</dd>
            </dl>
            <router-link tag="dl" to="/coupon" class="yhq">
              <dt></dt>
              <dd>优惠券</dd>
            </router-link>
            <router-link tag="dl" to="/order" class="jydd">
              <dt></dt>
              <dd>加油订单</dd>
            </router-link>
            <router-link tag="dl" to="/orderGoods" class="scdd">
              <dt></dt>
              <dd>商城订单</dd>
            </router-link>
            <div class="clear"></div>
          </div>
        </div>
        <div class="tool_list">
          <ul>
            <router-link tag="li" to="/balance" class="balance"><span class="more"></span><i></i>我的余额</router-link>
            <router-link tag="li" to="/myCard" class="manage"><span class="more"></span><i></i>油卡管理</router-link>
            <li class="help"><a href="/web/mobile/help/helpCenter_h5.html"></a><span class="more"></span><i></i>帮助中心
            </li>
            <li class="contact"><a href="/web/mobile/service/index.html"></a><span class="more"></span><i></i>联系我们
            </li>
          </ul>
        </div>
        <!--<div class="service_bar">-->
        <!--<h2>客服热线：4008-123-511</h2>-->
        <!--<p>服务时间：9:00-17:30</p>-->
        <!--<a href="tel:4008123511"></a>-->
        <!--</div>-->
      </div>
    </cube-scroll>
    <div class="layer" v-if="token==null">
      <div class="tipLogin">
        <dl>
          <dd>
            <p>立即注册</p>
            <p>享<span>加油优惠</span></p>
            <p>领取您的<span>专属福利</span></p>
            <button class="zdy-btn" @click="toLogin">登录/注册</button>
          </dd>
          <dt></dt>
          <div class="clear"></div>
        </dl>
      </div>
    </div>
    <footer-bar :sy="3"></footer-bar>
  </div>
</template>

<script>
  import Swiper from 'swiper'
  import footerBar from './footerBar'
  import {postRequest} from '../axios'

  export default {
    name: 'home',
    data () {
      return {
        swiper: null,
        defaultImg: '../../static/images/defaultLitpic.png',
        items: [],
        phone: localStorage.getItem('hykPhone'),
        phoneTxt: '',
        userReadyMoney: 0,
        options: {
          swipeBounceTime: 400
        },
        count: 0,
        token: localStorage.getItem('hykToken')
      }
    },
    mounted () {
      let _this = this
      if (_this.token !== null) {
        _this.phoneTxt = _this.phone.substr(0, 3) + '****' + _this.phone.substr(7)
        _this.loadPage()
      }
    },
    watch: {
      items (n) {
        if (n.length <= 1) {
          return false
        }
        this.$nextTick(function () {
          this.initSwiper()
        })
      }
    },
    methods: {
      toLogin () {
        this.$router.replace('/userJoin')
      },
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/mine/oilCard', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          let list = data.list
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.userReadyMoney = data.userReadyMoney
          list.forEach(function (item, i) {
            let oliCard = item.oliCardNo
            item.oliCardNo = oliCard.replace(/(.{4})/g, '$1 ')
          })
          _this.items = list
          _this.loadCount()
        })
      },
      loadCount () {
        let _this = this
        postRequest(this.HOST + '/msg/newTitle', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.count = data.count
        })
      },
      initSwiper () {
        this.mySwiper = new Swiper('.swiper-home', {
          watchSlidesProgress: true,
          slidesPerView: 'auto',
          centeredSlides: true,
          loop: true,
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
      footerBar
    }
  }
</script>

<style lang="less" scoped>
  .layer {
    z-index: 887 !important;
    .tipLogin {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      padding: 25px;
      position: absolute;
      bottom: 50px;
      dl {
        dd {
          width: 48%;
          height: auto;
          float: left;
          text-align: left;
          p {
            font-size: 17px;
            font-weight: bold;
            line-height: 1;
            margin-bottom: 15px;
            letter-spacing: 1px;
            span {
              color: #FF4456;
              font-weight: bold;
            }
          }
          button.zdy-btn {
            width: 110px;
            height: 35px;
            border: none;
            color: #FFFFFF;
            background: linear-gradient(to right, #FF8D3D, #FF3548);
            line-height: 35px;
            padding: 0;
            font-size: 15px;
            letter-spacing: 1px;
            outline: none;

          }
        }
        dt {
          width: 52%;
          height: 145px;
          background: url("../../static/images/mine_img.png") no-repeat center center;
          background-size: contain;
          float: left;
        }
      }
    }
  }

  .user_page {
    color: #333;
    padding-bottom: 0 !important;
    svg {
      width: 100%;
      height: 100%;
    }
    .top_bar {
      width: 100%;
      height: auto;
      padding: 15px 15px 25px 15px;
      background: #FFFFFF;
      dl {
        dt.defaultLitpic {
          background-image: url("../../static/images/defaultLitpic.png");
        }
        dt {
          width: 35px;
          height: 35px;
          background-size: cover;
          background-repeat: no-repeat;
          background-position: center center;
          border-radius: 50%;
          overflow: hidden;
          float: left;
        }
        dd {
          float: left;
          line-height: 35px;
          font-size: 18px;
          padding-left: 15px;
          color: #333;
        }
        dd.islogin {
          color: #000;
          letter-spacing: 1px;
        }
      }
      .msg {
        width: 23px;
        height: 35px;
        position: relative;
        float: right;
        .tip {
          width: auto;
          height: auto;
          background: #FF3D17;
          border-radius: 0.2rem;
          font-size: 10px;
          padding: 0.05rem 0.11rem;
          line-height: 1;
          right: -0.1rem;
          top: 0.035rem;
          position: absolute;
          color: #ffffff;
        }
        svg {
          width: 100%;
          height: 100%;
          fill: #FFFFFF;
        }
      }
    }
    .drawOlicard {
      width: 100%;
      height: 70px;
      background: url("../../static/images/olicard.png") no-repeat center center;
      background-size: cover;
      margin-bottom: 13px;
    }
    .ticket_banner {
      width: 100%;
      background: #FFFFFF;
      height: 152px;
      overflow: hidden;
      position: relative;
      margin-bottom: 15px;
      padding: 5px 0 15px 0;
      #ticketList {
        width: 100%;
        height: 100%;
        position: relative;
        z-index: 99;
        .swiper-container {
          width: 100%;
          height: 100%;
          .swiper-slide.swiper-slide-prev {
            transform-origin: 100% bottom !important;
          }
          .swiper-slide.swiper-slide-next {
            transform-origin: 0 bottom !important;
          }
          .swiper-slide.swiper-slide-active {
            transform-origin: 100% bottom !important;
          }
          .swiper-slide {
            width: 84%;
            position: relative;
            margin: 0 auto;
            .card_box {
              width: 7.8rem;
              height: 100%;
              border-radius: 5px;
              margin: 0 auto;
              position: relative;
              background: url("../../static/images/card_bg.png") no-repeat center center;
              background-size: cover;
              overflow: hidden;
              .logo{
                position: absolute;
                text-align: right;
                width: 100%;
                padding-right: 8px;
                top: 15px;
                p{
                  font-size: 14px;
                  color: rgba(255, 255, 255, 0.6);
                  text-align:right;
                  .icon {
                    width: 18px;
                    height:18px;
                    display:inline-block;
                    vertical-align: -4px;
                    fill: rgba(255, 255, 255, 0.6)
                  }
                }
              }
              ul {
                width: 100%;
                height: auto;
                background: rgb(255, 221, 226);
                position: absolute;
                left: 0;
                bottom: 0;
                z-index: 9;
                li {
                  float: left;
                  width: 50%;
                  font-size: 12px;
                  color: #F73F55;
                  padding: 12px 0;
                  border-right: solid 1px rgba(255, 255, 255, 0.05)
                }
              }
              dl {
                width: 100%;
                padding: 28px 12px 20px 12px;
                dt {
                  color: #FFFFFF;
                  text-align: left;
                  font-size: 16px;
                  line-height: 1;
                  padding-bottom: 16px;
                  letter-spacing: 2px;
                }
                dd {
                  text-align: left;
                  color: #FFFFFF;
                  letter-spacing: 0;
                  text-shadow: 1PX 1PX 2PX rgba(0, 0, 0, 0.15);
                  font-size: 18px;
                }
              }
            }
          }
        }
      }

      .add_ticket.show {
        display: block;
      }
      .add_ticket {
        width: 7.8rem;
        height: 100%;
        display: none;
        position: relative;
        z-index: 999;
        margin: 0 auto;
        border-radius: 5px;
        overflow: hidden;
        background: url("../../static/images/card_bg.png") no-repeat center center;
        background-size: cover;
        .pd {
          width: 100%;
          padding: 18px 0;
          p.noPlan {
            height: 78px;
            line-height: 78px;
          }
        }
        ul {
          width: 100%;
          height: auto;
          background: rgb(255, 221, 226);
          position: absolute;
          left: 0;
          bottom: 0;
          z-index: 9;
          li {
            float: left;
            width: 50%;
            font-size: 13px;
            color: #F73F55;
            padding: 12px 0;
            border-right: solid 1px rgba(255, 255, 255, 0.05)
          }
        }
        p {
          text-align: center;
          width: 100%;
          color: #FFFFFF;
          font-size: 15px;
          line-height: 30px;
          height: 30px;
          letter-spacing: 2px;
          i {
            width: 12px;
            height: 12px;
            display: inline-block;
            vertical-align: center;
            background: url("../../static/images/plus.png") no-repeat center center;
            background-size: contain;
            margin-right: 5px;
          }
        }
        p.warning {
          font-size: 18px;
          letter-spacing: 0;
          label {
            font-size: 11px;
            letter-spacing: 0.01rem;
          }
        }
      }
    }
    .tab_menu {
      width: 100%;
      height: auto;
      padding: 0 10px;
      margin-bottom: 15px;
      .menus {
        width: 100%;
        height: auto;
        background: #ffffff;
        border-radius: 5px;
        padding: 8px 0;
        dl {
          width: 25%;
          height: auto;
          padding: 8px 0 5px 0;
          position: relative;
          float: left;
          a {
            width: 100%;
            height: 100%;
            position: absolute;
            display: block;
            top: 0;
            left: 0;
          }
          dt {
            width: 34px;
            height: 34px;
            background: url("../../static/images/jh.png") no-repeat center center;
            background-size: contain;
            margin: 0 auto;
          }
          dd {
            font-size: 14px;
            line-height: 2em;
          }
        }
        dl.scdd {
          dt {
            background: url("../../static/images/scdd.png") no-repeat center center;
            background-size: contain;
          }
        }
        dl.yhq {
          dt {
            background: url("../../static/images/yhq.png") no-repeat center center;
            background-size: contain;
          }
        }
        dl.jydd {
          dt {
            background: url("../../static/images/jydd.png") no-repeat center center;
            background-size: contain;
          }
        }
      }
    }
    .tool_list {
      width: 100%;
      height: auto;
      padding: 0 10px;
      margin-bottom: 15px;
      ul {
        width: 100%;
        height: auto;
        border-radius: 5px;
        padding: 5px 10px;
        background: #FFFFFF;
        li {
          font-size: 14px;
          border-bottom: solid 1px #eeeeee;
          padding: 16px 5px;
          position: relative;
          text-align: left;
          a {
            width: 100%;
            height: 100%;
            position: absolute;
            display: block;
            top: 0;
            left: 0;
          }
          i {
            width: 15px;
            height: 15px;
            display: inline-block;
            vertical-align: -2px;
            margin-right: 8px;
          }
          span {
            width: 12px;
            height: 12px;
            display: block;
            float: right;
            background: url("../../static/images/more_icon.png") no-repeat center center;
            background-size: contain;
          }
        }
        li.contact {
          i {
            background: url("../../static/images/contact_icon.png") no-repeat center center;
            background-size: contain;
          }
        }
        li.balance {
          i {
            background: url("../../static/images/balance_icon2.png") no-repeat center center;
            background-size: contain;
          }
        }
        li.manage {
          i {
            background: url("../../static/images/manage_icon.png") no-repeat center center;
            background-size: contain;
          }
        }
        li.help {
          i {
            background: url("../../static/images/help_icon.png") no-repeat center center;
            background-size: contain;
          }
        }
        li:last-child {
          border: none;
        }
      }
    }
  }
</style>
<style lang="css" src="swiper/dist/css/swiper.min.css" scoped></style>
