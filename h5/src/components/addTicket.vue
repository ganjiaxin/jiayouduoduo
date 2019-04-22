<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg">
      <div class="page ticket_page">
        <div class="select_ticket white">
          <p><router-link to="/drawCard">免费领卡</router-link>选择油卡类型</p>
          <ul>
            <li>
              <div class="card" :class="{active:oilType===1}" @click="selectType(1)">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#zhongshiyou"></use>
                </svg>
                <p>中国石油</p>
              </div>
            </li>
            <li>
              <div class="card" :class="{active:oilType===2}" @click="selectType(2)">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#zhongshihua"></use>
                </svg>
                <p>中国石化</p>
              </div>
            </li>
            <div class="clear"></div>
          </ul>
        </div>
        <div class="ticket_info white">
          <div class="item">
            <label>手机号码</label>
            <cube-input type="tel"  :maxlength="11" placeholder="请输入持卡人号码" v-model="phone" @blur="listenBlur" class="inline"></cube-input>
          </div>
          <div class="item">
            <label>加油卡号</label>
            <input type="password" style="position:absolute;top:-50px;opacity:0;filter:alpha(opacity=0)"/>
            <cube-input type="password" :maxlength="cardLength" :eye="eye" @focus="showCard" @blur="listenBlur" placeholder="请输入您的加油卡号" v-model="cardId"
                        class="inline" :class="{eyeNo:isShow}"></cube-input>
          </div>
          <div class="item">
            <label>确认卡号</label>
            <cube-input type="text" :maxlength="cardLength" placeholder="请确认您的加油卡号" @focus="hideCard" @blur="listenBlur" v-model="recardId"
                        class="inline"></cube-input>
          </div>
          <div class="item">
            <label>用户姓名</label>
            <cube-input type="text" placeholder="请输入持卡人姓名" v-model="name" @blur="listenBlur" class="inline"></cube-input>
          </div>
        </div>
        <div class="submit-button">
          <cube-button @click="handleLayer">添加卡片</cube-button>
        </div>
      </div>
    </cube-scroll>
    <div class="layer" v-if="isShow">
      <div class="dialog" :style="dialogCss">
        <div class="close" @click="handleClose">
          <div class="icon"></div>
        </div>
        <div class="title">
          加油卡信息
        </div>
        <div class="content">
          <div class="ticket_ul">
            <ul>
              <li>
                <label>{{name}}</label>持卡人姓名
              </li>
              <li>
                <label>{{oilType===1?'中国石油':'中国石化'}}</label>加油卡类型
              </li>
              <li>
                <label>{{cardId}}</label>加油卡号
              </li>
              <li>
                <label>{{phone}}</label>手机号码
              </li>
            </ul>
            <cube-button @click="handleSubmit" class="dialog_btn">确认添加</cube-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {required} from 'vuelidate/lib/validators'
  import {phone, oliCard} from '../assets/js/validators'
  import {postRequest} from '../axios'
  export default {
    name: 'addTicket',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        },
        oilType: 1,
        token: localStorage.getItem('hykToken'),
        phone: '',
        cardId: '',
        cardLength: 19,
        recardId: '',
        cardNum: '',
        name: '',
        isShow: false,
        eye: {
          open: true,
          reverse: false
        },
        dialogCss: {
          top: '17%',
          bottom: '17%'
        },
        showTip: true
      }
    },
    validations: {
      phone: {required, phone},
      cardNum: {required, oliCard},
      name: {required}
    },
    watch: {
      cardId (value) {
        let _this = this
        if (/\S{5}/.test(value)) {
          _this.cardId = value.replace(/\s/g, '').replace(/(.{4})/g, '$1 ')
        }
        _this.cardNum = value.replace(/\s+/g, '')
        console.log(_this.cardNum)
      },
      recardId (value) {
        let _this = this
        if (/\S{5}/.test(value)) {
          _this.recardId = value.replace(/\s/g, '').replace(/(.{4})/g, '$1 ')
        }
      }
    },
    mounted () {
      this.loadPage()
    },
    methods: {
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
          if (list.length > 0) {
            _this.showTip = false
          }
        })
      },
      listenBlur () {
        setTimeout(function () {
          window.scrollTo(0, 0)
        }, 100)
      },
      showCard () {
        this.eye.open = true
      },
      hideCard () {
        this.eye.open = false
      },
      checkPhone () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!verify.phone.required) {
          _this.showToast('请输入手机号')
          return false
        } else if (!verify.phone.phone) {
          _this.showToast('手机号不正确')
          return false
        }
        return true
      },
      checkCardNum () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!verify.cardNum.required) {
          _this.showToast('请输入加油卡号')
          return false
        } else if (!verify.cardNum.oliCard) {
          _this.showToast('加油卡号不正确')
          return false
        }
        if (_this.oilType === 1) {
          if (_this.cardNum.length < 16) {
            _this.showToast('中石化卡号为16位')
            return false
          }
        } else {
          if (_this.cardNum.length < 19) {
            _this.showToast('中石油卡号为19位')
            return false
          }
        }

        return true
      },
      checkRecardNum () {
        if (this.cardId !== this.recardId) {
          this.showToast('加油卡号不一致')
          return false
        }
        return true
      },
      checkName () {
        let _this = this
        let verify = _this.$v
        verify.$touch()
        if (!verify.name.required) {
          _this.showToast('请输入持卡人姓名')
          return false
        }
        return true
      },
      handleLayer () {
        let _this = this
        if (!_this.checkPhone()) {
          return false
        } else if (!_this.checkCardNum()) {
          return false
        } else if (!_this.checkRecardNum()) {
          return false
        } else if (!_this.checkName()) {
          return false
        }
        this.isShow = true
      },
      handleSubmit () {
        let _this = this
        postRequest(this.HOST + '/mine/addOilCard', {
          oilType: _this.oilType,
          phone: _this.phone,
          cardNum: _this.cardNum,
          name: _this.name,
          token: _this.token
        }, false).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.$router.replace('/home')
        })
      },
      selectType (type) {
        let _this = this
        _this.oilType = type
        _this.cardId = ''
        _this.recardId = ''
        _this.cardNum = ''
        switch (type) {
          case 1:
            _this.cardLength = 19
            break
          case 2:
            _this.cardLength = 23
            break
        }
      },
      handleClose () {
        this.isShow = false
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
  .ticket_ul{
     width:100%;
     height:auto;
     ul{
        li{
           width:100%;
           height:auto;
           font-size:14px;
           color:#999;
           text-align:left;
           line-height:1;
           padding:18px 0;
           border-bottom:solid 1px #ebebeb;
           label{
              float:right;
              color:#333;
              font-size:15px;
           }
        }
        li:last-child{
           border:none;
        }
     }
  }
  .ticket_page {
    color: #333;
    padding:10px 10px 30px 10px !important;
    .cube-input-append {
      display: none;
    }
    .cube-input::after {
      border: none !important;
    }
    .submit-button {
      padding: 50px 30px 0 30px;
      button {
        letter-spacing: 4px;
      }
    }
    .inline {
      width: 100%;
      display: inline-block;
    }
    .eyeNo input {
      letter-spacing: 3px;
    }
    svg {
      width: 100%;
      height: 100%;
    }
    .select_ticket {
      width: 100%;
      padding: 10px 0;
      border-radius:5px;
      font-size: 14px;
      margin-bottom: 15px;
      color: #666;
      p {
        line-height: 1;
        font-size:15px;
        color:#333333;
        padding:5px 15px 10px 15px;
        border-bottom:solid 1px #EEEEEE;
        a{
          float:right;
          color:#FF4456;
          font-size:14px;
        }
      }
      ul {
        width: 100%;
        height: auto;
        padding: 0 7.5px;
        li {
          width: 50%;
          height: auto;
          padding: 0 7.5px;
          float: left;
          .card {
            width: 100%;
            height:auto;
            position: relative;
            overflow: hidden;
            padding-top:15px;
            svg {
              width: 53px;
              height: 51px;
              fill:rgb(238,238,238);
            }
            p {
              font-size: 14px;
              color:#cccccc;
              padding: 5px;
              text-align:center;
              letter-spacing: 1px;
              border:none;
            }
          }
          .card.active {
              svg{
                fill:#FF4456;
              }
              p{
                 color:#FF4456;
              }
          }
        }
      }
    }
    .tip{
      text-align:center;
      color:#FF0000;
      font-size:12px;
    }
    .ticket_info {
      padding: 5px 15px;
      margin-bottom:15px;
      border-radius:5px;
      .item {
        width: 100%;
        height: auto;
        position: relative;
        padding: 6px 0 6px 70px;
        border-bottom: solid 1px #ebebeb;
        label {
          width: 70px;
          height: auto;
          display: inline-block;
          text-align: left;
          font-size: 14px;
          color: #666;
          position: absolute;
          left: 0;
          top: 6px;
          padding: 14px 0;
        }
        input {
          font-size: 15px;
          color: #333;
        }
      }
      .item:last-child {
        border: none;
      }
    }
  }
</style>
