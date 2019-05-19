import org.scalatest.FunSpec
import services.PrimeFactorsService

class PrimeFactorsServiceSpec extends FunSpec {

  describe("PrimeFactorsService") {
    val service: PrimeFactorsService = new PrimeFactorsService()

    it("should calculatePrimeFactors and return array with prime number if already prime") {
      var result: Array[Int] = service.calculatePrimeFactors(3)
      assert(result.size == 1)
      assert(result(0) == 3)
    }

    it("should calculatePrimeFactors and return array with prime factors if not prime") {
      var result: Array[Int] = service.calculatePrimeFactors(12)
      assert(result.size == 3)
      assert(result(0) == 2)
      assert(result(1) == 2)
      assert(result(2) == 3)
    }

    it("should calculatePrimeFactors and return an empty array for number less than 2") {
      var result: Array[Int] = service.calculatePrimeFactors(1)
      assert(result.size == 0)
    }
  }

}
