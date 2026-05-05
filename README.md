# Arquitectura del Sistema - Digital Money (Sprint 1)

## Flujo de Servicios y Componentes
1. **Eureka Server (Puerto 8761):** Actúa como Service Discovery. Todos los microservicios se registran aquí al inicializarse.
2. **API Gateway (Puerto 8080):** Es el único punto de entrada público. Enruta las peticiones de los clientes hacia los servicios backend correspondientes utilizando Eureka.
3. **User Service (Puerto 8081):** Gestiona el registro y consulta de usuarios. Se conecta directamente a la base de datos MySQL.
4. **Auth Service (Puerto 8082):** Encargado de la seguridad. Se comunica internamente con `User Service` mediante OpenFeign para validar credenciales y emite Tokens JWT.
5. **Base de Datos (MySQL):** Repositorio centralizado (didáctico) para persistencia de datos.

## Documentación de Endpoints (Sprint 1)

### 1. Registro de Usuario
- **Ruta:** `POST /users/register` (Vía Gateway: `POST http://localhost:8080/users/register`)
- **Descripción:** Crea un nuevo usuario en la billetera y encripta su contraseña.
- **Body:** `{ "firstName": "...", "lastName": "...", "email": "...", "password": "...", "phone": "..." }`
- **Respuestas:** `201 Created` (Éxito) / `400 Bad Request` (Email duplicado o datos inválidos).

### 2. Iniciar Sesión (Login)
- **Ruta:** `POST /auth/login` (Vía Gateway: `POST http://localhost:8080/auth/login`)
- **Descripción:** Valida credenciales y devuelve un Token JWT para uso en el resto de servicios.
- **Body:** `{ "email": "...", "password": "..." }`
- **Respuestas:** `200 OK` (Devuelve token JWT) / `400 Bad Request` (Credenciales inválidas).