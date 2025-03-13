import java.io.Serializable;

public abstract class Conta implements Serializable {
    private int numero;
    private double saldo;
    private Cliente cliente;

    public Conta(Cliente cliente) {
        this.cliente = cliente;
        this.numero = (int)(Math.random() * (999999 - 100000) + 100000);  // Geração de número aleatório para a conta
        this.saldo = 0;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        }
    }

    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public abstract void imprimirExtrato();
}