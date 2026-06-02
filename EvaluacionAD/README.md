# EvaluacinSocketTCP

## Descripción

RegistroDistribuido es un prototipo de sistema distribuido implementado en Java utilizando sockets TCP. El sistema está compuesto por tres nodos réplica (N1, N2 y N3) que reciben operaciones de clientes, mantienen relojes lógicos de Lamport, intercambian heartbeats para detectar fallos y realizan una elección automática de coordinador mediante una versión simplificada del algoritmo Bully.

## Tecnologías utilizadas

* Java 21
* Maven
* IntelliJ IDEA
* TCP Sockets

## Estructura del proyecto

cliente

* ClienteTCP

modelo

* EventoLog
* InfoNodo
* RelojLamport

nodo

* NodoServidor
* NodoCliente
* NodoN1
* NodoN2
* NodoN3
* HeartbeatManager
* BullyManager

## Ejecución

### Levantar los nodos

Ejecutar en ventanas independientes:

1. NodoN1
2. NodoN2
3. NodoN3

Puertos utilizados:

* N1 → 5000
* N2 → 5001
* N3 → 5002

### Ejecutar cliente

Ejecutar:

ClienteTCP

El cliente enviará una operación autenticada mediante token al nodo seleccionado.

## Funcionalidades implementadas

### Comunicación distribuida

* Cliente ↔ Nodo mediante TCP.
* Nodo ↔ Nodo mediante TCP.

### Seguridad

* Validación mediante token.
* Rechazo de solicitudes con token inválido.

### Relojes lógicos de Lamport

* Incremento en eventos locales.
* Actualización mediante max(local, recibido) + 1.

### Replicación

* N1 replica operaciones hacia N2 y N3.

### Tolerancia a fallos

* Heartbeats periódicos.
* Detección automática de nodos caídos.
* Continuidad de operación con los nodos restantes.

### Coordinación

* Elección automática de coordinador usando Bully simplificado.
* Reelección cuando un nodo deja de responder.

## Prueba de fallo

1. Ejecutar N1, N2 y N3.
2. Detener N3.
3. Verificar detección de caída.
4. Verificar elección de nuevo coordinador.
5. Reiniciar N3.
6. Verificar recuperación automática.

## Autor

Freddy Vladimir Farinango Guandinango
Ingeniería de Software – UTEQ
