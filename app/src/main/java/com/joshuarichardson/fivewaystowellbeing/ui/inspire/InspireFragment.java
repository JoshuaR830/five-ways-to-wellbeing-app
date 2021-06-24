package com.joshuarichardson.fivewaystowellbeing.ui.inspire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.chip.ChipGroup;
import com.joshuarichardson.fivewaystowellbeing.R;
import com.joshuarichardson.fivewaystowellbeing.WaysToWellbeing;
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule;
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase;
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
    This contains inspire cards which are designed to give users suggestions
 */
@AndroidEntryPoint
public class InspireFragment extends Fragment implements InspireAdapter.InspireClickListener {
    @Inject
    WellbeingDatabase db;
    private WaysToWellbeing chipSelected = WaysToWellbeing.UNASSIGNED;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_inspire, container, false);

        RecyclerView recycler = view.findViewById(R.id.inspire_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        InspireAdapter adapter = new InspireAdapter(getContext(), new ArrayList<>(), this);

        List<InspireRecord> inspireRecords = InspireHelper.deserializeInspirationJson();

        EditText searchTextView = view.findViewById(R.id.inspire_search_box);

        WellbeingDatabaseModule.databaseExecutor.execute(() -> {
//            List<String> activityNames = this.db.activityRecordDao().getNamesOfAllVisibleActivitiesNotLive();
            List<Long> inspireIds = this.db.activityRecordDao().getInspireIdsOfAllVisibleActivitiesNotLive();
//            List<ActivityRecord> activities = this.db.activityRecordDao().getAllVisibleActivitiesNotLive();


            // ToDo: This checks for the names matching - but should it
//            for (InspireRecord record : inspireRecords) {
//                if(activityNames.contains(record.getActivityName().toLowerCase())) {
//                    record.isFavourite(true);
//                }
//            }

            for (InspireRecord record : inspireRecords) {
                if(inspireIds.contains(record.getInspireId())) {
                    record.isFavourite(true);
                }
            }

            adapter.setValues(inspireRecords, searchTextView.getText().toString());
        });

        recycler.setAdapter(adapter);

        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                processChipClick(InspireFragment.this.chipSelected, s.toString(), adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ChipGroup group = view.findViewById(R.id.wellbeing_chip_group);

        group.setOnCheckedChangeListener((groupId, checkedId) -> {
            switch (checkedId) {
                case R.id.chip_connect:
                    this.chipSelected = WaysToWellbeing.CONNECT;
                    break;
                case R.id.chip_be_active:
                    this.chipSelected = WaysToWellbeing.BE_ACTIVE;
                    break;
                case R.id.chip_keep_learning:
                    this.chipSelected = WaysToWellbeing.KEEP_LEARNING;
                    break;
                case R.id.chip_take_notice:
                    this.chipSelected = WaysToWellbeing.TAKE_NOTICE;
                    break;
                case R.id.chip_give:
                    this.chipSelected = WaysToWellbeing.GIVE;
                    break;
                default:
                    this.chipSelected = WaysToWellbeing.UNASSIGNED;

            }

            processChipClick(this.chipSelected, searchTextView.getText().toString(), adapter);
        });

        return view;
    }

    private void processChipClick(WaysToWellbeing chipSelected, String query, InspireAdapter adapter) {
        adapter.getFilter().filter(chipSelected.toString() + "$" + query);
    }

    @Override
    public void onFavouriteButtonClicked(View v, InspireRecord inspireRecord) {

        if (!inspireRecord.isFavourite()) {
            WellbeingDatabaseModule.databaseExecutor.execute(() -> {
                db.activityRecordDao().hideByInspireId(inspireRecord.getInspireId());
            });

            return;
        }

        ActivityRecord activity = InspireHelper.convertInspireRecordToActivity(inspireRecord);

        WellbeingDatabaseModule.databaseExecutor.execute(() -> {
            db.activityRecordDao().insert(activity);
        });
    }
}