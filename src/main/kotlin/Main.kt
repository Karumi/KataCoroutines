@file:JvmName("Main")

import com.github.ajalt.mordant.TermColors
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val gameSize = 25
    var gameOfLife = GameOfLife.rPentomino(gameSize)

    with(TermColors()) {
        while (true) {
            render(gameOfLife)
            gameOfLife = update(gameOfLife)
            resetCursor(gameSize)
        }
    }
}

suspend fun update(gameOfLife: GameOfLife): GameOfLife = GameOfLife.build(gameOfLife.size.first) { i, j ->
    val numberOfLivingNeighbors = gameOfLife.numberOfLivingNeighbors(CellCoordinate(i, j))
    val currentCell = gameOfLife.cells[i][j]

    delay(1)

    when {
        currentCell && numberOfLivingNeighbors < 2 -> false
        currentCell && numberOfLivingNeighbors in (2..3) -> true
        !currentCell && numberOfLivingNeighbors == 3 -> true
        else -> false
    }
}
