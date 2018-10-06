package ppm
import java.io._

class PPM(filename:String = "/home/lemonhx/Desktop/Scala/tmp.ppm"){
  val f:PrintWriter= new PrintWriter(new File(filename))
  def init(width:Int,height:Int,coding:String = "P3",color_depth:Int = 255)={
    f.write(s"${coding}\n${width} ${height}\n${color_depth}\n")
    f.flush()
  }
  def addPixel(r:Int,g:Int,b:Int)={
    f.write(s"${r} ${g} ${b}\n")
    f.flush()
  }
  def close()={
    f.close()
  }
}
