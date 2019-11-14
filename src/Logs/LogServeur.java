/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logs;

import ServeurPoolThreads.ConsoleServeur;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Eliott
 */
public class LogServeur implements ConsoleServeur, Serializable{
    
    public final static String nomFichierLog = "ServeurLogRTI.txt";
    
    public String test = "Test";
    
    public void addLog(String ligneLog)
    {
        try {
            Date mtn = new Date();
            String maDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM,Locale.FRANCE).format(mtn);
            ligneLog = maDate.concat(" > " + ligneLog);
            FileWriter fw = new FileWriter(System.getProperty("user.dir") + System.getProperty("file.separator") + nomFichierLog, true);
            fw.write(ligneLog);
            fw.write(System.getProperty("line.separator"));
            fw.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LogServeur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public String getAllLog()
    {
        try{
            String fichierDeLog = new String();
            FileReader fr = new FileReader(System.getProperty("user.dir") + System.getProperty("file.separator") + nomFichierLog);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line=br.readLine())!=null)
            {
                //System.out.println("Lecture d'un log");
                fichierDeLog=fichierDeLog + line + System.getProperty("line.separator");
            }
            return fichierDeLog;
        } 
        catch (FileNotFoundException ex) 
        {
            java.util.logging.Logger.getLogger(LogServeur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return "Fichier pas trouvé";
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(LogServeur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return "Erreur d'entrée/sortie";
        } 
    }

    @Override
    public void TraceEvenements(String commentaire) {
        addLog(commentaire);
        System.out.println(commentaire);
    }
}
