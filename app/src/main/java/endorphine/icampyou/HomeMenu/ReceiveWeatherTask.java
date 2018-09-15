package endorphine.icampyou.HomeMenu;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.support.constraint.Constraints.TAG;

public class ReceiveWeatherTask extends AsyncTask<String, Void, JSONObject>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected  JSONObject doInBackground(String...datas) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(datas[0]).openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.connect();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(reader);

                String readed;
                while((readed = in.readLine()) != null) {
                    JSONObject jObject = new JSONObject(readed);
                    return jObject;
                }
            } else {
                return null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        Log.i(TAG, result.toString());
        if(result != null) {

            String iconName = "";
            String nowTemp = "";
            String maxTemp = "";
            String minTemp = "";

            String humidity = "";
            String speed = "";
            String main = "";
            String description = "";

            try {
                iconName = result.getJSONArray("weather").getJSONObject(0).getString("icon");
                nowTemp = result.getJSONObject("main").getString("temp");
                humidity = result.getJSONObject("main").getString("humidity");
                minTemp = result.getJSONObject("main").getString("temp_min");
                maxTemp = result.getJSONObject("main").getString("temp_max");
                speed = result.getJSONObject("wind").getString("speed");
                main = result.getJSONArray("weather").getJSONObject(0).getString("main");
                description = result.getJSONArray("weather").getJSONObject(0).getString("description");

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            description = transferWeather(description);
            final String msg = description + " 습도 " + humidity + "%, 풍속" + speed + "m/s" + " 온도 현재:" + nowTemp + " / 최저:" + minTemp + " / 최고:" + maxTemp;
        }

    }

    private String transferWeather(String weather) {
        weather = weather.toLowerCase();

        if(weather.equals("haze")) {
            return "안개";
        }
        else if(weather.equals("fog")) {
            return "안개";
        }
        else if(weather.equals("clouds")) {
            return "구름";
        }
        else if(weather.equals("few clouds")) {
            return "구름 조금";
        }
        else if(weather.equals("scattered clouds")) {
            return "구름 낌";
        }
        else if(weather.equals("broken clouds")) {
            return "구름 많음";
        }
        else if(weather.equals("overcase clouds")) {
            return "구름 많음";
        }
        else if(weather.equals("clear sky")) {
            return "맑음";
        }

        return "";
    }
}
