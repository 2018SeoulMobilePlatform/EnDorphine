package endorphine.icampyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FindUserInfo_Fragment extends BaseFragment {


    String findPassword_email;
    String findPassword_name;
    String findPassword_phone;

    EditText findPassword_email_editText;
    EditText findPassword_name_editText;
    EditText findPassword_phone_editText;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_find_userinfo,container,false);

        Button find_password = (Button)view.findViewById(R.id.find_password);
        find_password.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //데이터베이스에서 비밀번호 찾아오기
            }
        });

        findPassword_email_editText = (EditText)view.findViewById(R.id.find_email);
        findPassword_name_editText = (EditText)view.findViewById(R.id.find_name2);
        findPassword_phone_editText = (EditText)view.findViewById(R.id.find_phone);

        findPassword_email = findPassword_email_editText.getText().toString();
        findPassword_name = findPassword_name_editText.getText().toString();
        findPassword_phone = findPassword_phone_editText.getText().toString();

        return view;
    }
}
