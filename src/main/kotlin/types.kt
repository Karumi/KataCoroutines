import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

typealias Cell = Boolean

data class CellCoordinate(val x: Int, val y: Int)
enum class Direction {
    NorthWest, NorthHCenter, NorthEast,
    VCenterWest, /*VCenterHCenter, */ VCenterEast,
    SouthWest, SouthHCenter, SouthEast
}

data class GameOfLife(
    val cells: List<List<Cell>>
) {
    companion object {

        private val dispatcher = Executors.newFixedThreadPool(8).asCoroutineDispatcher()

        suspend fun build(size: Int, generator: suspend (Int, Int) -> Cell): GameOfLife = withContext(dispatcher) {
            val cells = (0 until size)
                .map { column -> (0 until size).map { row -> async { generator(column, row) } } }
                .map { col -> col.map { it.await() } }

            return@withContext GameOfLife(cells)
        }

        fun rPentomino(gameSize: Int): GameOfLife {
            val startingCoordinate = gameSize / 2
            return GameOfLife.square(gameSize)
                .swap(CellCoordinate(startingCoordinate + 2, startingCoordinate + 1))
                .swap(CellCoordinate(startingCoordinate + 3, startingCoordinate + 1))
                .swap(CellCoordinate(startingCoordinate + 1, startingCoordinate + 2))
                .swap(CellCoordinate(startingCoordinate + 2, startingCoordinate + 2))
                .swap(CellCoordinate(startingCoordinate + 2, startingCoordinate + 3))
        }

        private fun square(size: Int): GameOfLife =
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

    fun numberOfLivingNeighbors(coordinate: CellCoordinate): Int =
        listOf(
            neighbor(coordinate, Direction.NorthWest),
            neighbor(coordinate, Direction.NorthHCenter),
            neighbor(coordinate, Direction.NorthEast),
            neighbor(coordinate, Direction.VCenterWest),
            neighbor(coordinate, Direction.VCenterEast),
            neighbor(coordinate, Direction.SouthWest),
            neighbor(coordinate, Direction.SouthHCenter),
            neighbor(coordinate, Direction.SouthEast)
        ).fold(0) { acc, cell -> if (cell) acc + 1 else acc }

    private fun neighbor(coordinate: CellCoordinate, direction: Direction): Cell {
        val offset = when (direction) {
            Direction.NorthWest -> -1 to -1
            Direction.NorthHCenter -> 0 to -1
            Direction.NorthEast -> 1 to -1
            Direction.VCenterWest -> -1 to 0
            Direction.VCenterEast -> 1 to 0
            Direction.SouthWest -> -1 to 1
            Direction.SouthHCenter -> 0 to 1
            Direction.SouthEast -> 1 to 1
        }

        return cells
            .getOrElse(coordinate.x + offset.first) { emptyList() }
            .getOrElse(coordinate.y + offset.second) { false }
    }
}