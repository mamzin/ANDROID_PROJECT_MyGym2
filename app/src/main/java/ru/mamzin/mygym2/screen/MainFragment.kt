package ru.mamzin.mygym2.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.mamzin.mygym2.model.Exercise
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.model.DataAdapter

class MainFragment : Fragment(), DataAdapter.CellClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onAboutClickListener(exercise: Exercise) {
    }

    override fun onAddClickListener(exercise: Exercise) {
        Toast.makeText(requireContext(), exercise.description, Toast.LENGTH_SHORT).show()
    }

}