package PousadaFACSI;
import java.time.LocalDate;

public class Consumo {
    private LocalDate data;
    private String descricao;
    private Integer quantidade;
    private Double precoUnitario;

    public Consumo(LocalDate data, String descricao, Integer quantidade, Double precoUnitario) {
        setData(data);
        setDescricao(descricao);
        setQuantidade(quantidade);
        setPrecoUnitario(precoUnitario);
    }

    public Double subtotal() {return quantidade * precoUnitario;}

    public LocalDate getData() {return data;}

    public void setData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("Data inválida");
        }
        this.data = data;
    }

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição inválida");
        }
        this.descricao = descricao;
    }

    public Integer getQuantidade() {return quantidade;}

    public void setQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {return precoUnitario;}

    public void setPrecoUnitario(Double precoUnitario) {
        if (precoUnitario == null || precoUnitario < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
        this.precoUnitario = precoUnitario;
    }
}