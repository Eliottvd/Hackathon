/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors;

/**
 *
 * @author Eliott
 */
public class Temperature {
    
    private String date;
    private int value;
    private int zone;
    
    public Temperature(String d, int v, int z)
    {
        date = d;
        value = v;
        zone = z;
    }
        public String getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }

    public int getZone() {
        return zone;
    }
}
