package com.example.stopwatch

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.NumberPicker
import android.widget.Toast
import com.example.stopwatch.AdapterClass.LapsAdapter
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var isRunning = false
    private var minutes:String ?= "00:00:00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var listLaps = ArrayList<String>()
        var arrayAdapter = LapsAdapter(this, listLaps)
        binding.lapsList.adapter = arrayAdapter

        binding.imageStopwatch.setOnClickListener {

            var dialog = Dialog(this)
            dialog.setContentView(R.layout.timer_dialog)
            var timePicker = dialog.findViewById<NumberPicker>(R.id.time_picker)
            var buttonSetTime = dialog.findViewById<Button>(R.id.button_set_timer)

            timePicker.minValue = 1
            timePicker.maxValue = 60

            buttonSetTime.setOnClickListener{
                minutes = timePicker.value.toString()
                binding.tvSetTime.text = minutes + ":00 mins"
                dialog.dismiss()
            }

            dialog.show()
        }

        // Original Code // Not working
        /*binding.buttonRun.setOnClickListener {

            // Own Code

            if (!isRunning)
            {
                isRunning = false
                if (!minutes.equals("00:00:00")){

                    var totalMin = minutes!!.toInt()*60*1000L
                    var countdown = 1000L

                    binding.chronometer.base = SystemClock.elapsedRealtime() + totalMin
                    binding.chronometer.format = "%S %S"

                    binding.chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {

                        var elapsedTime = SystemClock.elapsedRealtime() - binding.chronometer.base
                        if (elapsedTime >= totalMin){

                            isRunning = false
                            binding.buttonRun.text = "Run"
                            binding.chronometer.stop()
                            listLaps.clear()

                        }
                    }

                }
                else
                {
                    isRunning = true
                    binding.buttonRun.text = "Stop"
                    binding.chronometer.start()
                    binding.chronometer.base = SystemClock.elapsedRealtime()
                }
            }
            else
            {
                isRunning = false
                binding.buttonRun.text = "Run"
                binding.chronometer.stop()
                listLaps.clear()
            }
            //binding.chronometer.start()

        }*/

        binding.buttonRun.setOnClickListener {

            if (!isRunning) {
                if (!minutes.equals("00:00:00")) {
                    isRunning = true
                    val totalMillis = minutes!!.toInt() * 60 * 1000L

                    binding.chronometer.base = SystemClock.elapsedRealtime() + totalMillis
                    binding.chronometer.format = "%S %S"

                    binding.chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
                        val elapsedTime = binding.chronometer.base - SystemClock.elapsedRealtime()
                        if (elapsedTime <= 0) {
                            isRunning = false
                            binding.buttonRun.text = "Run"
                            binding.chronometer.stop()
                            listLaps.clear()
                            // Reset the mode to the default state when the countdown reaches 0
                            minutes = "00:00:00"
                            binding.tvSetTime.text = "00:00:00"
                        }
                    }

                    binding.chronometer.start()
                    binding.buttonRun.text = "Stop" // Change to "Stop" here

                } else {
                    // Normal stopwatch mode
                    isRunning = true
                    binding.buttonRun.text = "Stop"
                    binding.chronometer.start()
                    binding.chronometer.base = SystemClock.elapsedRealtime()
                }
            } else {
                isRunning = false
                binding.buttonRun.text = "Run"
                binding.chronometer.stop()
                listLaps.clear()

                if (!minutes.equals("00:00:00")) {
                    // Reset the mode to the default state when stopping the countdown timer
                    minutes = "00:00:00"
                    binding.tvSetTime.text = "00:00:00"
                }
            }
        }

        binding.buttonLap.setOnClickListener {

            if(isRunning) {
                listLaps.add(binding.chronometer.text.toString())
                arrayAdapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(this, "Start the Timer first",Toast.LENGTH_SHORT).show()
            }
        }

    }
}