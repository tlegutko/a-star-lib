package astarlib

import java.util.concurrent.ForkJoinPool

import scala.collection.parallel.{ForkJoinTaskSupport, ParSeq}

case class ParallelExecutor(numOfProc: Int) {
  val taskSupport = new ForkJoinTaskSupport(new ForkJoinPool(numOfProc))
  def mapWithSingleThread[A, B](seq: Seq[A], f: A => B): Seq[B] = {
    seq.map(f)
  }
  def mapParallelCollection[A, B](ps: ParSeq[A], f: A => B): ParSeq[B] = {
    ps.tasksupport = taskSupport
    ps.map(f)
  }
  def mapWithParallelCollections[A, B](s: Seq[A], f: A => B): Seq[B] = {
    mapParallelCollection(s.par, f).seq
  }
  def mapWithActors[A, B](s: Seq[A], f: A => B): Seq[B] = {
    ???
  }
}
