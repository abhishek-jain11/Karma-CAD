// $ANTLR 2.7.1: "pathExtractorM.g" -> "PathExtractorM.java"$

	package jsdai.mappingUtils.paths;
	import java.io.*;
	import antlr.*;

import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import antlr.CharScanner;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;

public class PathExtractorM extends antlr.CharScanner implements PathExtractorMTokenTypes, TokenStream
 {

	static public final int NO_MAPPING = 0;
	static public final int ENTITY_MAPPING = 1;
	static public final int ATTRIBUTE_MAPPING = 2;
	private boolean done = false;
	private PrintWriter out;
	private int mappingType = NO_MAPPING;
	private boolean isEntityMapping = false;
	private String attributeTargetTypeName = null;
	private String extId = null;

	public void uponEOF() throws TokenStreamException, CharStreamException {
        done = true;
	}

	public static void main(String[] args) throws Exception {
		try {
			File sourceFiles[] = new File(args[0]).listFiles();
			String armSchemaName = args.length == 4 ? args[2].toUpperCase() : "AP214_ARM";
			String aimSchemaName = args.length == 4 ? args[3].toUpperCase() : "AUTOMOTIVE_DESIGN";
			String armSchemaNameLc = armSchemaName.toLowerCase();

			PrintWriter mainOut = (
				new PrintWriter(new FileWriter(new File(args[1], armSchemaNameLc + ".path"))));
			mainOut.println("schema_mapping " + armSchemaNameLc + " (" + 
				armSchemaName + ", " + aimSchemaName + ");");
// 			PrintWriter mainOut = new PrintWriter(new FileWriter(new File(args[1], "ap212.path")));
// 			mainOut.println("schema_mapping ap212_arm (AP212_ARM, ELECTROTECHNICAL_DESIGN);");
			mainOut.println();
			for(int i = 0; i < sourceFiles.length; i++) {
				File sourceFile = sourceFiles[i];
				String targetFileName = sourceFile.getName();
				if(!targetFileName.endsWith(".m")) continue;
				targetFileName = targetFileName.substring(0, targetFileName.length() - 1) + "path";
				File targetFile = new File(args[1], targetFileName);
				PathExtractorM lexer = new PathExtractorM(new FileReader(sourceFile));
				lexer.setFilename(sourceFile.getCanonicalPath());
				lexer.out = new PrintWriter(new FileWriter(targetFile));
				while ( !lexer.done ) {
					lexer.nextToken();
				}
				if(lexer.isEntityMapping) {
					lexer.out.print(" end_entity_mapping;");
				}
				lexer.out.print(" end_uof_mapping;"); 
				lexer.out.close();
				mainOut.println("include '" + targetFileName + "';"); 
			}
			mainOut.println();
			mainOut.println("end_schema_mapping;");
			mainOut.close();
		}
		catch(TokenStreamRecognitionException e) {
			System.err.println(e.recog);
		}
		catch(TokenStreamException e) {
			System.err.println(e);
		}
	}
public PathExtractorM(InputStream in) {
	this(new ByteBuffer(in));
}
public PathExtractorM(Reader in) {
	this(new CharBuffer(in));
}
public PathExtractorM(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public PathExtractorM(LexerSharedInputState state) {
	super(state);
	literals = new Hashtable();
caseSensitiveLiterals = true;
setCaseSensitive(true);
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		setCommitToPath(false);
		int _m;
		_m = mark();
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				if ((LA(1)=='$')) {
					mINSTR_START(true);
					theRetToken=_returnToken;
				}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {
					commit();
					try {mSCARF(false);}
					catch(RecognitionException e) {
						// catastrophic failure
						reportError(e);
						consume();
					}
					continue tryAgain;
				}
				}
				
				commit();
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_ttype = testLiteralsTable(_ttype);
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				if ( !getCommitToPath() ) {
					rewind(_m);
					resetText();
					try {mSCARF(false);}
					catch(RecognitionException ee) {
						// horrendous failure: error in filter rule
						reportError(ee);
						consume();
					}
					continue tryAgain;
				}
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mINSTR_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INSTR_START;
		int _saveIndex;
		
		if (!( getColumn() == 1))
		  throw new SemanticException(" getColumn() == 1");
		match("$");
		{
		switch ( LA(1)) {
		case 'u':
		{
			mUOF_MAPPING(false);
			break;
		}
		case 'r':
		{
			mPATH(false);
			break;
		}
		case 'e':
		{
			mEND(false);
			break;
		}
		default:
			if ((LA(1)=='a') && (LA(2)=='e')) {
				mENTITY_ATTRIBUTE_MAPPING(false);
			}
			else if ((LA(1)=='a') && (LA(2)=='i')) {
				mAIM_ELEMENT(false);
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mUOF_MAPPING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = UOF_MAPPING;
		int _saveIndex;
		Token id=null;
		
		match("uo%");
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		mID(true);
		id=_returnToken;
		match("@");
		if ( inputState.guessing==0 ) {
			out.print(" uof_mapping " + id.getText() + " (" + id.getText() + ");");
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mENTITY_ATTRIBUTE_MAPPING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ENTITY_ATTRIBUTE_MAPPING;
		int _saveIndex;
		Token s1=null;
		Token s2=null;
		Token s3=null;
		
		match("ae%");
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		mID(true);
		s1=_returnToken;
		{
		switch ( LA(1)) {
		case '\t':  case '\n':  case '\r':  case ' ':
		case '(':
		{
			{
			int _cnt7=0;
			_loop7:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					mWS(false);
				}
				else {
					if ( _cnt7>=1 ) { break _loop7; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
				}
				
				_cnt7++;
			} while (true);
			}
			match("to");
			{
			int _cnt9=0;
			_loop9:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					mWS(false);
				}
				else {
					if ( _cnt9>=1 ) { break _loop9; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
				}
				
				_cnt9++;
			} while (true);
			}
			mID(true);
			s2=_returnToken;
			{
			int _cnt11=0;
			_loop11:
			do {
				if ((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2)))) {
					mWS(false);
				}
				else {
					if ( _cnt11>=1 ) { break _loop11; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
				}
				
				_cnt11++;
			} while (true);
			}
			match("(as");
			{
			int _cnt13=0;
			_loop13:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					mWS(false);
				}
				else {
					if ( _cnt13>=1 ) { break _loop13; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
				}
				
				_cnt13++;
			} while (true);
			}
			mID(true);
			s3=_returnToken;
			match(")");
			break;
		}
		case '@':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		}
		match("@");
		if ( inputState.guessing==0 ) {
				String sText1 = s1.getText();
						if(Character.isUpperCase(sText1.charAt(0))) {
							if(isEntityMapping) {
								out.print(" end_entity_mapping;");
							}
							out.print(" entity_mapping " + sText1.toLowerCase() + " (" + sText1.toLowerCase());
							mappingType = ENTITY_MAPPING;
							isEntityMapping = true;
						} else {
							mappingType = ATTRIBUTE_MAPPING;
							if(s2 == null) {
								out.print(" attribute_mapping " + sText1 + " (" + sText1);
								attributeTargetTypeName = null;
							} else {
								out.print(" attribute_mapping " + s3.getText() + "_" + s2.getText() + 
									" (" + s3.getText());
								attributeTargetTypeName = s2.getText();
							}
						}
						extId = null;
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mAIM_ELEMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = AIM_ELEMENT;
		int _saveIndex;
		
		match("ai%");
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		if ( inputState.guessing==0 ) {
			out.print(", ");
		}
		mEXTSTRING(false,false);
		if ( inputState.guessing==0 ) {
				if(mappingType == ATTRIBUTE_MAPPING && attributeTargetTypeName != null) {
							out.print(", " + attributeTargetTypeName);
						}
						out.print(");");
					
		}
		match("@");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mPATH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PATH;
		int _saveIndex;
		
		match("rp%");
		if ( inputState.guessing==0 ) {
				setCommitToPath(true);
		}
		{
		switch ( LA(1)) {
		case '\u0003':  case '\u0004':  case '\u0005':  case '\u0006':
		case '\u0007':  case '\u0008':  case '\t':  case '\n':
		case '\u000b':  case '\u000c':  case '\r':  case '\u000e':
		case '\u000f':  case '\u0010':  case '\u0011':  case '\u0012':
		case '\u0013':  case '\u0014':  case '\u0015':  case '\u0016':
		case '\u0017':  case '\u0018':  case '\u0019':  case '\u001a':
		case '\u001b':  case '\u001c':  case '\u001d':  case '\u001e':
		case '\u001f':  case ' ':  case '!':  case '"':
		case '#':  case '$':  case '%':  case '&':
		case '\'':  case '(':  case ')':  case '*':
		case '+':  case ',':  case '-':  case '.':
		case '/':  case '0':  case '1':  case '2':
		case '3':  case '4':  case '5':  case '6':
		case '7':  case '8':  case '9':  case ':':
		case ';':  case '<':  case '=':  case '>':
		case '?':  case 'A':  case 'B':  case 'C':
		case 'D':  case 'E':  case 'F':  case 'G':
		case 'H':  case 'I':  case 'J':  case 'K':
		case 'L':  case 'M':  case 'N':  case 'O':
		case 'P':  case 'Q':  case 'R':  case 'S':
		case 'T':  case 'U':  case 'V':  case 'W':
		case 'X':  case 'Y':  case 'Z':  case '[':
		case '\\':  case ']':  case '^':  case '_':
		case '`':  case 'a':  case 'b':  case 'c':
		case 'd':  case 'e':  case 'f':  case 'g':
		case 'h':  case 'i':  case 'j':  case 'k':
		case 'l':  case 'm':  case 'n':  case 'o':
		case 'p':  case 'q':  case 'r':  case 's':
		case 't':  case 'u':  case 'v':  case 'w':
		case 'x':  case 'y':  case 'z':  case '{':
		case '|':  case '}':  case '~':  case '\u007f':
		{
			if ( inputState.guessing==0 ) {
					if(mappingType == ENTITY_MAPPING) {
									out.print(" mapping_constraints; ");
								}
								if(extId != null) {
									out.print("(" + extId + ")(");
								}
							
			}
			mEXTSTRING(false,true);
			if ( inputState.guessing==0 ) {
					if(extId != null) {
									out.print(" )");
								}
								if(mappingType == ENTITY_MAPPING) {
									out.print(" end_mapping_constraints;");
								}
							
			}
			break;
		}
		case '@':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		}
		match("@");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mEND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = END;
		int _saveIndex;
		
		match("end");
		if ( inputState.guessing==0 ) {
				if(mappingType == ATTRIBUTE_MAPPING) {
							out.print(" end_attribute_mapping;");
						}
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ID;
		int _saveIndex;
		
		mLETTER(false);
		{
		_loop55:
		do {
			switch ( LA(1)) {
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':  case '_':  case 'a':
			case 'b':  case 'c':  case 'd':  case 'e':
			case 'f':  case 'g':  case 'h':  case 'i':
			case 'j':  case 'k':  case 'l':  case 'm':
			case 'n':  case 'o':  case 'p':  case 'q':
			case 'r':  case 's':  case 't':  case 'u':
			case 'v':  case 'w':  case 'x':  case 'y':
			case 'z':
			{
				mLETTER(false);
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				mDIGIT(false);
				break;
			}
			default:
			{
				break _loop55;
			}
			}
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '(':
		{
			mCOMMENT(false);
			break;
		}
		case ' ':
		{
			match(' ');
			break;
		}
		case '\t':
		{
			match('\t');
			break;
		}
		case '\n':  case '\r':
		{
			{
			if ((LA(1)=='\r') && (LA(2)=='\n') && (true) && (true) && (true) && (true)) {
				match("\r\n");
			}
			else if ((LA(1)=='\r') && (true) && (true) && (true) && (true) && (true)) {
				match('\r');
			}
			else if ((LA(1)=='\n')) {
				match('\n');
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
			}
			
			}
			if ( inputState.guessing==0 ) {
				newline(); out.println();
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mEXTSTRING(boolean _createToken,
		boolean path
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXTSTRING;
		int _saveIndex;
		
		{
		int _cnt20=0;
		_loop20:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				mEXTSYMBOL(false,path);
			}
			else {
				if ( _cnt20>=1 ) { break _loop20; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			
			_cnt20++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			out.print(new String(text.getBuffer(),_begin,text.length()-_begin));
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mEXTSYMBOL(boolean _createToken,
		boolean path
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXTSYMBOL;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '$':
		{
			_saveIndex=text.length();
			match('$');
			text.setLength(_saveIndex);
			{
			switch ( LA(1)) {
			case 'c':
			{
				_saveIndex=text.length();
				match("co%");
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					text.append("(** ");
				}
				{
				int _cnt35=0;
				_loop35:
				do {
					if ((_tokenSet_3.member(LA(1)))) {
						{
						match(_tokenSet_3);
						}
					}
					else {
						if ( _cnt35>=1 ) { break _loop35; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
					}
					
					_cnt35++;
				} while (true);
				}
				_saveIndex=text.length();
				match('|');
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					text.append(" *)");
				}
				break;
			}
			case 'o':  case 's':
			{
				{
				switch ( LA(1)) {
				case 'o':
				{
					match("or%");
					{
					_loop39:
					do {
						if ((_tokenSet_3.member(LA(1)))) {
							{
							match(_tokenSet_3);
							}
						}
						else {
							break _loop39;
						}
						
					} while (true);
					}
					match('|');
					break;
				}
				case 's':
				{
					match("so%");
					{
					_loop42:
					do {
						if ((_tokenSet_2.member(LA(1)))) {
							{
							match(_tokenSet_2);
							}
						}
						else {
							break _loop42;
						}
						
					} while (true);
					}
					match('@');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					text.setLength(_begin); text.append("");
				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
			}
			}
			}
			break;
		}
		case '\n':  case '\r':
		{
			{
			if ((LA(1)=='\r') && (LA(2)=='\n') && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && (true) && (true) && (true)) {
				match("\r\n");
			}
			else if ((LA(1)=='\r') && ((LA(2) >= '\u0003' && LA(2) <= '\u007f')) && (true) && (true) && (true) && (true)) {
				match('\r');
			}
			else if ((LA(1)=='\n')) {
				match('\n');
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
			}
			
			}
			if ( inputState.guessing==0 ) {
				newline();
			}
			break;
		}
		default:
			boolean synPredMatched24 = false;
			if (((LA(1)=='(') && (LA(2)=='s') && (LA(3)=='e') && (LA(4)=='e') && (LA(5)==' ') && (LA(6)=='N'))) {
				int _m24 = mark();
				synPredMatched24 = true;
				inputState.guessing++;
				try {
					{
					match("(see NOTE");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched24 = false;
				}
				rewind(_m24);
				inputState.guessing--;
			}
			if ( synPredMatched24 ) {
				_saveIndex=text.length();
				match("(see NOTE");
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					text.append("(** see NOTE");
				}
				{
				int _cnt27=0;
				_loop27:
				do {
					if ((_tokenSet_4.member(LA(1)))) {
						{
						match(_tokenSet_4);
						}
					}
					else {
						if ( _cnt27>=1 ) { break _loop27; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
					}
					
					_cnt27++;
				} while (true);
				}
				_saveIndex=text.length();
				match(')');
				text.setLength(_saveIndex);
				if ( inputState.guessing==0 ) {
					text.append(" *)");
				}
			}
			else {
				boolean synPredMatched31 = false;
				if ((((LA(1)=='I') && (LA(2)=='D') && (LA(3)=='E') && (LA(4)=='N') && (LA(5)=='T') && (LA(6)=='I'))&&(!path))) {
					int _m31 = mark();
					synPredMatched31 = true;
					inputState.guessing++;
					try {
						{
						match("IDENTICAL MAPPING");
						}
					}
					catch (RecognitionException pe) {
						synPredMatched31 = false;
					}
					rewind(_m31);
					inputState.guessing--;
				}
				if ( synPredMatched31 ) {
					match("IDENTICAL MAPPING");
					if ( inputState.guessing==0 ) {
						text.setLength(_begin); text.append("$IDENTICAL_MAPPING");
					}
				}
				else {
					boolean synPredMatched29 = false;
					if ((((LA(1)=='P') && (LA(2)=='A') && (LA(3)=='T') && (LA(4)=='H') && ((LA(5) >= '\u0003' && LA(5) <= '\u007f')) && (true))&&(!path))) {
						int _m29 = mark();
						synPredMatched29 = true;
						inputState.guessing++;
						try {
							{
							match("PATH");
							}
						}
						catch (RecognitionException pe) {
							synPredMatched29 = false;
						}
						rewind(_m29);
						inputState.guessing--;
					}
					if ( synPredMatched29 ) {
						match("PATH");
						if ( inputState.guessing==0 ) {
							text.setLength(_begin); text.append("$PATH");
						}
					}
					else {
						boolean synPredMatched44 = false;
						if (((LA(1)=='(') && (LA(2)=='*') && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && ((LA(4) >= '\u0003' && LA(4) <= '\u007f')) && ((LA(5) >= '\u0003' && LA(5) <= '\u007f')) && (true))) {
							int _m44 = mark();
							synPredMatched44 = true;
							inputState.guessing++;
							try {
								{
								match("(*");
								}
							}
							catch (RecognitionException pe) {
								synPredMatched44 = false;
							}
							rewind(_m44);
							inputState.guessing--;
						}
						if ( synPredMatched44 ) {
							mCOMMENT(false);
							if ( inputState.guessing==0 ) {
								text.setLength(_begin); text.append("");
							}
						}
						else if ((_tokenSet_5.member(LA(1))) && ((LA(2) >= '\u0003' && LA(2) <= '\u007f')) && (true) && (true) && (true) && (true)) {
							{
							match(_tokenSet_5);
							}
						}
					else {
						throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
					}
					}}}}
					}
					if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
						_token = makeToken(_ttype);
						_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
					}
					_returnToken = _token;
				}
				
	protected final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMENT;
		int _saveIndex;
		
		match("(*");
		{
		_loop66:
		do {
			switch ( LA(1)) {
			case '\n':
			{
				match('\n');
				if ( inputState.guessing==0 ) {
					newline(); out.println();
				}
				break;
			}
			case '\u0003':  case '\u0004':  case '\u0005':  case '\u0006':
			case '\u0007':  case '\u0008':  case '\t':  case '\u000b':
			case '\u000c':  case '\u000e':  case '\u000f':  case '\u0010':
			case '\u0011':  case '\u0012':  case '\u0013':  case '\u0014':
			case '\u0015':  case '\u0016':  case '\u0017':  case '\u0018':
			case '\u0019':  case '\u001a':  case '\u001b':  case '\u001c':
			case '\u001d':  case '\u001e':  case '\u001f':  case ' ':
			case '!':  case '"':  case '#':  case '$':
			case '%':  case '&':  case '\'':  case '(':
			case ')':  case '+':  case ',':  case '-':
			case '.':  case '/':  case '0':  case '1':
			case '2':  case '3':  case '4':  case '5':
			case '6':  case '7':  case '8':  case '9':
			case ':':  case ';':  case '<':  case '=':
			case '>':  case '?':  case '@':  case 'A':
			case 'B':  case 'C':  case 'D':  case 'E':
			case 'F':  case 'G':  case 'H':  case 'I':
			case 'J':  case 'K':  case 'L':  case 'M':
			case 'N':  case 'O':  case 'P':  case 'Q':
			case 'R':  case 'S':  case 'T':  case 'U':
			case 'V':  case 'W':  case 'X':  case 'Y':
			case 'Z':  case '[':  case '\\':  case ']':
			case '^':  case '_':  case '`':  case 'a':
			case 'b':  case 'c':  case 'd':  case 'e':
			case 'f':  case 'g':  case 'h':  case 'i':
			case 'j':  case 'k':  case 'l':  case 'm':
			case 'n':  case 'o':  case 'p':  case 'q':
			case 'r':  case 's':  case 't':  case 'u':
			case 'v':  case 'w':  case 'x':  case 'y':
			case 'z':  case '{':  case '|':  case '}':
			case '~':  case '\u007f':
			{
				{
				match(_tokenSet_6);
				}
				break;
			}
			default:
				if ((LA(1)=='\r') && (LA(2)=='\n') && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && ((LA(4) >= '\u0003' && LA(4) <= '\u007f')) && (true) && (true)) {
					match('\r');
					match('\n');
					if ( inputState.guessing==0 ) {
						newline(); out.println();
					}
				}
				else if (((LA(1)=='*') && ((LA(2) >= '\u0003' && LA(2) <= '\u007f')) && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && (true) && (true) && (true))&&( LA(2) != ')' )) {
					match('*');
				}
				else if ((LA(1)=='\r') && ((LA(2) >= '\u0003' && LA(2) <= '\u007f')) && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && (true) && (true) && (true)) {
					match('\r');
					if ( inputState.guessing==0 ) {
						newline(); out.println();
					}
				}
			else {
				break _loop66;
			}
			}
		} while (true);
		}
		match("*)");
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mEXTID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXTID;
		int _saveIndex;
		
		{
		int _cnt50=0;
		_loop50:
		do {
			if ((_tokenSet_7.member(LA(1)))) {
				{
				match(_tokenSet_7);
				}
			}
			else {
				if ( _cnt50>=1 ) { break _loop50; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			
			_cnt50++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mLETTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LETTER;
		int _saveIndex;
		
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIGIT;
		int _saveIndex;
		
		matchRange('0','9');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mSCARF(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SCARF;
		int _saveIndex;
		
		{
		boolean synPredMatched59 = false;
		if (((_tokenSet_0.member(LA(1))) && (true) && (true) && (true) && (true) && (true))) {
			int _m59 = mark();
			synPredMatched59 = true;
			inputState.guessing++;
			try {
				{
				mWS(false);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched59 = false;
			}
			rewind(_m59);
			inputState.guessing--;
		}
		if ( synPredMatched59 ) {
			mWS(false);
		}
		else if (((LA(1) >= '\u0003' && LA(1) <= '\u007f')) && (true) && (true) && (true) && (true) && (true)) {
			matchNot(EOF_CHAR);
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long _tokenSet_0_data_[] = { 1103806604800L, 0L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	private static final long _tokenSet_1_data_[] = { 5501853115904L, 0L, 0L };
	public static final BitSet _tokenSet_1 = new BitSet(_tokenSet_1_data_);
	private static final long _tokenSet_2_data_[] = { -8L, -2L, 0L, 0L };
	public static final BitSet _tokenSet_2 = new BitSet(_tokenSet_2_data_);
	private static final long _tokenSet_3_data_[] = { -8L, -1152921504606846977L, 0L, 0L };
	public static final BitSet _tokenSet_3 = new BitSet(_tokenSet_3_data_);
	private static final long _tokenSet_4_data_[] = { -2199023255560L, -1L, 0L, 0L };
	public static final BitSet _tokenSet_4 = new BitSet(_tokenSet_4_data_);
	private static final long _tokenSet_5_data_[] = { -68719485960L, -2L, 0L, 0L };
	public static final BitSet _tokenSet_5 = new BitSet(_tokenSet_5_data_);
	private static final long _tokenSet_6_data_[] = { -4398046520328L, -1L, 0L, 0L };
	public static final BitSet _tokenSet_6 = new BitSet(_tokenSet_6_data_);
	private static final long _tokenSet_7_data_[] = { -9224L, -536870913L, 0L, 0L };
	public static final BitSet _tokenSet_7 = new BitSet(_tokenSet_7_data_);
	
	}
