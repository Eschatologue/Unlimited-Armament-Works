package uaw.audiovisual.fx;

import mindustry.entities.Effect;
import mindustry.graphics.Trail;
import mindustry.content.Fx.*;

import static mindustry.Vars.state;

public class BulletFx extends UAWFx{

    /** Based on Fx.trailFade */
    public static final Effect trailFade = new Effect(400f, 400, e -> {
        if (!(e.data instanceof Trail trail)) return;
        //life is how many frames it takes to fade out the trail
        e.lifetime = trail.length * 1.4f;

        if (!state.isPaused()) {
            trail.shorten();
        }
        trail.drawCap(e.color, e.rotation);
        trail.draw(e.color, e.rotation);
    });
}
