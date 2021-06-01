<template xmlns="http://www.w3.org/1999/html">
  <div class="app-container">
    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
    <div style="margin: 15px 0;"></div>
    <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
      <el-checkbox v-for="city in AList" :label="city.id" :key="city.id">{{city.roleName}}</el-checkbox>
    </el-checkbox-group>
    <span><br></span>
    <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
      <el-checkbox v-for="city in BList" :label="city.id" :key="city.id">{{city.roleName}}</el-checkbox>
    </el-checkbox-group>

    <el-button :disabled="saveBtnDisabled" type="primary" @click="update">保存</el-button>
    <el-button :disabled="returnDisabled" type="primary" @click="retur">返回</el-button>

  </div>
</template>

<script>

import userApi from '@/network/role/user'

//const cityOptions = ['上海', '北京', '广州', '深圳'];

export default {
  data() {
    return {
      aaa: localStorage.getItem("rolelist"),
      user:JSON.parse(localStorage.getItem("user")),
      checkAll: true,
      checkedCities: [], //已选中
      cities: [], //所有的
      datas: location.search,
      isIndeterminate: true,
      userId:'',
      saveBtnDisabled: false, // 保存按钮是否禁用,
      returnDisabled:false,
      AList:[],
      BList:[]
    };
  },
  created () {
    this.init()
  },
  methods: {
    init(){
      if (this.datas.indexOf("?") != -1) {
        this.userId = this.datas.substr(1)
        console.log( this.userId)
        this.getById(this.userId)
      }
    },
    getById(userId){
      userApi.getAssign(userId).then(response => {
        var jsonObj = response.data.data.assignRoles
        this.checkedCities = this.getJsonToList(jsonObj,"id")
        this.cities = response.data.data.allRolesList


        var listElement0 = this.cities[0];
        var listElement1 = this.cities[1];
        var listElement2 = this.cities[2];

        var listElement3 = this.cities[3];
        var listElement4 = this.cities[4];
        var listElement5 = this.cities[5];
        var listElement6 = this.cities[6];
        var listElement7 = this.cities[7];

        this.AList.push(listElement0)
        this.AList.push(listElement1)
        this.AList.push(listElement2)

        this.BList.push(listElement3)
        this.BList.push(listElement4)
        this.BList.push(listElement5)
        this.BList.push(listElement6)
        this.BList.push(listElement7)

      })
    },
    //把json数据转成string再转成对象，根据Key获取value数据
    getJsonToList(json,key){

      //把JSON字符串转成对象
      var list = JSON.parse(JSON.stringify(json));
      //var list = JSON.parse(json)
      var strText = []
      //遍历这个集合对象，获取key的值
      for(var i = 0; i < list.length; i++){
        strText.push(list[i][key])
      }
      return strText;

    },
    handleCheckAllChange(val) {
      this.checkedCities = val ? this.cities : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {

      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },
    retur(){

        window.location.href='./UserBy.html'


    },
    update(){
      this.saveBtnDisabled = true // 防止表单重复提交
      var ids = this.checkedCities.join(",")
      console.log(ids)
      //修改权限
      userApi.saveAssign(this.userId, ids).then(response => {
        console.log(response)
        if(response.data.success){
          this.$message({
            type:'success',
            message:'保存成功',
          })
         //window.location.href='./NoruserManage.html'
          //this.init()
          setTimeout("window.location.href='./UserBy.html'", 1600)

          //window.location.href='./NoruserManage.html'
        }
       //window.location.href='./NoruserManage.html'
      })
    }
  }
};
</script>
