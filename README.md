# 🏥 PulseCare API

Projeto do **Tech Challenge da 3ª fase** da Pós-Graduação em **Arquitetura e Desenvolvimento Java**.

O PulseCare é uma plataforma de agendamento hospitalar construída como um conjunto de **microsserviços** que se comunicam de forma assíncrona via **RabbitMQ**, protegidos por **autenticação JWT** compartilhada e organizados em um **monorepo Gradle multi-módulo**.

👨‍💻 Desenvolvido por: **LuisFelipeGM**

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-green)
![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A?logo=gradle)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791?logo=postgresql&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Messaging-FF6600?logo=rabbitmq&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-Migrations-CC0200?logo=flyway&logoColor=white)
![GraphQL](https://img.shields.io/badge/GraphQL-Historico-E10098?logo=graphql&logoColor=white)
![JWT](https://img.shields.io/badge/Auth-JWT-black?logo=jsonwebtokens)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?logo=docker&logoColor=white)

---

# 🎯 Visão Geral

O PulseCare permite o agendamento de consultas médicas, com cadastro de usuários (médicos, enfermeiros e pacientes) e propagação assíncrona de eventos de consulta para os demais serviços da plataforma.

A API permite:

- ✅ Login único com emissão de token JWT, válido em todos os módulos
- ✅ Cadastro e gestão de usuários (médicos, enfermeiros e pacientes)
- ✅ Cadastro, edição, busca e listagem de consultas
- ✅ Controle de acesso por papel (role) de usuário
- ✅ Notificação assíncrona de lembretes via RabbitMQ
- ✅ Histórico de consultas em modelo de leitura (CQRS), consultável via GraphQL
- ✅ Paginação
- ✅ Validações
- ✅ Tratamento global de exceções no padrão RFC 7807 (ProblemDetail)
- ✅ Massa de dados de teste via Flyway + collection Postman pronta para uso

---

# 🏛️ Arquitetura

O sistema é dividido em **3 microsserviços independentes**, cada um com seu próprio banco de dados, mais um módulo `core` compartilhado com as peças comuns (segurança JWT, exceções de domínio e contrato de mensageria).

```
                                   ┌──────────────────────────┐
                                   │   pulse-care-core        │
                                   │  JwtUtil / JwtAuthFilter │
                                   │  BusinessException       │
                                   │  RabbitConstants         │
                                   │  ConsultaEventoDTO       │
                                   └──────────────┬───────────┘
                                                  │ dependência compartilhada
                     ┌────────────────────────────┼────────────────────────────┐
                     │                            │                            │
                     ▼                            ▼                            ▼
        ┌──────────────────────┐      ┌─────────────────────┐      ┌─────────────────────┐
        │ agendamento-service  │      │ notificacao-service │      │ historico-service   │
        │                      │      │                     │      │                     │
        │ REST + JWT login     │      │ Consumer RabbitMQ   │      │ Consumer RabbitMQ   │
        │ CRUD Usuário/Consulta│      │ "Envia" lembrete    │      │ Projeção de leitura │
        │ Publica eventos      │      │ (log)               │      │ Exposto via GraphQL │
        └──────────┬───────────┘      └──────────┬──────────┘      └──────────┬──────────┘
                   │                             ▲                            ▲
                   │  consulta.exchange (topic)  │                            │
                   └───────────────┬─────────────┘                            │
                                   │  routing key: consulta.*                 │
                   ┌──────────────────────────────────────────────────────────┘
                   │                                                          │
                   ▼                                                          ▼
          ┌─────────────────┐                                        ┌─────────────────┐
          │  agendamento_db │                                        │   historico_db  │
          │   (PostgreSQL)  │                                        │   (PostgreSQL)  │
          └─────────────────┘                                        └─────────────────┘
```

## Módulos

### `pulse-care-core`

Biblioteca compartilhada entre os 3 serviços, sem `@SpringBootApplication` própria.

- Segurança JWT (`JwtUtil`, `JwtAuthFilter`)
- Exceções de domínio (`BusinessException`, `NotFoundException`)
- Contrato de mensageria (`RabbitConstants`, `ConsultaEventoDTO`)

### `pulse-care-agendamento-service`

Serviço de escrita (produtor). Concentra o cadastro de usuários, consultas e o login único da plataforma.

- Camadas: `controller` → `sevice` → `validation` / `repository`, com `mapper` (MapStruct) para conversão entre `entity`, `dto` e `vo`
- Publica eventos de consulta criada/editada no `consulta.exchange`

### `pulse-care-notificacao-service`

Serviço consumidor. Apenas escuta a fila `notificacao.consulta.queue` e registra o envio do lembrete em log (sem integração real de e-mail/SMS, por decisão do escopo do desafio).

### `pulse-care-historico-service`

Serviço consumidor com projeção de leitura (padrão **CQRS**). Escuta a fila `historico.consulta.queue`, persiste uma cópia desnormalizada da consulta em `historico_db` e expõe consultas via **GraphQL**.

---

# 🛠️ Tecnologias

## Backend
- Java 21
- Spring Boot 4.1 / Spring Framework 7
- Spring Security *(JWT stateless)*
- Spring Data JPA
- Spring AMQP (RabbitMQ)
- Spring for GraphQL
- Hibernate Validator
- MapStruct

## Banco de Dados
- PostgreSQL 15 *(um container, dois databases: `agendamento_db` e `historico_db`)*
- Flyway (migrations)

## Mensageria
- RabbitMQ *(exchange topic + filas dedicadas por consumidor)*

## Segurança
- JWT

## Ferramentas
- Lombok
- Gradle (multi-módulo, com convention plugins em `buildSrc`)

## DevOps
- Docker
- Docker Compose

---

# 🐳 Arquitetura Docker

O `docker-compose.yml` sobe 5 containers:

- **postgres** → banco único, com os databases `agendamento_db` e `historico_db` criados via script de init
- **rabbitmq** → broker com exchange e filas já pré-configuradas via `definitions.json` (é criada na subida do broker, não pela aplicação)
- **agendamento-service** → API REST + login
- **notificacao-service** → consumer, sem porta HTTP exposta para uso externo
- **historico-service** → consumer + API GraphQL

Todos os serviços usam a mesma imagem `Dockerfile` (multi-stage, base `eclipse-temurin`), parametrizada pelo build-arg `SERVICE_MODULE` para gerar o jar do módulo correto.

A comunicação entre os containers ocorre via rede interna (`pulsecare_network`); cada serviço só sobe depois que suas dependências (`postgres`/`rabbitmq`) estão saudáveis (`healthcheck`).

---

# ⚙️ Configuração

## 📋 Pré-requisitos

- Docker
- Docker Compose

---

### 1. Clonar o Repositório

```bash
git clone https://github.com/LuisFelipeGM/PulseCare.git
cd PulseCare
```
---

### 2. Configurar variáveis de ambiente

Copie o arquivo de exemplo:

```bash
cp .env.example .env
```
No Windows (PowerShell):
```powershell
copy .env.example .env
```

O Docker Compose carrega o `.env` automaticamente (ele precisa estar na raiz do projeto, ao lado do `docker-compose.yml`) e usa esses valores para configurar credenciais do banco, do RabbitMQ, o segredo do JWT e as portas expostas de cada serviço. Ajuste o `.env` conforme necessário, os valores do `.env.example` já funcionam para rodar localmente.

Colocando os valores padrões somente por ser um projeto acadêmico, o ideal é utilizar um Vault ou configurar as variaveis de ambiente.

---

# 🚀 Execução

## 🐳 Docker Compose (Recomendado)

```bash
docker compose up --build
```

Isso sobe o Postgres, o RabbitMQ e os 3 microsserviços, aplicando as migrations do Flyway automaticamente em cada serviço.

| Serviço | URL |
|---|---|
| agendamento-service | http://localhost:3710 |
| historico-service | http://localhost:3711 |
| notificacao-service | *(sem API HTTP pública — apenas consumidor)* |
| RabbitMQ Management | http://localhost:15672 |

*(portas conforme configuradas no `.env`)*

---

# 🔐 Autenticação e Autorização

Existe **um único endpoint de login**, no `agendamento-service`. O token JWT gerado é válido para autenticar requisições em **todos os módulos**, pois o segredo e a validação (`JwtUtil` / `JwtAuthFilter`) são compartilhados via `pulse-care-core`.

```
POST /auth/login
```

```json
{
  "email": "medico.seed@pulsecare.com",
  "senha": "Senha123!"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9..."
}
```

Use o token nas demais requisições:

```
Authorization: Bearer {{token}}
```

## Papéis (roles)

O papel do usuário é embutido no token (`role` claim) e controla o acesso ao recurso de **consultas**:

| Papel | Permissão sobre `/consultas` |
|---|---|
| **MEDICO** | Cria e edita |
| **ENFERMEIRO** | Edita |
| **PACIENTE** | Lista/consulta |

Os demais recursos (`/usuarios`, `/tipos-usuario`) exigem apenas um token válido, sem restrição adicional de papel.

---

# 🔌 API

## `agendamento-service` (REST)

### Auth

| Método | Endpoint | Acesso |
|---|---|---|
| POST | `/auth/login` | Público |

### Usuários

| Método | Endpoint | Acesso |
|---|---|---|
| GET | `/usuarios?page&size` | Autenticado |
| GET | `/usuarios/{id}` | Autenticado |
| POST | `/usuarios` | Autenticado |
| PUT | `/usuarios/{id}` | Autenticado |
| PATCH | `/usuarios/{id}/senha` | Autenticado |
| DELETE | `/usuarios/{id}` | Autenticado |

### Tipos de Usuário

| Método | Endpoint | Acesso |
|---|---|---|
| GET | `/tipos-usuario` | Autenticado |
| GET | `/tipos-usuario/{id}` | Autenticado |

### Consultas

| Método | Endpoint | Acesso |
|---|---|---|
| POST | `/consultas` | MEDICO |
| PUT | `/consultas/{id}` | MEDICO, ENFERMEIRO |
| GET | `/consultas/{id}` | MEDICO, ENFERMEIRO, PACIENTE |
| GET | `/consultas?pacienteId=` **ou** `?medicoId=` | MEDICO, ENFERMEIRO, PACIENTE |

## `historico-service` (GraphQL)

Endpoint: `POST /graphql`
Console interativo (GraphiQL): `http://localhost:3711/graphiql`

> Todas as queries exigem o header `Authorization: Bearer {{token}}` — inclusive pelo GraphiQL, na aba de Headers.

```graphql
type HistoricoConsulta {
    id: ID!
    consultaId: ID!
    pacienteId: ID!
    pacienteNome: String
    medicoId: ID!
    medicoNome: String
    dataHora: String!
    status: String!
}

type Query {
    historicoPorPaciente(pacienteId: ID!): [HistoricoConsulta]
    consultasFuturas(pacienteId: ID!): [HistoricoConsulta]
}
```

Exemplo de query:

```graphql
query {
  historicoPorPaciente(pacienteId: 9003) {
    id
    medicoNome
    dataHora
    status
  }
}
```

---

# 📨 Mensageria (RabbitMQ)

O `agendamento-service` publica eventos de consulta em um **exchange do tipo topic**, consumidos de forma independente pelos outros dois serviços:

```
                         ┌───────────────────────────┐
                         │      consulta.exchange    │
                         │           (topic)         │
                         └──────────────┬────────────┘
                                        │ routing key: consulta.*
                     ┌──────────────────┼───────────────────┐
                     ▼                                      ▼
     ┌───────────────────────────┐              ┌───────────────────────────┐
     │ notificacao.consulta.queue│              │  historico.consulta.queue │
     └─────────────┬─────────────┘              └─────────────┬─────────────┘
                   ▼                                          ▼
        notificacao-service                          historico-service
       (loga o lembrete enviado)                (grava a projeção de leitura)
```

| Routing key | Evento |
|---|---|
| `consulta.criada` | Nova consulta cadastrada |
| `consulta.editada` | Consulta atualizada (status/data) |

A topologia (exchange, filas e bindings) é pré-carregada na subida do broker via `docker/rabbitmq/definitions.json`, **não** é criada pela aplicação em tempo de execução.

---

# 🧪 Testes

O projeto disponibiliza uma **collection do Postman** com cenários de sucesso e erro para todos os endpoints do `agendamento-service`.

- **Arquivo**: [Pulse Care.postman_collection.json](Pulse%20Care.postman_collection.json)
- A requisição de **Login** define automaticamente a variável de coleção `{{token}}` via script de teste, reaproveitada nas demais requisições.
- Uma massa de dados fixa (`V002__seed_dados_teste.sql`, ids a partir de `9001`) é aplicada pelo Flyway em cada subida do banco, permitindo rodar os cenários de sucesso em qualquer ordem, sem depender de uma requisição anterior.

OBS.: Execute os 3 endpoints de login com os usuários de teste para popular a variável `{{token}}` antes de testar os demais endpoints.

Usuários de teste (senha `Senha123!` para todos):

| Email | Papel |
|---|---|
| `medico.seed@pulsecare.com` | MEDICO |
| `enfermeiro.seed@pulsecare.com` | ENFERMEIRO |
| `paciente.seed@pulsecare.com` | PACIENTE |

Importe a collection no Postman para testar todos os fluxos com os cenários já prontos.

---

# 🏗️ Estrutura do Projeto

Monorepo Gradle multi-módulo, com plugins de convenção compartilhados em `buildSrc`:

```
pulse-care/
├── buildSrc/                          # Convention plugins (Groovy DSL)
├── docker/
│   ├── postgres/init-databases.sql    # Cria os 2 databases no boot do Postgres
│   └── rabbitmq/                      # definitions.json + rabbitmq.conf
├── pulse-care-core/
│   └── com.fiap.pulsecare.core/
│       ├── exception/                 # BusinessException, NotFoundException
│       ├── messaging/                 # RabbitConstants, ConsultaEventoDTO
│       └── security/                  # JwtUtil, JwtAuthFilter
├── pulse-care-agendamento-service/
│   └── com.fiap.pulsecare.agendamento/
│       ├── configuration/             # SecurityConfig, RabbitMQConfig, PasswordEncoderConfig
│       ├── controller/                # AuthController, UsuarioController, TipoUsuarioController, ConsultaController
│       ├── domain/
│       │   ├── entity/                # Usuario, TipoUsuario, Consulta
│       │   ├── dto/                   # Respostas da API
│       │   └── vo/                    # Payloads de entrada (validados com Bean Validation)
│       ├── exception/                 # GlobalExceptionHandler, ProblemDetailDTO (RFC 7807)
│       ├── mapper/                    # UsuarioMapper, ConsultaMapper (MapStruct)
│       ├── messaging/                 # ConsultaEventPublisher
│       ├── repository/                # Spring Data JPA
│       ├── sevice/                    # Regras de negócio
│       └── validation/                # UsuarioValidator
├── pulse-care-notificacao-service/
│   └── com.fiap.pulsecare.notificacao/
│       ├── configuration/             # RabbitMQConfig
│       ├── messaging/                 # ConsultaEventListener
│       └── service/                   # NotificacaoService
├── pulse-care-historico-service/
│   └── com.fiap.pulsecare.historico/
│       ├── configuration/             # SecurityConfig, RabbitMQConfig
│       ├── domain/entity/             # HistoricoConsulta
│       ├── messaging/                 # ConsultaEventListener
│       ├── repository/                # HistoricoConsultaRepository
│       ├── resolver/                  # HistoricoConsultaResolver (GraphQL)
│       └── service/                   # HistoricoConsultaService
├── Dockerfile                         # Imagem única, parametrizada por SERVICE_MODULE
├── docker-compose.yml
└── Pulse Care.postman_collection.json
```

---

# 🎯 Objetivos da Arquitetura

- Um único módulo de segurança/erros/mensageria compartilhado entre os serviços (`pulse-care-core`)
- Banco de dados isolado por serviço (database-per-service)
- Comunicação assíncrona e desacoplada via RabbitMQ (publicador não conhece os consumidores)
- Autenticação centralizada com token único válido em toda a plataforma
- Tratamento de erro padronizado (RFC 7807) em todos os serviços REST
- Ambiente inteiramente reproduzível via Docker Compose, sem passos manuais

---

# 📄 Licença
- Este projeto é acadêmico e de uso educacional.
