<template>
    <div>
        <div class="breadcrumbClass">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item v-if="power >= 1">
                    <a v-if="power == 1 || rolePower == 2 || rolePower == 3">公司列表</a>
                    <a v-else @click="firstOpen()">公司列表</a>
                </el-breadcrumb-item>
                <el-breadcrumb-item v-if="power >= 2">
                    <a v-if="power == 2 || rolePower == 3">区域列表</a>
                    <a v-else @click="secondOpen()">区域列表</a>
                </el-breadcrumb-item>
                <el-breadcrumb-item v-if="power >= 3">
                    <a v-if="power == 3">详情列表</a>
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-card class="box-card" v-if="power == 1">
            <el-card class="innerCard">
                <el-row>
                    <el-col :span="6" class="colClass">
                        <span>公司名称</span>
                        <el-input
                            placeholder="请输入公司名称"
                            v-model="paramsList.company"
                            clearable
                            @change="searchFirstData"
                        >
                        </el-input>
                    </el-col>
                </el-row>
            </el-card>

            <el-table :data="firstTable" style="width: 100%" stripe v-loading="firstLoading">
                <el-table-column prop="companyName" align="center" label="公司名称"></el-table-column>
                <el-table-column prop="count" align="center" label="线路数量"></el-table-column>
                <el-table-column prop="abnormity" align="center" label="异常"></el-table-column>
                <el-table-column prop="operation" align="center" label="操作" width="100px">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="secondOpen(scope.row.companyName)"
                            >查看</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>

            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="this.totalCount"
            >
            </el-pagination>
        </el-card>

        <el-card class="box-card" v-else-if="power == 2">
            <el-card class="innerCard">
                <el-row>
                    <el-col :span="6" class="colClass">
                        <span>区域名称：</span>
                        <el-input
                            placeholder="请输入区域名称"
                            v-model="paramsList.area"
                            clearable
                            @change="searchSecondData"
                        >
                        </el-input>
                    </el-col>
                </el-row>
            </el-card>

            <el-table :data="secondTable" style="width: 100%" stripe v-loading="secondLoading">
                <el-table-column prop="area" align="center" label="区域名称"></el-table-column>
                <el-table-column prop="abnormity" align="center" label="异常"></el-table-column>
                <el-table-column prop="danger" align="center" label="危险"></el-table-column>
                <el-table-column prop="operation" align="center" label="操作" width="100px">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="thirdlyOpen(scope.row.area)">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="this.totalCount"
            >
            </el-pagination>
        </el-card>

        <el-card class="box-card" v-else-if="power == 3">
            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane name="allList">
                    <span slot="label" class="tabFont">全部</span
                    ><jf-break-down :index="this.index" :area="this.area" v-if="isAll"></jf-break-down
                ></el-tab-pane>
                <el-tab-pane name="falseList">
                    <span slot="label" class="tabFont">未处理</span
                    ><jf-break-down :index="this.index" :area="this.area" v-if="isFalse"></jf-break-down
                ></el-tab-pane>
                <el-tab-pane name="trueList">
                    <span slot="label" class="tabFont">已处理</span
                    ><jf-break-down :index="this.index" :area="this.area" v-if="isTrue"></jf-break-down
                ></el-tab-pane>
            </el-tabs>
        </el-card>
    </div>
</template>

<script>
import { request } from '../../src/network/request.js'
import JfBreakDown from './JfBreakDown.vue'
export default {
  components: { JfBreakDown },
  data () {
    return {
      activeName: 'allList',
      index: 0,
      power: null,
      rolePower: null,
      isAll: true,
      isFalse: false,
      isTrue: false,
      companyName: null,
      area: null,
      firstLoading: false,
      secondLoading: false,
      // 每页显示数
      pageSize: 0,
      // 当前页码
      currentPage: 1,
      // 默认数据总数
      totalCount: 0,
      paramsList: {
        area: null,
        company: null
      },
      firstTable: [],
      secondTable: [],
      tableData: [
        {
          time: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          time: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        },
        {
          time: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        },
        {
          time: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }
      ]
    }
  },
  methods: {
    handleClick (tab, event) {
      this.index = tab.index
      if (this.index === 0) {
        this.isAll = true
        this.isFalse = false
        this.isTrue = false
      } else if (this.index === 1) {
        this.isAll = false
        this.isFalse = true
        this.isTrue = false
      } else {
        this.isAll = false
        this.isFalse = false
        this.isTrue = true
      }
    },

    firstOpen () {
      this.power = 1
    },

    secondOpen (companyName) {
      this.power = 2
      if (companyName != null && companyName !== '') {
        this.companyName = companyName
      }
    },

    thirdlyOpen (area) {
      this.power = 3
      if (area != null && area !== '') {
        this.area = area
      }
    },

    // 一级列表查询
    searchFirstData () {
      this.firstLoading = true
      request({
        method: 'get',
        url: 'jfDatainfo/selectCompanyName',
        params: {
          companyName: this.paramsList.company,
          pageSize: this.pageSize,
          currentPage: this.currentPage
        }
      }).then(res => {
        this.totalCount = res.data.totalCount
        this.firstTable = res.data.data
        this.firstLoading = false
      })
    },

    // 二级列表查询
    searchSecondData () {
      this.secondLoading = true
      request({
        method: 'get',
        url: 'jfDatainfo/selectAreaName',
        params: {
          companyName: this.companyName,
          area: this.paramsList.area,
          pageSize: this.pageSize,
          currentPage: this.currentPage
        }
      }).then(res => {
        this.totalCount = res.data.totalCount
        this.secondTable = res.data.data
        this.secondLoading = false
      })
    },

    // 每页显示数据量变更
    handleSizeChange (val) {
      this.pageSize = val
      if (this.power === 1) {
        this.searchFirstData()
      } else if (this.power === 2) {
        this.searchSecondData()
      }
    },

    // 页码变更
    handleCurrentChange (val) {
      this.currentPage = val
      if (this.power === 1) {
        this.companyName = null
        this.searchFirstData()
      } else if (this.power === 2) {
        this.searchSecondData()
      }
    },

    // 初始化
    initData () {
      this.firstLoading = false
      this.secondLoading = false
      this.pageSize = 0
      this.currentPage = 1
      this.totalCount = 0
      this.area = null
      this.paramsList = {
        area: null,
        company: null
      }
      this.firstTable = []
      this.secondTable = []
    },

    checkUserRole () {
      var userRoles = []
      userRoles = localStorage.getItem('rolelist').split(',')
      if (userRoles.indexOf('1') !== -1) {
        this.power = 1
        this.rolePower = 1
      } else if (userRoles.indexOf('2') !== -1) {
        var jsonData = JSON.parse(localStorage.getItem('user'))
        this.companyName = jsonData.companyName
        this.power = 2
        this.rolePower = 2
      } else {
        this.power = 3
        this.rolePower = 3
      }
    }
  },

  created () {
    this.checkUserRole()
  },

  watch: {
    power (newValue, oldValue) {
      if (newValue === 1) {
        this.initData()
        this.handleSizeChange(5)
      } else if (newValue === 2) {
        this.initData()
        this.handleSizeChange(5)
      }
    }
  }
}
</script>
<style>
.box-card {
    margin: 10px 20px 20px 20px;
}

.innerCard {
    margin: 10px;
}

.tabFont {
    font-size: 16px;
}

.el-input {
    width: 200px;
}

.colClass {
    margin: 10px 0;
}

.breadcrumbClass {
    margin: 10px 0 0 20px;
}
</style>
