import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  
import java.sql.Timestamp;  
import java.text.DateFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  

/**
 * 一个包含了多种情况下时间格式转换的范例
 *
 * @author glorychou
 */
public class TimeConvert {
    public static void main(String[] args) throws Exception {  
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
}  