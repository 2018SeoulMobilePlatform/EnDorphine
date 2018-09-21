package endorphine.icampyou.GuideMenu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import endorphine.icampyou.R;

import static endorphine.icampyou.GuideMenu.CalenderActivity.RESULT_SELECT_END_VIEW_DATE;
import static endorphine.icampyou.GuideMenu.CalenderActivity.RESULT_SELECT_START_VIEW_DATE;

public class TentSelectActivity extends AppCompatActivity
         {
    //private Button mButton;
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;

    public int tentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tent_select);

        Intent intent = new Intent(this.getIntent());

        final String startDate = intent.getStringExtra(RESULT_SELECT_START_VIEW_DATE);
        final String endDate = intent.getStringExtra(RESULT_SELECT_END_VIEW_DATE);

        TextView periodTextView = (TextView) findViewById(R.id.period);
        periodTextView.setText("예약 기간 : " + startDate + " ~ " + endDate);

        TextView campingTextView = (TextView) findViewById(R.id.selectedTitle);
        campingTextView.setText(intent.getExtras().getString("camping_name"));

        final String campName = intent.getExtras().getString("camping_name");

        Log.e("DetailsActivity",intent.getStringExtra("camping_name"));


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        //mButton = (Button) findViewById(R.id.cardTypeBtn);
        //((CheckBox) findViewById(R.id.checkBox)).setOnCheckedChangeListener(this);
        //mButton.setOnClickListener(this);

        makeCardView(campName);

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        mCardShadowTransformer.enableScaling(true);
        mFragmentCardShadowTransformer.enableScaling(true);

        final ElegantNumberButton numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        numberButton.setRange(1,5);

        RelativeLayout paymentButton = (RelativeLayout) findViewById(R.id.payment);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tentNum = mViewPager.getCurrentItem();
                Log.e("씨발", Integer.toString(tentNum));
                String tentName = classifyTent(tentNum, campName);
                Log.e("개새끼", tentName);
            }
        });
    }


    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public String classifyTent(int tentNum, String campName) {

        switch(campName) {

            case "난지 캠핑장":
                if(tentNum == 0)
                    return "가족 텐트";
                else if(tentNum == 1)
                    return "몽골 텐트_대";
                else if(tentNum == 2)
                    return "몽골 텐트_대(냉_난방 포함)";
                else if(tentNum == 3)
                    return "몽골 텐트_중";
                else if(tentNum == 4)
                    return "몽골 텐트_중(냉_난방 포함)";
                else if(tentNum == 5)
                    return "몽골 텐트_특대(5x4)";
                else if(tentNum == 6)
                    return "몽골 텐트_(5x5)";
                else if(tentNum == 7)
                    return "캐빈 텐트";
                else
                    return "캐빈 텐트(냉_난방 포함)";

            case "노을 캠핑장":
                if(tentNum == 0)
                    return "A 구역";
                else if(tentNum == 1)
                    return "B 구역";
                else if(tentNum == 2)
                    return "C 구역";
                else
                    return "D구역";

            case "강동 캠핑장":
                if(tentNum == 0)
                    return "오토 캠핑장";
                else if(tentNum == 1)
                    return "가족 캠핑장";
                else
                    return "매화나무 캠핑장";

            case "초안산 캠핑장":
                if(tentNum == 0)
                    return "일반 캠핑";
                else if(tentNum == 1)
                    return "오토 캠핑";
                else if(tentNum == 2)
                    return "데크 캠핑";
                else
                    return "캐빈하우스";

            default:
                return "4인용";
        }
    }


    public void makeCardView(String campName) {

        mCardAdapter = new CardPagerAdapter();

        switch(campName) {

            case "난지 캠핑장":
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_1, R.drawable.tent_1, R.string.price_1));
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_2, R.drawable.tent_2, R.string.price_2));
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_3, R.drawable.tent_3, R.string.price_3));
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_4, R.drawable.tent_4, R.string.price_4));
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_5, R.drawable.tent_5, R.string.price_5));
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_6, R.drawable.tent_6, R.string.price_6));
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_7, R.drawable.tent_7, R.string.price_7));
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_8, R.drawable.tent_8, R.string.price_8));
                mCardAdapter.addCardItem(new CardItem(R.string.nanji_9, R.drawable.tent_9, R.string.price_9));
                break;

            case "노을 캠핑장":
                mCardAdapter.addCardItem(new CardItem(R.string.noeul_1, R.drawable.tent_1, R.string.noeul_price_1));
                mCardAdapter.addCardItem(new CardItem(R.string.noeul_2, R.drawable.tent_2, R.string.noeul_price_2));
                mCardAdapter.addCardItem(new CardItem(R.string.noeul_3, R.drawable.tent_3, R.string.noeul_price_3));
                mCardAdapter.addCardItem(new CardItem(R.string.noeul_4, R.drawable.tent_4, R.string.noeul_price_4));
                break;

            case "강동 캠핑장":
                mCardAdapter.addCardItem(new CardItem(R.string.gangdong_1, R.drawable.tent_1, R.string.gangdong_price_1));
                mCardAdapter.addCardItem(new CardItem(R.string.gangdong_2, R.drawable.tent_2, R.string.gangdong_price_2));
                mCardAdapter.addCardItem(new CardItem(R.string.gangdong_3, R.drawable.tent_3, R.string.gangdong_price_3));

            case "초안산 캠핑장":
                mCardAdapter.addCardItem(new CardItem(R.string.choan_1, R.drawable.tent_1, R.string.choan_price_1));
                mCardAdapter.addCardItem(new CardItem(R.string.choan_2, R.drawable.tent_2, R.string.choan_price_2));
                mCardAdapter.addCardItem(new CardItem(R.string.choan_3, R.drawable.tent_3, R.string.choan_price_3));
                mCardAdapter.addCardItem(new CardItem(R.string.choan_4, R.drawable.tent_4, R.string.choan_price_4));
                break;

            default:
                mCardAdapter.addCardItem(new CardItem(R.string.seoul_1, R.drawable.tent_1, R.string.seoul_price_1));
                break;
        }
    }
}
