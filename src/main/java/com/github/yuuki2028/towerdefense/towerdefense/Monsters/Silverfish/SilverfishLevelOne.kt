package com.github.yuuki2028.towerdefense.towerdefense.Monsters.Silverfish

import com.github.yuuki2028.towerdefense.towerdefense.Events.DamageEvent
import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonAttackModules
import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonDamageModules
import com.github.yuuki2028.towerdefense.towerdefense.Monster
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import com.github.yuuki2028.towerdefense.towerdefense.Towers.Skeleton.SkeletonLevelOne
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.*

object SilverfishLevelOne : Monster(Material.SALMON){
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("ロックフィッシュ")
        meta!!.lore = mutableListOf("hp->10","speed->2.0","cost->5","inCoin->1")
        this.itemMeta = meta
    }
    override var name = "ロックフィッシュ"
    override var hp = 10
    override var speed = 2
    override var cost = 5
    override var inCoin = 1
    override var afters = mutableListOf<Monster>()
    override var modules = mutableListOf(CommonDamageModules.NULL.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnMonsterLocation, EntityType.SILVERFISH) as Silverfish
        entity.customName =this.name
        entity.maxHealth = this.hp.toDouble()
        entity.health = hp.toDouble()
        return entity
    }

}