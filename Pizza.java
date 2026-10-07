import Enums.TipoMasa;
import Enums.TipoSalsa;
import Enums.Topping;

public class Pizza {
    private TipoMasa masa;
    private TipoSalsa salsa;
    private Topping array[] = new Topping[3];

    public Pizza(TipoMasa masa, TipoSalsa salsa, Topping[] array) {
        this.masa = masa;
        this.salsa = salsa;
        this.array = array;
    }

    public TipoMasa getMasa() {
        return masa;
    }

    public TipoSalsa getSalsa() {
        return salsa;
    }

    public Topping[] getArray() {
        return array;
    }

    public void setMasa(TipoMasa masa) {
        this.masa = masa;
    }

    public void setSalsa(TipoSalsa salsa) {
        this.salsa = salsa;
    }

    public void setArray(Topping[] array) {
        this.array = array;
    }

    public void addTopping(Topping topping) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = topping;
                return;
            }
        }
    }

    public void addTopping(Topping topping, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            addTopping(topping);
        }
    }

    
}
