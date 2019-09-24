/**
 * Represents a pin-hole camera.
 * 
 * All coordinates are given in world space.
 * Note that for simplicity, this is a left-handed coordinate frame.
 * 
 * @author fabio
 */
public class Camera {
	/**
	 * Origin.
	 */
	public Vec3				origin;
	/**
	 * Right vector.
	 */
	public Vec3				x;
	/**
	 * Up vector.
	 */
	public Vec3				y;
	/**
	 * View vector.
	 */
	public Vec3				z;
	
	/**
	 * Field of view along the right(x) axis.
	 */
	public double				xfov;
	
    /**
     * Field of view along the right(y) axis.
     */
    public double               yfov;
    
	/**
	 * Width of the image in pixels.
	 */
	public int					xResolution;
	/**
	 * Height of the image in pixels.
	 */
	public int					yResolution;
	
	/**
	 * Generate a ray through the image location.
	 * @param u Image x location in [0,1]
	 * @param v Image y location in [0,1]
	 * @return Normalized view ray
	 */
	public Ray generateRay(double u, double v) {
		Ray ray = new Ray();
		
		ray.origin.set(origin);
		ray.direction.set(imagePlanePoint(u,v).sub(origin).normalize()); 
		
		return ray;
	}
	
	/**
	 * Compute the image plane point in world coordinates.
	 * @param u Image x location in [0,1]
	 * @param v Image y location in [0,1]
	 * @return Image plane point.
	 */
	public Vec3 imagePlanePoint(double u, double v) {
		Vec3 p = new Vec3();
		
		double imagePlaneSizeX = Math.tan(xfov);
		double imagePlaneSizeY = Math.tan(yfov);
		
		p = origin.add(z.negate()).add(
				x.scale((2*u-1)*imagePlaneSizeX)).add(
						y.scale((2*v-1)*imagePlaneSizeY));
		
		return p;
	}
    
    /**
     * Initialize frame from ZY. Used by parser.
     */
    public void initFromParser() {
        // normalize forward
        z.setToNormalize();
        
        // orthonormalize the up vector
        y = y.sub(z.scale(y.dot(z))).normalize();
        
        // right is just the cross product
        x = new Vec3();
        x = z.cross(y);
        
        yfov = Math.toRadians(yfov);
        xfov = yfov * (double)xResolution / (double)yResolution;
    }
}
