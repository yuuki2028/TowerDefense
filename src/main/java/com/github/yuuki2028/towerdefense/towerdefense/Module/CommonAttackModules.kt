package com.github.yuuki2028.towerdefense.towerdefense.Module

import com.github.yuuki2028.towerdefense.towerdefense.Events.AttackEvent

enum class CommonAttackModules(val function: (AttackEvent) -> Double){
    NULL(fun(event: AttackEvent): Double {return event.damage})
}