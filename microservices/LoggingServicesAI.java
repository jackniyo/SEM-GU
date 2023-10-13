import java.rmi.RemoteException;

public interface LoggingServicesAI extends java.rmi.Remote{
    
    public void register(String msg) throws RemoteException;

}
