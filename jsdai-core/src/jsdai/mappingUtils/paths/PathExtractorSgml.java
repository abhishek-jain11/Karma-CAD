// $ANTLR 2.7.1: "pathExtractorSgml.g" -> "PathExtractorSgml.java"$

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

public class PathExtractorSgml extends antlr.CharScanner implements PathExtractorSgmlTokenTypes, TokenStream
 {

	static public final int NO_MAPPING = 0;
	static public final int ENTITY_MAPPING = 1;
	static public final int ATTRIBUTE_MAPPING = 2;
	private static boolean done = false;
	private static PrintWriter out;
	private int mappingType = NO_MAPPING;
	private static boolean isEntityMapping = false;
	private static String attributeTargetTypeName = null;

	public void uponEOF() throws TokenStreamException, CharStreamException {
        done=true;
	}

	public static void main(String[] args) throws Exception {
		try {
			PathExtractorSgml lexer = new PathExtractorSgml(new FileReader(args[0]));
			out = new PrintWriter(new FileWriter(args[1]));
			String armSchemaName = args.length == 4 ? args[2].toUpperCase() : "AP210_ARM";
			String aimSchemaName = (args.length == 4 ? args[3].toUpperCase() : 
				"ELECTRONIC_ASSEMBLY_INTERCONNECT_AND_PACKAGING_DESIGN");
			String armSchemaNameLc = armSchemaName.toLowerCase();
			out.print("schema_mapping " + armSchemaNameLc + 
				" (" + armSchemaName + ", " + aimSchemaName + ");");
			while ( !done ) {
				lexer.nextToken();
			}
			out.print(" end_schema_mapping;"); 
			out.close();
		}
		catch(TokenStreamRecognitionException e) {
			System.err.println("exception: "+e.recog);
		}
		catch(TokenStreamException e) {
			System.err.println("exception: "+e);
		}
	}
public PathExtractorSgml(InputStream in) {
	this(new ByteBuffer(in));
}
public PathExtractorSgml(Reader in) {
	this(new CharBuffer(in));
}
public PathExtractorSgml(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public PathExtractorSgml(LexerSharedInputState state) {
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
				if ((LA(1)=='<') && (LA(2)=='/')) {
					mELEMENT_ENDS(true);
					theRetToken=_returnToken;
				}
				else if ((LA(1)=='<') && (LA(2)=='r')) {
					mPATH_LINE_OR_START_SELECT(true);
					theRetToken=_returnToken;
				}
				else if ((LA(1)=='<') && (LA(2)=='u')) {
					mUOF_MAPPING_START(true);
					theRetToken=_returnToken;
				}
				else if ((LA(1)=='<') && (LA(2)=='a')) {
					mENTITY_OR_ATTRIBUTE_MAPPING_SELECT(true);
					theRetToken=_returnToken;
				}
				else if ((LA(1)=='"'||LA(1)=='\'')) {
					mSTRING(true);
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

	public final void mELEMENT_ENDS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ELEMENT_ENDS;
		int _saveIndex;
		
		match("</");
		{
		switch ( LA(1)) {
		case 'r':
		{
			mPATH_END(false);
			break;
		}
		case 'u':
		{
			mUOF_MAPPING_END(false);
			break;
		}
		case 'm':
		{
			mMAPPING_ROW_END(false);
			break;
		}
		default:
		{
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
	
	protected final void mPATH_END(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PATH_END;
		int _saveIndex;
		
		match("reference.path.col.entry>");
		if ( inputState.guessing==0 ) {
				if(mappingType == ENTITY_MAPPING) {
							out.print(" end_mapping_constraints;");
						}
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mUOF_MAPPING_END(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = UOF_MAPPING_END;
		int _saveIndex;
		
		match("uof.mapping.sub.tbl>");
		if ( inputState.guessing==0 ) {
				if(isEntityMapping) {
							out.print(" end_entity_mapping;");
							isEntityMapping = false;
						}
						out.print(" end_uof_mapping;");
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mMAPPING_ROW_END(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MAPPING_ROW_END;
		int _saveIndex;
		
		match("mapping.sub.tbl.a");
		{
		switch ( LA(1)) {
		case 'p':
		{
			match("ppobj.row>");
			break;
		}
		case 's':
		{
			match("ssert.row>");
			break;
		}
		case 't':
		{
			match("ttr.row");
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		}
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
	
	public final void mPATH_LINE_OR_START_SELECT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PATH_LINE_OR_START_SELECT;
		int _saveIndex;
		
		match("<ref");
		{
		switch ( LA(1)) {
		case '.':
		{
			mPATH_LINE(false);
			break;
		}
		case 'e':
		{
			mPATH_START(false);
			break;
		}
		default:
		{
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
	
	protected final void mPATH_LINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PATH_LINE;
		int _saveIndex;
		Token s=null;
		
		match(".path.line");
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
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		match("line=");
		{
		_loop9:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop9;
			}
			
		} while (true);
		}
		mSTRING(true);
		s=_returnToken;
		{
		_loop11:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop11;
			}
			
		} while (true);
		}
		match(">");
		if ( inputState.guessing==0 ) {
			out.print("\t" + s.getText() + " ");
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mPATH_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PATH_START;
		int _saveIndex;
		
		match("erence.path.col.entry>");
		if ( inputState.guessing==0 ) {
				if(mappingType == ENTITY_MAPPING) {
							out.print(" mapping_constraints;");
						}
					
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
		case '<':
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
			if ((LA(1)=='\r') && (LA(2)=='\n') && (true) && (true)) {
				match("\r\n");
			}
			else if ((LA(1)=='\r') && (true) && (true) && (true)) {
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
	
	public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '\'':
		{
			_saveIndex=text.length();
			match('\'');
			text.setLength(_saveIndex);
			{
			_loop64:
			do {
				switch ( LA(1)) {
				case '&':
				{
					mENTITY(false);
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
				case '%':  case '(':  case ')':  case '*':
				case '+':  case ',':  case '-':  case '.':
				case '/':  case '0':  case '1':  case '2':
				case '3':  case '4':  case '5':  case '6':
				case '7':  case '8':  case '9':  case ':':
				case ';':  case '<':  case '=':  case '>':
				case '?':  case '@':  case 'A':  case 'B':
				case 'C':  case 'D':  case 'E':  case 'F':
				case 'G':  case 'H':  case 'I':  case 'J':
				case 'K':  case 'L':  case 'M':  case 'N':
				case 'O':  case 'P':  case 'Q':  case 'R':
				case 'S':  case 'T':  case 'U':  case 'V':
				case 'W':  case 'X':  case 'Y':  case 'Z':
				case '[':  case '\\':  case ']':  case '^':
				case '_':  case '`':  case 'a':  case 'b':
				case 'c':  case 'd':  case 'e':  case 'f':
				case 'g':  case 'h':  case 'i':  case 'j':
				case 'k':  case 'l':  case 'm':  case 'n':
				case 'o':  case 'p':  case 'q':  case 'r':
				case 's':  case 't':  case 'u':  case 'v':
				case 'w':  case 'x':  case 'y':  case 'z':
				case '{':  case '|':  case '}':  case '~':
				case '\u007f':
				{
					{
					match(_tokenSet_1);
					}
					break;
				}
				default:
				{
					break _loop64;
				}
				}
			} while (true);
			}
			_saveIndex=text.length();
			match('\'');
			text.setLength(_saveIndex);
			break;
		}
		case '"':
		{
			_saveIndex=text.length();
			match('"');
			text.setLength(_saveIndex);
			{
			_loop67:
			do {
				switch ( LA(1)) {
				case '&':
				{
					mENTITY(false);
					break;
				}
				case '\u0003':  case '\u0004':  case '\u0005':  case '\u0006':
				case '\u0007':  case '\u0008':  case '\t':  case '\u000b':
				case '\u000c':  case '\u000e':  case '\u000f':  case '\u0010':
				case '\u0011':  case '\u0012':  case '\u0013':  case '\u0014':
				case '\u0015':  case '\u0016':  case '\u0017':  case '\u0018':
				case '\u0019':  case '\u001a':  case '\u001b':  case '\u001c':
				case '\u001d':  case '\u001e':  case '\u001f':  case ' ':
				case '!':  case '#':  case '$':  case '%':
				case '\'':  case '(':  case ')':  case '*':
				case '+':  case ',':  case '-':  case '.':
				case '/':  case '0':  case '1':  case '2':
				case '3':  case '4':  case '5':  case '6':
				case '7':  case '8':  case '9':  case ':':
				case ';':  case '<':  case '=':  case '>':
				case '?':  case '@':  case 'A':  case 'B':
				case 'C':  case 'D':  case 'E':  case 'F':
				case 'G':  case 'H':  case 'I':  case 'J':
				case 'K':  case 'L':  case 'M':  case 'N':
				case 'O':  case 'P':  case 'Q':  case 'R':
				case 'S':  case 'T':  case 'U':  case 'V':
				case 'W':  case 'X':  case 'Y':  case 'Z':
				case '[':  case '\\':  case ']':  case '^':
				case '_':  case '`':  case 'a':  case 'b':
				case 'c':  case 'd':  case 'e':  case 'f':
				case 'g':  case 'h':  case 'i':  case 'j':
				case 'k':  case 'l':  case 'm':  case 'n':
				case 'o':  case 'p':  case 'q':  case 'r':
				case 's':  case 't':  case 'u':  case 'v':
				case 'w':  case 'x':  case 'y':  case 'z':
				case '{':  case '|':  case '}':  case '~':
				case '\u007f':
				{
					{
					match(_tokenSet_2);
					}
					break;
				}
				default:
				{
					break _loop67;
				}
				}
			} while (true);
			}
			_saveIndex=text.length();
			match('"');
			text.setLength(_saveIndex);
			break;
		}
		default:
		{
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
	
	public final void mUOF_MAPPING_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = UOF_MAPPING_START;
		int _saveIndex;
		Token s=null;
		
		match("<uof.mapping.sub.tbl");
		{
		int _cnt16=0;
		_loop16:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				if ( _cnt16>=1 ) { break _loop16; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			
			_cnt16++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		match("uof.name.linkend=");
		{
		_loop18:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop18;
			}
			
		} while (true);
		}
		mSTRING(true);
		s=_returnToken;
		mTO_END_OF_ELEMENT(false);
		match(">");
		if ( inputState.guessing==0 ) {
			out.print(" uof_mapping " + s.getText() + " (" + s.getText() + ");");
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mTO_END_OF_ELEMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TO_END_OF_ELEMENT;
		int _saveIndex;
		
		{
		_loop80:
		do {
			boolean synPredMatched78 = false;
			if (((_tokenSet_0.member(LA(1))) && ((LA(2) >= '\u0003' && LA(2) <= '\u007f')) && (true) && (true))) {
				int _m78 = mark();
				synPredMatched78 = true;
				inputState.guessing++;
				try {
					{
					mWS(false);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched78 = false;
				}
				rewind(_m78);
				inputState.guessing--;
			}
			if ( synPredMatched78 ) {
				mWS(false);
			}
			else if ((_tokenSet_3.member(LA(1))) && ((LA(2) >= '\u0003' && LA(2) <= '\u007f')) && (true) && (true)) {
				{
				match(_tokenSet_3);
				}
			}
			else {
				break _loop80;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mENTITY_OR_ATTRIBUTE_MAPPING_SELECT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ENTITY_OR_ATTRIBUTE_MAPPING_SELECT;
		int _saveIndex;
		
		match("<a");
		{
		switch ( LA(1)) {
		case 'p':
		{
			match("pp.elem.");
			{
			if ((LA(1)=='a') && (LA(2)=='p')) {
				mENTITY_ATTRIBUTE_MAPPING_START(false);
			}
			else if ((LA(1)=='a') && (LA(2)=='s')) {
				mATTRIBUTE_MAPPING1_START(false);
			}
			else if ((LA(1)=='a') && (LA(2)=='t')) {
				mATTRIBUTE_MAPPING2_START(false);
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
			}
			
			}
			break;
		}
		case 'i':
		{
			mAIM_ELEMENT(false);
			break;
		}
		default:
		{
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
	
	protected final void mENTITY_ATTRIBUTE_MAPPING_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ENTITY_ATTRIBUTE_MAPPING_START;
		int _saveIndex;
		Token s=null;
		
		match("appobj.col.entry");
		{
		int _cnt27=0;
		_loop27:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				if ( _cnt27>=1 ) { break _loop27; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			
			_cnt27++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		match("appobj.name.linkend=");
		{
		_loop29:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop29;
			}
			
		} while (true);
		}
		mSTRING(true);
		s=_returnToken;
		mTO_END_OF_ELEMENT(false);
		match(">");
		if ( inputState.guessing==0 ) {
				String sText = s.getText();
						if(Character.isUpperCase(sText.charAt(0))) {
							if(isEntityMapping) {
								out.print(" end_entity_mapping;");
							}
							//out.println("(* " + getLine() + " *)");
							out.print(" entity_mapping " + sText.toLowerCase() + " (" + sText.toLowerCase());
							mappingType = ENTITY_MAPPING;
							isEntityMapping = true;
						} else {
							//out.println("(* " + getLine() + " *)");
							out.print(" attribute_mapping " + sText + " (" + sText);
							mappingType = ATTRIBUTE_MAPPING;
							attributeTargetTypeName = null;
						}
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mATTRIBUTE_MAPPING1_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ATTRIBUTE_MAPPING1_START;
		int _saveIndex;
		Token s2=null;
		Token s3=null;
		
		match("assert.col.entry");
		{
		int _cnt32=0;
		_loop32:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				if ( _cnt32>=1 ) { break _loop32; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			
			_cnt32++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		match("obj1.name.linkend=");
		{
		_loop34:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop34;
			}
			
		} while (true);
		}
		mSTRING(false);
		{
		_loop36:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop36;
			}
			
		} while (true);
		}
		match("obj2.name.linkend=");
		{
		_loop38:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop38;
			}
			
		} while (true);
		}
		mSTRING(true);
		s2=_returnToken;
		{
		_loop40:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop40;
			}
			
		} while (true);
		}
		match("attr.name.linkend=");
		{
		_loop42:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop42;
			}
			
		} while (true);
		}
		match("\"(as ");
		mID(true);
		s3=_returnToken;
		match(")\"");
		mTO_END_OF_ELEMENT(false);
		match(">");
		if ( inputState.guessing==0 ) {
				//out.println("(* " + getLine() + " *)");
						out.print(" attribute_mapping " + s3.getText() + "_" + s2.getText() + " (" + s3.getText());
						mappingType = ATTRIBUTE_MAPPING;
						attributeTargetTypeName = s2.getText();
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mATTRIBUTE_MAPPING2_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ATTRIBUTE_MAPPING2_START;
		int _saveIndex;
		Token s=null;
		
		match("attr.col.entry");
		{
		int _cnt45=0;
		_loop45:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				if ( _cnt45>=1 ) { break _loop45; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			
			_cnt45++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		match("attr.name.linkend=");
		{
		_loop47:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop47;
			}
			
		} while (true);
		}
		mSTRING(true);
		s=_returnToken;
		{
		_loop49:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop49;
			}
			
		} while (true);
		}
		match(">");
		if ( inputState.guessing==0 ) {
				//out.println("(* " + getLine() + " *)");
						out.print(" attribute_mapping " + s.getText() + " (" + s.getText());
						mappingType = ATTRIBUTE_MAPPING;
						attributeTargetTypeName = null;
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
		Token s=null;
		
		match("im.element.col.entry");
		{
		int _cnt52=0;
		_loop52:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				if ( _cnt52>=1 ) { break _loop52; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());}
			}
			
			_cnt52++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			setCommitToPath(true);
		}
		match("name=");
		{
		_loop54:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop54;
			}
			
		} while (true);
		}
		{
		boolean synPredMatched57 = false;
		if (((LA(1)=='"') && (LA(2)=='P') && (LA(3)=='A') && (LA(4)=='T'))) {
			int _m57 = mark();
			synPredMatched57 = true;
			inputState.guessing++;
			try {
				{
				match("\"PATH\"");
				}
			}
			catch (RecognitionException pe) {
				synPredMatched57 = false;
			}
			rewind(_m57);
			inputState.guessing--;
		}
		if ( synPredMatched57 ) {
			match("\"PATH\"");
			if ( inputState.guessing==0 ) {
					if(mappingType == ATTRIBUTE_MAPPING) {
									if(attributeTargetTypeName != null) {
										out.print(", (*PATH*), " + attributeTargetTypeName);
									}
									out.print(");");
								} else {
									System.err.println("PATH can't be used in entity mapping in line " + getLine());
									out.print("(*!!ERROR!!*)");
								}
							
			}
		}
		else {
			boolean synPredMatched59 = false;
			if (((LA(1)=='"') && (LA(2)=='I') && (LA(3)=='D') && (LA(4)=='E'))) {
				int _m59 = mark();
				synPredMatched59 = true;
				inputState.guessing++;
				try {
					{
					match("\"IDENTICAL MAPPING\"");
					}
				}
				catch (RecognitionException pe) {
					synPredMatched59 = false;
				}
				rewind(_m59);
				inputState.guessing--;
			}
			if ( synPredMatched59 ) {
				match("\"IDENTICAL MAPPING\"");
				if ( inputState.guessing==0 ) {
						if(mappingType == ATTRIBUTE_MAPPING) {
										if(attributeTargetTypeName != null) {
											out.print(", (*IDENTICAL MAPPING*), " + attributeTargetTypeName);
										}
										out.print(");");
									} else {
										System.err.println("(*IDENTICAL MAPPING*) can't be used in entity mapping in line " + getLine());
										out.print("(*!!ERROR!!*)");
									}
								
				}
			}
			else if ((LA(1)=='"'||LA(1)=='\'') && (_tokenSet_4.member(LA(2))) && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && (true)) {
				mSTRING(true);
				s=_returnToken;
				if ( inputState.guessing==0 ) {
						out.print(", " + s.getText());
									if(mappingType == ATTRIBUTE_MAPPING &&attributeTargetTypeName != null) {
										out.print(", " + attributeTargetTypeName);
									}
									out.print(");");
								
				}
			}
			else {
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
			}
			}
			}
			mTO_END_OF_ELEMENT(false);
			match(">");
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
		_loop74:
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
				break _loop74;
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
	
	protected final void mENTITY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ENTITY;
		int _saveIndex;
		
		_saveIndex=text.length();
		match('&');
		text.setLength(_saveIndex);
		{
		switch ( LA(1)) {
		case 'e':
		{
			match("equal");
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append("=");
			}
			break;
		}
		case 'o':
		{
			match("openangl");
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append("<");
			}
			break;
		}
		case 'c':
		{
			match("clsangl");
			if ( inputState.guessing==0 ) {
				text.setLength(_begin); text.append(">");
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine());
		}
		}
		}
		_saveIndex=text.length();
		match(';');
		text.setLength(_saveIndex);
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
		boolean synPredMatched84 = false;
		if (((_tokenSet_0.member(LA(1))) && (true) && (true) && (true))) {
			int _m84 = mark();
			synPredMatched84 = true;
			inputState.guessing++;
			try {
				{
				mWS(false);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched84 = false;
			}
			rewind(_m84);
			inputState.guessing--;
		}
		if ( synPredMatched84 ) {
			mWS(false);
		}
		else if (((LA(1) >= '\u0003' && LA(1) <= '\u007f')) && (true) && (true) && (true)) {
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
	
	protected final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMENT;
		int _saveIndex;
		
		match("<!--");
		{
		_loop91:
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
			case ')':  case '*':  case '+':  case ',':
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
				match(_tokenSet_5);
				}
				break;
			}
			default:
				if (((LA(1)=='-') && ((LA(2) >= '\u0003' && LA(2) <= '\u007f')) && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && ((LA(4) >= '\u0003' && LA(4) <= '\u007f')))&&( LA(2) != '-' && LA(3) != '>' )) {
					match('-');
				}
				else if ((LA(1)=='\r') && (LA(2)=='\n') && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && ((LA(4) >= '\u0003' && LA(4) <= '\u007f'))) {
					match('\r');
					match('\n');
					if ( inputState.guessing==0 ) {
						newline(); out.println();
					}
				}
				else if ((LA(1)=='\r') && ((LA(2) >= '\u0003' && LA(2) <= '\u007f')) && ((LA(3) >= '\u0003' && LA(3) <= '\u007f')) && ((LA(4) >= '\u0003' && LA(4) <= '\u007f'))) {
					match('\r');
					if ( inputState.guessing==0 ) {
						newline(); out.println();
					}
				}
			else {
				break _loop91;
			}
			}
		} while (true);
		}
		match("-->");
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long _tokenSet_0_data_[] = { 1152921508901824000L, 0L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	private static final long _tokenSet_1_data_[] = { -824633730056L, -1L, 0L, 0L };
	public static final BitSet _tokenSet_1 = new BitSet(_tokenSet_1_data_);
	private static final long _tokenSet_2_data_[] = { -292057785352L, -1L, 0L, 0L };
	public static final BitSet _tokenSet_2 = new BitSet(_tokenSet_2_data_);
	private static final long _tokenSet_3_data_[] = { -4611686018427387912L, -1L, 0L, 0L };
	public static final BitSet _tokenSet_3 = new BitSet(_tokenSet_3_data_);
	private static final long _tokenSet_4_data_[] = { -9224L, -1L, 0L, 0L };
	public static final BitSet _tokenSet_4 = new BitSet(_tokenSet_4_data_);
	private static final long _tokenSet_5_data_[] = { -35184372098056L, -1L, 0L, 0L };
	public static final BitSet _tokenSet_5 = new BitSet(_tokenSet_5_data_);
	
	}
