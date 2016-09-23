// $ANTLR 2.7.1: "mappingDataWalker.g" -> "MappingDataWalker.java"$

	package jsdai.mappingUtils.paths;

	import java.util.*;

	import jsdai.lang.*;
	import jsdai.SExtended_dictionary_schema.*;
	import jsdai.SMapping_schema.*;
	import antlr.*;

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


public class MappingDataWalker extends antlr.TreeParser
       implements MappingDataWalkerTokenTypes
 {

	private MappingPathWalker pathWalker;

	private LinkedList attributePath;

	public MappingDataWalker(MappingPathWalker pathWalker) {
		this();
		this.pathWalker = pathWalker;
	}

	public void reportError(RecognitionException ex) {
		pathWalker.reportError(ex);
	}
public MappingDataWalker() {
	tokenNames = _tokenNames;
}

	public final void topType(AST _t,
		WalkerState state
	) throws RecognitionException, SdaiException,MappingSemanticException {
		
		AST topType_AST_in = (AST)_t;
		AST id = null;
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==TYPE_CONSTRAINT)) {
				AST __t3 = _t;
				AST tmp1_AST_in = (AST)_t;
				match(_t,TYPE_CONSTRAINT);
				_t = _t.getFirstChild();
				AST __t4 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,TYPE_CONSTRAINT);
				_t = _t.getFirstChild();
				{
				int _cnt6=0;
				_loop6:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==ID)) {
						id = (AST)_t;
						match(_t,ID);
						_t = _t.getNextSibling();
						if ( inputState.guessing==0 ) {
							state.addFirstToList((EntityAST)id);
						}
					}
					else {
						if ( _cnt6>=1 ) { break _loop6; } else {throw new NoViableAltException(_t);}
					}
					
					_cnt6++;
				} while (true);
				}
				_t = __t4;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					state.getFirst(pathWalker.parser);
				}
				_t = __t3;
				_t = _t.getNextSibling();
			}
			else if (((_t.getType() >= SCHEMA_MAPPING && _t.getType() <= END_OF_PATH_CONSTRAINT))) {
				AST tmp3_AST_in = (AST)_t;
				if ( _t==null ) throw new MismatchedTokenException();
				_t = _t.getNextSibling();
			}
			else if ((_t.getType()==3)) {
			}
			else {
				throw new NoViableAltException(_t);
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
	
	public final EEntity  topPathElement(AST _t,
		LinkedList attributePath, WalkerState state
	) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST topPathElement_AST_in = (AST)_t;
			if(topPathElement_AST_in == null) return null;
			this.attributePath = attributePath;
		
		
		try {      // for error handling
			{
			boolean synPredMatched10 = false;
			if (((_t.getType()==TYPE_CONSTRAINT))) {
				AST __t10 = _t;
				synPredMatched10 = true;
				inputState.guessing++;
				try {
					{
					AST tmp4_AST_in = (AST)_t;
					match(_t,TYPE_CONSTRAINT);
					_t = _t.getNextSibling();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched10 = false;
				}
				_t = __t10;
				inputState.guessing--;
			}
			if ( synPredMatched10 ) {
				path=typeConstraint(_t,state);
				_t = _retTree;
			}
			else if ((_tokenSet_0.member(_t.getType()))) {
				path=pathElement(_t);
				_t = _retTree;
			}
			else if ((_t.getType()==3)) {
			}
			else {
				throw new NoViableAltException(_t);
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
		return path;
	}
	
	public final EEntity  typeConstraint(AST _t,
		WalkerState state
	) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST typeConstraint_AST_in = (AST)_t;
		AST id = null;
			LinkedList typeList = new LinkedList();
		
		
		try {      // for error handling
			AST __t55 = _t;
			AST tmp5_AST_in = (AST)_t;
			match(_t,TYPE_CONSTRAINT);
			_t = _t.getFirstChild();
			AST __t56 = _t;
			AST tmp6_AST_in = (AST)_t;
			match(_t,TYPE_CONSTRAINT);
			_t = _t.getFirstChild();
			{
			int _cnt58=0;
			_loop58:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID)) {
					id = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
						typeList.add(id);
					}
				}
				else {
					if ( _cnt58>=1 ) { break _loop58; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt58++;
			} while (true);
			}
			_t = __t56;
			_t = _t.getNextSibling();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE:
			case NEGATION:
			case INVERSE_ATTRIBUTE_CONSTRAINT:
			case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT:
			case AGGREGATE_MEMBER_CONSTRAINT:
			case STRING_CONSTRAINT:
			case REAL_CONSTRAINT:
			case ENUMERATION_CONSTRAINT:
			case PATH_CONSTRAINT:
			case BOOLEAN_CONSTRAINT:
			case INTEGER_CONSTRAINT:
			case LOGICAL_CONSTRAINT:
			case SELECT_CONSTRAINT:
			case INSTANCE_EQUAL:
			case TYPE_CONSTRAINT:
			case ENTITY_CONSTRAINT:
			case AND_CONSTRAINT:
			case OR_CONSTRAINT:
			case INTERSECTION_CONSTRAINT:
			case END_OF_PATH_CONSTRAINT:
			{
				path=pathElement(_t);
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
			if ( inputState.guessing==0 ) {
					EntityAST typeEntity = EntityAST.construct(pathWalker.parser, typeList, true);
								boolean create = true;
								if(state != null) {
									EntityAST firstState = state.getFirst(pathWalker.parser);
				// 					create = (typeEntity.exactType == firstState.exactType || 
									create = (typeEntity.exactType || 
										!firstState.isEqualTo(typeEntity));
								}
				// 				if(state == null || !state.getFirst(pathWalker.parser).isEqualTo(typeEntity)) {
								if(create) {
									EType_constraint newTypeConstraint;
									if(typeEntity.exactType) {
										newTypeConstraint = (pathWalker.parser.constraintFactory
											.createExactTypeConstraint(
												(EEntity_definition)typeEntity.declaration.definition, path));
									} else {
										newTypeConstraint = (pathWalker.parser.constraintFactory
											.createTypeConstraint((EEntity_definition)typeEntity.declaration.definition,
												path));
									}
									path = newTypeConstraint;
								}
							
			}
			_t = __t55;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  pathElement(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST pathElement_AST_in = (AST)_t;
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE:
			case NEGATION:
			case INVERSE_ATTRIBUTE_CONSTRAINT:
			case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT:
			case AGGREGATE_MEMBER_CONSTRAINT:
			case PATH_CONSTRAINT:
			case SELECT_CONSTRAINT:
			case INSTANCE_EQUAL:
			case TYPE_CONSTRAINT:
			case ENTITY_CONSTRAINT:
			case INTERSECTION_CONSTRAINT:
			{
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case PATH_CONSTRAINT:
				{
					path=pathConstraint(_t);
					_t = _retTree;
					break;
				}
				case INVERSE_ATTRIBUTE_CONSTRAINT:
				case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT:
				{
					path=inverseAttributeConstraint(_t);
					_t = _retTree;
					break;
				}
				case AGGREGATE_MEMBER_CONSTRAINT:
				{
					path=aggregateMemberConstraint(_t);
					_t = _retTree;
					break;
				}
				case SELECT_CONSTRAINT:
				{
					path=selectConstraint(_t);
					_t = _retTree;
					break;
				}
				case INSTANCE_EQUAL:
				{
					path=instanceEqual(_t);
					_t = _retTree;
					break;
				}
				case ENTITY_CONSTRAINT:
				{
					path=entityConstraint(_t);
					_t = _retTree;
					break;
				}
				case ATTRIBUTE:
				{
					path=attribute(_t);
					_t = _retTree;
					break;
				}
				case TYPE_CONSTRAINT:
				{
					path=typeConstraint(_t,null);
					_t = _retTree;
					break;
				}
				case INTERSECTION_CONSTRAINT:
				{
					path=intersectionConstraint(_t);
					_t = _retTree;
					break;
				}
				case NEGATION:
				{
					path=negationConstraint(_t);
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
						if(attributePath != null && 
										((ParserAST)pathElement_AST_in).getInPath() == ParserAST.IN_PATH) {
					
										attributePath.addLast(path);
									}
								
				}
				break;
			}
			case END_OF_PATH_CONSTRAINT:
			{
				path=endOfPathConstraint(_t);
				_t = _retTree;
				break;
			}
			case STRING_CONSTRAINT:
			case REAL_CONSTRAINT:
			case ENUMERATION_CONSTRAINT:
			case BOOLEAN_CONSTRAINT:
			case INTEGER_CONSTRAINT:
			case LOGICAL_CONSTRAINT:
			{
				path=attributeValueConstraint(_t);
				_t = _retTree;
				break;
			}
			case AND_CONSTRAINT:
			{
				path=and(_t);
				_t = _retTree;
				break;
			}
			case OR_CONSTRAINT:
			{
				path=or(_t);
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
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  pathConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST pathConstraint_AST_in = (AST)_t;
		AST aio = null;
			EEntity remainingPath = null;
		
		
		try {      // for error handling
			AST __t15 = _t;
			AST tmp7_AST_in = (AST)_t;
			match(_t,PATH_CONSTRAINT);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AGGREGATE_MEMBER_CONSTRAINT:
			{
				path=aggregateMemberConstraint(_t);
				_t = _retTree;
				break;
			}
			case INVERSE_ATTRIBUTE_CONSTRAINT:
			case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT:
			{
				path=inverseAttributeConstraint(_t);
				_t = _retTree;
				break;
			}
			case ENTITY_CONSTRAINT:
			{
				path=entityConstraint(_t);
				_t = _retTree;
				break;
			}
			case ATTRIBUTE:
			{
				path=attribute(_t);
				_t = _retTree;
				break;
			}
			case INTERSECTION_CONSTRAINT:
			{
				path=intersectionConstraint(_t);
				_t = _retTree;
				break;
			}
			case ATTRIBUTE_INVERSE_ONLY:
			{
				aio = (AST)_t;
				match(_t,ATTRIBUTE_INVERSE_ONLY);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						if(aio != null) {
											throw new MappingSemanticException(aio, "Attribute " + aio.getText() + 
												" can be used in this way only in the context of inverse constraint");
										}
									
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			remainingPath=pathElement(_t);
			_t = _retTree;
			_t = __t15;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					EPath_constraint newPathConstraint = (
								pathWalker.parser.constraintFactory.createPathConstraint(path, remainingPath));
							path = newPathConstraint;
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  inverseAttributeConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST inverseAttributeConstraint_AST_in = (AST)_t;
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case INVERSE_ATTRIBUTE_CONSTRAINT:
			{
				AST __t19 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,INVERSE_ATTRIBUTE_CONSTRAINT);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ATTRIBUTE:
				{
					path=attribute(_t);
					_t = _retTree;
					break;
				}
				case ENTITY_CONSTRAINT:
				{
					path=entityConstraint(_t);
					_t = _retTree;
					break;
				}
				case AGGREGATE_MEMBER_CONSTRAINT:
				{
					path=aggregateMemberConstraint(_t);
					_t = _retTree;
					break;
				}
				case SELECT_CONSTRAINT:
				{
					path=selectConstraint(_t);
					_t = _retTree;
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t19;
				_t = _t.getNextSibling();
				break;
			}
			case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT:
			{
				AST __t21 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,INVERSE_ATTRIBUTE_CONSTRAINT_SELECT);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ATTRIBUTE:
				{
					path=attribute(_t);
					_t = _retTree;
					break;
				}
				case ENTITY_CONSTRAINT:
				{
					path=entityConstraint(_t);
					_t = _retTree;
					break;
				}
				case AGGREGATE_MEMBER_CONSTRAINT:
				{
					path=aggregateMemberConstraint(_t);
					_t = _retTree;
					break;
				}
				case SELECT_CONSTRAINT:
				{
					path=selectConstraint(_t);
					_t = _retTree;
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t21;
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
					EInverse_attribute_constraint newInvAttConstraint = (
								pathWalker.parser.constraintFactory.createInverseAttributeConstraint(path));
							path = newInvAttConstraint;
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  aggregateMemberConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST aggregateMemberConstraint_AST_in = (AST)_t;
		AST member = null;
		
		try {      // for error handling
			AST __t24 = _t;
			AST tmp10_AST_in = (AST)_t;
			match(_t,AGGREGATE_MEMBER_CONSTRAINT);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE:
			{
				path=attribute(_t);
				_t = _retTree;
				break;
			}
			case ENTITY_CONSTRAINT:
			{
				path=entityConstraint(_t);
				_t = _retTree;
				break;
			}
			case INVERSE_ATTRIBUTE_CONSTRAINT:
			case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT:
			{
				path=inverseAttributeConstraint(_t);
				_t = _retTree;
				break;
			}
			case AGGREGATE_MEMBER_CONSTRAINT:
			{
				path=aggregateMemberConstraint(_t);
				_t = _retTree;
				break;
			}
			case SELECT_CONSTRAINT:
			{
				path=selectConstraint(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case INT:
			{
				member = (AST)_t;
				match(_t,INT);
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
			_t = __t24;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					EAggregate_member_constraint newAggMembConstraint = (pathWalker.parser
								.constraintFactory.createAggregateMemberConstraint(path, 
									member != null ? Integer.parseInt(member.getText()) : -1));
							path = newAggMembConstraint;
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  selectConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST selectConstraint_AST_in = (AST)_t;
		AST id = null;
			LinkedList dataTypeList = new LinkedList();
		
		
		try {      // for error handling
			AST __t41 = _t;
			AST tmp11_AST_in = (AST)_t;
			match(_t,SELECT_CONSTRAINT);
			_t = _t.getFirstChild();
			AST __t42 = _t;
			AST tmp12_AST_in = (AST)_t;
			match(_t,SELECT_DATA_TYPE);
			_t = _t.getFirstChild();
			{
			int _cnt44=0;
			_loop44:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID)) {
					id = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
						dataTypeList.addFirst(((EntityAST)id).declaration.definition);
					}
				}
				else {
					if ( _cnt44>=1 ) { break _loop44; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt44++;
			} while (true);
			}
			_t = __t42;
			_t = _t.getNextSibling();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AGGREGATE_MEMBER_CONSTRAINT:
			{
				path=aggregateMemberConstraint(_t);
				_t = _retTree;
				break;
			}
			case ATTRIBUTE:
			{
				path=attribute(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t41;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					ESelect_constraint newSelectConstraint = (pathWalker.parser.constraintFactory
									.createSelectConstraint(path, dataTypeList));
							path = newSelectConstraint;
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  instanceEqual(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST instanceEqual_AST_in = (AST)_t;
			EEntity path2 = null;
		
		
		try {      // for error handling
			AST __t47 = _t;
			AST tmp13_AST_in = (AST)_t;
			match(_t,INSTANCE_EQUAL);
			_t = _t.getFirstChild();
			path=pathElement(_t);
			_t = _retTree;
			path2=pathElement(_t);
			_t = _retTree;
			_t = __t47;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					EInstance_equal newInstanceEqual = (pathWalker.parser.constraintFactory
									.createInstanceEqual(path, path2));
							path = newInstanceEqual;
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  entityConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST entityConstraint_AST_in = (AST)_t;
		AST id = null;
			LinkedList domainList = new LinkedList();
		
		
		try {      // for error handling
			AST __t49 = _t;
			AST tmp14_AST_in = (AST)_t;
			match(_t,ENTITY_CONSTRAINT);
			_t = _t.getFirstChild();
			AST __t50 = _t;
			AST tmp15_AST_in = (AST)_t;
			match(_t,TYPE_CONSTRAINT);
			_t = _t.getFirstChild();
			{
			int _cnt52=0;
			_loop52:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID)) {
					id = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
						domainList.add(id);
					}
				}
				else {
					if ( _cnt52>=1 ) { break _loop52; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt52++;
			} while (true);
			}
			_t = __t50;
			_t = _t.getNextSibling();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE:
			{
				path=attribute(_t);
				_t = _retTree;
				break;
			}
			case INVERSE_ATTRIBUTE_CONSTRAINT:
			case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT:
			{
				path=inverseAttributeConstraint(_t);
				_t = _retTree;
				break;
			}
			case AGGREGATE_MEMBER_CONSTRAINT:
			{
				path=aggregateMemberConstraint(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t49;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					EntityAST domainEntity = EntityAST.construct(pathWalker.parser, domainList, true);
							EEntity_constraint newEntityConstraint;
							if(domainEntity.exactType) {
								newEntityConstraint = (pathWalker.parser
									.constraintFactory.createExactEntityConstraint(path, 
										(EEntity_definition)domainEntity.declaration.definition));
							} else {
								newEntityConstraint = (pathWalker.parser
									.constraintFactory.createEntityConstraint(path, 
										(EEntity_definition)domainEntity.declaration.definition));
							}
							path = newEntityConstraint;
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  attribute(AST _t) throws RecognitionException {
		EEntity path = null;
		
		AST attribute_AST_in = (AST)_t;
		AST attribute = null;
		
		try {      // for error handling
			attribute = (AST)_t;
			match(_t,ATTRIBUTE);
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
				path = ((AttributeAST)attribute).attribute;
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  intersectionConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST intersectionConstraint_AST_in = (AST)_t;
			EIntersection_constraint newIntersectionConstraint = null;
			Set subpaths = new HashSet();
		
		
		try {      // for error handling
			AST __t61 = _t;
			AST tmp16_AST_in = (AST)_t;
			match(_t,INTERSECTION_CONSTRAINT);
			_t = _t.getFirstChild();
			{
			int _cnt63=0;
			_loop63:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					path=pathElement(_t);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
						subpaths.add(path);
					}
				}
				else {
					if ( _cnt63>=1 ) { break _loop63; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt63++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
					newIntersectionConstraint = (pathWalker.parser.constraintFactory
									.createIntersectionConstraint(subpaths));
								path = newIntersectionConstraint;
							
			}
			_t = __t61;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  negationConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST negationConstraint_AST_in = (AST)_t;
		
		try {      // for error handling
			AST __t65 = _t;
			AST tmp17_AST_in = (AST)_t;
			match(_t,NEGATION);
			_t = _t.getFirstChild();
			path=pathElement(_t);
			_t = _retTree;
			if ( inputState.guessing==0 ) {
					ENegation_constraint newNegationConstraint = (pathWalker.parser.constraintFactory
									.createNegationConstraint(path));
								path = newNegationConstraint;
							
			}
			_t = __t65;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  endOfPathConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST endOfPathConstraint_AST_in = (AST)_t;
		
		try {      // for error handling
			AST __t67 = _t;
			AST tmp18_AST_in = (AST)_t;
			match(_t,END_OF_PATH_CONSTRAINT);
			_t = _t.getFirstChild();
			path=pathElement(_t);
			_t = _retTree;
			if ( inputState.guessing==0 ) {
					EEnd_of_path_constraint newEndOfPathConstraint = (pathWalker.parser.constraintFactory
									.createEndOfPathConstraint(path));
								path = newEndOfPathConstraint;
							
			}
			_t = __t67;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  attributeValueConstraint(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST attributeValueConstraint_AST_in = (AST)_t;
		AST stringValue = null;
		AST enumValue = null;
		AST boolValue = null;
		AST intValue = null;
		AST logicalValue = null;
			EEntity valuePath = null;
			double realValue = 0;
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case STRING_CONSTRAINT:
			{
				AST __t29 = _t;
				AST tmp19_AST_in = (AST)_t;
				match(_t,STRING_CONSTRAINT);
				_t = _t.getFirstChild();
				stringValue = (AST)_t;
				match(_t,STRING);
				_t = _t.getNextSibling();
				valuePath=attributeValueConstraintSelect(_t);
				_t = _retTree;
				_t = __t29;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						EString_constraint newStringConstraint = (pathWalker.parser.constraintFactory
										.createStringConstraint(valuePath, stringValue.getText()));
									path = newStringConstraint;
								
				}
				break;
			}
			case REAL_CONSTRAINT:
			{
				AST __t30 = _t;
				AST tmp20_AST_in = (AST)_t;
				match(_t,REAL_CONSTRAINT);
				_t = _t.getFirstChild();
				realValue=intOrFloat(_t);
				_t = _retTree;
				valuePath=attributeValueConstraintSelect(_t);
				_t = _retTree;
				_t = __t30;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						EReal_constraint newRealConstraint = (pathWalker.parser.constraintFactory
										.createRealConstraint(valuePath, realValue));
									path = newRealConstraint;
								
				}
				break;
			}
			case ENUMERATION_CONSTRAINT:
			{
				AST __t31 = _t;
				AST tmp21_AST_in = (AST)_t;
				match(_t,ENUMERATION_CONSTRAINT);
				_t = _t.getFirstChild();
				enumValue = (AST)_t;
				match(_t,ENUM);
				_t = _t.getNextSibling();
				valuePath=attributeValueConstraintSelect(_t);
				_t = _retTree;
				_t = __t31;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						EEnumeration_constraint newEnumConstraint = (pathWalker.parser.constraintFactory
										.createEnumerationConstraint(valuePath, enumValue.getText().toLowerCase()));
									path = newEnumConstraint;
								
				}
				break;
			}
			case BOOLEAN_CONSTRAINT:
			{
				AST __t32 = _t;
				AST tmp22_AST_in = (AST)_t;
				match(_t,BOOLEAN_CONSTRAINT);
				_t = _t.getFirstChild();
				boolValue = (AST)_t;
				match(_t,ENUM);
				_t = _t.getNextSibling();
				valuePath=attributeValueConstraintSelect(_t);
				_t = _retTree;
				_t = __t32;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						String boolString = boolValue.getText().toUpperCase();
									EBoolean_constraint newBoolConstraint = (pathWalker.parser.constraintFactory
										.createBooleanConstraint(valuePath, boolString.equals("TRUE") ? true : false));
									path = newBoolConstraint;
								
				}
				break;
			}
			case INTEGER_CONSTRAINT:
			{
				AST __t33 = _t;
				AST tmp23_AST_in = (AST)_t;
				match(_t,INTEGER_CONSTRAINT);
				_t = _t.getFirstChild();
				intValue = (AST)_t;
				match(_t,INT);
				_t = _t.getNextSibling();
				valuePath=attributeValueConstraintSelect(_t);
				_t = _retTree;
				_t = __t33;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						EInteger_constraint newIntConstraint = (pathWalker.parser.constraintFactory
										.createIntegerConstraint(valuePath, Integer.parseInt(intValue.getText())));
									path = newIntConstraint;
								
				}
				break;
			}
			case LOGICAL_CONSTRAINT:
			{
				AST __t34 = _t;
				AST tmp24_AST_in = (AST)_t;
				match(_t,LOGICAL_CONSTRAINT);
				_t = _t.getFirstChild();
				logicalValue = (AST)_t;
				match(_t,ENUM);
				_t = _t.getNextSibling();
				valuePath=attributeValueConstraintSelect(_t);
				_t = _retTree;
				_t = __t34;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						String logicalString = logicalValue.getText().toUpperCase();
									ELogical_constraint newLogicalConstraint = (pathWalker.parser.constraintFactory
										.createLogicalConstraint(valuePath, 
											logicalString.equals("UNKNOWN") ? 3 : (logicalString.equals("TRUE") ? 2 : 1)));
									path = newLogicalConstraint;
								
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			if ( inputState.guessing==0 ) {
					if(attributePath != null && 
								((ParserAST)attributeValueConstraint_AST_in).getInPath() == ParserAST.IN_PATH) {
								
								attributePath.addLast(valuePath);
							}
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  and(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST and_AST_in = (AST)_t;
			EEntity path2 = null;
		
		
		try {      // for error handling
			AST __t69 = _t;
			AST tmp25_AST_in = (AST)_t;
			match(_t,AND_CONSTRAINT);
			_t = _t.getFirstChild();
			path=pathElement(_t);
			_t = _retTree;
			path2=pathElement(_t);
			_t = _retTree;
			_t = __t69;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					EAnd_constraint_relationship newAndConstraint = (pathWalker.parser.constraintFactory
									.createAndConstraintRelationship(path, path2));
							path = newAndConstraint;
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  or(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST or_AST_in = (AST)_t;
		AST or = null;
			EEntity path2 = null;
		
		
		try {      // for error handling
			AST __t71 = _t;
			or = _t==ASTNULL ? null :(AST)_t;
			match(_t,OR_CONSTRAINT);
			_t = _t.getFirstChild();
			path=pathElement(_t);
			_t = _retTree;
			path2=pathElement(_t);
			_t = _retTree;
			_t = __t71;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					EOr_constraint_relationship newOrConstraint = (pathWalker.parser.constraintFactory
									.createOrConstraintRelationship(path, path2));
							path = newOrConstraint;
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final EEntity  attributeValueConstraintSelect(AST _t) throws RecognitionException, SdaiException,MappingSemanticException {
		EEntity path = null;
		
		AST attributeValueConstraintSelect_AST_in = (AST)_t;
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE:
			{
				path=attribute(_t);
				_t = _retTree;
				break;
			}
			case AGGREGATE_MEMBER_CONSTRAINT:
			{
				path=aggregateMemberConstraint(_t);
				_t = _retTree;
				break;
			}
			case SELECT_CONSTRAINT:
			{
				path=selectConstraint(_t);
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
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return path;
	}
	
	public final double  intOrFloat(AST _t) throws RecognitionException {
		double value = 0;
		
		AST intOrFloat_AST_in = (AST)_t;
		AST i = null;
		AST f = null;
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case INT:
			{
				i = (AST)_t;
				match(_t,INT);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					value = Integer.parseInt(i.getText());
				}
				break;
			}
			case FLOAT:
			{
				f = (AST)_t;
				match(_t,FLOAT);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					value = new Double(f.getText()).doubleValue();
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return value;
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
		"ASTERISK",
		"INVERSE_ATTRIBUTE_CONSTRAINT",
		"INVERSE_ATTRIBUTE_CONSTRAINT_SELECT",
		"AGGREGATE_MEMBER_CONSTRAINT",
		"ATTRIBUTE_INVERSE_ONLY",
		"AGGREGATE_MEMBER_INVERSE_ONLY",
		"STRING_CONSTRAINT",
		"REAL_CONSTRAINT",
		"ENUMERATION_CONSTRAINT",
		"PATH_CONSTRAINT",
		"BOOLEAN_CONSTRAINT",
		"INTEGER_CONSTRAINT",
		"LOGICAL_CONSTRAINT",
		"SELECT_CONSTRAINT",
		"SELECT_DATA_TYPE",
		"INSTANCE_EQUAL",
		"TYPE_CONSTRAINT",
		"ENTITY_CONSTRAINT",
		"AND_CONSTRAINT",
		"OR_CONSTRAINT",
		"INTERSECTION_CONSTRAINT",
		"END_OF_PATH_CONSTRAINT"
	};
	
	private static final long _tokenSet_0_data_[] = { 274877911040L, 35887630033879040L, 0L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	}
	
