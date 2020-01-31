package com.github.yuuki2028.towerdefense.towerdefense.Commons

import com.github.yuuki2028.towerdefense.towerdefense.Monster
import com.github.yuuki2028.towerdefense.towerdefense.Tower
import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import org.bukkit.inventory.ItemStack

object inventoryCommon {
    fun getJobfromItemStack(item: ItemStack): Tower?{
        var j: Tower? = null
        for (key in TowerDefense.towers){
            if(key.itemMeta!!.displayName == item.itemMeta!!.displayName){
                j = key
            }
            else{
                for(other in key.afters){
                    if(other.itemMeta!!.displayName == item.itemMeta!!.displayName){
                        j = other
                    }
                }
            }
        }
        return j
    }
    fun getMonsterfromItemStack(item: ItemStack): Monster?{
        var j: Monster? = null
        for (key in TowerDefense.monsters){
            if(key.itemMeta!!.displayName == item.itemMeta!!.displayName){
                j = key
            }
            else{
                for(other in key.afters){
                    if(other.itemMeta!!.displayName == item.itemMeta!!.displayName){
                        j = other
                    }
                }
            }
        }
        return j
    }
}