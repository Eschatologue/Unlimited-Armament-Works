package UAW.graphics;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class UAWFxStatic {
    public static final Effect
    // region Shooting
    crossShoot = new Effect(15f, 90f,e -> {
        color(Pal.orangeSpark);
        stroke(e.fout() * 4f);

        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, 6f, 80f * e.fout(), i * 90);
        }

        color();
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), i * 90);
        }
    }),
    shootWaterFlame = new Effect(33f, 80f, e -> {
        color(UAWPal.waterFront, UAWPal.waterMiddle, Color.gray, e.fin());

        randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
                        Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
            }),
    shootCryoFlame = new Effect(33f, 80f, e -> {
        color(UAWPal.cryoMiddle, UAWPal.cryoBack, Color.gray, e.fin());

        randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
                Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
    }),
    shootSurgeFlame = new Effect(33f, 80f, e -> {
        color(UAWPal.surgeFront, UAWPal.surgeBack, Color.gray, e.fin());

        randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
                Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
    }),
    shootPlastFlame = new Effect(33f, 80f, e -> {
        var Cock =
                color(Pal.plastaniumFront, Pal.plastaniumBack, Color.gray, e.fin());

                randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
                        Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
            }),
    shootSporeFlame = new Effect(33f, 80f, e -> {
                color(UAWPal.sporeFront, UAWPal.sporeBack, Color.gray, e.fin());

                randLenVectors(e.id, 12, e.finpow() * 70f, e.rotation, 10f, (x, y) ->
                        Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f));
            }),
    // endregion Shooting
    // region Hit
    torpedoRippleHit = new Effect(40f, 100f, e -> {
        color(UAWPal.waterMiddle);
        stroke(e.fout() * 1.4f);
        float circleRad = 4f + e.finpow() * 55f;
        Lines.circle(e.x, e.y, circleRad);
    }).layer(Layer.debris),
    // endregion Hit
    // region Casings
    casing5 = new Effect(50f, e -> {
                color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
                alpha(e.fout(0.5f));
                float rot = Math.abs(e.rotation) + 90f;
                int i = -Mathf.sign(e.rotation);
                float len = (4f + e.finpow() * 9f) * i;
                float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

                rect(Core.atlas.find("casing"),
                        e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
                        e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
                        3.5f, 8f,
                        rot + e.fin() * 50f * i
                );
            }).layer(Layer.bullet),
    casingCanister = new Effect(50f, e -> {
                color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
                alpha(e.fout(0.5f));
                float rot = Math.abs(e.rotation) + 90f;
                int i = -Mathf.sign(e.rotation);
                float len = (4f + e.finpow() * 9f) * i;
                float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

                rect(Core.atlas.find("casing"),
                        e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
                        e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
                        5.4f, 9f,
                        rot + e.fin() * 50f * i
                );
            }).layer(Layer.bullet),
    casing6 = new Effect(55f, e -> {
        color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
        alpha(e.fout(0.5f));
        float rot = Math.abs(e.rotation) + 90f;
        int i = -Mathf.sign(e.rotation);
        float len = (4f + e.finpow() * 9f) * i;
        float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

        rect(Core.atlas.find("casing"),
                e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
                e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
                4.5f, 10f,
                rot + e.fin() * 50f * i
        );
    }).layer(Layer.bullet),
    casing7 = new Effect(55f, e -> {
                color(Pal.lightOrange, Pal.lightishGray, Pal.lightishGray, e.fin());
                alpha(e.fout(0.5f));
                float rot = Math.abs(e.rotation) + 90f;
                int i = -Mathf.sign(e.rotation);
                float len = (4f + e.finpow() * 9f) * i;
                float lr = rot + Mathf.randomSeedRange(e.id + i + 6, 20f * e.fin()) * i;

                rect(Core.atlas.find("casing"),
                        e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
                        e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
                        5.5f, 12f,
                        rot + e.fin() * 50f * i
                );
            }).layer(Layer.bullet),
    // endregion Casings
    // region Trail
    cruiseMissileTrail = new Effect(33f, 80f, e -> {
        color(Pal.lightishOrange, Pal.lighterOrange, Color.gray, e.fin());

        randLenVectors(e.id, 16, e.finpow() * 36f, e.rotation + 180, 8f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 2f);
        });
    }),

    torpedoRippleTrail = new Effect(180, e -> {
        e.lifetime = 30f * e.rotation;
        color(Tmp.c1.set(e.color).mul(1.5f));
        stroke(e.fout() * 1.2f);
        Lines.circle(e.x, e.y, 3 + e.finpow() * 12f);
    }).layer(Layer.debris),

    torpedoCruiseTrail = new Effect(25f, e -> {
        color(Color.valueOf("#f5f5f5"));
        randLenVectors(e.id, 16, 2f + e.fin() * 7f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.2f + e.fslope() * 1.5f);
        });
    }).layer(Layer.debris + 0.002f)
        // endregion Trail
;
}
