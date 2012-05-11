/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;
import java_cup.runtime.Scanner;
%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
	/* If necessary, add code for other states here, e.g:
	   case COMMENT:
	   ...
	   break;
	*/
    }
    return new Symbol(TokenConstants.EOF);
%eofval}
%state classfound
%class CoolLexer
%cup
INTCONST=[\-]?[1-9][0-9]*
%%

<YYINITIAL>"=>"			{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  		  return new Symbol(TokenConstants.DARROW); }
[\n\t\r\f]classfound>" "[A-Z][A-Za-z0-9_]*       	{ yybegin(YYINITIAL);System.out.println("TOK_KEY_CLASSNAME :"+yytext()); }
[\n\t\s\r\f][i|I][f|F][\n\t\s\r\f]					{ return new Symbol(TokenConstants.IF); }
[\n\t\s\r\f][f|F][i|I][\n\t\s\r\f]					{ return new Symbol(TokenConstants.FI);}
[\n\t\s\r\f][i|I][n|N][h|H][e|E][r|R][i|I][t|T][s|S][\n\t\s\r\f]	{ return new Symbol(TokenConstants.INHERITS);}
[\n\t\s\r\f][l|L][e|E][t|T][\n\t\s\r\f]					{ return new Symbol(TokenConstants.LET);}
[\n\t\s\r\f][c|C][l|L][a|A][s|S][\n\t\s\r\f]                        	{ yybegin(classfound);return new Symbol(TokenConstants.CLASS);}
[\n\t\s\r\f][n|N][e|E][w|W][\n\t\s\r\f]           	  	        { return new Symbol(TokenConstants.NEW);}
[\n\t\s\r\f][i|I][s|S][v|V][o|O][i|I][d|D][\n\t\s\r\f]                  { return new Symbol(TokenConstants.ISVOID);}
[\n\t\s\r\f][n|N][o|O][t|T][\n\t\s\r\f]			                { return new Symbol(TokenConstants.NOT); }
[\n\t\s\r\f][l|L][o|O][o|O][p|P][\n\t\s\r\f]			        { return new Symbol(TokenConstants.LOOP);}
[\n\t\s\r\f][i|I][n|N][\n\t\s\r\f]                      		{ return new Symbol(TokenConstants.IN);}
[\n\t\s\r\f][t|T][h|H][e|E][n|N][\n\t\s\r\f]            		{ return new Symbol(TokenConstants.THEN);}
[\n\t\s\r\f][e|E][l|L][s|S][e|E][\n\t\s\r\f]            		{ return new Symbol(TokenConstants.ELSE);}
[\n\t" "\r\f][c|C][a|A][s|S][e|E][\n\t" "\r\f]            		{ return new Symbol(TokenConstants.CASE);}
[\n\t\s\r\f][e|E][s|S][a|A][c|C][\n\t\s\r\f]            		{ return new Symbol(TokenConstants.ESAC);}
[\n\t\s\r\f][i|I][n|N][\n\t\s\r\f]					{ return new Symbol(TokenConstants.IN);}
[\n\t\s\r\f][o|O][f|F][\n\t\s\r\f]					{ return new Symbol(TokenConstants.OF);}
[\n\t\s\r\f][p|P][o|O][o|O][l|L][\n\t\s\r\f]				{ return new Symbol(TokenConstants.POOL);}
";"                             		{ return new Symbol(TokenConstants.SEMI);}
"<-"                            		{ return new Symbol(TokenConstants.ASSIGN);}
"("                             		{ return new Symbol(TokenConstants.LPAREN);}
")"                             		{ return new Symbol(TokenConstants.RPAREN);}
","                             		{ return new Symbol(TokenConstants.COMMA);}
":"                             		{ return new Symbol(TokenConstants.COLON);}
\.                              		{ return new Symbol(TokenConstants.DOT);}
\+ 						{ return new Symbol(TokenConstants.PLUS); }
\- 						{ return new Symbol(TokenConstants.MINUS); }
\* 						{ return new Symbol(TokenConstants.MULT); }
"/" 						{ return new Symbol(TokenConstants.DIV); }
"<"						{ return new Symbol(TokenConstants.LT); }
"<=" 						{ return new Symbol(TokenConstants.LE); }
"=="                            		{ return new Symbol(TokenConstants.EQ); }
"{" 						{ return new Symbol(TokenConstants.LBRACE); }
"}" 						{ return new Symbol(TokenConstants.RBRACE); }
[A-Za-z_]+[A-Za-z0-9_]* 			{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
[a-z_][A-Za-z0-9_]* 				{ 
							return new Symbol(TokenConstants.OBJECTID,AbstractTable.idtable.addString(yytext())); 
						}
[\"]+[.]*[\"] 					{ 
							
							return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(yytext())); 
						}
^[\-]?[0xX]?[0-9]*				{ 
							return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
						}









.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                
				System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
				return new Symbol(TokenConstants.ERROR); 
				} 
