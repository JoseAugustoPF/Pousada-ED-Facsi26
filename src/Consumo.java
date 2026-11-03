import java.time.LocalDate;

public class Consumo {
    private LocalDate data;
    private String descricao;
    private Integer quantidade;
    private Double precoUnitario;

    public Consumo(LocalDate data, String descricao, Integer quantidade, Double precoUnitario) {
        this.data = data;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Double subtotal(){
        return quantidade * precoUnitario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
