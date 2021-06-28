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
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Player> listPlayerMajorAvg(Map<Integer, Player> mapPlayer, Integer x) {
		String sql ="SELECT p.PlayerID, p.Name "
				+ "FROM players p, actions a "
				+ "WHERE p.PlayerID = a.PlayerID "
				+ "GROUP BY p.PlayerID "
				+ "HAVING  AVG (a.Goals) > ? ";
		List<Player> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, x);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Player player = new Player(res.getInt("PlayerID"),res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
