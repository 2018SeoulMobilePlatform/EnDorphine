package endorphine.icampyou.GuideMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.MainActivity;
import endorphine.icampyou.NanjiGuideActivity;
import endorphine.icampyou.R;
import endorphine.icampyou.ReservationMenu.ReservationFragment1;

/*
    안내 프래그먼트 첫번째 화면
    난지, 초안산, 중랑, 강동 중 캠핑장 선택하는 화면
 */
public class GuideFragment1 extends BaseFragment implements View.OnClickListener {
    private View view;
    private Button nanji;
    private Button choansan;
    private Button jungrang;
    private Button gangdong;

    // 프래그먼트 xml 설정하는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_guide_1, container, false);

        // 각 xml에 맞는 버튼들 연결해줌
        nanji = (Button) view.findViewById(R.id.nanji);
        choansan = (Button) view.findViewById(R.id.choansan);
        jungrang = (Button) view.findViewById(R.id.jungrang);
        gangdong = (Button) view.findViewById(R.id.jungrang);

        // 버튼 클릭 메소드 (각 프래그먼트간 유동성 확인하려고 임의로 난지버튼 누르면 예약화면으로 가게함)
        nanji.setOnClickListener(this);

        return view;
    }

    // 각 버튼 클릭 이벤트 메소드
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nanji:
                startActivity(new Intent(getActivity(), NanjiGuideActivity.class));
                //startFragment(getFragmentManager(), ReservationFragment1.class);
                break;
            default:
                break;
        }
    }
}
