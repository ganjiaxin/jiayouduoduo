<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll"
      :options="options"
      :data="addressList"
      class="page_full bodyBg page_set" style="z-index:999;">
      <div class="page address_page">
        <div class="address_box">
          <div class="list">
            <dl v-for="(data,index) in addressList" :key="index" :class="{active:data.id===addressId}"  @click="sendParent(data)">
              <div class="checkbox" v-if="data.id===addressId">
                <div>
                  <svg class="icon" aria-hidden="true">
                    <use xlink:href="#duigou"></use>
                  </svg>
                </div>
              </div>
              <dt>
                <h2>{{data.name}} <span>{{data.phone}}</span></h2>
                <p><i v-if="data.isDefault==1">默认</i>{{data.address}}</p>
              </dt>
              <div class="clear"></div>
            </dl>
          </div>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  export default {
    name: 'select-address',
    data () {
      return {
        options: {
          swipeBounceTime: 400
        }
      }
    },
    props: ['addressList', 'addressId'],
    methods: {
      sendParent (data) {
        this.$emit('listenChildEvent', data)
      }
    }
  }
</script>
<style lang="less" scoped>
  .bodyBg{
    animation: fadeInUpBig 0.8s;
    -webkit-animation: fadeInUpBig 0.8s;
  }
  @-webkit-keyframes fadeInUpBig {
    0% {
      opacity: 0;
      -webkit-transform: translate3d(0, 2000px, 0);
      transform: translate3d(0, 2000px, 0);
    }

    100% {
      opacity: 1;
      -webkit-transform: none;
      transform: none;
    }
  }

  @keyframes fadeInUpBig {
    0% {
      opacity: 0;
      -webkit-transform: translate3d(0, 2000px, 0);
      transform: translate3d(0, 2000px, 0);
    }

    100% {
      opacity: 1;
      -webkit-transform: none;
      transform: none;
    }
  }

  .fadeInUpBig {
    -webkit-animation-name: fadeInUpBig;
    animation-name: fadeInUpBig;
  }
</style>
<style lang="less" src="../assets/addressSet.less"></style>
