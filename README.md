# Sistema de Pruebas Basado en la Taxonomía de Bloom

Este proyecto implementa un sistema en Java Swing para administrar pruebas basadas en la taxonomía de Bloom, permitiendo cargar ítems desde un archivo, aplicar pruebas con preguntas de selección múltiple o verdadero/falso, y mostrar estadísticas desglosadas por tipo de ítem y nivel de Bloom. El sistema está modularizado en paquetes backend y frontend, utilizando un modelo de notificación-suscripción para la comunicación.

---

# 1. Requisitos del Sistema

- Aplicación en Java Swing que permite la carga, respuesta y revisión de pruebas estructuradas según la taxonomía de Bloom.
- Soporte para preguntas de tipo `MULTIPLE_CHOICE` y `TRUE_FALSE`.
- Un entorno de desarrollo (e.g., IntelliJ IDEA, Eclipse) o compilador javac para compilar y ejecutar el código.
---

# 2. Supuestos

- Las preguntas deben estar definidas en un archivo de texto.
- Se aceptan únicamente preguntas tipo `MULTIPLE_CHOICE` o `TRUE_FALSE`.
- Las respuestas del usuario se mantienen al navegar.

---

# 3. Instrucciones para Ejecución

## Compilación

Desde terminal, ejecutar:

```bash
javac backend/*.java frontend/*.java
```

## Ejecución

```bash
java Main
```

## Cargar archivo de ítems
```bash
Crear un archivo de texto (e.g., test.txt).
Al iniciar el programa, usar el botón "Cargar archivo de prueba" para seleccionar el archivo.
```

## Uso del sistema
```bash
Navegar por las preguntas con los botones "Volver atrás" y "Siguiente".
Enviar respuestas al final con "Enviar respuestas".
Revisar estadísticas en el resumen o navegar por las respuestas individuales con "Revisar respuestas".
```

---

# 4. Formato del Archivo de Ítems

El archivo de ítems debe ser un archivo de texto plano con una pregunta por línea, usando el siguiente formato:

`pregunta|opción1;opción2;...|índice_correcto|tipo|nivel_bloom|tiempo_estimado`

- **pregunta**: Texto de la pregunta.
- **opción1;opción2;...**: Opciones separadas por punto y coma. Para preguntas `TRUE_FALSE`, debe haber exactamente dos opciones.
- **índice_correcto**: Índice (base 0) de la opción correcta.
- **tipo**: `MULTIPLE_CHOICE` o `TRUE_FALSE`. No se soporta `ESSAY`.
- **nivel_bloom**: Uno de `REMEMBER`, `UNDERSTAND`, `APPLY`, `ANALYZE`, `EVALUATE`, `CREATE`.
- **tiempo_estimado**: Tiempo en segundos (entero no negativo) para resolver la pregunta.

**Ejemplo**:
```
¿Qué es un bucle?|Un bloque de código;Una variable;Una función|0|MULTIPLE_CHOICE|REMEMBER|30
¿Java es orientado a objetos?|Verdadero;Falso|0|TRUE_FALSE|UNDERSTAND|15
¿Cuál es la salida de `System.out.println(2 + 3 * 4);`?|14;20;24;18|0|MULTIPLE_CHOICE|APPLY|30
¿Qué palabra clave se utiliza para heredar una clase en Java?|extends;implements;import;instanceof|0|MULTIPLE_CHOICE|REMEMBER|25
¿Qué estructura permite ejecutar diferentes bloques de código según el valor de una variable?|if;switch;while;for|1|MULTIPLE_CHOICE|UNDERSTAND|30
¿Qué método se llama al iniciar un programa en Java?|main;start;run;init|0|MULTIPLE_CHOICE|REMEMBER|20
¿Un `ArrayList` en Java puede cambiar de tamaño dinámicamente?|Verdadero;Falso|0|TRUE_FALSE|UNDERSTAND|15
¿El operador `==` compara referencias en objetos en Java?|Verdadero;Falso|0|TRUE_FALSE|ANALYZE|20
¿Una clase abstracta puede tener métodos implementados?|Verdadero;Falso|0|TRUE_FALSE|EVALUATE|20
¿El recolector de basura en Java debe ser llamado manualmente por el programador?|Verdadero;Falso|1|TRUE_FALSE|REMEMBER|15
```

## Campos esperados:

- **pregunta**: enunciado de la pregunta.
- **opciones**: separadas por `;`.
- **índice_correcto**: número de la opción correcta (comenzando desde 0).
- **tipo**: `MULTIPLE_CHOICE` o `TRUE_FALSE`.
- **nivel**: uno de los niveles válidos de la taxonomía (`REMEMBER`, `UNDERSTAND`, `APPLY`, `ANALYZE`, `EVALUATE`, `CREATE`).
- **tiempo**: valor numérico entero en segundos.

---

# 5. Estructura del Proyecto

**backend/**  
- `EventManager.java`: Gestiona eventos de notificación-suscripción (Comunicación entre lógica e interfaz).
- - `BloomLevel.java`: Enumera los niveles de la taxonomía de Bloom.
- `ItemType.java`: Enumera los tipos de ítems.
- `Item.java`: Modelo de un ítem con pregunta, opciones, respuesta correcta, tipo, nivel y tiempo estimado.
- `TestManager.java`: Gestiona la lógica de carga, respuestas y estadísticas.

**frontend/**  
- `ItemPanel.java`: Panel para mostrar un ítem y sus opciones.
- `SummaryPanel.java`: Muestra estadísticas por tipo y nivel.
- `MainWindow.java`: Ventana principal con la lógica de navegación.
- `Main.java`: Punto de entrada. 

---

## Notas
- El sistema usa Swing para la interfaz gráfica, asegurando compatibilidad con los requisitos.
- Los errores en el archivo de ítems generan mensajes claros para el usuario.
- El código está modularizado y sigue el patrón observer para la comunicación entre frontend y backend.

