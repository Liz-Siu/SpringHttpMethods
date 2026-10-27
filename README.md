# Spring Boot JPA: GET, PUT y POST

## Paso 1: crea la base de datos en PostgreSQL
Ejecuta este comando desde la terminal:

```bash
psql -U postgres
```

Luego crea el usuario y la base de datos:

```sql
CREATE USER chochos WITH PASSWORD 'chochos';
CREATE DATABASE chochos OWNER chochos;
```

> Nota: en un ejemplo o documentación, usa credenciales de prueba o placeholders para evitar compartir información sensible.

## Paso 2: actualiza el modelo
Crea o modifica el modelo `Producto` en:

```text
demo/src/main/java/com/example/demo/model/Producto.java
```

```java
package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "producto")
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String lote;
    private BigDecimal precioUnitario;
    private LocalDate fechaCaducidad;
}
```

> Nota: eliminamos los getters y setters, pero agregamos dos anotaciones: `@NoArgsConstructor` y `@AllArgsConstructor`.
>
> Revisa la anotación `@GeneratedValue` y analiza qué significa lo que está dentro de los paréntesis.

## Paso 3: agrega o edita el método en el controlador
Modifica o agrega este método en el controlador:

```text
demo/src/main/java/com/example/demo/producto/ProductoController.java
```

El controlador usa el repositorio `ProductoRepo`, ubicado en:

```text
demo/src/main/java/com/example/demo/repo/ProductoRepo.java
```

```java
@GetMapping("/producto/{id}")
public Optional<Producto> getById(@PathVariable Long id) {
    return repo.findById(id);
}
```

## Paso 4: inserta un nuevo registro en otra terminal
Ejecuta este comando en una terminal distinta:

```bash
curl -s -X POST localhost:8080/producto \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Creatina","descripcion":"Creatina de 500 g HoliXlab","lote":"12345","precioUnitario":500.5,"fechaCaducidad":"2026-05-04"}'
```

## Paso 5: consulta el registro
Puedes consultar el registro usando el navegador:

```text
<url_de_la_aplicacion>/producto/<id_del_producto>
```

O desde la terminal:

```bash
curl -s -X GET localhost:8080/producto/1
```

## Paso 6: agrega o edita el siguiente método
En el mismo controlador, agrega esta operación:

```java
@PutMapping("/producto/{id}")
public Producto putProduct(@PathVariable Long id, @RequestBody Producto producto) {
    producto.setId(id);
    return repo.save(producto);
}
```

## Paso 7: actualiza un registro desde otra terminal
Ejecuta este comando:

```bash
curl -s -X PUT localhost:8080/producto/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Proteína de fresa","descripcion":"Proteína de suero de leche 25 g, 2.2 kg","lote":"12345","precioUnitario":500.5,"fechaCaducidad":"2027-06-07"}'
```

Repite el paso 5 para verificar que los cambios se hayan realizado correctamente.

## Paso 8: agrega o edita el siguiente método
En el controlador:

```java
@DeleteMapping("/producto/{id}")
public String deleteProduct(@PathVariable("id") Long id) {
    repo.deleteById(id);
    return "{\"success\":true}";
}
```

## Paso 9: valida la eliminación
Consulta el registro con la API o usa la terminal:

```bash
curl -s -X DELETE localhost:8080/producto/1
```

Luego revisa la lista de productos para confirmar que el registro se haya eliminado correctamente.

> Si se cierra la ventana del codespace mientras Spring está ejecutándose, puedes terminar el proceso con estos comandos:
>
> Buscar PID:
>
> ```bash
> fuser 8080/tcp
> ```
>
> Matar el proceso con el PID devuelto por el comando anterior:
>
> ```bash
> kill <PID>
> ```
>
> Si necesitas iniciar PostgreSQL porque el contenedor se reinició, usa estos comandos:
>
> ```bash
> sudo su
> service postgresql start
> ```
