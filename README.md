# Producto-2: Alquila Tus Vehiculos
Proyecto back-end desarrollado con Spring Boot para la gestión de alquiler de vehículos. En este Producto 2, la aplicación ha evolucionado para incluir vistas dinámicas con Thymeleaf, persistencia de datos y una base de datos MySQL, todo orquestado mediante Docker Compose.

## Requisitos Previos
* **Docker** y **Docker Compose** instalados en tu sistema.
* Puerto `8080` libre para la aplicación web.
* Puerto `3307` libre para la conexión externa a la base de datos.

## Despliegue del Proyecto

El proyecto utiliza un entorno multicontenedor definido en el archivo `docker-compose.yml`.

### Levantar el entorno completo (App + Base de datos)

Desde la raíz del proyecto, ejecuta el siguiente comando para construir la imagen de Java (usando un Dockerfile multi-etapa con Maven y Java 21) y levantar ambos contenedores:

```bash
docker-compose up -d --build
```
Para levantar solamente la base de datos añadir **bd**, para levantar solamente la aplicaicón añadir **app**

La aplicación quedará disponible en:

```
http://localhost:8080
```

### Actualización de la aplicación 

Si se hacen modificaciones en el código fuente de Spring Boot o en las plantillas HTML, reconstruir el contenedor de la aplicación:

```
docker-compose up -d --build app
```

---
## Comandos útiles

Ver contenedores en ejecución
```
docker ps
```
Ver logs del contenedor
```
docker logs alquila-vehiculos-container
```
Detener el contenedor
```
docker stop alquila-vehiculos-container
```
Eliminar el contenedor
```
docker rm alquila-vehiculos-container
```
