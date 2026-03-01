Proyecto de 2º DAM. Son dos aplicaciones en Java que se comunican entre sí: un servidor REST hecho con Spring Boot y un cliente por consola que lo consume.

La idea es que el servidor gestiona el inventario de un supermercado (categorías y productos) y el cliente te deja hacer el CRUD desde la consola.

Qué hay en el repositorio:
├── servidor/   → Spring Boot + MySQL
├── cliente/    → Java puro, sin Spring
└── docs/       → Javadoc generado con mvn javadoc:javadoc

Para ejecutarlo:
Necesitas tener Java 17 y MySQL. Primero crea la base de datos:

sqlCREATE DATABASE pericos_market;

Después arranca el servidor (las tablas las crea él solo):

bashjava -jar servidor/target/servidor-1.0.0.jar

Y en otra terminal el cliente:

bashjava -jar cliente/target/cliente-1.0.0-jar-with-dependencies.jar
El servidor tiene que estar corriendo antes de arrancar el cliente, si no te da error de conexión en cada operación.

Endpoints:

El servidor escucha en el puerto 12345.
GET     /api/categorias
GET     /api/categorias/{id}
POST    /api/categorias
PUT     /api/categorias/{id}
DELETE  /api/categorias/{id}

GET     /api/productos
GET     /api/productos/{id}
POST    /api/productos
PUT     /api/productos/{id}
DELETE  /api/productos/{id}
Si mandas un producto con precio negativo el servidor devuelve 400.
Tests
Los tests usan H2 en memoria así que no necesitas MySQL para ejecutarlos:
bashcd servidor
mvn test
Salen 9 tests, todos pasan.

Compilar desde el código fuente:
bashcd servidor && mvn clean package
cd cliente  && mvn clean package


Antonio Almenara Begue — 2º DAM
