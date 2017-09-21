package yasu.maze

/**
  * 迷路クラス
  * @param height 迷路の縦幅
  * @param width 迷路の横幅
  * Created by yasu on 17/09/21.
  */
class Maze(private val height: Int, private val width: Int) {

  private val road = 0
  private val wall = 1

  private val field = Array.ofDim[Int](height, width).map(_.map(_ => wall))

  /**
    * 迷路フィールド描画
    */
  def drawMaze = {
    field.foreach(x => println(
      x.map(y => y match {
        case this.road => "□"
        case this.wall => "■"
      }).mkString(" "))
    )
  }

  /**
    * 穴あき法による迷路のランダムクリエイト
    */
  def createRandomMaze : Unit = {
    val center = getFieldCenter
    setRoad(center._1, center._2)
  }

  /**
    * 迷路の中心座標(x, y)を返却
    * @return
    */
  private def getFieldCenter = Tuple2(height / 2, width / 2)

  /**
    * 指定座標を「道」にする
    * @param x ｘ座標
    * @param y ｙ座標
    */
  private def setRoad(x: Int, y: Int): Unit = field(x)(y) = road
}
