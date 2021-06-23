package com.joshuarichardson.fivewaystowellbeing.ui.schedules.list

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.joshuarichardson.fivewaystowellbeing.*
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule
import com.joshuarichardson.fivewaystowellbeing.storage.ActivityRecordWrapper
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase
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
    var isEditable : Boolean = false;

    companion object {
        const val SELECT_ACTIVITY_REQUEST_CODE : Int = 1;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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


        val observer = Observer<List<ActivityRecordWrapper>> { scheduledActivities ->
            layout.removeAllViews()

            renderActivityList(scheduledActivities, layout)
        }

        var liveData : LiveData<List<ActivityRecordWrapper>> = db.activityRecordActivityScheduleLinkDao().getActivitiesByScheduleId(scheduleId).asLiveData();
        liveData.observe(this, observer)

    }

    fun renderActivityList(scheduledActivities : List<ActivityRecordWrapper>, layout : LinearLayout) {
        for (activityWrapper in scheduledActivities) {
            var activity = activityWrapper.getRecord();
            var view : View = LayoutInflater.from(this).inflate(R.layout.schedule_activity_item, null, false)
            view.findViewById<TextView>(R.id.app_name_text_view).text = activity.activityName;
            view.findViewById<TextView>(R.id.associated_activity_text_view).text = String.format("%s%s", activity.activityType.substring(0, 1), activity.activityType.substring(1).toLowerCase(Locale.getDefault()))
            view.findViewById<TextView>(R.id.way_to_wellbeing_text_view).text = getString(WellbeingHelper.getWellbeingStringResource(WaysToWellbeing.valueOf(activity.activityWayToWellbeing)))
            view.findViewById<ImageView>(R.id.list_item_image).setImageResource(ActivityTypeImageHelper.getActivityImage(activity.activityType))
            view.tag = "false"

            val frame: FrameLayout = view.findViewById(R.id.icon_image_frame)
            WayToWellbeingImageColorizer.colorizeFrame(this, frame, WaysToWellbeing.valueOf(activity.activityWayToWellbeing))

            layout.addView(view)

            val deleteButton : ImageButton = view.findViewById<ImageButton>(R.id.delete_image_button)

            deleteButton.setOnClickListener {
                WellbeingDatabaseModule.databaseExecutor.execute {
                    db.activityRecordActivityScheduleLinkDao().unlink(activityWrapper.linkId);
                }
            }

            deleteButton.isEnabled = false
            deleteButton.visibility = View.GONE

            view.setOnLongClickListener {
                if (view.tag == "false") {
                    activity.isSelected = true
                    this.isEditable = true;
                    view.tag = "true"
                    var px : Float = DisplayHelper.dpToPx(this, -72).toFloat()
                    val animation = ObjectAnimator.ofFloat(view.findViewById<CardView>(R.id.schedule_activity_content), View.TRANSLATION_X, px)
                    animation.start()
                    deleteButton.isEnabled = true
                    deleteButton.visibility = View.VISIBLE
                }
                true
            }

            view.setOnClickListener {
                if (view.tag == "true") {
                    activity.isSelected = false;
                    this.isEditable = false;
                    view.tag = "false"
                    val animation = ObjectAnimator.ofFloat(view.findViewById<CardView>(R.id.schedule_activity_content), View.TRANSLATION_X, 0F)
                    animation.start()
                    deleteButton.isEnabled = false
                    deleteButton.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Inflate the options menu
        menuInflater.inflate(R.menu.help_menu, menu)
        menu?.findItem(R.id.action_edit)?.isVisible = true;

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.getItemId() == R.id.action_edit) {
            val layout : LinearLayout = findViewById<LinearLayout>(R.id.suggestion_item_container)

            for(i in 0 until layout.childCount) {
                val view = layout.getChildAt(i)
                if (!this.isEditable) {

                    view.tag = "true"

                    var px : Float = DisplayHelper.dpToPx(this, -72).toFloat()
                    val animation = ObjectAnimator.ofFloat(view.findViewById<CardView>(R.id.schedule_activity_content), View.TRANSLATION_X, px)
                    animation.start()

                    val deleteButton : ImageButton = view.findViewById<ImageButton>(R.id.delete_image_button)

                    deleteButton.isEnabled = true
                    deleteButton.visibility = View.VISIBLE
                } else {
                    view.tag = "false"

                    val animation = ObjectAnimator.ofFloat(view.findViewById<CardView>(R.id.schedule_activity_content), View.TRANSLATION_X, 0F)
                    animation.start()

                    val deleteButton : ImageButton = view.findViewById<ImageButton>(R.id.delete_image_button)

                    deleteButton.isEnabled = true
                    deleteButton.visibility = View.VISIBLE
                }
            }

            Log.d("Pressed it", "edit")

            // Invert it after
            this.isEditable = !this.isEditable;
        }
        else if (item.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
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