package rmi.calculator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Implementation of remote methods
public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

    protected CalculatorImpl() throws RemoteException {
        super();
    }

    public double add(double a, double b) throws RemoteException {
        return a + b;
    }

    public double subtract(double a, double b) throws RemoteException {
        return a - b;
    }

    public double multiply(double a, double b) throws RemoteException {
        return a * b;
    }

    public double divide(double a, double b) throws RemoteException {
        if (b == 0) throw new RemoteException("Cannot divide by zero!");
        return a / b;
    }
}

