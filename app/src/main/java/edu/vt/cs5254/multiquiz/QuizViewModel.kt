package edu.vt.cs5254.multiquiz

import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_one, listOf(
            Answer(R.string.question_one_answer_one, isCorrect = true, isEnabled = true, isSelected = false),
            Answer(R.string.question_one_answer_two, isCorrect = false, isEnabled = true, isSelected = false),
            Answer(R.string.question_one_answer_three, isCorrect = false, isEnabled = true, isSelected = false),
            Answer(R.string.question_one_answer_four, isCorrect = false, isEnabled = true, isSelected = false),
        )),
        Question(R.string.question_two, listOf(
            Answer(R.string.question_two_answer_one, isCorrect = false, isEnabled = true, isSelected = false),
            Answer(R.string.question_two_answer_two, isCorrect = true, isEnabled = true, isSelected = false),
            Answer(R.string.question_two_answer_three, isCorrect = false, isEnabled = true, isSelected = false),
            Answer(R.string.question_two_answer_four, isCorrect = false, isEnabled = true, isSelected = false),
        )),
        Question(R.string.question_three, listOf(
            Answer(R.string.question_three_answer_one, isCorrect = false, isEnabled = true, isSelected = false),
            Answer(R.string.question_three_answer_two, isCorrect = false, isEnabled = true, isSelected = false),
            Answer(R.string.question_three_answer_three, isCorrect = true, isEnabled = true, isSelected = false),
            Answer(R.string.question_three_answer_four, isCorrect = false, isEnabled = true, isSelected = false),
        )),
        Question(R.string.question_four, listOf(
            Answer(R.string.question_four_answer_one, isCorrect = false, isEnabled = true, isSelected = false),
            Answer(R.string.question_four_answer_two, isCorrect = false, isEnabled = true, isSelected = false),
            Answer(R.string.question_four_answer_three, isCorrect = false, isEnabled = true, isSelected = false),
                Answer(R.string.question_four_answer_four, isCorrect = true, isEnabled = true, isSelected = false),
        ))
    )

    var currentIndex = 0

    val submittedAnswers: Int = questionBank.size

    val currentQuestionResId: Int
        get() = questionBank[currentIndex].questionResId

    val currentAnswerBank: List<Answer>
        get() = questionBank[currentIndex].answerList

    val hasMoreQuestions: Boolean
        get() = currentIndex != questionBank.size - 1

    fun goToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun getCorrectAnswerCount(): Int{
        var count = 0
        questionBank.forEach {
            count += it.answerList.count { it.isCorrect && it.isSelected }
        }
        return count
    }

    fun getHintsUsedCount(): Int{
        var count = 0
        questionBank.forEach {
            count += it.answerList.count { !it.isEnabled }
        }
        return count
    }

    fun resetQuiz(){
        questionBank.forEach {
            it.answerList.forEach {
                it.isSelected = false
                it.isEnabled = true
            } }
    }
}