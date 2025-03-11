import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;
    private List<String> historicoTransacoes = new ArrayList<>();

    public Conta(Cliente cliente) {
        this.agencia = 1; // Agência padrão
        this.numero = SEQUENCIAL++;
        this.saldo = 0.0;
        this.cliente = cliente;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            registrarTransacao("Depósito de R$ " + valor);
        } else {
            System.out.println("O valor do depósito precisa ser positivo.");
        }
    }

    public void sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            registrarTransacao("Saque de R$ " + valor);
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public void transferir(double valor, Conta contaDestino) {
        if (saldo >= valor) {
            this.sacar(valor);
            contaDestino.depositar(valor);
            registrarTransacao("Transferência de R$ " + valor + " para conta " + contaDestino.numero);
        } else {
            System.out.println("Saldo insuficiente para transferência.");
        }
    }

    public void imprimirHistorico() {
        System.out.println("=== Histórico de Transações ===");
        for (String transacao : historicoTransacoes) {
            System.out.println(transacao);
        }
    }

    protected void registrarTransacao(String descricao) {
        historicoTransacoes.add(descricao);
    }

    protected void imprimirInfosComuns() {
        System.out.println("Titular: " + cliente.getNome());
        System.out.println("Agência: " + agencia);
        System.out.println("Número: " + numero);
        System.out.printf("Saldo: R$ %.2f%n", saldo);
    }

    public abstract void imprimirExtrato();

    public void salvarHistoricoEmArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historico_conta_" + numero + ".txt"))) {
            for (String transacao : historicoTransacoes) {
                writer.write(transacao);
                writer.newLine();
            }
            System.out.println("Histórico salvo no arquivo 'historico_conta_" + numero + ".txt'.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar histórico: " + e.getMessage());
        }
    }
}
