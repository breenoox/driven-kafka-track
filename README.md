# 🏎️ driven-kafka-track

<p align="center">
  <img src="https://img.shields.io/badge/Status-Em%20Constru%C3%A7%C3%A3o-orange?style=for-the-badge&logo=github" alt="Status Em Construção">
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

* **`car-simulator` (Em andamento 🛠️):** Responsável por gerar dados fictícios de telemetria dos carros (Velocidade, KM) e produzir eventos para o Kafka com chaves por veículo.
* **`billing-processor` (Planejado 🗓️):** Consumidor que processará a quilometragem e fará o cálculo financeiro das faturas no banco.
* **`alert-stream` (Planejado 🗓️):** Processador em tempo real utilizando Kafka Streams para disparar alertas de alta velocidade.

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
