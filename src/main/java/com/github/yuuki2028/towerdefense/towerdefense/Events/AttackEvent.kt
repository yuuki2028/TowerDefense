package com.github.yuuki2028.towerdefense.towerdefense.Events

import com.github.yuuki2028.towerdefense.towerdefense.MonsterEntity
import com.github.yuuki2028.towerdefense.towerdefense.TowerEntity

data class AttackEvent(var damage: Double, val tower: TowerEntity, var monster: MonsterEntity)