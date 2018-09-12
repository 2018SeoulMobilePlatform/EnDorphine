package endorphine.icampyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUserInfo_Fragment extends Fragment {

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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_userinfo,container,false);

        getActivity().getActionBar().hide();

        Button register_btn = (Button)view.findViewById(R.id.register_user);
        register_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //데이터베이스에 유저 정보 저장
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

        return view;
    }
}
