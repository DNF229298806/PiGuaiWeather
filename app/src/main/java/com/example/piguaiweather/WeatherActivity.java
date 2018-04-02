package com.example.piguaiweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.piguaiweather.gson.Forecast;
import com.example.piguaiweather.gson.Weather;
import com.example.piguaiweather.util.HttpUtil;
import com.example.piguaiweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView sv_WeatherLayout;
    private TextView tv_Title_City;
    private TextView tv_Update_Time;
    private TextView tv_Now_Degree;
    private TextView tv_Now_Weather_Info;
    private LinearLayout forecast_Layout;
    private TextView tv_Api;
    private TextView tv_Pm25;
    private TextView tv_Comfort;
    private TextView tv_Car_Wash;
    private TextView tv_Sport;
    private ImageView iv_BingPicImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当sdk版本大于5.0的时候可以实现状态栏透明的效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        //初始化各控件
        sv_WeatherLayout = (ScrollView) findViewById(R.id.sv_weather_layout);
        tv_Title_City = (TextView) findViewById(R.id.tv_title_city);
        tv_Update_Time = (TextView) findViewById(R.id.tv_update_time);
        tv_Now_Degree = (TextView) findViewById(R.id.tv_now_degree);
        tv_Now_Weather_Info = (TextView) findViewById(R.id.tv_now_weather_info);
        forecast_Layout = (LinearLayout) findViewById(R.id.forecast_layout);
        tv_Api = (TextView) findViewById(R.id.tv_aqi);
        tv_Pm25 = (TextView) findViewById(R.id.tv_pm25);
        tv_Comfort = (TextView) findViewById(R.id.tv_comfort);
        tv_Car_Wash = (TextView) findViewById(R.id.tv_car_wash);
        tv_Sport = (TextView) findViewById(R.id.tv_sport);
        iv_BingPicImg = (ImageView) findViewById(R.id.iv_bing_pic_img);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(iv_BingPicImg);
        } else {
            loadBingPic();
        }
        if (weatherString != null) {
            //有缓存的时候直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
            //无缓存的时候去服务器查询天气
            String weatherId = getIntent().getStringExtra("weather_id");
            sv_WeatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
    }

    /**
     * 根据天气id请求城市天气信息
     *
     * @param weatherId 城市id
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=a9eb1184516d4d0d9516d945e423a9e3";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(iv_BingPicImg);
                    }
                });
            }
        });
    }

    /**
     * 处理并建始Weather实体类中的数据
     *
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        tv_Title_City.setText(cityName);
        tv_Update_Time.setText(updateTime);
        tv_Now_Degree.setText(degree);
        tv_Now_Weather_Info.setText(weatherInfo);
        forecast_Layout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecast_Layout, false);
            TextView dateText = (TextView) view.findViewById(R.id.tv_date);
            TextView infoText = (TextView) view.findViewById(R.id.tv_future_info);
            TextView maxText = (TextView) view.findViewById(R.id.tv_future_max);
            TextView minText = (TextView) view.findViewById(R.id.tv_future_min);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecast_Layout.addView(view);
        }
        if (weather.aqi != null) {
            tv_Api.setText(weather.aqi.city.aqi);
            tv_Pm25.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运动建议：" + weather.suggestion.sport.info;
        tv_Comfort.setText(comfort);
        tv_Car_Wash.setText(carWash);
        tv_Sport.setText(sport);
        sv_WeatherLayout.setVisibility(View.VISIBLE);
    }
}
