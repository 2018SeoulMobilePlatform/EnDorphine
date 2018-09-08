package endorphine.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import static endorphine.reservation.CalenderActivity.RESULT_SELECT_END_VIEW_DATE;
import static endorphine.reservation.CalenderActivity.RESULT_SELECT_START_VIEW_DATE;

public class DetailsActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> sAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_fragment);

        final CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);
        final CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4);
        final CheckBox cb5 = (CheckBox) findViewById(R.id.checkBox5);
        final CheckBox cb6 = (CheckBox) findViewById(R.id.checkBox6);
        final CheckBox cb7 = (CheckBox) findViewById(R.id.checkBox7);
        final CheckBox cb8 = (CheckBox) findViewById(R.id.checkBox8);
        final CheckBox cb9 = (CheckBox) findViewById(R.id.checkBox9);

        Intent intent = new Intent(this.getIntent());

        String startDate = intent.getStringExtra(RESULT_SELECT_START_VIEW_DATE);
        String endDate = intent.getStringExtra(RESULT_SELECT_END_VIEW_DATE);
        TextView periodTextView = (TextView) findViewById(R.id.period);
        periodTextView.setText(startDate + "~" + endDate);

        TextView campingTextView = (TextView) findViewById(R.id.selectedTitle);
        campingTextView.setText(intent.getExtras().getString("camping_name"));

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
        final TextView tv = (TextView) findViewById(R.id.textView2);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String result = "";  // 결과를 출력할 문자열  ,  항상 스트링은 빈문자열로 초기화 하는 습관을 가지자
                if (cb1.isChecked() == true) result += cb1.getText().toString();
                if (cb2.isChecked() == true) result += cb2.getText().toString();
                if (cb3.isChecked() == true) result += cb3.getText().toString();
                if (cb4.isChecked() == true) result += cb4.getText().toString();
                tv.setText("선택결과:" + result);

            } // end onClick

        }); // end setOnClickListener
    }
}
