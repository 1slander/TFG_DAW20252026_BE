# TFG Backend – Guía de uso para el equipo

Este repositorio contiene el **backend del TFG**, desarrollado con **Java + Spring Boot + JPA (Hibernate)** y **PostgreSQL**.

El objetivo de este README es que **cualquier compañero del equipo pueda clonar el proyecto y arrancarlo sin romper nada**, entendiendo cómo funcionan:

* los perfiles (`local` / `railway`)
* las variables de entorno
* la base de datos

---

## 🧠 Arquitectura general

```
Frontend
   ↓ (HTTP / JSON)
Backend (Spring Boot)
   ↓ (JPA / Hibernate)
PostgreSQL
```

La aplicación **solo se conecta a UNA base de datos por ejecución**, según el perfil activo:

* `local` → PostgreSQL en local (Docker)
* `railway` → PostgreSQL en Railway (nube)

---

## 📦 Tecnologías usadas

* Java 21
* Spring Boot
* Spring Data JPA (Hibernate)
* PostgreSQL
* Maven Wrapper (`mvnw`)
* JWT (autenticación)

---

## 🚀 Arranque rápido (LOCAL)

### 1️⃣ Clonar el repositorio

```bash
git clone <URL_DEL_REPO>
cd TFG_DAW20252026_BE
```

---

### 2️⃣ Requisitos previos

* Java 21 instalado
* Docker Desktop instalado y en ejecución
* VS Code o IntelliJ (recomendado)

---

### 3️⃣ Base de datos local (PostgreSQL)

El backend usa PostgreSQL.

En local se espera una base de datos con:

* **Host:** `localhost`
* **Puerto:** `5432`
* **Base de datos:** `tfg_be_bbdd`
* **Usuario:** `tfg_user`
* **Password:** `tfg_password`

> Cada compañero puede usar sus propios valores si quiere, **siempre que los defina en variables de entorno**.

---

### 4️⃣ Variables de entorno (OBLIGATORIO)

El proyecto **NO tiene credenciales en el código**.

Cada desarrollador debe definir sus propias variables de entorno.

#### Opción recomendada (VS Code): archivo `.env`

Crear un archivo **`.env`** en la raíz del proyecto:

```env
SPRING_PROFILES_ACTIVE=local

DB_URL=jdbc:postgresql://localhost:5432/tfg_be_bbdd
DB_USER=tfg_user
DB_PASSWORD=tfg_password

JWT_SECRET=jwt_secret_local
JWT_EXPIRATION=84600000
```

⚠️ **Este archivo NO se sube a Git**.

Asegurarse de que `.env` está en el `.gitignore`.

---

### 5️⃣ Ejecutar el backend

Desde VS Code:

* Abrir el proyecto
* Ejecutar la configuración de Spring Boot

O desde terminal:

```bash
./mvnw spring-boot:run
```

Si todo está correcto, en los logs se verá:

```
The following profiles are active: local
```

Y el backend arrancará en:

```
http://localhost:8087
```

---

## 📚 Swagger (API Docs)

Cuando el backend está arrancado:

```
http://localhost:8087/swagger-ui.html
```

Swagger es la **fuente de verdad** para el frontend:

* rutas
* métodos
* payloads
* respuestas

---

## 🌍 Entorno Railway (BD online)

La base de datos en Railway **ya existe** y es compartida.

El backend desplegado en Railway usa:

* perfil `railway`
* variables de entorno definidas en el panel de Railway

Los compañeros **NO necesitan configurar Railway en local** para desarrollar frontend.

---

## 🔐 Seguridad (JWT)

* El backend usa JWT para proteger rutas
* Swagger y login están accesibles sin token
* El resto de endpoints requieren autenticación

⚠️ **Nunca subir secrets reales al repositorio**.

---

## 🧪 Perfiles de Spring

| Perfil    | Uso                       |
| --------- | ------------------------- |
| `local`   | Desarrollo local          |
| `railway` | Entorno compartido / nube |

El perfil se elige **mediante variable de entorno**, no en el código.

---

## ❌ Cosas que NO hay que hacer

* ❌ Subir `.env` a Git
* ❌ Hardcodear passwords en `application.properties`
* ❌ Cambiar de BD tocando código
* ❌ Usar dos perfiles a la vez para base de datos

---

## ✅ Buenas prácticas del proyecto

* Configuración por perfiles
* Variables de entorno
* Hibernate genera el esquema
* PostgreSQL como motor único
* Backend independiente del PC del desarrollador

---

## 🆘 Problemas comunes

### ❓ Error: `Could not resolve placeholder`

➡ Falta una variable de entorno.

Revisar el archivo `.env` o las variables del sistema.

---

### ❓ Error de conexión a la base de datos

➡ PostgreSQL no está levantado o la URL es incorrecta.

---

## 👥 Equipo

Este README está pensado para que **cualquier miembro del TFG** pueda:

* clonar el proyecto
* configurar su entorno
* arrancar el backend

sin depender de otra persona.

---

Si algo no está claro, preguntad antes de tocar la configuración 😄
