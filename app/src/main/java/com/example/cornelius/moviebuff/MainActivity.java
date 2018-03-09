package com.example.cornelius.moviebuff;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.cornelius.moviebuff.adapter.TabFragmentPagerAdapter;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MainActivity extends AppCompatActivity {
    private ViewPager pager;
    private TabLayout tabs;
    private LinearLayout ly;
    private final String api_key="1a97cf4b57348648d4de896f96a2a950";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlowManager.init(new FlowConfig.Builder(this).build());

        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        ly = (LinearLayout)findViewById(R.id.layout_all);

        //set object adapter kedalam ViewPager
        pager.setAdapter( new TabFragmentPagerAdapter(getSupportFragmentManager()));



        //Manimpilasi sedikit untuk set TextColor pada Tab
        tabs.setTabTextColors(getResources().getColor(R.color.green_button),
                getResources().getColor(R.color.gold_text));

        //set tab ke ViewPager
        tabs.setupWithViewPager(pager);

        //konfigurasi Gravity Fill untuk Tab berada di posisi yang proposional
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        //set object adapter kedalam ViewPager
        pager.setAdapter( new TabFragmentPagerAdapter(getSupportFragmentManager()));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        if (item.getItemId()==android.R.id.home){
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//
//    }
}
