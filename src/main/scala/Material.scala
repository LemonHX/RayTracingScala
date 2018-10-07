package material
import vec.Vec
import ray.Ray
import hitable._

import scala.util.Random

class Material {
  def scatter(rayin: Ray,rec:HitRecord,attenuation:Vec,scattered:Ray):Boolean = {
    println("Warning! you are using a father class of Material witch is not recomended")
    false
  }
}


case class Lambertian(var albedo:Vec) extends Material{

  override def scatter(rayin: Ray,rec:HitRecord,attenuation:Vec,scattered:Ray): Boolean = {
    val tar = rec.p+rec.normal+Lambertian.randomInUnitSphere()
    scattered.A = rec.p
    scattered.B = tar-rec.p
    attenuation.x = albedo.x
    attenuation.y = albedo.y
    attenuation.z = albedo.z
    return true
  }
}
object Lambertian{
  def randomInUnitSphere():Vec = {
    var p = Vec()
    do{
      var rand:Float = Random.nextFloat()
      p = Vec(rand)*2 - Vec(1)
    }while(p.squaredLength() >= 1.0)
    return p;
  }
}
case class Metal(var albedo:Vec) extends Material{
  def reflect(v:Vec,n:Vec):Vec = {
    return v- n*((v.dot(n))*2)
  }
  override def scatter(rayin: Ray, rec: HitRecord, attenuation: Vec, scattered: Ray): Boolean = {
    var reflected = this.reflect(rayin.direction().unitVec(),rec.normal)
    scattered.A = rec.p
    scattered.B = reflected
    attenuation.x = albedo.x
    attenuation.y = albedo.y
    attenuation.z = albedo.z
    return scattered.direction().dot(rec.normal) > 0
  }
}