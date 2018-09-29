package endorphine.icampyou.ExchangeMenu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.Constant;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ChattingList_Fragment extends BaseFragment {

    private EditText editSearch;

    final int save_info = 1;

    ArrayList<Chat_Item> copy;
    ArrayList<Chat_Item> copy2;
    ChatList_Adapter campList_adapter;
    ChatList_Adapter myList_adapter;
    ListView chatlist_listView;
    ListView mylist_listView;


    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chattinglist, container, false);

        preferences = getActivity().getSharedPreferences("preferences", MODE_PRIVATE);

        listViewSetting(view);

        // 탭 호스트에 탭 추가
        final TabHost tabHost1 = (TabHost) view.findViewById(R.id.tapHost_chatlist);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        View tabView1 = createTabView(tabHost1.getContext(), "캠핑장 채팅");
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("캠핑장 채팅").setIndicator(tabView1);
        ts1.setContent(R.id.content1_chatlist);
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        View tabView2 = createTabView(tabHost1.getContext(), "나의 채팅");
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("나의 채팅").setIndicator(tabView2);
        ts2.setContent(R.id.content2_chatlist);
        tabHost1.addTab(ts2);

        // 탭 선택하면 탭 위젯 텍스트 색상 바뀌게 설정
        tabHost1.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabHost1.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) tabHost1.getTabWidget().getChildAt(i).findViewById(R.id.tabs_text); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ACACAC"));
                }
                TextView tv = (TextView) tabHost1.getCurrentTabView().findViewById(R.id.tabs_text); //for Selected Tab
                tv.setTextColor(Color.parseColor("#13B9A5"));
            }
        });

        GlideApp.with(view).load(R.drawable.search).into((ImageView) view.findViewById(R.id.search_image));

        //채팅방 목록 생성하는 버튼
        FloatingActionButton add_chatlist_btn = (FloatingActionButton) view.findViewById(R.id.make_chatlist_button);
        GlideApp.with(view).load(R.drawable.review_plus_icon).into(add_chatlist_btn);
        add_chatlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Chat_Content.class);
                startActivityForResult(intent, save_info);
            }
        });

        //채팅방 들어가기
        chatlist_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((Chat_Item) campList_adapter.getItem(position)).getNickname().equals(preferences.getString("nickname", ""))) {
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/chatroom/getflag";

                    JSONObject data = JSONData(((Chat_Item) campList_adapter.getItem(position)).getNumber());

                    NetworkTask networkTask = new NetworkTask(getActivity(), url, data, Constant.GET_OPPONENT, false,((Chat_Item) campList_adapter.getItem(position)).getNumber());
                    networkTask.execute();
                } else if (((Chat_Item) campList_adapter.getItem(position)).getFlag().equals("0")) {
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/update/chatroomflag";
                    JSONObject data = setOtherJSONdata(((Chat_Item) campList_adapter.getItem(position)).getNumber(), preferences.getString("nickname", ""));
                    NetworkTask networkTask = new NetworkTask(getActivity(), url, data, Constant.SET_OPPONENT, ((Chat_Item) campList_adapter.getItem(position)).getNumber(), ((Chat_Item) campList_adapter.getItem(position)).getNickname());
                    networkTask.execute();
                } else {
                    String url2 = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/chatroom/getflag";

                    JSONObject data2 = getOpponentData(((Chat_Item) campList_adapter.getItem(position)).getNumber());

                    NetworkTask networkTask = new NetworkTask(getActivity(), url2, data2, Constant.GET_OPPONENT, true,
                            ((Chat_Item) campList_adapter.getItem(position)).getNumber(), ((Chat_Item) campList_adapter.getItem(position)).getNickname());
                    networkTask.execute();
                }
            }
        });

        mylist_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((Chat_Item) myList_adapter.getItem(position)).getNickname().equals(preferences.getString("nickname", ""))) {
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/chatroom/getflag";

                    JSONObject data = JSONData(((Chat_Item) myList_adapter.getItem(position)).getNumber());

                    NetworkTask networkTask = new NetworkTask(getActivity(), url, data, Constant.GET_OPPONENT, false,((Chat_Item) myList_adapter.getItem(position)).getNumber());
                    networkTask.execute();
                } else if (((Chat_Item) myList_adapter.getItem(position)).getFlag().equals("0")) {
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/update/chatroomflag";
                    JSONObject data = setOtherJSONdata(((Chat_Item) myList_adapter.getItem(position)).getNumber(), preferences.getString("nickname", ""));
                    NetworkTask networkTask = new NetworkTask(getActivity(), url, data, Constant.SET_OPPONENT, ((Chat_Item) myList_adapter.getItem(position)).getNumber(), ((Chat_Item) campList_adapter.getItem(position)).getNickname());
                    networkTask.execute();
                } else {
                    String url2 = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/chatroom/getflag";

                    JSONObject data2 = getOpponentData(((Chat_Item) myList_adapter.getItem(position)).getNumber());

                    NetworkTask networkTask = new NetworkTask(getActivity(), url2, data2, Constant.GET_OPPONENT, true,
                            ((Chat_Item) myList_adapter.getItem(position)).getNumber(), ((Chat_Item) myList_adapter.getItem(position)).getNickname());
                    networkTask.execute();
                }
            }
        });

        //채팅 목록 삭제
        chatlist_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long arg3) {

                DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (preferences.getString("nickname", "").equals(
                                ((Chat_Item) campList_adapter.getItem(position)).getNickname().toString())) {
                            String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/chatroom/remove";
                            JSONObject data = removeJSONDate(((Chat_Item) campList_adapter.getItem(position)).getNumber());
                            NetworkTask networkTask = new NetworkTask(getActivity(), url, data, Constant.REMOVE_CHATTINGLIST);
                            networkTask.execute();
                            copy.remove(position);
                            myList_adapter.removeObject(((Chat_Item)campList_adapter.getItem(position)));
                            campList_adapter.removeItem(position);
                            campList_adapter.notifyDataSetChanged();
                            myList_adapter.notifyDataSetChanged();
                        } else {
                            Toast toast = Toast.makeText(getActivity(),
                                    "사용자가 생성한 채팅방만 삭제할 수 있습니다", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(getActivity())
                        .setTitle("채팅 목록을 삭제하시겠습니까?")
                        .setPositiveButton("예", positiveListener)
                        .setNegativeButton("취소", cancelListener).show();

                return true;
            }
        });

        editSearch = (EditText) view.findViewById(R.id.search);
        editSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable edit) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                search(text, campList_adapter, copy);
                search(text, myList_adapter, copy2);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {

            }
        });

        //검색 텍스트 모두 지우기
        ImageButton remove_allText_btn = (ImageButton) view.findViewById(R.id.remove_allText_button);
        remove_allText_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.setText("");
            }
        });

        campList_adapter.removeAllitem();
        myList_adapter.removeAllitem();

        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/chatroom/getallroom";

        JSONObject data = sendJSonData();

        NetworkTask networkTask = new NetworkTask(getActivity(), url, data, Constant.GET_CHATTINGLIST, campList_adapter, copy, myList_adapter, copy2);
        networkTask.execute();

        return view;
    }

    //리스트 뷰 세팅
    private void listViewSetting(View view) {
        chatlist_listView = (ListView) view.findViewById(R.id.camp_chat_listview);
        campList_adapter = new ChatList_Adapter();

        mylist_listView = (ListView) view.findViewById(R.id.my_chat_listview);
        myList_adapter = new ChatList_Adapter();

        chatlist_listView.setAdapter(campList_adapter);
        mylist_listView.setAdapter(myList_adapter);

        copy = new ArrayList<>();
        copy2 = new ArrayList<>();

    }

    // 검색을 수행하는 메소드
    public void search(String charText, ChatList_Adapter adapter, ArrayList<Chat_Item> copy) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        adapter.removeAllitem();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            for (Chat_Item mItem : copy) {
                adapter.addItem(mItem);
            }
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < copy.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (copy.get(i).getNickname().toLowerCase().contains(charText) ||
                        copy.get(i).getNeed_thing().toLowerCase().contains(charText) ||
                        copy.get(i).getCamping_name().toLowerCase().contains(charText) ||
                        copy.get(i).getLettable_thing().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    adapter.addItem(copy.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // Tab에 나타날 View를 구성
    private View createTabView(final Context context, final String text) {
        // layoutinflater를 이용해 xml 리소스를 읽어옴
        View view = LayoutInflater.from(context).inflate(R.layout.image_tab, null);

        ImageView img;

        if (text.equals("캠핑장 채팅")) {
            img = (ImageView) view.findViewById(R.id.tabs_image);
            GlideApp.with(view).load(R.drawable.tent1).into(img);
        } else if (text.equals("나의 채팅")) {
            img = (ImageView) view.findViewById(R.id.tabs_image);
            GlideApp.with(view).load(R.drawable.tent1_2).into(img);
        }

        TextView tv = (TextView) view.findViewById(R.id.tabs_text);
        tv.setText(text);
        return view;
    }

    //POST 요청 JSON 데이터 형식 사용
    private JSONObject sendJSonData() {

        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }

    //채팅 목록 삭제 요청 데이터
    private JSONObject removeJSONDate(String number) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("number", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //채팅방에 아이디 설정
    private JSONObject setOtherJSONdata(String number, String other) {
        JSONObject data = new JSONObject();

        try {
            data.accumulate("number", number);
            data.accumulate("flag", "1");
            data.accumulate("opponent", other);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    //채팅방 상대방 가져오기
    private JSONObject getOpponentData(String number) {
        JSONObject data = new JSONObject();

        try {
            data.accumulate("number", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    private JSONObject JSONData(String number) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("number", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            campList_adapter.removeAllitem();
            myList_adapter.removeAllitem();

            String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/chatroom/getallroom";

            JSONObject data2 = sendJSonData();

            NetworkTask networkTask = new NetworkTask(getActivity(), url, data2, Constant.GET_CHATTINGLIST, campList_adapter, copy, myList_adapter, copy2);
            networkTask.execute();
        }
    }
}
