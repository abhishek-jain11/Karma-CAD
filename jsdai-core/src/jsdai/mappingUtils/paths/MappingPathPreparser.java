// $ANTLR 2.7.1: "mappingPathPreparser.g" -> "MappingPathPreparser.java"$

package jsdai.mappingUtils.paths;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jsdai.lang.SdaiException;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class MappingPathPreparser extends antlr.TreeParser
       implements MappingPathPreparserTokenTypes
 {

	private MappingPathParser parser;
	private EntityMappingAST entityMappingAst;

	public MappingPathPreparser(MappingPathParser parser,
			 EntityMappingAST entityMappingAst) {
		this.parser = parser;
		this.entityMappingAst = entityMappingAst;
	}

	public List preparse(AST topAst) throws RecognitionException, SdaiException {
		List topList = new ArrayList();
		pathPreparse(topAst, topList);
		return topList;
	}
public MappingPathPreparser() {
	tokenNames = _tokenNames;
}

	public final void pathPreparse(AST _t,
		List topList
	) throws RecognitionException, SdaiException {
		
		AST pathPreparse_AST_in = (AST)_t;
		AST t = null;
		
		try {      // for error handling
			{
			_loop3:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case TEMPLATE_CALL:
				{
					t = (AST)_t;
					match(_t,TEMPLATE_CALL);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							((TemplateCallAST)t).resolve(t, parser, entityMappingAst);
										_t = t.getNextSibling();
									
					}
					break;
				}
				case ORIGINAL_LOCATION:
				{
					AST tmp1_AST_in = (AST)_t;
					match(_t,ORIGINAL_LOCATION);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					break _loop3;
				}
				}
			} while (true);
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EQ_ENTITY:
			case ENTITY:
			case ENTITY_AGGREG:
			case SELECT:
			case SUB:
			case SUPER:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			{
				entityConstructPreparse(_t,topList);
				_t = _retTree;
				break;
			}
			case EQ_ATTRIBUTE:
			case ATTRIBUTE:
			case ATTRIBUTE_AGGREG:
			case TOPOINT:
			case ATTRIBUTE_NEGATION:
			{
				attributeConstructPreparse(_t,topList);
				_t = _retTree;
				break;
			}
			case AND_CONSTRUCT:
			{
				andConstructPreparse(_t,topList);
				_t = _retTree;
				break;
			}
			case OR:
			{
				orConstructPreparse(_t,topList);
				_t = _retTree;
				break;
			}
			case CONSTRAINT:
			case NEGATION:
			{
				constraintConstructPreparse(_t,topList);
				_t = _retTree;
				break;
			}
			case AND_END:
			{
				AST __t5 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,AND_END);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case EQ_ENTITY:
				case EQ_ATTRIBUTE:
				case ENTITY:
				case ENTITY_AGGREG:
				case ATTRIBUTE:
				case ATTRIBUTE_AGGREG:
				case TEMPLATE_CALL:
				case SELECT:
				case CONSTRAINT:
				case OR:
				case AND_CONSTRUCT:
				case OR_END:
				case AND_END:
				case SUB:
				case SUPER:
				case TOPOINT:
				case FROMPOINT:
				case EXTENDED_INTO:
				case EXTENSION_OF:
				case ATTRIBUTE_NEGATION:
				case NEGATION:
				case ORIGINAL_LOCATION:
				{
					pathPreparse(_t,topList);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t5;
				_t = _t.getNextSibling();
				break;
			}
			case OR_END:
			{
				AST __t7 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,OR_END);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case EQ_ENTITY:
				case EQ_ATTRIBUTE:
				case ENTITY:
				case ENTITY_AGGREG:
				case ATTRIBUTE:
				case ATTRIBUTE_AGGREG:
				case TEMPLATE_CALL:
				case SELECT:
				case CONSTRAINT:
				case OR:
				case AND_CONSTRUCT:
				case OR_END:
				case AND_END:
				case SUB:
				case SUPER:
				case TOPOINT:
				case FROMPOINT:
				case EXTENDED_INTO:
				case EXTENSION_OF:
				case ATTRIBUTE_NEGATION:
				case NEGATION:
				case ORIGINAL_LOCATION:
				{
					pathPreparse(_t,topList);
					_t = _retTree;
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t7;
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	public final void entityConstructPreparse(AST _t,
		List topList
	) throws RecognitionException {
		
		AST entityConstructPreparse_AST_in = (AST)_t;
		AST idEntity = null;
		AST idEntityAggeg = null;
		AST idEq = null;
		AST idFrom = null;
		AST idSuper = null;
		AST idSub = null;
		AST idSelect = null;
		AST idExtInto = null;
		AST idExtOf = null;
		AST entity = null;
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ENTITY:
			{
				AST __t11 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idEntity = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idEntity;
				}
				_t = __t11;
				_t = _t.getNextSibling();
				break;
			}
			case ENTITY_AGGREG:
			{
				AST __t12 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,ENTITY_AGGREG);
				_t = _t.getFirstChild();
				idEntityAggeg = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idEntityAggeg;
				}
				_t = __t12;
				_t = _t.getNextSibling();
				break;
			}
			case EQ_ENTITY:
			{
				AST __t13 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,EQ_ENTITY);
				_t = _t.getFirstChild();
				AST __t14 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idEq = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idEq;
				}
				_t = __t14;
				_t = _t.getNextSibling();
				_t = __t13;
				_t = _t.getNextSibling();
				break;
			}
			case FROMPOINT:
			{
				AST __t15 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,FROMPOINT);
				_t = _t.getFirstChild();
				AST __t16 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idFrom = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idFrom;
				}
				_t = __t16;
				_t = _t.getNextSibling();
				_t = __t15;
				_t = _t.getNextSibling();
				break;
			}
			case SUPER:
			{
				AST __t17 = _t;
				AST tmp10_AST_in = (AST)_t;
				match(_t,SUPER);
				_t = _t.getFirstChild();
				AST __t18 = _t;
				AST tmp11_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idSuper = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idSuper;
				}
				_t = __t18;
				_t = _t.getNextSibling();
				_t = __t17;
				_t = _t.getNextSibling();
				break;
			}
			case SUB:
			{
				AST __t19 = _t;
				AST tmp12_AST_in = (AST)_t;
				match(_t,SUB);
				_t = _t.getFirstChild();
				AST __t20 = _t;
				AST tmp13_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idSub = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idSub;
				}
				_t = __t20;
				_t = _t.getNextSibling();
				_t = __t19;
				_t = _t.getNextSibling();
				break;
			}
			case SELECT:
			{
				AST __t21 = _t;
				AST tmp14_AST_in = (AST)_t;
				match(_t,SELECT);
				_t = _t.getFirstChild();
				AST __t22 = _t;
				AST tmp15_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idSelect = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idSelect;
				}
				_t = __t22;
				_t = _t.getNextSibling();
				_t = __t21;
				_t = _t.getNextSibling();
				break;
			}
			case EXTENDED_INTO:
			{
				AST __t23 = _t;
				AST tmp16_AST_in = (AST)_t;
				match(_t,EXTENDED_INTO);
				_t = _t.getFirstChild();
				AST __t24 = _t;
				AST tmp17_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idExtInto = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idExtInto;
				}
				_t = __t24;
				_t = _t.getNextSibling();
				_t = __t23;
				_t = _t.getNextSibling();
				break;
			}
			case EXTENSION_OF:
			{
				AST __t25 = _t;
				AST tmp18_AST_in = (AST)_t;
				match(_t,EXTENSION_OF);
				_t = _t.getFirstChild();
				AST __t26 = _t;
				AST tmp19_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idExtOf = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					entity = idExtOf;
				}
				_t = __t26;
				_t = _t.getNextSibling();
				_t = __t25;
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			if ( inputState.guessing==0 ) {
				topList.add(MappingPathWalker.dupAttachTree(entity));
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	public final void attributeConstructPreparse(AST _t,
		List topList
	) throws RecognitionException {
		
		AST attributeConstructPreparse_AST_in = (AST)_t;
		AST idEntityAggeg = null;
		AST idEntity = null;
		AST attrib = null;
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE_NEGATION:
			{
				AST __t29 = _t;
				AST tmp20_AST_in = (AST)_t;
				match(_t,ATTRIBUTE_NEGATION);
				_t = _t.getFirstChild();
				attributeConstructPreparse(_t,topList);
				_t = _retTree;
				_t = __t29;
				_t = _t.getNextSibling();
				break;
			}
			case EQ_ATTRIBUTE:
			{
				AST __t32 = _t;
				AST tmp21_AST_in = (AST)_t;
				match(_t,EQ_ATTRIBUTE);
				_t = _t.getFirstChild();
				attrib=attributePreparse(_t);
				_t = _retTree;
				_t = __t32;
				_t = _t.getNextSibling();
				break;
			}
			case TOPOINT:
			{
				AST __t33 = _t;
				AST tmp22_AST_in = (AST)_t;
				match(_t,TOPOINT);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ATTRIBUTE:
				case ATTRIBUTE_AGGREG:
				{
					attrib=attributePreparse(_t);
					_t = _retTree;
					break;
				}
				case ENTITY_AGGREG:
				{
					AST __t35 = _t;
					AST tmp23_AST_in = (AST)_t;
					match(_t,ENTITY_AGGREG);
					_t = _t.getFirstChild();
					idEntityAggeg = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
						attrib = idEntityAggeg;
					}
					_t = __t35;
					_t = _t.getNextSibling();
					break;
				}
				case ENTITY:
				{
					AST __t36 = _t;
					AST tmp24_AST_in = (AST)_t;
					match(_t,ENTITY);
					_t = _t.getFirstChild();
					idEntity = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
						attrib = idEntity;
					}
					_t = __t36;
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t33;
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE:
			case ATTRIBUTE_AGGREG:
			{
				attrib=attributePreparse(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			if ( inputState.guessing==0 ) {
				topList.add(MappingPathWalker.dupAttachTree(attrib));
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	public final void andConstructPreparse(AST _t,
		List topList
	) throws RecognitionException, SdaiException {
		
		AST andConstructPreparse_AST_in = (AST)_t;
		List andList = null;
		
		
		try {      // for error handling
			AST __t42 = _t;
			AST tmp25_AST_in = (AST)_t;
			match(_t,AND_CONSTRUCT);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AND:
			{
				if ( inputState.guessing==0 ) {
					andList = new ArrayList();
				}
				andPreparse(_t,andList);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
					topList.addAll(andList);
				}
				break;
			}
			case EQ_ENTITY:
			case EQ_ATTRIBUTE:
			case ENTITY:
			case ENTITY_AGGREG:
			case ATTRIBUTE:
			case ATTRIBUTE_AGGREG:
			case TEMPLATE_CALL:
			case SELECT:
			case CONSTRAINT:
			case OR:
			case AND_CONSTRUCT:
			case OR_END:
			case AND_END:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case ORIGINAL_LOCATION:
			{
				pathPreparse(_t,topList);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t42;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	public final void orConstructPreparse(AST _t,
		List topList
	) throws RecognitionException, SdaiException {
		
		AST orConstructPreparse_AST_in = (AST)_t;
		
		try {      // for error handling
			AST __t51 = _t;
			AST tmp26_AST_in = (AST)_t;
			match(_t,OR);
			_t = _t.getFirstChild();
			pathPreparse(_t,topList);
			_t = _retTree;
			pathPreparse(_t,topList);
			_t = _retTree;
			_t = __t51;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	public final void constraintConstructPreparse(AST _t,
		List topList
	) throws RecognitionException, SdaiException {
		
		AST constraintConstructPreparse_AST_in = (AST)_t;
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NEGATION:
			{
				AST __t54 = _t;
				AST tmp27_AST_in = (AST)_t;
				match(_t,NEGATION);
				_t = _t.getFirstChild();
				constraintConstructPreparse(_t,topList);
				_t = _retTree;
				_t = __t54;
				_t = _t.getNextSibling();
				break;
			}
			case CONSTRAINT:
			{
				AST __t57 = _t;
				AST tmp28_AST_in = (AST)_t;
				match(_t,CONSTRAINT);
				_t = _t.getFirstChild();
				pathPreparse(_t,topList);
				_t = _retTree;
				_t = __t57;
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	public final AST  attributePreparse(AST _t) throws RecognitionException {
		AST attrib = null;
		
		AST attributePreparse_AST_in = (AST)_t;
		AST attId = null;
		AST attAggId = null;
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE:
			{
				AST __t39 = _t;
				AST tmp29_AST_in = (AST)_t;
				match(_t,ATTRIBUTE);
				_t = _t.getFirstChild();
				attId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					attrib = attId;
				}
				_t = __t39;
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE_AGGREG:
			{
				AST __t40 = _t;
				AST tmp30_AST_in = (AST)_t;
				match(_t,ATTRIBUTE_AGGREG);
				_t = _t.getFirstChild();
				attAggId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					attrib = attAggId;
				}
				_t = __t40;
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return attrib;
	}
	
	public final void andPreparse(AST _t,
		List andList
	) throws RecognitionException, SdaiException {
		
		AST andPreparse_AST_in = (AST)_t;
		List andList1 = new ArrayList();
		List andList2 = new ArrayList();
		
		
		try {      // for error handling
			AST __t45 = _t;
			AST tmp31_AST_in = (AST)_t;
			match(_t,AND);
			_t = _t.getFirstChild();
			pathAndElementPreparse(_t,andList1);
			_t = _retTree;
			pathAndElementPreparse(_t,andList2);
			_t = _retTree;
			_t = __t45;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
				for(Iterator l1 = andList1.iterator(); l1.hasNext(); ) {
				AST ast1 = (AST)l1.next();
				for(Iterator l2 = andList2.iterator(); l2.hasNext(); ) {
				AST ast2 = (AST)l2.next();
				AST newAst2 = MappingPathWalker.dupAttachTree(ast2);
				AST s2 = newAst2;
				for(AST s1 = ast2.getNextSibling(); s1 != null; s1 = s1.getNextSibling()) {
				AST s2next = MappingPathWalker.dupAttachTree(s1);
				s2.setNextSibling(s2next);
				s2 = s2next;
				}
				AST sibbling = ast1.getNextSibling();
				AST newAst1 = l2.hasNext() ? MappingPathWalker.dupAttachTree(ast1) : ast1;
				newAst1.setNextSibling(newAst2);
				ast2.setNextSibling(sibbling);
				andList.add(newAst1);
				}
				}
				
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	public final void pathAndElementPreparse(AST _t,
		List andList
	) throws RecognitionException, SdaiException {
		
		AST pathAndElementPreparse_AST_in = (AST)_t;
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AND:
			{
				andPreparse(_t,andList);
				_t = _retTree;
				break;
			}
			case EQ_ENTITY:
			case EQ_ATTRIBUTE:
			case ENTITY:
			case ENTITY_AGGREG:
			case ATTRIBUTE:
			case ATTRIBUTE_AGGREG:
			case TEMPLATE_CALL:
			case SELECT:
			case CONSTRAINT:
			case OR:
			case AND_CONSTRUCT:
			case OR_END:
			case AND_END:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case ORIGINAL_LOCATION:
			{
				pathPreparse(_t,andList);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"SCHEMA_MAPPING",
		"UOF_MAPPING",
		"ENTITY_MAPPING",
		"ATTRIBUTE_MAPPING",
		"EQ_ENTITY",
		"EQ_ATTRIBUTE",
		"ENTITY",
		"ENTITY_AGGREG",
		"ATTRIBUTE",
		"ATTRIBUTE_AGGREG",
		"TEMPLATE_CALL",
		"SELECT",
		"CONSTRAINT",
		"OR",
		"OR_EXTENDED",
		"AND",
		"AND_CONSTRUCT",
		"OR_END",
		"AND_END",
		"INT",
		"FLOAT",
		"ENUM",
		"LAST",
		"ARM",
		"ARM_ATTRIBUTE",
		"AIM",
		"AIM_ATTRIBUTE",
		"SUB",
		"SUPER",
		"TOPOINT",
		"FROMPOINT",
		"EXTENDED_INTO",
		"EXTENSION_OF",
		"ATTRIBUTE_NEGATION",
		"NEGATION",
		"STATE_LINK",
		"ORIGINAL_LOCATION",
		"SCHEMA_MAPPING_INFO",
		"STRONG",
		"ENTRY_POINT",
		"STRONG_USERS",
		"DERIVED_VARIANT",
		"\"schema_mapping\"",
		"ID",
		"OPAREN",
		"COMMA",
		"CPAREN",
		"SEP",
		"\"end_schema_mapping\"",
		"\"include\"",
		"STRING",
		"\"uof_mapping\"",
		"\"end_uof_mapping\"",
		"\"entity_mapping\"",
		"\"end_entity_mapping\"",
		"\"mapping_constraints\"",
		"\"end_mapping_constraints\"",
		"\"attribute_mapping\"",
		"\"end_attribute_mapping\"",
		"VBAR",
		"OSQBRAC",
		"\"i\"",
		"CSQBRAC",
		"DOT",
		"SLASH",
		"EQ",
		"OANBRAC",
		"CANBRAC",
		"NEQ",
		"EXCLAM",
		"OBRACE",
		"CBRACE",
		"MACRO",
		"\"schema_mapping_info\"",
		"\"end_schema_mapping_info\"",
		"\"entity\"",
		"\"end_entity\"",
		"\"attribute\"",
		"\"end_attribute\"",
		"\"strong\"",
		"\"for\"",
		"COLON",
		"\"mapped_values\"",
		"\"as\"",
		"\"entry_point\"",
		"\"strong_users\"",
		"\"derived_variant\"",
		"\"end_derived_variant\"",
		"WS",
		"MULTI_LINE_COMMENT",
		"SINGLE_LINE_COMMENT",
		"LETTER",
		"DIGIT",
		"ASTERISK"
	};
	
	}
	
