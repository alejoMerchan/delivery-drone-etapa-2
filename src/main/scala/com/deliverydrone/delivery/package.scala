package com.deliverydrone

import scalaz.{Monoid, Semigroup}

/**
 * Created by abelmeos on 2016/11/28.
 */
package object delivery {

  val ListDeliverySemigroup:Semigroup[List[String]] = new Monoid[List[String]] {

    override def zero: List[String] = List.empty[String]

    override def append(f1: List[String], f2: => List[String]): List[String] = f1 ::: f2
  }


}
