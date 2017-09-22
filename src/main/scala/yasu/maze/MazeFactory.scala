package yasu.maze

import scala.util.Random

/**
  * 迷路生成Object
  * Created by yasu on 17/09/21.
  */
object MazeFactory {

  case class Maze(field: Array[Array[Int]])
  case class ViewMaze(field: Array[Array[String]])

  private val wall = 1
  private val road = 0

  private case class Position(x: Int, y: Int)

  /**
    * 迷路生成処理
    * 穴掘りアルゴリズムでのランダム生成
    * @param height 迷路の縦幅
    * @param width 迷路の横幅
    * @return 生成された迷路２次元配列
    */
  def createMaze(height: Int, width: Int): ViewMaze = {
    val initMaze = createInitMaze(height, width)
    val center = getFieldCenter(height, width)
    val createdMaze = digMaze(initMaze, center)
    convertViewMaze(createdMaze)
  }

  private def digMaze(maze: Maze, position: Position): Maze = {
    val dugMaze = setRoad(maze, position)
    decideNextPosition(dugMaze, position) match {
      case Some(pos) => digMaze(dugMaze, pos)
      case None => dugMaze
    }
  }

  /**
    * 配列要素を描画用に変換した配列を返す
    * @param maze 迷路インスタンス
    * @return 画面描画用の描画迷路インスタンス
    */
  private def convertViewMaze(maze: Maze): ViewMaze = {
    val viewField = maze.field.map(_.map(elm => elm match {
        case this.wall => "■"
        case this.road => "□"
      }
    ))
    ViewMaze(viewField)
  }

  /**
    * 初期化された迷路インスタンスを返却する
    * 全要素が「1 = 壁」要素で初期化されている
    * @param height 縦幅
    * @param width 横幅
    * @return 迷路インスタンス
    */
  private def createInitMaze(height: Int, width: Int): Maze = Maze(Array.ofDim[Int](height, width).map(_.map(_ => wall)))

  /**
    * 迷路の中心座標(x, y)を返却
    * @param height 迷路の縦幅
    * @param width 迷路の横幅
    * @return 中心座標（x, y）
    */
  private def getFieldCenter (height: Int, width: Int): Position = Position(height / 2, width / 2)

  /**
    * 指定座標を「道」にした配列を返却
    * @param maze 迷路型インスタンス
    * @param position 道にする座標
    * @return 座標を更新した新規２次元配列
    */
  private def setRoad(maze: Maze, position: Position): Maze  = {
    val m = maze.copy()
    m.field(position.x)(position.y) = road
    m
  }

  /**
    * ランダムで次のポジションを決定する
    * @param position 座標インスタンス
    * @return ランダムに決定された次のポジション 進めない場合None
    */
  private def decideNextPosition(maze: Maze, position: Position): Option[Position] = {
    val curX = position.x
    val curY = position.y

    val rand = new Random()
    val nextWay = rand.nextInt(4)

    nextWay match {
      case 0 => // 上方向
        if (canMovePosition(maze, Position(curX - 2, curY))) Some(Position(curX - 1, curY)) else None
      case 1 => // 下方向
        if (canMovePosition(maze, Position(curX + 2, curY))) Some(Position(curX + 1, curY)) else None
      case 2 => // 右方向
        if (canMovePosition(maze, Position(curX, curY + 2))) Some(Position(curX, curY + 1)) else None
      case 3 => // 左方向
        if (canMovePosition(maze, Position(curX, curY - 2))) Some(Position(curX, curY - 1)) else None
    }
  }

  private def canMovePosition(maze: Maze, position: Position): Boolean = {
    // ２マス先が既に道かフィールド外かを確認する
    try {
      if (maze.field(position.x)(position.y) == road) false else true
    } catch {
      case _: ArrayIndexOutOfBoundsException => false
    }
  }
}
