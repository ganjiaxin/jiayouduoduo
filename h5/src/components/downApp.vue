<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      class="page_full">
      <div class="tipPage">
          <dl>
             <dt></dt>
             <dd>
               <p>欢迎下载加油多多App</p>
               <p>请使用{{isSafari}}浏览器打开</p>
             </dd>
          </dl>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  export default {
    name: 'down-load',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        }
      }
    },
    props: ['isSafari']
  }
</script>
<style lang="less" scoped>
  .tipPage{
    dl{
      width:100%;
      dt{
        width:100%;
        height:370px;
        background:url("../../static/images/downTip.png") no-repeat center center;
        background-size:contain;
      }
      dd{
        p{
          font-size:15px;
          color:#333333;
          line-height:1.8em;
          text-align:center;
        }
      }
    }
  }
</style>
