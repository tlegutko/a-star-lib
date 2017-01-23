package astarlib

import scala.collection.mutable

class DistancedSet[T] {
  private val elementsExistence: mutable.HashSet[T] = mutable.HashSet empty
  private val elementsOrdering: mutable.ArrayBuffer[DistancedNode[T]] = mutable.ArrayBuffer empty

  def add(distancedNode: DistancedNode[T]): Unit = {
    elementsExistence add distancedNode.data
    elementsOrdering insert(findIndexToAdd(distancedNode, elementsOrdering, 0, elementsOrdering.length), distancedNode)
  }

  private def findIndexToAdd(elementToAdd: DistancedNode[T], elements: Seq[DistancedNode[T]], start: Int, end: Int): Integer = {
    if (start >= end) {
      end
    } else if (end == elements.length && start == end - 1) {
      if (elementToAdd.totalCost < elements.last.totalCost) {
        start
      } else {
        end
      }
    } else {
      val middle: Int = (start + end + 1) / 2
      if (elements(middle).totalCost > elementToAdd.totalCost) {
        if (elements(middle - 1).totalCost <= elementToAdd.totalCost) {
          middle
        } else {
          findIndexToAdd(elementToAdd, elements, start, middle - 1)
        }
      } else {
        findIndexToAdd(elementToAdd, elements, middle + 1, end)
      }
    }
  }

  def remove(distancedNode: DistancedNode[T]): Unit = {
    elementsExistence remove distancedNode.data
    val indexToRemove = findIndexToRemove(distancedNode, elementsOrdering, 0, elementsOrdering.length - 1)
    if (indexToRemove >= 0) {
      elementsOrdering remove indexToRemove
    }
  }

  private def findIndexToRemove(elementToRemove: DistancedNode[T], elements: Seq[DistancedNode[T]], start: Int, end: Int): Integer = {
    if (start == end) {
      start
    } else {
      val middle: Int = (start + end) / 2
      if (elements(middle).totalCost > elementToRemove.totalCost) {
        findIndexToRemove(elementToRemove, elements, end, middle - 1)
      } else if (elements(middle).totalCost < elementToRemove.totalCost) {
        findIndexToRemove(elementToRemove, elements, middle + 1, end)
      } else {
        var index = middle
        while (index >= start && elements(index).totalCost == elementToRemove.totalCost) {
          if (elements(index).data equals elementToRemove.data) {
            return index
          }
          index = index - 1
        }
        index = middle + 1
        while (index <= end && elements(index).totalCost == elementToRemove.totalCost) {
          if (elements(index).data equals elementToRemove.data) {
            return index
          }
          index = index + 1
        }
        -1
      }
    }
  }

  def contains(distancedNode: DistancedNode[T]): Boolean = elementsExistence contains distancedNode.data

  def takeSmallestTotalCostNode: DistancedNode[T] = {
    val result = elementsOrdering.remove(0)
    elementsExistence remove result.data
    result
  }

  def isEmpty: Boolean = elementsOrdering isEmpty

  override def toString: String = elementsOrdering.map(_.toString).mkString("\n")
}
