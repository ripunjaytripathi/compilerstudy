

class startcon{
	
	public static void dec_dump(String dmpstr)
	{
		int i;
		byte ch;
		
		for (i=0;i<dmpstr.length();i++)
		{
			ch = (byte) dmpstr.charAt(i);
			System.out.print(" "+ch+ " ");
		}
		System.out.println(" ");
	}

	public static void main(String argv[]) throws java.io.IOException {
		Yylex yy = new Yylex(System.in);
		yy.yylex();
  	}


	public static String rem_ws(String instr){
		int i,j,len;
		i=0;j=0;
		len = instr.length();
		byte b;
		char[] CharArray = new char[len];
		
		for(;j<len;j++){
			b=(byte) instr.charAt(j);
			if(b!=10 && b!= 32 && b!= 12 && b!=9 )
				CharArray[i++] = instr.charAt(j);
		}
		String outstr = new String(CharArray);
		System.out.println(outstr);
		len = outstr.length();
		j=0;
		b=(byte) outstr.charAt(j);
		while(b!=0 )
		{
			j++;
			b=(byte) outstr.charAt(j);
		}
		len = j;
		char [] CharOutArray = new char [len];
		for(i=0;i<len;i++){
		{	b=(byte)CharArray[i];
			if(b!=0)
				CharOutArray[i] = (char) b;
			else
				break;
		}
				
		}
		String result = new String(CharOutArray);
		return result;
	}//end of rem_ws

}



%%
%state classfound
%state comment
%state idfound
%state dquotefound	 
%{
private int num_lines = 0;
private int num_chars = 0;
private int classseen = 0;
%}
%integer
%%
[\n]								{ System.out.print(" TOK_NL, ");num_lines++; }
\t                                                      	{ System.out.print(" TOK_TAB, ");num_chars++; }
\r                                                      	{}
\f                                                      	{}
" "								{ System.out.print(" TOK_SPACE, ");num_lines++; }
[\n\t ]                                                 	{ System.out.print(" TOK_WS, ");num_lines++; }


<comment>[\n\t \(\)\-_\+a-zA-Z\\.,:;!@#$%^&?`\*~\"0-9]*"*)"  	{
                                                	       	yybegin(YYINITIAL);
							 	System.out.print(" RTokenConstants.CLOSECOMENT, ");
                                                        	}
[t][r|R][u|U][e|E]						{ System.out.print(" RTokenConstants.BOOL_CONST, "+yytext());
									startcon.dec_dump(yytext());
								 }
[f][a|A][l|L][s|S][e|E]						{ System.out.print(" RTokenConstants.BOOL_CONST, "+yytext());
									startcon.dec_dump(yytext()); }
[\n\t ][i|I][f|F][\n\t ]                                      {System.out.print(" RTokenConstants.IF, ");startcon.dec_dump(yytext());      }
[\n\t ][f|F][i|I][\n\t ]                                      {System.out.print(" RTokenConstants.FI, "); startcon.dec_dump(yytext());     }
[\n\t ][i|I][n|N][h|H][e|E][r|R][i|I][t|T][s|S][\n\t ]        {System.out.print(" RTokenConstants.INHERITS, ");startcon.dec_dump(yytext());}
[\n\t ][l|L][e|E][t|T][\n\t ]                                 {System.out.print(" RTokenConstants.LET, "); startcon.dec_dump(yytext());    }
[\n\t ][c|C][l|L][a|A][s|S][s|S][\n\t ]                       {yybegin(classfound);classseen=1;System.out.print(" RTokenConstants.CLASS, ");
								startcon.dec_dump(yytext());   }
[\n\t ][n|N][e|E][w|W][\n\t ]                                 {System.out.print(" RTokenConstants.NEW, ");startcon.dec_dump(yytext());     }
[\n\t ][i|I][s|S][v|V][o|O][i|I][d|D][\n\t ]                  {System.out.print(" RTokenConstants.ISVOID, ");startcon.dec_dump(yytext());  }
[\n\t ][n|N][o|O][t|T][\n\t ]                                 {System.out.print(" RTokenConstants.NOT, "); startcon.dec_dump(yytext()); }
[\n\t ][l|L][o|O][o|O][p|P][\n\t ]                            {System.out.print(" R(TokenConstants.LOOP, "); startcon.dec_dump(yytext()); }
[\n\t ][i|I][n|N][\n\t ]                                      {System.out.print(" RTokenConstants.IN, ");startcon.dec_dump(yytext()); }
[\n\t ][t|T][h|H][e|E][n|N][\n\t ]                            {System.out.print(" RTokenConstants.THEN, ");startcon.dec_dump(yytext()); }
[\n\t ][e|E][l|L][s|S][e|E][\n\t ]                            {System.out.print(" RTokenConstants.ELSE, ");startcon.dec_dump(yytext()); }
[\n\t ][c|C][a|A][s|S][e|E][\n\t ]                            {System.out.print(" RTokenConstants.CASE, ");startcon.dec_dump(yytext()); }
[\n\t ][e|E][s|S][a|A][c|C][\n\t ]                            {System.out.print(" RTokenConstants.ESAC, ");startcon.dec_dump(yytext()); }
[\n\t ][i|I][n|N][\n\t ]                                      {System.out.print(" RTokenConstants.IN, ");startcon.dec_dump(yytext()); }
[\n\t ][o|O][f|F][\n\t ]                                      {System.out.print(" RTokenConstants.OF, ");startcon.dec_dump(yytext()); }
[\n\t ][p|P][o|O][o|O][l|L][\n\t ;]                            {System.out.print(" RTokenConstants.POOL, ");startcon.dec_dump(yytext()); }
[\n\t ][w|W][h|H][i|I][l|L][e|E][\{\n\t ]			{
									System.out.print(" RTokenConstants.WHILE, ");
									startcon.dec_dump(yytext());
								}

[a-z]+[A-Za-z0-9_]*						{
									yybegin(idfound);
									System.out.print(" RTokenConstants.IDENTIFIER : "+yytext());
									startcon.dec_dump(yytext());
								}

<idfound>[a-z][A-Za-z0-9_]*					{	
									yybegin(YYINITIAL);
									System.out.print(" RTokenConstants.IDENTIFIER : "+yytext());
									startcon.dec_dump(yytext());
								}



<idfound>[ \t]*[:]						{
									//yybegin(YYINITIAL);
									System.out.println(" RTokenConstants.COLON : ");
									startcon.dec_dump(yytext());
								}

<idfound>[ ]*[[A-Z][A-Za-z0-9_]*				{
									yybegin(YYINITIAL);
                                                                        System.out.println(" RTokenConstants.CLASSNAME : "+yytext());
									startcon.dec_dump(yytext());
								}


<classfound>[A-Z][A-Za-z0-9_]*					{ 
									yybegin(YYINITIAL);
									System.out.println(" RTOK_KEY_CLASSNAME : "+yytext()); 
									startcon.dec_dump(yytext());
								} 



[ \n\t]*[A-Z][A-Za-z0-9_]*					{ 
									System.out.println(" RTOK_KEY_CLASSNAME : "+yytext()); 
									startcon.dec_dump(yytext());
								} 

[\-][\-][\t \(\)\-_\+a-zA-Z\\.,:;!@#$%^&`\*?~0-9]*[\n]*		{System.out.print(" RTokenConstants.COMMENT ");startcon.dec_dump(yytext());}

[0-9]+								{System.out.print(" RTokenConstants.INT_CONST ");startcon.dec_dump(yytext());}								
"\""[\n\t \(\)\-_\+a-zA-Z\\.,:;!@#$%^&`\*?~0-9]*"\""		 { System.out.print(" RTokenConstants.DOUBLEQUOTE ");startcon.dec_dump(yytext());}

"@"								{System.out.print(" RTokenConstants.AT ");startcon.dec_dump(yytext());}
";"                                                            { System.out.print(" RTokenConstants.SEMI ");startcon.dec_dump(yytext()); }
"<-"                                                           { System.out.print(" RTokenConstants.ASSIGN ");startcon.dec_dump(yytext()); }
"("                                                            { System.out.print(" RTokenConstants.LPAREN ");startcon.dec_dump(yytext()); }
")"                                                            { System.out.print(" RTokenConstants.RPAREN ");startcon.dec_dump(yytext()); }
","                                                            { System.out.print(" RTokenConstants.COMMA ");startcon.dec_dump(yytext()); }
":"                                                            { System.out.print(" RTokenConstants.COLON ");startcon.dec_dump(yytext()); }
\.                                                             { yybegin(idfound); 
								 System.out.println(" RTokenConstants.DOT ");
								startcon.dec_dump(yytext()); }
\+                                                             { System.out.println(" RTokenConstants.PLUS ");startcon.dec_dump(yytext()); }
\-                                                             { System.out.println(" RTokenConstants.MINUS ");startcon.dec_dump(yytext()); }
\*                                                             { System.out.println(" RTokenConstants.MULT ");startcon.dec_dump(yytext()); }
"/"                                                            { System.out.println(" RTokenConstants.DIV "); startcon.dec_dump(yytext()); }
"<"                                                            { System.out.println(" RTokenConstants.LT "); startcon.dec_dump(yytext()); }
"<="                                                           { System.out.println(" RTokenConstants.LE "); startcon.dec_dump(yytext()); }
"="                                                            { System.out.println(" RTokenConstants.EQ "); startcon.dec_dump(yytext()); }
"{"                                                            { System.out.println(" RTokenConstants.LBRACE "); startcon.dec_dump(yytext()); }
"}"                                                            { System.out.println(" RTokenConstants.RBRACE ");startcon.dec_dump(yytext()); }
"~"								{ System.out.println(" RTokenConstants.NEG ");startcon.dec_dump(yytext()); }
<YYINITIAL>"=>"                                                 { /* Sample lexical rule for "=>" arrow.
                                                                                Further lexical rules should be defined
                                                                                here, after the last %% separator */
                                                                  System.out.println("RTokenConstants.DARROW ");
								  startcon.dec_dump(yytext());
								}



"(*"                                                          { 
								
                                                                 yybegin(comment);
								 System.out.println(" RTokenConstants.OPENCOMMENT ");
								 startcon.dec_dump(yytext());
                                                               }

.								{ 	
									System.out.print(" RTokenConstants.ERROR : ");
									startcon.dec_dump(yytext());
									System.out.println("\n" + yytext()+ "\n");
								}




