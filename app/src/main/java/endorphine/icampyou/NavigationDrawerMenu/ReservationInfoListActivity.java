package endorphine.icampyou.NavigationDrawerMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import endorphine.icampyou.R;

public class ReservationInfoListActivity extends AppCompatActivity {

    private LayoutInflater inflater;
    private ViewGroup viewLayout;
    private ListView reservationInfoList;
    private ArrayList<ReservationInfoItem> reservationinfoData;
    private ReservationInfoListViewAdapter adapter;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences =getSharedPreferences("preferences", MODE_PRIVATE);

        inflater = getLayoutInflater();
        viewLayout = (ViewGroup)inflater.inflate(R.layout.activity_reservation_info_list, null);
        setContentView(viewLayout);

        setReservationInfoList();

        //예약 상세정보 확인
        reservationInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view , int position, long id){
                Intent intent = new Intent(ReservationInfoListActivity.this, ReservationInfoActivity.class);
                intent.putExtra("reservation_number",((ReservationInfoItem)adapter.getItem(position)).getReservationNo());
                intent.putExtra("camping_place",((ReservationInfoItem)adapter.getItem(position)).getCampingPlace());
                intent.putExtra("date",((ReservationInfoItem)adapter.getItem(position)).getDate());
                intent.putExtra("tent_type",((ReservationInfoItem)adapter.getItem(position)).getTent_type());
                intent.putExtra("tent_number",((ReservationInfoItem)adapter.getItem(position)).getTent_number());
                intent.putExtra("total_price",((ReservationInfoItem)adapter.getItem(position)).getTotal_price());
                startActivity(intent);
            }
        });
    }

    private void setReservationInfoList(){
        // 예약정보 리스트 설정
        reservationInfoList = (ListView)findViewById(R.id.reservation_info_listView);

        // 예약정보 데이터 설정
        reservationinfoData = new ArrayList<>();

        Set<String> reservationNum = preferences.getStringSet("reservationNum", new HashSet<String>());
        Set<String> campingPlace = preferences.getStringSet("campingPlace", new HashSet<String>());
        Set<String> date = preferences.getStringSet("date", new HashSet<String>());
        Set<String> tentType = preferences.getStringSet("tentType", new HashSet<String>());
        Set<String> tentNum = preferences.getStringSet("tentNum", new HashSet<String>());
        Set<String> price = preferences.getStringSet("price", new HashSet<String>());

        ArrayList<String> arrayList_reservationNum = new ArrayList<>();
        ArrayList<String> arrayList_campingPlace = new ArrayList<>();
        ArrayList<String> arrayList_date = new ArrayList<>();
        ArrayList<String> arrayList_tentType = new ArrayList<>();
        ArrayList<String> arrayList_tentNum = new ArrayList<>();
        ArrayList<String> arrayList_price = new ArrayList<>();

        for (String str : reservationNum)
            arrayList_reservationNum.add(str);
        for (String str : campingPlace)
            arrayList_campingPlace.add(str);
        for (String str : date)
            arrayList_date.add(str);
        for (String str : tentType)
            arrayList_tentType.add(str);
        for (String str : tentNum)
            arrayList_tentNum.add(str);
        for (String str : price)
            arrayList_price.add(str);

        // 서버에서 예약정보 아이템들 추가
        for(int i=0;i<arrayList_reservationNum.size();i++){
            addReservationInfoList(arrayList_reservationNum.get(i),
                    arrayList_campingPlace.get(i), arrayList_date.get(i),
                    arrayList_tentType.get(i), arrayList_tentNum.get(i),
                    arrayList_price.get(i));
        }

        // 어댑터로 예약정보 리스트에 아이템 뿌려주기
        adapter = new ReservationInfoListViewAdapter(inflater, R.layout.reservation_info_listview_item, reservationinfoData);
        reservationInfoList.setAdapter(adapter);
    }

    // 예약정보 리스트
    private void addReservationInfoList(String reservationNo, String campingPlace, String date,String tent_type,String tent_number,String total_price){
        ReservationInfoItem reservationInfoItem = new ReservationInfoItem(reservationNo, campingPlace, date,tent_type,tent_number,total_price);
        reservationinfoData.add(reservationInfoItem);
    }
}
