package com.joshuarichardson.fivewaystowellbeing.ui.schedules.create

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.joshuarichardson.fivewaystowellbeing.R
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivitySchedule
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateActivitySchedule : AppCompatActivity() {
    @Inject
    lateinit var db : WellbeingDatabase

    var scheduleId : Long = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_activity_schedule)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        if (intent.extras == null)
            return;

        scheduleId = intent.extras!!.getLong("schedule_id", -1)
        var scheduleName = intent.extras!!.getString("schedule_name", "")

        if (scheduleId > 0) {
            var scheduleNameInput = findViewById<TextView>(R.id.activity_name_input)
            scheduleNameInput.text = scheduleName
        }
    }

    fun onSubmit(view : View) {

        val activityName = findViewById<EditText>(R.id.activity_name_input)
        val name = activityName.text.toString()
        val nameContainer = findViewById<TextInputLayout>(R.id.activity_name_input_container)

        var hasError = false

        if (name.length == 0) {
            nameContainer.setError(getString(R.string.error_no_name_entered))
            hasError = true
        } else {
            nameContainer.setError(null)
        }

        if(hasError) return

        WellbeingDatabaseModule.databaseExecutor.execute {
            if(scheduleId == -1L) {
                db.activityScheduleDao().insert(ActivitySchedule(name, ""))
            } else {
                db.activityScheduleDao().update(scheduleId, name)
            }
        }

        finish();
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item)
    }
}