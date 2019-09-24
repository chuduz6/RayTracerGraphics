/**
 * 3D vector (can also be used to represent a 3d point).
 * @author fabio
 */
public class Vec3 {
	/**
	 * x coordinate.
	 */
	public double x;
	/**
	 * y coordinate.
	 */
	public double y;
	/**
	 * z coordinate.
	 */
	public double z;
	
	/**
	 * Default contructor.
	 */
	public Vec3() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	/**
	 * Construct and initialize to given values.
	 * @param nx x
	 * @param ny y
	 * @param nz z
	 */
	public Vec3(double nx, double ny, double nz) {
		x = nx;
		y = ny;
		z = nz;
	}
	
	/**
	 * Copy constructor.
	 */
	public Vec3(Vec3 v) {
		set(v);
	}
	
	/**
	 * Assignment.
	 */
	public void set(Vec3 v) {
		x = v.x;
		y = v.y;
		z = v.z;				
	}

	/**
	 * Vector negatation.
	 * Assigns the value to this vector.
	 */
	public void setToNegate() {
		setToScale(-1);
	}
	
	/**
	 * Vector negatation.
	 */	
	public Vec3 negate() {
		return scale(-1);
	}
	
	/**
	 * Vector scalar multiply.
	 * Assigns the value to this vector.
	 */
	public void setToScale(double s) {
		x *= s;
		y *= s;
		z *= s;
	}

	/**
	 * Vector scalar multiply.
	 */
	public Vec3 scale(double s) {
		return new Vec3(x * s,
				 		y * s,
				 		z * s);
	}

	/**
	 * Vector component-wise addition.
	 * Assigns the value to this vector.
	 */
	public void setToAdd(Vec3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	/**
	 * Vector component-wise addition.
	 */
	public Vec3 add(Vec3 v) {
		return new Vec3(x + v.x,
						y + v.y,
						z + v.z);
	}
	
	/**
	 * Vector component-wise subtraction.
	 * Assigns the value to this vector.
	 */
	public void setToSub(Vec3 v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}
	
	/**
	 * Vector component-wise subtraction.
	 */
	public Vec3 sub(Vec3 v) {
		return new Vec3(x - v.x,
						y - v.y,
						z - v.z);
	}
	
	/**
	 * Vector dot product.
	 */
	public double dot(Vec3 v) {
		return x * v.x +
		       y * v.y +
		       z * v.z;
	}	
	
	/**
	 * Vector length.
	 */
	public double length() {
		return Math.sqrt(dot(this));
	}
	
	/**
	 * Vector normalization.
	 * Assigns the value to this vector.
	 */
	public void setToNormalize() {
		double l = length();
		if(l > 0) {
			setToScale(1/l);
		}		
	}
	
	/**
	 * Vector normalization.
	 */
	public Vec3 normalize() {
		double l = length();
		if(l > 0) {
			return scale(1/l);
		} else {
			return new Vec3(0,0,0);
		}
	}

	/**
	 * Vector cross product.
	 * Assigns the value to this vector.
	 */
	public void setToCross(Vec3 v) {
		// just make a copy for now
		set(cross(v));
	}
	
	/**
	 * Vector cross product.
	 */
	public Vec3 cross(Vec3 v) {
        return new Vec3(y*v.z - z*v.y,
                		z*v.x - x*v.z,
                		x*v.y - y*v.x);
	}

	/**
	 * Reflect this vector around the normal n.
	 * This vector is intended as pointing toward the surface.
	 */
	public Vec3 reflect(Vec3 n) {
		return n.scale(-2*n.dot(this)).add(this);		
	}
}
