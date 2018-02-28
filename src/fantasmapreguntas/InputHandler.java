package fantasmapreguntas;

/**
 * Input Handler.
 *
 * Maneja las entradas obtenidas por el cuadro de texto utilizando expresiones
 * regulares para buscar formato válido en el texto.
 *
 * @author Yael Arturo Chavoya Andalón 14300094
 */
public class InputHandler {

    // Referencia al texto ingresado por el usuario
    private StringBuilder input;

    // Solicitud: El texto que debe anteceder la pregunta
    public static final String PROMPT = "Profe por favor conteste";

    // Respuesta Negativa: El texto que se muestra si la entrada no es válida
    public static final String NEGATIVE = "Lo vemos a la hora de clase";

    /**
     * Crea un nuevo InputHandler con referencia a 'input'.
     *
     * @param input El texto que se comparará al llamar a las funciones
     */
    public InputHandler(StringBuilder input) {
        this.input = input;
    }

    /**
     * Consulta si el texto es una solicitud válida en el formato:
     *
     * .respuesta.solicitud restante: pregunta
     *
     * @return Verdadero si es válida.
     */
    public boolean isValidQuery() {
        String regex = "^(\\..*\\.)(.*):.+";

        // Si no contiene respuesta o pregunta, no es válida
        if (!input.toString().matches(regex)) {
            return false;
        }

        /*
        Verificar si se completó el resto de la solicitud.
        */
        
        // Regex selector 1: La respuesta, incluyendo los puntos '.'
        String respuesta = input.toString().replaceAll(regex, "$1");
        
        // Regex selector 2: El resto de la soliciutd hasta los dos puntos ':'
        String resto = input.toString().replaceAll(regex, "$2");

        // Cadena donde se convertirá la entrada
        String query = "";

        // Reemplazar la respuesta por la parte de la solicitud que la oculta
        for (int i = 0; i < respuesta.length(); i++) {
            try {
                // Reemplazar la respuesta por la solicitud por caracter
                query += "" + PROMPT.charAt(i);
            } catch (StringIndexOutOfBoundsException ex) {
                // Si la respuesta es más grande, completar con la respuesta
                query += "" + respuesta.charAt(i);
            }
        }
        
        // Agregar el resto de la solicitud y comparar con la solicitud real
        query += resto;     
        return query.equals(PROMPT);
    }

    /**
     * Reemplaza la respuesta ingresada por el usuario por la parte de la 
     * solicitud correspondiente en número de caracteres.
     * 
     * @return La cadena con la respuesta reemplazada
     */
    public String replaceString() {
        String newString = "";
        
        for (int i = 0; i < input.length(); i++) {        
            try {
                // Reemplazar la respuesta por la solicitud por caracter
                newString += "" + PROMPT.charAt(i);
            } catch (StringIndexOutOfBoundsException ex) {
                // Si la respuesta es más grande, completar con la respuesta
                newString += "" + input.charAt(i);
            }
        }
        return newString;
    }

    /**
     * Usa expresiones regulares para buscar que la cadena ingresada empiece
     * por un punto '.' que indica el inicio de la respuesta.
     * 
     * @return Verdadero si contiene un punto.
     */
    public boolean findAnswerStart() {
        String regex = "^\\..*";
        return input.toString().matches(regex);
    }

    /**
     * Usa expresiones regulares para buscar que la cadena ingresada contenga
     * una respuesta delimitada por puntos '.' y al menos un caracter después.
     * 
     * @return La cadena con sólo la respuesta, null si no hay respuesta.
     */
    public String findAnswerEnd() {
        String regex = "^\\.(.*)\\..+";
        if (input.toString().matches(regex)) {
            return input.toString().replaceAll(regex, "$1");
        }
        return null;
    }

}
