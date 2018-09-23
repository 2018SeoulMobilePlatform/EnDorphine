package endorphine.icampyou.QRcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import endorphine.icampyou.R;

public class QrcodePopupActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qrcode_popup);

        //UI 객체 생성
        imageView = (ImageView)findViewById(R.id.qrcode_popup);

        //qrcode 비트맵 데이터 가져와서 imageview에 뿌려주기
        Intent intent = getIntent();
        Bitmap qrcode = (Bitmap)intent.getParcelableExtra("qrcode");
        imageView.setImageBitmap(qrcode);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //액티비티(팝업) 닫기
        finish();
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