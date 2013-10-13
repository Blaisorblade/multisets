package scala.collection.mutable

import scala.collection.mutable


class MultiplicityBagBucket[A](val sentinel: A, var multiplicity: Int)
  extends scala.collection.MultiplicityBagBucket[A]
  with mutable.BagBucket[A] {

  def update(elem: A, multiplicity: Int): Unit = if (elem == sentinel) this.multiplicity = multiplicity

  def +=(elem: A): Unit = if (elem == sentinel) multiplicity += 1

  def -=(elem: A): Unit = if (elem == sentinel) multiplicity = Math.max(0, multiplicity - 1)

}


object MultiplicityBagBucketFactory {
  def of[A] = new mutable.MultiplicityBagBucketFactory[A]
}

class MultiplicityBagBucketFactory[A] extends scala.collection.BagBucketFactory[A, mutable.MultiplicityBagBucket[A]] {

  def empty(sentinel: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(sentinel, 0)

  def apply(elem: A): mutable.MultiplicityBagBucket[A] = new mutable.MultiplicityBagBucket(elem, 1)

  def apply(elemCount: (A, Int)): mutable.MultiplicityBagBucket[A] = {
    val (elem, count) = elemCount
    new mutable.MultiplicityBagBucket(elem, count)
  }

}