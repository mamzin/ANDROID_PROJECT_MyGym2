package ru.mamzin.mygym2.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.model.Exercise
import ru.mamzin.mygym2.model.ExerciseDataAdapter
import ru.mamzin.mygym2.viewmodel.ExerciseViewModel

class MainFragment : Fragment(), ExerciseDataAdapter.CellClickListener {

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

        val rvAdapter = ExerciseDataAdapter(requireContext(), this)
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
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
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
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            .replace(R.id.mainframelayout, AddExerciseFragment.newInstance())
            .commit()
    }

}