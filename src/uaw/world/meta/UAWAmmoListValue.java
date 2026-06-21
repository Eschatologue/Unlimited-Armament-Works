package uaw.world.meta;

import arc.Core;
import arc.math.Mathf;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.OrderedMap;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.type.Item;
import mindustry.ui.Styles;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.StatValues;
import uaw.entities.bullet.UAWBulletType;

import static mindustry.Vars.tilesize;

/**
 * A custom {@link StatValue} that builds the ammo stat table for UAW item turrets.
 *
 * <p>Replicates vanilla {@link StatValues#ammo} line for line, and extends it
 * with support for fields on {@link UAWBulletType} — currently {@link UAWBulletType#ammoLabel}.</p>
 * <p>Future per-bullet stat additions belong here.</p>
 */
public class UAWAmmoListValue implements StatValue {

    /**
     * The turret's ammo map, keyed by {@link Item}.
     * Passed in from {@code ItemTurret.ammoTypes}.
     */
    private final OrderedMap<Item, BulletType> ammoTypes;

    /**
     * The turret's internal name, used to look up per-ammo bundle keys.
     * Passed in from {@code Block.name}.
     */
    private final String turretName;

    /**
     * @param ammoTypes  the turret's ammo map ({@code ItemTurret.ammoTypes})
     * @param turretName the turret's internal name ({@code Block.name})
     */
    public UAWAmmoListValue(OrderedMap<Item, BulletType> ammoTypes, String turretName) {
        this.ammoTypes = ammoTypes;
        this.turretName = turretName;
    }

    /**
     * Adds a new row in {@code table} and places {@code text} in it.
     * Mirrors the private {@code StatValues.sep}.
     */
    private static void sep(Table table, String text) {
        table.row();
        table.add(text);
    }

    // region Helpers

    /**
     * Formats a numeric delta with a colour tag.
     *
     * <p>Positive values are prefixed with {@code [stat]+}; negative with {@code [negstat]}.
     * Mirrors the private {@code StatValues.ammoStat}.</p>
     */
    private static String delta(float value) {
        return (value > 0 ? "[stat]+" : "[negstat]") + Strings.autoFixed(value, 1);
    }

    @Override
    public void display(Table table) {
        table.row();

        var keys = ammoTypes.keys().toSeq();
        keys.sort();

        for (Item item : keys) {
            BulletType type = ammoTypes.get(item);

            // Spawn-unit ammo has its own panel layout; delegate to vanilla entirely.
            if (type.spawnUnit != null && type.spawnUnit.weapons.size > 0) {
                StatValues.ammo(ObjectMap.of(item, type.spawnUnit.weapons.first().bullet), turretName).display(table);
                continue;
            }

            table.table(Styles.grayPanel, bt -> {
                bt.left().top().defaults().padRight(3).left();

                // Header: item icon, localised name, and the optional UAW inline label.
                bt.table(header -> {
                    header.image(item.uiIcon).size(3 * 8).padRight(4).right().scaling(Scaling.fit).top();
                    header.add(item.localizedName).padRight(10).left().top();

                    // ammoLabel is UAW-specific; only cast when needed to avoid polluting vanilla bullets.
                    if (type instanceof UAWBulletType uawType && uawType.ammoLabel != null) {
                        header.add("[lightgray]// [stat]" + uawType.ammoLabel).left().top();
                    }

                    if (type.displayAmmoMultiplier && type.statLiquidConsumed > 0f) {
                        header.add("[stat]" + Strings.autoFixed(type.statLiquidConsumed / type.ammoMultiplier * 60f, 2)
                                + " [lightgray]" + StatUnit.perSecond.localized());
                    }
                });
                bt.row();

                // region Vanilla stat lines

                if (type.damage > 0 && (type.collides || type.splashDamage <= 0)) {
                    String cont = type.continuousDamage() > 0
                            ? "[lightgray] ~ [stat]" + Core.bundle.format("bullet.damage", type.continuousDamage()) + StatUnit.perSecond.localized()
                            : "";
                    bt.add(Core.bundle.format("bullet.damage", type.damage) + cont);
                }

                if (type.buildingDamageMultiplier != 1)
                    sep(bt, Core.bundle.format("bullet.buildingdamage", delta((int) (type.buildingDamageMultiplier * 100 - 100))));

                if (type.rangeChange != 0)
                    sep(bt, Core.bundle.format("bullet.range", delta(type.rangeChange / tilesize)));

                if (type.shieldDamageMultiplier != 1)
                    sep(bt, Core.bundle.format("bullet.shielddamage", delta((int) (type.shieldDamageMultiplier * 100 - 100))));

                if (type.splashDamage > 0)
                    sep(bt, Core.bundle.format("bullet.splashdamage", (int) type.splashDamage, Strings.fixed(type.splashDamageRadius / tilesize, 1)));

                if (!Mathf.equal(type.ammoMultiplier, 1f) && type.displayAmmoMultiplier && type.statLiquidConsumed <= 0f)
                    sep(bt, Core.bundle.format("bullet.multiplier", (int) type.ammoMultiplier));

                if (!Mathf.equal(type.reloadMultiplier, 1f))
                    sep(bt, Core.bundle.format("bullet.reload", delta((int) (type.reloadMultiplier * 100 - 100))));

                if (type.knockback > 0)
                    sep(bt, Core.bundle.format("bullet.knockback", Strings.autoFixed(type.knockback, 2)));

                if (type.healPercent > 0f)
                    sep(bt, Core.bundle.format("bullet.healpercent", Strings.autoFixed(type.healPercent, 2)));

                if (type.healAmount > 0f)
                    sep(bt, Core.bundle.format("bullet.healamount", Strings.autoFixed(type.healAmount, 2)));

                if (type.pierce || type.pierceCap != -1)
                    sep(bt, type.pierceCap == -1
                            ? "@bullet.infinitepierce"
                            : Core.bundle.format("bullet.pierce", type.pierceCap));

                if (type.incendAmount > 0) sep(bt, "@bullet.incendiary");
                if (type.homingPower > 0.01f) sep(bt, "@bullet.homing");

                if (type.lightning > 0)
                    sep(bt, Core.bundle.format("bullet.lightning", type.lightning,
                            type.lightningDamage < 0 ? type.damage : type.lightningDamage));

                if (type.pierceArmor) {
                    sep(bt, "@bullet.armorpierce");
                } else {
                    if (type.armorMultiplier != 1f) {
                        if (type.armorMultiplier > 1f)
                            sep(bt, Core.bundle.format("bullet.armorweakness", type.armorMultiplier));
                        else if (Mathf.sign(type.armorMultiplier) == 1)
                            sep(bt, Core.bundle.format("bullet.partialarmorpierce", (int) ((1 - type.armorMultiplier) * 100)));
                        else sep(bt, Core.bundle.format("bullet.antiarmor", -type.armorMultiplier));
                    }
                    if (type.blockArmorMultiplier != 1f) {
                        if (type.blockArmorMultiplier > 1f)
                            sep(bt, Core.bundle.format("bullet.blockarmorweakness", type.blockArmorMultiplier));
                        else if (Mathf.sign(type.blockArmorMultiplier) == 1)
                            sep(bt, Core.bundle.format("bullet.blockpartialarmorpierce", (int) ((1 - type.blockArmorMultiplier) * 100)));
                        else sep(bt, Core.bundle.format("bullet.blockantiarmor", -type.blockArmorMultiplier));
                    }
                }

                if (type.maxDamageFraction > 0)
                    sep(bt, Core.bundle.format("bullet.maxdamagefraction", (int) (type.maxDamageFraction * 100)));

                if (type.suppressionRange > 0)
                    sep(bt, Core.bundle.format("bullet.suppression",
                            Strings.autoFixed(type.suppressionDuration / 60f, 2),
                            Strings.fixed(type.suppressionRange / tilesize, 1)));

                if (type.status != StatusEffects.none)
                    sep(bt, (type.status.hasEmoji() ? type.status.emoji() : "")
                            + "[stat]" + type.status.localizedName
                            + (type.status.reactive ? "" :
                            "[lightgray] ~ [stat]" + Strings.autoFixed(type.statusDuration / 60f, 1)
                                    + "[lightgray] " + Core.bundle.get("unit.seconds")));

                if (!type.targetMissiles) sep(bt, "@bullet.notargetsmissiles");
                if (!type.targetBlocks) sep(bt, "@bullet.notargetsbuildings");

                // endregion

                // Frag bullets get a collapsible sub-table; vanilla handles the layout.
                if (type.fragBullet != null) {
                    bt.row();
                    Table fragContent = new Table();
                    StatValues.ammo(ObjectMap.of(item, type.fragBullet), true, false).display(fragContent);
                    Collapser collapse = new Collapser(fragContent, true);
                    collapse.setDuration(0.1f);
                    bt.table(ft -> {
                        ft.left().defaults().left();
                        ft.add(Core.bundle.format("bullet.frags", type.fragBullets));
                        ft.button(Icon.downOpen, Styles.emptyi, () -> collapse.toggle(false))
                                .update(i -> i.getStyle().imageUp = collapse.isCollapsed() ? Icon.downOpen : Icon.upOpen)
                                .size(8).padLeft(16f).expandX();
                    });
                    bt.row();
                    bt.add(collapse);
                }

            }).padLeft(5).padTop(5).padBottom(5).growX().margin(10);
            table.row();
        }
    }

    // endregion
}
