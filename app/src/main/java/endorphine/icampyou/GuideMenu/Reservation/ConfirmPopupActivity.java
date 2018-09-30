package endorphine.icampyou.GuideMenu.Reservation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import endorphine.icampyou.GlideApp;
import endorphine.icampyou.HomeActivity;
import endorphine.icampyou.R;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class ConfirmPopupActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_confirm_popup);

        //UI 객체 생성
        imageView = (ImageView)findViewById(R.id.confirm_qrcode_popup);
        TextView reservationNumView = (TextView)findViewById(R.id.confirm_reservation_number);
        TextView periodView = (TextView)findViewById(R.id.confirm_reservation_date);
        TextView campView = (TextView)findViewById(R.id.confirm_reservation_camping);
        TextView tentView = (TextView)findViewById(R.id.confirm_reservation_tent);
        TextView priceView = (TextView)findViewById(R.id.confirm_reservation_price);
        TextView quantityView = (TextView)findViewById(R.id.confirm_reservation_quantity);

        //qrcode 비트맵 데이터 가져와서 imageview에 뿌려주기
        Intent intent = getIntent();
        Bitmap qrcode = (Bitmap)intent.getParcelableExtra("qrcode");
        String reservationNum = intent.getStringExtra("reservation_number");
        String period = intent.getStringExtra("period");
        String campName = intent.getStringExtra("camp_name");
        String tentName = intent.getStringExtra("tent_name");
        String price = intent.getStringExtra("price");
        String quantity = intent.getStringExtra("quantity");

        GlideApp.with(this).load(qrcode).into(imageView);
        reservationNumView.setText(reservationNum);
        periodView.setText(period);
        campView.setText(campName);
        tentView.setText(tentName);
        priceView.setText(price);
        quantityView.setText(quantity);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //액티비티(팝업) 닫기
        finish();
        Intent homeIntent = new Intent(ConfirmPopupActivity.this, HomeActivity.class);
        homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
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
}
