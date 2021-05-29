package com.joshuarichardson.fivewaystowellbeing.ui.settings.data;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.joshuarichardson.fivewaystowellbeing.R;
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule;
import com.joshuarichardson.fivewaystowellbeing.storage.DataExport;
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyResponse;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyResponseActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingQuestion;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingResult;

import java.util.List;

import javax.inject.Inject;

@AndroidEntryPoint
public class ExportData extends Fragment {

    @Inject
    WellbeingDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_export_data, container, false);

        Button button = view.findViewById(R.id.export_button);

        button.setOnClickListener(this::exportClick);

        return view;
    }

    private void exportClick(View v) {


        WellbeingDatabaseModule.databaseExecutor.execute(() -> {
            List<ActivityRecord> activityRecords = db.activityRecordDao().getAllActivitiesNotLive();
            List<SurveyResponse> surveyResponses = db.surveyResponseDao().getAllSurveyResponsesNotLive();
            List<SurveyResponseActivityRecord> surveyActivityRecords = db.surveyResponseActivityRecordDao().getAllSurveyActivityRecords();
            List<WellbeingQuestion> wellbeingQuestions = db.wellbeingQuestionDao().getAllWellbeingQuestions();
            List<WellbeingRecord> wellbeingRecords = db.wellbeingRecordDao().getAllWellbeingRecords();
            List<WellbeingResult> wellbeingResults = db.wellbeingResultsDao().getAllResults();

            DataExport exportData = new DataExport(activityRecords, surveyResponses, surveyActivityRecords, wellbeingQuestions, wellbeingRecords, wellbeingResults);

            Gson gson = new Gson();
            String json = gson.toJson(exportData);

            Log.d("json", json);
        });
    }
}