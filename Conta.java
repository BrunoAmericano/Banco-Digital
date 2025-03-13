import java.io.Serializable;

public abstract class Conta implements Serializable {
    protected int numero;
    protected double saldo;

    public Conta() {
        this.numero = (int) (Math.random() * 1000000);
        this.saldo = 0.0;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public boolean sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public void imprimirExtrato() {
        System.out.println("Conta Nº: " + numero);
        System.out.println("Saldo: " + saldo);
    }

    public boolean transferir(double valor, int numeroContaDestino) {
        if (this.sacar(valor)) {
            // Simula a transferência para outra conta, seria necessário melhorar para armazenar destinos
            System.out.println("Transferência de " + valor + " para conta " + numeroContaDestino + " realizada.");
            return true;
        }
        return false;
    }
}
