package PousadaFACSI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class LeitorArquivo {

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private BufferedReader br;
    private int numeroLinha;

    private ArrayList<Quarto> quartosLidos;
    private ArrayList<Hospedagem> hospedagensLidas;

    public Pousada carregar(String caminho) {
        numeroLinha = 0;
        quartosLidos = new ArrayList<>();
        hospedagensLidas = new ArrayList<>();
        Pousada pousada = new Pousada("Sem nome", "Sem cidade");

        try {
            br = new BufferedReader(new FileReader(caminho));

            String linha = proximaLinha();
            if (linha == null) {
                System.out.println("Arquivo vazio.");
                br.close();
                return pousada;
            }
            String[] campos = linha.split(";");
            if (campos.length == 2) {
                pousada = new Pousada(campos[0].trim(), campos[1].trim());
            } else {
                erro("esperado nome;cidade", linha);
            }

            int n = lerQuantidade("QUARTOS");
            for (int i = 0; i < n; i++) {
                linha = proximaLinha();
                if (linha == null) {
                    break;
                }
                try {
                    Quarto q = lerQuarto(linha);
                    quartosLidos.add(q);
                    pousada.adicionarQuarto(q);
                } catch (NumberFormatException e) {
                    erro("numero invalido", linha);
                } catch (IllegalArgumentException e) {
                    erro(e.getMessage(), linha);
                }
            }

            int h = lerQuantidade("HOSPEDAGENS");
            for (int i = 0; i < h; i++) {
                linha = proximaLinha();
                if (linha == null) {
                    break;
                }
                campos = linha.split(";");
                int qtdConsumos = 0;
                Hospedagem hosp = null;
                try {
                    if (campos.length != 6) {
                        throw new IllegalArgumentException("hospedagem deve ter 6 campos");
                    }
                    qtdConsumos = Integer.parseInt(campos[5].trim());
                    hosp = lerHospedagem(campos);
                } catch (NumberFormatException e) {
                    erro("numero invalido", linha);
                } catch (DateTimeParseException e) {
                    erro("data invalida", linha);
                } catch (IllegalArgumentException e) {
                    erro(e.getMessage(), linha);
                }

                for (int j = 0; j < qtdConsumos; j++) {
                    String linhaConsumo = proximaLinha();
                    if (linhaConsumo == null) {
                        break;
                    }
                    if (hosp == null) {
                        continue;
                    }
                    try {
                        hosp.adicionarConsumo(lerConsumo(linhaConsumo, hosp));
                    } catch (NumberFormatException e) {
                        erro("numero invalido", linhaConsumo);
                    } catch (DateTimeParseException e) {
                        erro("data invalida", linhaConsumo);
                    } catch (IllegalArgumentException e) {
                        erro(e.getMessage(), linhaConsumo);
                    }
                }

                if (hosp != null) {
                    hospedagensLidas.add(hosp);
                    pousada.restaurarHospedagem(hosp);
                }
            }

            br.close();
            System.out.println("Arquivo carregado: " + quartosLidos.size() + " quartos e "
                    + hospedagensLidas.size() + " hospedagens.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo " + caminho + " nao encontrado. O sistema vai iniciar vazio.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return pousada;
    }

    private int lerQuantidade(String secao) throws IOException {
        String linha = proximaLinha();
        if (linha == null) {
            return 0;
        }
        String[] campos = linha.split(";");
        if (campos.length != 2 || !campos[0].trim().equals(secao)) {
            erro("esperado " + secao + ";quantidade", linha);
            return 0;
        }
        try {
            return Integer.parseInt(campos[1].trim());
        } catch (NumberFormatException e) {
            erro("quantidade invalida", linha);
            return 0;
        }
    }

    private Quarto lerQuarto(String linha) {
        String[] campos = linha.split(";");
        if (campos.length != 3) {
            throw new IllegalArgumentException("quarto deve ter 3 campos");
        }
        int numero = Integer.parseInt(campos[0].trim());
        TipoQuarto tipo = converterTipo(campos[1].trim());
        int capacidade = Integer.parseInt(campos[2].trim());

        if (capacidade <= 0) {
            throw new IllegalArgumentException("capacidade deve ser maior que zero");
        }
        if (buscarQuarto(numero) != null) {
            throw new IllegalArgumentException("quarto " + numero + " repetido");
        }
        return new Quarto(numero, tipo, capacidade);
    }

    private Hospedagem lerHospedagem(String[] campos) {
        String hospede = campos[0].trim();
        String documento = campos[1].trim();
        int numeroQuarto = Integer.parseInt(campos[2].trim());
        LocalDate checkin = LocalDate.parse(campos[3].trim(), fmt);
        LocalDate checkout = LocalDate.parse(campos[4].trim(), fmt);

        Quarto quarto = buscarQuarto(numeroQuarto);
        if (quarto == null) {
            throw new IllegalArgumentException("quarto " + numeroQuarto + " nao existe");
        }
        if (temSobreposicao(quarto, checkin, checkout)) {
            throw new IllegalArgumentException("quarto " + numeroQuarto + " ja ocupado nesse periodo");
        }

        Hospedagem h = new Hospedagem(hospede, documento, checkin, checkout, quarto.getTipo());
        h.setQuarto(quarto);
        return h;
    }

    private Consumo lerConsumo(String linha, Hospedagem h) {
        String[] campos = linha.split(";");
        if (campos.length != 4) {
            throw new IllegalArgumentException("consumo deve ter 4 campos");
        }
        LocalDate data = LocalDate.parse(campos[0].trim(), fmt);
        String descricao = campos[1].trim();
        int quantidade = Integer.parseInt(campos[2].trim());
        double preco = Double.parseDouble(campos[3].trim());

        if (data.isBefore(h.getCheckin()) || data.isAfter(h.getCheckout())) {
            throw new IllegalArgumentException("data do consumo fora da hospedagem");
        }
        if (quantidade <= 0 || preco < 0) {
            throw new IllegalArgumentException("quantidade ou preco invalido");
        }
        preco = Math.round(preco * 100) / 100.0;
        return new Consumo(data, descricao, quantidade, preco);
    }

    private TipoQuarto converterTipo(String texto) {
        if (texto.equals("STANDARD")) {
            return TipoQuarto.STANDARD;
        } else if (texto.equals("LUXO")) {
            return TipoQuarto.LUXO;
        } else if (texto.equals("SUITE")) {
            return TipoQuarto.SUITE;
        }
        throw new IllegalArgumentException("tipo de quarto invalido");
    }

    private Quarto buscarQuarto(int numero) {
        for (Quarto q : quartosLidos) {
            if (q.getNumero() == numero) {
                return q;
            }
        }
        return null;
    }

    private boolean temSobreposicao(Quarto quarto, LocalDate entrada, LocalDate saida) {
        for (Hospedagem h : hospedagensLidas) {
            if (h.getQuarto() == quarto && entrada.isBefore(h.getCheckout())
                    && h.getCheckin().isBefore(saida)) {
                return true;
            }
        }
        return false;
    }

    private String proximaLinha() throws IOException {
        String linha = br.readLine();
        numeroLinha++;
        while (linha != null && linha.trim().isEmpty()) {
            linha = br.readLine();
            numeroLinha++;
        }
        return linha;
    }

    private void erro(String motivo, String linha) {
        System.out.println("Linha " + numeroLinha + " ignorada (" + motivo + "): " + linha);
    }
}