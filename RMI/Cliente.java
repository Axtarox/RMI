package RMI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {
    private static InterfazSalarios stub;
    private static JFrame frame;
    private static JTextField empleadosField;
    private static JTextField mesesField;
    private static JTextArea resultadosArea;
    private static JTable tabla;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            stub = (InterfazSalarios) registry.lookup("SalariosService");

            SwingUtilities.invokeLater(() -> createAndShowGUI());
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Sistema de Cálculo de Salarios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de entrada
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Entrada de Datos"));

        empleadosField = new JTextField(10);
        mesesField = new JTextField(10);

        inputPanel.add(new JLabel("Número de empleados:"));
        inputPanel.add(empleadosField);
        inputPanel.add(new JLabel("Número de meses:"));
        inputPanel.add(mesesField);

        JButton calcularButton = new JButton("Calcular");
        calcularButton.addActionListener(e -> calcularSalarios());
        inputPanel.add(calcularButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Panel de resultados
        JPanel resultadosPanel = new JPanel(new BorderLayout());
        resultadosPanel.setBorder(BorderFactory.createTitledBorder("Resultados"));

        tabla = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabla);
        resultadosPanel.add(scrollPane, BorderLayout.CENTER);

        resultadosArea = new JTextArea(5, 40);
        resultadosArea.setEditable(false);
        JScrollPane resultadosScroll = new JScrollPane(resultadosArea);
        resultadosPanel.add(resultadosScroll, BorderLayout.SOUTH);

        mainPanel.add(resultadosPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void calcularSalarios() {
        try {
            int numEmpleados = Integer.parseInt(empleadosField.getText());
            int numMeses = Integer.parseInt(mesesField.getText());

            stub.llenarMatriz(numEmpleados, numMeses);

            double[] totalesPorEmpleado = stub.totalPorEmpleado();
            double[] promediosMensuales = stub.promedioMensual();
            double totalPagado = stub.totalPagado();

            // Crear modelo de tabla para los totales por empleado
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Empleado");
            model.addColumn("Total Pagado");
            for (int i = 0; i < totalesPorEmpleado.length; i++) {
                model.addRow(new Object[]{"Empleado " + (i+1), String.format("%,.2f", totalesPorEmpleado[i])});
            }
            tabla.setModel(model);

            // Mostrar promedios mensuales y total en el área de texto
            StringBuilder resultado = new StringBuilder();
            resultado.append("Promedio mensual de salarios:\n");
            for (int i = 0; i < promediosMensuales.length; i++) {
                resultado.append(String.format("Mes %d: %,.2f\n", i+1, promediosMensuales[i]));
            }
            resultado.append(String.format("\nTotal pagado en la matriz: %,.2f\n", totalPagado));
            resultadosArea.setText(resultado.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}