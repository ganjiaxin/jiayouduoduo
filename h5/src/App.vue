<template>
  <div id="app" v-cloak>
    <transition :name="transitionName">
      <navigation>
        <router-view v-wechat-title="$route.meta.title"/>
      </navigation>
    </transition>
  </div>
</template>

<script>
  import './assets/app.less'
  import './assets/js/iconfont'
  export default {
    name: 'app',
    data () {
      return {
        transitionName: 'fade',
        sy: 0
      }
    },
    // mounted () {
    //   this.randomWord()
    // },
    // methods: {
    //   randomWord (range) {
    //     let str = ''
    //     let arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
    //     for (let i = 0; i < range; i++) {
    //       let pos = Math.round(Math.random() * (arr.length - 1))
    //       str += arr[pos]
    //     }
    //     return str
    //   }
    // },
    created () {
      let _this = this
      this.$navigation.on('forward', (to, from) => {
        _this.transitionName = 'fade'
      })
      this.$navigation.on('back', (to, from) => {
        _this.transitionName = 'slide-right'
        // switch (to.route.path) {
        //   case '/':
        //     _this.$router.push('/?hyk=' + _this.randomWord(8))
        //     break
        //   default:
        //     _this.transitionName = 'slide-right'
        // }
      })
      this.$navigation.on('replace', (to, from) => {
        _this.transitionName = 'slide-left'
      })
    }
  }
</script>

