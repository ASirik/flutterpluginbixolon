package com.bixolonprnt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bixolon.commonlib.BXLCommonConst;
import com.bixolon.commonlib.log.LogService;
import com.bixolon.sample.PrinterControl.BixolonPrinter;

public class MainActivity extends AppCompatActivity {
    private static BixolonPrinter bxlPrinter = null;

    private static Fragment currentFragment;
    private static int currentPosition = 0;

    private Toolbar toolbar;
    private TabLayout mTabLayout = null;
    private ViewPager mViewPager = null;
    private static TabPagerAdapter mPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = findViewById(R.id.main_tab);
        mViewPager = findViewById(R.id.main_viewpager);
        mTabLayout.setupWithViewPager(mViewPager);

        mPagerAdapter = new TabPagerAdapter(this.getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                currentPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final int ANDROID_NOUGAT = 24;
        if(Build.VERSION.SDK_INT >= ANDROID_NOUGAT)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        bxlPrinter = new BixolonPrinter(getApplicationContext());

        Thread.setDefaultUncaughtExceptionHandler(new AppUncaughtExceptionHandler());

        startConnectionActivity();

        LogService.InitDebugLog(true, false, BXLCommonConst._LOG_LEVEL_HIGH);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        getPrinterInstance().printerClose();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_open:
            case R.id.action_close:
                bxlPrinter.printerClose();
                startConnectionActivity();
                break;
        }

        return true;
    }

    public void startConnectionActivity()
    {
        Intent intent = new Intent(getApplicationContext(), PrinterConnectActivity.class);
        startActivity(intent);
    }

    public static BixolonPrinter getPrinterInstance()
    {
        return bxlPrinter;
    }

    public static Fragment getVisibleFragment() {
        currentFragment = mPagerAdapter.getRegisteredFragment(currentPosition);
        return currentFragment;
    }

    public class AppUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, final Throwable ex) {
            ex.printStackTrace();

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }
}
