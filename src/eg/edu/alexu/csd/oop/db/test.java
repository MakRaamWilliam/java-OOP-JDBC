package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Assert;

public class test {

	public static void main(String[] args) throws SQLException {
		String databaseName="test";
		boolean drop=true;
		Driver driver = new MyDriver();
		Properties info=new Properties();
		File dbDir=new File("sample" + System.getProperty("file.separator") + ((int)(Math.random() * 100000)));
		info.put("path", dbDir.getAbsoluteFile());
        Connection connection = driver.connect("jdbc:xmldb://localhost", info);
        Statement statement = connection.createStatement();
        statement.execute("DROP DATABASE " + databaseName);
        if(drop)
            statement.execute("CREATE DATABASE " + databaseName);       
        Statement statement2 = connection.createStatement();
        statement.execute("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)");        
        int count1 = statement.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
        Assert.assertNotEquals("Insert returned zero rows", 0, count1);
        int count2 = statement.executeUpdate("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 5)");
        Assert.assertNotEquals("Insert returned zero rows", 0, count2);
        int count3 = statement.executeUpdate("INSERT INTO table_name1(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 6)");
        Assert.assertEquals("Insert returned zero rows", 1, count3);
        ResultSet result = statement.executeQuery("SELECT column_name2 FROM table_name1 WHERE coluMN_NAME2 < 6");
        int rows = 0;
   //     while(result.next())    rows++;
      System.out.println(result.isBeforeFirst()); result.next();
        System.out.println(result.getInt("column_NAME2"));
        System.out.println(result.getMetaData().getTableName(1));
        statement2.close();
	}

}
