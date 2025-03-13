public class ContaPoupanca extends Conta {

    public ContaPoupanca() {
        super();
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("Conta Poupança Nº: " + getNumero());
        System.out.println("Saldo: " + getSaldo());
    }
}
