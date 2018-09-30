package endorphine.icampyou.GuideMenu.Reservation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import endorphine.icampyou.Constant;
import endorphine.icampyou.R;

public class PricePopupActivity extends Activity {

    private Bitmap qrcodeBitmap;
    private Intent confirmPopupIntent;
    private ConfirmPopupActivity confirmPopupActivity;

    public String period;
    public String campName;
    public String tentName;
    public String price;
    public String quantity;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("preferences",MODE_PRIVATE);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_price_popup);

        Intent intent = getIntent();
        period = intent.getStringExtra("period");
        campName = intent.getStringExtra("camp_name");
        tentName = intent.getStringExtra("tent_name");
        price = intent.getStringExtra("price");
        quantity = intent.getStringExtra("quantity");

        TextView periodView = (TextView)findViewById(R.id.price_reservation_date);
        TextView campView = (TextView)findViewById(R.id.price_reservation_camping);
        TextView tentView = (TextView)findViewById(R.id.price_reservation_tent);
        TextView priceView = (TextView)findViewById(R.id.price_reservation_price);
        TextView quantityView = (TextView)findViewById(R.id.price_reservation_quantity);

        periodView.setText(period);
        campView.setText(campName);
        tentView.setText(tentName);
        priceView.setText(price + " 원");
        quantityView.setText(quantity + " 개");
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //액티비티(팝업) 닫기
        finish();
    }

    public void mOnOpen(View v){
        Random random = new Random();
        int reservationNum = random.nextInt();
        if(reservationNum < 0)
            reservationNum = reservationNum * (-1);
        SharedPreferences.Editor editor = preferences.edit();

        String reservationNum_Builder = preferences.getString("reservationNum","");
        String campingPlace_Builder = preferences.getString("campingPlace","");
        String date_Builder = preferences.getString("date","");
        String tentType_Builder= preferences.getString("tentType","");
        String tentNum_Builder = preferences.getString("tentNum","");
        String price_Builder = preferences.getString("price","");

        String[] reservationNum_array = preferences.getString("reservationNum", "").split(",");
        Log.e("size",String.valueOf(reservationNum_array.length));
        if(reservationNum_array.length < 3){
            editor.putString("reservationNum", reservationNum_Builder+String.valueOf(reservationNum)+",");
            editor.putString("campingPlace", campingPlace_Builder + campName +",");
            editor.putString("date", date_Builder+period+",");
            editor.putString("tentType", tentType_Builder+tentName+",");
            editor.putString("tentNum", tentNum_Builder+quantity+",");
            editor.putString("price", price_Builder+price+",");
            editor.commit();
        }

        //서버 연동
        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/addreservation";


        JSONObject data = null;
        data = sendJSonData(reservationNum);
        endorphine.icampyou.NetworkTask networkTask =
                new endorphine.icampyou.NetworkTask(PricePopupActivity.this,url,data, Constant.RESERVATION_CAMPING
                ,Integer.toString(reservationNum),price,quantity,tentName,campName,period);
        networkTask.execute();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    //예약정보 데이터 넘기는 JSON
    private JSONObject sendJSonData(int reservationNumber) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("reservation_number", String.valueOf(reservationNumber));
            jsonObject.accumulate("user_id", preferences.getString("email",""));
            jsonObject.accumulate("camp_name", campName);
            jsonObject.accumulate("tent_type", tentName);
            jsonObject.accumulate("date",period );
            jsonObject.accumulate("price",price);
            jsonObject.accumulate("count",quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
