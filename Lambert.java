/**
 * Lambertian material.
 * 
 * @author fabio
 */
public class Lambert extends Material {
	/**
	 * Diffuse color.
	 */
	public Color				diffuse;
	
	/**
	 * Evaluate material for direct lighting.
	 * @param N Surface normal.
	 * @param L Light direction (point towards the light).
	 * @param I View direction  (point towards the surface).
	 */
	public Color computeDirectLighting(Vec3 N, Vec3 L, Vec3 I) {
		double NdL = Math.max(N.dot(L),0);
		return diffuse.scale(NdL);
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
