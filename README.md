# Sistema de Pruebas Basado en la Taxonomía de Bloom

Este trabajo es un sistema de pruebas hecho en Java con Swing, que sirve para crear y revisar exámenes basados en la taxonomía de Bloom. La idea era cargar preguntas desde un archivo, responderlas y ver cómo lo hice al final. Lo dividí en partes (backend para la lógica y frontend para la interfaz) y usé un sistema de notificaciones para que las partes se comuniquen, como me pidieron en la tarea.

---

# 1. Alcances del Sistema

- Aplicación en Java Swing que permite la carga, respuesta y revisión de pruebas estructuradas según la taxonomía de Bloom.
- Soporte para preguntas de tipo `MULTIPLE_CHOICE` y `TRUE_FALSE`.
- Interfaz con navegación entre preguntas y revisión de respuestas con estadísticas visuales.
- Estadísticas de aciertos por tipo de pregunta y por nivel de Bloom.
- Separación modular entre lógica de negocio (`backend`) e interfaz gráfica (`frontend`), usando un sistema de notificaciones.

---

# 2. Supuestos Considerados

- Las preguntas deben estar definidas en un archivo de texto con formato válido.
- Se aceptan únicamente preguntas tipo `MULTIPLE_CHOICE` o `TRUE_FALSE`.
- El índice de la respuesta correcta debe estar dentro del rango de opciones.
- El tiempo asociado a cada ítem debe ser un número positivo.
- Las respuestas del usuario se mantienen al navegar.
- No se implementa validación de repetición de ítems entre años, ya que no se requiere almacenamiento histórico.

---

# 3. Instrucciones para Compilación y Ejecución

## Compilación

Desde consola o terminal, ubicarse en el directorio raíz y ejecutar:

```bash
javac backend/*.java frontend/*.java
```

## Ejecución

```bash
java frontend.Main
```

Al iniciar, se abrirá un explorador de archivos para seleccionar el archivo con los ítems.

---

# 4. Carga de Ítems

El archivo se selecciona manualmente al iniciar la aplicación. Si tiene errores de formato, se notifica mediante mensaje en pantalla y no se carga.

---

# 5. Especificación del Formato de Ítems

Cada línea representa una pregunta con el siguiente formato:

```
pregunta|opción1;opción2;opción3|índice_correcto|tipo|nivel|tiempo
```

Ejemplo 1 - Selección múltiple:
```
¿Qué es un bucle?|Un bloque de código;Una variable;Una función|0|MULTIPLE_CHOICE|REMEMBER|30
```

Ejemplo 2 - Verdadero/Falso:
```
¿Java está orientado a objetos?|Verdadero;Falso|0|TRUE_FALSE|UNDERSTAND|15
```

## Campos esperados:

- **pregunta**: enunciado de la pregunta.
- **opciones**: separadas por `;`.
- **índice_correcto**: número de la opción correcta (comenzando desde 0).
- **tipo**: `MULTIPLE_CHOICE` o `TRUE_FALSE`.
- **nivel**: uno de los niveles válidos de la taxonomía (`REMEMBER`, `UNDERSTAND`, `APPLY`, `ANALYZE`, `EVALUATE`, `CREATE`).
- **tiempo**: valor numérico entero en segundos.

---

# 6. Estructura del Proyecto

**backend/**  
- `EventManager.java`: Comunicación entre lógica e interfaz.  
- `BloomLevel.java` y `ItemType.java`: Enumeraciones para los niveles y tipos.  
- `Item.java`: Clase con datos de cada pregunta.  
- `TestManager.java`: Lógica central de la prueba (lectura, respuesta, estadísticas).  

**frontend/**  
- `ItemPanel.java`: Panel que muestra cada ítem.  
- `SummaryPanel.java`: Panel con las estadísticas de resultados.  
- `MainWindow.java`: Estructura visual principal.  
- `Main.java`: Punto de entrada del programa.  

---

# 7. Resultados de Prueba

Se probó con el siguiente archivo `test.txt`:

```
¿Qué es un bucle?|Un bloque de código;Una variable;Una función|0|MULTIPLE_CHOICE|REMEMBER|30
¿Java está orientado a objetos?|Verdadero;Falso|0|TRUE_FALSE|UNDERSTAND|15
```

- Se cargaron 2 ítems correctamente.
- Tiempo estimado total: 45 segundos.
- Se puede navegar entre preguntas, enviar respuestas, y ver el resumen con colores y porcentajes.
- Si el archivo tiene errores o tipos no soportados, la carga es rechazada.

---

# 8. Conclusión

El sistema cumple con lo solicitado, incluyendo validación de formato, interfaz funcional, y estructura modular. Swing facilita la visualización y el manejo de los ítems, y la lógica backend asegura la consistencia de los datos. Las limitaciones fueron conscientes y están justificadas por el alcance definido en el enunciado.

