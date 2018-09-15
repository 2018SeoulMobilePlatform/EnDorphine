package endorphine.icampyou.HomeMenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.R;

/**
 * 홈 화면 프래그먼트1 클래스
 */
public class HomeFragment1 extends BaseFragment {
    private View view;

    // 프래그먼트 xml 설정하는 메소드
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home_1);
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_1, container, false);
        ImageView imgView = (ImageView) view.findViewById(R.id.animationImage);
        imgView.setVisibility(ImageView.VISIBLE);
        imgView.setBackgroundResource(R.drawable.simple_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) imgView.getBackground();
        //getWeatherData();
        frameAnimation.start();

        Log.e("HomeFragment1", "1");

        return view;
    }

    public void getWeatherData() {

        String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=9595c31a22a4095ba487c9b0f1e062bc";

        ReceiveWeatherTask receiveWeatherTask = new ReceiveWeatherTask();
        receiveWeatherTask.execute(url);



//        BufferedReader br = null;
//        try{
//            String urlstr = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=9595c31a22a4095ba487c9b0f1e062bc";
//            Log.e("HomeFragment1", "9");
//            URL url = new URL(urlstr);
//            Log.e("HomeFragment1", "8");
//            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
//            Log.e("HomeFragment1", "7");
//            urlconnection.setRequestMethod("GET");
//            Log.e("HomeFragment1", "6");
//            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
//            Log.e("HomeFragment1", "5");
//            String result = "";
//            String line;
//            Log.e("HomeFragment1", "4");
//            while((line = br.readLine()) != null) {
//                result = result + line + "\n";
//            }
//            Log.e("HomeFragment1" + "1", result);
//            System.out.println(result);
//        } catch(Exception e) {
//            Log.e("HomeFragment1", "2");
//            System.out.println(e.getMessage());
//        }

    }

}
