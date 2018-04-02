package com.example.piguaiweather.gson;

/**
 * @author Richard_Y_Wang
 * @version $Rev$
 * @des 2018/4/2
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
