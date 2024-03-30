# Meu Almoxarifado - API REST

Este projeto consiste em uma API REST desenvolvida com o framework Spring Boot para realizar o controle e gerenciamento de estoque e almoxarifado. Essa API serve como backend para a aplicação frontend de controle de estoque.

## Dependências

- **Spring Boot**: Framework para desenvolvimento de aplicações Java baseadas em Spring.
- **Spring Data JPA**: Facilita o acesso e a persistência de dados em bancos de dados relacionais.
- **Spring Web**: Fornece suporte para desenvolvimento de aplicativos web, incluindo APIs RESTful.
- **Spring Boot Starter Validation**: Fornece suporte para validação de dados.
- **Flyway**: Utilizado para controle de versionamento de banco de dados.
- **H2 Database**: Banco de dados em memória para ambiente de desenvolvimento e teste.
- **PostgreSQL Driver**: Driver JDBC para conexão com bancos de dados PostgreSQL.
- **Lombok**: Biblioteca que simplifica o desenvolvimento Java reduzindo a quantidade de código boilerplate.
- **Spring Boot Starter Test**: Fornece suporte para testes unitários e de integração em aplicações Spring Boot.

## Requisitos

- Java 17 ou superior
- Maven

## Configuração e Execução

1. Certifique-se de ter o Java e o Maven instalados em seu sistema.
2. Clone este repositório:

```bash
git clone https://github.com/devLucasMoraes/meu-almoxarifado-backend.git
```

3. Navegue até o diretório do projeto:

```bash
cd meu-almoxarifado-backend
```

4. Execute o seguinte comando para compilar o projeto e gerar o arquivo JAR:

```bash
mvn clean package
```

5. Execute a aplicação:

```bash
java -jar target/meu-almoxarifado-backend-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em http://localhost:8080.

Documentação da API
A documentação da API pode ser acessada em http://localhost:8080/swagger-ui.html após iniciar a aplicação.

Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

Licença
Este projeto está licenciado sob a MIT License.
