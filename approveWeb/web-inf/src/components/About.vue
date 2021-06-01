<template>
  <div class="about">
    <div class="top">
      <div class="left">
        <div
          class="tab"
          :class="{ active: tabIndex == 0 }"
          @click="changeTab(0)"
        >
          首页
        </div>
        <div
          class="tab"
          :class="{ active: tabIndex == 1 }"
          @click="changeTab(1)"
        >
          模块二
        </div>
        <div
          class="tab"
          :class="{ active: tabIndex == 2 }"
          @click="changeTab(2)"
        >
          模块三
        </div>
      </div>
      <div class="middle">XX省XX市在线监测数据分布图</div>
      <div class="right">
        <div
          class="tab"
          :class="{ active: tabIndex == 3 }"
          @click="changeTab(3)"
        >
          模块四
        </div>
        <div
          class="tab"
          :class="{ active: tabIndex == 4 }"
          @click="changeTab(4)"
        >
          模块五
        </div>
        <div class="loginout">
          <img src="../assets/loginout.png" alt="" />
          退出
        </div>
      </div>
    </div>
    <div class="content">
      <div class="left">
        <div class="card1 card">
          <label>
            <img src="../assets/label.png" alt="" />
            设备在线检测
          </label>
          <device-online></device-online>
        </div>
        <div class="card2 card">
          <label
            ><img src="../assets/label.png" alt="" />全市设备数量统计</label
          >
          <device-num></device-num>
        </div>
      </div>
      <div class="middle">
        <ul class="top-box">
          <li>
            <span>今日告警</span>
            <h1>268</h1>
          </li>
          <li>
            <span>已处理</span>
            <h1>268</h1>
          </li>
        </ul>
        <div class="map-box">
          <a-map></a-map>
        </div>
      </div>
      <div class="right">
        <div class="card">
          <label><img src="../assets/label.png" alt="" />设备告警周统计</label>
          <device-warn></device-warn>
        </div>
        <div class="card">
          <label><img src="../assets/label.png" alt="" />告警类型占比</label>
          <warn-type></warn-type>
        </div>
        <div class="card">
          <label
            ><img src="../assets/label.png" alt="" />设备累计告警次数环比</label
          >
          <warn-total></warn-total>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// import { defineComponent } from "vue";
// 模块组件
import DeviceOnline from "@/components/About/deviceOnline"; //设备在线监测
import DeviceNum from "@/components/About/deviceNum"; //全市设备数量统计
import DeviceWarn from "@/components/About/deviceWarn"; //设备告警周统计
import WarnType from "@/components/About/warnType"; //告警类型占比
import WarnTotal from "@/components/About/warnTotal"; //设备累计告警次数环比
import AMap from "@/components/About/aMap"; //市级地图
export default ({
  components: {
    DeviceOnline,
    DeviceNum,
    DeviceWarn,
    WarnType,
    WarnTotal,
    AMap,
  },
  data() {
    return {
      tabIndex: 0,
    };
  },
  mounted() {},
  methods: {
    changeTab(i) {
      this.tabIndex = i;
    },
  },
});
</script>
<style lang="less" scoped>
.about {
  background: #0a0f43;

  margin-bottom: 20rpx;
  .top {
    width: 100vw;
    min-width: 1860px;
    height: 90px;
    text-align: center;
    background: url("../assets/about-top-bg.png") no-repeat 0 0;
    background-size: 100% 100%;
    & > div {
      display: inline-block;
      height: 90px;
      line-height: 90px;
      width: 518px;
      vertical-align: middle;
      color: #00deff;
      box-sizing: border-box;
    }
    .tab {
      display: inline-block;
      width: 150px;
      height: 50px;
      background: url("../assets/about-tab.png") no-repeat 0 0;
      background-size: 100% 100%;
      text-align: center;
      line-height: 50px;
      font-size: 18px;
      color: #00fffc;
      vertical-align: middle;
      &.active {
        background-image: url("../assets/about-tab-a.png");
      }
    }
    .left {
      text-align: right;
      position: relative;
      top: 20px;
      box-sizing: border-box;
      padding-right: 10px;
      div {
        margin-left: 20px;
        &:first-child {
          margin-left: 0;
        }
      }
    }
    .middle {
      width: 788px;
      color: #00fffc;
      font-size: 30px;
      font-weight: bold;
      letter-spacing: 13px;
      font-family: MicrosoftYaHei-Bold;
    }
    .right {
      text-align: left;
      position: relative;
      top: 20px;
      box-sizing: border-box;
      padding: 0 30px 0 10px;
      clear: both;
      .tab {
        margin-right: 20px;
      }
      .loginout {
        font-size: 16px;
        color: #c50000;
        float: right;
        img {
          width: 17px;
          height: 17px;
        }
      }
    }
  }
  .content {
    width: 1820px;
    margin: auto;
    text-align: center;
    margin-top: 24px;
    height: 930px;
    background: url("../assets/base.png") no-repeat center bottom;
    //  background-size:770px 705px;
    & > div {
      display: inline-block;
      width: 500px;
      height: 100%;
      vertical-align: top;
      position: relative;
      z-index: 1;
    }
    .card {
      background: url(../assets/left-box.png) no-repeat 0 0;
      background-size: 100% 100%;
      margin-top: 20px;
      text-align: left;
      padding: 32px 30px 0;
      box-sizing: border-box;
      label {
        font-size: 18px;
        color: #00fffc;
        margin-bottom: 20px;
        display: inline-block;
        img {
          margin-right: 20px;
        }
      }
    }
    .left {
      .card1 {
        height: 280px;
      }
      .card2 {
        height: 641px;
        background: url(../assets/right-box.png) no-repeat 0 0;
        background-size: 100% 100%;
        margin-top: 20px;
        text-align: left;
        padding: 32px 30px 0;
        box-sizing: border-box;
      }
    }
    .middle {
      width: 820px;
      box-sizing: border-box;
      padding: 0 27px;
      .top-box {
        text-align: left;
        li {
          display: inline-block;
          width: 272px;
          height: 100px;
          background: url(../assets/middle-card.png) no-repeat 0 0;
          background-size: 100% 100%;
          text-align: right;
          margin-right: 30px;
          line-height: 100px;
          * {
            display: inline-block;
            vertical-align: baseline;
          }
          span {
            font-size: 18px;

            color: #00deff;
          }
          h1 {
            font-size: 50px;
            margin: 0 20px;
            color: #00fffc;
          }
        }
      }
      .map-box {
        width: 100%;
        height: 500px;
        margin-top: 30px;
      }
    }
    .right {
      .card {
        height: 310px;
        &:first-child {
          height: 280px;
        }
      }
    }
  }
}
</style>
