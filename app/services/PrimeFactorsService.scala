package services

class PrimeFactorsService {

  /**
    * Calculates the prime factors for the given number
    */
  def calculatePrimeFactors(number: Int): Array[Int] = {
    var primeFactors = Array[Int]()

    if (number <= 1) {
      return primeFactors
    }

    // if given number is already a prime, then return it directly as there will be no prime factors
    if (isPrime(number)) {
      primeFactors = primeFactors :+ number
    } else {
      val primeFactor = determineDivisiblePrime(number)
      primeFactors = primeFactors :+ primeFactor

      val subPrimeFactors = calculatePrimeFactors(number / primeFactor)
      primeFactors = primeFactors ++ subPrimeFactors
    }

    return primeFactors
  }

  /**
    * Find the first prime number that divides the given number evenly with no remainder
    */
  private def determineDivisiblePrime(number: Int): Int = {
    for (i <- 2 to (number - 1)) {
      if (isPrime(i) && number % i == 0) {
        return i
      }
    }

    return -1
  }

  /**
    * Determines if the given number is a prime number
    */
  private def isPrime(number: Int): Boolean = {
    val j = number / 2;
    for (i <- 2 to j) {
      val remainder = number % i
      if (remainder == 0) {
        return false;
      }
    }

    return true
  }

}
