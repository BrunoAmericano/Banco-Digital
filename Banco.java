public class Banco {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Bruno Americano");

        Conta cc = new ContaCorrente(cliente);
        Conta poupanca = new ContaPoupanca(cliente);

        cc.depositar(1000);
        cc.transferir(500, poupanca);

        cc.imprimirExtrato();
        poupanca.imprimirExtrato();
    }
}
