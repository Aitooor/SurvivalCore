package online.nasgar.survival.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import online.nasgar.survival.Survival;
import org.bukkit.entity.Player;


public final class BungeeUtil {

	public static void sendMessage(Player source, String target, String message) {
		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Message");
			out.writeUTF(target);
			out.writeUTF(message);

			source.sendPluginMessage(Survival.getInstance(), "BungeeCord", out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendToServer(Player player, String server) {
		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF(server);

			player.sendPluginMessage(Survival.getInstance(), "BungeeCord", out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static CharSequence getServer() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		ByteArrayDataInput in = ByteStreams.newDataInput(out.toByteArray());
		out.writeUTF("GetServer");
		String servername = in.readUTF();

		return servername;
	}

	public static String getPlayerCount(Player player, String server) {
		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("PlayerCount");
			out.writeUTF(server);

			player.sendPluginMessage(Survival.getInstance(), "BungeeCord", out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return server;
	}

}