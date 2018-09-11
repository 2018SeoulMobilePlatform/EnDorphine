package endorphine.icampyou;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TitlesFragment.OnTitleSelectedListener
{
    final String[][] contents = new String[6][2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contents[0][0] = "난지 캠핑장";
        contents[1][0] = "노을 캠핑장";
        contents[2][0] = "서울대공원 캠핑장";
        contents[3][0] = "중랑 캠핑장";
        contents[4][0] = "강동 캠핑장";
        contents[5][0] = "초안산 캠핑장";

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList());
        adapter.add(contents[0][0]);
        adapter.add(contents[1][0]);
        adapter.add(contents[2][0]);
        adapter.add(contents[3][0]);
        adapter.add(contents[4][0]);
        adapter.add(contents[5][0]);

        TitlesFragment titlesFragment = (TitlesFragment)getSupportFragmentManager().findFragmentById(R.id.titles_fragment);
        titlesFragment.setListAdapter(adapter);

    }

    @Override
    public void onTitleSelected(int position) {

        Intent intent = new Intent();
        intent.setClass(this, CalenderActivity.class);
        intent.putExtra("title", contents[position][0]);

        startActivity(intent);

    }

}
