package endorphine.reservation2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contaioner,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, contaioner, false);
        super.onCreateView(inflater, contaioner, savedInstanceState);

        return view;
    }
}
