package ru.mamzin.mygym2.views

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.model.Recurrence
import ru.mamzin.mygym2.model.RecurrenceAndExercise
import ru.mamzin.mygym2.model.RecurrenceDataAdapter
import ru.mamzin.mygym2.viewmodel.RecurrenceViewModel

class StatisticFragment : Fragment(), RecurrenceDataAdapter.CellClickListener {

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

        val rvAdapter = RecurrenceDataAdapter(requireContext(), this)
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

    override fun onDelClickListener(entity: RecurrenceAndExercise) {
        val recurrence = Recurrence(
            entity.id,
            entity.recurrence_id,
            entity.session,
            entity.repeat,
            entity.date
        )
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage("Are you sure you want to delete a session?")
            .setIcon(R.drawable.editmenuicon)
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                viewmodel.deleteRecurrence(recurrence)
                DynamicToast
                    .makeSuccess(
                        requireContext(),
                        "Session has been deleted",
                        Toast.LENGTH_SHORT
                    ).show()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Deleting an session with repetition")
        alert.show()
    }
}