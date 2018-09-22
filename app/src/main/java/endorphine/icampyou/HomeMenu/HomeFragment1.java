package endorphine.icampyou.HomeMenu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.R;
import kr.go.seoul.airquality.AirQualityTypeMini;

/**
 * 홈 화면 프래그먼트1 클래스
 */
public class HomeFragment1 extends BaseFragment implements AsyncResponse {

    ReceiveWeatherTask receiveWeatherTask = new ReceiveWeatherTask();

    private AirQualityTypeMini typeMini;
    private String OpenApiKey = "7a66636d476769683532654d436a63";
    private View view;
    public String description="";
    public String temperature="";

    public static boolean mAsync = false;
    public static boolean mDoAsync = false;

    ImageView weatherIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_1, container, false);

        typeMini = (AirQualityTypeMini) view.findViewById(R.id.button_mini);
        typeMini.setOpenAPIKey(OpenApiKey);

        receiveWeatherTask.delegate = this;

        if(mAsync==false) {
            mAsync = true;
            mDoAsync = true;
            String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=9595c31a22a4095ba487c9b0f1e062bc";
            receiveWeatherTask.execute(url);
        }

        if(description == "" && mDoAsync == false) {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=9595c31a22a4095ba487c9b0f1e062bc";
            receiveWeatherTask.execute(url);
        }
        TextView weatherDescription = (TextView) view.findViewById(R.id.weather_description);
        weatherDescription.setText(description);
        Log.e("날씨", description);
        TextView currentTemperature = (TextView) view.findViewById(R.id.current_temperature);
        currentTemperature.setText(temperature);
        Log.e("온도", temperature);
        weatherIcon = (ImageView) view.findViewById(R.id.weather_icon);

        setWeatherIcon(description);

        return view;
    }

    @Override
    public void descriptionFinish(String output) {

        description = output;
        TextView textView = (TextView) getView().findViewById(R.id.weather_description);
        setWeatherIcon(description);
        textView.setText(description);
    }

    @Override
    public void temperatureFinish(String output){

        temperature = output + "°C";
        TextView textView = (TextView) getView().findViewById(R.id.current_temperature);
        textView.setText(temperature);
    }

    public void setWeatherIcon(String weather){

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        Log.e("hour", Integer.toString(hour));
        //ImageView weatherIcon = (ImageView) getView().findViewById(R.id.weather_icon);

        switch(weather) {

            case "맑음":
                if (hour >= 18 || hour < 6)
                    weatherIcon.setBackgroundResource(R.drawable.moon);
                else
                    weatherIcon.setBackgroundResource(R.drawable.sunny);
                break;

            case "구름 조금":
                if (hour >= 18 || hour < 6)
                    weatherIcon.setBackgroundResource(R.drawable.night);
                else
                    weatherIcon.setBackgroundResource(R.drawable.sun);
                break;

            case "흐림":
                weatherIcon.setBackgroundResource(R.drawable.clouds);
                break;

            case "비 조금":
                weatherIcon.setBackgroundResource(R.drawable.drizzle);
                break;

            case "비":
                weatherIcon.setBackgroundResource(R.drawable.rain);
                break;

            case "천둥번개":
                weatherIcon.setBackgroundResource(R.drawable.storm);
                break;

            case "눈":
                weatherIcon.setBackgroundResource(R.drawable.snowman);
                break;

            case "안개":
                weatherIcon.setBackgroundResource(R.drawable.fog);
                break;

            default:
                weatherIcon.setBackgroundResource(R.drawable.sunny);
                break;
        }
        mDoAsync = false;
    }
}
