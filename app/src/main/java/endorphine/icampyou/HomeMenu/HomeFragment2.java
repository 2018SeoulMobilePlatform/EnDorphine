package endorphine.icampyou.HomeMenu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.R;
import kr.go.seoul.airquality.AirQualityTypeMini;

/**
 * 홈 프래그먼트2 클래스
 */
public class HomeFragment2 extends BaseFragment {

    ReceiveWeatherTask receiveWeatherTask = new ReceiveWeatherTask();

    private AirQualityTypeMini typeMini;
    private String OpenApiKey = "7a66636d476769683532654d436a63";
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_2, container, false);

        typeMini = (AirQualityTypeMini) view.findViewById(R.id.button_mini);
        typeMini.setOpenAPIKey(OpenApiKey);

        GlideApp.with(this).load(R.drawable.home_image).into((ImageView)view.findViewById(R.id.home_background));
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        typeMini.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        typeMini.setVisibility(View.GONE);
    }
}
