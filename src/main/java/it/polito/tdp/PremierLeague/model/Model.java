package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	private SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> mapPlayer;
	private PremierLeagueDAO dao;
	
	public Model() {
		dao = new PremierLeagueDAO();
		mapPlayer = new HashMap<>();
		dao.listAllPlayers(mapPlayer);
	}
	
	public void creaGrafo(Integer avgGoal) {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungo i vertici
		Graphs.addAllVertices(this.grafo, dao.listPlayerMajorAvg(mapPlayer, avgGoal));
		
		//Aggiungo gli archi
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
}
