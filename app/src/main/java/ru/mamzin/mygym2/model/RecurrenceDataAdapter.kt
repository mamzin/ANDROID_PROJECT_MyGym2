package ru.mamzin.mygym2.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.statistic_item.view.*
import ru.mamzin.mygym2.R

class RecurrenceDataAdapter(
    private val context: Context,
    private val cellClickListener: RecurrenceDataAdapter.CellClickListener
) :
    RecyclerView.Adapter<RecurrenceDataAdapter.ViewHolder>() {

    private val recurlist = ArrayList<RecurrenceAndExercise>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecurrenceDataAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.statistic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecurrenceDataAdapter.ViewHolder, position: Int) {
        val data = recurlist[position]
        holder.bind(data)
        holder.itemView.btn_del_session.setOnClickListener {
            cellClickListener.onDelClickListener(data)
        }
    }

    override fun getItemCount(): Int {
        return recurlist.size
    }

    fun updateList(newList: List<RecurrenceAndExercise>) {
        recurlist.clear()
        recurlist.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var stat_name_exercise: TextView = itemLayoutView.findViewById(R.id.stat_name_exercise)
        var stat_date: TextView = itemLayoutView.findViewById(R.id.stat_date)
        var stat_sessions: TextView = itemLayoutView.findViewById(R.id.stat_sessions)
        var stat_repetition: TextView = itemLayoutView.findViewById(R.id.stat_repetition)

        fun bind(entity: RecurrenceAndExercise) {
            stat_name_exercise.text = entity.name
            stat_date.text = entity.date
            stat_sessions.text = "Number of sessions: ${entity.session}"
            stat_repetition.text = "Number of iterations: ${entity.repeat}"
        }
    }

    interface CellClickListener {
        fun onDelClickListener(entity: RecurrenceAndExercise)
    }

}