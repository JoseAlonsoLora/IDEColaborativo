/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesApoyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.negocio.Archivo;

/**
 *
 * @author Alonso Lora
 * @author Raymundo Pérez
 */
public class HiloCompilador extends Thread{
    private final StringBuilder resultadoCompilacion;
    private final ProcessBuilder procesoCompilar;
    
    /**
     * Constructor sobrecargado
     * @param procesoCompilar El proceso que va ejecutará el hilo
     */
    public HiloCompilador(ProcessBuilder procesoCompilar){
        this.procesoCompilar = procesoCompilar;
        resultadoCompilacion = new StringBuilder();
    }
    
    /**
     * Regresa el resultado de la compilación
     * @return Resultado de la compilación
     */
    public StringBuilder getResultadoCompilacion() {
        return resultadoCompilacion;
    }
    
    /**
     * Tarea que va ha realizar el hilo
     */
    @Override
    public void run() {
                try {
                    Process process = procesoCompilar.start();
                    InputStream out = process.getInputStream();
                    String auxiliar;
                    try (BufferedReader lector = new BufferedReader(new InputStreamReader(out))) {
                        while ((auxiliar = lector.readLine()) != null) {
                            resultadoCompilacion.append(auxiliar).append("\n");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    process.destroy();
                } catch (IOException e) {
                     Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        
}
