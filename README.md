# Restaurant Management Application

## Descripción General
Esta aplicación es un sistema de gestión de restaurantes desarrollado en Java utilizando Spring Boot. Proporciona la capacidad de administrar clientes, menús, platos y órdenes, con un diseño basado en buenas prácticas de programación y arquitecturas modernas.

---

## Características Principales

### 1. Entidades
- **Client**: Representa a los clientes con datos personales y su tipo (común o frecuente).
- **Menu**: Contiene los menús disponibles, cada uno asociado con una lista de platos.
- **Dish**: Representa los platos con detalles como nombre, descripción, precio y tipo (común o popular).
- **Order**: Registra las órdenes de los clientes, cada una con una lista de platos seleccionados.

### 2. Relaciones
- Un cliente puede realizar múltiples órdenes.
- Una órden debe contener al menos un plato.
- Los platos están asociados a un menú.
- Una relación muchos-a-muchos entre platos y órdenes.

### 3. Características Técnicas
- **API RESTful** para todas las entidades.
- Validación de datos con `Jakarta Validation`.
- Gestor de base de datos: MySQL.
- Hibernate para persistencia de datos.

---

## Instalación y Ejecución

### 1. Prerrequisitos
- **Java 17** o superior.
- **Gradle**.
- **MySQL**.

### 2. Configuración de la Base de Datos
Crea una base de datos llamada `restaurant_management`. Asegúrate de actualizar las credenciales de acceso en el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_management
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. Compilación y Ejecución
Ejecuta los siguientes comandos:

```bash
./gradlew clean build
./gradlew bootRun
```
La aplicación estará disponible en: [http://localhost:8080/api/v1](http://localhost:8080/api/v1)

---

## Endpoints Principales

### Client
- **GET** `/clients`: Obtiene todos los clientes.
- **POST** `/clients`: Crea un nuevo cliente.
- **GET** `/clients/{id}`: Obtiene un cliente por su ID.
- **PUT** `/clients/{id}`: Actualiza un cliente.
- **DELETE** `/clients/{id}`: Elimina un cliente.

### Menu
- **GET** `/menus`: Obtiene todos los menús.
- **POST** `/menus`: Crea un nuevo menú.
- **GET** `/menus/{id}`: Obtiene un menú por su ID.
- **PUT** `/menus/{id}`: Actualiza un menú.
- **DELETE** `/menus/{id}`: Elimina un menú.

### Dish
- **GET** `/dishes`: Obtiene todos los platos.
- **POST** `/dishes`: Crea un nuevo plato.
- **GET** `/dishes/{id}`: Obtiene un plato por su ID.
- **PUT** `/dishes/{id}`: Actualiza un plato.
- **DELETE** `/dishes/{id}`: Elimina un plato.

### Order
- **GET** `/orders`: Obtiene todas las órdenes.
- **POST** `/orders`: Crea una nueva órden.
- **GET** `/orders/{id}`: Obtiene una órden por su ID.
- **PUT** `/orders/{id}`: Actualiza una órden.
- **DELETE** `/orders/{id}`: Elimina una órden.

---

## Diagrama de Clases

El sistema cuenta con un diagrama UML que describe las relaciones entre las entidades:
- Asociaciones: `1..*`, `0..*` según los requisitos del negocio.
- Tipos de relaciones: composición y agregación.

---


## Autor
Proyecto desarrollado por Nahu Lemes.

