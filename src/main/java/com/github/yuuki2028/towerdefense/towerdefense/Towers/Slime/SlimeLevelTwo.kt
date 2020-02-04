package com.github.yuuki2028.towerdefense.towerdefense.Towers.Slime

import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonAttackModules
import com.github.yuuki2028.towerdefense.towerdefense.Tower
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*

object SlimeLevelTwo : Tower(Material.BONE) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("ねばねば")
        meta.lore = mutableListOf("damage->10", "range->4.0", "speed->40", "cost->600")
        this.itemMeta = meta
    }

    override var name = "ねばねば"
    override var damage = 10
    override var range = 4.0
    override var speed = 40
    override var cost = 600
    override var afters = mutableListOf<Tower>(SlimeLevelThree)
    override var attackSound = Sound.BLOCK_SLIME_BLOCK_HIT
    override var attackModules = mutableListOf(CommonAttackModules.Slow2.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnTowerLocation, EntityType.SLIME) as Slime
        entity.setAI(false)
        entity.size = 2
        entity.isInvulnerable = true
        entity.customName = name
        return entity
    }

    override fun getAttackEntity(entity: Entity): MutableList<LivingEntity> {
        val list = mutableListOf<LivingEntity>()
        val listB = mutableListOf<LivingEntity>()
        for (entity in entity.getNearbyEntities(range, 10.0, range)) {
            if (entity is LivingEntity) {
                if (entity !is Player) {
                    listB.add(entity)
                }
            }
        }
        list.add(listB.random())
        return list
    }
}