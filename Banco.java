import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Banco {
    private static List<Conta> contas = new ArrayList<>();

    public static void main(String[] args) {
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
            scanner.nextLine(); // Consumir a linha anterior
            String nome = scanner.nextLine();

            System.out.print("Digite uma senha forte para o cliente: ");
            String senha = scanner.nextLine();

            Cliente cliente = new Cliente(nome, senha);

            ContaCorrente contaCorrente = new ContaCorrente(cliente);
            ContaPoupanca contaPoupanca = new ContaPoupanca(cliente);

            contas.add(contaCorrente);
            contas.add(contaPoupanca);

            System.out.println("Cliente cadastrado com sucesso!");
            System.out.println("Conta Corrente Nº: " + contaCorrente.numero);
            System.out.println("Conta Poupança Nº: " + contaPoupanca.numero);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        System.out.println("\n=== Lista de Clientes ===");

        if (contas.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no momento.");
            return;
        }

        Set<Cliente> clientesExibidos = new HashSet<>();
        for (Conta conta : contas) {
            Cliente cliente = conta.cliente;
            if (!clientesExibidos.contains(cliente)) {
                System.out.println("Cliente: " + cliente.getNome());
                System.out.println("Conta Corrente Nº: " + contas.stream()
                        .filter(c -> c instanceof ContaCorrente && c.cliente.equals(cliente))
                        .map(c -> c.numero)
                        .findFirst()
                        .orElse(-1)); // Procurar conta corrente
                System.out.println("Conta Poupança Nº: " + contas.stream()
                        .filter(c -> c instanceof ContaPoupanca && c.cliente.equals(cliente))
                        .map(c -> c.numero)
                        .findFirst()
                        .orElse(-1)); // Procurar conta poupança
                System.out.println("-------------------");
                clientesExibidos.add(cliente);
            }
        }
    }

    private static void gerarRelatorioGerencial() {
        double saldoTotal = 0;
        int totalContas = contas.size();

        for (Conta conta : contas) {
            saldoTotal += conta.saldo;
        }

        System.out.println("\n=== Relatório Gerencial ===");
        System.out.printf("Saldo total no banco: R$ %.2f%n", saldoTotal);
        System.out.println("Total de contas: " + totalContas);
    }

    private static void realizarDeposito(Scanner scanner) {
        Conta conta = autenticarCliente(scanner);
        if (conta != null) {
            System.out.print("Digite o valor do depósito: ");
            double valor = scanner.nextDouble();
            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso!");
        }
    }

    private static void realizarSaque(Scanner scanner) {
        Conta conta = autenticarCliente(scanner);
        if (conta != null) {
            System.out.print("Digite o valor do saque: ");
            double valor = scanner.nextDouble();
            conta.sacar(valor);
            System.out.println("Saque realizado com sucesso!");
        }
    }

    private static void realizarTransferencia(Scanner scanner) {
        Conta origem = autenticarCliente(scanner);
        if (origem != null) {
            System.out.print("Digite o número da conta destino: ");
            int numeroDestino = scanner.nextInt();
            Conta destino = encontrarContaPorNumero(numeroDestino);

            if (destino != null) {
                System.out.print("Digite o valor da transferência: ");
                double valor = scanner.nextDouble();
                origem.transferir(valor, destino);
                System.out.println("Transferência realizada com sucesso!");
            } else {
                System.out.println("Conta destino não encontrada.");
            }
        }
    }

    private static void imprimirExtrato(Scanner scanner, boolean isCorrente) {
        Conta conta = autenticarCliente(scanner);
        if (conta != null) {
            if ((isCorrente && conta instanceof ContaCorrente) || (!isCorrente && conta instanceof ContaPoupanca)) {
                conta.imprimirExtrato();
            } else {
                System.out.println("O tipo de conta selecionado não é compatível.");
            }
        }
    }

    private static Conta autenticarCliente(Scanner scanner) {
        System.out.print("Digite o número da conta: ");
        int numero = scanner.nextInt();

        Conta conta = encontrarContaPorNumero(numero);
        if (conta != null) {
            System.out.print("Digite a senha do cliente: ");
            scanner.nextLine(); // Consumir a linha anterior.
            String senha = scanner.nextLine();

            if (!conta.cliente.autenticar(senha)) {
                System.out.println("Senha incorreta!");
                return null;
            }
            return conta;
        } else {
            System.out.println("Conta não encontrada.");
            return null;
        }
    }

    private static Conta encontrarContaPorNumero(int numero) {
        for (Conta conta : contas) {
            if (conta.numero == numero) {
                return conta;
            }
        }
        return null;
    }
}
