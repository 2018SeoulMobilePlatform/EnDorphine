package endorphine.icampyou.GuideMenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Network;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.Constant;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.GuideMenu.Review.ReviewListItem;
import endorphine.icampyou.GuideMenu.Review.ReviewListViewAdapter;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

/*
    안내 프래그먼트 첫번째 화면
    난지, 초안산, 중랑, 강동 중 캠핑장 선택하는 화면
 */
public class GuideFragment1 extends BaseFragment implements View.OnClickListener {
    private View view;
    private ImageView nanji;
    private ImageView choansan;
    private ImageView jungrang;
    private ImageView gangdong;
    private ImageView noeul;
    private ImageView seoul;
    private Intent intent;
    private Bitmap bitmap;

    // 프래그먼트 xml 설정하는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_guide_1, container, false);

        ViewGroup guideView = (ViewGroup)inflater.inflate(R.layout.activity_guide, null);

        // 각 xml에 맞는 버튼들 연결해줌
        nanji = (ImageView) view.findViewById(R.id.nanji);
        choansan = (ImageView) view.findViewById(R.id.choansan);
        jungrang = (ImageView) view.findViewById(R.id.jungrang);
        gangdong = (ImageView) view.findViewById(R.id.gangdong);
        noeul = (ImageView) view.findViewById(R.id.noeul);
        seoul = (ImageView) view.findViewById(R.id.seoul);

        // 버튼 클릭 메소드 (각 프래그먼트간 유동성 확인하려고 임의로 난지버튼 누르면 예약화면으로 가게함)
        nanji.setOnClickListener(this);
        seoul.setOnClickListener(this);
        noeul.setOnClickListener(this);
        jungrang.setOnClickListener(this);
        gangdong.setOnClickListener(this);
        choansan.setOnClickListener(this);

        GlideApp.with(this).load(R.drawable.menu_nanji).into(nanji);
        GlideApp.with(this).load(R.drawable.menu_choansan).into(choansan);
        GlideApp.with(this).load(R.drawable.menu_junglang).into(jungrang);
        GlideApp.with(this).load(R.drawable.menu_gangdong).into(gangdong);
        GlideApp.with(this).load(R.drawable.menu_noeul).into(noeul);
        GlideApp.with(this).load(R.drawable.menu_seoul).into(seoul);

        return view;
    }

    // 각 버튼 클릭 이벤트 메소드
    @Override
    public void onClick(View v) {
        // 인텐트 설정
        intent = new Intent(getActivity(), GuideActivity.class);

        switch (v.getId()) {
            case R.id.nanji:
                intent.putExtra("캠핑장 이름", "난지 캠핑장");
                break;
            case R.id.seoul:
                intent.putExtra("캠핑장 이름", "서울대공원 캠핑장");
                break;
            case R.id.noeul:
                intent.putExtra("캠핑장 이름", "노을 캠핑장");
                break;
            case R.id.jungrang:
                intent.putExtra("캠핑장 이름", "중랑 캠핑장");
                break;
            case R.id.choansan:
                intent.putExtra("캠핑장 이름", "초안산 캠핑장");
                break;
            default:
                intent.putExtra("캠핑장 이름", "강동 캠핑장");
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        nanji = null;
        seoul = null;
        noeul = null;
        jungrang = null;
        choansan = null;
        gangdong = null;
        view = null;
        intent = null;
    }

    private JSONObject campDataJSON(String camp_name) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("camp_name", camp_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
