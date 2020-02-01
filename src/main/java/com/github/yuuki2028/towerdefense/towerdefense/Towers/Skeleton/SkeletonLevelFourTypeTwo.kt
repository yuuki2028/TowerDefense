package com.github.yuuki2028.towerdefense.towerdefense.Towers.Skeleton

import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonAttackModules
import com.github.yuuki2028.towerdefense.towerdefense.Tower
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta

object SkeletonLevelFourTypeTwo : Tower(Material.BONE) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("ガトリング")
        meta.lore = mutableListOf("damage->2000", "range->5.0", "speed->5", "cost->22000")
        this.itemMeta = meta
    }

    override var name = "ガトリング"
    override var damage = 2000
    override var range = 5.0
    override var speed = 5
    override var cost = 22000
    override var afters = mutableListOf<Tower>(SkeletonLevelFive)
    override var attackSound = Sound.ENTITY_ARROW_SHOOT
    override var attackModules = mutableListOf(CommonAttackModules.ADDSPEEDDAMAGE.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnTowerLocation, EntityType.SKELETON) as Skeleton
        entity.setAI(false)
        entity.isInvulnerable = true
        entity.customName = name
        entity.equipment!!.helmet = ItemStack(Material.LEATHER_HELMET)
        val helmet = entity.equipment!!.helmet
        val helmetMeta = helmet!!.itemMeta as LeatherArmorMeta
        helmetMeta.setColor(Color.YELLOW)
        entity.equipment!!.chestplate = ItemStack(Material.LEATHER_CHESTPLATE)
        val chestplate = entity.equipment!!.chestplate
        val chestplateMeta = chestplate!!.itemMeta as LeatherArmorMeta
        chestplateMeta.setColor(Color.YELLOW)
        entity.equipment!!.leggings = ItemStack(Material.LEATHER_LEGGINGS)
        val leggings = entity.equipment!!.leggings
        val leggingsMeta = leggings!!.itemMeta as LeatherArmorMeta
        leggingsMeta.setColor(Color.YELLOW)
        entity.equipment!!.boots = ItemStack(Material.LEATHER_BOOTS)
        val boots = entity.equipment!!.boots
        val bootsMeta = boots!!.itemMeta as LeatherArmorMeta
        bootsMeta.setColor(Color.YELLOW)
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