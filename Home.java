package com.prog.logicprog.task.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.prog.logicprog.task.Adapter.List_adapter;
import com.prog.logicprog.task.List_Content.list_content;
import com.prog.logicprog.task.R;
import com.prog.logicprog.task.fragments.Images_Fragment;
import com.prog.logicprog.task.fragments.Milestones_Fragment;
import com.prog.logicprog.task.fragments.Videos_Fragment;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    TextView header_name;
    ListView listView;
    ArrayList<list_content> list_array=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View header = navigationView.getHeaderView(0);
        header_name = (TextView) header.findViewById(R.id.header_name);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int i) {

            }

            @Override
            public void onPageScrollStateChanged(final int i) {
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        listView=(ListView) findViewById(R.id.listView);
        setStaticData();
        List_adapter adapter=new List_adapter(Home.this,list_array);
        listView.setAdapter(adapter);

        initNavigationDrawer();
    }




    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Videos_Fragment(),"VIDEOS");
        adapter.addFrag(new Images_Fragment(), "IMAGES");
        adapter.addFrag(new Milestones_Fragment(), "MILESTONE");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private void setStaticData() {
        list_content item1=new list_content("android.resource://com.prog.logicprog.task/drawable/image1","EMPTINESS FT. JUSTIN TIBLEKAR","1 HOUR AGO","Spotasap Technologies Private Limited is a Private incorporated on 14 May 2015");
        list_array.add(item1);
        list_content item2=new list_content("android.resource://com.prog.logicprog.task/drawable/image2","FALLING LOVE WITH YOU","3 HOURS AGO","Spotasap Technologies Private Limited is a Private incorporated on 14 May 2015");
        list_array.add(item2);
        list_content item3=new list_content("android.resource://com.prog.logicprog.task/drawable/image3","EMPTINESS FT. JUSTIN TIBLEKAR","5 HOURS AGO","Spotasap Technologies Private Limited is a Private incorporated on 14 May 2015");
        list_array.add(item3);
        list_content item4=new list_content("android.resource://com.prog.logicprog.task/drawable/image1","FT. JUSTIN TIBLEKAR","24 HOURS AGO","Spotasap Technologies Private Limited is a Private incorporated on 14 May 2015");
        list_array.add(item4);
    }

    public void initNavigationDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String s = "" + menuItem.getTitleCondensed();
                /*if (s.equals("Milestone")){
                    Intent intent=new Intent(Home.this,Home.class);
                    startActivity(intent);
                    finish();
                }
*/
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override

            public void onDrawerClosed(View v) {

                super.onDrawerClosed(v);

            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
