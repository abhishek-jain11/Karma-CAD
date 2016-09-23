// $ANTLR 2.7.1: "mappingPathWalker.g" -> "MappingPathWalker.java"$

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


public class MappingPathWalker extends antlr.TreeParser
       implements MappingPathWalkerTokenTypes
 {

	static public final int ENTITY_PATH = 0;
	static public final int ATTRIBUTE_PATH = 1;

	static private final int COMPLETION_NOT_FINISHED = 0;
	static private final int COMPLETION_FINISHED_ALL = 1;
	static private final int COMPLETION_FINISHED_CONSTRAINT = 2;

	static private final String NESTED_EXCEPTION_MESSAGE = "";
	static private final String REFERENCED_ERROR_MESSAGE = "Error/warning in referenced mapping";

	public MappingPathParser parser;
	public int line;
    public SourceLocator sourceLocator;

	public EntityAST armEntity;
	public LinkedList aimEntityList = new LinkedList();
	public boolean dontReport;
	public MappingSemanticException notReported;
	private int constraintLevel;
	private boolean errorOccured;
	private boolean pathDetected;

	/** This set contains all possible mapped values or null if mapped values 
	 *	are not used for this attribute. It is set to null in case of entity mapping also
	 */
	private Set mappedValueSet;
	private Map mappedValues;

	/** This flag is true when mapped value was used in mapping path
	 *	this turns mapped value detection off
	 */
	private boolean mappedValueUsed;

	/** This flag is true when mapped value was really used in mapping tree
	 *	either explicitly in mappin path or through detection in constraints
	 */
	private boolean mappedValueReallyUsed;

	private WalkerOptions defaultOpt = new WalkerOptions();

	public MappingPathWalker(MappingPathParser parser) {
		this();
		this.parser = parser;
		setASTNodeClass("jsdai.mappingUtils.paths.MappingAST");
	}

	public void reportError(RecognitionException ex) {
		System.err.println(antlr.FileLineFormatter.getFormatter()
			.getFormatString(sourceLocator.getFilename(), line) + ex.getMessage());
	}

	public void reportError(AST token, String text) {
		reportWarning(token, text, true);
		errorOccured = true;
	}

	public void reportWarning(AST token, String text) {
		MappingSemanticException nr = notReported;
		notReported = null;
		for( ; nr != null; nr = (MappingSemanticException)nr.getCause()) {
			reportWarning(nr.token, nr.message, false);
		}
		reportWarning(token, text, true);
	}

	private void reportWarning(AST token, String text, boolean useSourceLocator) {
		boolean wasReported = false;
		for(ListIterator i = locators.listIterator(locators.size()); i.hasPrevious(); ) {
			Object locatorObj = i.previous();
			if(locatorObj instanceof SourceLocator) {
				SourceLocator locator = (SourceLocator)locatorObj;
				if(!wasReported) {
					int reportLine = line >= 0 ? line : locator.getLine();
					System.err.println(antlr.FileLineFormatter.getFormatter()
									   .getFormatString(locator.getFilename(), 
														token instanceof ParserAST
														? ((ParserAST)token).getLine()
														: reportLine) + text);
					wasReported = true;
				} else {
					System.err.println(antlr.FileLineFormatter.getFormatter()
									   .getFormatString(locator.getFilename(), locator.getLine()) +
									   REFERENCED_ERROR_MESSAGE);
				}
			}
		}
		if(useSourceLocator) {
			if(!wasReported) {
				int reportLine = line >= 0 ? line : sourceLocator.getLine();
				System.err.println(antlr.FileLineFormatter.getFormatter()
								   .getFormatString(sourceLocator.getFilename(), 
													token instanceof ParserAST
													? ((ParserAST)token).getLine() : reportLine) + text);
			} else {
				System.err.println(antlr.FileLineFormatter.getFormatter()
								   .getFormatString(sourceLocator.getFilename(), sourceLocator.getLine()) +
								   REFERENCED_ERROR_MESSAGE);
			}
		}
	}

	private static class SourceLocatorMark {
		private SourceLocatorMark() { }
	}

	private List locators = new ArrayList();

	private void clearLocators(SourceLocatorMark mark) {
		for(ListIterator i = locators.listIterator(locators.size()); i.hasPrevious(); ) {
			Object locator = i.previous();
			i.remove();
			if(locator == mark) {
				break;
			}
		}
	}

	private void pushLocatorMark(SourceLocatorMark mark) {
		locators.add(mark);
	}

	private void pushLocator(SourceLocator sourceLocator) {
		locators.add(sourceLocator);
	}

	private List dupLocators() {
		List locatorCopy = new ArrayList(locators.size() + 1);
		locatorCopy.add(sourceLocator);
		locatorCopy.addAll(locators);
		return locatorCopy;
	}

	// Utility methods used for mapping path construction
	public AST getRemainingOverPathConstraint(WalkerState state) {
		return getRemainingOverPathConstraint(state.remaining);
	}

	public AST getRemainingOverPathConstraint(AST tree) {
		if(tree != null) {
			return (tree.getType() == PATH_CONSTRAINT ? 
				tree.getFirstChild() : tree);
		} else
		return null;
	}

	public void setRemainingOverPathConstraint(WalkerState state, AST newRemaining) {
		if(state.remaining != null) {
			if(state.remaining.getType() == PATH_CONSTRAINT) {
				AST sibling = state.remaining.getFirstChild().getNextSibling();
				state.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(newRemaining,PATH_CONSTRAINT)).add(newRemaining).add(sibling));
			} else {
				state.remaining = newRemaining;
			}
		} else
			state.remaining = newRemaining;
	}

	static public AST dupAttachTree(AST ast) {
		if(ast == null) return null;
		if(ast instanceof ParserAST) {
			return ((ParserAST)ast).dupAttachTree();
		}
		AST newAst = new CommonAST();
		newAst.setType(ast.getType());
		newAst.setText(ast.getText());
		newAst.setFirstChild(ast.getFirstChild());
		return newAst;
	}

	static public AST dupWholeTree(AST ast) {
		if(ast == null) return null;
		AST newAst = dupAttachTree(ast);
		if(ast.getFirstChild() != null) newAst.setFirstChild(dupWholeTree(ast.getFirstChild()));
		if(ast.getNextSibling() != null) newAst.setNextSibling(dupWholeTree(ast.getNextSibling()));
		return newAst;
	}

	public AST auxiliaryAST(int type) {
		return auxiliaryAST(null, type);
	}

	public AST auxiliaryAST(AST parentAST, int type) {
		int line = 0;
		String name = (parentAST != null && parentAST instanceof ParserAST && 
			(line = ((ParserAST)parentAST).getLine()) != 0 ? 
			_tokenNames[type] + " at line " + line :
			_tokenNames[type] + (parentAST != null ? " nearby " + parentAST.getText() : ""));
		AST newAst = ((AST)astFactory.create(type,name));
		((ParserAST)newAst).setLine(line);
		return newAst;
	}

	private static AST skipOverPathElement(AST node) {
		int nodeType;
		while(node != null && ((nodeType = node.getType()) == MappingPathParser.TEMPLATE_CALL
							   || nodeType == MappingPathParser.ORIGINAL_LOCATION)) {
			node = node.getNextSibling();
		}
		if(node != null) {
			node = node.getNextSibling();
			if(node != null && node.getType() == MappingPathParser.ENUM) {
				node = node.getNextSibling();
			}
		}
		return node;
	}

	// Mapping path construction methods

	private void cleanInnerElement(LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		if(remainingStateList != null && innerElementOpt.reachedAndEnd != null) {
			Iterator remainingIter = remainingStateList.iterator();
			while(remainingIter.hasNext()) {
				WalkerState remainingState = WalkerState.next(remainingIter);
				remainingState.remaining = null;
//DEBUG 				reportWarning(remainingState.getFirst(parser), 
// 					"reached andEnd at " + remainingState.getFirst(parser));
			}
		}
	}

	public LinkedList stateAtEntity(EntityAST id, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(remainingStateList == null) {
			WalkerState remainingState = new WalkerState(id);
			if(id.exactType) {
				addTypeConstraint(id, remainingState, id.dup());
			}
			return WalkerState.createList(remainingState);
		} else {
			Iterator remainingIter = remainingStateList.iterator();
			while(remainingIter.hasNext()) {
				WalkerState remainingState = WalkerState.next(remainingIter);
				EntityAST remainingFirst = remainingState.getFirst(parser);
				if(!id.isSame(remainingFirst)) {
					if(id.isSelectOf(remainingFirst) || remainingFirst.isSelectOf(id) ||
					   remainingFirst.isExtensionOf(id) || id.isExtensionOf(remainingFirst)) {

						// Ugly hack for select
						remainingState.setFirst(id);
					} else {
						throw new MappingSemanticException(id, "Illegal sequence: " + id.getText() + 
							" " + remainingFirst.getText());
					}
				}
				if(id.exactType) {
					addTypeConstraint(id, remainingState, id.dup());
				}
			}
			return remainingStateList;
		}
	}

	public LinkedList stateAtExact(EntityAST id, LinkedList remainingStateList, WalkerOptions options)
	throws MappingSemanticException, SdaiException {
		if(id.exactType) {
			if(options.postProcess) remainingStateList = postProcessState(remainingStateList, false);
			Iterator remainingIter = remainingStateList.iterator();
			while(remainingIter.hasNext()) {
				WalkerState remainingState = WalkerState.next(remainingIter);
				addTypeConstraint(id, remainingState, id.dup());
			}
		}
		return remainingStateList;
	}

	private void addTypeConstraint(EntityAST id, WalkerState remainingState, AST typeConstraintAST) {
		AST typeConstraintBody = auxiliaryAST(id, TYPE_CONSTRAINT);
		typeConstraintBody.setFirstChild(typeConstraintAST);
		if(remainingState.remaining != null) {
			if(remainingState.remaining.getType() != TYPE_CONSTRAINT) {
				remainingState.remaining = (
					(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add(typeConstraintBody).add(remainingState.remaining))
				);
			} else {
				// #(T.C #(T.C. dataType etc.) remaining)
				remainingState.remaining.getFirstChild().addChild(typeConstraintAST);
			}
		} else {
			remainingState.remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add(typeConstraintBody));
		}
	}

	public LinkedList stateAtEntityAggreg(EntityAST id, AST index, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(id.isEntityDefinition()) {
			throw new MappingSemanticException(id, "Defined type expected here");
		}
		EEntity domain = id.getRealDomain();
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
			if(!id.isSame(remainingState.getFirst(parser))) {
				throw new MappingSemanticException(id, "Illegal sequence: " + id.getText() + 
												   "[] " + remainingState.getFirst(parser).getText());
			}
			AST topRemaining = getRemainingOverPathConstraint(remainingState);
			int topRemainingType = topRemaining.getType();
			if(topRemainingType == INVERSE_ATTRIBUTE_CONSTRAINT
			|| topRemainingType == INVERSE_ATTRIBUTE_CONSTRAINT_SELECT) {
				AST oldInverse = topRemaining.getFirstChild();
				AST oldNextSibling = oldInverse.getNextSibling();
				oldInverse.setNextSibling(null);
				AST aggregateMemberConstraint = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,AGGREGATE_MEMBER_CONSTRAINT)).add(oldInverse).add(astFactory.dup(index)));
				aggregateMemberConstraint.setNextSibling(oldNextSibling);
				topRemaining.setFirstChild(aggregateMemberConstraint);
			} else {
				setRemainingOverPathConstraint(remainingState, 
					(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,INVERSE_ATTRIBUTE_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,AGGREGATE_MEMBER_CONSTRAINT)).add(topRemaining).add(astFactory.dup(index)))).add(dupAttachTree(topRemaining)))
				);
			}
		}
		return remainingStateList;
	}

	public LinkedList stateAtEqEntity(EntityAST id, LinkedList remainingStateList)
	throws MappingSemanticException {
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Value is missing: " + id.getText());
		}
		if(id.declaration.type != DictionaryDeclaration.TYPE) {
			throw new MappingSemanticException(id, "Defined type expected but entity definition found: " +
											   id.getText());
		}
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
			remainingState.setFirst(id);
			remainingState.remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,EQ_ENTITY)).add(remainingState.remaining));
		}
		return remainingStateList;
	}
	
	public LinkedList stateAtFromPoint(EntityAST id, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " <-");
		}
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
			if(remainingState.remaining == null) {
				throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " <-");
			}
			AST typeConstraint = null;
			if(remainingState.remaining.getType() == TYPE_CONSTRAINT) {
				// #(T.C #(T.C. dataType etc.) remaining)
				typeConstraint = dupAttachTree(remainingState.remaining.getFirstChild());
				remainingState.remaining = remainingState.remaining.getFirstChild().getNextSibling();
			}
			AST attributeAST = getRemainingOverPathConstraint(remainingState);
			EntityAST remainingFirst = remainingState.getFirst(parser);

			if(remainingFirst.isDomainValid(id)) {

				int remainingType = remainingState.remaining.getType();
				if(remainingType == PATH_CONSTRAINT) {
					remainingType = remainingState.remaining.getFirstChild().getType();
				}
				boolean wrongRemaining = false;
				if(remainingFirst instanceof AttributeAST) {
					if(remainingType != ATTRIBUTE 
					&& remainingType != AGGREGATE_MEMBER_CONSTRAINT 
					&& remainingType != ATTRIBUTE_INVERSE_ONLY 
					&& remainingType != AGGREGATE_MEMBER_INVERSE_ONLY) {
						wrongRemaining = true;
					}
				} else {
					if(remainingType != INVERSE_ATTRIBUTE_CONSTRAINT
					&&  remainingType != INVERSE_ATTRIBUTE_CONSTRAINT_SELECT) {
						wrongRemaining = true;
					}
				}
				if(wrongRemaining) {
					throw new MappingSemanticException(id, "Wrong type follows after " + id.getText() +
													   " <- " + remainingFirst +
													   " : " + _tokenNames[remainingType]);
				}
				if(remainingFirst instanceof AttributeAST) {
					AST iac = auxiliaryAST(id, (id.isSelectType() ? 
							INVERSE_ATTRIBUTE_CONSTRAINT_SELECT :
							INVERSE_ATTRIBUTE_CONSTRAINT));
					AST newAttributeAST = dupAttachTree(attributeAST);
					if(newAttributeAST.getType() == ATTRIBUTE_INVERSE_ONLY) {
						newAttributeAST.setType(ATTRIBUTE);
					} else if(newAttributeAST.getType() == AGGREGATE_MEMBER_INVERSE_ONLY) {
						newAttributeAST.setType(AGGREGATE_MEMBER_CONSTRAINT);
					}
					iac = (AST)astFactory.make( (new ASTArray(2)).add(iac).add(newAttributeAST));
					if(typeConstraint != null) {
						iac =(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,ENTITY_CONSTRAINT)).add(typeConstraint).add(iac));
					}
					setRemainingOverPathConstraint(remainingState, iac);
				}
			} else {
				throw new MappingSemanticException(id, "Illegal type: " + id.getText() + " for " +
												   remainingFirst);
			}
			remainingState.setFirst(id);
		}
		if(innerElementOpt.reachedAndEnd != null) {
			LinkedList postAndStateList = remainingStateList;
			remainingStateList = new LinkedList();
			remainingIter = postAndStateList.iterator();
			while(remainingIter.hasNext()) {
				WalkerState remainingState = WalkerState.next(remainingIter);
				WalkerState newRemainingState = remainingState.dup();
				// INVERSE_ATTRIBUTE_CONSTRAINT goes into intersection and then remaining part 
				if(newRemainingState.remaining.getType() == PATH_CONSTRAINT) {
					newRemainingState.remaining = remainingState.remaining.getFirstChild();
					remainingState.remaining = newRemainingState.remaining.getNextSibling();
					newRemainingState.remaining.setNextSibling(null);
				} else {
					remainingState.remaining = null;
				}
				remainingStateList.add(newRemainingState);
			}
			innerElementOpt.reachedAndEnd.andConstruct.remainingStateList = postAndStateList;
		}

		return remainingStateList;
	}

	private boolean oneStateAtSuper(EntityAST id, WalkerState remainingState, boolean throwException)
	throws MappingSemanticException, SdaiException {
		AST typeConstraintAST = null;
		if(remainingState.getFirstList() == null) {
			EntityAST remainingFirst = remainingState.getFirst(parser);
			if(!((remainingFirst.exactType
			&& id.declaration.definition == remainingFirst.declaration.definition)
			|| id.isSupertypeOf(remainingFirst))) {
				if(!throwException) {
					return false;
				} else {
					throw new MappingSemanticException(id, id.getText() + " is not a supertype of " +
													   remainingFirst.getText());
				}
			}
			typeConstraintAST = remainingState.getFirst(parser).dup();
		} else {
			Iterator firstIter = remainingState.getFirstList().iterator();
			while(firstIter.hasNext()) {
				EntityAST first = (EntityAST)firstIter.next();
				if(!((first.exactType
				&& id.declaration.definition == first.declaration.definition)
				|| id.isSupertypeOf(first))) {
					if(!throwException) {
						return false;
					} else {
						throw new MappingSemanticException(id, id.getText() + 
														   " is not a supertype of " + first.getText());
					}
				}
				AST typeConstraintElement = first.dup();
				typeConstraintElement.setNextSibling(typeConstraintAST);
				typeConstraintAST = typeConstraintElement;
			}
		}
		addTypeConstraint(id, remainingState, typeConstraintAST);
		remainingState.setFirst(id);
		return true;
	}

	public LinkedList stateAtSuper(EntityAST id, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " =>");
		}
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
			oneStateAtSuper(id, remainingState, true);
		}
		return remainingStateList;
	}

	public LinkedList stateAtSub(EntityAST id, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " <=");
		}
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
			if(remainingState.getFirstList() == null) {
				if(!remainingState.getFirst(parser).isSupertypeOf(id)) {
					throw new MappingSemanticException(id, id.getText() + " is not a subtype of " +
													   remainingState.getFirst(parser).getText());
				}
			} else {
				Iterator firstIter = remainingState.getFirstList().iterator();
				while(firstIter.hasNext()) {
					EntityAST first = (EntityAST)firstIter.next();
					if(!first.isSupertypeOf(id)) {
						throw new MappingSemanticException(id, id.getText() + " is not a subtype of " +
														   first.getText());
					}
				}
			}
			remainingState.setFirst(id);
			if(remainingState.remaining != null
			&& remainingState.remaining.getType() == TYPE_CONSTRAINT) {

				// #(T.C #(T.C. dataType etc.) remaining)
				EntityAST otherEntity = (
					(EntityAST)remainingState.remaining.getFirstChild().getFirstChild());
				if(!id.isSupertypeOf(otherEntity)) {
					// Complex entity
					remainingState.remaining.getFirstChild().addChild(id.dup());
				}
			}
		}
		return remainingStateList;
	}

	public boolean oneStateAtSelect(EntityAST id, WalkerState remainingState, boolean throwException)
	throws MappingSemanticException, SdaiException {
		EntityAST selectAST;
		EntityAST selectionAST;
		if(id.isSelectOf(remainingState.getFirst(parser))) {
			selectAST = id;
			selectionAST = remainingState.getFirst(parser);
		} else if(remainingState.getFirst(parser).isSelectOf(id)) {
			selectAST = remainingState.getFirst(parser);
			selectionAST = id;
		} else {
			if(!throwException) {
				return false;
			} else {
				throw new MappingSemanticException(id, "Wrong select: " + id.getText() + " = " +
												   remainingState.getFirst(parser).getText());
			}
		}
		remainingState.setFirst(selectAST);
		if(remainingState.remaining != null) {
			AST topRemaining = getRemainingOverPathConstraint(remainingState);
			AST remainingInverse = topRemaining; // Possible inverse
			boolean doDefault = false;
			switch(topRemaining.getType()) {
			case SELECT_CONSTRAINT: {
                    if(selectionAST.isSimpleType()) {
                        AST selectDataType = topRemaining.getFirstChild();
                        selectDataType.addChild(selectionAST.dup());
                        break;
                    } else {
                        doDefault = true;
                        break;
                    }
                }

			case ENTITY_CONSTRAINT:
				AST entityConstraintChild = topRemaining.getFirstChild().getNextSibling();
				if(entityConstraintChild.getType() == INVERSE_ATTRIBUTE_CONSTRAINT_SELECT) {
					remainingInverse = entityConstraintChild;
					// Continues into INVERSE_ATTRIBUTE_CONSTRAINT_SELECT
				} else {
					doDefault = true;
					break;
				}
			case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT: {
					AST inverseChild = remainingInverse.getFirstChild();
					if(inverseChild.getType() != SELECT_CONSTRAINT) {
						if(!selectionAST.isEntityDefinition()) {
							AST newInverse = (
								(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,INVERSE_ATTRIBUTE_CONSTRAINT_SELECT)).add((AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,SELECT_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,SELECT_DATA_TYPE)).add(selectionAST.dup()))).add(inverseChild))))
							);
							if(topRemaining.getType() == INVERSE_ATTRIBUTE_CONSTRAINT_SELECT) {
								setRemainingOverPathConstraint(remainingState, newInverse);
							} else {
								topRemaining.getFirstChild().setNextSibling(newInverse);
							}
						} else {
							AST iacs = (topRemaining.getType() == INVERSE_ATTRIBUTE_CONSTRAINT_SELECT ?
								topRemaining :
								topRemaining.getFirstChild().getNextSibling());
							iacs.setType(INVERSE_ATTRIBUTE_CONSTRAINT);
						}
					} else {
						AST selectDataType = inverseChild.getFirstChild();
						if(!selectionAST.isEntityDefinition()) {
							AST selectElement = selectionAST.dup();
							selectElement.setNextSibling(selectDataType.getFirstChild());
							selectDataType.setFirstChild(selectElement);
						} else {
							AST oldInverseChild = inverseChild.getFirstChild().getNextSibling();
							AST newInverse = (
								(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,INVERSE_ATTRIBUTE_CONSTRAINT)).add(oldInverseChild))
							);
							if(topRemaining.getType() == INVERSE_ATTRIBUTE_CONSTRAINT_SELECT) {
								setRemainingOverPathConstraint(remainingState, newInverse);
							} else {
								topRemaining.getFirstChild().setNextSibling(newInverse);
							}
						}
					}
					remainingState.setFirst(selectionAST);
					break;
				}

			case TYPE_CONSTRAINT: {
					// #(T.C #(T.C. dataType etc.) remaining)
					AST lastAST = topRemaining.getFirstChild().getFirstChild();
					AST next;
					while((next = lastAST.getNextSibling()) != null) {
						lastAST = next;
					}
					int lastASTType = lastAST.getType();
					if(lastASTType == AGGREGATE_MEMBER_CONSTRAINT && selectionAST.isSimpleType()) {
						lastAST.setNextSibling(
							(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,SELECT_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,SELECT_DATA_TYPE)).add(selectionAST.dup()))))
						);
					} else if(lastASTType == SELECT_CONSTRAINT && selectionAST.isSimpleType()) {
						lastAST.getFirstChild().addChild(selectionAST.dup());
					} else {
						doDefault = true;
					}
					break;
				}

			default: {
					doDefault = true;
					break;
				}
			}
			if(doDefault) {
				if(selectionAST.isSimpleType()) { //!selectionAST.isEntityDefinition()) {
					AST selectConstraint = (
						(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,SELECT_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,SELECT_DATA_TYPE)).add(selectionAST.dup()))))
					);
					remainingState.remaining = (
						(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,PATH_CONSTRAINT)).add(selectConstraint).add(remainingState.remaining))
					);
				} else if(selectionAST.isEntityDefinition() 
				&& remainingState.remaining.getType() != TYPE_CONSTRAINT) { //???
					remainingState.remaining = (
						(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add(selectionAST.dup()))).add(remainingState.remaining))
					);
				}
			}
		} else {
			if(selectionAST.isSimpleType()) { //!selectionAST.isEntityDefinition()) {
				remainingState.remaining = (
					(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,SELECT_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,SELECT_DATA_TYPE)).add(selectionAST.dup()))))
				);
			} else if(selectionAST.isEntityDefinition()) { //???
				remainingState.remaining = (
					(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add(selectionAST.dup()))).add(remainingState.remaining))
				);
			}
		}
		return true;
	}

	public LinkedList stateAtSelect(EntityAST id, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " =");
		}
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
			oneStateAtSelect(id, remainingState, true);
		}
		return remainingStateList;
	}

	public LinkedList stateAtExtendedInto(EntityAST id, 
										  LinkedList remainingStateList,
										  WalkerOptions innerElementOpt
	) throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " *>");
		}
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
            EntityAST extendedSel = remainingState.getFirst(parser);
            if(!extendedSel.isExtensionOf(id)) {
                throw new MappingSemanticException(id, "Identifier: " + id.getText() +
                                                   " does not extend into " + extendedSel.getText());
            }
            remainingState.setFirst(id);
		}
		return remainingStateList;
    }

	public LinkedList stateAtExtensionOf(EntityAST id, 
										 LinkedList remainingStateList,
										 WalkerOptions innerElementOpt
	) throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " <*");
		}
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
            EntityAST extensibleSel = remainingState.getFirst(parser);
            if(!id.isExtensionOf(extensibleSel)) {
                throw new MappingSemanticException(id, "Identifier: " + id.getText() +
                                                   " is not an extension of " +
												   extensibleSel.getText());
            }
            remainingState.setFirst(id);
		}
		return remainingStateList;
    }

	public LinkedList stateAtAttribute(AttributeAST id, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(remainingStateList == null) {
			AttributeAST remainingAtt = (AttributeAST)id.dup();
			remainingAtt.constraintLevelInRemaining = constraintLevel;
			WalkerState remainingState = new WalkerState(remainingAtt);
			remainingState.remaining = id.dup();
			remainingState.remaining.setType(ATTRIBUTE);
			return WalkerState.createList(remainingState);
		} else {
			Iterator remainingIter = remainingStateList.iterator();
			while(remainingIter.hasNext()) {
				WalkerState remainingState = WalkerState.next(remainingIter);
				EntityAST remainingFirst = remainingState.getFirst(parser);
				if(!id.isSame(remainingFirst)) {
					throw new MappingSemanticException(id, "Illegal sequence: " + id.getText() + 
						" " + remainingState.getFirst(parser).getText());
				}
				AttributeAST remainingAtt =
					remainingFirst instanceof AttributeAST ? (AttributeAST)remainingFirst : null;
				if(!(remainingAtt != null && id.isSameAttribute(remainingAtt))
				|| (constraintLevel == 0 && remainingAtt.constraintLevelInRemaining <= constraintLevel)) {
					remainingState.setFirst(id);
					AST attributeAST = id.dup();
//DEBUG 					attributeAST.setType(ATTRIBUTE);
					attributeAST.setType(ATTRIBUTE_INVERSE_ONLY);
//DEBUG 					reportWarning(attributeAST, "Attribute was set as inverse only: " + attributeAST);
					AST typeConstraint = null;
					if(remainingState.remaining != null && 
						remainingState.remaining.getType() == TYPE_CONSTRAINT) {

						typeConstraint = dupAttachTree(remainingState.remaining.getFirstChild());
						remainingState.remaining = (remainingState.remaining
							.getFirstChild().getNextSibling());
					}
					remainingState.remaining = (remainingState.remaining != null ?
						(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,PATH_CONSTRAINT)).add(attributeAST).add(remainingState.remaining)) : 
						attributeAST);
					if(typeConstraint != null) {
						remainingState.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add(typeConstraint).add(remainingState.remaining));
					}
				} else if(constraintLevel == 0) {
					WalkerState attRemainingState = new WalkerState(id);
					attRemainingState.remaining = id.dup();
					attRemainingState.remaining.setType(ATTRIBUTE);
					((ParserAST)attRemainingState.remaining).setInPath(ParserAST.IN_PATH);
					pathDetected = true;
					remainingState.remaining = combineToAnd(attRemainingState, remainingState, false);
					remainingState.setFirst(id);
// 					reportWarning(id, "Attribute else case: " + id + " " + remainingState.remaining.toStringTree());//DEBUG
				}
			}
			return remainingStateList;
		}
	}

	public LinkedList stateAtAttributeAggreg(AttributeAST id, AST member, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		AST attributeAST = id.dup();
		attributeAST.setType(ATTRIBUTE);
		AST aggregateMemberConstraint = (
			(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,AGGREGATE_MEMBER_CONSTRAINT)).add(attributeAST).add(astFactory.dup(member)))
		);
		if(remainingStateList == null) {
			WalkerState remainingState = new WalkerState(id);
			remainingState.remaining = aggregateMemberConstraint;
			return WalkerState.createList(remainingState);
		} else {
			aggregateMemberConstraint.setType(AGGREGATE_MEMBER_INVERSE_ONLY);
			Iterator remainingIter = remainingStateList.iterator();
			while(remainingIter.hasNext()) {
				WalkerState remainingState = WalkerState.next(remainingIter);
				if(!id.isSame(remainingState.getFirst(parser))) {
					throw new MappingSemanticException(id, "Illegal sequence: " + id.getText() + 
													   " " + remainingState.getFirst(parser).getText());
				}
				remainingState.setFirst(id);
				AST typeConstraint = null;
				if(remainingState.remaining != null && 
					remainingState.remaining.getType() == TYPE_CONSTRAINT) {

					typeConstraint = dupAttachTree(remainingState.remaining.getFirstChild());
					remainingState.remaining = remainingState.remaining.getFirstChild().getNextSibling();
				}
				remainingState.remaining = (remainingState.remaining != null ? 
					(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,PATH_CONSTRAINT)).add(dupAttachTree(aggregateMemberConstraint)).add(remainingState.remaining)) :
					aggregateMemberConstraint);
				if(typeConstraint != null) {
					remainingState.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add(typeConstraint).add(remainingState.remaining));
				}
			}
			return remainingStateList;
		}
	}

	public LinkedList stateAtEqAttributeValue(
		LinkedList attributeStateList, LinkedList remainingStateList, WalkerOptions options)
	throws MappingSemanticException, SdaiException {
		if(attributeStateList.size() != 1) {
			throw new MappingSemanticException(null, "FATAL ERROR! Wrong attribute list at value");
		}
		WalkerState attributeState = (WalkerState)attributeStateList.getFirst();
		AttributeAST id = (AttributeAST)attributeState.getFirst(parser);
		EEntity realDomain = id.getRealDomain();
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);

			if(realDomain instanceof EBinary_type) {
				throw new MappingSemanticException(id, "Binary type is unsupported: " + id.getText());
			} else if(realDomain instanceof EBoolean_type) {
				if(remainingState.remaining.getType() == ENUMERATION_CONSTRAINT) {
					remainingState.remaining.setType(BOOLEAN_CONSTRAINT);
				} else {
					throw new MappingSemanticException(id, "Wrong type for " + id.getText() +
													   ": boolean expected but " + 
													   remainingState.remaining.getType() + " found");
				}
			} else if(realDomain instanceof EInteger_type) {
				if(remainingState.remaining.getType() != INTEGER_CONSTRAINT) {
					throw new MappingSemanticException(id, "Wrong type for " + id.getText() +
													   ": integer expected but " + 
													   remainingState.remaining.getType() + " found");
				}
			} else if(realDomain instanceof ELogical_type) {
				if(remainingState.remaining.getType() == ENUMERATION_CONSTRAINT) {
					remainingState.remaining.setType(LOGICAL_CONSTRAINT);
				} else {
					throw new MappingSemanticException(id, "Wrong type for " + id.getText() +
													   ": logical expected but " + 
													   remainingState.remaining.getType() + " found");
				}
			} else if(realDomain instanceof ENumber_type
			 		|| realDomain instanceof EReal_type) {
				if(remainingState.remaining.getType() == INTEGER_CONSTRAINT) {
					remainingState.remaining.setType(REAL_CONSTRAINT);
				} else if(remainingState.remaining.getType() != REAL_CONSTRAINT) {
					throw new MappingSemanticException(id, "Wrong type for " + id.getText() +
													   ": real expected but " + 
													   remainingState.remaining.getType() + " found");
				}
			} else if(realDomain instanceof EString_type) {
				if(remainingState.remaining.getType() != STRING_CONSTRAINT) {
					throw new MappingSemanticException(id, "Wrong type for " + id.getText() +
													   ": string expected but " + 
													   remainingState.remaining.getType() + " found");
				}
			} else if(realDomain instanceof EEnumeration_type) {
				if(remainingState.remaining.getType() != ENUMERATION_CONSTRAINT) {
					throw new MappingSemanticException(id, "Wrong type for " + id.getText() +
													   ": enumeration expected but " + 
													   remainingState.remaining.getType() + " found");
				}
			} else {
				throw new MappingSemanticException(id, "Illegal attribute value: " + id.getText());
			}
			remainingState.remaining.addChild(dupAttachTree(attributeState.remaining));
			remainingState.setFirst(id);
			if(options.negation) {
				remainingState.remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,NEGATION)).add(remainingState.remaining));
			}
		}
		return remainingStateList;
	}

	public LinkedList stateAtEqAttributeAttribute(
		LinkedList attributeOneList, LinkedList attributeTwoList, WalkerOptions options)
	throws MappingSemanticException, SdaiException {
		if(attributeOneList.size() != 1 || attributeTwoList.size() != 1) {
			throw new MappingSemanticException(null, "FATAL ERROR! Wrong attribute list at attribute");
		}
		WalkerState attributeOne = (WalkerState)attributeOneList.getFirst();
		WalkerState attributeTwo = (WalkerState)attributeTwoList.getFirst();
		if(!attributeOne.getFirst(parser).isSame(attributeTwo.getFirst(parser))
		&& !attributeOne.getFirst(parser).isSupertypeOf(attributeTwo.getFirst(parser))
		&& !attributeTwo.getFirst(parser).isSupertypeOf(attributeOne.getFirst(parser))) {
			throw new MappingSemanticException(attributeOne.getFirst(parser),
											   "Attributes can not be of different entities: " + 
											   attributeOne.getFirst(parser).getText() + " and " + 
											   attributeTwo.getFirst(parser).getText());
		}
		AST element1 = attributeOne.remaining;
		AST element2 = attributeTwo.remaining;
		attributeOne.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(element1,INSTANCE_EQUAL)).add(element1).add(element2));
		if(options.negation) {
			attributeOne.remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(element1,NEGATION)).add(attributeOne.remaining));
		}
		return attributeOneList;
	}

	private void makePathTo(EntityAST attribute, WalkerState remainingState)
	throws MappingSemanticException, SdaiException {

		boolean illegalDomain = true;
		List pathList = attribute.getPathTo(remainingState.getFirst(parser));
		if(pathList != null) {
			Iterator pathListIter = pathList.iterator();
			illegalDomain = false;
			while(pathListIter.hasNext()) {
				EEntity domainElement = (EEntity)pathListIter.next();
				EntityAST domainAST = attribute.dup();
				if(domainElement instanceof EEntity_definition) {
					domainAST.setIdentifier(parser, 
						((EEntity_definition)domainElement).getName(null), true);
					if(!oneStateAtSuper(domainAST, remainingState, false)) {
						illegalDomain = true;
						break;
					}
				} else if(domainElement instanceof EDefined_type) {
					domainAST.setIdentifier(parser, 
						((EDefined_type)domainElement).getName(null), true);
					if(!oneStateAtSelect(domainAST, remainingState, false)) {
						illegalDomain = true;
						break;
					}
				} else {
					illegalDomain = true;
					break;
				}
			}
		}
		if(illegalDomain) {
			throw new MappingSemanticException(attribute, "Illegal type: " + 
											   remainingState.getFirst(parser).getText() +
											   " for " + attribute.getText());
		}
	}

	public LinkedList stateAtToPoint(LinkedList attributeStateList, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(attributeStateList.size() != 1) {
			throw new MappingSemanticException(null, "FATAL ERROR! Wrong attribute list at ->");
		}
		WalkerState attributeState = (WalkerState)attributeStateList.getFirst();

		if(remainingStateList == null) {
			throw new MappingSemanticException(attributeState.getFirst(parser), "Incomplete element: " +
											   attributeState.getFirst(parser).getText() + " ->");
		}
		remainingStateList = postProcessState(remainingStateList, true);
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);

			EntityAST attribute = attributeState.getFirst(parser);
			makePathTo(attribute, remainingState);
			AST attributeRemaining = dupAttachTree(attributeState.remaining);
			if(remainingState.remaining != null
			&& remainingState.remaining.getType() == TYPE_CONSTRAINT) {
				// #(T.C #(T.C. dataType etc.) remaining)
				AST lastAST = remainingState.remaining.getFirstChild().getFirstChild();
				LinkedList backwardList = new LinkedList();
				while(lastAST != null) {
					backwardList.addFirst(lastAST);
					lastAST = lastAST.getNextSibling();
				}
				Iterator backwardIter = backwardList.iterator();
				boolean clearSiblings = false;
				AST previousAST = null;
				while(backwardIter.hasNext()) {
					AST ast = (AST)backwardIter.next();
					if(clearSiblings) {
						ast.setNextSibling(null);
						clearSiblings = false;
					}
					int astType = ast.getType();
					if(astType == SELECT_CONSTRAINT
					|| astType == AGGREGATE_MEMBER_CONSTRAINT) {
						AST entityConstraints = ast.getNextSibling();
						if(entityConstraints != null) {
							AST typeConstraintBody = auxiliaryAST(attribute, TYPE_CONSTRAINT);
							typeConstraintBody.setFirstChild(dupAttachTree(entityConstraints));
							attributeRemaining = (
								(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(attribute,ENTITY_CONSTRAINT)).add(typeConstraintBody).add(attributeRemaining))
							);
						}
						clearSiblings = true;
						if(astType == SELECT_CONSTRAINT) {
							AST selectConstraint = dupAttachTree(ast);
							selectConstraint.getFirstChild().setNextSibling(attributeRemaining);
							attributeRemaining = selectConstraint;
						} else {
							AST index = ast.getFirstChild();
							attributeRemaining = (
								(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(attribute,AGGREGATE_MEMBER_CONSTRAINT)).add(attributeRemaining).add(index))
							);
						}
					} else if(!backwardIter.hasNext()) {
						attributeRemaining = (
							(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(attribute,ENTITY_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(attribute,TYPE_CONSTRAINT)).add(dupWholeTree(ast)))).add(attributeRemaining))
						);
					}
				}
				remainingState.remaining = remainingState.remaining.getFirstChild().getNextSibling();
			}
			if(remainingState.remaining != null) {
				AST topRemaining = getRemainingOverPathConstraint(remainingState);
				if(topRemaining.getType() == SELECT_CONSTRAINT && 
					topRemaining.getFirstChild().getNextSibling() == null) {

// 					reportWarning(attributeState.getFirst(parser), 
// 						"!!!!!! SELECT_CONSTRAINT was used!!!!!!");
					topRemaining.getFirstChild().setNextSibling(attributeRemaining);
					if(remainingState.remaining.getType() == PATH_CONSTRAINT) {
						AST pathElement2 = remainingState.remaining.getFirstChild().getNextSibling();
						if(pathElement2.getType() == EQ_ENTITY) {
							AST attributeValueConstraint = getRemainingOverPathConstraint(
								pathElement2.getFirstChild());
							attributeValueConstraint.getFirstChild().setNextSibling(
								dupAttachTree(topRemaining));
							remainingState.remaining = pathElement2.getFirstChild();
						}
					}
				} else if(remainingState.remaining.getType() == EQ_ENTITY) {
					AST attributeValueConstraintPath = remainingState.remaining.getFirstChild();
					AST attributeValueConstraint = getRemainingOverPathConstraint(
						attributeValueConstraintPath);
					attributeValueConstraint.getFirstChild().setNextSibling(attributeRemaining);
					remainingState.remaining = attributeValueConstraintPath;
				} else {
					remainingState.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(attribute,PATH_CONSTRAINT)).add(attributeRemaining).add(remainingState.remaining));
				}
			} else {
				remainingState.remaining = attributeRemaining;
			}
			remainingState.setFirst(attributeState.getFirst(parser));
		}
		return remainingStateList;
	}

	public LinkedList stateAtEntityToPoint(EntityAST id, 
		LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " ->");
		}
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(id.isEntityDefinition()) {
			throw new MappingSemanticException(id, "Defined type expected here");
		}
		remainingStateList = postProcessState(remainingStateList, true);
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
			makePathTo(id, remainingState);
			remainingState.setFirst(id);
		}
		return remainingStateList;
	}

	public LinkedList stateAtEntityAggregToPoint(EntityAST id, 
		AST index, LinkedList remainingStateList, WalkerOptions innerElementOpt)
	throws MappingSemanticException, SdaiException {
		if(remainingStateList == null) {
			throw new MappingSemanticException(id, "Incomplete element: " + id.getText() + " ->");
		}
		cleanInnerElement(remainingStateList, innerElementOpt);
		if(id.isEntityDefinition()) {
			throw new MappingSemanticException(id, "Defined type expected here");
		}
		remainingStateList = postProcessState(remainingStateList, true);
		Iterator remainingIter = remainingStateList.iterator();
		while(remainingIter.hasNext()) {
			WalkerState remainingState = WalkerState.next(remainingIter);
			makePathTo(id, remainingState);
			AST aggregateMemberConstraint = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,AGGREGATE_MEMBER_CONSTRAINT)).add(astFactory.dup(index)));
			if(remainingState.remaining != null
			&& remainingState.remaining.getType() == TYPE_CONSTRAINT) {
				// #(T.C #(T.C. dataType etc.) remaining)
				remainingState.remaining.getFirstChild().addChild(aggregateMemberConstraint);
			} else {
				remainingState.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(id,TYPE_CONSTRAINT)).add(aggregateMemberConstraint))).add(remainingState.remaining));
			}
			remainingState.setFirst(id);
		}
		return remainingStateList;
	}

	private AST combineToAnd(final WalkerState remainingState1, 
		final WalkerState remainingState2, boolean checkConstraintLevel)
	throws MappingSemanticException, SdaiException {
		if(remainingState1.remaining == null) {
			return dupWholeTree(remainingState2.remaining);
		}
		if(remainingState2.remaining == null) {
			return dupWholeTree(remainingState1.remaining);
		}
		AST remaining1 = remainingState1.remaining;
		AST remaining2 = remainingState2.remaining;
		if(remaining1.getType() == TYPE_CONSTRAINT && remaining2.getType() == TYPE_CONSTRAINT) {
			AST anotherType = remaining2.getFirstChild();
			remaining2 = anotherType.getNextSibling();
			// Children are made as siblings
			anotherType = dupWholeTree(anotherType.getFirstChild());
			remaining1 = dupWholeTree(remaining1);
			remaining1.getFirstChild().addChild(anotherType);
			if(remaining2 == null) return remaining1;
		}
		AST typeConstraintAST = null;
		AST otherAST = null;
		boolean switchedAnds = false;
		if(remaining1.getType() == TYPE_CONSTRAINT) {
			typeConstraintAST = dupWholeTree(remaining1);
			otherAST = dupWholeTree(remaining2);
			switchedAnds = true;
		} else if(remaining2.getType() == TYPE_CONSTRAINT) {
			typeConstraintAST = dupWholeTree(remaining2);
			otherAST = dupWholeTree(remaining1);
		}
		if(typeConstraintAST != null) {
			AST typeConstraintChild = typeConstraintAST.getFirstChild();
			AST typeConstraintRemaining = typeConstraintChild.getNextSibling();
			if(checkConstraintLevel && constraintLevel == 0 && typeConstraintRemaining != null) {
				throw new MappingSemanticException(remainingState1.getFirst(parser), 
												   "AND is not supported here (note 1)");
			}
			if(typeConstraintRemaining == null) {
				typeConstraintChild.setNextSibling(otherAST);
			} else {
				ParserAST childAndConstraint = (ParserAST)(
					(switchedAnds ? 
						(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(otherAST,AND_CONSTRAINT)).add(typeConstraintRemaining).add(otherAST)) :
						(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(otherAST,AND_CONSTRAINT)).add(otherAST).add(typeConstraintRemaining)))
				);
				childAndConstraint.setInPath(((ParserAST)childAndConstraint.getFirstChild()).getInPath());
				typeConstraintChild.setNextSibling(childAndConstraint);
			}
			return typeConstraintAST;
		} else {
			if(checkConstraintLevel && constraintLevel == 0) {
				throw new MappingSemanticException(remainingState1.getFirst(parser), 
												   "AND is not supported here (note 2)");
			}
			ParserAST andConstraint = (ParserAST)(
				(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(remaining1,AND_CONSTRAINT)).add(dupWholeTree(remaining1)).add(dupWholeTree(remaining2)))
			);
			andConstraint.setInPath(((ParserAST)andConstraint.getFirstChild()).getInPath());
			return andConstraint;
		}
	}

	public LinkedList stateAtConstraint(LinkedList constraintStateList, 
		LinkedList remainingStateList, WalkerOptions options)
	throws MappingSemanticException, SdaiException {
		if(remainingStateList == null) {
			if(options.negation) {
				Iterator constraintIter = constraintStateList.iterator();
				while(constraintIter.hasNext()) {
					WalkerState constraintState = WalkerState.next(constraintIter);
					EntityAST constraintStateFirst = constraintState.getFirst(parser);
					if(constraintState.remaining == null) {
						throw new MappingSemanticException(constraintStateFirst, "Negation is " +
														   "illegal here, there is nothing to negate");
					} else {
						constraintState.remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(constraintStateFirst,NEGATION)).add(constraintState.remaining));
						((ParserAST)constraintState.remaining).setInPath(ParserAST.NOT_IN_PATH);
					}
				}
			}
			return constraintStateList;
		}
		if(options.partialConstraints
		&& !((WalkerState)constraintStateList.getFirst()).getFirst(parser)
		.isSame(((WalkerState)remainingStateList.getFirst()).getFirst(parser))) {
			options.constraintStateList = constraintStateList;
			return remainingStateList;
		}
		LinkedList newRemainingList = new LinkedList();
		Iterator constraintIter = constraintStateList.iterator();
		while(constraintIter.hasNext()) {
			WalkerState constraintState = WalkerState.next(constraintIter);
			EntityAST constraintStateFirst = constraintState.getFirst(parser);
			if(options.negation) {
				if(constraintState.remaining == null) {
					throw new MappingSemanticException(constraintStateFirst, "Negation is " +
													   "illegal here, there is nothing to negate");
				} else {
					constraintState.remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(constraintStateFirst,NEGATION)).add(constraintState.remaining));
					((ParserAST)constraintState.remaining).setInPath(ParserAST.NOT_IN_PATH);
				}
			}
			Iterator remainingIter = remainingStateList.iterator();
			while(remainingIter.hasNext()) {
				WalkerState remainingState = WalkerState.next(remainingIter);
				EntityAST remainingStateFirst = remainingState.getFirst(parser);
				boolean constraintType = false;
				if(!constraintStateFirst.isSame(remainingStateFirst)
				&& !constraintStateFirst.isSupertypeOf(remainingStateFirst)) {
					if(remainingStateFirst.isSupertypeOf(constraintStateFirst)) {
						constraintType = true;
					} else {
						throw new MappingSemanticException(constraintStateFirst, 
														   "Constraint starts with " +
														   constraintStateFirst.getText() + 
														   " but type following constraint is " + 
														   remainingState.getFirst(parser).getText());
					}
				}
				WalkerState newRemainingState = remainingState.dup();
				newRemainingState.synchronizeWith(constraintState);
				newRemainingState.remaining = combineToAnd(remainingState, constraintState, false);
				if(constraintType) {
					newRemainingState.remaining = (
						(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(constraintStateFirst,TYPE_CONSTRAINT)).add((AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(constraintStateFirst,TYPE_CONSTRAINT)).add(constraintStateFirst.dup()))).add(newRemainingState.remaining))
					);
				}
				newRemainingList.add(newRemainingState);
			}
		}
		return newRemainingList;
	}

	public LinkedList stateAtAnd(LinkedList remainingStateList1, LinkedList remainingStateList2)
	throws MappingSemanticException, SdaiException {
		LinkedList newRemainingList = new LinkedList();
		Iterator remainingIter1 = remainingStateList1.iterator();
		while(remainingIter1.hasNext()) {
			WalkerState remainingState1 = WalkerState.next(remainingIter1);
			Iterator remainingIter2 = remainingStateList2.iterator();
			while(remainingIter2.hasNext()) {
				WalkerState remainingState2 = WalkerState.next(remainingIter2);
				WalkerState newRemainingState = remainingState2.dup();
				newRemainingState.synchronizeWith(remainingState1);
				LinkedList complexIds;
				LinkedList firstList;
				if(remainingState2.getFirstList() == null) {
					newRemainingState.addFirstToList(remainingState2.getFirst(parser));
				}
				if(remainingState1.getFirstList() == null) {
					newRemainingState.addFirstToList(remainingState1.getFirst(parser));
				} else {
					newRemainingState.addFirstToList(remainingState1.getFirstList());
				}
				newRemainingState.remaining = combineToAnd(remainingState1, remainingState2, true);
				newRemainingList.add(newRemainingState);
			}
		}
		return newRemainingList;
	}

	public void scanAndEnds(AndConstructAST andConstructAst)
	throws RecognitionException, MappingSemanticException, SdaiException {
		andConstructAst.subpaths = null;
		AST remainingAst;
		if(andConstructAst.andLastList != null 
		&& (remainingAst = andConstructAst.andEnd.getFirstChild()) != null) {
			LinkedList remainingStateList = pathInnerElement(remainingAst, new WalkerOptions());
			if(remainingStateList != null && remainingStateList.size() >= 1) {
				EntityAST remainingFirst = null;
				Iterator remainingStateIter = remainingStateList.iterator();
				while(remainingStateIter.hasNext()) {
					WalkerState state = WalkerState.next(remainingStateIter);
					EntityAST stateFirst = state.getFirst(parser);
					if(remainingFirst == null) {
						remainingFirst = stateFirst;
					} else if(remainingFirst != stateFirst) {
						throw new MappingSemanticException(remainingFirst,
														   "Alternatives with different " +
														   "start types can not be used in " + 
														   "this way after AND constraint");
					}
				}
				Iterator andLastIter = andConstructAst.andLastList.iterator();
				boolean intersection = true;
				boolean valid = false;
				int andLastCount = 0;
				while(andLastIter.hasNext()) {
					AST andLast = (AST)andLastIter.next();
//DEBUG 					reportWarning(andConstructAst, "andLast is " + andLast.getText());
					AST andLastChild = andLast.getFirstChild();
					AST nextSibling;
					while((nextSibling = andLastChild.getNextSibling()) != null) {
						andLastChild = nextSibling;
					}
					((AndEndAST)andLastChild).unused = false;
					if(andLast.getType() == ENTITY) {
						if(!((EntityAST)andLast.getFirstChild()).isSame(remainingFirst)) {
							intersection = false;
							((AndEndAST)andLastChild).unused = true;
//DEBUG 							reportWarning(andConstructAst, andLastChild.getText() + " was unused") ;
							continue;
						}
					}
					valid = true;
					andLastCount++;
				}
				if(!valid) {
					throw new MappingSemanticException(remainingFirst, 
						"Path following AND can not be attached");
				}
				if(andLastCount <= 1) intersection = false;
				if(intersection) andConstructAst.subpaths = new LinkedList();
				andConstructAst.remainingStateList = remainingStateList;
			}
		}
	}

	private boolean collectSubpaths(AST andConstructAst, LinkedList andStateList, 
		WalkerState headState, LinkedList subpaths[], int level)
	throws MappingSemanticException, SdaiException {
		if(level == subpaths.length) return false;
//DEBUG 		reportWarning(andConstructAst, "subpaths[level].size() " + subpaths[level].size());
		boolean added = false;
		Iterator oneStateIter = subpaths[level].iterator();
		while(oneStateIter.hasNext()) {
			WalkerState state = WalkerState.next(oneStateIter);
			WalkerState currentState = headState.dup();
// 			reportWarning(andConstructAst, "New current state");
			if(currentState.remaining != null) {
				currentState.remaining = dupWholeTree(currentState.remaining);
				if(state.remaining != null) {
					currentState.remaining.addChild(dupAttachTree(state.remaining));
				}
			} else if(state.remaining != null) {
				currentState.remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(andConstructAst,INTERSECTION_CONSTRAINT)).add(state.remaining));
			}
			LinkedList stateFirstList = state.getFirstList();
			if(stateFirstList != null) {
				currentState.addFirstToList(stateFirstList);
			} else {
				currentState.addFirstToList(state.getFirst(parser));
			}
			if(!collectSubpaths(andConstructAst, andStateList, currentState, subpaths, level + 1)) {
				if(currentState.remaining != null) {
					AST remainingStart = null;
					boolean commonStart = true;
					for(AST remaining = currentState.remaining.getFirstChild(); remaining != null; 
						remaining = remaining.getNextSibling()) {

						if(remaining.getType() != PATH_CONSTRAINT) {
							commonStart = false;
							break;
						}
						if(remainingStart == null) {
							remainingStart = remaining.getFirstChild();
						} else {
							if(!WalkerState.equalsAST(remainingStart,
									remaining.getFirstChild())) {
								commonStart = false;
								break;
							}
						}
					}
					if(commonStart) {
//DEBUG 						System.out.println("commonStart = true");
						AST oldRemaining = dupAttachTree(currentState.remaining);
						AST remainingPart = dupAttachTree(oldRemaining.getFirstChild().getFirstChild());
						remainingPart.setNextSibling(null);
						currentState.remaining.setFirstChild(null);
						for(AST remaining = oldRemaining.getFirstChild(); remaining != null; 
							remaining = remaining.getNextSibling()) {
							currentState.remaining.addChild(
								dupAttachTree(remaining.getFirstChild().getNextSibling()));
						}
						ensureIntersectPath(currentState.remaining);
						((ParserAST)currentState.remaining).setInPath(constraintLevel == 0 ? 
							ParserAST.IN_PATH : ParserAST.NOT_IN_PATH);
						currentState.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(andConstructAst,PATH_CONSTRAINT)).add(remainingPart).add(currentState.remaining));
					} else {
						ensureIntersectPath(currentState.remaining);
					}
				}
				andStateList.add(currentState);
			}
			added = true;
		}
		return added ? true : collectSubpaths(andConstructAst, andStateList, 
			headState, subpaths, level + 1);
	}

	public LinkedList stateAtIntersect(AndConstructAST andConstructAst, LinkedList andStateList)
	throws MappingSemanticException, SdaiException {
		if(andConstructAst.subpaths != null) {
			LinkedList subpaths[] = (LinkedList[])andConstructAst.subpaths.toArray(
				new LinkedList[andConstructAst.subpaths.size()]);
			andStateList.clear();
			if(!collectSubpaths(andConstructAst, andStateList, new WalkerState(), subpaths, 0)) {
				return andConstructAst.remainingStateList;
			}
			andStateList = postProcessState(andStateList, true);
			andConstructAst.remainingStateList = (
				postProcessState(andConstructAst.remainingStateList, true));
			LinkedList andFinalStateList = new LinkedList();
			Iterator andStateIter = andStateList.iterator();
			while(andStateIter.hasNext()) {
				WalkerState andState = WalkerState.next(andStateIter);
				if(andState.remaining != null) {
					boolean straightforwardAdd = false;
					Iterator remainingStateIter = andConstructAst.remainingStateList.iterator();
					while(remainingStateIter.hasNext()) {
						WalkerState remainingState = WalkerState.next(remainingStateIter);
						WalkerState andFinalState = andState.dup();
						if(remainingState.remaining != null) {
							if(andState.remaining.getType() == PATH_CONSTRAINT) {
//DEBUG								System.out.println("andState.remaining.getType() == PATH_CONSTRAINT");
								AST commonStartAst = andState.remaining.getFirstChild();
								andFinalState.remaining = (
									(AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(andConstructAst,PATH_CONSTRAINT)).add(dupAttachTree(commonStartAst)).add((AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(andConstructAst,PATH_CONSTRAINT)).add(dupAttachTree(commonStartAst.getNextSibling())).add(remainingState.remaining))))
								);
							} else {
								andFinalState.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(andConstructAst,PATH_CONSTRAINT)).add(dupAttachTree(andState.remaining)).add(remainingState.remaining));
							}
						} else {
							if(!straightforwardAdd) {
								andFinalState.remaining = dupAttachTree(andState.remaining);
								straightforwardAdd = true;
							} else {
								continue;
							}
						}
//DEBUG 						reportWarning(andConstructAst, "INTERSECTION found (case 1)");
						andFinalStateList.add(andFinalState);
					}
				} else {
					Iterator remainingStateIter = andConstructAst.remainingStateList.iterator();
					while(remainingStateIter.hasNext()) {
						WalkerState remainingState = WalkerState.next(remainingStateIter);
						WalkerState andFinalState = andState.dup();
						andFinalState.remaining = remainingState.remaining;
						LinkedList stateFirstList = remainingState.getFirstList();
						if(stateFirstList != null) {
							andFinalState.addFirstToList(stateFirstList);
						} else {
							andFinalState.addFirstToList(remainingState.getFirst(parser));
						}
						andFinalStateList.add(andFinalState);
//DEBUG 						reportWarning(andConstructAst, "INTERSECTION found (case 2)");
					}
				}
			}
			if(andFinalStateList.size() == 0) {
				throw new MappingSemanticException(andConstructAst,
												   "Empty path is invalid in detected intersection");
			}
			return andFinalStateList;
		} else {
			return andStateList;
		}
	}

	public LinkedList stateAtOr(LinkedList remainingStateList1, LinkedList remainingStateList2)
	throws MappingSemanticException {
		if(remainingStateList1 == null) {
			return remainingStateList2;
		} else if(remainingStateList2 == null) {
			return remainingStateList1;
		} else {
			remainingStateList1.addAll(remainingStateList2);
//DEBUG  			reportWarning(remainingState1.getFirst(parser), "!!!!!! OR !!!!!!");
			return remainingStateList1;
		}
	}

	public LinkedList stateAtOrExtended(LinkedList states, LinkedList extStates) throws MappingSemanticException, SdaiException {
		if(states == null) {
			return extStates;
		} else if(extStates == null) {
			return states;
		} else {
            for(Iterator extStIter = extStates.iterator(); extStIter.hasNext(); ) {
                WalkerState extState = WalkerState.next(extStIter);
                EntityAST extStateFirst = extState.getFirst(parser);
                for(Iterator stIter = states.iterator(); stIter.hasNext(); ) {
                    WalkerState state = WalkerState.next(stIter);
                    if(extStateFirst.isSame(state.getFirst(parser))) {
                        if(state.remaining == null) {
                            // Remove generic mapping from base type because of specialization in the extended mapping
                            stIter.remove(); 
                        }
                    }
                }
            }
            states.addAll(extStates);
            return states;
        }
	}

	private int getCompletionState(AST element, boolean extendedElement) {
		AST checkElement = getRemainingOverPathConstraint(element);
		if(checkElement != null) {
			switch(checkElement.getType()) {
			case AGGREGATE_MEMBER_CONSTRAINT: 
			case AND_CONSTRAINT:
			case ATTRIBUTE: 
			case ENTITY_CONSTRAINT:
			case INTERSECTION_CONSTRAINT:
			case INVERSE_ATTRIBUTE_CONSTRAINT:
			case INVERSE_ATTRIBUTE_CONSTRAINT_SELECT:
				return COMPLETION_FINISHED_ALL;


			case SELECT_CONSTRAINT:
				return (checkElement.getFirstChild().getNextSibling() != null ? 
					COMPLETION_FINISHED_ALL : COMPLETION_NOT_FINISHED);

			case TYPE_CONSTRAINT:
				return extendedElement ? COMPLETION_FINISHED_ALL : COMPLETION_NOT_FINISHED;

			case INSTANCE_EQUAL: 
			case NEGATION:
			case OR_CONSTRAINT:
				return COMPLETION_FINISHED_CONSTRAINT;

			case BOOLEAN_CONSTRAINT:
			case ENUMERATION_CONSTRAINT:
			case INTEGER_CONSTRAINT:
			case LOGICAL_CONSTRAINT:
			case REAL_CONSTRAINT:
			case STRING_CONSTRAINT:
				return (checkElement.getFirstChild().getNextSibling() != null ? 
					COMPLETION_FINISHED_CONSTRAINT : COMPLETION_NOT_FINISHED);
			}
		} else {
            return extendedElement ? COMPLETION_FINISHED_ALL : COMPLETION_NOT_FINISHED;
        }
		return COMPLETION_NOT_FINISHED;
	}

	private int getCompletionState(WalkerState state, boolean extendedElement) {
		if(state.mappedValue != null) {
			return COMPLETION_NOT_FINISHED;
		} else {
			return getCompletionState(state.remaining, extendedElement);
		}
	}

	public LinkedList postProcessState(LinkedList remainingStateList, boolean extendedElement)
	throws MappingSemanticException, SdaiException {
		LinkedList newStateList = new LinkedList();
		while(remainingStateList.size() != 0) {
			WalkerState state = (WalkerState)remainingStateList.removeFirst();
			newStateList.add(state);
			int completionState = getCompletionState(state, extendedElement);
			if(completionState != COMPLETION_NOT_FINISHED) {
				if(constraintLevel == 0) {
					ParserAST path = (ParserAST)state.remaining;
                    int pathType;
					if(path != null && (pathType = path.getType()) != AND_CONSTRAINT
                       && path.getInPath() == ParserAST.UNDETERMINED) {
						if(completionState == COMPLETION_FINISHED_CONSTRAINT) {
							int remainingType = getRemainingOverPathConstraint(state).getType();
							throw new MappingSemanticException(state.remaining, "Illegal type " +
															   "of constraint in the path: " +
															   _tokenNames[remainingType]);
						}
						path.setInPath(ParserAST.IN_PATH);
						pathDetected = true;
						if(pathType == PATH_CONSTRAINT || pathType == TYPE_CONSTRAINT) {
							ParserAST element2 = (ParserAST)path.getFirstChild().getNextSibling();
							if(element2 != null && element2.getInPath() != ParserAST.IN_PATH) {
								ParserAST endOfPathConstraint = (ParserAST)(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(element2,END_OF_PATH_CONSTRAINT)).add(element2));
								endOfPathConstraint.setInPath(ParserAST.IN_PATH);
								path.getFirstChild().setNextSibling(endOfPathConstraint);
							}
						}
					}
				} else {
					ListIterator remainingIter = remainingStateList.listIterator();
					while(remainingIter.hasNext()) {
						WalkerState remainingState = WalkerState.next(remainingIter);
                        EntityAST stateFirst, remainingStateFirst;
						if(state.getFirstList() == null && remainingState.getFirstList() == null &&
							(stateFirst = state.getFirst(parser)).isSame(remainingStateFirst = remainingState.getFirst(parser))) {

							if(getCompletionState(remainingState, extendedElement) != 
								COMPLETION_NOT_FINISHED) {

                                if(state.remaining == null) {
                                    addTypeConstraint(stateFirst, state, stateFirst);
                                    if(remainingState.remaining != null) {
                                        reportWarning(stateFirst,
                                                      "Warning: The alternative takes precedence over other alternatives");
                                    }
                                }
                                if(remainingState.remaining == null) {
                                    addTypeConstraint(remainingStateFirst, remainingState, remainingStateFirst);
                                    if(state.remaining != null) {
                                        reportWarning(remainingStateFirst,
                                                      "Warning: The alternative takes precedence over other alternatives");
                                    }
                                }
								remainingIter.remove();
								state.remaining = (AST)astFactory.make( (new ASTArray(3)).add(auxiliaryAST(remainingState.remaining,OR_CONSTRAINT)).add(remainingState.remaining).add(state.remaining));
								((ParserAST)state.remaining).setInPath(ParserAST.NOT_IN_PATH);
							}
						}
					}
                    if(state.remaining != null) {
                        ((ParserAST)state.remaining).setInPath(ParserAST.NOT_IN_PATH);
                    }
				}
			}
		}
		return newStateList;
	}

	private void ensureIntersectPath(AST intersection) throws MappingSemanticException, SdaiException {
		AST prevSubpath = null;
		for(ParserAST subpath = (ParserAST)intersection.getFirstChild(); subpath != null; 
			subpath = (ParserAST)subpath.getNextSibling()) {

			int completionState = getCompletionState(subpath, true);
			if(completionState != COMPLETION_NOT_FINISHED 
			&& subpath.getInPath() == ParserAST.UNDETERMINED) {
				if(completionState == COMPLETION_FINISHED_CONSTRAINT) {
					throw new MappingSemanticException(subpath, 
						"Illegal type of constraint in intersection path: " +
						_tokenNames[getRemainingOverPathConstraint(subpath).getType()]);
				}
				subpath.setInPath(ParserAST.IN_PATH);
				int subpathType = subpath.getType();
				if(subpathType == PATH_CONSTRAINT || subpathType == TYPE_CONSTRAINT) {
					ParserAST element2 = (ParserAST)subpath.getFirstChild().getNextSibling();
					if(element2 != null && element2.getInPath() != ParserAST.IN_PATH) {
						ParserAST endOfPathConstraint = (ParserAST)(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(element2,END_OF_PATH_CONSTRAINT)).add(element2));
						endOfPathConstraint.setInPath(ParserAST.IN_PATH);
						subpath.getFirstChild().setNextSibling(endOfPathConstraint);
					}
				}
			}
			if(subpath.getInPath() != ParserAST.IN_PATH) {
				ParserAST endOfPathConstraint = (ParserAST)(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(subpath,END_OF_PATH_CONSTRAINT)).add(dupAttachTree(subpath)));
				endOfPathConstraint.setInPath(ParserAST.IN_PATH);
				if(prevSubpath == null) {
					intersection.setFirstChild(endOfPathConstraint);
				} else {
					prevSubpath.setNextSibling(endOfPathConstraint);
				}
				endOfPathConstraint.setNextSibling(subpath.getNextSibling());
			}
			prevSubpath = subpath;
		}
	}

	private void ensurePathExists(LinkedList stateList) 
	throws MappingSemanticException, SdaiException {
		Iterator stateIter = stateList.iterator();
		while(stateIter.hasNext()) {
			WalkerState state = WalkerState.next(stateIter);
			if(state.remaining != null && ((ParserAST)state.remaining).getInPath() != ParserAST.IN_PATH) {
				ParserAST endOfPathConstraint = (ParserAST)(AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(state.remaining,END_OF_PATH_CONSTRAINT)).add(state.remaining));
				endOfPathConstraint.setInPath(ParserAST.IN_PATH);
				state.remaining = endOfPathConstraint;
			}
		}
	}

	public LinkedList combineStatesAndAimList(LinkedList aimList, LinkedList stateList)
	throws MappingSemanticException, SdaiException {
		if(!pathDetected && !(mappedValueSet != null && mappedValueReallyUsed)) {
			LinkedList newStateList = null;
			Iterator aimListIter = aimList.iterator();
			while(aimListIter.hasNext()) {
				Object aimAttrObject = aimListIter.next();
				if(aimAttrObject instanceof AttributeAST) {
					AttributeAST aimAttr = (AttributeAST)aimAttrObject;
					if(newStateList == null) {
						newStateList = new LinkedList();
					}
					boolean attrWasAttached = false;
					Iterator stateIter = stateList.iterator();
					while(stateIter.hasNext()) {
						WalkerState state = WalkerState.next(stateIter);
						if(state.getFirst(parser).isSame(aimAttr)) {
							WalkerState newState = state.dup();
							WalkerState attState = new WalkerState(aimAttr);
							attState.remaining = aimAttr.dup();
							attState.remaining.setType(ATTRIBUTE);
							((ParserAST)attState.remaining).setInPath(ParserAST.IN_PATH);
							newState.remaining = combineToAnd(attState, state, false);
							newState.setFirst(aimAttr);
							newStateList.add(newState);
							attrWasAttached = true;
						}
					}
					if(!attrWasAttached) {
						WalkerState attState = new WalkerState(aimAttr);
						attState.remaining = aimAttr.dup();
						attState.remaining.setType(ATTRIBUTE);
						((ParserAST)attState.remaining).setInPath(ParserAST.IN_PATH);
						newStateList.add(attState);
					}
				}
			}
			if(newStateList != null) {
				return newStateList;
			}
		}
		if(!(mappedValueSet != null && mappedValueReallyUsed)) {
			ensurePathExists(stateList);
		}
		return stateList;
	}
public MappingPathWalker() {
	tokenNames = _tokenNames;
}

	public final void entityMapping(AST _t) throws RecognitionException, MappingSemanticException,SdaiException {
		
		AST entityMapping_AST_in = (AST)_t;
		AST mapping = null;
		AST arm = null;
		AST aimAst = null;
		AST t = null;
		AST o = null;
		AST aim = null;
		AST p = null;
			LinkedList states;
			Map extAttrMappings = null;
			EntityMappingAST entityMappingAst = null;
			SourceLocatorMark mark = null;
		
		
		try {      // for error handling
			AST __t2 = _t;
			mapping = _t==ASTNULL ? null :(AST)_t;
			match(_t,ENTITY_MAPPING);
			_t = _t.getFirstChild();
			if ( inputState.guessing==0 ) {
				errorOccured = false;
								dontReport = false;
								notReported = null;
								entityMappingAst = (EntityMappingAST)mapping;
								sourceLocator = entityMappingAst;
								line = -1;
							
			}
			AST __t3 = _t;
			AST tmp1_AST_in = (AST)_t;
			match(_t,ARM);
			_t = _t.getFirstChild();
			arm = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					if(((EntityAST)arm).declaration.type != DictionaryDeclaration.ENTITY) {
										reportError(aim, "Entity mapping ARM type is not an entity");
									}
									armEntity = (EntityAST)arm;
									mappedValueSet = null;
									mappedValueUsed = false;
								
			}
			_t = __t3;
			_t = _t.getNextSibling();
			AST __t4 = _t;
			aimAst = _t==ASTNULL ? null :(AST)_t;
			match(_t,AIM);
			_t = _t.getFirstChild();
			{
			int _cnt8=0;
			_loop8:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==TEMPLATE_CALL||_t.getType()==ORIGINAL_LOCATION||_t.getType()==ID)) {
					{
					_loop7:
					do {
						if (_t==null) _t=ASTNULL;
						switch ( _t.getType()) {
						case TEMPLATE_CALL:
						{
							t = (AST)_t;
							match(_t,TEMPLATE_CALL);
							_t = _t.getNextSibling();
							if ( inputState.guessing==0 ) {
								((TemplateCallAST)t).resolve(aimAst, parser, sourceLocator);
									_t = t.getNextSibling();
														
							}
							break;
						}
						case ORIGINAL_LOCATION:
						{
							o = (AST)_t;
							match(_t,ORIGINAL_LOCATION);
							_t = _t.getNextSibling();
							if ( inputState.guessing==0 ) {
									if(mark == null) {
																mark = new SourceLocatorMark();
																pushLocatorMark(mark);
															}
															pushLocator((SourceLocator)o);
														
							}
							break;
						}
						default:
						{
							break _loop7;
						}
						}
					} while (true);
					}
					aim = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							if(((EntityAST)aim).declaration.type != DictionaryDeclaration.ENTITY) {
													reportError(aim, "Entity mapping AIM type is not an entity");
												}
												aimEntityList.add(aim);
												if(mark != null) {
													clearLocators(mark);
													mark = null;
												}
											
					}
				}
				else {
					if ( _cnt8>=1 ) { break _loop8; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt8++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
					try {
				for(EntityMappingAST e = entityMappingAst.extended; e != null; e = e.nextEntityMapping) {
											mark = new SourceLocatorMark();
											pushLocatorMark(mark);
											pushLocator(e);
				extEntityMappingAim(e);
											clearLocators(mark);
											mark = null;
				}
				} finally {
										if(mark != null) {
											clearLocators(mark);
											mark = null;
										}
				}
									EntityAST.removeAimListDuplicates(aimEntityList);
				
			}
			_t = __t4;
			_t = _t.getNextSibling();
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
			case OR_EXTENDED:
			case AND_CONSTRUCT:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case STATE_LINK:
			case ORIGINAL_LOCATION:
			{
				p = _t==ASTNULL ? null : (AST)_t;
				states=path(_t,ENTITY_PATH);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
					try {
					for(EntityMappingAST e = entityMappingAst.extended; e != null; e = e.nextEntityMapping) {
												mark = new SourceLocatorMark();
												pushLocatorMark(mark);
												pushLocator(e);
					LinkedList extStates = extEntityMappingPath(e);
					if(states == null) {
					states = extStates;
					} else if(extStates != null) {
					states = stateAtOrExtended(states, extStates);
					}
												clearLocators(mark);
												mark = null;
					}
					} finally {
											if(mark != null) {
												clearLocators(mark);
												mark = null;
											}
					}
										if(states != null) {
					states = postProcessState(states, true);
											Iterator stateIter = states.iterator();
											while(stateIter.hasNext()) {
												WalkerState state = (WalkerState)stateIter.next();
												if(state.remaining != null) {
					// 								if(state.remaining.getType() == TYPE_CONSTRAINT) {
					// 									reportWarning(arm, "!!!!!! TYPE_CONSTRAINT on the top!!!!!!");
					// 								}
					mapping.addChild(state.remaining);
												}
											}
											try {
												if(states != null) {
													WalkerState.removeDuplicates(parser, states);
												}
												EntityAST.attachEntityMappings(states, this);
											} catch(MappingSemanticException e) {
												reportError(e.token, e.message);
											}
										}
										for(EntityMappingAST e = entityMappingAst.extended; e != null; e = e.nextEntityMapping) {
											mapping.addChild(dupAttachTree(e));
										}
									
				}
				break;
			}
			case 3:
			case ATTRIBUTE_MAPPING:
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
				try {
									for(EntityMappingAST e = entityMappingAst.extended; e != null; e = e.nextEntityMapping) {
										mark = new SourceLocatorMark();
										pushLocatorMark(mark);
										pushLocator(e);
										if(extAttrMappings == null) {
											extAttrMappings = new HashMap();
										}
										extEntityMappingAttributes(e, extAttrMappings);
										line = -1;
										clearLocators(mark);
										mark = null;
									}
								} finally {
									if(mark != null) {
										clearLocators(mark);
										mark = null;
									}
									line = -1;
								}
				
			}
			{
			_loop11:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ATTRIBUTE_MAPPING)) {
					attributeMapping(_t,extAttrMappings, false);
					_t = _retTree;
				}
				else {
					break _loop11;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				line = -1;
								try {
									if(!errorOccured) EntityAST.createMappings(this);
								} catch(MappingSemanticException e) {
									reportError(e.token, e.message);
								}
							
			}
			_t = __t2;
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
	
	public final LinkedList  path(AST _t,
		int pathType
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states;
		
		AST path_AST_in = (AST)_t;
		
			constraintLevel = (pathType == ENTITY_PATH ? 1 : 0);
			pathDetected = false;
			SourceLocatorMark mark = new SourceLocatorMark();
			pushLocatorMark(mark);
		
		
		try {      // for error handling
			states=pathElement(_t,defaultOpt);
			_t = _retTree;
			if ( inputState.guessing==0 ) {
				clearLocators(mark);
			}
		}
		catch (MappingSemanticException e) {
			if (inputState.guessing==0) {
				
						if(e.message != NESTED_EXCEPTION_MESSAGE) {
							if(dontReport) {
								e.setLocators(dupLocators());
								e.initCause(notReported);
								notReported = e;
							} else {
								reportError(e.token, e.message);
								clearLocators(mark);
							}
						}
						states = null;
						// Skip over pathElement that caused exception
						_t = skipOverPathElement(_t); // Go ahead taken from ANTLR generated code
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final void attributeMapping(AST _t,
		Map extAttrMappings, boolean extended
	) throws RecognitionException, MappingSemanticException,SdaiException {
		
		AST attributeMapping_AST_in = (AST)_t;
		AST mapping = null;
		AST arm = null;
		AST aim = null;
		AST dataType = null;
		AST p = null;
		AST last = null;
			LinkedList states = null;
			LinkedList aimList = new LinkedList();
			List extAttrList = null;
			mappedValueUsed = mappedValueReallyUsed = false;
		
		
		try {      // for error handling
			AST __t40 = _t;
			mapping = _t==ASTNULL ? null :(AST)_t;
			match(_t,ATTRIBUTE_MAPPING);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ENUM:
			{
				AST tmp2_AST_in = (AST)_t;
				match(_t,ENUM);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					mappedValueUsed = mappedValueReallyUsed = true;
				}
				break;
			}
			case ARM_ATTRIBUTE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			AST __t42 = _t;
			AST tmp3_AST_in = (AST)_t;
			match(_t,ARM_ATTRIBUTE);
			_t = _t.getFirstChild();
			arm = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t42;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					line = ((ParserAST)arm).getLine();
								if(!extended && extAttrMappings != null) {
									AttributeAST armAttrAst = (AttributeAST)arm;
									extAttrList = (List)extAttrMappings.get(armAttrAst.attribute);
								}
							
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AIM_ATTRIBUTE:
			{
				AST __t44 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,AIM_ATTRIBUTE);
				_t = _t.getFirstChild();
				{
				int _cnt46=0;
				_loop46:
				do {
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case ID:
					{
						aim = (AST)_t;
						match(_t,ID);
						_t = _t.getNextSibling();
						if ( inputState.guessing==0 ) {
							aimList.add(aim);
						}
						break;
					}
					case MACRO:
					{
						AST tmp5_AST_in = (AST)_t;
						match(_t,MACRO);
						_t = _t.getNextSibling();
						break;
					}
					default:
					{
						if ( _cnt46>=1 ) { break _loop46; } else {throw new NoViableAltException(_t);}
					}
					}
					_cnt46++;
				} while (true);
				}
				_t = __t44;
				_t = _t.getNextSibling();
				break;
			}
			case 3:
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
			case OR_EXTENDED:
			case AND_CONSTRUCT:
			case ARM:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case STATE_LINK:
			case ORIGINAL_LOCATION:
			{
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
			case ARM:
			{
				AST __t48 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,ARM);
				_t = _t.getFirstChild();
				dataType = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t48;
				_t = _t.getNextSibling();
				break;
			}
			case 3:
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
			case OR_EXTENDED:
			case AND_CONSTRUCT:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case STATE_LINK:
			case ORIGINAL_LOCATION:
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
				mappedValueSet = ((AttributeAST)arm).getMappedValueSet((EntityAST)dataType);
				mappedValues = null;
								Map alternativeMappedValues = (Map)parser.attributeMappedValues.get(
									((AttributeAST)arm).attribute);
								if(alternativeMappedValues != null) {
									ENamed_type dataTypeDefintion = (dataType != null ? 
										((EntityAST)dataType).declaration.definition : null);
									mappedValues = (Map)alternativeMappedValues.get(dataTypeDefintion);
								}
							
			}
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
			case OR_EXTENDED:
			case AND_CONSTRUCT:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case STATE_LINK:
			case ORIGINAL_LOCATION:
			{
				{
				if (_t==null) _t=ASTNULL;
				if (((_tokenSet_0.member(_t.getType())))&&(extAttrList == null)) {
					states=path(_t,ATTRIBUTE_PATH);
					_t = _retTree;
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case LAST:
					{
						AST tmp7_AST_in = (AST)_t;
						match(_t,LAST);
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
					if ( inputState.guessing==0 ) {
							if(mappedValueSet != null) {
													if(!mappedValueReallyUsed) {
														reportWarning(arm, "Mapped value expected but not found");
													}
												} else {
													if(mappedValueReallyUsed) {
														reportError(arm, "Mapped value should not be used for this attribute");
													}
												}
												if(states != null) {
													states = combineStatesAndAimList(aimList, postProcessState(states, true));
													Iterator stateIter = states.iterator();
													while(stateIter.hasNext()) {
														WalkerState state = (WalkerState)stateIter.next();
														if(state.mappedValue != null) {
															if(mappedValueSet == null) {
																reportError(state.mappedValue, "Unexpected mapped value: " +
																			state.mappedValue.getText());
															}
														} else if(mappedValueSet != null && mappedValueReallyUsed) {
															reportError(state.getFirst(parser),
																		"Mapped value needed but is missing for some constraints");
														}
														if(state.remaining != null) {
															line = state.getFirst(parser).getLine();
															mapping.addChild(state.remaining);
														}
													}
													WalkerState.removeDuplicates(parser, states);
												}
											
					}
				}
				else if ((_tokenSet_0.member(_t.getType()))) {
					p = _t==ASTNULL ? null : (AST)_t;
					states=path(_t,ATTRIBUTE_PATH);
					_t = _retTree;
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case LAST:
					{
						AST __t53 = _t;
						AST tmp8_AST_in = (AST)_t;
						match(_t,LAST);
						_t = _t.getFirstChild();
						last = (AST)_t;
						if ( _t==null ) throw new MismatchedTokenException();
						_t = _t.getNextSibling();
						_t = __t53;
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
					if ( inputState.guessing==0 ) {
							states = null;
												EntityAST.processExtAttrMappings(p, last, extAttrList, this);
												for(Iterator e = extAttrList.iterator(); e.hasNext(); ) {
													ExtAttribMapping extAttribMapping = (ExtAttribMapping)e.next();
													LinkedList extStates = extAttribMapping.extStates != null
														? extAttribMapping.extStates
														: extAttribMapping.states;
													if(extStates != null) {
														for(Iterator s = extStates.iterator(); s.hasNext(); ) {
															WalkerState state = WalkerState.next(s);
															if(state.remaining != null) {
																mapping.addChild(dupAttachTree(state.remaining));
															}
														}
													}
												}
											
					}
				}
				else {
					throw new NoViableAltException(_t);
				}
				
				}
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
					if(!errorOccured) {
				if(extended) {
				AttributeAST armAttrAst = (AttributeAST)arm;
				EAttribute armAttr = armAttrAst.attribute;
				extAttrList = (List)extAttrMappings.get(armAttr);
				if(extAttrList == null) {
				extAttrList = new ArrayList();
				extAttrMappings.put(armAttr, extAttrList);
				}
				extAttrList.add(new ExtAttribMapping(sourceLocator, armAttrAst,
				(EntityAST)dataType, aimList, states));
				} else {
				if(extAttrList != null) {
				SourceLocatorMark mark = null;
				try {
				for(Iterator eam = extAttrList.iterator(); eam.hasNext(); ) {
				ExtAttribMapping extAttribMapping = (ExtAttribMapping)eam.next();
				LinkedList extStates = extAttribMapping.extStates != null
				? extAttribMapping.extStates
				: extAttribMapping.states;
				mark = new SourceLocatorMark();
				pushLocatorMark(mark);
				pushLocator(extAttribMapping.sourceLocator);
				EntityAST.attachAttributeMappings(extAttribMapping.arm,
				extAttribMapping.dataType, 
				extAttribMapping.aimList,
				extStates, this);
				clearLocators(mark);
				mark = null;
				}
				} finally {
				if(mark != null) {
				clearLocators(mark);
				mark = null;
				}
				for(Iterator e = extAttrList.iterator(); e.hasNext(); ) {
				ExtAttribMapping extAttribMapping = (ExtAttribMapping)e.next();
				extAttribMapping.extStates = null;
				}
				}
				} else {
				EntityAST.attachAttributeMappings((AttributeAST)arm, (EntityAST)dataType, 
				aimList, states, this);
				}
				}
				}
							
			}
			_t = __t40;
			_t = _t.getNextSibling();
		}
		catch (MappingSemanticException e) {
			if (inputState.guessing==0) {
				
				reportError(e.token, e.message);
				
			} else {
				throw e;
			}
		}
		_retTree = _t;
	}
	
	public final void extEntityMappingAim(AST _t) throws RecognitionException, MappingSemanticException,SdaiException {
		
		AST extEntityMappingAim_AST_in = (AST)_t;
		AST mapping = null;
		AST aimAst = null;
		AST t = null;
		AST o = null;
		AST aim = null;
		
			SourceLocatorMark mark = null;
		
		
		try {      // for error handling
			AST __t13 = _t;
			mapping = _t==ASTNULL ? null :(AST)_t;
			match(_t,ENTITY_MAPPING);
			_t = _t.getFirstChild();
			AST __t14 = _t;
			AST tmp9_AST_in = (AST)_t;
			match(_t,ARM);
			_t = _t.getFirstChild();
			AST tmp10_AST_in = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t14;
			_t = _t.getNextSibling();
			AST __t15 = _t;
			aimAst = _t==ASTNULL ? null :(AST)_t;
			match(_t,AIM);
			_t = _t.getFirstChild();
			{
			int _cnt19=0;
			_loop19:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==TEMPLATE_CALL||_t.getType()==ORIGINAL_LOCATION||_t.getType()==ID)) {
					{
					_loop18:
					do {
						if (_t==null) _t=ASTNULL;
						switch ( _t.getType()) {
						case TEMPLATE_CALL:
						{
							t = (AST)_t;
							match(_t,TEMPLATE_CALL);
							_t = _t.getNextSibling();
							if ( inputState.guessing==0 ) {
								((TemplateCallAST)t).resolve(aimAst, parser, sourceLocator);
									_t = t.getNextSibling();
														
							}
							break;
						}
						case ORIGINAL_LOCATION:
						{
							o = (AST)_t;
							match(_t,ORIGINAL_LOCATION);
							_t = _t.getNextSibling();
							if ( inputState.guessing==0 ) {
									if(mark == null) {
																mark = new SourceLocatorMark();
																pushLocatorMark(mark);
															}
															pushLocator((SourceLocator)o);
														
							}
							break;
						}
						default:
						{
							break _loop18;
						}
						}
					} while (true);
					}
					aim = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							if(((EntityAST)aim).declaration.type != DictionaryDeclaration.ENTITY) {
						reportError(aim, "Entity mapping AIM type is not an entity");
												}
												aimEntityList.add(aim);
												if(mark != null) {
													clearLocators(mark);
													mark = null;
												}
											
					}
				}
				else {
					if ( _cnt19>=1 ) { break _loop19; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt19++;
			} while (true);
			}
			_t = __t15;
			_t = _t.getNextSibling();
			skipEntityMappingPath(_t);
			_t = _retTree;
			{
			_loop21:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ATTRIBUTE_MAPPING)) {
					AST tmp11_AST_in = (AST)_t;
					match(_t,ATTRIBUTE_MAPPING);
					_t = _t.getNextSibling();
				}
				else {
					break _loop21;
				}
				
			} while (true);
			}
			AST tmp12_AST_in = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t13;
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
	
	public final void skipEntityMappingPath(AST _t) throws RecognitionException {
		
		AST skipEntityMappingPath_AST_in = (AST)_t;
		
		try {      // for error handling
			{
			_loop37:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case TEMPLATE_CALL:
				{
					AST tmp13_AST_in = (AST)_t;
					match(_t,TEMPLATE_CALL);
					_t = _t.getNextSibling();
					break;
				}
				case ORIGINAL_LOCATION:
				{
					AST tmp14_AST_in = (AST)_t;
					match(_t,ORIGINAL_LOCATION);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					break _loop37;
				}
				}
			} while (true);
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ENTITY:
			{
				AST tmp15_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getNextSibling();
				break;
			}
			case ENTITY_AGGREG:
			{
				AST tmp16_AST_in = (AST)_t;
				match(_t,ENTITY_AGGREG);
				_t = _t.getNextSibling();
				break;
			}
			case EQ_ENTITY:
			{
				AST tmp17_AST_in = (AST)_t;
				match(_t,EQ_ENTITY);
				_t = _t.getNextSibling();
				break;
			}
			case FROMPOINT:
			{
				AST tmp18_AST_in = (AST)_t;
				match(_t,FROMPOINT);
				_t = _t.getNextSibling();
				break;
			}
			case SUPER:
			{
				AST tmp19_AST_in = (AST)_t;
				match(_t,SUPER);
				_t = _t.getNextSibling();
				break;
			}
			case SUB:
			{
				AST tmp20_AST_in = (AST)_t;
				match(_t,SUB);
				_t = _t.getNextSibling();
				break;
			}
			case SELECT:
			{
				AST tmp21_AST_in = (AST)_t;
				match(_t,SELECT);
				_t = _t.getNextSibling();
				break;
			}
			case EXTENDED_INTO:
			{
				AST tmp22_AST_in = (AST)_t;
				match(_t,EXTENDED_INTO);
				_t = _t.getNextSibling();
				break;
			}
			case EXTENSION_OF:
			{
				AST tmp23_AST_in = (AST)_t;
				match(_t,EXTENSION_OF);
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE_NEGATION:
			{
				AST tmp24_AST_in = (AST)_t;
				match(_t,ATTRIBUTE_NEGATION);
				_t = _t.getNextSibling();
				break;
			}
			case EQ_ATTRIBUTE:
			{
				AST tmp25_AST_in = (AST)_t;
				match(_t,EQ_ATTRIBUTE);
				_t = _t.getNextSibling();
				break;
			}
			case TOPOINT:
			{
				AST tmp26_AST_in = (AST)_t;
				match(_t,TOPOINT);
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE:
			{
				AST tmp27_AST_in = (AST)_t;
				match(_t,ATTRIBUTE);
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE_AGGREG:
			{
				AST tmp28_AST_in = (AST)_t;
				match(_t,ATTRIBUTE_AGGREG);
				_t = _t.getNextSibling();
				break;
			}
			case AND_CONSTRUCT:
			{
				AST tmp29_AST_in = (AST)_t;
				match(_t,AND_CONSTRUCT);
				_t = _t.getNextSibling();
				break;
			}
			case OR:
			{
				AST tmp30_AST_in = (AST)_t;
				match(_t,OR);
				_t = _t.getNextSibling();
				break;
			}
			case OR_EXTENDED:
			{
				AST tmp31_AST_in = (AST)_t;
				match(_t,OR_EXTENDED);
				_t = _t.getNextSibling();
				break;
			}
			case NEGATION:
			{
				AST tmp32_AST_in = (AST)_t;
				match(_t,NEGATION);
				_t = _t.getNextSibling();
				break;
			}
			case CONSTRAINT:
			{
				AST tmp33_AST_in = (AST)_t;
				match(_t,CONSTRAINT);
				_t = _t.getNextSibling();
				break;
			}
			case STATE_LINK:
			{
				AST tmp34_AST_in = (AST)_t;
				match(_t,STATE_LINK);
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE_MAPPING:
			case ID:
			{
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
	
	public final LinkedList  extEntityMappingPath(AST _t) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST extEntityMappingPath_AST_in = (AST)_t;
		AST mapping = null;
		
		try {      // for error handling
			AST __t23 = _t;
			mapping = _t==ASTNULL ? null :(AST)_t;
			match(_t,ENTITY_MAPPING);
			_t = _t.getFirstChild();
			AST __t24 = _t;
			AST tmp35_AST_in = (AST)_t;
			match(_t,ARM);
			_t = _t.getFirstChild();
			AST tmp36_AST_in = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t24;
			_t = _t.getNextSibling();
			AST __t25 = _t;
			AST tmp37_AST_in = (AST)_t;
			match(_t,AIM);
			_t = _t.getFirstChild();
			AST tmp38_AST_in = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t25;
			_t = _t.getNextSibling();
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
			case OR_EXTENDED:
			case AND_CONSTRUCT:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case STATE_LINK:
			case ORIGINAL_LOCATION:
			{
				states=path(_t,ENTITY_PATH);
				_t = _retTree;
				break;
			}
			case ATTRIBUTE_MAPPING:
			case ID:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			{
			_loop28:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ATTRIBUTE_MAPPING)) {
					AST tmp39_AST_in = (AST)_t;
					match(_t,ATTRIBUTE_MAPPING);
					_t = _t.getNextSibling();
				}
				else {
					break _loop28;
				}
				
			} while (true);
			}
			AST tmp40_AST_in = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t23;
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
		return states;
	}
	
	public final void extEntityMappingAttributes(AST _t,
		Map extAttrMappings
	) throws RecognitionException, MappingSemanticException,SdaiException {
		
		AST extEntityMappingAttributes_AST_in = (AST)_t;
		AST mapping = null;
		
		try {      // for error handling
			AST __t30 = _t;
			mapping = _t==ASTNULL ? null :(AST)_t;
			match(_t,ENTITY_MAPPING);
			_t = _t.getFirstChild();
			AST __t31 = _t;
			AST tmp41_AST_in = (AST)_t;
			match(_t,ARM);
			_t = _t.getFirstChild();
			AST tmp42_AST_in = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t31;
			_t = _t.getNextSibling();
			AST __t32 = _t;
			AST tmp43_AST_in = (AST)_t;
			match(_t,AIM);
			_t = _t.getFirstChild();
			AST tmp44_AST_in = (AST)_t;
			if ( _t==null ) throw new MismatchedTokenException();
			_t = _t.getNextSibling();
			_t = __t32;
			_t = _t.getNextSibling();
			skipEntityMappingPath(_t);
			_t = _retTree;
			{
			_loop34:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ATTRIBUTE_MAPPING)) {
					attributeMapping(_t,extAttrMappings, true);
					_t = _retTree;
				}
				else {
					break _loop34;
				}
				
			} while (true);
			}
			AST tmp45_AST_in = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t30;
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
	
	public final LinkedList  pathElement(AST _t,
		WalkerOptions options
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST pathElement_AST_in = (AST)_t;
		AST t = null;
		AST o = null;
		AST stateLink = null;
		AST enum = null;
		
			SourceLocatorMark mark = null;
		
		
		try {      // for error handling
			{
			_loop57:
			do {
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case TEMPLATE_CALL:
				{
					t = (AST)_t;
					match(_t,TEMPLATE_CALL);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							((TemplateCallAST)t).resolve(t, parser, sourceLocator);
										_t = t.getNextSibling();
									
					}
					break;
				}
				case ORIGINAL_LOCATION:
				{
					o = (AST)_t;
					match(_t,ORIGINAL_LOCATION);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							if(mark == null) {
											mark = new SourceLocatorMark();
											pushLocatorMark(mark);
										}
										pushLocator((SourceLocator)o);
									
					}
					break;
				}
				default:
				{
					break _loop57;
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
				states=entityConstruct(_t,options);
				_t = _retTree;
				break;
			}
			case EQ_ATTRIBUTE:
			case ATTRIBUTE:
			case ATTRIBUTE_AGGREG:
			case TOPOINT:
			case ATTRIBUTE_NEGATION:
			{
				states=attributeConstruct(_t,options);
				_t = _retTree;
				break;
			}
			case AND_CONSTRUCT:
			{
				states=andConstruct(_t,options);
				_t = _retTree;
				break;
			}
			case OR:
			case OR_EXTENDED:
			{
				states=orConstruct(_t,true);
				_t = _retTree;
				break;
			}
			case CONSTRAINT:
			case NEGATION:
			{
				states=constraintConstruct(_t,options);
				_t = _retTree;
				break;
			}
			case STATE_LINK:
			{
				stateLink = (AST)_t;
				match(_t,STATE_LINK);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						states = WalkerState.createList(((StateLink)stateLink).state);
									((StateLink)stateLink).wasVisited = true;
								
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
					if(mark != null) {
								clearLocators(mark);
							}
							if(options.postProcess) states = postProcessState(states, false);
						
			}
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ENUM:
			{
				enum = (AST)_t;
				match(_t,ENUM);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						if(states.size() == 1 && mappedValueSet != null) {
										if(mappedValueSet.contains(enum.getText().toLowerCase())) {
											((WalkerState)states.getFirst()).mappedValue = enum;
											constraintLevel++;
										} else {
											throw new MappingSemanticException(enum, "Mapped value invalid");
										}
									} else {
										throw new MappingSemanticException(enum, "Mapped value can not be used here");
									}
								
				}
				break;
			}
			case 3:
			case ATTRIBUTE_MAPPING:
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
			case OR_EXTENDED:
			case AND:
			case AND_CONSTRUCT:
			case OR_END:
			case AND_END:
			case LAST:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case STATE_LINK:
			case ORIGINAL_LOCATION:
			case ID:
			{
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
				
				//DEBUG 		reportWarning("1" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  entityConstruct(AST _t,
		WalkerOptions options
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST entityConstruct_AST_in = (AST)_t;
		AST idEntity = null;
		AST idEntityAggreg = null;
		AST index = null;
		AST idEq = null;
		AST idFrom = null;
		AST idSuper = null;
		AST idSub = null;
		AST idSelect = null;
		AST idExtInto = null;
		AST idExtOf = null;
			LinkedList remainingStates = null;
			WalkerOptions innerElementOpt = new WalkerOptions();
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ENTITY:
			{
				AST __t69 = _t;
				AST tmp46_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idEntity = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					innerElementOpt.previousId = (EntityAST)idEntity;
				}
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
				case OR_EXTENDED:
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
				case STATE_LINK:
				case ORIGINAL_LOCATION:
				{
					remainingStates=pathInnerElement(_t,innerElementOpt);
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
					states = stateAtEntity((EntityAST)idEntity, remainingStates, innerElementOpt);
				}
				_t = __t69;
				_t = _t.getNextSibling();
				break;
			}
			case ENTITY_AGGREG:
			{
				AST __t71 = _t;
				AST tmp47_AST_in = (AST)_t;
				match(_t,ENTITY_AGGREG);
				_t = _t.getFirstChild();
				idEntityAggreg = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_i:
				{
					AST tmp48_AST_in = (AST)_t;
					match(_t,LITERAL_i);
					_t = _t.getNextSibling();
					break;
				}
				case INT:
				{
					index = (AST)_t;
					match(_t,INT);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				remainingStates=pathInnerElement(_t,innerElementOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtEntityAggreg((EntityAST)idEntityAggreg, index, 
											remainingStates, innerElementOpt);
									
				}
				_t = __t71;
				_t = _t.getNextSibling();
				break;
			}
			case EQ_ENTITY:
			{
				AST __t73 = _t;
				AST tmp49_AST_in = (AST)_t;
				match(_t,EQ_ENTITY);
				_t = _t.getFirstChild();
				AST __t74 = _t;
				AST tmp50_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idEq = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t74;
				_t = _t.getNextSibling();
				remainingStates=value(_t);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						if(((EntityAST)idEq).exactType) {
											reportWarning(idEq, "Warning: Exact type was ignored");
										}
										states = stateAtEqEntity((EntityAST)idEq, remainingStates);
				}
				_t = __t73;
				_t = _t.getNextSibling();
				break;
			}
			case FROMPOINT:
			{
				AST __t75 = _t;
				AST tmp51_AST_in = (AST)_t;
				match(_t,FROMPOINT);
				_t = _t.getFirstChild();
				AST __t76 = _t;
				AST tmp52_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idFrom = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t76;
				_t = _t.getNextSibling();
				remainingStates=pathInnerElement(_t,innerElementOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtFromPoint((EntityAST)idFrom, remainingStates, innerElementOpt);
										states = stateAtExact((EntityAST)idFrom, states, options);
									
				}
				_t = __t75;
				_t = _t.getNextSibling();
				break;
			}
			case SUPER:
			{
				AST __t77 = _t;
				AST tmp53_AST_in = (AST)_t;
				match(_t,SUPER);
				_t = _t.getFirstChild();
				AST __t78 = _t;
				AST tmp54_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idSuper = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t78;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						if(((EntityAST)idSuper).exactType) {
											reportWarning(idSuper, "Warning: Exact type was ignored");
										}
										innerElementOpt.partialConstraints = true;
									
				}
				remainingStates=pathInnerElement(_t,innerElementOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtSuper((EntityAST)idSuper, remainingStates, innerElementOpt); 
										if(innerElementOpt.constraintStateList != null) {
											innerElementOpt.partialConstraints = false;
											states = stateAtConstraint(innerElementOpt.constraintStateList,
												states, innerElementOpt);
										}
									
				}
				_t = __t77;
				_t = _t.getNextSibling();
				break;
			}
			case SUB:
			{
				AST __t79 = _t;
				AST tmp55_AST_in = (AST)_t;
				match(_t,SUB);
				_t = _t.getFirstChild();
				AST __t80 = _t;
				AST tmp56_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idSub = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t80;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					innerElementOpt.partialConstraints = true;
				}
				remainingStates=pathInnerElement(_t,innerElementOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtSub((EntityAST)idSub, remainingStates, innerElementOpt);
										if(innerElementOpt.constraintStateList != null) {
											innerElementOpt.partialConstraints = false;
											states = stateAtConstraint(innerElementOpt.constraintStateList,
												states, innerElementOpt);
										}
										states = stateAtExact((EntityAST)idSub, states, options);
									
				}
				_t = __t79;
				_t = _t.getNextSibling();
				break;
			}
			case SELECT:
			{
				AST __t81 = _t;
				AST tmp57_AST_in = (AST)_t;
				match(_t,SELECT);
				_t = _t.getFirstChild();
				if ( inputState.guessing==0 ) {
					innerElementOpt.postProcess = false;
				}
				AST __t82 = _t;
				AST tmp58_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idSelect = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t82;
				_t = _t.getNextSibling();
				remainingStates=pathInnerElement(_t,innerElementOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtSelect((EntityAST)idSelect, remainingStates, innerElementOpt);
										states = stateAtExact((EntityAST)idSelect, states, options);
									
				}
				_t = __t81;
				_t = _t.getNextSibling();
				break;
			}
			case EXTENDED_INTO:
			{
				AST __t83 = _t;
				AST tmp59_AST_in = (AST)_t;
				match(_t,EXTENDED_INTO);
				_t = _t.getFirstChild();
				if ( inputState.guessing==0 ) {
					innerElementOpt.postProcess = false;
				}
				AST __t84 = _t;
				AST tmp60_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idExtInto = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t84;
				_t = _t.getNextSibling();
				remainingStates=pathInnerElement(_t,innerElementOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtExtendedInto((EntityAST)idExtInto, remainingStates, innerElementOpt);
									
				}
				_t = __t83;
				_t = _t.getNextSibling();
				break;
			}
			case EXTENSION_OF:
			{
				AST __t85 = _t;
				AST tmp61_AST_in = (AST)_t;
				match(_t,EXTENSION_OF);
				_t = _t.getFirstChild();
				if ( inputState.guessing==0 ) {
					innerElementOpt.postProcess = false;
				}
				AST __t86 = _t;
				AST tmp62_AST_in = (AST)_t;
				match(_t,ENTITY);
				_t = _t.getFirstChild();
				idExtOf = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t86;
				_t = _t.getNextSibling();
				remainingStates=pathInnerElement(_t,innerElementOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtExtensionOf((EntityAST)idExtOf, remainingStates, innerElementOpt);
									
				}
				_t = __t85;
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
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("3" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  attributeConstruct(AST _t,
		WalkerOptions options
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST attributeConstruct_AST_in = (AST)_t;
		AST n = null;
		AST idEntityAggreg = null;
		AST entityIndex = null;
		AST idEntity = null;
		AST attId = null;
		AST attAggId = null;
		AST index = null;
			LinkedList remainingStates = null;
			LinkedList attributeStates = null;
			AST member = null;
			WalkerOptions innerElementOpt = new WalkerOptions();
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE_NEGATION:
			{
				AST __t89 = _t;
				n = _t==ASTNULL ? null :(AST)_t;
				match(_t,ATTRIBUTE_NEGATION);
				_t = _t.getFirstChild();
				if ( inputState.guessing==0 ) {
						innerElementOpt = new WalkerOptions(options);
										innerElementOpt.negation = true;
									
				}
				states=attributeConstruct(_t,innerElementOpt);
				_t = _retTree;
				_t = __t89;
				_t = _t.getNextSibling();
				break;
			}
			case EQ_ATTRIBUTE:
			{
				AST __t92 = _t;
				AST tmp63_AST_in = (AST)_t;
				match(_t,EQ_ATTRIBUTE);
				_t = _t.getFirstChild();
				attributeStates=attribute(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case INT:
				case FLOAT:
				case ENUM:
				case STRING:
				{
					remainingStates=value(_t);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
						states = stateAtEqAttributeValue(attributeStates, remainingStates, options);
					}
					break;
				}
				case ATTRIBUTE:
				case ATTRIBUTE_AGGREG:
				{
					remainingStates=attribute(_t);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
						states = stateAtEqAttributeAttribute(attributeStates, remainingStates, options);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t92;
				_t = _t.getNextSibling();
				break;
			}
			case TOPOINT:
			{
				AST __t94 = _t;
				AST tmp64_AST_in = (AST)_t;
				match(_t,TOPOINT);
				_t = _t.getFirstChild();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case ATTRIBUTE:
				case ATTRIBUTE_AGGREG:
				{
					attributeStates=attribute(_t);
					_t = _retTree;
					remainingStates=pathInnerElement(_t,innerElementOpt);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
						states = stateAtToPoint(attributeStates, remainingStates, innerElementOpt);
					}
					break;
				}
				case ENTITY_AGGREG:
				{
					AST __t96 = _t;
					AST tmp65_AST_in = (AST)_t;
					match(_t,ENTITY_AGGREG);
					_t = _t.getFirstChild();
					idEntityAggreg = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					{
					if (_t==null) _t=ASTNULL;
					switch ( _t.getType()) {
					case LITERAL_i:
					{
						AST tmp66_AST_in = (AST)_t;
						match(_t,LITERAL_i);
						_t = _t.getNextSibling();
						break;
					}
					case INT:
					{
						entityIndex = (AST)_t;
						match(_t,INT);
						_t = _t.getNextSibling();
						break;
					}
					default:
					{
						throw new NoViableAltException(_t);
					}
					}
					}
					_t = __t96;
					_t = _t.getNextSibling();
					remainingStates=pathInnerElement(_t,innerElementOpt);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
							states = stateAtEntityAggregToPoint((EntityAST)idEntityAggreg, 
													entityIndex, remainingStates, innerElementOpt);
											
					}
					break;
				}
				case ENTITY:
				{
					AST __t98 = _t;
					AST tmp67_AST_in = (AST)_t;
					match(_t,ENTITY);
					_t = _t.getFirstChild();
					idEntity = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					_t = __t98;
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							if(((EntityAST)idEntity).exactType) {
													reportWarning(idEntity, "Warning: Exact type was ignored");
												}
											
					}
					remainingStates=pathInnerElement(_t,innerElementOpt);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
							states = stateAtEntityToPoint((EntityAST)idEntity,
													remainingStates, innerElementOpt);
											
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
				_t = __t94;
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE:
			{
				AST __t99 = _t;
				AST tmp68_AST_in = (AST)_t;
				match(_t,ATTRIBUTE);
				_t = _t.getFirstChild();
				attId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
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
				case OR_EXTENDED:
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
				case STATE_LINK:
				case ORIGINAL_LOCATION:
				{
					remainingStates=pathInnerElement(_t,innerElementOpt);
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
					states = stateAtAttribute((AttributeAST)attId, remainingStates, innerElementOpt);
				}
				_t = __t99;
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE_AGGREG:
			{
				AST __t101 = _t;
				AST tmp69_AST_in = (AST)_t;
				match(_t,ATTRIBUTE_AGGREG);
				_t = _t.getFirstChild();
				attAggId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_i:
				{
					AST tmp70_AST_in = (AST)_t;
					match(_t,LITERAL_i);
					_t = _t.getNextSibling();
					break;
				}
				case INT:
				{
					index = (AST)_t;
					match(_t,INT);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
						member = index;
					}
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
				case OR_EXTENDED:
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
				case STATE_LINK:
				case ORIGINAL_LOCATION:
				{
					remainingStates=pathInnerElement(_t,innerElementOpt);
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
						states = stateAtAttributeAggreg((AttributeAST)attAggId, member, 
											remainingStates, innerElementOpt);
									
				}
				_t = __t101;
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
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("4" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  andConstruct(AST _t,
		WalkerOptions options
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST andConstruct_AST_in = (AST)_t;
		AST andConstructAst = null;
			LinkedList remainingStates = null;
		
		
		try {      // for error handling
			AST __t105 = _t;
			andConstructAst = _t==ASTNULL ? null :(AST)_t;
			match(_t,AND_CONSTRUCT);
			_t = _t.getFirstChild();
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AND:
			{
				if ( inputState.guessing==0 ) {
					scanAndEnds((AndConstructAST)andConstructAst);
				}
				states=and(_t,(AndConstructAST)andConstructAst);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
					states = stateAtIntersect((AndConstructAST)andConstructAst, states);
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
			case OR_EXTENDED:
			case AND_CONSTRUCT:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case STATE_LINK:
			case ORIGINAL_LOCATION:
			{
				states=pathElement(_t,options);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			_t = __t105;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("5" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  orConstruct(AST _t,
		boolean checkEmpty
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST orConstruct_AST_in = (AST)_t;
			LinkedList remainingStates1 = null;
			LinkedList remainingStates2 = null;
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case OR:
			{
				AST __t115 = _t;
				AST tmp71_AST_in = (AST)_t;
				match(_t,OR);
				_t = _t.getFirstChild();
				remainingStates1=pathOrElement(_t);
				_t = _retTree;
				remainingStates2=pathOrElement(_t);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtOr(remainingStates1, remainingStates2);
					if(checkEmpty && states == null) {
					throw new MappingSemanticException(null, NESTED_EXCEPTION_MESSAGE);
					}
					
				}
				_t = __t115;
				_t = _t.getNextSibling();
				break;
			}
			case OR_EXTENDED:
			{
				AST __t116 = _t;
				AST tmp72_AST_in = (AST)_t;
				match(_t,OR_EXTENDED);
				_t = _t.getFirstChild();
				remainingStates1=pathOrElement(_t);
				_t = _retTree;
				remainingStates2=pathOrElement(_t);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						states = stateAtOrExtended(remainingStates1, remainingStates2);
					if(checkEmpty && states == null) {
					throw new MappingSemanticException(null, NESTED_EXCEPTION_MESSAGE);
					}
					
				}
				_t = __t116;
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
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("8" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  constraintConstruct(AST _t,
		WalkerOptions options
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST constraintConstruct_AST_in = (AST)_t;
		AST n = null;
			LinkedList remainingStates = null;
			WalkerOptions innerElementOpt = null;
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NEGATION:
			{
				AST __t123 = _t;
				n = _t==ASTNULL ? null :(AST)_t;
				match(_t,NEGATION);
				_t = _t.getFirstChild();
				if ( inputState.guessing==0 ) {
						innerElementOpt = new WalkerOptions(options);
										innerElementOpt.negation = true;
									
				}
				states=constraintConstruct(_t,innerElementOpt);
				_t = _retTree;
				_t = __t123;
				_t = _t.getNextSibling();
				break;
			}
			case CONSTRAINT:
			{
				AST __t126 = _t;
				AST tmp73_AST_in = (AST)_t;
				match(_t,CONSTRAINT);
				_t = _t.getFirstChild();
				if ( inputState.guessing==0 ) {
					constraintLevel++;
				}
				states=pathElement(_t,defaultOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
					constraintLevel--;
				}
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
				case OR_EXTENDED:
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
				case STATE_LINK:
				case ORIGINAL_LOCATION:
				{
					remainingStates=pathInnerElement(_t,new WalkerOptions());
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
					states = stateAtConstraint(states, remainingStates, options);
				}
				_t = __t126;
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
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("9" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  pathInnerElement(AST _t,
		WalkerOptions options
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST pathInnerElement_AST_in = (AST)_t;
		AST andEndAst = null;
			AndEndAST andEnd = null;
			int savedConstraintLevel = 0;
			boolean savedPathDetected = false;
		
		
		try {      // for error handling
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
			case OR_EXTENDED:
			case AND_CONSTRUCT:
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case ATTRIBUTE_NEGATION:
			case NEGATION:
			case STATE_LINK:
			case ORIGINAL_LOCATION:
			{
				states=pathElement(_t,options);
				_t = _retTree;
				break;
			}
			case AND_END:
			{
				AST __t62 = _t;
				andEndAst = _t==ASTNULL ? null :(AST)_t;
				match(_t,AND_END);
				_t = _t.getFirstChild();
				if ( inputState.guessing==0 ) {
						andEnd = (AndEndAST)andEndAst;
										if(andEnd.andConstruct.subpaths != null) options.reachedAndEnd = andEnd;
									
				}
				{
				if (_t==null) _t=ASTNULL;
				if ((((_t.getType() >= SCHEMA_MAPPING && _t.getType() <= END_OF_PATH_CONSTRAINT)))&&(andEnd != null && andEnd.unused)) {
					AST tmp74_AST_in = (AST)_t;
					if ( _t==null ) throw new MismatchedTokenException();
					_t = _t.getNextSibling();
				}
				else if ((_tokenSet_1.member(_t.getType()))) {
					if ( inputState.guessing==0 ) {
							savedConstraintLevel = constraintLevel;
												savedPathDetected = pathDetected;
						if(andEnd.andConstruct.oldConstraintLevel >= 0) {
						constraintLevel = andEnd.andConstruct.oldConstraintLevel;
						pathDetected = andEnd.andConstruct.oldPathDetected;
						}
											
					}
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
					case OR_EXTENDED:
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
					case STATE_LINK:
					case ORIGINAL_LOCATION:
					{
						states=pathInnerElement(_t,options);
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
							constraintLevel = savedConstraintLevel;
												pathDetected = savedPathDetected;
											
					}
				}
				else {
					throw new NoViableAltException(_t);
				}
				
				}
				_t = __t62;
				_t = _t.getNextSibling();
				break;
			}
			case OR_END:
			{
				AST __t65 = _t;
				AST tmp75_AST_in = (AST)_t;
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
				case OR_EXTENDED:
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
				case STATE_LINK:
				case ORIGINAL_LOCATION:
				{
					states=pathInnerElement(_t,options);
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
				_t = __t65;
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
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("2" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  value(AST _t) throws RecognitionException {
		LinkedList states = null;
		
		AST value_AST_in = (AST)_t;
		AST s = null;
		AST i = null;
		AST f = null;
		AST enum = null;
			AST remaining = null;
			AST possibleMappedValue = null;
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case STRING:
			{
				s = (AST)_t;
				match(_t,STRING);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(s,STRING_CONSTRAINT)).add(astFactory.dup(s)));
									possibleMappedValue = s;
								
				}
				break;
			}
			case INT:
			{
				i = (AST)_t;
				match(_t,INT);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(i,INTEGER_CONSTRAINT)).add(astFactory.dup(i)));
									possibleMappedValue = i;
								
				}
				break;
			}
			case FLOAT:
			{
				f = (AST)_t;
				match(_t,FLOAT);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(f,REAL_CONSTRAINT)).add(astFactory.dup(f))); possibleMappedValue = f;
				}
				break;
			}
			case ENUM:
			{
				enum = (AST)_t;
				match(_t,ENUM);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						remaining = (AST)astFactory.make( (new ASTArray(2)).add(auxiliaryAST(enum,ENUMERATION_CONSTRAINT)).add(astFactory.dup(enum)));
									possibleMappedValue = enum;
								
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
					states = WalkerState.createList(remaining);
							if(possibleMappedValue != null && mappedValueSet != null && !mappedValueUsed) {
								// getText() is a bad way here!!!!!!!!!! Should be changed!!!!!!!!!!!!! V.N.
								AST mappedValue = (mappedValues != null ? 
									(AST)mappedValues.get(possibleMappedValue.getText()) : null);
								if(mappedValue != null) {
									((WalkerState)states.getFirst()).mappedValue = dupAttachTree(mappedValue);
									mappedValueReallyUsed = true;
									constraintLevel++;
								} else if(mappedValueSet.contains(possibleMappedValue.getText().toLowerCase())) {
									((WalkerState)states.getFirst()).mappedValue = possibleMappedValue;
									mappedValueReallyUsed = true;
									constraintLevel++;
								} else {
									String otherPossibleMappedValue = possibleMappedValue.getText().replace(' ', '_');
									if(mappedValueSet.contains(otherPossibleMappedValue.toLowerCase())) {
										AST newMappedValue = dupAttachTree(possibleMappedValue);
										newMappedValue.setText(otherPossibleMappedValue);
										((WalkerState)states.getFirst()).mappedValue = newMappedValue;
										mappedValueReallyUsed = true;
										constraintLevel++;
									}
								}
							}
						
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("11" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  attribute(AST _t) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST attribute_AST_in = (AST)_t;
		AST attId = null;
		AST attAggId = null;
		AST index = null;
		
			AST member = null;
		
		
		try {      // for error handling
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ATTRIBUTE:
			{
				AST __t130 = _t;
				AST tmp76_AST_in = (AST)_t;
				match(_t,ATTRIBUTE);
				_t = _t.getFirstChild();
				attId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
					states = stateAtAttribute((AttributeAST)attId, null, defaultOpt);
				}
				_t = __t130;
				_t = _t.getNextSibling();
				break;
			}
			case ATTRIBUTE_AGGREG:
			{
				AST __t131 = _t;
				AST tmp77_AST_in = (AST)_t;
				match(_t,ATTRIBUTE_AGGREG);
				_t = _t.getFirstChild();
				attAggId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case LITERAL_i:
				{
					AST tmp78_AST_in = (AST)_t;
					match(_t,LITERAL_i);
					_t = _t.getNextSibling();
					break;
				}
				case INT:
				{
					index = (AST)_t;
					match(_t,INT);
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
						member = index;
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
					states = stateAtAttributeAggreg((AttributeAST)attAggId, member, null, defaultOpt);
				}
				_t = __t131;
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
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("10" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  and(AST _t,
		AndConstructAST andConstructAst
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST and_AST_in = (AST)_t;
			LinkedList remainingStates1 = null;
			LinkedList remainingStates2 = null;
			boolean intersection = andConstructAst.subpaths != null;
		
		
		try {      // for error handling
			AST __t108 = _t;
			AST tmp79_AST_in = (AST)_t;
			match(_t,AND);
			_t = _t.getFirstChild();
			remainingStates1=pathAndElement(_t,andConstructAst, intersection);
			_t = _retTree;
			remainingStates2=pathAndElement(_t,andConstructAst, intersection);
			_t = _retTree;
			if ( inputState.guessing==0 ) {
					if(!intersection) {
									states = stateAtAnd(remainingStates1, remainingStates2);
								} else {
									states = remainingStates1;
								}
							
			}
			_t = __t108;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("6" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  pathAndElement(AST _t,
		AndConstructAST andConstructAst, boolean intersection
	) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST pathAndElement_AST_in = (AST)_t;
		
		try {      // for error handling
			{
			boolean synPredMatched112 = false;
			if (((_t.getType()==AND))) {
				AST __t112 = _t;
				synPredMatched112 = true;
				inputState.guessing++;
				try {
					{
					AST tmp80_AST_in = (AST)_t;
					match(_t,AND);
					_t = _t.getNextSibling();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched112 = false;
				}
				_t = __t112;
				inputState.guessing--;
			}
			if ( synPredMatched112 ) {
				states=and(_t,andConstructAst);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
					states = postProcessState(states, false);
				}
			}
			else if (((_tokenSet_0.member(_t.getType())))&&( intersection )) {
				if ( inputState.guessing==0 ) {
						andConstructAst.oldConstraintLevel = constraintLevel;
									andConstructAst.oldPathDetected = pathDetected;
									constraintLevel = 0;
									pathDetected = false;
								
				}
				states=pathElement(_t,defaultOpt);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						pathDetected = andConstructAst.oldPathDetected;
									constraintLevel = andConstructAst.oldConstraintLevel;
									LinkedList subpathStates = new LinkedList(states);
									Iterator subpathStateIter = subpathStates.iterator();
									while(subpathStateIter.hasNext()) {
										WalkerState state = WalkerState.next(subpathStateIter);
										if(state.remaining != null) {
											state.remaining = dupWholeTree(state.remaining);
										}
									}
									andConstructAst.subpaths.add(subpathStates);
								
				}
			}
			else if ((_tokenSet_0.member(_t.getType()))) {
				states=pathElement(_t,defaultOpt);
				_t = _retTree;
			}
			else {
				throw new NoViableAltException(_t);
			}
			
			}
		}
		catch (RecognitionException e) {
			if (inputState.guessing==0) {
				
				//DEBUG 		reportWarning("7" + e);
						throw e;
					
			} else {
				throw e;
			}
		}
		_retTree = _t;
		return states;
	}
	
	public final LinkedList  pathOrElement(AST _t) throws RecognitionException, MappingSemanticException,SdaiException {
		LinkedList states = null;
		
		AST pathOrElement_AST_in = (AST)_t;
		
			SourceLocatorMark mark = null;
		
		
		try {      // for error handling
			{
			boolean synPredMatched120 = false;
			if (((_t.getType()==OR||_t.getType()==OR_EXTENDED))) {
				AST __t120 = _t;
				synPredMatched120 = true;
				inputState.guessing++;
				try {
					{
					AST tmp81_AST_in = (AST)_t;
					match(_t,OR);
					_t = _t.getNextSibling();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched120 = false;
				}
				_t = __t120;
				inputState.guessing--;
			}
			if ( synPredMatched120 ) {
				states=orConstruct(_t,false);
				_t = _retTree;
			}
			else if ((_tokenSet_0.member(_t.getType()))) {
				try {      // for error handling
					if ( inputState.guessing==0 ) {
							mark = new SourceLocatorMark();
										pushLocatorMark(mark);
									
					}
					states=pathElement(_t,defaultOpt);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
							clearLocators(mark);
									
					}
				}
				catch (MappingSemanticException e) {
					if (inputState.guessing==0) {
						
										if(e.message != NESTED_EXCEPTION_MESSAGE) {
											if(dontReport) {
												e.setLocators(dupLocators());
												e.initCause(notReported);
												notReported = e;
											} else {
												reportError(e.token, e.message);
												clearLocators(mark);
											}
										}
										states = null;
										// Skip over pathElement that caused exception
										_t = skipOverPathElement(_t); // Go ahead taken from ANTLR generated code
									
					} else {
						throw e;
					}
				}
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
		return states;
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
	
	private static final long _tokenSet_0_data_[] = { 2196877344512L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	private static final long _tokenSet_1_data_[] = { 2196883635976L, 0L };
	public static final BitSet _tokenSet_1 = new BitSet(_tokenSet_1_data_);
	}
	
