package com.friendlyfirerules.base.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import electroblob.wizardry.event.*;
import electroblob.wizardry.spell.Transportation;

import java.util.HashMap;
import java.util.Map;

import com.friendlyfirerules.base.Base;

@EventBusSubscriber
public class Electo extends Thread {

    static Map<String, Long> lastDamageMap = new HashMap<>();
 
    @SubscribeEvent
    public static void spellPre(SpellCastEvent.Pre event) {

        Base.getLogger().info(event.getSpell().getUnlocalisedName());
        
        if (!(event.getSpell() instanceof Transportation)) { return; }
        
        Base.getLogger().warn(event.getCaster());
        // make sure it's player
        if (event.getCaster() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getCaster();

            Base.getLogger().info("Caster is a player");
            Base.getLogger().info(player.getUniqueID().toString());

            Long lastTime = lastDamageMap.get(player.getUniqueID().toString());
            lastTime = lastTime == null ? 0 : lastTime;

            // if they were damaged under certain threshold, cancel the event.
            if ((System.currentTimeMillis() - lastTime) < 10*1000) {
                event.setCanceled(true);
            } else {
                Base.getLogger().info(player.getName() + " can't teleport because they are on damage cooldown. ");
            } 
        }
    }

    @SubscribeEvent
    public static void damageEvent(LivingDamageEvent event) {

        if (event.getEntity() instanceof EntityPlayer) {
            // the victim is a player
            EntityPlayer p = (EntityPlayer) event.getEntity();

            // figure out if the source is a player
            DamageSource source = event.getSource();
            Entity trueSource = source == null ? null : source.getTrueSource();
            
            boolean isAPlayer;
            if (trueSource instanceof EntityPlayer) {
                isAPlayer = true;
            } else {
                isAPlayer = false;
            }

            // log the last time player took damage
            // DamageSource source = event.getSource();
            if (isAPlayer) {
                // Base.getLogger().info("Player dmg");
                long now = System.currentTimeMillis();

                lastDamageMap.put(p.getUniqueID().toString(), now);
            } 
        }
    }
}
