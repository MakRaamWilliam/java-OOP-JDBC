package eg.edu.alexu.csd.oop.db;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class MyMetadata implements ResultSetMetaData {

	private	ArrayList<String> namesCol ;
	private	ArrayList<String> nameInt ;
    private Object dataResult [][];
    private String tablename;
   
    public MyMetadata (Object data[][], ArrayList<String> namesCol, ArrayList<String> nameInt, String tablename  ) {
		this.dataResult = data;
		this.namesCol= namesCol;
		this.nameInt=nameInt;
		this.tablename=tablename;
	}
    
    
    @Override
    public int getColumnCount() throws SQLException {
    	
    	return dataResult[0].length;
    }
    
    @Override
    public String getColumnLabel(int column) throws SQLException {
    	if(column>0 && column <=dataResult[0].length ) {
    		return namesCol.get(column -1);
    	}
    	throw new SQLException();
    }
    @Override
    public String getColumnName(int column) throws SQLException {
    	if(column>0 && column <=dataResult[0].length ) {
    		return namesCol.get(column -1);
    	}
    	throw new SQLException();
    }
    
@Override
public int getColumnType(int column) throws SQLException {
	if(column>0 && column <=dataResult[0].length ) {
		if(nameInt.contains(namesCol.get(column -1))) {
			return Types.INTEGER ;
		} return Types.VARCHAR ;
	}
	
	throw new SQLException();
	
}

 @Override
public String getTableName(int column) throws SQLException {
	
	 
	 if(column>0 && column <=dataResult[0].length )
	return this.tablename;
	 throw new SQLException();
}

/////// not our methods 
@Override
public <T> T unwrap(Class<T> iface) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}


@Override
public boolean isWrapperFor(Class<?> iface) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean isAutoIncrement(int column) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean isCaseSensitive(int column) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean isSearchable(int column) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean isCurrency(int column) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public int isNullable(int column) throws SQLException {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public boolean isSigned(int column) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public int getColumnDisplaySize(int column) throws SQLException {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public String getSchemaName(int column) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}


@Override
public int getPrecision(int column) throws SQLException {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public int getScale(int column) throws SQLException {
	// TODO Auto-generated method stub
	return 0;
}


@Override
public String getCatalogName(int column) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}


@Override
public String getColumnTypeName(int column) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}


@Override
public boolean isReadOnly(int column) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean isWritable(int column) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public boolean isDefinitelyWritable(int column) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}


@Override
public String getColumnClassName(int column) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}
    
 
 
}
