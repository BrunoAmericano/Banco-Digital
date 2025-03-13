public class ContaCorrente extends Conta {

    public ContaCorrente() {
        super();
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("Conta Corrente Nº: " + getNumero());
        System.out.println("Saldo: " + getSaldo());
    }
}
