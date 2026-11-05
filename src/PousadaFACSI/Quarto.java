package PousadaFACSI;

public class Quarto {
    private Integer numero;
    private TipoQuarto tipo;
    private Integer capacidade;
    private boolean jaTeveHospedagem = false;

    public Quarto(Integer numero, TipoQuarto tipo, Integer capacidade) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidade = capacidade;
    }

    public double valorDiaria(){
        if (tipo == null) {
            throw new IllegalStateException("Tipo do quarto inválido");
        }
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

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public boolean getJaTeveHospedagem() {
        return jaTeveHospedagem;
    }

    public void setJaTeveHospedagem(boolean valor) {
        jaTeveHospedagem = valor;
    }
}