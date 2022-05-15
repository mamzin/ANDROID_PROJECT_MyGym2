package ru.mamzin.mygym2.views

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
                if (data?.extras?.get("data") != null) {
                    iv_photo.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
                else {
                    iv_photo.setImageURI(data?.data)
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
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            .replace(R.id.mainframelayout, homeFragment)
            .commit()
    }

    private fun getPhotoFromURI() {
        val callUriIntent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(callUriIntent)
    }

    private fun getPhotoFromCamera() {
        val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(callCameraIntent)
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
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Add image for exercise category")
            val choice = arrayOf("Make new photo from camera", "Choose existing image from gallery")
            builder.setItems(choice) { dialog, which ->
                when (which) {
                    0 -> { getPhotoFromCamera() }
                    1 -> { getPhotoFromURI() }
                }
            }
            val dialog = builder.create()
            dialog.show()
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