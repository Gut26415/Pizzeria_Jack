import Enums.TipoMasa;
import Enums.TipoSalsa;
import Enums.Topping;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Main {
    private static final List<Orden> ordenes = new ArrayList<>();

    private static JFrame ventana;
    private static DefaultListModel<String> modeloOrdenes;
    private static JLabel pizzaActual;
    private static Cliente cliente;
    private static Orden ordenActual;
    private static TipoMasa masa;
    private static TipoSalsa salsa;
    private static final List<Topping> toppings = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::mostrarDatosCliente);
    }

    private static void mostrarDatosCliente() {
        JFrame datos = new JFrame("Datos del cliente");
        datos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField campoId = new JTextField();
        JTextField campoNombre = new JTextField();
        JTextField campoCorreo = new JTextField();
        JTextField campoDireccion = new JTextField();

        JPanel formulario = new JPanel(new GridLayout(4, 2, 8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formulario.add(new JLabel("ID:"));
        formulario.add(campoId);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Correo:"));
        formulario.add(campoCorreo);
        formulario.add(new JLabel("Dirección:"));
        formulario.add(campoDireccion);

        JButton continuar = new JButton("Continuar");
        continuar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText().trim());
                String nombre = campoNombre.getText().trim();
                String correo = campoCorreo.getText().trim();
                String direccion = campoDireccion.getText().trim();

                if (id <= 0 || nombre.isEmpty()
                        || correo.isEmpty() || direccion.isEmpty()) {
                    JOptionPane.showMessageDialog(datos,
                            "Completa todos los datos e ingresa un ID positivo.");
                    return;
                }

                cliente = new Cliente(id, nombre, correo, direccion);
                ordenActual = null;
                limpiarPizza();

                datos.dispose();

                if (ventana == null) {
                    mostrarVentanaPizzas();
                } else {
                    ventana.setVisible(true);
                    actualizarPantalla();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(datos, "El ID debe ser un número.");
            }
        });

        datos.add(formulario, BorderLayout.CENTER);
        datos.add(continuar, BorderLayout.SOUTH);
        datos.setSize(400, 250);
        datos.setLocationRelativeTo(null);
        datos.setVisible(true);
    }

    private static void mostrarVentanaPizzas() {
        ventana = new JFrame("Pizzería Jack - Crear orden");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout(10, 10));

        JPanel panelOrdenes = new JPanel(new BorderLayout(5, 5));
        panelOrdenes.setBorder(BorderFactory.createTitledBorder("Órdenes hechas"));

        modeloOrdenes = new DefaultListModel<>();
        JList<String> listaOrdenes = new JList<>(modeloOrdenes);
        panelOrdenes.add(new JScrollPane(listaOrdenes), BorderLayout.CENTER);
        ventana.add(panelOrdenes, BorderLayout.NORTH);

        JPanel menus = new JPanel(new GridLayout(3, 1, 5, 5));
        menus.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelMasas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMasas.add(new JLabel("Masa:"));
        for (TipoMasa opcion : TipoMasa.values()) {
            JButton boton = new JButton(opcion.toString());
            boton.addActionListener(e -> {
                masa = opcion;
                actualizarPantalla();
            });
            panelMasas.add(boton);
        }
        menus.add(panelMasas);

        JPanel panelSalsas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSalsas.add(new JLabel("Salsa:"));
        for (TipoSalsa opcion : TipoSalsa.values()) {
            JButton boton = new JButton(opcion.toString());
            boton.addActionListener(e -> {
                salsa = opcion;
                actualizarPantalla();
            });
            panelSalsas.add(boton);
        }
        menus.add(panelSalsas);

        JPanel panelToppings = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelToppings.add(new JLabel("Toppings:"));
        for (Topping opcion : Topping.values()) {
            JButton boton = new JButton(opcion.toString());
            boton.addActionListener(e -> {
                if (toppings.size() >= 3) {
                    JOptionPane.showMessageDialog(ventana,
                            "La pizza admite un máximo de 3 toppings.");
                    return;
                }

                toppings.add(opcion);
                actualizarPantalla();
            });
            panelToppings.add(boton);
        }
        menus.add(panelToppings);

        ventana.add(menus, BorderLayout.CENTER);

        JPanel parteInferior = new JPanel(new GridLayout(2, 1, 5, 5));
        parteInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pizzaActual = new JLabel();
        parteInferior.add(pizzaActual);

        JPanel acciones = new JPanel(new FlowLayout());

        JButton agregarPizza = new JButton("Añadir pizza");
        agregarPizza.addActionListener(e -> agregarPizza());
        acciones.add(agregarPizza);

        JButton revisar = new JButton("Revisar orden");
        revisar.addActionListener(e -> revisarOrden());
        acciones.add(revisar);

        JButton nuevoCliente = new JButton("Nuevo cliente");
        nuevoCliente.addActionListener(e -> {
            ventana.setVisible(false);
            mostrarDatosCliente();
        });
        acciones.add(nuevoCliente);

        parteInferior.add(acciones);
        ventana.add(parteInferior, BorderLayout.SOUTH);

        actualizarPantalla();
        ventana.setSize(700, 450);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private static void agregarPizza() {
        if (masa == null || salsa == null) {
            JOptionPane.showMessageDialog(ventana,
                    "Selecciona una masa y una salsa.");
            return;
        }

        Topping[] ingredientes = new Topping[3];
        for (int i = 0; i < toppings.size(); i++) {
            ingredientes[i] = toppings.get(i);
        }

        Pizza pizza = new Pizza(masa, salsa, ingredientes);

        if (ordenActual == null) {
            cliente.hacerPedido(pizza);
            ordenActual = cliente.getOrdenes().get(
                    cliente.getOrdenes().size() - 1
            );
            ordenes.add(ordenActual);
        } else {
            ordenActual.agregarPizza(pizza);
        }

        limpiarPizza();
        actualizarPantalla();

        JOptionPane.showMessageDialog(ventana,
                "Pizza añadida a la orden.");
    }

    private static void revisarOrden() {
        if (ordenActual == null) {
            JOptionPane.showMessageDialog(ventana,
                    "Primero añade una pizza.");
            return;
        }

        StringBuilder detalle = new StringBuilder();
        detalle.append("Cliente: ")
                .append(cliente.getNombre())
                .append("\nCorreo: ")
                .append(cliente.getCorreo())
                .append("\nDirección: ")
                .append(cliente.getDireccion())
                .append("\n\n");

        for (int i = 0; i < ordenActual.getPizzas().size(); i++) {
            Pizza pizza = ordenActual.getPizzas().get(i);

            detalle.append("Pizza ")
                    .append(i + 1)
                    .append(": ")
                    .append(pizza.getMasa())
                    .append(", SALSA: ")
                    .append(pizza.getSalsa());

            for (Topping topping : pizza.getArray()) {
                if (topping != null) {
                    detalle.append(", ").append(topping);
                }
            }

            detalle.append("\n");
        }

        JOptionPane.showMessageDialog(ventana, detalle.toString(),
                "Revisar orden", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void limpiarPizza() {
        masa = null;
        salsa = null;
        toppings.clear();
    }

    private static void actualizarPantalla() {
        if (modeloOrdenes == null) {
            return;
        }

        modeloOrdenes.clear();

        for (int i = 0; i < ordenes.size(); i++) {
            Orden orden = ordenes.get(i);
            modeloOrdenes.addElement(
                    "Orden " + (i + 1)
                    + " - " + orden.getCliente().getNombre()
                    + " - " + orden.getPizzas().size() + " pizza(s)"
            );
        }

        pizzaActual.setText(
                "Pizza actual: masa " + (masa == null ? "sin elegir" : masa)
                + " | salsa " + (salsa == null ? "sin elegir" : salsa)
                + " | toppings " + toppings
        );
    }
}