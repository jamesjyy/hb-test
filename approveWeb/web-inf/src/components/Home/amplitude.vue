<template>
  <div class="amplitude">
    <label><img src="@/assets/label.png" alt=""/>实时数据</label>
    <table cellspacing="0">
      <thead>
      <tr>
        <th>今日时间</th>
        <th>设备编号</th>
        <th>设备位置</th>
        <th>设备类型</th>
        <th>告警程度</th>
      </tr>
      </thead>
    </table>
    <div id="demo" style="width:380px; height: 400px">
      <div id="demo1">
        <table class="tableCon" width="100%" @click="click">
          <tr v-for="(value,key) in items" :key="key">
            <td>
               <span style="font-size: 10px">
              {{ formatterDate(value.ct) }}
              </span>
            </td>
            <td style="font-size: 10px">{{ value.sensorid }}</td>
            <td style="font-size: 10px">{{ value.position }}</td>
            <td style="font-size: 10px">{{ value.shebeitype }}</td>
            <td style="font-size: 10px">
              <span>{{ (value.statusdesc) }}</span>
            </td>
          </tr>
        </table>
      </div>
      <div id="demo2"></div>
    </div>
  </div>
</template>
<script>
export default {
  props: ['amplitude'],
  data() {
    return {
      items: [],
      scroll: -1,     //定时器中使用。当表格滚动完了自动从第一条数据开始重复滚动
      myInterval: '',//设置定时器
      speed: 100,  //定时器执行时间间隔
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    // datashubiao() {
    //   clearInterval(this.myInterval);
    // },
    click() {
      clearInterval(this.myInterval);
    },
    Marquee() {
      // debugger
      if (demo2.offsetTop <= demo.scrollTop) {
        demo.scrollTop -= demo1.offsetHeight;
      } else {
        if (this.scroll !== demo.scrollTop) {
          this.scroll = demo.scrollTop;
          demo.scrollTop++;
        } else {
          demo.scrollTop = 0;
          this.scroll = -1;
        }
      }
    },
    init() {
      demo2.innerHTML = demo1.innerHTML;
      this.speed = 50
      this.myInterval = setInterval(this.Marquee, this.speed);
      if(this.myInterval>30){
        clearInterval(this.myInterval);
        this.myInterval=''
      }
    },
    getData() {
      var jsons = this.amplitude
      this.items = []
      // debugger
      if (jsons.ae == undefined && jsons.ae == null) {
      } else {
        for (var i = 0; i < jsons.ae.length; i++) {
          this.$set(jsons.ae[i], 'shebeitype', "AE")
          if (jsons.ae[i].statuscode == 0) {
            this.$set(jsons.ae[i], 'statuscodeinfo', "无需关注")
          }
          if (jsons.ae[i].statuscode == 1) {
            this.$set(jsons.ae[i], 'statuscodeinfo', "无需关注")
          }
        }
        this.items = this.items.concat(jsons.ae)
      }
      if (jsons.door == undefined && jsons.ae == null) {
      } else {
        for (var i = 0; i < jsons.door.length; i++) {
          this.$set(jsons.door[i], 'shebeitype', "门磁")
        }
        this.items = this.items.concat(jsons.door)
      }

      if (jsons.smoke == undefined && jsons.ae == null) {

      } else {
        for (var i = 0; i < jsons.smoke.length; i++) {
          this.$set(jsons.smoke[i], 'shebeitype', "烟雾")
        }
        this.items = this.items.concat(jsons.smoke)
      }

      if (jsons.water == undefined && jsons.ae == null) {

      } else {
        for (var i = 0; i < jsons.water.length; i++) {
          this.$set(jsons.water[i], 'shebeitype', "水浸")
        }
        this.items = this.items.concat(jsons.water)
      }

      if (jsons.temhum == undefined && jsons.ae == null) {

      } else {
        for (var i = 0; i < jsons.temhum.length; i++) {
          this.$set(jsons.temhum[i], 'shebeitype', "温湿度")
        }
        this.items = this.items.concat(jsons.temhum)
      }

      if (jsons.hfct == undefined && jsons.ae == null) {
      } else {
        for (var i = 0; i < jsons.hfct.length; i++) {
          this.$set(jsons.hfct[i], 'shebeitype', "HFCT")
        }
        this.items = this.items.concat(jsons.hfct)
      }
      if (jsons.tev == undefined && jsons.ae == null) {
      } else {
        for (var i = 0; i < jsons.tev.length; i++) {
          this.$set(jsons.tev[i], 'shebeitype', "TEV")
          if (jsons.tev[i].statuscode == 0) {
            this.$set(jsons.tev[i], 'statuscodeinfo', "无需关注")
          }
          if (jsons.tev[i].statuscode == 1) {
            this.$set(jsons.tev[i], 'statuscodeinfo', "轻微放电")
          }
          if (jsons.tev[i].statuscode == 3) {
            this.$set(jsons.tev[i], 'statuscodeinfo', "高等内部放电")
          }

          if (jsons.tev[i].statuscode == 6) {
            this.$set(jsons.tev[i], 'statuscodeinfo', "高度放电")
          }
        }
        this.items = this.items.concat(jsons.tev)
      }

      if (jsons.uhf == undefined && jsons.ae == null) {
      } else {
        for (var i = 0; i < jsons.uhf.length; i++) {
          this.$set(jsons.uhf[i], 'shebeitype', "UHF")
        }
        this.items = this.items.concat(jsons.uhf)
      }
    },
    formatterDate: function (row) {
      var date = new Date(parseInt(row) * 1000);
      let y = date.getFullYear();
      let m = date.getMonth() + 1;
      m = m < 10 ? "0" + m : m;
      let d = date.getDate();
      d = d < 10 ? "0" + d : d;
      let h = date.getHours();
      h = h < 10 ? "0" + h : h;
      let minute = date.getMinutes();
      let second = date.getSeconds();
      minute = minute < 10 ? "0" + minute : minute;
      second = second < 10 ? "0" + second : second;
      // return y + "年" + m + "月" + d + "日 " + h +"时"+ ":" + minute +"分"+ ":" + second+"秒";
      return h + ":" + minute + ":" + second;
      // return y + "-" + m + "-" + d + h + ":" + minute + ":" + second;
    },
  },
  created() {
    this.getData()
  }
}
</script>
<style lang="less" scoped>
.amplitude {
  background: #0a0f43;
  height: 510px;
  background: url(../../assets/left-box.png) no-repeat 0 0;
  background-size: 100% 100%;
  margin-top: 20px;
  text-align: left;
  padding: 32px 30px 0;
  //box-sizing: border-box;

  label {
    font-size: 18px;
    color: #00fffc;

    img {
      margin-right: 20px;
    }
  }

  table {
    width: 100%;
    font-size: 14px;
    line-height: 17px;
    color: #00fffc;
    text-align: center;
    background: rgba(255, 255, 255, 0.05);
    //border: solid 1px #00deff;
    //box-sizing: border-box;
    padding-bottom: 0px;
    margin-top: 8px;

    tr {
      line-height: 13px;

      td {
        border-bottom: 1px dashed #005ca3;
        width: 20%;
        padding: 10px 0;
      }

      &:first-child {
        line-height: 33px;

        th {
          font-size: 14px;
          color: #00c3e6;
          font-weight: normal;
          border-bottom: 1px solid #005ca3;
          vertical-align: top;
          overflow: hidden;
          position: relative;

          &:first-child::before {
            content: "";
            display: inline-block;
            width: 20px;
            height: 20px;
            background: #00deff;
            transform: rotate(45deg);
            position: absolute;
            left: -10px;
            top: -10px;
          }
        }
      }
    }
  }
}

#demo {
  overflow: hidden;
  width: 100%;
  height: 310px;
}

</style>
