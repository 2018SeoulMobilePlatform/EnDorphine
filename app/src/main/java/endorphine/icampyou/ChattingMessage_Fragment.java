package endorphine.icampyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChattingMessage_Fragment extends Fragment {

    ListView m_chatMessage_listView;
    ChatMessage_Adapter m_chatmessage_adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatmessage,container,false);

        m_chatmessage_adapter = new ChatMessage_Adapter();
        m_chatMessage_listView = (ListView)view.findViewById(R.id.chatmessage_listView);

        m_chatMessage_listView.setAdapter(m_chatmessage_adapter);

        m_chatmessage_adapter.add("이건 뭐지",1);
        m_chatmessage_adapter.add("쿨쿨",1);
        m_chatmessage_adapter.add("쿨쿨쿨쿨",0);
        m_chatmessage_adapter.add("재미있게",1);
        m_chatmessage_adapter.add("놀자라구나힐힐 감사합니다. 동해물과 백두산이 마르고 닳도록 놀자 놀자 우리 놀자",1);
        m_chatmessage_adapter.add("재미있게",1);
        m_chatmessage_adapter.add("재미있게",0);
        m_chatmessage_adapter.add("2015/11/20",2);
        m_chatmessage_adapter.add("재미있게",1);
        m_chatmessage_adapter.add("재미있게",1);

        return view;
    }

}
