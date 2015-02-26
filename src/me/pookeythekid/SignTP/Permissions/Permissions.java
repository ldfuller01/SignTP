package me.pookeythekid.SignTP.Permissions;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

public class Permissions {
	
	public Permission allPerms = new Permission("signtp.*");
	
	public Permission mainCmd = new Permission("signtp.main");
	
	public Permission createSign = new Permission("signtp.create");
	
	public Permission useSign = new Permission("signtp.use");
	
	public Permission setWarp = new Permission("signtp.setwarp");
	
	public Permission warpCmd = new Permission("signtp.warp");
	
	public Permission listWarps = new Permission("signtp.list");
	
	public Permission reloadCmd = new Permission("signtp.reload");
	
	public Permission delWarp = new Permission("signtp.delwarp");
	
	public Permission warpOther = new Permission("signtp.warp.other");
	
	
	public void registerPerms(PluginManager pm) {
		
		pm.addPermission(allPerms);
		pm.getPermission("signtp.*").setDefault(PermissionDefault.OP);
		
		pm.addPermission(mainCmd);
		
		pm.getPermission("signtp.main").setDefault(PermissionDefault.OP);
		
		pm.getPermission("signtp.main").addParent(allPerms, true);
		pm.getPermission("signtp.main").addParent(createSign, true);
		pm.getPermission("signtp.main").addParent(useSign, true);
		pm.getPermission("signtp.main").addParent(setWarp, true);
		pm.getPermission("signtp.main").addParent(listWarps, true);
		pm.getPermission("signtp.main").addParent(warpCmd, true);
		pm.getPermission("signtp.main").addParent(reloadCmd, true);
		pm.getPermission("signtp.main").addParent(delWarp, true);
		pm.getPermission("signtp.main").addParent(warpOther, true);
		
		pm.addPermission(createSign);
		pm.getPermission("signtp.create").setDefault(PermissionDefault.OP);
		pm.getPermission("signtp.create").addParent(allPerms, true);
		
		pm.addPermission(useSign);
		pm.getPermission("signtp.use").setDefault(PermissionDefault.OP);
		pm.getPermission("signtp.use").addParent(allPerms, true);
		
		pm.addPermission(setWarp);
		pm.getPermission("signtp.setwarp").setDefault(PermissionDefault.OP);
		pm.getPermission("signtp.setwarp").addParent(allPerms, true);
		
		pm.addPermission(warpCmd);
		pm.getPermission("signtp.warp").setDefault(PermissionDefault.OP);
		pm.getPermission("signtp.warp").addParent(allPerms, true);
		
		pm.addPermission(listWarps);
		pm.getPermission("signtp.list").setDefault(PermissionDefault.OP);
		pm.getPermission("signtp.list").addParent(allPerms, true);
		
		pm.addPermission(reloadCmd);
		pm.getPermission("signtp.warp").setDefault(PermissionDefault.OP);
		pm.getPermission("signtp.warp").addParent(allPerms, true);
		
		pm.addPermission(delWarp);
		pm.getPermission("signtp.delwarp").setDefault(PermissionDefault.OP);
		pm.getPermission("signtp.delwarp").addParent(allPerms, true);
		
		pm.addPermission(warpOther);
		pm.getPermission("signtp.warp.other").setDefault(PermissionDefault.OP);
		pm.getPermission("signtp.warp.other").addParent(allPerms, true);
		
	}
	
	public void removePerms(PluginManager pm) {
		
		pm.removePermission(allPerms);
		
		pm.removePermission(mainCmd);
		
		pm.removePermission(createSign);
		
		pm.removePermission(useSign);
		
		pm.removePermission(setWarp);
		
		pm.removePermission(warpCmd);
		
		pm.removePermission(listWarps);
		
		pm.removePermission(reloadCmd);
		
		pm.removePermission(delWarp);
		
		pm.removePermission(warpOther);
		
	}

}
