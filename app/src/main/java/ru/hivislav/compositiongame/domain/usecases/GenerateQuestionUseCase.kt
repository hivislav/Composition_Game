package ru.hivislav.compositiongame.domain.usecases

import ru.hivislav.compositiongame.domain.entities.Question
import ru.hivislav.compositiongame.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}