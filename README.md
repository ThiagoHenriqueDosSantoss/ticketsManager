# Tickets Manager 🎟

## Descrição do Sistema

A aplicação foi desenvolvida para gerenciar a quantidade de tickets de refeições entregues para cada pessoa. 
O sistema permite ao usuário visualizar o total de tickets entregues no geral e por pessoa, além de fornecer a possibilidade de consultar os dados dentro de um período específico.

### Tecnologias Utilizadas:

### Back-End ⚙
- **Spring Boot**: A aplicação utiliza o `spring-boot-starter-parent`, que oferece uma configuração simplificada e gerenciamento eficiente de dependências.
- **Spring Data JPA**: Usado para interagir com o banco de dados, o `spring-boot-starter-data-jpa` permite que as entidades e dados sejam manipulados de forma simples e eficiente.
- **Validadtion**: A biblioteca `spring-boot-starter-validation` é utilizada para validar as entradas de dados, garantindo que as informações processadas sejam consistentes.
  
### Front-End 🖼️
- **Java Swing** — utilizado como biblioteca para o desenvolvimento da interface gráfica da aplicação

### Banco de Dados 💾 
- **PostgreSQL**: O sistema usa o PostgreSQL como banco de dados, configurado com a dependência `postgresql` para ser executado em tempo de execução.


## 💻 Como executar o projeto

### 1. **Java 21 ou superior**
   A aplicação foi configurada para ser executada com o Java versão 21. Você precisa ter o JDK 21 ou superior instalado.

### 2. **Banco de Dados PostgreSQL**
   O sistema utiliza o PostgreSQL na versão 17 como banco de dados. Você deve ter o PostgreSQL instalado e configurado corretamente, com as credenciais de acesso apropriadas.

### 3. **Maven**
   O Maven é usado como ferramenta de construção (build tool). Para compilar e executar a aplicação, é necessário ter o Maven instalado na sua máquina.

### 4. **Configuração do Banco de Dados**

#### 1. **Criar o Banco de Dados `ticketsManager`**

##### Usando o pgAdmin:
1. Abra o **pgAdmin** e conecte-se ao servidor PostgreSQL.
2. Se esta for a primeira vez que você está usando o **pgAdmin**, você precisará configurar o acesso ao seu servidor PostgreSQL, criando um usuário com um nome "admin" e a senha "admin" para se conectar ao banco de dados (Caso ja tenha com outras credênciais, senão explicação abaixo como configurar uma nova login/role).
3. No painel à esquerda, clique com o botão direito em **Databases** e selecione **Create > Database**.
4. Nomeie o banco de dados como `ticketsManager` e clique em **Save**.

#### 2. **Criar Role de Login `admin` no PostgreSQL**

##### Passos:
1. No **pgAdmin**, expanda **Login/Group Roles**.
2. Clique com o botão direito e selecione **Create > Login/Group Role**.
3. Na aba **General**, defina o **Role Name** como `admin`.
4. Na aba **Definition**, defina a **Password** como `admin`.
5. Na aba **Privileges**, marque todas as opções, como `Can login`, `Create DB`, `Create Role`, `Superuser` (se desejar permissões totais).
6. Clique em **Save** para criar o usuário.

#### 3. **Conceder Acesso ao Banco de Dados (Caso ja use o banco com outras credências)**

1. No **pgAdmin**, clique com o botão direito no banco de dados `ticketsManager` e selecione **Properties**.
2. Na aba **Privileges**, clique no **+** para adicionar a role `admin`.
3. Marque a permissão **CONNECT** para conceder acesso.
4. Clique em **Save**.

Agora, a role `admin` com o banco de dados `ticketsManager` está configurada e pronta para ser usada pela aplicação.

## 💻 Rodando o projeto
```sh
# Clonar repositório
git clone https://github.com/ThiagoHenriqueDosSantoss/ticketsManager.git

# Entrar na pasta do projeto
cd \src\main\java\com\example\ticketsManager

# Executar o projeto
mvn spring-boot:run
```
