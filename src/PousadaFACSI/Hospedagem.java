package PousadaFACSI;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Hospedagem {
    private String hospede;
    private String documento;
    private LocalDate checkin;
    private LocalDate checkout;
    private TipoQuarto tipo;
    private Quarto quarto;
    private ArrayList<Consumo> consumos;

    public Hospedagem(String hospede, String documento, LocalDate checkin, LocalDate checkout, TipoQuarto tipo) {
        if(!checkout.isAfter(checkin)){
            throw new IllegalArgumentException("O checkout deve ser posterior ao check-in");
        }
        this.hospede = hospede;
        this.documento = documento;
        this.checkin = checkin;
        this.checkout = checkout;
        this.tipo = tipo;
        this.quarto = null;
        this.consumos = new ArrayList<>();
    }

    public boolean adicionarConsumo(Consumo c){
        return consumos.add(c);
    }

    public void removerConsumo(Consumo c){
        consumos.remove(c);
    }

    public long diarias(){
        return ChronoUnit.DAYS.between(checkin, checkout);
    }

    public double totalConsumo(){
        double total = 0.0;
        for (Consumo c : consumos){
            total += c.subtotal();
        }
        return total;
    }

    public double taxaServico(){
        return totalConsumo() * 0.10;
    }

    public double total(){
        if (quarto == null){
            throw new IllegalStateException("A hospedagem ainda não possui quarto");
        }
        return diarias() * quarto.valorDiaria() + totalConsumo() + taxaServico();
    }

    public long noitesNoMes(int ano, int mes){
        YearMonth periodo = YearMonth.of(ano,mes);
        LocalDate inicio = checkin.isAfter(periodo.atDay(1)) ? checkin : periodo.atDay(1);
        LocalDate fim = checkout.isBefore(periodo.plusMonths(1).atDay(1)) ? checkout : periodo.plusMonths(1).atDay(1);
        if (!fim.isAfter(inicio)){
            return  0;
        }
        return ChronoUnit.DAYS.between(inicio, fim);
    }

    public String getHospede() {
        return hospede;
    }

    public void setHospede(String hospede) {
        this.hospede = hospede;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public ArrayList<Consumo> getConsumos() {
        return consumos;
    }

    public void setConsumos(ArrayList<Consumo> consumos) {
        this.consumos = consumos;
    }
}