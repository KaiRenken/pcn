fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Wrong number of arguments! Exactly one argument required.")
        return
    }

    try {
        val number = args[0].toInt()

        println(computeConsecutivePartitions(number = number))
    } catch (exception: NumberFormatException) {
        println("Argument must be a positive integer!")
    }
}

fun computeConsecutivePartitions(number: Int): List<List<Int>> {
    val partitions: MutableList<List<Int>> = ArrayList()

    for (i in 1..number step 2) {
        if (number % i == 0) partitions.add(computePartition(number = number, divisor = i))
    }

    return partitions
}

fun computePartition(number: Int, divisor: Int): List<Int> {
    return listOf(divisor)
}
