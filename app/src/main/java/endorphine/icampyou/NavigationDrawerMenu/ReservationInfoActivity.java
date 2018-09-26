package endorphine.icampyou.NavigationDrawerMenu;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import endorphine.icampyou.R;

public class ReservationInfoActivity extends AppCompatActivity {

    ImageView qrcode;
    TextView reservationNum;
    TextView campingPlace;
    TextView date;
    TextView tentType;
    TextView tentNum;
    TextView price;
    Button confirmButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Bitmap qrcodeBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_info);

        qrcode.findViewById(R.id.res_info_qrcode);
        reservationNum.findViewById(R.id.res_info_reservation_number);
        campingPlace.findViewById(R.id.res_info_camping_place);
        date.findViewById(R.id.res_info_date);
        tentType.findViewById(R.id.res_info_tent);
        tentNum.findViewById(R.id.res_info_tent_num);
        price.findViewById(R.id.res_info_total_price);
        confirmButton.findViewById(R.id.res_info_confirm);

        preferences = getSharedPreferences("preferences",MODE_PRIVATE);
        editor = preferences.edit();

        // 프리퍼런스 파일에서 정보 가져와서 예약정보페이지에 뿌려주기
        // 사실상 예약번호 하나로 다른 정보들을 서버에서 가져와서 뿌려줘야함
        generateQRCode(preferences.getString("reservationNum",""));
        qrcode.setImageBitmap(qrcodeBitmap);
        reservationNum.setText(preferences.getString("reservationNum",""));
        campingPlace.setText(preferences.getString("campingPlace",""));
        date.setText(preferences.getString("date",""));
        tentType.setText(preferences.getString("tentType",""));
        tentNum.setText(preferences.getString("tentNum",""));
        price.setText(preferences.getString("price",""));

        editor.commit();
    }

    // QR코드 생성
    public void generateQRCode(String contents) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            qrcodeBitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 500, 500));
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


}
