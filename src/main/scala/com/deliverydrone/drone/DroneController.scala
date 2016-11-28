package com.deliverydrone.drone

/**
  *
  * Trait que define las funciones de control del drone.
  *
  * Created by ALEJANDRO on 26/11/2016.
  */
trait DroneController {


  /**
    * Conjunto de objetos que definen la posbile orientacion del manejo de un drone.
    */
  sealed abstract class Orientation

  case object Nort extends Orientation
  case object South extends Orientation
  case object East extends Orientation
  case object West extends Orientation


  /**
    * Conjunto de objetos que finen los posibls movimientos en el manejo de un drone.
    */
  sealed abstract class Move

  case object Avance extends Move
  case object Left extends Move
  case object Right extends Move


  /**
    * Clase que define las funciones de localizacion del drone.
    */
  case class Position(x:Int,y:Int,orientation: Orientation) {

    def dx(d:Int) = copy(x = x + d)

    def dy(d:Int) = copy(y = y + d)

    def dposition(d:Orientation) = copy(orientation = d)

  }

  /**
    * Clase que define las funciones del drone.
    * @param position
    */
  case class Drone(position: Position){

    /**
      * Funcion que retorna un drone con la nueva posicion en x.
      */
    def modifyX(dx:Int) = Drone(position.dx(dx))

    /**
      * Funcion que retorna un drone con la nueva posicion en y.
      */
    def modifyY(dy:Int) = Drone(position.dy(dy))

    /**
      * Funcion que retorna un drone con la nueva orientacion.
      */
    def modifyOrientation(dp:Orientation) = Drone(position.dposition(dp))


    /**
      *Funcion encargada de actualizar los valores del drone en el plano cartesiano
      *segun su orientacion.
      */
    def avance = {

      position.orientation match {

        case Nort => modifyY(1)

        case South => modifyY(-1)

        case East => modifyX(1)

        case West => modifyX(-1)

      }

    }

    /**
      * Funcion encargada de definir la orientacion del drone
      * segun la orden de girar a la izquierda.
      */
    def left = {

      position.orientation match {
        case Nort => modifyOrientation(West)

        case South => modifyOrientation(East)

        case East => modifyOrientation(Nort)

        case West => modifyOrientation(South)

      }


    }


    /**
      * Funcion encargada de definir la orientacion del drone
      * segun la orden de girar a la derecha.
      */
    def right = {
      position.orientation match {
        case Nort => modifyOrientation(East)

        case South => modifyOrientation(West)

        case East => modifyOrientation(South)

        case West => modifyOrientation(Nort)
      }

    }

    /**
      * Funcion que retorna la localizacion actual del drone.
      */
    def getLocation():String = {
      "("+position.x+", "+position.y+") direccion "+getStringOrientation()
    }

    /**
      * Funcion que retorna el string de la orientacon  actual del drone.
      * @return
      */
    def getStringOrientation():String = {
      position.orientation match {
        case Nort => "Norte"
        case South => "Sur"
        case East => "Este"
        case West => "Oeste"
      }
    }

  }

  /**
    * Metodo encargado de crear un nuevo drone en la posicion estandar definida de inicio.
    */
  def createDrone():Drone = {
    val startPosition = new Position(0,0,Nort)
    val drone = new Drone(startPosition)
    drone

  }




}
