package ru.hivislav.compositiongame.domain.usecases

import ru.hivislav.compositiongame.domain.entities.GameSettings
import ru.hivislav.compositiongame.domain.entities.Level
import ru.hivislav.compositiongame.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}