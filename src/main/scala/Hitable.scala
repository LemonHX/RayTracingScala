
package hitable
import vec.Vec
import ray.Ray

import material._

class HitRecord(var t:Float,var p:Vec,var normal:Vec){}
object HitRecord{
  def apply():HitRecord = {
    return new HitRecord(0,Vec(),Vec())
  }
}

//Any is a kind of Material
//Yes i trust U compuler
class HitRecordWithMaterial[T<:Material](var rec:HitRecord, var mat:T){}
//object HitRecordWithMaterial{
//  def apply[T<:Material]():HitRecordWithMaterial[T] = {
//    return new HitRecordWithMaterial[T](HitRecord(),new T())
//  }
//}

abstract class Hitable {
  def hit(r:Ray,tMin:Float,tMax:Float,rec:HitRecord):Boolean
}
