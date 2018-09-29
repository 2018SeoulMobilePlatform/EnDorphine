package endorphine.icampyou.NavigationDrawerMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import endorphine.icampyou.GlideApp;
import endorphine.icampyou.R;

public class ReservationInfoListActivity extends AppCompatActivity implements View.OnClickListener{

    private LayoutInflater inflater;
    private ViewGroup viewLayout;
    private ListView reservationInfoList;
    private ArrayList<ReservationInfoItem> reservationinfoData;
    private ReservationInfoListViewAdapter adapter;
    private ImageView backButton;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences =getSharedPreferences("preferences", MODE_PRIVATE);

        inflater = getLayoutInflater();
        viewLayout = (ViewGroup)inflater.inflate(R.layout.activity_reservation_info_list, null);
        setContentView(viewLayout);

        setReservationInfoList();

        // back 버튼 설정
        backButton = findViewById(R.id.res_info_list_back_btn);
        GlideApp.with(this).load(R.drawable.back_btn).into(backButton);
        backButton.setOnClickListener(this);

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


        String[] reservationNum = preferences.getString("reservationNum", "").split(",");
        String[] campingPlace = preferences.getString("campingPlace",  "").split(",");
        String[] date = preferences.getString("date", "").split(",");
        String[] tentType = preferences.getString("tentType",  "").split(",");
        String[] tentNum = preferences.getString("tentNum",  "").split(",");
        String[] price = preferences.getString("price", "").split(",");

        if(preferences.getString("reservationNum", "").equals("")){
            Toast.makeText(this,"예약 정보가 없습니다.",Toast.LENGTH_LONG).show();
        } else{
            for(int i=0;i<reservationNum.length;i++){
                addReservationInfoList(reservationNum[i],
                        campingPlace[i], date[i],
                        tentType[i], tentNum[i],
                        price[i]);
            }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.res_info_list_back_btn:
                finish();
            default:
                break;
        }
    }
}
