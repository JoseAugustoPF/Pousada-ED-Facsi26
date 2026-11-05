package PousadaFACSI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Pousada {
    private String nome;
    private  String cidade;
    private List<Quarto> quartos;
    private List<Hospedagem> hospedagens;
    private LinkedList<Hospedagem> espera;

    public Pousada(String nome , String cidade){
        this.nome = nome;
        this.cidade = cidade;
        this.quartos = new ArrayList<>();
        this.hospedagens = new ArrayList<>();
        this.espera = new LinkedList<>();
    }
    private boolean jaTeveHospedagem = false;
    public boolean getJaTeveHospedagem() {
        return jaTeveHospedagem;
    }

    public void setJaTeveHospedagem(boolean valor) {
        jaTeveHospedagem = valor;
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

    public List<Hospedagem> getHospedagens() {
        return hospedagens;
    }

    public void setHospedagens(List<Hospedagem> hospedagens) {
        this.hospedagens = hospedagens;
    }

    public LinkedList<Hospedagem> getEspera() {
        return espera;
    }

    public void setEspera(LinkedList<Hospedagem> espera) {
        this.espera = espera;
    }

    public void adicionarQuarto(Quarto q) {
        if (q == null) {
            throw new IllegalArgumentException("Quarto inválido");
        }
        if (q.getNumero() == null || q.getNumero() <= 0 ||
                q.getTipo() == null ||
                q.getCapacidade() == null ||
                q.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Dados do quarto inválidos");
        }
        for (Quarto existente : quartos) {
            if (existente.getNumero().equals(q.getNumero())) {
                throw new IllegalArgumentException("Número de quarto já cadastrado");
            }
        }
        int i = 0;
        while (i < quartos.size() &&
                quartos.get(i).getNumero() < q.getNumero()) {
            i++;
        }
        quartos.add(i, q);
    }

    public void removerQuarto(Quarto q) {
        if (q == null) {
            throw new IllegalArgumentException("Quarto inválido");
        }
        int indice = -1;
        for (int i = 0; i < quartos.size(); i++) {
            if (quartos.get(i) == q) {
                indice = i;
                break;
            }
        }
        if (indice == -1) {
            throw new IllegalArgumentException("Quarto não encontrado");
        }
        if (q.getJaTeveHospedagem()) {
            throw new IllegalArgumentException(
                    "Não é possível remover um quarto que já teve hospedagem"
            );
        }
        quartos.remove(indice);
    }

    public Quarto quartoLivre(TipoQuarto tipo, LocalDate entrada, LocalDate saida) {
        if (tipo == null || entrada == null || saida == null || !saida.isAfter(entrada)) {
            throw new IllegalArgumentException("Período inválido");
        }
        for (Quarto q : quartos) {
            if (q.getTipo() == tipo &&
                    quartoDisponivelNoPeriodo(q, entrada, saida)) {
                return q;
            }
        }
        return null;
    }

    public boolean registrarHospedagem(Hospedagem h) {
        if (h == null) {
            throw new IllegalArgumentException("Hospedagem inválida");
        }
        if (h.getQuarto() != null ||
                hospedagens.contains(h) ||
                espera.contains(h)) {
            throw new IllegalArgumentException(
                    "Hospedagem já registrada ou possui quarto"
            );
        }
        Quarto q = quartoLivre(
                h.getTipo(),
                h.getCheckin(),
                h.getCheckout()
        );
        if (q == null) {
            espera.addLast(h);
            return false;
        }
        h.setQuarto(q);
        q.setJaTeveHospedagem(true);
        hospedagens.add(h);
        return true;
    }

    public Hospedagem cancelarHospedagem(Hospedagem h) {
        if (h == null || !hospedagens.contains(h)) {
            return null;
        }
        Quarto liberado = h.getQuarto();
        hospedagens.remove(h);
        h.setQuarto(null);
        for (int i = 0; i < espera.size(); i++) {
            Hospedagem candidata = espera.get(i);
            if (candidata.getTipo() == liberado.getTipo() &&
                    quartoDisponivelNoPeriodo(
                            liberado,
                            candidata.getCheckin(),
                            candidata.getCheckout())) {
                candidata.setQuarto(liberado);
                liberado.setJaTeveHospedagem(true);
                hospedagens.add(candidata);
                espera.remove(i);

                return candidata;
            }
        }
        return null;
    }

    public String fechamento(int ano, int mes) {
        if (mes < 1 || mes > 12) {throw new IllegalArgumentException("Mês inválido");}
        ArrayList<Hospedagem> lista = new ArrayList<>();

        double total = 0.0;
        double standard = 0.0;
        double luxo = 0.0;
        double suite = 0.0;

        for (Hospedagem h : hospedagens) {
            if (h.getCheckout().getYear() == ano && h.getCheckout().getMonthValue() == mes) {
                lista.add(h);
                double valor = h.total();
                total += valor;
                switch (h.getTipo()) {
                    case STANDARD:
                        standard += valor;
                        break;
                    case LUXO:
                        luxo += valor;
                        break;
                    case SUITE:
                        suite += valor;
                        break;
                }
            }
        }

        lista.sort((h1, h2) ->
                h1.getCheckout().compareTo(h2.getCheckout()));

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("FECHAMENTO MENSAL\n");
        relatorio.append(String.format("%02d/%d\n\n", mes, ano));

        for (Hospedagem h : lista) {
            relatorio.append(String.format(
                    "%s | Quarto %d | Checkout: %s | Total: R$ %.2f\n",
                    h.getHospede(),
                    h.getQuarto().getNumero(),
                    h.getCheckout(),
                    h.total()
            ));
        }
        relatorio.append(String.format(
                "\nRECEITA TOTAL: R$ %.2f\n", total));
        relatorio.append("\nRECEITA POR TIPO:\n");
        relatorio.append(String.format("STANDARD: R$ %.2f\n", standard));
        relatorio.append(String.format("LUXO: R$ %.2f\n", luxo));
        relatorio.append(String.format("SUITE: R$ %.2f\n", suite));

        long noites = 0;

        for (Hospedagem h : hospedagens) {
            noites += h.noitesNoMes(ano, mes);
        }

        int diasMes = java.time.YearMonth.of(ano, mes).lengthOfMonth();
        int capacidade = quartos.size() * diasMes;
        double ocupacao = capacidade == 0 ? 0.0 : (double) noites / capacidade * 100;
        relatorio.append(String.format("\nOCUPAÇÃO: %.2f%%\n", ocupacao));
        return relatorio.toString();
    }
    private boolean quartoDisponivelNoPeriodo(Quarto q, LocalDate entrada, LocalDate saida) {
        for (Hospedagem h : hospedagens) {
            if (h.getQuarto() == q &&
                    entrada.isBefore(h.getCheckout()) &&
                    h.getCheckin().isBefore(saida)) {
                return false;
            }
        }
        return true;
    }
}