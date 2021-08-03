package com.example.testapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.testapp2.databinding.ActivityMainBinding

//import kotlinx.android.synthetic.main.activity_main.*
//import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Can use findViewById to get the item from activity_main.xml
        Must import android.widget.TextView (etc) */
        // val textview_progress = findViewById<TextView>(R.id.textview_progress)

        /* Or can add (DEPRECATED) 'kotlin-android-extensions' to build.gradle and do this
        Must import import kotlinx.android.synthetic.main.activity_main.* */
        //val initialTextViewTranslationY = textview_progress.translationY

        /* Or can use the modern method, View Binding. See above code.
        Must also set "buildFeatures { viewBinding true }" in module-level build.gradle. */
        val initialTextViewTranslationY = binding.textviewProgress.translationY

        binding.seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.textviewProgress.setText(binding.seekbar.progress.toString())

                val translationDistance = (initialTextViewTranslationY +
                        binding.seekbar.progress * resources.getDimension(R.dimen.text_anim_step) * -1)
                binding.textviewProgress.animate().translationY(translationDistance)

                // Only do the spin animation if text was changed programmatically (by Reset button)
                // NOTE: p2 is "fromUser".
                if(!p2)
                    // Rotate 360 degrees for 500ms, and move back to initial Y position.
                    binding.textviewProgress.animate().setDuration(500).rotationBy(360f)
                        .translationY(initialTextViewTranslationY)
                }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        // The "v ->" part is called a lambda. ToDo: Learn more about lambdas.
        binding.buttonReset.setOnClickListener { v ->
            binding.seekbar.setProgress(0)
        }
    }
}