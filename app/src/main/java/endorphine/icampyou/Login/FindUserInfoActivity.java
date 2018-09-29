package endorphine.icampyou.Login;

import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import endorphine.icampyou.Constant;
import endorphine.icampyou.R;

public class FindUserInfoActivity extends AppCompatActivity {

    EditText findPassword_email_editText;
    EditText findPassword_name_editText;
    EditText findPassword_phone_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user_info);

        Button find_password = (Button)findViewById(R.id.find_password);
        Drawable alpha = (find_password).getBackground();
        alpha.setAlpha(90);

        find_password.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //데이터베이스에서 비밀번호 찾아오기,로그인 화면으로
                String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/findpassword";

                JSONObject data = null;

                try {
                    data = sendJSonData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                endorphine.icampyou.NetworkTask networkTask = new endorphine.icampyou.NetworkTask(FindUserInfoActivity.this,url,data, Constant.USER_FIND_INFO);
                networkTask.execute();
            }
        });

        findPassword_email_editText = (EditText)findViewById(R.id.find_email);
        findPassword_email_editText.setPrivateImeOptions("defaultInputmode=english;");
        findPassword_name_editText = (EditText)findViewById(R.id.find_name2);
        findPassword_name_editText.setPrivateImeOptions("defaultInputmode=korea;");
        findPassword_phone_editText = (EditText)findViewById(R.id.find_phone);
        findPassword_phone_editText.setPrivateImeOptions("defaultInputmode=english;");

        Intent intent = getIntent();
        if (intent.getBooleanExtra("find", false)) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    //POST 요청 JSON 데이터 형식 사용
    private JSONObject sendJSonData() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate("id", findPassword_email_editText.getText().toString());
        jsonObject.accumulate("name", findPassword_name_editText.getText().toString());
        jsonObject.accumulate("phonenumber", findPassword_phone_editText.getText().toString());

        return jsonObject;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
            return ;
        }
        if(data.getStringExtra("result").equals("fail")){

        } else{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

}
