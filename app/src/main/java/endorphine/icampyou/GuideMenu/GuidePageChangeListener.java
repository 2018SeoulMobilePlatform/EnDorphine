package endorphine.icampyou.GuideMenu;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import endorphine.icampyou.R;

public class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

    private ImageView[] imageViews;

    public GuidePageChangeListener(ImageView[] imageViews) {
        super();
        this.imageViews = imageViews;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[position].setBackgroundResource(R.drawable.circle_white);
            if (position != i) {
                imageViews[i].setBackgroundResource(R.drawable.circle_grey);
            }
        }
    }
}
