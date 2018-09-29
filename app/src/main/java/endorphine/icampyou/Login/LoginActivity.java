package endorphine.icampyou.Login;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import endorphine.icampyou.Constant;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private TextView password_find_button;
    private TextView register_user_button;

    private EditText user_email_editText;
    private EditText user_password_editText;

    // shared preference에 유저정보 저장
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String profileImage;
    private String campingPlace;
    private String date;
    private String tentType;
    private String reservationNum;
    private String tentNum;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password_find_button = findViewById(R.id.password_find_button);
        register_user_button = findViewById(R.id.register_user_button);

        user_email_editText = findViewById(R.id.user_email_login);
        user_email_editText.setPrivateImeOptions("defaultInputmode=english;");
        user_password_editText = findViewById(R.id.user_password_login);
        user_password_editText.setPrivateImeOptions("defaultInputmode=english;");


        password_find_button.setOnClickListener(this);
        register_user_button.setOnClickListener(this);

        getSupportActionBar().hide();

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        Drawable alpha = (loginButton).getBackground();
        alpha.setAlpha(90);

        GlideApp.with(this).load(R.drawable.login_background).into((ImageView) findViewById(R.id.login_background));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:

                //서버에서 로그인 정보 확인
                String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/login";

                //서버에 보낼 아이디,비밀번호 데이터
                JSONObject jsonObject = sendObject();

                NetworkTask networkTask = new NetworkTask(this, url, jsonObject, Constant.USER_LOGIN);
                networkTask.execute();

                break;
            case R.id.password_find_button:
                Intent find_password_intent = new Intent(this,FindUserInfoActivity.class);
                startActivity(find_password_intent);
                break;
            case R.id.register_user_button:
                Intent register_intent = new Intent(this,RegisterUserActivity.class);
                startActivity(register_intent);

                break;
            default:
                break;
        }
    }

    //POST 요청 JSON 데이터 형식 사용
    private JSONObject sendObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("id", user_email_editText.getText().toString());
            jsonObject.accumulate("password", user_password_editText.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}