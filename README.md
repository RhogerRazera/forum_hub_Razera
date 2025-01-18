# Fórum Hub API 🚀

Bem-vindo ao **Fórum Hub API**! Este é o coração do seu novo espaço de discussões online, onde você pode criar, gerenciar e interagir com tópicos de maneira simples e eficiente. O projeto foi desenvolvido com muito carinho durante o desafio da **Alura** em parceria com o programa **Oracle Next Education**.

## 🛠️ O que está por trás disso?

Este projeto foi construído com as tecnologias mais modernas para garantir que você tenha uma experiência rápida, segura e sem fricções. Aqui estão os detalhes:

### ⚙️ Tecnologias que usamos
- **Java 17**: A linguagem que oferece performance e versatilidade.
- **Spring Boot 3.3.2**: O framework que facilita a criação de APIs robustas e escaláveis.
- **MySQL**: Banco de dados relacional, com migrações gerenciadas via Flyway para manter tudo organizado.
- **Maven**: O orquestrador de dependências que garante que tudo esteja sempre no seu devido lugar.
- **Spring Validation & Spring Security**: Garantindo que os dados sejam sempre válidos e os usuários, seguros.
- **JWT (JSON Web Token)**: O método de autenticação que permite sessões seguras e ágeis.
- **Springdoc OpenAPI**: Para uma documentação de API automática e interativa – fácil de usar e entender!

## 📦 Dependências principais

Aqui estão as bibliotecas que fazem tudo funcionar bem nos bastidores. Para mais detalhes, você pode conferir o arquivo `pom.xml`:

- **Spring Boot Starter Web**: A fundação para criar APIs REST poderosas.
- **Spring Boot Starter Data JPA**: Facilita o trabalho com o banco de dados, tornando as interações mais ágeis.
- **Spring Boot Starter Validation**: Garante que os dados que você recebe e envia estejam sempre corretos.
- **Spring Boot Starter Security**: Controle de acesso para que apenas quem deve, acesse as funcionalidades.
- **Flyway**: Cuida das migrações do banco de dados, mantendo tudo sempre no seu devido lugar.
- **Lombok**: Reduz a quantidade de código repetitivo, tornando o código mais limpo e eficiente.
- **Springdoc OpenAPI**: Gera uma documentação linda e interativa da sua API.
- **MySQL Connector**: Faz a mágica da conexão entre o backend e o banco de dados.

## ✨ Funcionalidades Principais

O **Fórum Hub API** oferece diversas funcionalidades para facilitar o gerenciamento de tópicos e discussões. Aqui estão as mais importantes:

### 🔹 **Cadastro de Tópicos**
Crie novos tópicos com um título único e uma descrição envolvente. Quer garantir que tudo esteja organizado? A API valida se o título e a mensagem são únicos!

### 🔹 **Listagem de Tópicos**
Visualize todos os tópicos do sistema com a capacidade de ordenar e paginar as respostas. Perfeito para encontrar rapidamente aquilo que mais interessa.

### 🔹 **Detalhamento de Tópico**
Quer saber mais sobre um tópico específico? Basta usar o ID e você terá todas as informações detalhadas ao alcance de um clique.

### 🔹 **Atualização e Exclusão**
Necessário fazer ajustes? A API permite atualizar dados de tópicos e até mesmo excluir logicamente tópicos, tornando-os inativos sem perder informações.

### 🔹 **Autenticação JWT**
Com a autenticação baseada em JWT, apenas usuários autenticados terão acesso a endpoints sensíveis. Segurança e praticidade andando lado a lado.
