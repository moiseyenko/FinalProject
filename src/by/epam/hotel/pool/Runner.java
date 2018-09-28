package by.epam.hotel.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Deprecated
public class Runner {
	
	public static void main(String[] args) throws SQLException {
		Properties properties = DatabaseManager.getProperties();
		Connection con = DriverManager.getConnection(properties.getProperty("url"), properties);
		Connection proxycon = new ProxyConnection(con);
		System.out.println(proxycon.getClass());
		System.out.println(ProxyConnection.class);
		/*ConnectionPool cp = ConnectionPool.getInstance();
		int inc=0;
		for (int i=0;i<50000;i++) {
			inc++;
			System.out.println("" +inc);
			new Thread(()->{
				Connection connection = cp.getConnection();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cp.releaseConnection(connection);
			}
			).start();
			if(i==1000) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cp.destroyPool();
			}
		}*/
	}
}
