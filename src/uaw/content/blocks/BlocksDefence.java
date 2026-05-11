package uaw.content.blocks;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.DrawTurret;

import static uaw.Vars.*;
import static uaw.Vars.px;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

public class BlocksDefence {

    public static Block placeholder,
    // MG
    quadra;

    public static void load() {
        quadra = new ItemTurret("quadra") {{
            requirements(Category.turret, with(
                    Items.copper, 115,
                    Items.lead, 120,
                    Items.titanium, 25,
                    Items.graphite, 80
            ));
            size = 2;
            scaledHealth = 160;

            reload = 6;
            recoil = 1f;
            recoilTime = 30f;
            maxAmmo = 30;

            range = 20 * tilesize;
            shootCone = 15f;
            inaccuracy = 7.5f;
            rotateSpeed = 10f;

            ammoUseEffect = Fx.casing2Double;
            shootSound = Sounds.shoot;

            shoot = new ShootAlternate() {{
                barrels = 2;
                shots = 2;
                barrelOffset = 5;
                spread = 4f;
                velocityRnd = 0.2f;
            }};

            ammo(
                    Items.copper,  new BasicBulletType(8, 9){{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 2;

                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        hitColor = backColor = trailColor = Pal.copperAmmoBack;
                        frontColor = Pal.copperAmmoFront;
                    }}
            );
            limitRange();

            squareSprite = false;
            cooldownTime = reload * 0.5f;
            drawer = new DrawTurret(modTurretBase) {{
                parts.addAll(
                        new RegionPart("-barrel") {{
                            progress = PartProgress.warmup;
                            moveY = -4.0f * px;
                        }},
                        new RegionPart("-bolt") {{
                            progress = PartProgress.recoil;
                            moveY = -7.0f * px;
                        }},
                        new RegionPart("-body")
                );
            }};
        }};
    }
}