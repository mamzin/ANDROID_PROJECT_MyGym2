package ru.mamzin.mygym2.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import ru.mamzin.mygym2.R

class StatisticFragment : Fragment() {

    private val homeFragment = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainframelayout, homeFragment)
                    .commit()
            }
        })

        (activity as AppCompatActivity).supportActionBar?.title = "MyGym - Statistic"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatisticFragment()
    }
}