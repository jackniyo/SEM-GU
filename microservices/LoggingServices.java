import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LoggingServices implements LoggingServicesAI{
    
     // Set up the JDBC driver name and database URL
    static final String JDBC_CONNECTOR = "com.mysql.jdbc.Driver";  
    static final String DB_URL = Configuration.getJDBCConnection();

    // Set up the orderinfo database credentials
    static final String USER = "root";
    static final String PASS = Configuration.MYSQL_PASSWORD;

    public LoggingServices() throws RemoteException {}

    // Main service loop
    public static void main(String args[]) 
    { 	 
        try 
        { 
            LoggingServices obj = new LoggingServices();

            Registry registry = Configuration.createRegistry();
            registry.bind("LoggingServices ", obj);

            String[] boundNames = registry.list();
            System.out.println("Registered services:");
            for (String name : boundNames) {
                System.out.println("\t" + name);
            }
            // Bind this object instance to the name CreateServices' port in the rmiregistry, as per registry.properties

        } catch (Exception e) 
        {
            System.out.println("Logging binding err: " + e.getMessage()); 
            e.printStackTrace();
        } 

    } 

    public void register(String msg) throws RemoteException
    {
      	// Local declarations
        Connection conn = null;		                 // connection to the orderinfo database
        Statement stmt = null;		                 // A Statement object is an interface that represents a SQL statement.
       // String ReturnString = "logging Created";	     // Return string. If everything works you get an 'OK' message
        							                 // if not you get an error string
        try
        {
            // Here we load and initialize the JDBC connector. Essentially a static class
            // that is used to provide access to the database from inside this class.
            Class.forName(JDBC_CONNECTOR);

            //Open the connection to the orderinfo database

            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // Here we execute the addition query. Not that the Statement class is part
            // of the Java.rmi.* package that enables you to submit SQL queries to the database
            // that we are connected to (via JDBC in this case).

            stmt = conn.createStatement();
            
            String sql = "INSERT INTO loggingActivity(statment) VALUES (\""+msg+"\")";

            // execute the update
            stmt.executeUpdate(sql);

            // clean up the environment
            stmt.close();
            conn.close();
            stmt.close(); 
            conn.close();

        } catch(Exception e) 
        {
           // ReturnString = e.toString();
        } 
        
        

    } 

}
