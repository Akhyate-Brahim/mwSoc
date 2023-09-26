package common;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Service extends Remote{
    int getInt() throws RemoteException;
    int multiply(int param,IClient cname) throws RemoteException;
}
