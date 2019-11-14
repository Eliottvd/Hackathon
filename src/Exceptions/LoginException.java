/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author mrpar
 */

public class LoginException extends Exception {
    private LogException error; // 0=login invalide 1=mdp invalide 2=mot de passe discordant
    public enum LogException {INVALIDLOG, INVALIDPWD, MISMATCHPWD};
    
    public LoginException(LogException e)
    {
        error = e;
    }
    
    public LogException getException()
    {
        return error;
    }
    
    public void display()
    {
        String msg = new String();
        switch(error)
        {
            case INVALIDLOG: msg="Login incorrect";
                break;
            case INVALIDPWD: msg="Mot de passe incorrect";
                break;
            case MISMATCHPWD: msg="Les mots de passes sont différents";
                break;
        }
        
        JOptionPane.showMessageDialog(new JFrame(), msg);
    }
}
