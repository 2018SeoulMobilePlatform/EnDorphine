package endorphine.icampyou;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import endorphine.icampyou.ExchangeMenu.ChattingList_Fragment;
import endorphine.icampyou.Login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    String pass_user;
    String pass_need;
    String pass_lettable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
