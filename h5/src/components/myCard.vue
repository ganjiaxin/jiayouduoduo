<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      class="page_full bodyBg">
      <div class="card_list">
        <div class="card_box" v-for="(data,index) in items" :key="index">
          <div class="delete" @click="deleteCard(data.id)">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#shanchu"></use>
            </svg>
          </div>
          <router-link :to="`/orderLog?id=${data.id}`">
            <dl>
              <dt>{{data.name}}</dt>
              <dd>{{data.oliCardNo}}</dd>
            </dl>
            <ul>
              <li>{{data.ydzMoney==0?'暂无剩余应加金额':'剩余应加金额：'+data.surplusAdd+'元'}}</li>
              <li>历史加油金额：{{data.money}}元</li>
              <div class="clear"></div>
            </ul>
          </router-link>
          <svg class="icon" aria-hidden="true">
            <use :xlink:href="data.oilType==='1'?'#zhongshiyou':'#zhongshihua'"></use>
          </svg>
        </div>
        <router-link to="/addTicket" tag="div" class="card_box add_card">
          <p><i></i>添加油卡</p>
        </router-link>
        <p class="tip">没有油卡？
          <router-link tag="span" to="/drawCard">免费领卡</router-link>
        </p>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'my-card',
    data () {
      return {
        items: [],
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
      deleteCard (id) {
        this.$createDialog({
          type: 'confirm',
          title: '提示',
          content: '是否移除该油卡？',
          confirmBtn: {
            text: '确定',
            active: true,
            disabled: false,
            href: 'javascript:;'
          },
          cancelBtn: {
            text: '取消',
            active: false,
            disabled: false,
            href: 'javascript:;'
          },
          onConfirm: () => {
            let _this = this
            postRequest(this.HOST + '/mine/delOilCard', {
              id: id,
              token: _this.token
            }, false).then(resp => {
              let data = resp.data
              if (data.code !== '200') {
                _this.showToast(data.msg)
                return false
              }
              _this.showConfirm('油卡已移除')
            })
          }
        }).show()
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
          list.forEach(function (item, i) {
            let oliCard = item.oliCardNo
            item.oliCardNo = oliCard.replace(/(.{4})/g, '$1 ')
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
      },
      showConfirm (tip) {
        let _this = this
        const toast = this.$createToast({
          txt: tip,
          type: 'txt',
          time: 1500
        })
        toast.show()
        setTimeout(() => {
          _this.$router.push('/home')
        }, 1600)
      }
    }
  }
</script>
<style lang="less">
  .card_list {
    width: 100%;
    height: auto;
    padding: 15px 0 0 0 !important;
    color: #333;
    .tip {
      text-align: center;
      font-size: 14px;
      color: #999999;
      padding: 20px 0;
      span {
        color: #FF4456;
        text-decoration: underline;
        font-weight:bold;
      }
    }
    .card_box {
      width: 85%;
      height: 147px;
      border-radius: 4px;
      margin: 0 auto 20px auto;
      background:url("../../static/images/card_bg.png") no-repeat center center;
      background-size:cover;
      position: relative;
      overflow: hidden;
      .icon {
        width: 90px;
        height: 90px;
        position: absolute;
        bottom: -30px;
        right: -10px;
        fill: rgba(255, 255, 255, 0.2)
      }
      .delete {
        width: 40px;
        height: 40px;
        position: absolute;
        right: 3px;
        top: 3px;
        svg {
          width: 20px;
          height: 20px;
          position: absolute;
          top: 50%;
          left: 50%;
          margin: -10px 0 0 -10px;
          fill: rgba(255, 255, 255, 0.6)
        }
      }
      ul {
        width: 100%;
        height: auto;
        background: rgba(0, 0, 0, 0.05);
        position: absolute;
        left: 0;
        bottom: 0;
        z-index: 9;
        li {
          float: left;
          width: 50%;
          font-size: 12px;
          color: #FFFFFF;
          padding: 15px 0;
          border-right: solid 1px rgba(255, 255, 255, 0.1)
        }
      }
      dl {
        width: 100%;
        padding: 24px 20px;
        dt {
          color: #FFFFFF;
          text-align: left;
          font-size: 16px;
          line-height: 1;
          padding-bottom: 20px;
          letter-spacing: 2px;
        }
        dd {
          text-align: left;
          color: #FFFFFF;
          letter-spacing: 1px;
          text-shadow: 1PX 1PX 2PX rgba(0, 0, 0, 0.15);
          font-size: 19px;
        }
      }
    }
    .card_box.add_card {
      p {
        text-align: center;
        display: inline-block;
        color: #FFFFFF;
        font-size: 18px;
        line-height: 135px;
        letter-spacing: 2px;
        i {
          width: 15px;
          height: 15px;
          display: inline-block;
          background: url("../../static/images/plus.png") no-repeat center center;
          background-size: contain;
          margin-right: 5px;
        }
      }
    }
  }

</style>
