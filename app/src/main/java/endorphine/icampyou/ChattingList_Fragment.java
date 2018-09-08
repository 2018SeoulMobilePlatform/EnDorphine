package endorphine.icampyou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ChattingList_Fragment extends Fragment {

    TypedArray need_pics;
    String[] user_ids;
    String[] need_things;
    String[] lettable_things;

    String pass_user;
    String pass_need;
    String pass_lettable;

    List<Chat_Item> chatItems;
    ListView chat_listview;

    final int save_info = 1;

    static ChatList_Adapter chatList_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.e("onCreatView","냥ㅋㅋ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chattinglist,container,false);

        Log.e("onCreatView","냥냥");

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

        chatItems = new ArrayList<Chat_Item>();

        need_pics = getResources().obtainTypedArray(R.array.need_pics);
        user_ids = getResources().getStringArray(R.array.user_ids);
        need_things = getResources().getStringArray(R.array.need_things);
        lettable_things = getResources().getStringArray(R.array.lettable_things);

        for(int i=0;i<user_ids.length;i++){
            Chat_Item chat_item = new Chat_Item(need_pics.getResourceId(i,-1),user_ids[i],
                    need_things[i],lettable_things[i]);
            chatItems.add(chat_item);
        }

        chat_listview = (ListView) view.findViewById(R.id.chat_listview);
        chatList_adapter = new ChatList_Adapter(getActivity(),chatItems);
        chat_listview.setAdapter(chatList_adapter);

        //채팅방 들어가기
        chat_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view ,int position,long id){
                ChattingMessage_Fragment message_fragment = new ChattingMessage_Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main,message_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //채팅 목록 삭제
        chat_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long arg3) {

                DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chatItems.remove(position);
                        chatList_adapter.notifyDataSetChanged();
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

//        Bundle args =getArguments();
//        if(args != null) {
//            Log.e("user", getArguments().getString("user"));
//            Log.e("need", getArguments().getString("need"));
//            Log.e("lettable", getArguments().getString("lettable"));
//            Chat_Item chat_item = new Chat_Item(need_pics.getResourceId(0,-1)
//                    ,getArguments().getString("user"),
//                    getArguments().getString("need"),
//                    getArguments().getString("lettable"));
//            chatList_adapter.add(chat_item);
//            chatList_adapter.notifyDataSetChanged();
//        }


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        String pass_user = data.getStringExtra("user");
        String pass_need =data.getStringExtra("need");
        String pass_lettable = data.getStringExtra("lettable");
        Log.e("user1",pass_user);
        Log.e("need2",pass_need);
        Log.e("lettable3",pass_lettable);
        Chat_Item chat_item = new Chat_Item(need_pics.getResourceId(0,-1)
                ,pass_user,
                pass_need,
                pass_lettable);
        chatList_adapter.add(chat_item);
        chat_listview.setAdapter(chatList_adapter);
    }
}
