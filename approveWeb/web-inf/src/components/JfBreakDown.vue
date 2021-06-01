<template>
    <div>
        <el-card class="innerCard">
            <el-row>
                <el-col :span="6" class="colClass">
                    <span>设备种类：</span>
                    <el-select
                        class="selectStyle"
                        v-model="paramsList.kind"
                        clearable
                        placeholder="请选择设备种类"
                        @change="searchData"
                    >
                        <el-option
                            v-for="item in kindOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-col>

                <el-col :span="6" class="colClass">
                    <span>设备状态：</span>
                    <el-select
                        class="selectStyle"
                        v-model="paramsList.state"
                        clearable
                        placeholder="请选择设备状态"
                        @change="searchData"
                    >
                        <el-option
                            v-for="item in stateOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-col>

                <el-col :span="6" class="colClass">
                    <span>线路名称：</span>
                    <el-input
                        placeholder="请输入内容"
                        v-model="paramsList.lineNameInput"
                        clearable
                        @change="searchData"
                    ></el-input>
                </el-col>

                <el-col :span="6" class="colClass">
                    <span>线路编号：</span>
                    <el-input
                        placeholder="请输入内容"
                        v-model="paramsList.lineNoInput"
                        clearable
                        @change="searchData"
                    ></el-input>
                </el-col>

                <el-col :span="6" class="colClass">
                    <span>缺陷程度：</span>
                    <el-input
                        placeholder="请输入内容"
                        v-model="paramsList.flawInput"
                        clearable
                        @change="searchData"
                    ></el-input>
                </el-col>

                <el-col :span="12" class="colClass">
                    <span>检测时间：</span>
                    <el-date-picker
                        v-model="paramsList.dateTimeInput"
                        value-format="yyyy-MM-dd"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        @change="searchData"
                    ></el-date-picker>
                </el-col>
            </el-row>
        </el-card>

        <el-table :data="tableData" style="width: 100%" stripe v-loading="loading">
            <el-table-column prop="stationnumber" align="center" label="线路编号"></el-table-column>
            <el-table-column prop="stationname" align="center" label="线路名称"></el-table-column>
            <el-table-column prop="time" align="center" label="检测时间" width="100px"></el-table-column>
            <el-table-column prop="maxdb" align="center" label="最大dB值"></el-table-column>
            <el-table-column prop="avedb" align="center" label="平均dB值"></el-table-column>
            <el-table-column prop="defectlevel" align="center" width="146px" label="缺陷程度">
                <template slot-scope="scope">
                    <div v-if="scope.row.isTrue == 1">
                        <el-button
                            type="warning"
                            size="medium"
                            class="warningInput"
                            v-if="scope.row.avedb < 20"
                            @click="abnormity(scope.row.id)"
                            >异常(点击处理)</el-button
                        >
                        <el-button
                            type="danger"
                            size="medium"
                            class="dangerInput"
                            v-else
                            @click="abnormity(scope.row.id)"
                            >危险(点击处理)</el-button
                        >
                    </div>
                    <div v-else>
                        <div class="trueStyle" v-if="scope.row.avedb < 20">异常</div>
                        <div class="trueStyle" v-else>危险</div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="temperature" align="center" label="温度"></el-table-column>
            <el-table-column prop="humidity" align="center" label="湿度"></el-table-column>
            <el-table-column prop="longitude" align="center" label="经度"></el-table-column>
            <el-table-column prop="latitude" align="center" label="纬度"></el-table-column>
            <el-table-column prop="voice" align="center" label="音频" width="320px">
                <template slot-scope="scope">
                    <audio :src="scope.row.audio" controls="controls"></audio>
                </template>
            </el-table-column>
            <el-table-column prop="operation" align="center" label="操作" width="290px">
                <template slot-scope="scope">
                    <el-button size="mini" type="primary" @click="analyseOpen(scope.$index, scope.row)"
                        >分析</el-button
                    >
                    <el-button size="mini" type="primary" @click="reportOpen(scope.$index, scope.row)"
                        >报告</el-button
                    >
                    <el-button size="mini" type="primary" @click="openEdit(scope.$index, scope.row)"
                        >编辑</el-button
                    >
                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button
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
        ></el-pagination>

        <el-dialog title="缺陷处理" :visible.sync="centerDialogVisible" width="30%" center @close="initAbnormity">
            <el-input
                type="textarea"
                autosize
                placeholder="请输入处理方式(解决方案)"
                v-model="textarea"
            ></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="abnormityButton">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="编辑报告详情" :visible.sync="editVisible" width="70%" center @close="initEdit(0)">
            <el-row>
                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">线路名称：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input placeholder="请输入线路名称" v-model="editData.stationname" clearable></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">检测日期：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.time" disabled></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">线路编号：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input
                            placeholder="请输入线路编号"
                            v-model="editData.stationnumber"
                            clearable
                        ></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">频率：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.rcf" disabled></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">线路等级：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-select
                            class="selectStyle"
                            v-model="editData.lineLevel"
                            clearable
                            placeholder="请选择线路等级"
                        >
                            <el-option
                                v-for="item in kindOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">温度(℃)：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.temperature" disabled></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">地域特征：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-select
                            class="selectStyle"
                            v-model="editData.areaFeature"
                            clearable
                            placeholder="请选择地域特征"
                        >
                            <el-option
                                v-for="item in kindOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">湿度(％)：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.humidity" disabled></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">设备种类：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-select
                            class="selectStyle"
                            v-model="editData.kind"
                            clearable
                            placeholder="请选择设备种类"
                        >
                            <el-option
                                v-for="item in kindOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">经度：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.longitude" disabled></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">设备状态：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-select
                            class="selectStyle"
                            v-model="editData.state"
                            clearable
                            placeholder="请选择设备状态"
                        >
                            <el-option
                                v-for="item in kindOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">纬度：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.latitude" disabled></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">检测距离(m)：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input placeholder="请输入检测距离" v-model="editData.distance" clearable></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">最大dB值：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.maxdb" disabled></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">检测人员：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input placeholder="请输入检测人员" v-model="editData.people" clearable></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">平均dB值：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.avedb" disabled></el-input>
                    </el-col>
                </el-col>

                <el-col :span="12" :push="12" class="elClass colClass">
                    <el-col :span="5">
                        <span class="editFont">缺陷程度：</span>
                    </el-col>
                    <el-col :span="19">
                        <el-input v-model="editData.defectlevel" disabled></el-input>
                    </el-col>
                </el-col>
            </el-row>

            <el-row class="imgStyle">
                <span class="imgFontStyle">编号照片：</span>
                <div style="margin-left: 80px">
                    <el-upload
                        ref="uploadNo"
                        action="action"
                        list-type="picture-card"
                        :http-request="submitEdit"
                        :on-preview="handlePictureCardPreview1"
                        :on-remove="NoRemove"
                        :before-upload="beforeUpload"
                        accept="image/*"
                        :auto-upload="false"
                        :file-list="NoImges"
                        :on-change="NoChange"
                    >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogNo" append-to-body>
                        <img width="100%" :src="dialogImageUrl" alt />
                    </el-dialog>
                </div>
            </el-row>

            <el-row class="imgStyle">
                <span class="imgFontStyle">整体照片：</span>
                <div style="margin-left: 80px">
                    <el-upload
                        ref="uploadEntirety"
                        action="action"
                        list-type="picture-card"
                        :http-request="submitEdit"
                        :on-preview="handlePictureCardPreview2"
                        :on-remove="entiretyRemove"
                        :before-upload="beforeUpload"
                        accept="image/*"
                        :auto-upload="false"
                        :file-list="entiretyImges"
                        :on-change="entiretyChange"
                    >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogEntirety" append-to-body>
                        <img width="100%" :src="dialogImageUrl" alt />
                    </el-dialog>
                </div>
            </el-row>

            <el-row class="imgStyle">
                <span class="imgFontStyle">不良照片：</span>
                <div style="margin-left: 80px">
                    <el-upload
                        ref="uploadBadness"
                        action="action"
                        list-type="picture-card"
                        :http-request="submitEdit"
                        :on-preview="handlePictureCardPreview3"
                        :on-remove="badnessRemove"
                        :before-upload="beforeUpload"
                        accept="image/*"
                        :auto-upload="false"
                        :file-list="badnessImges"
                        :on-change="badnessChange"
                    >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogBadness" append-to-body>
                        <img width="100%" :src="dialogImageUrl" alt />
                    </el-dialog>
                </div>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="initEdit(1)">重 置</el-button>
                <el-button type="primary" @click="submitEdit">提 交</el-button>
            </span>
        </el-dialog>

        <el-drawer
            title="故障分析"
            :visible.sync="analyse"
            size="65%"
            :destroy-on-close="true"
            @close="initDrawer"
        >
            <el-card class="innerCard">
                <el-row>
                    <el-col :span="8" class="colClass">
                        <span>线路名称：</span>
                        <span>{{ this.analyseData.stationname }}</span>
                    </el-col>
                    <el-col :span="8" class="colClass">
                        <span>线路编号：</span>
                        <span>{{ this.analyseData.stationnumber }}</span>
                    </el-col>
                    <el-col :span="8" class="colClass">
                        <span>设备种类：</span>
                        <span>{{ this.analyseData.kkk }}</span>
                    </el-col>
                    <el-col :span="8" class="colClass">
                        <span>设备状态：</span>
                        <span>{{ this.analyseData.pppp }}</span>
                    </el-col>
                    <el-col :span="8" class="colClass">
                        <span>最大dB值：</span>
                        <span>{{ this.analyseData.maxdb }}</span>
                    </el-col>
                    <el-col :span="8" class="colClass">
                        <span>平均dB值：</span>
                        <span>{{ this.analyseData.avedb }}</span>
                    </el-col>
                    <el-col :span="8" class="colClass">
                        <span>缺陷程度：</span>
                        <span>{{ this.analyseData.defectlevel }}</span>
                    </el-col>
                    <el-col :span="16" class="colClass">
                        <span>检测时间：</span>
                        <span>{{ this.analyseData.time }}</span>
                    </el-col>
                </el-row>
            </el-card>
            <el-card class="innerCard">
                <div id="allmap" style="width: 100%; height: 400px"></div>
            </el-card>
            <el-card class="innerCard">
                <div class="Dataview">
                    <div class="eChartsBut">
                        <el-button
                            size="mini"
                            type="primary"
                            :disabled="!this.isDynamic || !this.isEnd"
                            @click="lineStart()"
                            >播放</el-button
                        >
                        <el-button
                            size="mini"
                            type="primary"
                            :disabled="!this.isDynamic || this.isEnd"
                            @click="lineEnd()"
                            >暂停</el-button
                        >
                        <el-button size="mini" type="primary" @click="cutStatic()" v-if="this.isDynamic"
                            >静态图</el-button
                        >
                        <el-button size="mini" type="primary" @click="cutDynamic()" v-else>动态图</el-button>
                    </div>
                    <div id="eChartsDom" style="width: 100%; height: 400px"></div>
                </div>
            </el-card>
            <el-card class="innerCard">
                <div class="demo-image">
                    <div class="block">
                        <span class="demonstration">整体照片</span>
                        <el-image
                            class="imageClass"
                            :src="this.analyseEntiretyImges"
                            fit="contain"
                            :preview-src-list="srcList"
                        ></el-image>
                    </div>

                    <div class="block">
                        <span class="demonstration">编号照片</span>
                        <el-image
                            class="imageClass"
                            :src="this.analyseNoImges"
                            fit="contain"
                            :preview-src-list="srcList"
                        ></el-image>
                    </div>

                    <div class="block">
                        <span class="demonstration">不良照片</span>
                        <el-image
                            class="imageClass"
                            :src="this.analyseBadnessImges"
                            fit="contain"
                            :preview-src-list="srcList"
                        ></el-image>
                    </div>
                </div>
            </el-card>
            <el-card class="innerCard">
                <el-divider content-position="left">分析结果</el-divider>
                <span>饿了别叫妈, 叫饿了么</span>
                <el-divider content-position="left">建议</el-divider>
                <span>饿了别叫妈, 叫饿了么</span>
            </el-card>
        </el-drawer>

        <el-dialog title="故障报告" :visible.sync="report" width="60%" center @close="initReport">
            <div style="margin-bottom: 10px">
                <el-button
                    size="mini"
                    type="primary"
                    @click="createWord"
                    :disabled="completeLoad == 3"
                    :loading="reportLoading"
                    >生成word</el-button
                >
                <el-button size="mini" type="primary" @click="exportWord" :disabled="completeLoad != 3"
                    >下载word</el-button
                >
                <el-button size="mini" type="primary" @click="outclick">生成pdf(打印)</el-button>
            </div>
            <div class="reportPrint" id="reportPrint" ref="reportPrint">
                <table class="tableClass">
                    <tr>
                        <td class="tdClass" align="center" colspan="8">
                            <span class="spanClass">超声波局部放电检验报告</span>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="243" colspan="2">检测日期</td>
                        <td class="tdClass" align="center" width="243" colspan="2">
                            {{ this.reportData.time }}
                        </td>
                        <td class="tdClass" align="center" width="242" colspan="2">检测人员</td>
                        <td class="tdClass" align="center" width="242" colspan="2">123</td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="243" colspan="2">线路名称</td>
                        <td class="tdClass" align="center" width="243" colspan="2">
                            {{ this.reportData.stationname }}
                        </td>
                        <td class="tdClass" align="center" width="242" colspan="2">线路编号</td>
                        <td class="tdClass" align="center" width="242" colspan="2">
                            {{ this.reportData.stationnumber }}
                        </td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="243" colspan="2">设备种类</td>
                        <td class="tdClass" align="center" width="243" colspan="2">123</td>
                        <td class="tdClass" align="center" width="242" colspan="2">缺陷程度</td>
                        <td class="tdClass" align="center" width="242" colspan="2">
                            {{ this.reportData.defectlevel }}
                        </td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="90" colspan="1">距离(m)</td>
                        <td class="tdClass" align="center" width="153" colspan="1">123</td>
                        <td class="tdClass" align="center" width="90" colspan="1">温度(℃)</td>
                        <td class="tdClass" align="center" width="153" colspan="1">
                            {{ this.reportData.temperature }}
                        </td>
                        <td class="tdClass" align="center" width="90" colspan="1">湿度(%)</td>
                        <td class="tdClass" align="center" width="152" colspan="1">
                            {{ this.reportData.humidity }}
                        </td>
                        <td class="tdClass" align="center" width="90" colspan="1">地域特征</td>
                        <td class="tdClass" align="center" width="152" colspan="1">123</td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="90" colspan="1">经度</td>
                        <td class="tdClass" align="center" width="153" colspan="1">
                            {{ this.reportData.longitude }}
                        </td>
                        <td class="tdClass" align="center" width="90" colspan="1">纬度</td>
                        <td class="tdClass" align="center" width="153" colspan="1">
                            {{ this.reportData.latitude }}
                        </td>
                        <td class="tdClass" align="center" width="90" colspan="1">最大dB值</td>
                        <td class="tdClass" align="center" width="152" colspan="1">
                            {{ this.reportData.maxdb }}
                        </td>
                        <td class="tdClass" align="center" width="90" colspan="1">平均dB值</td>
                        <td class="tdClass" align="center" width="152" colspan="1">
                            {{ this.reportData.avedb }}
                        </td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="90" colspan="1">诊断建议</td>
                        <td class="tdClass" style="padding-left: 10px" align="left" width="880" colspan="7">
                            123
                        </td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="970" colspan="8">
                            <div id="eChartsB" style="width: 100%; height: 320px"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="970" colspan="8">检测位置信息</td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="970" colspan="8">
                            <!-- <div id="reportMap" style="width: 100%; height: 325px"></div> -->
                            <el-image :src="mapSrc">
                                <div slot="error" class="image-slot">
                                    <i class="el-icon-picture-outline"></i>
                                </div>
                            </el-image>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="486" colspan="4">杆塔编号照片</td>
                        <td class="tdClass" align="center" width="484" colspan="4">杆塔局部照片</td>
                    </tr>
                    <tr v-for="(imges, id) in this.entiretyAndNo" v-bind:key="id">
                        <td class="tdClass" align="center" width="486" height="320px" colspan="4">
                            <el-image :src="imges.no">
                                <div slot="error" class="image-slot">
                                    <i class="el-icon-picture-outline"></i>
                                </div>
                            </el-image>
                        </td>
                        <td class="tdClass" align="center" width="486" height="320px" colspan="4">
                            <el-image :src="imges.entirety">
                                <div slot="error" class="image-slot">
                                    <i class="el-icon-picture-outline"></i>
                                </div>
                            </el-image>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdClass" align="center" width="486" colspan="4">杆塔缺陷照片</td>
                    </tr>
                    <tr v-for="imges in this.reportBadnessImges" v-bind:key="imges">
                        <td class="tdClass" align="center" width="486" height="320px" colspan="4">
                            <el-image :src="imges"></el-image>
                        </td>
                    </tr>
                </table>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { request } from '../../src/network/request.js'
import { map } from '../network/map/map.js'
import * as echarts from 'echarts'
import Print from '@/network/print/breakDownPrint.js'
import docxtemplater from 'docxtemplater'
import PizZip from 'pizzip'
import JSZipUtils from 'jszip-utils'
import { saveAs } from 'file-saver'
import Vue from 'vue'
Vue.use(Print) // 注册
export default {
  props: ['index', 'area'],
  data () {
    return {
      mapId: 'BMap-' + parseInt(Date.now() + Math.random()),
      isEnd: false,
      loading: false,
      createBut: false,
      isShowWord: false,
      reportLoading: false,
      reportTimeOut: null,
      timer: null,
      dataURL1: '',
      analyse: false,
      completeLoad: 0,
      report: false,
      isDynamic: true,
      analyseEntiretyImges: null,
      analyseNoImges: null,
      reportEntiretyImges: [],
      reportNoImges: [],
      reportBadnessImges: [],
      entiretyAndNo: [],
      badImagesWord: [],
      NoImagesWord: [],
      entiretyImagesWord: [],
      analyseBadnessImges: null,
      centerDialogVisible: false,
      editVisible: false,
      abnormityId: null,
      myChartsUrl: null,
      mapImage: null,
      mapSrc: null,
      mapBase64: null,
      line: '',
      reEntiretyImges: [],
      reNoImges: [],
      reBadnessImges: [],
      entiretyImges: [],
      NoImges: [],
      badnessImges: [],
      entiretyImges1: [],
      NoImges1: [],
      badnessImges1: [],
      dialogImageUrl: '',
      dialogNo: false,
      dialogEntirety: false,
      dialogBadness: false,
      data: [],
      xdata: [],
      textarea: '',
      optionDynamic: {},
      optionStatic: {},
      myChart: null,
      myChartB: null,
      editData: {},
      editData1: {},
      analyseData: {},
      reportData: {},
      srcList: [],
      // 每页显示数
      pageSize: 0,
      // 当前页码
      currentPage: 1,
      // 默认数据总数
      totalCount: 0,
      // editParams: {
      //   lineName: null,
      //   lineNo: null,
      //   lineLevel: null,
      //   areaFeature: null,
      //   kind: null,
      //   state: null,
      //   distance: null,
      //   people: null,
      // },
      paramsList: {
        kind: '',
        state: '',
        lineNameInput: '',
        lineNoInput: '',
        flawInput: '',
        dateTimeInput: null
      },
      kindOptions: [
        {
          value: '选项1',
          label: '黄金糕'
        },
        {
          value: '选项2',
          label: '双皮奶'
        },
        {
          value: '选项3',
          label: '蚵仔煎'
        },
        {
          value: '选项4',
          label: '龙须面'
        },
        {
          value: '选项5',
          label: '北京烤鸭'
        }
      ],
      stateOptions: [
        {
          value: '选项1',
          label: '黄金糕'
        },
        {
          value: '选项2',
          label: '双皮奶'
        },
        {
          value: '选项3',
          label: '蚵仔煎'
        },
        {
          value: '选项4',
          label: '龙须面'
        },
        {
          value: '选项5',
          label: '北京烤鸭'
        }
      ],
      tableData: []
    }
  },
  methods: {
    initMap (data) {
      map('tnXg5saO2k00pAboF709MRHqERbMKiLm')
        .then(() => {
          // 百度地图API功能
          // eslint-disable-next-line no-undef
          var topLeftControl = new BMap.ScaleControl({
            // eslint-disable-next-line no-undef
            anchor: BMAP_ANCHOR_TOP_LEFT
          }) // 左上角，添加比例尺
          // eslint-disable-next-line no-undef
          var topLeftNavigation = new BMap.NavigationControl() // 左上角，添加默认缩放平移控件

          // eslint-disable-next-line no-undef
          var map = new BMap.Map('allmap') // 创建Map实例
          // eslint-disable-next-line no-undef
          var point = new BMap.Point(data.longitude, data.latitude)
          map.centerAndZoom(point, 16) // 初始化地图,设置中心点坐标和地图级别
          // 添加地图类型控件
          map.addControl(
            // eslint-disable-next-line no-undef
            new BMap.MapTypeControl({
              // eslint-disable-next-line no-undef
              mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP]
            })
          )
          map.addControl(topLeftControl)
          map.addControl(topLeftNavigation)
          // 创建点标记
          // eslint-disable-next-line no-undef
          var marker1 = new BMap.Marker(point)
          // 在地图上添加点标记
          map.addOverlay(marker1)
          var text = '平均dB值：' + data.avedb + '<br>缺陷程度：' + data.defectlevel + '(未处理)'
          // eslint-disable-next-line no-undef
          var label = new BMap.Label(text)
          label.setPosition(point)
          map.addOverlay(label)
          // eslint-disable-next-line no-undef
          var infoWindow = new BMap.InfoWindow(data.stationname + ':' + data.stationnumber) // 创建信息窗口对象
          marker1.addEventListener('click', function () {
            this.openInfoWindow(infoWindow) // 开启信息窗口
          })
          map.setCurrentCity('郑州') // 设置地图显示的城市 此项是必须设置的
          map.enableScrollWheelZoom(true) // 开启鼠标滚轮缩放
        })
        .catch(() => {
          this.$message.error('地图加载失败！')
        })
    },

    // 报告页面地图
    reportMap (data) {
      map('tnXg5saO2k00pAboF709MRHqERbMKiLm')
        .then(() => {
          // 百度地图API功能
          // eslint-disable-next-line no-undef
          var map = new BMap.Map('reportMap') // 创建Map实例
          // eslint-disable-next-line no-undef
          var point = new BMap.Point(data.longitude, data.latitude)
          map.centerAndZoom(point, 16) // 初始化地图,设置中心点坐标和地图级别
          // 添加地图类型控件
          map.addControl(
            // eslint-disable-next-line no-undef
            new BMap.MapTypeControl({
              // eslint-disable-next-line no-undef
              mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP]
            })
          )
          // 创建点标记
          // eslint-disable-next-line no-undef
          var marker1 = new BMap.Marker(point)
          // 在地图上添加点标记
          map.addOverlay(marker1)
          var text = '平均dB值：' + data.avedb + '<br>缺陷程度：' + data.defectlevel + '(未处理)'
          // eslint-disable-next-line no-undef
          var label = new BMap.Label(text)
          label.setPosition(point)
          map.addOverlay(label)
          map.setCurrentCity('郑州') // 设置地图显示的城市 此项是必须设置的
          map.enableScrollWheelZoom(true) // 开启鼠标滚轮缩放
          // this.mapImage = map.getDataURL();
        })
        .catch(() => {
          this.$message.error('地图加载失败！')
        })
    },

    // 动态折线图
    eChartsDom () {
      var k = 0

      function randomData () {
        var value = Math.random() * 30
        return Math.round(value)
      }

      function xData () {
        if (k < 500) {
          k++
          // eslint-disable-next-line eqeqeq
        } else if (k == 500) {
          k = 1
        }
        return k
      }

      for (var i = 0; i < 500; i++) {
        this.data.push(randomData())
        this.xdata.push(xData())
      }

      if (this.isDynamic) {
        this.myChart = echarts.init(document.getElementById('eChartsDom'))

        this.optionDynamic = {
          title: {
            text: '增幅曲线',
            subtext: '纯属虚构'
          },
          legend: {
            data: ['dB值']
          },
          tooltip: {
            trigger: 'axis'
          },
          xAxis: {
            min: 1,
            boundaryGap: false,
            data: this.xdata,
            splitLine: {
              show: false
            }
          },
          dataZoom: [
            // 滑动条
            {
              show: false, // 是否显示滑动条
              type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
              startValue: 0, // 从头开始。
              endValue: 500 // 一次性展示500个。
            }
          ],
          yAxis: {
            min: 0,
            max: 240,
            axisLine: {
              show: true
            },
            axisTick: {
              show: true
            }
          },
          series: [
            {
              name: 'dB值',
              type: 'line',
              showSymbol: false,
              lineStyle: {
                width: 1.4,
                color: '#ee6666'
              },
              itemStyle: {
                color: '#ee6666'
              },
              data: this.data
            }
          ]
        }

        this.myChart.setOption(this.optionDynamic)
        this.timer = setInterval(this.linePlay, 75)
      } else {
        this.myChart = echarts.init(document.getElementById('eChartsDom'))

        this.optionStatic = {
          title: {
            text: '增幅曲线',
            subtext: '纯属虚构'
          },
          legend: {
            data: ['dB值']
          },
          tooltip: {
            trigger: 'axis'
          },
          xAxis: {
            data: this.xdata,
            splitLine: {
              show: false
            }
          },
          yAxis: {
            axisLine: {
              show: true
            },
            axisTick: {
              show: true
            }
          },
          series: [
            {
              name: 'dB值',
              type: 'line',
              showSymbol: false,
              lineStyle: {
                width: 1.4,
                color: '#ee6666'
              },
              data: this.data,
              markLine: {
                data: [{ type: 'average', name: '平均值' }]
              },
              itemStyle: {
                color: '#ee6666'
              }
            }
          ]
        }

        this.myChart.setOption(this.optionStatic)
      }
    },

    // 报告页面折线图
    eChartsB () {
      var k = 0
      var data = []
      var xdata = []

      function randomData () {
        var value = Math.random() * 30
        return Math.round(value)
      }

      function xData () {
        if (k < 500) {
          k++
        } else if (k === 500) {
          k = 1
        }
        return k
      }

      for (var i = 0; i < 500; i++) {
        data.push(randomData())
        xdata.push(xData())
      }
      this.myChartB = echarts.init(document.getElementById('eChartsB'))

      this.myChartB.setOption({
        title: {
          text: '增幅曲线'
        },
        legend: {
          data: ['dB值']
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          data: xdata,
          splitLine: {
            show: false
          }
        },
        yAxis: {
          axisLine: {
            show: true
          },
          axisTick: {
            show: true
          }
        },
        series: [
          {
            name: 'dB值',
            type: 'line',
            showSymbol: false,
            lineStyle: {
              width: 1.4,
              color: '#ee6666'
            },
            data: data,
            markLine: {
              data: [{ type: 'average', name: '平均值' }]
            },
            itemStyle: {
              color: '#ee6666'
            }
          }
        ]
      })
    },

    // 根据url转换base64
    getBase64 (url, callback) {
      let Img = new Image()
      let dataURL = ''
      Img.onload = function () {
        let canvas = document.createElement('canvas')
        let width = Img.width
        let height = Img.height
        canvas.width = width
        canvas.height = height
        canvas.getContext('2d').drawImage(Img, 0, 0, width, height)
        dataURL = canvas.toDataURL('image/png')
        return callback ? callback(dataURL) : null
      }
      Img.setAttribute('crossOrigin', 'anonymous')
      Img.src = url
    },

    // 根据url转换base64
    getMapBase64 () {
      request({
        method: 'get',
        url: 'jfDatainfo/mapBase64',
        params: {
          mapSrc: this.mapSrc
        }
      }).then(res => {
        this.mapBase64 = res.data
      })
    },

    /**
     * description： 导出echarts图片，格式转换
     */
    base64DataURLToArrayBuffer (dataURL) {
      const base64Regex = /^data:image\/(png|jpg|svg|svg\+xml);base64,/
      if (!base64Regex.test(dataURL)) {
        return false
      }
      const stringBase64 = dataURL.replace(base64Regex, '')
      let binaryString
      if (typeof window !== 'undefined') {
        binaryString = window.atob(stringBase64)
      } else {
        // eslint-disable-next-line node/no-deprecated-api
        binaryString = new Buffer(stringBase64, 'base64').toString('binary')
      }
      const len = binaryString.length
      const bytes = new Uint8Array(len)
      for (let i = 0; i < len; i++) {
        const ascii = binaryString.charCodeAt(i)
        bytes[i] = ascii
      }
      return bytes.buffer
    },

    /**
     * description： 导出文档
     */
    exportWord () {
      // 点击导出word
      this.myChartsUrl = this.myChartB.getDataURL()
      var ImageModule = require('docxtemplater-image-module-free')
      let _this = this
      let wordFile = '../../static/JfBreakDownWord.docx'
      let NoImagesWord = JSON.parse(JSON.stringify(_this.NoImagesWord))
      let entiretyImagesWord = JSON.parse(JSON.stringify(_this.entiretyImagesWord))
      let badImagesWord = JSON.parse(JSON.stringify(_this.badImagesWord))

      // 读取并获得模板文件的二进制内容
      JSZipUtils.getBinaryContent(wordFile, function (error, content) {
        // JfBreakDownWord.docx是模板。我们在导出的时候，会根据此模板来导出对应的数据
        // 抛出异常
        if (error) {
          throw error
        }

        // 图片处理
        let opts = {}
        opts.centered = true // 图片居中，在word模板中定义方式为{%%image}
        opts.fileType = 'docx'
        opts.getImage = function (chartId) {
          return _this.base64DataURLToArrayBuffer(chartId)
        }
        opts.getSize = function (img, tagValue, tagName) {
          // 自定义指定图像大小，此处可动态调试各别图片的大小
          if (tagName === 'src') return [250, 250]
          return [600, 300]
        }

        let imageModule = new ImageModule(opts)
        // 创建一个PizZip实例，内容为模板的内容
        let zip = new PizZip(content)
        // 创建并加载docxtemplater实例对象
        // eslint-disable-next-line new-cap
        let doc = new docxtemplater().loadZip(zip).attachModule(imageModule)

        // 设置模板变量的值
        // 如果包含异步数据，用resolveData，在回调之后再处理，如果都是同步数据，直接用setData就可以
        doc.setData({
          myCharts: _this.myChartsUrl, // 获取echarts图片
          mapImage: _this.mapBase64, // 获取map图片
          no: NoImagesWord,
          entirety: entiretyImagesWord,
          badness: badImagesWord,
          time: _this.reportData.time,
          people: _this.reportData.maxdb,
          stationname: _this.reportData.stationname,
          stationnumber: _this.reportData.stationnumber,
          kind: _this.reportData.maxdb,
          defectlevel: _this.reportData.defectlevel,
          temperature: _this.reportData.temperature,
          humidity: _this.reportData.humidity,
          longitude: _this.reportData.longitude,
          latitude: _this.reportData.latitude,
          maxdb: _this.reportData.maxdb,
          avedb: _this.reportData.avedb,
          distance: _this.reportData.avedb,
          areaFeature: _this.reportData.avedb,
          suggest: _this.reportData.avedb
        })
        try {
          // 用模板变量的值替换所有模板变量
          doc.render()
        } catch (error) {
          // 抛出异常
          let e = {
            message: error.message,
            name: error.name,
            stack: error.stack,
            properties: error.properties
          }
          throw e
        }
        // 生成一个代表docxtemplater对象的zip文件（不是一个真实的文件，而是在内存中的表示）
        let out = doc.getZip().generate({
          type: 'blob',
          mimeType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
        })
        // 将目标文件对象保存为目标类型的文件，并命名
        saveAs(out, '超声波局部放电检验报告 ' + _this.reportData.time + '.docx')
      })
    },

    // 切换动态折线图
    cutDynamic () {
      this.data = []
      this.xdata = []
      this.isDynamic = true
      this.isEnd = false
      this.myChart.clear()
      this.myChart.dispose()
      this.$nextTick(function () {
        this.eChartsDom()
      })
    },

    // 切换静态折线图
    cutStatic () {
      this.data = []
      this.xdata = []
      this.isDynamic = false
      clearInterval(this.timer)
      this.myChart.clear()
      this.myChart.dispose()
      this.$nextTick(function () {
        this.eChartsDom()
      })
    },

    linePlay: function () {
      this.data.push(this.data.shift())
      this.xdata.push(this.xdata.shift())
      if (this.myChart != null) {
        this.myChart.setOption(this.optionDynamic)
      }
    },

    // 动态折线图播放
    lineStart () {
      this.isEnd = false
      this.timer = setInterval(this.linePlay, 75)
    },

    // 动态折线图暂停
    lineEnd () {
      this.isEnd = true
      clearInterval(this.timer)
    },

    // 提交编辑页面
    submitEdit () {
      let formDate = new FormData()
      let resEntiretyImges = []
      let resNoImges = []
      let resBadnessImges = []
      this.entiretyImges.forEach(item => {
        if (item.status !== 'success') {
          formDate.append('entiretyImges', item.raw)
        } else {
          resEntiretyImges.push(item.url)
        }
      })
      this.NoImges.forEach(item => {
        if (item.status !== 'success') {
          formDate.append('NoImges', item.raw)
        } else {
          resNoImges.push(item.url)
        }
      })
      this.badnessImges.forEach(item => {
        if (item.status !== 'success') {
          formDate.append('badnessImges', item.raw)
        } else {
          resBadnessImges.push(item.url)
        }
      })
      formDate.append('resEntiretyImges', resEntiretyImges)
      formDate.append('resBadnessImges', resBadnessImges)
      formDate.append('resNoImges', resNoImges)
      formDate.append('reEntiretyImges', this.reEntiretyImges)
      formDate.append('reNoImges', this.reNoImges)
      formDate.append('reBadnessImges', this.reBadnessImges)
      formDate.append('stationname', this.editData.stationname)
      formDate.append('stationnumber', this.editData.stationnumber)
      formDate.append('id', this.editData.id)
      formDate.append('lineLevel', this.editData.lineLevel)
      formDate.append('areaFeature', this.editData.areaFeature)
      formDate.append('kind', this.editData.kind)
      formDate.append('state', this.editData.state)
      formDate.append('distance', this.editData.distance)
      formDate.append('people', this.editData.people)

      request({
        method: 'post',
        url: 'jfDatainfo/breakDownSubmit',
        // headers: { "Content-Type": "multipart/form-data" },
        data: formDate
      }).then(res => {
        if (res.status === 200) {
          this.$message.success('保存成功！')
          this.editVisible = false
          // this.initData();
          this.handleSizeChange(5)
        } else {
          this.$message.error('保存失败！')
        }
      })
    },

    // 文件上传前的钩子，数为上传的文件
    beforeUpload (file) {
      // 判断文件的类型
      const isImage = file.type.indexOf('image') !== -1

      if (!isImage) {
        this.$message.error('请上传图片！')
        return false
      }
    },

    // 每页显示数据量变更
    handleSizeChange (val) {
      this.pageSize = val
      this.searchData()
    },

    // 打印
    outclick () {
      this.$print(this.$refs.reportPrint)
    },

    // 打开编辑页面
    openEdit (index, data) {
      this.editVisible = true
      let editData = Object.assign({}, data)
      let editData1 = Object.assign({}, data)
      this.editData = editData
      this.editData1 = editData1

      if (data.wholeimg != null && data.wholeimg !== '') {
        this.entiretyImges = data.wholeimg.split(',')
        for (let i = 0; i < this.entiretyImges.length; i++) {
          var entiretyImges = {}
          entiretyImges.name = this.entiretyImges[i].substr(this.entiretyImges[i].lastIndexOf('/') + 1)
          entiretyImges.url = this.entiretyImges[i]
          this.entiretyImges[i] = entiretyImges
        }
        this.entiretyImges1 = this.entiretyImges
      }

      if (data.badimg != null && data.badimg !== '') {
        this.badnessImges = data.badimg.split(',')
        for (let i = 0; i < this.badnessImges.length; i++) {
          var badnessImges = {}
          badnessImges.name = this.badnessImges[i].substr(this.badnessImges[i].lastIndexOf('/') + 1)
          badnessImges.url = this.badnessImges[i]
          this.badnessImges[i] = badnessImges
        }
        this.badnessImges1 = this.badnessImges
      }

      if (data.numberimg != null && data.numberimg !== '') {
        this.NoImges = data.numberimg.split(',')
        for (let i = 0; i < this.NoImges.length; i++) {
          var NoImges = {}
          NoImges.name = this.NoImges[i].substr(this.NoImges[i].lastIndexOf('/') + 1)
          NoImges.url = this.NoImges[i]
          this.NoImges[i] = NoImges
        }
        this.NoImges1 = this.NoImges
      }
    },

    // 关闭编辑页面
    closeEdit () {
      this.editVisible = false
    },

    // 整体照片添加
    entiretyChange (file, fileList) {
      if (file.raw.type.indexOf('image') !== -1) {
        this.entiretyImges = fileList
      } else {
        this.$message.error('只能上传图片！')
        fileList.splice(fileList.indexOf(file), 1)
      }
    },

    // 不良照片添加
    badnessChange (file, fileList) {
      if (file.raw.type.indexOf('image') !== -1) {
        this.badnessImges = fileList
      } else {
        this.$message.error('只能上传图片！')
        fileList.splice(fileList.indexOf(file), 1)
      }
    },

    // 编号照片添加
    NoChange (file, fileList) {
      if (file.raw.type.indexOf('image') !== -1) {
        this.NoImges = fileList
      } else {
        this.$message.error('只能上传图片！')
        fileList.splice(fileList.indexOf(file), 1)
      }
    },

    // 删除整体照片
    entiretyRemove (file, fileList) {
      this.entiretyImges = fileList
      if (file.status === 'success') {
        this.reEntiretyImges.push(file.url)
      }
    },

    // 删除不良照片
    badnessRemove (file, fileList) {
      this.badnessImges = fileList
      if (file.status === 'success') {
        this.reBadnessImges.push(file.url)
      }
    },

    // 删除编号照片
    NoRemove (file, fileList) {
      this.NoImges = fileList
      if (file.status === 'success') {
        this.reNoImges.push(file.url)
      }
    },

    // 编辑页面初始化
    initEdit (value) {
      if (value === 0) {
        this.NoImges = []
        this.entiretyImges = []
        this.badnessImges = []
        this.reEntiretyImges = []
        this.reNoImges = []
        this.reBadnessImges = []
        this.editData = {}
        this.editData1 = {}
      } else {
        this.entiretyImges = this.entiretyImges1
        this.NoImges = this.NoImges1
        this.badnessImges = this.badnessImges1
        let copy = Object.assign({}, this.editData1)
        this.editData = copy
        this.reEntiretyImges = []
        this.reNoImges = []
        this.reBadnessImges = []
      }
    },

    // 页码变更
    handleCurrentChange (val) {
      this.currentPage = val
      this.searchData()
    },

    handlePictureCardPreview1 (file) {
      this.dialogImageUrl = file.url
      this.dialogNo = true
    },

    handlePictureCardPreview2 (file) {
      this.dialogImageUrl = file.url
      this.dialogEntirety = true
    },

    handlePictureCardPreview3 (file) {
      this.dialogImageUrl = file.url
      this.dialogBadness = true
    },

    // 异常处理页面
    abnormity (id) {
      this.abnormityId = id
      this.centerDialogVisible = true
    },

    // 异常处理
    abnormityButton () {
      if (this.textarea != null && this.textarea !== '') {
        this.textarea = ''
        this.centerDialogVisible = false
      } else {
        this.$message.error('请输入处理方式(解决方案)')
      }
    },

    // 异常处理页面初始化
    initAbnormity () {
      this.textarea = ''
    },

    analyseOpen (index, row) {
      this.analyseEntiretyImges = null
      this.analyseNoImges = null
      this.analyseBadnessImges = null
      if (row.wholeimg != null && row.wholeimg !== '') {
        this.analyseEntiretyImges = row.wholeimg.split(',')[0]
      }
      if (row.numberimg != null && row.numberimg !== '') {
        this.analyseNoImges = row.numberimg.split(',')[0]
      }
      if (row.badimg != null && row.badimg !== '') {
        this.analyseBadnessImges = row.badimg.split(',')[0]
      }
      this.srcList.push(this.analyseEntiretyImges)
      this.srcList.push(this.analyseNoImges)
      this.srcList.push(this.analyseBadnessImges)
      this.analyseData = row
      this.initMap(row)
      this.analyse = true
      this.$nextTick(function () {
        this.eChartsDom()
      })
    },

    createWord () {
      if (this.createBut === false) {
        let _this = this
        this.reportLoading = true
        setTimeout(function () {
          if (_this.completeLoad !== 3) {
            _this.$message.error('word生成失败！请重新上传图片或编辑数据')
          }
        }, 10000)
        this.createBut = true
        this.reportEntiretyImges.forEach(item => {
          let entirety = {
            src: ''
          }
          this.getBase64(item, res => {
            entirety.src = res
            this.entiretyImagesWord.push(entirety)
          })
        })

        this.reportNoImges.forEach(item => {
          let no = {
            src: ''
          }
          this.getBase64(item, res => {
            no.src = res
            this.NoImagesWord.push(no)
          })
        })

        this.reportBadnessImges.forEach(item => {
          let badness = {
            src: ''
          }
          this.getBase64(item, res => {
            badness.src = res
            this.badImagesWord.push(badness)
          })
        })

        this.getMapBase64()
      }
    },

    reportOpen (index, row) {
      this.report = true
      this.reportData = row
      this.completeLoad = 0
      this.entiretyAndNo = []
      this.$nextTick(function () {
        this.eChartsB()
      })
      if (row.wholeimg != null && row.wholeimg !== '') {
        this.reportEntiretyImges = row.wholeimg.split(',')
      }
      if (row.numberimg != null && row.numberimg !== '') {
        this.reportNoImges = row.numberimg.split(',')
      }
      if (row.badimg != null && row.badimg !== '') {
        this.reportBadnessImges = row.badimg.split(',')
      }

      if (this.reportEntiretyImges != null && this.reportNoImges != null) {
        if (this.reportEntiretyImges.length >= this.reportNoImges.length) {
          this.reportEntiretyImges.forEach(item => {
            var entiretyAndNo = {}
            entiretyAndNo.entirety = item
            entiretyAndNo.no = null
            this.entiretyAndNo.push(entiretyAndNo)
          })
          for (let i = 0; i < this.reportNoImges.length; i++) {
            this.entiretyAndNo[i].no = this.reportNoImges[i]
          }
        } else {
          this.reportNoImges.forEach(item => {
            var entiretyAndNo = {}
            entiretyAndNo.entirety = null
            entiretyAndNo.no = item
            this.entiretyAndNo.push(entiretyAndNo)
          })
          for (let i = 0; i < this.reportEntiretyImges.length; i++) {
            this.entiretyAndNo[i].entirety = this.reportEntiretyImges[i]
          }
        }
      }
      this.mapSrc =
        'http://api.map.baidu.com/staticimage/v2?ak=tnXg5saO2k00pAboF709MRHqERbMKiLm&center=' +
        row.longitude +
        ',' +
        row.latitude +
        '&markers=' +
        row.longitude +
        ',' +
        row.latitude +
        '&labels=' +
        row.longitude +
        ',' +
        row.latitude +
        '&labelStyles=平均dB值-' +
        row.avedb +
        '缺陷程度-' +
        row.defectlevel +
        '(未处理),0,14,0xFF0000,0xffffff,0&width=960&height=325&zoom=18'
    },

    initDrawer () {
      this.isEnd = false
      if (this.timer != null) {
        clearInterval(this.timer)
        this.timer = null
      }
      this.analyse = false
      this.analyseData = {}
      this.isDynamic = true
      this.analyseBadnessImges = null
      this.analyseNoImges = null
      this.analyseEntiretyImges = null
      this.data = []
      this.xdata = []
      this.optionDynamic = {}
      this.optionStatic = {}
      if (this.myChart != null) {
        this.myChart.clear()
        this.myChart.dispose()
        this.myChart = null
      }
    },

    initReport () {
      this.mapSrc = null
      this.createBut = false
      this.reportData = {}
      this.reportEntiretyImges = []
      this.reportNoImges = []
      this.reportBadnessImges = []
      this.entiretyAndNo = []
      this.badImagesWord = []
      this.entiretyImagesWord = []
      this.NoImagesWord = []
      if (this.myChartB != null) {
        this.myChartB.clear()
        this.myChartB.dispose()
        this.myChartB = null
      }
    },

    initData () {
      this.analyse = false
      this.report = false
      this.analyseData = {}
      this.reportData = {}
      this.srcList = []
      this.pageSize = 0
      this.currentPage = 1
      this.totalCount = 0
      this.paramsList = {
        kind: '',
        state: '',
        lineNameInput: '',
        lineNoInput: '',
        flawInput: '',
        dateTimeInput: null
      }
    },

    searchData () {
      this.loading = true
      request({
        method: 'get',
        url: 'jfDatainfo/searchBreakDownList',
        params: {
          kind: this.paramsList.kind,
          state: this.paramsList.state,
          lineNameInput: this.paramsList.lineNameInput,
          lineNoInput: this.paramsList.lineNoInput,
          flawInput: this.paramsList.flawInput,
          startTime: this.paramsList.dateTimeInput != null ? this.paramsList.dateTimeInput[0] : null,
          endTime: this.paramsList.dateTimeInput != null ? this.paramsList.dateTimeInput[1] : null,
          index: this.index,
          pageSize: this.pageSize,
          currentPage: this.currentPage,
          area: this.area,
          userId: JSON.parse(localStorage.getItem('user')).id
        }
      }).then(res => {
        this.totalCount = res.data.totalCount
        this.tableData = res.data.data
        this.loading = false
      })
    }
  },

  watch: {
    index (newValue, oldValue) {
      this.initData()
      this.handleSizeChange(5)
    },
    badImagesWord (newValue, oldValue) {
      if (newValue.length === this.reportBadnessImges.length) {
        this.completeLoad += 1
      }
    },
    NoImagesWord (newValue, oldValue) {
      if (newValue.length === this.reportNoImges.length) {
        this.completeLoad += 1
      }
    },
    entiretyImagesWord (newValue, oldValue) {
      if (newValue.length === this.reportEntiretyImges.length) {
        this.completeLoad += 1
      }
    },
    completeLoad (newValue, oldValue) {
      if (newValue === 3) {
        this.reportLoading = false
      }
    }
  },

  created () {
    var userRoles = []
    userRoles = localStorage.getItem('rolelist').split(',')
    if (userRoles.indexOf('1') === -1 && userRoles.indexOf('2') === -1) {
      this.area = null
    }
    this.initData()
    this.handleSizeChange(5)
  }
}
</script>
<style>
/* .box-card {
  margin: 20px;
} */

.eChartsBut {
    margin-left: 800px;
}

.el-drawer__header {
    margin-bottom: 10px;
}

.imageClass {
    width: 275px;
}

.elClass {
    padding-left: 125px;
}

.innerCard {
    margin: 10px;
}

.editInputClass {
    margin-left: 25px;
}

.selectStyle {
    width: 200px;
}

.colClass {
    margin: 10px 0;
}

/* [class*="el-col-"] {
  margin: 10px 0;
} */

.inputStyle {
    width: 350px;
}

.el-input {
    width: 200px;
}

.el-pagination {
    margin-top: 20px;
}

.editFont {
    line-height: 40px;
}

/* 去掉音频标签黑色边框 */
audio:focus {
    outline: none;
}

/* 去掉抽屉黑色边框 */
.el-drawer:focus {
    outline: none;
}

/* 去掉抽屉头部黑色边框 */
.el-drawer__header > :first-child:focus {
    outline: none;
}

.imgStyle {
    margin-top: 20px;
}

.trueStyle {
    background-image: url('../assets/true.png');
    background-repeat: no-repeat;
    width: 100px;
    height: 35px;
    margin-left: 13px;
}

.imgFontStyle {
    float: left;
    line-height: 150px;
    font-size: 16px;
}

.warningInput {
    background-color: rgba(255, 149, 0, 1);
    border-color: rgba(255, 149, 0, 1);
    color: #ffffff;
}

/* 去掉标签页蓝色边框 */
.el-tabs__item:focus.is-active.is-focus:not(:active) {
    -webkit-box-shadow: none;
    box-shadow: none;
}

.dangerInput {
    background-color: rgba(255, 0, 0, 1);
    border-color: rgba(255, 0, 0, 1);
    color: #ffffff;
}

.demo-image {
    text-align: center;
}

.tableClass {
    width: 100%;
    border-top: 1px solid #999;
    border-left: 1px solid #999;
    border-spacing: 0; /* 去掉边框间隙 */
}

.tdClass {
    border-bottom: 1px solid #999;
    border-right: 1px solid #999;
    font-size: 16px;
    padding: 10px 0;
}

.spanClass {
    font-size: 26px;
}

.demo-image .block,
.demo-image__error .block,
.demo-image__placeholder .block {
    padding: 30px 0;
    text-align: center;
    border-right: 1px solid #eff2f6;
    display: inline-block;
    width: 320px;
    box-sizing: border-box;
    vertical-align: top;
}

.demo-image .demonstration,
.demo-image__error .demonstration,
.demo-image__placeholder .demonstration {
    display: block;
    color: #8492a6;
    font-size: 14px;
    margin-bottom: 20px;
}

.demo-image .block:last-child,
.demo-image__error .block:last-child,
.demo-image__placeholder .block:last-child {
    border-right: none;
}

/* 1. 添加抽屉滚动条 */
.el-drawer__body {
    overflow: auto;
}

/* 2.隐藏抽屉滚动条样式 */
.el-drawer__container ::-webkit-scrollbar {
    display: none;
}
</style>
