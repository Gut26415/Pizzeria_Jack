import java.util.ArrayList;

public class Cliente extends User {
    private ArrayList<Orden> ordenes;
    private int puntos;

    public Cliente(int id, String nombre, String correo, String direccion) {
        super(id, nombre, correo, direccion); 
        this.ordenes = new ArrayList<>();
        this.puntos = 0;
    }

    public ArrayList<Orden> getOrdenes() {
        return ordenes;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setOrdenes(ArrayList<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void hacerPedido(Pizza pizza) {
        Orden orden = new Orden(this);
        orden.agregarPizza(pizza);
        ordenes.add(orden);
    }
}