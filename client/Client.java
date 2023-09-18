package client;

import common.Distante;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try {
            Registry registry = LocateRegistry.getRegistry("172.20.10.2",1099);
            Distante d = (Distante) registry.lookup("MonOD");
            System.out.println("we will execute echo now!\n"+d.echo());
            Scanner scanner =new Scanner(System.in);
            System.out.println("enter a number : ");
            int i = scanner.nextInt();
            System.out.println("enter infoCB : ");
            String infoCB = scanner.next();
            System.out.println(d.result(i,infoCB));
            scanner.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}