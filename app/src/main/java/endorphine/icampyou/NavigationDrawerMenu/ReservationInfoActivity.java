package endorphine.icampyou.NavigationDrawerMenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import endorphine.icampyou.GlideApp;
import endorphine.icampyou.R;

public class ReservationInfoActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView qrcode;
    TextView reservationNum;
    TextView campingPlace;
    TextView date;
    TextView tentType;
    TextView tentNum;
    TextView price;
    Bitmap qrcodeBitmap;
    Button confirmButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_info);

        qrcode = (ImageView)findViewById(R.id.res_info_qrcode);
        reservationNum = (TextView) findViewById(R.id.res_info_reservation_number);
        campingPlace = (TextView) findViewById(R.id.res_info_camping_place);
        date = (TextView)findViewById(R.id.res_info_date);
        tentType = (TextView)findViewById(R.id.res_info_tent);
        tentNum = (TextView)findViewById(R.id.res_info_tent_num);
        price = (TextView)findViewById(R.id.res_info_total_price);
        confirmButton = (Button)findViewById(R.id.res_info_confirm);

        // back 버튼 설정
        backButton = findViewById(R.id.res_info_back_btn);
        GlideApp.with(this).load(R.drawable.back_btn).into(backButton);
        backButton.setOnClickListener(this);

        //인텐트로 정보 가져오기
        Intent intent = getIntent();

        generateQRCode(intent.getStringExtra("reservation_number"));
        qrcode.setImageBitmap(qrcodeBitmap);
        reservationNum.setText(intent.getStringExtra("reservation_number"));
        campingPlace.setText(intent.getStringExtra("camping_place"));
        date.setText(intent.getStringExtra("date"));
        tentType.setText(intent.getStringExtra("tent_type"));
        tentNum.setText(intent.getStringExtra("tent_number"));
        price.setText(intent.getStringExtra("total_price"));

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.res_info_back_btn:
                finish();
            default:
                break;
        }
    }

}
