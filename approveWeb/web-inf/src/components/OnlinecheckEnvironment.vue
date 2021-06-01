<template>
  <div class="companylist">
    <el-row :span="24">
      <el-col :span="1.5" class="towernums">
        <div class="ganta">环境设备:</div>
      </el-col>
      <el-col :span="3">
        <template>
          <el-select v-model="values" placeholder="请选择设备号" @change="selectChangenumber">
            <el-option
              v-for="item in environmentnumbers"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </template>
      </el-col>
      <el-col :span="1" class="towernums">
        <div class="ganta">设备:</div>
      </el-col>
      <el-col :span="3" style="margin-right: 50px">
        <template>
          <el-select v-model="value" placeholder="请选择设备号" @change="selectChanged">
            <el-option
              v-for="item in optionnumbers"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </template>
      </el-col>
      <el-col :span="1.5" class="loopnames">
        <div>设备ID:</div>
      </el-col>
      <el-col :span="3">
        <el-input placeholder="请输入设备ID查询" v-model="searchData.sensorid"></el-input>
      </el-col>
      <el-col :span="1.5" class="loopnames">
        <div>状态代码</div>
      </el-col>
      <el-col :span="3">
        <el-input placeholder="请输入状态代码查询" v-model="searchData.statuscode"></el-input>
      </el-col>
    </el-row>
    <el-row :span="24">
      <el-col :span="1" class="checkDate">
        <span>日期:</span>
      </el-col>
      <el-col :span="4">
        <el-date-picker
          v-model="searchData.ts"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right"
          value-format="yyyy-MM-dd"
        ></el-date-picker>
      </el-col>
      <el-col :span="10" style="text-align: center">
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      </el-col>
    </el-row>
    <div class="tableth">
      <el-table :data="tableDatathem" border style="width: 80%" v-if="tablethumflg" :cell-style="rowStyle">
        <el-table-column sortable type="index" label="序号" width="150" align="center"></el-table-column>
        <el-table-column sortable prop="ts" label="检测日期" width="220" align="center"></el-table-column>
        <el-table-column sortable prop="sensorid" label="传感器ID" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="position" label="传感器位置" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="battery" label="传感器电量" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="temperature" label="传感器温度" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="humidity" label="传感器湿度" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="statuscode" label="状态代码" width="162" align="center"></el-table-column>
        <el-table-column sortable prop="statusdesc" label="烟雾状态说明" width="162" align="center"></el-table-column>
        <el-table-column sortable prop="ct" label="采集时间" :formatter="formatterDate" width="220"
                         align="center"></el-table-column>
      </el-table>
    </div>
    <div class="tableth">
      <el-table :data="tableData" border style="width: 80%" v-if="tableflg" :cell-style="rowStyles">
        <el-table-column sortable type="index" label="序号" width="150" align="center"></el-table-column>
        <el-table-column sortable prop="ts" label="检测日期" width="220" align="center"></el-table-column>
        <el-table-column sortable prop="sensorid" label="传感器ID" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="position" label="传感器位置" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="battery" label="传感器电量" width="130" align="center"></el-table-column>
        <el-table-column sortable prop="statuscode" label="状态代码" width="162" align="center"></el-table-column>
        <el-table-column sortable prop="statusdesc" label="烟雾状态说明" width="162" align="center"></el-table-column>
        <el-table-column sortable prop="ct" label="采集时间" :formatter="formatterDate" width="220"
                         align="center"></el-table-column>
      </el-table>
    </div>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
var padDate = function (value) {
  return value < 10 ? '0' + value : value;
}
import {request} from '@/network/request.js'
import {initData} from '@/network/OnlinecheckEnvironment/OnlinecheckEnvironment'
import Qs from 'qs'

export default {
  data() {
    return {
      // 分页
      pageNum: 1,
      pageCount: 1000,
      pageSize: 0,
      d: "",

      optionnumbers: [{
        value: 'tb_sn20210107t0001',
        label: 'tb_sn20210107t0001'
      }, {
        value: 'tb_sn20210107tY000',
        label: 'tb_sn20210107tY000'
      }],
      value: "tb_sn20210107t0001",
      environmentnumbers: [{
        value: '1',
        label: '烟雾设备'
      }, {
        value: '2',
        label: '水浸设备'
      }, {
        value: '3',
        label: '门磁设备'
      }, {
        value: '4',
        label: '温湿度设备'
      }],
      values: "1",
      //当前页码
      currentPage: 1,
      //查询的页码
      start: 1,
      //默认数据总数
      totalCount: 0,
      fuhao: ";",
      //搜索条件
      searchData: {
        value: '',
        values: [],
        sensorid: '',
        statuscode: '',
        ts: []
      },
      shebeinumber: "",
      JSONBody: "",
      tableData: [],
      tableDatathem: [],
      tableflg: true,
      tablethumflg: false,
      jsonformname: "select * from ",
      jsondbname: "",
      searchlimit: " ORDER BY ts DESC LIMIT 0,1000",
      searchsensorid: '',
      searchstatuscode: '',
      searchtsone: '',
      searchtstwo: '',
      searchtsData: '',
    }
  },
  methods: {

    selectChanged(value) {
      this.shebeinumber = value
    },
    selectChangenumber(value) {
      if (value == 1) {
        this.jsondbname = "db_smoke."
      }
      if (value == 2) {
        this.jsondbname = "db_water."
      }
      if (value == 3) {
        this.jsondbname = "db_door."
      }
      if (value == 4) {
        this.jsondbname = "db_temhum."
        // this.tableflg=false
        // this.tablethumflg=true
      }
    },
    handleSelectionChange(val) {
      // this.upditionalSelection = val;
    },
    //每页显示数据量变更
    handleSizeChange(val) {
      this.pagesize = val
      this.search()
    },
    //页码变更
    handleCurrentChange(val) {
      this.currentPage = val
      this.search()
    },
    display(scope, i) {
      let obj = scope.row
      obj.selected = obj.result[i].join(':')
      this.$set(this.tableData, scope.$index, obj)
    },
    rowStyle({row, column, rowIndex, columnIndex}) {
      if (columnIndex == 7 && row.statuscode!== 0) { //指定坐标rowIndex ：行，columnIndex ：列
        return 'color:red' //rgb(105,0,7)
      } else {
        return ''
      }
    },
    rowStyles({row, column, rowIndex, columnIndex}) {
      if (columnIndex == 5 && row.statuscode!== 0) { //指定坐标rowIndex ：行，columnIndex ：列
        return 'color:red' //rgb(105,0,7)
      } else {
        return ''
      }
    },
    formatterDate: function (row) {
      var date = new Date(parseInt(row.ct) * 1000);
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
      return y + "年" + m + "月" + d + "日 " + h + ":" + minute + ":" + second;
    },
    //查询
    search: function () {
      if (this.jsondbname == "" || this.jsondbname == null) {
        this.jsondbname = "db_smoke."
      }
      if (this.shebeinumber == "" || this.shebeinumber == null) {
        this.shebeinumber = "tb_sn20210107t0001"
      }
      if (!this.searchData.sensorid == "" || !this.searchData.sensorid == null) {
        this.searchsensorid = " where sensorid LIKE " + "'%" + this.searchData.sensorid + "%'"
      }
      if (!this.searchData.statuscode == "" || !this.searchData.statuscode == null) {
        if (this.searchsensorid == "" || this.searchsensorid == null) {
          this.searchstatuscode = " where statuscode =" + this.searchData.statuscode
        } else {
          this.searchstatuscode = " and statuscode =" + this.searchData.statuscode
        }
      }
      if (this.searchData.ts == null || this.searchData.ts == "") {
        this.searchtsData = ""
      } else {
        if (!this.searchData.ts.length == 0) {
          this.searchtsone = "'" + this.searchData.ts[0] + " 00:00:00.000'"
          this.searchtstwo = "'" + this.searchData.ts[1] + " 23:59:59.999'"
          if (this.searchsensorid == "" && this.searchstatuscode == "") {
            this.searchtsData = " where ts >=" + this.searchtsone + " and ts <=" + this.searchtstwo
          } else {
            this.searchtsData = " and ts >= " + this.searchtsone + " and ts <=" + this.searchtstwo
          }
        }
      }
      if(this.jsondbname=="db_temhum."){
        this.tableflg=false
        this.tablethumflg=true
      }else{
        this.tableflg=true
        this.tablethumflg=false
      }
      if (this.searchsensorid== "" && this.searchstatuscode == "" && this.searchtsData == "") {
        this.JSONBody = this.jsonformname + this.jsondbname + this.shebeinumber + this.searchlimit + this.fuhao
      } else {
        this.JSONBody = this.jsonformname + this.jsondbname + this.shebeinumber + this.searchsensorid + this.searchstatuscode + this.searchtsData + this.searchlimit + this.fuhao
      }
      this.initData(
        {
          url: "http://36.133.20.49:8001",
          JSONBody: this.JSONBody,
        })
      this.jsondbname = ""
      this.shebeinumber = ""
      this.searchsensorid = ""
      this.searchstatuscode = ""
      this.searchtsData = ""
      // this.tableflg=true
      // this.tablethumflg=false
    },
    initData: function (data) {
      const _this = this
      this.tableData = []
      this.tableDatathem = []
      initData(Qs.stringify(data)).then((res) => {
        let arr = JSON.parse(res.data.data.data).data;
        if (arr == "" || arr == null) {
          this.tableData = arr
        } else {
          let arr1 = arr.map(item => ({
            ts: item[0],
            sensorid: item[1],
            position: item[2],
            battery: item[3],
            statuscode: item[4],
            statusdesc: item[5],
            ct: item[6]
          }))
          let arr2 = arr.map(item => ({
            ts: item[0],
            sensorid: item[1],
            position: item[2],
            battery: item[3],
            temperature: item[4],
            humidity: item[5],
            statuscode: item[6],
            statusdesc: item[7],
            ct: item[8]
          }))
          this.tableData = arr1
          this.tableDatathem = arr2
          this.pageSize = 10
          this.totalCount = arr1.length
        }
      })
    }
  },
  created() {
    this.handleSizeChange(10)
  },
}
</script>
<style>
.companylist {
  margin-left: 80px;
  margin-top: 50px;
}

.block {
  margin-top: 20px;
}

.towernums {
  /*margin-right: 30px;*/
  text-align: right;
  padding: 9px;
}

.checkDate {
  text-align: right;
  padding: 9px;
}

.loopnames {
  text-align: right;
  padding: 9px;
}

.tableth {
  margin-top: 30px;
}

.ganta {
  /*margin-right: 65px;*/
}

.hbh2 {
  align-content: center;

  margin-left: 350px;
}

.hbh3 {
  margin-left: 50px;
}

.entityone {
  background: red;
}

.el-row-row {
  text-align: center;
  font-weight: bold;
  font-size: 25px;
}

.reports {
  margin-left: 100px;
}

.el-dialog {
  display: flex;
  flex-direction: column;
  margin: 0 !important;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-height: calc(100% - 20px);
  max-width: calc(100% - 30px);
}

.el-dialog .el-dialog__body {
  flex: 1;
  overflow: auto;
}

.buttonto {
  margin-left: 1120px;
  /*margin-bottom: 50px;*/
}
</style>
