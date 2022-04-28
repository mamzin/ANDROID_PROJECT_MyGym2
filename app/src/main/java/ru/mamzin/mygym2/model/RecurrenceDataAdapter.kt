package ru.mamzin.mygym2.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.mamzin.mygym2.R

class RecurrenceDataAdapter(private val context: Context) : RecyclerView.Adapter<RecurrenceDataAdapter.ViewHolder>() {

    private  val recurlist = ArrayList<Recurrence>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecurrenceDataAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.statistic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecurrenceDataAdapter.ViewHolder, position: Int) {
        holder.bind(recurlist[position])
    }

    override fun getItemCount(): Int {
        return recurlist.size
    }

    fun updateList(newList: List<Recurrence>){
        recurlist.clear()
        recurlist.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var stat_name_exercise: TextView = itemLayoutView.findViewById(R.id.stat_name_exercise)
        var stat_date: TextView = itemLayoutView.findViewById(R.id.stat_date)
        var stat_sessions: TextView = itemLayoutView.findViewById(R.id.stat_sessions)
        var stat_repetition: TextView = itemLayoutView.findViewById(R.id.stat_repetition)

        fun bind(recurrence: Recurrence) {
            stat_name_exercise.text = recurrence.exercise_id.toString()
            stat_date.text = recurrence.date
            stat_sessions.text = "Number of sessions: ${recurrence.session}"
            stat_repetition.text = "Number of iterations: ${recurrence.repeat}"
        }
    }

}