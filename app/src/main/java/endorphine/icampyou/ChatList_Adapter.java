package endorphine.icampyou;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatList_Adapter extends BaseAdapter {

    Context context;
    List<Chat_Item> chatItems;

    public ChatList_Adapter(Context _context, List<Chat_Item> _chatItems){
        this.context = _context;
        this.chatItems = _chatItems;
    }

    public void add(Chat_Item chat_item){
        chatItems.add(chat_item);
        for(int i=0;i<chatItems.size();i++){
            Log.e("이다인ㅇ멍청이",chatItems.get(i).toString());
        }
    }

    public void remove(int _position){
        chatItems.remove(_position);
    }

    @Override
    public int getCount() {
        return chatItems.size();
    }

    @Override
    public Object getItem(int position) {
        return chatItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chatItems.indexOf(getItem(position));
    }

    private class ViewHolder{
        ImageView need_pic;
        TextView user_id;
        TextView need_thing;
        TextView lettable_thing;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater minflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = minflater.inflate(R.layout.chatlist_item,null);
            holder = new ViewHolder();

            holder.need_pic = (ImageView)convertView.findViewById(R.id.need_pic);
            holder.user_id = (TextView) convertView.findViewById(R.id.user_id);
            holder.need_thing = (TextView) convertView.findViewById(R.id.need_thing);
            holder.lettable_thing = (TextView)convertView.findViewById(R.id.lettable_thing);

            Chat_Item chat_pos = chatItems.get(position);

            //holder.need_pic.setImageResource(chat_pos.getNeed_pic_id());
            holder.need_pic.setImageBitmap(chat_pos.getImage());
            holder.user_id.setText(chat_pos.getUser_id());
            holder.need_thing.setText(chat_pos.getNeed_thing());
            holder.lettable_thing.setText(chat_pos.getLettable_thing());

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
