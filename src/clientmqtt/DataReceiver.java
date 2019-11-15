/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmqtt;
import Sensors.Sensor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
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
    String myDate;
    Socket cliSock;
    int zone;
    String type;
    NetworkUtilities.NetworkUtil nw;
    
    private int _formatHeure;
    private int _formatDate;
    private Locale _pays;
    
    private Properties hashtable;
    
    private int portServ;
    
    public Properties getHashtable(){return hashtable;}
    private void setHashtable(Properties h){hashtable=h;}
    
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
        
        
        // Connexion to server
        
        try {
            FileInputStream in = new FileInputStream("data.properties");
            Properties data = new Properties();
            data.load(in);
            portServ = Integer.parseInt((String) data.get("portServ"));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found " + ex.getMessage());
        }
        catch(IOException e)
        {
            System.out.println("IO EXCEPTION " + e.getMessage());
        }
        
        // Socket Creation
        
        nw = new NetworkUtilities.NetworkUtil();
        
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
                zone = 1;
                type = "Temperature";
                
                Date maintenant = new Date();
                myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addTemperature(1);
                
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
        }
        
        if(string.equals( "Zone1/Humidite"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                zone = 1;
                type = "Humidity";
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addHumidity(1);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
        }
        
        if(string.equals( "Zone2/Temperature"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                zone = 2;
                type = "Temperature";
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addTemperature(2);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
        }
        
        if(string.equals( "Zone2/Humidite"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                zone = 2;
                type = "Humidity";
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addHumidity(2);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
        }
        
        if(string.equals( "Zone1/Pression"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                zone = 1;
                type = "Pressure";
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addPressure(1);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
        }
        
        if(string.equals( "Zone2/Pression"))
        {            
             try
             {
                _formatDate = DateFormat.SHORT;
                _formatHeure = DateFormat.MEDIUM;
                _pays = Locale.FRANCE;
                zone = 2;
                type = "Pressure";
                
                Date maintenant = new Date();
                String myDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                 
                InsertionBdCapteur inserer = new InsertionBdCapteur(Double.parseDouble(mm.toString()), myDate, con);
                inserer.addPressure(2);
             }
             catch (SQLException e)
             {
                 System.out.println(e.getMessage());
             }
        }
        
        // ADD CLIENT CODE HERE
        
        Sensor s = new Sensor(myDate, Integer.parseInt(mm.toString()), zone, type);
        
        cliSock = nw.Init(portServ);
        nw.sendSensor(cliSock, s);
        
        
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
