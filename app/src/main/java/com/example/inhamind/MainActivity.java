package com.example.inhamind;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context context = this;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    private BoardFragment boardFragment = new BoardFragment();
    private MyHelpFragment myHelpFragment = new MyHelpFragment();
    private ChattingFragment chattingFragment = new ChattingFragment();
    private MyPageFragment mypageFragment = new MyPageFragment();
    private NoticeFragment noticeFragment = new NoticeFragment();
    private CustomerServiceFragment customerServiceFragment = new CustomerServiceFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_black_24); //뒤로가기 버튼 이미지 지정

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.myPage:
                        transaction.replace(R.id.frameLayout, mypageFragment).commitAllowingStateLoss();
                        break;
                    case R.id.notice:
                        transaction.replace(R.id.frameLayout, noticeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.customerService:
                        transaction.replace(R.id.frameLayout, customerServiceFragment).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.home:
                    transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();
                    break;
                case R.id.board:
                    transaction.replace(R.id.frameLayout, boardFragment).commitAllowingStateLoss();
                    break;
                case R.id.myHelp:
                    transaction.replace(R.id.frameLayout, myHelpFragment).commitAllowingStateLoss();
                    break;
                case R.id.chatting:
                    transaction.replace(R.id.frameLayout, chattingFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}