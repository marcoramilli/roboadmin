/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.util.logging.*;

/**
 *
 * @author Luca
 * Classe con il thread per l'avvio di RoboAdmin
 */

public class RaThread extends Thread {

        public String [] args;

        public RaThread (String[]argo){
            super();
            args=argo;
        }

        public RaThread(String string, String[]argo) {
            super(string);
            args=argo;
        }

        @Override
        public void run(){
            try {
                org.apache.felix.main.Main.main(args);
            } catch (Exception ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

}