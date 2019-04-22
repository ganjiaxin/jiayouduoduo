// import axios from 'axios'
import router from '../router'
// import Vue from 'vue'
import {createAPI, Toast} from 'cube-ui'
createAPI(Vue, Toast, ['click'], true)

const loading = Toast.$create({
  txt: '努力加载中...',
  mask: true,
  time: 0
})
axios.interceptors.request.use(config => {
  return config
}, err => {
  Toast.$create({
    txt: '请求超时',
    type: 'warn',
    time: 1500
  }).show()
  return Promise.resolve(err)
})
axios.interceptors.response.use(data => {
  if (data.status === 200) {
    setTimeout(function () {
      loading.hide()
    }, 800)
    if (data.data.code === '300') {
      setTimeout(function () {
        router.replace('/login?phone=' + localStorage.getItem('hykPhone') + '&redirect=')
        localStorage.removeItem('hykToken')
       // localStorage.removeItem('hykPhone')
        localStorage.removeItem('hykUserId')
      }, 1600)
    }
    return data
  } else {
    Toast.$create({
      txt: '请求错误，错误码：' + data.status,
      type: 'warn',
      time: 1500
    }).show()
  }
}, err => {
  Toast.$create({
    txt: '服务器崩溃了~',
    type: 'warn',
    time: 1500
  }).show()
  return Promise.resolve(err)
})

export const postRequest = (url, params, isLoading) => {
  if (isLoading == undefined || isLoading) {
    loading.show()
  }
  return axios({
    method: 'post',
    url: `${url}`,
    data: params,
    transformRequest: [function (data) {
      let ret = ''
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
      }
      return ret
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
export const getRequest = (url) => {
  return axios({
    method: 'get',
    url: `${url}`
  })
}
