<template>
    <el-card class="box-card">
        <el-row>
            <el-col :span="12" class="spanCenter">
                <span>请上传数据文档(dat格式):</span>
            </el-col>

            <el-col :span="12" class="spanCenter">
                <span>请上传音频文件(wav格式):</span>
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
                    <el-button slot="trigger" size="small" type="primary">获取文件</el-button>

                    <el-button style="margin-left: 10px" size="small" type="success" @click="submitUpload()"
                        >上传文件</el-button
                    >

                    <el-button style="margin-left: 10px" size="small" type="danger" @click="cleanUpload()"
                        >清空文件</el-button
                    >
                </el-upload>
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
                    <el-button slot="trigger" size="small" type="primary">获取文件</el-button>

                    <el-button style="margin-left: 10px" size="small" type="success" @click="submitUpload()"
                        >上传文件</el-button
                    >

                    <el-button style="margin-left: 10px" size="small" type="danger" @click="cleanUpload()"
                        >清空文件</el-button
                    >
                </el-upload>
            </el-col>
        </el-row>
    </el-card>
</template>

<script>
import { request } from '../network/request'

export default {
  data () {
    return {}
  },
  methods: {
    beforeAvatarUpload (file) {
      var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      const extension = testmsg === 'dat'
      const isLt50M = file.size / 1024 / 1024 < 10
      if (!extension) {
        this.$message({
          message: '上传图片只能是jpg / png / bpm格式!',
          type: 'error'
        })
        return false // 必须加上return false; 才能阻止
      }
      if (!isLt50M) {
        this.$message({
          message: '上传文件大小不能超过 10MB!',
          type: 'error'
        })
        return false
      }
      return extension || isLt50M
    }
  },
  created () {}
}
</script>
<style>
.box-card {
    margin: 20px;
    height: 800px;
}

.el-upload-list {
    width: 350px;
}

[class*='el-col-'] {
    padding: 10px 0;
}
</style>
