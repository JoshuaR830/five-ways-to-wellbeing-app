package com.joshuarichardson.fivewaystowellbeing.ui.schedules

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joshuarichardson.fivewaystowellbeing.MainActivity
import com.joshuarichardson.fivewaystowellbeing.R
import com.joshuarichardson.fivewaystowellbeing.R.layout.fragment_activity_schedules
import com.joshuarichardson.fivewaystowellbeing.hilt.modules.WellbeingDatabaseModule
import com.joshuarichardson.fivewaystowellbeing.storage.WellbeingDatabase
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivitySchedule
import com.joshuarichardson.fivewaystowellbeing.ui.activities.edit.ViewActivitiesActivity
import com.joshuarichardson.fivewaystowellbeing.ui.schedules.ActivityScheduleAdapter.ActivityScheduleViewHolder
import com.joshuarichardson.fivewaystowellbeing.ui.schedules.create.CreateActivitySchedule
import com.joshuarichardson.fivewaystowellbeing.ui.schedules.list.ScheduleInstanceActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// First Kotlin file - w00p
@AndroidEntryPoint
class ActivitySchedulesFragment : Fragment(), OnScheduleItemClick {
    @Inject lateinit var db : WellbeingDatabase

    val schedulerResponseItems : MutableLiveData<List<ActivitySchedule>> by lazy {
        MutableLiveData<List<ActivitySchedule>>()
    }

    private var isEditable: Boolean = false
    var scheduleAdapter : ActivityScheduleAdapter? = null;

    companion object {
        const val CREATE_SURVEY_REQUEST_CODE : Int = 1
        const val ADD_ACTIVITIES_REQUEST_CODE : Int = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        var view : View = inflater.inflate(fragment_activity_schedules, container, false);

        val recycler: RecyclerView = view.findViewById(R.id.schedule_recycler_view)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val scheduleResponseObserver = Observer<List<ActivitySchedule>> { schedules ->
            scheduleAdapter = ActivityScheduleAdapter(requireContext(), schedules, this)
            recycler.adapter = scheduleAdapter
        }

        var data : LiveData<List<ActivitySchedule>> = db.activityScheduleDao().getAllLiveSchedules().asLiveData();
        data.observe(requireActivity(), scheduleResponseObserver)

        val addScheduleButton : FloatingActionButton = view.findViewById(R.id.fab_schedule)

        addScheduleButton.setOnClickListener {
            val activityIntent = Intent(requireActivity(), CreateActivitySchedule::class.java)
            startActivityForResult(activityIntent, CREATE_SURVEY_REQUEST_CODE)
        }

        // Reference https://stackoverflow.com/a/47531110/13496270
        setHasOptionsMenu(true)

//        var touchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                val scheduleViewHolder: ActivityScheduleViewHolder = viewHolder as ActivityScheduleViewHolder
//                scheduleViewHolder.doTheThing();
//                return true
//            }
//
//            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
//                var position : Int = viewHolder.adapterPosition
//                var dragFlag : Int = ItemTouchHelper.START
//                var swipeFlags : Int = 0
//
//                return ItemTouchHelper.Callback.makeMovementFlags(dragFlag, swipeFlags)
//            }
//
//            override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
//                return super.getMoveThreshold(viewHolder)
//            }
//
//            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
//                return 0
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            }
//        }
//
//        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
//        itemTouchHelper.attachToRecyclerView(recycler)

        return view;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK)
            return

        if (requestCode != CREATE_SURVEY_REQUEST_CODE)
            return
    }

    override fun itemClicked(schedule: ActivitySchedule) {

        if (requireActivity()::class.qualifiedName == MainActivity::class.qualifiedName) {
            val intent = Intent(requireActivity(), ScheduleInstanceActivity::class.java)

            val bundle = Bundle()
            bundle.putLong("schedule_id", schedule.id)
            bundle.putString("schedule_name", schedule.name)
            intent.putExtras(bundle)

            startActivityForResult(intent, ADD_ACTIVITIES_REQUEST_CODE)
        }

        if (requireActivity()::class.qualifiedName == ViewActivitiesActivity::class.qualifiedName) {
            val intent = Intent(requireActivity(), ScheduleInstanceActivity::class.java)

            val bundle = Bundle()
            bundle.putLong("schedule_id", schedule.id)
            bundle.putString("schedule_name", schedule.name)
            intent.putExtras(bundle)
            requireActivity().setResult(Activity.RESULT_OK, intent)
            requireActivity().finish()
        }
    }

    override fun itemLongClicked(schedule: ActivitySchedule, viewHolder: ActivityScheduleViewHolder) {
        Log.d("Long press", "Yes")
        viewHolder.doTheThing()
    }

    override fun deleteSchedule(scheduleId: Long) {

        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.title_delete_schedule)
            .setMessage(R.string.body_delete_schedule)
            .setIcon(R.drawable.icon_close)
            .setPositiveButton(R.string.button_delete) { dialog, which ->
                // Set the pass time to hidden
                WellbeingDatabaseModule.databaseExecutor.execute {
                    this.db.activityScheduleDao().deleteById(scheduleId)
                }
            }
            .setNegativeButton(R.string.button_cancel) { dialog, which -> }
            .create()
            .show()
    }

    override fun renameSchedule(scheduleId: Long, scheduleName : String) {

        val activityIntent = Intent(requireActivity(), CreateActivitySchedule::class.java)
        val bundle = Bundle()
        bundle.putLong("schedule_id", scheduleId)
        bundle.putString("schedule_name", scheduleName)
        activityIntent.putExtras(bundle)

        startActivityForResult(activityIntent, CREATE_SURVEY_REQUEST_CODE)
    }

//    override fun onPause() {
//        super.onPause()
//
//        if(scheduleResponseItems != null) {
//            scheduleResponseItems.removeObserver(scheduleObserver)
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Reference: https://stackoverflow.com/a/47531110/13496270
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_edit).isVisible = true
    }


    fun makeSchedulesEditable() {
        this.isEditable = !this.isEditable

        Log.d("Make editable", this.isEditable.toString())

        // This updates the recycler view and filters it by the search term for better navigation
        this.scheduleAdapter?.editableList(this.isEditable)
    }
}