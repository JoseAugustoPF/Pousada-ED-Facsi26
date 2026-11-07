package PousadaFACSI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GravadorArquivo {

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void salvar(Pousada pousada, String caminho) {
        try {
            File pasta = new File(caminho).getAbsoluteFile().getParentFile();
            if (pasta != null && !pasta.exists()) {
                pasta.mkdirs();
            }
            PrintWriter pw = new PrintWriter(new FileWriter(caminho));

            pw.println(pousada.getNome() + ";" + pousada.getCidade());

            pw.println("QUARTOS;" + pousada.getQuartos().size());
            for (Quarto q : pousada.getQuartos()) {
                pw.println(q.getNumero() + ";" + q.getTipo() + ";" + q.getCapacidade());
            }

            pw.println("HOSPEDAGENS;" + pousada.getHospedagens().size());
            for (Hospedagem h : pousada.getHospedagens()) {
                pw.println(h.getHospede() + ";" + h.getDocumento() + ";"
                        + h.getQuarto().getNumero() + ";"
                        + h.getCheckin().format(fmt) + ";"
                        + h.getCheckout().format(fmt) + ";"
                        + h.getConsumos().size());
                for (Consumo c : h.getConsumos()) {
                    pw.println(c.getData().format(fmt) + ";" + c.getDescricao() + ";"
                            + c.getQuantidade() + ";"
                            + String.format(Locale.US, "%.2f", c.getPrecoUnitario()));
                }
            }

            pw.close();
            System.out.println("Dados salvos em " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}