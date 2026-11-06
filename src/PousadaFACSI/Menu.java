package PousadaFACSI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Menu{

    private Scanner scanner;
    private Pousada pousada;

    public Menu(Pousada pousada) {
        this.pousada = pousada;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao = 0;
        do{
            System.out.println("\n===== POUSADA FACSI =====");
            System.out.println("1 - Listar quartos e hospedagens");
            System.out.println("2 - Consultar detalhes");
            System.out.println("3 - Cadastrar quarto");
            System.out.println("4 - Adicionar elemento a hospedagem");
            System.out.println("5 - Remover quarto");
            System.out.println("6 - Remover consumo");
            System.out.println("7 - Buscar hospedagem");
            System.out.println("8 - Operações específicas");
            System.out.println("9 - Relatórios");
            System.out.println("10 - Salvar dados");
            System.out.println("11 - Sair");

            System.out.print("\nEscolha uma das opções acima. ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                if (opcao < 1 || opcao > 11) {
                    System.out.println("Opção inválida! Digite um número que represente uma das opções.");
                }else {

                switch (opcao) {
                    case 1:
                        listarQuartos();
                        break;
                    case 2:
                        consultarDetalhes();
                        break;
                    case 3:
                        cadastrarQuarto();
                        break;
                    case 4:
                        adicionarConsumo();
                        break;
                    case 5:
                        removerQuarto();
                        break;
                    case 6:
                        removerConsumo();
                        break;
                    case 7:
                        buscarHospedagem();
                        break;
                    case 8:
                        menuOperacoes();
                        break;
                    case 9:
                        menuRelatorios();
                        break;
                    case 10:
                        salvarDados();
                        break;
                    case 11:
                        String resposta;
                        do {
                            System.out.print("Deseja salvar os dados antes de sair? (S/N): ");
                            resposta = scanner.nextLine();
                            if (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("N")) {
                                System.out.println("Resposta inválida! Digite S ou N.");
                            }
                        } while (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("N"));
                        if (resposta.equalsIgnoreCase("S")) {
                            salvarDados();
                        }
                        break;
                    }
                }
            } catch (NumberFormatException erro) {
                System.out.println("Opção inválida! Digite um número que represente uma das opções. ");
            }
        }while (opcao != 11);
        System.out.println("Encerrando o sistema...");
    }

    private void listarQuartos() {
        System.out.println("\n===== QUARTOS CADASTRADOS =====");
        if (pousada.getQuartos().isEmpty()) {
            System.out.println("Nenhum quarto cadastrado.");
        } else {
            for (Quarto quarto : pousada.getQuartos()) {
                System.out.println(
                        "Número: " + quarto.getNumero() +
                                " | Tipo: " + quarto.getTipo() +
                                " | Capacidade: " + quarto.getCapacidade()
                );
            }
        }
    }

    private void consultarDetalhes() {

        if (pousada.getHospedagens().isEmpty()) {
            System.out.println("\nSem hospedagens cadastradas.");
        } else {
            System.out.print("\nDigite o documento do hóspede: ");
            String documento = scanner.nextLine();
            boolean encontrado = false;
            for (Hospedagem hospedagem : pousada.getHospedagens()) {
                if (hospedagem.getDocumento().equals(documento)) {
                    System.out.println("\n===== HOSPEDAGEM ENCONTRADA =====");
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("\nNenhuma hospedagem encontrada para esse documento.");
            }
        }
    }

    private void cadastrarQuarto() {
        System.out.print("\nDigite o número do quarto: ");
        int numero = Integer.parseInt(scanner.nextLine());

        System.out.println("Tipos disponíveis:");
        System.out.println("1 - STANDARD");
        System.out.println("2 - LUXO");
        System.out.println("3 - SUITE");
        System.out.print("Escolha o tipo do quarto: ");
        int opcaoTipo = Integer.parseInt(scanner.nextLine());

        TipoQuarto tipo = null;

        switch (opcaoTipo) {
            case 1:
                tipo = TipoQuarto.STANDARD;
                break;
            case 2:
                tipo = TipoQuarto.LUXO;
                break;
            case 3:
                tipo = TipoQuarto.SUITE;
                break;
            default:
                System.out.println("Tipo de quarto inválido!");
                return;
        }
        System.out.print("Digite a capacidade do quarto: ");
        int capacidade = Integer.parseInt(scanner.nextLine());
        Quarto quarto = new Quarto(numero, tipo, capacidade);
        try {
            pousada.adicionarQuarto(quarto);
            System.out.println("Quarto cadastrado com sucesso!");
        } catch (IllegalArgumentException erro) {
            System.out.println("Não foi possível cadastrar: " + erro.getMessage());
        }
    }

    private void adicionarConsumo() {
        System.out.println("Digite o documento do hóspede: ");
        String documento = scanner.nextLine();
        LocalDate data = null;

        while (data == null) {
            System.out.print("Digite a data do consumo (AAAA-MM-DD): ");

            try {
                data = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException erro) {
                System.out.println("Data inválida! Digite no formato AAAA-MM-DD.");
            }
        }
        System.out.print("Digite a descrição do consumo: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite a quantidade consumida: ");
        int quantidade = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o preço unitário: ");
        double precoUnitario = Double.parseDouble(scanner.nextLine());
        boolean encontrado = false;
        for (Hospedagem hospedagem : pousada.getHospedagens()) {

            if (hospedagem.getDocumento().equals(documento)) {
                encontrado = true;
                Consumo consumo = new Consumo(data, descricao, quantidade, precoUnitario);
                hospedagem.adicionarConsumo(consumo);
                System.out.println("Consumo adicionado com sucesso!");
                return;
            }
        }
        if (!encontrado) {
            System.out.println("Nenhuma hospedagem encontrada para esse documento.");
            return;
        }
    }

    private void removerQuarto() {
        System.out.print("\nDigite o número do quarto que deseja remover: ");
        int numero = Integer.parseInt(scanner.nextLine());
        Quarto quartoEncontrado = null;
        for (Quarto quarto : pousada.getQuartos()) {
            if (quarto.getNumero().equals(numero)) {
                quartoEncontrado = quarto;
                break;
            }
        }
        if (quartoEncontrado == null) {
            System.out.println("O quarto não foi encontrado.");
            return;
        }
        System.out.println("Quarto encontrado: " + numero);
        System.out.print("Tem certeza de que deseja removê-lo? (S/N): ");

        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            try {
                pousada.removerQuarto(quartoEncontrado);
                System.out.println("Quarto removido com sucesso!");
            } catch (IllegalArgumentException erro) {
                System.out.println("Não foi possível remover: " + erro.getMessage());
            }
        } else {
            System.out.println("Remoção cancelada.");
        }
    }

    private void removerConsumo() {
        System.out.print("\nDigite o documento do hóspede: ");
        String documento = scanner.nextLine();
        Hospedagem hospedagemEncontrada = null;
        for (Hospedagem hospedagem : pousada.getHospedagens()) {
            if (hospedagem.getDocumento().equals(documento)) {
                hospedagemEncontrada = hospedagem;
                break;
            }
        }
        if (hospedagemEncontrada == null) {
            System.out.println("Hospedagem não encontrada!");
            return;
        }
        if (hospedagemEncontrada.getConsumos().isEmpty()) {
            System.out.println("Essa hospedagem não possui consumos registrados.");
            return;
        }
        System.out.println("\n===== CONSUMOS DA HOSPEDAGEM =====");
        int contador = 1;
        for (Consumo consumo : hospedagemEncontrada.getConsumos()) {
            System.out.println(contador + " - " + consumo);
            contador++;
        }
        System.out.print("\nDigite o número do consumo que deseja remover: ");
        int indice = Integer.parseInt(scanner.nextLine());
        if (indice < 1 || indice > hospedagemEncontrada.getConsumos().size()) {
            System.out.println("Número de consumo inválido!");
            return;
        }
        Consumo consumoRemover = hospedagemEncontrada.getConsumos().get(indice - 1);
        System.out.println("Consumo selecionado: " + consumoRemover);
        System.out.print("Tem certeza de que deseja remover? (S/N): ");

        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            hospedagemEncontrada.removerConsumo(consumoRemover);
            System.out.println("Consumo removido com sucesso!");
        } else {
            System.out.println("Remoção cancelada.");
        }
    }

    private void buscarHospedagem() {
        System.out.print("\nDigite o nome do hóspede: ");
        String hospede = scanner.nextLine();
        System.out.print("Digite o documento do hóspede: ");
        String documento = scanner.nextLine();
        LocalDate checkin = null;
        while (checkin == null) {
            System.out.print("Digite a data de check-in (AAAA-MM-DD): ");
            try {
                checkin = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException erro) {
                System.out.println("Data inválida! Utilize o formato AAAA-MM-DD.");
            }
        }
        if (checkin.isBefore(LocalDate.now())) {
            System.out.println("O check-in não pode ser anterior à data atual.");
            return;
        }
        LocalDate checkout = null;
        while (checkout == null || !checkout.isAfter(checkin)) {
            System.out.print("Digite a data de checkout (AAAA-MM-DD): ");
            try {
                LocalDate dataInformada = LocalDate.parse(scanner.nextLine());
                if (!dataInformada.isAfter(checkin)) {
                    System.out.println("Erro: o checkout deve ser posterior ao check-in.");
                } else {
                    checkout = dataInformada;
                }
            } catch (DateTimeParseException erro) {
                System.out.println("Data inválida! Utilize o formato AAAA-MM-DD.");
            }
        }
        System.out.println("\nTipos de quarto disponíveis:");
        System.out.println("1 - STANDARD");
        System.out.println("2 - LUXO");
        System.out.println("3 - SUITE");
        System.out.print("Escolha o tipo de quarto: ");
        int opcaoTipo = Integer.parseInt(scanner.nextLine());
        TipoQuarto tipo = null;
        switch (opcaoTipo) {
            case 1:
                tipo = TipoQuarto.STANDARD;
                break;
            case 2:
                tipo = TipoQuarto.LUXO;
                break;
            case 3:
                tipo = TipoQuarto.SUITE;
                break;
            default:
                System.out.println("Tipo de quarto inválido!");
                return;
        }
        Hospedagem hospedagem;
        try {
            hospedagem = new Hospedagem(hospede, documento, checkin, checkout, tipo);
        } catch (IllegalArgumentException erro) {
            System.out.println("Erro no cadastro: " + erro.getMessage());
            return;
        }
        boolean registrada = pousada.registrarHospedagem(hospedagem);
        if (registrada) {
            System.out.println("Hospedagem registrada com sucesso! Quarto reservado.");
        } else {
            System.out.println("Não há quarto disponível desse tipo no período solicitado.");
            System.out.println("Hóspede adicionado à lista de espera.");
        }
    }

    private void menuOperacoes() {
        int opcao;
        do {
            System.out.println("\n===== OPERAÇÕES ESPECÍFICAS =====");
            System.out.println("1 - Cadastrar hospedagem (reserva)");
            System.out.println("2 - Cancelar hospedagem");
            System.out.println("3 - Lançar consumo");
            System.out.println("4 - Emitir conta");
            System.out.println("5 - Mapa de ocupação");
            System.out.println("0 - Voltar ao menu principal");

            System.out.print("\nEscolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        buscarHospedagem();
                        break;
                    case 2:
                        cancelarHospedagem();
                        break;
                    case 3:
                        adicionarConsumo();
                        break;
                    case 4:
                        emitirConta();
                        break;
                    case 5:
                        mapaOcupacao();
                        break;
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException erro) {
                System.out.println("Digite um número válido!");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cancelarHospedagem() {
        System.out.print("\nDigite o documento do hóspede: ");
        String documento = scanner.nextLine();
        Hospedagem hospedagemEncontrada = null;
        for (Hospedagem hospedagem : pousada.getHospedagens()) {
            if (hospedagem.getDocumento().equals(documento)) {
                hospedagemEncontrada = hospedagem;
                break;
            }
        }
        if (hospedagemEncontrada == null) {
            System.out.println("Nenhuma hospedagem encontrada para esse documento.");
            return;
        }
        System.out.println("\n===== HOSPEDAGEM ENCONTRADA =====");
        System.out.println("Hóspede: " + hospedagemEncontrada.getHospede());
        System.out.println("Documento: " + hospedagemEncontrada.getDocumento());
        System.out.println("Check-in: " + hospedagemEncontrada.getCheckin());
        System.out.println("Check-out: " + hospedagemEncontrada.getCheckout());
        System.out.print("\nTem certeza de que deseja cancelar esta hospedagem? (S/N): ");

        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            pousada.cancelarHospedagem(hospedagemEncontrada);
            System.out.println("Hospedagem cancelada com sucesso!");
        } else {
            System.out.println("Cancelamento interrompido.");
        }
    }

    private void emitirConta() {
        System.out.print("\nDigite o documento do hóspede: ");
        String documento = scanner.nextLine();
        Hospedagem hospedagemEncontrada = null;
        for (Hospedagem hospedagem : pousada.getHospedagens()) {
            if (hospedagem.getDocumento().equals(documento)) {
                hospedagemEncontrada = hospedagem;
                break;
            }
        }
        if (hospedagemEncontrada == null) {
            System.out.println("Nenhuma hospedagem encontrada para esse documento.");
            return;
        }
        if (hospedagemEncontrada.getQuarto() == null) {
            System.out.println("Não é possível emitir a conta: hospedagem ainda está na lista de espera.");
            return;
        }
        System.out.println("\n========== CONTA DA HOSPEDAGEM ==========");
        System.out.println("Hóspede: " + hospedagemEncontrada.getHospede());
        System.out.println("Documento: " + hospedagemEncontrada.getDocumento());
        System.out.println("Check-in: " + hospedagemEncontrada.getCheckin());
        System.out.println("Check-out: " + hospedagemEncontrada.getCheckout());
        System.out.println("Quarto: " + hospedagemEncontrada.getQuarto().getNumero());
        System.out.println("----------------------------------------");
        System.out.println("Diárias: " + hospedagemEncontrada.diarias());
        System.out.printf("Total das diárias: R$ %.2f%n",
                hospedagemEncontrada.diarias() * hospedagemEncontrada.getQuarto().valorDiaria());
        System.out.printf("Total dos consumos: R$ %.2f%n",
                hospedagemEncontrada.totalConsumo());
        System.out.printf("Taxa de serviço (10%%): R$ %.2f%n",
                hospedagemEncontrada.taxaServico());
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL A PAGAR: R$ %.2f%n",
                hospedagemEncontrada.total());
    }

    private void mapaOcupacao() {
        LocalDate data = null;
        while (data == null) {
            System.out.print("\nDigite a data para consultar a ocupação (AAAA-MM-DD): ");
            try {
                data = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException erro) {
                System.out.println("Data inválida! Utilize o formato AAAA-MM-DD.");
            }
        }
        if (pousada.getQuartos().isEmpty()) {
            System.out.println("Não existem quartos cadastrados na pousada.");
            return;
        }
        System.out.println("\nMAPA DE OCUPAÇÃO - " + data);
        System.out.println("--------------------------------");
        for (Quarto quarto : pousada.getQuartos()) {
            boolean ocupado = false;
            for (Hospedagem hospedagem : pousada.getHospedagens()) {
                if (hospedagem.getQuarto() == quarto && !data.isBefore(hospedagem.getCheckin()) && data.isBefore(hospedagem.getCheckout())){
                    ocupado = true;
                    break;
                }
            }
            if (ocupado) {
                System.out.println("Quarto " + quarto.getNumero() + ": OCUPADO");
            } else {
                System.out.println("Quarto " + quarto.getNumero() + ": LIVRE");
            }
        }
    }

    private void menuRelatorios() {
        int opcao;

        do {
            System.out.println("\n========== RELATÓRIOS ==========");
            System.out.println("1 - Fechamento mensal");
            System.out.println("2 - Lista de espera");
            System.out.println("0 - Voltar ao menu principal");
            System.out.println("================================");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException erro) {
                System.out.println("Opção inválida! Digite um número.");
                opcao = -1;
                continue;
            }
            switch (opcao) {
                case 1:
                    fechamentoMensal();
                    break;

                case 2:
                    imprimirListaEspera();
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private void fechamentoMensal() {
        int mes = 0;
        while (mes < 1 || mes > 12) {
            System.out.print("Digite o mês do fechamento (1 a 12): ");
            try {
                mes = Integer.parseInt(scanner.nextLine());
                if (mes < 1 || mes > 12) {
                    System.out.println("Mês inválido! Digite um valor entre 1 e 12.");
                }
            } catch (NumberFormatException erro) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }
        int ano = 0;
        while (ano < 2000 || ano > LocalDate.now().getYear()) {
            System.out.print("Digite o ano do fechamento: ");
            try {
                ano = Integer.parseInt(scanner.nextLine());
                if (ano < 2000 || ano > LocalDate.now().getYear()) {
                    System.out.println("Ano inválido! Informe um ano entre 2000 e " + LocalDate.now().getYear() + ".");
                }
            } catch (NumberFormatException erro) {
                System.out.println("Entrada inválida! Digite um ano numérico.");
            }
        }
        String relatorio = pousada.fechamento(ano, mes);
        System.out.println("\n" + relatorio);
        File pasta = new File("saídas");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        String nomeArquivo = String.format("fechamento_%02d_%d.txt", mes, ano);
        File arquivo = new File(pasta, nomeArquivo);
        try (PrintWriter escritor = new PrintWriter(new FileWriter(arquivo))) {
            escritor.print(relatorio);
            System.out.println("Relatório salvo com sucesso em: " + arquivo.getPath());

        } catch (IOException erro) {
            System.out.println("Erro ao salvar o relatório: " + erro.getMessage());
        }
    }

    private void imprimirListaEspera() {
        if (pousada.getEspera().isEmpty()) {
            System.out.println("Não existem hospedagens na lista de espera.");
            return;
        }
        System.out.println("\n========== LISTA DE ESPERA ==========");
        int posicao = 1;
        for (Hospedagem hospedagem : pousada.getEspera()) {
            System.out.println("\nPosição: " + posicao);
            System.out.println("Hóspede: " + hospedagem.getHospede());
            System.out.println("Documento: " + hospedagem.getDocumento());
            System.out.println("Check-in: " + hospedagem.getCheckin());
            System.out.println("Checkout: " + hospedagem.getCheckout());
            posicao++;
        }
        File pasta = new File("saidas");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        File arquivo = new File(pasta, "espera.txt");
        try (PrintWriter escritor = new PrintWriter(new FileWriter(arquivo))) {
            int posicaoArquivo = 1;
            for (Hospedagem hospedagem : pousada.getEspera()) {
                escritor.println("Posição: " + posicaoArquivo);
                escritor.println("Hóspede: " + hospedagem.getHospede());
                escritor.println("Documento: " + hospedagem.getDocumento());
                escritor.println("Check-in: " + hospedagem.getCheckin());
                escritor.println("Checkout: " + hospedagem.getCheckout());
                escritor.println("-----------------------------------");
                posicaoArquivo++;
            }
            System.out.println("Lista de espera salva em: " + arquivo.getPath());
        } catch (IOException erro) {
            System.out.println("Erro ao salvar a lista de espera: " + erro.getMessage());
        }
    }

    private void salvarDados() {
        GravadorArquivo gravador = new GravadorArquivo();
        String caminho = "dados/pousada.txt";
        gravador.salvar(pousada, caminho);
    }
}