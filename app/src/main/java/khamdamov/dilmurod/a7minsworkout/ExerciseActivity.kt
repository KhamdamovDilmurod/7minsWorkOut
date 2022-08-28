package khamdamov.dilmurod.a7minsworkout


import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import khamdamov.dilmurod.a7minsworkout.databinding.ActivityExerciseBinding
import khamdamov.dilmurod.a7minsworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimeDuration: Long = 10

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimeDuration: Long = 30

    private var exerciseList: MutableList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var mediaPlayer: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()

        setUpExerciseStatusAdapter()

        setUpRestView()

    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(restTimeDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = restTimeDuration.toInt() - restProgress
                binding.tvTimer.text = (restTimeDuration.toInt() - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpExerciseView() {

        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()

        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    private fun setExerciseProgressBar() {
        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimeDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress =
                    exerciseTimeDuration.toInt() - exerciseProgress
                binding.tvExerciseTimer.text =
                    (exerciseTimeDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {

                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                if (currentExercisePosition < exerciseList!!.size - 1) {
                    setUpRestView()
                } else {
                    startActivity(Intent(this@ExerciseActivity, FinishActivity::class.java))
                    finish()
                }
            }
        }.start()
    }

    private fun setUpRestView() {

        try {
//        val soundUri = Uri.parse("android:resource://khamdamov.dilmurod.a7minsworkout/" + R.raw.press_start)
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.press_start)
            mediaPlayer!!.isLooping = false
            mediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.GONE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        binding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setUpExerciseStatusAdapter(){
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this@ExerciseActivity)
        val dialogView = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogView.root)
        dialogView.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        dialogView.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }
}