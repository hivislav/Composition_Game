package ru.hivislav.compositiongame.presentation

import android.widget.ImageView
import android.widget.TextView
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
