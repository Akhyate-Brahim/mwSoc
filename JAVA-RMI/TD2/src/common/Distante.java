package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Distante extends Remote {
    String echo() throws RemoteException;
    Resultat result(int i,String infoCB,int pin) throws RemoteException;
    Service service() throws RemoteException;
}