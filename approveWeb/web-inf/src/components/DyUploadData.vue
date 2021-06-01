<template>
  <el-card class="box-card">
    <el-row>
      <el-col :span="12" class="spanCenter">
        <span>本次巡检线路：</span>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <span>本次巡检第一根杆塔号(四位数字)：</span>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <el-input
          class="inputStyle"
          placeholder="请输入本次巡检线路"
          v-model="line"
          @change="watchLine"
          clearable
        ></el-input>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <el-input
          class="inputStyle"
          placeholder="请输入本次巡检第一根杆塔号"
          v-model="firstNo"
          :disabled="lineTrue"
          @change="watchFirst"
          clearable
        ></el-input>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <span>本次巡检最后一根杆塔号(四位数字)：</span>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <span>本次第一个上传文档的前四位数：</span>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <el-input
          class="inputStyle"
          placeholder="请输入本次巡检最后一根杆塔号"
          v-model="finallyNo"
          :disabled="firstTrue"
          @change="watchFinally"
          clearable
        ></el-input>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <el-input
          class="inputStyle"
          placeholder="请输入本次第一个上传文档的前四位数"
          v-model="firstText"
          :disabled="finallyTrue"
          @change="watchFirstTxt"
          clearable
        ></el-input>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <span>本次最后一个上传文档的前四位数：</span>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <span>选择检测时间：</span>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <el-input
          class="inputStyle"
          placeholder="本次最后一个上传文档的前四位数"
          v-model="finallyText"
          :disabled="true"
          clearable
        ></el-input>
      </el-col>

      <el-col :span="12" class="spanCenter">
        <el-date-picker
          v-model="detectionDate"
          :disabled="textTrue"
          type="datetime"
          placeholder="请选择检测日期时间"
        ></el-date-picker>
      </el-col>

      <el-col :span="12">
        <el-upload
          class="upload-demo"
          ref="upload"
          action="api/dyDatainfo/upLoadFile"
          :before-remove="beforeRemove"
          :before-upload="beforeUpload"
          :on-success="uploadOnSuccess"
          :on-remove="removeFile"
          :multiple="true"
          :file-list="fileList"
          :auto-upload="false"
        >
          <el-button slot="trigger" size="small" type="primary"
            >获取文件</el-button
          >

          <el-button
            style="margin-left: 10px"
            size="small"
            type="success"
            @click="submitUpload()"
            >上传文件</el-button
          >

          <el-button
            style="margin-left: 10px"
            size="small"
            type="danger"
            @click="cleanUpload()"
            >清空文件</el-button
          >
        </el-upload>
      </el-col>
    </el-row>

    <el-row class="saveClass">
      <el-button size="small" type="primary" @click="saveData()"
        >确认保存</el-button
      >
      <el-button size="small" type="primary" @click="initData()"
        >重置数据</el-button
      >
    </el-row>
  </el-card>
</template>

<script>
import { request } from "../../src/network/request.js";

export default {
  data() {
    return {
      checkFile: new Set(),
      fileListData: [],
      fileList: [],
      line: "",
      firstNo: "",
      finallyNo: "",
      firstText: "",
      finallyText: "",
      firstNoTest: "",
      finallyNoTest: "",
      firstTextTest: "",
      finallyTextTest: "",
      detectionDate: "",
      firstTrue: true,
      finallyTrue: true,
      lineTrue: true,
      textTrue: true,
    };
  },
  methods: {
    // 监测路线是否输入
    watchLine() {
      if (this.line != null && this.line != "") {
        this.lineTrue = false;
      } else {
        this.lineTrue = true;
      }
    },

    // 检测第一杆塔是否输入并正确
    watchFirst() {
      if (this.firstNo != null && this.firstNo != "") {
        this.firstNoTest = this.firstNo.replace(/\s/g, "");
        if (this.firstNoTest.length == 4) {
          if (!isNaN(parseInt(this.firstNoTest))) {
            if (
              this.finallyNoTest != null &&
              this.finallyNoTest != "" &&
              this.finallyNoTest.length == 4
            ) {
              if (!isNaN(parseInt(this.finallyNoTest))) {
                if (
                  this.firstTextTest != null &&
                  this.firstTextTest != "" &&
                  this.firstTextTest.length == 4
                ) {
                  if (!isNaN(parseInt(this.firstTextTest))) {
                    if (
                      parseInt(this.finallyNoTest) -
                        parseInt(this.firstNoTest) >=
                      0
                    ) {
                      this.finallyText =
                        parseInt(this.firstTextTest) +
                        (parseInt(this.finallyNoTest) -
                          parseInt(this.firstNoTest));
                      if (this.finallyText.toString().length != 4) {
                        let number = 4 - this.finallyText.toString().length;
                        for (let i = 0; i < number; i++) {
                          this.finallyText = "0" + this.finallyText.toString();
                        }
                      }
                    } else {
                      this.firstTrue = true;
                      this.finallyNo = "";
                      this.finallyNoTest = "";
                      this.$alert("请正确输入杆塔号", "提示", {
                        confirmButtonText: "确定",
                        type: "warning",
                      });
                    }
                  }
                }
              }
            }
            this.firstTrue = false;
          } else {
            this.firstTrue = true;
            this.finallyNo = "";
            this.finallyNoTest = "";
          }
        } else {
          this.firstTrue = true;
          this.finallyNo = "";
          this.finallyNoTest = "";
        }
      } else {
        this.firstTrue = true;
        this.finallyNo = "";
        this.finallyNoTest = "";
      }
    },

    // 检测最后杆塔是否输入并正确
    watchFinally() {
      if (this.finallyNo != null && this.finallyNo != "") {
        this.finallyNoTest = this.finallyNo.replace(/\s/g, "");
        if (this.finallyNoTest.length == 4) {
          if (!isNaN(parseInt(this.finallyNoTest))) {
            if (
              parseInt(this.finallyNoTest) - parseInt(this.firstNoTest) >=
              0
            ) {
              if (
                this.firstTextTest != null &&
                this.firstTextTest != "" &&
                this.firstTextTest.length == 4
              ) {
                if (!isNaN(parseInt(this.firstTextTest))) {
                  this.finallyText =
                    parseInt(this.firstTextTest) +
                    (parseInt(this.finallyNoTest) - parseInt(this.firstNoTest));
                  if (this.finallyText.toString().length != 4) {
                    let number = 4 - this.finallyText.toString().length;
                    for (let i = 0; i < number; i++) {
                      this.finallyText = "0" + this.finallyText.toString();
                    }
                  }
                }
              }
              this.finallyTrue = false;
            } else {
              this.finallyTrue = true;
              this.firstText = "";
              this.firstTextTest = "";
              this.$alert("请正确输入杆塔号", "提示", {
                confirmButtonText: "确定",
                type: "warning",
              });
            }
          } else {
            this.finallyTrue = true;
            this.firstText = "";
            this.firstTextTest = "";
          }
        } else {
          this.finallyTrue = true;
          this.firstText = "";
          this.firstTextTest = "";
        }
      } else {
        this.finallyTrue = true;
        this.firstText = "";
        this.firstTextTest = "";
      }
    },

    // 检测第一上传文档前四位是否输入并正确
    watchFirstTxt() {
      if (this.firstText != null && this.firstText != "") {
        this.firstTextTest = this.firstText.replace(/\s/g, "");
        if (this.firstTextTest.length == 4) {
          if (
            !isNaN(parseInt(this.firstTextTest)) &&
            !isNaN(parseInt(this.finallyNoTest)) &&
            !isNaN(parseInt(this.firstNoTest))
          ) {
            this.textTrue = false;
            this.finallyText =
              parseInt(this.firstTextTest) +
              (parseInt(this.finallyNoTest) - parseInt(this.firstNoTest));
            if (this.finallyText.toString().length != 4) {
              let number = 4 - this.finallyText.toString().length;
              for (let i = 0; i < number; i++) {
                this.finallyText = "0" + this.finallyText.toString();
              }
            }
          } else {
            this.textTrue = true;
            this.finallyText = "";
            this.finallyTextTest = "";
          }
        } else {
          this.textTrue = true;
          this.finallyText = "";
          this.finallyTextTest = "";
        }
      } else {
        this.textTrue = true;
        this.finallyText = "";
        this.finallyTextTest = "";
      }
    },

    // 清空已上传文件
    cleanUpload() {
      this.$confirm("确认移除全部文件？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$refs.upload.clearFiles();
        this.fileListData = [];
      });
    },

    // 文件上传前的钩子，数为上传的文件
    beforeUpload(file) {
      // 判断文件的类型
      const isTxt = file.type == "text/plain";

      if (!isTxt) {
        this.$message.error("只能上传txt格式的文件！");
        return false;
      }
    },

    // 手动上传文件
    submitUpload() {
      this.$refs.upload.submit();
    },

    // 删除文件
    removeFile(file) {
      if (this.fileListData.length > 0) {
        for (let i = 0; i < this.fileListData.length; i++) {
          if (this.fileListData[i].name == file.name) {
            this.fileListData.splice(i, 1);
          }
        }
      }
    },

    beforeRemove(file) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    uploadOnSuccess(res, file, fileList) {
      if (res.msg == "success") {
        this.fileListData.push(res.data);
      } else {
        this.$message.error(file.name + res.msg);
        fileList.splice(fileList.indexOf(file), 1);
      }
    },

    // 保存数据
    saveData() {
      if (this.fileListData.length <= 0) {
        this.$alert("请上传数据文件", "提示", {
          confirmButtonText: "确定",
          type: "warning",
        });
      } else if (
        this.detectionDate == null ||
        this.detectionDate == "" ||
        this.line == null ||
        this.line == "" ||
        this.firstNo == null ||
        this.firstNo == "" ||
        this.finallyNo == null ||
        this.finallyNo == "" ||
        this.firstText == null ||
        this.firstText == "" ||
        this.finallyText == null ||
        this.finallyText == ""
      ) {
        this.$alert("请正确填写数据", "提示", {
          confirmButtonText: "确定",
          type: "warning",
        });
      } else if (
        parseInt(this.finallyNoTest) - parseInt(this.firstNoTest) <
        0
      ) {
        this.$alert("请正确输入杆塔号", "提示", {
          confirmButtonText: "确定",
          type: "warning",
        });
      } else {
        let flag = true;
        for (let i = 0; i < this.fileListData.length; i++) {
          this.checkFile.add(this.fileListData[i].name.substr(0, 4));
        }

        let list = Array.from(
          new Array(parseInt(this.finallyText) + 1).keys()
        ).slice(parseInt(this.firstTextTest));

        let towerList = Array.from(
          new Array(parseInt(this.finallyNoTest) + 1).keys()
        ).slice(parseInt(this.firstNoTest));

        for (let k = 0; k < towerList.length; k++) {
          if (towerList[k].toString().length < 4) {
            let number = 4 - towerList[k].toString().length;
            for (let i = 0; i < number; i++) {
              towerList[k] = "0" + towerList[k].toString();
            }
          }
        }

        this.fileListData.sort(function (a, b) {
          if (a.name.substring(0, 4) == b.name.substring(0, 4)) {
            return a.name.substring(7) - b.name.substring(7);
          } else {
            return a.name.substring(0, 4) - b.name.substring(0, 4);
          }
        });

        for (let n = 0; n < this.fileListData.length; n++) {
          if (n == 0) {
            this.fileListData[n].tower = towerList[0];
          } else {
            if (
              this.fileListData[n].name.substring(0, 4) ==
              this.fileListData[n - 1].name.substring(0, 4)
            ) {
              this.fileListData[n].tower = this.fileListData[n - 1].tower;
            } else {
              for (let m = 0; m < towerList.length; m++) {
                if (towerList[m] == this.fileListData[n - 1].tower) {
                  this.fileListData[n].tower = towerList[m + 1];
                }
              }
            }
          }
        }

        for (let j = 0; j < list.length; j++) {
          if (list[j].toString().length < 4) {
            let number = 4 - list[j].toString().length;
            for (let k = 0; k < number; k++) {
              list[j] = "0" + list[j].toString();
            }
          }
        }

        for (let a = 0; a < list.length; a++) {
          if (!this.checkFile.has(list[a].toString())) {
            flag = false;
          }
        }

        if (flag) {
          request({
            method: "post",
            url: "dyDatainfo/upLoad",
            data: {
              fileList: this.fileListData,
              line: this.line,
              detectionDate: this.detectionDate,
              userId: JSON.parse(localStorage.getItem("user")).id,
            },
          }).then((res) => {
            this.initData();
          });
        } else {
          this.$alert("所上传文件序号未对应", "提示", {
            confirmButtonText: "确定",
            type: "warning",
          });
        }
      }
    },

    // 初始化数据
    initData() {
      this.checkFile = new Set();
      this.fileListData = [];
      this.fileList = [];
      this.line = "";
      this.firstNo = "";
      this.finallyNo = "";
      this.firstText = "";
      this.finallyText = "";
      this.detectionDate = "";
      this.firstTrue = true;
      this.finallyTrue = true;
      this.lineTrue = true;
      this.textTrue = true;
      this.firstNoTest = "";
      this.finallyNoTest = "";
      this.firstTextTest = "";
      this.finallyTextTest = "";
    },
  },

  // 初始化数据
  created() {
    this.initData();
  },
};
</script>
<style>
.el-row {
  height: 720px;
}

.el-upload-list {
  width: 350px;
}

.el-card {
  height: 800px;
  margin: 20px;
}

.saveClass {
  text-align: center;
}

.el-date-editor.el-input,
.el-date-editor.el-input__inner {
  width: 350px;
}

[class*="el-col-"] {
  padding: 10px 0;
}

.inputStyle {
  width: 350px;
}
</style>
