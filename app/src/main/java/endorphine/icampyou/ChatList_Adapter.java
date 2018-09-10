package endorphine.icampyou;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatList_Adapter extends BaseAdapter implements Filterable{
    Context context;
    ArrayList<Chat_Item> chatItems;

    private ArrayList<Chat_Item> filteredItemList;
    Filter listFilter;

    public ChatList_Adapter(Context _context){
        this.context = _context;
        chatItems = new ArrayList<Chat_Item>();
        filteredItemList = chatItems;
    }

    public void add(Bitmap pass_image, String pass_user, String pass_need, String pass_lettable){
        Chat_Item item = new Chat_Item();
        item.setImage(pass_image);
        item.setUser_id(pass_user);
        item.setNeed_thing(pass_need);
        item.setLettable_thing(pass_lettable);
        chatItems.add(item);
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
        return position;
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null){
            listFilter = new ListFilter();
        }
        return listFilter;
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

    private class ListFilter extends  Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() ==0 ){
                results.values = chatItems;
                results.count = chatItems.size();
            } else{
                ArrayList<Chat_Item> itemList = new ArrayList<Chat_Item>();

                for(Chat_Item item : chatItems){
                    if(item.getUser_id().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                            item.getNeed_thing().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                            item.getLettable_thing().toUpperCase().contains(constraint.toString().toUpperCase())){
                        itemList.add(item);
                    }
                }
                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItemList = (ArrayList<Chat_Item>) results.values;

            if(results.count > 0){
                notifyDataSetChanged();
            } else{
                notifyDataSetInvalidated();
            }
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        chatItems.clear();
        if (charText.length() == 0) {
            chatItems.addAll(filteredItemList);
        }
        else
        {
            for (Chat_Item wp : filteredItemList) {
                if (wp.getUser_id().toLowerCase(Locale.getDefault()).contains(charText)) {
                    chatItems.add(wp);
                }
            }
        }
        notifyDataSetInvalidated();
    }

}
