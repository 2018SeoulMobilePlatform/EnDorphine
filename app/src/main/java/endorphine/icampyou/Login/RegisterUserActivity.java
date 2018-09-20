package endorphine.icampyou.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import endorphine.icampyou.Camera;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class RegisterUserActivity extends AppCompatActivity {

    EditText email_editText;
    EditText password_editText;
    EditText name_editText;
    EditText nickName_editText;
    EditText phoneNumber_editText;

    CircleImageView user_profile;

    //예시
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private final int REQUEST_PERMISSION_CODE_CAMERA = 2222;
    private final int REQUEST_PERMISSION_CODE_GALLERY = 2223;

    Camera camera;

    RegisterUserException exception;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        exception = new RegisterUserException();

        Button register_btn = (Button)findViewById(R.id.register_user);
        register_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(!exception.EmailException(email_editText.getText().toString()) ||
                        !exception.UserNameException(name_editText.getText().toString()) ||
                        !exception.UserNickNameException(nickName_editText.getText().toString()) ||
                        !exception.UserPassWordExcepiton(password_editText.getText().toString()) ||
                        !exception.UserPhoneException(phoneNumber_editText.getText().toString())){

                    Toast.makeText(RegisterUserActivity.this,"올바른 입력이 필요합니다",Toast.LENGTH_LONG).show();
                } else{
                    // URL 설정
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/register";

                    JSONObject data = null;

                    try {
                        data = sendJSonData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    endorphine.icampyou.NetworkTask networkTask = new endorphine.icampyou.NetworkTask(RegisterUserActivity.this,url,data, NetworkTask.USER_REGISTER);
                    networkTask.execute();

                    // AsyncTask를 통해 HttpURLConnection 수행
                    Toast.makeText(RegisterUserActivity.this,"사용자 정보 등록이 완료되었습니다.",Toast.LENGTH_LONG).show();

                }
            }
        });

        email_editText = (EditText)findViewById(R.id.user_email);
        password_editText = (EditText)findViewById(R.id.password);
        name_editText = (EditText)findViewById(R.id.user_name);
        nickName_editText = (EditText)findViewById(R.id.user_nickname);
        phoneNumber_editText = (EditText)findViewById(R.id.user_phone);
        user_profile = (CircleImageView)findViewById(R.id.profile_image);

        camera = new Camera(RegisterUserActivity.this,user_profile);

        //이메일 리스너
        email_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();

                if(exception.EmailException(edit)){
                    email_editText.setBackgroundResource(R.drawable.check_edittext);
                } else{
                    if(edit.length() != 0)
                        email_editText.setBackgroundResource(R.drawable.uncheck_edittext);
                    else
                        email_editText.setBackgroundResource(R.drawable.rounded_login);
                }
            }
        });

        //emailText 포커스 변동
        email_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/user/checkid";

                    JSONObject data = null;
                    try {
                        data = checkJSonEmail();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NetworkTask networkTask = new NetworkTask(RegisterUserActivity.this,url,data,NetworkTask.DUPLICATED_EMAIL,email_editText);
                    networkTask.execute();
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

        //닉네임 리스너
        nickName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if(exception.UserNickNameException(edit)){
                    nickName_editText.setBackgroundResource(R.drawable.check_edittext);
                } else{
                    if(edit.length() != 0)
                        nickName_editText.setBackgroundResource(R.drawable.uncheck_edittext);
                    else
                        nickName_editText.setBackgroundResource(R.drawable.rounded_login);
                }
            }
        });

        //닉네임 중복검사
        nickName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/user/checknickname";

                    JSONObject data = null;
                    try {
                        data = checkJSonNickname();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NetworkTask networkTask = new NetworkTask(RegisterUserActivity.this,url,data,NetworkTask.DUPLICATED_NICKNAME,nickName_editText);
                    networkTask.execute();
                }
            }
        });


        //핸드폰번호 리스너
        phoneNumber_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if(exception.UserPhoneException(edit)){
                    phoneNumber_editText.setBackgroundResource(R.drawable.check_edittext);
                } else{
                    if(edit.length() != 0)
                        phoneNumber_editText.setBackgroundResource(R.drawable.uncheck_edittext);
                    else
                        phoneNumber_editText.setBackgroundResource(R.drawable.rounded_login);
                }
            }
        });

        //핸드폰 중복검사
        phoneNumber_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/user/checkphone";

                    JSONObject data = null;
                    try {
                        data = checkJSonPhone();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NetworkTask networkTask = new NetworkTask(RegisterUserActivity.this,url,data,NetworkTask.DUPLICATED_PHONENUMBER,phoneNumber_editText);
                    networkTask.execute();
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
                        .setNeutralButton("앨번 선택", albumListener)
                        .setNegativeButton("취소", cancelListener).show();
            }
        });
    }

    //POST 요청 JSON 데이터 형식 사용
    private JSONObject sendJSonData() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate("id", email_editText.getText().toString());
        jsonObject.accumulate("password", password_editText.getText().toString());
        jsonObject.accumulate("name", name_editText.getText().toString());
        jsonObject.accumulate("nickname", nickName_editText.getText().toString());
        jsonObject.accumulate("phonenumber", phoneNumber_editText.getText().toString());

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
            case REQUEST_PERMISSION_CODE_GALLERY:
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
            case REQUEST_PERMISSION_CODE_CAMERA:
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
                case GALLERY_CODE:
                    camera.sendPicture(data.getData());
                    break;
                case CAMERA_CODE:
                    camera.getPictureForPhoto();
                    break;
                default:
                    break;
            }
        }
    }
}
