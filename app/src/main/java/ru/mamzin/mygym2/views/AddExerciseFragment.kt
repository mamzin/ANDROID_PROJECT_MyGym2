package ru.mamzin.mygym2.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.databinding.FragmentAddExerciseBinding
import ru.mamzin.mygym2.model.Recurrence
import ru.mamzin.mygym2.viewmodel.RecurrenceViewModel
import java.util.*


class AddExerciseFragment : Fragment() {

    private val homeFragment = MainFragment()
    private lateinit var id_category: UUID

    private lateinit var tv_name_add: TextView
    private lateinit var btn_save_repeat: Button
    private lateinit var dateButton: Button
    private lateinit var et_session: EditText
    private lateinit var et_num: EditText

    private var _binding: FragmentAddExerciseBinding? = null
    private val binding get() = _binding!!

    val viewmodel: RecurrenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                pressedBackBtn()
            }
        })
        (activity as AppCompatActivity).supportActionBar?.title = "MyGym - Add Exercise session"
    }

    fun pressedBackBtn() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            .replace(R.id.mainframelayout, homeFragment)
            .commit()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_exercise, container, false)

        tv_name_add = root.findViewById(R.id.tv_name_add)
        btn_save_repeat = root.findViewById(R.id.btn_save_repeat)
        dateButton = root.findViewById(R.id.dateButton) as Button
        et_session = root.findViewById(R.id.et_session)
        et_num = root.findViewById(R.id.et_num)

        parentFragmentManager.setFragmentResultListener("exercise_add", this) { key, bundle ->
            id_category = bundle.getSerializable("id_add") as UUID
            tv_name_add.text = bundle.getString("name_add")
        }

        btn_save_repeat.setOnClickListener {
            if (et_session.text.isEmpty() || et_num.text.isEmpty() || (dateButton.text == "Date?")) {
                val builder = AlertDialog.Builder(requireContext())
                with(builder)
                {
                    setTitle("Data not filled")
                    setMessage("Specify the number of sessions and repetitions and date")
                    show()
                }
            } else {
                val recurrence = Recurrence(
                    id_category,
                    UUID.randomUUID(),
                    et_session.text.toString().toInt(),
                    et_num.text.toString().toInt(),
                    dateButton.text.toString()
                )
                viewmodel.addRecurrence(recurrence)
                DynamicToast
                    .makeSuccess(
                        requireContext(),
                        "Exercise session of ${tv_name_add.text} saved",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                pressedBackBtn()
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddExerciseBinding.bind(view)

        binding.apply {
            dateButton.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager

                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY", viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        dateButton.text = date
                    }
                }
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddExerciseFragment()
    }
}