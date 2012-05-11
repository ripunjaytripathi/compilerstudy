/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;
import java_cup.runtime.Scanner;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int classfound = 1;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0,
		187
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_START,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NOT_ACCEPT,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_START,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_START,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NOT_ACCEPT,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NOT_ACCEPT,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NOT_ACCEPT,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NOT_ACCEPT,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NOT_ACCEPT,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NOT_ACCEPT,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NOT_ACCEPT,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NOT_ACCEPT,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NOT_ACCEPT,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NOT_ACCEPT,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NOT_ACCEPT,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NOT_ACCEPT,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NOT_ACCEPT,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NOT_ACCEPT,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NOT_ACCEPT,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NOT_ACCEPT,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NOT_ACCEPT,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NOT_ACCEPT,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NOT_ACCEPT,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NOT_ACCEPT,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NOT_ACCEPT,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NOT_ACCEPT,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NOT_ACCEPT,
		/* 147 */ YY_NOT_ACCEPT,
		/* 148 */ YY_NOT_ACCEPT,
		/* 149 */ YY_NOT_ACCEPT,
		/* 150 */ YY_NOT_ACCEPT,
		/* 151 */ YY_NOT_ACCEPT,
		/* 152 */ YY_NOT_ACCEPT,
		/* 153 */ YY_NOT_ACCEPT,
		/* 154 */ YY_NOT_ACCEPT,
		/* 155 */ YY_NOT_ACCEPT,
		/* 156 */ YY_NOT_ACCEPT,
		/* 157 */ YY_NOT_ACCEPT,
		/* 158 */ YY_NOT_ACCEPT,
		/* 159 */ YY_NOT_ACCEPT,
		/* 160 */ YY_NOT_ACCEPT,
		/* 161 */ YY_NOT_ACCEPT,
		/* 162 */ YY_NOT_ACCEPT,
		/* 163 */ YY_NOT_ACCEPT,
		/* 164 */ YY_NOT_ACCEPT,
		/* 165 */ YY_NOT_ACCEPT,
		/* 166 */ YY_NOT_ACCEPT,
		/* 167 */ YY_NOT_ACCEPT,
		/* 168 */ YY_NOT_ACCEPT,
		/* 169 */ YY_NOT_ACCEPT,
		/* 170 */ YY_NOT_ACCEPT,
		/* 171 */ YY_NOT_ACCEPT,
		/* 172 */ YY_NOT_ACCEPT,
		/* 173 */ YY_NOT_ACCEPT,
		/* 174 */ YY_NOT_ACCEPT,
		/* 175 */ YY_NOT_ACCEPT,
		/* 176 */ YY_NOT_ACCEPT,
		/* 177 */ YY_NOT_ACCEPT,
		/* 178 */ YY_NOT_ACCEPT,
		/* 179 */ YY_NOT_ACCEPT,
		/* 180 */ YY_NOT_ACCEPT,
		/* 181 */ YY_NOT_ACCEPT,
		/* 182 */ YY_NOT_ACCEPT,
		/* 183 */ YY_NOT_ACCEPT,
		/* 184 */ YY_NOT_ACCEPT,
		/* 185 */ YY_NOT_ACCEPT,
		/* 186 */ YY_NOT_ACCEPT,
		/* 187 */ YY_NOT_ACCEPT,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NOT_ACCEPT,
		/* 190 */ YY_NOT_ACCEPT,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NO_ANCHOR,
		/* 193 */ YY_NOT_ACCEPT,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NOT_ACCEPT,
		/* 197 */ YY_NOT_ACCEPT,
		/* 198 */ YY_NOT_ACCEPT,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NOT_ACCEPT,
		/* 202 */ YY_NOT_ACCEPT,
		/* 203 */ YY_NOT_ACCEPT,
		/* 204 */ YY_NOT_ACCEPT,
		/* 205 */ YY_NOT_ACCEPT,
		/* 206 */ YY_NO_ANCHOR,
		/* 207 */ YY_NOT_ACCEPT,
		/* 208 */ YY_NOT_ACCEPT,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NOT_ACCEPT,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NOT_ACCEPT,
		/* 213 */ YY_NOT_ACCEPT,
		/* 214 */ YY_NOT_ACCEPT,
		/* 215 */ YY_NOT_ACCEPT,
		/* 216 */ YY_NOT_ACCEPT
	};
	private int yy_cmap[] = unpackFromString(1,130,
"59:9,60,3,59,60,3,59:18,13,59,55,59:5,44,45,50,49,46,43,48,51,15:10,47,41,4" +
"2,1,2,59:2,32,14,31,38,23,19,14,21,16,14:2,30,14,20,37,39,14,25,29,27,14,35" +
",33,56,14:2,59:4,54,59,6,54,4,12,24,8,54,22,17,54:2,5,54,11,9,40,54,26,7,28" +
",10,36,34,57,54:2,52,18,53,59:2,58,0")[0];

	private int yy_rmap[] = unpackFromString(1,217,
"0,1,2,3,1,4,1:11,5,1:4,6,1:16,7,8,1,9,10,1,3:2,11,3,12,3:2,13,3:7,14,15,16," +
"17,3:3,18,6,19,20,8,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,3" +
"9,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,6" +
"4,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,8" +
"9,90,91,92,93,94,95,96,97,98,99,11,100,101,102,12,103,104,105,106,107,108,1" +
"09,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127," +
"128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146" +
",147,148,149,150,151,152,153,154,155,156,157,158,159,135,160,161,162")[0];

	private int yy_nxt[][] = unpackFromString(163,61,
"1,2,41,40,3:3,42,3:5,61,3,41,3:2,41,3:22,4,5,6,7,8,9,10,11,12,13,14,15,16,3" +
",68,3:2,17,41,71,-1:62,18,19,-1:62,3:9,-1,3:4,-1,3:22,-1:13,3,-1,3:2,-1:4,2" +
"0,-1:41,21,-1:32,43,-1:27,63,-1:12,43:2,-1:51,90,-1:6,22,-1:9,39:9,-1,39:4," +
"-1,39:22,-1:13,39,-1,39:2,-1:7,60,67,-1:2,70,73,-1,190,-1:4,76:2,78,70,190," +
"-1:2,80:2,-1:2,82:2,-1,67,189,-1:5,73,-1,84:2,-1:24,62,69,3:2,72,75,3,191,3" +
",-1,3:2,77:2,86,72,191,3:2,79:2,3:2,81:2,3,69,62,3:5,75,3,83:2,-1:13,3,-1,3" +
":2,-1:18,43,-1:63,166,-1:4,166:2,-1:39,30,-1:3,30,-1:52,30,-1:8,175,-1:57,2" +
"14,92,-1:11,94,-1:11,96,-1,92,-1:32,88,-1:13,88,-1:12,88,-1:33,3,85,3:7,-1," +
"3:4,96,3:11,85,3:10,-1:13,3,-1,3:2,-1:18,43,-1:40,43:2,-1:12,193,-1:8,98,-1" +
":4,100:2,-1:12,193,-1:27,3:5,192,3:3,-1,3:4,98,3:4,87:2,3:12,192,3:3,-1:13," +
"3,-1,3:2,-1:19,102:3,-1:46,3:9,-1,3:2,89:2,102,3:22,-1:13,3,-1,3:2,-1:11,10" +
"4,-1:9,104:2,-1:42,18,-1:63,3:4,91,3:4,-1,3:4,104,91,3:21,-1:13,3,-1,3:2,-1" +
":10,110,112,-1:2,114,-1:6,116,112,114,-1:8,110,-1:35,3:3,95,97,3:2,99,3,-1," +
"3:4,116,97,99,3:8,95,3:11,-1:13,3,-1,3:2,-1:8,216,92,118,112,197,-1,114,-1:" +
"4,102:2,120,112,114,122:2,201:2,-1:4,118,216,-1,92,-1:4,197,-1:27,3,101,3,1" +
"94,3:5,-1,3:4,126,3:10,194,101,3:10,-1:13,3,-1,3:2,-1:8,215,-1,124,-1:10,12" +
"6,-1:10,124,215,-1:34,3:9,-1,3:4,122,3:2,103:2,3:18,-1:13,3,-1,3:2,-1:21,12" +
"2,-1:2,122:2,-1:42,3:5,206,3:3,-1,3:4,207,3:18,206,3:3,-1:13,3,-1,3:2,-1:12" +
",207,-1:8,207,-1:18,207,-1:27,3:2,199,3:6,-1,3:4,208,3:13,199,3:8,-1:13,3,-" +
"1,3:2,-1:8,216,-1,118,112,197,-1,114,-1:4,102:2,202,112,114,122:2,201:2,-1:" +
"4,118,216,-1:6,197,-1:27,3:9,-1,3:4,134,3:8,107:2,3:12,-1:13,3,-1,3:2,-1:9," +
"92,-1:11,92,-1:13,92,-1:31,23,3:3,45,3:5,-1,3:4,-1,3:22,-1:13,3,-1,3:2,-1:2" +
",23,-1:48,90,-1:6,44,-1:8,24,3:3,46,3:5,-1,3:4,-1,3:22,-1:13,3,-1,3:2,-1:2," +
"24,-1:7,203,-1:10,203,-1:10,203,-1:35,3:9,-1,3:4,138,3:14,111:2,3:6,-1:13,3" +
",-1,3:2,-1:9,208,203,-1:10,128,-1:10,203,-1:2,208,-1:32,3:9,-1,3:4,212,3:16" +
",211:2,3:4,-1:13,3,-1,3:2,-1:9,208,-1:11,208,-1:13,208,-1:31,25,3:3,64,3:5," +
"-1,3:4,-1,3:22,-1:13,3,-1,3:2,-1:2,25,-1:9,130,-1:8,132,-1:8,134:2,-1:8,130" +
",-1:26,26,3:3,48,3:5,-1,3:4,210,3:2,200:2,3:18,-1:13,3,-1,3:2,-1:2,26,-1:18" +
",134,-1:8,134:2,-1:36,3:3,209,3:5,-1,3:4,213,3:10,209,3:11,-1:13,3,-1,3:2,-" +
"1:6,23,-1:3,23,-1:52,23,-1:4,3:9,-1,3:4,150,3:4,115:2,3:16,-1:13,3,-1,3:2,-" +
"1:6,24,-1:3,24,-1:52,24,-1:4,3:9,-1,3:4,158,3:20,121:2,-1:13,3,-1,3:2,-1:21" +
",138,-1:8,136:2,-1:4,138:2,-1:29,27,3:3,65,3:5,-1,3:4,-1,3:22,-1:13,3,-1,3:" +
"2,-1:2,27,-1:18,138,-1:14,138:2,-1:29,28,3:3,50,3:5,-1,3:4,-1,3:22,-1:13,3," +
"-1,3:2,-1:2,28,-1:18,212,-1:16,212:2,-1:27,29,3:3,51,3:5,-1,3:4,-1,3:22,-1:" +
"13,3,-1,3:2,-1:2,29,-1:3,25,-1:3,25,-1:52,25,-1:4,129,3:8,-1,3:4,163,3:12,1" +
"29,3:9,-1:13,3,-1,3:2,-1:6,26,-1:3,26,-1:10,210,-1:2,210:2,-1:37,26,-1:4,3:" +
"7,131,3,-1,3:4,167,3,131,3:20,-1:13,3,-1,3:2,-1:6,25,-1:3,25,-1:10,140,-1:2" +
",210:2,-1:12,212:2,-1:23,25,-1:4,3,133,3:7,-1,3:4,165,3:11,133,3:10,-1:13,3" +
",-1,3:2,-1:9,142,-1:11,144,-1:13,142,-1:2,212:2,-1:27,30,3:3,66,3:5,-1,3:4," +
"-1,3:22,-1:13,3,-1,3:2,-1:2,30,-1:3,25,-1:2,148,47,-1,146,-1:8,149,-1:2,210" +
":2,150:2,-1:2,134:2,151,-1:2,148,138:2,212:2,146,-1:22,25,-1:3,32,3:3,53,3:" +
"5,-1,3:4,-1,3:22,-1:13,3,-1,3:2,-1:2,32,-1:18,150,-1:4,150:2,-1:40,3:9,-1,3" +
":2,135:2,172,3:22,-1:13,3,-1,3:2,-1:9,142,-1:11,142,-1:13,142,-1:32,3:9,-1," +
"3:4,173,3:6,137:2,3:14,-1:13,3,-1,3:2,-1:9,142,213,-1:10,152,-1:10,213,-1:2" +
",142,-1:31,33,3:3,54,3:5,-1,3:4,-1,3:22,-1:13,3,-1,3:2,-1:2,33,-1:7,155,-1:" +
"10,157,-1:4,156:2,-1:4,155,-1:34,34,3:3,55,3:5,-1,3:4,-1,3:22,-1:13,3,-1,3:" +
"2,-1:2,34,-1:18,158,-1:20,158:2,-1:23,36,3:3,57,3:5,-1,3:4,-1,3:22,-1:13,3," +
"-1,3:2,-1:2,36,-1:3,27,-1:3,27,-1:10,158,-1:20,158:2,-1:19,27,-1:3,35,3:3,5" +
"6,3:5,-1,3:4,-1,3:22,-1:13,3,-1,3:2,-1:2,35,-1:3,27,-1:3,27,-1:52,27,-1:4,3" +
":8,139,-1,3:4,176,3:19,139,3:2,-1:13,3,-1,3:2,-1:6,28,-1:3,28,-1:52,28,-1:4" +
",3:9,-1,3:2,141:2,177,3:22,-1:13,3,-1,3:2,-1:6,29,-1:3,29,-1:52,29,-1:3,37," +
"3:3,58,3:5,-1,3:4,-1,3:22,-1:13,3,-1,3:2,-1:2,37,-1:9,159,-1:8,161,-1:4,160" +
":2,-1:12,159,-1:27,3:9,-1,3:4,180,3:8,143:2,3:12,-1:13,3,-1,3:2,-1:7,163,-1" +
":13,163,-1:12,163,-1:33,3:3,145,3:5,-1,3:4,182,3:10,145,3:11,-1:13,3,-1,3:2" +
",-1:7,163,-1:4,159,-1:8,164,-1:12,163,-1:5,159,-1:26,38,3:3,59,3:5,-1,3:4,-" +
"1,3:22,-1:13,3,-1,3:2,-1:2,38,-1:5,165,-1:12,158,-1:11,165,-1:8,158:2,-1:23" +
",28,-1,165,-1,28,-1:10,158,-1:11,165,-1:8,158:2,-1:19,28,-1:4,163,-1:2,155," +
"-1:10,155,-1:10,155,-1,163,-1:32,27,163,165,-1,49,-1,159,-1,167,-1:6,168,-1" +
",167,-1:2,169:2,-1:4,155,165,163,-1:5,159,-1,158:2,-1:19,27,-1:11,167,-1:6," +
"167,-1,167,-1:44,163,-1:13,162,-1:4,162:2,-1:6,163,-1:34,165,-1:12,165,-1:1" +
"1,165,-1:33,30,-1:3,52,-1:52,30,-1:3,31,-1:9,31,-1:46,31,-1:3,30,-1:3,30,-1" +
":5,31,-1:46,30,-1:3,32,-1:3,32,-1:52,32,-1:16,172:3,-1:60,173,-1:6,173:2,-1" +
":50,172:2,174,-1:6,173:2,-1:37,33,-1:3,33,-1:52,33,-1:3,34,-1:3,34,-1:52,34" +
",-1:3,34,-1:3,34,-1:8,172:3,-1:41,34,-1:3,35,-1:3,35,-1:52,35,-1:3,33,-1:3," +
"33,-1:5,31,-1:46,33,-1:3,36,-1:3,36,-1:52,36,-1:3,30,-1:3,30,-1:5,31,-1:2,1" +
"72:2,174,-1:6,173:2,-1:33,30,-1:3,33,-1:3,33,-1:5,31,-1:4,173,-1:6,173:2,-1" +
":33,33,-1:3,30,-1:3,30,-1:8,172:2,174,-1:6,173:2,-1:33,30,-1:3,33,-1:3,33,-" +
"1:10,173,-1:6,173:2,-1:33,33,-1:12,176,-1:5,176,-1:19,176,-1:38,177:3,-1:54" +
",176,-1:3,177:2,178,-1:19,176,-1:31,179,-1:54,37,-1:3,37,-1:52,37,-1:18,180" +
",-1:8,180:2,-1:35,37,-1:3,37,-1:10,180,-1:8,180:2,-1:31,37,-1:10,181,-1:57," +
"182,-1:10,182,-1:10,182,-1:42,183,-1:52,38,-1:3,38,-1:52,38,-1:12,184,-1:50" +
",185,-1:71,186,-1:61,39,-1,39,-1:2,39:3,-1,39,-1,39,-1,39,-1,39:5,-1,39,-1," +
"39:3,-1:16,39,-1:4,1,74,41,40,3:3,42,3:5,61,3,41,3:2,41,3:22,4,5,6,7,8,9,10" +
",11,12,13,14,15,16,3,68,3:2,17,41,71,-1:18,162,-1:4,162:2,-1:41,96,92,-1:11" +
",94,-1:11,96,-1,92,-1:37,198,-1:8,106,-1:4,108:2,-1:12,198,-1:27,3:5,195,3:" +
"3,-1,3:4,106,3:4,93:2,3:12,195,3:3,-1:13,3,-1,3:2,-1:7,3:5,105,3:3,-1,3:4,1" +
"30,3:18,105,3:3,-1:13,3,-1,3:2,-1:12,130,-1:8,130,-1:18,130,-1:27,3:2,113,3" +
":6,-1,3:4,142,3:13,113,3:8,-1:13,3,-1,3:2,-1:7,3:9,-1,3:4,136,3:8,109:2,3:1" +
"2,-1:13,3,-1,3:2,-1:10,154,-1:10,155,-1:10,155,-1:40,146,-1:8,147,-1:8,136:" +
"2,-1:8,146,-1:41,136,-1:8,136:2,-1:36,3:3,119,3:5,-1,3:4,155,3:10,119,3:11," +
"-1:13,3,-1,3:2,-1:7,3:9,-1,3:4,160,3:4,125:2,3:16,-1:13,3,-1,3:2,-1:21,134," +
"-1:8,134:2,-1:4,138:2,-1:29,25,-1:2,148,188,-1,146,-1:8,205,-1:2,210:2,150:" +
"2,-1:2,134:2,213,-1:2,148,138:2,212:2,146,-1:22,25,-1:18,156,-1:4,156:2,-1:" +
"43,155,-1:10,155,-1:4,162:2,-1:4,155,-1:34,27,163,165,-1,49,-1,159,-1,167,-" +
"1:6,170,-1,167,-1:2,171:2,-1:4,155,165,163,-1:5,159,-1,158:2,-1:19,27,-1:4," +
"3:5,117,3:3,-1,3:4,153,3:18,117,3:3,-1:13,3,-1,3:2,-1:12,153,-1:8,153,-1:18" +
",153,-1:30,155,-1:10,155,-1:10,155,-1:35,3:9,-1,3:4,162,3:4,127:2,3:16,-1:1" +
"3,3,-1,3:2,-1:21,160,-1:4,160:2,-1:40,3:5,123,3:3,-1,3:4,159,3:18,123,3:3,-" +
"1:13,3,-1,3:2,-1:12,159,-1:8,159,-1:18,159,-1:29,196,-1:11,208,-1:13,208,-1" +
":35,213,-1:10,213,-1:10,213,-1:37,208,213,-1:10,204,-1:10,213,-1:2,208,-1:2" +
"8");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
				System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
				return new Symbol(TokenConstants.ERROR); 
				}
					case -3:
						break;
					case 3:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.SEMI);}
					case -5:
						break;
					case 5:
						{ return new Symbol(TokenConstants.LT); }
					case -6:
						break;
					case 6:
						{ return new Symbol(TokenConstants.MINUS); }
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.LPAREN);}
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.RPAREN);}
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.COMMA);}
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.COLON);}
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.DOT);}
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.PLUS); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.MULT); }
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.DIV); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -17:
						break;
					case 17:
						{ 
							return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
						}
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.EQ); }
					case -19:
						break;
					case 19:
						{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  		  return new Symbol(TokenConstants.DARROW); }
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.LE); }
					case -21:
						break;
					case 21:
						{ return new Symbol(TokenConstants.ASSIGN);}
					case -22:
						break;
					case 22:
						{ 
							return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(yytext())); 
						}
					case -23:
						break;
					case 23:
						{ return new Symbol(TokenConstants.FI);}
					case -24:
						break;
					case 24:
						{ return new Symbol(TokenConstants.OF);}
					case -25:
						break;
					case 25:
						{ return new Symbol(TokenConstants.IF); }
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.IN);}
					case -27:
						break;
					case 27:
						{ return new Symbol(TokenConstants.LET);}
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.NOT); }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.NEW);}
					case -30:
						break;
					case 30:
						{ yybegin(classfound);return new Symbol(TokenConstants.CLASS);}
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.CASE);}
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.LOOP);}
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.ELSE);}
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.ESAC);}
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.POOL);}
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.THEN);}
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.ISVOID);}
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.INHERITS);}
					case -39:
						break;
					case 39:
						{ yybegin(YYINITIAL);System.out.println("TOK_KEY_CLASSNAME :"+yytext()); }
					case -40:
						break;
					case 41:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
				System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
				return new Symbol(TokenConstants.ERROR); 
				}
					case -41:
						break;
					case 42:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -42:
						break;
					case 43:
						{ 
							return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
						}
					case -43:
						break;
					case 44:
						{ 
							return new Symbol(TokenConstants.STR_CONST,AbstractTable.stringtable.addString(yytext())); 
						}
					case -44:
						break;
					case 45:
						{ return new Symbol(TokenConstants.FI);}
					case -45:
						break;
					case 46:
						{ return new Symbol(TokenConstants.OF);}
					case -46:
						break;
					case 47:
						{ return new Symbol(TokenConstants.IF); }
					case -47:
						break;
					case 48:
						{ return new Symbol(TokenConstants.IN);}
					case -48:
						break;
					case 49:
						{ return new Symbol(TokenConstants.LET);}
					case -49:
						break;
					case 50:
						{ return new Symbol(TokenConstants.NOT); }
					case -50:
						break;
					case 51:
						{ return new Symbol(TokenConstants.NEW);}
					case -51:
						break;
					case 52:
						{ yybegin(classfound);return new Symbol(TokenConstants.CLASS);}
					case -52:
						break;
					case 53:
						{ return new Symbol(TokenConstants.LOOP);}
					case -53:
						break;
					case 54:
						{ return new Symbol(TokenConstants.ELSE);}
					case -54:
						break;
					case 55:
						{ return new Symbol(TokenConstants.ESAC);}
					case -55:
						break;
					case 56:
						{ return new Symbol(TokenConstants.POOL);}
					case -56:
						break;
					case 57:
						{ return new Symbol(TokenConstants.THEN);}
					case -57:
						break;
					case 58:
						{ return new Symbol(TokenConstants.ISVOID);}
					case -58:
						break;
					case 59:
						{ return new Symbol(TokenConstants.INHERITS);}
					case -59:
						break;
					case 61:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
				System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
				return new Symbol(TokenConstants.ERROR); 
				}
					case -60:
						break;
					case 62:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -61:
						break;
					case 63:
						{ 
							return new Symbol(TokenConstants.INT_CONST, AbstractTable.inttable.addString(yytext()));
						}
					case -62:
						break;
					case 64:
						{ return new Symbol(TokenConstants.IF); }
					case -63:
						break;
					case 65:
						{ return new Symbol(TokenConstants.LET);}
					case -64:
						break;
					case 66:
						{ yybegin(classfound);return new Symbol(TokenConstants.CLASS);}
					case -65:
						break;
					case 68:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
				System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
				return new Symbol(TokenConstants.ERROR); 
				}
					case -66:
						break;
					case 69:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -67:
						break;
					case 71:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
				System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
				return new Symbol(TokenConstants.ERROR); 
				}
					case -68:
						break;
					case 72:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -69:
						break;
					case 74:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
				System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
				return new Symbol(TokenConstants.ERROR); 
				}
					case -70:
						break;
					case 75:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -71:
						break;
					case 77:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -72:
						break;
					case 79:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -73:
						break;
					case 81:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -74:
						break;
					case 83:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -75:
						break;
					case 85:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -76:
						break;
					case 87:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -77:
						break;
					case 89:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -78:
						break;
					case 91:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -79:
						break;
					case 93:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -80:
						break;
					case 95:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -81:
						break;
					case 97:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -82:
						break;
					case 99:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -83:
						break;
					case 101:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -84:
						break;
					case 103:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -85:
						break;
					case 105:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -86:
						break;
					case 107:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -87:
						break;
					case 109:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -88:
						break;
					case 111:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -89:
						break;
					case 113:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -90:
						break;
					case 115:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -91:
						break;
					case 117:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -92:
						break;
					case 119:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -93:
						break;
					case 121:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -94:
						break;
					case 123:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -95:
						break;
					case 125:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -96:
						break;
					case 127:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -97:
						break;
					case 129:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -98:
						break;
					case 131:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -99:
						break;
					case 133:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -100:
						break;
					case 135:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -101:
						break;
					case 137:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -102:
						break;
					case 139:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -103:
						break;
					case 141:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -104:
						break;
					case 143:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -105:
						break;
					case 145:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -106:
						break;
					case 188:
						{ return new Symbol(TokenConstants.IF); }
					case -107:
						break;
					case 191:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -108:
						break;
					case 192:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -109:
						break;
					case 194:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -110:
						break;
					case 195:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -111:
						break;
					case 199:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -112:
						break;
					case 200:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -113:
						break;
					case 206:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -114:
						break;
					case 209:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -115:
						break;
					case 211:
						{ 
							return new Symbol(TokenConstants.TYPEID, AbstractTable.idtable.addString(yytext())); 
						}
					case -116:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
