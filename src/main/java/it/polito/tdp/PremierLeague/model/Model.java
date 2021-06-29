package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
	
	public void creaGrafo(Double x) {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungo i vertici
		Graphs.addAllVertices(this.grafo, dao.listPlayerMajorAvg(mapPlayer, x));
		
		//Aggiungo gli archi
		for(LinkPlayer l: dao.getPlayerLink(mapPlayer)) {
			if(grafo.containsVertex(l.getP1()) && grafo.containsVertex(l.getP2())) {
				if(l.getPeso() < 0) {
					//arco da p2 a p1
					Graphs.addEdgeWithVertices(grafo, l.getP2(), l.getP1(), ((double) -1)*l.getPeso());
				} else if(l.getPeso() > 0){
					//arco da p1 a p2
					Graphs.addEdgeWithVertices(grafo, l.getP1(), l.getP2(), l.getPeso());
				}
			}
		}			
	}
	
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}

	public TopPlayer getTopPlayer() {
		if(grafo == null)
			return null;
		
		Player best = null;
		Integer maxDegree = Integer.MIN_VALUE;
		
		//controllo tutti i vertici
		for(Player p: grafo.vertexSet()) {
			if(grafo.outDegreeOf(p) > maxDegree) {
				//trovo quello con più archi uscenti
				maxDegree = grafo.outDegreeOf(p);
				best = p;
			}
		}
		
		TopPlayer topPlayer = new TopPlayer();
		topPlayer.setPlayer(best);
		
		List<Opponents> opponents = new ArrayList<>();
		
		//controllo tutti gli archi del vertice migliore
		for(DefaultWeightedEdge edge : grafo.outgoingEdgesOf(topPlayer.getPlayer())) {
			
			//inserisco tutti gli altri vertci collegati
			opponents.add(new Opponents(grafo.getEdgeTarget(edge), (int) grafo.getEdgeWeight(edge)));
		}
		//ordino la lista
		Collections.sort(opponents);
		topPlayer.setOpponents(opponents);
		return topPlayer;
	
	}
	
	
	
	
	
	
	
	
	
}
