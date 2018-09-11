package endorphine.icampyou;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TitlesFragment extends ListFragment {

    public TitlesFragment()
    {}

    public interface OnTitleSelectedListener {
        public void onTitleSelected(int position);
    }

    OnTitleSelectedListener titleSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            if (context instanceof Activity) {
                titleSelectedListener = (OnTitleSelectedListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnTitleSelectedListner");
        }
    }

    public void showDetails(int position) {

        titleSelectedListener.onTitleSelected(position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }


}

