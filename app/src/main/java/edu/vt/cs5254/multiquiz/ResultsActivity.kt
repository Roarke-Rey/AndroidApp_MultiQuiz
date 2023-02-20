package edu.vt.cs5254.multiquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import edu.vt.cs5254.multiquiz.databinding.ActivityResultsBinding

private const val CORRECT_ANSWERS = "edu.vt.cs5254.multiquiz.correct_answers"
private const val SUBMITTED_ANSWERS = "edu.vt.cs5254.multiquiz.submitted_answers"
private const val HINTS_USED = "edu.vt.cs5254.multiquiz.hints_used"

private const val RESET_BUTTON_SELECTED = "edu.vt.cs5254.multiquiz.reset_all"

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding

    private val resultsViewModel: ResultsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.resetAllButton.setOnClickListener {
            resultsViewModel.isButtonSelected = true
            var data = Intent().apply {
                putExtra(RESET_BUTTON_SELECTED, resultsViewModel.isButtonSelected)
            }
            setResult(Activity.RESULT_OK, data)
            updateResults()
        }
        updateResults()

    }

    private fun updateResults(){
        if ( !resultsViewModel.isButtonSelected){
            binding.correctAnswersValue.text = intent.getIntExtra(CORRECT_ANSWERS, 0).toString()
            binding.submittedAnswersValue.text = intent.getIntExtra(SUBMITTED_ANSWERS, 0).toString()
            binding.hintsUsedValue.text = intent.getIntExtra(HINTS_USED, 0).toString()
        } else{
            binding.correctAnswersValue.text = "0"
            binding.submittedAnswersValue.text = "0"
            binding.hintsUsedValue.text = "0"
            binding.resetAllButton.isEnabled = false
        }
    }

    companion object{
        fun newIntent(
            packageContext: Context,
            correctAnswers: Int,
            submittedAnswers: Int,
            hintsUsed: Int
        ): Intent {
            return Intent(packageContext, ResultsActivity::class.java).apply{
                putExtra(CORRECT_ANSWERS, correctAnswers)
                putExtra(SUBMITTED_ANSWERS, submittedAnswers)
                putExtra(HINTS_USED, hintsUsed)
            }
        }

    }
}