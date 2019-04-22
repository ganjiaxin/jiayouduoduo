<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg page_set">
      <div class="page msg_page">
        <div class="tool_list">
          <ul>
            <router-link to="/noticeList" tag="li">
              <i class="more"></i>
              <dl>
                <dt class="notice"></dt>
                <dd>
                  <h2>官方公告</h2>
                  <p>{{title}}</p>
                </dd>
              </dl>
            </router-link>
            <router-link to="/msgList" tag="li">
              <i class="more"></i>
              <span v-if="count>0">{{count}}</span>
              <dl>
                <dt></dt>
                <dd>
                  <h2>站内信</h2>
                  <p>{{title2}}</p>
                </dd>
              </dl>
            </router-link>
          </ul>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'
  export default {
    name: 'msg-center',
    data () {
      return {
        title: '',
        count: '',
        title2: '',
        options: {
          swipeBounceTime: 400
        },
        token: localStorage.getItem('hykToken')
      }
    },
    mounted () {
      this.loadPage()
    },
    methods: {
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/msg/newTitle', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.title2 = data.title
          _this.count = data.count
          _this.title = data.hykNotice.title
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
  .page_set {
    padding-top: 15px;
    .msg_page {
      color: #333;
      .tool_list {
        width: 100%;
        height: auto;
        background: #FFFFFF;
        padding: 0 15px;
        ul {
          width: 100%;
          margin-bottom: 30px;
          li {
            line-height: 1;
            padding: 20px 1px;
            text-align: left;
            position: relative;
            border-bottom: solid 1px #ebebeb;
            dl {
              dt {
                width: 36px;
                height: 36px;
                background: url("../../static/images/msgs_icon.png") no-repeat;
                background-size: contain;
                float: left;
                margin-right: 12px;
              }
              dt.notice {
                background: url("../../static/images/notice_icon.png") no-repeat;
                background-size: contain;
              }
              dd {
                height: 36px;
                line-height: 1;
                h2 {
                  font-size: 16px;
                  padding-bottom: 8px;
                }
                p {
                  font-size: 13px;
                  color: #999999;
                }
              }
            }
            span {
              font-size: 16px;
              float: right;
              color: #FF4456;
              display: block;
              width: auto;
              height: 36px;
              line-height: 39px;
              padding-right: 22px;
            }
          }
          li:last-child {
            border: none;
          }
          .more {
            width: 15px;
            height: 13px;
            background: url("../../static/images/more_icon.png") no-repeat center center;
            background-size: contain;
            display: block;
            position: absolute;
            top: 50%;
            margin-top: -6px;
            right: 0;
          }
        }
      }
    }
  }
</style>
