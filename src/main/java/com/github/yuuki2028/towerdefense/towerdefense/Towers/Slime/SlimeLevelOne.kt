package com.github.yuuki2028.towerdefense.towerdefense.Towers.Skeleton

import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonAttackModules
import com.github.yuuki2028.towerdefense.towerdefense.Tower
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import com.github.yuuki2028.towerdefense.towerdefense.Towers.Slime.SlimeLevelTwo
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*

object SlimeLevelOne : Tower(Material.BONE) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("ねば")
        meta.lore = mutableListOf("damage->1", "range->4.0", "speed->40", "cost->50")
        this.itemMeta = meta
    }

    override var name = "ねば"
    override var damage = 1
    override var range = 4.0
    override var speed = 40
    override var cost = 50
    override var afters = mutableListOf<Tower>(SlimeLevelTwo)
    override var attackSound = Sound.BLOCK_SLIME_BLOCK_HIT
    override var attackModules = mutableListOf(CommonAttackModules.Slow1.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnTowerLocation, EntityType.SLIME) as Slime
        entity.setAI(false)
        entity.size = 1
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