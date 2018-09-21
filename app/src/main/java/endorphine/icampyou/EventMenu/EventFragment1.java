package endorphine.icampyou.EventMenu;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.nkzawa.thread.EventThread;

import java.util.ArrayList;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.ExchangeMenu.ChattingMessage_Fragment;
import endorphine.icampyou.HomeActivity;
import endorphine.icampyou.R;

/**
 * 이벤트 프래그먼트1 클래스
 */
public class EventFragment1 extends BaseFragment {

    private View view;
    private ListView eventList;
    private ArrayList<EventListViewItem> eventData;
    private EventListViewAdapter adapter;

    // 프래그먼트 xml 설정하는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_event_1, container, false);

        // 이벤트 리스트 설정
        eventList = (ListView)view.findViewById(R.id.event_listView);

        // 이벤트 데이터 설정
        eventData = new ArrayList<>();

        // 데이터에 이벤트 아이템들 추가
        EventListViewItem event1 = new EventListViewItem(R.drawable.event_1,"난지 캠핑장 8월 초특가","2018-08-01 ~ 2018-09-11");
        EventListViewItem event2 = new EventListViewItem(R.drawable.event_2,"강동 캠핑장 아시안게임 응원 이벤트","2018-08-01 ~ 2018-09-11");

        eventData.add(event1);
        eventData.add(event2);

        // 어댑터로 이벤트 리스트에 아이템 뿌려주기
        adapter = new EventListViewAdapter(inflater, R.layout.fragment_event_1_listview_item, eventData);
        eventList.setAdapter(adapter);

        // 이벤트 상세페이지 프래그먼트로 선택한 이벤트 position값 전달
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.event_frame, EventInfoFragment.newInstance(eventList.getSelectedItemPosition()));
                fragmentTransaction.addToBackStack("TEXT_VIEWER_BACKSTACK").commit();
            }
        });

        return view;
    }



}
