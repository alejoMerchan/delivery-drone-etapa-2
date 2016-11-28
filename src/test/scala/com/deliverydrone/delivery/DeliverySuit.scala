package com.deliverydrone.delivery

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.Assertions._
import org.scalatest.junit.JUnitRunner

/**
  * Created by ALEJANDRO on 27/11/2016.
  */

@RunWith(classOf[JUnitRunner])
class DeliverySuit extends FunSuite{

  trait FirtsDelivery extends Delivery{
    val delivery = List(Avance,Avance,Avance,Avance,Left,Avance,Avance,Right)
    val position = Position(0,0,Nort)
    val drone = Drone(position)
    val finalLocation = deliverys(drone,List(delivery),Nil)._3.head
  }

  trait SecondDelivery extends Delivery{
    val delivery = List(Right,Right,Avance,Left,Avance,Right)
    val position = Position(-2,4,Nort)
    val drone = Drone(position)
    val finalLocation = deliverys(drone,List(delivery),Nil)._3.head
  }

  trait ThirtDelivery extends Delivery{
    val delivery = List(Avance,Avance,Left,Avance,Right,Avance,Right)
    val position = Position(-3,3, South)
    val drone = Drone(position)
    val finalLocation = deliverys(drone,List(delivery),Nil)._3.head

  }

  test("delivery number 1") {

    new FirtsDelivery {
      assert(finalLocation.equals("(-2, 4) direccion Norte"))
    }
  }



  test("delivery number 3") {
    new SecondDelivery {
      assert(finalLocation.equals("(-4, 2) direccion Oriente"))
    }
  }

}
