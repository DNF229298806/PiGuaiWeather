package com.example.piguaiweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author Richard_Y_Wang
 * @version $Rev$
 * @des 2018/4/2
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
