/**
 * Basci raytracer.
 * @author fabio
 */
public class RayTracer {
	/**
	 * Scene.
	 */
	public Scene 			scene;
	
	/**
	 * Max recursion for reflection and refraction.
	 */
	public int				maxRecursion;
	
	/**
	 * Construct a raytracer for a given scene.
	 */	
	public RayTracer(Scene nScene) {
		scene = nScene;
		maxRecursion = 1;
	}
	
	/**
	 * Reaytrace the scene with one sample per pixel.
	 */
	public ColorImage render() {
		ColorImage image = new ColorImage(scene.camera.xResolution, scene.camera.yResolution);
		
		Ray ray = null;
		for(int i = 0; i < scene.camera.xResolution; i ++) {
			for (int j = 0; j < scene.camera.yResolution; j++) {
				ray = scene.camera.generateRay(
						(i+0.5)/scene.camera.xResolution,
						(j+0.5)/scene.camera.yResolution);
				image.setColor(i,j,computeColor(ray));
			}
		}
		
		return image;
	}
	
	/**
	 * Raytrace the scene with nsamples^2 samples oper pixel.
	 */
	public ColorImage renderSuperSampled(int nsamples) {
		ColorImage image = new ColorImage(scene.camera.xResolution, scene.camera.yResolution);
		
		Ray ray = null;
		for(int i = 0; i < scene.camera.xResolution; i ++) {
			for (int j = 0; j < scene.camera.yResolution; j++) {
				Color color = new Color(0,0,0);
				for(int si = 0; si < nsamples; si ++ ) {
					for(int sj = 0; sj < nsamples; sj ++ ) {
						ray = scene.camera.generateRay(
								(i+(si+0.5)/nsamples)/scene.camera.xResolution,
								(j+(sj+0.5)/nsamples)/scene.camera.yResolution);
						color.setToAdd(computeColor(ray));
					}
				}
				image.setColor(i,j,color.scale(1.0/(nsamples*nsamples)));
			}
		}
		
		return image;
	}
	
	/**
	 * Compute the visible color along the given ray.
	 * Should check to make sure rayDepth is less or equal to the given maximum.
	 */
	public Color computeColor(Ray ray) {
		Color color = null;
		Intersection intersection = new Intersection();
		boolean hit = false;
		if(ray.rayDepth <= maxRecursion) {
			hit = scene.intersect(ray,intersection);
		}
		if(hit) {
			color = computeIllumination(ray,intersection);
		} else {
			color = new Color(0,0,0);
		}		
		return color;
	}
	
	/**
	 * Compute the color of a gien intersection point.
	 * Recurse if necessary for reflection.
	 */
	public Color computeIllumination(Ray ray, Intersection intersection) {
		Vec3 N = intersection.normal;
		Vec3 P = intersection.position;
		Vec3 I = ray.direction;

		Color color = new Color();
		for (int l = 0; l < scene.lights.length; l++) {
			Light light = scene.lights[l];
			Vec3 L = light.computeLightDirection(P);
			Color lightIntensity = light.computeLightIntensity(P);
			Color surfaceReflectance = 
				intersection.material.computeDirectLighting(N,L,I);
			double shadow = computeShadow(P,light);
			color.setToAdd(surfaceReflectance.scale(lightIntensity).scale(shadow));
		}
		
		if(intersection.material.hasReflection(N,I)) {
			Color surfaceReflection = intersection.material.computeReflection(N,I);
			Ray reflectionRay = new Ray(P,I.reflect(N),ray.rayDepth+1);
			color.setToAdd(surfaceReflection.scale(computeColor(reflectionRay)));
		}
		
		return color;
	}
	
	/**
	 * Check if a point P is in shadow given a light.
	 * Returns 0 or 1.
	 */
	public double computeShadow(Vec3 P, Light light) {
		Ray shadowRay = new Ray();
		shadowRay.origin.set(P);
		shadowRay.direction.set(light.computeLightDirection(P));
		shadowRay.maxDistance = light.computeShadowDistance(P);
		Intersection intersection = new Intersection();
		if(scene.intersect(shadowRay, intersection)) {
			return 0;
		} else {
			return 1;
		}
	}	
}
