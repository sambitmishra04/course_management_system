module RMI_Calculator {
    requires java.rmi;            // allow access to java.rmi / RemoteException
    exports rmi.calculator;       // export your package so other modules (or runtime) can access it
}
