# PulseCheck API

API REST em Spring Boot para gerenciar usuários e registrar check-ins de bem-estar. As rotas protegidas exigem um JWT válido; `/auth/login` e `/users/create` continuam públicas para facilitar a configuração inicial.

---

## 🚀 Instalação e Execução

### Pré-requisitos
- Java 21+
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
CREATE DATABASE pulsecheck1;
USE pulsecheck1;

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
```

### 3. Variáveis de ambiente
> Caso não utilize o `application.properties` padrão, exporte as variáveis abaixo antes de subir a API.
```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/pulsecheck1
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

> Gere pelo menos um usuário usando `/users/create`, depois faça login para obter o token. Todos os endpoints abaixo, exceto criação e login, exigem `Authorization: Bearer <TOKEN>`.

### Criar usuários (rota pública)
```bash
curl -X POST http://localhost:8080/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@test.com",
    "password_hash": "admin123",
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

### GET /users – listar tudo
```bash
curl -X GET http://localhost:8080/users \
  -H "Authorization: Bearer SEU_TOKEN"
```

### GET /users/{id} – buscar por ID
```bash
curl -X GET http://localhost:8080/users/1 \
  -H "Authorization: Bearer SEU_TOKEN"
```

### PUT /users/{id} – atualizar dados
```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Authorization: Bearer SEU_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Novo Nome",
    "email": "novo@email.com",
    "role": "admin",
    "department_id": 2
  }'
```

### DELETE /users/{id} – desativar usuário
```bash
curl -X DELETE http://localhost:8080/users/1 \
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
- Java 21 • Spring Boot 3 • Spring Security
- MyBatis 3 • MySQL 8 • JWT (Auth0)
- Maven • Lombok

---
