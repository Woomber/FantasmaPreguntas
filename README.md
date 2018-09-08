# Fantasma Preguntas
Juego que atiende a una solicitud de pregunta, respondiendo usando la respuesta proveída de manera oculta por el usuario.

El juego lee el cuadro de texto cada vez que hay un cambio.

Si detecta que el texto empieza con un punto '.' ocultará lo ingresado por el usuario, mostrando en su lugar el texto de la solicitud, hasta encontrar otro punto '.', donde volverá a mostrar lo ingresado por el usuario.

El texto comprendido entre ambos puntos se considera la 'respuesta' que dará al terminar la solicitud de pregunta. 

Simplemente hay que completar la solicitud, terminada por dos puntos ':' y después de éstos colocar cualquier texto (la pregunta a realizar), y presionar Enter. 
 
## Ejemplo:
* Se escribe: ".la respuesta.TO DE SOLICITUD: Pregunta."
* Se muestra: "ESTE ES EL TEXTO DE SOLICITUD: Pregunta."
* El juego responde: "la respuesta"

## Nota
Si el punto '.' inicial no se encuentra al inicio del texto, no hay punto final, no hay dos puntos, no hay pregunta, el texto restante de la solicitud no coincide, o no hay respuesta, el juego responde negativamente.
