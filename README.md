# Información General:

El proyecto musicplataform gestiona la información relacionada con los artistas y canciones, y a los usuarios se les permitirá acceder a una suscripción para poder crear una lista de reproducción en la cual podrán agregar sus canciones favoritas. .

# Technologies

* Java
* Spring Boot
* MariaDB 

# Instalación

*Clona el repositorio en tu máquina local.

*Ejecuta el comando Maven clean para limpiar el proyecto.

	mvn clean
*Ejecuta el comando Maven install para compilar el proyecto y generar el archivo JAR.

	mvn install
*Crea la base de datos en MariaDB. Asegúrate de tener MariaDB instalado y en funcionamiento en tu máquina local.

	create database musica;
 
*Para poder acceder a las servicios se debe especificar el versionado en postman

  	X-VERSION = 1
   
*Se debe registrar en la aplicación con el servicio, para poder logerarse y generar un token:

   	http://localhost:8005/auth/register
    	http://localhost:8005/auth/login

*Y se debe copiar el token generado en el textbox de BearerToken, y ya se podrá acceder a los servicios de usuario con el rol USUARIO.

Nota: Los usuarios con el rol de ADMIN pueden acceder a todos los servicios


# Colaboración y Desarrollo

Universidad Nacional de Cajamarca

E.A.P "Ingeniería de Sistemas"

*Dobbertin Izquierdo, Andy Guillermo

*Gutiérrez Sánchez Ángel Abraham

*Ramos Abanto, Cleysi Yeraldi

*Villalobos Guerra, Junior Alexis

