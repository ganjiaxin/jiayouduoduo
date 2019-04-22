<template>
  <div class="content_panel reg_panel">
    <div class="page_full">
      <div class="map" id="myMap">

      </div>
      <div class="mapInfo" v-if="isShow">
        <h2>{{info.name}}</h2>
        <p class="address">{{info.address}}</p>
        <p>距您{{distance}}公里</p>
        <div class="mapBtn" @click="handleGo">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import AMap from 'AMap'
  export default {
    name: 'map',
    data () {
      return {
        map: '',
        cityCode: '',
        marker: [],
        info: [],
        cPoint: [],
        // circle: '',
        distance: 0,
        isShow: false,
        markerIndex: -1,
        toast: ''
      }
    },
    mounted () {
      let _this = this
      _this.map = new AMap.Map('myMap', {
        resizeEnable: true,
        features: ['bg', 'road', 'point']
      })
      _this.map.on('complete', function () {
        AMap.plugin([
          'AMap.ToolBar'
        ], function () {
          // 在图面添加工具条控件，工具条控件集成了缩放、平移、定位等功能按钮在内的组合控件
          _this.map.addControl(new AMap.ToolBar({
            // 简易缩放模式，默认为 false
            position: 'LT',
            offset: new AMap.Pixel(0, 30), // 定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            liteStyle: true
          }))
        })
        _this.showTip('正在获取当前位置...')
        _this.positionMap()
      })
      // _this.map.on('zoomend', function () {
      //   _this.isShow = false
      //   _this.markerIndex = -1
      //   let center = _this.map.getCenter()
      //   _this.searchMap(center)
      // })
      _this.map.on('moveend', function () {
        _this.isShow = false
        _this.markerIndex = -1
        let center = _this.map.getCenter()
        _this.searchMap(center)
      })
    },
    methods: {
      handleGo () {
        let _this = this
        let p = _this.info.location
        let end = _this.info.name
        console.log(_this.cPoint)
        console.log(p)
        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
          _this.GoIos(end, p)
        } else if (/(Android)/i.test(navigator.userAgent)) {
          _this.GoAndroid(end, p)
        } else { // pc
          window.location.href = 'https://gaode.com/dir?from[name]=我的位置&from[lnglat]=' + this.cPoint.lng + ',' + this.cPoint.lat + '&to[name]=' + end + '&to[lnglat]=' + p.lng + ',' + p.lat + '&policy=1&type=car'
        }
      },
      GoAndroid (end, p) {
        let _this = this
        _this.$createActionSheet({
          data: [
            {
              content: '高德地图'
            },
            {
              content: '百度地图'
            }
          ],
          onSelect: (item, index) => {
            _this.openMapApp(p.lat, p.lng, end, index)
          }
        }).show()
      },
      GoIos (end, p) {
        let _this = this
        _this.$createActionSheet({
          data: [
            {
              content: '高德地图'
            },
            {
              content: '百度地图'
            },
            {
              content: '系统自带地图'
            }
          ],
          onSelect: (item, index) => {
            _this.openMapApp(p.lat, p.lng, end, index)
          }
        }).show()
      },
      openMapApp (lat, lng, addr, index) {
        // let _this = this
        if (/(Android)/i.test(navigator.userAgent)) {
          if (index === 0) {
            window.location.href = 'androidamap://navi?sourceApplication=hyk_h5&poiname=' + addr + '&lat=' + lat + '&lon=' + lng + '&dev=0&style=4'
          } else {
            window.location.href = 'bdapp://map/navi?location=' + lat + ',' + lng + '&query=' + addr + '&coord_type=gcj02&type=BLK'
          }
        } else if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
          if (index === 0) {
            window.location.href = 'iosamap://navi?sourceApplication=hyk_h5&poiname=' + addr + '&lat=' + lat + '&lon=' + lng + '&dev=0&style=4'
          } else if (index === 1) {
            window.location.href = 'baidumap://map/navi?location=' + lat + ',' + lng + '&coord_type=gcj02&type=BLK'
          } else {
            window.location.href = 'http://maps.apple.com/?sll=' + lat + ',' + lng + '&address=' + addr
          }
        }
      },
      searchMap (point) {
        let _this = this
        _this.map.remove(_this.marker)
        AMap.plugin('AMap.PlaceSearch', function () {
          let autoOptions = {
            type: '中国石化,中国石油',
            city: _this.cityCode,
            // showCover: true,
            autoFitView: false,
            pageSize: 100, // 单页显示结果条数
            pageIndex: 1 // 页码
          }
          let cpoint = [point.lng, point.lat]
          let placeSearch = new AMap.PlaceSearch(autoOptions)
          placeSearch.searchNearBy('', cpoint, 5000, function (status, result) {
            if (result.poiList === undefined) {
              return false
            }
            console.log(result)
            let zshIcon = new AMap.Icon({
              size: new AMap.Size(50, 60),
              image: '../mobile/static/images/zshIcon.png',
              imageOffset: new AMap.Pixel(3.5, 10),
              imageSize: new AMap.Size(43, 51)
            })
            let zsyIcon = new AMap.Icon({
              size: new AMap.Size(50, 60),
              imageOffset: new AMap.Pixel(3.5, 10),
              image: '../mobile/static/images/zsyIcon.png',
              imageSize: new AMap.Size(43, 51)
            })
            let zshIconClick = new AMap.Icon({
              size: new AMap.Size(50, 60),
              image: '../mobile/static/images/zshIconClick.png',
              imageSize: new AMap.Size(50, 60)
            })
            let zsyIconClick = new AMap.Icon({
              size: new AMap.Size(50, 60),
              image: '../mobile/static/images/zsyIconClick.png',
              imageSize: new AMap.Size(50, 60)
            })
            let pois = result.poiList.pois
            for (let i = 0; i < pois.length; i++) {
              let poi = pois[i]
              _this.marker[i] = new AMap.Marker({
                position: poi.location,   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
                title: poi.name,
                topWhenClick: true,
                // animation: 'AMAP_ANIMATION_DROP',
                offset: new AMap.Pixel(-25, -55),
                icon: poi.type.indexOf('中国石化') > -1 ? zshIcon : zsyIcon
              })
              // 将创建的点标记添加到已有的地图实例：
              _this.map.add(_this.marker[i])
              _this.marker[i].on('click', function () {
                _this.info = result.poiList.pois[i]
                if (_this.markerIndex === i) {
                  _this.marker[i].setIcon(_this.info.type.indexOf('中国石化') > -1 ? zshIcon : zsyIcon)
                  _this.isShow = false
                } else {
                  pois.forEach(function (item, index) {
                    _this.marker[index].setIcon(pois[index].type.indexOf('中国石化') > -1 ? zshIcon : zsyIcon)
                  })
                  _this.markerIndex = i
                  _this.marker[i].setIcon(_this.info.type.indexOf('中国石化') > -1 ? zshIconClick : zsyIconClick)
                  _this.distance = (Math.round(_this.cPoint.distance(_this.info.location)) / 1000).toFixed(2)
                  _this.isShow = true
                }
              })
            }
            // _this.circle.setMap(_this.map)
            // _this.map.setFitView()
          })
        })
      },
      showTip (tip) {
        let _this = this
        _this.toast = this.$createToast({
          txt: tip,
          type: 'txt',
          time: 0
        })
        _this.toast.show()
      },
      positionMap () {
        let _this = this
        AMap.plugin('AMap.Geolocation', function () {
          let geolocation = new AMap.Geolocation({
            enableHighAccuracy: true, // 是否使用高精度定位，默认:true
            timeout: 5000,          // 超过10秒后停止定位，默认：5s
            buttonPosition: 'LT',    // 定位按钮的停靠位置
            buttonOffset: new AMap.Pixel(13, 130), // 定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            // zoomToAccuracy: true,  // 定位成功后是否自动调整地图视野到定位点
            GeoLocationFirst: true,
            panToLocation: true
          })
          _this.map.addControl(geolocation)
          geolocation.getCurrentPosition(function (status, result) {
            if (status === 'complete') {
              _this.toast.hide()
              _this.cityCode = result.addressComponent.citycode
              _this.map.setZoom(13)
              _this.cPoint = result.position
            } else {
              _this.showToast('获取当前位置失败~' + status)
            }
          })
        })
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

<style lang="less" scoped>
  .map {
    width: 100%;
    height: 105%;
  }

  @-webkit-keyframes fadeInUp {
    0% {
      opacity: 0;
      -webkit-transform: translate3d(0, 100%, 0);
      transform: translate3d(0, 100%, 0);
    }

    100% {
      opacity: 1;
      -webkit-transform: none;
      transform: none;
    }
  }

  @keyframes fadeInUp {
    0% {
      opacity: 0;
      -webkit-transform: translate3d(0, 100%, 0);
      transform: translate3d(0, 100%, 0);
    }

    100% {
      opacity: 1;
      -webkit-transform: none;
      transform: none;
    }
  }

  .amap-logo {
    display: none !important;
  }

  .mapInfo {
    width: 100%;
    height: 115px;
    background: #ffffff;
    position: absolute;
    bottom: 0;
    box-shadow: 2px 0 4px rgba(0, 0, 0, 0.15);
    z-index: 99;
    animation: fadeInUp 0.3s;
    -webkit-animation: fadeInUp 0.3s;
    padding: 15px 15px 10px;
    h2 {
      font-size: 18px;
      text-align: left;
      font-weight: bold;
      color: #333333;
      line-height: 1.8em;
      width: 100%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    p {
      font-size: 13px;
      color: #333333;
      padding: 5px 0;
      line-height: 1.5em;
    }
    p.address {
      color: #999999;
      padding-bottom: 10px;
      border-bottom: solid 1px #ECECEC;
    }
    .mapBtn {
      width: 65px;
      height: 65px;
      background: url("../../static/images/mapBtn.png") no-repeat center center;
      background-size: contain;
      position: absolute;
      right: 15px;
      top: -32px;
      a {
        width: 100%;
        height: 100%;
        display: block;
      }
    }
  }
</style>
