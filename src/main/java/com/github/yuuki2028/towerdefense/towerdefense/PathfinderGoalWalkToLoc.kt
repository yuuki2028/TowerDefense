package com.github.yuuki2028.towerdefense.towerdefense

import net.minecraft.server.v1_15_R1.EntityInsentient
import net.minecraft.server.v1_15_R1.Navigation
import net.minecraft.server.v1_15_R1.PathEntity
import net.minecraft.server.v1_15_R1.PathfinderGoal
import org.bukkit.Location


class PathfinderGoalWalkToLoc(private val entity: EntityInsentient, private val loc: Location, speed: Double) : PathfinderGoal() {
    private val speed:Double = speed
    private val navigation: Navigation = entity.navigation as Navigation
    override fun a(): Boolean {
        return true
    }

    override fun b(): Boolean {
        return false
    }

    override fun c() {
        navigation.a(loc.x, loc.y, loc.z,speed)
    }

}