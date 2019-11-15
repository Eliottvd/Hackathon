/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServeurPoolThreads;


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
    }

    public void run()
    {
        while (!isInterrupted())
        {
            try
            {
                tacheEnCours = tachesAExecuter.getTache();
            }
            catch (InterruptedException e)
            {
            }

            tacheEnCours.run();
        }
    }
} 
