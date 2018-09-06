package endorphine.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);

        setContentView(R.layout.details_fragment);

        Intent intent = getIntent();

        TextView titleTextView = (TextView) findViewById(R.id.selectedTitle);
        titleTextView.setText(intent.getExtras().getString("title"));

        TextView detailsTextView = (TextView) findViewById(R.id.detailsText);
        detailsTextView.setText(intent.getExtras().getString("details"));
    }
}
