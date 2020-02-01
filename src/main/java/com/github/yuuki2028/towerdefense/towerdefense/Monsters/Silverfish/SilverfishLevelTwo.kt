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

object SilverfishLevelTwo : Monster(Material.SALMON) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("アイアンフィッシュ")
        meta.lore = mutableListOf("hp->100", "speed->2.5", "cost->60", "inCoin->5")
        this.itemMeta = meta
    }

    override var name = "アイアンフィッシュ"
    override var hp = 100
    override var speed = 2.5
    override var cost = 60
    override var inCoin = 5
    override var xp = 10
    override var afters = mutableListOf<Monster>()
    override var modules = mutableListOf(CommonAttackModules.NULL.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnMonsterLocation, EntityType.SILVERFISH) as Silverfish
        entity.customName = this.name
        entity.maxHealth = this.hp.toDouble()
        entity.health = hp.toDouble()
        return entity
    }

}