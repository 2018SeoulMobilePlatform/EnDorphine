package endorphine.icampyou.Login;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import endorphine.icampyou.Constant;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private TextView password_find_button;
    private TextView register_user_button;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

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
        user_password_editText = findViewById(R.id.user_password_login);

        password_find_button.setOnClickListener(this);
        register_user_button.setOnClickListener(this);

        getSupportActionBar().hide();

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

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
                FindUserInfo_Fragment findUserInfo_fragment = new FindUserInfo_Fragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.login, findUserInfo_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.register_user_button:
                Intent intent = new Intent(this,RegisterUserActivity.class);
                startActivity(intent);

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