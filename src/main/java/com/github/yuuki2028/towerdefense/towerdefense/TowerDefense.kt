package com.github.yuuki2028.towerdefense.towerdefense
import net.minecraft.server.v1_15_R1.*
import com.github.yuuki2028.towerdefense.towerdefense.Commons.EntityCommon
import com.github.yuuki2028.towerdefense.towerdefense.Commons.PlayerCommon
import com.github.yuuki2028.towerdefense.towerdefense.Commons.ScoreboardCommon
import com.github.yuuki2028.towerdefense.towerdefense.Commons.inventoryCommon
import com.github.yuuki2028.towerdefense.towerdefense.Events.AttackEvent
import com.github.yuuki2028.towerdefense.towerdefense.Events.DamageEvent
import com.github.yuuki2028.towerdefense.towerdefense.Monsters.Silverfish.SilverfishLevelOne
import com.github.yuuki2028.towerdefense.towerdefense.Towers.Skeleton.SkeletonLevelOne
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityCombustEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scoreboard.*
import org.bukkit.scoreboard.Scoreboard
import java.util.*

class TowerDefense : JavaPlugin(),Listener {
    override fun onEnable() {
        // Plugin startup logic
        logger.info(ChatColor.AQUA.toString()+"${TD}起動開始します")
        Bukkit.getPluginManager().registerEvents(this,this)
        registerTower(SkeletonLevelOne)
        registerMonster(SilverfishLevelOne)
        logger.info(ChatColor.AQUA.toString()+"${TD}起動完了")
    }
    override fun onDisable() {
        // Plugin shutdown logic
        logger.info(ChatColor.LIGHT_PURPLE.toString()+"${TD}停止します")
        logger.info(ChatColor.LIGHT_PURPLE.toString()+"${TD}停止完了")
    }
    @EventHandler
    fun fire(event:EntityCombustEvent){
        event.isCancelled = true
    }
    @EventHandler
    fun Open(event: PlayerInteractEvent){
        if(game){
            if(event.hasBlock()){
                if(event.action == Action.RIGHT_CLICK_BLOCK) {
                    if (event.clickedBlock!!.type == Material.REDSTONE_BLOCK) {
                        if (event.player.inventory.itemInMainHand.type == Material.TOTEM_OF_UNDYING) {
                            val loc = event.clickedBlock!!.getRelative(BlockFace.UP).location
                            loc.x += 0.5
                            loc.z += 0.5
                            status[event.player.uniqueId]!!.spawnTowerLocation = loc
                            event.player.openInventory(towerInventory)
                            event.isCancelled = true
                        }
                    }
                }
            }
            if (event.player.inventory.itemInMainHand.type == Material.NETHER_STAR) {
                event.player.openInventory(status[event.player.uniqueId]!!.monsterInventory)
            }
        }
    }
    @EventHandler
    fun Upgrade(event: PlayerInteractAtEntityEvent) {
        if (game) {
            if(event.player.inventory.itemInMainHand.type == Material.LAPIS_LAZULI){
                val tower = EntityCommon.getTowerEntityFromEntity(event.rightClicked,event.player)
                if(tower != null) {
                    val upGradeInventory = Bukkit.createInventory(null, InventoryType.DISPENSER, "タワー強化")
                    for (after in tower.tower.afters) {
                        upGradeInventory.addItem(after)
                        status[event.player.uniqueId]!!.crickEntity = tower.entity as LivingEntity
                        event.player.openInventory(upGradeInventory)
                    }
                }
            }
        }
    }
    @EventHandler
    fun Click(event: InventoryClickEvent){
        if(game) {
            if (event.currentItem != null) {
                val item = event.currentItem!!
                if (item.hasItemMeta()) {
                    event.isCancelled = true
                    when (event.view.title) {
                        "タワー作成" -> {
                            if (event.isRightClick) {
                                val tower = inventoryCommon.getJobfromItemStack(item)!!
                                if (tower.cost <= status[event.whoClicked.uniqueId]!!.coin) {
                                    val entity = tower.createEntity(event.whoClicked as Player)
                                    status[event.whoClicked.uniqueId]!!.towers.add(TowerEntity(tower, entity))
                                    status[event.whoClicked.uniqueId]!!.coin -= tower.cost
                                }
                            }
                        }
                        "タワー強化"->{
                            val beforeTower = EntityCommon.getTowerEntityFromEntity(status[event.whoClicked.uniqueId]!!.crickEntity, event.whoClicked as Player)
                            val afterTower = inventoryCommon.getJobfromItemStack(item)!!
                            if (beforeTower != null) {
                                if ((afterTower.cost - beforeTower.tower.cost) <= status[event.whoClicked.uniqueId]!!.coin) {
                                    status[event.whoClicked.uniqueId]!!.crickEntity.teleport(Location(event.whoClicked.world, 0.0, -20.0, 0.0))
                                    status[event.whoClicked.uniqueId]!!.crickEntity.remove()
                                    status[event.whoClicked.uniqueId]!!.towers.remove(beforeTower)
                                    val entity = afterTower.createEntity(event.whoClicked as Player)
                                    status[event.whoClicked.uniqueId]!!.towers.add(TowerEntity(afterTower, entity))
                                    status[event.whoClicked.uniqueId]!!.coin -= (afterTower.cost - beforeTower.tower.cost)
                                }
                            }
                        }
                        "モンスター召喚"->{
                            if (event.isRightClick) {
                                val monster = inventoryCommon.getMonsterfromItemStack(item)!!
                                if (monster.cost <= status[event.whoClicked.uniqueId]!!.coin) {
                                    for(player in PlayerCommon.getAllPlayer()) {
                                        val entity = monster.createEntity(event.whoClicked as Player)
                                        val loc = status[event.whoClicked.uniqueId]!!.goalGate
                                        val cEntity = ((entity as CraftEntity).handle as EntityInsentient)
                                        val path = PathfinderGoalWalkToLoc(cEntity, loc, monster.speed / 2.0)
                                        cEntity.goalSelector = PathfinderGoalSelector(cEntity.world.methodProfiler)
                                        cEntity.goalSelector.a(0, path)
                                        status[player.uniqueId]!!.monsters.add(MonsterEntity(monster, entity, event.whoClicked as Player))
                                    }
                                    status[event.whoClicked.uniqueId]!!.coin -= monster.cost
                                    status[event.whoClicked.uniqueId]!!.inCoin += monster.inCoin
                                }
                            }
                            else if(event.isLeftClick){
                                val beforeMonster = inventoryCommon.getMonsterfromItemStack(item)!!
                                val upGradeInventory = Bukkit.createInventory(null, InventoryType.DISPENSER, "モンスター強化")
                                for (after in beforeMonster.afters) {
                                    upGradeInventory.addItem(after)
                                    event.whoClicked.openInventory(upGradeInventory)
                                }
                            }
                        }
                        "モンスター強化"-> {
                            for(monster in status[event.whoClicked.uniqueId]!!.monsterInventory.contents){
                                if(monster == status[event.whoClicked.uniqueId]!!.beforeMonster){
                                     status[event.whoClicked.uniqueId]!!.monsterInventory.let { it.setItem(it.first(monster),inventoryCommon.getMonsterfromItemStack(item)) }
                                }
                            }
                        }
                    }
                    PlayerCommon.updateCoin(event.whoClicked as Player)
                    PlayerCommon.updateInCoin(event.whoClicked as Player)
                }
            }
        }
    }
    @EventHandler
    fun death(event: EntityDeathEvent){
        for(player in PlayerCommon.getAllPlayer()){
            val monster = EntityCommon.getMonsterEntityFromEntity(event.entity,player)
            if(monster != null){
                status[player.uniqueId]!!.monsters.remove(monster)
                status[player.uniqueId]!!.xp += 1
                PlayerCommon.updateXP(player)
            }
        }
    }
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (cmd.name.equals("TowerDefense", ignoreCase = true)) {
            if (args.isEmpty()) {
                sender.sendMessage(ChatColor.DARK_RED.toString() + "コマンドの引数が足りません!!")
                return false
            }
            val sp = sender as Player
            when (args[0]) {
                "start" -> {
                    if (!game) {
                        game = true
                        manager = Bukkit.getScoreboardManager()!!
                        for(player in Bukkit.getOnlinePlayers()){
                            status[player.uniqueId] = Status()
                            bar.addPlayer(player)
                            board[player.uniqueId] = manager.newScoreboard
                            objective[player.uniqueId] = ScoreboardCommon.setActive(board[player.uniqueId]!!, TD,"NULL",TD)
                            objective[player.uniqueId]!!.displaySlot = DisplaySlot.SIDEBAR
                            health[player.uniqueId] = objective[player.uniqueId]!!.getScore("health[${status[player.uniqueId]!!.health}]")
                            health[player.uniqueId]!!.score = 9
                            coin[player.uniqueId] = objective[player.uniqueId]!!.getScore("coin[${status[player.uniqueId]!!.coin}]")
                            coin[player.uniqueId]!!.score = 8
                            inCoin[player.uniqueId] = objective[player.uniqueId]!!.getScore("inCoin[${status[player.uniqueId]!!.inCoin}]")
                            inCoin[player.uniqueId]!!.score = 7
                            xp[player.uniqueId] = objective[player.uniqueId]!!.getScore("xp[${status[player.uniqueId]!!.xp}]")
                            xp[player.uniqueId]!!.score = 6
                            player.scoreboard = board[player.uniqueId]!!

                        }
                        var mainId = 0
                        var subId = 0
                        mainId = scheduler.scheduleSyncRepeatingTask(this,{

                        },0L,20L)
                        subId = scheduler.scheduleSyncRepeatingTask(this,{
                            inCoinTime-=1
                            bar.progress = inCoinTime/200.0
                            if(inCoinTime == 0){
                                inCoinTime = 200
                                for(player in PlayerCommon.getAllPlayer()){
                                    status[player.uniqueId]!!.coin = status[player.uniqueId]!!.coin + status[player.uniqueId]!!.inCoin
                                    PlayerCommon.updateCoin(player)
                                }
                            }
                            for(player in PlayerCommon.getAllPlayer()) {
                                val towerEntities = status[player.uniqueId]!!.towers
                                for (towerEntity in towerEntities) {
                                    towerEntity.tick += 1
                                    if (towerEntity.tower.speed == towerEntity.tick) {
                                        towerEntity.tick = 0
                                        Bukkit.getWorld("world")!!.playSound(towerEntity.entity.location, towerEntity.tower.attackSound, 1F, 1F)
                                        val entityList = towerEntity.tower.getAttackEntity(towerEntity.entity)
                                        if(entityList.isNotEmpty()) {
                                            for (entity in entityList) {
                                                val monster = EntityCommon.getMonsterEntityFromEntity(entity,player)
                                                if(monster != null) {
                                                    var damage = towerEntity.tower.damage.toDouble()
                                                    for (function in towerEntity.tower.attackModules) {
                                                        damage = function.invoke(AttackEvent(damage))
                                                    }
                                                    for (function in monster.monster.modules) {
                                                        damage = function.invoke(DamageEvent(damage))
                                                    }
                                                    entity.damage(damage)
                                                    towerEntity.entity.velocity = EntityCommon.genVec(towerEntity.entity.location, entity.location)
                                                }
                                            }
                                        }
                                    }
                                }
                                val monsterEntities = status[player.uniqueId]!!.monsters
                                if(monsterEntities.isNotEmpty()) {
                                    for (monsterEntity in monsterEntities) {
                                        monsterEntity.remoteness += monsterEntity.monster.speed
                                    }
                                }
                                val goalLoc = status[player.uniqueId]!!.goalGate
                                val goalEntities = goalLoc.world!!.getNearbyEntities(goalLoc, 2.0, 5.0, 2.0)
                                if (goalEntities.isNotEmpty()) {
                                    for (goalEntity in goalEntities) {
                                        val goalMonsterEntity = EntityCommon.getMonsterEntityFromEntity(goalEntity, player)
                                        if (goalMonsterEntity != null) {
                                            status[player.uniqueId]!!.health -= 1
                                            status[goalMonsterEntity.player.uniqueId]!!.health += 1
                                            status[player.uniqueId]!!.monsters.remove(goalMonsterEntity)
                                            PlayerCommon.updateHealth(player)
                                            goalEntity.remove()
                                            if(status[player.uniqueId]!!.health<=0){
                                                
                                            }
                                        }
                                    }
                                }
                            }
                        },0L,1L)
                    }
                }
                "setGate"->{
                    val player = Bukkit.getPlayer(args[1])!!
                    status[player.uniqueId]!!.goalGate = player.location
                }
                "setStart"->{
                    val player = Bukkit.getPlayer(args[1])!!
                    status[player.uniqueId]!!.spawnMonsterLocation = player.location
                }
            }
        }
        return false
    }
    companion object{
        const val TD = "TowerDefense"
        var game = false
        val scheduler: BukkitScheduler = Bukkit.getScheduler()
        var bar = Bukkit.getServer().createBossBar(ChatColor.AQUA.toString() + "INCOIN", BarColor.YELLOW, BarStyle.SEGMENTED_20, BarFlag.DARKEN_SKY)
        lateinit var manager: ScoreboardManager //スコアボード管理システム変数作成
        val board = hashMapOf<UUID, Scoreboard>()
        val objective = hashMapOf<UUID,Objective>()
        val health = hashMapOf<UUID,Score>()
        val coin = hashMapOf<UUID,Score>()
        val inCoin = hashMapOf<UUID,Score>()
        val xp = hashMapOf<UUID,Score>()
        val status = hashMapOf<UUID,Status>()
        var inCoinTime = 200
        var towerInventory = Bukkit.createInventory(null, InventoryType.CHEST,"タワー作成")
        var monsterinventory = Bukkit.createInventory(null, InventoryType.CHEST,"モンスター召喚")
        var towers = mutableListOf<Tower>()
        var monsters = mutableListOf<Monster>()
        fun registerTower(tower: Tower){
            towerInventory.addItem(tower)
            towers.add(tower)
        }
        fun registerMonster(monster: Monster){
            monsterinventory.addItem(monster)
            monsters.add(monster)
        }
    }
}