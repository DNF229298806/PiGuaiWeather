package com.example.piguaiweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Richard_Y_Wang
 * @version $Rev$
 * @des 2018/4/2
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class Weather {
    /**
     * 服务器返回的状态代码
     */
    public String status;
    /**
     * 城市id 更新时间 城市名称
     */
    public Basic basic;
    /**
     * AQI指数和PM2.5指数
     */
    public AQI aqi;
    /**
     * 实时温度 实时天气
     */
    public Now now;
    /**
     * 各种建议 身体感受 洗车 运动
     */
    public Suggestion suggestion;
    /**
     * 未来一天的天气信息
     */
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
