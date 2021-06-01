<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline":rules="validateRules">
      <el-form-item>
        <el-input v-model="searchObj.roleName" placeholder="角色名称"/>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 工具条 -->
    <div>
      <el-button type="danger" size="mini" @click="addUser()">添加</el-button>
      <el-button type="danger" size="mini" @click="removeRows()">批量删除</el-button>

    </div>

    <!-- jiaose列表 -->
    <el-table
      :data="list"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange">

      <el-table-column
        type="selection"
        width="55"/>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="roleName" label="角色名称"/>


      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">

          <el-button type="info" size="mini" icon="el-icon-info" @click="showPermisson(scope.row)"></el-button>


          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updatDataById(scope.row)"></el-button>

          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="total, sizes, prev, pager, next, jumper"
      @current-change="handleCurrentChange"
      @size-change="changeSize"
    />

    <el-dialog class="abow_dialog"
               :title="dialogTitle"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               width="80%"
               height="60%">
      <el-form ref="role" :model="role" label-width="120px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="role.roleName"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

      <!--权限菜单 -->
    <el-dialog class="abow_dialog"
               :title="dialogTitle"
               :visible.sync="dialogVisibles"
               :close-on-click-modal="false"
               width="80%"
               height="60%">
    <el-tree
      :data="data"
      show-checkbox
      default-expand-all
      node-key="id"
      ref="tree"
      highlight-current
      :props="defaultProps">
    </el-tree>
    <el-button :disabled="saveBtnDisabled" type="primary" @click="save">保存</el-button>
    </el-dialog>
  </div>



</template>

<script>

import roleApi from '@/network/role/role';
import {request} from "../network/request";
import menu from '@/network/permisson/permisson';


export default {
  data() {
    return {

      saveBtnDisabled:false,
      data: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      roleId:'',

      validateRules:{
        roleName: [{ required: true, trigger: 'blur', message: '角色名称必须输入' }],
      },
      listLoading: true, // 数据是否正在加载
      list: null, // 讲师列表
      currentPage: 1,
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询表单对象
      dialogVisibles: false,
      dialogVisible: false,
      role: {
        roleName: ''
      },
      dialogTitle: '',
      multipleSelection: [] // 批量选择中选择的记录列表



    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    console.log('list created......')
    this.fetchData()
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    console.log('list mounted......')
  },

  methods: {



    fetchDataById(roleId){
      menu.toAssign(roleId).then(response => {
        this.data = response.data.children
        var jsonList = JSON.parse(JSON.stringify(this.data))
       // debugger;
        var list = []
        this.getJsonToList(list, jsonList[0]['children'])
        console.log("最终集合")
        console.log(list)
        this.setCheckedKeys(list)
      })
    },
    //把json数据转成string再转成对象，根据Key获取value数据
    getJsonToList(list, jsonList){
      //遍历这个集合对象，获取key的值
      for(var i = 0; i < jsonList.length; i++){
        if(jsonList[i]['select'] == true && jsonList[i]['level'] == 4){
          list.push(jsonList[i]['id'])
        }
        if(jsonList[i]['children'] != null){
          this.getJsonToList(list, jsonList[i]['children'])
        }
      }

    },

    getCheckedNodes() {
      console.log(this.$refs.tree.getCheckedNodes());
    },
    getCheckedKeys() {
      console.log(this.$refs.tree.getCheckedKeys());
    },

    setCheckedKeys(list) {
      this.$refs.tree.setCheckedKeys(list);
    },

    save(){
      this.saveBtnDisabled = true
      var ids = this.$refs.tree.getCheckedKeys().join(",");
      //vue elementUI tree树形控件获取父节点ID的实例
      //node_modules\element-ui\lib\element-ui.common.js
      //25348行修改源码
      menu.doAssign(this.roleId, ids).then(response => {
        if(response.success){
          this.$message({
            type:'success',
            message:'保存成功'
          })
          this.$router.push({ path: '/acl/role/list' })
        }
      })
    },



    // 每页显示数据变量
    changeSize(size) {
      console.log(size)
      this.limit = size
      this.fetchData(1)
    },

    //页码变更
    handleCurrentChange(val) {
      this.page = val;
      this.fetchData()
    },

    addUser() {
      this.dialogVisible = true;
    },
    updatDataById(row){
      this.dialogVisible=true;

      this.role.id=row.id
      this.role.roleName=row.roleName

    },
    showPermisson(row){
      //this.dialogVisibles=true;
      window.location.href = './roleForm.html?'+row.id+"";

      //this.$router.push({path:'/roleForm.html',params:{roleid:row.id}});
    },


    // 加载列表数据
    fetchData(page = 1) {
       console.log('翻页。。。' + page)
      // 异步获取远程数据（ajax）
      this.page = page

      roleApi.getPageList(this.page, this.limit, this.searchObj).then(
        response => {
          // console.log(response.data.data)
          this.list = response.data.data.items
          // console.log(this.list[0]+"sss")
          this.total=response.data.data.items.length
          console.log(response.data.data.items.length)
          // 数据加载并绑定成功
          // this.listLoading = false
        }
      )
    },

    // 重置查询表单
    resetData() {
      // console.log('重置查询表单')
      this.searchObj = {}
      this.fetchData()
    },


    saveOrUpdate() {
      // this.$refs.role.validate(valid => {
      //   if (valid) {
      //     this.saveBtnDisabled = true // 防止表单重复提交

          if (!this.role.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        // }
      // })
    },

    // 新增角色
    saveData() {
      this.file = new FormData();
      this.file.append("roleName", this.role.roleName)
      request({
        method: 'post',
        url: '/admin/acl/role/save',
        data: this.file
      }).then(res => {
        this.dialogVisible=false
        this.fetchData()
      })
    },

    // 根据id更新记录
    updateData() {
      this.file = new FormData();
      this.file.append("id",this.role.id)
      this.file.append("roleName", this.role.roleName)
      request({
        method: 'post',
        url: '/admin/acl/role/update',
        data: this.file
      }).then(res => {
        this.dialogVisible=false
        this.fetchData()
      })
    },

    // 根据id删除数据
    removeDataById(id) {
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return roleApi.removeById(id)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.success) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },



    // 当表格复选框选项发生变化的时候触发
    handleSelectionChange(selection) {
      // console.log('handleSelectionChange......')
      // console.log(selection)
      this.multipleSelection = selection
    },

    // 批量删除
    removeRows() {
      // console.log('removeRows......')

      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择要删除的记录!'
        })
        return
      }

      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        // 遍历selection，将id取出放入id列表
        var idList = []
        this.multipleSelection.forEach(item => {
          idList.push(item.id)
          // console.log(idList)
        })
        // 调用api
        return roleApi.removeRows(idList)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.success) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    // 执行搜索
    // queryString：文本框中输入的值
    // cb：一个函数
    querySearch(queryString, cb) {
      // console.log(queryString)
      console.log(cb)

      // teacher.selectNameByKey(queryString).then(response => {
      //   // 调用 callback 返回建议列表的数据
      //   cb(response.data.items)
      // })
    }
  }
}
</script>

