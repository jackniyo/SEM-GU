import java.rmi.*;

public interface LoggingServicesAI extends java.rmi.Remote  {

    void log(String message) throws RemoteException;
    //boolean logError(String msg, String serviceName) throws RemoteException;

}
