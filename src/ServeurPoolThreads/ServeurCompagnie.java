/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServeurPoolThreads;
import Sensors.Sensor;
import java.net.*;
import java.io.*;

/**
 *
 * @author alexa
 */
public class ServeurCompagnie extends Thread{

    private int port;
    private SourceTaches tachesAExecuter;
    private ServerSocket SSocket = null;
    
    
    public ServeurCompagnie(int p, SourceTaches st)
    {
       port = p; tachesAExecuter = st;
       this.setName("Server (" + getName() +")");
    }

    public void run()
    {
       try
       {
           SSocket = new ServerSocket(port);
       }
       catch (IOException e)
       {
           System.out.println("ServeurDMC : IOException Error on listening port " + e.getMessage());
           System.exit(1);
       }
       // Démarrage du pool de threads
       for (int i=0; i<3; i++) // 3 devrait être constante ou une propriété du fichier de config
       {
            ThreadClient thr = new ThreadClient (tachesAExecuter, "Thread n°" +
            String.valueOf(i));
            thr.start();
       }

        // Mise en attente du serveur
        Socket CSocket = null;
        while (!isInterrupted())
        {
            try
            {
                CSocket = SSocket.accept();
            }
            catch (IOException e)
            {
                System.out.println("ServerDMC : Error on accept " + e.getMessage());
                System.exit(1);
            }

            ObjectInputStream ois=null;
            Sensor s = null;
            try
            {
                ois = new ObjectInputStream(CSocket.getInputStream());
                s = (Sensor)ois.readObject();
                
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("ServerDMC : ClassNotFoundException " 
                        + e.getMessage());
            }
            catch (IOException e)
            {
                System.out.println("ServerDMC : IOException " + e.getMessage());
            }

            Runnable travail;
            travail = s.createRunnable(CSocket);
            if (travail != null)
            {
                tachesAExecuter.recordTache(travail);
            }

        }
    }
} 
