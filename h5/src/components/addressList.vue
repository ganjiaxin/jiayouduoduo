<template>
  <div class="content_panel">
    <cube-scroll
      ref="scroll" :options="options" class="page_full bodyBg page_set">
      <div class="page address_page">
        <div class="address_box">
          <div class="list">
            <dl v-for="(data,index) in items" :key="index">
              <dt>
                <h2>{{data.name}} <span>{{data.phone}}</span></h2>
                <p><i v-if="data.isDefault==1">默认</i>{{data.address}}</p>
              </dt>
              <router-link tag="dd" :to="`/editAddress?addressId=${data.id}`" replace>编辑</router-link>
              <div class="clear"></div>
            </dl>
          </div>
        </div>
        <div class="noDataTip" v-if="items.length===0 && isLoadDom">
          <dl class="address">
            <dt></dt>
            <dd>暂无地址</dd>
          </dl>
        </div>
        <div class="submit-button">
          <cube-button @click="handleAdd">添加新地址</cube-button>
        </div>
      </div>
    </cube-scroll>
  </div>
</template>

<script>
  import {postRequest} from '../axios'

  export default {
    name: 'address-list',
    data () {
      return {
        items: [],
        isLoadDom: false,
        token: localStorage.getItem('hykToken'),
        options: {
          swipeBounceTime: 400
        }
      }
    },
    mounted () {
      this.loadPage()
    },
    methods: {
      loadPage () {
        let _this = this
        postRequest(this.HOST + '/mine/seeAddress', {
          token: _this.token
        }).then(resp => {
          let data = resp.data
          if (data.code !== '200') {
            _this.showToast(data.msg)
            return false
          }
          _this.isLoadDom = true
          _this.items = data.list
        })
      },
      handleAdd () {
        this.$router.replace('/addAddress?type=add')
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

<style lang="less" src="../assets/addressSet.less"></style>
