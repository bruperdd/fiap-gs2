# PulseCheck API

API REST em Spring Boot para gerenciar usuários e registrar check-ins de bem-estar. As rotas protegidas exigem um JWT válido; `/auth/login` e `/users/register` continuam públicas para facilitar a configuração inicial.

---

## 🚀 Instalação e Execução

### Pré-requisitos
- Java 21
- Maven 3.6+
- MySQL 8.0+
- (Windows) WSL2 recomendado

### 1. Clonar e preparar dependências
```bash
git clone https://github.com/.../fiap-gs2.git
cd fiap-gs2
mvn -version    # garante Maven configurado
```

### 2. Configurar o banco
```bash
sudo service mysql start
sudo mysql
```
```sql
CREATE DATABASE pulsecheck;
USE pulsecheck;

CREATE TABLE departments(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  department_id INT,
  email VARCHAR(255) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL,
  password_hash TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  active BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL
);

CREATE TABLE checkins (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  mood INT NOT NULL,
  note TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE suggestions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  mood_target INT NOT NULL
);
```

### 3. Variáveis de ambiente
> Caso não utilize o `application.properties` padrão, exporte as variáveis abaixo antes de subir a API.
```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/pulsecheck
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=
export JWT_SECRET=FIAP12345#
export JWT_EXPIRATION=36000000
export JWT_ISSUER=pulsecheck-api
```

### 4. Executar a aplicação
```bash
mvn spring-boot:run
```
Quando o log mostrar `Tomcat started on port 8080`, a API estará pronta em `http://localhost:8080`.

---

## 🧪 Testes via cURL

> Gere pelo menos um usuário usando `/users/register`, depois faça login para obter o token. Todos os endpoints abaixo, exceto registro e login, exigem `Authorization: Bearer <TOKEN>`.

### Criar usuários (rota pública)
```bash
curl -X POST http://localhost:8080/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@test.com",
    "password": "admin123",
    "name": "Admin User",
    "role": "admin",
    "department_id": 1
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@test.com",
    "password": "admin123"
  }'
```

### GET /users/listAllUsers – listar todos os usuários
```bash
curl -X GET http://localhost:8080/users/listAllUsers \
  -H "Authorization: Bearer SEU_TOKEN"
```

### GET /users/getUserById – buscar usuário autenticado
```bash
curl -X GET http://localhost:8080/users/getUserById \
  -H "Authorization: Bearer SEU_TOKEN"
```

### PUT /users/updateUser – atualizar dados do usuário autenticado
```bash
curl -X PUT http://localhost:8080/users/updateUser \
  -H "Authorization: Bearer SEU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Novo Nome",
    "email": "novo@email.com",
    "role": "admin",
    "department_id": 2
  }'
```

### DELETE /users/deactivateUser – desativar usuário autenticado
```bash
curl -X DELETE http://localhost:8080/users/deactivateUser \
  -H "Authorization: Bearer SEU_TOKEN"
```

### POST /checkins/create – criar check-in
```bash
curl -X POST http://localhost:8080/checkins/create \
  -H "Authorization: Bearer SEU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "mood": 5,
    "note": "Me sinto bem hoje!"
  }'
```

### GET /checkins/listMyCheckins – listar meus check-ins (últimos 7 dias)
```bash
curl -X GET http://localhost:8080/checkins/listMyCheckins \
  -H "Authorization: Bearer SEU_TOKEN"
```

### GET /checkins/getCheckinStatus – obter estatísticas de check-ins
```bash
curl -X GET http://localhost:8080/checkins/getCheckinStatus \
  -H "Authorization: Bearer SEU_TOKEN"
```

### GET /departments/list – listar departamentos
```bash
curl -X GET http://localhost:8080/departments/list \
  -H "Authorization: Bearer SEU_TOKEN"
```

### GET /suggestions/getSuggestionForUser – obter sugestões baseadas no humor médio
```bash
curl -X GET http://localhost:8080/suggestions/getSuggestionForUser \
  -H "Authorization: Bearer SEU_TOKEN"
```

---

## 📁 Estrutura do Projeto
```
src/
├── main/
│   ├── java/br/com/fiap/pulsecheck/
│   │   ├── config/         # Security + JWT
│   │   ├── controller/     # REST controllers
│   │   ├── dao/            # Interfaces de acesso a dados
│   │   ├── dao/impl/       # Implementações DAO
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── mapper/         # Interfaces MyBatis
│   │   ├── model/          # Modelos
│   │   ├── security/       # Filtros JWT
│   │   └── service/        # Services (interfaces e impl)
│   └── resources/
│       ├── mapper/         # XML dos mappers
│       └── application.properties
└── test/                   
```

---

## 🛠️ Tecnologias
- Java 21 • Spring Boot 3.5.7 • Spring Security
- MyBatis 3 • MySQL 8 • JWT (Auth0)
- Maven • Lombok

---

## 🧪 Testes Unitários

A aplicação possui testes unitários implementados usando JUnit 5 e Mockito.

### Executar Testes

```bash
# Executar todos os testes
mvn test

# Executar testes de uma classe específica
mvn test -Dtest=UserServiceTest

# Executar um teste específico
mvn test -Dtest=UserServiceTest#testRegister_ShouldCreateUserSuccessfully
```

### Casos de Teste Implementados

**Total:** 11 testes unitários (requisito mínimo: 3 testes)

#### Testes de Serviço (8 testes)
1. ✅ `UserServiceTest.testRegister_ShouldCreateUserSuccessfully` - Registro de usuário
2. ✅ `UserServiceTest.testGetUserById_ShouldReturnUser_WhenUserExists` - Busca de usuário por ID (sucesso)
3. ✅ `UserServiceTest.testGetUserById_ShouldThrowException_WhenUserNotFound` - Busca de usuário por ID (erro)
4. ✅ `UserServiceTest.testUpdateUser_ShouldUpdateUserSuccessfully` - Atualização de usuário
5. ✅ `UserServiceTest.testListAllUsers_ShouldReturnListOfUsers` - Listagem de usuários
6. ✅ `AuthServiceTest.testLogin_ShouldReturnJwtToken_WhenCredentialsAreValid` - Login com credenciais válidas
7. ✅ `AuthServiceTest.testLogin_ShouldThrowException_WhenUserNotFound` - Login com usuário não encontrado
8. ✅ `AuthServiceTest.testLogin_ShouldThrowException_WhenPasswordIsInvalid` - Login com senha inválida

#### Testes de Controller (3 testes)
9. ✅ `UserControllerTest.testRegister_ShouldReturnOk_WhenUserIsCreated` - Registro via controller
10. ✅ `UserControllerTest.testListAllUsers_ShouldReturnListOfUsers` - Listagem via controller
11. ✅ `UserControllerTest.testGetUserById_ShouldReturnUser_WhenTokenIsValid` - Busca por ID via controller

### Estrutura de Testes

```
src/test/java/br/com/fiap/pulsecheck/
├── service/
│   ├── UserServiceTest.java      # 5 testes
│   └── AuthServiceTest.java       # 3 testes
├── controller/
│   └── UserControllerTest.java   # 3 testes
└── PulsecheckApplicationTests.java
```

**Status:** ✅ Todos os testes unitários passando (11/11)

---
