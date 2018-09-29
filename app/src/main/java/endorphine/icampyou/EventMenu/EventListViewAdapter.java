package endorphine.icampyou.EventMenu;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.R;

/**
 * 이벤트 리스트뷰 어댑터 클래스
 */
public class EventListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<EventListViewItem> eventData;
    private int layout;
    private ImageView eventImage;
    private TextView eventPlace;
    private TextView eventTitle;
    private TextView eventDate;
    private TextView isClosed;

    // 생성자
    public EventListViewAdapter(LayoutInflater inflater, int layout, ArrayList<EventListViewItem> eventData){
        this.inflater = inflater;
        this.eventData = eventData;
        this.layout=layout;
    }
    @Override
    public int getCount(){return eventData.size();}
    @Override
    public String getItem(int position){return eventData.get(position).getEventTitle();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }

        EventListViewItem eventListViewItem= eventData.get(position);

        eventImage=(ImageView)convertView.findViewById(R.id.event_image);
        GlideApp.with(convertView).load(eventListViewItem.getEventImage()).into(eventImage);

        eventPlace = (TextView)convertView.findViewById(R.id.event_place);
        eventPlace.setText(eventListViewItem.getEventPlace());

        eventTitle=(TextView)convertView.findViewById(R.id.event_title);
        eventTitle.setText(eventListViewItem.getEventTitle());

        eventDate=(TextView)convertView.findViewById(R.id.event_date);
        eventDate.setText(eventListViewItem.getEventDate());

        isClosed = (TextView)convertView.findViewById(R.id.closed_event);
        if(eventListViewItem.getEventIsClosed()){
            isClosed.setText(" 종료된 이벤트 ");
            isClosed.setBackgroundColor(Color.GRAY);
        }
        else{
            isClosed.setText(" 진행중인 이벤트 ");
            isClosed.setBackgroundColor(Color.parseColor("#FFBB00"));
        }

        return convertView;
    }
}
