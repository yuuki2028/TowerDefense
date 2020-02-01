package com.github.yuuki2028.towerdefense.towerdefense

import com.github.yuuki2028.towerdefense.towerdefense.Events.AttackEvent
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Tower(type:Material):ItemStack(type){
    abstract var name:String
    abstract var damage:Int
    abstract var range:Double
    abstract var speed:Int
    abstract var cost:Int
    abstract var afters:MutableList<Tower>
    abstract var attackSound: Sound
    abstract var attackModules: MutableList<(AttackEvent) -> AttackEvent>
    abstract fun createEntity(player: Player):LivingEntity
    abstract fun getAttackEntity(entity: Entity):MutableList<LivingEntity>
}