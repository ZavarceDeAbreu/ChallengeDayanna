# ChallengeDayanna

# Proyecto de Gestión de Provincias y Localidades

## Descripción

Este proyecto es una aplicación web que permite gestionar provincias y localidades.

# Requisitos Mínimos

## Sistema Operativo

* Windows 10/11
* Linux (Ubuntu 20.04 o superior)
* macOS (Big Sur o superior)

## Base de Datos

* PostgreSQL 16 o superior

## Lenguaje de Programación

* Java 17 o superior (Java 21 recomendado)

## Entorno de Desarrollo

## Instalación

1. Clonar el repositorio: `git clone https://github.com/tu-usuario/proyecto.git`
2. Instalar las dependencias: `mvn clean install` (si estás utilizando Maven) o `gradle build` (si estás utilizando
   Gradle)
3. Configurar la base de datos:
    * Crear una base de datos en Postgres llamada `provincias_localidades`
    * Agregar las credenciales de la base de datos en el archivo `application.properties` (si estás utilizando Spring
      Boot)
4. Ejecutar la aplicación: `mvn spring-boot:run`

## Estructura del Proyecto

* `src/main/java`: Contiene el código fuente de la aplicación
    + `com.eldar.dayanna`: Paquete principal de la aplicación
        - `DayannaZavarceApplication.java`: Clase principal de la aplicación
        - `controllers`: Paquete que contiene los controladores de la aplicación
        - `services`: Paquete que contiene los servicios de la aplicación
          -`model` : Paquete que contiene el modelo y DTO de la aplicacion
        - `Excepciones` : Manejador de excepciones personalizado
        - `Utilidades`

# Patrones de Diseño Implementados

## Patrón de Diseño MVC (Model-View-Controller)

* **Model**: Los modelos de la aplicación (Locality, Province, etc.) representan los datos y la lógica de negocio.
* **View**: Los controladores (LocalityController, ProvinceController, etc.) manejan las solicitudes y devuelven
  respuestas al usuario.
* **Controller**: Los servicios (ILocalityService, IProvinceService, etc.) contienen la lógica de negocio y se encargan
  de interactuar con los modelos y las vistas.

## Patrón de Diseño Repository

* Los repositorios (LocalityRepo, ProvinceRepo, etc.) se encargan de interactuar con la base de datos y devuelven los
  datos a los servicios.

## Patrón de Diseño Service

* Los servicios (ILocalityService, IProvinceService, etc.) se encargan de interactuar con los modelos y los repositorios
  para realizar operaciones de negocio.

## Patrón de Diseño DTO (Data Transfer Object)

* Los DTO (Locality, Province, etc.) se utilizan para transferir datos entre los servicios y los controladores.

## Patrón de Diseño Interceptor

* El interceptor (ExecutionTimeInterceptor) se encarga de medir el tiempo de ejecución de las solicitudes y devolver los
  resultados.

## Patrón de Diseño Singleton de Spring

* Los beans de Spring se crean como singleton por defecto, lo que significa que solo se crea una instancia de cada bean
  en la aplicación.
* Los servicios (ILocalityService, IProvinceService, etc.) se crean como singleton para garantizar que solo se cree una
  instancia de cada servicio en la aplicación.

# Decisiones Tomadas durante el Desarrollo

Durante el desarrollo de la aplicación, tomé varias decisiones importantes para garantizar que se cumplieran los
objetivos y se entregara la aplicación en el tiempo estimado.

## No Implementación del Patrón TDD

* Debido a las limitaciones de tiempo y la priorización de las funcionalidades urgentes e importantes, decidí no
  implementar el patrón de desarrollo de pruebas unitarias (TDD).
* Aunque el TDD es una práctica recomendada para garantizar la calidad del código y reducir errores, consideré que no
  era factible en este proyecto debido a las restricciones de tiempo.

## Priorización de Funcionalidades

* Prioricé las funcionalidades más urgentes e importantes, como la gestión de provincias y localidades, para garantizar
  que la aplicación cumpliera con los requisitos mínimos.
* Dejé de lado algunas funcionalidades menos importantes, como la implementación de pruebas unitarias, para enfocarme en
  la entrega de la aplicación en el tiempo estimado.

## Uso de Patrones de Diseño Establecidos

* Utilicé patrones de diseño establecidos, como el patrón MVC, Repository y Service, para garantizar que la aplicación
  fuera escalable, mantenible y fácil de entender.
* Estos patrones de diseño me permitieron desarrollar la aplicación de manera rápida y eficiente, sin comprometer la
  calidad del código.

## Uso de Spring Framework

* Utilicé el framework Spring para simplificar el desarrollo de la aplicación y aprovechar sus características, como la
  inyección de dependencias y la gestión de transacciones.
* Spring es un framework maduro y ampliamente utilizado, lo que me garantizó que la aplicación fuera estable y
  escalable.

# API Documentation

## Excel Export API Documentation

* Este conjunto de endpoints permite interactuar con archivos Excel para gestionar provincias y localidades. Las
  operaciones incluyen la descarga de archivos ZIP, la creación, actualización, eliminación y búsqueda de provincias y
  localidades.

### Método: GET

### URL: `/excel/download`

Este endpoint genera y descarga un archivo ZIP que contiene la información de provincias y localidades en formato Excel.

- **Ejemplo de Request**:

  ```http
    GET /excel/download

### Respuesta Exitosa:

- Código: 200 OK
- Contenido: Archivo ZIP descargable.

## Obtener Provincias

### Método: GET

### URL: `/excel/provinces`

Este endpoint permite obtener una lista de provincias filtradas por ciertos parámetros desde un archivo Excel.

### Parámetros de Consulta:

- `filePath` (Obligatorio): Ruta del archivo Excel.
- `id` (Opcional): ID de la provincia.
- `name` (Opcional): Nombre de la provincia.
- `code31662` (Opcional): Código de la provincia.

- **Ejemplo de Request**:

    ```http
        GET /excel/provinces?filePath=ruta/al/archivo.xlsx&name=NombreProvincia

### Respuesta Exitosa:

- Código: 200 OK
- Contenido: JSON con la lista de provincias filtradas.

## Crear Provincia

### Método: POST

### URL: `/excel/province/create`

Este endpoint permite crear una nueva provincia en un archivo Excel.

### Cuerpo de la Solicitud (JSON):

```json
{
  "name": "NombreProvincia",
  "code31662": "Cód31662",
  "filePath": "ruta/al/archivo.xlsx"
}
```

### Respuesta Exitosa:

- Código: 201 Create
- Contenido: Ninguno.

#### **4. Actualizar una Provincia**

## Actualizar Provincia

### Método: PATCH

### URL: `/excel/province/{id}`

Este endpoint permite actualizar una provincia existente en un archivo Excel.

### Parámetros:

- `id` (Obligatorio): ID de la provincia a actualizar.

### Cuerpo de la Solicitud (JSON):

```json
{
  "name": "NuevoNombreProvincia",
  "code31662": "NuevoCód31662",
  "filePath": "ruta/al/archivo.xlsx"
}
```

### Respuesta Exitosa:

- Código: 200 Ok
- Contenido: Ninguno.

#### **5. Eliminar una Provincia**

## Eliminar Provincia

### Método: DELETE

### URL: `/excel/province/{id}`

Este endpoint permite eliminar una provincia de un archivo Excel.

### Parámetros:

- `id` (Obligatorio): ID de la provincia a eliminar.
- `filePath` (Obligatorio): Ruta del archivo Excel.

- **Ejemplo de Request**:

    ```http
    DELETE /excel/province/123?filePath=ruta/al/archivo.xlsx

### Respuesta Exitosa:

- Código: 204 No Content
- Contenido: Ninguno.

## Obtener Localidades

### Método: GET

### URL: `/excel/locality`

Este endpoint permite obtener una lista de localidades filtradas por ciertos parámetros desde un archivo Excel.

### Parámetros de Consulta:

- `filePath` (Obligatorio): Ruta del archivo Excel.
- `name` (Opcional): Nombre de la localidad.
- `id` (Opcional): ID de la localidad.
- `postalCode` (Opcional): Código postal de la localidad.

- **Ejemplo de Request**:

  ```http
    GET /excel/locality?filePath=ruta/al/archivo.xlsx&name=NombreLocalidad

### Respuesta Exitosa:

- Código: 200 OK
- Contenido: JSON con la lista de localidades filtradas.

## Crear Localidad

### Método: POST

### URL: `/excel/locality/create`

Este endpoint permite crear una nueva localidad en un archivo Excel.

### Cuerpo de la Solicitud (JSON):

```json
{
  "name": "NombreLocalidad",
  "postalCode": "156",
  "provinceId": 1,
  "filePath": "ruta/al/archivo.xlsx"
}
```

### Respuesta Exitosa:

- Código: 202 Create
- Contenido: Ninguno.

#### **8. Actualizar una Localidad**

## Actualizar Localidad

### Método: PATCH

### URL: `/excel/locality/{id}`

Este endpoint permite actualizar una localidad existente en un archivo Excel.

### Parámetros:

- `id` (Obligatorio): ID de la localidad a actualizar.

### Cuerpo de la Solicitud (JSON):

```json
{
  "name": "NombreLocalidad",
  "postalCode": "156",
  "provinceId": 1,
  "filePath": "ruta/al/archivo.xlsx"
}
```

### Respuesta Exitosa:

- Código: 200 OK
- Contenido: Ninguno.

#### **9. Eliminar una Localidad**

## Eliminar Localidad

### Método: DELETE

### URL: `/excel/locality/{id}`

Este endpoint permite eliminar una localidad de un archivo Excel.

### Parámetros:

- `id` (Obligatorio): ID de la localidad a eliminar.
- `filePath` (Obligatorio): Ruta del archivo Excel.

- **Ejemplo de Request**:

  ```http
  DELETE /excel/locality/456?filePath=ruta/al/archivo.xlsx

# HybridController API Documentation

Este controlador ofrece endpoints para consultar provincias y localidades, con la capacidad de extraer datos tanto de un
archivo Excel como de una base de datos. Los tiempos de ejecución de las consultas también son devueltos.

## Endpoints

### **GET /hybrid/provinces**

**Descripción**: Obtiene una lista de provincias basada en los parámetros proporcionados, realizando consultas tanto en
un archivo Excel como en una base de datos. Devuelve los resultados de ambos junto con el tiempo de ejecución de cada
consulta.

- **URL**: `/hybrid/provinces`

- **Método HTTP**: `GET`

- **Parámetros**:
    - `name` (opcional): `String` - Nombre de la provincia.
    - `id` (opcional): `Integer` - ID de la provincia.
    - `code31662` (opcional): `String` - Código 3166-2 de la provincia.
    - `filePath` (requerido): `String` - Ruta al archivo Excel.

- **Ejemplo de Request**:

  ```http
  GET /hybrid/provinces?name=BuenosAires&filePath=/path/to/file.xlsx

### Respuesta Exitosa:

- Código: 200 OK
- Contenido: Json:

```Json
{
  "excelProvinces": [
    {
      "id": 12,
      "name": "San Luis",
      "code": "AR-D"
    }
  ],
  "dbProvinces": [
    {
      "id": 12,
      "name": "San Luis",
      "code": "AR-D"
    }
  ],
  "excelExecutionTime": 14,
  "dbExecutionTime": 6337
}
```

## GET /hybrid/localities

Obtiene una lista de localidades basada en los parámetros proporcionados, realizando consultas tanto en un archivo Excel
como en una base de datos. Devuelve los resultados de ambos junto con el tiempo de ejecución de cada consulta.

### Parámetros

* `name` (opcional): String - Nombre de la localidad.
* `id` (opcional): Integer - ID de la localidad.
* `postalCode` (opcional): String - Código postal de la localidad.
* `filePath` (requerido): String - Ruta al archivo Excel.

- **Ejemplo de Request**:

    ```http
    GET /hybrid/localities?name=CABA&filePath=/path/to/file.xlsx

### Respuesta Exitosa:

- Código: 200 OK
- Contenido: Json:

```json
{
  "excelCities": [
    {
      "id": 97,
      "name": "CABA",
      "postalCode": 2322,
      "Province": "Santa Fe"
    },
    {
      "id": 4323,
      "name": "CABA",
      "postalCode": 9105,
      "Province": "Chubut"
    },
    {
      "id": 14624,
      "name": "CABA",
      "postalCode": 3703,
      "Province": "Chaco"
    },
    {
      "id": 21802,
      "name": "CABA",
      "postalCode": 9420,
      "Province": "Tierra del Fuego"
    }
  ],
  "dbCities": [
    {
      "id": 97,
      "name": "CABA",
      "postalCode": 2322,
      "province": {
        "id": 13,
        "name": "Santa Fe",
        "code": "AR-S"
      }
    },
    {
      "id": 4323,
      "name": "CABA",
      "postalCode": 9105,
      "province": {
        "id": 17,
        "name": "Chubut",
        "code": "AR-U"
      }
    },
    {
      "id": 14624,
      "name": "CABA",
      "postalCode": 3703,
      "province": {
        "id": 16,
        "name": "Chaco",
        "code": "AR-H"
      }
    },
    {
      "id": 21802,
      "name": "CABA",
      "postalCode": 9420,
      "province": {
        "id": 24,
        "name": "Tierra del Fuego",
        "code": "AR-V"
      }
    }
  ],
  "excelExecutionTime": 1105,
  "dbExecutionTime": 47
}
```

# API de Localidades

Este controlador permite gestionar localidades mediante operaciones CRUD (Crear, Leer, Actualizar, Eliminar). A
continuación, se describen los endpoints disponibles.

### 1. Obtener Localidades

**Descripción:** Este endpoint permite obtener una lista de localidades filtradas por nombre, ID o código.

- **URL:** `/localities/get/`
- **Método:** `GET`
- **Parámetros de Consulta (Query Params):**
    - `name` (opcional): Nombre de la localidad.
    - `id` (opcional): ID de la localidad.
    - `code` (opcional): Código de la localidad.
    - **Respuesta Exitosa:**
        - **Código:** `200 OK`
        - **Cuerpo:**
        ```json
           {
              "id": 4,
              "name": "COLONIA INDEPENDENCIA",
              "postalCode": 3066,
              "province": {
                 "id": 13,
                 "name": "Santa Fe",
                " code": "AR-S"
               }
          }
      ```

### 2. Crear Localidad

**Descripción:** Este endpoint permite crear una nueva localidad.

- **URL:** `/localities/create`
- **Método:** `POST`
- **Cuerpo de la Solicitud:**
  ```json
    {
        "name": "COLONIA INDEPENDENCIA",
        "postalCode": 3066,
        "provinceId": 15,
        "filePath" : null
    }
  ```

### Respuesta Exitosa:

- Código: 201 Create
- Contenido: Ninguno.

### 3 Actualizar Localidad

**Descripción:** Este endpoint permite actualizar la información de una localidad existente.

- **URL:** `/localities/{id}`
- **Método:** `PATCH`
- **Parámetros de Ruta:**
    - `id`: ID de la localidad que se desea actualizar.

- **Cuerpo de la Solicitud:**

  ```json
  {
    "name": "Nombre",
    "postalCode": 3066,
    "provinceId": 15,
    "filePath": null
  }
  ```

### Eliminar Localidad

**Descripción:** Este endpoint permite eliminar una localidad existente.

- **URL:** `/localities/{id}`
- **Método:** `DELETE`
- **Parámetros de Ruta:**
    - `id`: ID de la localidad que se desea eliminar.

- **Respuesta Exitosa:**

    - **Código:** `204 No Content`

### Controller de Provincias

**Descripción:** Este endpoint permite obtener una lista de provincias filtradas por nombre, ID o código.

- **URL:** `/provinces/get`
- **Método:** `GET`
- **Parámetros de Consulta (Query Params):**
    - `name` (opcional): Nombre de la provincia.
    - `id` (opcional): ID de la provincia.
    - `code` (opcional): Código de la provincia.

- **Respuesta Exitosa:**
    - **Código:** `200 OK`
    - **Cuerpo:**
      ```json
      {
        "id": 1,
        "name": "Ciudad Autónoma de Buenos Aires (CABA)",
        "code": "AR-C"
      }
      ```

### Crear Provincia

**Descripción:** Este endpoint permite crear una nueva provincia.

- **URL:** `/provinces/create`
- **Método:** `POST`
- **Cuerpo de la Solicitud:**
  ```json
  {
      "name": "Nueva Provincia",
      "code": "1234",
  }

- **Respuesta Exitosa:**
    - **Código:** `201 Created`
    - **Headers:** `Location: /provinces/{id}` (donde `{id}` es el ID de la provincia creada)

### Actualizar Provincia

**Descripción:** Este endpoint permite actualizar la información de una provincia existente.

- **URL:** `/provinces/{id}`
- **Método:** `PATCH`
- **Parámetros de Ruta:**
    - `id`: ID de la provincia que se desea actualizar.

- **Cuerpo de la Solicitud:**
  ```json
  {
      "name": "Provincia Actualizada",
      "code": "126",
  }
- **Respuesta Exitosa:**
    - **Código:** `200 OK`
    - **Cuerpo:**
      ```json
      {
          "id": 1,
          "name": "Provincia Actualizada",
          "code": "126"
      }
      ```

### Eliminar Provincia

**Descripción:** Este endpoint permite eliminar una provincia existente.

- **URL:** `/provinces/{id}`
- **Método:** `DELETE`
- **Parámetros de Ruta:**
    - `id`: ID de la provincia que se desea eliminar.

- **Respuesta Exitosa:**
    - **Código:** `204 No Content`






