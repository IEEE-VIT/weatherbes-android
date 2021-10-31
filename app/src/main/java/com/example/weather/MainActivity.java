package com.example.weather;

import static android.view.animation.AnimationUtils.loadAnimation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.api.WeatherAPI;
import com.example.weather.model.Main;
import com.example.weather.model.Weather;
import com.example.weather.model.Weather_;
import com.example.weather.model.Wind;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String main, description, Date, city_name;
    double feels_like, temp_max, temp_min, temp, wind_speed;
    int humidity, pressure, wind_dir, visibility;
    ImageView main_image,imgVisibility,imgHumidity,imgWind,imgPressure,imgMintemp,imgMaxtemp;
    LinearLayout layout;
    Button button;
    TextView Main, Description, feels_Like, temp_Max, temp_Min,
            Temp, wind_Speed, Humidity, Pressure, Visibility, date, City;
    private AlertDialog loadingDialog;

    Animation mainImageAnimation,mainTextAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        showLoadingDialog();

        /**
         * Adding animation to the icons:
         */
        mainImageAnimation = loadAnimation(this,R.anim.main_image_animation);
        mainTextAnimation = loadAnimation(this,R.anim.main_text_animation);
        String key = BuildConfig.API_KEY;
        Intent intent = getIntent();
        final String city_name = intent.getExtras().getString("City");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final WeatherAPI weatherApi = retrofit.create(WeatherAPI.class);

        Call<Weather> call = weatherApi.getWeather(city_name, key);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Weather weather = response.body();
                setContentView(R.layout.activity_main);

                Temp = findViewById(R.id.temperature);
                temp_Max = findViewById(R.id.max_temp);
                temp_Min = findViewById(R.id.min_temp);
                feels_Like = findViewById(R.id.feels_like);
                Humidity = findViewById(R.id.humidity);
                Visibility = findViewById(R.id.visibility);
                Pressure = findViewById(R.id.pressure);
                wind_Speed = findViewById(R.id.wind_speed);
                Description = findViewById(R.id.description);
                layout = findViewById(R.id.layout_main);
                Main = findViewById(R.id.main);
                main_image = findViewById(R.id.main_image);
                button = findViewById(R.id.button);

                imgVisibility=findViewById(R.id.imgVisiblility);
                imgHumidity=findViewById(R.id.imgHumidity);
                imgWind=findViewById(R.id.imgWind);
                imgPressure=findViewById(R.id.imgPressure);
                imgMintemp=findViewById(R.id.imgMinTemp);
                imgMaxtemp=findViewById(R.id.imgMaxtemp);

                button.setOnClickListener(v -> shareWeather());

                List<Weather_> weather_ = weather.getWeather();
                for (Weather_ weather_1 : weather_) {
                    main = weather_1.getMain();
                    description = weather_1.getDescription();
                }

                Main.setText(main);

                date = findViewById(R.id.date);
                City = findViewById(R.id.city);
                City.setText(city_name);

                City.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1=new Intent(MainActivity.this, StartActivity.class);
                        startActivity(intent1);
                    }
                });

                if (main.equals("Clouds")) {
                    layout.setBackgroundResource(R.drawable.cloudy_back);
                    date.setTextColor(Color.GRAY);
                    City.setTextColor(Color.BLACK);
                    main_image.setImageResource(R.drawable.cloud);
                }
                if (main.equals("Haze")) {
                    layout.setBackgroundResource(R.drawable.haze_back);
                    date.setTextColor(Color.GRAY);
                    City.setTextColor(Color.BLACK);
                    main_image.setImageResource(R.drawable.haze);
                }
                if (main.equals("Sunny")) {
                    layout.setBackgroundResource(R.drawable.sunny_back);
                    date.setTextColor(Color.WHITE);
                    City.setTextColor(Color.WHITE);
                    main_image.setImageResource(R.drawable.sun);
                }
                if (main.equals("Rain")) {
                    layout.setBackgroundResource(R.drawable.rainy_back);
                    date.setTextColor(Color.WHITE);
                    City.setTextColor(Color.WHITE);
                    main_image.setImageResource(R.drawable.rain);
                }
                if (main.equals("Drizzle")) {
                    layout.setBackgroundResource(R.drawable.rainy_back);
                    date.setTextColor(Color.WHITE);
                    City.setTextColor(Color.WHITE);
                    main_image.setImageResource(R.drawable.drizzle);
                }
                if (main.equals("Clear")) {
                    layout.setBackgroundResource(R.drawable.clear_back);
                    date.setTextColor(Color.WHITE);
                    City.setTextColor(Color.WHITE);
                    main_image.setImageResource(R.drawable.clear);
                }


                Description.setText(description);

                com.example.weather.model.Main main = weather.getMain();

                temp = main.getTemp();
                feels_like = main.getFeelsLike();
                temp_max = main.getTempMax();
                temp_min = main.getTempMin();
                humidity = main.getHumidity();
                pressure = main.getPressure();

                String Date = new SimpleDateFormat("dd/MM/yyyy, EEEE", Locale.getDefault()).format(new Date());

                if (weather.getVisibility() != null) {
                    visibility = weather.getVisibility();
                    Visibility.setText(Integer.toString(visibility));
                } else {
                    Visibility.setText("Not\navailable");
                }

                Wind wind = weather.getWind();
                wind_speed = wind.getSpeed();
                wind_dir = wind.getDeg();

                temp_Max.setText(String.format("%.2f", temp_max - 273) + "°C");
                temp_Min.setText(String.format("%.2f", temp_min - 273) + "°C");
                Temp.setText(String.format("%.2f", temp - 273) + "°C");
                feels_Like.setText("Feels like " + String.format("%.2f", feels_like - 273) + "°C");

                Humidity.setText(Integer.toString(humidity));

                Pressure.setText(Integer.toString(pressure));
                wind_Speed.setText(Double.toString(wind_speed));

                date.setText(Date);
                hideLoadingDialog();
                main_image.setAnimation(mainImageAnimation);
                imgVisibility.setAnimation(mainImageAnimation);
                imgHumidity.setAnimation(mainImageAnimation);
                imgWind.setAnimation(mainImageAnimation);
                imgPressure.setAnimation(mainImageAnimation);
                imgMintemp.setAnimation(mainImageAnimation);
                imgMaxtemp.setAnimation(mainImageAnimation);

                Visibility.setAnimation(mainTextAnimation);
                Humidity.setAnimation(mainTextAnimation);
                Pressure.setAnimation(mainTextAnimation);
                wind_Speed.setAnimation(mainTextAnimation);
                temp_Max.setAnimation(mainTextAnimation);
                temp_Min.setAnimation(mainTextAnimation);
                Description.setAnimation(mainTextAnimation);
                Main.setAnimation(mainTextAnimation);
                Temp.setAnimation(mainTextAnimation);
                feels_Like.setAnimation(mainTextAnimation);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                hideLoadingDialog();
            }

        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showLoadingDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.loading_dialog, null);
        loadingDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();
    }


    private void hideLoadingDialog(){
        loadingDialog.hide();

    }
    private void shareWeather(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "The weather report of " + city_name +" on "+ Date + " is like this."+"\n"
                +"Min and max temperature: " + temp_max + " °C, "+ temp_min + " °C "+"\n"+"Humidity: "+ humidity
                + "\n"+ "Pressure: "+ pressure+"\n"+ "Wind speed: "+ wind_speed + " and wind direction: "+ wind_dir+"\n"
                + "Visibility: "+visibility + "\n"+"And it feels like " + feels_like+ " and "+ main);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, "Share via ");
        startActivity(shareIntent);

    }
}
