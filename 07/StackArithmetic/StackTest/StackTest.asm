//
//
//
//
//
//
//
//push constant 17
@17 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 17
@17 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//eq
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE0 
D;JEQ 
@SP 
A=M-1 
A=A-1 
M=0 
@END0 
0;JMP 
(TRUE0) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END0)
@SP 
D=M-1
M=D

//push constant 17
@17 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 16
@16 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//eq
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE1 
D;JEQ 
@SP 
A=M-1 
A=A-1 
M=0 
@END1 
0;JMP 
(TRUE1) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END1)
@SP 
D=M-1
M=D

//push constant 16
@16 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 17
@17 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//eq
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE2 
D;JEQ 
@SP 
A=M-1 
A=A-1 
M=0 
@END2 
0;JMP 
(TRUE2) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END2)
@SP 
D=M-1
M=D

//push constant 892
@892 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 891
@891 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//lt
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE3 
D;JLT 
@SP 
A=M-1 
A=A-1 
M=0 
@END3 
0;JMP 
(TRUE3) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END3)
@SP 
D=M-1
M=D

//push constant 891
@891 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 892
@892 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//lt
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE4 
D;JLT 
@SP 
A=M-1 
A=A-1 
M=0 
@END4 
0;JMP 
(TRUE4) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END4)
@SP 
D=M-1
M=D

//push constant 891
@891 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 891
@891 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//lt
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE5 
D;JLT 
@SP 
A=M-1 
A=A-1 
M=0 
@END5 
0;JMP 
(TRUE5) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END5)
@SP 
D=M-1
M=D

//push constant 32767
@32767 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 32766
@32766 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//gt
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE6 
D;JGT 
@SP 
A=M-1 
A=A-1 
M=0 
@END6 
0;JMP 
(TRUE6) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END6)
@SP 
D=M-1
M=D

//push constant 32766
@32766 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 32767
@32767 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//gt
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE7 
D;JGT 
@SP 
A=M-1 
A=A-1 
M=0 
@END7 
0;JMP 
(TRUE7) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END7)
@SP 
D=M-1
M=D

//push constant 32766
@32766 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 32766
@32766 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//gt
@SP 
A=M-1 
D=M 
A=A-1 
D=M-D 
@TRUE8 
D;JGT 
@SP 
A=M-1 
A=A-1 
M=0 
@END8 
0;JMP 
(TRUE8) 
@SP 
A=M-1 
A=A-1 
M=-1 
(END8)
@SP 
D=M-1
M=D

//push constant 57
@57 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 31
@31 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 53
@53 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//add
@SP 
A=M-1 
D=M  
A=A-1 
D=D+M 
M=D 
D=A+1 
@SP 
M=D 

//push constant 112
@112 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//sub
@SP 
A=M-1 
D=M  
A=A-1 
D=M-D 
M=D 
D=A+1 
@SP 
M=D 

//neg
@SP 
A=M-1 
D=-M 
M=D 
D=A+1 
@SP 
M=D 

//and
@SP 
A=M-1 
D=M  
A=A-1 
D=D&M 
M=D 
D=A+1 
@SP 
M=D 

//push constant 82
@82 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//or
@SP 
A=M-1 
D=M  
A=A-1 
D=D|M 
M=D 
D=A+1 
@SP 
M=D 

//not
@SP 
A=M-1 
D=!M 
M=D 
D=A+1 
@SP 
M=D 

