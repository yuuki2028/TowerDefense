package com.github.yuuki2028.towerdefense.towerdefense.Commons

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard

object ScoreboardCommon {
    fun setActive(board:Scoreboard,objectname:String,tag:String,displayName:String):Objective{
        var objective = board.getObjective(objectname)
        if(objective == null){
            objective = board.registerNewObjective(objectname,tag,displayName)
        }
        return objective!!
    }
}