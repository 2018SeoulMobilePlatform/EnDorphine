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
        View view = inflater.inflate(R.layout.fragment_chattinglist, null);
        return view;
    }

}
