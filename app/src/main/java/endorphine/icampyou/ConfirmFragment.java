package endorphine.icampyou;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ConfirmFragment extends Fragment {

    TextView campNameView;
    TextView periodView;
    TextView tentView;

    public String tent;
    public String period;
    public String campName;

    public ConfirmFragment() {

    }
    public static ConfirmFragment newInstance(String param1, String param2) {
        ConfirmFragment fragment = new ConfirmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tent = getArguments().getString("tent");
            campName = getArguments().getString("camp_name");
            period = getArguments().getString("period");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.confirm_fragment, null);

        campNameView = (TextView) view.findViewById(R.id.camp_name);
        tentView = (TextView) view.findViewById(R.id.tent);
        periodView = (TextView) view.findViewById(R.id.period);

        campNameView.setText(campName);
        tentView.setText(tent);
        periodView.setText(period);

        TextView confirm = (TextView) view.findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
