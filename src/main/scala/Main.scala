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
