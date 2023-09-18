package server;

import common.Distante;
import common.Resultat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ObjetDistante extends UnicastRemoteObject implements Distante {
    public ObjetDistante() throws RemoteException {
        super();
    }

    @Override
    public String echo() throws RemoteException {
        return "echo method!";
    }
    @Override
    public Resultat result(int i) throws RemoteException{
        return new Resultat(i);
    }
    public static void main(String[] args){
        try{
            Distante d = new ObjetDistante();
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.rebind("MonOD",d);
            System.out.println("Distante server is running...");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}