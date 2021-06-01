package cn.best.approveservice.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户表(Userinfo)实体类
 *
 */
public class Userinfo implements Serializable {
    private static final long serialVersionUID = 365041892656218933L;

    private Integer id;

    private String recordcreatetime;
    /**
     * 取号窗口
     */
    private Integer callwindowid;
    /**
     * 登录名
     */
    private String loginname;
    /**
     * 用户密码 MD5加密
     */
    private String password;
    /**
     * 用户名
     */
    private String username;
    /**
     * 职务
     */
    private String job;
    /**
     * 工号
     */
    private String jobno;
    /**
     * 用户所在地区
     */
    private Integer regid;
    /**
     * 用户所在部门
     */
    private Integer depid;
    /**
     * 启用状态  参见数据字典enabled
     */
    private String enabled;
    /**
     * 拼音 全拼
     */
    private String allspelling;
    /**
     * 拼音 首字母
     */
    private String firstspelling;
    /**
     * 手机号码
     */
    private String mobilephone;
    /**
     * 用户特殊权限，请参考字典表userSpeRoute
     */
    private String usersperoute;
    /**
     * 考勤号
     */
    private String checknum;
    /**
     * 人员照片关联upfiles表ID
     */
    private Integer upfilesid;
    /**
     * 省中心人员编号
     */
    private String upworkercode;
    /**
     * 办公电话
     */
    private String officephone;
    /**
     * 身份证号
     */
    private String cardno;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否值班人员0不是1是
     */
    private String isworkuser;

    //以下非表中字段
    // 所属部门
    private String depName;
    // 所属部门类别
    private String depType;
    // 所属地区
    private String regName;
    private String uporgancode;
    // 所属地区
    private String areaCode;
    private String areaName;
    // 人员图片
    private String upfilesName;

    // 人员图片路径
    private String upfilesPath;

    // 人员图片UUID
    private String upfilesUuid;
    //角色集合
    private List<TbSysRole> tbSysRoleList;

    // 角色id字符串，用,拼接
    private String roleIds;

    public String getRoleIds() { return roleIds;}

    public void setRoleIds(String roleIds) { this.roleIds = roleIds; }

    public List<TbSysRole> getTbSysRoleList() {
        return tbSysRoleList;
    }

    public void setTbSysRoleList(List<TbSysRole> tbSysRoleList) {
        this.tbSysRoleList = tbSysRoleList;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepType() {
        return depType;
    }

    public void setDepType(String depType) {
        this.depType = depType;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getUporgancode() {
        return uporgancode;
    }

    public void setUporgancode(String uporgancode) {
        this.uporgancode = uporgancode;
    }

    public String getUpfilesName() {
        return upfilesName;
    }

    public void setUpfilesName(String upfilesName) {
        this.upfilesName = upfilesName;
    }

    public String getUpfilesPath() {
        return upfilesPath;
    }

    public void setUpfilesPath(String upfilesPath) {
        this.upfilesPath = upfilesPath;
    }

    public String getUpfilesUuid() {
        return upfilesUuid;
    }

    public void setUpfilesUuid(String upfilesUuid) {
        this.upfilesUuid = upfilesUuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecordcreatetime() {
        return recordcreatetime;
    }

    public void setRecordcreatetime(String recordcreatetime) {
        this.recordcreatetime = recordcreatetime;
    }

    public Integer getCallwindowid() {
        return callwindowid;
    }

    public void setCallwindowid(Integer callwindowid) {
        this.callwindowid = callwindowid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobno() {
        return jobno;
    }

    public void setJobno(String jobno) {
        this.jobno = jobno;
    }

    public Integer getRegid() {
        return regid;
    }

    public void setRegid(Integer regid) {
        this.regid = regid;
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getAllspelling() {
        return allspelling;
    }

    public void setAllspelling(String allspelling) {
        this.allspelling = allspelling;
    }

    public String getFirstspelling() {
        return firstspelling;
    }

    public void setFirstspelling(String firstspelling) {
        this.firstspelling = firstspelling;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getUsersperoute() {
        return usersperoute;
    }

    public void setUsersperoute(String usersperoute) {
        this.usersperoute = usersperoute;
    }

    public String getChecknum() {
        return checknum;
    }

    public void setChecknum(String checknum) {
        this.checknum = checknum;
    }

    public Integer getUpfilesid() {
        return upfilesid;
    }

    public void setUpfilesid(Integer upfilesid) {
        this.upfilesid = upfilesid;
    }

    public String getUpworkercode() {
        return upworkercode;
    }

    public void setUpworkercode(String upworkercode) {
        this.upworkercode = upworkercode;
    }

    public String getOfficephone() {
        return officephone;
    }

    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsworkuser() {
        return isworkuser;
    }

    public void setIsworkuser(String isworkuser) {
        this.isworkuser = isworkuser;
    }

}
