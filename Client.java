import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Distante d = (Distante) registry.lookup("Distante");
            System.out.println("we will execute echo now!\n"+d.echo());
            Scanner scanner =new Scanner(System.in);
            scanner.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}