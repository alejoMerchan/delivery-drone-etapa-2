package com.deliverydrone

import java.io.{BufferedWriter, File, FileWriter, InputStream}

import scala.io.Source


/**
  * Objeto con funcionalidades genericas.
  *
  * Created by ALEJANDRO on 26/11/2016.
  */
package object common {


  /**
    * Funcion que retorna el contenido del archivo in.txt.
    */
  def getResource():List[String] = {

    val source : InputStream = getClass.getResourceAsStream("/in.txt")
    val lines =  scala.io.Source.fromInputStream( source ).getLines.toList
    lines

  }

  /**
   * Funcion que retorna el contenido de multiples archivos in.txt.
   */
  def getMultipleResources = {
    val classesDir = new File(getClass.getResource(".").toURI)
    val projectDir = classesDir.getParentFile.getParentFile.getParentFile.getParentFile.getParentFile.getParentFile
    val resourceFile = subFile(projectDir, "src/main/resources")
    val files = getListOfFiles(resourceFile.getPath)
    val sources = files.map(file => Source.fromFile(file).getLines().toList)
    sources

  }


  /**
   * Funcion encargada de crear la referencia a los archivos de la carpeta resources.
   */
 private  def subFile(file: File, children: String*) = {
    children.foldLeft(file)((file, child) => new File(file, child))
  }

  /**
   *Funcion que genera la lista de archivos de la carpeta resources.
   */
 private  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }


  /**
    * Funcion que escribe el archivo out1.txt.
    * @param resourceText
    */
  def writeResource(resourceText:List[String]) = {

    val file  = new File("out1.txt")
    val bw = new  BufferedWriter(new FileWriter(file))
    resourceText.map{text => bw.write(text);bw.newLine()}
    bw.close()

  }

  /**
   * Funcion que genera los archivos out.txt con los reportes de ruta de entrega.
   */
  def writeMultipleResources(resourcesListText:List[List[String]]) = {
    createFiles(resourcesListText,createGenericNames())
  }

  /**
   * Funcion encargada de generar nombres genericos para los informes de ruta de entrega.
   * @return
   */
  private def createGenericNames() = {
    val generics = for {
      a <- (1 to 20)
    }yield "out."+a+".txt"
    generics.toList

  }

  /**
   * Funcion encardada de escribir los archivos out.txt
   */
  def createFiles(resourceList:List[List[String]], namesFiles:List[String]):Unit = {
    resourceList match {
      case Nil => println("archivos generados")
      case resource::list =>
        val file = new File(namesFiles.head)
        val bw = new  BufferedWriter(new FileWriter(file))
        resource.map{text => bw.write(text);bw.newLine()}
        bw.close()
        createFiles(list,namesFiles.tail)
    }
  }


}
