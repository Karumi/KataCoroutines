typealias Cell = Boolean

data class CellCoordinate(val x: Int, val y: Int)

data class GameOfLife(
    val cells: List<List<Cell>>
) {
    companion object {
        fun square(size: Int): GameOfLife =
            GameOfLife(cells = (1..size).map { (1..size).map { false } })
    }

    val asListOfCells: List<Cell>
        get() = cells.flatten()

    val size: Pair<Int, Int> = cells.size to cells.first().size

    fun swap(coordinate: CellCoordinate): GameOfLife =
        copy(
            cells = cells.updatedMatrix(
                coordinate.y,
                coordinate.x,
                !cells[coordinate.y][coordinate.x]
            )
        )
}