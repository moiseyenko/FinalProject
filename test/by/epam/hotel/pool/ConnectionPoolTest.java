package by.epam.hotel.pool;

import org.testng.annotations.Test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.Deque;

public class ConnectionPoolTest {

	@DataProvider
	public Object[][] dp() {
		return new Object[][] { new Object[] 
				{ ConnectionPool.getInstance() }};
	}

	@Test(dataProvider = "dp")
	public void getInstanceTest(ConnectionPool pool) {
		assertSame(ConnectionPool.getInstance(), pool);
	}

	@Test(dataProvider = "dp", expectedExceptions = CloneNotSupportedException.class)
	public void ClonePoolTest(ConnectionPool pool) throws CloneNotSupportedException {
		pool.clone();
	}
	
	Deque<Connection> connections = new ArrayDeque<>();
	
	@Test(dataProvider = "dp", groups="group1", priority=2,timeOut=100)
	public void getConnectionTest(ConnectionPool pool) {
		for (int i = 0; i < 11; i++) {
			Connection connection = pool.getConnection();
			connections.push(connection);
			assertEquals(ProxyConnection.class, connection.getClass());
		}
	}
	
	@AfterMethod(groups="group1")
	public void releaseConnectionTest() {
		Connection connection;
		while ((connection = connections.poll()) != null) {
			ConnectionPool.getInstance().releaseConnection(connection);
		}
	}
	
	@Test(dataProvider = "dp", timeOut=100, priority=3)
	public void destroyPoolTest(ConnectionPool pool)  {
		pool.destroyPool();
	}

}
