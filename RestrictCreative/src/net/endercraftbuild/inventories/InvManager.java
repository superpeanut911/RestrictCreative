package net.endercraftbuild.inventories;

import java.io.*;

import net.endercraftbuild.Main;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class InvManager implements Listener {

	private Main plugin;

	public InvManager(Main instance) {
		plugin = instance;
	}
	@EventHandler//Change inv on gm change
	public void Gm(PlayerGameModeChangeEvent event) throws IllegalArgumentException, IOException {

		Player player = event.getPlayer();

		if(event.getNewGameMode() == GameMode.CREATIVE) {
			saveSurvivalInv(player);
			player.getInventory().clear();
			loadCreativeInv(player);

		} else {
			saveCreativeInv(player);
			player.getInventory().clear();
			loadSurvivalInv(player);
		}

	}



	@EventHandler//Make files
	public void PlayerJoin(PlayerJoinEvent event) {

		File PlayerFileS;
		Player player = event.getPlayer();
		String Inventories = "Inventories";
		boolean success = (new File("plugins/RestrictCreative/" + Inventories)).mkdir();
		if(success) {
			PlayerFileS = new File(plugin.getDataFolder(), "/Inventories/" + player.getName() + "-survival_.txt");
			if(!PlayerFileS.exists()) {
				try {
					PlayerFileS.createNewFile();
					saveSurvivalInv(player);
				}
				catch(IOException exx) {
					exx.printStackTrace();
				}
				File PlayerFileC;
				PlayerFileC = new File(plugin.getDataFolder(), "/" + "Inventories" + "/" + player.getName() + "-creative_.txt");
				if(!PlayerFileC.exists()) {
					try {
						PlayerFileC.createNewFile();
						saveCreativeInv(player);
					}
					catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		}
	}

	@EventHandler//Save on leave
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE){
			try {
				saveCreativeInv(player);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			try {
				saveSurvivalInv(player);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	@EventHandler
	public void kick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE){
			try {
				saveCreativeInv(player);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			try {
				saveSurvivalInv(player);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	private void saveCreativeInv(Player player1) throws IOException {
		String dataC = Serilization.toBase64(player1.getInventory());
		FileWriter fstream;
		fstream = new FileWriter(plugin.getDataFolder() + "/" + "Inventories/" + player1.getName() + "-creative_.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(dataC);
		//Close the output stream
		out.close();

	}


	private void saveSurvivalInv(Player player2) throws IOException {
		String dataS = Serilization.toBase64(player2.getInventory());
		FileWriter fstream;
		fstream = new FileWriter(plugin.getDataFolder() + "/" + "Inventories/" + player2.getName() + "-survival_.txt");

		BufferedWriter out = new BufferedWriter(fstream);
		out.write(dataS);
		//Close the output stream
		out.close();

	}

	private void loadCreativeInv(Player player3) throws IllegalArgumentException, IOException, FileNotFoundException {
		File PlayerFileC;
		PlayerFileC = new File(plugin.getDataFolder(), "/Inventories/" + player3.getName() + "-creative_.txt");
		if(PlayerFileC.exists()) {

			FileReader fileReader = new FileReader(PlayerFileC);
			StringBuffer stringBuffer = new StringBuffer();
			int numCharsRead;
			char[] charArray = new char[2048];
			while ((numCharsRead = fileReader.read(charArray)) > 0) {
				stringBuffer.append(charArray, 0, numCharsRead);
				Inventory i = Serilization.fromBase64(stringBuffer.toString());
				player3.getInventory().setContents(i.getContents());
			}
		}
	}		

	private void loadSurvivalInv(Player player4) throws IOException, FileNotFoundException { {
		File PlayerFileC;
		PlayerFileC = new File(plugin.getDataFolder(), "/Inventories/" + player4.getName() + "-survival_.txt");
		if(PlayerFileC.exists()) {
			FileReader fileReader = new FileReader(PlayerFileC);
			StringBuffer stringBuffer = new StringBuffer();
			int numCharsRead;
			char[] charArray = new char[2048];
			while ((numCharsRead = fileReader.read(charArray)) > 0) {
				stringBuffer.append(charArray, 0, numCharsRead);
				Inventory i = Serilization.fromBase64(stringBuffer.toString());
				player4.getInventory().setContents(i.getContents());
			}




			{
			}


			{
			}
			{
			}


			{
			}
			{
			}

		}
		{
		}
	}
	}
}



