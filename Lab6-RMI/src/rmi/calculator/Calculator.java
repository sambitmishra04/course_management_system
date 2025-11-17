package rmi.calculator;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Remote Interface
public interface Calculator extends Remote {
    double add(double a, double b) throws RemoteException;
    double subtract(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;
}
