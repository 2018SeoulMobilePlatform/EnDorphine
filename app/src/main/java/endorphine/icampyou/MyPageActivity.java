package endorphine.icampyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener {

    Camera camera;
    CircleImageView circleImageView;
    CircleImageView userImage;
    EditText nickname;
    EditText email;
    EditText name;
    EditText phone;
    EditText password;
    EditText passwordCheck;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        camera = new Camera(this,circleImageView);

        confirmButton = findViewById(R.id.mypage_confirm);
        confirmButton.setOnClickListener(this);

        // 유저정보 설정
        userImage = findViewById(R.id.mypage_user_image);
        nickname = findViewById(R.id.mypage_nickname);
        email = findViewById(R.id.mypage_email);
        name = findViewById(R.id.mypage_name);
        phone = findViewById(R.id.mypage_phone);
        password = findViewById(R.id.mypage_password);
        passwordCheck = findViewById(R.id.mypage_password_check);

        preferences = getSharedPreferences("preferences",MODE_PRIVATE);
        editor = preferences.edit();
        userImage.setImageResource(R.drawable.user_icon);
        nickname.setText(preferences.getString("nickname",""));
        email.setText(preferences.getString("email",""));
        name.setText(preferences.getString("name",""));
        phone.setText(preferences.getString("phoneNumber",""));
        password.setText(preferences.getString("password",""));
        passwordCheck.setText(preferences.getString("password",""));
    }

    //권한 요청하기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST_PERMISSION_CODE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //동의 했을 경우
                    camera.selectGallery();
                } else {
                    //거부했을 경우
                    Toast toast = Toast.makeText(this,
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
                    Toast toast = Toast.makeText(this,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }

    //선택한 사진 데이터 처리
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

    @Override
    public void onClick(View v) {
        // 확인버튼 누르면 예외처리 후 수정됨
        if(v.getId() == R.id.mypage_confirm){
            // 닉네임 수정
            editor.remove("nickname");
            editor.putString("nickname",nickname.getText().toString());
            // 비밀번호 수정
            editor.remove("password");
            editor.putString("password",password.getText().toString());
            editor.commit();
            // 수정된 정보 서버에 전달해서 서버에서도 수정하는 부분 구현해야함
        }
    }
}
