package fragment;

import java.sql.Timestamp;  
import java.text.DateFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间操作
 *
 * @author KyleChou
 */
public class DateUtils {
    public static void sample() throws Exception {
        // 时间格式化字母表
        // y: year
        // M: month
        // d: day in month
        // h: hour (HH: 24小时制   hh: 12小时制)
        // m: minute
        // s: second
        // S: millisecond
        // E: day in week (example: Thursday)
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
        
        // 获取当前时间，并转换为String
        Date date = new Date(); //获取当前时间，Date型  
        String dateStr = dateFormat.format(date);
        System.out.println("String:" + dateStr);  
          
        // 获取时间戳的长整型数值
        Long dateLong = date.getTime();
        System.out.println("毫秒数dateLong:" + dateLong);  
        
        // 时间戳与长整型之间的转换
        Timestamp timestamp = new Timestamp(dateLong); 
        System.out.println("Timestamp:" + timestamp);  
        
        Long dateLong2 = timestamp.getTime();  
        System.out.println("毫秒数dateLong2:" + dateLong2);//Timestamp类型转换为Long型毫秒数  
        
        // String型转换为Date型
        Date date2 = dateFormat.parse(dateStr);  
        System.out.println("Date:" + date2);
        
        // 时间戳的长整型数值转换为Date型
        Date date3 = new Date(dateLong2);  
        System.out.println("date3:" + date3);  
        
        // 数据库存储
        // String、Long、Timestamp三种格式的数据都是可以直接存储的  
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    public static String yyyymmdd(Date date){
        return getFormatDate(DateFmtPattern.YYYY__MM__DD, date);
    }

    /**
     * 获取给定日期的凌晨0时0分0秒
     *
     * @param date
     * @return
     */
    public static Date getDateFirstTime(Date date) {
        String dateStr = getFormatDate(DateFmtPattern.YYYYMMDD, date);
        return getDate(DateFmtPattern.YYYYMMDDHHMISS, dateStr + "000000");
    }

    /**
     * 获取给定日期的最后时刻，即23:59:59
     *
     * @param date
     * @return
     */
    public static Date getDateEndTime(Date date) {
        String dateStr = getFormatDate(DateFmtPattern.YYYYMMDD, date);
        return getDate(DateFmtPattern.YYYYMMDDHHMISS, dateStr + "235959");
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        return getDateFirstTime(now());
    }

    /**
     * 获取当前日期最后时刻
     *
     * @return
     */
    public static Date getCurrentDateEnd() {
        return getDateEndTime(now());
    }

    /**
     * 获取给定日期所在月份的周数
     *
     * @param date
     * @return
     */
    public static int getMonthWeekCount(Date date) {
        Date monthenddate = getMonthEndDay(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthenddate);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取当前月份的第一天
     *
     * @return
     */
    public static Date getCurrentMonth() {
        return getMonthFirstDay(now());
    }

    /**
     * 获取给定日期所在月份的第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        String firstDay = getFormatDate(DateFmtPattern.YYYYMM, date) + "01";
        return getDate(DateFmtPattern.YYYYMMDD, firstDay);
    }

    /**
     * 获取给定日期所在星期的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        // 设置周一为一个星期的第一天
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }

    /**
     * 获取给定日期所在星期的第一天凌晨零时
     *
     * @param date
     * @return
     */
    public static Date getWeekFirstDayFirstTime(Date date) {
        Date firstDay = getFirstDayOfWeek(date);
        return getDateFirstTime(firstDay);
    }

    /**
     * 获取给定日期所在星期的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    /**
     * 获取给定日期所在星期的最后一天的最后一秒
     *
     * @param date
     * @return
     */
    public static Date getWeekEndDayEndTime(Date date) {
        Date lastDay = getLastDayOfWeek(date);
        return getDateEndTime(lastDay);
    }

    /**
     * 获取给定日期所在的月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthEndDay(Date date) {
        Date endDay = dateAdd(DateUnit.DAY, -1, dateAdd(DateUnit.MONTH, 1, getMonthFirstDay(date)));
        return endDay;
    }

    /**
     * 获取年龄
     *
     * @param birthday
     * @return
     */
    public static String getAge(String birthday) {
        if (birthday == null || "".equals(birthday))
            return "0";
        Date birth = getDate(DateFmtPattern.YYYYMMDD, birthday);
        return getAge(birth);
    }

    public static String getAge(Date birthday) {
        if (birthday == null)
            return "0";
        Date now = now();
        return getAge(birthday, now);
    }

    public static String getAge(Date birthday, Date curDate) {
        if (birthday == null)
            return "0";
        Date now = curDate;
        int byear = Integer.parseInt(getFormatDate(DateFmtPattern.YYYY, birthday));
        int nyear = Integer.parseInt(getFormatDate(DateFmtPattern.YYYY, now));
        int bmonth = Integer.parseInt(getFormatDate(DateFmtPattern.MM, birthday));
        int nmonth = Integer.parseInt(getFormatDate(DateFmtPattern.MM, now));
        int age = nyear - byear;
        if (age < 0)
            return "0";
        if (nmonth < bmonth)
            age--;
        return String.valueOf(age);
    }

    /**
     * 根据日期格式化pattern，格式化日期
     *
     * @param sFormat
     * @param date
     * @return
     */
    public static String getFormatDate(DateFmtPattern sFormat, Date date) {
        if (date == null) {
            return null;
        }
        if (sFormat != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(sFormat.getValue());
            return formatter.format(date);
        }
        return null;
    }

    /**
     * 根据给定的日期格式，解析日期字符串
     *
     * @param sFormat
     * @param date
     * @return
     */
    public static Date getDate(DateFmtPattern sFormat, String date) {
        if ((date == null) || ("".equals(date))) {
            return null;
        }
        if (sFormat != null) {
            SimpleDateFormat formatter = null;
            if(sFormat.equals(DateFmtPattern.EEEMMMddHHmmsszzzyyyy)) {
                formatter = new SimpleDateFormat(sFormat.getValue(), Locale.US);
            } else {
                formatter = new SimpleDateFormat(sFormat.getValue());
            }
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 智能格式化(根据日期字符串自适应格式化pattern)
     *
     * @param date
     * @return
     */
    public static Date getDate(String date) {
        if ((date == null) || ("".equals(date))) {
            return null;
        }
        date = date.trim();
        DateFmtPattern sFormat = null;
        if (date.indexOf('-') >= 0) {
            if (date.length() == DateFmtPattern.YYYY__MM__DD.length())
                sFormat = DateFmtPattern.YYYY__MM__DD;
            else if (date.length() == DateFmtPattern.YYYY__MM__DD__HH__MI.length())
                sFormat = DateFmtPattern.YYYY__MM__DD__HH__MI;
            else if (date.length() == DateFmtPattern.YYYY__MM__DD__HH__MI__SS.length())
                sFormat = DateFmtPattern.YYYY__MM__DD__HH__MI__SS;
        } else if (date.indexOf('/') >= 0) {
            if (date.length() == DateFmtPattern.YYYY_MM_DD.length())
                sFormat = DateFmtPattern.YYYY_MM_DD;
            else if (date.length() == DateFmtPattern.YYYY_MM_DD_HH_MI.length())
                sFormat = DateFmtPattern.YYYY_MM_DD_HH_MI;
            else if (date.length() == DateFmtPattern.YYYY_MM_DD_HH_MI_SS.length())
                sFormat = DateFmtPattern.YYYY_MM_DD_HH_MI_SS;
        } else if (date.indexOf("年") >= 0) {
            if (date.length() == DateFmtPattern.YYYY$MM$DD.length())
                sFormat = DateFmtPattern.YYYY$MM$DD;
            else if (date.length() == DateFmtPattern.YYYY$MM$DD$HH$MI.length())
                sFormat = DateFmtPattern.YYYY$MM$DD$HH$MI;
            else if (date.length() == DateFmtPattern.YYYY$MM$DD$HH$MI$SS.length()) {
                sFormat = DateFmtPattern.YYYY$MM$DD$HH$MI$SS;
            }
        } else if (date.length() == DateFmtPattern.YYYYMMDD.length())
            sFormat = DateFmtPattern.YYYYMMDD;
        else if (date.length() == DateFmtPattern.YYYYMMDDHHMI.length())
            sFormat = DateFmtPattern.YYYYMMDDHHMI;
        else if (date.length() == DateFmtPattern.YYYYMMDDHHMISS.length()) {
            sFormat = DateFmtPattern.YYYYMMDDHHMISS;
        } else if(date.length() == DateFmtPattern.EEEMMMddHHmmsszzzyyyy.length()){
            sFormat = DateFmtPattern.EEEMMMddHHmmsszzzyyyy;
        }
        else {
            return null;
        }

        return getDate(sFormat, date);
    }

    /**
     * 日期增加
     *
     * @param iField
     * @param iValue
     * @param date
     * @return
     */
    public static Date dateAdd(DateUnit iField, int iValue, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (iField) {
            case YEAR:
                cal.add(Calendar.YEAR, iValue);
                break;
            case MONTH:
                cal.add(Calendar.MONTH, iValue);
                break;
            case DAY:
                cal.add(Calendar.DAY_OF_MONTH, iValue);
                break;
            case HOUR:
                cal.add(Calendar.HOUR, iValue);
                break;
            case HOUR_OF_DAY:
                cal.add(Calendar.HOUR_OF_DAY, iValue);
                break;
            case MINUTE:
                cal.add(Calendar.MINUTE, iValue);
                break;
            case SECOND:
                cal.add(Calendar.SECOND, iValue);
                break;
        }
        return cal.getTime();
    }

    /**
     * 计算两个日期的差值
     *
     * @param iField
     * @param startDate
     * @param endDate
     * @return
     */
    public static long dateDiff(DateUnit iField, Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        int startYear = Integer.parseInt(getFormatDate(DateFmtPattern.YYYY, startDate));
        int endYear = Integer.parseInt(getFormatDate(DateFmtPattern.YYYY, endDate));
        int startMonth = Integer.parseInt(getFormatDate(DateFmtPattern.MM, startDate)) - 1;
        int endMonth = Integer.parseInt(getFormatDate(DateFmtPattern.MM, endDate)) - 1;
        int startDay = Integer.parseInt(getFormatDate(DateFmtPattern.DD, startDate));
        int endDay = Integer.parseInt(getFormatDate(DateFmtPattern.DD, endDate));
        int startHour = Integer.parseInt(getFormatDate(DateFmtPattern.HH, startDate));
        int endHour = Integer.parseInt(getFormatDate(DateFmtPattern.HH, endDate));
        int startMinute = Integer.parseInt(getFormatDate(DateFmtPattern.MI, startDate));
        int endMinute = Integer.parseInt(getFormatDate(DateFmtPattern.MI, endDate));

        switch (iField) {
            case YEAR:
                return endYear - startYear;
            case MONTH:
                long yearDiff = endYear - startYear;
                long monthDiff = endMonth - startMonth;
                return yearDiff * 12L + monthDiff;
            case DAY:
                start.set(startYear, startMonth, startDay, 0, 0, 0);
                end.set(endYear, endMonth, endDay, 0, 0, 0);
                return (end.getTimeInMillis() - start.getTimeInMillis()) / 86400000L;
            case HOUR:
                start.set(startYear, startMonth, startDay, startHour, 0, 0);
                end.set(endYear, endMonth, endDay, endHour, 0, 0);
                return (end.getTimeInMillis() - start.getTimeInMillis()) / 3600000L;
            case HOUR_OF_DAY:
                start.set(startYear, startMonth, startDay, startHour, 0, 0);
                end.set(endYear, endMonth, endDay, endHour, 0, 0);
                return (end.getTimeInMillis() - start.getTimeInMillis()) / 3600000L;
            case MINUTE:
                start.set(startYear, startMonth, startDay, startHour, startMinute, 0);
                end.set(endYear, endMonth, endDay, endHour, endMinute, 0);
                return (end.getTimeInMillis() - start.getTimeInMillis()) / 60000L;
            case SECOND:
                return (endDate.getTime() - startDate.getTime()) / 1000L;
        }

        return 0;
    }

    public static Date dateAdd(DateUnit iField, int iValue) {
        return dateAdd(iField, iValue, now());
    }

    /**
     * 去掉时分秒部分
     *
     * @param date
     * @return
     */
    public static Date dateTrunc(Date date) {
        return getDate(DateFmtPattern.YYYYMMDD, getFormatDate(DateFmtPattern.YYYYMMDD, date));
    }

    /**
     * 获取给定日期所在月份的总天数
     *
     * @param date
     * @return
     */
    public static long getMonthDayCount(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期位于第几周(一年中的第几周/一月中的第几周)
     *
     * @param type
     * @param date
     * @return
     */
    public static int getWeekNum(DateUnit type, Date date) {
        Date monthenddate = getMonthEndDay(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthenddate);

        if (type == DateUnit.YEAR) {
            return cal.get(Calendar.WEEK_OF_YEAR);
        }
        if (type == DateUnit.MONTH) {
            return cal.get(Calendar.WEEK_OF_MONTH);
        }

        return 0;
    }

    /**
     * 创建日期对象
     *
     * @param hour
     * @param minute
     * @param second
     * @param month
     * @param day
     * @param year
     * @return
     */
    public static Date mktime(int hour, int minute, int second, int month, int day, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute, second);
        return cal.getTime();
    }

    /**
     * 获取timestamp对象
     *
     * @param date
     * @return
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Timestamp getCurrentDateTimestamp() {
        return getTimestamp(now());
    }

    public static enum DateFmtPattern {

        YY("yy"), YYYY("yyyy"), MM("MM"), DD("dd"), MM_DD("MM/dd"),
        //
        HH("HH"), MI("mm"),
        //
        YYYYMM("yyyyMM"), YYYYMMDD("yyyyMMdd"), YYYYMMDDHHMI("yyyyMMddHHmm"), YYYYMMDDHHMISS("yyyyMMddHHmmss"),
        //
        YYYY_MM("yyyy/MM"), YYYY_MM_DD("yyyy/MM/dd"), YYYY_MM_DD_HH_MI("yyyy/MM/dd HH:mm"), YYYY_MM_DD_HH_MI_SS("yyyy/MM/dd HH:mm:ss"),
        //
        YYYY__MM("yyyy-MM"), YYYY__MM__DD("yyyy-MM-dd"), YYYY__MM__DD__HH__MI("yyyy-MM-dd HH:mm"), YYYY__MM__DD__HH__MI__SS("yyyy-MM-dd HH:mm:ss"),
        //
        EEEMMMddHHmmsszzzyyyy("EEE MMM dd HH:mm:ss zzz yyyy"),
        //
        YYYY$MM("yyyy年MM月"), YYYY$MM$DD("yyyy年MM月dd日"), YYYY$MM$DD$HH$MI("yyyy年MM月dd日HH点mm分"), YYYY$MM$DD$HH$MI$SS("yyyy年MM月dd日HH点mm分ss秒"),
        //
        YYYY$$MM("yyyy 年 MM 月"), YYYY$$MM$$DD("yyyy 年 MM 月 dd 日"), YYYY$$MM$$DD$$HH$$MI("yyyy 年 MM 月 dd 日 HH 点 mm 分"), YYYY$$MM$$DD$$HH$$MI$$SS("yyyy 年 MM 月 dd 日 HH 点 mm 分 ss 秒");

        private String value;

        private DateFmtPattern(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public int length() {
            return this.value.length();
        }
    }

    public static enum DateUnit {

        YEAR(1), MONTH(2), DAY(3), HOUR(10), HOUR_OF_DAY(11), MINUTE(12), SECOND(13);

        private int value;

        private DateUnit(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }


    public static Date getMinDate(Date a, Date b) {
        if(a == null || b == null) {
            return a != null ? b : a;
        }
        if(a.compareTo(b) <= 0)
            return a;
        else
            return b;
    }

    public static Date getMaxDate(Date a, Date b) {
        if(a == null || b == null) {
            return a != null ? a : b;
        }
        if(a.compareTo(b) > 0)
            return a;
        else
            return b;
    }

    public static int getDateMonth(Date date) {
        Date monthenddate = getMonthEndDay(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthenddate);
        return cal.get(Calendar.MONTH);
    }


    /**
     * 把毫秒转化成日期
     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec(毫秒数)
     * @return
     */
    public static Date transferLongToDate(String dateFormat,long millSec){
//     SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return new Date(millSec);
    }


    /**
     * 获取第N天的时间戳
     * @param dateStr
     * @return
     */
    public static Date getDateByTimestampStr(long dateStr,int i){
        Date date=transferLongToDate("yyyy-MM-dd",dateStr);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime(); //这个时间就是日期往后推一天的结果
    }
}  