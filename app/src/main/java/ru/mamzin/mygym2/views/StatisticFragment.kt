package ru.mamzin.mygym2.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.model.ExerciseDataAdapter
import ru.mamzin.mygym2.model.RecurrenceDataAdapter
import ru.mamzin.mygym2.viewmodel.ExerciseViewModel
import ru.mamzin.mygym2.viewmodel.RecurrenceViewModel

class StatisticFragment : Fragment() {

    private val homeFragment = MainFragment()
    lateinit var rvStatistic: RecyclerView
    val viewmodel: RecurrenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                pressedBackBtn()
            }
        })

        (activity as AppCompatActivity).supportActionBar?.title = "MyGym - Statistic"
    }

    fun pressedBackBtn() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainframelayout, homeFragment)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_statistic, container, false)

        rvStatistic = root.findViewById(R.id.rvStatistic)
        rvStatistic.layoutManager = LinearLayoutManager(requireContext())

        val rvAdapter = RecurrenceDataAdapter(requireContext())
        rvStatistic.adapter = rvAdapter
        viewmodel.allRecurrence.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                rvAdapter.updateList(it)
            }

        })

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatisticFragment()
    }
}