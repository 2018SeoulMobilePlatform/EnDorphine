package endorphine.icampyou;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChattingList_Fragment extends Fragment {

    TypedArray need_pics;
    String[] user_ids;
    String[] need_things;
    String[] lettable_things;

    List<Chat_Item> chatItems;
    ListView chat_listview;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chattinglist,null);

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
        ChatList_Adapter chatList_adapter = new ChatList_Adapter(getActivity(),chatItems);
        chat_listview.setAdapter(chatList_adapter);

//        String[] chat_items = {"이다인","박지호","허진규","전세영","최재성","임석호","주영준","김예진"};
//
//        ListView listView = (ListView) view.findViewById(R.id.chat_listview);
//
//        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,chat_items);
//
//        listView.setAdapter(listViewAdapter);

        return view;
    }


}
