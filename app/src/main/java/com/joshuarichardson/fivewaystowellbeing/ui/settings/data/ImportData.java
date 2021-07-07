package com.joshuarichardson.fivewaystowellbeing.ui.settings.data;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
        new MaterialAlertDialogBuilder(getActivity())
            .setTitle(getString(R.string.import_dialog_title))
            .setMessage(getString(R.string.import_dialog_body))
            .setIcon(R.drawable.icon_import)
            .setPositiveButton(getString(R.string.import_dialog_positive_button), (dialog, which) -> {
                Intent fileChooser = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                fileChooser.setType("*/*");

                startActivityForResult(fileChooser, FILE_IMPORT_REQUEST_CODE);
            })
            .setNegativeButton(getString(R.string.import_dialog_negative_button), (dialog, which) -> {})
            .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (data == null) {
            return;
        }

        String json = readFromFile(data.getData());
        Gson gson = new Gson();
        DataExport importedData = gson.fromJson(json, DataExport.class);

        LinearLayout errorMessage = getActivity().findViewById(R.id.import_error);

        if (importedData == null) {
            errorMessage.setVisibility(View.VISIBLE);
            return;
        } else {
            errorMessage.setVisibility(View.GONE);
        }

        List<ActivityRecord> activityRecords = importedData.getActivityRecords();
        List<SurveyResponse> surveyResponses = importedData.getSurveyResponses();
        List<SurveyResponseActivityRecord> surveyActivityRecords = importedData.getSurveyActivityRecords();
        List<WellbeingQuestion> wellbeingQuestions = importedData.getWellbeingQuestions();
        List<WellbeingRecord> wellbeingRecords = importedData.getWellbeingRecords();
        List<WellbeingResult> wellbeingResults = importedData.getWellbeingResults();
        List<ActivityRecordActivitySchedule> activityScheduleLinks = importedData.getActivityScheduleLinks();
        List<ActivitySchedule> activitySchedules = importedData.getActivitySchedules();

        if (requestCode == FILE_IMPORT_REQUEST_CODE) {

            ConstraintLayout statusLayout = requireActivity().findViewById(R.id.sync_status);
            Button button = requireActivity().findViewById(R.id.import_button);

            // Update some status stuff
            button.setVisibility(View.GONE);
            statusLayout.setVisibility(View.VISIBLE);

            WellbeingDatabaseModule.databaseExecutor.execute(() -> {
                importActivityRecords(activityRecords);

                requireActivity().runOnUiThread(() -> {
                    ImageView syncImage = getActivity().findViewById(R.id.activitiesImportImageSync);
                    ImageView completeImage = getActivity().findViewById(R.id.activitiesImportImage);

                    syncImage.setVisibility(View.GONE);
                    completeImage.setVisibility(View.VISIBLE);
                });

                importSurveyResponses(surveyResponses);
                importSurveyResponseActivityRecords(surveyActivityRecords);
                importWellbeingQuestions(wellbeingQuestions);
                importWellbeingRecords(wellbeingRecords);

                requireActivity().runOnUiThread(() -> {
                    ImageView syncImage = getActivity().findViewById(R.id.surveyImportImageSync);
                    ImageView completeImage = getActivity().findViewById(R.id.surveyImportImage);

                    syncImage.setVisibility(View.GONE);
                    completeImage.setVisibility(View.VISIBLE);
                });

                importWellbeingResults(wellbeingResults);

                requireActivity().runOnUiThread(() -> {
                    ImageView syncImage = getActivity().findViewById(R.id.historyImportImageSync);
                    ImageView completeImage = getActivity().findViewById(R.id.historyImportImage);

                    syncImage.setVisibility(View.GONE);
                    completeImage.setVisibility(View.VISIBLE);
                });

                importScheduleResults(activityScheduleLinks, activitySchedules);

                requireActivity().runOnUiThread(() -> {
                    ImageView syncImage = getActivity().findViewById(R.id.scheduleImportImageSync);
                    ImageView completeImage = getActivity().findViewById(R.id.scheduleImportImage);
                    syncImage.setVisibility(View.GONE);
                    completeImage.setVisibility(View.VISIBLE);

                    Button doneButton = getActivity().findViewById(R.id.import_done_button);
                    doneButton.setVisibility(View.VISIBLE);

                    doneButton.setOnClickListener((view) -> {
                        getActivity().getSupportFragmentManager().popBackStack();
                    });
                });
            });
        }
    }

    private void importScheduleResults(List<ActivityRecordActivitySchedule> activityScheduleLinks, List<ActivitySchedule> activitySchedules) {
        for (ActivitySchedule schedule : activitySchedules) {
            db.activityScheduleDao().deleteById(schedule.getId());
            db.activityScheduleDao().insertData(schedule.getId(), schedule.getName(), schedule.getImage());
        }

        for (ActivityRecordActivitySchedule links : activityScheduleLinks) {
            db.activityRecordActivityScheduleLinkDao().insertData(links.getId(), links.getActivityId(), links.getScheduleId());
        }
    }

    private void importWellbeingResults(List<WellbeingResult> wellbeingResults) {
        for (WellbeingResult result : wellbeingResults) {
            // Insert anything that doesn't exist
            db.wellbeingResultsDao().insertData(
                result.getId(),
                result.getConnectValue(),
                result.getBeActiveValue(),
                result.getKeepLearningValue(),
                result.getTakeNoticeValue(),
                result.getGiveValue(),
                result.getTimestamp()
            );

            // Update anything that does exist
            db.wellbeingResultsDao().updateWaysToWellbeing(result.getId(), result.getConnectValue(), result.getBeActiveValue(), result.getKeepLearningValue(), result.getTakeNoticeValue(), result.getGiveValue());
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

    private void importSurveyResponseActivityRecords(List<SurveyResponseActivityRecord> surveyActivityRecords) {
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

            // Because conflict avoids - make sure that it doesn't exist if I am trying to insert it - launching the app creates will have created all of them
            db.surveyResponseDao().deleteSurveyResponseById(response.getSurveyResponseId());

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

            // Need to replace if it already exists
            db.activityRecordDao().deleteByActivityRecordId(record.getActivityRecordId());

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