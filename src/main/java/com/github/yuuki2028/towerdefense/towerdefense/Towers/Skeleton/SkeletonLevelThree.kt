package com.github.yuuki2028.towerdefense.towerdefense.Towers.Skeleton

import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonAttackModules
import com.github.yuuki2028.towerdefense.towerdefense.Tower
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack

object SkeletonLevelThree : Tower(Material.BONE) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("助参郎")
        meta.lore = mutableListOf("damage->250", "range->7.0", "speed->10", "cost->1200")
        this.itemMeta = meta
    }

    override var name = "助参郎"
    override var damage = 250
    override var range = 7.0
    override var speed = 10
    override var cost = 1200
    override var afters = mutableListOf<Tower>(SkeletonLevelFourTypeOne, SkeletonLevelFourTypeTwo)
    override var attackSound = Sound.ENTITY_ARROW_SHOOT
    override var attackModules = mutableListOf(CommonAttackModules.NULL.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnTowerLocation, EntityType.SKELETON) as Skeleton
        entity.setAI(false)
        entity.isInvulnerable = true
        entity.customName = name
        entity.equipment!!.helmet = ItemStack(Material.IRON_HELMET)
        entity.equipment!!.chestplate = ItemStack(Material.IRON_CHESTPLATE)
        entity.equipment!!.leggings = ItemStack(Material.IRON_LEGGINGS)
        entity.equipment!!.boots = ItemStack(Material.IRON_BOOTS)
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