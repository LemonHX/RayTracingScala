package shapes
import vec.Vec
import ray.Ray
import hitable._

case class Sphere(val center:Vec,val radius:Float) extends Hitable{
  override def hit(r: Ray, tMin: Float, tMax: Float, rec: HitRecord): Boolean = {
    val oc = r.origin - center
    val a:Float = r.direction().dot(r.direction())
    val b:Float = oc.dot(r.direction())
    val c:Float = oc.dot(oc) - radius*radius
    val d:Float = b*b - a*c
    if(d>0){
      val tmp = (-b - Math.sqrt(b*b-a*c).toFloat)/a
      if(tmp < tMax && tmp > tMin){
        rec.t = tmp
        rec.p = r.pointAtScale(rec.t)
        rec.normal = (rec.p - center)/radius
        return true
      }
      val tmp2 = (-b + Math.sqrt(b*b-a*c).toFloat)/a
      if(tmp < tMax && tmp > tMin){
        rec.t = tmp
        rec.p = r.pointAtScale(rec.t)
        rec.normal = (rec.p - center)/radius
        return true
      }
    }
      return false
  }
}
//case class Cube()extends Hitable{}
