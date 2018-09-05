package endorphine.icampyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChattingMessage_Fragment extends Fragment {

    ListView m_chatMessage_listView;
    ChatMessage_Adapter m_chatmessage_adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatmessage,container,false);

        m_chatmessage_adapter = new ChatMessage_Adapter();
        m_chatMessage_listView = (ListView)view.findViewById(R.id.chatmessage_listView);

        m_chatMessage_listView.setAdapter(m_chatmessage_adapter);

        final EditText send_message = (EditText)view.findViewById(R.id.editText1);

        view.findViewById(R.id.send_btn).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                if(send_message.length() == 0){
                    return ;
                }
                else{
                    m_chatmessage_adapter.add(send_message.getText().toString(),1);
                    send_message.setText("");
                    m_chatmessage_adapter.notifyDataSetChanged();
                }

            }
        });

//
//        m_chatmessage_adapter.add("이건 뭐지",1);
//        m_chatmessage_adapter.add("쿨쿨",1);
//        m_chatmessage_adapter.add("쿨쿨쿨쿨",0);
//        m_chatmessage_adapter.add("재미있게",1);
//        m_chatmessage_adapter.add("놀자라구나힐힐 감사합니다. 동해물과 백두산이 마르고 닳도록 놀자 놀자 우리 놀자",1);
//        m_chatmessage_adapter.add("재미있게",1);
//        m_chatmessage_adapter.add("재미있게",0);
//        m_chatmessage_adapter.add("2015/11/20",2);
//        m_chatmessage_adapter.add("재미있게",1);
//        m_chatmessage_adapter.add("재미있게",1);

        return view;
    }

}
