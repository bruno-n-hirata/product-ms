# Catálogo de Produtos

## Conteúdo
- [Descrição](#descricao)
- [Pré-requisitos](#pre-requisitos)
- [Ambiente](#ambiente)
- [Executando aplicativo com Docker](#executando-aplicativo-com-docker)
- [Executando aplicativo Localmente](#executando-aplicativo-localmente)
- [Executando testes](#executando-testes)
- [Documentação](#documentacao)
- [Construído com](#construido-com)

## Descrição
Serviço responsável por criar, alterar, visualizar e excluir um determinado produto.

## Pré-requisitos

- **[Obrigatório]** [Docker](https://www.docker.com/): Como este projeto está aplicado.
- **[Obrigatório]** [Docker-Compose](https://docs.docker.com/compose/): Para executar o projeto com suas dependências.
- **[Obrigatório]** [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

## Ambiente

- **Local** http://localhost:9999

## Executando aplicativo com Docker

Primeiro, clonar o projeto:

```shell
git clone https://github.com/bruno-n-hirata/product-ms.git
cd product-ms
```

Em seguida, execute o comando `make` a fim de construir o jar do aplicativo, construir a docker-image e executar todos os serviços docker-compose:

```shell
make run
```

## Executando aplicativo Localmente

### IntelliJ

Execute o comando `make` a fim de construir a docker-image e executar o serviço docker-compose do banco de dados:

```shell
make run-database
```

Depois disso, basta executar a função `main()` em `ProductMsApplication.java`

## Executando testes

Execute o comando `make` afim de executar todos os testes do aplicativo:

```shell
make tests
```

## Documentação

### Swagger

Este projeto usa [Swagger](https://www.swagger.io/) como documentação de referência da API.

Disponibilizamos toda a documentação por meio de um endpoint de aplicativo:

- [Documentação](http://localhost:9999/swagger-ui.html)

## Construído com

- [Java](https://www.java.com/) - Linguagem de Programação
- [Spring](https://spring.io/) - Framework Java
- [IntelliJ](https://www.jetbrains.com/idea/) - IDE
- [Postgres](https://www.postgresql.org/) - Banco de Dados
- [Flyway](https://flywaydb.org/) - Versão de Controle para o Banco de Dados
- [Maven](https://maven.apache.org/) - Gerenciamento de Dependências
- [Docker](https://www.docker.com/) - Plataforma de Conteinerização
