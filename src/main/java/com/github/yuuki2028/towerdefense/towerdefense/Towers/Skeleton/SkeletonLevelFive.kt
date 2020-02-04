package com.github.yuuki2028.towerdefense.towerdefense.Towers.Skeleton

import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonAttackModules
import com.github.yuuki2028.towerdefense.towerdefense.Tower
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack

object SkeletonLevelFive : Tower(Material.BONE) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("リッチ")
        meta.lore = mutableListOf("damage->0", "range->12.0", "speed->10", "cost->100000")
        this.itemMeta = meta
    }

    override var name = "リッチ"
    override var damage = 0
    override var range = 12.0
    override var speed = 10
    override var cost = 100000
    override var afters = mutableListOf<Tower>()
    override var attackSound = Sound.ENTITY_ARROW_SHOOT
    override var attackModules = mutableListOf(CommonAttackModules.HalfHealthDamage.function, CommonAttackModules.AddSpeedDamage.function, CommonAttackModules.AddHealthDamage.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnTowerLocation, EntityType.SKELETON) as Skeleton
        entity.setAI(false)
        entity.isInvulnerable = true
        entity.customName = name
        entity.equipment!!.helmet = ItemStack(Material.DIAMOND_HELMET)
        entity.equipment!!.chestplate = ItemStack(Material.DIAMOND_CHESTPLATE)
        entity.equipment!!.leggings = ItemStack(Material.DIAMOND_LEGGINGS)
        entity.equipment!!.boots = ItemStack(Material.DIAMOND_BOOTS)
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