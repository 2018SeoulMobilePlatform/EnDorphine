package endorphine.icampyou;

import android.content.Intent;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import endorphine.icampyou.ExchangeMenu.ChattingList_Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChattingList_Fragment chattingList_fragment = new ChattingList_Fragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main,chattingList_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
