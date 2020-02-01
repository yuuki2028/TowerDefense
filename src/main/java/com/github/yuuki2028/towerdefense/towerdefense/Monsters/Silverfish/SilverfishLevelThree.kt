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

object SilverfishLevelThree : Monster(Material.SALMON) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("ゴールデンフィッシュ")
        meta.lore = mutableListOf("hp->1000", "speed->3.0", "cost->600", "inCoin->60")
        this.itemMeta = meta
    }

    override var name = "ゴールデンフィッシュ"
    override var hp = 1000
    override var speed = 3.0
    override var cost = 600
    override var inCoin = 60
    override var xp = 25
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