package com.example.navin.databasehw;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class NavDrwActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PendingFragment.OnFragmentInteractionListener,
ApprovedFragment.OnFragmentInteractionListener,DeclinedFragment.OnFragmentInteractionListener{

    LinearLayout Navfd, Navfdc, Navmsg, Navmsgc, Navfrnd, Navfrndc, Navmap, Navmapc, Navstg, Navstgc, Navlog, Navlogc;
    BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    FrameLayout frmlay;
    PendingFragment pendingFragment;
    ApprovedFragment approvedFragment;
    DeclinedFragment declinedFragment;
    MenuItem prevMenuItem;

    SessionManager session;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        session = new SessionManager(getApplicationContext());
        //    toolbar.setNavigationIcon(R.drawable.sort_button_with_three_lines);
        setSupportActionBar(toolbar);



        Navlog = findViewById(R.id.navlog);
        Navlogc = findViewById(R.id.navlogc);
        Navlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();

                //   Navlogc.setBackgroundResource(R.drawable.roundcornerbtn);
            }
        });
        // toolbar.setNavigationIcon(R.drawable.sort_button_with_three_lines);


/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //  toggle.setDrawerIndicatorEnabled(false);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        //   toggle.setHomeAsUpIndicator(R.drawable.navdrapbr);
        Drawable icon = getResources().getDrawable(R.drawable.navdrapbr);
        icon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        toggle.setHomeAsUpIndicator(icon);
        // toggle.setDrawerSlideAnimationEnabled(true);
        //   toggle.setDrawerIndicatorEnabled(true);
        // toggle.setIcon(icon);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hiii", "fire");
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        frmlay = findViewById(R.id.mainframe);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        menu = bottomNavigationView.getMenu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainframe, new PendingFragment());
        transaction.commit();
        PrepareData();



/*
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_call:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_chat:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_contact:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

       /*  //Disable ViewPager Swipe

       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        */

     //   setupViewPager(viewPager);
        //
    }


    private void PrepareData() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.one:
                        menu.findItem(R.id.one).setChecked(true);
                        selectedFragment = new PendingFragment();
                        break;
                    case R.id.two:
                        menu.findItem(R.id.two).setChecked(true);
                        selectedFragment = new ApprovedFragment();
                        break;
                    case R.id.three:
                        menu.findItem(R.id.three).setChecked(true);
                        selectedFragment = new DeclinedFragment();
                        break;
                        /*
                    case R.id.four:
                        menu.findItem(R.id.four).setChecked(true);
                        selectedFragment = new ProfileFragment();
                        break;
                    case R.id.five:
                        menu.findItem(R.id.five).setChecked(true);
                        selectedFragment = new HomeFragment();
                       *//* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainframe, new MyProfileFragment());
                        //   transaction.addToBackStack("Menu");
                        transaction.commit();
                        menu.findItem(R.id.one).setChecked(true);*//*
                        break;*/

                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainframe, selectedFragment);
                transaction.commit();
                return false;
            }
        });
    }


    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        profileFragment = new ProfileFragment();
        settingsFragment = new SettingsFragment();
        calendarFragment = new CalendarFragment();
        adapter.addFragment(profileFragment);
        adapter.addFragment(settingsFragment);
        adapter.addFragment(calendarFragment);
        viewPager.setAdapter(adapter);
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        finish();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drw, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
