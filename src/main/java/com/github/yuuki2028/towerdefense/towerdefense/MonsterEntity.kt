package com.github.yuuki2028.towerdefense.towerdefense

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class MonsterEntity(monster: Monster, entity: Entity,player: Player){
    val entity = entity
    val monster = monster
    val player = player
    var remoteness = 0
}