package scala.collection.mutable


import scala.collection._
import scala.collection.generic.MutableHashedBagFactory
import scala.Some
import scala.Some


final class HashBag[A] private[collection](contents: mutable.HashTable.Contents[A, mutable.DefaultEntry[A, mutable.BagBucket[A]]])(implicit protected val bucketFactory: mutable.HashedBagBucketFactory[A])
  extends mutable.HashedBag[A]
  with mutable.HashedBagLike[A, mutable.HashBag[A]]
  with mutable.HashTable[A, mutable.DefaultEntry[A, mutable.BagBucket[A]]]
  with Serializable {

  initWithContents(contents)

  type Entry = mutable.DefaultEntry[A, mutable.BagBucket[A]]

  override def empty: mutable.HashBag[A] = mutable.HashBag.empty[A](bucketFactory)

  override def clear() {
    clearTable()
  }

  override protected def elemHashCode(key: A): Int = bucketFactory.hash(key)

  def getBucket(elem: A): Option[BagBucket[A]] = {
    val e = findEntry(elem)
    if (e eq null) None
    else Some(e.value)
  }


  def updateBucket(bucket: mutable.BagBucket[A]): this.type = {
    addEntry(createNewEntry(bucket.sentinel, bucket))
    this
  }

  def addedBucket(bucket: collection.BagBucket[A]): mutable.HashBag[A] = new mutable.HashBag[A](hashTableContents) addBucket bucket

  def bucketsIterator: Iterator[BagBucket[A]] = entriesIterator.map(_.value)

  protected def createNewEntry[B](key: A, value: B): mutable.DefaultEntry[A, mutable.BagBucket[A]] = new Entry(key, value.asInstanceOf[mutable.BagBucket[A]])

}

object HashBag extends MutableHashedBagFactory[mutable.HashBag] {

  //  implicit def canBuildFrom[A]: CanBuildFrom[Coll, A, HashMap[A, B]] = new BagCanBuildFrom[A, B]

  def empty[A](implicit bucketFactory: mutable.HashedBag[A]#BagBucketFactory[A]): mutable.HashBag[A] = new mutable.HashBag[A](null)
}