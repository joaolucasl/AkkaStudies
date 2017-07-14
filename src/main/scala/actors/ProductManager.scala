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
  override def preStart(): Unit = {
    println(s"== Starting $self.product manager ==")
  }

  override def receive = {
    case GetPrice => {
      if (scala.util.Random.nextFloat() <= 0.2f) {
        println(s"== Killing $self.product Manager ==")
        throw new Exception("BANG!")
      }
      sender() ! ReceivePrice(product, 123.0)
    }
    case GetQuantity => {
      if (scala.util.Random.nextFloat() <= 0.1f) {
        println(s"== Can't get qty $self.product Manager ==")
        throw new SecurityException("Not allowed to get quantity!")
      }
      sender() ! ReceiveQuantity(product, quantity)
    }
  }
}
