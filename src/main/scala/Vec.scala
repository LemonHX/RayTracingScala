package vec

class Vec(var x:Float = 0,var y:Float = 0,var z:Float = 0){
  def v():Array[Float] = return Array(x,y,z);
  //for color
//  val r = x;
//  val g = y;
//  val b = z;
  //op overload
  def +(that:Vec):Vec = {
    return new Vec(
      this.x+that.x,
      this.y+that.y,
      this.z+that.z
    )
  }
  def -(that:Vec):Vec={
    return new Vec(
      this.x-that.x,
      this.y-that.y,
      this.z-that.z
    )
  }
  def *(that:Vec):Vec={
    return new Vec(
      this.x*that.x,
      this.y*that.y,
      this.z*that.z
    )
  }
  def *(that:Float):Vec={
    return new Vec(
      this.x*that,
      this.y*that,
      this.z*that
    )
  }
  def /(that:Vec):Vec={
    return new Vec(
      this.x/that.x,
      this.y/that.y,
      this.z/that.z
    )
  }
  def /(that:Float):Vec={
    return new Vec(
      this.x/that,
      this.y/that,
      this.z/that
    )
  }

  def length():Float={
    return  math.sqrt(x*x+y*y+z*z).toFloat;
  }
  def squaredLength():Float={
    return x*x + y*y + z*z
  }
  def dot(that:Vec):Float={
    return  this.x*that.x + this.y*that.y + this.z*that.z
  }
  def cross(that:Vec):Vec={
    return new Vec(
      this.y*that.z-this.z*that.y,
      0-(this.x*that.z-this.z*that.x),
      this.x*that.y-this.y*that.x
    )
  }
  def angle(that:Vec):Float={
        val c = this.dot(that);
        return (Math.acos(c/(this.length()+that.length()))*180/math.Pi).toFloat;
  }
  def unitVec(): Vec ={
    val n = this/this.length()
    return n
  }

  override def toString: String = {
    return "{"+this.x.toString + " , " + this.y.toString + " , " + this.z.toString+"}";
  }
}
object Vec{
  def apply(): Vec = return new Vec();
  def apply(number:Float):Vec={
    return new Vec(number,number,number)
  }
  def apply(x:Float,y:Float,z:Float):Vec = {
    return new Vec(x,y,z)
  }
  def apply(number:Float,times:Int): Vec = {
    times match{
      case 0 => return new Vec()
      case 1 => return new Vec(number)
      case 2 => return new Vec(number,number)
      case 3 => return new Vec(number,number,number)
      case _ => return new Vec()
    }
  }

}
