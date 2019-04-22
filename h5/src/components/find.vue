<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      class="page_scroll bodyBg">
      <div class="page find_page">
        <div class="oil_price">
          <div class="activity">
            <div class="banner">
              <div class="swiper-container swiper-banner">
                <div class="swiper-wrapper">
                  <div class="swiper-slide" v-for="(data,index) in list" :key="index">
                    <a :href="data.url" :style="{backgroundImage:`url(${data.bannerImg})`}"></a>
                  </div>
                </div>
                <div class="swiper-pagination"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="top_menu">
          <div class="menu">
            <dl>
              <dt>新手福利</dt>
              <dd>专享红包</dd>
            </dl>
            <a href="/web/mobile/activity/novice/index.html"></a>
          </div>
          <router-link tag="div" class="menu" to="/activity">
            <dl>
              <dt>更多活动</dt>
              <dd>精彩享不同</dd>
            </dl>
          </router-link>
          <router-link tag="div" class="menu" to="/drawCard">
            <dl>
              <dt>免费领卡</dt>
              <dd>方便快捷</dd>
            </dl>
          </router-link>
          <router-link tag="div" class="menu" to="/map">
            <dl>
              <dt>附近油站</dt>
              <dd>快速查找</dd>
            </dl>
          </router-link>
          <div class="clear"></div>
        </div>
        <div class="oil_price">
          <router-link :to="`/oliPrice?prov=${oliList.city}`" tag="div" class="tool">
            <dl>
              <dt>今日油价<label>[{{oliList.city}}]</label></dt>
              <dd>
                <cube-slide ref="slide" :options="{disableTouch:true}" :data="oliList" :showDots="false"
                            :interval="4000"
                            :speed="800" direction='vertical'>
                  <cube-slide-item>
                    <label>92#汽油</label><em v-html="oliList.h92"></em>
                  </cube-slide-item>
                  <cube-slide-item>
                    <label>95#汽油</label><em v-html="oliList.h95"></em>
                  </cube-slide-item>
                  <cube-slide-item>
                    <label>98#汽油</label><em v-html="oliList.h98"></em>
                  </cube-slide-item>
                  <cube-slide-item>
                    <label>0#柴油</label><em v-html="oliList.h0"></em>
                  </cube-slide-item>
                </cube-slide>
              </dd>
            </dl>
            <div class="clear"></div>
          </router-link>
        </div>
        <div class="oil_price">
          <div class="notice">
            <h2>
              <router-link to="/notice" tag="span">查看更多<i></i></router-link>
              行业资讯
            </h2>
            <router-link :to="`/detail?id=${notice.id}`" tag="dl">
              <dt v-lazy:background-image="`${notice.ico}`">
              </dt>
              <dd>
                <h4>{{notice.title}}</h4>
                <p>{{notice.content}}...</p>
                <p class="time">{{notice.createDateStr}}</p>
              </dd>
            </router-link>
          </div>
        </div>
        <div class="bottom_menu">
          <div class="menu">
            <router-link to="/noticeList" tag="dl" class="menu">
              <dt></dt>
              <dd>官方公告</dd>
            </router-link>
            <dl>
              <dt></dt>
              <dd>帮助中心</dd>
              <a href="/web/mobile/help/helpCenter_h5.html"></a>
            </dl>
            <dl>
              <dt></dt>
              <dd>联系我们</dd>
              <a href="/web/mobile/service/index.html"></a>
            </dl>
            <div class="clear"></div>
          </div>
        </div>
      </div>
    </cube-scroll>
    <footer-bar :sy="2"></footer-bar>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  import footerBar from './footerBar'
  import Swiper from 'swiper'
  import AMap from 'AMap'

  export default {
    name: 'find',
    data () {
      return {
        swiper: null,
        token: localStorage.getItem('hykToken'),
        isLoadDom: false,
        options: {
          swipeBounceTime: 400
        },
        list: [],
        notice: {},
        oliList: {},
        toast: ''
      }
    },
    mounted () {
      this.loadNotice()
      this.loadBanner()
    },
    watch: {
      list (n) {
        if (n.length <= 1) {
          return false
        }
        this.$nextTick(function () {
          this.initSwiper()
        })
      }
    },
    methods: {
      loadProv () {
        let _this = this
        AMap.plugin('AMap.Geolocation', function () {
          let geolocation = new AMap.Geolocation({
            enableHighAccuracy: true // 是否使用高精度定位，默认:true
            // timeout: 5000          // 超过10秒后停止定位，默认：5s
          })
          geolocation.getCurrentPosition(function (status, result) {
            if (status === 'complete') {
              _this.toast.hide()
              let prov = result.addressComponent.province
              if (prov.indexOf('内蒙古') >= 0 || prov.indexOf('黑龙江') >= 0) {
                prov = prov.substring(0, 3)
              } else {
                prov = prov.substring(0, 2)
              }
              _this.loadOli(prov)
            } else {
              _this.showToast('获取当前位置失败~' + status)
              _this.loadOli('浙江')
            }
          })
        })
      },
      loadOli (province) {
        let _this = this
        postRequest(_this.HOST + '/find/oilPrice',
          {
            prov: province
          }, false
        ).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          data.hykOilPrice.h0 = `<i>${data.hykOilPrice.h0.split('.')[0]}</i>.${data.hykOilPrice.h0.split('.')[1]}/L`
          data.hykOilPrice.h92 = `<i>${data.hykOilPrice.h92.split('.')[0]}</i>.${data.hykOilPrice.h92.split('.')[1]}/L`
          data.hykOilPrice.h95 = `<i>${data.hykOilPrice.h95.split('.')[0]}</i>.${data.hykOilPrice.h95.split('.')[1]}/L`
          data.hykOilPrice.h98 = `<i>${data.hykOilPrice.h98.split('.')[0]}</i>.${data.hykOilPrice.h98.split('.')[1]}/L`
          _this.oliList = data.hykOilPrice
        })
      },
      loadNotice () {
        let _this = this
        postRequest(this.HOST + '/find/wondefulImg', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.list = data.list
        })
      },
      loadBanner () {
        let _this = this
        postRequest(this.HOST + '/find/allNews', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          data.list[0].content = _this.escape2Html(data.list[0].content).replace(/<[^<>]+?>/g, '').substring(0, 38)
          _this.notice = data.list[0]
          _this.showTip('正在获取当前位置...')
          _this.loadProv()
        })
      },
      escape2Html (str) {
        let arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"', 'ldquo': '"'}
        return str.replace(/&(lt|gt|nbsp|amp|quot|ldquo);/ig, function (all, t) {
          return arrEntities[t]
        })
      },
      initSwiper () {
        this.swiper = new Swiper('.swiper-banner', {
          pagination: '.swiper-pagination',
          speed: 500,
          autoplay: 5000,
          loop: true
        })
      },
      showTip (tip) {
        let _this = this
        _this.toast = this.$createToast({
          txt: tip,
          type: 'txt',
          time: 0
        })
        _this.toast.show()
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
  .find_page {
    padding: 10px;
    .oil_price {
      width: 100%;
      height: auto;
      padding: 0 5px;
      margin-bottom: 15px;
      .activity {
        width: 100%;
        height: auto;
        background: #ffffff;
        border-radius: 4px;
        overflow: hidden;
        h2 {
          text-align: left;
          font-size: 18px;
          margin-bottom: 8px;
          line-height: 1.5em;
          padding: 0 10px;
          span {
            float: right;
            color: #999999;
            font-size: 14px;
            i {
              display: inline-block;
              width: 6px;
              height: 12px;
              background: url("../../static/images/more_icon.png") no-repeat center center;
              background-size: cover;
              margin-left: 5px;
              vertical-align: -2px;
            }
          }
        }
        .banner {
          width: 100%;
          height: 120px;
          .swiper-container {
            width: 100%;
            height: 120px;
            .swiper-pagination-bullet-active {
              background: #ffffff;
            }
            .swiper-slide {
              width: 250px;
              height: 120px;
              margin: 0 auto;
              overflow: hidden;
              background: #f5f5f5 url("../../static/images/loadingImg.png") no-repeat center center;
              background-size: 34px 44px !important;
              box-shadow: 0 2px 8px #eeeeee;
              a {
                width: 100%;
                height: 100%;
                display: block;
                background-repeat: no-repeat;
                background-size: cover;
                background-position: center center;
              }
            }
          }
        }
      }
      .notice {
        width: 100%;
        height: auto;
        background: #ffffff;
        border-radius: 4px;
        padding: 12px 10px;
        h2 {
          text-align: left;
          font-size: 18px;
          margin-bottom: 8px;
          line-height: 1.5em;
          span {
            float: right;
            color: #999999;
            font-size: 14px;
            i {
              display: inline-block;
              width: 6px;
              height: 12px;
              background: url("../../static/images/more_icon.png") no-repeat center center;
              background-size: cover;
              margin-left: 5px;
              vertical-align: -2px;
            }
          }
        }
        dl {
          width: 100%;
          position: relative;
          height: auto;
          dt {
            width: 80px;
            height: 80px;
            position: absolute;
            background-position: center center;
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #f5f5f5;
          }
          dt[lazy=error], dt[lazy=loading] {
            background-size: 30px 36px !important;
          }
          dd {
            width: 100%;
            height: auto;
            text-align: left;
            padding-left: 90px;
            h4 {
              font-size: 14px;
              line-height: 1.5em;
              padding-bottom: 3px;
              width: 100%;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
            p {
              font-size: 12px;
              color: #666666;
              line-height: 1.5em;
              overflow: hidden;
              text-overflow: ellipsis;
              display: -webkit-box;
              -webkit-box-orient: vertical;
              -webkit-line-clamp: 2;
            }
            p.time {
              padding-top: 4px;
              color: #999999;
            }
          }
        }
      }
      .tool {
        width: 100%;
        height: auto;
        background: #ffffff;
        border-radius: 4px;
        padding: 0 10px;
        dl {
          dt {
            font-size: 18px;
            width: 50%;
            height: auto;
            line-height: 65px;
            text-align: left;
            float: left;
            label {
              color: #999999;
              font-size: 14px;
              padding-left: 8px;
            }
          }
          dd {
            width: 50%;
            height: 60px;
            line-height: 60px;
            text-align: right;
            overflow: hidden;
            float: left;
            label {
              color: #666666;
              font-size: 14px;
              border-right: solid 1px #E0E0E0;
              padding-right: 10px;
            }
            em {
              padding-left: 10px;
              color: #FF4456;
              font-style: normal;
              font-size: 13px;
              i {
                font-style: normal;
                font-size: 34px;
                vertical-align: -2px;
              }
            }
          }
        }
      }
    }
    .top_menu {
      width: 100%;
      height: auto;
      margin-bottom: 5px;
      .menu {
        width: 25%;
        height: auto;
        float: left;
        margin-bottom: 10px;
        position: relative;
        padding: 0 5px;
        a {
          width: 100%;
          height: 100%;
          position: absolute;
          top: 0;
          left: 0;
          display: block;
        }
        dl {
          width: 100%;
          height: auto;
          padding: 10px 5px;
          border-radius: 4px;
          background: #ffffff url("../../static/images/find_icon01.png") no-repeat 95% bottom;
          background-size: 18px 17px;
          position: relative;
          dt {
            font-size: 16px;
            color: #333333;
            text-align: left;
            line-height: 1em;
          }
          dd {
            font-size: 12px;
            color: #999999;
            text-align: left;
            line-height: 2em;
          }
        }
      }
      .menu:nth-child(2) {
        dl {
          background: #ffffff url("../../static/images/find_icon02.png") no-repeat 95% bottom;
          background-size: 18px 17px;
        }
      }
      .menu:nth-child(3) {
        dl {
          background: #ffffff url("../../static/images/find_icon03.png") no-repeat 95% bottom;
          background-size: 18px 17px;
        }
      }
      .menu:nth-child(4) {
        dl {
          background: #ffffff url("../../static/images/find_icon04.png") no-repeat 95% bottom;
          background-size: 18px 17px;
        }
      }
    }
    .bottom_menu {
      width:100%;
      height:auto;
      padding:0 5px;
      .menu {
        width: 100%;
        height: auto;
        margin-bottom: 10px;
        background: #ffffff;
        border-radius:5px;
        dl {
          width: 33.3333%;
          height: auto;
          float:left;
          padding:10px 0 5px 0;
          position: relative;
          background-size: cover;
          a {
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            display: block;
          }
          dt {
            width: 44px;
            height: 44px;
            display: inline-block;
            margin:0 auto;
          }
          dd {
            font-size: 14px;
            text-align:center;
            line-height:1;
             padding-top:4px;
            color:#666666;
          }
        }
      }
      dl:nth-child(1){
         dt{
            background:url("../../static/images/find_icon_05.png") no-repeat center center;
            background-size:contain;
         }
      }
      dl:nth-child(2){
        dt{
          background:url("../../static/images/find_icon_06.png") no-repeat center center;
          background-size:contain;
        }
      }
      dl:nth-child(3){
        dt{
          background:url("../../static/images/find_icon_07.png") no-repeat center center;
          background-size:contain;
        }
      }
    }
  }
</style>
<style lang="css" src="swiper/dist/css/swiper.min.css" scoped></style>
