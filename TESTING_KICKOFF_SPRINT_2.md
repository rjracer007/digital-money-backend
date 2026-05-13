# Estrategia de Pruebas (Testing Kickoff) - Sprint 2

## 1. Alcance de las Pruebas
Se realizarán pruebas exploratorias y manuales sobre los endpoints desarrollados en el Sprint 2 correspondientes al microservicio `Account Service`:
- Consulta de información de la cuenta (Saldo, CVU, Alias).
- Gestión de tarjetas (Creación y listado con enmascaramiento de seguridad).
- Motor transaccional (Depósitos/Fondeos y Transferencias).
- Consulta de historial de movimientos.
- Validación de seguridad (Filtro JWT) en todas las rutas.

## 2. Tipos de Pruebas a Ejecutar
- **Pruebas de Funcionalidad (Caja Negra):** Validar cálculos matemáticos exactos en saldos, generación correcta de CVU/Alias y respuestas HTTP correctas.
- **Pruebas de Seguridad:** Validar que los endpoints del `Account Service` rechacen peticiones sin un Token JWT válido y que el CVV de las tarjetas nunca sea expuesto en las respuestas.
- **Pruebas de Integración:** Validar la comunicación mediante OpenFeign entre `User Service` y `Account Service` para la creación automática de cuentas.

## 3. Evidencia de Testing Exploratorio (Registro de Ejecución)

| Fecha | Endpoint | Acción | Datos de Entrada | Resultado Esperado | Resultado Real | Estado |
|---|---|---|---|---|---|---|
| [Fecha] | GET /accounts/users/{userId} | Consultar cuenta | Header: Bearer Token válido | 200 OK + Datos (CVU, Alias, Saldo) | 200 OK + Datos de la cuenta | PASSED |
| [Fecha] | GET /accounts/users/{userId} | Seguridad en cuenta | Sin Token en Header | 403 Forbidden | 403 Forbidden | PASSED |
| [Fecha] | POST /accounts/{accountId}/cards | Asociar nueva tarjeta | JSON válido (Número, Nombre, Vencimiento, CVV) | 201 Created + Número enmascarado | 201 Created + Número enmascarado | PASSED |
| [Fecha] | GET /accounts/{accountId}/cards | Listar tarjetas | Header: Bearer Token válido | 200 OK + Lista de tarjetas sin CVV | 200 OK + Lista de tarjetas sin CVV | PASSED |
| [Fecha] | POST /.../transactions/deposit | Fondear cuenta | JSON con monto positivo (> 0) | 201 Created + Movimiento registrado | 201 Created + Saldo incrementado | PASSED |
| [Fecha] | POST /.../transactions/deposit | Fondeo inválido | JSON con monto negativo o 0 | 400 Bad Request | 400 Bad Request | PASSED |
| [Fecha] | POST /.../transactions/transfer | Transferencia exitosa | JSON (Monto válido, CVU destino existente) | 201 Created + Descuento en origen | 201 Created + Saldos actualizados | PASSED |
| [Fecha] | POST /.../transactions/transfer | Transferencia sin fondos | JSON (Monto > Saldo actual) | 400 Bad Request | 400 Bad Request | PASSED |
| [Fecha] | GET /.../transactions | Historial de cuenta | Header: Bearer Token válido | 200 OK + Lista descendente por fecha | 200 OK + Lista ordenada | PASSED |
