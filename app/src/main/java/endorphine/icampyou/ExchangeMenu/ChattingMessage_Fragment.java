package endorphine.icampyou.ExchangeMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.ExchangeMenu.ChatMessage_Adapter;
import endorphine.icampyou.R;

public class ChattingMessage_Fragment extends BaseFragment {

    ListView m_chatMessage_listView;
    ChatMessage_Adapter m_chatmessage_adapter;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatmessage,container,false);

        m_chatmessage_adapter = new ChatMessage_Adapter();
        m_chatMessage_listView = (ListView)view.findViewById(R.id.chatmessage_listView);

        m_chatMessage_listView.setAdapter(m_chatmessage_adapter);

        final EditText send_message = (EditText)view.findViewById(R.id.editText1);

        m_chatmessage_adapter.add("냥냥",0);

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

        return view;
    }

}
