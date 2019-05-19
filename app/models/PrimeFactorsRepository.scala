package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PrimeFactorsRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class PrimeFactorsTable(tag: Tag) extends Table[PrimeFactors](tag, "primeFactors") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def number = column[Int]("number")

    def equation = column[String]("equation")

    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the PrimeFactors object.
      *
      * In this case, we are simply passing the id, number and equation parameters to the PrimeFactors case classes
      * apply and unapply methods.
      */
    def * = (id, number, equation) <> ((PrimeFactors.apply _).tupled, PrimeFactors.unapply)
  }

  private val primeFactors = TableQuery[PrimeFactorsTable]

  def insert(number: Int, equation: String): Future[PrimeFactors] = db.run {
    // create a projection of just the number and equation columns, since not inserting a value for the id column
    (primeFactors.map(p => (p.number, p.equation))
      // define to return auto generated id
      returning primeFactors.map(_.id)
      // define a transformation for the returned value, which combines our original parameters with the returned id
      into ((numEquation, id) => PrimeFactors(id, numEquation._1, numEquation._2))
      // And finally, insert the primeFactors into the database
      ) += (number, equation)
  }

  def find(number: Int): Future[Option[String]] = db.run(
    (for (primeFactor <- primeFactors if primeFactor.number === number) yield primeFactor.equation).result.headOption
  )

}
