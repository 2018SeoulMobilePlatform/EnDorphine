package endorphine.icampyou.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import endorphine.icampyou.R;

public class PasswordPopupActivity extends Activity {

    TextView find_password;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_password_popup);

        //팝업창에 띄울 텍스트뷰
        find_password = (TextView)findViewById(R.id.request_user_password);

        //데이터 가져오기
        Intent intent = getIntent();
        data = intent.getStringExtra("password");

        if(data.equals("fail")){
            find_password.setText("해당 정보의 비밀번호를 찾을 수 없습니다");
        } else{
            find_password.setText(data);
        }
    }

    //확인 버튼 클릭
    public void mOnClose(View v) {

        if(data.equals("fail")){

        } else{
            Intent intent= new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        //팝업창 종료
        finish();
    }

    //바깥레이어 클릭시 안닫히게
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
