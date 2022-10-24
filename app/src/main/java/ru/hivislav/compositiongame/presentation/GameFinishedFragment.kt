package ru.hivislav.compositiongame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.hivislav.compositiongame.R
import ru.hivislav.compositiongame.databinding.FragmentGameFinishedBinding
import ru.hivislav.compositiongame.domain.entities.GameResult

class GameFinishedFragment() : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

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
        onBackPressedListener()
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

    private fun onBackPressedListener() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager
            .popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    companion object {

        private const val KEY_GAME_RESULT = "game result"

        fun newInstance(gameResult: GameResult) = GameFinishedFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_GAME_RESULT, gameResult)
            }
        }
    }
}