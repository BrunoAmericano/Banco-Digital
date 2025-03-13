import java.io.*;
import java.util.*;

public class Banco {

    private static List<Cliente> clientes = new ArrayList<>();
    private static final String ARQUIVO_CLIENTES = "clientes.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        carregarClientes(); // Carregar clientes do arquivo

        System.out.println("Bem-vindo ao Banco Digital!");
        System.out.print("Você é um Cliente ou Administrador? ");
        String tipoUsuario = scanner.nextLine().toLowerCase();

        Cliente cliente = null;
        if (tipoUsuario.equals("cliente")) {
            // Solicitar login do cliente
            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();
            System.out.print("Digite sua senha: ");
            String senha = scanner.nextLine();

            cliente = buscarCliente(nome, senha);

            if (cliente == null) {
                System.out.println("Cliente não encontrado ou senha incorreta.");
                System.out.println("O administrador vai criar seu cadastro.");
                cadastrarCliente(scanner);
            }
        }

        // Exibindo o menu após autenticação
        if (cliente != null) {
            menuCliente(scanner, cliente);
        } else {
            // Caso o cliente tenha sido cadastrado com sucesso
            System.out.println("Cadastro realizado com sucesso!");
            salvarClientes(); // Salvar após cadastro
        }

        scanner.close();
    }

    private static void menuCliente(Scanner scanner, Cliente cliente) {
        int opcao;
        do {
            System.out.println("\n=== Menu Cliente ===");
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            System.out.println("3. Transferir");
            System.out.println("4. Imprimir Extrato Corrente");
            System.out.println("5. Imprimir Extrato Poupança");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    realizarDeposito(scanner, cliente);
                    break;
                case 2:
                    realizarSaque(scanner, cliente);
                    break;
                case 3:
                    realizarTransferencia(scanner, cliente);
                    break;
                case 4:
                    imprimirExtrato(scanner, cliente, true);
                    break;
                case 5:
                    imprimirExtrato(scanner, cliente, false);
                    break;
                case 0:
                    System.out.println("Saindo do menu do Cliente.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static Cliente buscarCliente(String nome, String senha) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equals(nome) && cliente.getSenha().equals(senha)) {
                return cliente;
            }
        }
        return null;
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite uma senha forte: ");
        String senha = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, senha);
        clientes.add(novoCliente);
        System.out.println("Cadastro realizado com sucesso!");
        salvarClientes(); // Salvar os dados de clientes ao cadastrar
    }

    private static void realizarDeposito(Scanner scanner, Cliente cliente) {
        System.out.print("Digite o valor para depósito: ");
        double valor = scanner.nextDouble();
        cliente.depositar(valor);
        System.out.println("Depósito realizado com sucesso!");
    }

    private static void realizarSaque(Scanner scanner, Cliente cliente) {
        System.out.print("Digite o valor para saque: ");
        double valor = scanner.nextDouble();
        boolean sucesso = cliente.sacar(valor);
        if (sucesso) {
            System.out.println("Saque realizado com sucesso!");
        } else {
            System.out.println("Saldo insuficiente!");
        }
    }

    private static void realizarTransferencia(Scanner scanner, Cliente cliente) {
        System.out.print("Digite o número da conta de destino: ");
        int numeroContaDestino = scanner.nextInt();
        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();
        boolean sucesso = cliente.transferir(valor, numeroContaDestino);
        if (sucesso) {
            System.out.println("Transferência realizada com sucesso!");
        } else {
            System.out.println("Falha na transferência!");
        }
    }

    private static void imprimirExtrato(Scanner scanner, Cliente cliente, boolean corrente) {
        if (corrente) {
            cliente.imprimirExtratoContaCorrente();
        } else {
            cliente.imprimirExtratoContaPoupanca();
        }
    }

    private static void carregarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_CLIENTES))) {
            clientes = (List<Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nenhum cliente encontrado, criando um novo banco.");
        }
    }

    private static void salvarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_CLIENTES))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os clientes.");
        }
    }
}
