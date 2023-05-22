package de.kairenken.pcn

class Matrix(
    val lines: Int,
    val columns: Int,
) {
    private val data: Array<Array<Int>> = Array(lines) {
        Array(columns) {
            0
        }
    }

    fun getEntry(line: Int, column: Int): Int = data[line][column]

    fun setEntry(line: Int, column: Int, entry: Int) {
        data[line][column] = entry
    }
}