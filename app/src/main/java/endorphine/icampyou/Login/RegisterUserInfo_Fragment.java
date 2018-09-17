package endorphine.icampyou.Login;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kakao.network.NetworkTask;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.R;
import endorphine.icampyou.RequestHttpURLConnection;

public class RegisterUserInfo_Fragment extends BaseFragment {

    String email;
    String password;
    String name;
    String nickName;
    String phoneNumber;

    EditText email_editText;
    EditText password_editText;
    EditText name_editText;
    EditText nickName_editText;
    EditText phoneNumber_editText;

    RegisterUserException exception;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // URL 설정
        String url = "ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/register";

        // AsyncTask를 통해 HttpURLConnection 수행
        NetworkTask networkTask = new NetworkTask(url,null);
        networkTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register_userinfo,container,false);

        exception = new RegisterUserException();

        Button register_btn = (Button)view.findViewById(R.id.register_user);
        register_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //데이터베이스에 유저 정보 저장, 바로 홈 화면 전환
            }
        });

        email_editText = (EditText)view.findViewById(R.id.user_email);
        password_editText = (EditText)view.findViewById(R.id.password);
        name_editText = (EditText)view.findViewById(R.id.user_name);
        nickName_editText = (EditText)view.findViewById(R.id.user_nickname);
        phoneNumber_editText = (EditText)view.findViewById(R.id.user_phone);

        email = email_editText.getText().toString();
        password = password_editText.getText().toString();
        name = name_editText.getText().toString();
        nickName = nickName_editText.getText().toString();
        phoneNumber = phoneNumber_editText.getText().toString();

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

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            Log.e("result",s);
        }
    }


}
