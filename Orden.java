import java.util.ArrayList;

public class Orden {
    private ArrayList<Pizza> pizzas;
    private Cliente cliente;
    private boolean estado;

    public Orden(Cliente cliente) {
        this.pizzas = new ArrayList<>();
        this.cliente = cliente;
        this.estado = false;
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isEstado() { 
        return estado;
    }

    public void setPizzas(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void agregarPizza(Pizza pizza) {
        this.pizzas.add(pizza);
    }

    public void cambioEstado() {
        this.estado = !this.estado;
    }
}