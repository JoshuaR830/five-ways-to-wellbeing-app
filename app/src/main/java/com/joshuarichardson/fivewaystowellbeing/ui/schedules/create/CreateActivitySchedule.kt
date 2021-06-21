package com.joshuarichardson.fivewaystowellbeing.ui.schedules.create

import android.os.Bundle
import android.view.View
import android.widget.EditText
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_activity_schedule)
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
            db.activityScheduleDao().insert(ActivitySchedule(name, ""))
        }

        finish();
    }
}