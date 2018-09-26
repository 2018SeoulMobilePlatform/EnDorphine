package endorphine.icampyou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ReservationInfoListActivity extends AppCompatActivity {

    private LayoutInflater inflater;
    private ViewGroup viewLayout;
    private ListView reservationInfoList;         // 후기 리스트
    private ArrayList<ReservationInfoItem> reservationinfoData;   // 후기 데이터
    private ReservationInfoListViewAdapter adapter;  //후기 리스트뷰 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_info_list);

        setReservationInfoList();
    }

    private void setReservationInfoList(){
        // 예약정보 리스트 설정
        reservationInfoList = (ListView)findViewById(R.id.reservation_info_listView);

        // 예약정보 데이터 설정
        reservationinfoData = new ArrayList<>();

        // 서버에서 예약정보 아이템들 추가 (지금은 예시로 임의로 추가함)
        addReservationInfoList("DSAFQQWR1523","서울대공원캠핑장","2018-09-11 목 ~ 2018-09-15 토");
        addReservationInfoList("GVGH12334DAS","초안산캠핑장","2018-10-11 월 ~ 2018-11-12 화");
        addReservationInfoList("BVQ2312GFDQ5","중랑캠핑장","2018-11-02 화 ~ 2018-11-12 수");

        // 어댑터로 예약정보 리스트에 아이템 뿌려주기
        adapter = new ReservationInfoListViewAdapter(inflater, R.layout.reservation_info_listview_item, reservationinfoData);
        reservationInfoList.setAdapter(adapter);
    }

    // 예약정보 리스트
    private void addReservationInfoList(String reservationNo, String campingPlace, String date){
        ReservationInfoItem reservationInfoItem = new ReservationInfoItem(reservationNo, campingPlace, date);
        reservationinfoData.add(reservationInfoItem);
    }
}
