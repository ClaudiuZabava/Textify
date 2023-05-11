package com.messager.textify

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.messager.textify.ui.main.SectionsPagerAdapter
import com.messager.textify.databinding.ActivityMainBinding
import com.messager.textify.databinding.ActivityVerificationBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}




// COD for MainActivity with binding

//private lateinit var binding: ActivityMainBinding
//
//var binding_activity: ActivityVerificationBinding? = null
//var auth: FirebaseAuth? = null
//
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//
//    binding = ActivityMainBinding.inflate(layoutInflater)
//    setContentView(binding.root)
//
//    val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//    val viewPager: ViewPager = binding.viewPager
//    viewPager.adapter = sectionsPagerAdapter
//    val tabs: TabLayout = binding.tabs
//    tabs.setupWithViewPager(viewPager)
//    val fab: FloatingActionButton = binding.fab
//
//    fab.setOnClickListener { view ->
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()
//    }
//}


