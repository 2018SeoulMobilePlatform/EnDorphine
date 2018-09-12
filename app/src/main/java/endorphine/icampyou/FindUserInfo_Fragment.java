package endorphine.icampyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FindUserInfo_Fragment extends Fragment {

    String findId_name;
    String findId_phone;

    String findPassword_email;
    String findPassword_name;

    EditText findId_name_editText;
    EditText findId_phone_editText;

    EditText findPassword_email_editText;
    EditText findPassword_name_editText;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_find_userinfo,container,false);

        //아이디 찾는 버튼
        Button find_id = (Button)view.findViewById(R.id.find_id);
        find_id.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //데이터베이스에서 아이디 찾아오기
            }
        });

        Button find_password = (Button)view.findViewById(R.id.find_password);
        find_password.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //데이터베이스에서 비밀번호 찾아오기
            }
        });

        getActivity().getActionBar().hide();

        findId_name_editText = (EditText)view.findViewById(R.id.find_name);
        findId_phone_editText = (EditText)view.findViewById(R.id.find_phone);
        findPassword_email_editText = (EditText)view.findViewById(R.id.find_email);
        findPassword_name_editText = (EditText)view.findViewById(R.id.find_name2);

        findId_name = findId_name_editText.getText().toString();
        findId_phone = findId_phone_editText.getText().toString();
        findPassword_email = findPassword_email_editText.getText().toString();
        findPassword_name = findPassword_name_editText.getText().toString();

        return view;
    }
}
