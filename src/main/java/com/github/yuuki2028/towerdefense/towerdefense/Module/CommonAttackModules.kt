package com.github.yuuki2028.towerdefense.towerdefense.Module

import com.github.yuuki2028.towerdefense.towerdefense.Events.AttackEvent
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

enum class CommonAttackModules(val function: (AttackEvent) -> AttackEvent) {
    NULL(fun(event: AttackEvent): AttackEvent { return event }),
    AddSpeedDamage(fun(event: AttackEvent): AttackEvent {
        event.damage += event.monster.monster.speed * 100
        return event
    }),
    AddHealthDamage(fun(event: AttackEvent): AttackEvent {
        event.damage += event.monster.monster.hp
        return event
    }),
    HalfHealthDamage(fun(event: AttackEvent): AttackEvent {
        event.damage += event.monster.monster.hp.toDouble()
        return event
    }),
    Damage10Reduction(fun(event: AttackEvent): AttackEvent {
        event.damage = event.damage * 0.9
        return event
    }),
    Slow1(fun(event: AttackEvent): AttackEvent {
        (event.monster.entity as LivingEntity).addPotionEffect(PotionEffect(PotionEffectType.SLOW, 100, 0, false, false, false))
        return event
    }),
}