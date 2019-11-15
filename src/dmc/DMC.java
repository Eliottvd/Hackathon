/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmc;

import ServeurPoolThreads.ListeTaches;
import ServeurPoolThreads.ServeurCompagnie;
import clientmqtt.DataReceiver;
import clientmqtt.DataSender;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Pierre
 */
public class DMC {

    
    /**
     * @param args the command line arguments
     */
    private static DbManagement dbcapt;
    DbManagement dbm;
    
    public static void main(String[] args) {
        DMC dm = new DMC();
        dm.main1();
    }
    
    public void main1()
    {
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
        
        /*DataReceiver dr = new DataReceiver(); 
        dr.main1(null); 
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(DMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataSender ds = new DataSender(); 
        ds.main1(null);*/
        
        try {
            dbcapt = new DbManagement();
            dbcapt.connection("bdcapteur", "user", "user");
            dbm = new DbManagement(); 
            dbm.connection("liege_airport", "user", "user");
            
            ThreadWeather tw = new ThreadWeather();
            tw.start();
            
          
            ResultSet rs = dbcapt.getHumidite();
            ArrayList<Double> jourList = new ArrayList<Double>();
            ArrayList<Double> valList = new ArrayList<Double>();
            
            while(rs.next())
            {
                jourList.add(Double.parseDouble((rs.getTimestamp(1).toString()).substring(8,10)));
                valList.add(rs.getDouble(2));
                System.out.println(Integer.parseInt((rs.getTimestamp(1).toString()).substring(8,10)));
            }
            RegularLinear calcul= new RegularLinear(jourList,valList);
            
            double poidHumidite=calcul.traitement();
            
            System.out.println("REGULAR LINEAR "+poidHumidite);
            
            
            ResultSet rsTemp=dbcapt.getTemperature();
            ArrayList<Double> jourListTemp = new ArrayList<Double>();
            ArrayList<Double> valListTemp = new ArrayList<Double>();
            while(rsTemp.next())
            {
                jourListTemp.add(Double.parseDouble((rsTemp.getTimestamp(1).toString()).substring(8,10)));
                valListTemp.add(rsTemp.getDouble(2));
                System.out.println(Integer.parseInt((rsTemp.getTimestamp(1).toString()).substring(8,10)));
            
            }
            RegularLinear calculTemp= new RegularLinear(jourListTemp,valListTemp);
            double poidTemps=calculTemp.traitement();
            
            ResultSet rsPression=dbcapt.getPression();
            ArrayList<Double> jourListPression = new ArrayList<Double>();
            ArrayList<Double> valListPression= new ArrayList<Double>();
            
             while(rsPression.next())
            {
                jourListPression.add(Double.parseDouble((rsPression.getTimestamp(1).toString()).substring(8,10)));
                valListPression.add(rsPression.getDouble(2));
                System.out.println(Integer.parseInt((rsPression.getTimestamp(1).toString()).substring(8,10)));
                
            }
             RegularLinear calculPression= new RegularLinear(jourListPression,valListPression);
             
            double poidPression= calculPression.traitement();
            
            
              dbm.getAircraft();
               ArrayList<Double> jourListArrivee = new ArrayList<Double>(); 
               for(int i=0;i<10;i++)
               {
                   jourListArrivee.add(i+1.0);
               }
               
               
            ArrayList<Double> weights = new ArrayList<Double>();
            
            // Bip boop calculs
            
            weights.add(0.12); // Temp
            weights.add(0.2); // Hum
            weights.add(0.08); // Pression
            weights.add(0.25); // Atterrissage
            weights.add(0.35); // Degommage
                                    
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(DMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
