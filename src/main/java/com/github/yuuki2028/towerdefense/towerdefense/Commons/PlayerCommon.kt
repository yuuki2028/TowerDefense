package com.github.yuuki2028.towerdefense.towerdefense.Commons

import com.github.yuuki2028.towerdefense.towerdefense.TowerDefense
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player

object PlayerCommon {
    fun getAllPlayer():MutableList<Player>{
        val list = mutableListOf<Player>()
        for(player in Bukkit.getOnlinePlayers()){
            if(player.gameMode != GameMode.SPECTATOR){
                list.add(player)
            }
        }
        return list
    }
    fun updateCoin(player:Player){
        TowerDefense.board[player.uniqueId]!!.resetScores(TowerDefense.coin[player.uniqueId]!!.entry)
        TowerDefense.coin[player.uniqueId] = TowerDefense.objective[player.uniqueId]!!.getScore("coin[${TowerDefense.status[player.uniqueId]!!.coin}]")
        TowerDefense.coin[player.uniqueId]!!.score = 8
    }
    fun updateInCoin(player:Player){
        TowerDefense.board[player.uniqueId]!!.resetScores(TowerDefense.inCoin[player.uniqueId]!!.entry)
        TowerDefense.inCoin[player.uniqueId] = TowerDefense.objective[player.uniqueId]!!.getScore("inCoin[${TowerDefense.status[player.uniqueId]!!.inCoin}]")
        TowerDefense.inCoin[player.uniqueId]!!.score = 7
    }
    fun updateHealth(player:Player){
        TowerDefense.board[player.uniqueId]!!.resetScores(TowerDefense.health[player.uniqueId]!!.entry)
        TowerDefense.health[player.uniqueId] = TowerDefense.objective[player.uniqueId]!!.getScore("health[${TowerDefense.status[player.uniqueId]!!.health}]")
        TowerDefense.health[player.uniqueId]!!.score = 9
    }fun updateXP(player:Player){
        TowerDefense.board[player.uniqueId]!!.resetScores(TowerDefense.xp[player.uniqueId]!!.entry)
        TowerDefense.xp[player.uniqueId] = TowerDefense.objective[player.uniqueId]!!.getScore("xp[${TowerDefense.status[player.uniqueId]!!.xp}]")
        TowerDefense.xp[player.uniqueId]!!.score = 6
    }

}