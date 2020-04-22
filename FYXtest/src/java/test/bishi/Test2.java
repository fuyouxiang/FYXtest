package java.test.bishi;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;


/**
 * @author 程婧
 */
public class Test2 {
	
	public static void main(String[] args) throws SQLException {

	Connection con;
	
	//驱动
	String driver = "com.mysql.jdbc.Driver";

	//数据库URL
	String url = "jdbc:mysql://localhost:8888/db1";

	//MySQL配置时的用户名
	String user = "root";

	//MySQL配置时的密码
	String password = "123456";

	//加载驱动程序
		try {

			// 直连接的方式加载驱动
			Class.forName(driver);
			
			//连接MySQL数据库
			con = DriverManager.getConnection(url,user,password);
				
			if(!con.isClosed()) {
				System.out.println("数据库连接成功");
			}

			//创建statement类对象执行SQL语句
			Statement statement = (Statement) con.createStatement();

	
			//要执行的SQL语句
			//********这个地方题目有歧义，前一天是指当前时间往前24时内的数据？还是前一天0点当今天0点的数据？	
			//********还可以在java中获取时间，设定为数据库时间戳一样的格式，然后直接拼接在下面sql中去查。但是这种方法有隐患，数据库与服务器的时间如果有误差会导致数据查询不准确。
			String sql = "SELECT * FROM push WHERE TO_DAYS(NOW())-TO_DAYS(updateTime)=1";
			
			//ResultSet类，用来存放获取的结果集
			ResultSet rs = statement.executeQuery(sql);
			
			//
			String title = null;
			while(rs.next()){
				
				//获取title这列数据
				title = rs.getString("title");

				//输出结果
				System.out.println("push表中前一天的所有的title"+title);
			}
			rs.close();
			con.close();		 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
