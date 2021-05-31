package com.joshuarichardson.fivewaystowellbeing.ui.settings.data;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.joshuarichardson.fivewaystowellbeing.R;
import com.joshuarichardson.fivewaystowellbeing.ThemeHelper;
import com.joshuarichardson.fivewaystowellbeing.TimeHelper;
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule;
import com.joshuarichardson.fivewaystowellbeing.storage.DataExport;
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase;
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingGraphItem;
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingGraphValueHelper;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyResponse;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyResponseActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingQuestion;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingResult;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImportData extends Fragment {
    private static final int FILE_IMPORT_REQUEST_CODE = 1;

    @Inject
    WellbeingDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_import_data, container, false);

        Button button = view.findViewById(R.id.import_button);

        button.setOnClickListener(this::importClick);

        return view;
    }

    private void importClick(View v) {

        Intent fileChooser = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        fileChooser.setType("*/*");

        startActivityForResult(fileChooser, FILE_IMPORT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(data == null) {
            return;
        }

        String json = readFromFile(data.getData());
        Gson gson = new Gson();
        DataExport importedData = gson.fromJson(json, DataExport.class);

        Log.d("Activity name", importedData.getActivityRecords().get(0).getActivityName());

        List<ActivityRecord> activityRecords = importedData.getActivityRecords();
        List<SurveyResponse> surveyResponses = importedData.getSurveyResponses();
        List<SurveyResponseActivityRecord> surveyActivityRecords = importedData.getSurveyActivityRecords();
        List<WellbeingQuestion> wellbeingQuestions = importedData.getWellbeingQuestions();
        List<WellbeingRecord> wellbeingRecords = importedData.getWellbeingRecords();
        List<WellbeingResult> wellbeingResults = importedData.getWellbeingResults();

        if(requestCode == FILE_IMPORT_REQUEST_CODE) {
            WellbeingDatabaseModule.databaseExecutor.execute(() -> {
//                List<ActivityRecord> activityRecords = db.activityRecordDao().getAllActivitiesNotLive();
//                List<SurveyResponse> surveyResponses = db.surveyResponseDao().getAllSurveyResponsesNotLive();
//                List<SurveyResponseActivityRecord> surveyActivityRecords = db.surveyResponseActivityRecordDao().getAllSurveyActivityRecords();
//                List<WellbeingQuestion> wellbeingQuestions = db.wellbeingQuestionDao().getAllWellbeingQuestions();
//                List<WellbeingRecord> wellbeingRecords = db.wellbeingRecordDao().getAllWellbeingRecords();
//                List<WellbeingResult> wellbeingResults = db.wellbeingResultsDao().getAllResults();

//                DataExport exportData = new DataExport(activityRecords, surveyResponses, surveyActivityRecords, wellbeingQuestions, wellbeingRecords, wellbeingResults);

                importActivityRecords(activityRecords);
                importSurveyResponses(surveyResponses);
                importSurveyResponseActivtyRecords(surveyActivityRecords);
                importWellbeingQuestions(wellbeingQuestions);
                importWellbeingRecords(wellbeingRecords);
                importWellbeingResults(wellbeingResults);



//
//                Log.d("File", String.valueOf(data.getData()));
//                Log.d("json", json);
//
//                writeToFile(json, data.getData());
            });
        }
    }

    private void importWellbeingResults(List<WellbeingResult> wellbeingResults) {
        for (WellbeingResult result : wellbeingResults) {

            Log.d("Result", "" + result.getConnectValue() + " " + result.getBeActiveValue() + " " + result.getKeepLearningValue()  + " " + result.getTakeNoticeValue() + " " + result.getGiveValue());


            List<WellbeingGraphItem> values = db.wellbeingQuestionDao().getWaysToWellbeingBetweenTimesNotLive(TimeHelper.getStartOfDay(result.getTimestamp()), TimeHelper.getEndOfDay(result.getTimestamp()));

            WellbeingGraphValueHelper data = WellbeingGraphValueHelper.getWellbeingGraphValues(values);

            db.wellbeingResultsDao().updateWaysToWellbeing(result.getId(), data.getConnectValue(), data.getBeActiveValue(), data.getKeepLearningValue(), data.getTakeNoticeValue(), data.getGiveValue());

//            db.wellbeingResultsDao().insertData(
//                result.getId(),
//                result.getConnectValue(),
//                result.getBeActiveValue(),
//                result.getKeepLearningValue(),
//                result.getTakeNoticeValue(),
//                result.getGiveValue(),
//                result.getTimestamp()
//            );
        }
    }

    private void importWellbeingRecords(List<WellbeingRecord> wellbeingRecords) {
        for (WellbeingRecord record : wellbeingRecords) {
            db.wellbeingRecordDao().insertData(
                record.getId(),
                record.getUserInput(),
                record.getTime(),
                record.getSurveyActivityId(),
                record.getSequenceNumber(),
                record.getQuestionId()
            );
        }
    }

    private void importWellbeingQuestions(List<WellbeingQuestion> wellbeingQuestions) {
        for (WellbeingQuestion question : wellbeingQuestions) {
            db.wellbeingQuestionDao().insertData(
                question.getId(),
                question.getQuestion(),
                question.getPositiveMessage(),
                question.getNegativeMessage(),
                question.getWayToWellbeing(),
                question.getWeighting(),
                question.getActivityType(),
                question.getInputType()
            );
        }
    }

    private void importSurveyResponseActivtyRecords(List<SurveyResponseActivityRecord> surveyActivityRecords) {
        for (SurveyResponseActivityRecord record : surveyActivityRecords) {
            db.surveyResponseActivityRecordDao().insertData(
                record.getSurveyActivityId(),
                record.getActivityRecordId(),
                record.getSurveyResponseId(),
                record.getSequenceNumber(),
                record.getNote(),
                record.getStartTime(),
                record.getEndTime(),
                record.getEmotion(),
                record.getIsDone()
            );
        }
    }

    private void importSurveyResponses(List<SurveyResponse> surveyResponses) {
        for (SurveyResponse response : surveyResponses) {
            db.surveyResponseDao().insertData(
                response.getSurveyResponseId(),
                response.getSurveyResponseTimestamp(),
                response.getSurveyResponseWayToWellbeing(),
                response.getTitle(),
                response.getDescription()
            );
        }
    }

    private void importActivityRecords(List<ActivityRecord> activityRecords) {
        for (ActivityRecord record : activityRecords) {
            db.activityRecordDao().insertData(
                record.getActivityRecordId(),
                record.getActivityName(),
                record.getActivityType(),
                record.getActivityTimestamp(),
                record.getActivityDuration(),
                record.getActivityWayToWellbeing(),
                record.getIsHidden(),
                record.getInspireId()
            );
        }
    }

    private void writeToFile(String json, Uri uri) {
        try {
            ParcelFileDescriptor pfd = getActivity().getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write((json).getBytes());
            fileOutputStream.close();
            pfd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(Uri uri) {

        StringBuilder builder = new StringBuilder();

        try {
            InputStream stream = getContext().getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull((stream))));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
