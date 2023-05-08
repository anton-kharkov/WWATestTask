package ua.anton.wwatesttask.game.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GameRepository(context: Context) {

    private val gson = Gson()

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.packageName + ".DB", Context.MODE_PRIVATE)

    var score: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_SCORE, 0)
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_SCORE, value).apply()
            field = value
        }

    var highScore: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_HIGH_SCORE, 0)
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_HIGH_SCORE, value).apply()
            field = value
        }

    var boardState: List<IntArray> = emptyList()
        get() = gson.fromJson(
            sharedPreferences.getString(PREFERENCE_BOARD_STATE, "[]"),
            object : TypeToken<MutableList<IntArray>>() {}.type
        )
        set(value) {
            sharedPreferences.edit().putString(PREFERENCE_BOARD_STATE, gson.toJson(value)).apply()
            field = value
        }

    companion object {
        private const val PREFERENCE_BOARD_STATE = "boardState"
        private const val PREFERENCE_SCORE = "score"
        private const val PREFERENCE_HIGH_SCORE = "highScore"
    }
}