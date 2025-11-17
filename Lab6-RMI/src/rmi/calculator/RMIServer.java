package rmi.calculator;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Create and export the remote object
            CalculatorImpl calc = new CalculatorImpl();

            // Create RMI registry on port 1099
            LocateRegistry.createRegistry(1098);

            // Bind the remote object to the registry
            Naming.rebind("rmi://localhost/CalculatorService", calc);

            System.out.println("✅ RMI Calculator Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
