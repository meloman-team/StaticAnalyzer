/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import module.ModuleWaitAndNotify;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilya
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ModuleWaitAndNotify n = new ModuleWaitAndNotify();
        try {
            n.main();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
