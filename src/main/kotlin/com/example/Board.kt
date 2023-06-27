package com.example


class Board(val length: Int, val width: Int) {
    var array = Array(length) { BooleanArray(width) }

    fun create() {
        for(i in 0 until length) {
            for (j in 0 until width) {
                array[i][j] = false
            }
        }
    }

    private fun checkIndexX(x: Int): Boolean {
        return ((x >= 0) && (x < length))
    }

    private fun checkIndexY(x: Int): Boolean {
        return ((x >= 0) && (x < width))
    }

    fun countNeighbours() {
        var nextState = Array(length) { BooleanArray(width) }

        for (x in 0 until length) {
            for (y in 0 until width) {
                var count = 0

                for (i in x - 1..x + 1) {
                    for (j in y - 1..y + 1) {
                        if (checkIndexX(i) && checkIndexY(j) && array[i][j]) {
                            count++
                            }
                        }
                    }
                if(array[x][y]) count--
                nextState[x][y] = when {
                    (array[x][y] && count in 2..3) -> true
                    (!array[x][y] && count == 3) -> true
                    else -> false
                }
            }
        }
        for (ver in 0 until length) {
            for (hor in 0 until width) {
                array[ver][hor] = nextState[ver][hor]
            }
        }
    }

}