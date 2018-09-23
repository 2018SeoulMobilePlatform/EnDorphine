package endorphine.icampyou.GuideMenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        Log.e("예약 번호", Integer.toString(reservationNum));
//        confirmPopupIntent = new Intent(PricePopupActivity.this, ConfirmPopupActivity.class);
//        generateQRCode(Integer.toString(reservationNum));

        //서버 연동
        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/addreservation";

        JSONObject data = null;
        data = sendJSonData(reservationNum);

        endorphine.icampyou.NetworkTask networkTask =
                new endorphine.icampyou.NetworkTask(PricePopupActivity.this,url,data, Constant.RESERVATION_CAMPING);
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

    // QR코드 생성
    public void generateQRCode(String contents) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            qrcodeBitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 500, 500));
            confirmPopupIntent.putExtra("qrcode",qrcodeBitmap);
            confirmPopupIntent.putExtra("reservation_number", contents);
            confirmPopupIntent.putExtra("tent_name", tentName);
            confirmPopupIntent.putExtra("camp_name", campName);
            confirmPopupIntent.putExtra("period", period);
            confirmPopupIntent.putExtra("price", price);
            confirmPopupIntent.putExtra("quantity", quantity);
            startActivity(confirmPopupIntent);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    // QR코드 이미지 비트맵으로 변환
    public static Bitmap toBitmap(BitMatrix matrix) {
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

    //후기 데이터 서버에 보내기 위한 JSON 형식 데이터
    private JSONObject sendJSonData(int reservationNumber) {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.accumulate("reservation_number", String.valueOf(reservationNumber));
            jsonObject.accumulate("user_id", "허진규멍청잉");
            jsonObject.accumulate("camp_name", campName);
            jsonObject.accumulate("tent_type", tentName);
            jsonObject.accumulate("date",period );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
