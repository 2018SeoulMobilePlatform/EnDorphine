package endorphine.icampyou.EventMenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import endorphine.icampyou.R;

public class EventListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<EventListViewItem> eventData;
    private int layout;
    private ImageView eventImage;
    private TextView eventTitle;
    private TextView eventDate;

    // 생성자
    public EventListViewAdapter(LayoutInflater inflater, int layout, ArrayList<EventListViewItem> eventData){
        //this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        eventImage.setImageResource(eventListViewItem.getEventImage());

        eventTitle=(TextView)convertView.findViewById(R.id.event_title);
        eventTitle.setText(eventListViewItem.getEventTitle());

        eventDate=(TextView)convertView.findViewById(R.id.event_date);
        eventDate.setText(eventListViewItem.getEventDate());

        return convertView;
    }
}
