package com.example.piguaiweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author Richard_Y_Wang
 * @version $Rev$
 * @des 2018/4/2
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Forecast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }
}
