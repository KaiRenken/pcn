package de.kairenken.pcn

fun createConsecutivePartitions(n: Int): List<List<Int>> {
    val partitions: MutableList<List<Int>> = ArrayList()

    for (i in 1..n step 2) {
        if (n % i == 0) partitions.add(computeConsecutivePartition(n = n, divisor = i))
    }

    return partitions
}

private fun computeConsecutivePartition(n: Int, divisor: Int): List<Int> {
    val result: MutableList<Int> = ArrayList()
    val codivisor = n / divisor

    for (i in (codivisor - ((divisor - 1) / 2))..(codivisor + ((divisor - 1) / 2))) result.add(i)

    if (result[0] <= 0) {
        for (i in result[0]..result[0] * (-1)) result.remove(i)
    }

    return result
}