package server;

import common.IClient;
import common.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServiceImpl extends UnicastRemoteObject implements Service {
    private int n=1;
    public ServiceImpl() throws RemoteException{
        super();
    }


    @Override
    public int getInt() throws RemoteException {
        return n;
    }

    @Override
    public synchronized int multiply(int param, IClient cname) throws RemoteException{
        this.n *= param;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread:"+Thread.currentThread().getName()+" val renvoye : " + n + " au Client "+cname);
        return this.n;
    }
}
