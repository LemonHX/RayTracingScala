package material
import vec.Vec
import ray.Ray
import hitable._

import scala.util.Random

class Material {
  def scatter(rayin: Ray,rec:HitRecord): Tuple2[Ray,Vec] = {
    println("Warning! you are using a father class of Material witch is not recomended")
    return new Tuple2(Ray(),Vec())
  }
  def isScatterd(rayin:Ray,scattered:Ray,rec:HitRecord):Boolean ={
    println("Warning! you are using a father class of Material witch is not recomended")
    false
  }
}


case class Lambertian(val albedo:Vec) extends Material{

  override def scatter(rayin: Ray,rec:HitRecord): Tuple2[Ray,Vec] = {

    val tar = rec.p+rec.normal+Lambertian.randomInUnitSphere()
    return new Tuple2[Ray,Vec](Ray(rec.p,tar-rec.p),albedo)
  }

  override def isScatterd(rayIn:Ray,scattered:Ray,rec:HitRecord): Boolean = {
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
  def apply(r:Vec):Lambertian = {
    if(r == Vec(0)) {
      throw new Exception("Fuck u")
    }else{
      return new Lambertian(r)
    }
  }
}

case class Metal(val albedo:Vec,val fuzz : Float) extends Material{
  override def scatter(rayin: Ray, rec: HitRecord): Tuple2[Ray,Vec] = {
    var reflected = Metal.reflect(rayin.direction().unitVec(),rec.normal)
    return new Tuple2[Ray,Vec](Ray(rec.p,reflected + Lambertian.randomInUnitSphere()*fuzz),albedo)
  }

  override def isScatterd(rayIn:Ray,scattered:Ray,rec:HitRecord): Boolean = return scattered.direction().dot(rec.normal) > 0
}
object Metal{
  def reflect(v:Vec,n:Vec):Vec = {
    return v- n*((v.dot(n))*2)
  }

  def apply(r:Vec,f:Float):Metal = {
    if(r == Vec(0) && f > 1) {
      throw new Exception("Fuck u")
    }else{
      return new Metal(r,f)
    }
  }
}
case class Dielectric(val ri:Float) extends Material{

  override def scatter(rayin: Ray, rec: HitRecord): (Ray, Vec) = {
    var outwardNormal = Vec()
    var reflected = Metal.reflect(rayin.direction(),rec.normal)
    var attenuation = Vec(1,1,0);
    //todo careful
    var refra = Vec()
    var niOverNt = 0f
    if(rayin.direction().dot(rec.normal) > 0){
      outwardNormal = (rec.normal)*(-1)
      niOverNt = ri
    }else{
      outwardNormal = rec.normal
      niOverNt = 1/ri
    }
    if(Dielectric.isRefracted(rayin.direction(),outwardNormal,niOverNt,reflected)){
      refra = Dielectric.refract(rayin.direction(),outwardNormal,niOverNt,reflected)
      return Tuple2(Ray(rec.p,reflected),Vec(1))
    }
    else{
      return Tuple2(Ray(rec.p,reflected),Vec(1))
    }

  }
  override def isScatterd(rayIn:Ray,scattered:Ray,rec:HitRecord): Boolean = {
    var outwardNormal = Vec()
    var reflected = Metal.reflect(rayIn.direction(),rec.normal)
    var attenuation = Vec(1,1,0);
    //todo careful
    var refra = Vec()
    var niOverNt = 0f
    if(Dielectric.isRefracted(rayIn.direction(),outwardNormal,niOverNt,reflected)){
      return true
    }
    else{
      return false
    }
  }
}
object Dielectric{
  def refract(v:Vec,n:Vec,niOverNt:Float,refracted:Vec): Vec ={
    val uv = v.unitVec()
    val dt = uv.dot(n)
    val dd = 1.0 - niOverNt*niOverNt*(1-dt*dt)
    return (uv-n*dt)*niOverNt-n*Math.sqrt(dd).toFloat
  }
  def isRefracted(v:Vec,n:Vec,niOverNt:Float,refracted:Vec):Boolean = {
      val uv = v.unitVec()
      val dt = uv.dot(n)
      val dd = 1.0 - niOverNt*niOverNt*(1-dt*dt)
      if(dd > 0){
        return true
      }else{
        return false
      }
  }
  def schlick(cos:Float,ri:Float):Float={
    var r = (1-ri)/(1+ri)
    r = r*r
    return r+(1-r)*Math.pow((1-cos).toDouble,5).toFloat
  }
}