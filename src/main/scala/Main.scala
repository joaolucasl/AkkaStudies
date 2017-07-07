/**
  * Created by joao.lucchetta on 7/7/17.
  */

import actors.Broker
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}

object Main extends App{
  val system: ActorSystem = ActorSystem("TesteActors");

  val broker1 = system.actorOf(Props[Broker])

  broker1 ! Broker.RequestData("Banana")
  broker1 ! Broker.RequestData("Melancia")
  broker1 ! Broker.RequestData("Banana")
  broker1 ! Broker.RequestData("Batata")

}
