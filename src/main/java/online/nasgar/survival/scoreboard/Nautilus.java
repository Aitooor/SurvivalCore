package online.nasgar.survival.scoreboard;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import online.nasgar.survival.scoreboard.adapter.NautilusAdapter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Getter @Setter
public class Nautilus {
	
	private NautilusAdapter adapter;

	private Scoreboard scoreboard;
	private int lastSentCount;

	public Nautilus(NautilusAdapter adapter){
		this.adapter = adapter;
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

		this.lastSentCount = -1;
	}
	
	public void update(Player player) {
		String title = this.adapter.getTitle(player);
		List<String> list = this.adapter.getContent(player);

		Team team = null;

		for (int i = 0; i < list.size(); i++) {
			team = this.getOrCreateTeam(ChatColor.stripColor(StringUtils.left(title, 14)) + i, i);

			String text = list.get(list.size() - i - 1);

			String prefix, suffix = null;

			if (text.length() > 16){
				int subString = text.charAt(16) == ChatColor.COLOR_CHAR ? 16 : 17;

				prefix = text.substring(0, subString);
				suffix = ChatColor.getLastColors(prefix) + text.substring(subString);
			} else {
				prefix = text;
			}

			team.setPrefix(prefix);
			team.setSuffix(suffix);

			this.getObjective().setDisplayName(title);
			this.getObjective().getScore(this.getNameForIndex(i)).setScore(i + 1);
		}

		if (this.lastSentCount != -1) {
			for (int i = 0; i < this.lastSentCount - list.size(); i++) {
				this.scoreboard.resetScores(this.getNameForIndex(i));

				if (team != null) {
					team.unregister();
				}
			}
		}

		this.lastSentCount = list.size();

		if (player.getScoreboard() != this.scoreboard){
			player.setScoreboard(this.scoreboard);
		}
	}

	public void clear() {
		this.scoreboard.getTeams().forEach(Team::unregister);
		this.scoreboard.getObjectives().forEach(Objective::unregister);
	}

	private Team getOrCreateTeam(String teamName, int i) {
		Team team = this.scoreboard.getTeam(teamName);
		
		if (team == null) {
			team = this.scoreboard.registerNewTeam(teamName);

			if (!team.getEntries().contains(this.getNameForIndex(i))) {
				team.addEntry(this.getNameForIndex(i));
			}
		}
		
		return team;
	}
	
	private Objective getObjective() {
		Objective objective = this.scoreboard.getObjective("Vnco");
		
		if (objective == null) {
			objective = this.scoreboard.registerNewObjective("Vnco", "dummy");
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		}

		return objective;
	}

	private String getNameForIndex(int index) {
		return ChatColor.values()[index].toString() + ChatColor.RESET;
	}

}