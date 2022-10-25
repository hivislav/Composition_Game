package ru.hivislav.compositiongame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.hivislav.compositiongame.R
import ru.hivislav.compositiongame.databinding.FragmentGameFinishedBinding

class GameFinishedFragment() : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()
    private val gameResult by lazy {
        args.gameResult
    }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.retryButtonGameFinishedFragment.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews() {
        with(binding) {
            resultImageViewGameFinishedFragment.setImageResource(getSmileResId())
            requiredAnswersTextViewGameFinishedFragment.text = String.format(
                getString(R.string.required_score),
                gameResult.gameSettings.minCountOfRightAnswers.toString()
            )
            scoreAnswersTextViewGameFinishedFragment.text = String.format(
                getString(R.string.score_answers),
                gameResult.countOfRightAnswers
            )
            requiredPercentTextViewGameFinishedFragment.text = String.format(
                getString(R.string.required_percent),
                gameResult.gameSettings.minPercentOfRightAnswers
            )
            scorePercentTextViewGameFinishedFragment.text = String.format(
                getString(R.string.score_percent),
                calculatePercentOfRightAnswers()
            )
        }
    }

    private fun getSmileResId(): Int {
        return if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (gameResult.countOfQuestions == 0) {
            return 0
        }
        return ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}