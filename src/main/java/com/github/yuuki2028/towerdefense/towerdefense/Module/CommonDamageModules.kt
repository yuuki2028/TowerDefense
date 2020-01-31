package com.github.yuuki2028.towerdefense.towerdefense.Module

import com.github.yuuki2028.towerdefense.towerdefense.Events.AttackEvent
import com.github.yuuki2028.towerdefense.towerdefense.Events.DamageEvent

enum class CommonDamageModules(val function: (DamageEvent) -> Double){
    NULL(fun(event: DamageEvent): Double {return event.damage})
}