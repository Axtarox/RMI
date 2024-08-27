package RMI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            ImplementacionSalarios obj = new ImplementacionSalarios();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("SalariosService", obj);
            System.out.println("Servidor listo");
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}