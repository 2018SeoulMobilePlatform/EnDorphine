package endorphine.icampyou.ExchangeMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.R;

import static android.app.Activity.RESULT_OK;

public class ChattingList_Fragment extends BaseFragment {

    private EditText editSearch;

    final int save_info = 1;

    ArrayList<Chat_Item> copy;
    ArrayList<Chat_Item> items;
    ChatList_Adapter adapter;
    ListView chatlist_listView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chattinglist,container,false);

        // 탭 호스트에 탭 추가
        TabHost tabHost1 = (TabHost)view.findViewById(R.id.tapHost_chatlist);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1_chatlist);
        ts1.setIndicator("캠핑장 채팅");
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2_chatlist);
        ts2.setIndicator("나의 채팅");
        tabHost1.addTab(ts2);

        //채팅방 목록 생성하는 버튼
        Button add_chatlist_btn = (Button) view.findViewById(R.id.make_chatlist_button);
        add_chatlist_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),Chat_Content.class);
                startActivityForResult(intent,save_info);
            }
        });

        items = new ArrayList<>();
        copy = new ArrayList<>();

        items.add(new Chat_Item(null,"1","1","1","1"));
        items.add(new Chat_Item(null,"2","2","2","2"));

        copy.addAll(items);
        chatlist_listView =(ListView) view.findViewById(R.id.camp_chat_listview);

        adapter = new ChatList_Adapter(items);

        chatlist_listView.setAdapter(adapter);

        //채팅방 들어가기
        chatlist_listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view ,int position,long id){
                startFragment(getFragmentManager(),ChattingMessage_Fragment.class);

            }
        });

        //채팅 목록 삭제
        chatlist_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long arg3) {

                DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("position",String.valueOf(position));
                        items.remove(position);
                        chatlist_listView.setAdapter(adapter);
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
                        .setPositiveButton("예",positiveListener)
                        .setNegativeButton("취소",cancelListener).show();

                return true;
            }
        });

        editSearch = (EditText)view.findViewById(R.id.search);
        editSearch.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable edit) {
                String text = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                search(text);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        Log.e("requestCode",String.valueOf(requestCode));
        Log.e("resultCode",String.valueOf(resultCode));

        if(resultCode != RESULT_OK){
            return ;
        }
        String pass_user = data.getStringExtra("user");
        String pass_need =data.getStringExtra("need");
        String pass_lettable = data.getStringExtra("lettable");
        String camp_name = data.getStringExtra("camp_name");
        byte[] image_byte = data.getByteArrayExtra("image");
        Bitmap pass_image = BitmapFactory.decodeByteArray(image_byte,0,image_byte.length);
        adapter.add(pass_image,pass_user,pass_need,pass_lettable,camp_name);
        chatlist_listView.setAdapter(adapter);
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        items.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            items.addAll(copy);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < copy.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (copy.get(i).getUser_id().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    items.add(copy.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        chatlist_listView.setAdapter(adapter);
    }
}
