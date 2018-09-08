package endorphine.reservation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class DetailsFragment extends Fragment {

    public DetailsFragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle args = getArguments();

        TextView titleTextView = (TextView) view.findViewById(R.id.selectedTitle);
        titleTextView.setText(args.getString("title"));

        return view;
    }
}
