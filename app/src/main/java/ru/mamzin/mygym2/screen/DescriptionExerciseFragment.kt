package ru.mamzin.mygym2.screen

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
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
import ru.mamzin.mygym2.ExerciseViewModel
import ru.mamzin.mygym2.R
import ru.mamzin.mygym2.model.Exercise
import java.util.*

class DescriptionExerciseFragment : Fragment() {

    private val homeFragment = MainFragment()

    private lateinit var iv_photo_on_edit: ImageView
    private lateinit var btn_get_photo_on_edit: Button
    private lateinit var btn_save_on_edit: Button
    private lateinit var btn_delete_exercise_category: Button
    private lateinit var et_name_on_edit: EditText
    private lateinit var et_description_on_edit: EditText
    val viewmodel: ExerciseViewModel by viewModels()
    private lateinit var id: UUID

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                iv_photo_on_edit.setImageBitmap(data?.extras?.get("data") as Bitmap)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                pressedBackBtn()
            }
        })
        (activity as AppCompatActivity).supportActionBar?.title = "MyGym - About of Exercise category"
    }

    private fun pressedBackBtn() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainframelayout, homeFragment)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_description_exercise, container, false)

        btn_get_photo_on_edit = root.findViewById(R.id.btn_get_photo_on_edit)
        iv_photo_on_edit = root.findViewById(R.id.iv_photo_on_edit)
        btn_save_on_edit = root.findViewById(R.id.btn_save_on_edit)
        et_name_on_edit = root.findViewById(R.id.et_name_on_edit)
        et_description_on_edit = root.findViewById(R.id.et_description_on_edit)
        btn_delete_exercise_category = root.findViewById(R.id.btn_delete_exercise_category)

        btn_get_photo_on_edit.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(callCameraIntent)
        }

        parentFragmentManager.setFragmentResultListener("exercisedata", this) { key, bundle ->
            id = bundle.getSerializable("id") as UUID
            et_name_on_edit.setText(bundle.getString("name"))
            et_description_on_edit.setText(bundle.getString("description"))
            iv_photo_on_edit.setImageBitmap(bundle.getParcelable("photo"))
        }

        btn_delete_exercise_category.setOnClickListener {
            val exercise = Exercise(
                id,
                et_name_on_edit.text.toString(),
                et_description_on_edit.text.toString(),
                iv_photo_on_edit.drawable.toBitmap()
            )
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Are you sure you want to delete a category?")
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id ->
                    viewmodel.deleteExercise(exercise)
                    Toast.makeText(requireContext(), "Exercise category ${et_name_on_edit.text.toString()} deleted", Toast.LENGTH_SHORT)
                        .show()
                    pressedBackBtn()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle("Deleting an exercise category")
            alert.show()
        }

        btn_save_on_edit.setOnClickListener {
            if (et_name_on_edit.text.isEmpty() || et_description_on_edit.text.isEmpty()) {
                val builder = AlertDialog.Builder(requireContext())
                with(builder)
                {
                    setTitle("Data not filled")
                    setMessage("Provide a title and description for the exercise")
                    show()
                }
            } else {
                val exercise = Exercise(
                    id,
                    et_name_on_edit.text.toString(),
                    et_description_on_edit.text.toString(),
                    iv_photo_on_edit.drawable.toBitmap()
                )
                viewmodel.updateExercise(exercise)
                Toast.makeText(requireContext(), "Exercise category ${et_name_on_edit.text.toString()} saved", Toast.LENGTH_SHORT)
                    .show()
                pressedBackBtn()
            }
        }
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = DescriptionExerciseFragment()
    }
}