package astarlib

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object ParallelExecutor {
  val timeout = Timeout(1 day)
  def mapWithSingleThread[A, B](seq: Seq[A], f: A => B): Seq[B] = {
    seq.map(f)
  }
  def mapWithParallelCollections[A, B](s: Seq[A], f: A => B): Seq[B] = {
    s.par.map(f).seq // ideally use par. collection all the time so no copying/converting is needed
  }
  def mapWithFutures[A, B](s: Seq[A], f: A => B): Seq[B] = {
    Await.result(Future.traverse(s)(x => Future(f(x))), timeout.duration)
  }
}

class ParallelActorsExecutor[A, B](numOfActors: Int) {
  implicit val timeout: Timeout = ParallelExecutor.timeout
  private val actorSystem = ActorSystem("DoNotUseMe")
  private val actors = (0 until numOfActors).map(i =>
    actorSystem.actorOf(Props[SuperSmartActor[A,B]], i.toString))

  def mapUsingActors(s: Seq[A], f: A => B): Seq[B] = {
    Await.result(Future.traverse(s.zipWithIndex)({
      case (x, i) => actors(i % numOfActors) ? (x, f)
    }), timeout.duration).asInstanceOf[Seq[B]]
  }
}

class SuperSmartActor[A,B] extends Actor {
  override def receive = {
    case (x: A, f: (A => B)) => sender ! f(x)
  }
}
