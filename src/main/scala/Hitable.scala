
package hitable
import vec.Vec
import ray.Ray

class HitRecord(var t:Float,var p:Vec,var normal:Vec){}

trait Hitable {
  def hit(r:Ray,tMin:Float,tMax:Float,rec:HitRecord):Boolean
}
