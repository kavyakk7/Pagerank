package pagerank;
/*
 * 
 * @author : Kavya
 */

import static java.lang.Math.abs;
import java.io.*;
import java.util.*;

public class pagerank_1111 {

	String filename ;
	int initialVal, iteration, vertex, edge;
	double[] rank;
	int[][] graph; 
	int[] outDegree;
	
//	final constants
	final double d = 0.85;
    final double errorrate = 0.00001;

	pagerank_1111(int iteration, int initialVal, String filename) throws IOException
	{
		this.iteration = iteration;
		this.initialVal = initialVal;
		this.filename = filename;
		File file = new File(filename);
		try {        
	        Scanner scan = new Scanner(file);
	        vertex = scan.nextInt();
	        edge = scan.nextInt();
	       
	    //	Initialize graph
			graph = new int[vertex][vertex];
			for(int i = 0; i<vertex ; i++)
			{
				for(int j = 0; j<vertex ; j++)
				{
					graph[i][j] = 0;
				}
			}
			
		// 	Initialize outDegree	
			outDegree = new int[vertex];
			for(int i = 0; i < vertex ; i++)
			{
				outDegree[i]=0;
			}
	    		
		// 	Read vertex and edge from file	
			int i = 0 , j = 0 ;
	        while(scan.hasNextInt())
	        {
	           i = scan.nextInt();
	           j = scan.nextInt();
	           graph[i][j] = 1;
	         }	   	
	        
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		
	//  Calculate out-degree of each vertex
		for(int i = 0 ; i < vertex ; i++)
			for(int j = 0 ; j < vertex ; j++)
				outDegree[i]+=graph[i][j];
		
	//	Initialize rank
		rank = new double[vertex];
		if( initialVal == 0 || initialVal == 1)
		{
			for(int i = 0 ; i < vertex ; i++)
			{
				rank[i] = initialVal;
			}
		}
		else if( initialVal == -1 )
		{
			for(int i = 0 ; i < vertex ; i++)
			{
				rank[i] = 1.0 / vertex ;
			}
		}
		else if( initialVal == -2 )
		{
			for(int i = 0 ; i < vertex ; i++)
			{
				rank[i] = 1.0 / Math.sqrt(vertex) ;
			}
		}		
	}
	
	public static void main(String[] args)
	{
		if(args.length != 3)
		{
	          System.out.println("Usage: pagerank_1111 iterations initialvalue filename");
	          return;
	    }
	    
	//	Command line arguments
		int iterations = Integer.parseInt(args[0]);
		int initialVal = Integer.parseInt(args[1]);
		String filename = args[2];

		if( !(initialVal >= -2 && initialVal <= 1) ) 
		{
          System.out.println(" Initialvalue must be 0, 1, -1 or -2 !");
          return;
		} 

		pagerank_1111 pgrk;
		try {
			pgrk = new pagerank_1111(iterations, initialVal, filename);
			pgrk.PageRankAlgorithm_1111();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void PageRankAlgorithm_1111()
	{
		double[] newRank = new double[vertex];
		boolean check = false ; 
		if( vertex >10 )
		{
			int stop_iter = 0 ;
			
		//	Initialize iteration and initial value
			iteration = 0;
			for(int i = 0; i < vertex ; i++)
			{
				rank[i] = 1.0 / vertex ;
			}
		do{
			if(check)	check = false;
			else	rank = newRank;
			
			for(int i = 0; i < vertex ; i++)
			{
				newRank[i] = 0.0;
			}
			
			for(int j = 0; j < vertex; j++) 
			{
               for(int k = 0; k < vertex; k++)
               {
                  if(graph[k][j] == 1) 
                  {
                      newRank[j] += ( rank[k] / outDegree[k]) ;                     
                  }
               }
            }
			
			for(int i = 0; i < vertex ; i++)
			{
				newRank[i] = ( d * newRank[i] ) + ( (1-d) / vertex ) ;
			}
			stop_iter += 1;			
		}while(!checkConvergence_1111(rank,newRank));
		
	// 	pagerank of last iteration
		System.out.println("Iter: " + stop_iter);
		double value ;
		for(int i = 0 ; i < vertex ; i++)
		{
			value = Math.round(newRank[i]*1000000.0)/1000000.0;
			System.out.printf("P["+i+"] = %.6f\n ", value);
		}
		
		return;		
	}
	
		System.out.println("Base   :  0 ");
		double value;
		for(int i = 0 ; i < vertex ; i++)
		{
			value = Math.round(rank[i]*1000000.0)/1000000.0;
			System.out.printf(" :P[" + i + "]=%.6f", value);
		}
		
		if( iteration != 0 )
		{
			for(int i = 0; i < iteration; i++)
	        {
	            for(int j = 0 ; j < vertex ; j++)
	            {
	            	newRank[j] = 0.0;
	            }
	          
	            for(int k = 0; k < vertex; k++) 
	            {
	            	for(int l = 0; l < vertex; l++)
	            	{
	                  if(graph[l][k] == 1)
	                  {
	                	  newRank[k] += rank[l] / outDegree[l];
	                  }
	            	}
	            }

	            System.out.println();
	            System.out.print("Iter    : " + (i+1));
	            for(int j = 0; j < vertex; j++) 
	            {
	              newRank[j] = ( d * newRank[j] ) + ( (1-d) / vertex ) ;
	              value = Math.round(newRank[j]*1000000.0)/1000000.0;
	              System.out.printf(" :P["+j+"] = %.6f ", value);
	            }
	          
	            for(int k = 0; k < vertex; k++) 
	            {
	              rank[k] = newRank[k]; 
	            } 
	        }
	        System.out.println();
	    }
		else
		{
			// iteration is zero
			int iter = 0 ;
			do
			{
				if(check)	check = false;
				else
				{
					for(int i = 0 ; i < vertex ; i++)
						rank[i] = newRank[i];
				}
				
				for(int j = 0 ; j < vertex ; j++)
					newRank[j] = 0.0 ;
				
				for(int k = 0 ; k < vertex ; k++)
				{
					for(int l = 0 ; l < vertex ; l++)
					{
						if(graph[l][k] == 1)
						{
							newRank[k] += rank[l] / outDegree[l] ;
						}
					}
				}
				
				System.out.println(); 
	            System.out.print("Iter    : " + (iter+1));
	            for(int i = 0; i < vertex; i++) 
	            {
	              newRank[i] = ( d * newRank[i] ) + ( (1-d) / vertex );
	              value = Math.round(newRank[i]*1000000.0)/1000000.0;
	              System.out.printf(" :P["+i+"] = %.6f ", value);
	            }
	            iter++; 
	            
			}while(!checkConvergence_1111(rank,newRank));
			
		System.out.println(); 
	}
}
	boolean checkConvergence_1111(double[] source, double[] destination)
	  {
	      for(int i = 0; i < vertex; i++)
	      {
	        if ( abs(source[i] - destination[i]) > errorrate )
	        	return false;
	      }
	      return true;
	  }
}
