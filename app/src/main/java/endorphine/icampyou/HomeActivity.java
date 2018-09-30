package endorphine.icampyou;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import endorphine.icampyou.EventMenu.EventFragment1;
import endorphine.icampyou.ExchangeMenu.ChattingList_Fragment;
import endorphine.icampyou.GuideMenu.GuideFragment1;
import endorphine.icampyou.HomeMenu.HomeFragment2;
import endorphine.icampyou.Login.LoginActivity;
import endorphine.icampyou.NavigationDrawerMenu.MyPageActivity;
import endorphine.icampyou.QRcode.QrcodePopupActivity;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // fragment 교체를 위한 변수들
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    // fragment 모음
    private GuideFragment1 guideFragment1;
    private ChattingList_Fragment chattingList_fragment;
    private EventFragment1 eventFragment1;
    private HomeFragment2 homeFragment2;
    // intent 모음
    private Intent qrcodePopupIntent;
    private Intent mypageIntent;
    private Intent reservationInfoListIntent;
    private Intent logoutIntent;
    // qr코드 비트맵
    private Bitmap qrcodeBitmap;
    private SharedPreferences preferences;
    // drawer
    private ImageView drawerBackground;
    private CircleImageView drawerProfileImage;
    private TextView drawerNickName;
    private TextView drawerEmail;
    private ImageView drawerQrcode;

    private BottomNavigationView navigation;

    //이미지 변환
    ImageConversion imageConversion;

    // 하단바 클릭 이벤트
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_guide:
                    // 예약 프래그먼트1로 변경
                    guideFragment1 = new GuideFragment1();
                    eventFragment1 = null;
                    homeFragment2 = null;
                    chattingList_fragment = null;
                    fragmentTransaction.replace(R.id.main_frame, guideFragment1).commit();
                    return true;
                case R.id.navigation_home:
                    // 홈 프래그먼트1로 변경
                    homeFragment2 = new HomeFragment2();
                    guideFragment1 = null;
                    eventFragment1 = null;
                    chattingList_fragment = null;
                    fragmentTransaction.replace(R.id.main_frame, homeFragment2).commit();
                    return true;
                case R.id.navigation_exchange:
                    // 교환 프래그먼트1로 변경
                    chattingList_fragment = new ChattingList_Fragment();
                    guideFragment1 = null;
                    eventFragment1 = null;
                    homeFragment2 = null;
                    fragmentTransaction.replace(R.id.main_frame, chattingList_fragment).commit();
                    return true;
                case R.id.navigation_event:
                    // 이벤트 프래그먼트1로 변경
                    eventFragment1 = new EventFragment1();
                    guideFragment1 = null;
                    homeFragment2 = null;
                    chattingList_fragment = null;
                    fragmentTransaction.replace(R.id.main_frame, eventFragment1).commit();
                    return true;
            }
            fragmentTransaction.addToBackStack("TEXT_VIEWER_BACKSTACK").commit();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE)).getLargeMemoryClass();

        setContentView(R.layout.activity_home);

        imageConversion = new ImageConversion();

        // 디폴트 프래그먼트 홈화면으로 설정
        homeFragment2 = new HomeFragment2();
        getFragmentManager().beginTransaction().replace(R.id.main_frame, homeFragment2).commit();

        // Bottom Navigation (하단 네비게이션 바)
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
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

        // 상단에 토글 아이콘 색상 변경
        int color = ContextCompat.getColor(getBaseContext(), R.color.colorPrimary);
        toggle.getDrawerArrowDrawable().setColor(color);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_navi, navigationView, false);
        navigationView.addHeaderView(headerView);

        // drawer 네비게이션 바 설정
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);

        drawerBackground = headerView.findViewById(R.id.drawer_background);
        drawerProfileImage = headerView.findViewById(R.id.drawer_user_image);
        drawerNickName = headerView.findViewById(R.id.drawer_user_name);
        drawerEmail = headerView.findViewById(R.id.drawer_email);
        drawerQrcode = headerView.findViewById(R.id.drawer_qrcode);

        GlideApp.with(this).load(R.drawable.drawer_background).into(drawerBackground);
        GlideApp.with(this).load(imageConversion.fromBase64(preferences.getString("profileImage", ""))).into(drawerProfileImage);
        drawerNickName.setText(preferences.getString("nickname", ""));
        drawerEmail.setText(preferences.getString("email", ""));

        // 예약 정보도 저장
        String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/getreservation";

        JSONObject data = getReservationInfo();
        NetworkTask networkTask = new NetworkTask(HomeActivity.this, url, data, Constant.GET_RESERVATION_INFO,drawerQrcode);
        networkTask.execute();

        // 앱 로고 설정
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.app_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        qrcodePopupIntent = null;
        reservationInfoListIntent = null;
        mypageIntent = null;
        guideFragment1 = null;
        eventFragment1 = null;
        homeFragment2 = null;
        chattingList_fragment = null;
        fragmentManager = null;
        fragmentTransaction = null;
    }

    // Back키 누르면 종료
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(getFragmentManager().getBackStackEntryCount() == 0){
            AlertDialog.Builder finishDialog = new AlertDialog.Builder(this);
            finishDialog.setMessage("정말로 종료하시겠습니까?");

            finishDialog.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            finishDialog.setNegativeButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            finishDialog.setIcon(R.drawable.app_icon7);
            finishDialog.setTitle(R.string.app_name);
            AlertDialog alert = finishDialog.create();
            alert.show();
        }
        else{
            getFragmentManager().popBackStack();
        }
    }

    // 옆구리 네비게이션 바 아이템 클릭 이벤트
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        // 내 정보 이동
        if (id == R.id.nav_mypage) {
            mypageIntent = new Intent(this, MyPageActivity.class);
            startActivity(mypageIntent);
        }
        // 예약 정보 이동
        else if(id==R.id.nav_reservation_information){
            reservationInfoListIntent = new Intent(this, endorphine.icampyou.NavigationDrawerMenu.ReservationInfoListActivity.class);
            startActivity(reservationInfoListIntent);
        }
        // 로그인 이동
        else if (id == R.id.nav_logout) {
            logoutIntent = new Intent(this, LoginActivity.class);
            startActivity(logoutIntent);
            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
            SharedPreferences.Editor autoLogin = auto.edit();
            autoLogin.clear();
            autoLogin.commit();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            qrcodePopupIntent = new Intent(this, QrcodePopupActivity.class);
            startActivity(qrcodePopupIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //아이디 체크하는 JSON 데이터
    private JSONObject getReservationInfo(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("user_id", preferences.getString("email",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerProfileImage.setImageBitmap(imageConversion.fromBase64(preferences.getString("profileImage", "")));
        drawerNickName.setText(preferences.getString("nickname",""));
    }

    public BottomNavigationView getNavigation(){
        return navigation;
    }

}
