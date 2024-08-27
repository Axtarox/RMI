package RMI;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfazSalarios extends Remote {
    void llenarMatriz(int numEmpleados, int numMeses) throws RemoteException;
    double[] totalPorEmpleado() throws RemoteException;
    double[] promedioMensual() throws RemoteException;
    double totalPagado() throws RemoteException;
}