/**
 * A Phong material with a mirror reflection.
 * @author fabio
 *
 */
public class ShinyPhong extends Phong {
	/**
	 * Reflected color.
	 */
	public Color				reflection;

	/**
	 * Evaluate material mirror reflection.
	 * @param N Surface normal.
	 * @param I View direction  (point towards the surface).
	 */
	public Color computeReflection(Vec3 N, Vec3 I) {
		return reflection.copy();
	}

	/**
	 * True if this material has mirror reflections.
	 * @param N Surface normal.
	 * @param I View direction  (point towards the surface).
	 */
	public boolean hasReflection(Vec3 N, Vec3 I) {
		return N.dot(I) < 0;
	}
}
