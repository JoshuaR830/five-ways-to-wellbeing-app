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
        if(position == 0) {
            return new ActivityHistoryFragment();
        } else if(position == 1) {
            return new ActivitySchedulesFragment();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
