package endorphine.icampyou.Login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.hdodenhof.circleimageview.CircleImageView;
import endorphine.icampyou.Camera;
import endorphine.icampyou.HomeActivity;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SessionCallback callback;
    private Button loginButton;
    private TextView password_find_button;
    private TextView register_user_button;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private EditText user_email_editText;
    private EditText user_password_editText;

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

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

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

                NetworkTask networkTask = new NetworkTask(this, url, jsonObject, NetworkTask.USER_LOGIN);
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
                RegisterUserInfo_Fragment registerUserInfo_fragment = new RegisterUserInfo_Fragment();

                Intent intent = new Intent(this,RegisterUserActivity.class);
                startActivity(intent);

                break;
            default:
                break;
        }
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("패키지이름", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("tag", "key_hash=" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //에러로 인한 로그인 실패
//                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {

                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.

//                    Log.e("UserProfile", userProfile.toString());
//                    Log.e("UserProfile", userProfile.getId() + "");


                    long number = userProfile.getId();


                }
            });

        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            // 세션 연결이 실패했을때

        }
    }

    public void requestMe() {
        //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("실패", "error message=" + errorResult);
//                super.onFailure(errorResult);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

                Log.d("닫힘", "onSessionClosed1 =" + errorResult);
            }

            @Override
            public void onNotSignedUp() {
                //카카오톡 회원이 아닐시
                Log.d("회원x", "onNotSignedUp ");

            }

            @Override
            public void onSuccess(UserProfile result) {
                Log.e("UserProfile", result.toString());
                Log.e("UserProfile", result.getId() + "");
            }
        });
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

    //선택한 사진 데이터 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("resultCode",String.valueOf(resultCode));
        Log.e("requestCode",String.valueOf(requestCode));

    }
}