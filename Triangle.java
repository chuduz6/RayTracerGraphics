/**
 * Triangle.
 * @author fabio
 */
public class Triangle extends Surface {
	/**
	 * Material.
	 */
	public Material				material;
	/**
	 * Vertex 0.
	 */
	public Vec3					v0;
	/**
	 * Vertex 1.
	 */
	public Vec3					v1;
	/**
	 * Vertex 2.
	 */
	public Vec3					v2;
	/**
	 * Normal.
	 */
	public Vec3					normal;
	
	/**
	 * Intersect the given ray with this scene storing the result in intersection and
	 * result true if there was an intersection.
	 */
	public boolean intersect(Ray ray, Intersection intersection) {
		// intersect with a plane
		double t = - (normal.dot(ray.origin) - normal.dot(v0)) /
			(ray.direction.dot(normal));
		Vec3 P = ray.evaluate(t);
		// check if inside
		boolean hit = 
			normal.dot((v1.sub(v0)).cross(P.sub(v0))) > 0 &&
			normal.dot((v2.sub(v1)).cross(P.sub(v1))) > 0 &&
			normal.dot((v0.sub(v2)).cross(P.sub(v2))) > 0;
		// set intersection
		if(hit) {
			if(t  > ray.minDistance && t < ray.maxDistance) {
				intersection.distance = t;
				intersection.position.set(P);
				intersection.normal.set(normal);
				intersection.material = material;
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the normal as orthogonal to the triangle plane.
	 * Use by the parser.
	 */
	public void initFromParser() {
		normal = (v1.sub(v0)).cross(v2.sub(v0)).normalize();
	}
	
}
