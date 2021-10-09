package UAW.graphics;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.*;

public class UAWFxDynamic {
    // region Shooting
    public static Effect instShoot(Color color, float size) {
        return new Effect(24.0F, size * 8, (e) -> {
            e.scaled(10.0F, (b) -> {
                Draw.color(Color.white, color, b.fin());
                Lines.stroke(b.fout() * 3.0F + 0.2F);
                Lines.circle(b.x, b.y, b.fin() * size);
            });
            Draw.color(color);

            for (int i : Mathf.signs) {
                Drawf.tri(e.x, e.y, size / 4 * e.fout(), size, e.rotation + 90.0F * i);
                Drawf.tri(e.x, e.y, size / 4 * e.fout(), size, e.rotation + 20.0F * i);
            }
        });
    }
    public static Effect railShoot(Color color, float size) {
        return new Effect(24.0F, size * 8, (e) -> {
            e.scaled(10.0F, (b) -> {
                Draw.color(Color.white, Color.lightGray, b.fin());
                Lines.stroke(b.fout() * 3.0F + 0.2F);
                Lines.circle(b.x, b.y, b.fin() * size);
            });
            Draw.color(color);

            for (int i : Mathf.signs) {
                Drawf.tri(e.x, e.y, (size * 0.26f) * e.fout(), (size * 1.7f), e.rotation + 90f * i);
            }
        });
    }
    public static Effect statusFieldApply(Color lightColor, Color darkColor, float size) {
        return new Effect(50, e -> {
            color(lightColor, darkColor, e.fin());
            stroke(e.fout() * 5f);
            Lines.circle(e.x, e.y, size + e.fout() * 4f);
            /*
            Lines.poly(e.x, e.y, 8,e.finpow() * size, 45);
            Lines.square(e.x, e.y, e.foutpow() * (size - 5), 45);
            */
            int points = 6;
            float offset = Mathf.randomSeed(e.id, 360f);
            for(int i = 0; i < points; i++){
                float angle = (i * 360f / points + (Time.time * 3)) + (offset + 4);
                float rx = Angles.trnsx(angle, size), ry =  Angles.trnsy(angle, size);
                Drawf.tri(
                        e.x + rx, e.y + ry, 48f, 28f * e.fout(), angle);
            }
            Drawf.light(e.x, e.y, size * 1.6f, darkColor, e.fout());
        });
    }
    public static Effect shootFlamethrower(float lifetime) {
        return new Effect(lifetime * 2f, 90f, e -> {
            color(Pal.lightPyraFlame, Pal.darkPyraFlame, Color.gray, e.fin());

            randLenVectors(e.id, 26, e.finpow() * 75, e.rotation, 20, (x, y) ->
                    Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 2f));
        });
    }
    /*
    public static Effect shootThermal(Color color, float size) {
        return new Effect(24.0F, size * 8, (e) -> {
            e.scaled(10.0F, (b) -> {
                Draw.color(Color.white, Color.lightGray, b.fin());
                Lines.stroke(b.fout() * 3.0F + 0.2F);
                Lines.circle(b.x, b.y, b.fin() * size);
            });
            Draw.color(color);

            for (int i : Mathf.signs) {
                Drawf.tri(e.x, e.y, (size * 0.26f) * e.fout(), (size * 1.7f), e.rotation + 90f * i);
            }
        });
    }*/
    // endregion
    // region Hit
    public static Effect crossBlast(Color color, float size) {
        float length = size * 1.7f;
        float width = size / 13.3f;
        return new Effect(20f, 100f, e -> {
            color(color);
            stroke(e.fout() * 4f);
            Lines.circle(e.x, e.y, 4f + e.finpow() * size);

            color(color);
            for (int i = 0; i < 4; i++) {
                Drawf.tri(e.x, e.y, (width * 2), length * e.fout(), i * 90 + 45);
            }

            color();
            for (int i = 0; i < 4; i++) {
                Drawf.tri(e.x, e.y, width, (length / 2.7f) * e.fout(), i * 90 + 45);
            }
        });
    }
    public static Effect crossBombBlast(Color color, float size) {
        return new Effect(50f, 100, e -> {
            color(color);
            stroke(e.fout() * 2f);
            Lines.circle(e.x, e.y, 4f + e.finpow() * size);

            color(Color.gray);

            randLenVectors(e.id, 15, 1.5f + size * e.finpow(), (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
            });

            color(color);
            for (int i = 0; i < 4; i++) {
                Drawf.tri(e.x, e.y, size / 12, size * e.fout(), i * 90);
            }

            color();
            for (int i = 0; i < 4; i++) {
                Drawf.tri(e.x, e.y, size / 24, (size / 3) * e.fout(), i * 90);
            }
        });
    }
    public static Effect circleHit(Color color) {
        return new Effect(8, e -> {
            color(Color.white, color, e.fin());
            stroke(0.5f + e.fout());
            Lines.circle(e.x, e.y, e.fin() * 5f);
        });
    }
    public static Effect statusHit(Color color) {
        return new Effect(40f, e -> {
            color(color);

            randLenVectors(e.id, 6, 1.5f + e.fin() * 2.5f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * 1.5f);
            });
        });
    }
    public static Effect circleSplash(Color lightColor, Color darkColor, float size) {
        return new Effect(30, e -> {
            color(lightColor, darkColor, e.fin());
            stroke(e.fout() * 5f);
            Lines.circle(e.x, e.y, size + e.fout() * 4f);
        });
    }

    public static Effect thermalExplosion(Color frontColor, Color backColor) {
        return new Effect(22, e -> {
            color(frontColor);

            e.scaled(6, i -> {
                stroke(3f * i.fout());
                Lines.circle(e.x, e.y, 3f + i.fin() * 15f);
            });

            color(Color.lightGray);

            randLenVectors(e.id, 5, 2f + 23f * e.finpow(), (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
            });

            color(backColor);
            stroke(e.fout());

            randLenVectors(e.id + 1, 4, 1f + 23f * e.finpow(), (x, y) -> {
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 0.7f + e.fout() * 3f);
            });

            Drawf.light(e.x, e.y, 45f, backColor, 0.8f * e.fout());
        });
    }
    // endregion Hit
    public static Effect burstSmelt(float height, float width, float offset, int rotation, Color frontColor, Color backColor) {
        //Inspired, modified and translated to java from 'FlinTyX/DiverseTech'
        return new Effect(35, e -> {
            for (int i = 0; i < 2; i++) {
                //Darker Shade
                Draw.color(i == 0 ? frontColor : frontColor);

                float h = e.finpow() * height;
                float w = e.fout() * width;

                Drawf.tri(i == 0 ? e.x + (offset / 3) : e.x - (offset / 3), e.y, w, h, i == 0 ? (rotation - 90) : (rotation + 90));
                Drawf.tri(e.x, i == 0 ? e.y - (offset / 3) : e.y + (offset / 3), w, h, i == 0 ? -rotation : rotation);
                //Lighter Shade
                Draw.color(Color.gray, backColor, Color.white, e.fin());

                e.scaled(7, j -> {
                    Lines.stroke(3 * j.fout());
                    Lines.circle(e.x, e.y, 4 + j.fin() * 30);
                });
                Draw.color(backColor);
                Angles.randLenVectors(e.id, 8, 2 + 30 * e.finpow(), (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, (float) (e.fout() * 2 + 0.5));
                });
            }
        });
    }

}