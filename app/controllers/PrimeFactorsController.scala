package controllers

import javax.inject.Inject
import models.PrimeFactorsRepository
import play.api.data.Form
import play.api.data.Forms.{mapping, number}
import play.api.data.validation.Constraints.min
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents, Result}
import services.PrimeFactorsService

import scala.concurrent.{ExecutionContext, Future}

class PrimeFactorsController @Inject()(repo: PrimeFactorsRepository,
                                       cc: MessagesControllerComponents
                                      )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
    * The form object used to bind to view
    */
  val primeFactorForm: Form[PrimeFactorForm] = Form {
    mapping(
      "number" -> number.verifying(min(2))
    )(PrimeFactorForm.apply)(PrimeFactorForm.unapply)
  }

  /**
    * Method to render index page with prime factor form
    */
  def index = Action { implicit request =>
    Ok(views.html.index(primeFactorForm))
  }

  def calculatePrimeFactors = Action.async { implicit request =>
    primeFactorForm.bindFromRequest.fold(
      /**
        * Handle errors in form input
        */
      errorForm => {
        Future.successful(BadRequest(views.html.index(errorForm)))
      },

      /**
        * Input is valid, calculate the prime factors for the given number
        */
      primeFactorForm => {
        handlePrimeFactorFormInput(primeFactorForm)
      }
    )
  }

  def handlePrimeFactorFormInput(primeFactorForm: PrimeFactorForm): Future[Result] = {
    println("inside handlePrimeFactorFormInput")
    val number = primeFactorForm.number

    // check if the number has already had the prime factors calculated
    repo.find(number).flatMap { result =>
      println("after find")
      val existingEquation = result.getOrElse("")
      println("Existing prime factors found: " + (existingEquation != None))

      if (existingEquation.equals("")) {
        println("Calculating prime factors...")
        val primeFactors = new PrimeFactorsService().calculatePrimeFactors(number)
        val equationStr = number + " = " + primeFactors.mkString(" x ")

        // save the result so we can retrieve it later
        repo.insert(number, equationStr).map { result =>
          Redirect(routes.PrimeFactorsController.index).flashing("equation" -> result.equation)
        }
      } else {
        println("Returning existing prime factors equation")
        Future.successful(Redirect(routes.PrimeFactorsController.index).flashing("equation" -> existingEquation))
      }
    }
  }

}

case class PrimeFactorForm(number: Int)
