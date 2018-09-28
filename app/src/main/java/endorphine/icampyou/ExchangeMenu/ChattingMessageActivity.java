package endorphine.icampyou.ExchangeMenu;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import endorphine.icampyou.Constant;
import endorphine.icampyou.NetworkTask;
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

        preferences = getSharedPreferences("preferences",MODE_PRIVATE);

        if(getIntent() != null){
            other = getIntent().getStringExtra("other");
            setTitle(other);
        }

        m_chatmessage_adapter = new ChatMessage_Adapter();

        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/getchat";

        JSONObject data = sendJSonData();

        NetworkTask networkTask = new NetworkTask(ChattingMessageActivity.this,url,data,Constant.GET_CHATTINGMESSAGELIST,m_chatmessage_adapter);
        networkTask.execute();

        mSocket.on("new_private_message", onNewMessage);
        mSocket.connect();



        attemptLoginSignal();

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
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);

        try {
            data.put("to_username", other);
            data.put("from_username",preferences.getString("nickname",""));
            data.put("message", message);
            data.put("datetime",getTime);
            mSocket.emit("private_message", data);
        } catch(JSONException e) {
            e.printStackTrace();

        }
    }

    private JSONObject sendJSonData(){
        JSONObject jsonObject = new JSONObject();

        try {
            Log.e("from_id",preferences.getString("nickname",""));
            Log.e("to_id",other);
            jsonObject.accumulate("from_id",preferences.getString("nickname",""));
            jsonObject.accumulate("to_id",other);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
