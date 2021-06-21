package com.joshuarichardson.fivewaystowellbeing.ui.activities.edit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.joshuarichardson.fivewaystowellbeing.R;
import com.joshuarichardson.fivewaystowellbeing.ui.schedules.ActivitySchedulesFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class ActivityScheduleParentFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parentView, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_activity_schedule_parent, parentView, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActivitySchedulePagerAdapter adapter = new ActivitySchedulePagerAdapter(this);

        ViewPager2 viewPager = view.findViewById(R.id.activity_schedule_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.activity_schedule_tabs);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            if (position == 0){
                tab.setText(R.string.navigation_activities);
            } else if (position == 1) {
                tab.setText(R.string.activity_schedule);
            }
        })).attach();
    }

    /**
     * Enable editing on the activities
     */
    public void makeActivitiesEditable() {
        TabLayout tabLayout = requireActivity().findViewById(R.id.activity_schedule_tabs);

        if (tabLayout.getSelectedTabPosition() == 0) {
            // Reference https://stackoverflow.com/a/61178226/13496270
            Fragment activeFragment = getChildFragmentManager().findFragmentByTag("f0");
            if (activeFragment == null) {
                return;
            }

            if (activeFragment.getClass() != ActivityHistoryFragment.class) {
                return;
            }

            ActivityHistoryFragment activityFragment = (ActivityHistoryFragment) activeFragment;
            activityFragment.makeActivitiesEditable();
        }
    }

    public void makeSchedulesEditable() {
        TabLayout tabLayout = requireActivity().findViewById(R.id.activity_schedule_tabs);

        Log.d("Make editable", String.valueOf(tabLayout.getSelectedTabPosition()));
        if (tabLayout.getSelectedTabPosition() == 1) {
            // Reference https://stackoverflow.com/a/61178226/13496270
            Fragment activeFragment = getChildFragmentManager().findFragmentByTag("f1");
            if (activeFragment == null) {
                return;
            }

            if (activeFragment.getClass() != ActivitySchedulesFragment.class) {
                return;
            }

            ActivitySchedulesFragment activityFragment = (ActivitySchedulesFragment) activeFragment;
            activityFragment.makeSchedulesEditable();
        }
    }
}