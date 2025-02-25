# SimuladorSIU
Simulador de registro universitario (SIU) en Java: gestión de materias, docentes y estudiantes utilizando estructuras de datos avanzadas (Tries y listas enlazadas), con análisis de complejidad y documentación detallada.

# Sistema SIU - Simulación de Registro de Materias y Docentes Universitario

## Descripción

Este proyecto simula un sistema de registro universitario (SIU) para la gestión de materias y docentes. Implementado en Java, utiliza estructuras de datos avanzadas (como Tries y listas enlazadas) para optimizar el almacenamiento y la búsqueda de información. El sistema permite:

- Inscribir estudiantes en materias.
- Asignar docentes con diferentes cargos a las materias.
- Consultar el plantel docente de una materia.
- Verificar si se excede el cupo de una materia.
- Cerrar materias y actualizar las inscripciones de los estudiantes.
- Consultar carreras y materias disponibles, así como la cantidad de materias en las que está inscrito un estudiante.

Además, el código cuenta con análisis de complejidad para cada operación.

## Características

- **Estructuras de Datos Avanzadas:** Uso de Tries para gestionar carreras y materias.
- **Inscripción Dinámica:** Actualización en tiempo real de la inscripción de estudiantes y asignación de docentes.
- **Control de Cupo:** Cálculo automático del cupo disponible en cada materia según el plantel docente.
- **Cierre de Materias:** Eliminación de una materia en todas las carreras y actualización de inscripciones de estudiantes.
- **Documentación Detallada:** Comentarios y análisis de complejidad en el código para facilitar su comprensión.

## Requisitos

- **Java JDK 8** o superior.
- Herramienta de compilación (puede utilizarse `javac` y `java` desde la línea de comandos o un IDE como IntelliJ IDEA, Eclipse, etc.).

Este README proporciona una visión general del proyecto, además de invitar a la colaboración. Puedes modificarlo según lo consideres necesario.
