import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class LoggingServices extends UnicastRemoteObject implements LoggingServicesAI {

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
            registry.bind("LoggingServices", obj);

            String[] boundNames = registry.list();
            System.out.println("Registered services:");
            for (String name : boundNames) {
                System.out.println("\t" + name);
            }
            // Bind this object instance to the name CreateServices' port in the rmiregistry, as per registry.properties

        } catch (Exception e)
        {
            System.out.println("LoggingServices binding err: " + e.getMessage());
            e.printStackTrace();
        }

    } // main

    @Override
    public void log(String message) throws RemoteException {

        try {
            long timestamp = new Date().getTime();
            System.out.println(message + String.format(" (%d)", timestamp));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
