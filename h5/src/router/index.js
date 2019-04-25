
const router = new VueRouter({
  routes: [
    {
      path: '/',
      name: 'index',
      component: resolve => (require(['@/components/index'], resolve)),
      meta: {title: '加油多多'}
    },
    {
      path: '/userJoin',
      name: 'userJoin',
      component: resolve => (require(['@/components/userJoin'], resolve)),
      meta: {title: '登录/注册'}
    },
    {
      path: '/store',
      name: 'store',
      component: resolve => (require(['@/components/store'], resolve)),
      meta: {title: '多多商城'}
    },
    {
      path: '/storeBuy',
      name: 'storeBuy',
      component: resolve => (require(['@/components/storeBuy'], resolve)),
      meta: {
        title: '商品详情',
        requireAuth: true
      }
    },
    {
      path: '/storeOrder',
      name: 'storeOrder',
      component: resolve => (require(['@/components/storeOrder'], resolve)),
      meta: {
        title: '填写订单',
        requireAuth: true
      }
    },
    {
      path: '/updatePass',
      name: 'updatePass',
      component: resolve => (require(['@/components/updatePass'], resolve)),
      meta: {title: '忘记密码'}
    },
    {
      path: '/login',
      name: 'login',
      component: resolve => (require(['@/components/login'], resolve)),
      meta: {title: '登录'}
    },
    {
      path: '/code',
      name: 'code',
      component: resolve => (require(['@/components/code'], resolve)),
      meta: {title: '短信验证码'}
    },
    {
      path: '/setPass',
      name: 'setPass',
      component: resolve => (require(['@/components/setPass'], resolve)),
      meta: {title: '设置密码'}
    },
    {
      path: '/home',
      name: 'home',
      component: resolve => (require(['@/components/home'], resolve)),
      meta: {
        title: '个人中心'
        // requireAuth: true
      }
    },
    {
      path: '/addTicket',
      name: 'addTicket',
      component: resolve => (require(['@/components/addTicket'], resolve)),
      meta: {title: '添加油卡'}
    },
    {
      path: '/recharge',
      name: 'recharge',
      component: resolve => (require(['@/components/recharge'], resolve)),
      meta: {
        title: '套餐充值',
        requireAuth: true
      }
    },
    {
      path: '/recharge2',
      name: 'recharge2',
      component: resolve => (require(['@/components/recharge2'], resolve)),
      meta: {
        title: '即时充值',
        requireAuth: true
      }
    },
    {
      path: '/coupon',
      name: 'coupon',
      component: resolve => (require(['@/components/coupon'], resolve)),
      meta: {title: '我的券包'}
    },
    {
      path: '/useCoupon',
      name: 'useCoupon',
      component: resolve => (require(['@/components/useCoupon'], resolve)),
      meta: {title: '使用红包'}
    },
    {
      path: '/drawCard',
      name: 'drawCard',
      component: resolve => (require(['@/components/drawCard'], resolve)),
      meta: {title: '油卡领取'}
    },
    {
      path: '/pay',
      name: 'pay',
      component: resolve => (require(['@/components/pay'], resolve)),
      meta: {
        title: '收银台',
        requireAuth: true
      }
    },
    {
      path: '/payGoods',
      name: 'payGoods',
      component: resolve => (require(['@/components/payGoods'], resolve)),
      meta: {
        title: '收银台',
        requireAuth: true
      }
    },
    {
      path: '/payCallback',
      name: 'payCallback',
      component: resolve => (require(['@/components/payCallback'], resolve)),
      meta: {title: '收银台'}
    },
    {
      path: '/userSet',
      name: 'userSet',
      component: resolve => (require(['@/components/userSet'], resolve)),
      meta: {
        title: '个人信息',
        requireAuth: true
      }
    },
    {
      path: '/addressList',
      name: 'addressList',
      component: resolve => (require(['@/components/addressList'], resolve)),
      meta: {title: '地址管理'}
    },
    {
      path: '/addAddress',
      name: 'addAddress',
      component: resolve => (require(['@/components/addAddress'], resolve)),
      meta: {title: '添加新地址'}
    },
    {
      path: '/editAddress',
      name: 'editAddress',
      component: resolve => (require(['@/components/editAddress'], resolve)),
      meta: {title: '编辑地址'}
    },
    {
      path: '/order',
      name: 'order',
      component: resolve => (require(['@/components/order'], resolve)),
      meta: {title: '加油订单'}
    },
    {
      path: '/orderLog',
      name: 'orderLog',
      component: resolve => (require(['@/components/orderLog'], resolve)),
      meta: {title: '订单记录'}
    },
    {
      path: '/orderGoods',
      name: 'orderGoods',
      component: resolve => (require(['@/components/orderGoods'], resolve)),
      meta: {title: '惠优订单'}
    },
    {
      path: '/orderDetail',
      name: 'orderDetail',
      component: resolve => (require(['@/components/orderDetail'], resolve)),
      meta: {title: '订单详情'}
    },
    {
      path: '/orderShop',
      name: 'orderShop',
      component: resolve => (require(['@/components/orderShop'], resolve)),
      meta: {title: '订单详情'}
    },
    {
      path: '/myCard',
      name: 'myCard',
      component: resolve => (require(['@/components/myCard'], resolve)),
      meta: {title: '油卡管理'}
    },
    // {
    //   path: '/invoiceLog',
    //   name: 'invoiceLog',
    //   component: resolve => (require(['@/components/userJoin'], resolve)),
    //   meta: {title: '发票记录'}
    // },
    {
      path: '/msgCenter',
      name: 'msgCenter',
      component: resolve => (require(['@/components/msgCenter'], resolve)),
      meta: {title: '消息中心'}
    },
    {
      path: '/noticeList',
      name: 'noticeList',
      component: resolve => (require(['@/components/noticeList'], resolve)),
      meta: {title: '官方公告'}
    },
    {
      path: '/notice',
      name: 'notice',
      component: resolve => (require(['@/components/notice'], resolve)),
      meta: {title: '行业资讯'}
    },
    {
      path: '/msgList',
      name: 'msgList',
      component: resolve => (require(['@/components/msgList'], resolve)),
      meta: {title: '站内信'}
    },
    {
      path: '/balance',
      name: 'balance',
      component: resolve => (require(['@/components/balance'], resolve)),
      meta: {title: '我的余额'}
    },
    {
      path: '/exchange',
      name: 'exchange',
      component: resolve => (require(['@/components/exchange'], resolve)),
      meta: {title: '余额充值'}
    },
    {
      path: '/page',
      name: 'page',
      component: resolve => (require(['@/components/page'], resolve)),
      meta: {title: '公告详情'}
    },
    {
      path: '/detail',
      name: 'detail',
      component: resolve => (require(['@/components/detail'], resolve)),
      meta: {title: '行业资讯'}
    },
    {
      path: '/oliPrice',
      name: 'oliPrice',
      component: resolve => (require(['@/components/oliPrice'], resolve)),
      meta: {title: '今日油价'}
    },
    {
      path: '/find',
      name: 'find',
      component: resolve => (require(['@/components/find'], resolve)),
      meta: {title: '发现'}
    },
    {
      path: '/activity',
      name: 'activity',
      component: resolve => (require(['@/components/activity'], resolve)),
      meta: {title: '活动中心'}
    },
    {
      path: '/privacyPolicy',
      name: 'privacyPolicy',
      component: resolve => (require(['@/components/privacyPolicy'], resolve)),
      meta: {title: '隐私政策'}
    },
    {
      path: '/map',
      name: 'map',
      component: resolve => (require(['@/components/map'], resolve)),
      meta: {title: '附近油站'}
    },
    {
      path: '/attentionProtocol',
      name: 'attentionProtocol',
      component: resolve => (require(['@/components/attentionProtocol'], resolve)),
      meta: {title: '优惠卡用户使用协议'}
    },
    {
      path: '*',
      component: resolve => (require(['@/components/index'], resolve))
    }
  ]
})
router.beforeEach((to, from, next) => {
  if (to.matched.some(res => res.meta.requireAuth)) {
    let token = localStorage.getItem('hykToken')
    if (token === null || token === '') {
      next({
        path: '/userJoin',
        query: {redirect: to.fullPath}
      })
    } else {
      next()
    }
  } else {
    next()
  }
})
export default router
