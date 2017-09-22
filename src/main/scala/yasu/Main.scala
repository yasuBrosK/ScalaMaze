package yasu

import yasu.maze.Maze

/**
 * Mainクラス
 * Created by yasuhiro.kato on 2017/09/21.
 */
object Main {

  def main(args: Array[String]): Unit = {
    val mazeField = Maze.generateMaze(13, 13)
    drawMaze(mazeField)
  }

  private def drawMaze(field: Array[Array[String]]) {
    field.foreach(x =>
      println(x.mkString(" "))
    )
  }
}
