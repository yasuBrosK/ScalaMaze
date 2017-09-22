package yasu.maze

import scala.collection.immutable.Range

/**
  * Maze object.
  * Created by yasu on 17/09/21.
  */
object Maze {

  private val wall = 1
  private val road = 0

  /**
    * 迷路生成処理
    * 穴掘りアルゴリズムでのランダム生成
    * @param height 迷路の縦幅
    * @param width 迷路の横幅
    * @return 生成された迷路２次元配列
    */
  def generateMaze(height: Int, width: Int): Array[Array[String]] = {
    val field = newField(height, width)
    val center = getFieldCenter(height, width)
    convertViewMaze(setRoad(field, center._1, center._2))
  }

  /**
    * 配列要素を描画用に変換した配列を返す
    * @param field ２次元配列
    * @return 迷路を描画した２次元配列
    */
  private def convertViewMaze(field: Array[Array[Int]]): Array[Array[String]] = {
    field.map(_.map(elm => elm match {
        case this.wall => "■"
        case this.road => "□"
      }
    ))
  }

  /**
    * ２次元配列のフィールドを返す
    * 全要素が「1 = 壁」要素で初期化されている
    * @param height 縦幅
    * @param width 横幅
    * @return 全要素１で初期化された２次元配列
    */
  private def newField(height: Int, width: Int): Array[Array[Int]] = Array.ofDim[Int](height, width).map(_.map(_ => wall))

  /**
    * 迷路の中心座標(x, y)を返却
    * @param height 迷路の縦幅
    * @param width 迷路の横幅
    * @return 中心座標（x, y）
    */
  private def getFieldCenter (height: Int, width: Int): (Int, Int) = Tuple2(height / 2, width / 2)

  /**
    * 指定座標を「道」にした配列を返却
    * @param field 迷路フィールド２次元配列
    * @param x ｘ座標
    * @param y ｙ座標
    * @return 座標を更新した新規２次元配列
    */
  private def setRoad(field: Array[Array[Int]], x: Int, y: Int): Array[Array[Int]]  = {
    val f = field.clone()
    f(x)(y) = road
    f
  }
}
