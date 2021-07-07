package com.joshuarichardson.fivewaystowellbeing.ui.settings.data;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.joshuarichardson.fivewaystowellbeing.R;
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule;
import com.joshuarichardson.fivewaystowellbeing.storage.DataExport;
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecordActivitySchedule;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivitySchedule;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyResponse;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.SurveyResponseActivityRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingQuestion;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingRecord;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.WellbeingResult;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExportData extends Fragment {
    private static final int FILE_CHOOSER_REQUEST_CODE = 1;

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
        Intent fileChooser = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        fileChooser.addCategory(Intent.CATEGORY_OPENABLE);
        fileChooser.setType("file/txt");
        fileChooser.putExtra(Intent.EXTRA_TITLE, "five_ways_to_wellbeing_data.txt");

        startActivityForResult(fileChooser, FILE_CHOOSER_REQUEST_CODE);
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

        if(requestCode == FILE_CHOOSER_REQUEST_CODE) {

            ConstraintLayout statusLayout = requireActivity().findViewById(R.id.sync_status);
            Button button = requireActivity().findViewById(R.id.export_button);

            // Update some status stuff
            button.setVisibility(View.GONE);
            statusLayout.setVisibility(View.VISIBLE);

            WellbeingDatabaseModule.databaseExecutor.execute(() -> {
                List<ActivityRecord> activityRecords = db.activityRecordDao().getAllActivitiesNotLive();

                requireActivity().runOnUiThread(() -> {
                    ImageView syncImage = getActivity().findViewById(R.id.activitiesExportImageSync);
                    ImageView completeImage = getActivity().findViewById(R.id.activitiesExportImage);

                    syncImage.setVisibility(View.GONE);
                    completeImage.setVisibility(View.VISIBLE);
                });

                List<SurveyResponse> surveyResponses = db.surveyResponseDao().getAllSurveyResponsesNotLive();
                List<SurveyResponseActivityRecord> surveyActivityRecords = db.surveyResponseActivityRecordDao().getAllSurveyActivityRecords();
                List<WellbeingQuestion> wellbeingQuestions = db.wellbeingQuestionDao().getAllWellbeingQuestions();
                List<WellbeingRecord> wellbeingRecords = db.wellbeingRecordDao().getAllWellbeingRecords();

                requireActivity().runOnUiThread(() -> {
                    ImageView syncImage = getActivity().findViewById(R.id.surveyExportImageSync);
                    ImageView completeImage = getActivity().findViewById(R.id.surveyExportImage);

                    syncImage.setVisibility(View.GONE);
                    completeImage.setVisibility(View.VISIBLE);
                });

                List<WellbeingResult> wellbeingResults = db.wellbeingResultsDao().getAllResults();

                requireActivity().runOnUiThread(() -> {
                    ImageView syncImage = getActivity().findViewById(R.id.historyExportImageSync);
                    ImageView completeImage = getActivity().findViewById(R.id.historyExportImage);

                    syncImage.setVisibility(View.GONE);
                    completeImage.setVisibility(View.VISIBLE);
                });

                List<ActivityRecordActivitySchedule> activityScheduleLinks = db.activityRecordActivityScheduleLinkDao().getAllActivityScheduleLinks();
                List<ActivitySchedule> activitySchedules = db.activityScheduleDao().getAllSchedules();

                DataExport exportData = new DataExport(activityRecords, surveyResponses, surveyActivityRecords, wellbeingQuestions, wellbeingRecords, wellbeingResults, activityScheduleLinks, activitySchedules);

                Gson gson = new Gson();
                String json = gson.toJson(exportData);

                writeToFile(json, data.getData());

                requireActivity().runOnUiThread(() -> {
                    ImageView syncImage = getActivity().findViewById(R.id.scheduleExportImageSync);
                    ImageView completeImage = getActivity().findViewById(R.id.scheduleExportImage);
                    syncImage.setVisibility(View.GONE);
                    completeImage.setVisibility(View.VISIBLE);

                    Button doneButton = getActivity().findViewById(R.id.export_done_button);
                    doneButton.setVisibility(View.VISIBLE);

                    doneButton.setOnClickListener((view) -> {
                        getActivity().getSupportFragmentManager().popBackStack();
                    });
                });
            });
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
}