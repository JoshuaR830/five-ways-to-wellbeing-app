package com.joshuarichardson.fivewaystowellbeing.ui.schedules

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.joshuarichardson.fivewaystowellbeing.DisplayHelper
import com.joshuarichardson.fivewaystowellbeing.R
import com.joshuarichardson.fivewaystowellbeing.storage.entity.ActivitySchedule
import com.joshuarichardson.fivewaystowellbeing.ui.schedules.ActivityScheduleAdapter.ActivityScheduleViewHolder
import kotlin.contracts.contract

class ActivityScheduleAdapter(context: Context, scheduleList: List<ActivitySchedule>, scheduleItemClicked : OnScheduleItemClick) : RecyclerView.Adapter<ActivityScheduleViewHolder>() {

    val inflater : LayoutInflater = LayoutInflater.from(context)
    val scheduleItemClicked = scheduleItemClicked;
    var schedules : List<ActivitySchedule> = scheduleList;
    var isEditable: Int = -1;
    var context: Context = context;


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

    fun editableList(isEditable : Boolean) {
        if(isEditable) {
            this.isEditable = 1
        } else {
            this.isEditable = 0
        }
        notifyDataSetChanged()
    }

   inner class ActivityScheduleViewHolder(@NonNull itemView : View) : RecyclerView.ViewHolder(itemView) {

        val nameText: TextView
        val scheduleImage: ImageView
        var isEditingItem: Boolean = false;


        init {
            nameText =  itemView.findViewById(R.id.schedule_name);
            scheduleImage = itemView.findViewById(R.id.schedule_image)
        }

        fun onBind(selectedSchedule : ActivitySchedule) {
            selectedSchedule.id

            Log.d("IsEditable", isEditable.toString())

            if(isEditable == 1) {
                this.isEditingItem = true
                var px : Float = DisplayHelper.dpToPx(context, -142).toFloat();
                val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, px)
                animation.start()
            } else if (isEditable == 0){
                this.isEditingItem = false
                val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, 0f)
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
           this.isEditingItem = !this.isEditingItem;

           if(this.isEditingItem) {
              var px : Float = DisplayHelper.dpToPx(context, -142).toFloat();
               val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, px)
               animation.start()
           } else {
                val animation = ObjectAnimator.ofFloat(itemView.findViewById<View>(R.id.main_content), View.TRANSLATION_X, 0f)
                animation.start()
           }
       }
    }
}

interface OnScheduleItemClick {
    fun itemClicked(schedule: ActivitySchedule)
    fun itemLongClicked(schedule: ActivitySchedule, viewHolder: ActivityScheduleViewHolder)
}
