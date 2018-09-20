package endorphine.icampyou.EventMenu;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.GuideMenu.ConfirmFragment;
import endorphine.icampyou.HomeActivity;
import endorphine.icampyou.R;

/**
 * 이벤트 상세정보 페이지 프래그먼트
 */
public class EventInfoFragment extends BaseFragment {

    View view;
    TextView eventTitle;
    TextView eventContent;
    int mPosition;

    public EventInfoFragment() {
    }

    public static EventInfoFragment newInstance(int position) {
        EventInfoFragment fragment = new EventInfoFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    // 프래그먼트 xml 설정하는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_event_info, container, false);

        eventTitle = view.findViewById(R.id.event_info_title);
        eventContent = view.findViewById(R.id.event_info_content);

        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
        }
        switch (mPosition) {
            case 0:
                eventTitle.setText("짱짱");
                eventContent.setText("굳굳");
            case 2:
            case 3:
            case 4:
            case 5:
            default:
                eventTitle.setText("땅땅");
                eventContent.setText("꿍꿍");
        }


        return view;
    }

//    // Back키 누르면 앱이 종료되는게 아니라 EventFragment1로 이동하게 설정
//    @Override
//    public void onBack() {
//        if (getArguments() != null) {
//            startFragment(getFragmentManager(), EventFragment1.class);
//        } else {
//            HomeActivity activity = (HomeActivity) getActivity();
//            activity.setOnKeyBackPressedListener(null);
//            activity.onBackPressed();
//        }
//
////        if (mWebView.canGoBack()) {
////            mWebView.goBack();
////        } else {
////            HomeActivity activity = (HomeActivity) getActivity();
////            activity.setOnKeyBackPressedListener(null);
////            activity.onBackPressed();
////        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((HomeActivity) activity).setOnKeyBackPressedListener(this);
//    } // in SearchFragment
}
