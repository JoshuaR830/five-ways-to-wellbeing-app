package com.joshuarichardson.fivewaystowellbeing.ui.activities.edit;

import com.joshuarichardson.fivewaystowellbeing.ui.schedules.ActivitySchedulesFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ActivitySchedulePagerAdapter extends FragmentStateAdapter {
    public ActivitySchedulePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if(position == 0) {
            fragment = new ActivityHistoryFragment();
            return fragment;
        } else if(position == 1) {
            fragment = new ActivitySchedulesFragment();
            return fragment;
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
