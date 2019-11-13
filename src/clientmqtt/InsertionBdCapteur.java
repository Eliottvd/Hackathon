/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmqtt;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
/**
 *
 * @author Eliott
 */
public class InsertionBdCapteur{
    
    private double valeur;
    private java.util.Date date;
    private java.sql.Date datesql;
    private java.sql.Timestamp ts;
 
    private Connection con;
    
    public Connection getConnection(){return con;}
    
    public InsertionBdCapteur (double v,String d,Connection c)
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);

        valeur = v;
        
        java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            date = sdf.parse(d);
        } catch (ParseException ex) {
            Logger.getLogger(InsertionBdCapteur.class.getName()).log(Level.SEVERE, null, ex);
        }
        ts = new java.sql.Timestamp(date.getTime());
        System.out.println(ts);
        con=c;
        
                
        
        
    
    }
    
    public ResultSet eQuery(String Query)
    {
        Statement stmt; 
        ResultSet rs = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery(Query);  
        } catch (SQLException ex) {
            Logger.getLogger(InsertionBdCapteur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public synchronized void addHumidity(int z)throws SQLException
    {
        ResultSet rs;
        int idHumidity = 1;
        String query;
        
        rs = eQuery("SELECT MAX(idhumidite) from `BdCapteur`.humidite;");
        rs.next();
        if((String)rs.getString(1) != null)
        {
            idHumidity = Integer.parseInt((String)rs.getString(1))+1;
        }
        
        if(z == 1)
            query ="INSERT INTO humidite (idhumidite,Valeur,TempsRecolte,ZoneHumidite)"+" VALUES(?,?,?,1)";
        else 
            query ="INSERT INTO humidite (idhumidite,Valeur,TempsRecolte,ZoneHumidite)"+" VALUES(?,?,?,2)";
        
        PreparedStatement stmt = getConnection().prepareStatement(query);
        
        stmt.setInt(1, idHumidity);
        stmt.setDouble(2, valeur);
        stmt.setTimestamp(3, ts);
        stmt.executeUpdate();
        
    }
    
    public synchronized void addTemperature(int z)throws SQLException
    {
        ResultSet rs;
        int idTemperature= 1;
        String query;
        
        rs = eQuery("SELECT MAX(idTemperature) from `BdCapteur`.temperature;");
        rs.next();
        if((String)rs.getString(1) != null)
        {
            idTemperature = Integer.parseInt((String)rs.getString(1))+1;
        }
        
        if(z == 1)
            query ="INSERT INTO temperature (idTemperature,ValeurTemperature,DateTemperature,ZoneTemperature)"+" VALUES(?,?,?,1)";
        else 
            query ="INSERT INTO temperature (idTemperature,ValeurTemperature,DateTemperature,ZoneTemperature)"+" VALUES(?,?,?,2)";
        
        PreparedStatement stmt = getConnection().prepareStatement(query);
        
        stmt.setInt(1, idTemperature);
        stmt.setDouble(2, valeur);
        stmt.setTimestamp(3, ts);
        stmt.executeUpdate();
        
    }
    
    public synchronized void addPressure(int z)throws SQLException
    {
        ResultSet rs;
        int idPression= 1;
        String query;
        
        rs = eQuery("SELECT MAX(idPression) from `BdCapteur`.pression;");
        rs.next();
        if((String)rs.getString(1) != null)
        {
            idPression = Integer.parseInt((String)rs.getString(1))+1;
        }
        
        if(z == 1)
            query ="INSERT INTO pression (idPression,ValeurPression,DatePression,ZonePression)"+" VALUES(?,?,?,1)";
        else 
            query ="INSERT INTO pression (idPression,ValeurPression,DatePression,ZonePression)"+" VALUES(?,?,?,2)";
        
        PreparedStatement stmt = getConnection().prepareStatement(query);
        
        stmt.setInt(1, idPression);
        stmt.setDouble(2, valeur);
        stmt.setTimestamp(3, ts);
        stmt.executeUpdate();
        
    }
}

    