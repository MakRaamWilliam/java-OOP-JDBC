package eg.edu.alexu.csd.oop.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;


public class MyStatement implements Statement{
	Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	MyConnection connection;
	ArrayList<String> batch=new ArrayList<String>();
	boolean closed=false;
	String path;
	GeneralDBMS database;
	int timeout=0;
public MyStatement(MyConnection con) {
	this.connection=con;
	path=connection.path;
	database=(GeneralDBMS) connection.database;
}
	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		if(closed==true) {
			logger.severe("This statement is closed");
			return;
		}
		batch.add(sql);
		logger.info("The query has been added to the batch sucessfully");
	}

	@Override
	public void cancel() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearBatch() throws SQLException {
		if(closed==true) {
			logger.severe("This statement is closed");
			return;
		}
		batch.clear();
		logger.info("The batch has been cleared sucessfully");
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws SQLException {
		if(closed==false) {
	    closed=true;
	    batch.clear();
	    connection=null;
	    logger.info("Statement has been closed sucessfully");}
		else {
			logger.severe("This statement is already closed");
			return;
		}
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		if(closed==false) {
			Query ob=new Query();
			String s=ob.call(sql);
			if(s==null) {logger.severe("Invalid query");
			throw new SQLException("Invalid query\n");}
			if(s.equals("update")) {
			try {
				int u=database.executeUpdateQuery(sql);
				if(u==0) {logger.severe("Invalid query");
				throw new SQLException("Invalid query\n");}
				logger.info("The query has been executed sucessfully");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
			else if (s.equals("structure")) {
				try {
					boolean t=database.executeStructureQuery(sql);
					   if(t==true) {logger.info("The query has been executed sucessfully");}
					   else {logger.severe("Failed to execute this query");}
						return t;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (s.equals("execute")) {
				Object[][] c=database.executeQuery(sql);
				if(c==null) {return false;}
				logger.info("The query has been executed sucessfully");
				return true;
			}
		}
		else {
			logger.severe("This statement is closed");
			throw new SQLException("This statement is closed\n");}
		     return false;
	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] executeBatch() throws SQLException {
		int arr[]=new int[batch.size()];
		Query ob=new Query();
		for(int i=0;i<batch.size();i++) {
			int result=0;
			String s=ob.call(batch.get(i));
			if(s.equals("update")) {
				result=database.executeUpdateQuery(batch.get(i));
			}
			else if (s.equals("structure")) {
				database.executeStructureQuery(batch.get(i));
				result=0;
			}
			else if (s.equals("execute")) {
				database.executeQuery(batch.get(i));
				result=0;
			}
			arr[i]=result;
		}
		return arr;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		Query ob1=new Query();
		String s=ob1.call(sql);
		if(s.equals("execute")) { sql = sql.toLowerCase();
			Object arr[][]=database.executeQuery(sql);
			if(arr==null) {return null;}
			String split[]=sql.split("\\s+");
			int index=0;
			for(int j=0;j<split.length;j++) {
				if(split[j].equalsIgnoreCase("from")) {
					index=j+1;
					j=split.length+1;
				}
			}
		//	System.out.println(database.tablesNames.get(split[index]));
			int numOfTable=database.tablesNames.get(split[index].toLowerCase()); MyResultset ob;
 if(sql.contains("*") )
 ob=new MyResultset(arr,database.dbtables.get(numOfTable).getNamesCol(),database.dbtables.get(numOfTable).getNameInt(),split[index],new MyStatement(connection));
 else {
	 ArrayList<String> namescol = new ArrayList<String>();
	 namescol.add(split[1]);
	 ob=new MyResultset(arr,namescol,database.dbtables.get(numOfTable).getNameInt(),split[index],new MyStatement(connection));
 
 }
 return ob;
		}
		else {
			return null;
		}
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		if(closed==false) {
		int result=0;
		Query ob=new Query();
		String s=ob.call(sql);
		if(s.equals("update")) {
			long start=System.currentTimeMillis();
			result=database.executeUpdateQuery(sql);
			if(result!=0) {logger.info("The query has been updated sucessfully");}
			long end=System.currentTimeMillis();
			int time=(int) (end-start);
			if((timeout!=0)&&(time>timeout)) {
				throw new SQLException("Time limit exeeded");
			}
		}
		return result;}
		else {
			logger.severe("This statement is closed");
			throw new SQLException("This statement is closed");
		}
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if(closed==true) {
			logger.severe("The Statement is closed");
			return null;
		}
		return connection;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return timeout;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		timeout=seconds;
		
	}

}

