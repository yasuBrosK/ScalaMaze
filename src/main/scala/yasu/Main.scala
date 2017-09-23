package yasu

import yasu.maze.Maze

/**
 * Mainクラス
 * Created by yasuhiro.kato on 2017/09/21.
 */
object Main {

  def main(args: Array[String]): Unit = {
    val maze = new Maze(15, 15)
    maze.getViewMaze.draw
  }
}
