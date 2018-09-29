package endorphine.icampyou.EventMenu;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import endorphine.icampyou.BaseFragment;
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
        EventListViewItem event1 = new EventListViewItem(R.drawable.event1,"서울대공원캠핑장","2018년 서울동물원 대탐험(1박 2일 가족캠프)에 참여하세요!","2018-09-22 ~ 2018-10-09",false);
        EventListViewItem event2 = new EventListViewItem(R.drawable.event2,"서울대공원캠핑장","즐거운 추석행사 '동물원 한가위 한마당' 개최","2018-09-24 ~ 2018-09-25",false);
        EventListViewItem event3 = new EventListViewItem(R.drawable.event3,"중랑캠핑장","중랑캠핑숲! 2019년도 상반기 『시민의숲 꽃길 결혼식』 참여자를 모집합니다.","2018-10-01 ~ 2018-10-15",false);
        EventListViewItem event4 = new EventListViewItem(R.drawable.event4,"난지캠핑장","★난지캠핑장 여름맞이 COOL한 8월 할인 프로모션! ★", "2018-06-27 ~ 2018-07-02",true);

        eventData.add(event1);
        eventData.add(event2);
        eventData.add(event3);
        eventData.add(event4);

        // 어댑터로 이벤트 리스트에 아이템 뿌려주기
        adapter = new EventListViewAdapter(inflater, R.layout.fragment_event_1_listview_item, eventData);
        eventList.setAdapter(adapter);

        // 이벤트 상세페이지 프래그먼트로 선택한 이벤트 position값 전달
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.event_frame, EventInfoFragment.newInstance(position));
                fragmentTransaction.addToBackStack("TEXT_VIEWER_BACKSTACK").commit();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        eventList = null;
        eventData= null;
        view = null;
        adapter = null;
    }
}
