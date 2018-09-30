package endorphine.icampyou.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import endorphine.icampyou.Camera;
import endorphine.icampyou.Constant;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.ImageConversion;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class RegisterUserActivity extends AppCompatActivity {

    EditText email_editText;
    EditText password_editText;
    EditText name_editText;
    EditText nickName_editText;
    EditText phoneNumber_editText;

    CircleImageView user_profile;

    Camera camera;

    RegisterUserException exception;

    ImageConversion imageConversion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        GlideApp.with(this).load(R.drawable.register_background).into((ImageView) findViewById(R.id.register_background));
        GlideApp.with(this).load(R.drawable.review_plus_icon).into((ImageView) findViewById(R.id.register_profile_change_btn));
        GlideApp.with(this).load(R.drawable.round_user).into((CircleImageView) findViewById(R.id.profile_image));

        exception = new RegisterUserException();

        imageConversion = new ImageConversion();

        Button register_btn = (Button)findViewById(R.id.register_user);
        Drawable alpha = (register_btn).getBackground();
        alpha.setAlpha(90);

        register_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(!exception.EmailException(email_editText.getText().toString()) ||
                        !exception.UserNameException(name_editText.getText().toString()) ||
                        !exception.UserNickNameException(nickName_editText.getText().toString()) ||
                        !exception.UserPassWordExcepiton(password_editText.getText().toString()) ||
                        !exception.UserPhoneException(phoneNumber_editText.getText().toString())){

                    Toast.makeText(RegisterUserActivity.this,"입력을 다시 확인해주세요",Toast.LENGTH_LONG).show();
                } else{
                    // URL 설정
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/register";

                    JSONObject data = null;

                    try {
                        data = sendJSonData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    endorphine.icampyou.NetworkTask networkTask = new endorphine.icampyou.NetworkTask(RegisterUserActivity.this,url,data, Constant.USER_REGISTER);
                    networkTask.execute();

                }
            }
        });

        email_editText = (EditText)findViewById(R.id.user_email);
        email_editText.setPrivateImeOptions("defaultInputmode=english;");
        password_editText = (EditText)findViewById(R.id.password);
        password_editText.setPrivateImeOptions("defaultInputmode=english;");
        name_editText = (EditText)findViewById(R.id.user_name);
        name_editText.setPrivateImeOptions("defaultInputmode=korea;");
        nickName_editText = (EditText)findViewById(R.id.user_nickname);
        nickName_editText.setPrivateImeOptions("defaultInputmode=korea;");
        phoneNumber_editText = (EditText)findViewById(R.id.user_phone);
        phoneNumber_editText.setPrivateImeOptions("defaultInputmode=english;");
        user_profile = (CircleImageView)findViewById(R.id.profile_image);

        camera = new Camera(RegisterUserActivity.this,user_profile);


        //emailText 포커스 변동
        email_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(email_editText.getText().length() != 0){
                        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/user/checkid";

                        JSONObject data = null;
                        try {
                            data = checkJSonEmail();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        NetworkTask networkTask = new NetworkTask(RegisterUserActivity.this,url,data,Constant.DUPLICATED_EMAIL,email_editText);
                        networkTask.execute();
                    } else{
                        email_editText.setBackgroundResource(R.drawable.rounded_login);
                    }
                }
            }
        });

        //패스워드 리스너
        password_editText.addTextChangedListener(new TextWatcher() {
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
                    password_editText.setBackgroundResource(R.drawable.check_edittext);
                } else{
                    if(edit.length() != 0)
                        password_editText.setBackgroundResource(R.drawable.uncheck_edittext);
                    else
                        password_editText.setBackgroundResource(R.drawable.rounded_login);
                }
            }
        });

        //이름 리스너
        name_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if(exception.UserNameException(edit)){
                    name_editText.setBackgroundResource(R.drawable.check_edittext);
                } else{
                    if(edit.length() != 0)
                        name_editText.setBackgroundResource(R.drawable.uncheck_edittext);
                    else
                        name_editText.setBackgroundResource(R.drawable.rounded_login);
                }
            }
        });

        //닉네임 중복검사
        nickName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(nickName_editText.getText().length() != 0){
                        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/user/checknickname";

                        JSONObject data = null;
                        try {
                            data = checkJSonNickname();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        NetworkTask networkTask = new NetworkTask(RegisterUserActivity.this,url,data,Constant.DUPLICATED_NICKNAME,nickName_editText);
                        networkTask.execute();
                    } else{
                        nickName_editText.setBackgroundResource(R.drawable.rounded_login);
                    }
                }
            }
        });

        //핸드폰 중복검사
        phoneNumber_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(phoneNumber_editText.getText().length() !=0){
                        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/user/checkphone";

                        JSONObject data = null;
                        try {
                            data = checkJSonPhone();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        NetworkTask networkTask = new NetworkTask(RegisterUserActivity.this,url,data,Constant.DUPLICATED_PHONENUMBER,phoneNumber_editText);
                        networkTask.execute();
                    } else{
                        phoneNumber_editText.setBackgroundResource(R.drawable.rounded_login);
                    }
                }
            }
        });

        user_profile.setOnClickListener(new CircleImageView.OnClickListener(){

            @Override
            public void onClick(View view) {
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

                new AlertDialog.Builder(RegisterUserActivity.this)
                        .setTitle("업로드할 이미지 선택")
                        .setPositiveButton("사진 촬영", cameraListener)
                        .setNeutralButton("앨범 선택", albumListener)
                        .setNegativeButton("취소", cancelListener).show();
            }
        });
    }

    //POST 요청 JSON 데이터 형식 사용
    private JSONObject sendJSonData() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        String encodedImage = imageConversion.toBase64(user_profile);

        jsonObject.accumulate("image", encodedImage);
        jsonObject.accumulate("id", email_editText.getText().toString());
        jsonObject.accumulate("password", password_editText.getText().toString());
        jsonObject.accumulate("name", name_editText.getText().toString());
        jsonObject.accumulate("nickname", nickName_editText.getText().toString());
        jsonObject.accumulate("phonenumber", phoneNumber_editText.getText().toString());
        jsonObject.accumulate("flag","0");

        return jsonObject;
    }

    //아이디 체크하는 JSON 데이터
    private JSONObject checkJSonEmail() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate("id",email_editText.getText().toString());

        return jsonObject;
    }

    //닉네임 체크하는 JSON 데이터
    private JSONObject checkJSonNickname() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate("nickname",nickName_editText.getText().toString());

        return jsonObject;
    }

    //핸드폰 체크하는 JSON 데이터
    private JSONObject checkJSonPhone() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate("phonenumber",phoneNumber_editText.getText().toString());

        return jsonObject;
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
                    Toast toast = Toast.makeText(RegisterUserActivity.this,
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
                    Toast toast = Toast.makeText(RegisterUserActivity.this,
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
}
