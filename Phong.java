/**
 * Phong material.
 * @author fabio
 */
public class Phong extends Material {
	/**
	 * Diffuse color.
	 */
	public Color				diffuse;
	/**
	 * Specular color.
	 */
	public Color				specular;
	/**
	 * Specular exponent.
	 */
	public double				exponent;
	
	/**
	 * Evaluate material for direct lighting.
	 * @param N Surface normal.
	 * @param L Light direction (point towards the light).
	 * @param I View direction  (point towards the surface).
	 */
	public Color computeDirectLighting(Vec3 N, Vec3 L, Vec3 I) {
		double NdL = Math.max(N.dot(L),0);
		if(NdL > 0) {
			Vec3 R = L.negate().reflect(N);
			double RdV = Math.max(0,-R.dot(I));
			return diffuse.scale(NdL).add(specular.scale(Math.pow(RdV,exponent)));
		} else {
			return new Color(0,0,0);
		}
	}

	/**
	 * Evaluate material mirror reflection.
	 * @param N Surface normal.
	 * @param I View direction  (point towards the surface).
	 */
	public Color computeReflection(Vec3 N, Vec3 I) {
		return new Color(0,0,0);
	}
	
	/**
	 * True if this material has mirror reflections.
	 * @param N Surface normal.
	 * @param I View direction  (point towards the surface).
	 */
	public boolean hasReflection(Vec3 N, Vec3 I) {
		return false;
	}

}
