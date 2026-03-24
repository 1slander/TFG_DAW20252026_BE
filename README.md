# ⚙️ TeamPlate - Sistema de Gestión de Restaurantes (Backend)

Este repositorio contiene el núcleo lógico (API RESTful) de **TeamPlate**, una plataforma integral enfocada a digitalizar la operatividad del sector hostelero (HORECA). Desarrollado con el ecosistema de **Spring Boot 3** y **Java 21**.

---

## 🛠️ Tecnologías y Arquitectura

* **Lenguaje:** Java 21 (Aprovechando Virtual Threads para alta simultaneidad y bajo consumo de RAM).
* **Framework Core:** Spring Boot 3.x.
* **Arquitectura:** Limpia (Controllers -> Services -> Repositories -> Entities) y API REST Stateless.
* **Persistencia (ORM):** Spring Data JPA (Hibernate).
* **Base de Datos:** PostgreSQL (Ejecutándose en contenedores Docker para aislamiento).
* **Gestor de dependencias:** Maven Wrapper (`mvnw`).
* **Seguridad:** Spring Security + JWT HMAC SHA-256 (com.auth0).
* **Documentación:** Swagger UI (OpenAPI v3).

---

## 🗄️ Modelado de Datos y Retos Técnicos

### Estrategia de Herencia (JOINED)
Dada la naturaleza del sistema, los usuarios varían enormemente en atributos según si son administradores del sistema o empleados de un restaurante. Para resolver el *Impedance Mismatch* y no duplicar datos de credenciales, usamos la herencia de JPA `@Inheritance(strategy=InheritanceType.JOINED)` con una tabla base `users` abstracta y subclases físicas `employees` y `admins`. Hibernate genera los `JOIN` de forma nativa manteniendo la integridad referencial.

### Resolución de Ciclos JSON
Las relaciones bidireccionales típicas en los ORMs (Ej: `Employee` a `TableAssignment` y viceversa) generan *StackOverflowExceptions* durante la serialización Jackson. Esto se resolvió diseñando una dirección limpia en los modelos físicos evitando dependencias circulares innecesarias desde `Employee.java`.

### Manejo Centralizado de Errores
El uso de `@ControllerAdvice` (`GlobalExceptionHandler`) captura excepciones y devuelve objetos JSON semánticos (400, 403, 404, etc.), impidiendo que las trazas de error internas lleguen al Frontend de Angular.

---

## 🛡️ Seguridad y Jerarquía de Roles (RBAC)

La API no almacena contexto de sesión (`SessionCreationPolicy.STATELESS`). Todo el control de seguridad perimetral se basa en la validación matemática de JWTs.

**Roles soportados (Estrictamente jerárquicos):**
1. `ROLE_ADMIN`: Control global sobre plataformas y propietarios.
2. `ROLE_OWNER`: Dueño y administrador de la instancia de su propio restaurante.
3. `ROLE_MANAGER`: Gestión de turnos y salas.
4. `ROLE_ASSISTANT_MANAGER`: Apoyo en recursos humanos y configuración.
5. `ROLE_TEAM_LEADER`: Liderazgo de sala durante el servicio.
6. `ROLE_EMPLOYEE`: Empleado raso (acceso a calendario, vistas propias, chat y estado de mesas asignadas).

---

## 🚀 Guía Rápida de Arranque (Desarrollo Local)

### 1️⃣ Requisitos previos
* Java 21 instalado globalmente.
* Docker Desktop ejecutándose.
* IDE recomendado: VS Code, IntelliJ IDEA o Eclipse.

### 2️⃣ Base de Datos Local (PostgreSQL)
El backend requiere conectarse a un PostgreSQL corriendo localmente (típicamente levantado por Docker). 

### 3️⃣ Variables de Entorno (OBLIGATORIO)
El proyecto usa interpolación segura (`${VARIABLE:}`). Crea un archivo `.env` en la raíz (no se sube a Git).


### 4️⃣ Ejecución
Desde tu terminal ejecuta:
```bash
./mvnw spring-boot:run
```
La consola informará: `The following profiles are active: local`.
Accede a Swagger para ver, probar e interactuar con toda la API disponible en:
👉 `http://localhost:8087/swagger-ui.html`

---

## 🌍 Entornos y Perfiles

| Perfil | Uso / Configuración |
| :--- | :--- |
| `local` | Desarrollo en el ordenador. Carga la conexión local al Docker instanciado. |
| `railway` | Producción remota. Conecta a la base de datos PostgreSQL abstraída en la nube de Railway, con variables de entorno servidas por su orquestador. Las migraciones y comprobaciones están delegadas a Railway. |

No comprometas nunca claves de entorno reales dentro de `application.properties`.
