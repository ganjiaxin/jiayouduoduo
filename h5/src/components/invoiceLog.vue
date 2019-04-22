<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      @pulling-down="onPullingDown"
      @pulling-up="onPullingUp"
      class="page_full bodyBg">
      <div class="invoice_list">
        <div class="invoice_box">
          <h4><span class="wait">待开票</span>2018-11-12</h4>
          <dl>
            <dd>
              <h2><span>￥<label>99</label></span>纸质发票</h2>
              <p>浙江车邦金融信息服务有限公司</p>
            </dd>
            <div class="clear"></div>
          </dl>
        </div>
        <div class="invoice_box">
          <h4><span>已开票</span>2018-11-12</h4>
          <dl>
            <dd>
              <h2><span>￥<label>99</label></span>纸质发票</h2>
              <p>浙江车邦金融信息服务有限公司</p>
            </dd>
            <div class="clear"></div>
          </dl>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  export default {
    name: 'invoice-log',
    data () {
      return {
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
    methods: {
      onPullingDown () {
        // 模拟更新数据
        setTimeout(() => {
          this.$refs.scroll.forceUpdate()
        }, 1000)
      },
      onPullingUp () {
        // 更新数据
        setTimeout(() => {
          this.$refs.scroll.forceUpdate()
        }, 1000)
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
  .invoice_list {
    width: 100%;
    height: auto;
    padding: 15px 15px 0 15px !important;
    color: #333;
    .invoice_box {
      width: 100%;
      height: auto;
      background: #FFFFFF;
      border-radius: 4px;
      box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.05);
      margin-bottom: 12px;
      padding: 10px 15px;
      h4 {
        width: 100%;
        height: auto;
        border-bottom: solid 1px #EBEBEB;
        font-size: 14px;
        color: #999;
        text-align: left;
        padding: 5px 0 10px;
        span {
          float: right;
          color: #333;
          font-weight: bolder;
        }
        span.wait {
          color: #36A94C;
        }
        span.cancel {
          color: #999999;
        }
      }
      dl {
        width: 100%;
        position: relative;
        padding: 5px 0;
        dd {
          width: 100%;
          height: auto;
          h2 {
            font-size: 14px;
            line-height: 30px;
            text-align: left;
            color:#666666;
            padding-bottom:2px;
            span {
              float: right;
              font-size: 10px;
              color:#333;
              label {
                font-size: 20px;
              }
            }
          }
          p {
            font-size: 14px;
            color: #666666;
            letter-spacing: 2px;
          }
        }
      }
    }
  }
</style>
