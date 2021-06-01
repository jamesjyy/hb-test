package cn.best.approveservice.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        String Md5Pass = new Md5Hash("root").toHex();
        System.out.println(Md5Pass);
        String ss="abcd?1";
        System.out.println(ss.substring(1));

        System.out.println(new Date().toString());



    }




}
