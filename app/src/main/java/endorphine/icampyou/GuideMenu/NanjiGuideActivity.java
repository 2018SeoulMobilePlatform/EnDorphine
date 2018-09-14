package endorphine.icampyou.GuideMenu;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;

import com.melnykov.fab.FloatingActionButton;

import endorphine.icampyou.EventMenu.EventListViewAdapter;
import endorphine.icampyou.EventMenu.EventListViewItem;
import endorphine.icampyou.HomeActivity;
import endorphine.icampyou.R;

public class NanjiGuideActivity extends Activity implements View.OnClickListener{

    private ArrayList<View> pageViews;    // 사진 View
    private ImageView pointImage;        // 동그라미 포인트
    private ImageView[] pointImages;    // 동그라미 포인트 모음
    private ViewGroup viewLayout;         // 레이아웃
    private ViewGroup viewPoints;       // 동그라미 포인트들
    private ViewPager viewPager;        // 사진들
    private Button reservationButton;   // 예약 버튼
    private ListView reviewList;         // 후기 리스트
    private ArrayList<ReviewListItem> reviewData;   // 후기 데이터
    private ReviewListViewAdapter adapter;  //후기 리스트뷰 어댑터
    private ScrollView scrollView;  // 스크롤뷰
    private LinearLayout layout;    // 두번째 후기 탭
    private FloatingActionButton reviewAddButton;   // 후기작성버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 페이지뷰에 사진 페이지 저장
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        pageViews.add(inflater.inflate(R.layout.page01, null));
        pageViews.add(inflater.inflate(R.layout.page02, null));
        pageViews.add(inflater.inflate(R.layout.page03, null));

        // ImageViews = 동그라미들
        pointImages = new ImageView[pageViews.size()];
        // activity_nanji_guide.xml 설정
        viewLayout = (ViewGroup) inflater.inflate(R.layout.activity_nanji_guide, null);

        // viewPoints = 동그라미 점들 있는 view, viewPager = 사진 페이지 부분 view
        viewPoints = (ViewGroup) viewLayout.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) viewLayout.findViewById(R.id.guidePages);

        // pageViews = 사진 개수
        // 사진 개수에 맞게 동그라미 설정
        for (int i = 0; i < pageViews.size(); i++) {
            pointImage = new ImageView(NanjiGuideActivity.this);
            pointImage.setLayoutParams(new LayoutParams(30, 30));    // 동그라미 크기
            pointImage.setPadding(30, 0, 20, 0); // 동그라미 padding 설정
            pointImages[i] = pointImage;

            // 하나만 하얀색이고 나머지는 그레이로 설정
            if (i == 0) {
                pointImages[i].setBackgroundResource(R.drawable.circle_white);
            } else {
                pointImages[i].setBackgroundResource(R.drawable.circle_grey);
            }

            // 뷰에 붙이기
            viewPoints.addView(pointImages[i]);
        }
        setContentView(viewLayout);

        // page adapter랑 listener 설정
        viewPager.setAdapter(new GuidePageAdapter(pageViews, this));
        viewPager.setOnPageChangeListener(new GuidePageChangeListener(pointImages));

        // 탭 호스트에 탭 추가
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("정보");
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("후기");
        tabHost1.addTab(ts2);

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"content3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("위치");
        tabHost1.addTab(ts3);

        // 예약버튼 이벤트 설정
        reservationButton = (Button)viewLayout.findViewById(R.id.reservation_button);
        reservationButton.setOnClickListener(this);

        // 페이지 떴을 때 항상 스크롤 맨위에 가있도록
        scrollView = (ScrollView)findViewById(R.id.nanji_guide_scrollView);
        upScroll();

        // 후기 리스트 설정
        reviewList = (ListView)findViewById(R.id.review_listView);

        // 후기 데이터 설정
        reviewData = new ArrayList<>();

        // 후기 아이템들 추가
        ReviewListItem review1 = new ReviewListItem(R.drawable.user_icon,"이다콩","★★★☆☆",R.drawable.nanji_1);
        ReviewListItem review2 = new ReviewListItem(R.drawable.user_icon,"김다콩","★★★☆☆",R.drawable.nanji_2);
        ReviewListItem review3 = new ReviewListItem(R.drawable.user_icon,"박다콩","★★★☆☆",0);
        ReviewListItem review4 = new ReviewListItem(R.drawable.user_icon,"김다콩","★★★☆☆",R.drawable.nanji_2);
        ReviewListItem review5 = new ReviewListItem(R.drawable.user_icon,"김다콩","★★★☆☆",R.drawable.nanji_2);
        ReviewListItem review6 = new ReviewListItem(R.drawable.user_icon,"김다콩","★★★☆☆",R.drawable.nanji_2);
        reviewData.add(review1);
        reviewData.add(review2);
        reviewData.add(review3);
        reviewData.add(review4);
        reviewData.add(review5);
        reviewData.add(review6);

        // 어댑터로 후기 리스트에 아이템 뿌려주기
        adapter = new ReviewListViewAdapter(inflater, R.layout.review_listview_item, reviewData);
        reviewList.setAdapter(adapter);

        // 후기작성버튼 설정
        reviewAddButton = findViewById(R.id.review_add_button);

        // 스크롤 맨 위로
        upScroll();
    }

    // 예약버튼 클릭 이벤트 메소드
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reservation_button:
                // 예약 버튼 누르면 캘린더 액티비티 시작됨
                Intent intent = new Intent();
                intent.setClass(this, CalenderActivity.class);
                startActivity(intent);
                break;
        }
    }

    // 스크롤 맨 위로
    public void upScroll(){
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
    }

    // 리스트뷰 높이 자동 맞춤 메소드
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)  return;

        int numberOfItems = listAdapter.getCount();

        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight();
        }

        int totalDividersHeight = listView.getDividerHeight() *  (numberOfItems - 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();


    }

}