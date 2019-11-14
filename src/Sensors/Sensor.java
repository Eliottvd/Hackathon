/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors;

import ServeurPoolThreads.ConsoleServeur;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tout le monde sauf Eliott
 */
public class Sensor implements Serializable {
    
    private String date;
    private int value;
    private int zone;
    private String type;
    
    public Sensor(String d, int v, int z, String t)
    {
        date = d;
        value = v;
        zone = z;
        type = t;
    }
    
    public Runnable createRunnable (final Socket s)
    {   
        return new Runnable() {
             @Override
             public void run() {
                 
                 switch(type)
                 {
                    case "Humidity"    :    try { 
                                                Humidity h = new Humidity(date, value, zone);
                                                FileWriter fw = new FileWriter("Humidity.csv", true);
                                                fw.append(String.valueOf(h.getValue()) +";"+ h.getDate() +";"+  String.valueOf(h.getZone())+"\n"); 
                                                fw.close(); 
                                            }
                                            catch(IOException e) {
                                                System.out.println("IOException Humidity" + e.getMessage());
                                            }
                                            break;
                    case "Temperature"  :   try { 
                                                Temperature h = new Temperature(date, value, zone);
                                                FileWriter fw = new FileWriter("Temperature.csv", true);
                                                fw.append(String.valueOf(h.getValue()) +";"+  h.getDate() +";"+  String.valueOf(h.getZone())+"\n" ); 
                                                fw.close(); 
                                            }
                                            catch(IOException e) {
                                                System.out.println("IOException Temperature" + e.getMessage());
                                            }
                                            break;
                    case "Pressure"     :   try { 
                                                Pressure h = new Pressure(date, value, zone);
                                                FileWriter fw = new FileWriter("Pressure.csv", true);
                                                fw.append(String.valueOf(h.getValue()) +";"+  h.getDate() +";"+  String.valueOf(h.getZone())+"\n"); 
                                                fw.close(); 
                                            }
                                            catch(IOException e) {
                                                System.out.println("IOException Pressure" + e.getMessage());
                                            }
                                            break;
                 }  
            };
        };
    }
}
