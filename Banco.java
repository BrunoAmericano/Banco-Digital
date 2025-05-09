import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Banco {
    private static final String ARQUIVO_CLIENTES = "clientes.txt";
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Conta> contas = new ArrayList<>();
    private static Cliente clienteLogado;

    public static void main(String[] args) {
        carregarClientes();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("=== Banco Digital ===");
            System.out.println("1. Login");
            System.out.println("2. Criar Conta");
            System.out.println("3. Administrador");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    criarConta(scanner);
                    break;
                case 3:
                    menuAdministrador(scanner);
                    break;
                case 0:
                    salvarClientes();
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        clienteLogado = buscarCliente(nome, senha);
        if (clienteLogado != null) {
            System.out.println("Login bem-sucedido!");
            menuCliente(scanner);
        } else {
            System.out.println("Nome ou senha incorretos!");
        }
    }

    private static void criarConta(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, senha);
        clientes.add(novoCliente);
        System.out.println("Conta criada com sucesso!");
    }

    private static Cliente buscarCliente(String nome, String senha) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equals(nome) && cliente.getSenha().equals(senha)) {
                return cliente;
            }
        }
        return null;
    }

    private static void salvarClientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CLIENTES))) {
            for (Cliente cliente : clientes) {
                writer.write("Cliente: " + cliente.getNome() + ", Senha: " + cliente.getSenha());
                writer.newLine();
                for (Conta conta : contas) {
                    if (conta.getCliente().equals(cliente)) {
                        writer.write("Conta Nº: " + conta.getNumero() + ", Tipo: " +
                                (conta instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança") +
                                ", Saldo: " + conta.getSaldo());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    private static void carregarClientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CLIENTES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Cliente: ")) {
                    String[] partes = linha.split(", ");
                    String nome = partes[0].substring("Cliente: ".length());
                    String senha = partes[1].substring("Senha: ".length());
                    Cliente cliente = new Cliente(nome, senha);
                    clientes.add(cliente);
                } else if (linha.startsWith("Conta Nº: ")) {
                    String[] partes = linha.split(", ");
                    int numero = Integer.parseInt(partes[0].substring("Conta Nº: ".length()));
                    String tipo = partes[1].substring("Tipo: ".length());
                    double saldo = Double.parseDouble(partes[2].substring("Saldo: ".length()));
                    Cliente cliente = clientes.get(clientes.size() - 1);
                    Conta conta = tipo.equals("Conta Corrente") ? new ContaCorrente(cliente) : new ContaPoupanca(cliente);
                    conta.setNumero(numero);
                    conta.depositar(saldo);
                    contas.add(conta);
                }
            }
        } catch (IOException e) {
            System.out.println("Nenhum cliente salvo encontrado. Iniciando novo banco de dados.");
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

    private static void listarClientes() {
        System.out.println("\n=== Lista de Clientes ===");
        for (Cliente cliente : clientes) {
            System.out.println("Cliente: " + cliente.getNome());
            for (Conta conta : contas) {
                if (conta.getCliente().equals(cliente)) {
                    System.out.println("  Conta Nº: " + conta.getNumero() +
                            ", Tipo: " + (conta instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança") +
                            ", Saldo: " + conta.getSaldo());
                }
            }
        }
    }

    private static void gerarRelatorioGerencial() {
        System.out.println("\n=== Relatório Gerencial ===");
        for (Conta conta : contas) {
            System.out.println("Conta Nº: " + conta.getNumero() +
                    ", Tipo: " + (conta instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança") +
                    ", Saldo: " + conta.getSaldo());
        }
    }

    private static void menuCliente(Scanner scanner) {
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
                    realizarDeposito(scanner);
                    break;
                case 2:
                    realizarSaque(scanner);
                    break;
                case 3:
                    realizarTransferencia(scanner);
                    break;
                case 4:
                    imprimirExtrato(true);
                    break;
                case 5:
                    imprimirExtrato(false);
                    break;
                case 0:
                    System.out.println("Saindo do menu do Cliente.");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void realizarDeposito(Scanner scanner) {
        System.out.print("Digite o valor do depósito: ");
        double valor = scanner.nextDouble();

        if (valor <= 0) {
            System.out.println("Valor inválido para depósito.");
            return;
        }

        System.out.println("Escolha a conta: 1. Corrente, 2. Poupança");
        int tipoConta = scanner.nextInt();
        Conta contaEscolhida = null;

        // Procurar a conta correta do cliente logado
        for (Conta conta : contas) {
            if (conta.getCliente().equals(clienteLogado) &&
                ((tipoConta == 1 && conta instanceof ContaCorrente) ||
                 (tipoConta == 2 && conta instanceof ContaPoupanca))) {
                contaEscolhida = conta;
                break;
            }
        }

        if (contaEscolhida != null) {
            contaEscolhida.depositar(valor);
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void realizarSaque(Scanner scanner) {
        System.out.print("Digite o valor do saque: ");
        double valor = scanner.nextDouble();

        if (valor <= 0) {
            System.out.println("Valor inválido para saque.");
            return;
        }

        System.out.println("Escolha a conta: 1. Corrente, 2. Poupança");
        int tipoConta = scanner.nextInt();
        Conta contaEscolhida = null;

        // Procurar a conta correta do cliente logado
        for (Conta conta : contas) {
            if (conta.getCliente().equals(clienteLogado) &&
                ((tipoConta == 1 && conta instanceof ContaCorrente) ||
                 (tipoConta == 2 && conta instanceof ContaPoupanca))) {
                contaEscolhida = conta;
                break;
            }
        }

        if (contaEscolhida != null) {
            if (contaEscolhida.sacar(valor)) {
                System.out.println("Saque realizado com sucesso!");
            } else {
                System.out.println("Saldo insuficiente para saque.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void realizarTransferencia(Scanner scanner) {
        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();

        if (valor <= 0) {
            System.out.println("Valor inválido para transferência.");
            return;
        }

        System.out.println("Escolha a conta origem: 1. Corrente, 2. Poupança");
        int tipoContaOrigem = scanner.nextInt();

        System.out.println("Escolha a conta destino: 1. Corrente, 2. Poupança");
        int tipoContaDestino = scanner.nextInt();

        Conta contaOrigem = null;
        Conta contaDestino = null;

        // Procurar a conta origem do cliente logado
        for (Conta conta : contas) {
            if (conta.getCliente().equals(clienteLogado) &&
                ((tipoContaOrigem == 1 && conta instanceof ContaCorrente) ||
                 (tipoContaOrigem == 2 && conta instanceof ContaPoupanca))) {
                contaOrigem = conta;
                break;
            }
        }

        // Procurar a conta destino do cliente logado
        for (Conta conta : contas) {
            if (conta.getCliente().equals(clienteLogado) &&
                ((tipoContaDestino == 1 && conta instanceof ContaCorrente) ||
                 (tipoContaDestino == 2 && conta instanceof ContaPoupanca))) {
                contaDestino = conta;
                break;
            }
        }

        if (contaOrigem != null && contaDestino != null) {
            if (contaOrigem.sacar(valor)) {
                contaDestino.depositar(valor);
                System.out.println("Transferência realizada com sucesso!");
            } else {
                System.out.println("Saldo insuficiente para transferência.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void imprimirExtrato(boolean corrente) {
        System.out.println("\n=== Extrato " + (corrente ? "Corrente" : "Poupança") + " ===");
        for (Conta conta : contas) {
            if (conta.getCliente().equals(clienteLogado) &&
                ((corrente && conta instanceof ContaCorrente) ||
                 (!corrente && conta instanceof ContaPoupanca))) {
                System.out.println("Conta Nº: " + conta.getNumero() + ", Saldo: " + conta.getSaldo());
            }
        }
    }
}