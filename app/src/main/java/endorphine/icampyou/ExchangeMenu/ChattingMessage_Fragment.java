package endorphine.icampyou.ExchangeMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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



import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.ExchangeMenu.ChatMessage_Adapter;
import endorphine.icampyou.R;

public class ChattingMessage_Fragment extends BaseFragment {

    ListView m_chatMessage_listView;
    ChatMessage_Adapter m_chatmessage_adapter;
    View view;

    EditText send_message;

    //서버
    Socket mSocket = null;
    {
        try {
            mSocket = IO.socket("http://192.168.1.67:8000");
        } catch (URISyntaxException e) {}
    }

    //서버에서 전송 된다면
    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username1;
                    String username2;
                    String message;
                    try {
                        username1 = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    m_chatmessage_adapter.add(message,0);
                    m_chatmessage_adapter.notifyDataSetChanged();
                }
            });
        }
    };


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chatmessage,container,false);

        mSocket.on("new_private_message", onNewMessage);
        mSocket.connect();
        attemptLoginSignal();

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
                    attemptSend();
                    send_message.setText("");
                    m_chatmessage_adapter.notifyDataSetChanged();

                }
            }
        });

        return view;
    }

    //로그인했다는 표시
    private void attemptLoginSignal(){
        String user_id = "홍길동";
        if (TextUtils.isEmpty(user_id)) {
            return;
        }
        mSocket.emit("username",user_id);
    }

    //채팅 전송
    private void attemptSend() {
        String message = send_message.getText().toString();

        JSONObject data = new JSONObject();
        try {
            data.put("to_username", "홍길동");
            data.put("from_username","홍길동");
            data.put("message", message);
            mSocket.emit("private_message", data);
        } catch(JSONException e) {
            e.printStackTrace();
        }

//        if (TextUtils.isEmpty(message)) {
//            return;
//        }
//
//        send_message.setText("");
//        mSocket.emit("private_message", "seyoung babu");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }
}
