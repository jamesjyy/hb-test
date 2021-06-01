<template>
  <div class="envInfo">
    <label><img src="@/assets/label.png" alt=""/>环境信息详情</label>
    <ul>
      <li v-bind:style="yanwu.style">
        <img :src="yanwu.icon" alt=""/>
        <div>
          <p>{{ smokestatusdesc }}</p>
          <span>烟雾</span>
        </div>
      </li>
      <li v-bind:style="menjin.style">
        <img :src="menjin.icon" alt=""/>
        <div>
          <p>{{ doorstatusdesc }}</p>
          <span>门禁</span>
        </div>
      </li>
      <li v-bind:style="shuijin.style">
        <img :src="shuijin.icon" alt=""/>
        <div>
          <p>{{ waterstatusdesc }}</p>
          <span>水浸</span>
        </div>
      </li>
      <li v-bind:style="temp.style">
        <img :src="temp.icon" alt=""/>
        <div>
          <h3>{{ temhumhumidity }}℃</h3>
          <span>湿度</span>
        </div>
      </li>
      <li v-bind:style="wendu.style">
        <img :src="wendu.icon" alt=""/>
        <div>
          <h3>{{ temhumtemperature }}℃</h3>
          <span>温度</span>
        </div>
      </li>
      <li v-bind:style="pm.style">
        <img :src="pm.icon" alt=""/>
        <div>
          <h3>170</h3>
          <span>PM2.5</span>
        </div>
      </li>
    </ul>
  </div>
</template>
<script>
import imgs from "@/assets/imgs.js";
import {HomeData} from '@/network/Home/Home'

export default {
  props: ['env'],
  data() {
    return {
      yanwu: {
        style: imgs.cardBox1,
        icon: imgs.yanwu1,
      },
      menjin: {
        style: imgs.cardBox1,
        icon: imgs.menjin1,
      },
      shuijin: {
        style: imgs.cardBox4,
        icon: imgs.shuijin4,
      },
      temp: {
        style: imgs.cardBox1,
        icon: imgs.temp1,
      },
      wendu: {
        style: imgs.cardBox3,
        icon: imgs.wendu3,
      },
      pm: {
        style: imgs.cardBox2,
        icon: imgs.pm2,
      },

      smokestatusdesc: '',
      waterstatusdesc: '',
      doorstatusdesc: '',
      //温度
      temhumtemperature: '',
      //湿度
      temhumhumidity: '',
    };
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      var jsons = this.env
      if (jsons.smoke == null) {
        this.smokestatusdesc = "无"
      } else {
        this.smokestatusdesc = jsons.smoke[0].statusdesc;
      }

      if (jsons.water == null) {
        this.waterstatusdesc = "无"
      } else {
        this.waterstatusdesc = jsons.water[0].statusdesc;
      }

      if (jsons.door == null) {
        this.doorstatusdesc = "无"
      } else {
        this.doorstatusdesc = jsons.door[0].statusdesc;
      }

      if (jsons.temhum == null) {
        this.temhumtemperature = "无"
        this.temhumhumidity = "无"
      } else {
        this.temhumtemperature = jsons.temhum[0].temperature;
        this.temhumhumidity = jsons.temhum[0].humidity;
      }
    },
  },
  created() {
    // this.getData()
  }
};
</script>
<style lang="less" scoped>
.envInfo {
  background: #0a0f43;
  width: 100%;
  background: url(../../assets/left-box.png) no-repeat 0 0;
  background-size: 100% 100%;
  text-align: left;
  box-sizing: border-box;
  height: 270px;
  margin-top: 0;
  padding: 32px 40px 0 58px;

  label {
    font-size: 18px;
    color: #00fffc;

    img {
      margin-right: 20px;
    }
  }

  ul {
    margin-top: 15px;
    word-wrap: wrap;
    display: inline-block;

    li {
      width: 110px;
      height: 69px;
      background-size: 100% 100%;
      padding: 10px 0 0 15px;
      box-sizing: border-box;
      display: inline-block;
      margin-right: 30px;

      &:nth-child(3n) {
        margin-right: 0;
      }

      &:nth-child(-n + 3) {
        margin-bottom: 30px;
      }

      & > * {
        display: inline-block;
        vertical-align: top;
      }

      img {
        width: 24px;
        height: 30px;
        margin-right: 12px;
      }

      div {
        text-align: center;

        p {
          font-size: 18px;
          margin-top: 2px;
        }

        h3 {
          font-size: 18px;
          margin-top: 2px;
        }

        span {
          opacity: 0.6;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
