<template>
  <div class="content_panel reg_panel">
    <cube-scroll
      ref="scroll"
      @pulling-down="onPullingDown"
      @pulling-up="onPullingUp"
      :options="options"
      :data="items"
      class="page_scroll">
      <div class="page find_page">
        <div class="find_box" v-for="(data,index) in items" v-lazy:background-image="`${data.wondefulImg}`"  :key="index" :class="{over_time:data.overSign!=='0'}">
          <a :href="data.url" v-if="data.overSign==='0'"></a>
          <div class="flag" v-if="data.overSign!=='0'">已结束</div>
        </div>
      </div>
    </cube-scroll>
    <footer-bar :sy="2"></footer-bar>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  import footerBar from './footerBar'

  export default {
    name: 'home',
    data () {
      return {
        items: [],
        pageNum: 2,
        isLoadDom: false,
        isLoading: true,
        token: localStorage.getItem('hykToken'),
        options: {
          pullDownRefresh: {
            threshold: 60,
            txt: '刷新成功'
          },
          pullUpLoad: {
            threshold: 60,
            txt: {
              more: '加载更能',
              noMore: '— 更多内容，敬请期待 —'
            }
          }
        }
      }
    },
    mounted () {
      this.loadPage(1, 1)
    },
    methods: {
      loadPage (pageNum, type) {
        let _this = this
        postRequest(this.HOST + '/find/wondefulImgAll', {
          currPage: pageNum
        }, _this.isLoading).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let list = data.list
          _this.isLoadDom = true
          if (type === 1) {
            _this.items = list
            _this.pageNum = 2
          } else {
            if (list.length > 0) {
              _this.items = _this.items.concat(list)
              _this.pageNum = _this.pageNum + 1
            }
          }
          setTimeout(function () {
            _this.$refs.scroll.forceUpdate()
          }, 300)
        })
      },
      onPullingDown () {
        this.isLoading = false
        this.loadPage(1, 1)
      },
      onPullingUp () {
        let _this = this
        _this.isLoading = false
        setTimeout(function () {
          _this.loadPage(_this.pageNum, 2)
        }, 800)
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
    width: 100%;
    height: auto;
    padding: 15px 15px 0 15px !important;
    color: #333;
    .find_box {
      width: 100%;
      height: 150px;
      border-radius: 3px;
      overflow: hidden;
      background-size: cover;
      background-repeat: no-repeat;
      position:relative;
      background-position: center center;
      /*box-shadow: 1px 1px 5px rgba(0,0,0,0.2);*/
      margin-bottom: 14px;
      a{
         width:100%;
         height:100%;
        display:block;
        background:rgba(0,0,0,0.03)
      }
      .flag{
         width:68px;
         height:28px;
         line-height:28px;
         background:#aaa;
         color:#FFFFFF;
         text-align:right;
         position:absolute;
         border-radius:14px 0 0 14px;
         font-size:15px;
         letter-spacing: 1px;
         padding-right:5px;
         top:15px;
         right:0;
      }
    }
    .find_box[lazy=loading],.find_box[lazy=error]{
      background-size:54px 67px !important;
      background-color:#f5f5f5;
    }
    .find_box[lazy=loading] a,.find_box[lazy=error] a{
        background:none !important;
    }
    .find_box.over_time{
       a{
          background:rgba(0,0,0,0.5)
       }
    }
  }
</style>
