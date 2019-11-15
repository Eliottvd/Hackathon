/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmc;

import java.io.IOException;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.json.JSONException;

/**
 *
 * @author Eliott
 */
public class Weather {
    static final String owmApiKey = "4ed06654edb6af803a88571e0bc143b3"; 
    
    private float _temperature = 0;
    private float _humidity = 0;
    private float _windSpeed = 0;
    //private float _rain = 0;

    public Weather(){
        OpenWeatherMap.Units units = OpenWeatherMap.Units.METRIC;
        OpenWeatherMap owm = new OpenWeatherMap(units, owmApiKey);
        try {
            CurrentWeather cw = owm.currentWeatherByCityName("Bierset");
            this.setWindSpeed(cw.getWindInstance().getWindSpeed()); 
            this.setTemperature(cw.getMainInstance().getTemperature());
            _humidity = cw.getMainInstance().getHumidity();
            //_rain = cw.getRainInstance().getRain();
        }
        catch (IOException | JSONException e) {
          e.printStackTrace();
        }
    }
    
    //public float getRain(){return _rain;}
    public float getHumidity(){return _humidity;}

    public float getTemperature() {
        return _temperature;
    }

    public float getWindSpeed() {
        return _windSpeed; 
    }

    public void setTemperature(float _temperature) {
        this._temperature = _temperature;
    }
    
    public void setWindSpeed(float _windSpeed) {
        this._windSpeed = _windSpeed; 
    }
    
}
