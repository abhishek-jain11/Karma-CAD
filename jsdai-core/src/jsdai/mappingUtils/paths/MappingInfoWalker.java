// $ANTLR 2.7.1: "mappingInfo.g" -> "MappingInfoWalker.java"$

	package jsdai.mappingUtils.paths;

	import java.util.*;

	import jsdai.lang.*;
	import jsdai.SExtended_dictionary_schema.*;
	import jsdai.SMapping_schema.*;
	import antlr.*;

	import java.io.*;

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


public class MappingInfoWalker extends antlr.TreeParser
       implements MappingInfoWalkerTokenTypes
 {

	protected MappingPathParser parser;

	public MappingInfoWalker(MappingPathParser parser) {
		this();
		this.parser = parser;
	}

	public void reportWarning(AST token, String text) {
		System.err.println(antlr.FileLineFormatter.getFormatter()
			.getFormatString(parser.getFilename(), 
				token instanceof ParserAST ? ((ParserAST)token).getLine() : 0) + 
			text);
	}
public MappingInfoWalker() {
	tokenNames = _tokenNames;
}

	public final void schemaMappingInfo(AST _t) throws RecognitionException, SdaiException {
		
		AST schemaMappingInfo_AST_in = (AST)_t;
		
		try {      // for error handling
			AST __t2 = _t;
			AST tmp1_AST_in = (AST)_t;
			match(_t,SCHEMA_MAPPING_INFO);
			_t = _t.getFirstChild();
			{
			int _cnt4=0;
			_loop4:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ENTITY)) {
					entity(_t);
					_t = _retTree;
				}
				else {
					if ( _cnt4>=1 ) { break _loop4; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt4++;
			} while (true);
			}
			_t = __t2;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void entity(AST _t) throws RecognitionException, SdaiException {
		
		AST entity_AST_in = (AST)_t;
		AST entity = null;
		
		try {      // for error handling
			AST __t6 = _t;
			entity = _t==ASTNULL ? null :(AST)_t;
			match(_t,ENTITY);
			_t = _t.getFirstChild();
			{
			int _cnt8=0;
			_loop8:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ATTRIBUTE:
				{
					attribute(_t);
					_t = _retTree;
					break;
				}
				case ENTRY_POINT:
				{
					entryPoint(_t,(EntityAST)entity);
					_t = _retTree;
					break;
				}
				case STRONG_USERS:
				{
					strongUsers(_t,(EntityAST)entity);
					_t = _retTree;
					break;
				}
				case DERIVED_VARIANT:
				{
					derivedVariant(_t,(EntityAST)entity);
					_t = _retTree;
					break;
				}
				default:
				{
					if ( _cnt8>=1 ) { break _loop8; } else {throw new NoViableAltException(_t);}
				}
				}
				_cnt8++;
			} while (true);
			}
			_t = __t6;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void attribute(AST _t) throws RecognitionException, SdaiException {
		
		AST attribute_AST_in = (AST)_t;
		AST attribute = null;
		
		try {      // for error handling
			AST __t10 = _t;
			attribute = _t==ASTNULL ? null :(AST)_t;
			match(_t,ATTRIBUTE);
			_t = _t.getFirstChild();
			{
			_loop12:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==STRONG)) {
					strong(_t,(AttributeAST)attribute);
					_t = _retTree;
				}
				else {
					break _loop12;
				}
				
			} while (true);
			}
			_t = __t10;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void entryPoint(AST _t,
		EntityAST entity
	) throws RecognitionException, SdaiException {
		
		AST entryPoint_AST_in = (AST)_t;
		AST aimId = null;
		
		try {      // for error handling
			AST __t17 = _t;
			AST tmp2_AST_in = (AST)_t;
			match(_t,ENTRY_POINT);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			{
				aimId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
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
				if(entity.declaration.entityMappings == null) {
								reportWarning(entity, entity + " can not be set as entry point: no mappings");
							} else {
								Iterator entityMappingIter = entity.declaration.entityMappings.values().iterator();
								while(entityMappingIter.hasNext()) {
									EEntity_mapping entityMapping = (EEntity_mapping)entityMappingIter.next();
									if(aimId == null || entityMapping.getTarget(null) == aimId) {
										entityMapping.setEntry_point(null, true);
									}
								}
							}
						
			_t = __t17;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void strongUsers(AST _t,
		EntityAST entity
	) throws RecognitionException, SdaiException {
		
		AST strongUsers_AST_in = (AST)_t;
		AST aimId = null;
		
		try {      // for error handling
			AST __t20 = _t;
			AST tmp3_AST_in = (AST)_t;
			match(_t,STRONG_USERS);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			{
				aimId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
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
				if(entity.declaration.entityMappings == null) {
								reportWarning(entity, entity + " can not be set for strong users: no mappings");
							} else {
								Iterator entityMappingIter = entity.declaration.entityMappings.values().iterator();
								while(entityMappingIter.hasNext()) {
									EEntity_mapping entityMapping = (EEntity_mapping)entityMappingIter.next();
									if(aimId == null || entityMapping.getTarget(null) == aimId) {
										entityMapping.setStrong_users(null, true);
									}
								}
							}
						
			_t = __t20;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void derivedVariant(AST _t,
		final EntityAST entity
	) throws RecognitionException, SdaiException {
		
		AST derivedVariant_AST_in = (AST)_t;
		AST path = null;
		
		try {      // for error handling
			AST __t23 = _t;
			AST tmp4_AST_in = (AST)_t;
			match(_t,DERIVED_VARIANT);
			_t = _t.getFirstChild();
			path = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
				try {
								MappingPathWalker mappingPathWalker = new MappingPathWalker(parser);
								mappingPathWalker.sourceLocator = new SourceLocator() {
										public String getFilename() {
											return parser.getFilename();
										}
										public int getLine() {
											return entity.getLine();
										}
									};
								mappingPathWalker.line = -1;
								LinkedList pathStates = mappingPathWalker.path(path, MappingPathWalker.ATTRIBUTE_PATH);
								if(pathStates != null) {
									entity.createDerivedVariant(pathStates, mappingPathWalker);
								}
							} catch(MappingSemanticException e) {
								reportWarning(e.token, e.message);
							}
						
			_t = __t23;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void strong(AST _t,
		AttributeAST attribute
	) throws RecognitionException, SdaiException {
		
		AST strong_AST_in = (AST)_t;
		AST aimId = null;
		
		try {      // for error handling
			AST __t14 = _t;
			AST tmp5_AST_in = (AST)_t;
			match(_t,STRONG);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			{
				aimId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
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
				if(attribute.declaration.attributeMappings == null) {
								reportWarning(attribute, attribute + " can not be set as strong: no mappings");
							} else {
								Iterator attributeMappingIter = attribute.declaration.attributeMappings.iterator();
								while(attributeMappingIter.hasNext()) {
									MappingForAttribute mappingForAttribute = (
										(MappingForAttribute)attributeMappingIter.next());
									if(mappingForAttribute.attribute.attribute == attribute.attribute &&
										(aimId == null || 
											mappingForAttribute.mappingInstance
											.getParent_entity(null).getTarget(null) == aimId)) {
										mappingForAttribute.mappingInstance.setStrong(null, true);
									}
								}
							}
						
			_t = __t14;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
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
	
