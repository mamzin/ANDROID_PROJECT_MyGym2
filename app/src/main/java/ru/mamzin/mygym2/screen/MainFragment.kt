package ru.mamzin.mygym2.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mamzin.mygym2.ExerciseViewModel
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.model.DataAdapter
import ru.mamzin.mygym2.model.Exercise

class MainFragment : Fragment(), DataAdapter.CellClickListener {

    lateinit var rvData: RecyclerView
    val viewmodel: ExerciseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "MyGym"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        rvData = root.findViewById(R.id.rvData)
        rvData.layoutManager = LinearLayoutManager(requireContext())

        val rvAdapter = DataAdapter(requireContext(), this)
        rvData.adapter = rvAdapter
        viewmodel.allExercise.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                rvAdapter.updateList(it)
            }

        })
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onAboutClickListener(exercise: Exercise) {
        val bundle = Bundle()
        bundle.putSerializable("id", exercise.id)
        bundle.putString("name", exercise.name)
        bundle.putString("description", exercise.description)
        bundle.putParcelable("photo", exercise.photo)
        parentFragmentManager.setFragmentResult("exercisedata", bundle)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainframelayout, DescriptionExerciseFragment.newInstance())
            .commit()
    }

    override fun onAddClickListener(exercise: Exercise) {
        val bundle = Bundle()
        bundle.putSerializable("id_add", exercise.id)
        bundle.putString("name_add", exercise.name)
        parentFragmentManager.setFragmentResult("exercise_add", bundle)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainframelayout, AddExerciseFragment.newInstance())
            .commit()
    }

}