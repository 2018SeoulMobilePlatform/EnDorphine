package endorphine.icampyou.EventMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.R;

/**
 * 이벤트 상세정보 페이지 프래그먼트
 */
public class EventInfoFragment  extends BaseFragment {

    private View view;
    private ListView eventList;
    private ArrayList<EventListViewItem> eventData;
    private EventListViewAdapter adapter;

    // 프래그먼트 xml 설정하는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_event_info, container, false);

//        ConfirmFragment confirmFragment = new ConfirmFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("tent", result);
//        bundle.putString("period", startDate + "~" + endDate);
//        bundle.putString("camp_name", campName);
//        confirmFragment.setArguments(bundle);
//        Log.e("DetailsActivity","2");
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.details, confirmFragment);
//        fragmentTransaction.commit();
//        Log.e("DetailsActivity","3");
        return view;
    }
}
