
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
class HitRecordWithMaterial(var rec:HitRecord, var mat:Any){}
object HitRecordWithMaterial{
  def apply():HitRecordWithMaterial = {
    return new HitRecordWithMaterial(HitRecord(),new Lambertian(Vec()))
  }
}

abstract class Hitable {
  def hit(r:Ray,tMin:Float,tMax:Float,rec:HitRecord):Boolean
}
