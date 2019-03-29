import com.github.ajalt.mordant.TermColors

fun next(gameOfLife: GameOfLife): GameOfLife {
    return gameOfLife
}

fun main() {
    val gameSize = 5
    var gameOfLife = GameOfLife.square(gameSize)
        .swap(CellCoordinate(2, 1))
        .swap(CellCoordinate(3, 1))
        .swap(CellCoordinate(1, 2))
        .swap(CellCoordinate(2, 2))
        .swap(CellCoordinate(2, 3))

    with(TermColors()) {
        (0..100).forEach { _ ->
            render(gameOfLife)
            gameOfLife = update(gameOfLife)
        }
    }
}

fun update(gameOfLife: GameOfLife): GameOfLife =
    GameOfLife.build(gameOfLife.size.first) { i, j ->
        val numberOfLivingNeighbors = gameOfLife.numberOfLivingNeighbors(CellCoordinate(i, j))
        val currentCell = gameOfLife.cells[i][j]

        when {
            currentCell && numberOfLivingNeighbors < 2 -> false
            currentCell && numberOfLivingNeighbors in (2..3) -> true
            !currentCell && numberOfLivingNeighbors == 3 -> true
            else -> false
        }
    }