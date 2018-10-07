package camera

import ray.Ray
import vec.Vec

class Camera(val lowerLeftCorner:Vec = Vec(-2,-1,-1),
             val horizontal:Vec = Vec(4,0,0),
             val vertical:Vec = Vec(0,2,0),
             val origin:Vec = Vec(0,0,0)) {
  def getRay(u:Float,v:Float): Ray ={
    return Ray(origin,lowerLeftCorner+(horizontal*u)+(vertical*v)-origin)
  }
}
