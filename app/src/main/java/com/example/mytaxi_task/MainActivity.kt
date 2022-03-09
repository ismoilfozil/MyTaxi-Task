package com.example.mytaxi_task

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mytaxi_task.databinding.ActivityMainBinding
import com.example.mytaxi_task.utils.custom.divider.ItemDecorationWithLeftPadding
import com.example.mytaxi_task.utils.toPx
import com.google.android.material.internal.NavigationMenuView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.drawerLayout.setScrimColor(ContextCompat.getColor(this, android.R.color.transparent))
        binding.drawerLayout.drawerElevation = 0f

        val navController =findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        ){
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)

                val slideX = drawerView.width * slideOffset
                val scaleFactorX = 9f
                val scaleFactorY = 6f

                binding.contentLayout.root.apply {
                    translationX = slideX
                    scaleX = 1 - slideOffset/scaleFactorX
                    scaleY = 1 - slideOffset/scaleFactorY
                    radius = if (slideX < 20f) 0f else 60f
                }
            }
        }

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)


        val navMenuView:NavigationMenuView = binding.navView.getChildAt(0) as NavigationMenuView
        val itemDecoration = ItemDecorationWithLeftPadding(this, 16.toPx)
        navMenuView.addItemDecoration(itemDecoration)
    }


    fun openDrawer(){
        if (binding.drawerLayout.isOpen) binding.drawerLayout.close() else binding.drawerLayout.open()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) binding.drawerLayout.close() else super.onBackPressed()
    }

}