package models

import play.api.libs.json.Json

case class PrimeFactors(id: Long, number: Int, equation: String)

object PrimeFactors {
  implicit val primeFactorsFormat = Json.format[PrimeFactors]
}
