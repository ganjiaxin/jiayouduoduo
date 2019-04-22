<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="items"
      class="page_scroll bodyBg">
      <div class="page">
        <div class="noDataTip store" v-if="items.length===0 && isLoadDom">
        </div>
        <div class="storeBanner">
          <div class="swiper-container view_swiper">
            <div class="swiper-wrapper">
              <div class="swiper-slide" v-for="(data,index) in list" :key="index">
                <a :href="data.url">
                  <div class="img bgimg_cover" v-lazy:background-image="`${data.goodsImg}`"></div>
                </a>
              </div>
            </div>
            <!--<div class="swiper-pagination"></div>-->
          </div>
        </div>
        <div class="store_list">
          <div class="list_box">
            <div v-for="(data,index) in items" :key="index" class="mb">
              <h2><i></i>{{data.name}}</h2>
              <div class="store" v-for="(item,index) in data.goodsList" :key="index">
                <router-link :to="`/storeBuy?id=${item.id}`" tag="dl">
                  <dt v-lazy:background-image="`${item.viewpic}`"></dt>
                  <dd>
                    <p><i class="tag" v-if="item.ishot==='1'"></i>{{item.goodsName}}</p>
                    <p class="price" v-if="item.prices">￥<label>{{item.prices.toFixed(2)}}</label></p>
                    <p class="price" v-else><label style="font-size:0.4rem;">限免</label></p>
                  </dd>
                </router-link>
              </div>
              <div class="clear"></div>
            </div>
          </div>
        </div>
      </div>
    </cube-scroll>
    <footer-bar :sy="1"></footer-bar>
  </div>
</template>

<script>
  import footerBar from './footerBar'
  import {postRequest} from '../axios'
  import Swiper from 'swiper'

  export default {
    name: 'stroe',
    data () {
      return {
        mySwiper: null,
        items: [],
        isLoadDom: false,
        options: {
          swipeBounceTime: 400
        },
        token: localStorage.getItem('hykToken'),
        list: []
      }
    },
    mounted () {
      this.loadList()
      this.loadView()
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
      initSwiper () {
        this.mySwiper = new Swiper('.view_swiper', {
          // pagination: '.swiper-pagination',
          loop: false,
          speed: 600,
          autoplay: 5000
        })
      },
      loadView () {
        let _this = this
        postRequest(this.HOST + '/shop/banner', {
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
      loadList () {
        let _this = this
        postRequest(this.HOST + '/shop/allMallGoods', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let list = data.list
          _this.isLoadDom = true
          list.forEach(function (item, i) {
            item.goodsList.forEach(function (data, i) {
              let name = data.goodsName
              if (name.length > 14) {
                data.goodsName = name.substring(0, 14) + '...'
              }
            })
          })
          _this.items = list
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
  .page {
    padding-bottom:0 !important;
    .storeBanner {
      width: 100%;
      height: 100px;
      margin-bottom: 0.3rem;
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
    .store_list {
      width: 100%;
      height: auto;
      padding: 0 0.3rem;
      .list_box{
         width:100%;
         height:auto;
         .mb{
           border-radius:6px;
           background:#ffffff;
            margin-bottom:15px;
         }
      }
      h2 {
        font-size: 18px;
        color: #333333;
        padding: 15px 0.2rem 4px 0.2rem;
        margin-bottom: 18px;
        font-weight: bold;
        clear: both;
        background: url("../../static/images/store_title_bg.png") no-repeat center bottom;
        background-size: 85px 9px;
      }
      .store {
        width: 50%;
        height: auto;
        float: left;
        padding: 0 0.3rem;
        margin-bottom: 14px;
        dl {
          width: 100%;
          height: auto;
          border-radius: 0.1rem;
          position: relative;
          overflow: hidden;
          dt {
            width: 100%;
            height: 165px;
            background-size: contain;
            background-color: #ffffff;
            background-position: center center;
            background-repeat: no-repeat;
          }
          dt[lazy=error], dt[lazy=loading] {
            background-color: #E6E6E6;
            background-size: 43px 52px !important;
          }
          dd {
            padding: 8px 5px 0 5px;
            width: 100%;
            height: auto;
            p {
              width: 100%;
              font-size: 12px;
              color: #333333;
              min-height: 28px;
              line-height: 1.4em;
              .tag {
                width: 22px;
                height: 12px;
                display:inline-block;
                vertical-align: -1px;
                margin-right:5px;
                background: url("../../static/images/hotIcon.png") no-repeat center center;
                background-size: contain;
              }
            }
            p.price {
              font-size: 14px;
              height: auto;
              color: #FF4456;
              text-align: left;
              label {
                font-size: 16px;
              }
            }
          }
        }
      }
    }
  }
</style>
<style lang="css" src="swiper/dist/css/swiper.min.css"></style>

