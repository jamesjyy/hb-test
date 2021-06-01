<template>
  <div class="baseInfo">
    <h3>基础信息</h3>
    <div class="baseInfoAE" v-if="baseInfoAEflg">
      <ul>
        <li>
          <img src="@/assets/fuzhi.png" alt=""/>
          <div>
            <h3>{{ amplitudemeanAe }}</h3>
            <span>均值</span>
          </div>
        </li>
        <li>
          <img src="@/assets/jishu.png" alt=""/>
          <div>
            <h3>{{ amplitudepeakAe }}</h3>
            <span>峰值</span>
          </div>
        </li>
        <li>
          <img src="@/assets/temp1.png" alt=""/>
          <div>
            <h3>{{ humidityAe + "℃" }}</h3>
            <span>湿度</span>
          </div>
        </li>
        <li>
          <img src="@/assets/wendu1.png" alt=""/>
          <div>
            <h3>{{ temperatureAe + "℃" }}</h3>
            <span>温度</span>
          </div>
        </li>
        <li>
          <img src="@/assets/quezhi.png" alt=""/>
          <div>
            <h3>{{ amplitudegainAe }}</h3>
            <span>增益</span>
          </div>
        </li>
        <li>
          <img src="@/assets/status.png" alt=""/>
          <div>
            <h3>{{ statuscodeAe }}</h3>
            <span>状态</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="baseInfoTEV" v-if="baseInfoTEVflg">
      <ul>
        <li>
          <img src="@/assets/fuzhi.png" alt=""/>
          <div>
            <h3>{{ amplitudepeakTev }}</h3>
            <span>幅值</span>
          </div>
        </li>
        <li>
          <img src="@/assets/jishu.png" alt=""/>
          <div>
            <h3>{{ quantityTev }}</h3>
            <span>计数</span>
          </div>
        </li>
        <li>
          <img src="@/assets/temp1.png" alt=""/>
          <div>
            <h3>{{ humidityTev + "℃" }}</h3>
            <span>湿度</span>
          </div>
        </li>
        <li>
          <img src="@/assets/wendu1.png" alt=""/>
          <div>
            <h3>{{ temperatureTev + "℃" }}</h3>
            <span>温度</span>
          </div>
        </li>
        <li>
          <img src="@/assets/quezhi.png" alt=""/>
          <div>
            <h3>{{ amplitudethdTev }}</h3>
            <span>阙值</span>
          </div>
        </li>
        <li>
          <img src="@/assets/status.png" alt=""/>
          <div>
            <h3>{{ statuscodeTev }}</h3>
            <span>状态</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="baseInfoHFCT" v-if="baseInfoHFCTflg">
      <ul>
        <li>
          <img src="@/assets/fuzhi.png" alt=""/>
          <div>
            <h3>{{ amplitudepeakHfct }}</h3>
            <span>幅值</span>
          </div>
        </li>
        <li>
          <img src="@/assets/jishu.png" alt=""/>
          <div>
            <h3>{{ quantityHfct }}</h3>
            <span>计数</span>
          </div>
        </li>
        <li>
          <img src="@/assets/temp1.png" alt=""/>
          <div>
            <h3>{{ humidityHfct + "℃" }}</h3>
            <span>湿度</span>
          </div>
        </li>
        <li>
          <img src="@/assets/wendu1.png" alt=""/>
          <div>
            <h3>{{ temperatureHfct + "℃" }}</h3>
            <span>温度</span>
          </div>
        </li>
        <li>
          <img src="@/assets/quezhi.png" alt=""/>
          <div>
            <h3>{{ amplitudethdHfct }}</h3>
            <span>阙值</span>
          </div>
        </li>
        <li>
          <img src="@/assets/status.png" alt=""/>
          <div>
            <h3>{{ statuscodeHfct }}</h3>
            <span>状态</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="baseInfoUHF" v-if="baseInfoUHFflg">
      <ul>
        <li>
          <img src="@/assets/fuzhi.png" alt=""/>
          <div>
            <h3>{{ amplitudepeakUhf }}</h3>
            <span>幅值</span>
          </div>
        </li>
        <li>
          <img src="@/assets/jishu.png" alt=""/>
          <div>
            <h3>{{ quantityUhf }}</h3>
            <span>计数</span>
          </div>
        </li>
        <li>
          <img src="@/assets/temp1.png" alt=""/>
          <div>
            <h3>{{ humidityUhf + "℃" }}</h3>
            <span>湿度</span>
          </div>
        </li>
        <li>
          <img src="@/assets/wendu1.png" alt=""/>
          <div>
            <h3>{{ temperatureUhf + "℃" }}</h3>
            <span>温度</span>
          </div>
        </li>
        <li>
          <img src="@/assets/quezhi.png" alt=""/>
          <div>
            <h3>{{ amplitudethdUhf }}</h3>
            <span>阙值</span>
          </div>
        </li>
        <li>
          <img src="@/assets/status.png" alt=""/>
          <div>
            <h3>{{ statuscodeUhf }}</h3>
            <span>状态</span>
          </div>
        </li>
      </ul>
    </div>
    <div class="line"></div>
  </div>
</template>
<script>
export default ({
  props: ['base', 'type'],

  data() {
    return {
      jsonData: [],
      baseInfoAEflg: false,
      baseInfoTEVflg: false,
      baseInfoHFCTflg: false,
      baseInfoUHFflg: false,
      //AE幅值均值
      amplitudemeanAe: '',
      //AE幅值峰值
      amplitudepeakAe: '',
      //AE温度
      temperatureAe: '',
      //AE湿度
      humidityAe: '',
      //AE幅值增益
      amplitudegainAe: '',
      //AE状态
      statuscodeAe: '',
      //Tev幅值峰值
      amplitudepeakTev: '',
      //Tev计数
      quantityTev: '',
      //Tev湿度
      humidityTev: '',
      //Tev温度
      temperatureTev: '',
      //Tev阈值
      amplitudethdTev: '',
      //Tev状态
      statuscodeTev: '',
      //Hfct幅值峰值
      amplitudepeakHfct: '',
      //Hfct计数
      quantityHfct: '',
      //Hfct湿度
      humidityHfct: '',
      //Hfct温度
      temperatureHfct: '',
      //Hfct阈值
      amplitudethdHfct: '',
      //Hfct状态
      statuscodeHfct: '',
      //Uhf幅值峰值
      amplitudepeakUhf: '',
      //Uhf计数
      quantityUhf: '',
      //Uhf湿度
      humidityUhf: '',
      //Uhf温度
      temperatureUhf: '',
      //Uhf阈值
      amplitudethdUhf: '',
      //Uhf状态
      statuscodeUhf: '',

    };
  },
  mounted() {
  },
  methods: {
    getData() {
      var jsons = this.base
      if (jsons.ae == null) {
        this.amplitudemeanAe = "无";

        this.amplitudepeakAe = "无";
        //AE温度
        this.temperatureAe = "无";
        //AE湿度
        this.humidityAe = "无";
        //AE幅值增益
        this.amplitudegainAe = "无";
        //AE状态
        this.statuscodeAe = "无";
      } else {
        this.amplitudemeanAe = jsons.ae[0].amplitudemean;
        this.amplitudepeakAe = jsons.ae[0].amplitudepeak;
        //AE温度
        this.temperatureAe = jsons.ae[0].temperature;
        //AE湿度
        this.humidityAe = jsons.ae[0].humidity;
        //AE幅值增益
        this.amplitudegainAe = jsons.ae[0].amplitudegain;
        //AE状态
        this.statuscodeAe = jsons.ae[0].statuscode;
      }
      if (jsons.tev == null) {
        //Tev幅值峰值
        this.amplitudepeakTev = "无";
        //Tev计数 "",
        this.quantityTev = "无";
        //Tev湿度
        this.humidityTev = "无";
        //Tev温度
        this.temperatureTev = "无";
        //Tev阈值
        this.amplitudethdTev = "无";
        //Tev状态
        this.statuscodeTev = "无";
      } else {
        //Tev幅值峰值
        this.amplitudepeakTev = jsons.tev[0].amplitudepeak;
        //Tev计数 "",
        this.quantityTev = jsons.tev[0].quantity;
        //Tev湿度
        this.humidityTev = jsons.tev[0].humidity;
        //Tev温度
        this.temperatureTev = jsons.tev[0].temperature;
        //Tev阈值
        this.amplitudethdTev = jsons.tev[0].amplitudethd;
        //Tev状态
        this.statuscodeTev = jsons.tev[0].statuscode;
      }
      if (jsons.hfct == null) {
        //Hfct幅值峰值
        this.amplitudepeakHfct = "无";
        //Hfct计数
        this.quantityHfct = "无";
        //Hfct湿度
        this.humidityHfct = "无";
        //Hfct温度
        this.temperatureHfct = "无";
        //Hfct阈值
        this.amplitudethdHfct = "无";
        //Hfct状态
        this.statuscodeHfct = "无";
      } else {
        //Hfct幅值峰值
        this.amplitudepeakHfct = jsons.hfct[0].amplitudepeak;
        //Hfct计数
        this.quantityHfct = jsons.hfct[0].quantity;
        //Hfct湿度
        this.humidityHfct = jsons.hfct[0].humidity;
        //Hfct温度
        this.temperatureHfct = jsons.hfct[0].temperature;
        //Hfct阈值
        this.amplitudethdHfct = jsons.hfct[0].amplitudethd;
        //Hfct状态
        this.statuscodeHfct = jsons.hfct[0].statuscode;
      }
      if (jsons.uhf == null) {
        //Uhf幅值峰值
        this.amplitudepeakUhf = "无";
        //Uhf计数
        this.quantityUhf = "无";
        //Uhf湿度
        this.humidityUhf = "无";
        //Uhf温度
        this.temperatureUhf = "无";
        //Uhf阈值
        this.amplitudethdUhf = "无";
        //Uhf状态
        this.statuscodeUhf = "无";
      } else {
        //Uhf幅值峰值
        this.amplitudepeakUhf = jsons.uhf[0].amplitudepeak;
        //Uhf计数
        this.quantityUhf = jsons.uhf[0].quantity;
        //Uhf湿度
        this.humidityUhf = jsons.uhf[0].humidity;
        //Uhf温度
        this.temperatureUhf = jsons.uhf[0].temperature;
        //Uhf阈值
        this.amplitudethdUhf = jsons.uhf[0].amplitudethd;
        //Uhf状态
        this.statuscodeUhf = jsons.uhf[0].statuscode;
      }
    },
    baseflg() {
      if (this.type == 0) {
        this.baseInfoAEflg = true
        this.baseInfoTEVflg = false
        this.baseInfoHFCTflg = false
        this.baseInfoUHFflg = false

      }
      if (this.type == 1) {
        this.baseInfoAEflg = false
        this.baseInfoTEVflg = true
        this.baseInfoHFCTflg = false
        this.baseInfoUHFflg = false
      }
      if (this.type == 2) {
        this.baseInfoAEflg = false
        this.baseInfoTEVflg = false
        this.baseInfoHFCTflg = true
        this.baseInfoUHFflg = false
      }
      if (this.type == 3) {
        this.baseInfoAEflg = false
        this.baseInfoTEVflg = false
        this.baseInfoHFCTflg = false
        this.baseInfoUHFflg = true
      }
    }
  },
  created() {
    this.baseInfoAEflg = true
    this.baseflg()
    this.getData()

  }
})
;
</script>

<style lang="less" scoped>
.baseInfo {
  background: #0a0f43;
  padding-left: 10px;

  & > h3 {
    text-align: center;
    padding: 22px 0 15px;
    font-size: 16px;
    color: #00fffc;
    font-weight: normal;
    padding-right: 40px;
  }

  .line {
    height: 1px;
    width: 100%;
    background: linear-gradient(to right, transparent, #00fffc, transparent);
  }

  ul {
    word-wrap: wrap;
    display: inline-block;

    li {
      width: 136px;
      height: 69px;
      background-size: 100% 100%;
      padding: 10px 0 0 15px;
      box-sizing: border-box;
      display: inline-block;
      margin-right: 5px;
      background: url("../../assets/cardBox1.png") no-repeat 0 0;
      background-size: 100% 100%;
      color: #00fffc;
      margin-bottom: 30px;

      &:nth-child(3n) {
        margin-right: 0;
      }

      & > * {
        display: inline-block;
        vertical-align: top;
      }

      img {
        height: 30px;
        margin-right: 12px;
      }

      div {
        text-align: center;

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
