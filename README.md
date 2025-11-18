# PulseCheck API - Sistema de Bem-estar no Trabalho

API REST desenvolvida em Java Spring Boot para gerenciamento de usuários e check-ins de bem-estar.

## 📋 Pré-requisitos

- **Java JDK 21** ou superior
- **Maven 3.6+**
- **MySQL 8.0+**
- **WSL2** (se estiver no Windows)

## 🚀 Instalação

### 1. Verificar Java e Maven

```bash
# Verificar versão do Java (deve ser 21+)
java -version

# Verificar versão do Maven
mvn -version
```

Se não tiver instalado:

```bash
# Instalar Java 21 (Ubuntu/WSL2)
sudo apt update
sudo apt install openjdk-21-jdk

# Configurar JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Adicionar ao ~/.bashrc para persistir
echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc

# Instalar Maven
sudo apt install maven
```

### 2. Instalar e Configurar MySQL

```bash
# Instalar MySQL Server
sudo apt update
sudo apt install mysql-server -y

# Iniciar MySQL
sudo service mysql start

# Verificar status
sudo service mysql status
```

### 3. Configurar Banco de Dados

```bash
# Entrar no MySQL
sudo mysql
```

Execute os seguintes comandos SQL:

```sql
-- Configurar senha root (deixar vazia para desenvolvimento)
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '';
FLUSH PRIVILEGES;

-- Criar banco de dados
CREATE DATABASE pulsecheck1;

-- Usar o banco
USE pulsecheck1;

-- Criar tabela departments
CREATE TABLE departments(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela users
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  department_id INT,
  email VARCHAR(255) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL CHECK (role IN ('member','admin')) DEFAULT 'member',
  password_hash TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  active BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL
);

-- Criar tabela checkins
CREATE TABLE checkins (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  mood INT NOT NULL CHECK (mood BETWEEN 1 AND 5),
  note TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Criar tabela suggestions
CREATE TABLE suggestions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(500) NOT NULL,
  description TEXT NOT NULL,
  mood_target INT CHECK (mood_target BETWEEN 1 AND 5)
);

-- Verificar tabelas criadas
SHOW TABLES;

EXIT;
```

### 4. Configurar Aplicação

O arquivo `src/main/resources/application.properties` já está configurado:

```properties
spring.application.name=pulsecheck

# Database Credentials
spring.datasource.url=jdbc:mysql://localhost:3306/pulsecheck1
spring.datasource.username=root
spring.datasource.password=

# JWT
jwt.secret=FIAP12345#
jwt.expiration=36000000
jwt.issuer=pulsecheck-api
```

## ▶️ Como Rodar o Projeto

### 1. Iniciar MySQL

```bash
sudo service mysql start
```

### 2. Compilar e Executar

```bash
# Compilar e executar
mvn spring-boot:run
```

### 3. Verificar se está rodando

Aguarde a mensagem:
```
Started PulsecheckApplication in X.XX seconds
Tomcat started on port 8080 (http)
```

A API estará disponível em: `http://localhost:8080`

## 🧪 Testando as Rotas

### Pré-requisito: Criar Usuários

Primeiro, você precisa criar pelo menos um usuário admin. Se a rota `/users/create` estiver liberada, use:

```bash
# Criar usuário ADMIN
curl -X POST http://localhost:8080/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@test.com",
    "password_hash": "admin123",
    "name": "Admin User",
    "role": "admin",
    "department_id": 1
  }'

# Criar usuário COMUM
curl -X POST http://localhost:8080/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@test.com",
    "password_hash": "user123",
    "name": "User Normal",
    "role": "member",
    "department_id": 1
  }'
```

### 1. Autenticação - Login

```bash
# Login com admin
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@test.com",
    "password": "admin123"
  }'

# Login com user
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@test.com",
    "password": "user123"
  }'
```

**Resposta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**⚠️ IMPORTANTE:** Copie o token retornado! Você precisará dele para as próximas requisições.

---

### 2. GET /users - Listar Todos os Usuários (Admin Only)

```bash
# Com token de ADMIN (deve funcionar)
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer SEU_TOKEN_ADMIN"

# Com token de USER (deve retornar erro)
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer SEU_TOKEN_USER"
```

**Resposta esperada (admin):**
```json
[
  {
    "id": 1,
    "department_id": 1,
    "email": "admin@test.com",
    "name": "Admin User",
    "role": "admin",
    "active": true,
    "created_at": "2024-11-17T21:00:00"
  },
  {
    "id": 2,
    "department_id": 1,
    "email": "user@test.com",
    "name": "User Normal",
    "role": "member",
    "active": true,
    "created_at": "2024-11-17T21:00:00"
  }
]
```

**Resposta esperada (user):** Erro 500 - "Apenas administradores podem listar todos os usuários"

---

### 3. GET /users/{id} - Buscar Usuário por ID

```bash
# Admin pode ver qualquer perfil
curl -X GET http://localhost:8080/users/1 \
  -H "Authorization: Bearer SEU_TOKEN_ADMIN"

curl -X GET http://localhost:8080/users/2 \
  -H "Authorization: Bearer SEU_TOKEN_ADMIN"

# User só pode ver próprio perfil
curl -X GET http://localhost:8080/users/2 \
  -H "Authorization: Bearer SEU_TOKEN_USER"

# User tentando ver outro perfil (deve retornar erro)
curl -X GET http://localhost:8080/users/1 \
  -H "Authorization: Bearer SEU_TOKEN_USER"
```

**Resposta esperada:**
```json
{
  "id": 2,
  "department_id": 1,
  "email": "user@test.com",
  "name": "User Normal",
  "role": "member",
  "active": true,
  "created_at": "2024-11-17T21:00:00"
}
```

**Resposta esperada (user vendo outro perfil):** Erro 500 - "Você só pode ver seu próprio perfil"

---

### 4. PUT /users/{id} - Atualizar Usuário

#### Admin atualizando qualquer usuário:

```bash
# Admin atualizando usuário ID 2
curl -X PUT http://localhost:8080/users/2 \
  -H "Authorization: Bearer SEU_TOKEN_ADMIN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Nome Atualizado pelo Admin",
    "email": "novoemail@test.com",
    "role": "admin",
    "department_id": 1
  }'
```

#### User atualizando próprio perfil:

```bash
# User atualizando seus próprios dados (só nome e email)
curl -X PUT http://localhost:8080/users/2 \
  -H "Authorization: Bearer SEU_TOKEN_USER" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Meu Nome Atualizado",
    "email": "meunovoemail@test.com"
  }'
```

#### User tentando atualizar outro usuário (deve falhar):

```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Authorization: Bearer SEU_TOKEN_USER" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Tentando Hackear"
  }'
```

#### User tentando alterar role (deve falhar):

```bash
curl -X PUT http://localhost:8080/users/2 \
  -H "Authorization: Bearer SEU_TOKEN_USER" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Meu Nome",
    "role": "admin"
  }'
```

**Resposta esperada (sucesso):** `"User updated"`

**Resposta esperada (erros):**
- "Você só pode atualizar seu próprio perfil"
- "Você não pode alterar sua role"
- "Você não pode alterar seu departamento"

---

### 5. DELETE /users/{id} - Desativar Usuário

#### Admin desativando qualquer usuário:

```bash
# Admin desativando usuário ID 2
curl -X DELETE http://localhost:8080/users/2 \
  -H "Authorization: Bearer SEU_TOKEN_ADMIN"
```

#### User desativando própria conta:

```bash
# User desativando sua própria conta
curl -X DELETE http://localhost:8080/users/2 \
  -H "Authorization: Bearer SEU_TOKEN_USER"
```

#### User tentando desativar outro usuário (deve falhar):

```bash
curl -X DELETE http://localhost:8080/users/1 \
  -H "Authorization: Bearer SEU_TOKEN_USER"
```

**Resposta esperada (sucesso):** `"User deactivated"`

**Resposta esperada (erro):** "Você só pode desativar sua própria conta"

---

### 6. PUT /users/{id}/activate - Ativar Usuário (Admin Only)

```bash
# Admin ativando usuário desativado
curl -X PUT http://localhost:8080/users/2/activate \
  -H "Authorization: Bearer SEU_TOKEN_ADMIN"

# User tentando ativar (deve falhar)
curl -X PUT http://localhost:8080/users/2/activate \
  -H "Authorization: Bearer SEU_TOKEN_USER"
```

**Resposta esperada (sucesso):** `"User activated"`

**Resposta esperada (erro):** "Apenas administradores podem ativar usuários"

---

## 🔐 Privilégios de Acesso

### Admin pode:
- ✅ Ver todos os usuários
- ✅ Ver qualquer perfil
- ✅ Atualizar qualquer usuário (incluindo role e departamento)
- ✅ Desativar/ativar qualquer usuário
- ✅ Criar novos usuários

### User pode:
- ❌ Ver lista de todos os usuários
- ✅ Ver apenas próprio perfil
- ✅ Atualizar apenas próprio perfil (nome, email)
- ❌ Alterar role ou departamento
- ✅ Desativar apenas própria conta
- ❌ Criar usuários

---

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/com/fiap/pulsecheck/
│   │       ├── config/          # Configurações (Security, JWT)
│   │       ├── controller/      # Controllers REST
│   │       ├── dao/             # Data Access Object (interfaces)
│   │       ├── dao/impl/        # Implementações DAO
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── factory/         # Factory classes (MyBatis, Connection)
│   │       ├── mapper/         # MyBatis Mappers (interfaces)
│   │       ├── model/           # Modelos de domínio
│   │       ├── security/        # Filtros de segurança
│   │       └── service/         # Services (interfaces)
│   │       └── service/impl/    # Implementações Service
│   └── resources/
│       ├── mapper/              # XML MyBatis Mappers
│       └── application.properties
└── test/
```

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Security**
- **MyBatis 3.0.3**
- **MySQL 8.0**
- **JWT (Auth0)**
- **Maven**
- **Lombok**

---

## 📝 Variáveis de Ambiente

O projeto usa `application.properties`. Para produção, considere usar variáveis de ambiente:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/pulsecheck1
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=
export JWT_SECRET=FIAP12345#
export JWT_EXPIRATION=36000000
```

---

## 🐛 Troubleshooting

### Erro: "Connection refused" ao iniciar
- Verifique se o MySQL está rodando: `sudo service mysql status`
- Inicie o MySQL: `sudo service mysql start`

### Erro: "JAVA_HOME not set"
- Configure JAVA_HOME conforme instruções de instalação
- Recarregue o terminal: `source ~/.bashrc`

### Erro: "401 Unauthorized"
- Verifique se o token JWT está correto
- Faça login novamente para obter um novo token

### Erro: "Usuário não encontrado"
- Verifique se os usuários foram criados no banco
- Confirme os IDs dos usuários: `SELECT id, email FROM users;`

---