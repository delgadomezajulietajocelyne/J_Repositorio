	ORG	1
	TFR hola
	LDAA	#@2 ;
	LDAA	#@75 ;

	NOP	;inhere
	INX	;inhere

	LDAA	#$55
	LDX	#$1234
	LDY	#$67

	LDAA	$55
	LDAA	$0055
	LDX	$20

	LDAA	$00FF
	LDAA	$FFFF
	LDAA	VALOR1

	LDAA	,X

	ADCA	0
	ADCA	#$3G ;hexamal---------------------
	ADCA	#$3 ;hexa
	ADCA	#@-3 ;octa
	ADCA	#$3A ;hexade
	ADCA	#50 ;decima
d	ADCA	#-257 ;decimalmal---------------
	ADCA	#%1111 ;binario
	ADCA	#%s11	;malaadsfafsdf----
	ADCA	$3 ;adf
	ADCA	256 ;adsf
	ADCA	#hola ;sssmaladsfsadfd-----
	ASL	$FF ;sd
	ASL 	$FFFF ;exten
	ASL	Val1
	ASL	1ads ;sssmaladsfsadfd-----
	ADDD	#-257 ;decimal
	CMPA	4,X
	CMPA	16,X
	CMPA	4,5	;sssmaladsfsadfd-----
	CMPA	4,PC
	CMPA	4,,X	;sssmaladsfsadfd-----
	CMPA	4X	;sssmaladsfsadfd-----
	CMPA	[4,X	;sssmaladsfsadfd-----
	CMPA	,PC
	LDAA	0,X
	LDAA	0,X
	LDAA	$1,X
	LDAA	15,X
	LDAA	-1,X
	LDAA	-16,X
	LDAA	-16,k  ;mal---------
	LDAA	-8,Y
	LDAA	255,X
	LDAA	-18,X
	LDAA	-20,Y
	BRSET	65535,X
	TBL	400,X ;mal------------
	LDAA	[100,X]
	LDAA	[D,X]
	LDAA	[D,ds] ;mal--------
	LDAA	[ds,x] ;mal--------
	LDAA	a,x
	LDAA	a,sp
	LDAA	d,pc
	LDAA	8,-sp
	LDAA	7,+pc	;mal------
	LBRA	65535
	LBRA	65536   ;mal
		SWI
	ADCA 3
	ADCA @3
	ADCA %1111
	ADCA $3
	ADCA 257
	ADCA $FFF
	ADCA #3
	ADCA #@3
	ADCA #%1111
	ADCA 1,X
	ADCA 255,X
	ADCA 32768,X
	ADCA 1,+PC	;mal-----
	ADCA A,X
	ADCA 257,X
	ADCA 64444,X
	ADCA [1,X]
	ADCA [6444,X]
	ADCA [D,X] 
	BRA UNO
	LBRA UNO
	END