package ru.mamzin.mygym2.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.databinding.FragmentAddExerciseBinding
import java.util.*


class AddExerciseFragment : Fragment() {

    private val homeFragment = MainFragment()
    private lateinit var id_category: UUID

    private lateinit var tv_name_add: TextView
    private lateinit var btn_save_repeat: Button
    private lateinit var dateButton: Button

    private var _binding: FragmentAddExerciseBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                pressedBackBtn()
            }
        })
        (activity as AppCompatActivity).supportActionBar?.title = "MyGym - Add Exercise"
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
        val root = inflater.inflate(R.layout.fragment_add_exercise, container, false)

        tv_name_add = root.findViewById(R.id.tv_name_add)
        btn_save_repeat = root.findViewById(R.id.btn_save_repeat)
        dateButton = root.findViewById(R.id.dateButton) as Button

        parentFragmentManager.setFragmentResultListener("exercise_add", this) { key, bundle ->
            id_category = bundle.getSerializable("id_add") as UUID
            tv_name_add.setText(bundle.getString("name_add"))
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
                    "REQUEST_KEY",
                    viewLifecycleOwner
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