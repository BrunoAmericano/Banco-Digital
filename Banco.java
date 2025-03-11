import java.io.*;
import java.util.*;

public class Banco {
    private static List<Conta> contas = new ArrayList<>();
    private static final String ARQUIVO_CLIENTES = "clientes.dat";

    public static void main(String[] args) {
        carregarClientes();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Banco Digital!");
        System.out.println("Você é um Cliente ou Administrador?");
        System.out.print("Digite 'cliente' ou 'admin': ");
        String tipoUsuario = scanner.nextLine().toLowerCase();

        if (tipoUsuario.equals("admin")) {
            autenticarAdministrador(scanner);
        } else if (tipoUsuario.equals("cliente")) {
            menuCliente(scanner);
        } else {
            System.out.println("Opção inválida! Encerrando o sistema.");
        }

        scanner.close();
    }

    private static void autenticarAdministrador(Scanner scanner) {
        System.out.print("Digite o nome de usuário do administrador: ");
        String username = scanner.nextLine();

        System.out.print("Digite a senha do administrador: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("123456")) {
            menuAdministrador(scanner);
        } else {
            System.out.println("Autenticação falhou! Nome de usuário ou senha incorretos.");
        }
    }

    private static void menuAdministrador(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n=== Menu Administrador ===");
            System.out.println("1. Listar Clientes");
            System.out.println("2. Gerar Relatório Gerencial");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    gerarRelatorioGerencial();
                    break;
                case 0:
                    System.out.println("Saindo do sistema do Administrador.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void menuCliente(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n=== Menu Cliente ===");
            System.out.println("1. Cadastrar Novo Cliente");
            System.out.println("2. Depositar");
            System.out.println("3. Sacar");
            System.out.println("4. Transferir");
            System.out.println("5. Imprimir Extrato Corrente");
            System.out.println("6. Imprimir Extrato Poupança");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    realizarDeposito(scanner);
                    break;
                case 3:
                    realizarSaque(scanner);
                    break;
                case 4:
                    realizarTransferencia(scanner);
                    break;
                case 5:
                    imprimirExtrato(scanner, true);
                    break;
                case 6:
                    imprimirExtrato(scanner, false);
                    break;
                case 0:
                    System.out.println("Saindo do menu do Cliente.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarCliente(Scanner scanner) {
        try {
            System.out.print("Digite o nome do cliente: ");
            scanner.nextLine(); 
            String nome = scanner.nextLine();

            System.out.print("Digite uma senha forte para o cliente: ");
            String senha = scanner.nextLine();

            Cliente cliente = new Cliente(nome, senha);
            ContaCorrente contaCorrente = new ContaCorrente(cliente);
            ContaPoupanca contaPoupanca = new ContaPoupanca(cliente);

            contas.add(contaCorrente);
            contas.add(contaPoupanca);
            salvarClientes();

            System.out.println("Cliente cadastrado com sucesso!");
            System.out.println("Conta Corrente Nº: " + contaCorrente.numero);
            System.out.println("Conta Poupança Nº: " + contaPoupanca.numero);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void salvarClientes() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_CLIENTES))) {
            out.writeObject(contas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    private static void carregarClientes() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO_CLIENTES))) {
            contas = (List<Conta>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nenhum cliente salvo encontrado. Iniciando novo banco de dados.");
        }
    }
}
