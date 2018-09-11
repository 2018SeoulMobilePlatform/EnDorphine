package endorphine.icampyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import static endorphine.icampyou.CalenderActivity.RESULT_SELECT_END_VIEW_DATE;
import static endorphine.icampyou.CalenderActivity.RESULT_SELECT_START_VIEW_DATE;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        final RadioButton cb1 = (RadioButton) findViewById(R.id.checkBox1);
        final RadioButton cb2 = (RadioButton) findViewById(R.id.checkBox2);
        final RadioButton cb3 = (RadioButton) findViewById(R.id.checkBox3);
        final RadioButton cb4 = (RadioButton) findViewById(R.id.checkBox4);
        final RadioButton cb5 = (RadioButton) findViewById(R.id.checkBox5);
        final RadioButton cb6 = (RadioButton) findViewById(R.id.checkBox6);
        final RadioButton cb7 = (RadioButton) findViewById(R.id.checkBox7);
        final RadioButton cb8 = (RadioButton) findViewById(R.id.checkBox8);
        final RadioButton cb9 = (RadioButton) findViewById(R.id.checkBox9);

        Intent intent = new Intent(this.getIntent());

        final String startDate = intent.getStringExtra(RESULT_SELECT_START_VIEW_DATE);
        final String endDate = intent.getStringExtra(RESULT_SELECT_END_VIEW_DATE);
        TextView periodTextView = (TextView) findViewById(R.id.period);
        periodTextView.setText(startDate + "~" + endDate);

        TextView campingTextView = (TextView) findViewById(R.id.selectedTitle);
        campingTextView.setText(intent.getExtras().getString("camping_name"));

        final String campName = intent.getExtras().getString("camping_name");

        Log.e("DetailsActivity",intent.getStringExtra("camping_name"));
        if(!intent.getExtras().getString("camping_name").equals("난지 캠핑장"))
        {
            switch(intent.getStringExtra("camping_name")){
                case "노을 캠핑장":
                    cb1.setText("A구역 13,000원");
                    cb2.setText("B구역 10,000원");
                    cb3.setText("C구역 13,000원");
                    cb4.setText("D구역 13,000원");
                    cb5.setVisibility(View.GONE);
                    cb6.setVisibility(View.GONE);
                    cb7.setVisibility(View.GONE);
                    cb8.setVisibility(View.GONE);
                    cb9.setVisibility(View.GONE);
                    break;

                case "강동 캠핑장":
                    cb1.setText("오토 캠핑장 21,000원");
                    cb2.setText("가족 캠핑장 20,000원");
                    cb3.setText("매화나무 캠핑장 20,000원");
                    cb4.setVisibility(View.GONE);
                    cb5.setVisibility(View.GONE);
                    cb6.setVisibility(View.GONE);
                    cb7.setVisibility(View.GONE);
                    cb8.setVisibility(View.GONE);
                    cb9.setVisibility(View.GONE);
                    break;

                case "초안산 캠핑장":
                    cb1.setText("일반 캠핑 15,000원");
                    cb2.setText("오토 캠핑 25,000원");
                    cb3.setText("데크 캠핑 25,000원");
                    cb4.setText("캐빈하우스 30,000원");
                    cb5.setVisibility(View.GONE);
                    cb6.setVisibility(View.GONE);
                    cb7.setVisibility(View.GONE);
                    cb8.setVisibility(View.GONE);
                    cb9.setVisibility(View.GONE);
                    break;

                default:
                    cb1.setText("25000원 4인용");
                    cb2.setVisibility(View.GONE);
                    cb3.setVisibility(View.GONE);
                    cb4.setVisibility(View.GONE);
                    cb5.setVisibility(View.GONE);
                    cb6.setVisibility(View.GONE);
                    cb7.setVisibility(View.GONE);
                    cb8.setVisibility(View.GONE);
                    cb9.setVisibility(View.GONE);
                    break;
            }
        }



        TextView b = (TextView) findViewById(R.id.button1);


        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String result = "";  // 결과를 출력할 문자열  ,  항상 스트링은 빈문자열로 초기화 하는 습관을 가지자
                if (cb1.isChecked() == true) result += cb1.getText().toString();
                if (cb2.isChecked() == true) result += cb2.getText().toString();
                if (cb3.isChecked() == true) result += cb3.getText().toString();
                if (cb4.isChecked() == true) result += cb4.getText().toString();
                if (cb5.isChecked() == true) result += cb5.getText().toString();
                if (cb6.isChecked() == true) result += cb6.getText().toString();
                if (cb7.isChecked() == true) result += cb7.getText().toString();
                if (cb8.isChecked() == true) result += cb8.getText().toString();
                if (cb9.isChecked() == true) result += cb9.getText().toString();

                Log.e("DetailsActivity","1");

                ConfirmFragment confirmFragment = new ConfirmFragment();
                Bundle bundle = new Bundle();
                bundle.putString("tent", result);
                bundle.putString("period", startDate + "~" + endDate);
                bundle.putString("camp_name", campName);
                confirmFragment.setArguments(bundle);
                Log.e("DetailsActivity","2");
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.details, confirmFragment);
                fragmentTransaction.commit();
                Log.e("DetailsActivity","3");
            } // end onClick

        }); // end setOnClickListener


    }
}
