package com.example.baoadr01.loginscreen2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener {
    Toolbar toolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mTabs;
    private MaterialTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment fragmentById =
                (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        //sét nút để đóng mở Fragment
        fragmentById.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        mViewPager = (ViewPager) findViewById(R.id.paper);
//        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        MyPaperAdapter adapter = new MyPaperAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
//        mTabs.setDistributeEvenly(true);
//        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//            @Override
//            public int getIndicatorColor(int position) {
//                return getResources().getColor(R.color.accentColor);
//            }
//        });
//        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
//        mTabs.setViewPager(mViewPager);

        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(tabHost.newTab()
//                    .setText(adapter.getPageTitle(i))
                    .setIcon(adapter.getIcon(i))
                    .setTabListener(this));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "This is Settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.navigate) {
            startActivity(new Intent(this, SubActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

//--------------------------------------------------------
    class MyPaperAdapter extends FragmentPagerAdapter {
        int icons[] = {R.drawable.ic_action_home, R.drawable.ic_action_personal, R.drawable.ic_action_articles};
//        String[] tabs = getResources().getStringArray(R.array.tabs);

        public MyPaperAdapter(FragmentManager fm) {
            super(fm);
//            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            Drawable drawable = getResources().getDrawable(icons[position]);
//            drawable.setBounds(0, 0, 36, 36);
//            ImageSpan imageSpan = new ImageSpan(drawable);
//            SpannableString spannableString = new SpannableString(" ");
//            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            return spannableString;
            return getResources().getStringArray(R.array.tabs)[position];
        }
        public Drawable getIcon(int possition){
            return getResources().getDrawable(icons[possition]);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

//--------------------------------------------------------------------------------
    public static class MyFragment extends Fragment {
        private TextView textView;

        public static MyFragment getInstance(int possition) {
            MyFragment myFragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("possition", possition);
            myFragment.setArguments(bundle);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.custom_tab_view, container, false);
            textView = (TextView) layout.findViewById(R.id.tabText);

            Bundle bundle = getArguments();
            if (bundle != null) {
                textView.setText("The page Select Is " + bundle.getInt("possition"));
            }
            return layout;
        }
    }
}
