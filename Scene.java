/**
 * Raytracing scene.
 * @author fabio
 */
public class Scene {
	/**
	 * Camera.
	 */
	public Camera				camera;
	/**
	 * Object surfaces.
	 */
    public Surface[]			surfaces;
	/**
	 * Lights.
	 */
    public Light[]				lights;
	
	/**
	 * Default constructor.
	 * Scene is initialized in the Loader.
	 */
	public Scene() {
		camera = new Camera();
		surfaces = new Surface[0];
		lights = new Light[0];
	}
	
	/**
	 * Intersect the given ray with this scene storing the result in intersection and
	 * result true if there was an intersection.
	 */
	boolean intersect(Ray ray, Intersection intersection) {
		intersection.clear();
		boolean hit = false;
		Intersection surfaceIntersection = new Intersection(); 
		for(int i = 0; i < surfaces.length; i ++) {
			boolean hitSurface = surfaces[i].intersect(ray, surfaceIntersection);
			if(hitSurface && surfaceIntersection.distance < intersection.distance) {
				intersection.set(surfaceIntersection);
				hit = true;
			}
		}
		return hit;
	}
}
