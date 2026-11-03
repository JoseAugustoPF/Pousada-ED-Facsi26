public class Quarto {
    private Integer numero;
    private TipoQuarto tipo;
    private Integer capacidadde;

    public Quarto(Integer numero, TipoQuarto tipo, Integer capacidadde) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidadde = capacidadde;
    }

    public double valorDiaria(){
        switch (tipo){
            case STANDARD:
                return 180.0;
            case LUXO:
                return 260.0;
            case SUITE:
                return 390.0;
            default:
                return 0.0;
        }
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidadde() {
        return capacidadde;
    }

    public void setCapacidadde(Integer capacidadde) {
        this.capacidadde = capacidadde;
    }
}
