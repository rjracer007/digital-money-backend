# Estrategia de Pruebas (Testing Kickoff) - Sprint 1

## 1. Alcance de las Pruebas
Se realizarán pruebas exploratorias y manuales sobre los endpoints desarrollados en el Sprint 1:
- Registro de usuarios.
- Inicio de sesión (Login).

## 2. Tipos de Pruebas a Ejecutar
- **Pruebas de Funcionalidad (Caja Negra):** Validar que las respuestas HTTP y el estado de la base de datos coincidan con los requerimientos.
- **Pruebas de Seguridad (Autenticación):** Validar encriptación de contraseñas y correcta emisión de JWT.

## 3. Evidencia de Testing Exploratorio (Registro de Ejecución)
| Fecha | Endpoint | Acción | Datos de Entrada | Resultado Esperado | Resultado Real | Estado |
|---|---|---|---|---|---|---|
| [05/05/2026] | POST /users/register | Crear usuario nuevo | Datos válidos | 201 Created | 201 Created, tabla actualizada | PASSED |
| [05/05/2026] | POST /users/register | Email duplicado | Email ya existente | 400 Bad Request | 400 Bad Request, mensaje de error | PASSED |
| [05/05/2026] | POST /auth/login | Login exitoso | Credenciales válidas | 200 OK + Token JWT | 200 OK + Token JWT | PASSED |
| [05/05/2026] | POST /auth/login | Login fallido | Clave incorrecta | 400 Bad Request | 400 Bad Request | PASSED |