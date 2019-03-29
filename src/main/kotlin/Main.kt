import com.github.ajalt.mordant.TermColors

fun next(gameOfLife: GameOfLife): GameOfLife {
    return gameOfLife
}

fun main() {
    val gameSize = 10
    val gameOfLife = GameOfLife.square(gameSize)
        .swap(CellCoordinate(2, 1))
        .swap(CellCoordinate(3, 1))
        .swap(CellCoordinate(1, 2))
        .swap(CellCoordinate(2, 2))
        .swap(CellCoordinate(2, 3))

    with(TermColors()) {
        render(gameOfLife)
    }
}