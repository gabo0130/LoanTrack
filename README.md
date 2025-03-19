# LoanTrack

**LoanTrack** es una plataforma web diseñada para gestionar y dar seguimiento a los procesos de solicitud, aprobación y pago de micropréstamos de manera automatizada. Su objetivo es mejorar la eficiencia operativa, reducir errores y migrar los procesos manuales a un entorno digital accesible tanto para la empresa como para los clientes.

## 🚀 Funcionalidades Principales

- **Gestión de Solicitudes:** Permite a los usuarios solicitar micropréstamos fácilmente.
- **Aprobación Automatizada:** Sistema de evaluación de crédito para decisiones rápidas y basadas en datos.
- **Seguimiento de Pagos:** Control en tiempo real del estado de los préstamos y sus pagos.
- **Cumplimiento Normativo:** Asegura que todos los procesos cumplan con las regulaciones locales e internacionales.
- **Escalabilidad:** Capacidad para manejar grandes volúmenes de transacciones sin aumentar los costos operativos.

## 🎯 Objetivos del Proyecto

- **Automatización de Procesos:** Digitalización de operaciones manuales para mejorar la eficiencia y reducir errores.
- **Reducción de Costos Operativos:** Minimizar la intervención humana en tareas repetitivas, lo que reduce el margen de error y los costos asociados.
- **Agilidad en la Toma de Decisiones:** Decisiones más rápidas y precisas gracias a la integración de algoritmos de análisis de riesgo crediticio.
- **Estandarización y Consistencia:** Implementación de procesos estandarizados para asegurar la calidad del servicio.
- **Escalabilidad:** Diseñado para expandirse sin requerir un incremento en costos proporcionales.

## 🛠️ Tecnologías Utilizadas

- **Frontend:** React JS
- **Backend:** Spring Boot, Java
- **Base de Datos:** PostgreSQL

## 📂 Estructura del Proyecto

LoanTrack/ │
├── frontend/ # Aplicación web (React)
├── backend/ # API REST (Spring Boot)
│       ├── customer-service/
│       ├── credit-product-service/
│       ├── payment-service/
│       ├── auth-service/
│       ├── notification-service/
│       ├── docker-compose.yml (Postgres + servicios)
├── database/ # Scripts de base de datos y migraciones
└── docs/ # Documentación del proyecto

## 📝 Instalación y Configuración

### Prerrequisitos

- **Node.js** (versión >= 16.x)
- **Java** (versión 17)
- **PostgreSQL**
- **Maven**

# LoanTrack Microservices

Este repositorio contiene los microservicios de la plataforma **LoanTrack**, diseñada para gestionar y dar seguimiento a los procesos de solicitud, aprobación y pago de micropréstamos. Cada microservicio cumple una responsabilidad específica, facilitando la escalabilidad, mantenibilidad y despliegue independiente.

---

## Microservicios Incluidos:

### 1. Customer Service

**Descripción:**
Gestiona la información de los clientes, incluyendo datos personales, estado del cliente (nuevo o recurrente), y sus referencias.

**Endpoints Principales:**

- `POST /api/customers` - Crear nuevo cliente
- `GET /api/customers` - Listar clientes
- `GET /api/customers/{id}` - Obtener detalle de cliente
- `PUT /api/customers/{id}` - Actualizar cliente
- `DELETE /api/customers/{id}` - Eliminar cliente

**Dockerfile:**

```dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/customer-service.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 2. Credit Product Service

**Descripción:**
Gestiona productos financieros, ya sean productos físicos a crédito o préstamos de dinero, incluyendo las reglas para intereses y plazos.

**Endpoints Principales:**

- `POST /api/products` - Crear producto financiero
- `GET /api/products` - Listar productos
- `GET /api/products/{id}` - Detalle producto
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto

**Dockerfile:**

```dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/credit-product-service.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 3. Loan Management Service

**Descripción:**
Gestiona los créditos otorgados a los clientes, incluyendo la asignación de productos o dinero a crédito, plazos, cuotas y condiciones.

**Endpoints Principales:**

- `POST /api/loans` - Crear crédito
- `GET /api/loans` - Listar créditos
- `GET /api/loans/{id}` - Detalle crédito
- `PUT /api/loans/{id}` - Actualizar crédito
- `DELETE /api/loans/{id}` - Eliminar crédito

**Dockerfile:**

```dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/loan-management-service.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 4. Payment Service

**Descripción:**
Gestiona los pagos de las cuotas de los créditos, permite registrar pagos parciales, totales y consultar el estado de pagos.

**Endpoints Principales:**

- `POST /api/payments` - Registrar pago
- `GET /api/payments` - Listar pagos
- `GET /api/payments/{id}` - Detalle pago

**Dockerfile:**

```dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/payment-service.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 5. Auth Service

**Descripción:**
Gestiona la autenticación y autorización de usuarios, generación de tokens JWT y control de roles (admin, usuario).

**Endpoints Principales:**

- `POST /api/auth/register` - Registrar usuario
- `POST /api/auth/login` - Iniciar sesión y obtener token JWT
- `GET /api/auth/me` - Obtener información del usuario autenticado

**Dockerfile:**

```dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/auth-service.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 6. Notification Service

**Descripción:**
Gestiona el envío de notificaciones a los clientes (correo, SMS o cualquier integración futura) para informar sobre aprobaciones, vencimientos, pagos, etc.

**Endpoints Principales:**

- `POST /api/notifications` - Enviar notificación

**Dockerfile:**

```dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/notification-service.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Configuración de Base de Datos

Se utiliza PostgreSQL como base de datos para todos los microservicios.

**Docker Compose para desarrollo local:**

```yaml
version: "3.8"
services:
  postgres:
    image: postgres:14
    container_name: loantrack-db
    restart: always
    environment:
      POSTGRES_DB: loantrack
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

---

## Despliegue en AWS

Los microservicios están diseñados para ser desplegados en **AWS ECS con Fargate**. Las imágenes Docker deben subirse a **Amazon Elastic Container Registry (ECR)**. Luego, se deben crear definiciones de tareas en ECS para cada servicio, configurando sus puertos, variables de entorno y asignando la capacidad de Fargate necesaria.

---

## Próximos Pasos

- Implementar CI/CD pipeline con AWS CodePipeline o GitHub Actions para automatizar build y deploy.
- Implementar observabilidad con AWS CloudWatch y X-Ray.
- Asegurar comunicación segura con AWS Secrets Manager para credenciales.

---

## Contacto

¡Para dudas o sugerencias, contáctame en yoonny0130@hotmail.com.
