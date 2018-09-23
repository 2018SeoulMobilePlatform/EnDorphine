package endorphine.icampyou.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.Constant;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

import static android.app.Activity.RESULT_OK;

public class FindUserInfo_Fragment extends BaseFragment{

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
                //데이터베이스에서 비밀번호 찾아오기,로그인 화면으로
                String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/findpassword";

                JSONObject data = null;

                try {
                    data = sendJSonData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                endorphine.icampyou.NetworkTask networkTask = new endorphine.icampyou.NetworkTask(getActivity(),url,data, Constant.USER_FIND_INFO);
                networkTask.execute();
            }
        });

        findPassword_email_editText = (EditText)view.findViewById(R.id.find_email);
        findPassword_name_editText = (EditText)view.findViewById(R.id.find_name2);
        findPassword_phone_editText = (EditText)view.findViewById(R.id.find_phone);

        Intent intent = getActivity().getIntent();
        if (intent.getBooleanExtra("find", false)) {
            intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }

        return view;
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
        Log.e("requestCode",String.valueOf(requestCode));
        Log.e("resultCode",String.valueOf(resultCode));
        Log.e("data",data.getStringExtra("result"));
        if(resultCode != RESULT_OK){
            Log.e("1","1");
            return ;
        }
        if(data.getStringExtra("result").equals("fail")){

        } else{
            Log.e("2","2");
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
