package com.github.yuuki2028.towerdefense.towerdefense.Commons

import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import com.github.yuuki2028.towerdefense.towerdefense.MonsterEntity
import com.github.yuuki2028.towerdefense.towerdefense.TowerEntity
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object EntityCommon {
    fun genVec(a: Location, b: Location): Vector {
        val dX = a.x - b.x
        val dY = a.y - b.y
        val dZ = a.z - b.z
        val yaw = atan2(dZ, dX)
        val pitch = atan2(sqrt(dZ * dZ + dX * dX), dY) + Math.PI
        val x = sin(pitch) * cos(yaw)
        val y = sin(pitch) * sin(yaw)
        val z = cos(pitch)
        return Vector(x, z, y)
    }
    fun getTowerEntityFromEntity(entity: Entity,player: Player):TowerEntity? {
        for(towerEntity in TowerDefense.Companion.status[player.uniqueId]!!.towers){
            if(towerEntity.entity.location == entity.location){
                return towerEntity
            }
        }
        return null
    }
    fun getMonsterEntityFromEntity(entity: Entity,player: Player):MonsterEntity? {
        for(monsterEntity in TowerDefense.Companion.status[player.uniqueId]!!.monsters){
            if(monsterEntity.entity.location == entity.location){
                return monsterEntity
            }
        }
        return null
    }
}