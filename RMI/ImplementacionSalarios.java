package RMI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class ImplementacionSalarios extends UnicastRemoteObject implements InterfazSalarios {
    private double[][] matriz;
    private int numEmpleados;
    private int numMeses;

    public ImplementacionSalarios() throws RemoteException {
        super();
    }

    @Override
    public void llenarMatriz(int numEmpleados, int numMeses) throws RemoteException {
        this.numEmpleados = numEmpleados;
        this.numMeses = numMeses;
        matriz = new double[numEmpleados][numMeses];
        Random rand = new Random();

        for (int i = 0; i < numEmpleados; i++) {
            for (int j = 0; j < numMeses; j++) {
                // Generamos salarios entre 1'000.000 y 2'000.000
                matriz[i][j] = 1000000 + rand.nextDouble() * 1000000;
            }
        }
    }

    @Override
    public double[] totalPorEmpleado() throws RemoteException {
        double[] totales = new double[numEmpleados];
        for (int i = 0; i < numEmpleados; i++) {
            for (int j = 0; j < numMeses; j++) {
                totales[i] += matriz[i][j];
            }
        }
        return totales;
    }

    @Override
    public double[] promedioMensual() throws RemoteException {
        double[] promedios = new double[numMeses];
        for (int j = 0; j < numMeses; j++) {
            double suma = 0;
            for (int i = 0; i < numEmpleados; i++) {
                suma += matriz[i][j];
            }
            promedios[j] = suma / numEmpleados;
        }
        return promedios;
    }

    @Override
    public double totalPagado() throws RemoteException {
        double total = 0;
        for (int i = 0; i < numEmpleados; i++) {
            for (int j = 0; j < numMeses; j++) {
                total += matriz[i][j];
            }
        }
        return total;
    }
}