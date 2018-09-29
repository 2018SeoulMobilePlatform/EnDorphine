package endorphine.icampyou.NavigationDrawerMenu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import endorphine.icampyou.Camera;
import endorphine.icampyou.Constant;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.ImageConversion;
import endorphine.icampyou.Login.RegisterUserException;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener {

    Camera camera;
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

    TextView check_nickname;
    TextView check_password;
    TextView check_confirmPassword;

    ImageView backButton;

    ImageConversion imageConversion;

    RegisterUserException exception;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        imageConversion = new ImageConversion();

        exception = new RegisterUserException();

        confirmButton = findViewById(R.id.mypage_confirm);
        confirmButton.setOnClickListener(this);

        // back 버튼 설정
        backButton = findViewById(R.id.mypage_back_btn);
        backButton.setOnClickListener(this);
        GlideApp.with(this).load(R.drawable.back_btn).into(backButton);

        GlideApp.with(this).load(R.drawable.review_plus_icon).into((CircleImageView) findViewById(R.id.mypage_profile_change_btn));

        // 유저정보 설정
        userImage = findViewById(R.id.mypage_user_image);
        nickname = findViewById(R.id.mypage_nickname);
        email = findViewById(R.id.mypage_email);
        name = findViewById(R.id.mypage_name);
        phone = findViewById(R.id.mypage_phone);
        password = findViewById(R.id.mypage_password);
        passwordCheck = findViewById(R.id.mypage_password_check);
        check_nickname = findViewById(R.id.check_nickname);
        check_password = findViewById(R.id.check_password);
        check_confirmPassword = findViewById(R.id.check_confimPassword);

        camera = new Camera(this, userImage);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        editor = preferences.edit();

        GlideApp.with(this).load(imageConversion.fromBase64(preferences.getString("profileImage", ""))).into(userImage);
        nickname.setText(preferences.getString("nickname", ""));
        email.setText(preferences.getString("email", ""));
        name.setText(preferences.getString("name", ""));
        phone.setText(preferences.getString("phoneNumber", ""));
        password.setText(preferences.getString("password", ""));
        passwordCheck.setText(preferences.getString("password", ""));

        nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (nickname.getText().length() != 0) {
                        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/user/checknickname";

                        JSONObject data = null;
                        try {
                            data = checkJSonNickname();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        NetworkTask networkTask = new NetworkTask(MyPageActivity.this,url,data,Constant.DUPLICATED_NICKNAME2,nickname,check_nickname);
                        networkTask.execute();
                        
                    } else{
                        check_nickname.setVisibility(View.INVISIBLE);

                    }
                }
            }
        });

        //패스워드 리스너
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();

                if(exception.UserPassWordExcepiton(edit)){
                    check_password.setVisibility(View.VISIBLE);
                } else{
                    check_password.setVisibility(View.INVISIBLE);
                }
            }
        });

        //패스워드 리스너
        passwordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if(exception.UserPassWordExcepiton(edit)){
                    check_confirmPassword.setVisibility(View.VISIBLE);
                } else{
                    check_confirmPassword.setVisibility(View.INVISIBLE);
                }
            }
        });
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
        if (v.getId() == R.id.mypage_confirm) {
            if(password.getText().toString().equals(passwordCheck.getText().toString())){

                // 이미지 수정
                editor.remove("profileImage");
                editor.putString("profileImage", imageConversion.toBase64(userImage));

                // 닉네임 수정
                editor.remove("nickname");
                editor.putString("nickname", nickname.getText().toString());
                // 비밀번호 수정
                editor.remove("password");
                editor.putString("password", password.getText().toString());
                editor.commit();

                // 수정된 정보 서버에 전달해서 서버에서도 수정하는 부분 구현해야함
                String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/update";

                JSONObject data = sendJSONdata();

                NetworkTask networkTask = new NetworkTask(MyPageActivity.this,url,data,Constant.MODIFY_USER_INFO);
                networkTask.execute();

            } else{
                Toast.makeText(this,"비밀번호를 확인해주세요",Toast.LENGTH_LONG).show();
            }
        }
        else if (v.getId() == R.id.mypage_back_btn) {
            finish();
        }
    }

    public void modify_profile(View view) {
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

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진 촬영", cameraListener)
                .setNeutralButton("앨범 선택", albumListener)
                .setNegativeButton("취소", cancelListener).show();
    }


    public JSONObject sendJSONdata() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("id", preferences.getString("email", ""));
            jsonObject.accumulate("image", imageConversion.toBase64(userImage));
            jsonObject.accumulate("nickname", nickname.getText().toString());
            jsonObject.accumulate("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //닉네임 체크하는 JSON 데이터
    private JSONObject checkJSonNickname() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate("nickname", nickname.getText().toString());

        return jsonObject;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
