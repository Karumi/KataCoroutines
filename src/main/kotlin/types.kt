import HorizontalDirection.Center
import HorizontalDirection.East
import HorizontalDirection.West
import VerticalDirection.North
import VerticalDirection.South
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

typealias Cell = Boolean

data class CellCoordinate(val x: Int, val y: Int)
data class Direction(val vertical: VerticalDirection, val horizontal: HorizontalDirection) {
    companion object {
        val NorthWest: Direction = Direction(North, West)
        val NorthCenter: Direction = Direction(North, Center)
        val NorthEast: Direction = Direction(North, East)
        val CenterWest: Direction = Direction(VerticalDirection.Center, West)
        val CenterEast: Direction = Direction(VerticalDirection.Center, East)
        val SouthWest: Direction = Direction(South, West)
        val SouthCenter: Direction = Direction(South, Center)
        val SouthEast: Direction = Direction(South, East)
    }
}

enum class VerticalDirection {
    North, Center, South
}

enum class HorizontalDirection {
    West, Center, East
}

data class GameOfLife(
    val cells: List<List<Cell>>
) {
    companion object {

        private val dispatcher = Executors.newFixedThreadPool(8).asCoroutineDispatcher()

        fun square(size: Int): GameOfLife =
            GameOfLife(cells = (1..size).map { (1..size).map { false } })

        suspend fun build(size: Int, generator: suspend (Int, Int) -> Cell): GameOfLife = withContext(dispatcher) {
            val cells = MutableList(size) { MutableList(size) { false } }

            for (i in (1..size)) {
                for (j in (1..size)) {
                    launch {
                        cells[i - 1][j - 1] = generator(i - 1, j - 1)
                    }
                }
            }

            GameOfLife(cells)
        }
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
            neighbor(coordinate, Direction.NorthCenter),
            neighbor(coordinate, Direction.NorthEast),
            neighbor(coordinate, Direction.CenterWest),
            neighbor(coordinate, Direction.CenterEast),
            neighbor(coordinate, Direction.SouthWest),
            neighbor(coordinate, Direction.SouthCenter),
            neighbor(coordinate, Direction.SouthEast)
        ).fold(0) { acc, cell -> if (cell) acc + 1 else acc }

    private fun neighbor(coordinate: CellCoordinate, direction: Direction): Cell {
        val offset = when {
            direction.horizontal == West &&
                    direction.vertical == North -> -1 to -1
            direction.horizontal == Center &&
                    direction.vertical == North -> 0 to -1
            direction.horizontal == East &&
                    direction.vertical == North -> 1 to -1
            direction.horizontal == West &&
                    direction.vertical == VerticalDirection.Center -> -1 to 0
            direction.horizontal == Center &&
                    direction.vertical == VerticalDirection.Center -> 0 to 0
            direction.horizontal == East &&
                    direction.vertical == VerticalDirection.Center -> 1 to 0
            direction.horizontal == West &&
                    direction.vertical == South -> -1 to 1
            direction.horizontal == Center &&
                    direction.vertical == South -> 0 to 1
            else -> 1 to 1
        }

        return cells
            .getOrElse(coordinate.x + offset.first) { emptyList() }
            .getOrElse(coordinate.y + offset.second) { false }
    }
}