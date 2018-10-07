package renderobject

import vec.Vec
import ray.Ray
import shapes._
import hitable._
import material._


//之前是对所有的shapes对象添加一个hitrecord然后在循环中更改hitrecord的值
//现在我对每一个shapes绑定上一个hitobject和hitrecord,存放在Array[..]以便使用map
class RenderObject[Sha<:Hitable,Mat<:Material](val shape:Sha,val material:Mat) extends Hitable{
  override def hit(r: Ray, tMin: Float, tMax: Float, rec: HitRecord): Boolean = shape.hit(r,tMin,tMax,rec)
}