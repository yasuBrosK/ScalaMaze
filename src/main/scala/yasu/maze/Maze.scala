package yasu.maze

import scala.util.Random

/**
  * 迷路生成Object
  * Created by yasu on 17/09/21.
  */
class Maze(height: Int, width: Int) {
  // 高さと横幅は奇数限定
  require(height % 2 == 1 && width % 2 == 1)

  private case class Position(x: Int, y: Int)

  private val wall = 1
  private val road = 0

  val field = createMaze(height, width)

  // 描画メソッドを持つ描画用クラス
  case class ViewMaze(field: Array[Array[String]]) {
    def draw {
      field.foreach(x =>
        println(x.mkString(" "))
      )
    }
  }

  /**
    * 迷路生成処理
    * 穴掘りアルゴリズムでのランダム生成
    * @param height 迷路の縦幅
    * @param width 迷路の横幅
    * @return 生成された迷路２次元配列
    */
  def createMaze(height: Int, width: Int): Array[Array[Int]] = {
    val initMaze = getInitMaze(height, width)
    val center = getFieldCenter(height, width)
    // 穴掘り開始
    digMaze(initMaze, center)
  }

  /**
    * 穴掘り処理
    * @param field フィールド
    * @param position 座標
    * @return
    */
  private def digMaze(field: Array[Array[Int]], position: Position): Array[Array[Int]] = {
    // 開始地点を掘る
    digPosition(field, position)
    // ４方向をランダムな順番のリストにして順々に処理する
    shuffleWay.foreach(way => {
      val nextPos = Position(position.x + way._1.x, position.y + way._1.y)
      val nextNextPos = Position(position.x + way._2.x, position.y + way._2.y)
      // 2マス先が掘り進められる場合1マス先を掘ってから再帰的に処理を継続
      if (isValidPosition(field, nextNextPos)) {
        digMaze(digPosition(field, nextPos), nextNextPos)
      }
    })
    field
  }

  /**
    * 画面描画用の迷路オブジェクトを返却する
    * @return
    */
  def getViewMaze: ViewMaze = {
    convertViewMaze(field)
  }

  /**
    * 配列要素を描画用に変換した配列を返す
    * @param field フィールド
    * @return 画面描画用の描画迷路インスタンス
    */
  private def convertViewMaze(field: Array[Array[Int]]): ViewMaze = {
    val viewField = field.map(_.map(elm => elm match {
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
  private def getInitMaze(height: Int, width: Int): Array[Array[Int]] = Array.ofDim[Int](height, width).map(_.map(_ => wall))

  /**
    * 迷路の中心座標(x, y)を返却
    * @param height 迷路の縦幅
    * @param width 迷路の横幅
    * @return 中心座標（x, y）
    */
  private def getFieldCenter (height: Int, width: Int): Position = Position(height / 2, width / 2)

  /**
    * 指定座標を道にする（破壊的）
    * @param field フィールド
    * @param position 対象座標
    * @return 指定座標を道にした配列
    */
  private def digPosition(field: Array[Array[Int]], position: Position): Array[Array[Int]] = {
    field(position.x)(position.y) = road
    field
  }

  /**
    * 有効な座標かどうか判定後返却
    * @param field フィールド
    * @param position 座標
    * @return
    */
  private def isValidPosition(field: Array[Array[Int]], position: Position): Boolean = {
    try {
      if (field(position.x)(position.y) == road) false else true
    } catch {
      case _: ArrayIndexOutOfBoundsException => false
    }
  }

  /**
    * 4方向をランダムに詰めたリストを返却する
    * @return
    */
  private def shuffleWay: List[(Position, Position)] = {
    val top = (Position(-1, 0), Position(-2, 0))
    val bottom = (Position(1, 0), Position(2, 0))
    val right = (Position(0, 1), Position(0, 2))
    val left = (Position(0, -1), Position(0, -2))
    val fourWay = List(top, bottom, right, left)
    val rand = new Random()
    rand.shuffle(fourWay)
  }
}
