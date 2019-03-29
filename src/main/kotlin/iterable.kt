fun <E> List<List<E>>.updatedMatrix(x: Int, y: Int, elem: E) =
    updated(x, this[x].updated(y, elem))

fun <E> Iterable<E>.updated(index: Int, elem: E) = mapIndexed { i, existing -> if (i == index) elem else existing }