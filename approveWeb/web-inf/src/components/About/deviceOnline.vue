<template>
  <div class="deviceOnline">
    <div class="box">
      <img src="@/assets/quan-a.png" alt="" class="bg"/>
      <div class="echart" id="program"></div>
    </div>

    <div class="num">
      <div>
        <p>设备总数</p>
        <h2>{{ shebeicount }}</h2>
      </div>
      <div>
        <p>在线设备</p>
        <h2>896</h2>
      </div>
      <div>
        <p>离线设备</p>
        <h2>65</h2>
      </div>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts'

export default ({
  data() {
    return {
      charts: '',
      shebeicount: '',
      shebeionline: '',
    };
  },
  mounted() {
    this.initEchart();
  },
  methods: {
    getData() {
      //客户端ID唯一，这里使用随机+时间
      const clientId = 'mqttjs_' + Math.random().toString(16).substr(2, 8)

      //设备ID，可以使用控制器传入，这里测试
      const deviceId = 'sn20210107t0001';

      //固定不变
      const options = {
        keepalive: 60,
        clean: true,
        reconnectPeriod: 1000,
        connectTimeout: 30 * 1000,
        protocolId: 'MQTT',
        protocolVersion: 4,
        clientId: clientId,
        username: 'hbcom',
        password: 'hbcom@12345'
      }

      //固定不变
      const client = mqtt.connect('ws://36.133.20.49:8000', options)

      //错误时执行
      client.on('error', function (err) {
        client.end()
      })

      //重新连接执行
      client.on('reconnect', () => {
      })

      //连接成功执行
      client.on('connect', function () {
        //订阅主题
        client.subscribe('/dev/pub/msg/' + deviceId.toLowerCase());
      })
      //服务器主动发送数据 topic为主题，message为消息
      client.on("message", (topic, message) => {
        var jsons = JSON.parse(message.toString());
        this.shebeicount = jsons.aecount + jsons.doorcount + jsons.hfctcount + jsons.smokecount +
          jsons.temhumcount + jsons.tevcount + jsons.uhfcount + jsons.watercount
      })

      //关闭使用
      client.on('close', () => {
      });
    },
    initEchart() {
      this.charts = echarts.init(document.getElementById("program"));
      let e = 75;
      // 绘制图表
      this.charts.setOption({
        title: {
          show: true,
          text: `${e}%`,
          x: "center",
          top: 40,
          padding: 20,
          textStyle: {
            fontSize: "30",
            color: "#00fffc",
            fontWeight: "bold",
          },
          subtext: "''",

          subtextStyle: {
            fontSize: "15",
            color: "#00fffc",
          },
        },

        legend: {
          orient: "vertical",
          x: "left",
          show: false,
        },
        series: {
          name: "",
          type: "pie",
          radius: ["68%", "83%"],
          avoidLabelOverlap: true,
          hoverAnimation: false,
          label: {
            normal: {
              show: false,
              position: "center",
            },
            emphasis: {
              show: false,
            },
          },
          labelLine: {
            normal: {
              show: false,
            },
          },
          data: [
            {value: e, name: "成绩评定"},
            {value: 100 - e, name: ""},
          ],
          itemStyle: {
            normal: {
              color: function (params) {
                //自定义颜色
                var colorList = ["#00fffc", "transparent"];
                return colorList[params.dataIndex];
              },
            },
          },
        },
      });
    },
  },
  created() {
    this.getData()
  }
});
</script>
<style lang="less" scoped>
.deviceOnline {
  background: #0a0f43;
  width: 100%;
  height: 150px;
  padding-left: 30px;
  box-sizing: border-box;

  & > div {
    display: inline-block;
    vertical-align: middle;
  }

  .box {
    width: 180px;
    height: 180px;
    box-sizing: border-box;
    position: relative;
    margin-right: 30px;
    background: url('../../assets/quan-in.png') no-repeat 30px 30px;

    .bg {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      animation: xuanzhuan 5s linear infinite;
    }
  }

  .echart {
    position: relative;
    z-index: 5;
    width: 100%;
    height: 100%;
  }

  .num {
    color: #00fffc;
    line-height: 1.3;

    p {
      display: inline-block;
      font-size: 16px;
      margin-right: 10px;
    }

    h2 {
      display: inline-block;
      font-size: 38px;
      font-family: "楷体", "楷体_GB2312";
    }
  }
}

@keyframes xuanzhuan {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
