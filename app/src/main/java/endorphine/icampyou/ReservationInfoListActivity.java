package endorphine.icampyou;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.zip.Inflater;

import endorphine.icampyou.EventMenu.EventInfoFragment;
import endorphine.icampyou.GuideMenu.ReviewListItem;
import endorphine.icampyou.GuideMenu.ReviewListViewAdapter;

public class ReservationInfoListActivity extends AppCompatActivity {

    private LayoutInflater inflater;
    private ViewGroup viewLayout;
    private ListView reservationInfoList;         // 후기 리스트
    private ArrayList<ReservationInfoItem> reservationinfoData;   // 후기 데이터
    private ReservationInfoListViewAdapter adapter;  //후기 리스트뷰 어댑터
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inflater = getLayoutInflater();
        viewLayout = (ViewGroup)inflater.inflate(R.layout.activity_reservation_info_list, null);
        setContentView(viewLayout);
        setReservationInfoList();
    }

    private void setReservationInfoList(){
        // 예약정보 리스트 설정
        reservationInfoList = (ListView)findViewById(R.id.reservation_info_listView);

        // 예약정보 데이터 설정
        reservationinfoData = new ArrayList<>();

        // 유저정보 가져오기
        String userId = preferences.getString("email","");
        // 서버에서 해당하는 유저 아이디의 예약정보 가져와서 리스트아이템 추가 (지금은 예시로 임의로 추가함)
        addReservationInfoList("DSAFQQWR1523","서울대공원캠핑장","2018-09-11 목 ~ 2018-09-15 토");
        addReservationInfoList("GVGH12334DAS","초안산캠핑장","2018-10-11 월 ~ 2018-11-12 화");
        addReservationInfoList("BVQ2312GFDQ5","중랑캠핑장","2018-11-02 화 ~ 2018-11-12 수");

        // 어댑터로 예약정보 리스트에 아이템 뿌려주기
        adapter = new ReservationInfoListViewAdapter(inflater, R.layout.reservation_info_listview_item, reservationinfoData);
        reservationInfoList.setAdapter(adapter);

        // 리스트 아이템 클릭이벤트 리스너
        reservationInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.event_frame, EventInfoFragment.newInstance(position));
                fragmentTransaction.addToBackStack("TEXT_VIEWER_BACKSTACK").commit();
            }
        });
    }

    //

    // 예약정보 리스트
    private void addReservationInfoList(String reservationNo, String campingPlace, String date){
        ReservationInfoItem reservationInfoItem = new ReservationInfoItem(reservationNo, campingPlace, date);
        reservationinfoData.add(reservationInfoItem);
    }
}
