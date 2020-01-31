package com.github.yuuki2028.towerdefense.towerdefense.Towers.Skeleton

import com.github.yuuki2028.towerdefense.towerdefense.Module.CommonAttackModules
import com.github.yuuki2028.towerdefense.towerdefense.Tower
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack

object SkeletonLevelTwo : Tower(Material.BONE) {
    init {
        val meta = this.itemMeta
        meta!!.setDisplayName("助二郎")
        meta!!.lore = mutableListOf("damage->40","range->6.0","speed->30","cost->100")
        this.itemMeta = meta
    }
    override var name = "助二郎"
    override var damage = 40
    override var range = 6.0
    override var speed = 30
    override var cost = 100
    override var afters = mutableListOf<Tower>()
    override var attackSound = Sound.ENTITY_ARROW_SHOOT
    override var attackModules = mutableListOf(CommonAttackModules.NULL.function)
    override fun createEntity(player: Player): LivingEntity {
        val entity = Bukkit.getWorld("world")!!.spawnEntity(TowerDefense.status[player.uniqueId]!!.spawnTowerLocation, EntityType.SKELETON) as Skeleton
        entity.setAI(false)
        entity.isInvulnerable = true
        entity.customName = name
        entity.equipment!!.helmet = ItemStack(Material.LEATHER_HELMET)
        entity.equipment!!.chestplate = ItemStack(Material.LEATHER_CHESTPLATE)
        entity.equipment!!.leggings = ItemStack(Material.LEATHER_LEGGINGS)
        entity.equipment!!.boots = ItemStack(Material.LEATHER_BOOTS)
        return entity
    }

    override fun getAttackEntity(entity: Entity):MutableList<LivingEntity>{
        val list = mutableListOf<LivingEntity>()
        val listB = mutableListOf<LivingEntity>()
        for(entity in entity.getNearbyEntities(range,10.0, range)){
            if(entity is LivingEntity){
                if(entity !is Player) {
                    listB.add(entity)
                }
            }
        }
        list.add(listB.random())
        return list
    }
}