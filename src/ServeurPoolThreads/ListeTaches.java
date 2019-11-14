/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServeurPoolThreads;

import java.util.LinkedList;

/**
 *
 * @author alexa
 */
public class ListeTaches implements SourceTaches
{
    private LinkedList listeTaches;
    
    public ListeTaches()
    {
        listeTaches = new LinkedList();
    }

    @Override
    public synchronized Runnable getTache() throws InterruptedException
    {
        //System.out.println(" ListeTache> getTache avant wait");
        while (!existTaches()) wait();
        
        return (Runnable)listeTaches.remove();
    }
    
    @Override
    public synchronized boolean existTaches()
    {
        return !listeTaches.isEmpty();
    }
    
    @Override
    public synchronized void recordTache (Runnable r)
    {
        listeTaches.addLast(r);
        notify();
    }
}
