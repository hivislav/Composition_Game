package ru.hivislav.compositiongame.domain.repository

import ru.hivislav.compositiongame.domain.entities.GameSettings
import ru.hivislav.compositiongame.domain.entities.Level
import ru.hivislav.compositiongame.domain.entities.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
    fun getGameSettings(level: Level): GameSettings
}