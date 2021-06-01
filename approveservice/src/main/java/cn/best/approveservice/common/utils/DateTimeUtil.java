package cn.best.approveservice.common.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期时间工具类
 *
 */
public class DateTimeUtil {

	private static final Logger logger = LogManager.getLogger(DateTimeUtil.class.getName());

	private final static String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/**
	 * yyyy-MM-dd
	 */
	public static int yyyy_MM_dd = 0;
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static int yyyy_MM_dd_HH_mm_ss = 1;
	/**
	 * HH:mm:ss
	 */
	public static int HH_mm_ss = 2;
	/**
	 * yyyyMMddHHmmss
	 */
	public static int yyyyMMddHHmmss = 3;
	/**
	 * yyyyMMddHHmmssSS
	 */
	public static int yyyyMMddHHmmssSSS = 4;
	/**
	 * yyyy年MM月dd日
	 */
	public static int c_yyyy_MM_dd_ = 5;
	/**
	 * yyyy年MM月dd日HH时mm分ss秒
	 */
	public static int c_yyyy_MM_dd_HH_mm_ss_ = 6;
	/**
	 * yyyy
	 */
	public static int yyyy = 7;
	/**
	 * MM
	 */
	public static int MM = 8;
	/**
	 * dd
	 */
	public static int dd = 9;
	/**
	 * yyyyMMdd
	 */
	public static int yyyyMMdd = 10;
	/**
	 * yyyy-MM
	 */
	public static int yyyy_MM = 11;
	/**
	 * yyyy年MM月dd日 EE
	 */
	public static int c_yyyy_MM_dd_EE = 12;
	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	public static int x_yyyy_MM_dd_HH_mm_ss = 13;

	/**
	 * HHmmss
	 */
	public static int HHmmss = 14;

	/**
	 * HHmmssSS
	 */
	public static int HHmmssSSS = 15;
	/**
	 * yyyy/MM/dd
	 */
	public static int x_yyyy_MM_dd = 16;
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static int yyyy_MM_dd_HH_mm = 17;


	/**
	 * 获取所有格式的当前日期
	 * @return
	 */
	public static Map<String, String> getAllFormatDate() {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i <= 15; i++) {
			map.put(String.valueOf(i), DateTimeUtil.getNowDateStr(i));
		}
		return map;
	}

	/**
	 * 格式化日期样式
	 * @param caseNum
	 * @return
	 */
	public static SimpleDateFormat getDateFormat(int caseNum) {
		SimpleDateFormat dateFormat = null;
		switch (caseNum) {
			case 0:
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case 1:
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 2:
				dateFormat = new SimpleDateFormat("HH:mm:ss");
				break;
			case 3:
				dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case 4:
				dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				break;
			case 10:
				dateFormat = new SimpleDateFormat("yyyyMMdd");
				break;
			case 5:
				dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
				break;
			case 6:
				dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
				break;
			case 7:
				dateFormat = new SimpleDateFormat("yyyy");
				break;
			case 8:
				dateFormat = new SimpleDateFormat("MM");
				break;
			case 9:
				dateFormat = new SimpleDateFormat("dd");
				break;
			case 11:
				dateFormat = new SimpleDateFormat("yyyy-MM");
				break;
			case 12:
				dateFormat = new SimpleDateFormat("yyyy年MM月dd日 EE");
				break;
			case 13:
				dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				break;
			case 14:
				dateFormat = new SimpleDateFormat("HHmmss");
				break;
			case 15:
				dateFormat = new SimpleDateFormat("HHmmssSSS");
				break;
			case 16:
				dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				break;
			case 17:
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				break;
			default:
				break;
		}
		return dateFormat;
	}

	/**
	 * 把日期转换为字符串
	 * @param date
	 * @param caseNum
	 * @return
	 */
	public static String formatDate(Date date, int caseNum) {
		String dateStr = null;
		SimpleDateFormat dateFormat = getDateFormat(caseNum);
		dateStr = dateFormat.format(date);
		return dateStr;
	}

	/**
	 * 把字符串转换为日期
	 * @param dateStr
	 * @param caseNum
	 * @return
	 */
	public static Date parseDate(String dateStr, int caseNum) {
		Date date = null;
		try {
			SimpleDateFormat dateFormat = getDateFormat(caseNum);
			date = dateFormat.parse(dateStr);
		} catch (Exception e) {
			logger.error("parseDate error:", e);
		}
		return date;
	}

	public static Calendar parseCalendar(String dateStr, int caseNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parseDate(dateStr, caseNum));
		return calendar;
	}

	/**
	 * 获取当前时间的字符串形式
	 * @param caseNum
	 * @return
	 * @throws Exception
	 */
	public static String getNowDateStr(int caseNum) {
		Date date = new Date();
		SimpleDateFormat dateFormat = getDateFormat(caseNum);
		String dateStr = dateFormat.format(date);
		return dateStr;
	}

	/**
	 * 得到年份
	 * @param c
	 * @return
	 */
	public static int getYear(Calendar c) {
		return c.get(Calendar.YEAR);
	}

	/**
	 * 得到当前年份
	 * @return
	 */
	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 得到月份
	 * @param c
	 * @return
	 */
	public static int getMonth(Calendar c) {
		return c.get(Calendar.MONTH);
	}

	/**
	 * 得到当前月份
	 * @return
	 */
	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 得到日
	 * @param c
	 * @return
	 */
	public static int getDate(Calendar c) {
		return c.get(Calendar.DATE);
	}

	/**
	 * 得到当前日
	 * @return
	 */
	public static int getDate() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * 得到星期
	 * @param c
	 * @return
	 */
	public static int getDay(Calendar c) {
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 得到当前星期  1星期日,2星期一...
	 * @return
	 */
	public static int getDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 得到指定日期date一周内周一到周日的日期
	 * @param caseNum
	 * @param day 1星期一...7星期日
	 * @return
	 */
	public static String getWeekDate(String date, int caseNum, int day) {
		String rstr = "";
		int num = getDay(parseCalendar(date, caseNum));
		if (num == 1) {// 给定的日期是本周星期日
			rstr = addDate(date, caseNum, Calendar.DATE, day - 7);
		} else if (num == 2) {// 给定的日期是本周星期1
			rstr = addDate(date, caseNum, Calendar.DATE, day - 1);
		} else if (num == 3) {// 给定的日期是本周星期2
			rstr = addDate(date, caseNum, Calendar.DATE, day - 2);
		} else if (num == 4) {// 给定的日期是本周星期3
			rstr = addDate(date, caseNum, Calendar.DATE, day - 3);
		} else if (num == 5) {// 给定的日期是本周星期4
			rstr = addDate(date, caseNum, Calendar.DATE, day - 4);
		} else if (num == 6) {// 给定的日期是本周星期5
			rstr = addDate(date, caseNum, Calendar.DATE, day - 5);
		} else if (num == 7) {// 给定的日期是本周星期6
			rstr = addDate(date, caseNum, Calendar.DATE, day - 6);
		}
		return rstr;
	}

	/**
	 * 得到本周内周一到周日的日期
	 * @param day
	 * @return
	 */
	public static String getNowWeekDate(int day) {
		return getWeekDate(getNowDateStr(0), 0, day);
	}

	/**
	 * 得到星期
	 * @param c
	 * @return
	 */
	public static String getChineseDay(Calendar c) {
		return dayNames[getDay(c) - 1];
	}

	/**
	 * 得到当前星期（中文）
	 * @return
	 */
	public static String getChineseDay() {
		return dayNames[getDay() - 1];
	}

	/**
	 * 得到小时
	 * @param c
	 * @return
	 */
	public static int getHour(Calendar c) {
		return c.get(Calendar.HOUR);
	}

	/**
	 * 得到当前小时
	 * @return
	 */
	public static int getHour() {
		return Calendar.getInstance().get(Calendar.HOUR);
	}

	/**
	 * 得到当前小时 (24小时制)
	 * @return
	 */
	public static int getHour24() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 昨天
	 * @param c
	 * @return
	 */
	public static Calendar yesterday(Calendar c) {
		long offset = 1 * 24 * 60 * 60 * 1000;
		c.setTimeInMillis(c.getTimeInMillis() - offset);
		return c;
	}

	/**
	 * 当前时间的昨天
	 * @return
	 */
	public static Calendar yesterday() {
		long offset = 1 * 24 * 60 * 60 * 1000;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(c.getTimeInMillis() - offset);
		return c;
	}

	/**
	 * 明天
	 * @param c
	 * @return
	 */
	public static Calendar tomorrow(Calendar c) {
		long offset = 1 * 24 * 60 * 60 * 1000;
		c.setTimeInMillis(c.getTimeInMillis() + offset);
		return c;
	}

	/**
	 * 当前时间的明天
	 * @return
	 */
	public static Calendar tomorrow() {
		long offset = 1 * 24 * 60 * 60 * 1000;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(c.getTimeInMillis() + offset);
		return c;
	}

	/**
	 * Date类型转换到Calendar类型
	 * @param d
	 * @return
	 */
	public static Calendar Date2Calendar(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}

	/**
	 * Calendar类型转换到Date类型
	 * @param c
	 * @return
	 */
	public static Date Calendar2Date(Calendar c) {
		return c.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int daysBetweenDate(Date startDate, Date endDate) {
		Long interval = endDate.getTime() - startDate.getTime();
		interval = interval / (24 * 60 * 60 * 1000);
		return interval.intValue();
	}

	/**
	 * 日期加减
	 * @param date 起始日期
	 * @param formatCaseNum 日期格式
	 * @param calendarType 日期计算方式Calender常量(YEAR：加年,MONTH：加月,DATE：加天,HOUR：加小时,MINUTE:分钟,SECOND：加秒)
	 * @param num 需要加减的数量
	 * @return
	 * @throws Exception
	 */
	static public String addDate(String date, int formatCaseNum, int calendarType, int num) {
		SimpleDateFormat format = null;
		Calendar calendar = Calendar.getInstance();
		try {
			format = getDateFormat(formatCaseNum);
			calendar.setTime(format.parse(date));
			calendar.add(calendarType, num);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format.format(calendar.getTime());
	}

	/**
	 * 判断是否是周末
	 * @param d
	 * @return
	 */
	public static boolean isWeekend(String d) {
		return isWeekend(parseDate(d, yyyy_MM_dd));
	}

	public static boolean isWeekend(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		if (day == 1 || day == 7) {
			return true;
		}
		return false;
	}

	/**
	 * 给定日期是第几季度
	 * @param date
	 * @return
	 */
	public static int getQuarter(Date date) {
		int quarter = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = getMonth(c);
		if (month >= 0 && month <= 2) {
			quarter = 1;
		} else if (month >= 3 && month <= 5) {
			quarter = 2;
		} else if (month >= 6 && month <= 8) {
			quarter = 3;
		} else if (month >= 9 && month <= 11) {
			quarter = 4;
		}
		return quarter;
	}

	/**
	 * 判断某个日期是否在某个日期范围 
	 * @param beginDate
	 * @param endDate
	 * @param src
	 * @return
	 */
	public static boolean isBetween(Date beginDate, Date endDate, Date src) {
		return beginDate.before(src) && endDate.after(src);
	}

	/**
	 * 比较2个日期 dt1>dt2 1   dt1=dt2 0   dt1<dt2 -1
	 * @param dt1
	 * @param dt2
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static int compareDate(String dt1, String dt2, int format) {
		int num = 0;
		Date date1;
		Date date2;
		dt1 = DateStrCorect(dt1, format);
		dt2 = DateStrCorect(dt2, format);

		date1 = parseDate(dt1, format);
		date2 = parseDate(dt2, format);
		if (date1.after(date2)) {
			num = 1;
		} else if (date1.before(date2)) {
			num = -1;
		}
		return num;
	}

	/**
	 * 获得两个时间段内的所有日期
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static List<String> getBetweenDate(String d1, String d2) throws ParseException{
		List<String> v = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc1 = new GregorianCalendar(), gc2 = new GregorianCalendar();
		gc1.setTime(sdf.parse(d1));
		gc2.setTime(sdf.parse(d2));
		if (gc1.after(gc2)) {
			GregorianCalendar swap = gc1;
			gc1 = gc2;
			gc2 = swap;
		}
		do {
			GregorianCalendar gc3 = (GregorianCalendar) gc1.clone();
			v.add(sdf.format(gc3.getTime()));
			gc1.add(Calendar.DAY_OF_MONTH, 1);
		} while (!gc1.after(gc2));
		return v;
	}

	/**
	 * 获取指定月份的第一天日期
	 * @param yearMonth-MM
	 * @return
	 */
	public static String getMonthFirstDay(String yearMonth) {
		return yearMonth + "-01";
	}

	/**
	 * 获取指定月份的最后一天日期
	 * @param yearMonth-MM
	 * @return
	 */
	public static String getMonthLastDay(String yearMonth) {
		String s = DateTimeUtil.addDate(yearMonth + "-01", 0, Calendar.MONTH, 1);
		String lastDay = DateTimeUtil.addDate(s, 0, Calendar.DATE, -1);
		return lastDay;
	}

	/**
	 * 根据年份返回每季度最后一天日期或者每季度第一天日期
	 * @param year：年份
	 * @param quarter:季度
	 * @param type:first:度第一天日期，last：季度最后一天日期
	 * @return 
	 * @throws Exception
	 */
	public static String endDateofTheQuarter(String year, String quarter, String type) {
		String edate = "";
		String sdate = "";
		if (quarter.equals("1")) {// 第一季度
			sdate = year + "-01-01";
			edate = year + "-03-31";
		} else if (quarter.equals("2")) {// 第二季度
			sdate = year + "-04-01";
			edate = year + "-06-30";
		} else if (quarter.equals("3")) {// 第三季度
			sdate = year + "-07-01";
			edate = year + "-09-30";
		} else if (quarter.equals("4")) {// 第四季度
			sdate = year + "-10-01";
			edate = year + "-12-31";
		}
		if (type.equals("first")) {
			return sdate;
		} else {
			return edate;
		}
	}

	/**
	 * 获取当前时刻 （早上，上午，下午，晚上）
	 * @return
	 */
	public static String getMonment() {
		String s = "";
		int hour = getHour24();
		if (hour >= 0 && hour < 6) {
			s = "早上";
		} else if (hour > 6 && hour <= 12) {
			s = "上午";
		} else if (hour > 12 && hour <= 18) {
			s = "下午";
		} else if (hour > 18 && hour <= 24) {
			s = "晚上";
		}
		return s;
	}

	/**
	 * 根据年和月获取每个月的每天数字，并按照日历格式输出
	 * @param year
	 * @param month
	 * @return
	 */
	public static String[] getMonthDays(int year, int month) {
		String a[] = new String[42];
		Calendar date = Calendar.getInstance();
		date.set(year, month - 1, 1);
		int week = date.get(Calendar.DAY_OF_WEEK) - 1;
		int day = 0;
		// 判断大月份
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			day = 31;
		}
		// 判断小月
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			day = 30;
		}
		// 判断平年与闰年
		if (month == 2) {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				day = 29;
			} else {
				day = 28;
			}
		}
		for (int i = week, n = 1; i < week + day; i++) {
			a[i] = String.valueOf(n);
			n++;
		}
		return a;
	}

	/**
	 * 获取当前月份第一天的日期
	 * @param caseNum 返回格式
	 * @return
	 */
	public static String getNowMonthFirstDay(int caseNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return getDateFormat(caseNum).format(calendar.getTime());
	}

	/**
	 * 获取当前月份最后一天的日期
	 * @param caseNum 返回格式
	 * @return
	 */
	public static String getNowMonthLastDay(int caseNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getDateFormat(caseNum).format(calendar.getTime());
	}

	/**
	 * 获取指定月份第一天的日期
	 * @param month 要获取的月份
	 * @param caseNum 返回格式
	 * @return
	 */
	public static String getMonthFirstDayByMonth(int month, int caseNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return getDateFormat(caseNum).format(calendar.getTime());
	}

	/**
	 * 获取指定月份的最后一天日期
	 * @param month 要获取的月份
	 * @param caseNum 返回格式
	 * @return
	 */
	public static String getMonthLastDayByMonth(int month, int caseNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getDateFormat(caseNum).format(calendar.getTime());
	}
	/**
	 * 计算两个时间之间相差的分钟数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int miBetweenTime(Date startDate, Date endDate) {
		Long interval = endDate.getTime() - startDate.getTime();
		interval = interval / (60 * 1000);
		return interval.intValue();
	}

	public static String DateStrCorect(String Datestr, int format) {
		Datestr = Datestr.replace("/", "-");
		Datestr = Datestr.replace(".", "-");
		Datestr = Datestr.replace("。", "-");
		Datestr = Datestr.replace("/", "-");
		Datestr = Datestr.replace("\u3001", "-");//、
		Datestr = Datestr.replace("\u2014\u2014", "-");//——
		Datestr = Datestr.replace("：", ":");
		Datestr = Datestr.replace("—", "-");
		Datestr = Datestr.replace("00:00:00 00:00:00", "00:00:00");

		if (Datestr.length() > 19 && Datestr.contains(" 00:00:00")) {
			Datestr = Datestr.replace(" 00:00:00", "");
		}

		if (Datestr.length() == 10 && (format == 1 || format == 13)) {
			Datestr = Datestr + " 00:00:00";
		}

		Integer DateFormat = getDatetimeFormat(Datestr);
		if(DateFormat==-1) return "";
		Date strdt = parseDate(Datestr,DateFormat);
		Datestr = formatDate(strdt,format);

		return Datestr;
	}

	public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		// 截取当前时间时分秒
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
		int strDateS = Integer.parseInt(strDate.substring(17, 19));
		// 截取开始时间时分秒
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
		// 截取结束时间时分秒
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
			// 当前时间小时数在开始时间和结束时间小时数之间
			if (strDateH > strDateBeginH && strDateH < strDateEndH) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
					&& strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM
					&& strDateS >= strDateBeginS && strDateS <= strDateEndS) {
				return true;
			}
			// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
			else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM == strDateEndM && strDateS <= strDateEndS) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isDate(String date)
	{
		/**
		 * 判断日期格式和范围
		 */
		String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pat = Pattern.compile(rexp);

		Matcher mat = pat.matcher(date);

		boolean dateType = mat.matches();

		return dateType;
	}


	/**
	 * 判断是否日期格式
	 * @param date
	 * @return
	 */
	public static boolean validDateTime(String date){
		date=date.trim();
//		String a1 = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";//yyyyMMddHHmmss
//		String a2 = "[0-9]{4}[0-9]{2}[0-9]{2}";//yyyyMMdd

//		String a3 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";//yyyy-MM-dd HH:mm:ss
//		String a4 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//yyyy-MM-dd

		String a3 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{2}:[0-9]{2}";//yyyy-MM-dd HH:mm:ss
		String a4 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";//yyyy-MM-dd


//		String a5= "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}";//yyyy-MM-dd  HH:mm


//		boolean datea1 = Pattern.compile(a1).matcher(date).matches();
//		if(datea1){
//			return "yyyyMMddHHmmss";
//		}
//		boolean datea2 = Pattern.compile(a2).matcher(date).matches();
//		if(datea2){
//			return "yyyyMMdd";
//		}
		boolean datea3 = Pattern.compile(a3).matcher(date).matches();
		if(datea3){
			return true;//"yyyy-MM-dd HH:mm:ss"
		}
		boolean datea4 = Pattern.compile(a4).matcher(date).matches();
		if(datea4){
			return true;//"yyyy-MM-dd";
		}
//		boolean datea5 = Pattern.compile(a5).matcher(date).matches();
//		if(datea5){
//			return "yyyy-MM-dd HH:mm";
//		}
		return false;
	}

	/**
	 * 判断字符串时间格式
	 * @param date
	 * @return
	 */
	public static Integer getDatetimeFormat(String date){
		date=date.trim();
		String a1 = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";//yyyyMMddHHmmss
		String a2 = "[0-9]{4}[0-9]{2}[0-9]{2}";//yyyyMMdd

		//		String a3 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";//yyyy-MM-dd HH:mm:ss
//		String a4 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//yyyy-MM-dd

		String a3 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{2}:[0-9]{2}";//yyyy-MM-dd HH:mm:ss
		String a4 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";//yyyy-MM-dd

		String a5= "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{2}:[0-9]{2}";//yyyy-MM-dd  HH:mm
		boolean datea1 = Pattern.compile(a1).matcher(date).matches();
		if(datea1){
			return DateTimeUtil.yyyyMMddHHmmss;// "yyyyMMddHHmmss" ;
		}
		boolean datea2 = Pattern.compile(a2).matcher(date).matches();
		if(datea2){
			return  DateTimeUtil.yyyyMMdd;//"yyyyMMdd";
		}
		boolean datea3 = Pattern.compile(a3).matcher(date).matches();
		if(datea3){
			return DateTimeUtil.yyyy_MM_dd_HH_mm_ss;//"yyyy-MM-dd HH:mm:ss";
		}
		boolean datea4 = Pattern.compile(a4).matcher(date).matches();
		if(datea4){
			return DateTimeUtil.yyyy_MM_dd;//"yyyy-MM-dd";
		}
		boolean datea5 = Pattern.compile(a5).matcher(date).matches();
		if(datea5){
			return DateTimeUtil.yyyy_MM_dd_HH_mm;//"yyyy-MM-dd HH:mm";
		}
		return -1;
	}

}
