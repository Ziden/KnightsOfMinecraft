/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nativelevel.ArenaGuilda2x2;

/**
 *
 * @author usuario
 * 
 */

//////RANKING
public class Elo
{

        public static void main(String [] args) {
            
            /*
            int vitoria = 1200;
            int derrota = 800;
            
            int eloGanhador = matou(vitoria, derrota);
            int eloPerdedor = morreu(vitoria, derrota);
            
            System.out.println("1: "+eloGanhador);
            System.out.println("2: "+eloPerdedor);
            */
            
            int karmaMatou = 1000;
            int karmaMorreu = 700;
            
            int karmaMatador = matou(karmaMatou, karmaMorreu);
            
            System.out.println(karmaMatador);
        }
        
    
    
	public static int matou(int winner, int loser){
		double prob; 
		int diff =  Math.abs(winner-loser);
		if(winner > loser)
		{
			prob = strongProbability(diff);
		}
		else
		{
			prob = weakProbability(diff);
		}
		int rateChange = formula(prob,1);		
		return winner + rateChange;
	}

	public static int morreu(int winner, int loser){
		double prob; 
		int diff =  Math.abs(winner-loser);
		if(loser > winner)
		{
			prob = strongProbability(diff);
		}
		else
		{
			prob = weakProbability(diff);
		}
		int rateChange = formula(prob,0);		
		return loser + rateChange;
	}	

	private static double strongProbability(int diff)
	{
		double prob;
		if(diff > 735){prob = 0.99;}
		else if(diff > 500){prob = 0.96;}
		else if(diff > 450){prob = 0.94;}
		else if(diff > 400){prob = 0.92;}
		else if(diff > 350){prob = 0.89;}
		else if(diff > 300){prob = 0.85;}
		else if(diff > 250){prob = 0.81;}
		else if(diff > 200){prob = 0.76;}
		else if(diff > 150){prob = 0.7;}
		else if(diff > 100){prob = 0.64;}
		else if(diff > 50){prob = 0.57;}
		else if(diff > 25){prob = 0.53;}
		else{prob = 0.5;}
		return prob;
	}

	private static double weakProbability(int diff)
	{	
		double prob;
		if(diff > 735){prob = 0.01;}
		else if(diff > 500){prob = 0.04;}
		else if(diff > 450){prob = 0.06;}
		else if(diff > 400){prob = 0.08;}
		else if(diff > 350){prob = 0.11;}
		else if(diff > 300){prob = 0.15;}
		else if(diff > 250){prob = 0.19;}
		else if(diff > 200){prob = 0.24;}
		else if(diff > 150){prob = 0.3;}
		else if(diff > 100){prob = 0.36;}
		else if(diff > 50){prob = 0.43;}
		else if(diff > 25){prob = 0.47;}
		else{prob = 0.5;}
		return prob;
	}

	// The formula for figuring out how much to change
	// Outcome is 1 for win and 0 for loss.
	private static int formula(double prob, double outcome){
		return (int)(K *(outcome - prob));
	}

	// K-factor determines how large or small the change is in ranks.
	public final static int K = 25; 
}