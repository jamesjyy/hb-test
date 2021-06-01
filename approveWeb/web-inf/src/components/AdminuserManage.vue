<template>
  <div class="AdminuserManage">
    <el-table
      :data="tableData"
      border
      style="width: 80%">
      <el-table-column prop="id" label="ID" width="150" align="center"></el-table-column>
      <el-table-column prop="name" label="用户名" width="150" align="center"></el-table-column>
      <el-table-column prop="companyName" label="公司名称" width="150" align="center"></el-table-column>
      <el-table-column prop="address" label="公司地址" width="150" align="center"></el-table-column>
      <el-table-column prop="phone" label="联系电话" width="150" align="center"></el-table-column>
      <el-table-column label="操作" width="240" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" icon="el-icon-check" @click="addclick()">添加</el-button>
          <el-button type="text" size="small" icon="el-icon-edit" @click="updteclick(scope.row)">修改</el-button>
          <el-button type="text" icon="el-icon-delete" @click="deleteclick()">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page.sync="currentPage"
                     :page-sizes="[100, 200, 300, 400]" :page-size="pageSize" layout="total, prev, pager, next,jumper"
                     :total="pageCount">
      </el-pagination>
    </div>
    <el-dialog class="abow_dialog"
               :title="dialogTitle"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               width="80%"
               height="60%">
      <!--      <div class="adduserform">-->
      <el-form :model="adduserform" ref="adduserform">
        <div class="adduserform">
          <el-form-item label="用户名称:" prop="name" style="margin-right: 300px" :label-width="formLabelWidth"
                        align="center">
            <el-input v-model="adduserform.name" placeholder="请输入内容" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户密码:" prop="password" style="margin-right: 300px" :label-width="formLabelWidth">
            <el-input v-model="adduserform.password" placeholder="请输入内容" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户模块:" prop="module" style="margin-right: 300px" :label-width="formLabelWidth">
            <el-select v-model="adduserform.module" placeholder="请选择用户模块">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="公司名称:" prop="companyName" style="margin-right: 300px" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="adduserform.companyName"></el-input>
          </el-form-item>

          <el-form-item label="公司电话:" prop="phone" style="margin-right: 300px" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="adduserform.phone"></el-input>
          </el-form-item>
          <el-form-item label="公司地址:" prop="address" style="margin-right: 300px" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="adduserform.address"></el-input>
          </el-form-item>
        </div>
      </el-form>
      <!--      </div>-->
      <div slot="footer" class="buttonstyle">
        <el-button style="margin-right: 10px" @click="resetForm('adduserform')">取 消</el-button>
        <el-button type="primary" @click="submitForm('adduserform')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {request} from '../../src/network/request.js';

export default {
  data() {
    return {
      // 分页
      pageNum: 1,
      currentPage: 1,
      pageCount: 1000,
      pageSize: 10,
      viewform: '',
      formLabelWidth: '25%',
      dialogVisible: false,
      floorRange: [],
      dialogTitle: '',
      iframeUrl: '',
      displayOptions: [],
      tableData: [],
      options: [],
      useroptions: [],
      adduserform: {
        // id,
        name: '',
        password: '',
        companyName: '',
        phone: '',
        module: '',
        address: ''
      },
    }
  },
  methods: {
    resetForm(adduserform) {
      this.$refs['adduserform'].resetFields();
      // this.tableData = [];
      this.dialogVisible = false;
    },
    handleSelectionChange(val) {
      // this.upditionalSelection = val;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.pageNum = val;
    },
    display(value) {

      // console.log(this.tableData[0].result)
      // var a =5; var b=555;
      // this.displayOptions.push({value: a, label: "第五片的值 "+b+""})
    },
    handleClick(scope) {
      console.log(scope);
    },
    updteclick(adduserform) {
      this.adduserform = adduserform,
        this.dialogTitle = '修改用户信息',
        this.dialogVisible = true;
    },
    addclick() {
      this.dialogTitle = '添加子用户'
      this.dialogVisible = true
    },
    deleteclick(row) {
      console.log(row)
    },
    submitForm() {
      this.user = new FormData();
      this.user.append("name", this.adduserform.name);
      this.user.append("password", this.adduserform.password);
      this.user.append("companyName", this.adduserform.companyName);
      this.user.append("phone", this.adduserform.phone);
      this.user.append("module", this.adduserform.module);
      this.user.append("address", this.adduserform.address);
      // this.useroptions.push(this.adduserform.name, this.adduserform.password, this.adduserform.companyName,
      //   this.adduserform.phone, this.adduserform.module, this.adduserform.address)
      // console.log(this.useroptions)
      // this.user.append("user", this.useroptions);
      request({
        method: 'post',
        url: 'user/insertUserinfo',
        data: this.user,
      }).then(res => {
        // console.log(res.data.list)
        // this.tableData = res.data.list
        // this.currentPage = res.data.list.size;
        // this.pageCount = res.data.list.size;
        // for(var i=1;i<res.data.list.size;i++){
        //   var b=res.data.list[i].result
        //   this.displayOptions.push({value: i, label: "第五片的值 "+b+""})
        // }
      })
    },
    initData() {
      request({
        method: 'post',
        url: 'user/views',
        // params: {
        //   upFileId: upFileId
        // }
      }).then(res => {
        console.log(res.data.list)
        this.tableData = res.data.list
        this.currentPage = res.data.list.size;
        this.pageCount = res.data.list.size;
      })
    }
  },
  created() {
    // this.initData();
  }
}
</script>
<style>
.AdminuserManage {
  margin-left: 80px;
  margin-top: 50px;
}

.buttonstyle {
  text-align: center;
}

/*.adduserform{*/
/*  margin-right: 80px;*/
/*}*/
</style>
