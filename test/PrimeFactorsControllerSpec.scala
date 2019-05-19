import java.io.File

import controllers.PrimeFactorsController
import models.{PrimeFactors, PrimeFactorsRepository}
import org.mockito.Mockito._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.{ExecutionContext, Future}

class PrimeFactorsControllerSpec extends PlaySpec with MockitoSugar with ScalaFutures {

  "PrimeFactorsController Page#index" should {
    "return 200 status" in {
      val mockPrimeFactorsRepository = mock[PrimeFactorsRepository]
      val mockExecutionContext = mock[ExecutionContext]
      val controller = new PrimeFactorsController(mockPrimeFactorsRepository, Helpers.stubMessagesControllerComponents())(mockExecutionContext)
      val result: Future[Result] = controller.index().apply(FakeRequest())
      assert(status(result) == 200)
    }
  }

  "PrimeFactorsController Page#calculatePrimeFactors" should {
    "return 303 status for valid input with existing prime factors" in {
      val injector = new GuiceApplicationBuilder().in(new File("conf/application.conf")).in(Mode.Test).injector
      implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

      val mockPrimeFactorsRepository: PrimeFactorsRepository = mock[PrimeFactorsRepository]

      val number = 12
      val findFuture = Future(Option("2 x 2 x 3"))

      when(mockPrimeFactorsRepository.find(number)).thenReturn(findFuture)

      val controller = new PrimeFactorsController(mockPrimeFactorsRepository, Helpers.stubMessagesControllerComponents())
      val result: Future[Result] = controller.calculatePrimeFactors().apply(FakeRequest(GET, "calculate?number=12"))

      assert(status(result) == 303)
    }
    "return 303 status for valid input with non-existing prime factors" in {
      val injector = new GuiceApplicationBuilder().in(new File("conf/application.conf")).in(Mode.Test).injector
      implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

      val mockPrimeFactorsRepository: PrimeFactorsRepository = mock[PrimeFactorsRepository]

      val number = 12
      val findFuture = Future(Option.empty)
      val insertFuture = Future(PrimeFactors(1, 12, "2 x 2 x 3"))

      when(mockPrimeFactorsRepository.find(number)).thenReturn(findFuture)
      when(mockPrimeFactorsRepository.insert(12, "12 = 2 x 2 x 3")).thenReturn(insertFuture)

      val controller = new PrimeFactorsController(mockPrimeFactorsRepository, Helpers.stubMessagesControllerComponents())
      val result: Future[Result] = controller.calculatePrimeFactors().apply(FakeRequest(GET, "calculate?number=12"))

      assert(status(result) == 303)
    }
    "return 400 status when invalid input" in {
      val mockPrimeFactorsRepository = mock[PrimeFactorsRepository]
      implicit val mockExecutionContext = mock[ExecutionContext]

      val controller = new PrimeFactorsController(mockPrimeFactorsRepository, Helpers.stubMessagesControllerComponents())
      val result: Future[Result] = controller.calculatePrimeFactors().apply(FakeRequest(GET, "/calculate?number=abc"))

      assert(status(result) == 400)
    }
  }

}
