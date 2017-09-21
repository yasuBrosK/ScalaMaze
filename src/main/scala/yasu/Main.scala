package yasu

import yasu.maze.Maze

/**
 * Mainクラス
 * Created by yasuhiro.kato on 2017/09/21.
 */
object Main {

  def main(args: Array[String]): Unit = {
    val maze = new Maze(13, 13)
    maze.createRandomMaze
    maze.drawMaze
  }
}
