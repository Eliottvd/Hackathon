/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServeurPoolThreads;

import Logs.LogServeur;

/**
 *
 * @author alexa
 */
public class ThreadClient extends Thread
{
    private SourceTaches tachesAExecuter;
    private String nom;
    private ConsoleServeur guiApplication;
    
    private Runnable tacheEnCours;
    public ThreadClient(SourceTaches st, String n )
    {
        tachesAExecuter = st;
        nom = n;
        setName(nom);
        guiApplication = new LogServeur();
    }

    public void run()
    {
        while (!isInterrupted())
        {
            try
            {
                guiApplication.TraceEvenements("ThreadClient : Avant le get");
                tacheEnCours = tachesAExecuter.getTache();
            }
            catch (InterruptedException e)
            {
                guiApplication.TraceEvenements("ThreadClient : InterruptedException " + e.getMessage());
            }

            guiApplication.TraceEvenements("ThreadClient : Execution du run de tacheEnCours");
            tacheEnCours.run();
        }
    }
} 
