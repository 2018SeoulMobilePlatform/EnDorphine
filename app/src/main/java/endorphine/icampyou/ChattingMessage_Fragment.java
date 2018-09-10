package endorphine.icampyou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;



public class ChattingMessage_Fragment extends Fragment {

    ListView m_chatMessage_listView;
    ChatMessage_Adapter m_chatmessage_adapter;

    EditText send_message;

    //서버
    Socket mSocket = null;
    {
        try {
            mSocket = IO.socket("http://192.168.1.67:5000");
        } catch (URISyntaxException e) {}
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    m_chatmessage_adapter.add(message,1);
                }
            });
        }
    };


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatmessage,container,false);

        mSocket.on("new message", onNewMessage);
        mSocket.connect();

        m_chatmessage_adapter = new ChatMessage_Adapter();
        m_chatMessage_listView = (ListView)view.findViewById(R.id.chatmessage_listView);

        m_chatMessage_listView.setAdapter(m_chatmessage_adapter);

        send_message = (EditText)view.findViewById(R.id.editText1);

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
                    attemptSend();
                }
            }
        });

        return view;
    }

    //채팅 전송
    private void attemptSend() {
        String message = send_message.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }

        send_message.setText("");
        mSocket.emit("username", "seyoung babu");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }
}
