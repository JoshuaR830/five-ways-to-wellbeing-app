package com.joshuarichardson.fivewaystowellbeing.ui.schedules.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.joshuarichardson.fivewaystowellbeing.ActivityTypeImageHelper
import com.joshuarichardson.fivewaystowellbeing.R
import com.joshuarichardson.fivewaystowellbeing.WaysToWellbeing
import com.joshuarichardson.fivewaystowellbeing.WellbeingHelper
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecord
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivityRecordActivitySchedule
import com.joshuarichardson.fivewaystowellbeing.ui.activities.edit.ViewActivitiesActivity
import com.joshuarichardson.fivewaystowellbeing.ui.insights.WayToWellbeingImageColorizer
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleInstanceActivity : AppCompatActivity() {

    @Inject
    lateinit var db : WellbeingDatabase

    var scheduleId : Long = 0

    companion object {
        const val SELECT_ACTIVITY_REQUEST_CODE : Int = 1;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_instance)

        if(!intent.hasExtra("schedule_id"))
            return

        scheduleId = intent.getLongExtra("schedule_id", 0);

        val layout : LinearLayout = findViewById<LinearLayout>(R.id.suggestion_item_container)

        var name : String? = intent.getStringExtra("schedule_name");

        if(name == null) {
            name = getString(R.string.schedule_instance)
        }

        findViewById<TextView>(R.id.schedule_name).text = name


        val observer = Observer<List<ActivityRecord>> { scheduledActivities ->
            layout.removeAllViews()

            for (activity in scheduledActivities) {
                var view : View = LayoutInflater.from(this).inflate(R.layout.inspire_suggestion_item, null, false)
                view.findViewById<ImageButton>(R.id.favorite_button).visibility = View.GONE // ToDo - make this able to delete an item from the list
                view.findViewById<TextView>(R.id.app_name_text_view).text = activity.activityName;
                view.findViewById<TextView>(R.id.associated_activity_text_view).text = String.format("%s%s", activity.activityType.substring(0, 1), activity.activityType.substring(1).toLowerCase(Locale.getDefault()))
                view.findViewById<TextView>(R.id.way_to_wellbeing_text_view).text = getString(WellbeingHelper.getWellbeingStringResource(WaysToWellbeing.valueOf(activity.activityWayToWellbeing)))
                view.findViewById<ImageView>(R.id.list_item_image).setImageResource(ActivityTypeImageHelper.getActivityImage(activity.activityType))

                val frame: FrameLayout = view.findViewById(R.id.icon_image_frame)
                WayToWellbeingImageColorizer.colorizeFrame(this, frame, WaysToWellbeing.valueOf(activity.activityWayToWellbeing))

                layout.addView(view)
            }
        }

        var liveData : LiveData<List<ActivityRecord>> = db.activityRecordActivityScheduleLinkDao().getActivitiesByScheduleId(scheduleId).asLiveData();
        liveData.observe(this, observer)

    }

    fun onButtonClick(view : View) {
        val activityIntent : Intent = Intent(this, ViewActivitiesActivity::class.java)
        startActivityForResult(activityIntent, SELECT_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        if (requestCode == SELECT_ACTIVITY_REQUEST_CODE) {
            if(data == null)
                return

            if (!data.hasExtra("activity_id"))
                return

            var activityId : Long = data.getLongExtra("activity_id", 0)

            WellbeingDatabaseModule.databaseExecutor.execute {
                db.activityRecordActivityScheduleLinkDao().insert(ActivityRecordActivitySchedule(activityId, scheduleId))
            }


        }
    }
}