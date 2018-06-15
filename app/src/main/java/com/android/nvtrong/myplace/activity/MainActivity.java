package com.android.nvtrong.myplace.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.nvtrong.myplace.ActivityUltis;
import com.android.nvtrong.myplace.R;
import com.android.nvtrong.myplace.databinding.ActivityMainBinding;
import com.android.nvtrong.myplace.viewModel.MainViewModel;
import com.android.nvtrong.myplace.viewModel.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener {
    ActivityMainBinding binding;
    MainViewModel viewModel;
    DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBinding();
        setupView();
        setListener();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupMenuToolbar();
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        setViewPager();
    }
    private void setViewPager() {

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    private void setupBinding(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewModel = new MainViewModel();
        binding.setViewModel(viewModel);
    }
    private void setListener(){
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setupView(){
        mDrawerLayout = binding.drawerLayout;
        navigationView = binding.navView;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawers();
        int id = item.getItemId();
        switch (id){
            case R.id.nav_food:
                startPlaceActivity(ActivityUltis.MAP_TYPE_SEARCH_RESTAURANT);
                break;
            case R.id.nav_coffee:
                startPlaceActivity(ActivityUltis.MAP_TYPE_SEARCH_CAFE);
                break;
        }
        return false;
    }
    private void startPlaceActivity(String type) {
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra(ActivityUltis.CATEGORY_NAME_EXTRA, type);
        startActivity(intent);
    }
    private void setupMenuToolbar(){
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.mnu_setting:
                Toast.makeText(this, "Ngon", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

}
