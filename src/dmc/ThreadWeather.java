/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmc;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierre
 */
public class ThreadWeather extends Thread {
    
    @Override
    public void run()
    {
        Weather weather = new Weather();
        
        /*System.out.println("Temperature : " + weather.getTemperature() + "°");
        System.out.println("Vent : " + weather.getWindSpeed() + "m/s");*/
        
        try{
            while(true)
            {
                FileWriter fis = new FileWriter("weather.csv", true);
                fis.append(weather.getTemperature() +";"+weather.getWindSpeed()+";"+weather.getHumidity()+"\n");
                weather = new Weather();
                fis.close();
                Thread.sleep(30000);
            }
        }
        catch(FileNotFoundException e)
        {
            
        } catch (IOException ex) {
            Logger.getLogger(DMC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
