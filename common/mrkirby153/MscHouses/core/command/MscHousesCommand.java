package mrkirby153.MscHouses.core.command;

import java.util.ArrayList;
import java.util.List;

import mrkirby153.MscHouses.core.MscHouses;
import mrkirby153.MscHouses.core.Version;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatMessageComponent;

public class MscHousesCommand extends CommandBase {

	@Override
	public int compareTo(Object arg0) {
		return this.getCommandName().compareTo(((ICommand) arg0).getCommandName());
	}

	@Override
	public String getCommandName() {
		return "MscHouses";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + this.getCommandName() + " help";
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getCommandAliases() {
		List aliasses = new ArrayList<>();
		aliasses.add("mschouses");
		return aliasses;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] arguments) {

		if (arguments.length <= 0)
			throw new WrongUsageException("Type '" + this.getCommandUsage(sender) + "' for help.");

		if (arguments[0].matches("version")) {
			commandVersion(sender, arguments);
			return;
		} else if (arguments[0].matches("help")) {
			sender.sendChatToPlayer(ChatMessageComponent.createFromText("Format: '" + this.getCommandName() + " <command> <arguments>'"));
			sender.sendChatToPlayer(ChatMessageComponent.createFromText("Available commands:"));
			sender.sendChatToPlayer(ChatMessageComponent.createFromText("- version : Version information."));
			sender.sendChatToPlayer(ChatMessageComponent.createFromText("- create: Create a new house"));
			return;
		} else if(arguments[0].matches("create")){
			if(arguments.length < 10){
				//x, y, z, widht, height, legnth, name, floorDepth, specal, specalid
				if(arguments.length == 2){
					if(arguments[1].matches("help")){
						sender.sendChatToPlayer(ChatMessageComponent.createFromText("Format: '"+this.getCommandName()+ " create <x> <y> <z> <name> <floorDepth>"));
						sender.sendChatToPlayer(ChatMessageComponent.createFromText("Arguments:"));
						sender.sendChatToPlayer(ChatMessageComponent.createFromText("- x: The starting x coordinate for the structure"));
						sender.sendChatToPlayer(ChatMessageComponent.createFromText("- y: The starting x coordinate for the structure"));
						sender.sendChatToPlayer(ChatMessageComponent.createFromText("- z: The starting x coordinate for the structure"));
						sender.sendChatToPlayer(ChatMessageComponent.createFromText("- name: The name of the structure."));
						sender.sendChatToPlayer(ChatMessageComponent.createFromText("- floorDepth: How deep the floor goes"));
						sender.sendChatToPlayer(ChatMessageComponent.createFromText("Example Usage: /"+this.getCommandName()+" create 124 40 -34 foo 3"));
						return;
					}else{
						throw new WrongUsageException("Type '"+this.getCommandName()+ " create help' for help.");
					}
				}else if(arguments.length ==1){
					throw new WrongUsageException("Type '"+this.getCommandName()+" create help' for help");
				}
			}else{
				// sender.sendChatToPlayer(ChatMessageComponent.createFromText("[DEBUG] X: "+arguments[1]+" Y: "+arguments[2]+" Z: "+arguments[3]+" Name: "+arguments[4] + " FloorDepth: "+arguments[5]));
				// Begin parsing file
				if(!isNumeric(arguments[1])){
					sender.sendChatToPlayer(ChatMessageComponent.createFromText(MscHouses.COLOR_CODE+"eX Coordinate is not a number!"));
					return;
				}
				if(!isNumeric(arguments[2])){
					sender.sendChatToPlayer(ChatMessageComponent.createFromText(MscHouses.COLOR_CODE+"eY Coordinate is not a number!"));
					return;
				}
				if(!isNumeric(arguments[3])){
					sender.sendChatToPlayer(ChatMessageComponent.createFromText(MscHouses.COLOR_CODE+"eZ Coordinate is not a number!"));
					return;
				}
				if(!isNumeric(arguments[4])){
					sender.sendChatToPlayer(ChatMessageComponent.createFromText(MscHouses.COLOR_CODE+"eFloor Depth is not a number!"));
					return;
				}
				if(!isNumeric(arguments[5])){
					sender.sendChatToPlayer(ChatMessageComponent.createFromText(MscHouses.COLOR_CODE+"eLegnth is not a number!"));
					return;
				}
				if(!isNumeric(arguments[6])){
					sender.sendChatToPlayer(ChatMessageComponent.createFromText(MscHouses.COLOR_CODE+"eHeight is not a number!"));
					return;
				}
				if(!isNumeric(arguments[7])){
					sender.sendChatToPlayer(ChatMessageComponent.createFromText(MscHouses.COLOR_CODE+"eWidth is not a number!"));
					return;
				}
				if(!isNumeric(arguments[10])){
					sender.sendChatToPlayer(ChatMessageComponent.createFromText(MscHouses.COLOR_CODE+"eSpecal ID is not a number!"));
					return;
				}
				int x = Integer.parseInt(arguments[1]);
				int y = Integer.parseInt(arguments[2]);
				int z = Integer.parseInt(arguments[3]);
				int floorDepth = Integer.parseInt(arguments[4]);
				int width = Integer.parseInt(arguments[5]);
				int height = Integer.parseInt(arguments[6]);
				int legnth = Integer.parseInt(arguments[7]);
				String name = arguments[8];
				boolean specal = (arguments[9].matches("true"))? true : false;
				int specalId = Integer.parseInt(arguments[10]);
				System.out.println(x+":"+y+":"+z+":"+floorDepth+":"+width+":"+height+":"+legnth+":"+name+":"+specal+":"+specalId);
			}
		}
	}

	private void commandVersion(ICommandSender sender, String[] arguments) {
		String colour = Version.isOutdated() ? "\u00A7c" : "\u00A7a";

		sender.sendChatToPlayer(ChatMessageComponent.createFromText(String.format(colour + "MscHouses %s for Minecraft %s (Latest: %s).", Version.getVersion(),
				MscHouses.getMCVersion(), Version.getReccomendedVersion())));
		if (Version.isOutdated()) {
			for (String updateLine : Version.getChangelog()) {
				sender.sendChatToPlayer(ChatMessageComponent.createFromText("\u00A79" + updateLine));
			}
		}
	}
	
	private static boolean isNumeric(String str){
		try{
			double d = Double.parseDouble(str);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

}