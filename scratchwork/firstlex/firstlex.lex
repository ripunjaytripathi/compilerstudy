import java.lang.System;

class Sample {
    public static void main(String argv[]) throws java.io.IOException {
    Yylex yy = new Yylex(System.in);
    yy.yylex();
  }
}


%%

ystem.out.print("TOK_OP_COMP \n"); }
private int num_lines = 0;
private int num_chars = 0;
%}
%integer
%%
"int "                  { System.out.print("TOK_DT_INT \n"); }
"flaot "                { System.out.print("TOK_DT_REAL \n"); }
"char "                 { System.out.print("TOK_DT_STR \n"); }
"if"                    { System.out.print("TOK_KEY_IF \n"); }
[A-Za-z][A-Za-z0-9]+    { System.out.print("TOK_VAR: " + yytext()); }
"/"                     { System.out.print("TOK_OP_DIV \n"); }
"+"                     { System.out.print("TOK_OP_ADD \n"); }
"-"                     { System.out.print("TOK_OP_SUB \n"); }
">"                     { System.out.print("TOK_OP_RTSHF \n"); }
"<"                     { System.out.print("TOK_OP_LFSHF \n"); }
";"                     { System.out.print("TOK_TERM \n"); }
"="                     { System.out.print("TOK_ASSGN \n"); }
"=="                    { System.out.print("TOK_OP_COMP \n"); }
"{"			{ System.out.print("TOK_OPENCURLY \n"); }
"}"			{ System.out.print("TOK_CLOSECURLY \n"); }	
[1-9][0-9]*             { System.out.print("TOK_DIG \n"); }
