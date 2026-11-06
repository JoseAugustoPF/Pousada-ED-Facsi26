package PousadaFACSI;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class GeradorMassa {

    public static void main(String[] args) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Random rnd = new Random(10);

        String[] nomes = {"Ana", "Bruno", "Carla", "Diego", "Elaine", "Felipe", "Gabriela",
                "Heitor", "Isabela", "Joao", "Karina", "Lucas", "Mariana", "Paula", "Rafael"};
        String[] sobrenomes = {"Silva", "Souza", "Costa", "Oliveira", "Pereira", "Lima",
                "Carvalho", "Ferreira", "Rocha", "Almeida"};
        String[] itens = {"Agua de coco", "Peixe frito (porcao)", "Jantar regional",
                "Cafe da manha extra", "Refrigerante lata", "Suco de cupuacu", "Tacaca", "Passeio de barco"};
        double[] precos = {6.00, 48.00, 55.00, 25.00, 7.50, 9.00, 18.00, 80.00};

        try {
            PrintWriter pw = new PrintWriter(new FileWriter("dados/grande.txt"));
            pw.println("Pousada Recanto do Tocantins;Maraba");

            pw.println("QUARTOS;20");
            for (int i = 101; i <= 108; i++) {
                pw.println(i + ";STANDARD;2");
            }
            for (int i = 201; i <= 207; i++) {
                pw.println(i + ";LUXO;3");
            }
            for (int i = 301; i <= 305; i++) {
                pw.println(i + ";SUITE;4");
            }

            pw.println("HOSPEDAGENS;100");
            int contador = 0;
            for (int q = 0; q < 20; q++) {
                int numeroQuarto;
                if (q < 8) {
                    numeroQuarto = 101 + q;
                } else if (q < 15) {
                    numeroQuarto = 201 + (q - 8);
                } else {
                    numeroQuarto = 301 + (q - 15);
                }

                LocalDate dia = LocalDate.of(2026, 8, 1).plusDays(rnd.nextInt(10));
                for (int k = 0; k < 5; k++) {
                    contador++;
                    String hospede = nomes[rnd.nextInt(nomes.length)] + " "
                            + sobrenomes[rnd.nextInt(sobrenomes.length)];
                    String documento = String.format("%011d", 10000000000L + contador);
                    LocalDate checkin = dia;
                    int diarias = 1 + rnd.nextInt(5);
                    LocalDate checkout = checkin.plusDays(diarias);

                    pw.println(hospede + ";" + documento + ";" + numeroQuarto + ";"
                            + checkin.format(fmt) + ";" + checkout.format(fmt) + ";2");

                    for (int c = 0; c < 2; c++) {
                        LocalDate data = checkin.plusDays(rnd.nextInt(diarias + 1));
                        int item = rnd.nextInt(itens.length);
                        int qtd = 1 + rnd.nextInt(3);
                        pw.println(data.format(fmt) + ";" + itens[item] + ";" + qtd + ";"
                                + String.format(Locale.US, "%.2f", precos[item]));
                    }
                    dia = checkout.plusDays(rnd.nextInt(8));
                }
            }

            pw.close();
            System.out.println("Arquivo dados/grande.txt gerado: 20 quartos, 100 hospedagens, 200 consumos.");
        } catch (IOException e) {
            System.out.println("Erro ao gerar o arquivo: " + e.getMessage());
        }
    }
}