package PousadaFACSI;
import java.time.LocalDate;
import java.util.Scanner;

public class Menu {

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
                        System.out.println("Removendo quarto...");
                        break;

                    case 6:
                        System.out.println("Removendo consumo...");
                        break;

                    case 7:
                        System.out.println("Buscando hospedagem...");
                        break;

                    case 8:
                        System.out.println("Abrindo operações específicas...");
                        break;

                    case 9:
                        System.out.println("Abrindo relatórios...");
                        break;

                    case 10:
                        System.out.println("Salvando dados...");
                        break;

                    case 11:
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
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

        pousada.adicionarQuarto(quarto);
        System.out.println("Quarto cadastrado com sucesso!");
    }

    private void adicionarConsumo() {
        System.out.println("Digite o documento do hóspede: ");
        String documento = scanner.nextLine();
        System.out.print("Digite a data do consumo (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());
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

}