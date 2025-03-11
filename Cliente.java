public class Cliente {
    private String nome;
    private String senha;

    public Cliente(String nome, String senha) {
        if (!validarSenhaForte(senha)) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres, incluindo letras e números.");
        }
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    private boolean validarSenhaForte(String senha) {
        return senha.length() >= 6 && senha.matches(".*[a-zA-Z].*") && senha.matches(".*\\d.*");
    }
}
