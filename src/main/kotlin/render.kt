import com.github.ajalt.mordant.TermColors

fun TermColors.render(gameOfLife: GameOfLife) {
    val columns = gameOfLife.size.second

    printHeader(columns)

    gameOfLife.asListOfCells.forEachIndexed { index, cell ->
        printFirstColumnCharacterIfNeeded(index, columns)

        printCell(cell)

        printLastColumnIfNeeded(index, columns)
    }

    printFooter(columns)
}

private fun TermColors.printCell(cell: Cell) {
    if (cell) {
        print(red("O"))
    } else {
        print(" ")
    }
}

private fun printLastColumnIfNeeded(index: Int, columns: Int) {
    if (index % columns == columns - 1) {
        if (index < columns * columns - 1) {
            println("|")
        } else {
            print("|")
        }
    }
}

private fun printFirstColumnCharacterIfNeeded(index: Int, columns: Int) {
    if (index % columns == 0) {
        print("|")
    }
}

private fun TermColors.printFooter(columns: Int) {
    println()
    print(" ")
    (1..columns).forEach { _ -> print(red("¯")) }
    println()
}

private fun TermColors.printHeader(columns: Int) {
    print(" ")
    (1..columns).forEach { _ -> print(red("_")) }
    println()
}