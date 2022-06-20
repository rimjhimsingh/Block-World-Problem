import java.util.*;

class Operator{

	int type_of_operator; // 1 for S(X,Y), 2 for US(X,Y), and 3 for PU(X), and 4 for PD(X)
	Block first;
	Block second;
	ArrayList<Predicate> preCond;
	ArrayList<Predicate> addList;
	ArrayList<Predicate> deleteList;

	Operator(int type_of_operator, Block first, Block second){
		this.type_of_operator = type_of_operator;
		this.first = first;
		this.second = second;
		preCond = new ArrayList<Predicate>();
		addList = new ArrayList<Predicate>();
		deleteList = new ArrayList<Predicate>(); 
	}

	Operator(int type_of_operator, Block first){
		this.type_of_operator = type_of_operator;
		this.first = first;
		this.second = null;
		preCond = new ArrayList<Predicate>();
		addList = new ArrayList<Predicate>();
		deleteList = new ArrayList<Predicate>(); 
	}

	ArrayList<Predicate> getPreCond(){
		switch(type_of_operator){
			case 1:
			preCond.add(new Predicate(3, second));
			preCond.add(new Predicate(4, first));
			break;
			case 2:
			preCond.add(new Predicate(1, first, second));
			preCond.add(new Predicate(3, first));
			preCond.add(new Predicate(5));
			break;
			case 3:
			preCond.add(new Predicate(2, first));
			preCond.add(new Predicate(3, first));
			preCond.add(new Predicate(5));
			break;
			case 4:
			preCond.add(new Predicate(4, first));
			break;
			default:
			System.out.println("Wrong Operator Called");
		}
		return preCond;
	}

	ArrayList<Predicate> getAddList(){
		switch(type_of_operator){
			case 1:
			addList.add(new Predicate(5));
			addList.add(new Predicate(1, first, second));
			addList.add(new Predicate(3, first));
			break;
			case 2:
			addList.add(new Predicate(4, first));
			addList.add(new Predicate(3, second));
			break;
			case 3:
			addList.add(new Predicate(4, first));
			break;
			case 4:
			addList.add(new Predicate(2, first));
			addList.add(new Predicate(5));
			addList.add(new Predicate(3, first));
			break;
			default:
			System.out.println("Wrong Operator Called");
		}
		return addList;
	}

	ArrayList<Predicate> getDeleteList(){
		switch(type_of_operator){
			case 1:
			deleteList.add(new Predicate(3, second));
			deleteList.add(new Predicate(4, first));
			break;
			case 2:
			deleteList.add(new Predicate(1, first, second));
			deleteList.add(new Predicate(5));
			deleteList.add(new Predicate(3, first));
			break;
			case 3:
			deleteList.add(new Predicate(2, first));
			deleteList.add(new Predicate(5));
			deleteList.add(new Predicate(3, first));
			break;
			case 4:
			deleteList.add(new Predicate(4, first));
			break;
			default:
			System.out.println("Wrong Operator Called");
		}
		return deleteList;
	}

}