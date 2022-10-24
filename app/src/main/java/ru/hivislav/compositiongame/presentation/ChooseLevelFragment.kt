package ru.hivislav.compositiongame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.hivislav.compositiongame.R
import ru.hivislav.compositiongame.databinding.FragmentChooseLevelBinding
import ru.hivislav.compositiongame.domain.entities.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment(level: Level) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    private fun setupButtonClickListeners() {
        with(binding) {
            testLevelButtonChooseLevelFragment.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            easyLevelButtonChooseLevelFragment.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            normalLevelButtonChooseLevelFragment.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            hardLevelButtonChooseLevelFragment.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    companion object {
        fun newInstance() = ChooseLevelFragment()
    }
}