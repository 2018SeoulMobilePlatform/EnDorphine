package endorphine.icampyou;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import endorphine.icampyou.EventMenu.EventFragment1;
import endorphine.icampyou.ExchangeMenu.ExchangeFragment1;
import endorphine.icampyou.GuideMenu.GuideFragment1;
import endorphine.icampyou.HomeMenu.HomeFragment1;
import endorphine.icampyou.ReservationMenu.ReservationFragment1;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    // fragment 교체를 위한 변수들
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    // fragment 객체들
    private GuideFragment1 guideFragment1;
    private ReservationFragment1 reservationFragment1;
    private ExchangeFragment1 exchangeFragment1;
    private HomeFragment1 homeFragment1;
    private EventFragment1 eventFragment1;

    // 하단바 클릭 이벤트
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_guide:
                    // 프래그먼트 변경
                    setFragment(0);
                    return true;
                case R.id.navigation_reservation:
                    setFragment(1);
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
        reservationFragment1 = new ReservationFragment1();
        exchangeFragment1 = new ExchangeFragment1();
        homeFragment1 = new HomeFragment1();
        eventFragment1 = new EventFragment1();

        setFragment(2);

        // Bottom Navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Navigation Drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     num을 인자로 받아서 num에 맞는 fragment 교체하는 메소드
     */
    public void setFragment(int num){
        // fragmentTransactions는 fagment 변경할 때마다 초기화해야 에러가 나지 않는다.
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (num){
            case 0:
                // 안내 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, guideFragment1);
                fragmentTransaction.commit();
                break;
            case 1:
                // 예약 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, reservationFragment1);
                fragmentTransaction.commit();
                break;
            case 2:
                // 홈 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, homeFragment1);
                fragmentTransaction.commit();
                break;
            case 3:
                // 교환 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, exchangeFragment1);
                fragmentTransaction.commit();
                break;
            case 4:
                // 이벤트 프래그먼트1로 변경
                fragmentTransaction.replace(R.id.main_frame, eventFragment1);
                fragmentTransaction.commit();
            default: break;
        }
    }

}
