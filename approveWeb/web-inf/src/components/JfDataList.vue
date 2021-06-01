<template>
  <el-card class="box-card">
    <el-card class="innerCard">
      <el-row>
        <el-col :span="6">
          <span>设备种类：</span>
          <el-select v-model="paramsList.kind" clearable placeholder="设备种类" @change="searchData">
            <el-option v-for="item in kindOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="6">
          <span>设备状态：</span>
          <el-select v-model="paramsList.state" clearable placeholder="请选择" @change="searchData">
            <el-option v-for="item in stateOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="6">
          <span>线路名称：</span>
          <el-input placeholder="请输入内容" v-model="paramsList.lineNameInput" clearable @change="searchData">
          </el-input>
        </el-col>
        <el-col :span="6">
          <span>线路编号：</span>
          <el-input placeholder="请输入内容" v-model="paramsList.lineNoInput" clearable @change="searchData">
          </el-input>
        </el-col>
        <el-col :span="6">
          <span>缺陷程度：</span>
          <el-input placeholder="请输入内容" v-model="paramsList.flawInput" clearable @change="searchData">
          </el-input>
        </el-col>
        <el-col :span="12">
          <span>检测时间：</span>
          <el-date-picker v-model="paramsList.dateTimeInput" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="searchData">
          </el-date-picker>
        </el-col>
      </el-row>
    </el-card>
    <el-table :data="tableData" style="width: 100%" stripe height="555px">
      <el-table-column prop="lineName" align="center" label="线路名称"></el-table-column>
      <el-table-column prop="lineNo" align="center" label="线路编号"></el-table-column>
      <el-table-column prop="dBValue" align="center" label="平均dB值">
        <template slot-scope="scope">
          <el-tag type="success" effect="dark" v-if="scope.row.dBValue < 11">{{scope.row.dBValue}}dB</el-tag>
          <el-tag type="warning" effect="dark" v-else-if="scope.row.dBValue < 20">{{scope.row.dBValue}}dB</el-tag>
          <el-tag type="danger" effect="dark" v-else>{{scope.row.dBValue}}dB</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="flaw" align="center" label="缺陷程度">
        <template slot-scope="scope">
          <el-tag type="success" effect="dark" v-if="scope.row.dBValue < 11">正常</el-tag>
          <el-tag type="warning" effect="dark" v-else-if="scope.row.dBValue < 20">异常</el-tag>
          <el-tag type="danger" effect="dark" v-else>危险</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="dateTime" align="center" label="检测时间"></el-table-column>
      <el-table-column prop="operation" align="center" label="操作" width="290px">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">分析</el-button>
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">报告</el-button>
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[5, 10, 15, 20]" :page-size="5" layout="total, sizes, prev, pager, next, jumper" :total="this.tableData.length">
    </el-pagination>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1,
      paramsList: {
        kind: '',
        state: '',
        lineNameInput: '',
        lineNoInput: '',
        flawInput: '',
        dateTimeInput: []
      },
      kindOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      stateOptions: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      tableData: [
        {
          lineName: '陇海线',
          lineNo: 'A160',
          dateTime: '2016-05-02 15:50:29',
          dBValue: 11,
        },
        {
          lineName: '京广线',
          lineNo: 'A350',
          dateTime: '2016-05-04 15:50:29',
          dBValue: 20,
        },
        {
          lineName: '连霍线',
          lineNo: 'A113',
          dateTime: '2016-05-01 15:50:29',
          dBValue: 15,
        },
        {
          lineName: '斑马线',
          lineNo: 'C603',
          dateTime: '2016-05-03 15:50:29',
          dBValue: 7,
        },
        {
          lineName: '斑马线',
          lineNo: 'C603',
          dateTime: '2016-05-03 15:50:29',
          dBValue: 7,
        },
        {
          lineName: '斑马线',
          lineNo: 'C603',
          dateTime: '2016-05-03 15:50:29',
          dBValue: 7,
        },
        {
          lineName: '斑马线',
          lineNo: 'C603',
          dateTime: '2016-05-03 15:50:29',
          dBValue: 7,
        },
        {
          lineName: '斑马线',
          lineNo: 'C603',
          dateTime: '2016-05-03 15:50:29',
          dBValue: 7,
        },
        {
          lineName: '斑马线',
          lineNo: 'C603',
          dateTime: '2016-05-03 15:50:29',
          dBValue: 7,
        },
        {
          lineName: '斑马线',
          lineNo: 'C603',
          dateTime: '2016-05-03 15:50:29',
          dBValue: 7,
        },
      ],
    }
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },

    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },

    initData() {
      this.paramsList = {
        kind: '',
        state: '',
        lineNameInput: '',
        lineNoInput: '',
        flawInput: '',
        dateTimeInput: []
      }
    },

    searchData() {
      if (this.paramsList.kind != '' ||
        this.paramsList.state != '' ||
        this.paramsList.lineNameInput != '' ||
        this.paramsList.lineNoInput != '' ||
        this.paramsList.flawInput != '' ||
        this.paramsList.dateTimeInput != null) {
        console.log(`有值`);
      } else {
        console.log(`没值`);
      }
    }

    // tableRowStyle({ row, rowIndex }) {
    //   let rowStyle = {};
    //   if (10 <= row.dBValue && row.dBValue < 20) {
    //     rowStyle.background = 'rgba(255, 255, 0, 0.4)';
    //     rowStyle.color = '#000000';
    //     return rowStyle;
    //   } else if (20 <= row.dBValue) {
    //     rowStyle.background = 'rgba(212, 0, 0, 0.6)';
    //     rowStyle.color = '#000000';
    //     return rowStyle;
    //   } else {
    //     return '';
    //   }
    // }
  },
  created() {
    this.initData();
  },
}
</script>
<style>
.box-card {
  margin: 20px;
}

.innerCard {
  margin: 10px;
}

.el-select {
  width: 200px;
}

[class*='el-col-'] {
  margin: 10px 0;
}

.el-input {
  width: 200px;
}

.el-pagination {
  margin-top: 20px;
}

.el-tag {
  width: 65px;
}

.el-tag--dark.el-tag--warning {
  background-color: rgba(255, 149, 0, 1);
  border-color: rgba(255, 149, 0, 1);
}

.el-tag--dark.el-tag--danger {
  background-color: rgba(255, 0, 0, 1);
  border-color: rgba(255, 0, 0, 1);
}
</style>
