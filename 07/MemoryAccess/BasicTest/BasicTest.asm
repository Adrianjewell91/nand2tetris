//push constant 10
@10 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop local 0

//push constant 21
@21 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push constant 22
@22 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop argument 2

//pop argument 1

//push constant 36
@36 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop this 6

//push constant 42
@42 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push constant 45
@45 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop that 5

//pop that 2

//push constant 510
@510 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop temp 6

//push local 0
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push that 5
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//add
@SP 
D=M 
D=D-1 
A=D 
D=M  
A=A-1 
D=D+M 
M=D 
D=A+1 
@SP 
M=D 

//push argument 1
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//sub

//push this 6
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push this 6
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//add
@SP 
D=M 
D=D-1 
A=D 
D=M  
A=A-1 
D=D+M 
M=D 
D=A+1 
@SP 
M=D 

//sub

//push temp 6
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//add
@SP 
D=M 
D=D-1 
A=D 
D=M  
A=A-1 
D=D+M 
M=D 
D=A+1 
@SP 
M=D 

