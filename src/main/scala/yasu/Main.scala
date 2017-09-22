package yasu

import yasu.maze.MazeFactory
import yasu.maze.MazeFactory.ViewMaze

/**
 * Mainクラス
 * Created by yasuhiro.kato on 2017/09/21.
 */
object Main {

  def main(args: Array[String]): Unit = {
    val mazeField = MazeFactory.createMaze(13, 13)
    drawMaze(mazeField)
  }

  // 迷路の描画
  private def drawMaze(maze: ViewMaze) {
    maze.field.foreach(x =>
      println(x.mkString(" "))
    )
  }
}
