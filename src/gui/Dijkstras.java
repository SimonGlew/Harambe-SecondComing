package gui;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import javax.swing.Timer;

import core.Location;
import tile.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dijkstras implements ActionListener{
	ClientController controller;
	Tile start;
	Tile end;
	Location location;
	Node[][] nodes;
	Stack<Tile> path;
	Timer timer;
	int count;

	public Dijkstras(ClientController controller, Tile start, Tile end, Location location){
		this.controller = controller;
		this.start = start;
		this.end = end;
		this.location = location;
		this.path = null;
		this.count = 1;
		timer = new Timer(1000, this);

		setup();
	}

	private void setup() {
		int length = location.getTiles().length;
		nodes = new Node[length][length];

		//Create nodes for tiles
		for(int y = 0; y < length; y++){
			for(int x = 0; x < length; x++){
				Tile t = location.getTiles()[x][y];
			
					nodes[x][y] = new Node(false, x, y, t);

				
			}
		}
		//Get available neighbours for each node
		for(int y = 0; y < length; y++){
			for(int x = 0; x < length; x++){
				Node n = nodes[x][y];
				try{ n.neighbours.add(nodes[x][y-1]); } catch(Exception e){}
				try{ n.neighbours.add(nodes[x][y+1]); } catch(Exception e){}
				try{ n.neighbours.add(nodes[x+1][y]); } catch(Exception e){}
				try{ n.neighbours.add(nodes[x-1][y]); } catch(Exception e){}
			}
		}
	}

	public void createPath(){

		Node start = nodes[this.start.getPos().getX()][this.start.getPos().getY()];
		Node end = nodes[this.end.getPos().getX()][this.end.getPos().getY()];
		Node found = null;
		//System.out.println("\nStart:" + start.x + "." + start.y+ "." + start.neighbours.size() + "\t End: " + end.x + "." + end.y+ "." + end.neighbours.size());

		Queue<SearchNode> fringe = new PriorityQueue<SearchNode>(nodeSorter);
		fringe.offer(new SearchNode(0, start, null));

		//Checks every surrounding node and updates if the path to the node is shorter than current path
		while(!fringe.isEmpty()){

			SearchNode searchNode = fringe.poll();

			if(!searchNode.node.visited){
				searchNode.node.visited = true;
				searchNode.node.pathFrom = searchNode.from;
				if(searchNode.node.equals(end)){
					found = searchNode.node;
					break;
				}
				for(Node n: searchNode.node.neighbours){
					if(!n.visited && n.t.getGameObject() == null){
						int cost = searchNode.costToHere + 1;
						fringe.offer(new SearchNode(cost, n, searchNode.node));
					}
				}
			}
		}

		//Create path for nodes using stack
		Stack<Tile> path = new Stack<Tile>();
		while(found != null){
			path.push(found.t);
			found = found.pathFrom;
		}
		
		if(!path.isEmpty()) path.pop();
		
		this.path = path;
		timer.start();
	}

	/**
	 * Comparator for queue in dijkstras, bases priority on path length
	 */
	private Comparator<SearchNode> nodeSorter = new Comparator<SearchNode>() {
		@Override
		public int compare(SearchNode o1, SearchNode o2) {
			if(o1.costToHere > o2.costToHere) return 1;
			else if(o1.costToHere == o2.costToHere) return 0;
			else return -1;
		}
	};

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(path.isEmpty()){
			timer.stop();
		}else{
			System.out.println(count + ")" + path.peek().getPos());
			controller.moveToPos(path.pop());
			count++;
		}
	}
}

/**
 * SearchNode for dijkstras algorithm for cluedo
 *
 */
class SearchNode {
	public int costToHere;
	public Node node;
	public Node from;

	public SearchNode(int c, Node n, Node f){
		costToHere = c;
		node = n;
		from = f;
	}
}

/**
 * Node for dijkstras algorithm, used in cluedo
 *
 */
class Node {
	public boolean visited;
	public int x;
	public int y;
	public HashSet<Node> neighbours;
	public Node pathFrom;
	public Tile t;

	public Node(boolean visited, int x, int y, Tile t){
		this.visited = visited;
		this.x = x;
		this.y = y;
		neighbours = new HashSet<Node>();
		this.t= t;
	}

	public String toString(){
		return x + ":" +y + "\t|" + t;
	}

}

