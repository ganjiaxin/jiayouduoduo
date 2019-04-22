import 'amfe-flexible'
import Navigation from 'vue-navigation'
import App from './App'
import router from './router'
import Vuelidate from 'vuelidate'
import {
  /* eslint-disable no-unused-vars */
  // Style,
  Button,
  Scroll,
  Input,
  Toast,
  Switch,
  Dialog,
  DatePicker,
  ActionSheet,
  Slide,
  Picker
} from 'cube-ui'

Vue.use(VueLazyload, {
  preLoad: 1.3,
  attempt: 1,
  loading: '../mobile/static/images/loadingImg.png', // 线上
  error: '../mobile/static/images/loadingImg.png'
  // loading: '../static/images/loadingImg.png', // 本地
  // error: '../static/images/loadingImg.png'
})
Vue.use(Button)
Vue.use(Scroll)
Vue.use(Input)
Vue.use(ActionSheet)
Vue.use(Toast)
Vue.use(Dialog)
Vue.use(Switch)
Vue.use(DatePicker)
Vue.use(Picker)
Vue.use(Slide)
Vue.use(require('vue-wechat-title'))
Vue.use(Navigation, {router, keyName: 'hyk'})
Vue.use(Vuelidate)
// Vue.config.productionTip = false
// Vue.prototype.HOST = '/hyk'
Vue.prototype.HOST = '/web'
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})
