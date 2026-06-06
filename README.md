# 🏎️ driven-kafka-track

<p align="center">
  <img src="https://img.shields.io/badge/Status-Concluído-green?style=for-the-badge&logo=github" alt="Concluído">
</p>

---

## 📝 Sobre o Projeto

O **driven-kafka-track** é um ecossistema de microsserviços criado para simular o cenário real de uma empresa de locação de veículos moderna (modelo *Pay-Per-Mile*). O sistema captura dados de telemetria de carros em movimento para calcular faturamentos e disparar alertas de segurança em tempo real.

O objetivo principal deste projeto é dominar o ecossistema do **Apache Kafka** do básico ao avançado, aplicando os conceitos de **Clean Architecture** para manter o core do negócio totalmente isolado e testável.

---

## 🛠️ Tecnologias Utilizadas

Abaixo estão as principais ferramentas e frameworks que compõem a arquitetura do projeto:

<p align="left">
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="Java" width="40" height="40" style="max-width: 100%;">
  &nbsp;&nbsp;
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="Spring Boot" width="40" height="40" style="max-width: 100%;">
  &nbsp;&nbsp;
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/apachekafka/apachekafka-original.svg" alt="Apache Kafka" width="40" height="40" style="max-width: 100%;">
  &nbsp;&nbsp;
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original.svg" alt="PostgreSQL" width="40" height="40" style="max-width: 100%;">
  &nbsp;&nbsp;
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original.svg" alt="Docker" width="40" height="40" style="max-width: 100%;">
</p>

* **Java 17+ & Spring Boot 3:** Base do ecossistema para construção dos microsserviços de forma robusta.
* **Apache Kafka (KRaft):** O motor de mensageria distributed, responsável por processar o streaming de dados com garantia de ordem e resiliência.
* **Docker & Docker Compose:** Utilizado para subir localmente todo o ambiente de infraestrutura (Kafka, Kafka UI e Banco de Dados) de forma rápida e isolada.

---

## 🚀 Estrutura dos Microsserviços

O repositório é organizado no formato Monorepo e atualmente está dividido em:

- **`car-simulator` (Finalizado ✅):** Responsável por gerar dados fictícios de telemetria dos carros (velocidade, KM) e produzir eventos para o Kafka com chaves por veículo.
* **`billing-processor` (Finalizado ✅):** Consumidor que processa a quilometragem e faz o cálculo financeiro das faturas no banco.

---

## 🔌 Mapeamento de Portas

Para facilitar o desenvolvimento e evitar conflitos na sua máquina local, a arquitetura utiliza as seguintes portas:

| Serviço | Porta | Descrição |
| :--- | :--- | :--- |
| **Apache Kafka** | `9092` | Porta exposta para comunicação das aplicações Spring Boot com o broker. |
| **Kafka UI** | `8080` | Interface visual do Kafka. Acesse via `http://localhost:8080`. |
| **PostgreSQL** | `5432` | Porta padrão do banco de dados, utilizada pelo `billing-processor`. |
| **car-simulator** | `8081` | Porta do microsserviço simulador de telemetria. |
| **billing-processor** | `8082` | Porta do microsserviço responsável pelo faturamento. |

---

## Guia de implantação

Antes de iniciar o projeto, certifique-se de ter o [Docker](https://www.docker.com/) e o [Git](https://git-scm.com/) instalados.

Clone o repositório:

```bash
git clone https://github.com/breenoox/driven-kafka-track.git
```

Suba toda a stack em segundo plano:

```bash
docker compose up -d --build
```

Aguarde alguns segundos e verifique se todos os containers estão saudáveis:

```bash
docker compose ps
```

Para parar e remover os containers (mantendo os volumes):

```bash
docker compose down
```

Para parar e apagar **também os dados** dos bancos:

```bash
docker compose down -v
```

Com a stack no ar, acesse o painel do Kafka UI pelo seu navegador para visualizar o tópico car-telemetry e acompanhar o fluxo de mensagens:

```bash
http://localhost:8080
```

---

## Desenvolvedor
<table align="center">
  <tr>
    <td align="center">
      <div>
        <img src="https://avatars.githubusercontent.com/breenoox" width="120px;" alt="Foto no GitHub" class="profile"/><br>
          <b> Breno Barbosa   </b><br>
            <a href="https://www.linkedin.com/in/brenobarbosa22/" alt="Linkedin"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" height="20"></a>
            <a href="https://github.com/breenoox" alt="Github"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" height="20"></a>
      </div>
    </td>
  </tr>
</table>
