package endorphine.icampyou;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import endorphine.icampyou.GuideMenu.NanjiGuideActivity;

/**
 * 후기 작성 페이지
 */
public class ReviewWriteActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent;

    RatingBar ratingBar;    // 별점 바
    Spinner campingPlaceSpinner;    // 캠핑장 선택바
    ImageView reviewImageView;  // 후기사진 이미지뷰
    EditText reviewEditText;    // 후기 내용
    Button completingReviewButton;  // 작성완료 버튼
    float starNum;  // 별점
    String campingPlace;    // 캠핑장 이름
    int reviewImage; // 후기 사진
    String reviewContent;   // 후기 내용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        ratingBar = findViewById(R.id.star_ratingbar);
        campingPlaceSpinner = findViewById(R.id.camping_place_spinner);
        reviewImageView = findViewById(R.id.review_image);
        reviewEditText = findViewById(R.id.review_content);
        completingReviewButton = findViewById(R.id.completing_review_button);

        completingReviewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 작성완료 버튼 누를 시 이벤트
            case R.id.completing_review_button :
                // 현재 설정값 저장
                setReviewValue();
                // 인텐트로 리뷰한테 값 보내기
                putIntent();
                startActivity(intent);
                finish();
                break;
        }
    }

    public void setReviewValue(){
        // 별점 가져오기
        starNum = (float)ratingBar.getRating();
        // 캠핑장 이름 가져오기
        campingPlace = campingPlaceSpinner.getSelectedItem().toString();
        // 후기 사진 가져오기...(어케하는지 모르긔)
        reviewImage = 0;
        // 후기 내용 가져오기
        reviewContent = reviewEditText.getText().toString();
    }

    public void putIntent(){
        intent = new Intent(this, NanjiGuideActivity.class);
        intent.putExtra("star",(float)starNum);
        intent.putExtra("camping_place",campingPlace);
        intent.putExtra("review_content",reviewContent);
        intent.putExtra("review_image",reviewImage);
    }
}
