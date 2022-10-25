package ru.hivislav.compositiongame.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.hivislav.compositiongame.R
import ru.hivislav.compositiongame.databinding.FragmentGameBinding
import ru.hivislav.compositiongame.domain.entities.GameResult
import ru.hivislav.compositiongame.domain.entities.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private val viewModelFactory by lazy {
        GameViewModelFactory(level, requireActivity().application)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val textViewAnswers by lazy {
        mutableListOf<TextView>().apply {
            with(binding) {
                add(answerOneTextViewGameFragment)
                add(answerTwoTextViewGameFragment)
                add(answerThreeTextViewGameFragment)
                add(answerFourTextViewGameFragment)
                add(answerFiveTextViewGameFragment)
                add(answerSixTextViewGameFragment)
            }
        }
    }

    private var _binding: FragmentGameBinding? = null
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

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToAnswers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClickListenersToAnswers() {
        for (textViewAnswer in textViewAnswers) {
            textViewAnswer.setOnClickListener {
                viewModel.chooseAnswer(textViewAnswer.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            with(binding) {
                sumTextViewGameFragment.text = it.sum.toString()
                visibleNumberTextViewGameFragment.text = it.visibleNumber.toString()
            }
            for (i in 0 until textViewAnswers.size) {
                textViewAnswers[i].text = it.options[i].toString()
            }
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBarGameFragment.setProgress(it, true)
        }

        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressTextViewGameFragment.setTextColor(getColorByState(it))
        }

        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBarGameFragment.progressTintList = ColorStateList.valueOf(
                getColorByState(it)
            )
        }

        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.timerTextViewGameFragment.text = it
        }

        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBarGameFragment.secondaryProgress = it
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }

        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.progressTextViewGameFragment.text = it
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        private const val KEY_LEVEL = "level"
        const val NAME = "GameFragment"

        fun newInstance(level: Level) = GameFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_LEVEL, level)
            }
        }
    }
}