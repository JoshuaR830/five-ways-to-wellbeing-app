package com.joshuarichardson.fivewaystowellbeing.ui.intro;

import com.joshuarichardson.fivewaystowellbeing.ui.inspire.InspireFragment;
import com.joshuarichardson.fivewaystowellbeing.ui.learn_more.LearnMoreFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class IntroPagerAdapter extends FragmentStateAdapter {

    public static int INTRO_PAGES = 3;

    public IntroPagerAdapter(@NonNull Fragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        if (position == 0) {
            fragment = new LearnMoreFragment();
        }
        else if (position == 1) {
            fragment = new IntroFragment();
        } else if (position == 2) {
            fragment = new InspireFragment();
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return INTRO_PAGES;
    }
}
