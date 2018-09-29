package endorphine.icampyou.ExchangeMenu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.R;

public class ChatList_Adapter extends BaseAdapter{

    //아이템을 세트로 담기 위한 어레이 리스트
    private ArrayList<Chat_Item> items = new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatlist_item,parent,false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.need_pic);
        TextView user = (TextView) convertView.findViewById(R.id.user_id);
        TextView camp = (TextView) convertView.findViewById(R.id.camping_name);
        TextView need = (TextView) convertView.findViewById(R.id.need_thing);
        TextView lettable = (TextView) convertView.findViewById(R.id.lettable_thing);

        Chat_Item item = (Chat_Item)getItem(position);

        GlideApp.with(convertView).load(item.getImage()).into(imageView);
        user.setText(item.getNickname());
        camp.setText(item.getCamping_name());
        need.setText(item.getNeed_thing());
        lettable.setText(item.getLettable_thing());

        return convertView;
    }

    public void addItem(Chat_Item mitem){
        items.add(mitem);
    }

    public void removeItem(int position){
        items.remove(position);
    }

    public void removeAllitem(){
        items.clear();
    }

    public int countItme(){
        return items.size();
    }

    public void removeObject(Chat_Item chat_item){ items.remove(chat_item);}
}
