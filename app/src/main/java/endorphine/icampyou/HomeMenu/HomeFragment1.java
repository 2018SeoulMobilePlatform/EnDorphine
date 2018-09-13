package endorphine.icampyou.HomeMenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.R;

/**
 * 홈 화면 프래그먼트1 클래스
 */
public class HomeFragment1 extends BaseFragment {
    private View view;

    // 프래그먼트 xml 설정하는 메소드
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home_1);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_1, container, false);
        ImageView imgView = (ImageView) view.findViewById(R.id.animationImage);
        imgView.setVisibility(ImageView.VISIBLE);
        imgView.setBackgroundResource(R.drawable.simple_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) imgView.getBackground();
        frameAnimation.start();
        return view;
    }
}
