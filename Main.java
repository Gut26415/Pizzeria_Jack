import java.util.Scanner;
import Enums.TipoMasa;
import Enums.TipoSalsa;
import Enums.Topping;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Crear una orden ===");
        int id = leerEntero(scanner, "ID del cliente: ", 1, Integer.MAX_VALUE);
        String nombre = leerTexto(scanner, "Nombre: ");
        String correo = leerTexto(scanner, "Correo: ");
        String direccion = leerTexto(scanner, "Dirección: ");

        Cliente cliente = new Cliente(id, nombre, correo, direccion);
        int cantidadPizzas = leerEntero(scanner, "¿Cuántas pizzas deseas? ", 1, 100);
        Orden orden = null;

        for (int i = 0; i < cantidadPizzas; i++) {
            System.out.println("\nPizza " + (i + 1));
            TipoMasa masa = elegir(scanner, "masa", TipoMasa.values());
            TipoSalsa salsa = elegir(scanner, "salsa", TipoSalsa.values());
            Topping[] toppings = new Topping[3];
            int cantidadToppings = leerEntero(scanner, "Cantidad de toppings (0 a 3): ", 0, 3);
            for (int j = 0; j < cantidadToppings; j++) {
                toppings[j] = elegir(scanner, "topping " + (j + 1), Topping.values());
            }

            Pizza pizza = new Pizza(masa, salsa, toppings);
            if (orden == null) {
                cliente.hacerPedido(pizza);
                orden = cliente.getOrdenes().get(0);
            } else {
                orden.agregarPizza(pizza);
            }
        }

        System.out.println("\n=== Orden creada ===");
        System.out.println("Cliente: " + orden.getCliente().getNombre());
        System.out.println("Correo: " + orden.getCliente().getCorreo());
        System.out.println("Dirección: " + orden.getCliente().getDireccion());
        for (int i = 0; i < orden.getPizzas().size(); i++) {
            Pizza pizza = orden.getPizzas().get(i);
            System.out.println("Pizza " + (i + 1) + ": " + pizza.getMasa() + ", " + pizza.getSalsa());
            for (Topping topping : pizza.getArray()) {
                if (topping != null) System.out.println("  Topping: " + topping);
            }
        }
        System.out.println("Estado: " + (orden.isEstado() ? "lista" : "pendiente"));
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("Escribe un valor.");
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            try {
                int numero = Integer.parseInt(scanner.nextLine().trim());
                if (numero >= minimo && numero <= maximo) return numero;
            } catch (NumberFormatException e) {
            }
            System.out.println("Ingresa un número entre " + minimo + " y " + maximo + ".");
        }
    }

    private static <E extends Enum<E>> E elegir(Scanner scanner, String nombre, E[] opciones) {
        System.out.println("Elige " + nombre + ":");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
        return opciones[leerEntero(scanner, "Opción: ", 1, opciones.length) - 1];
    }
}
