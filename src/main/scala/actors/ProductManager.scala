package actors

import actors.Broker.{ReceivePrice, ReceiveQuantity}
import actors.ProductManager.{GetPrice, GetQuantity}
import akka.actor.Actor

/**
  * Created by joao.lucchetta on 7/7/17.
  */

object ProductManager {
  case object GetPrice
  case object GetQuantity
}

class ProductManager(product: String, quantity: Int, price: Double) extends Actor {
  override def receive = {
    case GetPrice => sender() ! ReceivePrice(product, 123.0)
    case GetQuantity => sender() ! ReceiveQuantity(product, quantity)
  }
}
