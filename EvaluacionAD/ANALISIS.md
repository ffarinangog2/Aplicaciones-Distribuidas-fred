# ANALISIS DEL PROTOTIPO REGISTRODISTRIBUIDO

## 1. Ante una interrupción de comunicación entre dos nodos, ¿qué propiedad del teorema CAP privilegia su implementación y por qué?

La implementación privilegia principalmente la Disponibilidad (Availability). Cuando un nodo deja de responder, los demás continúan procesando operaciones y manteniendo el servicio activo. Aunque puede existir una pérdida temporal de sincronización entre nodos durante una partición de red, el sistema sigue aceptando solicitudes y operando con los nodos disponibles. Esto se observa cuando un nodo es marcado como caído mediante heartbeats y el resto continúa funcionando normalmente.


## 2. ¿Qué falacias de la computación distribuida tuvo que considerar al delimitar los mensajes y al definir los tiempos de espera de los latidos?

Durante el desarrollo se consideraron varias falacias de la computación distribuida:

* La red no es confiable: un nodo puede dejar de responder inesperadamente.
* La latencia no es cero: los mensajes requieren tiempo para llegar.
* La topología puede cambiar: un nodo puede caer y volver a estar activo.
* El transporte tiene costo: cada heartbeat genera tráfico adicional.
* La red puede congestionarse: por ello se utilizan intervalos de tiempo entre latidos.

Para evitar problemas de delimitación se utilizó readLine() y println(), garantizando que cada mensaje se procese de forma independiente.



## 3. ¿Qué tipos de transparencia ofrece o no ofrece la solución?

### Transparencia de acceso

Sí se ofrece. Los clientes utilizan el mismo mecanismo de comunicación TCP independientemente del nodo.

### Transparencia de ubicación

Parcialmente. Los nodos utilizan puertos específicos conocidos por el sistema.

### Transparencia de fallos

Parcialmente. El sistema detecta caídas mediante heartbeats y continúa operando con los nodos restantes.

### Transparencia de replicación

Sí se ofrece. N1 replica operaciones hacia N2 y N3 de forma automática.



## 4. Proponga un SLA de disponibilidad para este sistema y calcule el tiempo de inactividad anual admisible.

Se propone un SLA de disponibilidad del 99.9%.

Tiempo total anual:

365 × 24 × 60 = 525600 minutos.

Disponibilidad:

99.9%

Tiempo de inactividad permitido:

525600 × 0.001 = 525.6 minutos.

Equivale aproximadamente a:

8.76 horas por año.



## 5. Si reemplazara el algoritmo Bully por un consenso tipo Raft, ¿qué ganaría y qué costo introduciría?

### Ventajas

* Mayor tolerancia a fallos.
* Consenso distribuido más robusto.
* Replicación consistente del estado.
* Mejor comportamiento ante múltiples fallos simultáneos.

### Costos

* Mayor complejidad de implementación.
* Más mensajes intercambiados entre nodos.
* Mayor consumo de recursos.
* Mayor dificultad de mantenimiento y depuración.

Por estas razones, para un prototipo académico se utilizó Bully simplificado, mientras que en sistemas de producción sería recomendable emplear Raft.



## Conclusión

El prototipo desarrollado permitió implementar los principales conceptos de sistemas distribuidos estudiados en la Unidad 1: comunicación mediante TCP, sincronización con relojes lógicos de Lamport, replicación de operaciones, autenticación mediante token, detección de fallos mediante heartbeats y coordinación mediante una elección automática de líder. La solución demuestra el funcionamiento básico de un sistema distribuido tolerante a fallos y proporciona una base sólida para futuras mejoras orientadas a consenso distribuido y alta disponibilidad.
