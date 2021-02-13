package com.joshuarichardson.fivewaystowellbeing;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.joshuarichardson.fivewaystowellbeing.analytics.LogAnalyticEventHelper;
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule;
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase;
import com.joshuarichardson.fivewaystowellbeing.storage.dao.ActivityRecordDao;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;

import java.util.Date;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreatePassTimeActivity extends AppCompatActivity {
    ActivityRecordDao passTimeDao;

    @Inject
    WellbeingDatabase db;

    @Inject
    LogAnalyticEventHelper analyticsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass_time);

        if(getIntent() != null && getIntent().getExtras() != null) {
            EditText textBox = findViewById(R.id.pass_time_name_input);
            textBox.setText(getIntent().getExtras().getString("new_activity_name", ""));
        }

        AutoCompleteTextView dropDownInput = findViewById(R.id.pass_time_type_input);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreatePassTimeActivity.this, R.layout.item_list_text, DropDownHelper.getEnumStrings(ActivityType.values()));
        dropDownInput.setAdapter(adapter);

        this.passTimeDao = this.db.activityRecordDao();
    }

    public void onSubmit(View v) {
        // Log the interaction
        this.analyticsHelper.logCreatePasstimeEvent(this);

        EditText passTimeName = findViewById(R.id.pass_time_name_input);
        EditText passTimeDuration = findViewById(R.id.pass_time_duration_input);
        EditText passTimeType = findViewById(R.id.pass_time_type_input);

        String name = passTimeName.getText().toString();
        int duration = Integer.parseInt(passTimeDuration.getText().toString());
        String type = passTimeType.getText().toString();

        Date currentTime = new Date();
        long unixTime = currentTime.getTime();

        WellbeingDatabaseModule.databaseWriteExecutor.execute(() -> {
            this.passTimeDao.insert(new ActivityRecord(name, duration, unixTime, type, WaysToWellbeing.UNASSIGNED.toString()));
            finish();
        });
    }
}