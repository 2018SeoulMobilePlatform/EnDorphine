package endorphine.icampyou;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
    안내 프래그먼트 첫번째 화면
    난지, 초안산, 중랑, 강동 중 캠핑장 선택하는 화면
 */
public class GuideFragment1 extends Fragment {
    private View view;
    private Button nanji;
    private Button choansan;
    private Button jungrang;
    private Button gangdong;

    // 생성자
    public GuideFragment1() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 프래그먼트 xml 설정하는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_guide_1, container, false);

        // 각 xml에 맞는 버튼들 연결해줌
        nanji = (Button)view.findViewById(R.id.nanji);
        choansan = (Button)view.findViewById(R.id.choansan);
        jungrang = (Button)view.findViewById(R.id.jungrang);
        gangdong = (Button)view.findViewById(R.id.jungrang);
        // 버튼 클릭 메소드 아직 안만들었음(글로벌 MusicActivity onclick 메소드 참고

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
