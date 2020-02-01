package com.github.yuuki2028.towerdefense.towerdefense.Monsters.Silverfish

import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonAttackModules
import com.github.yuuki2028.towerdefense.towerdefense.Monster
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Silverfish

object SilverfishLevelFour : Monster(Material.SALMON) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("スチールフィッシュ")
        meta.lore = mutableListOf("hp->10000", "speed->3.5", "cost->6000", "inCoin->600")
        this.itemMeta = meta
    }

    override var name = "スチールフィッシュ"
    override var hp = 10000
    override var speed = 3.5
    override var cost = 6000
    override var inCoin = 600
    override var xp = 60
    override var afters = mutableListOf<Monster>()
    override var modules = mutableListOf(CommonAttackModules.Damage10Reduction.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnMonsterLocation, EntityType.SILVERFISH) as Silverfish
        entity.customName = this.name
        entity.maxHealth = this.hp.toDouble()
        entity.health = hp.toDouble()
        return entity
    }

}