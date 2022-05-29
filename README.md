# Block World Problem

Introduction to the block world problem

![image](https://user-images.githubusercontent.com/54510650/170856153-d6f39a0d-7142-47e4-9f31-33338be0bd6a.png)

There is a table on which some blocks are placed. Some blocks may or may not be
stacked on other blocks. We have a robot arm to pick up or put down the blocks. The
robot arm can move only one block at a time, and no other block should be stacked on
top of the block which is to be moved by the robot arm.
Our aim is to change the configuration of the blocks from the Initial State to the Goal
State.

Assumptions: 
The single character name of each block serves as a unique identifier. There can't be
two blocks with the same name.
Before providing the real state, the number of input and output stacks must be specified.

Input Format:
ABC DEF: denotes two stacks, the first of which is A below B below C and the second
of which is D below E below F

# Files:

Operator.java :
This java file contains the Operator class which contains routines for 4 different
operators.
Stack(X,Y): Action to stack block X over block Y
Unstack(X,Y): Action to unstack block X from block Y
PickUp(X): Action to pickup block X from the table
PutDown(X): Action to putdown block X on the table

BWP.java
This file contains four classes:-

1. Goal Class: This class has 3 distinct kinds of terms, which are goals. Each goal must
be identified and processed in accordance with its category. Compound Goals, Single
Goals, and Operators have distinct flags attached to them so that they may be
differentiated easily.

2. Predicate Class: There are 5 predicates that we have used to identify a state
correctly. These are as follows:-
ON(X,Y) -> Block X placed over Block Y
ONT(X) -> Block X placed on the table
CL(X) -> Top of X is clear
HOLD(X) -> Bot is holding X AE -> Bot's Arm is empty

3. Block Class: The Block Class initializes a block object with its unique identifier.

4. BWP: Runs the main algorithm, which solves the Block World Problem with a single
goal stack, and saves the necessary actions in a queue, which is displayed when the


Working of the program:

For the following problem:
![image](https://user-images.githubusercontent.com/54510650/170856278-c20f567b-7825-4a0c-a8ec-1c39f35cfbd7.png)

![image](https://user-images.githubusercontent.com/54510650/170856239-edc4fda3-3f19-4615-88bb-25cf6832dcad.png)

![image](https://user-images.githubusercontent.com/54510650/170856261-869cdb46-b3a3-483a-a102-d182a9cfacba.png)

