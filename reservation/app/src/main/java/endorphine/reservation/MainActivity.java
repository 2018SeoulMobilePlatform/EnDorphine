package endorphine.reservation;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TitlesFragment.OnTitleSelectedListener
{
    final String[][] contents = new String[3][2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contents[0][0] = "Title-1";
        contents[0][1] = "this is Details of Title-1";
        contents[1][0] = "Title-2";
        contents[1][1] = "this is Details of Title-2";
        contents[2][0] = "Title-3";
        contents[2][1] = "this is Details of Title-3";

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList());
        adapter.add(contents[0][0]);
        adapter.add(contents[1][0]);
        adapter.add(contents[2][0]);

        TitlesFragment titlesFragment = (TitlesFragment)getSupportFragmentManager().findFragmentById(R.id.titles_fragment);
        titlesFragment.setListAdapter(adapter);
    }

    @Override
    public void onTitleSelected(int position)
    {
        if (getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE) &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            DetailsFragment fr = new DetailsFragment();
            Bundle args = new Bundle();
            args.putString("title", contents[position][0]);
            args.putString("details", contents[position][1]);
            fr.setArguments(args);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.details_container, fr);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent();
            intent.setClass(this, DetailsActivity.class);
            intent.putExtra("title", contents[position][0]);
            intent.putExtra("details", contents[position][1]);

            startActivity(intent);
        }

    }

}
