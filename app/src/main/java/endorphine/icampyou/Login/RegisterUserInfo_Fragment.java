package endorphine.icampyou.Login;

import android.content.ContentValues;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import endorphine.icampyou.BaseFragment;
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

                    //정보
                    ArrayList<NameValuePair> info = null;
                    try {
                        info = makeDataType();
                        endorphine.icampyou.NetworkTask networkTask = new endorphine.icampyou.NetworkTask(url,info);
                        networkTask.execute();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

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


        return view;
    }

    //POST 요청 할 때 데이터 형식 만들기
    public ArrayList<NameValuePair> makeDataType() throws UnsupportedEncodingException {

//        ContentValues info = new ContentValues();
//
//        info.put("id",email_editText.toString());
//        info.put("password",password_editText.toString());
//        info.put("name",name_editText.toString());
//        info.put("nickname",nickName_editText.toString());
//        info.put("phonenumber",phoneNumber_editText.toString());

        ArrayList<NameValuePair> data = new ArrayList<>();
        //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.
        data.add(new BasicNameValuePair("id", URLDecoder.decode(email_editText.getText().toString(), "UTF-8")));
        data.add(new BasicNameValuePair("password", URLDecoder.decode(password_editText.getText().toString(), "UTF-8")));
        data.add(new BasicNameValuePair("name", URLDecoder.decode(name_editText.getText().toString(), "UTF-8")));
        data.add(new BasicNameValuePair("nickname", URLDecoder.decode(nickName_editText.getText().toString(), "UTF-8")));
        data.add(new BasicNameValuePair("phonenumber", URLDecoder.decode(phoneNumber_editText.getText().toString(), "UTF-8")));

        return data;
    }
}
