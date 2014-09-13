package me.mgone.bossbarapi;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;



/**
 * Thanks for chasechocolate and BigTeddy98 for the tutorial
 * it is based on the code by them and some other tutorial
 * https://forums.bukkit.org/threads/util-set-a-players-boss-bar-nms.245073/
 * https://forums.bukkit.org/threads/tutorial-utilizing-the-boss-health-bar.158018/
 * @author Marzouki Ghofrane , mgone CraftZone.fr
 */


public class BossbarAPI extends JavaPlugin implements Listener {
	
	
	public static Plugin plugin;
	public static Map<Player, String> playerdragonbartask = new HashMap<Player, String>();
	public static Map<Player, Float> healthdragonbartask = new HashMap<Player, Float>();
	public static Map<Player, Integer> cooldownsdragonbar= new HashMap<Player, Integer>();
	public static Map<Player, Integer> starttimerdragonbar= new HashMap<Player, Integer>();
	
	public static Map<Player, String> playerwitherbartask = new HashMap<Player, String>();
	public static Map<Player, Float> healthwitherbartask = new HashMap<Player, Float>();
	public static Map<Player, Integer> cooldownswitherbar= new HashMap<Player, Integer>();
	public static Map<Player, Integer> starttimerwitherbar= new HashMap<Player, Integer>();
	
	  public void onEnable()
	  {   
		  
		  getServer().getPluginManager().registerEvents(this, this);
		  getConfig().options().copyDefaults(true);
		  saveDefaultConfig();
		  reloadConfig();
		  plugin = this;
		  Bukkit.getConsoleSender().sendMessage("§e§lBossbarAPI Enabled...");
		  DragonBarTask();
	  }
		  
		  
		  
	  public void onDisable()
	  {
	    Bukkit.getConsoleSender().sendMessage("§3§lBossbarAPI Disabled...");
	  }
	
	
	  public static Plugin getInstance(){
		    return plugin;
		}
	  
	  
	  
	  public void reloadConfig()
	  {
	    super.reloadConfig();
	    
	  }
	  
	  
      public void DragonBarTask() {
         
         new BukkitRunnable() {
                 
                  @SuppressWarnings("deprecation")
					@Override
                  public void run() {
                          for(Player p : plugin.getServer().getOnlinePlayers()){
                       
                        	if(!cooldownsdragonbar.containsKey(p))	{ 
                        	  
                         if(playerdragonbartask.containsKey(p) && !healthdragonbartask.containsKey(p))	{ setBarDragon(p, playerdragonbartask.get(p)); }
                         else if(playerdragonbartask.containsKey(p) && healthdragonbartask.containsKey(p))	{ setBarDragon(p, playerdragonbartask.get(p), healthdragonbartask.get(p)); }
                         
                        	                 }
                        	
                        	if(!cooldownswitherbar.containsKey(p))	{ 
                          	  
                         if(playerwitherbartask.containsKey(p) && !healthwitherbartask.containsKey(p))	{ setBarWither(p, playerwitherbartask.get(p)); }
                         else if(playerwitherbartask.containsKey(p) && healthwitherbartask.containsKey(p))	{ setBarWitherHealth(p, playerwitherbartask.get(p), healthwitherbartask.get(p)); }
                         
                        	                 }
                            
                          }
                  }
          }.runTaskTimer(this, 0, 20); 
          
          
          
          
          new BukkitRunnable() {
              
              @SuppressWarnings("deprecation")
				@Override
              public void run() {
                      for(Player p : plugin.getServer().getOnlinePlayers()){
                    	  
                     if(cooldownsdragonbar.containsKey(p))	{ 
                    	 
                    	if(cooldownsdragonbar.get(p) > 0) 
                    	{ cooldownsdragonbar.put(p,cooldownsdragonbar.get(p)-1); setBarDragonTimer(p, playerdragonbartask.get(p), cooldownsdragonbar.get(p)); }
                    	else removeBarDragon(p);

                     }
                     
                     
                     if(cooldownswitherbar.containsKey(p))	{ 
                    	 
                    	if(cooldownswitherbar.get(p) > 0) 
                    	{ cooldownswitherbar.put(p,cooldownswitherbar.get(p)-1); setBarWitherTimer(p, playerwitherbartask.get(p), cooldownswitherbar.get(p)); }
                    	else removeBarWither(p);

                     }
                        
                      }
              }
      }.runTaskTimer(this, 0, 20); 
          
          
  }
	  
      
      //dragon
	  
	  
	  
		public static void setBarDragon(Player p, String text) {
			playerdragonbartask.put(p, text);
			FDragon.setBossBartext(p, text);
		}
	
	  
		public static void setBarDragon(Player p, String text, float health) {
			if(health<0 || health >100) { health = 100; text = "health must be between 0 and 100 it's a %";}
			playerdragonbartask.put(p, text);
			healthdragonbartask.put(p, (health/100)*200);
			FDragon.setBossBar(p, text, health);
		}
		
		public static void setBarDragonTimer(Player p, String text, int timer) {
			playerdragonbartask.put(p, text);
			cooldownsdragonbar.put(p, timer);
			if(!starttimerdragonbar.containsKey(p)) starttimerdragonbar.put(p, timer);
			int unite = Math.round(200/starttimerdragonbar.get(p));
			FDragon.setBossBar(p, text, unite*timer);
		
			p.sendMessage(String.valueOf(unite*timer));
		}
		
		
		public static void removeBarDragon(Player p) {	
			playerdragonbartask.remove(p);
			healthdragonbartask.remove(p);
			cooldownsdragonbar.remove(p);
			starttimerdragonbar.remove(p);
			FDragon.removeBossBar(p);
		}
		
		public static boolean hasBarDragon(Player p) {	
			return playerdragonbartask.get(p) != null;	
		}
		
		
		
		
		//wither
		
		public static void setBarWither(Player p, String text) {
			playerwitherbartask.put(p, text);
			FWither.setBossBartext(p, text);
		}
	
	  
		public static void setBarWitherHealth(Player p, String text, float health) {
			if(health<0 || health >100) { health = 100; text = "health must be between 0 and 100 it's a %";}
			playerwitherbartask.put(p, text);
			healthwitherbartask.put(p, (health/100)*300);
			FWither.setBossBar(p, text, health);
		}
		
		public static void setBarWitherTimer(Player p, String text, int timer) {
			playerwitherbartask.put(p, text);
			cooldownswitherbar.put(p, timer);
			if(!starttimerwitherbar.containsKey(p)) starttimerwitherbar.put(p, timer);
			int unite = Math.round(300/starttimerwitherbar.get(p));
			FWither.setBossBar(p, text, unite*timer);
		
			p.sendMessage(String.valueOf(unite*timer));
		}
		
		
		public static void removeBarWither(Player p) {	
			playerwitherbartask.remove(p);
			healthwitherbartask.remove(p);
			cooldownswitherbar.remove(p);
			starttimerwitherbar.remove(p);
			FWither.removeBossBar(p);
		}
		
		public static boolean hasBarWither(Player p) {	
			return playerwitherbartask.get(p) != null;	
		}
		
		
		
		
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
		  {
			
			
		    if ((sender instanceof Player))
		    {  Player p = (Player)sender;
		    
		    if (cmd.getName().equalsIgnoreCase("bossbar"))
		    {
		    	setBarWither(p, "§6§lhello §aworld");
		    	setBarDragon(p, "§6§lhello §aworld");
		    }
		    
		    if (cmd.getName().equalsIgnoreCase("bossbar2"))
		    {
		    	setBarWitherHealth(p, "§6§lhello §aworld", 0);
		    }
		    
		    if (cmd.getName().equalsIgnoreCase("bossbar3"))
		    {
		    	setBarWitherTimer(p, "§6§lhello §aworld", 100);
		    	setBarDragonTimer(p, "§6§lhello §aworld", 100);
		    }
		    
		    
		    if (cmd.getName().equalsIgnoreCase("removebossbar"))
		    {
		    	removeBarWither(p);
		    	removeBarDragon(p);
		    }
		    
		    
		    
		    
		    }
			
			
		    return false;
			
			
		  }
		   
		 
		    


}