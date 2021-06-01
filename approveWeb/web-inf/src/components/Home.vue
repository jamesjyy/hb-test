<template>
  <div class="home">
    <div class="top">
      <div class="left">
        <div class="time">{{ this.newDateTime }}</div>
        <div class="day">{{ this.newDateday }}</div>
        <div class="date">{{ this.newDatedata }}</div>
      </div>
      <div class="middle">郑州市宏博机房在线监测数据分布图</div>
      <div class="right">
        <div class="weather">
          <img src="../assets/xiaoyu.png" alt=""/>
          <span>小雨</span>
        </div>
        <div class="temp">-3℃~5℃</div>
        <div class="loginout">
          <img src="../assets/loginout.png" alt="" @click="logout()"/>
          退出
        </div>
      </div>
    </div>
    <div class="content">
      <div class="left" v-if="isShow">
        <!-- 环境信息详情 -->
        <env-info :env="baseInfo"></env-info>

        <!-- 设备告警统计 -->
        <device-warn :device="baseInfo"></device-warn>

        <!-- 告警信息 -->
        <warn-info :warn="baseInfo"></warn-info>
      </div>
      <div class="middle" v-if="isShow">
        <!-- 中间模块组件 -->
        <middle-box :borderbox="baseInfo" :borderboxdata="borderboxdata"  @middleboxemit='middleboxemit($event)'>
        </middle-box>
      </div>
      <div class="right">
        <label><img src="../assets/label.png" alt=""/>设备信息详情</label>
        <ul class="tabs">
          <li :class="{ active: tabIndex == 0 }" @click="changeTab(0)">AE</li>
          <li :class="{ active: tabIndex == 1 }" @click="changeTab(1)">TEV</li>
          <li :class="{ active: tabIndex == 2 }" @click="changeTab(2)">HFCT</li>
          <li :class="{ active: tabIndex == 3 }" @click="changeTab(3)">UHF</li>
        </ul>
        <div v-if="isShow">
          <!-- 基本信息 -->
          <base-info :type="tabIndex" :base="baseInfo"></base-info>

          <!-- 幅值曲线 -->
          <amplitude :type="tabIndex" :amplitude="baseInfo"/>

          <!-- 相位动态 -->
<!--          <phase :type="tabIndex" :phase="baseInfo"/>-->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {HomeData} from '@/network/Home/Home'
import middleBox from "@/components/Home/middleBox"; //中间模块组件
import envInfo from "@/components/Home/envInfo"; //环境信息详情
import DeviceWarn from "@/components/Home/deviceWarn"; //设备告警统计
import WarnInfo from "@/components/Home/warnInfo"; //告警信息
import BaseInfo from "@/components/Home/baseInfo"; //基本信息
import Amplitude from "@/components/Home/amplitude"; //幅值曲线
// import Phase from "@/components/Home/phase"; //相位动态
export default ({
  components: {
    middleBox,
    envInfo,
    DeviceWarn,
    WarnInfo,
    BaseInfo,
    Amplitude,
    // Phase,
  },
  data() {
    return {
      tabIndex: 0,
      isShow: true,
      jsonData: [],
      newDateTime: '',
      newDateday: '',
      newDatedata: '',
      nowDate: "", // 当前日期
      baseInfo: '',
      borderbox: '',
      shebeinumberinfo: "",
      borderboxdata:'',

    };
  },
  mounted() {
    this.getData(this.shebeinumberinfo)
    this.currentTime();
  },
  methods: {
    middleboxemit(data) {
      this.borderboxdata=data[0].cc
      this.getData(data[0].dd)
    },
    getData(data) {
      if (data == ""||data==null) {
        data = "sn20210107t0001"
      }
      HomeData(data).then(res => {
        this.baseInfo = res
        this.borderbox = res
        this.isShow = false;
        this.$nextTick(() => {
          this.isShow = true
        })
      })
    },
    logout() {
      location.href = ("/index.html")
    },
    currentTime() {
      setInterval(this.formatDate, 500);
    },

    formatDate() {
      let date = new Date();
      let year = date.getFullYear(); // 年
      let month = date.getMonth() + 1; // 月
      let day = date.getDate(); // 日
      let week = date.getDay(); // 星期
      let weekArr = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
      let hour = date.getHours(); // 时
      hour = hour < 10 ? "0" + hour : hour; // 如果只有一位，则前面补零
      let minute = date.getMinutes(); // 分
      minute = minute < 10 ? "0" + minute : minute; // 如果只有一位，则前面补零
      let second = date.getSeconds(); // 秒
      second = second < 10 ? "0" + second : second; // 如果只有一位，则前面补零
      // this.nowDate = `${year}/${month}/${day} ${hour}:${minute}:${second} ${weekArr[week]}`;
      this.newDateTime = `${hour}:${minute}:${second}`
      this.newDateday = `${weekArr[week]}`
      this.newDatedata = `${year}/${month}/${day}`
    },
    changeTab(i) {
      this.tabIndex = i;
      this.isShow = false;
      this.$nextTick(() => {
        this.isShow = true
      })
    },
  },
  beforeDestroy() {
    if (this.formatDate) {
      clearInterval(this.formatDate); // 在Vue实例销毁前，清除时间定时器
    }
  },

  created() {
  }
});
</script>

<style lang="less" scoped>
.home {
  //transform: scale(.8);
  width: 100%;
  margin: 25px auto;
  background: #0a0f43;

  .top {
    height: 90px;
    text-align: center;

    & > div {
      display: inline-block;
      height: 90px;
      line-height: 90px;
      width: 478px;
      vertical-align: middle;
      color: #00deff;
      box-sizing: border-box;
    }

    .left {
      background: url("../assets/t-left.png") no-repeat 100% 100%;
      background-size: 478px 90px;
      text-align: left;
      padding-left: 40px;

      div {
        display: inline-block;
        vertical-align: baseline;
      }

      .time {
        font-size: 30px;
      }

      .day {
        font-size: 16px;
        margin: 0 30px;
      }

      .date {
        font-size: 20px;
      }
    }

    .middle {
      width: 858px;
      color: #00fffc;
      font-size: 30px;
      font-weight: bold;
      letter-spacing: 13px;
      font-family: MicrosoftYaHei-Bold;
      background: url(../assets/t-center.png) no-repeat 0 0;
      background-size: 100% 100%;
    }

    .right {
      background: url(../assets/t-right.png) no-repeat 0 0;
      background-size: 100% 100%;
      text-align: right;
      padding-right: 90px;

      div {
        display: inline-block;
        font-size: 14px;

        & > * {
          display: inline-block;
          vertical-align: middle;
        }
      }

      .weather {
        img {
          width: 30px;
          height: 30px;
          margin-right: 10px;
        }
      }

      .temp {
        margin: 0 30px;
      }

      .loginout {
        font-size: 16px;
        color: #c50000;

        img {
          width: 17px;
          height: 17px;
        }
      }
    }
  }

  .content {
    text-align: center;
    margin-top: 24px;
    height: 930px;
    background: url("../assets/base.png") no-repeat center bottom,
    url("../assets/base1.png") no-repeat center 200px;

    & > div {
      display: inline-block;
      width: 500px;
      height: 100%;
      vertical-align: top;
      position: relative;
      z-index: 1;
    }

    .middle {
      width: 820px;
      box-sizing: border-box;
      padding-left: 27px;
    }

    .right {
      background: url("../assets/right-box.png") no-repeat 0 0;
      background-size: 100% 100%;
      padding: 25px 30px;
      box-sizing: border-box;
      text-align: left;

      label {
        font-size: 18px;
        line-height: 36px;
        color: #00fffc;

        img {
          margin-right: 20px;
        }
      }

      .tabs {
        margin: 15px 0 5px 40px;

        li {
          display: inline-block;
          vertical-align: top;
          width: 90px;
          height: 30px;
          line-height: 30px;
          text-align: center;
          font-size: 18px;
          color: #00deff;
          background: url("../assets/selecting.png") no-repeat 0 0;
          background-size: 100% 100%;

          &.active {
            background-image: url("../assets/selected.png");
          }
        }
      }
    }
  }
}
</style>
