package com.prasunpersonal.ExamManagementAdmin.Activities;

import static com.prasunpersonal.ExamManagementAdmin.App.ME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.prasunpersonal.ExamManagementAdmin.Adapters.PagerAdapter;
import com.prasunpersonal.ExamManagementAdmin.Fragments.CoursesStructureFragment;
import com.prasunpersonal.ExamManagementAdmin.Fragments.ExamsFragment;
import com.prasunpersonal.ExamManagementAdmin.Fragments.StudentsFragment;
import com.prasunpersonal.ExamManagementAdmin.R;
import com.prasunpersonal.ExamManagementAdmin.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    ActivityHomeBinding binding;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.homeToolbar);
        preferences = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE);

        binding.homeToolbar.setNavigationOnClickListener(v -> binding.homeDrawar.open());
        View navHeader = binding.homeNavigation.getHeaderView(0);
        ((TextView) navHeader.findViewById(R.id.navHeaderName)).setText(ME.getName());
        ((TextView) navHeader.findViewById(R.id.navHeaderEmail)).setText(ME.getEmail());

        binding.homeNavigation.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.logout) {
                preferences.edit().remove("EMAIL").remove("PASSWORD").apply();
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
            }
            return true;
        });

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ExamsFragment());
        fragments.add(new StudentsFragment());
        fragments.add(new CoursesStructureFragment());

        binding.homeTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.homeViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.homeViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.homeTablayout.selectTab(binding.homeTablayout.getTabAt(position));
            }
        });

        binding.homeViewpager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }
}