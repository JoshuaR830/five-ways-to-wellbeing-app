package com.joshuarichardson.fivewaystowellbeing.ui.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.joshuarichardson.fivewaystowellbeing.R;
import com.joshuarichardson.fivewaystowellbeing.ui.learn_more.LearnMoreAboutFiveWaysActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import dagger.hilt.android.AndroidEntryPoint;

import static com.joshuarichardson.fivewaystowellbeing.ui.intro.IntroPagerAdapter.INTRO_PAGES;

@AndroidEntryPoint
public class IntroParentFragment extends Fragment {

    private ViewPager2 viewPager;
    private ImageButton previousButton;
    private ImageButton nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_intro_parent, container, false);

        IntroPagerAdapter adapter = new IntroPagerAdapter(this);

        viewPager = view.findViewById(R.id.history_pager);
        viewPager.setAdapter(adapter);

        LinearLayout pageIndicatorContainer = view.findViewById(R.id.page_indicator_container);

        for (int i = 0; i < pageIndicatorContainer.getChildCount(); i ++) {

            ImageView image = (ImageView) pageIndicatorContainer.getChildAt(i);
            final int dotNumber = i;
            image.setOnClickListener((v) -> onDotClick(v, dotNumber));
        }

        if(getActivity().getIntent().getExtras() != null) {
            int pageNumber = getActivity().getIntent().getExtras().getInt("zero_indexed_page_number", 0);
            viewPager.setCurrentItem(pageNumber);
        }

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                for (int i = 0; i < pageIndicatorContainer.getChildCount(); i ++) {
                    ImageView image = (ImageView) pageIndicatorContainer.getChildAt(i);
                    image.setImageResource(R.drawable.progress_circle_outline);
                }

                if (position == 0) {
                    View card = getActivity().findViewById(R.id.wellbeing_education_card);
                    View button = getActivity().findViewById(R.id.wellbeing_education_button);
                    previousButton.setVisibility(View.INVISIBLE);

                    card.setOnClickListener((v) -> ((IntroActivity)getActivity()).onLearnMoreButtonClicked(v));
                    button.setOnClickListener((v) -> ((IntroActivity)getActivity()).onLearnMoreButtonClicked(v));
                } else {
                    previousButton.setVisibility(View.VISIBLE);
                }

                if (position == pageIndicatorContainer.getChildCount() - 1) {
                    nextButton.setImageResource(R.drawable.icon_tick);
                } else {
                    nextButton.setImageResource(R.drawable.button_arrow);
                }

                ImageView selectedImage = (ImageView) pageIndicatorContainer.getChildAt(position);
                selectedImage.setImageResource(R.drawable.progress_circle_filled);
            }
        });

        nextButton = view.findViewById(R.id.button_next);
        previousButton = view.findViewById(R.id.button_previous);

        previousButton.setVisibility(View.INVISIBLE);

        nextButton.setOnClickListener(this::onNextButtonClick);
        previousButton.setOnClickListener(this::onPreviousButtonClick);

        return view;
    }

    public void onNextButtonClick(View view) {

        int previousValue = viewPager.getCurrentItem();

        previousButton.setVisibility(View.VISIBLE);

        viewPager.setCurrentItem(previousValue + 1);

        if (previousValue >= (INTRO_PAGES - 2)) {
            nextButton.setImageResource(R.drawable.icon_tick);
        }

        if (previousValue >= (INTRO_PAGES - 1)) {
            getActivity().finish();
        }
    }

    public void onPreviousButtonClick(View view) {
        int previousValue = viewPager.getCurrentItem();

        if (previousValue - 1 <= 0) {
            previousButton.setVisibility(View.INVISIBLE);
        }

        nextButton.setImageResource(R.drawable.button_arrow);

        viewPager.setCurrentItem(previousValue - 1);
    }

    public void onDotClick(View v, int pageNum) {
        if (pageNum == INTRO_PAGES - 1) {
            nextButton.setImageResource(R.drawable.icon_tick);
        } else {
            nextButton.setImageResource(R.drawable.button_arrow);
        }

        if (pageNum > 0) {
            previousButton.setVisibility(View.VISIBLE);
        } else {
            previousButton.setVisibility(View.INVISIBLE);
        }

        viewPager.setCurrentItem(pageNum);
    }
}
