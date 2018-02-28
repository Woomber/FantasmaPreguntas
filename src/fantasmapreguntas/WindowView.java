package fantasmapreguntas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Interfaz visual del programa.
 * 
 * @author Yael Arturo Chavoya Andalón 14300094
 */
public class WindowView extends JFrame {

    // Donde se guarda la entrada verdadera del usuario
    private StringBuilder realInput;
    
    // Determina si el TextField está siendo editado por el usuario
    private boolean isUserEdit = true;
    
    // Manejador de entrada
    private InputHandler inputHandler;
    
    // Usado para imprimir signos de admiración al tener solicitudes erróneas
    private int angerLevel;

    // Componentes Swing
    private JLabel instructionsLabel;
    private JTextField inputField;
    private JTextField replyField;

    /**
     * Constructor
     */
    public WindowView() {
        //Crea la interfaz
        buildWindow();
        buildContent();
        
        //Inicializa Variables
        realInput = new StringBuilder();
        inputHandler = new InputHandler(realInput);
        angerLevel = 0;

    }

    /**
     * Listener encargado de actuar cuando el usuario presione enter para
     * finalizar su solicitud en el TextField.
     */
    private final ActionListener finEscrituraListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Busca una respuesta en el texto
            String answer = inputHandler.findAnswerEnd();

            if (inputHandler.isValidQuery()) {
                /*
                Coloca la respuesta si la consulta es válida y reinicia
                el nivel de enojo
                */
                replyField.setText(answer);
                angerLevel = 0;
            } else {
                /*
                Coloca el texto de solicitud errónea e imprime signos de
                admiración '!' según el nivel de enojo
                */
                String anger = "";
                for (int i = 0; i < angerLevel; i++) {
                    anger += "!";
                }
                replyField.setText(InputHandler.NEGATIVE + anger);
                angerLevel++;
            }
        }
    };

    /**
     * Listener encargado de determinar cuando se ha insertado o eliminado.
     * texto del TextField.
     */
    private final DocumentListener changeListener = new DocumentListener() {
        
        /**
         * Método que actúa en las inserciones de texto.
         * 
         * @param e El documento editado
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            // Si la edición no la realizó el usuario, no hacer nada.
            if (!isUserEdit) return;
            
            /*
            Runnable que se ejecuta al desbloquear el TextField, para poder
            editarlo
            */
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    /*
                    Colocar el último carácter añadido a la cadena con la
                    entrada real
                    */
                    String in = inputField.getText();
                    realInput.append(in.charAt(in.length() - 1));
                    
                    // Editar el TextField para ocultar la respuesta
                    isUserEdit = false;
                    
                    // Si hay respuesta y no ha sido terminada, ocultarla
                    if (inputHandler.findAnswerStart()
                            && inputHandler.findAnswerEnd() == null) {
                            inputField.setText(inputHandler.replaceString());
                    }
                    
                    // Permitir al usuario volver a editar
                    isUserEdit = true;
                }
            });
        }

        /**
         * Método que actúa en las eliminaciones de texto.
         * 
         * @param e El documento editado
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            // Si no es edición del usuario o no hay texto, no hacer nada
            int length = realInput.length();
            if (!isUserEdit || length <= 0) return;
            
            // Eliminar el último carácter de la cadena real
            realInput.deleteCharAt(length - 1);
        }

         // No se utiliza
        @Override
        public void changedUpdate(DocumentEvent e) {
           
        }
    };

    /**
     * Método encargado de crear la ventana.
     */
    private void buildWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 160);
        this.setLayout(null);
        this.setTitle("El profesor embrujado");
    }

    /**
     * Método encargado de crear los controles.
     */
    private void buildContent() {
        // El TextField donde el usuario ingresa su solicitud
        inputField = new JTextField();
        inputField.setSize(360, 20);
        inputField.setLocation(10, 40);
        inputField.addActionListener(finEscrituraListener);
        inputField.getDocument().addDocumentListener(changeListener);

        // El TextField donde aparece la respuesta
        replyField = new JTextField();
        replyField.setSize(360, 20);
        replyField.setLocation(10, 70);
        replyField.setEditable(false);

        // Las instrucciones de uso
        instructionsLabel = new JLabel(
                "Pregunte así => " + InputHandler.PROMPT + ": su pregunta.");
        instructionsLabel.setLocation(10, 10);
        instructionsLabel.setSize(360, 20);

        this.add(inputField);
        this.add(replyField);
        this.add(instructionsLabel);
    }

}
