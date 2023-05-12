import kotlin.math.ceil
import kotlin.math.floor

fun main(args: Array<String>) {
    if (args.size == 1) {
        try {
            val number = args[0].toInt()

            if (number <= 0) {
                println("Argument must be a positive integer!")
                return
            }

            println(computeConsecutivePartitions(number = number))
            return
        } catch (exception: NumberFormatException) {
            println("Argument must be a positive integer!")
            return
        }
    }

    if (args.size == 3) {
        try {
            val n = args[0].toInt()
            val a = args[1].toInt()
            val b = args[2].toInt()

            if (n <= 0 || a <= 0 || b <= 0) {
                println("Arguments must be positive integers!")
                return
            }

            if (a > b) {
                println("Second argument must be strictly smaller than third argument!")
                return
            }

            if (sum((1..n).toList().toIntArray()) != sum((a..b).toList().toIntArray())) {
                println("Sum from 1 to n must equal sum from a to b")
                return
            }

            val result = Matrix(lines = b - a + 1, columns = n)

            computePartition(n, a, b, result, 0, 0)

            for (i in 0..(b - a)) {
                for (j in 0..(n - 1)) {
                    print("${result.getEntry(line = i, column = j)} ")
                }
                println()
            }

            return
        } catch (exception: NumberFormatException) {
            println("Arguments must be positive integers!")
            return
        }
    }

    println("Wrong number of arguments! Exactly one argument required.")
}

fun computePartition(n: Int, a: Int, b: Int, matrix: Matrix, iter: Int, firstLine: Int) {
    if (a < n) {
        for (i in a..n) {
            matrix.setEntry(i - a + firstLine, iter, i)

            for (j in (iter + 1)..matrix.columns - 1) {
                matrix.setEntry(i - a + firstLine, j, 0)
            }
        }

        for (i in (n + 1)..b) {
            matrix.setEntry(i - a + firstLine, iter, 0)
        }

        if (a != 1) {
            computePartition(a - 1, n + 1, b, matrix, iter + 1, n - a + 1 + firstLine)
        }

        return
    }

    val s = b - a + 1
    val c = (2 * n) - (2 * s) + 1
    var m: Int
    var steps = c - a
    var posX: Int
    var posY: Int
    var temp: Int
    val nextLine: Int

    if (steps < 0) {
        steps = 0
    }

    val result: MutableMap<Int, Int> = HashMap()

    for (i in n..(n - s + 1) step -1) {
        result[i] = c - i
    }

    var i = a
    m = c - i

    while (m > 0) {
        posX = findPairsByStep(m, steps, n - s + 1, false)
        posY = findPairsByStep(m, steps, n - s + 1, true)
        temp = result[posX]!!
        result[posX] = result[posY]!!
        result[posY] = temp
        matrix.setEntry(i - a + firstLine, iter, posX)
        matrix.setEntry(i - a + firstLine, iter + 1, result[posX]!!)
        matrix.setEntry(i - a + (2 * m) + firstLine, iter, posY)
        matrix.setEntry(i - a + (2 * m) + firstLine, iter + 1, result[posY]!!)
        i++
        m = c - i
    }

    if (steps > 0) {
        posX = findFirstFreeSpace(steps, n - s + 1)
        matrix.setEntry(i - a + firstLine, iter, posX)
        matrix.setEntry(i - a + firstLine, iter + 1, result[posX]!!)
    }

    posX = findSecondFreeSpace(steps, n - s + 1)

    if (steps == 0) {
        for (i in 0..(s - 1)) {
            matrix.setEntry(i + firstLine, iter, posX)
            matrix.setEntry(i + firstLine, iter + 1, result[posX]!!)
            posX++
        }
    } else {
        for (i in ((2 * steps) + 1)..(s - 1)) {
            matrix.setEntry(i + firstLine, iter, posX)
            matrix.setEntry(i + firstLine, iter + 1, result[posX]!!)
            posX++
        }
    }

    if (n - (2 * s) > 0) {
        for (i in firstLine..(s + firstLine)) {
            posX = matrix.getEntry(i, iter)
            posY = matrix.getEntry(i, iter + 1)

            if (posX + posY != a + i - firstLine) {
                nextLine = i
                break
            }
        }

        for (i in firstLine..(nextLine - 1)) {
            for (j in (iter + 2)..(matrix.columns - 1)) {
                matrix.setEntry(i, j, 0)
            }
        }

        if (c - a >= 0) {
            computePartition(n - (2 * s), a + (2 * steps) + 1 - c, b - c, matrix, iter + 2, nextLine)
        } else {
            computePartition(n - (2 * s), a - c, b - c, matrix, iter + 2, nextLine)
        }
    } else {
        for (i in (iter + 2)..(matrix.columns - 1)) {
            matrix.setEntry(matrix.lines - 1, i, 0)
        }
    }
}

fun findPairsByStep(step: Int, steps: Int, shift: Int, comp: Boolean): Int {
    if (step > steps) {
        return 0;
    }

    val result: Double

    if ((step % 2) == 1) {
        result = ceil((steps / 2).toDouble()) - ceil((step / 2).toDouble())
    } else {
        result = (ceil((steps / 2).toDouble()) * 2) + floor((steps / 2).toDouble()) - (step / 2)
    }

    if (comp == false) {
        return (result + shift).toInt()
    } else {
        return (result + step + shift).toInt()
    }
}

fun findFirstFreeSpace(steps: Int, shift: Int): Int =
    ((2 * ceil((steps / 2).toDouble())) + floor((steps / 2).toDouble()) + shift).toInt()

fun findSecondFreeSpace(steps: Int, shift: Int): Int {
    if (steps == 1) {
        return 2 + shift
    } else if (steps == 0) {
        return shift
    } else {
        return ((2 * ceil((steps / 2).toDouble())) + (2 * floor((steps / 2).toDouble())) + 1 + shift).toInt()
    }
}

fun sum(summands: IntArray): Int {
    var result: Int = 0

    for (i in summands) {
        result += i
    }

    return result
}

fun computeConsecutivePartitions(number: Int): List<List<Int>> {
    val partitions: MutableList<List<Int>> = ArrayList()

    for (i in 1..number step 2) {
        if (number % i == 0) partitions.add(computeConsecutivePartition(number = number, divisor = i))
    }

    return partitions
}

fun computeConsecutivePartition(number: Int, divisor: Int): List<Int> {
    val result: MutableList<Int> = ArrayList()
    val codivisor = number / divisor

    for (i in (codivisor - ((divisor - 1) / 2))..(codivisor + ((divisor - 1) / 2))) result.add(i)

    if (result[0] <= 0) {
        for (i in result[0]..result[0] * (-1)) result.remove(i)
    }

    return result
}
