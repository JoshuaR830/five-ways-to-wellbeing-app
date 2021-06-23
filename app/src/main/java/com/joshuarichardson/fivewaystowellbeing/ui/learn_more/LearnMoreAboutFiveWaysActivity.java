package com.joshuarichardson.fivewaystowellbeing.ui.learn_more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.joshuarichardson.fivewaystowellbeing.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * An activity to help users to find out more about the five ways to wellbeing
 */
public class LearnMoreAboutFiveWaysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more_about_five_ways);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View card = findViewById(R.id.wellbeing_education_card);
        View button = findViewById(R.id.wellbeing_education_button);

        card.setOnClickListener(this::onLearnMoreButtonClicked);
        button.setOnClickListener(this::onLearnMoreButtonClicked);
    }

    /**
     * Open the research paper in a web browser
     * @param view The instance of the button clicked
     */
    public void onLearnMoreButtonClicked(View view) {
        // Open research PDF in Chrome
        Intent webViewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://neweconomics.org/uploads/files/five-ways-to-wellbeing-1.pdf"));
        startActivity(webViewIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}