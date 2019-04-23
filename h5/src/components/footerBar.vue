<template>
  <div class="footer_bar">
    <dl v-for="(item,index) in data" :class="{active:index===sy}">
      <router-link :to="item.url">
      <dt>
        <svg class="icon" aria-hidden="true">
          <use  :xlink:href="index===sy?item.icon:item.activeIcon"></use>
        </svg>
      </dt>
      <dd>{{item.title}}</dd>
      </router-link>
    </dl>
    <div class="clear"></div>
  </div>
</template>
<script>
  export default {
    name: 'footer-bar',
    props: ['sy'],
    data () {
      return {
        data: [
          {
            'title': '首页',
            'icon': '#shouye-pre',
            'activeIcon': '#shouye-nor',
            'url': '/'
          },
          {
            'title': '多多商城',
            'icon': '#shangcheng-pre',
            'activeIcon': '#shangcheng-nor',
            'url': '/store'
          },
          {
            'title': '发现',
            'icon': '#faxian-pre',
            'activeIcon': '#faxian-nor',
            'url': '/find'
          },
          {
            'title': '我的',
            'icon': '#wo-pre',
            'activeIcon': '#wo-nor',
            'url': '/home'
          }
        ]
      }
    }
  }
</script>

