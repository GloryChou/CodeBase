package fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Finance Group
 * @Date: 2018/12/12 20:07
 */
public class TimeUtils {
    /**
     * UnixTime of Day
     */
    public static final long UNIXTIME_STD_DAY = TimeUnit.SECONDS.convert(1, TimeUnit.DAYS) ;





    /**
     * ȡ��yyyy-MM-dd HH:mm:ss��ʽ�ĵ�ǰʱ��
     *
     * @return
     */
    public static String yyyyMMddHHmmssNow() {
        return yyyyMMddHHmmss(new Date());
    }

    /**
     * ȡ��yyyy-MM-dd HH:mm:ss��ʽ�ĵ�ǰʱ��
     *
     * @return
     */
    public static String yyyyMMddHHmmss() {
        return yyyyMMddHHmmss(new Date());
    }


    /**
     * ȡ��yyyy-MM-dd HH:mm:ss��ʽ�ĵ�ǰʱ��
     *
     * @return
     */
    public static String yyyyMMddHHmmss(long timestamp) {
        return yyyyMMddHHmmss(new Date(timestamp));
    }



    /**
     * ȡ��yyyy-MM-dd HH:mm:ss��ʽ��ʱ��
     *
     * @return
     */
    public static String yyyyMMddHHmmss(Date date) {
        return dateFormat4yyyyMMddHHmmss().format(date);
    }



    /**
     * ��yyyy-MM-dd HH:mm:ssת��ΪDate
     *
     * @param formatdate
     * @return
     */
    public static Date yyyyMMddHHmmss(String formatdate) {
        try
        {
            return dateFormat4yyyyMMddHHmmss().parse(formatdate);
        } catch (Exception e){throw new IllegalStateException("parse["+formatdate+"] with ex.", e);}
    }

    public static Date yyyyMMdd(String formatdate) {
        try
        {
            return dateFormat4yyyyMMdd().parse(formatdate);
        } catch (Exception e){throw new IllegalStateException("parse["+formatdate+"] with ex.", e);}
    }

    public static long getMilliseconds(String unixtime) {
        return Long.valueOf(unixtime)*1000 ;
    }


    public static Date setUnixTime(Date date, String unixtime) {
        if(date==null) date = new Date() ;
        date.setTime(getMilliseconds(unixtime)) ;
        return date ;
    }



    /**
     *
     * @param format
     * @param date
     * @return
     */
    public static String format(DateFormat format, Date date) {
        try{
            return format.format(date) ;
        } catch (Exception e){throw new IllegalArgumentException("format["+date+"] with ex.", e);}
    }


    /**
     *
     * @param format
     * @param source
     * @return
     */
    public static Date parse(DateFormat format, String source) {
        try{
            return format.parse(source) ;
        } catch (Exception e){throw new IllegalArgumentException("parse["+source+"] with ex.", e);}
    }


    /**
     *
     * @return
     */
    public static SimpleDateFormat dateFormatyyyyMMddHHmmss() {
        return new SimpleDateFormat("yyyyMMddHHmmss") ;
    }

    /**
     *
     * @return
     */
    public static SimpleDateFormat dateFormat4yyyyMMddHHmmss() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
    }


    /**
     *
     * @return
     */
    public static SimpleDateFormat dateFormat2yyyyMMddHHmmss() {
        return new SimpleDateFormat("yyyy��MM��dd��  HHʱmm��ss��") ;
    }

    /**
     *
     * @return yyyy-MM-dd��ʽ
     */
    public static SimpleDateFormat dateFormat4yyyyMMdd() {
        return new SimpleDateFormat("yyyy-MM-dd") ;
    }


    /**
     *
     * @return yyyyMMdd��ʽ
     */
    public static SimpleDateFormat yyyyMMddFormatter() {
        return new SimpleDateFormat("yyyyMMdd") ;
    }

    /**
     *
     * @return yyyy��MM��dd�ո�ʽ
     */
    public static DateFormat yyyyMMddChianiseFormatter() {
        return new SimpleDateFormat("yyyy��MM��dd��") ;
    }


    /**
     *
     * @param date
     * @return
     */
    public static String yyyyMMdd(Date date) {
        return dateFormat4yyyyMMdd().format(date) ;
    }

    public static String yyyyMMddHHMMSS(Date date){
        return dateFormat2yyyyMMddHHmmss().format(date);
    }

    public static Date newDateByUnixTimestamp(long unixTimestamp) {
        return unixTimestamp==0 ? null : new Date(unixTimestamp*1000) ;
    }

    public static long getUnixTimestamp(Date date) {
        return date == null ? 0 : date.getTime()/1000 ;
    }

    public static long unixtimestampOfNow() {
        return System.currentTimeMillis()/1000 ;
    }


    public static String stringOfUnixtimestampNow() {
        return String.valueOf(System.currentTimeMillis()/1000) ;
    }

    public static String stringOfUnixtimestamp(Date date) {
        return String.valueOf(date.getTime()/1000) ;
    }

    public static String yyyyMMddChianise(Date date) {
        return yyyyMMddChianiseFormatter().format(date) ;
    }
    public static void main(String[] args) {
        Date now = newDateByUnixTimestamp(1385027819) ;
        System.out.println(TimeUtils.yyyyMMdd(now));
    }

    /**
     *
     * @param date
     * @return
     */
    public static Date getDateZero(Date date) {
        Calendar c = Calendar.getInstance() ;
        if(date!=null) c.setTime(date) ;
        setCalendarToZero(c) ;
        return c.getTime() ;
    }

    /**
     * ��ȡ����Ľ���ʱ��
     * @return
     */
    public static Date getTodayLimit() {
        return getDateEndTime(new Date()) ;
    }



    /**
     * ��ȡĳһ��Ŀ�ʼʱ��
     * @param date
     * @return
     */
    public static Date getDateStartTime(Date date) {
        if(date==null) return null ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(date) ;
        setCalendarToZero(c) ;
        return c.getTime() ;
    }


    /**
     *
     * @param c
     * @return
     */
    public static void setCalendarToZero(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0) ;
        c.set(Calendar.MINUTE, 0) ;
        c.set(Calendar.SECOND, 0) ;
        c.set(Calendar.MILLISECOND, 0) ;
    }


    /**
     * ��ȡĳһ��Ľ���ʱ��
     * @param date
     * @return
     */
    public static Date getDateEndTime(Date date) {
        if(date==null) return null ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(date) ;
        c.add(Calendar.DAY_OF_MONTH, 1) ;
        setCalendarToZero(c) ;
        return c.getTime() ;
    }
    /**
     *
     * @return
     */
    public static Date getTodayZero() {
        return getDateZero(null) ;
    }


    public static Date parse(java.sql.Timestamp timestamp) {
        if(timestamp==null) return null ;
        return new Date(timestamp.getTime()) ;
    }


    /**
     *
     * @param birthday
     * @return
     */
    public static int getAge(Date birthday) {
        long day = TimeUnit.DAYS.convert(System.currentTimeMillis()-birthday.getTime(), TimeUnit.MILLISECONDS) ;
        return (int)(day/365) ;
    }

    /**
     * �õ����µ�һ�쿪ʼʱ��
     * @param now
     * @return
     */
    public static Date getMonthStartTime(Date date){
        if(date==null) return null ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(date) ;
        setCalendarToZero(c);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime() ;
    }

    public static Date getMonthEndTime(Date date){
        if(date==null) return null ;
        Date newDate = getMonthStartTime(date);
        Calendar c = Calendar.getInstance() ;
        c.setTime(newDate) ;
        setCalendarToZero(c);
        c.set(Calendar.MONTH,c.get(Calendar.MONTH)+1);
        return c.getTime() ;
    }
    /**
     * �õ����ܵ�һ�쿪ʼʱ��
     * @param now
     * @return
     */
    public static Date getWeekStartTime(Date date){
        if(date==null) return null ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(date) ;
        setCalendarToZero(c);
        c.set(Calendar.DAY_OF_WEEK, 1);
        return c.getTime() ;
    }

    /**
     * �õ����쿪ʼʱ��
     * @param now
     * @return
     */
    public static Date getYesterdayStartTime(Date date){
        if(date==null) return null ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(date) ;
        setCalendarToZero(c);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime() ;
    }

    /**
     * �õ�����֮ǰ������Ŀ�ʼʱ�� �� +value
     * �����֮�������Ŀ�ʼʱ��  -value
     * @param date
     * @return
     */
    public static Date getDayOffStartTime(Date date,int value){

        if(date==null) return null ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(date) ;
        setCalendarToZero(c);
        c.add(Calendar.DAY_OF_MONTH,value);
        return c.getTime() ;
    }

    /**
     * �õ�����ǰ�����Ľ���ʱ��
     * @param date
     * @param value
     * @return
     */
    public static Date getDayOffTime(Date date,int value){

        if(date==null) return null ;
        Calendar c = Calendar.getInstance() ;
        c.setTime(date) ;
        c.add(Calendar.DAY_OF_MONTH,value);
        setCalendarToZero(c);
        return c.getTime() ;
    }

    public static Date getYearStartTime(){
        Calendar c = Calendar.getInstance() ;
        setCalendarToZero(c);
        c.set(Calendar.DAY_OF_YEAR, 1);
        return c.getTime() ;
    }
    /**
     * ���ָ�����ڵ���һ��ʱ��
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        return calendar.getTime();
    }

    /**
     * ���ָ������ǰһ��ʱ��
     * @param date
     * @return
     */
    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * ��õ�ǰʱ�� valueСʱ���ʱ��
     * @param value
     * @return
     */
    public static int getDiffSecond(int value){
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.HOUR, value);
        date=c.getTime();
        int second = (int)(getUnixTimestamp(date) - getUnixTimestamp(new Date()));
        return second;
    }

    /**
     * ��������ʱ���
     * @param second
     * @return
     */
    @SuppressWarnings("unused")
    public static String getTimeDiff(int second) {
        int h = 0, d = 0, s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        return h + "Сʱ" + d + "����";
    }

    /**
     * �����������ڵ�������(����one=2016-01-06��other=2016-01-01), ֻ����������, ����ʱ����
     * @param one
     * @param other
     * @return
     */
    public static int daysBetween(Date one, Date other) {
        Calendar c = Calendar.getInstance() ;
        c.setTime(one);
        TimeUtils.setCalendarToZero(c);
        long first = c.getTimeInMillis() ;

        c.setTime(other);
        TimeUtils.setCalendarToZero(c);
        long second = c.getTimeInMillis() ;
        return (int)TimeUnit.DAYS.convert(first - second, TimeUnit.MILLISECONDS) ;
    }




    //-----ʱ�����ķ�����ʼ------------------------------------------------------//





    /**
     * �����ʱ
     *
     * @param beginTimeMillis ��ʼʱ��(MILLISECONDS)
     * @return ��ʱ(��λ:��)
     */
    public static long cost(long beginTimeMillis) {
        long endTimeMillis = System.currentTimeMillis() ;
        return cost(beginTimeMillis, endTimeMillis) ;
    }




    /**
     * �����ʱ
     *
     * @param beginTimeMillis ��ʼʱ��(MILLISECONDS)
     * @param endTimeMillis ����ʱ��(MILLISECONDS)
     *
     * @return ��ʱ(��λ:��)
     */
    public static long cost(long beginTimeMillis, long endTimeMillis) {
        return TimeUnit.SECONDS.convert(endTimeMillis - beginTimeMillis, TimeUnit.MILLISECONDS) ;
    }



    /**
     * ��ʽ�����ĵ�ʱ��(��ʽ:XСʱY����Z��)
     *
     * @param beginTimeMillis ��ʼʱ��(��λ:MILLISECONDS)
     *
     * @return
     */
    public static String costTime(long beginTimeMillis) {
        long cost = System.currentTimeMillis() - beginTimeMillis ;
        return formatCostTime(cost) ;
    }


    /**
     * ��ʽ�����ĵ�ʱ��(��ʽ:XСʱY����Z��)
     *
     * @param cost ���ĵ�ʱ��(MILLISECONDS)
     *
     * @return
     */
    public static String formatCostTime(long cost) {
        long cost_second = TimeUnit.SECONDS.convert(cost, TimeUnit.MILLISECONDS) ;
        long second = cost_second % 60 ;
        long minute = cost_second / 60 ;
        long hour = minute / 60 ;

        int type = 0 ;
        if(hour > 0) {
            type = 2 ;
            minute = minute % 60 ;
        } else if( hour == 0 && minute > 0 ) {
            type = 1 ;
        }

        String desc = "" ;
        if(type == 0) {
            desc = second + "��" ;
        } else if(type == 1) {
            desc = minute + "����" + second + "��" ;
        } else if( type == 2 ) {
            desc = hour + "Сʱ" + minute + "����" + second + "��" ;
        }

        return desc ;
    }
}
