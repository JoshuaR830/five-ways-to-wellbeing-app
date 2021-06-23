package com.joshuarichardson.fivewaystowellbeing.ui.schedules

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.joshuarichardson.fivewaystowellbeing.DisplayHelper
import com.joshuarichardson.fivewaystowellbeing.R
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivitySchedule
import com.joshuarichardson.fivewaystowellbeing.ui.schedules.ActivityScheduleAdapter.ActivityScheduleViewHolder

class ActivityScheduleAdapter(context: Context, scheduleList: List<ActivitySchedule>, scheduleItemClicked : OnScheduleItemClick) : RecyclerView.Adapter<ActivityScheduleViewHolder>() {

    val inflater : LayoutInflater = LayoutInflater.from(context)
    val scheduleItemClicked = scheduleItemClicked;
    var schedules : List<ActivitySchedule> = scheduleList;
    var isEditable: Boolean = false;
    var context: Context = context
    var nextAction : Int = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityScheduleViewHolder {
        val view : View = inflater.inflate(R.layout.scheduled_list_item, parent, false)

        return ActivityScheduleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(holder: ActivityScheduleViewHolder, position: Int) {
        var selectedSchedule : ActivitySchedule = schedules.get(position);
        holder.onBind(selectedSchedule)
    }

    fun editableList() {

        if(nextAction == -1) {
            this.isEditable = !this.isEditable
        } else {
            this.isEditable = nextAction == 1
        }

        nextAction = -1
        notifyDataSetChanged()
    }

    inner class ActivityScheduleViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView) {

        val nameText: TextView
        val scheduleImage: ImageView
        var isEditingItem: Boolean = false
        val deleteButton: ImageButton
        val editButton: ImageButton


        init {
            nameText =  itemView.findViewById(R.id.schedule_name);
            scheduleImage = itemView.findViewById(R.id.schedule_image)
            deleteButton = itemView.findViewById(R.id.delete_image_button)
            editButton = itemView.findViewById(R.id.edit_image_button)

            hide()
        }

        fun onBind(selectedSchedule : ActivitySchedule) {
            selectedSchedule.id

            deleteButton.setOnClickListener {
                scheduleItemClicked.deleteSchedule(selectedSchedule.id)
            }

            editButton.setOnClickListener {
                scheduleItemClicked.renameSchedule(selectedSchedule.id, selectedSchedule.name)
            }

            Log.d("IsEditable", isEditable.toString())
            Log.d("Last action", nextAction.toString())

//            if(nextAction == 0) {
//                this.isEditingItem = true
//                var px : Float = DisplayHelper.dpToPx(context, -144).toFloat();
//                val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, px)
//                show()
//                animation.start()
//            } else if (nextAction == 1) {
//                this.isEditingItem = false
//                val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, 0f)
//                hide()
//                animation.start()
//            }
            if (isEditable) {
                this.isEditingItem = true
                var px : Float = DisplayHelper.dpToPx(context, -144).toFloat();
                val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, px)
                show()
                animation.start()
            } else {
                this.isEditingItem = false
                val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, 0f)
                hide()
                animation.start()
            }

            nameText.text = selectedSchedule.name;
            scheduleImage.setImageResource(R.drawable.icon_schedule)

            itemView.setOnClickListener{
                if (!this.isEditingItem) {
                    scheduleItemClicked.itemClicked(selectedSchedule)
                } else {
                    doTheThing()
                }
            }

            itemView.setOnLongClickListener{
                scheduleItemClicked.itemLongClicked(selectedSchedule, this)
                true
            }
        }

       fun doTheThing() {
           this.isEditingItem = !this.isEditingItem

           if(this.isEditingItem) {
               nextAction = 0
               var px : Float = DisplayHelper.dpToPx(context, -142).toFloat();
               val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, px)
               show()
               animation.start()
           } else {
                nextAction = 1
                val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, 0f)
                hide()
                animation.start()
           }
       }

       fun hide() {
           deleteButton.isEnabled = false
           deleteButton.visibility = View.GONE
           editButton.isEnabled = false
           editButton.visibility = View.GONE
       }

       fun show() {
           deleteButton.isEnabled = true
           deleteButton.visibility = View.VISIBLE
           editButton.isEnabled = true
           editButton.visibility = View.VISIBLE

       }
    }
}

interface OnScheduleItemClick {
    fun itemClicked(schedule: ActivitySchedule)
    fun itemLongClicked(schedule: ActivitySchedule, viewHolder: ActivityScheduleViewHolder)

    fun deleteSchedule(scheduleId: Long)
    fun renameSchedule(scheduleId: Long, scheduleName : String)
}
