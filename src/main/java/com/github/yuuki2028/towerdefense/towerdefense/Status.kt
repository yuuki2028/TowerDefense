package com.github.yuuki2028.towerdefense.towerdefense

import net.minecraft.server.v1_15_R1.DamageSource.arrow
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity


class Status {
    var health = 20
    var coin = 100
    var inCoin = 5
    var xp = 0
    var spawnTowerLocation = org.bukkit.Location(Bukkit.getWorld("world"),0.0,0.0,0.0)
    var spawnMonsterLocation = org.bukkit.Location(Bukkit.getWorld("world"),0.0,0.0,0.0)
    var goalGate:Location = org.bukkit.Location(Bukkit.getWorld("world"),0.0,0.0,0.0)
    var monsterInventory = TowerDefense.monsterinventory
    lateinit var crickEntity:LivingEntity
    lateinit var beforeMonster:Monster
    val towers = mutableListOf<TowerEntity>()
    val monsters = mutableListOf<MonsterEntity>()
}