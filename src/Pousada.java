import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Pousada {
    private String nome;
    private  String cidade;
    private List<Quarto> quartos;
    private List<Hospedagem> hospedagems;
    private LinkedList<Hospedagem> espera;

    public Pousada(String nome , String cidade){
        this.nome = nome;
        this.cidade = cidade;
        this.quartos = new ArrayList<>();
        this.hospedagems = new ArrayList<>();
        this.espera = new LinkedList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos;
    }

    public List<Hospedagem> getHospedagems() {
        return hospedagems;
    }

    public void setHospedagems(List<Hospedagem> hospedagems) {
        this.hospedagems = hospedagems;
    }

    public LinkedList<Hospedagem> getEspera() {
        return espera;
    }

    public void setEspera(LinkedList<Hospedagem> espera) {
        this.espera = espera;
    }

    public void adicionarQuarto(Quarto q){
        //pro cara das regras implementar cadastro e validaçao
    }

    public void removerQuarto(Quarto q){
        //pro caba das regras implementar remoçao
    }

    public Quarto quartoLivre(TipoQuarto tipo, LocalDate entrada, LocalDate saida){
        //caba das regraas implementar a busca
        return null;
    }

    public boolean registrarHospedagem(Hospedagem h){
        //implementar
        return false;
    }

    public Hospedagem cancelarHospedagem(Hospedagem h){
        //caba das regras
        return null;
    }

    public String fechamento(int ano, int mes){
        //implementarcara das regras
        return "";
    }
}
