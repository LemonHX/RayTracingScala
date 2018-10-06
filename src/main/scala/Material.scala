package material
import vec.Vec
import ray.Ray
import hitable._
trait Material {
  def scatter(ray: Ray,rec:HitRecord):Boolean
}
