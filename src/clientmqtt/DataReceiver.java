/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmqtt;
import static clientmqtt.DataSender.BROKER_URL;
import com.sun.jdi.connect.spi.Connection;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author Eliott
 */
public class DataReceiver implements MqttCallback {
    
    static final String BROKER_URL = "tcp://localhost:1883" ;
    MqttClient myClient;
    MqttConnectOptions connOpt;
    String topSub;
    private java.sql.Connection con;
    
    private int _formatHeure;
    private int _formatDate;
    private Locale _pays;
    
    public static void main(String[] args) {
        DataReceiver m = new DataReceiver();
        m.main1(args);
    }
    
    public void main1(String[] args) {
        // TODO code application logic here
        this.Connexion();
        int min = 1;
        int max = 10;
        Random r = new Random();
        
        try {
                int subQoS = 0;
                myClient.subscribe("Zone1/Temperature", subQoS);
                myClient.subscribe("Zone1/Humidite", subQoS);
                myClient.subscribe("Zone1/Pression", subQoS);
                
                myClient.subscribe("Zone2/Temperature", subQoS);
                myClient.subscribe("Zone2/Humidite", subQoS);
                myClient.subscribe("Zone2/Pression", subQoS);
                
                System.out.println("SUBSCRIBED");
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public int Connexion()
    {
        
        String clientID = MqttClient.generateClientId();
        connOpt = new MqttConnectOptions();

        connOpt.setCleanSession(true);
        connOpt.setUserName("root");
        connOpt.setPassword("toor".toCharArray());
        connOpt.setAutomaticReconnect(true);

        // Connect to Broker
        try {
            myClient = new MqttClient(BROKER_URL, clientID);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
                e.printStackTrace();
                System.exit(-1);
        }

        System.out.println("Connected to " + BROKER_URL);
                
        System.out.println("Essai de connexion JDBC");
        Class leDriver;
        try {    
            
            leDriver = Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver JDBC-mysql chargé");
            
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BdCapteur?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root","root");
            System.out.println("Connexion à la BDD bd_capteur réalisée");
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 1;
    }
    
    @Override
    public void connectionLost(Throwable thrwbl) {
        
    }

    @Override 
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println("Message arrived : " + mm + " " + string);
        
        if(string.equals( "Zone1/Temperature"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addTemperature(1);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
            //System.out.println("ENDTHIS");
        }
        
        if(string.equals( "Zone1/Humidite"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addHumidity(1);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
            //System.out.println("ENDTHIS");
        }
        
        if(string.equals( "Zone2/Temperature"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                //System.out.println("COUCOU2");
                inserer.addTemperature(2);
                //System.out.println("ENDTHIS");
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
            //System.out.println("ENDTHIS");
        }
        
        if(string.equals( "Zone2/Humidite"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                //System.out.println("COUCOU2");
                inserer.addHumidity(2);
                //System.out.println("ENDTHIS");
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
            //System.out.println("ENDTHIS");
        }
        
        if(string.equals( "Zone1/Pression"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addPressure(1);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
            //System.out.println("ENDTHIS");
        }
        
        if(string.equals( "Zone2/Pression"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addPressure(2);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
            //System.out.println("ENDTHIS");
        }
    }

    public ResultSet eQuery(String Query)
    {
        Statement stmt; 
        ResultSet rs = null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs=stmt.executeQuery(Query);  
        } catch (SQLException ex) {
            Logger.getLogger(DataReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        
    }
    
}
