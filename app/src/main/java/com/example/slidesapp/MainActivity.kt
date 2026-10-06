package com.example.slidesapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.slidesapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val slides = generateSlides()
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupButtons()
        setupFab()
        updateIndicator()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = SlideAdapter(slides)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPosition = position
                updateIndicator()
                updateButtonStates()
            }
        })
        updateButtonStates()
    }

    private fun setupButtons() {
        binding.btnPrev.setOnClickListener { goToSlide(currentPosition - 1) }
        binding.btnNext.setOnClickListener { goToSlide(currentPosition + 1) }
    }

    private fun setupFab() {
        binding.fabJump.setOnClickListener { showJumpDialog() }
    }

    private fun goToSlide(position: Int) {
        if (position in 0 until slides.size) {
            binding.viewPager.setCurrentItem(position, true)
        }
    }

    private fun updateIndicator() {
        binding.tvSlideIndicator.text = getString(R.string.slide_number, currentPosition + 1, slides.size)
    }

    private fun updateButtonStates() {
        binding.btnPrev.isEnabled = currentPosition > 0
        binding.btnNext.isEnabled = currentPosition < slides.size - 1
        binding.btnNext.text = if (currentPosition == slides.size - 1) "Finish" else getString(R.string.next)
    }

    private fun showJumpDialog() {
        val input = android.widget.EditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            hint = "Enter slide number (1-${slides.size})"
        }
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.jump_to)
            .setView(input)
            .setPositiveButton(R.string.go) { _, _ ->
                val text = input.text.toString().toIntOrNull()
                if (text != null && text in 1..slides.size) {
                    goToSlide(text - 1)
                } else {
                    Toast.makeText(this@MainActivity, "Invalid slide number", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun generateSlides(): List<Slide> {
        val colors = intArrayOf(
            R.color.slide_bg_1, R.color.slide_bg_2, R.color.slide_bg_3,
            R.color.slide_bg_4, R.color.slide_bg_5, R.color.slide_bg_6
        )
        val icons = intArrayOf(
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_directions,
            android.R.drawable.ic_menu_edit,
            android.R.drawable.ic_menu_help,
            android.R.drawable.ic_menu_info_details,
            android.R.drawable.ic_menu_manage,
            android.R.drawable.ic_menu_mapmode,
            android.R.drawable.ic_menu_my_calendar,
            android.R.drawable.ic_menu_myplaces,
            android.R.drawable.ic_menu_preferences,
            android.R.drawable.ic_menu_report_image,
            android.R.drawable.ic_menu_revert,
            android.R.drawable.ic_menu_rotate,
            android.R.drawable.ic_menu_save,
            android.R.drawable.ic_menu_search,
            android.R.drawable.ic_menu_send,
            android.R.drawable.ic_menu_set_as,
            android.R.drawable.ic_menu_share,
            android.R.drawable.ic_menu_slideshow,
            android.R.drawable.ic_menu_sort_by_size,
            android.R.drawable.ic_menu_sort_alphabetically,
            android.R.drawable.ic_menu_today,
            android.R.drawable.ic_menu_upload,
            android.R.drawable.ic_menu_view,
            android.R.drawable.ic_menu_zoom,
            android.R.drawable.ic_media_play,
            android.R.drawable.ic_media_pause,
            android.R.drawable.ic_media_next
        )

        return (1..30).map { i ->
            val colorRes = colors[(i - 1) % colors.size]
            val iconRes = icons[(i - 1) % icons.size]
            val hasAction = i % 5 == 0 // Every 5th slide has an action button

            Slide(
                title = "Slide $i",
                content = "This is the content for slide $i. " +
                    "Swipe left/right or use the buttons to navigate. " +
                    "Tap the floating action button to jump to a specific slide.",
                backgroundColor = getColor(colorRes),
                iconRes = iconRes,
                actionText = if (hasAction) "Slide $i Action" else null,
                actionListener = if (hasAction) {
                    { Toast.makeText(this@MainActivity, "Action on Slide $i clicked!", Toast.LENGTH_SHORT).show() }
                } else null
            )
        }
    }
}