package endorphine.icampyou.HomeMenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.HomeActivity;
import endorphine.icampyou.R;
import kr.go.seoul.airquality.AirQualityTypeMini;

import static android.content.Context.MODE_PRIVATE;

/**
 * 홈 프래그먼트2 클래스
 */
public class HomeFragment2 extends BaseFragment implements View.OnClickListener{

    ReceiveWeatherTask receiveWeatherTask = new ReceiveWeatherTask();

    private AirQualityTypeMini typeMini;
    private String OpenApiKey = "7a66636d476769683532654d436a63";
    private View view;
    private ImageView chatMessage;

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_2, container, false);

        preferences = getActivity().getSharedPreferences("preferences", MODE_PRIVATE);

        typeMini = (AirQualityTypeMini) view.findViewById(R.id.button_mini);
        typeMini.setOpenAPIKey(OpenApiKey);

        chatMessage = (ImageView)view.findViewById(R.id.chat_message);
        // 물물교환 메시지 설정
        if(preferences.getString("flag","").equals("0")){
            GlideApp.with(this).load(R.drawable.chat_message2).into(chatMessage);
        } else{
            GlideApp.with(this).load(R.drawable.chat_message1).into(chatMessage);
        }
        chatMessage.setOnClickListener(this);

        // 배경 이미지 설정
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

    // 물물교환 메시지 누르면 교환 프래그먼트로 이동
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.chat_message){
            HomeActivity home = (HomeActivity) getActivity();
            home.getNavigation().setSelectedItemId(R.id.navigation_exchange);
        }
    }
}
