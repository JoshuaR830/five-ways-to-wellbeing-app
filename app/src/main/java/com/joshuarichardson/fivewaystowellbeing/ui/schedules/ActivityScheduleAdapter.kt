package com.joshuarichardson.fivewaystowellbeing.ui.schedules

import android.animation.ObjectAnimator
import android.content.Context
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

        private var isEditingItem: Boolean = false
        private val nameText: TextView = itemView.findViewById(R.id.schedule_name)
        private val scheduleImage: ImageView = itemView.findViewById(R.id.schedule_image)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_image_button)
        private val editButton: ImageButton = itemView.findViewById(R.id.edit_image_button)


        init {
            hide()
        }

        fun onBind(selectedSchedule : ActivitySchedule) {
            deleteButton.setOnClickListener {
                scheduleItemClicked.deleteSchedule(selectedSchedule.id)
            }

            editButton.setOnClickListener {
                scheduleItemClicked.renameSchedule(selectedSchedule.id, selectedSchedule.name)
            }

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
                    animateSchedule()
                }
            }

            itemView.setOnLongClickListener{
                scheduleItemClicked.itemLongClicked(selectedSchedule, this)
                true
            }
        }

       fun animateSchedule() {
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

       private fun hide() {
           deleteButton.isEnabled = false
           deleteButton.visibility = View.GONE
           editButton.isEnabled = false
           editButton.visibility = View.GONE
       }

       private fun show() {
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
