package com.example.piguaiweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author Richard_Y_Wang
 * @version $Rev$
 * @des 2018/4/2
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Basic {
    /**
     * 使用@SerializedName注解的方式来让json字段和java字段建立映射关系
     */
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
