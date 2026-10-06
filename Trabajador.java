import Enums.TipoTrabajador;

public class Trabajador extends User {
    private TipoTrabajador tipoTrabajador;
    private String entrada;
    private String salida;

    public Trabajador(int id, String nombre, String correo, String direccion, TipoTrabajador tipoTrabajador, String entrada, String salida) {
        super(id, nombre, correo, direccion);
        this.tipoTrabajador = tipoTrabajador;
        this.entrada = entrada;
        this.salida = salida;
    }

    public TipoTrabajador getTipoTrabajador() {
        return tipoTrabajador;
    }

    public String getEntrada() {
        return entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setTipoTrabajador(TipoTrabajador tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String hacerPizza() {
       return "Pizza hecha";
    }
}