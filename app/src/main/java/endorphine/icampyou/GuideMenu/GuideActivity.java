package endorphine.icampyou.GuideMenu;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import endorphine.icampyou.Constant;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.GuideMenu.Reservation.CalenderActivity;
import endorphine.icampyou.GuideMenu.Review.ReviewListItem;
import endorphine.icampyou.GuideMenu.Review.ReviewListViewAdapter;
import endorphine.icampyou.GuideMenu.Review.ReviewWriteActivity;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

/**
 * 캠핑장 안내 액티비티
 */

public class GuideActivity extends Activity implements View.OnClickListener, OnMapReadyCallback {

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
    private int userIcon;   // 유저 아이콘 이미지
    private String nickName;    // 유저 닉네임
    private Intent intent;  // 인텐트
    private LayoutInflater inflater;
    private RatingBar totalReviewStar;  // 총 별점평균
    private TextView totalReviewStarScore;  // 총 별점 평균 스코어
    private MapFragment mapFragment;    // 지도 프래그먼트
    private View page01, page02, page03;    // 상세정보 사진 페이지들
    private ImageView pic01, pic02, pic03;  // 상세정보 사진 페이지들 안에 사진
    private String campingPlace;    // 인텐트로 받아온 캠핑장 정보
    private TextView campingName;   // 캠핑장 이름
    private TextView campingAddress;    // 캠핑장 주소
    private ImageView campingInfoImage; // 캠핑장 상세정보 이미지
    private ImageView mapImage; // 캠핑장 지도 이미지


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE)).getLargeMemoryClass();

        super.onCreate(savedInstanceState);

        // activity_nanji_guide.xml 설정
        inflater = getLayoutInflater();
        viewLayout = (ViewGroup)inflater.inflate(R.layout.activity_guide, null);
        setContentView(viewLayout);

        // 인텐트로 캠핑장 이름 받아오기
        intent = getIntent();

        if(intent.getStringExtra("캠핑장 이름") != null) {
            campingPlace = intent.getStringExtra("캠핑장 이름");
        }

        // 캠핑장 종류에 맞게 정보 페이지뷰에 사진 페이지 저장
        setPageViews();

        // ViewPager 하단에 동그란 포인터 설정
        setViewPagerPointers();

        // ViewPager 어댑터랑 리스너 설정
        viewPager.setAdapter(new GuidePageAdapter(pageViews, this));
        viewPager.setOnPageChangeListener(new GuidePageChangeListener(pointImages));

        // 탭 호스트에 탭 추가
        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        // 탭 선택하면 탭 위젯 텍스트 색상 바뀌게 설정
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // 선택 안된 탭들
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#ACACAC"));
                }
                // 선택된 탭
                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#13B9A5"));
            }
        });

        tabHost.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"정보")
        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("정보");
        tabHost.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"후기")
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("후기");
        tabHost.addTab(ts2);

        // 세 번째 Tab. (탭 표시 텍스트:"지도")
        TabHost.TabSpec ts3 = tabHost.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("지도");
        tabHost.addTab(ts3);

        // 예약버튼 이벤트 설정
        reservationButton = (Button) viewLayout.findViewById(R.id.reservation_button);
        reservationButton.setOnClickListener(this);

        // 후기 페이지 설정
        setReviewPage();

        // 페이지 떴을 때 항상 스크롤 맨위에 가있도록
        setUpScroll();

        // 구글맵 연동
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

                adapter = new ReviewListViewAdapter(inflater, R.layout.review_listview_item, reviewData);

        reviewData.clear();

        reviewList.setAdapter(adapter);

        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/postscript/getinfo";

        JSONObject data = campingJSonData(campingPlace);

        NetworkTask networkTask = new NetworkTask(this,url,data, Constant.GET_REVIEWLIST,adapter);
        networkTask.execute();

    }

    @Override
    public void onPause(){
        super.onPause();
        pageViews = null;
        pointImage = null;
        pointImages = null;
        viewLayout = null;
        viewPager = null;
        viewPoints = null;
        intent = null;
        pointImages = null;
        mapImage = null;
        pointImage = null;
        campingInfoImage = null;
    }

    // 캠핑장 종류에 맞게 페이지뷰에 사진 설정
    public void setPageViews() {
        // 페이지뷰에 사진 페이지 저장
        pageViews = new ArrayList<View>();

        // 상세정보 사진 페이지들 View 지정
        page01 = inflater.inflate(R.layout.page01, null);
        page02 = inflater.inflate(R.layout.page02, null);
        page03 = inflater.inflate(R.layout.page03, null);

        // 사진 페이지 View에 캠핑장 종류에 맞게 사진 교체
        pic01 = page01.findViewById(R.id.pic01);
        pic02 = page02.findViewById(R.id.pic02);
        pic03 = page03.findViewById(R.id.pic03);

        // 캠핑장 종류에 맞게 안내 사진 다르게 설정
        switch (campingPlace) {
            case "난지 캠핑장":
                GlideApp.with(this).load(R.drawable.nanji_1).into(pic01);
                GlideApp.with(this).load(R.drawable.nanji_2).into(pic02);
                GlideApp.with(this).load(R.drawable.nanji_3).into(pic03);
                break;
            case "서울대공원 캠핑장":
                GlideApp.with(this).load(R.drawable.seoul_park_1).into(pic01);
                GlideApp.with(this).load(R.drawable.seoul_park_2).into(pic02);
                GlideApp.with(this).load(R.drawable.seoul_park_3).into(pic03);
                break;
            case "노을 캠핑장":
                GlideApp.with(this).load(R.drawable.noeul_1).into(pic01);
                GlideApp.with(this).load(R.drawable.noeul_2).into(pic02);
                GlideApp.with(this).load(R.drawable.noeul_3).into(pic03);
                break;
            case "중랑 캠핑장":
                GlideApp.with(this).load(R.drawable.jungrang_1).into(pic01);
                GlideApp.with(this).load(R.drawable.jungrang_2).into(pic02);
                GlideApp.with(this).load(R.drawable.jungrang_3).into(pic03);
                break;
            case "초안산 캠핑장":
                GlideApp.with(this).load(R.drawable.choansan_1).into(pic01);
                GlideApp.with(this).load(R.drawable.choansan_2).into(pic02);
                GlideApp.with(this).load(R.drawable.choansan_3).into(pic03);
                break;
            default:
                GlideApp.with(this).load(R.drawable.gangdong_1).into(pic01);
                GlideApp.with(this).load(R.drawable.gangdong_2).into(pic02);
                GlideApp.with(this).load(R.drawable.gangdong_3).into(pic03);
                break;
        }

        // 페이지뷰에 페이지들 붙이기
        pageViews.add(page01);
        pageViews.add(page02);
        pageViews.add(page03);

        setInfo();
    }

    // 버튼 클릭 이벤트 메소드
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reservation_button:
                // 예약 버튼 누르면 캘린더 액티비티 시작됨
                intent = new Intent(GuideActivity.this, CalenderActivity.class);
                intent.putExtra("title", campingPlace);
                startActivity(intent);
                break;
            case R.id.review_add_button:
                // 후기 작성 버튼 누르면 후기 작성 액티비티 시작됨
                intent = new Intent(GuideActivity.this, ReviewWriteActivity.class);
                intent.putExtra("캠핑장 이름",campingPlace);
                switch (campingPlace) {
                    case "난지 캠핑장":
                        startActivityForResult(intent, Constant.NANJI);
                        break;
                    case "서울대공원 캠핑장":
                        startActivityForResult(intent, Constant.SEOUL);
                        break;
                    case "노을 캠핑장":
                        startActivityForResult(intent, Constant.NOEUL);
                        break;
                    case "중랑 캠핑장":
                        startActivityForResult(intent, Constant.JUNGRANG);
                        break;
                    case "초안산 캠핑장":
                        startActivityForResult(intent, Constant.CHOANSAN);
                        break;
                    case "강동 캠핑장":
                        startActivityForResult(intent, Constant.GANGDONG);
                        break;
                }
        }
    }

    // 리뷰페이지 설정하는 메소드
    public void setReviewPage() {
        // 후기 리스트 설정
        reviewList = (ListView) findViewById(R.id.review_listView);

        // 후기 데이터 설정
        reviewData = new ArrayList<>();

        // 어댑터로 후기 리스트에 아이템 뿌려주기
        adapter = new ReviewListViewAdapter(inflater, R.layout.review_listview_item, reviewData);
        reviewList.setAdapter(adapter);

        // 후기작성버튼 설정
        reviewAddButton = findViewById(R.id.review_add_button);
        reviewAddButton.setOnClickListener(this);
        GlideApp.with(this).load(R.drawable.review_plus_icon).into(reviewAddButton);
    }

    // 캠핑장 별로 다르게 정보 설정해주기
    public void setInfo(){
        campingName = findViewById(R.id.camping_name);
        campingAddress = findViewById(R.id.camping_address);
        campingInfoImage = findViewById(R.id.camping_info_image);
        mapImage = findViewById(R.id.map_image);

        switch (campingPlace) {
            case "난지 캠핑장":
                campingName.setText("난지 캠핑장");
                campingAddress.setText("서울특별시 마포구 상암동 495-51");
                GlideApp.with(this).load(R.drawable.info_nanji).into(campingInfoImage);
                GlideApp.with(this).load(R.drawable.map_nanji).into(mapImage);
                break;
            case "서울대공원 캠핑장":
                campingName.setText("서울대공원 캠핑장");
                campingAddress.setText("경기도 과천시 막계동 산59-2");
                GlideApp.with(this).load(R.drawable.info_seoul).into(campingInfoImage);
                GlideApp.with(this).load(R.drawable.map_seoul).into(mapImage);
                break;
            case "노을 캠핑장":
                campingName.setText("노을 캠핑장");
                campingAddress.setText("서울특별시 마포구 상암동 478-1");
                GlideApp.with(this).load(R.drawable.info_noeul).into(campingInfoImage);
                GlideApp.with(this).load(R.drawable.map_noeul).into(mapImage);
                break;
            case "중랑 캠핑장":
                campingName.setText("중랑 캠핑장");
                campingAddress.setText("서울특별시 중랑구 망우동 망우로87길 110");
                GlideApp.with(this).load(R.drawable.info_jungrang).into(campingInfoImage);
                GlideApp.with(this).load(R.drawable.map_jungrang).into(mapImage);
                break;
            case "초안산 캠핑장":
                campingName.setText("초안산 캠핑장");
                campingAddress.setText("서울특별시 노원구 월계2동 749-1");
                GlideApp.with(this).load(R.drawable.info_choansan).into(campingInfoImage);
                GlideApp.with(this).load(R.drawable.map_choansan).into(mapImage);
                break;
            default:
                campingName.setText("강동 캠핑장");
                campingAddress.setText("서울특별시 강동구 둔촌동 천호대로206길 87");
                GlideApp.with(this).load(R.drawable.info_gangdong).into(campingInfoImage);
                GlideApp.with(this).load(R.drawable.map_gangdong).into(mapImage);
                break;
        }
    }

    // 구글맵 연동 메소드
    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng place;
        MarkerOptions markerOptions = new MarkerOptions();

        switch (campingPlace) {
            case "난지 캠핑장":
                place = new LatLng(37.5701602, 126.87255210000001);
                markerOptions.title("난지캠핑장");
                markerOptions.snippet("서울특별시 마포구 상암동 495-81");
                break;
            case "서울대공원 캠핑장":
                place = new LatLng(37.4295232, 127.02398859999994);
                markerOptions.title("서울대공원 캠핑장");
                markerOptions.snippet("경기도 과천시 막계동 산59-2");
                break;
            case "노을 캠핑장":
                place = new LatLng(37.5732074, 126.87382500000001);
                markerOptions.title("노을 캠핑장");
                markerOptions.snippet("서울특별시 마포구 상암동 478-1");
                break;
            case "중랑 캠핑장":
                place = new LatLng(37.6044856, 127.1096119);
                markerOptions.title("중랑 캠핑장");
                markerOptions.snippet("서울특별시 중랑구 망우동 망우로87길 110");
                break;
            case "초안산 캠핑장":
                place = new LatLng(37.6430117, 127.05011579999996);
                markerOptions.title("초안산 캠핑장");
                markerOptions.snippet("서울특별시 노원구 월계2동 749-1");
                break;
            default:
                place = new LatLng(37.5360802, 127.1532522);
                markerOptions.title("강동 캠핑장");
                markerOptions.snippet("서울특별시 강동구 둔촌동 천호대로206길 87");
                break;
        }

        markerOptions.position(place);
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
    }

    // 뷰페이저 페이지 알려주는 동그란 포인터 설정
    public void setViewPagerPointers() {
        // ImageViews = 동그라미들
        pointImages = new ImageView[pageViews.size()];

        // viewPoints = 동그라미 점들 있는 view, viewPager = 사진 페이지 부분 view
        viewPoints = (ViewGroup) viewLayout.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) viewLayout.findViewById(R.id.guidePages);

        // pageViews = 사진 개수
        // 사진 개수에 맞게 동그라미 설정
        for (int i = 0; i < pageViews.size(); i++) {
            pointImage = new ImageView(GuideActivity.this);
            pointImage.setLayoutParams(new ViewGroup.LayoutParams(35, 35));    // 동그라미 크기
            pointImage.setPadding(30, 0, 30, 50); // 동그라미 padding 설정
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
    }

    // 항상 스크롤 위로 가있게 하는 메소드
    public void setUpScroll() {
        scrollView = (ScrollView) findViewById(R.id.nanji_guide_scrollView);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
    }

    //캠핑장 별로 데이터 요청
    private JSONObject campingJSonData(String camp_name){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("camp_name",camp_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("시발","시발아알");
        Log.e("result",String.valueOf(resultCode));
        if(resultCode != 0){

            adapter = new ReviewListViewAdapter(inflater, R.layout.review_listview_item, reviewData);

            reviewData.clear();

            reviewList.setAdapter(adapter);

            String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/postscript/getinfo";

            JSONObject data2=null;

            switch (resultCode) {
                case Constant.NANJI:
                    data2 = campingJSonData("난지 캠핑장");
                    break;
                case Constant.SEOUL:
                    data2 = campingJSonData("서울대공원 캠핑장");
                    break;
                case Constant.NOEUL:
                    data2 = campingJSonData("노을 캠핑장");
                    break;
                case Constant.JUNGRANG:
                    data2 = campingJSonData("중랑 캠핑장");
                    break;
                case Constant.CHOANSAN:
                    data2 = campingJSonData("초안산 캠핑장");
                    break;
                case Constant.GANGDONG:
                    data2 = campingJSonData("강동 캠핑장");
                    break;
            }

            NetworkTask networkTask = new NetworkTask(this,url,data2, Constant.GET_REVIEWLIST,adapter);
            networkTask.execute();

        }
    }
}
