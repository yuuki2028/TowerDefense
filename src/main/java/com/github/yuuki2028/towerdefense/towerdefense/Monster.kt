package com.github.yuuki2028.towerdefense.towerdefense

import com.github.yuuki2028.towerdefense.towerdefense.Events.AttackEvent
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Monster(type: Material): ItemStack(type){
    abstract var name:String
    abstract var hp: Int
    abstract var speed: Double
    abstract var cost: Int
    abstract var inCoin: Int
    abstract var xp: Int
    abstract var afters: MutableList<Monster>
    abstract var modules: MutableList<(AttackEvent) -> AttackEvent>
    abstract fun createEntity(player: Player): LivingEntity
}