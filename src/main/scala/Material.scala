package material
import vec.Vec
import ray.Ray
import hitable._

import scala.util.Random

abstract class Material {
  def scatter(rayin: Ray,rec:HitRecord,attenuation:Vec,scattered:Ray):Boolean
}

case class Lambertian(var albedo:Vec) extends Material{
  def randomInUnitSphere():Vec = {
    var p = Vec()
    val rand = Random.nextFloat()
    do{
      p = Vec(rand)*2 - Vec(1)
    }while(p.squaredLength() >= 1.0)
    return p;
  }
  override def scatter(rayin: Ray,rec:HitRecord,attenuation:Vec,scattered:Ray): Boolean = {
    val tar = rec.p+rec.normal+this.randomInUnitSphere()
    scattered.A = rec.p
    scattered.B = tar-rec.p
    attenuation.x = albedo.x
    attenuation.y = albedo.y
    attenuation.z = albedo.z
    return true
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