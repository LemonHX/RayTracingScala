package hitlist
import vec.Vec
import ray.Ray
import hitable._
import shapes._
//Any is a type of Shape
class HitList[T<:Hitable](list:Vector[T])extends Hitable {
  val list_size = list.length
  override def hit(r: Ray, tMin: Float, tMax: Float, rec: HitRecord): Boolean = {
    //initial stage
    val trec = new HitRecord(0,Vec(0),Vec(0))
    var hitAny = false
    var closest:Float = 1000000000;
    for(i <- 0 until list.length ){
      if(list(i).hit(r,tMin,closest,rec)){
        hitAny = true
        closest = trec.t
        trec.t = rec.t
        trec.normal = rec.normal
        trec.p = rec.p
      }
    }
    return hitAny
  }
}
