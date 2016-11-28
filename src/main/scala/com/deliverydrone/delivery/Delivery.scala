package com.deliverydrone.delivery

import com.deliverydrone.common._

import scala.concurrent.{Future}
import scala.util.{Failure, Success}
import scalaz.{  Semigroup }
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Clase que define las funciones de una entrega.
  *
  * Created by ALEJANDRO on 27/11/2016.
  */
class Delivery extends DeliveryDefinitions {


  implicit  val ListDelivery:Semigroup[List[String]] = ListDeliverySemigroup


  /**
    * Funcion que ejecuta una entrega segun las rutas igresadas en el archivo in.txt y escribe un
    * informe de las entregas en el archivo out1.txt
    */
  def executeDelivery() = {
    val deliveryList = deliveryRoute
    val drone = createDrone()
    val finalLocations = deliverys(drone,deliveryList,Nil)
    val locations = finalLocations._3
    writeResource(locations)

  }


  /**
   * Funcion que resulve el futuro de los deliverys en paralelo y ejecuta el metodo de escritura
   * de informes de ruta de entrega.
   */
  def executeMultipleDelivery()= {

    executeParallelDelivery onComplete{
      case Success (locations) =>
        writeMultipleResources(locations)
      case Failure (e) =>
        println("--- failure: "+ e.getMessage)
    }
    deliveryTime()

  }

  /**
   *Funcion encargada de obtener la lista de rutas y ejecutarlos deliverys.
   */
  private def executeParallelDelivery() = {

    val multipleDeliveryList = deliveryRouteMultiples
    val locationsFuture = multipleDeliveryList.map{
      val drone = createDrone()
      deliveryList => executeFutureDelivery(drone,deliveryList,Nil)
    }
    val locations = Future sequence locationsFuture
    locations

  }

  /**
   * Funcion encargada de realizar deliverys en paralelo
   */
 private  def executeFutureDelivery(drone: Drone, deliveryList:List[List[Move]], listLocations:List[String])  = {
    Future{
      deliverys(drone,deliveryList,listLocations)._3
    }

  }

  /**
    * Funcion que retorna la lista con la localizacion de las entregas y el drone con la lozalizacion final
    */
  def deliverys(drone: Drone, deliveryList:List[List[Move]], listLocations:List[String]):(Drone,List[List[Move]],List[String]) = {

    deliveryList match {

      case Nil => (drone,Nil,listLocations)
      case listMove::moves =>
        val droneMove = deliveryDrone(drone,listMove)
        deliverys(droneMove,moves,List(droneMove.getLocation()):::listLocations)

    }

  }

  /**
    * Funcion auxiliar que actualiza la posicion del drone segun  una lista de movimientos
    * que conrresponden a las ordenes que se le definieron.
    */
  private def deliveryDrone(drone: Drone, deliveryList: List[Move]): Drone = {
    def moveDeliveryDrone(drone: Drone, delivery: List[Move]): (Drone, List[Move]) = {

      delivery match {

        case Nil => (drone, Nil)
        case move :: moves =>
          move match {
            case Avance =>
              moveDeliveryDrone(drone.avance, moves)
            case Left =>
              moveDeliveryDrone(drone.left, moves)
            case Right =>
              moveDeliveryDrone(drone.right, moves)
          }
      }
    }
     moveDeliveryDrone(drone, deliveryList)._1
  }

  /**
   * Tiempo de espera para el proceso de entrega de todos los drones.
   */
  private def deliveryTime() = Thread.sleep(5000)


}