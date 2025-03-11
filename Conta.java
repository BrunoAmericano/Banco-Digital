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
        if (valor <= 0) {
            System.out.println("Erro: O valor do depósito precisa ser maior que zero.");
            return;
        }
        saldo += valor;
        registrarTransacao("Depósito de R$ " + valor);
    }

    public void sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Erro: O valor do saque precisa ser maior que zero.");
            return;
        }
        if (saldo < valor) {
            System.out.println("Erro: Saldo insuficiente.");
            return;
        }
        saldo -= valor;
        registrarTransacao("Saque de R$ " + valor);
    }

    public void transferir(double valor, Conta contaDestino) {
        if (valor <= 0) {
            System.out.println("Erro: O valor da transferência precisa ser maior que zero.");
            return;
        }
        if (saldo < valor) {
            System.out.println("Erro: Saldo insuficiente para transferência.");
            return;
        }
        this.sacar(valor);
        contaDestino.depositar(valor);
        registrarTransacao("Transferência de R$ " + valor + " para conta " + contaDestino.numero);
    }

    public void exportarHistoricoCsv() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historico_conta_" + numero + ".csv"))) {
            writer.write("Descrição\n");
            for (String transacao : historicoTransacoes) {
                writer.write(transacao + "\n");
            }
            System.out.println("Histórico exportado para CSV com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao exportar histórico: " + e.getMessage());
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
}
