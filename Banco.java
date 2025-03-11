import java.util.Scanner;

public class Banco {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Cliente cliente = new Cliente("Bruno Americano");
        ContaCorrente contaCorrente = new ContaCorrente(cliente);
        ContaPoupanca contaPoupanca = new ContaPoupanca(cliente);

        int opcao;
        do {
            System.out.println("\n=== Banco Digital ===");
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            System.out.println("3. Transferir");
            System.out.println("4. Imprimir Extrato Corrente");
            System.out.println("5. Imprimir Extrato Poupança");
            System.out.println("6. Histórico de Transações");
            System.out.println("7. Salvar Histórico em Arquivo");
            System.out.println("8. Aplicar Rendimentos na Poupança");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor do depósito: ");
                    double valorDeposito = scanner.nextDouble();
                    contaCorrente.depositar(valorDeposito);
                    break;
                case 2:
                    System.out.print("Digite o valor do saque: ");
                    double valorSaque = scanner.nextDouble();
                    contaCorrente.sacar(valorSaque);
                    break;
                case 3:
                    System.out.print("Digite o valor da transferência: ");
                    double valorTransferencia = scanner.nextDouble();
                    contaCorrente.transferir(valorTransferencia, contaPoupanca);
                    break;
                case 4:
                    contaCorrente.imprimirExtrato();
                    break;
                case 5:
                    contaPoupanca.imprimirExtrato();
                    break;
                case 6:
                    contaCorrente.imprimirHistorico();
                    break;
                case 7:
                    contaCorrente.salvarHistoricoEmArquivo();
                    break;
                case 8:
                    System.out.print("Digite a taxa de rendimento (ex: 0.02 para 2%): ");
                    double taxaRendimento = scanner.nextDouble();
                    contaPoupanca.aplicarRendimentos(taxaRendimento);
                    break;
                case 0:
                    System.out.println("Saindo... Obrigado por usar o Banco Digital!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
