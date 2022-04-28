package ru.mamzin.mygym2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import ru.mamzin.mygym2.databinding.ActivityMainBinding
import ru.mamzin.mygym2.viewmodel.ExerciseViewModel
import ru.mamzin.mygym2.views.AboutFragment
import ru.mamzin.mygym2.views.AddExerciseCategoryFragment
import ru.mamzin.mygym2.views.MainFragment
import ru.mamzin.mygym2.views.StatisticFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val homeFragment = MainFragment()
    lateinit var viewmodel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainframelayout, homeFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_category -> {loadFragment(item.itemId)}
            R.id.menu_statistic -> {loadFragment(item.itemId)}
            R.id.menu_about -> {loadFragment(item.itemId)}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.menu_add_category -> { AddExerciseCategoryFragment.newInstance() }
            R.id.menu_statistic -> { StatisticFragment.newInstance() }
            R.id.menu_about -> { AboutFragment.newInstance() }
            else -> { null }
        }
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainframelayout, fragment, tag)
                .commit()
        }
    }
}