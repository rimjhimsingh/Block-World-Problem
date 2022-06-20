import java.io.*;
import java.util.*;

class Block{ //class for 'block' which uniquely identifies each block by its name
	char ch;

	Block(char ch){
		this.ch = ch;
	}

	public char getBlock(){
		return ch;
	}
}

class Predicate {
	int type_of_predicate; //1 - ON(X,Y), 2 for ONT(X), 3 for CL(X), 4 for HOLD(X), 5 for AE(arm empty)
	Block b1;
	Block b2;
	Predicate(){
		this.type_of_predicate = -1;
		b1 = null;
		b2 = null;
	}
	Predicate(int type_of_predicate){
		this.type_of_predicate = type_of_predicate;
		this.b1 = null;
		this.b2 = null;
	}	
	Predicate(int type_of_predicate, Block b1){
		this.type_of_predicate = type_of_predicate;
		this.b1 = b1;
		this.b2 = null;
	}
	Predicate(int type_of_predicate, Block b1, Block b2){
		this.type_of_predicate = type_of_predicate;
		this.b1 = b1;
		this.b2 = b2;
	}

	@Override
	public boolean equals(Object obj){ //overriding the equality method for comparing two predicate objects
		Predicate otherPred = (Predicate)obj;
		int myPred = otherPred.type_of_predicate;
		if(myPred != this.type_of_predicate) return false;
		if(myPred == 1 && (otherPred.b1.getBlock()==this.b1.getBlock()) && (otherPred.b2.getBlock() == this.b2.getBlock())) return true;
		if(myPred == 2 && (otherPred.b1.getBlock()==this.b1.getBlock()) ) return true;
		if(myPred == 3 && (otherPred.b1.getBlock()==this.b1.getBlock()) ) return true;
		if(myPred == 4 && (otherPred.b1.getBlock()==this.b1.getBlock()) ) return true;
		if(myPred == 5) return true;
		return false;
	}

}

class Goal{  
	ArrayList<Predicate> goalList;
	Operator op;
	int flag; // 1 for compound goal, 2 for single goal , 3 for operator
	Goal(ArrayList<Predicate> goalList){
		this.goalList = goalList;
		this.op = null;
		if(goalList.size() == 1){
			flag = 2;
		}
		else flag = 1;
	}
	Goal(Operator op){
		this.goalList = null;
		this.op = op;
		flag = 3;
	}
}

public class BlockWorld{
	static ArrayList<ArrayList<Block>> start_state;
	static ArrayList<ArrayList<Block>> goal_state;

	public static void print_state(ArrayList<Predicate> curr_state){
		for(int i=0; i<curr_state.size();i++){
			Predicate curr_pred = (Predicate)curr_state.get(i);
			if(curr_pred.type_of_predicate == 1){
				System.out.print("ON("+curr_pred.b1.getBlock()+","+curr_pred.b2.getBlock()+"), ");
			}
			else if(curr_pred.type_of_predicate == 2){
				System.out.print("ONT("+curr_pred.b1.getBlock()+"), ");
			}
			else if(curr_pred.type_of_predicate == 3){
				System.out.print("CL("+curr_pred.b1.getBlock()+"), ");
			}
			else if(curr_pred.type_of_predicate == 4){
				System.out.print("HOLD("+curr_pred.b1.getBlock()+"), ");
			}
			else if(curr_pred.type_of_predicate == 5){
				System.out.print("AE, ");
			}
		}
		System.out.println();
	}

	public static ArrayList<Predicate> getStatePredicates(ArrayList<ArrayList<Block>> start_state, int n){
		ArrayList<Predicate> initial_state = new ArrayList<Predicate>();
		for(int i=0; i<n; i++){
			ArrayList<Block> al = start_state.get(i);
			if(al.size() == 1){
				Block curr_block = (Block)al.get(0);
				initial_state.add(new Predicate(2, curr_block));
				initial_state.add(new Predicate(3, curr_block));
			}
			else{
				for(int j=0; j<al.size()-1; j++){
					Block curr_block = (Block)al.get(j);
					Block next_block = (Block)al.get(j+1);	
					if(j==0){
						initial_state.add(new Predicate(2, curr_block));
					}
					initial_state.add(new Predicate(1, next_block, curr_block));
					if(j==(al.size()-2)){
						initial_state.add(new Predicate(3,next_block));
					}
				}// end of loop	
			}
		}// end of outer for loop
		initial_state.add(new Predicate(5));
		return initial_state;
	}

	public static void print_goal_stack(ArrayList<Goal> goalStack){
		for(int i=0; i<goalStack.size(); i++){
			Goal current_goal = (Goal)goalStack.get(i);
			if(current_goal.flag == 1 || current_goal.flag == 2){
				print_state(current_goal.goalList);
			}
			else if(current_goal.flag == 3){
				Operator curr_op = (Operator)current_goal.op;
				if(curr_op.type_of_operator == 1){
					System.out.print("STACK("+curr_op.first.getBlock()+","+curr_op.second.getBlock()+"), ");
				}else if(curr_op.type_of_operator == 2){
					System.out.print("UNSTACK("+curr_op.first.getBlock()+","+curr_op.second.getBlock()+"), ");
				}else if(curr_op.type_of_operator == 3){
					System.out.print("PICKUP("+curr_op.first.getBlock()+"), ");	
				}else if(curr_op.type_of_operator == 4){
					System.out.print("PUTDOWN("+curr_op.first.getBlock()+"), ");	
				}else{
					System.out.println("** Inappropriate Operator. Exiting...");
					System.exit(0);
				}
				System.out.println();
			}else{
				System.out.println("Invalid Goal State, Exiting...");
				System.exit(0);
			}
		}
		System.out.println("------------------------------------------------------------------------------------------------");
	}

	public static void main(String args[])throws IOException{
		goal_state = new ArrayList<ArrayList<Block>>();
		start_state = new ArrayList<ArrayList<Block>>();
		System.out.println("Welcome to the Block World Problem. The planning follows the STRIPS Goal-Stacking to solve the problem");
		System.out.println("Please enter the number of different block stacks in the start state");
		Scanner sc =  new Scanner(System.in);
		int n = sc.nextInt();
		while(n <= 0){
			System.out.println("Please enter a positive number");
			n = sc.nextInt();
		}
		for(int i=0; i<n; i++){
			start_state.add(new ArrayList<Block>());
		}//initializing 		
		System.out.println("Please enter the block stacked upon each other without spaces, and separate two block stacks using space or new line");
		int num_stacks = 0;
		int i=0;
		while(num_stacks < n){
			String str = sc.next().trim();
			for(i=0; i<str.length();i++){
				start_state.get(num_stacks).add(new Block(str.charAt(i)));
			}
			num_stacks++;
		}

		System.out.println("Please enter the number of different block stacks in the goal state");
		int p = sc.nextInt();
		while(p <= 0){
			System.out.println("Please enter a positive number");
			p = sc.nextInt();
		}
		for(i=0; i<p; i++){
			goal_state.add(new ArrayList<Block>());
		}//initializing 		
		System.out.println("Please enter the block stacked upon each other without spaces, and separate two block stacks using space or new line");
		num_stacks = 0;
		i=0;
		while(num_stacks < p){
			String str = sc.next().trim();
			for(i=0; i<str.length();i++){
				goal_state.get(num_stacks).add(new Block(str.charAt(i)));
			}
			num_stacks++;
		}

		ArrayList<Predicate> initial_stack = getStatePredicates(start_state, n);
		ArrayList<Predicate> goal_stack = getStatePredicates(goal_state, p);
		ArrayList<Goal> main_stack = new ArrayList<Goal>();
		main_stack.add(new Goal(goal_stack));
		ArrayList<Operator> squeue = new ArrayList<Operator>();
		int count = 0;

		while(main_stack.size()!=0){ //THE MAIN ALGORITHM
			// System.out.println("INITIAL STACK : ");
			// print_state(initial_stack);
			// System.out.println("GOAL STACK : ");
			// print_goal_stack(main_stack);
			Goal current_goal = main_stack.get(main_stack.size()-1);
			if(current_goal.flag == 1){
				// System.out.println("FLAG 1");
				//compound goal
				ArrayList<Predicate> compoundGoal = (ArrayList<Predicate>)current_goal.goalList;
				if(initial_stack.containsAll(compoundGoal)){
					//GOAL ACHIEVED
					//POP THIS GOAL FROM GOAL STACK
					main_stack.remove(main_stack.size()-1);
				}else{
					for(i=0; i<compoundGoal.size(); i++){
						Predicate curr_pred = (Predicate)compoundGoal.get(i);
						if(!initial_stack.contains(curr_pred)){ //add those single goals which are not present in initial stack
							ArrayList<Predicate> al = new ArrayList<Predicate>();
							al.add(curr_pred);
							main_stack.add(new Goal(al));
						}
					}//end of for loop
				}//end to if-else construct
			}else if(current_goal.flag == 2){
				//single goal
				// System.out.println("FLAG 2");
				ArrayList<Predicate> singleGoal = (ArrayList<Predicate>)current_goal.goalList;
				Predicate singlePred = singleGoal.get(0);
				if(initial_stack.contains(singlePred)){
					main_stack.remove(main_stack.size()-1);
				}
				else{
					// find the operator in the add list which has this single goal
					Operator opOfAddList = new Operator(5, null);
					if(singlePred.type_of_predicate == 1){
						opOfAddList = new Operator(1, singlePred.b1, singlePred.b2);
					}else if(singlePred.type_of_predicate == 2){
						opOfAddList = new Operator(4, singlePred.b1);
					}else if(singlePred.type_of_predicate == 3){
						Block b = new Block('.');
						for(i=0; i<initial_stack.size(); i++){
							Predicate curr_pred = (Predicate)initial_stack.get(i);
							if(curr_pred.type_of_predicate == 1 && curr_pred.b2.getBlock()==singlePred.b1.getBlock()){
								b = curr_pred.b1;
							} 
						}
						if(b.getBlock() != '.')
							opOfAddList = new Operator(2, b, singlePred.b1);
						else{
							System.out.println("No Valid Operator is Available. Cannot be solved. Exiting...");
							System.exit(0);
						}
					}
					else if(singlePred.type_of_predicate == 4){
						Block b = new Block('.');
						for(i=0; i<initial_stack.size(); i++){
							Predicate curr_pred = (Predicate)initial_stack.get(i);
							if(curr_pred.type_of_predicate == 1 && curr_pred.b1.getBlock()==singlePred.b1.getBlock()){
								b = curr_pred.b2;
							} 
						}
						// Random rand = new Random();
						// int num = rand.nextInt(2);
						if(b.getBlock() == '.')
							opOfAddList = new Operator(3, singlePred.b1);
						else{
							opOfAddList = new Operator(2, singlePred.b1, b);
						}
					}
					else if(singlePred.type_of_predicate == 5){
						Block b = new Block('.');
						for(i=0; i<initial_stack.size(); i++){
							Predicate curr_pred = (Predicate)initial_stack.get(i);
							if(curr_pred.type_of_predicate == 4)
								b = curr_pred.b1;
						}
						if(b.getBlock() != '.')
							opOfAddList = new Operator(4, b);
						else{
							System.out.println("Incorrect Predicate of AE. Check again. Exiting.... ");
							System.exit(0);
						}
					}
					else{
						System.out.println("Incorrect Predicate. Exiting...");
						System.exit(0);
					}

					//remove the 'single goal' from the main stack
					main_stack.remove(main_stack.size()-1);

					//push the operator onto the stack
					main_stack.add(new Goal(opOfAddList));

					//push the preconditions onto the stack as a compound goal
					main_stack.add(new Goal(opOfAddList.getPreCond()));

				}
			}
			else if(current_goal.flag == 3){
				//operator
				// System.out.println("FLAG 3");
				Operator action = (Operator)current_goal.op;

				//remove the action out of stack
				main_stack.remove(main_stack.size()-1);

				//obtain the new state by appropriate adds and deletes in the initial stage
				ArrayList<Predicate> addList = action.getAddList();
				ArrayList<Predicate> deleteList = action.getDeleteList();
				for(i=0; i<deleteList.size(); i++){
					Predicate del_pred = (Predicate)deleteList.get(i);
					int del_index = initial_stack.indexOf(del_pred);
					if(del_index!=-1) initial_stack.remove(del_index);
				}
				for(i=0; i<addList.size(); i++){
					Predicate add_pred = (Predicate)addList.get(i);
					// System.out.println(add_pred.type_of_predicate);
					initial_stack.add(add_pred);
				}

				//add action to plans
				squeue.add(action);
			}else{
				System.out.println("Improper goal state. Exiting...");
				System.exit(0);
			}

		}

		System.out.println("----------------------- LIST OF APPROPRIATE ACTIONS ARE ----------------------------------------------");
		for(i=0; i<squeue.size(); i++){ 
			Operator curr_op = (Operator)squeue.get(i);
			if(curr_op.type_of_operator == 1){
				System.out.println("STACK("+curr_op.first.getBlock()+","+curr_op.second.getBlock()+")");
			}else if(curr_op.type_of_operator == 2){
				System.out.println("UNSTACK("+curr_op.first.getBlock()+","+curr_op.second.getBlock()+")");
			}else if(curr_op.type_of_operator == 3){
				System.out.println("PICKUP("+curr_op.first.getBlock()+")");	
			}else if(curr_op.type_of_operator == 4){
				System.out.println("PUTDOWN("+curr_op.first.getBlock()+")");	
			}else{
				System.out.println("Inappropriate Operator. Exiting...");
				System.exit(0);
			}			
		}

		// for(i=0; i<n; i++){
		// 	ArrayList al = start_state.get(i);
		// 	for(int j=0; j<al.size(); j++){
		// 		Block curr_block = (Block)al.get(j);
		// 		System.out.print(curr_block.getBlock()+", ");
		// 	}
		// 	System.out.println();
		// }

		// for(i=0; i<p; i++){
		// 	ArrayList al = goal_state.get(i);
		// 	for(int j=0; j<al.size(); j++){
		// 		Block curr_block = (Block)al.get(j);
		// 		System.out.print(curr_block.getBlock()+", ");
		// 	}
		// 	System.out.println();
		// }

	} //main
} //class

