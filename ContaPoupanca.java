public class ContaPoupanca extends Conta {
    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Poupança ===");
        imprimirInfosComuns();
    }

    public void aplicarRendimentos(double taxaRendimento) {
        double rendimento = saldo * taxaRendimento;
        saldo += rendimento;
        registrarTransacao("Rendimentos aplicados: R$ " + rendimento);
    }
}
