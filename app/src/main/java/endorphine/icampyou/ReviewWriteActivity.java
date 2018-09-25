package endorphine.icampyou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import endorphine.icampyou.GuideMenu.GuideActivity;
import endorphine.icampyou.Login.RegisterUserActivity;

/**
 * 후기 작성 페이지
 */
public class ReviewWriteActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;

    RatingBar ratingBar;    // 별점 바
    ImageView reviewImageView;  // 후기사진 이미지뷰
    EditText reviewEditText;    // 후기 내용
    Button completingReviewButton;  // 작성완료 버튼
    float starNum;  // 별점
    int reviewImage; // 후기 사진
    String reviewContent;   // 후기 내용
    String campingPlace;    // 캠핑장 종류

    //카메라
    Camera camera;

    //이미지 변환 객체 선언
    ImageConversion imageConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        ratingBar = findViewById(R.id.star_ratingbar);
        reviewImageView = (ImageView) findViewById(R.id.review_imageview);
        reviewEditText = findViewById(R.id.review_content);
        completingReviewButton = findViewById(R.id.completing_review_button);
        campingPlace = getIntent().getStringExtra("캠핑장 이름");

        camera = new Camera(ReviewWriteActivity.this, reviewImageView);
        imageConversion = new ImageConversion();

        completingReviewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 작성완료 버튼 누를 시 이벤트
            case R.id.completing_review_button:
                //서버 연동
                String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/postscript/add";

                JSONObject data = null;
                data = sendJSonData();

                endorphine.icampyou.NetworkTask networkTask =
                        new endorphine.icampyou.NetworkTask(ReviewWriteActivity.this,url,data, Constant.MAKE_REVIEWLIST);
                networkTask.execute();
                break;
        }
    }

    //이미지 선택 함수
    public void select_image(View view) {
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //권한 보유 여부 확인
                camera.CheckCameraAcess();
            }
        };
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                camera.CheckAlbumAcess();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(ReviewWriteActivity.this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진 촬영", cameraListener)
                .setNeutralButton("앨번 선택", albumListener)
                .setNegativeButton("취소", cancelListener).show();

    }

    //권한 요청하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST_PERMISSION_CODE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //동의 했을 경우
                    camera.selectGallery();
                } else {
                    //거부했을 경우
                    Toast toast = Toast.makeText(ReviewWriteActivity.this,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case Constant.REQUEST_PERMISSION_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //동의 했을 경우
                    camera.selectPhoto();

                } else {
                    //거부했을 경우
                    Toast toast = Toast.makeText(ReviewWriteActivity.this,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }

    //선택한 사진 데이터 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.GALLERY_CODE:
                    camera.sendPicture(data.getData());
                    break;
                case Constant.CAMERA_CODE:
                    camera.getPictureForPhoto();
                    break;
                default:
                    break;
            }
        }
    }

    //후기 데이터 서버에 보내기 위한 JSON 형식 데이터
    private JSONObject sendJSonData() {

        JSONObject jsonObject = new JSONObject();

        String encodedImage = imageConversion.toBase64(reviewImageView);

        starNum = (float) ratingBar.getRating();

        try {

            jsonObject.accumulate("image", encodedImage);
            jsonObject.accumulate("number", "1");
            jsonObject.accumulate("camp_name", campingPlace);
            jsonObject.accumulate("nickname", "다뚱이");
            jsonObject.accumulate("point", String.valueOf(starNum));
            jsonObject.accumulate("content", reviewEditText.getText().toString());
            Log.e("point",String.valueOf(starNum));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
