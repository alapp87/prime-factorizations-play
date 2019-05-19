import java.io.File

import models.PrimeFactorsRepository
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.Mode
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.guice.GuiceApplicationBuilder

import scala.concurrent.ExecutionContext

class PrimeFactorsRepositorySpec extends PlaySpec with MockitoSugar with ScalaFutures {

  "PrimeFactorsRepository insert" should {
    "return result if successful" in {
      val injector = new GuiceApplicationBuilder().in(new File("conf/application.conf")).in(Mode.Test).injector
      val dbConfigProvider: DatabaseConfigProvider = injector.instanceOf[DatabaseConfigProvider]
      implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

      val repo: PrimeFactorsRepository = new PrimeFactorsRepository(dbConfigProvider)

      val result = repo.insert(12, "12 = 2 x 2 x 3")

      result.map { r =>
        assert(r.id == 1)
      }
    }
  }

  "PrimeFactorsRepository find" should {
    "return result if found" in {
      val injector = new GuiceApplicationBuilder().in(new File("conf/application.conf")).in(Mode.Test).injector
      val dbConfigProvider: DatabaseConfigProvider = injector.instanceOf[DatabaseConfigProvider]
      implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

      val repo: PrimeFactorsRepository = new PrimeFactorsRepository(dbConfigProvider)

      val result = repo.find(12)

      result.map { r =>
        assert(r.get == "12 = 2 x 3 x 3")
      }
    }
    "return None if not found" in {
      val injector = new GuiceApplicationBuilder().in(new File("conf/application.conf")).in(Mode.Test).injector
      val dbConfigProvider: DatabaseConfigProvider = injector.instanceOf[DatabaseConfigProvider]
      implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

      val repo: PrimeFactorsRepository = new PrimeFactorsRepository(dbConfigProvider)

      val result = repo.find(3)

      result.map { r =>
        assert(r.get == None)
      }
    }
  }

}
