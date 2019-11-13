/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmqtt;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.*;
/**
 *
 * @author Eliott
 */
public class DataSender implements MqttCallback{

    MqttClient myClient;
    MqttConnectOptions connOpt;
    String topSub;
    
    static final String BROKER_URL = "tcp://localhost:1883" ;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataSender m = new DataSender();
        m.main1(args);
    }
    
    public void main1(String[] args) {
        // TODO code application logic here
        this.Connexion();
        int min = 1;
        int max = 10;
        Random r = new Random();
        
        /*
        try {
                int subQoS = 0;
                myClient.subscribe("yolo/Temperature", subQoS);
                System.out.println("SUBSCRIBED");
        } catch (Exception e) {
                e.printStackTrace();
        }*/
        
        for(int i = 0; i < 10; i++)
        {
            publish(r.nextInt((max - min) + 1) + min, "Zone1/Temperature"); 
            publish(r.nextInt((max - min) + 1) + min, "Zone1/Humidite");
            publish(r.nextInt((max - min) + 1) + min, "Zone1/Pression");
            
            publish(r.nextInt((max - min) + 1) + min, "Zone2/Temperature");
            publish(r.nextInt((max - min) + 1) + min, "Zone2/Humidite");
            publish(r.nextInt((max - min) + 1) + min, "Zone2/Pression");
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
        
        
                
        return 1;
    }
    
    public void publish(int number, String myTopic)
    {
        //String myTopic = "yolo/Capteur1";
        MqttTopic topic = myClient.getTopic(myTopic);
        
        String pubMsg = Integer.toString(number);
        System.out.println("Nouveau msg cree : " + pubMsg);
        int pubQoS = 0;
        MqttMessage message = new MqttMessage(pubMsg.getBytes());
        message.setQos(pubQoS);
        message.setRetained(false);

        // Publish the message
        System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
        MqttDeliveryToken token = null;
        // publish message to broker
        try {
            myClient.publish(myTopic,message);
        } catch (MqttException ex) {
            Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     

    @Override
    public void connectionLost(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println("Message arrived : " + mm);
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        }
    
}
