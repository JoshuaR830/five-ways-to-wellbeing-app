package com.joshuarichardson.fivewaystowellbeing.ui.intro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.joshuarichardson.fivewaystowellbeing.R;

import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
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
}