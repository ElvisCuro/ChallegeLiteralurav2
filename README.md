
# ChallegeLiteralurav2

### Descripción 📚
Aplicación en **Java** con **Spring Boot** para gestionar libros y autores. Permite buscar libros desde una API externa, guardarlos en una base de datos, y realizar consultas personalizadas.

---

## Funcionalidades 🛠️
1. **Buscar libros por título** desde la API de Gutendex.
2. **Listar libros registrados**, mostrando título, autor y detalles.
3. **Listar autores registrados** con su información básica.
4. **Listar autores vivos en un año específico**.
5. **Filtrar libros por idioma** registrado (ej. `es` o `en`).

---

## Tecnologías Utilizadas 🖥️
- **Java 17** y **Spring Boot**
- **Base de datos H2** embebida
- **Gutendex API** para búsqueda de libros

---

## Instalación ⚙️
1. Clonar el repositorio:
   ```bash
   git clone <URL>
   cd nombre-del-proyecto
   ```
2. Ejecutar con Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acceso a la base de datos H2:
   - URL: `http://localhost:8080/h2-console`
   - Usuario: `sa`
   - Contraseña: *(vacío)*

---

## Uso 🚀
En el menú principal puedes:  
- Buscar libros  
- Listar libros y autores  
- Filtrar por idioma o autores vivos en un año

Ejemplo de consulta:  
```plaintext
📚 Libros en el idioma 'es':
=========================================
📖 Título: Cien años de soledad | Autor: Gabriel García Márquez
=========================================
```

---

## Licencia 📄
Proyecto bajo licencia **MIT**.

--- 
