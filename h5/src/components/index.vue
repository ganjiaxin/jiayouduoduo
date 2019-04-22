<template>
  <div class="content_panel index_panel">
    <cube-scroll
      ref="indexScroll"
      :options="options"
      :data="banner"
      class="page_scroll bodyBg">
      <div class="page">
        <div class="banner">
          <div class="swiper-container banner_swiper">
            <div class="swiper-wrapper">
              <div class="swiper-slide" v-for="(data,index) in banner" :key="index">
                <a :href="data.url">
                  <div class="img bgimg_cover" v-lazy:background-image="`${data.bannerImg}`"></div>
                </a>
              </div>
            </div>
            <div class="swiper-pagination"></div>
          </div>
          <div class="box">
            <div class="float">
              <div class="tool_bar">
                <div class="notice">
                  <cube-slide ref="slide" :options="{disableTouch:true}" :showDots="false" :data="notice"
                              :interval="4000"
                              :speed="600" direction='vertical'>
                    <cube-slide-item v-for="(item, index) in notice" :key="index">
                      <p>
                        <i class="title" :class="{noticeTitle:item.id!==undefined}">
                        </i>
                        <label>{{item.userPhone}}</label><em>{{item.id!==undefined?`${item.title}`:`购买
                        [${item.goodsId}]`}}</em>
                        <router-link tag="span" :to="`/page?id=${item.id}`" v-if="item.id!==undefined">[查看详情]
                        </router-link>
                      </p>
                    </cube-slide-item>
                  </cube-slide>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box">
          <div class="float">
            <div class="tool_bar">
              <div class="icon">
                <router-link tag="dl" class="safe" to="/drawCard">
                  <dt></dt>
                  <dd>领取油卡</dd>
                </router-link>
                <router-link tag="dl" class="eq" to="/store">
                  <dt></dt>
                  <dd>惠优商城</dd>
                </router-link>
                <dl class="caifly">
                  <dt></dt>
                  <dd>邀请好友</dd>
                  <a href="/web/mobile/activity/share/share.html"></a>
                </dl>
                <div class="clear"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="product">
          <div class="list">
            <h2>
              <router-link tag="span" class="more" to="/recharge">查看全部<i></i></router-link>
              油卡优惠套餐
            </h2>
            <div class="goods" v-for="data in list1" :key="data.id">
              <router-link tag="dl" :to="`/recharge?id=${data.id}&zk=${data.activityDiscount}&zq=${data.cycle}`">
                <dt>{{data.Int}}<label>{{data.Last}}</label><span>折</span></dt>
                <dd>
                  <p>{{data.goodsName}}</p>
                  <cube-button>立即充值</cube-button>
                </dd>
                <!--<div class="icon" v-if="data.label!=='100'" :class="data.label==='0'?'limit':'hot'"></div>-->
              </router-link>
              <div class="line"></div>
            </div>
            <div class="clear"></div>
          </div>
          <div class="list">
            <h2>
              <router-link tag="span" class="more" to="/recharge2">查看全部<i></i></router-link>
              油卡即时充值
            </h2>
            <div class="goods" v-for="data in list2" :key="data.id">
              <router-link tag="dl" :to="`/recharge2?id=${data.id}`">
                <dt>{{data.val}}<span>元</span></dt>
                <dd>
                  <p class="price">{{data.discountMoney}}元</p>
                  <cube-button class="goods_btn">立即充值</cube-button>
                </dd>
                <!--<div class="icon" v-if="data.label!=='100'" :class="data.label==='0'?'limit':'hot'"></div>-->
              </router-link>
              <div class="line"></div>
            </div>
            <div class="clear"></div>
          </div>
        </div>
      </div>
    </cube-scroll>
    <footer-bar :sy="0"></footer-bar>
  </div>
</template>
<script>
  import {postRequest} from '../axios/'
  import footerBar from './footerBar'
  import Swiper from 'swiper'

  export default {
    name: 'index',
    data () {
      return {
        mySwiper: null,
        list1: [],
        list2: [],
        notice: [],
        banner: [],
        options: {
          swipeBounceTime: 400
        },
        token: localStorage.getItem('hykToken'),
        phone: localStorage.getItem('hykPhone')
      }
    },
    mounted () {
      this.loadPage()
    },
    activated () {
      this.$refs.indexScroll.refresh()
    },
    watch: {
      banner (n) {
        if (n.length <= 1) {
          return false
        }
        this.$nextTick(function () {
          this.initSwiper()
        })
      }
    },
    methods: {
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/index/integration').then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let list = data.packageList
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
          _this.$nextTick(function () {
            _this.banner = data.bannerList
            if (data.hykNotice !== undefined) {
              data.successOrderList.unshift(data.hykNotice)
            }
            _this.list1 = list
            _this.list2 = data.immediateList
            _this.notice = data.successOrderList
          })
        })
      },
      initSwiper () {
        this.mySwiper = new Swiper('.banner_swiper', {
          pagination: '.swiper-pagination',
          loop: false,
          speed: 600,
          autoplay: 5000
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
<style lang="less">
  .index_panel {
    .page {
      padding-bottom: 0 !important;
      .product {
        width: 100%;
        height: auto;
        padding: 0 10px;
        .list:nth-child(2) {
          h2 {
            background: url("../../static/images/product_title01.png") no-repeat;
            background-size: cover;
          }
          .goods dl dt{
            color:#4C92FF;
          }
        }
        .list {
          width: 100%;
          height: auto;
          background: #ffffff;
          border-radius: 5px;
          margin-bottom: 15px;
          .goods {
            width: 50%;
            float: left;
            position:relative;
            padding: 12px 8px;
            .line{
               width:1PX;
               height:60%;
               border-right:solid 1px #eeeeee;
               position:absolute;
               right:0;
               top:20%;
            }
            dl {
              width: 100%;
              height: auto;
              background: #FFFFFF;
              padding: 5px 15px;
              position: relative;
              .icon {
                width: 46px;
                height: 46px;
                position: absolute;
                top: -2px;
                right: -2px;
              }
              /*.icon.hot {*/
              /*background: url("../../static/images/hot_icon.png") no-repeat center center;*/
              /*background-size: contain;*/
              /*}*/
              /*.icon.limit {*/
              /*background: url("../../static/images/limit_icon.png") no-repeat center center;*/
              /*background-size: contain;*/
              /*}*/
              dt {
                font-size: 44px;
                color: #FF4456;
                line-height: 1;
                padding-bottom: 4px;
                label {
                  font-size: 22px;
                }
                span {
                  font-size: 14px;
                  color: #666;
                  padding-left: 4px;
                }
              }
              dd {
                color: #666666;
                font-size: 14px;
                p {
                  line-height: 1;
                  padding: 12px 0;
                  text-align: center;
                }
                p.price {
                  color: #999;
                  text-decoration:line-through;
                  label {
                    color: #333;
                    padding: 0 5px;
                  }
                }
                button.goods_btn{
                   background:#5097FF;
                }
                button {
                  height: 30px;
                  line-height: 30px;
                  font-size: 14px;
                  padding: 0;
                  letter-spacing: 0.05rem;
                }
              }
            }
          }
          h2 {
            width: 100%;
            color: #ffffff;
            height: 48px;
            line-height: 48px;
            background: url("../../static/images/product_title02.png") no-repeat center center;
            background-size: cover;
            padding: 0 15px;
            font-size: 17px;
            text-align: left;
            span {
              float: right;
              font-size: 12px;
              i {
                width: 12px;
                height: 12px;
                display: inline-block;
                background: url("../../static/images/more.png") no-repeat;
                background-size: contain;
                vertical-align: -1px;
                margin-left: 5px;
              }
            }
          }
        }
      }
      .banner {
        width: 100%;
        position: relative;
        height: 200px;
        background: #f5f5f5;
        .box {
          width: 100%;
          height: auto;
          position: absolute;
          bottom: 0;
          z-index: 9999;
          margin-bottom: -35px;
        }
        .swiper-container-horizontal > .swiper-pagination-bullets, .swiper-pagination-custom, .swiper-pagination-fraction {
          bottom: 15px !important;
        }
        .swiper-container {
          width: 100%;
          height: 100%;
          .swiper-slide {
            height: 100%;
            .img {
              height: 100%;
              background-size: cover;
              background-repeat: no-repeat;
              background-position: center center;
            }
            .img[lazy=error], .img[lazy=loading] {
              background-size: 54px 67px !important;
            }
          }
          .swiper-pagination-bullet {
            width: 8px;
            height: 8px;
            background: rgba(255, 255, 255, 0.6);
            margin: 0 4px;
          }
          .swiper-pagination-bullet-active {
            background: #ffffff;
          }
        }
        a {
          background-size: cover;
          background-position: center center;
          background-repeat: no-repeat;
        }
      }
      .activityBar {
        width: 100%;
        height: 100px;
        background: url("../../static/images/activityImg.png") no-repeat center center;
        background-size: contain;
        margin-bottom: 10px;
      }
      .box {
        width: 100%;
        height: auto;
        margin-bottom: 15px;
        padding: 50px 10px 0 10px;
        .float {
          width: 100%;
          height: auto;
          z-index: 99;
          .cube-scroll-wrapper, .cube-scroll-list-wrapper {
            overflow: visible;
          }
          .tool_bar {
            width: 100%;
            height: auto;
            padding: 1px 10px;
            background: #FFFFFF;
            margin-top: -1px;
            border-radius: 3px;
            .icon {
              width: 100%;
              height: auto;
              padding: 4px 0 10px 0;
              dl {
                width: 33.333%;
                height: auto;
                float: left;
                position: relative;
                padding-bottom: 5px;
                dt {
                  width: 65px;
                  height: 65px;
                  margin: 0 auto;
                  border-radius: 50%;
                }
                dd {
                  font-size: 14px;
                  color: #333333;
                  line-height: 1em;
                }
                a {
                  width: 100%;
                  height: 100%;
                  display: block;
                  top: 0;
                  left: 0;
                  position: absolute;
                }
              }
              .safe {
                dt {
                  background: url("../../static/images/safe_icon.png") no-repeat center center;
                  background-size: contain;
                }
              }
              .eq {
                dt {
                  background: url("../../static/images/log_icon.png") no-repeat center center;
                  background-size: contain;
                }
              }
              .caifly {
                dt {
                  background: url("../../static/images/price_icon.png") no-repeat center center;
                  background-size: contain;
                }
              }
            }
            .notice {
              width: 100%;
              height: 44px;
              position: relative;
              padding: 12px 0;
              .cube-slide {
                width: 100%;
                .cube-slide-item {
                  width: 100%;
                }
                p {
                  .title {
                    width: 35px;
                    height: 14px;
                    display: inline-block;
                    border-right: solid 1px #CCCCCC;
                    background: url("../../static/images/noticeTitle.png") no-repeat center center;
                    background-size: contain;
                    vertical-align: -2px;
                    margin-right: 6px;
                  }
                  font-size: 14px;
                  color: #333333;
                  line-height: 20px;
                  label {
                    padding-right: 5px;
                  }
                  em {
                    font-style: normal;
                  }
                  em.noticeEm {
                    width: 54%;
                    display: inline-block;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                  }
                  span {
                    float: right;
                    color: #FF6600;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
</style>
<style lang="css" src="swiper/dist/css/swiper.min.css"></style>
