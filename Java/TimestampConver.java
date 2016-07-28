import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  
import java.sql.Timestamp;  
import java.text.DateFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
  
public class Test {  
    public static void main(String[] args) throws Exception {  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //首先设定日期时间格式,HH指使用24小时制,hh是使用12小时制  
          
        //获取当前时间，并转换为String  
        Date date = new Date();//获取当前时间，Date型  
        String dateStr = dateFormat.format(date);   // Date型转换成String型，可直接存储进数据库  
        System.out.println("String:" + dateStr);  
          
        //将当前时间转换为从1970年1月1日到现在所经历的毫秒数  
        Long dateLong = date.getTime();             // Date型转换成Long型毫秒数，用于计算  
        System.out.println("毫秒数dateLong:" + dateLong);  
          
        Timestamp timestamp = new Timestamp(dateLong); //Long型毫秒数转为Timestamp型，可直接存储进数据库  
  
        System.out.println("Timestamp:" + timestamp);  
          
        Long dateLong2 = timestamp.getTime();  
        System.out.println("毫秒数dateLong2:" + dateLong2);//Timestamp类型转换为Long型毫秒数  
          
        Date date2 = null;  
        try {  
            date2 = dateFormat.parse(dateStr);//String型转换为Date型  
        } catch (ParseException e1) {  
            e1.printStackTrace();  
        }  
        System.out.println("Date:" + date2);  
          
        String dateStr2 = dateFormat.format(date2);//Date型转换为String型  
        System.out.println("String:" + dateStr2);  
          
        Date date3 = new Date(dateLong2);//Long型毫秒数转换为Date型  
        System.out.println("date3:" + date3);  
          
        Connection conn = DB.createConn();  
        String insert_sql = "insert into _test values (0,'" + dateStr +"');";  
        // String insert_sql = "insert into _test values (0,'" + dateLong +"');";  
        // String insert_sql = "insert into _test values (0,'" + timestamp +"');";  
        // 以上三个sql语句都是可以执行的，即String、Long、Timestamp三种格式的数据都是可以直接存储的  
        System.out.println(insert_sql);  
        String sql = "insert into _test values (null,?)";  
        PreparedStatement ps = DB.prepare(conn, sql);  
        try {  
            ps.executeUpdate(insert_sql);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        DB.close(ps);  
        DB.close(conn);  
          
    }  
}  