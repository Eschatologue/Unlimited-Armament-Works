package uaw.audiovisual;

import arc.Core;
import arc.Events;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import mindustry.game.EventType.ResizeEvent;
import mindustry.game.EventType.Trigger;

import static arc.Core.app;
import static arc.Core.camera;
import static mindustry.Vars.headless;
import static mindustry.Vars.renderer;

/**
 * Fakes true perspective for Mindustry's top-down camera, which otherwise has no pitch of its own.
 *
 * <p>Treats the camera as if it sat at a fixed height ({@link #cameraZ}, derived from
 * {@link #fov}) looking straight down, and projects any point with an elevation
 * ({@code z}) the way a real camera would: higher points shift further from screen centre
 * and draw larger; similar visual cue as a bird's eye view camera</p>
 *
 * <p>Ported from ProgMats' {@code Perspective.java} (credit: MEEP of Faith)</p>
 */
public class Perspective {

    private static final Vec2 offsetPos = new Vec2();
    private static final Vec3 scalingPos = new Vec3();

    /**
     * z values at or below this sit flat on the ground; all projection math is skipped
     */
    private static final float groundTolerance = 0.001f;
    private static final Vec2 viewportSize = new Vec2();
    /**
     * How far below the virtual camera the projection plane sits, in world units
     */
    public static float viewportOffset = 80f;
    /**
     * Floor on how close the camera is allowed to sit to the viewport before {@link #maxZoom()}
     * starts capping zoom. Prevents the projection maths from breaking down at extreme zoom-in
     */
    public static float minViewportZ = 20f;
    /**
     * Field of view of the virtual camera, in degrees
     */
    public static float fov = 60f;
    /**
     * Distance at which distant high points start fading instead of popping out of view
     */
    public static float fadeDst = 1024f;
    private static float lastScale;
    private static float cameraZ;

    static {
        if (!headless) {
            // Recalculates the virtual camera whenever zoom changes, so scale/projection stay correct.
            Events.run(Trigger.preDraw, () -> {
                if (renderer.getDisplayScale() != lastScale) {
                    lastScale = renderer.getDisplayScale();
                    cameraZ = calcCameraZ();
                    updateViewportSize();
                }
            });

            Events.on(ResizeEvent.class, e -> app.post(() -> {
                cameraZ = calcCameraZ();
                updateViewportSize();
            }));
        }
    }

    /**
     * Use this to skip drawing entirely for points that would just resolve to zero alpha anyway
     */
    public static boolean canDraw(float z) {
        return z < viewportZ();
    }

    /**
     * Projects a ground point at elevation {@code z} to where it should actually draw on screen
     */
    public static Vec2 drawPos(float x, float y, float z) {
        if (z <= groundTolerance) return offsetPos.set(x, y);

        float vw = viewportSize.x, vh = viewportSize.y;
        float cx = camera.position.x, cy = camera.position.y;
        Vec3 scaled = scaleToViewport(x, y, z);

        offsetPos.set(scaled.x / vw * camera.width, scaled.y / vh * camera.height).add(cx, cy);
        return offsetPos;
    }

    /**
     * Size multiplier for a point at this elevation
     * <p>Higher z reads as closer to camera, so bigger</p>
     */
    public static float scale(float x, float y, float z) {
        if (z <= groundTolerance) return 1f;

        float cx = camera.position.x, cy = camera.position.y;
        float cz = cameraZ;

        x -= cx;
        y -= cy;
        float zz = cz - z;

        float px = x / zz * cz, py = y / zz * cz;
        float vx = x / zz * viewportOffset, vy = y / zz * viewportOffset;

        float d1 = dst3(vx, vy, cz - viewportOffset, x, y, z);
        float d2 = dst3(vx, vy, cz - viewportOffset, px, py, 0);

        return 1f + (1f / viewportSize.x * camera.width - 1f) * (1f - d1 / d2);
    }

    /**
     * Fade multiplier for points nearing or past the virtual viewport plane
     */
    public static float alpha(float x, float y, float z) {
        if (z <= groundTolerance) return 1f;

        float vz = viewportZ();
        float dst = dstToViewport(x, y, z);
        float fade = Math.min(fadeDst, vz);

        if (dst > fade) return 1f;
        else if (z > vz) return 0f;
        else return Interp.pow5In.apply(Mathf.clamp(dst / fade));
    }

    public static float cameraZ() {
        return cameraZ;
    }

    public static float viewportZ() {
        return Math.max(cameraZ - viewportOffset, 0f);
    }

    /**
     * The most a player should be allowed to zoom in before the projection starts breaking
     * down (the virtual camera getting closer to the viewport than {@link #minViewportZ}
     * allows). Feed this into whatever caps camera zoom
     */
    public static float maxZoom() {
        float minCZ = minViewportZ + viewportOffset;
        float minWidth = (float) (minCZ * Math.tan(fov / 2f * Mathf.degRad)) * 2f;
        float maxScale = Math.max(Core.graphics.getHeight(), Core.graphics.getWidth()) / minWidth;

        return Math.min(24f, maxScale);
    }

    private static Vec3 scaleToViewport(float x, float y, float z) {
        if (z <= groundTolerance) return scalingPos.set(x, y, 0);

        float cx = camera.position.x, cy = camera.position.y;
        x -= cx;
        y -= cy;
        float zz = cameraZ - z;

        return scalingPos.set(x / zz * viewportOffset, y / zz * viewportOffset, viewportZ());
    }

    private static float dstToViewport(float x, float y, float z) {
        Vec3 scaled = scaleToViewport(x, y, z);
        return scaled.dst(x - camera.position.x, y - camera.position.y, z);
    }

    private static void updateViewportSize() {
        float v1 = (float) (Math.tan(fov / 2f * Mathf.degRad) * viewportOffset * 2f);
        if (camera.width >= camera.height) {
            viewportSize.set(v1, v1 * (camera.height / camera.width));
        } else {
            viewportSize.set(v1 * (camera.width / camera.height), v1);
        }
    }

    private static float calcCameraZ() {
        float width = Math.max(camera.width, camera.height) / 2f;
        return (float) (width / Math.tan(fov / 2f * Mathf.degRad));
    }

    private static float dst3(float x1, float y1, float z1, float x2, float y2, float z2) {
        float dx = x1 - x2, dy = y1 - y2, dz = z1 - z2;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    // region Shadow helpers

    /**
     * How dark of a shadow-fade multiplier to use for something at elevation {@code z}
     */
    public static float shadowAlpha(float z) {
        return Mathf.clamp(1f - z / 300f);
    }

    /**
     * How much bigger than normal a drop-shadow should be drawn for something at elevation {@code z}
     */
    public static float shadowScale(float z) {
        return 1f + (3f / 300f) * z;
    }

    // endregion
}