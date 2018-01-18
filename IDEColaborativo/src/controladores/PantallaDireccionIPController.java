/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import idecolaborativo.IDEColaborativo;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Alonso Lora
 * @author Raymundo Pérez
 */
public class PantallaDireccionIPController implements Initializable {

    @FXML
    private JFXButton botonGuardar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private Label etiquetaIP;
    @FXML
    private JFXTextField campoTextoIP1;
    @FXML
    private JFXTextField campoTextoIP2;
    @FXML
    private JFXTextField campoTextoIP3;
    @FXML
    private JFXTextField campoTextoIP4;

    private PantallaPrincipalController controlador;

    private Stage stagePantallaDireccionIP;

    private ResourceBundle recurso;
    @FXML
    private Label etiquetaPuerto;
    @FXML
    private JFXTextField campoTextoPuerto;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.recurso = rb;// TODO
        configurarIdioma();
    }

    /**
     * Configura el idioma de todas etiquetas de la pantalla
     */
    public void configurarIdioma() {
        etiquetaIP.setText(recurso.getString("etDireccionIP"));
        botonCancelar.setText(recurso.getString("btCancelar"));
        botonGuardar.setText(recurso.getString("btGuardar"));
        etiquetaPuerto.setText(recurso.getString("etPuertoServidor"));
    }

    /**
     * Da valor al controlador para poder manipular componentes de la pantalla
     * principal
     *
     * @param controlador Instancia del controlador
     */
    public void setControlador(PantallaPrincipalController controlador) {
        this.controlador = controlador;
    }

    /**
     * Dar valor al stage para poder manipular la pantalla direccion Ip
     *
     * @param stagePantallaDireccionIP Stage de la instancia actual
     */
    public void setStagePantallaDireccionIP(Stage stagePantallaDireccionIP) {
        this.stagePantallaDireccionIP = stagePantallaDireccionIP;
        this.stagePantallaDireccionIP.setOnCloseRequest((WindowEvent event) -> {
            controlador.hacerVisiblePantallaprincipal();
        });
    }

    /**
     * Guarda la IP de servidor
     *
     * @param event Clic del usuario
     */
    @FXML
    private void guardarIP(ActionEvent event) {
        StringBuilder direccionIP = new StringBuilder();
        if (campoTextoIP1.getText().isEmpty() || campoTextoIP2.getText().isEmpty()
                || campoTextoIP3.getText().isEmpty() || campoTextoIP4.getText().isEmpty() || campoTextoPuerto.getText().isEmpty()) {
            IDEColaborativo.mensajeAlert(recurso.getString("mensajeCamposVacios"));
        } else {
            if (validarIP() && validarEntradaPuerto()) {
                direccionIP.append(campoTextoIP1.getText()).append(".").append(campoTextoIP2.getText())
                        .append(".").append(campoTextoIP3.getText()).append(".").append(campoTextoIP4.getText());
                controlador.setDireccionIP(direccionIP.toString());
                controlador.setPuerto(campoTextoPuerto.getText());
                controlador.hacerVisiblePantallaprincipal();
                stagePantallaDireccionIP.close();
            } else {
                IDEColaborativo.mensajeAlert(recurso.getString("mensajeDatosInvalidos"));
            }

        }
    }

    /**
     * Evento para salir de la pantalla dirección IP
     *
     * @param event Clic del usuario
     */
    @FXML
    private void cancelar(ActionEvent event) {
        controlador.hacerVisiblePantallaprincipal();
        stagePantallaDireccionIP.close();
    }

    /**
     * Valida que solo pueda ingresar números
     *
     * @param event Presión de una tecla
     */
    @FXML
    private void validarEntradas(KeyEvent event) {
        int limiteCaracteres = 3;
        char digito = event.getCharacter().charAt(0);
        if ((digito < 48 || digito > 57) || ((JFXTextField) event.getSource()).getText().length() >= limiteCaracteres) {
            event.consume();
        }
    }

    public boolean validarEntradaPuerto() {
        boolean esPuertoValido = false;
        try {
        Integer puerto = Integer.valueOf(campoTextoPuerto.getText());
        esPuertoValido = (puerto>1023 && puerto<65535);
        } catch (NumberFormatException ex) {
            Logger.getLogger(PantallaDireccionIPController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esPuertoValido;
    }

    public boolean validarIP() {
        boolean esIPValida = false;
        try {
            Integer primerOcteto = Integer.valueOf(campoTextoIP1.getText());
            Integer segundoOcteto = Integer.valueOf(campoTextoIP2.getText());
            Integer tercerOcteto = Integer.valueOf(campoTextoIP3.getText());
            Integer cuartoOcteto = Integer.valueOf(campoTextoIP4.getText());
            esIPValida = (primerOcteto >= 0 && primerOcteto <= 255) && (segundoOcteto >= 0 && segundoOcteto <= 255)
                    && (tercerOcteto >= 0 && tercerOcteto <= 255) && (cuartoOcteto >= 0 && cuartoOcteto <= 255);
        } catch (NumberFormatException ex) {
            Logger.getLogger(PantallaDireccionIPController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return esIPValida;
    }

    @FXML
    private void validarPuerto(KeyEvent event) {
        char digito = event.getCharacter().charAt(0);
        if (digito < 48 || digito > 57) {
            event.consume();
        }
    }

}
