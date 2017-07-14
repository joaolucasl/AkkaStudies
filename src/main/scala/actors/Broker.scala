package actors

import actors.Broker._
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._

/**
  * Created by joao.lucchetta on 7/7/17.
  */
object Broker {
  case class ReceivePrice(product: String, price: Double)
  case class ReceiveQuantity(product: String, quantity: Int)
  case class RequestData(product: String)
  case object RequestRandomData
}

class Broker extends Actor with ActorLogging {
  var products: Vector[String] = Vector("Banana", "Batata", "Melancia", "Arroz", "Feijao", "Carne", "Alface", "Tomate")

  override def preStart() = {
    super.preStart()
    products.foreach(p => context.actorOf(Props(classOf[ProductManager], p, 1, 2.0), s"manager-$p"))
  }

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: SecurityException        => Resume
      case _: Exception                => Restart
    }

  def receive = {
    case RequestData(product: String) => {
      log.info("Requesting Data...")
      val child: Option[ActorRef] = context.child(s"manager-$product")
      child match {
        case Some(actor) => {
          actor ! ProductManager.GetQuantity
          actor ! ProductManager.GetPrice
        }
        case None => self ! ReceiveQuantity(product, 0)
      }
    }
    case ReceiveQuantity(product: String, quantity: Int) => log.info(s"Product: $product - Qty: $quantity")
    case ReceivePrice(product: String, price: Double) => log.info(s"Product $product - Price $price")
    case RequestRandomData => self ! RequestData(products(scala.util.Random.nextInt(products.length)))
  }
}
