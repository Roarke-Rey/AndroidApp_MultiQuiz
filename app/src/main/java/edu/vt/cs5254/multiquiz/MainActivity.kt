package edu.vt.cs5254.multiquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import edu.vt.cs5254.multiquiz.databinding.ActivityMainBinding

private const val RESET_BUTTON_SELECTED = "edu.vt.cs5254.multiquiz.reset_all"

class MainActivity : AppCompatActivity() {

    // Name: Shreyas Pawar
    // PID: shreyaspawar

    private val quizViewModel: QuizViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonList: List<Button>

    private val resultsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        quizViewModel.currentIndex = 0
        val isResetSelected = result.data?.getBooleanExtra(RESET_BUTTON_SELECTED, false)
        if (isResetSelected != null) {
            quizViewModel.resetQuiz()
        }
        updateListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonList = listOf(
            binding.answer0Button,
            binding.answer1Button,
            binding.answer2Button,
            binding.answer3Button
        )
        updateListeners()
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionResId
        binding.questionText.setText(questionTextResId)

        quizViewModel.currentAnswerBank.zip(buttonList)
            .forEach { (answer, button) ->
                button.isSelected = answer.isSelected
                button.isEnabled = answer.isEnabled
                button.setText(answer.textResId)
                button.updateColor()
            }

        binding.hintButton.isEnabled = quizViewModel.currentAnswerBank.count { it.isEnabled } > 1
        binding.submitButton.isEnabled = quizViewModel.currentAnswerBank.any{ it.isSelected }
    }

    private fun updateListeners(){
        quizViewModel.currentAnswerBank.zip(buttonList)
            .forEach { (answer, button) ->
                button.setOnClickListener {
                    quizViewModel.currentAnswerBank.filter{ it != answer }
                        .forEach {
                            it.isSelected = false
                        }
                    answer.isSelected = !answer.isSelected
                    updateQuestion()
                }
            }

        binding.hintButton.setOnClickListener {
            val answer = quizViewModel.currentAnswerBank.filter{ (it.isEnabled && !it.isCorrect) }.random()
            answer.isEnabled = false
            answer.isSelected = false
            updateQuestion()
        }

        binding.submitButton.setOnClickListener {
            if (quizViewModel.hasMoreQuestions){
                quizViewModel.goToNext()
                updateListeners()
            } else {
                val intent = ResultsActivity.newIntent(this@MainActivity,
                    quizViewModel.getCorrectAnswerCount(),
                    quizViewModel.submittedAnswers,
                    quizViewModel.getHintsUsedCount())
                resultsLauncher.launch(intent)
            }

        }

        updateQuestion()
    }

}