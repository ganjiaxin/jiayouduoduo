<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="items"
      @pulling-down="onPullingDown"
      @pulling-up="onPullingUp"
      class="page_full bodyBg">
      <div class="notice_list">
        <div class="noDataTip notice" v-if="items.length===0 && isLoadDom">
          <dl>
            <dt></dt>
            <dd>暂无公告</dd>
          </dl>
        </div>
        <div class="notice_box" v-for="(data,index) in items" :key="index">
          <router-link :to="`/page?id=${data.id}`">
            <h2><span>{{data.startTimeStrShort}}</span>{{data.title}}</h2>
            <p><span v-html="data.content"></span>......</p>
            <p class="more"><i></i>查看详情</p>
          </router-link>
        </div>
        <!--<div class="notice_box readed">-->
        <!--<h2><span>2018-11-12</span>关于放假通知</h2>-->
        <!--<p>反正就是放假反正就是放假反正就是放假反正就是放假反正就是放假...</p>-->
        <!--<p class="more"><i></i>查看详情</p>-->
        <!--</div>-->
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
        postRequest(this.HOST + '/mine/notice', {
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
            item.content = _this.escape2Html(item.content).replace(/<[^<>]+?>/g, '').substring(0, 60)
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
        let arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"'}
        return str.replace(/&(lt|gt|nbsp|amp|quot);/ig, function (all, t) { return arrEntities[t] })
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
  .notice_list {
    width: 100%;
    height: auto;
    padding: 15px 0 0 0 !important;
    color: #333;
    .notice_box {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      margin-bottom: 12px;
      padding: 0 15px;
      a{
         color:#333;
      }
      h2 {
        font-size: 16px;
        width: 100%;
        padding-top: 10px;
        line-height: 32px;
        text-align: left;
        span {
          float: right;
          font-size: 12px;
          color: #999999;
        }
      }
      p {
        font-size: 13px;
        color: #666666;
        line-height: 1.5em;
        margin-bottom: 10px;
      }
      .more {
        border-top: solid 1px #ebebeb;
        padding: 10px 0;
        margin: 0;
        color: #999999;
        i {
          width: 15px;
          height: 13px;
          background: url("../../static/images/more_icon.png") no-repeat center center;
          background-size: contain;
          display: block;
          float: right;
        }
      }
    }
    .notice_box:last-child {
      margin: 0;
    }
    .notice_box.readed {
      color: #999999;
      p {
        color: #999999;
      }
    }
  }
</style>
