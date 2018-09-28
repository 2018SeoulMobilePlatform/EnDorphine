package endorphine.icampyou.ExchangeMenu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import endorphine.icampyou.R;

public class ChattingMessageActivity extends AppCompatActivity {

    ListView m_chatMessage_listView;
    ChatMessage_Adapter m_chatmessage_adapter;

    EditText send_message;

    SharedPreferences preferences;

    String other;

    //서버
    Socket mSocket = null;
    {
        try {
            mSocket = IO.socket("http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000");
        } catch (URISyntaxException e) {}
    }

    //서버에서 전송 된다면
    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_message);

        mSocket.on("new_private_message", onNewMessage);
        mSocket.connect();

        if(getIntent() != null){
            other = getIntent().getStringExtra("other");
        }

        preferences = getSharedPreferences("preferences",MODE_PRIVATE);

        attemptLoginSignal();

        m_chatmessage_adapter = new ChatMessage_Adapter();
        m_chatMessage_listView = (ListView)findViewById(R.id.chatmessage_listView);

        m_chatMessage_listView.setAdapter(m_chatmessage_adapter);

        send_message = (EditText)findViewById(R.id.editText1);

        findViewById(R.id.send_btn).setOnClickListener(new Button.OnClickListener(){
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

    }

    //로그인했다는 표시
    private void attemptLoginSignal(){
        String user_id = preferences.getString("nickname","");
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
            data.put("to_username", other);
            data.put("from_username",preferences.getString("nickname",""));
            data.put("message", message);
            mSocket.emit("private_message", data);
        } catch(JSONException e) {
            e.printStackTrace();

        }
    }

}
