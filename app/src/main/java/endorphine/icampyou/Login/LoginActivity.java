package endorphine.icampyou.Login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    //자동 로그인
    private CheckBox autoCheck;
    String loginId, loginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //자동 로그인
        autoCheck = (CheckBox) findViewById(R.id.auto_login);

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
        GlideApp.with(this).load(R.drawable.app_logo2).into((ImageView) findViewById(R.id.login_app_logo));

        //자동 로그인
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        loginId = auto.getString("inputId", null);
        loginPwd = auto.getString("inputPwd", null);
        if(auto.getBoolean("autoLogin", false))
            autoCheck.setChecked(true);


        if(loginId != null && loginPwd != null && autoCheck.isChecked()) {

            //서버에서 로그인 정보 확인
            String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/login";

            //서버에 보낼 아이디,비밀번호 데이터
            JSONObject jsonObject = sendAutoObject(loginId, loginPwd);

            NetworkTask networkTask = new NetworkTask(this, url, jsonObject, Constant.USER_LOGIN);
            networkTask.execute();
        }

        //자동 로그인
        autoCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLogin = auto.edit();
                if(autoCheck.isChecked())
                    autoLogin.putBoolean("autoLogin", true);
                else
                    autoLogin.putBoolean("autoLogin", false);
                autoLogin.commit();
            }
        });

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

    //자동 로그인
    private JSONObject sendAutoObject(String id, String pwd){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("id", id);
            jsonObject.accumulate("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // Back키 누르면 종료
    @Override
    public void onBackPressed() {
        AlertDialog.Builder finishDialog = new AlertDialog.Builder(this);
        finishDialog.setMessage("정말로 종료하시겠습니까?");

        finishDialog.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        finishDialog.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        finishDialog.setIcon(R.drawable.app_icon7);
        finishDialog.setTitle(R.string.app_name);
        AlertDialog alert = finishDialog.create();
        alert.show();
    }
}