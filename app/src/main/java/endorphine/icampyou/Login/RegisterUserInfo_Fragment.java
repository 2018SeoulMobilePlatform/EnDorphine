package endorphine.icampyou.Login;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class RegisterUserInfo_Fragment extends BaseFragment {

    EditText email_editText;
    EditText password_editText;
    EditText name_editText;
    EditText nickName_editText;
    EditText phoneNumber_editText;

    RegisterUserException exception;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register_userinfo,container,false);

        exception = new RegisterUserException();

        Button register_btn = (Button)view.findViewById(R.id.register_user);
        register_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(!exception.EmailException(email_editText.getText().toString()) ||
                        !exception.UserNameException(name_editText.getText().toString()) ||
                        !exception.UserNickNameException(nickName_editText.getText().toString()) ||
                        !exception.UserPassWordExcepiton(password_editText.getText().toString()) ||
                        !exception.UserPhoneException(phoneNumber_editText.getText().toString())){

                    Toast.makeText(getActivity(),"올바른 입력이 필요합니다",Toast.LENGTH_LONG).show();
                } else{
                    // URL 설정
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/register";

                    JSONObject data = null;

                    try {
                        data = sendJSonData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    endorphine.icampyou.NetworkTask networkTask = new endorphine.icampyou.NetworkTask(getActivity(),url,data, NetworkTask.USER_REGISTER);
                    networkTask.execute();

                    // AsyncTask를 통해 HttpURLConnection 수행
                    Toast.makeText(getActivity(),"사용자 정보 등록이 완료되었습니다.",Toast.LENGTH_LONG).show();

                }
            }
        });

        email_editText = (EditText)view.findViewById(R.id.user_email);
        password_editText = (EditText)view.findViewById(R.id.password);
        name_editText = (EditText)view.findViewById(R.id.user_name);
        nickName_editText = (EditText)view.findViewById(R.id.user_nickname);
        phoneNumber_editText = (EditText)view.findViewById(R.id.user_phone);

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

                    NetworkTask networkTask = new NetworkTask(getActivity(),url,data,NetworkTask.DUPLICATED_EMAIL,email_editText);
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

                    NetworkTask networkTask = new NetworkTask(getActivity(),url,data,NetworkTask.DUPLICATED_NICKNAME,nickName_editText);
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

                    NetworkTask networkTask = new NetworkTask(getActivity(),url,data,NetworkTask.DUPLICATED_PHONENUMBER,phoneNumber_editText);
                    networkTask.execute();
                }
            }
        });

        return view;
    }

    //POST 요청 JSON 데이터 형식 사용
    private JSONObject sendJSonData() throws JSONException{

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

}
