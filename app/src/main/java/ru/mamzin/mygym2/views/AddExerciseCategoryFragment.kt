package ru.mamzin.mygym2.views

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.model.Exercise
import ru.mamzin.mygym2.viewmodel.ExerciseViewModel
import java.util.*

class AddExerciseCategoryFragment : Fragment() {

    private val homeFragment = MainFragment()

    private lateinit var iv_photo: ImageView
    private lateinit var btn_get_photo: Button
    private lateinit var btn_save_on_add: Button
    private lateinit var et_name_on_add: EditText
    private lateinit var et_description_on_add: EditText

    val viewmodel: ExerciseViewModel by viewModels()

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val requestCode = result.data?.getIntExtra("IMG_EXIST", 10)
                when (requestCode) {
                    100 -> {iv_photo.setImageURI(data?.data)}
                    101 -> {iv_photo.setImageBitmap(data?.extras?.get("data") as Bitmap)}
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                pressedBackBtn()
            }
        })
        (activity as AppCompatActivity).supportActionBar?.title = "MyGym - Add Exercise Category"
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
        val root = inflater.inflate(R.layout.fragment_add_exercise_category, container, false)

        btn_get_photo = root.findViewById(R.id.btn_get_photo)
        iv_photo = root.findViewById(R.id.iv_photo)
        btn_save_on_add = root.findViewById(R.id.btn_save_on_add)
        et_name_on_add = root.findViewById(R.id.et_name_on_add)
        et_description_on_add = root.findViewById(R.id.et_description_on_add)

        btn_get_photo.setOnClickListener {
            val callCameraIntent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            callCameraIntent.putExtra("IMG_EXIST", 101)
            resultLauncher.launch(callCameraIntent)
        }

        btn_save_on_add.setOnClickListener {
            if (et_name_on_add.text.isEmpty() || et_description_on_add.text.isEmpty()) {
                val builder = AlertDialog.Builder(requireContext())
                with(builder)
                {
                    setTitle("Data not filled")
                    setMessage("Provide a title and description for the exercise")
                    show()
                }
            } else {
                val exercise = Exercise(
                    UUID.randomUUID(),
                    et_name_on_add.text.toString(),
                    et_description_on_add.text.toString(),
                    iv_photo.drawable.toBitmap()
                )
                viewmodel.addExercise(exercise)
                DynamicToast
                    .makeSuccess(
                        requireContext(),
                        "Exercise category ${et_name_on_add.text} saved",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                pressedBackBtn()
            }
        }
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddExerciseCategoryFragment()
    }

}