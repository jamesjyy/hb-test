<template>
  <div class="borderbox">
    <ul class="top-box">
      <li>
        <span>传感器数量</span>
        <h1>{{ shebeicount }}</h1>
      </li>
      <li>
        <span>在线设备</span>
        <h1>{{ shebeionline }}</h1>
      </li>
      <li>
        <span>集中器</span>
        <select name="" style="background-color:#00fffc"
                id=""
                @change="test"
                v-model="current">
          <option v-for="(item,index) in list"
                  :key="index"
                  :value="item.id"
                  :data-rid='item.rid'>{{ item.name }}
          </option>
        </select>
      </li>
    </ul>
    <div id="aeinfo" style="background-color:#00fffc;">
      <div>
        <span style="margin-right: 80px;white-space: nowrap; display:block">设备种类: {{ aeshebeitypeh1 }}</span>
        <span style="margin-right: 68px;white-space: nowrap; display:block">设备编号: {{ aeshebeinumberh1 }}</span>
        <span style="margin-right: 60px;white-space: nowrap; display:block">设备位置: {{ aeshebeiposstitionh1 }}</span>
        <span style="margin-right: 90px;white-space: nowrap; display:block">设备状态: {{ aeshebeistatush1 }}</span>
        <span style="margin-right: 95px;white-space: nowrap; display:block">状态说明: </span>
        <span>{{ aeshebeistatusdes1 }}</span>
      </div>
    </div>
    <div id="tevinfo" style="background-color:#00fffc;">
      <div>
        <span style="margin-right:80px;white-space: nowrap; display:block">设备种类: {{ tevshebeitypeh1 }}</span>
        <span style="margin-right:68px;white-space: nowrap; display:block">设备编号: {{ tevshebeinumberh1 }}</span>
        <span style="margin-right:60px;white-space: nowrap; display:block">设备位置: {{ tevshebeiposstitionh1 }}</span>
        <span style="margin-right:90px;white-space: nowrap; display:block">设备状态: {{ tevshebeistatush1 }}</span>
        <span style="margin-right:95px;white-space: nowrap; display:block">状态说明: </span>
        <span>{{ tevshebeistatusdes1 }}</span>
      </div>
    </div>
    <div id="uhfinfo" style="background-color:#00fffc;">
      <div>
        <span style="margin-right:80px;white-space: nowrap; display:block">设备种类: {{ uhfshebeitypeh1 }}</span>
        <span style="margin-right:68px;white-space: nowrap; display:block">设备编号: {{ uhfshebeinumberh1 }}</span>
        <span style="margin-right:60px;white-space: nowrap; display:block">设备位置: {{ uhfshebeiposstitionh1 }}</span>
        <span style="margin-right:90px;white-space: nowrap; display:block">设备状态: {{ uhfshebeistatush1 }}</span>
        <span style="margin-right:95px;white-space: nowrap; display:block">状态说明: </span>
        <span>{{ uhfshebeistatusdes1 }}</span>
      </div>
    </div>
    <div id="hfctinfo" style="background-color:#00fffc;">
      <div>
        <span style="margin-right:80px;white-space: nowrap; display:block">设备种类: {{ hfctshebeitypeh1 }}</span>
        <span style="margin-right:68px;white-space: nowrap; display:block">设备编号: {{ hfctshebeinumberh1 }}</span>
        <span style="margin-right:60px;white-space: nowrap; display:block">设备位置: {{ hfctshebeiposstitionh1 }}</span>
        <span style="margin-right:90px;white-space: nowrap; display:block">设备状态: {{ hfctshebeistatush1 }}</span>
        <span style="margin-right:95px;white-space: nowrap; display:block">状态说明: </span>
        <span>{{ hfctshebeistatusdes1 }}</span>
      </div>
    </div>
    <ul class="border">
      <li v-if="jsondataoneflg">
        <div class="dot" v-for="(dot,index) in maxjsondata" :key="index">
          <img src="@/assets/blue.png" alt=""
               v-if="dot.aestatus==0 && dot.tevstatus==0 && dot.uhfstatus==0 && dot.hfctstatus==0"
               @mouseover="enter(dot)" @mouseleave="leave()">
          <!--          <img src="@/assets/yellow.png" alt="" v-if="dot.aestatus==1 " @mouseover="enter(dot)" @mouseleave="leave()">-->
          <!--          <img src="@/assets/orange.png" alt="" v-if="dot.aestatus==2" @mouseover="enter(dot)" @mouseleave="leave()">-->
          <!--          <img src="@/assets/orange.png" alt="" v-if="dot.aestatus==3" @mouseover="enter(dot)" @mouseleave="leave()">-->
          <!--          <img src="@/assets/red.png" alt="" v-if="dot.aestatus==4" @mouseover="enter(dot)" @mouseleave="leave()">-->
          <!--          <img src="@/assets/red.png" alt="" v-if="dot.aestatus==5" @mouseover="enter(dot)" @mouseleave="leave()">-->
          <img src="@/assets/red.png" alt="" v-else @mouseover="enter(dot)" @mouseleave="leave()">
        </div>
      </li>
      <li v-if="jsondatatwoflg">
        <div class="dot" v-for="(dot,jsondatatwoindex) in jsondatatwo" :key="jsondatatwoindex">
          <img src="@/assets/blue.png" alt="" v-if="dot.status==0" @mouseover="enter(dot)" @mouseleave="leave()">
          <img src="@/assets/yellow.png" alt="" v-if="dot.status==1 " @mouseover="enter(dot)" @mouseleave="leave()">
          <img src="@/assets/orange.png" alt="" v-if="dot.status==2" @mouseover="enter(dot)" @mouseleave="leave()">
          <img src="@/assets/orange.png" alt="" v-if="dot.status==3" @mouseover="enter(dot)" @mouseleave="leave()">
          <img src="@/assets/red.png" alt="" v-if="dot.status==4" @mouseover="enter(dot)" @mouseleave="leave()">
          <img src="@/assets/red.png" alt="" v-if="dot.status==5" @mouseover="enter(dot)" @mouseleave="leave()">
          <img src="@/assets/red.png" alt="" v-if="dot.status==6" @mouseover="enter(dot)" @mouseleave="leave()">
        </div>
      </li>
      <!--      <li v-if="jsondatathreeflg">-->
      <!--        <div class="dot" v-for="(dot,jsondatathreeindex) in jsondatathree" :key="jsondatathreeindex"-->
      <!--        >-->
      <!--          <img src="@/assets/blue.png" alt="" v-if="dot.status==0" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/yellow.png" alt="" v-if="dot.status==1 " @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/orange.png" alt="" v-if="dot.status==2" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/orange.png" alt="" v-if="dot.status==3" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/red.png" alt="" v-if="dot.status==4" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/red.png" alt="" v-if="dot.status==5" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/red.png" alt="" v-if="dot.status==6" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--        </div>-->
      <!--      </li>-->
      <!--      <li v-if="jsondatafourflg">-->
      <!--        <div class="dot" v-for="(dot,jsondatafourindex) in jsondatafour" :key="jsondatafourindex"-->
      <!--        >-->
      <!--          <img src="@/assets/blue.png" alt="" v-if="dot.status==0" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/yellow.png" alt="" v-if="dot.status==1 " @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/orange.png" alt="" v-if="dot.status==2" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/orange.png" alt="" v-if="dot.status==3" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/red.png" alt="" v-if="dot.status==4" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/red.png" alt="" v-if="dot.status==5" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--          <img src="@/assets/red.png" alt="" v-if="dot.status==6" @mouseover="enter(dot)" @mouseleave="leave()">-->
      <!--        </div>-->
      <!--      </li>-->
    </ul>
  </div>
</template>
<script>
export default ({
  props: ['borderbox', 'borderboxdata'],
  data() {
    return {
      shebeicount: '',
      selected: '',
      shebeionline: 0,
      list: [{id: 1, name: 'sn20210107t0001', rid: 'sn20210107t0001'}, {
        id: 2,
        name: 'sn4108812020t0001',
        rid: 'sn4108812020t0001'
      }],
      current: 0,
      province: '',
      aecount: '',
      doorcount: '',
      hfctcount: '',
      smokecount: '',
      temhumcount: '',
      uhfcount: '',
      tevcount: '',
      watercount: '',
      ccddData: [],
      blueflg: true,
      yellowflg: false,
      orangeflg: false,
      redflg: false,
      jsondata: [],
      jsondatacv: [],
      jsondataone: [],
      jsondatatwo: [],
      jsondatathree: [],
      jsondatafour: [],
      jsonalldata: [],
      jsondataoneflg: true,
      jsondatatwoflg: false,
      jsondatathreeflg: false,
      jsondatafourflg: false,
      //AE
      aeshebeitypeh1: "",
      aeshebeinumberh1: "",
      aeshebeiposstitionh1: "",
      aeshebeistatush1: "",
      aeshebeistatusdes1: "",
      //TEV
      tevshebeitypeh1: "",
      tevshebeinumberh1: "",
      tevshebeiposstitionh1: "",
      tevshebeistatush1: "",
      tevshebeistatusdes1: "",
      //UHF
      uhfshebeitypeh1: "",
      uhfshebeinumberh1: "",
      uhfshebeiposstitionh1: "",
      uhfshebeistatush1: "",
      uhfshebeistatusdes1: "",
      //HFCT
      hfctshebeitypeh1: "",
      hfctshebeinumberh1: "",
      hfctshebeiposstitionh1: "",
      hfctshebeistatush1: "",
      hfctshebeistatusdes1: "",
      maxcount: "",
      maxcounttwo: "",
      maxcountthree: "",
      maxmaxcount: "",
      maxjsondata: [],
      aedatastatuscode: "",
      aedatastatusdesc: "",
      aedataposition: "",
      aedatasensorid: "",

      tevdatastatuscode: "",
      tevdatastatusdesc: "",
      tevdataposition: "",
      tevdatasensorid: "",

      uhfdatastatuscode: "",
      uhfdatastatusdesc: "",
      uhfdataposition: "",
      uhfdatasensorid: "",

      hfctdatastatuscode: "",
      hfctdatastatusdesc: "",
      hfctdataposition: "",
      hfctdatasensorid: "",
    };
  },

  mounted() {
  },
  methods: {
    enter(data) {
      //AE
      this.aeshebeitypeh1 = data.aeshebeitype
      this.aeshebeinumberh1 = data.aeshebibianhao
      this.aeshebeiposstitionh1 = data.aeshebeiweizhi
      this.aeshebeistatush1 = data.aestatus
      this.aeshebeistatusdes1 = data.aestatusdesc
      //TEV
      this.tevshebeitypeh1 = data.tevshebeitype
      this.tevshebeinumberh1 = data.tevshebibianhao
      this.tevshebeiposstitionh1 = data.tevshebeiweizhi
      this.tevshebeistatush1 = data.tevstatus
      this.tevshebeistatusdes1 = data.tevstatusdesc
      //UHF
      this.uhfshebeitypeh1 = data.uhfshebeitype
      this.uhfshebeinumberh1 = data.uhfshebibianhao
      this.uhfshebeiposstitionh1 = data.uhfshebeiweizhi
      this.uhfshebeistatush1 = data.uhfstatus
      this.uhfshebeistatusdes1 = data.uhfstatusdesc
      //HFCT
      this.hfctshebeitypeh1 = data.hfctshebeitype
      this.hfctshebeinumberh1 = data.hfctshebibianhao
      this.hfctshebeiposstitionh1 = data.hfctshebeiweizhi
      this.hfctshebeistatush1 = data.hfctstatus
      this.hfctshebeistatusdes1 = data.hfctstatusdesc
      document.getElementById('aeinfo').style.display = 'block';
      document.getElementById('tevinfo').style.display = 'block';
      document.getElementById('uhfinfo').style.display = 'block';
      document.getElementById('hfctinfo').style.display = 'block';
    },
    leave() {
      document.getElementById('aeinfo').style.display = 'none';
      document.getElementById('tevinfo').style.display = 'none';
      document.getElementById('uhfinfo').style.display = 'none';
      document.getElementById('hfctinfo').style.display = 'none';
    },
    test(e) {
      var that = this
      var hh = that.list.filter(function (c, i, a) {//第一个参数为当前项,第二个参数为索引,第三个为原值
        if (c.id == that.current) {
          return c
        }
      })
      var cc = hh[0].id
      var dd = hh[0].rid //获取当前option的rid的值
      this.ccddData = []
      this.ccddData.push({
        cc: cc,
        dd: dd
      })
      this.$emit('middleboxemit', this.ccddData)
    },
    getData() {
      var jsons = this.borderbox
      this.jsondata = [];
      if (jsons.aecount == undefined || jsons.aecount == "" || jsons.aecount == null) {
        this.aecount = 0
      } else {
        if (jsons.ae.length > 0) {
          for (var i = 0; i < jsons.ae.length; i++) {
            this.jsondata.push({
              status: jsons.ae[i].statuscode,
              statusdesc: jsons.ae[i].statusdesc,
              shebeitype: "AE",
              shebeiweizhi: jsons.ae[i].position,
              shebibianhao: jsons.ae[i].sensorid
            })
          }
        }
        this.aecount = parseInt(jsons.aecount)
      }

      if (jsons.doorcount == undefined || jsons.doorcount == "" || jsons.doorcount == null) {
        this.doorcount = 0
      } else {
        this.doorcount = parseInt(jsons.doorcount)
      }

      if (jsons.hfctcount == undefined || jsons.hfctcount == "" || jsons.hfctcount == null) {
        this.hfctcount = 0
      } else {
        if (jsons.hfct.length > 0) {
          for (var i = 0; i < jsons.hfct.length; i++) {
            this.jsondata.push({
              status: jsons.hfct[i].statuscode,
              statusdesc: jsons.hfct[i].statusdesc,
              shebeitype: "HFCT",
              shebeiweizhi: jsons.hfct[i].position,
              shebibianhao: jsons.hfct[i].sensorid
            })
          }

        }
        this.hfctcount = parseInt(jsons.hfctcount)
      }

      if (jsons.smokecount == undefined || jsons.smokecount == "" || jsons.smokecount == null) {
        this.smokecount = 0
      } else {
        this.smokecount = parseInt(jsons.smokecount)
      }

      if (jsons.temhumcount == undefined || jsons.temhumcount == "" || jsons.temhumcount == null) {
        this.temhumcount = 0
      } else {
        this.temhumcount = parseInt(jsons.temhumcount)
      }

      if (jsons.uhfcount == undefined || jsons.uhfcount == "" || jsons.uhfcount == null) {
        this.uhfcount = 0
      } else {
        if (jsons.uhf.length > 0) {
          for (var i = 0; i < jsons.uhf.length; i++) {
            this.jsondata.push({
              status: jsons.uhf[i].statuscode,
              statusdesc: jsons.uhf[i].statusdesc,
              shebeitype: "UHF",
              shebeiweizhi: jsons.uhf[i].position,
              shebibianhao: jsons.uhf[i].sensorid
            })
          }
        }
        this.uhfcount = parseInt(jsons.uhfcount)
      }

      if (jsons.watercount == undefined || jsons.watercount == "" || jsons.watercount == null) {
        this.watercount = 0
      } else {
        this.watercount = parseInt(jsons.watercount)
      }
      if (jsons.tevcount == undefined || jsons.tevcount == "" || jsons.tevcount == null) {
        this.tevcount = 0
      } else {

        if (jsons.tev.length > 0) {
          for (var i = 0; i < jsons.tev.length; i++) {
            this.jsondata.push({
              status: jsons.tev[i].statuscode,
              statusdesc: jsons.tev[i].statusdesc,
              shebeitype: "TEV",
              shebeiweizhi: jsons.tev[i].position,
              shebibianhao: jsons.tev[i].sensorid
            })
          }
        }
        this.tevcount = parseInt(jsons.tevcount)
      }
      this.jsondataone = []
      this.jsondatatwo = []
      this.jsondatathree = []
      this.jsondatafour = []
      this.jsondatacv = this.jsondata
      if (0 < this.jsondata.length < 9) {
        this.jsondataone = this.jsondata
      }
      if (8 < this.jsondata.length < 17) {
        this.jsondataone = this.jsondata
        this.jsondatatwo = this.jsondatacv.splice(8)
        // this.jsondatatwoflg = true
      }

      if (this.aecount > this.hfctcount) {
        this.maxcount = this.aecount
      } else {
        this.maxcount = this.hfctcount
      }
      if (this.uhfcount > this.tevcount) {
        this.maxcounttwo = this.uhfcount
      } else {
        this.maxcounttwo = this.tevcount
      }
      if (this.maxcounttwo > this.maxcount) {
        this.maxmaxcount = this.maxcounttwo
      } else {
        this.maxmaxcount = this.maxcount
      }
      this.maxjsondata = []
      for (var i = 0; i < this.maxmaxcount; i++) {
        //AE
        if (jsons.ae == undefined) {
          this.aedatastatuscode = "无"
          this.aedatastatusdesc = "无"
          this.aedataposition = "无"
          this.aedatasensorid = "无"
        } else {
          if (i < this.aecount) {
            this.aedatastatuscode = jsons.ae[i].statuscode
            this.aedatastatusdesc = jsons.ae[i].statusdesc
            this.aedataposition = jsons.ae[i].position
            this.aedatasensorid = jsons.ae[i].sensorid
          } else {
            this.aedatastatuscode = "无"
            this.aedatastatusdesc = "无"
            this.aedataposition = "无"
            this.aedatasensorid = "无"
          }
        }
        //TEV
        if (jsons.tev == undefined) {
          this.tevdatastatuscode = "无"
          this.tevdatastatusdesc = "无"
          this.tevdataposition = "无"
          this.tevdatasensorid = "无"
        } else {
          if (i < this.tevcount) {
            this.tevdatastatuscode = jsons.tev[i].statuscode
            this.tevdatastatusdesc = jsons.tev[i].statusdesc
            this.tevdataposition = jsons.tev[i].position
            this.tevdatasensorid = jsons.tev[i].sensorid
          } else {
            this.tevdatastatuscode = "无"
            this.tevdatastatusdesc = "无"
            this.tevdataposition = "无"
            this.tevdatasensorid = "无"
          }
        }
        //UHF
        if (jsons.uhf == undefined) {
          this.uhfdatastatuscode = "无"
          this.uhfdatastatusdesc = "无"
          this.uhfdataposition = "无"
          this.uhfdatasensorid = "无"
        } else {
          if (i < this.uhfcount) {
            this.uhfdatastatuscode = jsons.uhf[i].statuscode
            this.uhfdatastatusdesc = jsons.uhf[i].statusdesc
            this.uhfdataposition = jsons.uhf[i].position
            this.uhfdatasensorid = jsons.uhf[i].sensorid
          } else {
            this.uhfdatastatuscode = "无"
            this.uhfdatastatusdesc = "无"
            this.uhfdataposition = "无"
            this.uhfdatasensorid = "无"
          }
        }
        //HFCT
        if (jsons.hfct == undefined) {
          this.hfctdatastatuscode = "无"
          this.hfctdatastatusdesc = "无"
          this.hfctdataposition = "无"
          this.hfctdatasensorid = "无"
        } else {
          if (i < this.hfctcount) {
            this.hfctdatastatuscode = jsons.hfct[i].statuscode
            this.hfctdatastatusdesc = jsons.hfct[i].statusdesc
            this.hfctdataposition = jsons.hfct[i].position
            this.hfctdatasensorid = jsons.uhf[i].sensorid
          } else {
            this.hfctdatastatuscode = "无"
            this.hfctdatastatusdesc = "无"
            this.hfctdataposition = "无"
            this.hfctdatasensorid = "无"
          }
        }
        this.maxjsondata.push({
          aestatus: this.aedatastatuscode,
          aestatusdesc: this.aedatastatusdesc,
          aeshebeitype: "AE",
          aeshebeiweizhi: this.aedataposition,
          aeshebibianhao: this.aedatasensorid,

          tevstatus: this.tevdatastatuscode,
          tevstatusdesc: this.tevdatastatusdesc,
          tevshebeitype: "TEV",
          tevshebeiweizhi: this.tevdataposition,
          tevshebibianhao: this.tevdatasensorid,

          uhfstatus: this.uhfdatastatuscode,
          uhfstatusdesc: this.uhfdatastatusdesc,
          uhfshebeitype: "UHF",
          uhfshebeiweizhi: this.uhfdataposition,
          uhfshebibianhao: this.uhfdatasensorid,

          hfctstatus: this.hfctdatastatuscode,
          hfctstatusdesc: this.hfctdatastatusdesc,
          hfctshebeitype: "HFCT",
          hfctshebeiweizhi: this.hfctdataposition,
          hfctshebibianhao: this.hfctdatasensorid,
        })
      }
      this.shebeicount = this.aecount + this.doorcount + this.hfctcount + this.smokecount +
        this.temhumcount + this.tevcount + this.uhfcount + this.watercount
      this.shebeionline = this.shebeicount
    },
  },
  created() {
    if (this.borderboxdata == "" || this.borderboxdata == null) {
      this.current = 1
    } else {
      this.current = this.borderboxdata
    }
    this.$nextTick(() => {
      this.getData()
    })
  }
});
</script>

<style scoped lang="less">
.borderbox {
  //background: #0a0f43;
  .top-box {
    text-align: left;

    li {
      display: inline-block;
      width: 244px;
      height: 100px;
      background: url(../../assets/middle-card.png) no-repeat 0 0;
      background-size: 100% 100%;
      text-align: right;
      margin-right: 16px;
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

      select {
        margin: 0 8px;
        //margin-bottom: 20px;
      }
    }
  }

  .border {
    // transform: scale(.8);
    position: relative;
    width: 793px;
    height: 580px;

    li {
      position: absolute;
      width: 245px;
      height: 290px;
      text-align: left;
      display: inline-block;
      background: url('../../assets/borderbox.png') no-repeat bottom right,
      url('../../assets/borderbox.png') no-repeat top left;
      transform: scale(.9);

      &:first-child {
        top: 23%;
        left: 4%
      }

      &:nth-child(2) {
        top: 48%;
        left: 37%;
      }

      &:nth-child(3) {
        top: 5%;
        left: 27%;
      }

      &:nth-child(4) {
        top: 30%;
        left: 60%;
      }

      .dot {
        position: absolute;
        width: 10px;
        height: 8px;

        &:nth-child(1) {
          top: 48px;
          left: 35px;
        }

        &:nth-child(2) {
          top: 80px;
          left: 92px;
        }

        &:nth-child(3) {
          top: 113px;
          left: 147px;
        }

        &:nth-child(4) {
          top: 146px;
          left: 205px;
        }

        &:nth-child(5) {
          top: 78px;
          left: 35px;
        }

        &:nth-child(6) {
          top: 110px;
          left: 92px;
        }

        &:nth-child(7) {
          top: 143px;
          left: 147px;
        }

        &:nth-child(8) {
          top: 176px;
          left: 205px;
        }

        &:nth-child(9) {
          top: 122px;
          left: 35px;
        }

        &:nth-child(10) {
          top: 154px;
          left: 92px;
        }

        &:nth-child(11) {
          top: 189px;
          left: 147px;
        }

        &:nth-child(12) {
          top: 220px;
          left: 205px;
        }

        &:nth-child(13) {
          top: 152px;
          left: 35px;
        }

        &:nth-child(14) {
          top: 184px;
          left: 92px;
        }

        &:nth-child(15) {
          top: 219px;
          left: 147px;
        }

        &:nth-child(16) {
          top: 250px;
          left: 205px;
        }
      }
    }
  }
}

#aeinfo {
  display: none;
  position: absolute;
  //top: 0%;
  left: 0%;
  font-size: 12px;
  padding-top: 10px;
  width: 150px;
  height: 150px;
  background-color: #bbe3df;
  z-index: 1001;
  opacity: 0.75;
}

#hfctinfo {
  display: none;
  position: absolute;
  font-size: 12px;
  //top: 0%;
  left: 20%;
  padding-top: 10px;
  width: 150px;
  height: 150px;
  background-color: #bbe3df;
  z-index: 1001;
  opacity: 0.75;
}

#tevinfo {
  display: none;
  position: absolute;
  font-size: 12px;
  //top: 0%;
  left: 40%;
  padding-top: 10px;
  width: 150px;
  height: 150px;
  background-color: #bbe3df;
  z-index: 1001;
  opacity: 0.75;
}

#uhfinfo {
  display: none;
  position: absolute;
  font-size: 12px;
  //top: 0%;
  left: 60%;
  padding-top: 10px;
  width: 150px;
  height: 150px;
  background-color: #bbe3df;
  z-index: 1001;
  opacity: 0.75;
}

//#light {
//  display: block;
//  position: absolute;
//  top: 25%;
//  left: 29%;
//  width: 40%;
//  height: 50%;
//  padding: 20px;
//  border: 1px solid #111;
//  background-color:#fff;
//  z-index:1002;
//  overflow: auto;
//  background-size:100%;
//}
</style>
