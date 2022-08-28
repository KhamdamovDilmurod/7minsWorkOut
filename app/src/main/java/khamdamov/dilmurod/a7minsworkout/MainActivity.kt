package khamdamov.dilmurod.a7minsworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import khamdamov.dilmurod.a7minsworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
        binding.llStart.setOnClickListener {
                val intent = Intent(this, ExerciseActivity::class.java)
                startActivity(intent)
        }
        binding.llBMI.setOnClickListener {
            startActivity(Intent(this,BMIActivity::class.java))
        }
        binding.llHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

    }
}