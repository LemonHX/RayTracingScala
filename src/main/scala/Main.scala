import java.io.{File, PrintWriter}

import vec.Vec
import ray.Ray
import ppm.PPM
import hitable._
import shapes._
import hitlist._

import scala.util.Random
object Main extends App {
  /*val vec1 = Vec(1,3)
  val vec2 = new Vec(0,1,-2)
  val vec3 = new Vec(2,3,4)
  println((vec1*vec2).angle(vec3))
  println("now try to make z with x and y to verify the cross")
  val x = new Vec(1,0,0)
  val y = new Vec(0,1,0)
  val z = x.cross(y)
  println(s" ${z.toString} and the angle between is ${x.angle(y)}")*/

//  def hit(center:Vec,radius:Float,r: Ray):Float = {
//    val oc = r.origin - center
//    val a:Float = r.diraction.dot(r.diraction)
//    val b:Float = oc.dot(r.diraction)*2.0f
//    val c:Float = oc.dot(oc) - radius*radius
//    val d:Float = b*b - 4*a*c
//    if(d<0){
//      return -1.0f
//    }else{
//      return (-b - Math.sqrt(d).toFloat)/(2.0f*a)
//    }
//  }
//  def color(r: Ray):Vec = {
//    val t = hit(Vec(0,0,-1),0.5f,r)
//    if(t>0){
//      val n:Vec = (r.pointAtScale(t)-Vec(0,0,-1f)).unitVec()
//      return Vec(n.x+1f,n.y+1f,n.z+1f)*0.5f
//    }else{
//     val ud:Vec = r.diraction.unitVec()
//      val t = (ud.y+1f) * 0.5f
//      return Vec(1f)*(1f-t) + Vec(0.5f,0.7f,1.0f) * t.toFloat
//    }
//  }

//  def hit(center:Vec,radius:Float,r:Ray): Boolean ={
//    val oc = r.origin - center
//        val a:Float = r.diraction.dot(r.diraction)
//        val b:Float = (oc.dot(r.diraction)*2.0).toFloat
//        val c:Float = oc.dot(oc) - radius*radius
//        val d:Float = b*b - 4*a*c
//        return d>0
//  }
//  def color(r: Ray):Vec = {
//      if(hit(Vec(0,0,-1),0.5f,r)){
//        return Vec(1,0,0)
//      }
//    val ud = r.diraction.unitVec()
//    val t:Float = (ud.y+1)*0.5f
//    return (Vec(1)*(1.0f-t) + Vec(0.5f,0.7f,1.0f)*t)
//  }





  def color(ray: Ray,world:HitList): Vec ={
    val rec = new HitRecord(0,new Vec(),new Vec())
    if(world.hit(ray,0,100000000,rec)){
      return Vec(rec.normal.x+1f,rec.normal.y+1f,rec.normal.z+1f)*0.5f
    }
    val ud:Vec = ray.diraction.unitVec()
    val t = (ud.y+1f) * 0.5f
    return Vec(1f)*(1f-t) + Vec(0.5f,0.7f,1.0f) * t.toFloat
  }

  override def main(args: Array[String]): Unit = {
    val nx = 2000
    val ny = 1000
    val f = new PPM()

    val llc = new Vec(-2,-1,-1)
    val h = new Vec(4,0,0)
    val v = new Vec(0,2,0)
    val o = new Vec(0)

    f.init(width = nx,height = ny)
    val as:Array[Sphere] = Array(new Sphere(Vec(0,0,-1),0.5f),new Sphere(Vec(0,-100.5f,-1),100))
    val list = new HitList(as)
    for(j <- (ny-1) to 0 by -1;i <- 0 to (nx-1) by 1) {
        val random1:Float = Random.nextFloat()
        val random2:Float = Random.nextFloat()
        val uf:Float = (i + random1) / nx.toFloat
        val vf:Float = (j + random2) / ny.toFloat
        val r:Ray = new Ray(o, llc + (h * uf) + (v * vf))
        val p = r.pointAtScale(2)
        val col:Vec = color(r,list)
        val ir = (255.99f * col.x).round
        val ig = (255.99f * col.y).round
        val ib = (255.99f * col.z).round


        f.addPixel(ir,ig,ib)
    }
    println("fini")
  }
}
