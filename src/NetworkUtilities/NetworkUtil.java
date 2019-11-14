/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkUtilities;

import Sensors.Sensor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 *
 * @author alexa
 */
public class NetworkUtil {
  
    public NetworkUtil()
    {
        
    }
   
    public Socket Init(int p)
    {
        Socket cliSock;
        String ipAddress = null;
        try {
            FileInputStream in = new FileInputStream("data.properties");
            Properties data = new Properties();
            data.load(in);
            ipAddress = (String)data.get("ip");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        catch(IOException ex)
        {
           System.out.println(ex.getMessage());
        }
        
        // Connection to Server

        cliSock = null;
        int port = p;

        try 
        {
            cliSock = new Socket(ipAddress, port);
        }
        catch (UnknownHostException e)
        {
            System.err.println("Error ! Host not found ["+  e +"]");
        }
        catch (IOException e)
        {
            System.err.println("Error ! Host not found ["+  e +"]");
        }
        
        return cliSock;
    }
    
    
    public void sendSensor(Socket cliSock, Sensor s)
    {
        ObjectOutputStream oos;
        
        // Sending of sensor

        try 
        {
            oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(s);
            oos.flush();
        } 
        catch (IOException e)
        {
            System.err.println("Network error ["+  e.getMessage() +"]");
        }
    }
    
    
}

