package eg.edu.alexu.csd.oop.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
public class MyDriver implements Driver{
   Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		if(url.equals("jdbc:xmldb://localhost")) {
			logger.info("url is accepted");
			return true;
		}
		else {
			logger.severe("url is not accepted");
		return false;}
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		if(acceptsURL(url)) {
		MyConnection connection=new	MyConnection(info.get("path").toString());
		logger.info("connection has been created sucessfully");
		return connection;
		}
		else {
			logger.severe("Failed to create connection");
		return null;}
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		DriverPropertyInfo[] prop=new DriverPropertyInfo[info.size()+1];
		prop[0]=new DriverPropertyInfo("url",url);
		prop[0]=new DriverPropertyInfo("path",info.getProperty("path"));
		return prop;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

}
