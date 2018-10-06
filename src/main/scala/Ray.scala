package ray
import vec.Vec
class Ray(val A:Vec = Vec(0),val B:Vec = Vec(0)) {
  val origin = A
  val diraction = B
  def pointAtScale(t:Float):Vec=  {
    return (A+(B*t))
  }
}

object Ray{
  def apply():Ray = {
    return new Ray()
  }
  def apply(a:Vec,b:Vec):Ray = {
    return new Ray(a,b)
  }
}