<template>
  <div class="faultmap">
    <div id="allmap" style="width:1500px;height:800px"></div>
  </div>
</template>
<script>
import {map} from '../../src/network/map/map.js';

export default {
  dataz() {
    return {
      mapId: 'BMap-' + parseInt(Date.now() + Math.random()),
      myMap: null
    }
  },
  methods: {
    initMap() {
      map('tnXg5saO2k00pAboF709MRHqERbMKiLm').then(() => {
        // 百度地图API功能
        var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
        var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
        var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮

        this.myMap = new BMap.Map("allmap") // 创建Map实例
        this.myMap.centerAndZoom(new BMap.Point(113.649, 34.756), 11) // 初始化地图,设置中心点坐标和地图级别
        //添加地图类型控件
        this.myMap.addControl(
          new BMap.MapTypeControl({
            mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP]
          })
        )
        this.myMap.addControl(top_left_control);
        this.myMap.addControl(top_left_navigation);
        this.myMap.addControl(top_right_navigation);
        // 创建点标记
        var marker1 = new BMap.Marker(new BMap.Point(116.404, 39.925));
        var marker2 = new BMap.Marker(new BMap.Point(116.404, 39.915));
        var marker3 = new BMap.Marker(new BMap.Point(116.395, 39.935));
        var marker4 = new BMap.Marker(new BMap.Point(116.415, 39.931));
        // 在地图上添加点标记
        this.myMap.addOverlay(marker1);
        this.myMap.addOverlay(marker2);
        this.myMap.addOverlay(marker3);
        this.myMap.addOverlay(marker4);
        this.myMap.setCurrentCity('郑州市') // 设置地图显示的城市 此项是必须设置的
        this.myMap.enableScrollWheelZoom(true) //开启鼠标滚轮缩放
      })
        .catch(err => {
          // console.log(err)
          console.log('地图加载失败')
        })
    },
  },
  mounted() {
    this.initMap()
  }
}
</script>
<style>
.faultmap{
  margin-left: 80px;
  margin-top: 50px;
}
</style>

