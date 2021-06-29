package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.LinkPlayer;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {

	public void listAllPlayers(Map<Integer, Player> mapPlayer) {
		String sql = "SELECT * FROM Players";
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if (!mapPlayer.containsKey("PlayerID")) {
					Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
					mapPlayer.put(player.getPlayerID(), player);
				}
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Action> listAllActions() {
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"), res.getInt("MatchID"), res.getInt("TeamID"),
						res.getInt("Starts"), res.getInt("Goals"), res.getInt("TimePlayed"), res.getInt("RedCards"),
						res.getInt("YellowCards"), res.getInt("TotalSuccessfulPassesAll"),
						res.getInt("totalUnsuccessfulPassesAll"), res.getInt("Assists"),
						res.getInt("TotalFoulsConceded"), res.getInt("Offsides"));

				result.add(action);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Player> listPlayerMajorAvg(Map<Integer, Player> mapPlayer, Double x) {
		String sql = "SELECT p.PlayerID, p.Name " + "FROM players p, actions a " + "WHERE p.PlayerID = a.PlayerID "
				+ "GROUP BY p.PlayerID " + "HAVING  AVG (a.Goals) > ? ";
		List<Player> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, x);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<LinkPlayer> getPlayerLink(Map<Integer,Player> idMap){
		String sql = "SELECT A1.PlayerID AS p1, A2.PlayerID AS p2, (SUM(A1.TimePlayed) - SUM(A2.TimePlayed)) AS peso " + 
				"FROM 	Actions A1, Actions A2 " + 
				"WHERE A1.TeamID != A2.TeamID " + 
				"	AND A1.MatchID = A2.MatchID " + 
				"	AND A1.starts = 1 AND A2.starts = 1 " + 
				"	AND A1.PlayerID > A2.PlayerID " + 
				"GROUP BY A1.PlayerID,A2.PlayerID";
		List<LinkPlayer> result = new ArrayList<LinkPlayer>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(idMap.containsKey(res.getInt("p1")) && idMap.containsKey(res.getInt("p2"))) {
					result.add(new LinkPlayer(idMap.get(res.getInt("p1")), idMap.get(res.getInt("p2")), res.getInt("peso")));
				}
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
}
