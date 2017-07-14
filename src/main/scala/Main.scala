/**
  * Created by joao.lucchetta on 7/7/17.
  */

import actors.Broker
import actors.Broker.RequestData
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Cancellable, Props}

import scala.concurrent.duration._
import scala.util.Random

object Main extends App{
  val system: ActorSystem = ActorSystem("TesteActors")

  val broker1 = system.actorOf(Props[Broker])

//  broker1 ! Broker.RequestData("Banana")
//  broker1 ! Broker.RequestData("Melancia")
//  broker1 ! Broker.RequestData("Banana")
//  broker1 ! Broker.RequestData("Batata")

  import system.dispatcher
  val timelyRequests : Cancellable = system.scheduler.schedule(0 milliseconds, 1200 milliseconds, broker1, Broker.RequestRandomData)
  val quitProgram : Cancellable = system.scheduler.scheduleOnce(30 seconds, () => timelyRequests.cancel())
}
