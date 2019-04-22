<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="items"
      @pulling-down="onPullingDown"
      @pulling-up="onPullingUp"
      class="page_full bodyBg">
      <div class="msg_list">
        <div class="noDataTip notice" v-if="items.length===0 && isLoadDom">
          <dl>
            <dt></dt>
            <dd>暂无站内信</dd>
          </dl>
        </div>
        <div class="msg_box" v-for="(data,index) in items" :key="index" :class="{readed:data.messStatus==='1'}"
             @click="handleRead(index,data.id,data.messStatus)">
          <h2><span>{{data.createDateStr}}</span>{{data.title}}</h2>
          <p :class="{all:isOpen===index}">{{data.content}}</p>
          <i :class="isOpen===index?'down':'up'"></i>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'msg-list',
    data () {
      return {
        items: [],
        pageNum: 2,
        isOpen: '',
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
      handleRead (index, id, status) {
        let _this = this
        if (_this.isOpen === index) {
          _this.isOpen = ''
          return false
        }
        _this.isOpen = index
        if (status === '0') {
          postRequest(this.HOST + '/msg/updateOne', {
            token: _this.token,
            id: id
          }, false).then(resp => {
            let data = resp.data
            if (data.code !== '200') {
              _this.showToast(data.msg)
              return false
            }
            _this.items[index].messStatus = '1'
          })
        }
      },
      loadPage (pageNum, type) {
        let _this = this
        postRequest(this.HOST + '/msg/getAll', {
          token: _this.token,
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
    }
  }
</script>
<style lang="less">
  .msg_list {
    width: 100%;
    height: auto;
    padding: 15px 0 0 0 !important;
    color: #333;
    .msg_box {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      position: relative;
      padding: 0 15px 13px 15px;
      i {
        width: 30px;
        height: 30px;
        display: block;
        background: red;
        position: absolute;
        right: 12px;
        bottom: 9px;
      }
      i.up {
        background: url("../../static/images/down_icon.png") no-repeat center center;
        background-size: 16px 16px;
      }
      i.down {
        background: url("../../static/images/up_icon.png") no-repeat center center;
        background-size: 16px 16px;
      }
      h2 {
        font-size: 16px;
        width: 100%;
        border-top: solid 1px #ebebeb;
        padding: 8px 0 3px 0;
        line-height: 33px;
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
        padding: 0 35px 0 0;
        height: 20px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      p.all {
        overflow: visible !important;
        white-space: normal !important;
        height: auto !important;
      }
    }
    .msg_box:first-child {
      h2 {
        border: none;
      }
    }
    .msg_box.readed {
      color: #999999;
      p {
        color: #999999;
      }
    }
  }
</style>
