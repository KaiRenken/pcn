package de.kairenken.pcn

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ConsecutivePartitionsFactoryKtTest {

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80])
    fun `createPartitions`(input: Int) {
        val result = createConsecutivePartitions(input)

        result.size shouldBe numberOfOddDivisorsOf(input)
        result.forEach { it shouldBeAConsecutivePartitionOf input }
    }

    private fun numberOfOddDivisorsOf(n: Int): Int {
        var result = 0

        for (i in 1..n) {
            if (i.isOdd() && i divides n) result++
        }

        return result
    }

    private fun Int.isOdd() = this % 2 == 1

    private infix fun Int.divides(other: Int) = other % this == 0

    private infix fun List<Int>.shouldBeAConsecutivePartitionOf(n: Int) {
        this.sum() shouldBe n

        val firstInt = this[0]
        val lastInt = this[this.size - 1]

        firstInt shouldBeGreaterThan 0
        lastInt shouldBeGreaterThanOrEqual firstInt

        for (i in 0..lastInt - firstInt) {
            this[i] shouldBe firstInt + i
        }
    }
}
