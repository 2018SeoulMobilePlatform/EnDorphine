package endorphine.icampyou.ExchangeMenu;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import endorphine.icampyou.R;

public class ChatList_Adapter extends BaseAdapter{

    //채팅 목록 리스트 변수 선언
    private ArrayList<Chat_Item> m_chatlist;

    public ChatList_Adapter(ArrayList<Chat_Item> _m_chatlist){
        this.m_chatlist = _m_chatlist;
    }

    public void add(Bitmap image,String user,String need, String lettable, String camping_name){
        m_chatlist.add(new Chat_Item(image,user,need,lettable,camping_name));
    }

    public void remove(int _position){
        m_chatlist.remove(_position);
    }

    public void updateItemList(ArrayList<Chat_Item> newlist) {
        m_chatlist.clear();
        m_chatlist.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return m_chatlist.size();
    }

    @Override
    public Object getItem(int position) {
        return m_chatlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        CustomHolder holder = null;
        ImageView _image = null;
        TextView _user = null;
        TextView _need = null;
        TextView _lettable = null;
        TextView _camp = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatlist_item,parent,false);

            holder = new CustomHolder();

            holder.image = (ImageView) convertView.findViewById(R.id.need_pic);
            holder.user = (TextView) convertView.findViewById(R.id.user_id);
            holder.need = (TextView) convertView.findViewById(R.id.need_thing);
            holder.lettable = (TextView)convertView.findViewById(R.id.lettable_thing);
            holder.camp = (TextView)convertView.findViewById(R.id.camping_name);

            Chat_Item chat_item = m_chatlist.get(position);

            holder.image.setImageBitmap(chat_item.getImage());
            holder.user.setText(chat_item.getUser_id());
            holder.need.setText(chat_item.getNeed_thing());
            holder.lettable.setText(chat_item.getLettable_thing());
            holder.camp.setText(chat_item.getCamping_name());

            convertView.setTag(holder);

        } else{
            holder = (CustomHolder) convertView.getTag();
            _image = holder.image;
            _user = holder.user;
            _need = holder.need;
            _lettable = holder.lettable;
            _camp = holder.camp;
        }

        return convertView;
    }

    private class CustomHolder{
        ImageView image;
        TextView user;
        TextView need;
        TextView lettable;
        TextView camp;
    }
}
