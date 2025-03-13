import java.io.Serializable;

public class Cliente implements Serializable {
    private String nome;
    private String senha;
    private ContaCorrente contaCorrente;
    private ContaPoupanca contaPoupanca;

    public Cliente(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.contaCorrente = new ContaCorrente();
        this.contaPoupanca = new ContaPoupanca();
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public void depositar(double valor) {
        contaCorrente.depositar(valor);
    }

    public boolean sacar(double valor) {
        return contaCorrente.sacar(valor);
    }

    public boolean transferir(double valor, int numeroContaDestino) {
        return contaCorrente.transferir(valor, numeroContaDestino);
    }

    public void imprimirExtratoContaCorrente() {
        contaCorrente.imprimirExtrato();
    }

    public void imprimirExtratoContaPoupanca() {
        contaPoupanca.imprimirExtrato();
    }
}
