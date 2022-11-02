package ru.hivislav.compositiongame.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ru.hivislav.compositiongame.R
import ru.hivislav.compositiongame.domain.entities.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percent),
        count
    )
}

@BindingAdapter("scorePercent")
fun bindScoredPercent(textView: TextView, gameResult: GameResult) {
    val count = if (gameResult.countOfQuestions == 0) {
        0
    } else {
        ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }

    textView.text = String.format(
        textView.context.getString(R.string.score_percent),
        count
    )
}

@BindingAdapter("resultImage")
fun bindResultImage(imageView: ImageView, gameResult: GameResult) {
    val resId = if (gameResult.winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }

    imageView.setImageResource(resId)
}

@BindingAdapter("question")
fun bindQuestion(textView: TextView, sumQuestion: Int) {
    textView.text = sumQuestion.toString()
}

@BindingAdapter("visibleNumber")
fun bindVisibleNumber(textView: TextView, visibleNumber: Int) {
    textView.text = visibleNumber.toString()
}

@BindingAdapter("progressTextColor")
fun bindProgressTextColor(textView: TextView, goodState: Boolean) {
    textView.setTextColor(getColorByState(textView.context, goodState))
}

@BindingAdapter("progressBarColor")
fun bindProgressBarColor(progressBar: ProgressBar, goodState: Boolean) {
    progressBar.progressTintList = ColorStateList.valueOf(
        getColorByState(
            progressBar.context,
            goodState
        )
    )
}

private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.optionClick(textView.text.toString().toInt())
    }
}

interface OnOptionClickListener {
    fun optionClick(option: Int)
}