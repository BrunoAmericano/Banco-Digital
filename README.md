# Banco-Digital 🏦✨

Bem-vindo ao **Banco Digital**, o seu sistema bancário digital simplificado e eficiente! Este projeto foi desenvolvido para simular operações bancárias essenciais, como criação de contas, depósitos, saques, transferências e consulta de extratos, de uma maneira fácil e intuitiva.

## 🌟 Recursos Principais

- **🔐 Criação de Conta**: Cadastre-se facilmente com seu nome e senha.
- **🔓 Login Seguro**: Acesse sua conta com segurança.
- **💵 Depósitos e Saques**: Realize transações financeiras rapidamente.
- **💸 Transferências**: Transfira dinheiro entre contas.
- **📊 Extratos Detalhados**: Consulte o saldo e o histórico de transações de suas contas Corrente e Poupança.

## 🛠 Tecnologias Utilizadas

- **Java**: Linguagem de programação principal utilizada.
- **Arquivo de Texto**: Persistência de dados em arquivos `.txt` para armazenar informações dos clientes e suas contas.

## 🚀 Como Executar o Projeto

### Pré-requisitos

- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) instalado na máquina.

### Passo a Passo

1. **Clone o Repositório**:
    ```sh
    git clone https://github.com/BrunoAmericano/Banco-Digital.git
    ```

2. **Navegue até o Diretório do Projeto**:
    ```sh
    cd Banco-Digital
    ```

3. **Compile o Código**:
    ```sh
    javac *.java
    ```

4. **Execute o Código**:
    ```sh
    java Banco
    ```

## 📋 Funcionalidades Detalhadas

### Login e Cadastro
- **Login**: Digite seu nome e senha para acessar sua conta.
- **Criar Conta**: Registre-se com um novo nome e senha.

### Menu do Cliente
- **Depositar**: Adicione fundos à sua conta.
- **Sacar**: Retire fundos da sua conta.
- **Transferir**: Transfira fundos para outra conta.
- **Imprimir Extrato Corrente**: Veja o histórico de transações e o saldo atual da conta corrente.
- **Imprimir Extrato Poupança**: Veja o histórico de transações e o saldo atual da conta poupança.

### Menu do Administrador
- **Listar Clientes**: Veja uma lista de todos os clientes cadastrados.
- **Gerar Relatório Gerencial**: Veja um relatório detalhado de todas as contas com seus respectivos saldos.

## 📁 Estrutura do Projeto

Banco-Digital/

    Banco.java
    Cliente.java
    Conta.java
    ContaCorrente.java
    ContaPoupanca.java
    clientes.txt
    README.md

## 🔍 Explicação dos Arquivos

- **Banco.java**: Classe principal que gerencia a aplicação, incluindo menus e lógica principal.
- **Cliente.java**: Classe que representa um cliente do banco, contendo nome e senha.
- **Conta.java**: Classe abstrata que representa uma conta bancária genérica.
- **ContaCorrente.java**: Classe que estende `Conta` e representa uma conta corrente.
- **ContaPoupanca.java**: Classe que estende `Conta` e representa uma conta poupança.
- **clientes.txt**: Arquivo de texto onde os dados dos clientes e suas contas são armazenados.

## 🧐 Curiosidades

- **Segurança**: Embora o projeto utilize um método simples para armazenar senhas (em texto simples), é importante lembrar que em aplicações reais, é essencial utilizar técnicas de hashing e criptografia para proteger as informações dos usuários.
- **Persistência de Dados**: O uso de arquivos de texto para a persistência de dados é uma abordagem básica. Em aplicações mais avançadas, bancos de dados relacionais ou NoSQL são utilizados para uma maior eficiência e segurança.
- **Desenvolvimento Ágil**: Este projeto foi desenvolvido como uma forma de aprendizado e prática de conceitos de programação orientada a objetos e gerenciamento de dados em Java.

## 🤝 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

1. **Fork o Repositório**
2. **Crie uma Branch para sua Feature (`git checkout -b feature/AmazingFeature`)**
3. **Commit suas Mudanças (`git commit -m 'Add some AmazingFeature'`)**
4. **Push para a Branch (`git push origin feature/AmazingFeature`)**
5. **Abra um Pull Request**

## 📬 Contato

Desenvolvido por [Bruno Americano](https://github.com/BrunoAmericano) - Entre em contato!

---

Obrigado por conferir o **Banco Digital**! Esperamos que você goste do projeto tanto quanto gostamos de desenvolvê-lo. 😊