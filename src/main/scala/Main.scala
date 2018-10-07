import java.io.{File, PrintWriter}

import camera.Camera
import vec.Vec
import ray.Ray
import ppm.PPM
import hitable._
import shapes._
import hitlist._
import material._
import renderobject.RenderObject
import shader.Shader

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
  //    val a:Float = r.direction().dot(r.direction())
  //    val b:Float = oc.dot(r.direction())*2.0f
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
  //     val ud:Vec = r.direction().unitVec()
  //      val t = (ud.y+1f) * 0.5f
  //      return Vec(1f)*(1f-t) + Vec(0.5f,0.7f,1.0f) * t.toFloat
  //    }
  //  }

  //  def hit(center:Vec,radius:Float,r:Ray): Boolean ={
  //    val oc = r.origin - center
  //        val a:Float = r.direction().dot(r.direction())
  //        val b:Float = (oc.dot(r.direction())*2.0).toFloat
  //        val c:Float = oc.dot(oc) - radius*radius
  //        val d:Float = b*b - 4*a*c
  //        return d>0
  //  }
  //  def color(r: Ray):Vec = {
  //      if(hit(Vec(0,0,-1),0.5f,r)){
  //        return Vec(1,0,0)
  //      }
  //    val ud = r.direction().unitVec()
  //    val t:Float = (ud.y+1)*0.5f
  //    return (Vec(1)*(1.0f-t) + Vec(0.5f,0.7f,1.0f)*t)
  //  }

  override def main(args: Array[String]): Unit = {
    val nx = 1000
    val ny = 500
    val ns = 100
    val f = new PPM()

    val cam = new Camera()
    val shader = new Shader(nx,ny,ns,cam,f)

    f.init(width = nx, height = ny)
    //val as:Array[Sphere] = Array(new Sphere(Vec(0,0,-1),0.5f),new Sphere(Vec(0,-100.5f,-1),100))
    //val list = new HitList[Sphere](as)



    val mas = Vector(
      new RenderObject[Sphere, Lambertian](new Sphere(Vec(0, 0, -1), 0.5f), Lambertian(new Vec(0.8f, 0.3f, 0.3f))),
      new RenderObject[Sphere, Metal](new Sphere(Vec(1, 0, -1), 0.5f), Metal(new Vec(0.8f, 0.6f, 0.2f),0.6f)),
      new RenderObject[Sphere, Dielectric](new Sphere(Vec(-1, 0, -1), 0.5f), new Dielectric(1.5f)),
      new RenderObject[Sphere, Lambertian](new Sphere(Vec(0, -100.5f, -1), 100), Lambertian(new Vec(0.8f, 0.8f,0))),
    )

    shader.start(mas)
  }
}
