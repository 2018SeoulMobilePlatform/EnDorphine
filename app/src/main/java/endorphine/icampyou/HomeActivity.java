package endorphine.icampyou;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import endorphine.icampyou.EventMenu.EventFragment1;
import endorphine.icampyou.ExchangeMenu.ChattingList_Fragment;
import endorphine.icampyou.ExchangeMenu.ExchangeFragment1;
import endorphine.icampyou.GuideMenu.GuideFragment1;
import endorphine.icampyou.HomeMenu.HomeFragment1;
import endorphine.icampyou.QRcode.QrcodePopupActivity;
import endorphine.icampyou.ReservationMenu.ReservationFragment1;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // fragment 교체를 위한 변수들
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    // fragment 모음
    private GuideFragment1 guideFragment1;
    private ChattingList_Fragment chattingList_fragment;
    private HomeFragment1 homeFragment1;
    private EventFragment1 eventFragment1;
    // intent 모음
    private Intent qrcodePopupIntent;
    private Intent mypageIntent;
    private Intent reservationInfoIntent;
    // qr코드 비트맵
    private Bitmap qrcodeBitmap;

    // Back키 이벤트 인터페이스
    public interface onKeyBackPressedListener {
        public void onBack();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    // 하단바 클릭 이벤트
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_guide:
                    // 프래그먼트 변경
                    setFragment(1);
                    // 임의로 QR 코드 설정
                    generateRQCode("QR코드");
                    return true;
                case R.id.navigation_home:
                    setFragment(2);
                    return true;
                case R.id.navigation_exchange:
                    setFragment(3);
                    return true;
                case R.id.navigation_event:
                    setFragment(4);
                    //mTextMessage.setText(R.string.title_event);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // fragment 객체 생성
        guideFragment1 = new GuideFragment1();
        chattingList_fragment = new ChattingList_Fragment();
        homeFragment1 = new HomeFragment1();
        eventFragment1 = new EventFragment1();
        // 디폴트 프래그먼트 홈화면으로 설정
        setFragment(2);

        // intent 설정하기
        qrcodePopupIntent = new Intent(this, QrcodePopupActivity.class);
        mypageIntent = new Intent(this, MyPageActivity.class);
        reservationInfoIntent = new Intent(this, ReservationInfoActivity.class);

        // Bottom Navigation (하단 네비게이션 바)
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home); // 디폴트 홈메뉴로 지정

        // Navigation Drawer (옆구리 네비게이션 바)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //int color = getResources().getColor(getResources().getIdentifier("black","color",getPackageName()));

        // 상단에 토글 아이콘 색상 변경
        int color = ContextCompat.getColor(getBaseContext(), R.color.colorPrimary);
        toggle.getDrawerArrowDrawable().setColor(color);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Back키 누르면 종료
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    // 옆구리 네비게이션 바 아이템 클릭 이벤트
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // 내 정보 누르면 마이페이지 프래그먼트로 이동
        if(id==R.id.nav_mypage){
            startActivity(mypageIntent);
        }
        else if(id==R.id.nav_reservation_information){
            startActivity(reservationInfoIntent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     num을 인자로 받아서 num에 맞는 fragment 교체하는 메소드
     */
    public void setFragment(int num) {
        // fragmentTransaction은 fragment 변경할 때마다 초기화해야 에러가 나지 않는다.
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (num) {
            case 1:
                // 예약 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, guideFragment1);
                fragmentTransaction.addToBackStack("TEXT_VIEWER_BACKSTACK").commit();
                break;
            case 2:
                // 홈 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, homeFragment1);
                fragmentTransaction.addToBackStack("TEXT_VIEWER_BACKSTACK").commit();
                break;
            case 3:
                // 교환 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, chattingList_fragment);
                fragmentTransaction.addToBackStack("TEXT_VIEWER_BACKSTACK").commit();
                break;
            case 4:
                // 이벤트 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, eventFragment1);
                fragmentTransaction.addToBackStack("TEXT_VIEWER_BACKSTACK").commit();
            default:
                break;
        }
    }

    // QR코드 생성
    public void generateRQCode(String contents) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            qrcodeBitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 500, 500));
            ((ImageView) findViewById(R.id.qrcode)).setImageBitmap(qrcodeBitmap);
            qrcodePopupIntent.putExtra("qrcode",qrcodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    // QR코드 이미지 비트맵으로 변환
    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    // 상단에 QR코드 아이콘 생성하는 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi, menu);
        return true;
    }

    // 상단 QR코드 클릭이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // QR코드 팝업창 띄우기
            startActivity(qrcodePopupIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
