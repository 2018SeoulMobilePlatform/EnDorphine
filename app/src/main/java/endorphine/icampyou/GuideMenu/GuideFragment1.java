package endorphine.icampyou.GuideMenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import endorphine.icampyou.BaseFragment;
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

    // 프래그먼트 xml 설정하는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_guide_1, container, false);

        // 각 xml에 맞는 버튼들 연결해줌
        nanji = (ImageView) view.findViewById(R.id.nanji);
        choansan = (ImageView) view.findViewById(R.id.choansan);
        jungrang = (ImageView) view.findViewById(R.id.jungrang);
        gangdong = (ImageView) view.findViewById(R.id.gangdong);
        noeul = (ImageView) view.findViewById(R.id.noeul);
        seoul = (ImageView) view.findViewById(R.id.seoul);

        // 인텐트 설정
        intent = new Intent(getActivity(), GuideActivity.class);

        // 버튼 클릭 메소드 (각 프래그먼트간 유동성 확인하려고 임의로 난지버튼 누르면 예약화면으로 가게함)
        nanji.setOnClickListener(this);
        seoul.setOnClickListener(this);
        noeul.setOnClickListener(this);
        jungrang.setOnClickListener(this);
        gangdong.setOnClickListener(this);
        choansan.setOnClickListener(this);

        return view;
    }

    // 각 버튼 클릭 이벤트 메소드
    @Override
    public void onClick(View v) {
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

        Log.e("캠핑장 선택",v.getId()+"");
        startActivity(intent);
    }
}
