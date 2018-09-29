package endorphine.icampyou.ExchangeMenu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import endorphine.icampyou.R;

public class ChatMessage_Adapter extends BaseAdapter {

    //메세지 내용 클래스
    public class MessageContents{
        String msg;
        int type;

        public MessageContents(String _msg,int _type){
            this.msg = _msg;
            this.type = _type;
        }
    }

    //메세지 목록 변수 선언
    private ArrayList<MessageContents> m_message_list;

    public ChatMessage_Adapter(){
        m_message_list = new ArrayList<MessageContents>();
    }

    public void add(String _msg,int _type){
        m_message_list.add(new MessageContents(_msg,_type));
    }

    public void remove(int _position){
        m_message_list.remove(_position);
    }

    @Override
    public int getCount() {
        return m_message_list.size();
    }

    @Override
    public Object getItem(int position) {
        return m_message_list.get(position);
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
        TextView text =null;
        LinearLayout layout = null;
        View viewRight = null;
        View viewLeft = null;

        if(convertView == null){
            //view가 null 일 경우 custom layout을 얻어옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatmessage_item,parent,false);

            layout = (LinearLayout)convertView.findViewById(R.id.chat_message_layout);
            text = (TextView)convertView.findViewById(R.id.message_text);
            viewRight = (View)convertView.findViewById(R.id.imageViewright);
            viewLeft = (View)convertView.findViewById(R.id.imageViewleft);

            //홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView = text;
            holder.layout = layout;
            holder.viewLeft = viewLeft;
            holder.viewRight = viewRight;
            convertView.setTag(holder);

        } else{
            holder = (CustomHolder) convertView.getTag();
            text = holder.m_TextView;
            layout = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;
        }

        text.setText(m_message_list.get(position).msg);

        if( m_message_list.get(position).type == 0 ) {
            text.setBackgroundResource(R.drawable.chat_you);
            text.setTextColor(Color.BLACK);
            layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }else if(m_message_list.get(position).type == 1){
            text.setBackgroundResource(R.drawable.chat_me);
            text.setTextColor(Color.WHITE);
            layout.setGravity(Gravity.RIGHT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }else if(m_message_list.get(position).type == 2){
            text.setBackgroundResource(R.drawable.datebg);
            layout.setGravity(Gravity.CENTER);
            viewRight.setVisibility(View.VISIBLE);
            viewLeft.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private class CustomHolder {
        TextView    m_TextView;
        LinearLayout layout;
        View viewRight;
        View viewLeft;
    }
}
