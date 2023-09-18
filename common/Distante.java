package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Distante extends Remote {
    public String echo() throws RemoteException;
    public Resultat result(int i,int pin) throws RemoteException;
}