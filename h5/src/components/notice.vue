<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="items"
      @pulling-down="onPullingDown"
      @pulling-up="onPullingUp"
      class="page_full">
      <div class="notice_news">
        <div class="noDataTip notice" v-if="items.length===0 && isLoadDom">
          <dl>
            <dt></dt>
            <dd>暂无资讯</dd>
          </dl>
        </div>
        <div class="notice_box" v-for="(data,index) in items" :key="index">
          <router-link :to="`/detail?id=${data.id}`" tag="dl">
            <dt v-lazy:background-image="`${data.ico}`">
            </dt>
            <dd>
              <h4>{{data.title}}</h4>
              <p>{{data.content}}...</p>
              <p class="time">{{data.createDateStr}}</p>
            </dd>
          </router-link>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'notice-list',
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
              more: '加载更多',
              noMore: '没有更多了'
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
        postRequest(this.HOST + '/find/allNews', {
          currPage: pageNum
        }, _this.isLoading).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          let list = data.list
          _this.isLoadDom = true
          list.forEach(function (item, i) {
            item.content = _this.escape2Html(item.content).replace(/<[^<>]+?>/g, '').substring(0, 46)
          })
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
      escape2Html (str) {
        let arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"', 'ldquo': '"'}
        return str.replace(/&(lt|gt|nbsp|amp|quot|ldquo);/ig, function (all, t) {
          return arrEntities[t]
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
    }
  }
</script>
<style lang="less">
  .notice_news {
    width: 100%;
    height: auto;
    padding: 18px 0 0 0 !important;
    color: #333;
    .notice_box {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      margin-bottom: 12px;
      padding: 0 20px;
      dl {
        width: 100%;
        position: relative;
        height: auto;
        padding-bottom: 10px;
        border-bottom: solid 1px #EEEEEE;
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
  }
</style>
