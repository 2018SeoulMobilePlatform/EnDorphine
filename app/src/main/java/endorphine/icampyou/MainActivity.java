package endorphine.icampyou;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String pass_user;
    String pass_need;
    String pass_lettable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);

//        Intent intent = getIntent();
//        ChattingList_Fragment chattingList_fragment = new ChattingList_Fragment();

//        if(intent.getStringExtra("user") != null){
//            pass_user = intent.getStringExtra("user");
//            pass_need = intent.getStringExtra("need");
//            pass_lettable = intent.getStringExtra("lettable");
//
//            Bundle bundle = new Bundle();
//            bundle.putString("user",pass_user);
//            bundle.putString("need",pass_need);
//            bundle.putString("lettable",pass_lettable);
//            chattingList_fragment.setArguments(bundle);
//        }

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.main, chattingList_fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }
}
