package endorphine.icampyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 인트로 화면 테스트를 위해 임의로 sleep 걸었음
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 메인 액티비티 실행
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
