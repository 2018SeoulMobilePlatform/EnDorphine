package endorphine.icampyou.QRcode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import endorphine.icampyou.R;

public class QrcodePopupActivity extends Activity {

    ImageView imageView;
    TextView reservationNum_textView;
    TextView campingPlace_textView;
    TextView date_textView;
    TextView tentType_View;

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);

        Set<String> reservationNum = preferences.getStringSet("reservationNum", new HashSet<String>());
        Set<String> campingPlace = preferences.getStringSet("campingPlace", new HashSet<String>());
        Set<String> date = preferences.getStringSet("date", new HashSet<String>());
        Set<String> tentType = preferences.getStringSet("tentType", new HashSet<String>());

        ArrayList<String> arrayList_reservationNum = new ArrayList<>();
        ArrayList<String> arrayList_campingPlace = new ArrayList<>();
        ArrayList<String> arrayList_date = new ArrayList<>();
        ArrayList<String> arrayList_tentType = new ArrayList<>();

        for (String str : reservationNum)
            arrayList_reservationNum.add(str);
        for (String str : campingPlace)
            arrayList_campingPlace.add(str);
        for (String str : date)
            arrayList_date.add(str);
        for (String str : tentType)
            arrayList_tentType.add(str);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qrcode_popup);

        //UI 객체 생성
        imageView = (ImageView) findViewById(R.id.qrcode_popup);
        reservationNum_textView = (TextView) findViewById(R.id.reservation_number);
        campingPlace_textView = (TextView) findViewById(R.id.reservation_camping);
        date_textView = (TextView) findViewById(R.id.reservation_date);
        tentType_View = (TextView) findViewById(R.id.reservation_tent);

        if (arrayList_reservationNum.size() == 0) {

        } else {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Bitmap qrcode = null;
            try {
                qrcode = toBitmap(qrCodeWriter.encode(arrayList_reservationNum.get(0), BarcodeFormat.QR_CODE,500, 500));
            } catch (WriterException e) {
                e.printStackTrace();
            }

            reservationNum_textView.setText(arrayList_reservationNum.get(0));
            campingPlace_textView.setText(arrayList_campingPlace.get(0));
            date_textView.setText(arrayList_date.get(0));
            tentType_View.setText(arrayList_tentType.get(0));

            imageView.setImageBitmap(qrcode);
        }
}

    //확인 버튼 클릭
    public void mOnClose(View v) {
        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    // QR코드 이미지 비트맵으로 변환
    public Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}