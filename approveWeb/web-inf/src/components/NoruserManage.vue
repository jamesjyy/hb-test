<template>
  <div class="NoruserManage">
    <el-row :span="24">
      <el-col :span="1.5" class="towernums">
        <div class="ganta">用户名:</div>
      </el-col>
      <el-col :span="1.5" style="margin-right: 50px">
        <el-input placeholder="请输入用户名查询" v-model="searchData.username"></el-input>
      </el-col>

      <el-col :span="2" class="loopnames">
        <div>公司名称:</div>
      </el-col>
      <el-col :span="2.2">
        <el-input placeholder="请输入公司名称查询" v-model="searchData.companyname"></el-input>
      </el-col>
      <el-col :span="2" style="text-align: center">
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      </el-col>

      <el-col :span="2" style="text-align: center">
        <el-button type="primary" icon="el-icon-circle-plus-outline"  @click="addclick">添加</el-button>
      </el-col>
    </el-row>
    <el-table
      :data="tableData"
      border
      style="width: 84%">
      <el-table-column prop="id" label="序号" width="200" align="center" type="index"></el-table-column>
      <el-table-column prop="name" label="用户名" width="200" align="center"></el-table-column>
      <el-table-column prop="companyName" label="公司名称" width="200" align="center"></el-table-column>
      <el-table-column prop="address" label="公司地址" width="200" align="center"></el-table-column>
      <el-table-column prop="options" label="设备种类使用"  width="200" align="center">
        <template slot-scope="scope">
          <el-select
            size="mini"
            align="center"
            v-model="scope.row.selected"
            placeholder="设备购买详情"
            @focus="findbycompany(scope.row)"
          >
            <el-option
              v-for="(item,index) in options"
              :key="index"
              :label="item.appid"
              :value="item.appid">
            </el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="400" align="center">
        <template slot-scope="scope">
          <el-button type="info" size="mini" icon="el-icon-info" @click="doPermisson(scope.row)"></el-button>
<!--          <el-button type="danger" size="mini" icon="el-icon-add" @click="addclick(scope.row)">添加</el-button>-->
          <el-button type="primary" size="mini" icon="el-icon-edit"  @click="updateclick(scope.row)" >修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="open(scope.row)">删除</el-button>
          <el-button type="text" size="small" @click="viewclick(scope.row)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5,10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount">
      </el-pagination>
    </div>
    <el-dialog class="abow_dialog"
               :title="dialogTitle"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               width="80%"
               height="60%">
      <el-form :model="adduserform" ref="adduserform":rules="validateRules" @close="resetForms('adduserform')">
        <el-form-item label="用户名称:" prop="name" :label-width="formLabelWidth">
          <el-input v-model="adduserform.name" placeholder="请输入内容" autocomplete="off" @blur="selectbyname('adduserform')"></el-input>
        </el-form-item>
        <el-form-item label="用户密码:" prop="password" :label-width="formLabelWidth">
          <el-input v-model="adduserform.password" placeholder="请输入内容" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="公司名称:" prop="companyname" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="adduserform.companyname" @blur="selectbycompany('adduserform')"></el-input>
        </el-form-item>
        <el-form-item label="公司电话:" prop="phone" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="adduserform.phone"></el-input>
        </el-form-item>
        <el-form-item label="公司地址:" prop="address" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="adduserform.address"></el-input>
        </el-form-item>
        <el-form-item
          type="textarea"
          :label-width="formLabelWidth"
          v-for="(domain, index) in adduserform.domains"
          :label="'设备号:' + index"
          :key="domain.key"
          :prop="'domains.' + index + '.value'"
          :rules="{
            required: true, message: '设备号不能为空', trigger: 'blur'
          }"
        >
          <el-input v-model="domain.value"></el-input><el-button @click.prevent="removeDomain(index)" v-if="index!=0">删除</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDomain('adduserform')">新增设备</el-button>
        <el-button @click="resetForm('adduserform')">取 消</el-button>
        <el-button type="primary" @click="submitForm('adduserform')">确 定</el-button>
      </div>
    </el-dialog>





      <el-dialog class="abow_dialogs"
                 :title="dialogTitle"
                 :visible.sync="dialogVisibles"
                 :close-on-click-modal="false"
                 width="80%"
                 height="60%">
        <el-form :model="updateuserform" ref="updateuserform":rules="validateRules">
          <el-form-item label="用户名称:" prop="name" :label-width="formLabelWidth">
            <el-input v-model="updateuserform.name" placeholder="请输入内容" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户密码:" prop="password" :label-width="formLabelWidth">
            <el-input v-model="updateuserform.password" placeholder="请输入内容" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="公司名称:" prop="companyname" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="updateuserform.companyname"></el-input>
          </el-form-item>
          <el-form-item label="公司电话:" prop="phone" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="updateuserform.phone" ></el-input>
          </el-form-item>
          <el-form-item label="公司地址:" prop="address" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="updateuserform.address"></el-input>
          </el-form-item>
          <el-form-item
            type="textarea"
            :label-width="formLabelWidth"
            v-for="(domain, index) in optionsb"
            :label="'设备号:' + index"
            :key="domain.key"
            :prop="domain.appid"

          >
            <el-input v-model="domain.appid"></el-input><el-button @click.prevent="removeDomains(index)" v-if="index!=0">删除</el-button>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="updateDomain('updateuserform')">新增设备</el-button>
          <el-button @click="resetForm('updateuserform')">取 消</el-button>
          <el-button type="primary" @click="updateuserforms('updateuserform')">确 定</el-button>
        </div>
      </el-dialog>
  </div>

</template>

<script>
import {request} from '../../src/network/request.js';
import Qs from "qs";

const validatePass = (rule, value, callback) => {
  if (value.length < 6) {
    callback(new Error('密码不能小于6位'))
  } else {
    callback()
  }
}
export default {
  data() {
    return {
      options: [{
        value: '电压设备:',
        label: '电压设备:100'
      }, {
        value: '电阻设备:',
        label: '电阻设备:100'
      }, {
        value: '局防设备:',
        label: '局防设备:100'
      }],

      optionsb:[],
      // 分页
      pageNum: 1,
      currentPage: 1,
      pageCount: 1000,
      pageSize: 10,

      validateRules: {
        name: [{ required: true, trigger: 'blur', message: '用户名必须输入' }],
        // password: [{ required: true, trigger: 'blur', validator: validatePass }],
        companyname: [{ required: true, trigger: 'blur', message: '公司名称必须输入' }],
        phone: [{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误', trigger: 'blur' }],
        address: [{ required: true, trigger: 'blur', message: '公司地址必须输入' }],
      },

      //默认数据总数
      totalCount: 0,
      viewform: '',
      formLabelWidth: '25%',
      viewsformVisible: false,
      dialogVisible: false,
      dialogVisibles: false,
      floorRange: [],
      dialogTitle: '',
      iframeUrl: '',
      displayOptions: [],
      tableData: [],
      adduserform: {
        name: '',
        password: '',
        companyname: '',
        phone: '',
        address: '',
        domains: [{
          value: ''
        }],
      },


      //搜索条件
      searchData: {
        username: '',
        companyname: '',
        searchDate: [],
      },
      updateuserform: {
        name: '',
        password: '',
        companyname: '',
        phone: '',
        address: '',
        id:'',
        domains: [{
          value: ''
        }],
        optionsb:[{value:''}]
      },
    }
  },
  methods: {

    addDomain() {
      this.adduserform.domains.push({
        value: '',
        key: Date.now()
      });
    },

    removeDomain(index) {
      let list = this.adduserform.domains;

      list.splice(index, 1)
      this.$set(this.adduserform,'domains',list)
    },



    updateDomain() {
      let optionsb = this.optionsb;
      console.log("aaaaaa"+optionsb)
      optionsb.push({
        value: '',
        key: Date.now()
      });
      this.$set(this.updateuserform,'optionsb',optionsb)
    },

    removeDomains(index) {
      let list = this.optionsb;

      list.splice(index, 1)
      this.$set(this.updateuserform,'optionsb',list)
    },

    selebyuserid(){
      this.file = new FormData();
      this.file.append("id",this.updateuserform.id)
      request({
        method: 'post',
        url: 'user/selebyuserid',
        // params: {
        //
        //
        // }
        data:this.file
      }).then(res => {
        this.optionsb = res.data.data.proinfo
       // console.log("我好煩啊啊啊:"+this.optionsb)

      })
    },





    doPermisson(row){
      //this.dialogVisibles=true;
      window.location.href = './userForm.html?'+row.id+"";

      //this.$router.push({path:'/roleForm.html',params:{roleid:row.id}});
    },

    resetForms(formName) {
      this.$refs[adduserform].resetFields();
    },
    selectbyname(){
      this.file = new FormData();
      this.file.append("username",this.adduserform.name)
      request({
        method: 'post',
        url: 'user/selectbyname',

        data:this.file
      }).then(res => {
       if(res.data.data.flag==true){
         this.$message.error("用户名不能重复！！！");
       }

      })
    },


    selectbycompany(){
      this.file = new FormData();
      this.file.append("companyname",this.adduserform.companyname)
      request({
        method: 'post',
        url: 'user/selectbycompany',

        data:this.file
      }).then(res => {
        if(res.data.data.flag==true){
          this.$message.error("公司名称不能重复！！！");
        }

      })
    },

    findbycompany(row){

      this.file = new FormData();
      this.file.append("companyname",row.companyName)
      request({
        method: 'post',
        url: 'user/findbycompany',
        data:this.file
      }).then(res => {
        this.options = res.data.data.proinfo
       // console.log("我好煩啊啊啊:"+this.options)


      })

    },

    open(row) {
      this.datas=new FormData()
      // console.log(row)
      this.datas.append("id",row.id)

      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          request({
            method: 'post',
            url: 'user/remove',
            data: this.datas
      }).then(res => {
            if (res.data.flg == false) {
              this.$message.error("删除失败，请重试");
            } else {
              this.$message({
                message: "删除成功",
                type: 'success'
              });
              this.initData()
              // this.handleSizeChange(10)
            }
          });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    initDatas: function (data) {
      const _this = this
      this.initDatas(Qs.stringify(data)).then(res => {
        _this.tableData = res.data.data.list;
        // this.scope.row.floorRange=1

        _this.totalCount = res.data.data.list.length;
        console.log(res.data.data.list.length)
        _this.loading = false
      });
    },


    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.fileList=[];
      this.dialogVisible = false;
      this.dialogVisibles = false;
    },
    handleSelectionChange(val) {
      // this.upditionalSelection = val;
    },
    //每页显示数据变量
    handleSizeChange(val) {
      this.pagesize = val;
      this.initData()

    },
    //页码变更
    handleCurrentChange(val) {
      this.currentPage = val;
      this.search()
    },

    //查询
    search: function () {
      let strsearchDate = ''
      if (this.searchData.searchDate != null) {
        strsearchDate = this.searchData.searchDate.join(',')
      }
      this.initData({
        name: this.searchData.username,
        searchDate: strsearchDate,
        companyName: this.searchData.companyname,
        pageNum: this.currentPage,
        pageSize: this.pagesize,
      })
    },
    display(scope, i) {
      let obj = scope.row
      obj.selected = obj.options[i].appid
      this.$set(this.options, scope.$index, obj)
    },
    handleClick(row) {
      // console.log(row);
    },
    viewclick(row) {
      //console.log("普通用户："+row.id)
      window.location.href = './CompanyManage.html?'+row.id+""
    },

      addclick(row){
        this.dialogVisible=true;
        this.adduserform.companyname=row.companyName
        this.adduserform.phone=row.phone
        this.adduserform.address=row.address

    },
    updateclick(row){

      this.dialogVisibles=true;
      this.updateuserform.name=row.name
     // this.updateuserform.password=row.password
      this.updateuserform.companyname=row.companyName
      this.updateuserform.phone=row.phone
      this.updateuserform.address=row.address
      this.updateuserform.id=row.id
      this.updateuserform.appid=row.appid
      this.file = new FormData();
      this.file.append("id",this.updateuserform.id)
      request({
        method: 'post',
        url: 'user/selebyuserid',
        data:this.file
      }).then(res => {
        this.optionsb = res.data.data.proinfo
        // console.log("我好煩啊啊啊:"+this.optionsb)

      })
    },
    deleteclick() {
      this.dialogVisible=true;
    },
    submitForm(adduserform){
      this.$refs.adduserform.validate(valid=>{
        console.log("校验扁担:"+valid)
      if (!valid)return

      this.file = new FormData();
      this.file.append("username",this.adduserform.name)
      this.file.append("password",this.adduserform.password)
      this.file.append("companyname",this.adduserform.companyname)
        if (this.adduserform.phone==null){
          this.file.append("phone","")
        }else {
          this.file.append("phone",this.adduserform.phone)
        }
      this.file.append("address",this.adduserform.address)
      let domains = '';
      let list = this.adduserform.domains;
      for(let i = 0;i< list.length;i++){
        if(i == 0){
          domains += list[0].value;
          console.log("domains:"+domains)
        }else{
          domains += ',' + list[i].value
        }
      }
      this.file.append("domains",domains)
      request({
        method: 'post',
        url: 'user/insertUserinfo',
         // params: {
         //
         //
         // }
        data:this.file
      }).then(res => {
        this.dialogVisible=false
        if(res.data.data.flag==true){
          this.$message.error("用户名不能重复！！！");
        }

        if(res.data.data.flags==true){
          this.$message.error("公司名称不能重复！！！");
        }
        if(res.data.data.flagss==true){
          this.$message.error("获取用户信息失效，请重新登陆!!!");
        }
        this.initData()
      })

      })
    },






    updateuserforms(){
      this.$refs.updateuserform.validate(valid=>{
          console.log("校验扁担:"+valid)
          if (!valid)return
      this.file = new FormData();
      this.file.append("username",this.updateuserform.name)
      this.file.append("password",this.updateuserform.password)
      this.file.append("companyname",this.updateuserform.companyname)
      this.file.append("phone",this.updateuserform.phone)
      this.file.append("address",this.updateuserform.address)
      this.file.append("id",this.updateuserform.id)
      let optionsb = '';
      let list = this.optionsb;
    //  console.log("动态列数据:"+list)
      for(let i = 0;i< list.length;i++){
        if(i == 0){
          optionsb += list[0].appid;
        }else{
          optionsb += ',' + list[i].appid
          //console.log("sbbbbbbbbbbbbbbb:"+optionsb)
        }
      }
      this.file.append("domains",optionsb)
     // console.log("dddddddddd:"+optionsb)
      request({
        method: 'post',
        url: 'user/update',
        // params: {
        //
        //
        // }
        data:this.file
      }).then(res => {
        this.dialogVisibles=false
        this.initData()

      })
      })
    },

    initData(data) {
      const _this = this
      console.log(data)
      this.data=Qs.stringify(data)
      request({
        method: 'post',
        url: 'user/querycompany',
        data:this.data
      }).then(res => {

        this.tableData = res.data.data.list
        this.totalCount=res.data.data.list.length
        //this.options=res.data.data.proinfo
       // console.log("湖人加油总冠军:"+this.tableData)
       // console.log("湖人加油总冠军:"+this.options)
      })
    }
  },
  created() {
    // this.initData();
    // this.tableData=[]
    // this.tableData.push(1)
    this.handleSizeChange(5)
    // this.initDatas({pageNum: this.currentPage, pageSize: this.pagesize});

}
}
</script>
<style>
.NoruserManage {
  margin-left: 80px;
  margin-top: 50px;
}
.towernums {
  /*margin-right: 30px;*/
  text-align: right;
  padding: 16px;
}
</style>
