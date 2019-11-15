/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServeurPoolThreads;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Eliott
 */
public class RTIP2 {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        
        int portServ = 50000;
        try {
            FileInputStream in = new FileInputStream("data.properties");
            Properties data = new Properties();
            data.load(in);
            
            portServ = Integer.parseInt((String) data.getProperty("portServ"));
        } catch (FileNotFoundException ex) {
            
            System.out.println("Launcher : FileNotFoundException " + ex.getMessage());
        }
        catch(IOException ex)
        {
            System.out.println("Launcher : IOEXception " + ex.getMessage());
        }
        
        ListeTaches lt = new ListeTaches();
        
        ServeurCompagnie sc = new ServeurCompagnie(portServ, lt);
        Thread t = new Thread(sc);
        t.start();
        
        

    }
    
}
