# Auth-API

Microservicio para la creación y consulta de usuarios

## 📌 Funcionalidades

- Registro de usuarios (`/sign-up`)
- Login con generación de JWT (`/login`)
- CRUD de usuarios (solo accesible con token válido)

## 🧩 Estructura general del proyecto

![components-diagram](./misc/component-diagram.png)

## Funcionamiento del proceso de seguridad

**Sign-up**
![sign-up](./misc/sign-up.png)

**Login**
![login](./misc/login.png)

**Autenticación de otras consultas**
![getUsers](./misc/getUsers.png)

## 🚀 Stack

- Java 11+
- Spring Boot (Security, Web, Data JPA, Validation)
- H2 Database
- JJWT (Json Web Token)
- Lombok
- JUnit 5 & Mockito
- Gradle
- JaCoCo
- Swagger

## 📦 Instalación y Ejecución

### 🔧 Prerrequisitos

- Java 11 o superior
- Gradle (o usar el wrapper incluido: `./gradlew`)
- IDE como IntelliJ, Eclipse o VSCode (opcional)

### ▶️ Ejecutar localmente

```bash
./gradlew bootRun
```

### 📖 Documentación de la API

[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

### 🧪 Ejecutar tests

```bash
./gradlew test
```

### 📈 Ver reporte de cobertura

**_El reporte será generado automaticamente una vez ejecutados los tests_**

Abrir `./build/reports/jacoco/test/html/index.html` en el navegador.
