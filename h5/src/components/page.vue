<template>
  <div class="content_panel">
    <cube-scroll
      ref="pageScroll"
      :options="options"
      :data="[content]"
      class="page_full">
      <div class="page detail">
        <div class="page_title">
          <h2>{{title}}</h2>
          <p>{{time}}</p>
        </div>
        <div class="content" v-html="content">
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'page',
    data () {
      return {
        id: this.$route.query.id,
        title: '',
        time: '',
        content: '',
        options: {
          swipeBounceTime: 400
        }
      }
    },
    mounted () {
      this.loadPage()
    },
    updated () {
      let img = document.getElementsByClassName('content')[0].getElementsByTagName('img')
      let count = 0
      let length = img.length
      if (length) {
        let timer = setInterval(() => {
          if (count === length) {
            this.$refs.pageScroll.refresh()
            clearInterval(timer)
          } else if (img[count].complete) {
            count++
          }
        }, 100)
      }
    },
    methods: {
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/mine/getNotice', {
          id: _this.id
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.title = data.notice.title
          _this.time = data.notice.startTimeStr
          _this.content = _this.escape2Html(data.notice.content)
        })
      },
      escape2Html (str) {
        let arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"', 'ldquo': '"'}
        return str.replace(/&(lt|gt|nbsp|amp|quot|ldquo);/ig, function (all, t) {
          return arrEntities[t]
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
  .detail {
    color: #333;
    padding: 20px 15px;
    .page_title {
      width: 100%;
      padding-bottom: 15px;
      h2 {
        font-size: 16px;
        padding-bottom: 10px;
      }
      p {
        font-size: 11px;
        color: #999999;
        text-align: center;
      }
    }
    .content {
      width: 100%;
      padding-bottom: 30px;
      p {
        text-align: left;
        line-height: 1.5em;
        font-size: 14px;
        color: #666666;
        padding: 4px 0;
      }
      img{
         width:100% !important;
         height:auto !important
      }
    }
  }
</style>
