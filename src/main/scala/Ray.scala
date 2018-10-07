package ray
import vec.Vec
//射线会被材质所改变,所以不能VAL
class Ray(var A:Vec = Vec(0),var B:Vec = Vec(0)) {
  def origin():Vec={
    A
  }
  def direction():Vec={
    B
  }
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