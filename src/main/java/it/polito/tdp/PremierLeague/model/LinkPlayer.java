package it.polito.tdp.PremierLeague.model;

public class LinkPlayer {
	
	private Player p1;
	private Player p2;
	private Integer peso;
	
	
	public LinkPlayer(Player p1, Player p2, int i) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.peso = i;
	}


	public Player getP1() {
		return p1;
	}


	public void setP1(Player p1) {
		this.p1 = p1;
	}


	public Player getP2() {
		return p2;
	}


	public void setP2(Player p2) {
		this.p2 = p2;
	}


	public Integer getPeso() {
		return peso;
	}


	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	
	
	

}
