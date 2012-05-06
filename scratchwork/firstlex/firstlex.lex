import java.lang.System;

class firstlex{
    public static void main(String argv[]) throws java.io.IOException {
    Yylex yy = new Yylex(System.in);
    yy.yylex();
  }
}


%%
%{
private int num_lines = 0;
private int num_chars = 0;
%}
%integer
%state COMMENT
%%
\n			{ System.out.print("TOK_NL \n");num_lines++; }
\t			{ System.out.print("TOK_TAB \n");num_chars++; }		
" "			{ /*System.out.print("TOK_SP \n");*/num_chars++; }	
"int "                  { System.out.print("TOK_DT_INT \n"); }
"float "                { System.out.print("TOK_DT_REAL \n"); }
"char "                 { System.out.print("TOK_DT_STR \n"); }
"if"                    { System.out.print("TOK_KEY_IF \n"); }
"else"			{ System.out.print("TOK_KEY_ELSE \n"); }
"then"			{ System.out.print("TOK_KEY_THEN \n"); }
"fi"			{ System.out.print("TOK_KEY_FI \n"); }
[A-Za-z]+[A-Za-z0-9]*    { System.out.println("TOK_IDENTIFIER: " + yytext()); }
"("			{ System.out.print("TOK_KEY_OPENSMALL \n"); }
")"			{ System.out.print("TOK_KEY_CLOSESMALL \n"); }
"/"                     { System.out.print("TOK_OP_DIV \n"); }
"+"                     { System.out.print("TOK_OP_ADD \n"); }
"-"                     { System.out.print("TOK_OP_SUB \n"); }
">"                     { System.out.print("TOK_OP_GRT \n"); }
"<"                     { System.out.print("TOK_OP_LST \n"); }
";"                     { System.out.print("TOK_TERM \n"); }
"="                     { System.out.print("TOK_ASSGN \n"); }
"=="                    { System.out.print("TOK_OP_COMP \n"); }
"{"			{ System.out.print("TOK_OPENCURLY \n"); }
"}"			{ System.out.print("TOK_CLOSECURLY \n"); }	
","			{ System.out.print("TOK_COMMA \n"); }
[1-9]+[0-9]*      { System.out.println("TOK_INT: "+ yytext()); }
.			{ System.out.println("TOK_SINCH : "+yytext()); num_chars++; }


