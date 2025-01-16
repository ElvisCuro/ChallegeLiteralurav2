
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

##Ejecucion

![Captura de pantalla 2025-01-16 020456](https://github.com/user-attachments/assets/9e8de1da-6554-4222-8661-6ddb5eb83045)

![Captura de pantalla 2025-01-16 020544](https://github.com/user-attachments/assets/1bad26f2-3e73-4300-b49e-efdaf6757ede)
![Captura de pantalla 2025-01-16 020608](https://github.com/user-attachments/assets/5930a7f9-64c4-4060-9e6a-ab89914fe633)
![Captura de pantalla 2025-01-16 020632](https://github.com/user-attachments/assets/2acf9e01-9292-44d2-bc28-fd156eb5edcb)![Captura de pantalla 2025-01-16 020659](https://github.com/user-attachments/assets/d3d91335-eae1-4a01-94b0-e15eb6dfff86)

