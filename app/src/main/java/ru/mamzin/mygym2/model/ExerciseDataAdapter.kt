package ru.mamzin.mygym2.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exercise_item.view.*
import ru.mamzin.mygym2.R

class ExerciseDataAdapter(
    private val context: Context,
    private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<ExerciseDataAdapter.ViewHolder>() {

    private val exerlist = ArrayList<Exercise>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = exerlist[position]
        holder.bind(data)
        holder.itemView.btn_description.setOnClickListener {
            cellClickListener.onAboutClickListener(data)
        }

        holder.itemView.btn_add_exercise.setOnClickListener {
            cellClickListener.onAddClickListener(data)
        }
    }

    fun updateList(newList: List<Exercise>) {
        exerlist.clear()
        exerlist.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return exerlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var tvname: TextView = itemLayoutView.findViewById(R.id.tv_exercise)

        fun bind(exercise: Exercise) {
            tvname.text = exercise.name
        }

    }

    interface CellClickListener {
        fun onAboutClickListener(exercise: Exercise)
        fun onAddClickListener(exercise: Exercise)
    }
}