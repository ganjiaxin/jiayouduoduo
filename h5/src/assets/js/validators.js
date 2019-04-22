import { withParams, regex } from 'vuelidate/lib/validators/common.js'

export var maxMin = function (max, min) {
  return withParams({type: maxMin}, function (value) {
    return !!value && max > value && min < value
  })
}
export var phone = regex('phoneNumber', /^1(3|4|5|6|7|8|9)\d{9}$/)
export var onlyNumber = regex('onlyNumber', /^\d+$/)
export var onlyLetter = regex('onlyLetter', /^[a-zA-Z]+$/)
export var idCard = regex('idCard', /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/)
export var bankNo = regex('bankNo', /^(\d{16}|\d{19})$/)
export var realName = regex('realname', /^[\u4E00-\u9FA5]{2,4}$/)
export var realnameMoney = regex('realnameMoney', /^[1-9]\d*(\.\d+)?$/)
// export var isEmail = regex('isEmail', /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)
export var money = regex('money', /^[0-9]+(.[0-9]{2})?$/)
  // export var telCode = regex('telCode', /^\d{6}$/)
// export var telCode = regex('telCode', /^\d{4}$/)
export var hasBlank = regex('hasBlank', /\s/)
export var integer = regex('integer', /^[1-9]\d*$/)
export var oliCard = regex('oliCard', /^[0-9]\d*$/)
