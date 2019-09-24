/**
 * Sphere.
 * @author fabio
 */
public class Sphere extends Surface {
	/**
	 * Material.
	 */
	public Material					material;
	/**
	 * Center.
	 */
	public Vec3						position;
	/**
	 * Radius.
	 */
	public double					radius;
	
	/**
	 * Intersect the given ray with this scene storing the result in intersection and
	 * result true if there was an intersection.
	 */
	boolean intersect(Ray ray, Intersection intersection) {
		Vec3 localOrigin = ray.origin.sub(position);
		double a = ray.direction.dot(ray.direction);
		double b = 2 * ray.direction.dot(localOrigin);
		double c = localOrigin.dot(localOrigin) - radius*radius;
		double det = b*b - 4*a*c;
		boolean hit = det < 0;
		if(hit) {
			return false;
		} else {
            // assume we are not inside a sphere
			double t = (-b-Math.sqrt(det))/(2*a);
			if(t  > ray.minDistance && t < ray.maxDistance) {
				intersection.distance = t;
				intersection.material = material;
				intersection.position = ray.evaluate(intersection.distance);
				intersection.normal = intersection.position.sub(position).normalize();
				return true;
			} else {
				return false;				
			}
		}
	}

}
