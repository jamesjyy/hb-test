package cn.best.approveservice;

import cn.best.approveservice.common.utils.DateTimeUtil;
import cn.best.approveservice.entity.Userinfo;
import cn.best.approveservice.service.UserinfoService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApproveserviceApplicationTests {
    @Autowired
    StringEncryptor encryptor;

//    @Autowired
//    UserinfoService userinfoService;

    @Test
    void contextLoads() {
        //加密 jdbc:oracle:thin:@221.14.138.147:39702:ORCL11G
        // 221.14.138.143:30123    账号  root   密码
        String username = encryptor.encrypt("jdbc:mysql://localhost:3306/flowfilesmgr?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
//        username = encryptor.encrypt("hnbnt63157pT.");
        System.out.println(username);
        //解密
        /*encryptor.decrypt("uaNBj4ZmzCD83uedRYUXqQ==")*/

//        Userinfo userinfo=new Userinfo();
//        userinfo.setLoginname("admin");
//        userinfo.setUsername("admin");
//        userinfo.setDepid(1);
//        userinfo.setRegid(1);
//        userinfo.setPassword(new Md5Hash("admin@123").toHex());
//        userinfo.setRecordcreatetime(DateTimeUtil.getNowDateStr(1));
//        userinfo.setEnabled("1");
//        userinfo.setAllspelling("guanliyuan");
//        userinfo.setFirstspelling("gly");
//        Userinfo i=userinfoService.insert(userinfo);

//        System.out.println(i);
    }

}
