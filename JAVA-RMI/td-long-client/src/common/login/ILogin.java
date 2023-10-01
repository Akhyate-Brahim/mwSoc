package common.login;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILogin extends Remote {
    boolean isSuccess(int studentNumber,String password) throws RemoteException;
}
