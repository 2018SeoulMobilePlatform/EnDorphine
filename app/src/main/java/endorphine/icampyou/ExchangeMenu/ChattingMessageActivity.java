package endorphine.icampyou.ExchangeMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import endorphine.icampyou.Constant;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class ChattingMessageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView backButton;
    ListView m_chatMessage_listView;
    ChatMessage_Adapter m_chatmessage_adapter;

    EditText send_message;

    SharedPreferences preferences;

    String opponent;

    String room_number;
    //서버
    Socket mSocket = null;

    {
        try {
            mSocket = IO.socket("http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000");
        } catch (URISyntaxException e) {
        }
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
                    m_chatmessage_adapter.add(message, 0);
                    m_chatmessage_adapter.notifyDataSetChanged();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_message);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);

        m_chatmessage_adapter = new ChatMessage_Adapter();


        Intent intent = getIntent();

        opponent = intent.getStringExtra("opponent");
        room_number = intent.getStringExtra("number");

        mSocket.on("new_private_message", onNewMessage);
        mSocket.connect();

        attemptLoginSignal();

        m_chatMessage_listView = (ListView) findViewById(R.id.chatmessage_listView);

        m_chatMessage_listView.setAdapter(m_chatmessage_adapter);

        send_message = (EditText) findViewById(R.id.editText1);

        findViewById(R.id.send_btn).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (send_message.length() == 0) {
                    return;
                } else {
                    m_chatmessage_adapter.add(send_message.getText().toString(), 1);
                    attemptSend();
                    send_message.setText("");
                    m_chatmessage_adapter.notifyDataSetChanged();
                }
            }
        });

        // back 버튼 설정
        backButton = findViewById(R.id.chat_message_back_btn);
        backButton.setOnClickListener(this);
        GlideApp.with(this).load(R.drawable.chat_out).into(backButton);

        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/getchat";

        JSONObject data = sendJSonData();

        NetworkTask networkTask = new NetworkTask(ChattingMessageActivity.this, url, data, Constant.GET_CHATTINGMESSAGELIST, m_chatmessage_adapter);
        networkTask.execute();
    }

    //로그인했다는 표시
    private void attemptLoginSignal() {
        String user_id = preferences.getString("nickname", "");
        if (TextUtils.isEmpty(user_id)) {
            return;
        }
        mSocket.emit("username", user_id);
    }

    //채팅 전송
    private void attemptSend() {
        String message = send_message.getText().toString();

        JSONObject data = new JSONObject();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String getTime = sdf.format(date);
        Log.e("Time", getTime);

        try {
            data.put("to_username", opponent);
            data.put("from_username", preferences.getString("nickname", ""));
            data.put("message", message);
            data.put("datetime", getTime);
            data.put("number",room_number);
            mSocket.emit("private_message", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject sendJSonData() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("from_id", preferences.getString("nickname", ""));
            jsonObject.accumulate("to_id", opponent);
            jsonObject.accumulate("number",room_number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private JSONObject setFlagData() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("nickname", preferences.getString("nickname", ""));
            jsonObject.accumulate("flag", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private JSONObject setChatRoomFlag(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("number", room_number);
            jsonObject.accumulate("flag", "0");
            jsonObject.accumulate("opponent","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public void onBackPressed() {

        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/update/userflag";

        JSONObject data = setFlagData();

        NetworkTask networkTask = new NetworkTask(ChattingMessageActivity.this, url, data, Constant.SET_FLAG);
        networkTask.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("서버 끊기","냥냥");
        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chat_message_back_btn) {

            DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/update/chatroomflag";

                    JSONObject data = setChatRoomFlag();

                    NetworkTask networkTask = new NetworkTask(ChattingMessageActivity.this, url, data,Constant.SET_CHATTINGFLAG);
                    networkTask.execute();
                }
            };

            DialogInterface.OnClickListener nagetiveListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(ChattingMessageActivity.this)
                    .setTitle("채팅방을 나가시겠습니까?")
                    .setPositiveButton("예", positiveListener)
                    .setNegativeButton("취소", nagetiveListener).show();
        }
    }
}
