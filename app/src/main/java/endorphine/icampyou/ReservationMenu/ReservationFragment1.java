package endorphine.icampyou.ReservationMenu;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.R;

/**
 * 예약 프래그먼트 1
 * 예약할 캠핑장 선택 후 예약 화면
 */
public class ReservationFragment1 extends BaseFragment {
    private View view;

    // 프래그먼트 xml 설정하는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_reservation_1, container, false);
        return view;
    }
}
