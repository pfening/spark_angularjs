package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
        private Database() {}
	
	private static final Database instance = new Database();

        private static Connection sqliteconn;	
        private final String sqliteurl = "jdbc:sqlite:C:\\NetBeansProjects\\spark_angularjs\\spark.db";
        

	public static Database getInstance() {
		return instance;
	}
        
        public Connection getSqliteConnection() throws Exception {            
		return sqliteconn;
	}

	public void SqliteConnect() throws Exception {
		Class.forName("org.sqlite.JDBC");
                sqliteconn = DriverManager.getConnection(sqliteurl);
                //System.out.println("S connected");
	}
	
	public void SqliteDisconnect() throws SQLException {
		sqliteconn.close();		
		sqliteconn = null;
                //System.out.println("S disconnected");
	}
	
}