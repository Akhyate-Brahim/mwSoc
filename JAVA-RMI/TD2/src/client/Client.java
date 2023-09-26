package client;

import common.Distante;
import common.IClient;
import common.ResultatChild;
import common.Service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements IClient {
    String name;
    public Client(String name) throws RemoteException {
        super();
        this.name=name;
    }
    @Override
    public String toString(){
        return name;
    }
    public static void main(String[] args){
        try {
            Scanner scanner =new Scanner(System.in);
            System.out.println("enter your client name :");
            IClient currentClient = new Client(scanner.nextLine());
            Thread.sleep(10000);
            Registry registry = LocateRegistry.getRegistry("localhost",1099);
            Distante d = (Distante) registry.lookup("MonOD");
            Service service = d.service();
            System.out.println("this is initial int, we'll multiply 10 times : "+ service.getInt());
            for (int j=1;j<5;j++){
                System.out.println("Result : "+service.multiply(2, currentClient));
            }
            scanner.close();


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}