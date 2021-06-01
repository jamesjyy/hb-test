package cn.best.approveservice.common.utils;

import cn.best.approveservice.entity.UpFiles;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	/**
	 * 数组合并 多个数组合并成一个
	 * 
	 * @param strs
	 * @return
	 */
	public static Object addArray(Object[]... strs) {
		Object[] rs = (Object[]) new Object[0];
		for (Object[] str : strs) {
			rs = (Object[]) ArrayUtils.addAll(rs, str);
		}
		return rs;
	}

	/**
	 * 数组合并 多个数组合并成一个
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] addArray(String[]... strs) {
		String[] rs = (String[]) new String[0];
		for (String[] str : strs) {
			rs = (String[]) ArrayUtils.addAll(rs, str);
		}
		return rs;
	}

	/**
	 * 去掉空格 回车 换行
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 替换字符串中的单引号和双引号
	 * @param str
	 * @return
	 */
	public static String replaceDSYin(String str) {
		str = str.replaceAll("\"", "\\\\\"");
		str = str.replaceAll("\'", "\\\\\'");
		return str;
	}

	public static int getDyReadonly(String str) {
		int i = 0;
		try {
			i = str.split("LODOP.ADD_PRINT_TEXT").length - 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 判断对象是否为空，如果对象为ArrayList，且不为空，则还要判断是否有长度
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		List list = null;
		String str = null;
		boolean flag = false;
		String[] strs = null;
		Long l;
		if (obj == null) {
			flag = true;
		} else {
			if (obj.getClass().getSimpleName().equals("ArrayList")) {
				list = (ArrayList) obj;
				if (list.size() == 0) {
					flag = true;
				}
			}
			else if (obj.getClass().getSimpleName().equals("String[]")) {
				strs = (String[]) obj;
				if (strs.length == 1) {
					if (CommonUtil.isNull(strs[0])) {
						flag = true;
					}
				}
			}
			else if (obj.getClass().getSimpleName().equals("String")) {
				str = (String) obj;
				if (str.trim().equals("") || str.trim().equals("null") || str.trim().equals("NULL")) {
					flag = true;
				}
			}
			else if (obj.getClass().getSimpleName().equals("Long")) {
				l = (Long) obj;
				if (l == 0) {
					flag = true;
				}
			}
			else flag =isObjectFieldEmpty(obj);
		}
		return flag;
	}

	public static  boolean isObjectFieldEmpty(Object obj){
		Class<?> clazz=obj.getClass();  //得到类对象
		Field[] fs=clazz.getDeclaredFields(); //得到属性集合
		boolean flag=false;
		int nullCnt =0;
		for(Field field:fs){            //遍历属性
			field.setAccessible(true); //设置属性是可以访问的（私有的也可以）
			try {
				if(field.get(obj)==null||field.get(obj)==""||"null".equalsIgnoreCase(String.valueOf(field.get(obj)))){
					nullCnt++;
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(nullCnt==fs.length) flag=true;
		return flag;
	}

	/**
	 * 验证是否是正确的电话号码
	 * 
	 * @param phonenum
	 * @return
	 * @throws Exception
	 */
	public static boolean validatePhonenum(String phonenum) {
		String pat = "(^[0-9]{3,4}-[0-9]{3,8}$)|^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[1|5|7]|18[0|2|3|5|6|7|8|9])\\d{8}$";
		boolean flag = false;
		if (! CommonUtil.isNull(phonenum)) {
			if (phonenum.matches(pat)) {
				flag = true;
			}
		}
		return flag;
	}

	public static String getReportXX(String a, String b) {
		String rs = "0";
		if (b.equals("0")) {
			rs = "-";
		} else {
			rs = MathExtend.divide(MathExtend.multiply(a, "100"), b, 2);
		}
		return rs;
	}


	/**
	 * String参数转Integer对象
	 * @param str
	 * @return
	 */
	public static Integer stringToInteger(String str) {
		Integer obj = null;
		if ( CommonUtil.isNull(str)) {
			obj = null;
		} else {
			obj = new Integer(str);
		}
		return obj;
	}

	/**
	 * 生成缩略图
	 * @param ffmpegPath
	 * @param filePath
	 * @return
	 */
	public static boolean processImg(String ffmpegPath, String filePath, String uuid) {
		File file = new File(filePath);
		String desFileName = file.getPath().substring(0, file.getPath().lastIndexOf("\\"));
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegPath);
		commend.add("-i");
		commend.add(filePath);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("100");
		commend.add("-t");
		commend.add("0.001");
		commend.add("-s");
		commend.add("640x480");
		commend.add(desFileName + "\\" + uuid + ".jpg");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 从字典中获取复选框的选中值
	 * @param type 类别
	 * @param value 选中值
	 * @return
	 */
	/*public static String getZvalueByCheckBox(String type, String value) {
		StringBuffer str = new StringBuffer();
		List<DataDic> listTreeDataDic = DataDicContext.getInstance().listTreeDataDicByType(type);
		if (!CommonUtil.isNull(listTreeDataDic)) {
			for (int i = 0; i < listTreeDataDic.size(); i++) {
				DataDic dataDic = listTreeDataDic.get(i);
				if (!CommonUtil.isNull(value) && Arrays.asList(value.split(",")).contains(dataDic.getCode())) {
					str.append(dataDic.getZvalue());
					str.append("，");
				}
			}
		}
		String s = str.toString();
		if (s.length() > 0) {
			s = s.substring(0, s.lastIndexOf("，"));
		} else {
			s = "";
		}
		return s;
	}*/

	/**
	 * 压缩指定路径下的文件
	 */
	/*public static void createZip(String sorcePath, String destPath) {
		// 目标文件如果存在，先删除再压缩
		File destFile = new File(destPath);
		if (destFile.exists()) {
			destFile.delete();
		}
		// 文件源目录
		File srcdir = new File(sorcePath);
		if (srcdir.exists()) {
			Project prj = new Project();
			Zip zip = new Zip();
			zip.setProject(prj);
			zip.setDestFile(destFile);
			FileSet fileSet = new FileSet();
			fileSet.setProject(prj);
			fileSet.setDir(srcdir);
			zip.addFileset(fileSet);
			zip.execute();
		}
	}*/

	/**
	 * 金额小写转大写
	 * @param value
	 * @return
	 */
	public static String hangeToBig(String value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (Double.valueOf(value) * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串
		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分
		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}
		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
//	   public static String getClintIP(HttpServletRequest request)
//	   {
////		 String ip = request.getHeader("X-Forwarded-For");
////		 if ((!isNull(ip)) && (!"unKnown".equalsIgnoreCase(ip)))
////		 {
////		   int index = ip.indexOf(",");
////		   if (index != -1) {
////			 return ip.substring(0, index);
////		   }
////		   return ip;
////		 }
////		 ip = request.getHeader("X-Real-IP");
////		 if ((!isNull(ip)) && (!"unKnown".equalsIgnoreCase(ip))) {
////		   return ip;
////		 }
////		 return request.getRemoteAddr();
//		   String ipAddress = request.getHeader("x-forwarded-for");
//		   if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			   ipAddress = request.getHeader("Proxy-Client-IP");
//		   }
//		   if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			   ipAddress = request.getHeader("WL-Proxy-Client-IP");
//		   }
//		   if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//			   ipAddress = request.getRemoteAddr();
//			   if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
//				   //根据网卡取本机配置的IP
//				   InetAddress inet=null;
//				   try {
//					   inet = InetAddress.getLocalHost();
//				   } catch (UnknownHostException e) {
//					   e.printStackTrace();
//				   }
//				   ipAddress= inet.getHostAddress();
//			   }
//		   }
//		   //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//		   if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
//			   if(ipAddress.indexOf(",")>0){
//				   ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
//			   }
//		   }
//		   return ipAddress;
//
//	   }

		public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		public static final Pattern pattern = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");
		public static String longToIpV4(long longIp) {
			int octet3 = (int) ((longIp >> 24) % 256);
			int octet2 = (int) ((longIp >> 16) % 256);
			int octet1 = (int) ((longIp >> 8) % 256);
			int octet0 = (int) ((longIp) % 256);
			return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
		}


		public static long ipV4ToLong(String ip) {
			String[] octets = ip.split("\\.");
			return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
					+ (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
		}
		public static boolean isIPv4Private(String ip) {
			long longIp = ipV4ToLong(ip);
			return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255"))
					|| (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255"))
					|| longIp >= ipV4ToLong("192.168.0.0") && longIp <= ipV4ToLong("192.168.255.255");
		}

		public static boolean isIPv4Valid(String ip) {
			return pattern.matcher(ip).matches();
		}
		public static String getClintIP(HttpServletRequest request) {
			String ip;
			boolean found = false;
			if ((ip = request.getHeader("x-forwarded-for")) != null) {
				StrTokenizer tokenizer = new StrTokenizer(ip, ",");
				while (tokenizer.hasNext()) {
					ip = tokenizer.nextToken().trim();
					if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				ip = request.getRemoteAddr();
			}
			return ip;
		}

	/**
	 * 获取客户端ip地址(可以穿透代理)
	 *
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	private static final String[] HEADERS_TO_TRY = {
			"X-Forwarded-For",
			"Proxy-Client-IP",
			"WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR",
			"HTTP_X_FORWARDED",
			"HTTP_X_CLUSTER_CLIENT_IP",
			"HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR",
			"HTTP_FORWARDED",
			"HTTP_VIA",
			"REMOTE_ADDR",
			"X-Real-IP"};

	/***
	 * 获取客户端ip地址(可以穿透代理)
	 * @param request
	 * @return
	 */
	public static String getClientIpAddress(HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}
	/***
	 * 获取客户端ip地址(可以穿透代理)
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_VIA");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (null != ip && !"".equals(ip.trim())
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (null != ip && !"".equals(ip.trim())
				&& !"unknown".equalsIgnoreCase(ip)) {
			// get first ip from proxy ip
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	/**
	 * 对象转Json
	 * @param o
	 * @return
	 */
	/*public static String objectToJson(Object o){
		String str = "";
		if(!CommonUtil.isNull(o)){
			Gson gson = new Gson();
			str = gson.toJson(o);
		}
		return str;
	}*/
	
	/**
	 * 从properties 中读取配置信息
	 * @param fileName 文件名称
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getSystemProperty(String fileName,String key){
		String str = "";
		InputStream in =null;
		try {
			Resource resource = new ClassPathResource(fileName);
			Properties props = new Properties();
			in = resource.getInputStream();
			props.load(in);
			//根据key返回值
			str = (String) props.get(key);
			str = new String(StringUtil.delNull(str).getBytes("ISO8859-1"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(! CommonUtil.isNull(in)){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return str;
	}
	/**
	 * 首字母转大写
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	/**
	 * 首字母小写
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	/**
	 * 下载网络图片到本地
	 * @param url
	 */
	public static UpFiles downNetImage(String url) throws Exception{
		//new一个URL对象    
		URL neturl = new URL(url);    
		//打开链接    
		HttpURLConnection conn = (HttpURLConnection)neturl.openConnection();    
		//设置请求方式为"GET"    
		conn.setRequestMethod("GET");    
		//超时响应时间为5秒    
		conn.setConnectTimeout(5 * 1000);    
		//通过输入流获取图片数据    
		InputStream inStream = conn.getInputStream();    
		//得到图片的二进制数据，以二进制封装得到数据，具有通用性    
		byte[] data = readInputStream(inStream);    
		//new一个文件对象用来保存图片
		String basepath = FileUtil.getUploadBasePath();
		
		/* 所有附件都保存到uploads 不存在则新增文件夹 */
		File f = new File(basepath);
		if (!f.exists()) {
			f.mkdir();
		}
		String path = File.separator + "uploads";
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		/* 每年一个文件夹 */
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		/* 每月一个文件夹 */
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		/* 每天一个文件夹 */
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		path = path + File.separator +  UUIDGenerator.getUUID() + ".jpg";
		File imageFile = new File(basepath+path);    
		//创建输出流    
		FileOutputStream outStream = new FileOutputStream(imageFile);    
		//写入数据    
		outStream.write(data);    
		//关闭输出流    
		outStream.close();
		
		//图片现在完成后返回UpFiles对象
		UpFiles upfiles = new UpFiles();
		upfiles.setUuid( UUIDGenerator.getUUID());
		upfiles.setFilename( UUIDGenerator.getUUID()+".jpg");
		upfiles.setFilepath(path);
		upfiles.setFileext("jpg");
		upfiles.setSizeb(0);
		upfiles.setSizem("0");
		return upfiles;
	}
	
	//得到图片的二进制数据，以二进制封装得到数据，具有通用性
	private static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	} 
	/**
	 * 根据base64编码获取图片
	 * @param baseCode
	 * @return
	 */
	public static UpFiles getImageBybase64(String baseCode){
		OutputStream out = null;
		UpFiles upfiles = null;
		try {
			//图片扩展名
			String ext = StringUtils.defaultIfEmpty(StringUtils.substringBetween(baseCode, "data:image/", ";"), "jpg");
			// 图片数据
			String base64ImgData = StringUtils.substringBetween(baseCode + "\"", "base64,", "\"");
			String basepath = FileUtil.getUploadBasePath();
			/* 所有附件都保存到uploads 不存在则新增文件夹 */
			File f = new File(basepath);
			if (!f.exists()) {
				f.mkdir();
			}
			String path = File.separator + "uploads";
			f = new File(basepath + path);
			if (!f.exists()) {
				f.mkdir();
			}
			/* 每年一个文件夹 */
			path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy);
			f = new File(basepath + path);
			if (!f.exists()) {
				f.mkdir();
			}
			/* 每月一个文件夹 */
			path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM);
			f = new File(basepath + path);
			if (!f.exists()) {
				f.mkdir();
			}
			/* 每天一个文件夹 */
			path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd);
			f = new File(basepath + path);
			if (!f.exists()) {
				f.mkdir();
			}
			path = path + File.separator +  UUIDGenerator.getUUID() + "." + ext.toLowerCase();
			
			//生成图片
			BASE64Decoder d = new BASE64Decoder();
			byte[] bs = d.decodeBuffer(base64ImgData);
			for (int i = 0; i < bs.length; ++i) {
				if (bs[i] < 0) {// 调整异常数据
					bs[i] += 256;
				}
			}
			out = new FileOutputStream(basepath+path);
			out.write(bs);
			out.flush();
			
			// 附件信息存库
			upfiles = new UpFiles();
			upfiles.setUuid( UUIDGenerator.getUUID());
			upfiles.setFilename(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyyMMddHHmmssSSS) + "." + ext);
			upfiles.setFilepath(path);
			upfiles.setFileext(ext.toLowerCase());
			upfiles.setSizeb(Integer.parseInt(String.valueOf((new File(basepath + path)).length())));
			String sizeM = MathExtend.divide(String.valueOf(upfiles.getSizeb()), String.valueOf(1024), 2);
			String dw = "K";
			if (Float.parseFloat(sizeM) > 1024) {
				sizeM = MathExtend.divide(sizeM, String.valueOf(1024), 2);
				dw = "M";
				if (Float.parseFloat(sizeM) > 1024) {
					sizeM = MathExtend.divide(sizeM, String.valueOf(1024), 2);
					dw = "G";
				}
			}
			upfiles.setSizem(sizeM + dw);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(out!=null){out.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return upfiles;
	}
	/**
	 * 保存ewebeditor里面word转换的图片到upfiles
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static UpFiles copyImageFromEwebeditor(String filePath) throws Exception{
		//new一个文件对象用来保存图片
		String basepath = FileUtil.getUploadBasePath();
		
		/* 所有附件都保存到uploads 不存在则新增文件夹 */
		File f = new File(basepath);
		if (!f.exists()) {
			f.mkdir();
		}
		String path = File.separator + "uploads";
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		/* 每年一个文件夹 */
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		/* 每月一个文件夹 */
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		/* 每天一个文件夹 */
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		path = path + File.separator +  UUIDGenerator.getUUID() + ".jpg";
		File imageFile = new File(basepath+path);    
		//输入流
		FileInputStream in = new FileInputStream(System.getProperty("egap.root").replace("\\","/")+filePath.substring(1,filePath.length()));
		//创建输出流    
		FileOutputStream outStream = new FileOutputStream(imageFile);    
		//System.out.println(FileUtil.getRootPath().replace("\\","/")+filePath.substring(1,filePath.length()));
		//System.out.println(imageFile);
		byte[] buf = new byte[1024];
		int len = 0;
		while ((len = in.read(buf)) > 0) {
			outStream.write(buf, 0, len);
		}
		in.close();
		//关闭输出流    
		outStream.close();
		
		//图片现在完成后返回Upfiles对象
		UpFiles upfiles = new UpFiles();
		upfiles.setUuid( UUIDGenerator.getUUID());
		upfiles.setFilename( UUIDGenerator.getUUID()+".jpg");
		upfiles.setFilepath(path);
		upfiles.setFileext("jpg");
		upfiles.setSizeb(0);
		upfiles.setSizem("0");
		return upfiles;
	}
	/**
	 * 图片转PDF
	 * @param imgPath
	 * @return
	 */
	/*public static Upfiles imageToPdf(String imgPath){
		Upfiles upfiles = null;
		OfficeFileAndImgToPDF office2pdf = new OfficeFileAndImgToPDF();
		String basepath = UploadFileUtil.getUploadBasePath();
		imgPath = basepath+imgPath;

		*//* 所有附件都保存到uploads 不存在则新增文件夹 *//*
		File f = new File(basepath);
		if (!f.exists()) {
			f.mkdir();
		}
		String path = File.separator + "uploads";
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		*//* 每年一个文件夹 *//*
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		*//* 每月一个文件夹 *//*
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}
		*//* 每天一个文件夹 *//*
		path = path + File.separator + DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd);
		f = new File(basepath + path);
		if (!f.exists()) {
			f.mkdir();
		}

		//生成PDF
		path = path + File.separator + UUIDGenerator.getUUID() + ".pdf";
		try {
			office2pdf.imgToPdf(imgPath,basepath+path);
			// 附件信息存库
			upfiles = new Upfiles();
			upfiles.setUuid(UUIDGenerator.getUUID());
			upfiles.setFilename(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyyMMddHHmmssSSS) + ".pdf");
			upfiles.setFilepath(path);
			upfiles.setFileExt("pdf");
			upfiles.setSizeb(Integer.parseInt(String.valueOf((new File(basepath + path)).length())));
			String sizeM = MathExtend.divide(String.valueOf(upfiles.getSizeb()), String.valueOf(1024), 2);
			String dw = "K";
			if (Float.parseFloat(sizeM) > 1024) {
				sizeM = MathExtend.divide(sizeM, String.valueOf(1024), 2);
				dw = "M";
				if (Float.parseFloat(sizeM) > 1024) {
					sizeM = MathExtend.divide(sizeM, String.valueOf(1024), 2);
					dw = "G";
				}
			}
			upfiles.setSizem(sizeM + dw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upfiles;
	}*/
	/**
	 * 提取中文字符
	 * @param str
	 * @return
	 */
	public static String getChineseStr(String str){
		String rs = "";
		if(! CommonUtil.isNull(str)){
			rs =  str.replaceAll("[^\\u4e00-\\u9fa5]", "");
		}
		return rs;
	}
	/**
	 * 根据java反射机制获取类的属性值
	 * 注意：这个方法返回的都是属性值的string字符串，如果是一些复杂的类型对象此方法不支持
	 * @param obj
	 * @return
	 */
	public static String getFieldValue(Object obj, String fieldName) {
		String str = "";
		try {
			String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method method = obj.getClass().getMethod("get" + methodName);
			Object value = method.invoke(obj);
			if (! CommonUtil.isNull(value)) {
				str = value.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 将字符串写到文件里面去
	 * @param str 字符串
	 * @param filePath 文件路径
	 * @throws IOException
	 */
	public static void writeFile(String str, String filePath) throws IOException {
		if(!CommonUtil.isNull(str)){
			File targetFile = new File(filePath);
			//按照文件路径生成目录结构
			File parent = targetFile.getParentFile();
			if(parent!=null&&!parent.exists()){ 
				parent.mkdirs(); 
			}
			
			// 写入中文字符时解决中文乱码问题
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			// 简写如下：
			// BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
			// new FileOutputStream(new
			// File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8"));
			bw.write(str + "\r\n");
			// 注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
			bw.close();
			osw.close();
			fos.close();
		}
	}
	/**
	 * 阿拉伯数字转成中文数字
	 * @param intInput
	 * @return
	 */
	 public static String toCH(int intInput) {  
	        String si = String.valueOf(intInput);  
	        String sd = "";  
	        if (si.length() == 1) // 個  
	        {  
	            sd += GetCH(intInput);  
	            return sd;  
	        } else if (si.length() == 2)// 十  
	        {  
	            if (si.substring(0, 1).equals("1"))  
	                sd += "十";  
	            else  
	                sd += (GetCH(intInput / 10) + "十");  
	            sd += toCH(intInput % 10);  
	        } else if (si.length() == 3)// 百  
	        {  
	            sd += (GetCH(intInput / 100) + "百");  
	            if (String.valueOf(intInput % 100).length() < 2)  
	                sd += "零";  
	            sd += toCH(intInput % 100);  
	        } else if (si.length() == 4)// 千  
	        {  
	            sd += (GetCH(intInput / 1000) + "千");  
	            if (String.valueOf(intInput % 1000).length() < 3)  
	                sd += "零";  
	            sd += toCH(intInput % 1000);  
	        } else if (si.length() == 5)// 萬  
	        {  
	            sd += (GetCH(intInput / 10000) + "萬");  
	            if (String.valueOf(intInput % 10000).length() < 4)  
	                sd += "零";  
	            sd += toCH(intInput % 10000);  
	        }  
	  
	        return sd;  
	    }  
	  
	    private static String GetCH(int input) {  
	        String sd = "";  
	        switch (input) {  
	        case 1:  
	            sd = "一";  
	            break;  
	        case 2:  
	            sd = "二";  
	            break;  
	        case 3:  
	            sd = "三";  
	            break;  
	        case 4:  
	            sd = "四";  
	            break;  
	        case 5:  
	            sd = "五";  
	            break;  
	        case 6:  
	            sd = "六";  
	            break;  
	        case 7:  
	            sd = "七";  
	            break;  
	        case 8:  
	            sd = "八";  
	            break;  
	        case 9:  
	            sd = "九";  
	            break;  
	        default:  
	            break;  
	        }  
	        return sd;  
	    }  
	/**
	 * 取随机数
	 * @param leg 生成的长度
	 * @return leg位数的随机数 最长19位
	 */
	public static long round(int leg){
		if(leg <0){
			return 0;
		}
		long flag= 0;
		while (true) {
			flag = Math.round(Math.random()*Math.pow(10, leg));
			if(flag!=0&& flag>Math.pow(10, leg-1))
				break;
		}
		return  flag;
	}   
	
	/**
	 * 获取保存在Session中的对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	/*public static Object getSessionObj(String key) {
		Object obj = null;
		Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) RequestContextHolderUtil.getSession().getAttribute(SessionKeyConstant.SESSION_CONTEXT);
		if (map == null) {
			map = new HashMap<String, Map<String, Object>>();
		}
		Map<String, Object> m = map.get(key);
		if (m == null) {
			m = new HashMap<String, Object>();
		}
		String sid = RequestContextHolderUtil.getRequest().getParameter("sid");
		if (map != null && sid != null) {
			obj = m.get(sid);
		}
		return obj;
	}*/

	/**
	 * 获取保存在Session中的登录人员信息
	 * @return
	 */
	/*public static UserInfo getSessionUser() {
		return (UserInfo) CommonUtil.getSessionObj(SessionKeyConstant.USER_CONTEXT);
	}*/
	
	/**
	 * 获取随机数，返回string
	 * @param num
	 * @return
	 */
	public static String getRandomNum(int num){
		 int x = new Random().nextInt(Integer.valueOf(num)); 
		 return String.valueOf(x);
	}
	
	/**
	 * 从LIST中获取一个对象
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getOne(List list) {
		Object obj = null;
		if (list != null && list.size() > 0) {
			obj = list.get(0);
		}
		return obj;
	}
	/**
	 * 判断字符串是否为URL
	 * @param urls 用户头像key
	 * @return true:是URL、false:不是URL
	 */
	public static boolean isHttpUrl(String urls) {
		boolean isurl = false;
		String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
				+ "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

		Pattern pat = Pattern.compile(regex.trim());//比对
		Matcher mat = pat.matcher(urls.trim());
		isurl = mat.matches();//判断是否匹配
		if (isurl) {
			isurl = true;
		}
		return isurl;
	}

	public static String getAttribute(Object o,String field) {
		String r = "";
		try {
			Field f = o.getClass().getDeclaredField(field);
			f.setAccessible(true);
			r =  f.get(o).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	public static String toSplit(String splitStr){
		splitStr=splitStr.replaceAll("\r\n",",");
		splitStr=splitStr.replaceAll("\n",",");
		/*String[] str=splitStr.split(",");
		StringBuffer buffer=new StringBuffer();
		for (String s: str) {
			StringBuffer b=new StringBuffer(s);
			b.insert(0,"'");
			b.insert(s.length()+1,"',");
			buffer.append(b);
		}
		splitStr=buffer.toString();*/
		if(splitStr.endsWith(",")){
			splitStr=splitStr.substring(0,splitStr.length()-1);
		}
		return splitStr;
	}
	//是否仅包含字母和数字
	public static boolean isLetterDigit(String str) {
		String regex = "^[a-z0-9A-Z]+$";
		return Pattern.matches(regex, str);
	}
}
