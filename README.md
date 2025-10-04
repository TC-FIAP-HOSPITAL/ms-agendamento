# ms-agendamento

Microserviço Spring Boot responsável por gerenciar agendamentos clínicos. Ele expõe uma API GraphQL para criação, atualização, consulta e exclusão de agendamentos, mantendo regras de negócio centralizadas em casos de uso e integração com banco relacional MySQL e mensageria RabbitMQ para publicação de eventos.

## Sumário

- [Arquitetura](#arquitetura)
- [Camadas principais](#camadas-principais)
- [Dependências e requisitos](#dependências-e-requisitos)
- [Configuração e execução](#configuração-e-execução)
- [Mensageria](#mensageria)
- [Segurança](#segurança)
- [API GraphQL](#api-graphql)
- [Exemplos de chamadas](#exemplos-de-chamadas)
- [Testes e cobertura](#testes-e-cobertura)
- [Estrutura do projeto](#estrutura-do-projeto)

## Arquitetura

A aplicação segue uma abordagem hexagonal/limpa, dividindo responsabilidades em camadas:

```
┌──────────────────────────┐
│ Entrypoints (GraphQL)    │  ← controllers, resolvers, DTOs
└────────────┬─────────────┘
             │ usa
┌────────────▼─────────────┐
│ Application / Use Cases  │  ← orquestra regras de negócio
└────────────┬─────────────┘
             │ depende de
┌────────────▼─────────────┐
│ Domain                   │  ← entidades e serviços de domínio
└────────────┬─────────────┘
             │ implementado por
┌────────────▼─────────────┐
│ Infra (dataproviders)    │  ← JPA, mensageria, especificações
└──────────────────────────┘
```

### Camadas principais

- **Entrypoints (`entrypoints.controllers`)**: AgendamentoController expõe a API GraphQL. DTOs e Presenter mapeiam entre objetos de transporte e domínio.
- **Application (`application.usecase`)**: Casos de uso (inserir, atualizar, buscar, deletar) encapsulam regras de negócio e orquestram integrações externas via gateways.
- **Domain (`domain`)**: Modelo `AgendamentoDomain`, enums (Status, Tipo, Especialidade) e serviço de domínio (`AgendamentoDomainServiceImpl`).
- **Infraestrutura (`infraestruture.dataproviders`)**:
  - **database**: Repositório JPA (`AgendamentoRepository`), entidade (`AgendamentoEntity`), mapper MapStruct, implementação do gateway (`AgendamentoImpl`) e especificação dinâmica.
  - **config**: Beans de segurança (JWT, filtros, utilitários) e RabbitMQ (`RabbitMQConfig`).
  - **messaging**: `RabbitMQMessagePublisher` publica eventos após operações de escrita.

## Dependências e requisitos

- **Java** 21+
- **Maven** 3.9+
- **MySQL** 8 (ou compatível) – database `agendamento`
- **RabbitMQ** 3.x (fila padrão `notificacao-queue` e `historico-queue` no exchange `agendamento-exchange`)
- **Docker** (opcional) – há um `Dockerfile` na raiz

## Configuração e execução

### Variáveis de ambiente

| Variável                     | Descrição                                                 |
| ---------------------------- | --------------------------------------------------------- |
| `SPRING_DATASOURCE_URL`      | URL JDBC (ex.: `jdbc:mysql://localhost:4306/agendamento`) |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco                                          |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco                                            |
| `JWT_SECRET`                 | Segredo usado na geração/validação de tokens              |

> Para testes locais existe o arquivo `src/main/resources/application-local.properties` com credenciais padrão (MySQL em `localhost:4306`, usuário `adm123`).

### Executando localmente

```bash
# compilar e rodar testes
./mvnw clean verify

# executar a aplicação
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:9085/ms-agendamento`.

### Docker

Um `Dockerfile` está disponível para empacotar o serviço:

```bash
docker build -t ms-agendamento .
```

## Mensageria

Após operações de criação/atualização/exclusão, um evento é publicado via RabbitMQ:

- **Exchange**: `agendamento-exchange`
- **Routing Key**: `agendamento-routing-key`
- **Filas padrão**: `notificacao-queue`, `historico-queue`

O publisher inclui o JWT original no header `Authorization`.

## Segurança

- Autenticação via JWT lido do header `Authorization: Bearer <token>`.
- `SecurityConfig` registra `JwtAuthenticationFilter`, que extrai o usuário/role e popula o `SecurityContext`.
- Regras de acesso na controller:
  - `create/update`: permitido para ADMIN e ENFERMEIRO
  - `delete`: apenas ADMIN
  - `find`: ADMIN e ENFERMEIRO, exigindo `userId` no token

## API GraphQL

O schema está em `src/main/resources/graphql/agendamento.graphqls`. Principais operações:

- **Query** `findAgendamento(filter)` – lista agendamentos com filtros opcionais (paciente, médico, data, status, tipo, especialidade).
- **Mutations** `createAgendamento`, `updateAgendamento`, `deleteAgendamento`.

### Tipos relevantes

- `AgendamentoRequest` – payload para criação/atualização
- `AgendamentoFilter` – parâmetros de busca
- Enums: `Status`, `TipoAtendimento`, `Especialidade`

## Exemplos de chamadas

### Mutation – criar agendamento

```graphql
mutation {
  createAgendamento(
    request: {
      pacienteId: 1
      medicoId: 5
      dataAgendamento: "2025-09-20T10:00:00Z"
      tipoAtendimento: CONSULTA
      especialidade: CARDIOLOGIA
      status: AGENDADO
      motivo: "Dores no peito"
      observacoes: "Paciente com histórico cardíaco"
    }
  ) {
    id
    status
    dataAgendamento
  }
}
```

### Mutation – atualizar agendamento

```graphql
mutation {
  updateAgendamento(
    id: 42
    request: {
      pacienteId: 1
      medicoId: 5
      dataAgendamento: "2025-09-21T09:00:00Z"
      tipoAtendimento: CONSULTA
      especialidade: CARDIOLOGIA
      status: CONFIRMADO
      motivo: "Reavaliação"
      observacoes: "Levar resultados de exames"
    }
  ) {
    id
    status
  }
}
```

### Mutation – deletar agendamento

```graphql
mutation {
  deleteAgendamento(id: 42)
}
```

### Query – buscar com filtros

```graphql
query {
  findAgendamento(
    filter: { pacienteId: 1, status: CONFIRMADO, dataAgendamento: "2025-09-21" }
  ) {
    id
    medicoId
    status
    dataAgendamento
  }
}
```

> Para chamadas autenticadas, envie o header `Authorization: Bearer <token>`.

## Testes e cobertura

- Rodar suíte: `./mvnw test`
- Após os testes, o relatório Jacoco fica em `target/site/jacoco/index.html`
- Cobertura atual: **85%** (instruções) / 72% (branches)

Principais testes automatizados cobrem:

- Casos de uso (`application/usecase/...Test`)
- Serviço de domínio (`AgendamentoDomainServiceImplTest`)
- Controller GraphQL (`AgendamentoControllerTest`)
- Infraestrutura (mappers, JPA gateway, RabbitMQ publisher, especificações dinâmicas)
- Segurança (JWT utilitário, filtro, utilitário de segurança)

## Estrutura do projeto

```
src/
 ├─ main/
 │   ├─ java/com/ms/agendamento/
 │   │   ├─ application/         # casos de uso e gateways
 │   │   ├─ domain/              # entidades e serviços de domínio
 │   │   ├─ entrypoints/         # controllers GraphQL, DTOs, presenter
 │   │   ├─ infraestruture/      # adapters (JPA, RabbitMQ, segurança)
 │   │   └─ MsAgendamentoApplication.java
 │   └─ resources/
 │       ├─ application.properties
 │       ├─ application-local.properties
 │       └─ graphql/agendamento.graphqls
 └─ test/
     └─ java/...                # testes unitários e de integração leve
```

## Contribuindo

1. Crie branch a partir de `main`
2. Garanta `./mvnw clean verify` sem falhas
3. Atualize/adicione testes relevantes
4. Submeta PR com descrição dos impactos (negócio + técnicos)

---

Para dúvidas ou melhorias, abra uma issue ou entre em contato com a equipe responsável pelo serviço.
