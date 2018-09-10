package endorphine.icampyou;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static java.lang.Thread.sleep;

/**
 * 인트로 화면 클래스
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 인트로 화면 테스트를 위해 임의로 sleep 걸었음
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 로그인 액티비티 실행
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
