public class Cocina {
    private Orden ordenes[];
    private Trabajador trabajadores[];

    public Cocina() {
        this.ordenes = new Orden[5];
        this.trabajadores = new Trabajador[5];
    }

    public Orden[] getOrdenes() {
        return ordenes;
    }

    public Trabajador[] getTrabajadores() {
        return trabajadores;
    }

    public void setOrdenes(Orden[] ordenes) {
        this.ordenes = ordenes;
    }

    public void setTrabajadores(Trabajador[] trabajadores) {
        this.trabajadores = trabajadores;
    }

    public void hacerPizza() {
        for (Orden orden : ordenes) {
            if (orden != null && !orden.isEstado()) {
                orden.cambioEstado();
                return;
            }
        }
    }
}
