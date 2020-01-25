package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Properties;

public class main {

	public static void main(String[] args) {
		
		MyDriver driver = new MyDriver();
		Properties info=new Properties();
		File dbDir=new File("sample" + System.getProperty("file.separator") + ((int)(Math.random() * 100000)));
		info.put("path", dbDir.getAbsoluteFile());
		MyConnection connection = new MyConnection("path");
		MyStatement statement = new MyStatement(connection);
		try {
            connection = (MyConnection) driver.connect("jdbc:xmldb://localhost", info);
            statement = (MyStatement) connection.createStatement();
		}catch ( Exception e) {
			System.out.println(e);
		}
        while(true) {
        	Scanner scan = new Scanner(System.in);
    		String query = scan.nextLine();
			try {
    		    if(query.contains("select")) {
    		    	ArrayList<Integer> arr1 = new ArrayList<Integer>();
    		    	ArrayList<Integer> arr2 = new ArrayList<Integer>();
    			      MyResultset s = null;
				      s = (MyResultset) statement.executeQuery(query);
				      if(s==null) {throw new Exception("Empty table");}
				      for(int i=0; i<s.getMetaData().getColumnCount(); i++) {
            		         System.out.print(s.getMetaData().getColumnName(i+1) + "       ");
            		         arr1.add(s.getMetaData().getColumnName(i+1).length());
            	      }System.out.println();
                      while(!s.isLast()) {
                    	  s.next();
                	      for(int i=1; i<=s.getMetaData().getColumnCount(); i++) {
                		      System.out.print(s.getObject(i));
                		      arr2.add(i-1,s.getObject(i).toString().length());
                		      for (int j=0; j<arr1.get(i-1)+7-arr2.get(i-1) ; j++) {
                		    	  System.out.print(" ");
                		      }
                	      }System.out.println();
                      }
    		    }
    		    else {
    		    	statement.execute(query);
    			}
    		}catch(Exception e) {
    			System.out.print(e);
    		}
        }
	}
}