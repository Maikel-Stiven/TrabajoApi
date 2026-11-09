# TiendaApp - API REST con Autenticación JWT y Control de Roles

API RESTful para la gestión integral de un comercio electrónico, desarrollada en **Spring Boot 3.3.5** y **Java 21**. El sistema cuenta con arquitectura de seguridad desacoplada (sin usar Spring Security completo), validación manual mediante filtro JWT (`JwtValidationFilter`) e interceptor de permisos por anotaciones (`RoleInterceptor` con `@RequiresRole`).

---

## 🛠️ Tecnologías y Requisitos

* **Lenguaje:** Java 17+ / Java 21
* **Framework:** Spring Boot 3.3.5 (Spring Web, Spring Data JPA)
* **Base de Datos:** MariaDB / MySQL[cite: 2, 22]
* **Librería JWT:** `io.jsonwebtoken:jjwt-api:0.12.5`[cite: 2, 22]
* **Encriptación de Claves:** `spring-security-crypto` (`BCryptPasswordEncoder`)
* **Herramientas:** Lombok, Maven[cite: 2, 22]

---

## ⚙️ Configuración y Variables de Entorno

Por seguridad, **nunca debes incluir contraseñas ni llaves secretas en el código fuente**. Las propiedades sensibles se leen desde variables de entorno con valores por defecto locales definidos en `src/main/resources/application.yaml`[cite: 2, 22]:

### Generación de la Clave Secreta JWT

Puedes generar una clave secreta segura en Base64 utilizando `openssl` desde la terminal:

```bash
openssl rand -base64 32
