package rmi.calculator;

import java.rmi.Naming;

public class RMIClient {
    public static void main(String[] args) {
        try {
            // Lookup the remote object
            Calculator calc = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");

            System.out.println("Connected to RMI Calculator Service\n");

            double a = 10, b = 5;

            System.out.println("Addition: " + calc.add(a, b));
            System.out.println("Subtraction: " + calc.subtract(a, b));
            System.out.println("Multiplication: " + calc.multiply(a, b));
            System.out.println("Division: " + calc.divide(a, b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
