package endorphine.reservation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ReservationFragment extends ListFragment {

    private ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Adapter 생성 ㅁㅊ Adapter 지정
        adapter = new ListViewAdapter();
        setListAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.a),
                "난지 캠핑장", "전세영");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.b),
                "노을 캠핑장", "흑돼지");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.c),
                "서울대공원 캠핑장", "떡뽀끼");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        //get TextView's Text
        ListViewItem item = (ListViewItem) l.getItemAtPosition(position);

        String titleStr = item.getTitle();
        String descStr = item.getDesc();
        Drawable iconDrawable = item.getIcon();
    }

    public void addItem(Drawable icon, String title, String desc) {
        adapter.addItem(icon, title, desc);
    }

}
