package ua.anton.wwatesttask.game

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import ua.anton.wwatesttask.game.model.GameLogic
import ua.anton.wwatesttask.game.repository.GameRepository
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {


    private val gameRepository = GameRepository(context)

    private val _score = MutableStateFlow(gameRepository.score)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _highScore = MutableStateFlow(gameRepository.highScore)
    val highScore: StateFlow<Int> = _highScore.asStateFlow()


    val gameLogic = GameLogic(
        size = 4,
        score = gameRepository.score,
        state = gameRepository.boardState,
        onScoreChange = { score ->
            gameRepository.score = _score.updateAndGet { score }
            if (highScore.value < score) gameRepository.highScore =
                _highScore.updateAndGet { score }
        },
        onMove = {
            with(gameRepository) {
                boardState = it
            }
        },
    )

    val grid = mutableStateOf(gameLogic.grid)

//    init {
//        viewModelScope.launch {
//            flow {
//                while (true) {
//                    emit(Unit)
//                    delay(1.seconds)
//                }
//            }
//        }
//    }
}