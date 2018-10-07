package shader

import camera.Camera
import hitable._
import hitlist._
import material._
import ppm.PPM
import ray.Ray
import renderobject._
import shapes.Sphere
import vec.Vec

import scala.util.Random
import scala.reflect._

class Shader(val nx:Int,val ny:Int,val ns:Int,val cam: Camera,val f:PPM){

  def colorL[T<:RenderObject[_<:Hitable,_<:Material]](ray: Ray,list:Vector[T],depth:Int = 0): Vec ={
    //todo this is the main rason that why always black
    //now this matt is just for initializing the rec
    val matt = new Material()
    val rec = new HitRecordWithMaterial(HitRecord(),matt)

    val trec = new HitRecord(0,Vec(0),Vec(0))

    var hitAny = false
    var closest:Float = 1000000000;
    for(i <- 0 until list.length ){
      if(list(i).hit(ray,0.001f,closest,rec.rec)){
        rec.mat = list(i).material
        hitAny = true
        closest = trec.t
        trec.t = rec.rec.t
        trec.normal = rec.rec.normal
        trec.p = rec.rec.p
      }
    }
    if(hitAny){
      var scattered = rec.mat.scatter(ray,rec.rec)._1
      var attenuation = rec.mat.scatter(ray,rec.rec)._2
      if(depth<50 && rec.mat.isScatterd(ray,scattered,rec.rec)){
        var res = colorL(scattered,list,depth+1)*attenuation
        return res
      }else{
        return Vec()
      }
    }
    val ud:Vec = ray.direction().unitVec()
    val t = (ud.y+1f) * 0.5f
    return Vec(1f)*(1f-t) + Vec(0.5f,0.7f,1.0f) * t.toFloat
  }


  def start[T<:RenderObject[_<:Hitable,_<:Material]](list:Vector[T]): Unit ={
    for(j <- (ny-1) to 0 by -1;i <- 0 to (nx-1) by 1) {
      var col = Vec(0)
      for(s<- 0 to (ns-1) by 1){
        val random1:Float = Random.nextFloat()
        val random2:Float = Random.nextFloat()
        val uf:Float = (i + random1) / nx.toFloat
        val vf:Float = (j + random2) / ny.toFloat
        val r:Ray = cam.getRay(uf,vf)
        val p = r.pointAtScale(2)
        val cw = this.colorL[T](r,list,0)
        col = col+cw
      }
      col = col / ns.toFloat
      col = Vec(Math.sqrt(col.x).toFloat,Math.sqrt(col.y).toFloat,Math.sqrt(col.z).toFloat)
      val ir = (255.99f * col.x).round
      val ig = (255.99f * col.y).round
      val ib = (255.99f * col.z).round
      f.addPixel(ir,ig,ib)
    }
    f.close()
    println("done")
  }
}
