<template>
  <div style="margin: 20px 20px">
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
    <el-button :disabled="returnDisabled" type="primary" @click="retur">返回</el-button>
  </div>
</template>
<script>
import menu from '@/network/permisson/permisson';

export default {
  data() {
    return {
      saveBtnDisabled:false,
      returnDisabled:false,
      data: [],
      datas: location.search,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      roleId:''

    };
  },
  // 监听器
  watch: {
    $route(to, from) {
      console.log('路由变化......')
      console.log(to)
      console.log(from)
      this.init()
    }
  },

  created () {
    this.init()
  },
  methods: {

    init(){
      if (this.datas.indexOf("?") != -1) {
        this.roleId = this.datas.substr(1)
        console.log( this.roleId)
        // this.roleId = this.$route.params.id
        this.fetchDataById(this.roleId)
      }


    },
    //根据角色获取菜单
    fetchDataById(roleId){
      menu.toAssign(roleId).then(response => {
        this.data = response.data.data.children
        console.log(response.data.data.children)
        var jsonList = JSON.parse(JSON.stringify(this.data))
        //debugger;
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
        if(jsonList[i]['select'] == true){
          list.push(jsonList[i]['id'])
        }
        if(jsonList[i]['children'] != null){
          this.getJsonToList(list, jsonList[i]['children'])
        }
      }

    },

    getCheckedNodes() {
      console.log(this.$refs.tree.getCheckedNodes()+"checkednodes");
    },
    getCheckedKeys() {
      console.log(this.$refs.tree.getCheckedKeys()+"checkedkeys");
    },

    setCheckedKeys(list) {
      this.$refs.tree.setCheckedKeys(list);
    },
    retur(){
      window.location.href='./role.html'
    },

    save(){
      this.saveBtnDisabled = true
      var ids = this.$refs.tree.getCheckedKeys().join(",");
      //vue elementUI tree树形控件获取父节点ID的实例
      //node_modules\element-ui\lib\element-ui.common.js
      //25348行修改源码
      menu.doAssign(this.roleId, ids).then(response => {
        console.log("湖人总冠军"+this.roleId)
        if(response.data.success){
          this.$message({
            type:'success',
            message:'保存成功'
          })
          this.init()
         // this.$router.push({ path: '/acl/role/list' })
        //  window.location.href='./role.html'
        }
       // window.location.href='./role.html'
      })
    }
  }
};
</script>
