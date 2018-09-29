package endorphine.icampyou.NavigationDrawerMenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import endorphine.icampyou.GlideApp;
import endorphine.icampyou.R;

/**
 * 예약정보리스트 어댑터
 */
public class ReservationInfoListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ReservationInfoItem> reservationInfoData;
    private int layout;

    private TextView reservationNo;
    private TextView campingPlace;
    private TextView date;


    // 생성자
    public ReservationInfoListViewAdapter(LayoutInflater inflater, int layout, ArrayList<ReservationInfoItem> reservationInfoData){
        this.inflater = inflater;
        this.reservationInfoData = reservationInfoData;
        this.layout=layout;
    }

    @Override
    public int getCount(){return reservationInfoData.size();}
    @Override
    public Object getItem(int position){return reservationInfoData.get(position);}

    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }

        if(reservationInfoData.size() !=0 ) {
            GlideApp.with(convertView).load(R.drawable.tent1).into((ImageView) convertView.findViewById(R.id.res_info_item_tent_image));
            ReservationInfoItem reservationInfoItem = reservationInfoData.get(position);

            reservationNo = (TextView) convertView.findViewById(R.id.res_info_item_res_num);
            reservationNo.setText(reservationInfoItem.getReservationNo());

            campingPlace = (TextView) convertView.findViewById(R.id.res_info_item_camping_place);
            campingPlace.setText(reservationInfoItem.getCampingPlace());

            date = (TextView) convertView.findViewById(R.id.res_info_item_date);
            date.setText(reservationInfoItem.getDate());
        }
        return convertView;
    }
}
