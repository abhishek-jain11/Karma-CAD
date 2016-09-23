// $ANTLR 2.7.1: "mappingPathParser.g" -> "MappingPathParser.java"$

	package jsdai.mappingUtils.paths;

	import java.util.*;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;
	import javax.swing.JFrame;

	import jsdai.lang.*;
	import jsdai.SExtended_dictionary_schema.*;
	import jsdai.SMapping_schema.*;
	import jsdai.mappingUtils.MappingData;
	import jsdai.tools.RepositoryChanges;
	import antlr.*;

	import java.io.*;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class MappingPathParser extends antlr.LLkParser
       implements MappingPathParserTokenTypes
 {

//private static int andNum = 0;
	private static final int PATH_ALL = 0;
	private static final int PATH_ATTRIBUTE_ONLY = 1;

    static public SdaiSession sdaiSession;
    static private final String repositoryName = "ExpressCompilerRepo";
    private SdaiRepository expressRepository;
    public SdaiModel mappingModel;
    public SdaiModel armModel;
    public SdaiModel aimModel;
	public HashMap armDeclarations;
	public HashMap aimDeclarations;
	public HashMap attributeMappedValues;
    public Map entityMappings;
	public ConstraintFactory constraintFactory;

//     private SchemaInstance aimSchemaInstance;

	private ESchema_mapping schemaMappingInstance;
	private AUof_mapping uofInstances;
	private AEntity_mapping entityMappingInstances;
    private Map entityExtendedMappings;

	private boolean mappingError;
	private boolean mappedValueUsed;

	protected TokenStreamSelector selector;
	protected LinkedList fileNames;

	protected String infoFileName;
	protected AST infoParserTree;
	protected boolean reportMissing;
    protected Collection missingSchemaMatchers;
	protected String trackChangesFile;
	
	static private TokenStreamSelector createSelector(String fileName) throws FileNotFoundException {
		TokenStreamSelector selector = new TokenStreamSelector();
		MappingPathLexer lexer = new MappingPathLexer(new FileReader(fileName));
		lexer.setFilename(fileName);
		selector.addInputStream(lexer, fileName);
		selector.select(fileName);
		return selector;
	}

	public MappingPathParser(String fileName, String infoFileName, boolean reportMissing, Collection missingSchemaMatchers,
                             String trackChangesFile) throws FileNotFoundException {
		this(createSelector(fileName), fileName);
		this.infoFileName = infoFileName;
		this.infoParserTree = null;
		this.reportMissing = reportMissing;
		this.missingSchemaMatchers = missingSchemaMatchers;
		this.trackChangesFile = trackChangesFile;
		setASTNodeClass("jsdai.mappingUtils.paths.MappingAST");
	}

	private MappingPathParser(TokenStreamSelector selector, String fileName) {
		this(selector);
		setFilename(fileName);
		fileNames = new LinkedList();
		fileNames.add(fileName);
		this.selector = selector;
	}

	public void pushFile(String fileName, boolean relative) throws FileNotFoundException, IOException {
		if(relative) {
			String fileParent = new File(getFilename()).getParent();
			if(fileParent != null) {
				fileName =  fileParent + File.separator + fileName;
			}
		}
		MappingPathLexer lexer = new MappingPathLexer(new FileReader(fileName));
		lexer.setFilename(fileName);
		selector.push(lexer);
		setFilename(fileName);
		fileNames.add(fileName);
	}

	public void popFile() {
		selector.pop();
		fileNames.removeLast();
		setFilename((String)fileNames.getLast());
	}

    static protected ESchema_definition getSchemaDefinition(SdaiModel model) throws SdaiException {
		ASchema_definition schemaDefinitions = 
			(ASchema_definition)model.getInstances(ESchema_definition.class);
		return schemaDefinitions.getByIndex(1);
    }

    static protected void promoteSdaiModelToRO(SdaiModel model) throws SdaiException {
		if(model.getMode() == SdaiModel.NO_ACCESS) {
			model.startReadOnlyAccess();
		}
    }

    static protected void addToAEntity(AEntity aggregate, EEntity value) throws SdaiException {
		aggregate.addByIndex(aggregate.getMemberCount() + 1, value);
    }

    static protected void addEntityMappingToMap(EntityMappingAST entMapAst,
                                                EntityAST armEntity, Map map) {
        ENamed_type armDefinition = armEntity.declaration.definition;
        EntityMappingAST currEntMapAst =
            (EntityMappingAST)map.get(armDefinition);
        if(currEntMapAst != null) {
            while(currEntMapAst.nextEntityMapping != null) {
                currEntMapAst = currEntMapAst.nextEntityMapping;
            }
            currEntMapAst.nextEntityMapping = entMapAst;
        } else {
            map.put(armDefinition, entMapAst);
        }
    }

    /** Finds model with given name
     * @param repository Repository to find model in.
     * @param findModelName model name to find
     * @throws SdaiException Exception MO_NVLD is thrown if model with this name is not found.
     * Other exceptions can be thrown as well.
     * @return Model which was found
     */    
    static public SdaiModel findModel(SdaiRepository repository, String findModelName)
	throws SdaiException {
        SdaiModel model = repository.findSdaiModel(findModelName);
        if(model != null) return model;
        throw new SdaiException(SdaiException.MO_NVLD, repository, 
                                "Model not found: " + findModelName);
    }

    static public SchemaInstance findSchemaInstance(SdaiRepository repository, String findInstanceName)
    throws SdaiException {
        SchemaInstance schemaInstance = repository.findSchemaInstance(findInstanceName);
        if(schemaInstance != null) return schemaInstance;
        throw new SdaiException(SdaiException.SI_NEXS, repository, 
                                "Schema instance not found: " + findInstanceName);
    }

    public void reportError(RecognitionException ex) {
		System.err.println(ex);
		mappingError = true;
    }

	public final void schemaMapping()
	throws RecognitionException, TokenStreamException, SdaiException,FileNotFoundException,IOException {
		sdaiSession = SdaiSession.openSession();
		try {
			schemaMappingInternal();
		} finally {
			if(sdaiSession.testActiveTransaction()) {
				sdaiSession.getActiveTransaction().endTransactionAccessAbort();
			}
			sdaiSession.closeSession();
		}
	}


protected MappingPathParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public MappingPathParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected MappingPathParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public MappingPathParser(TokenStream lexer) {
  this(lexer,2);
}

public MappingPathParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
}

	public final void schemaMappingInternal() throws RecognitionException, TokenStreamException, SdaiException,FileNotFoundException,IOException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInternal_AST = null;
		Token  mappingId = null;
		CommonLowerCaseAST mappingId_AST = null;
		Token  armId = null;
		CommonLowerCaseAST armId_AST = null;
		Token  aimId = null;
		CommonLowerCaseAST aimId_AST = null;
		
			SdaiTransaction transaction = null;
			String mappingModelName = null;
		
		
		try {      // for error handling
			AST tmp1_AST = null;
			tmp1_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_schema_mapping);
			mappingId = LT(1);
			if (inputState.guessing==0) {
				mappingId_AST = new CommonLowerCaseAST(mappingId);
			}
			match(ID);
			if ( inputState.guessing==0 ) {
				mappingId_AST.setType(SCHEMA_MAPPING);
			}
			AST tmp2_AST = null;
			tmp2_AST = (AST)astFactory.create(LT(1));
			match(OPAREN);
			armId = LT(1);
			if (inputState.guessing==0) {
				armId_AST = new CommonLowerCaseAST(armId);
				astFactory.addASTChild(currentAST, armId_AST);
			}
			match(ID);
			AST tmp3_AST = null;
			tmp3_AST = (AST)astFactory.create(LT(1));
			match(COMMA);
			aimId = LT(1);
			if (inputState.guessing==0) {
				aimId_AST = new CommonLowerCaseAST(aimId);
				astFactory.addASTChild(currentAST, aimId_AST);
			}
			match(ID);
			AST tmp4_AST = null;
			tmp4_AST = (AST)astFactory.create(LT(1));
			match(CPAREN);
			AST tmp5_AST = null;
			tmp5_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
					transaction = sdaiSession.startTransactionReadWriteAccess();
							expressRepository = sdaiSession.linkRepository(repositoryName, null);
							expressRepository.openRepository();
							if(trackChangesFile != null) {
								RepositoryChanges.rememberRepositoryState(expressRepository);
							}
				
							mappingModelName = mappingId_AST.getText().toUpperCase() + "_MAPPING_DATA";
							mappingModel = expressRepository.findSdaiModel(mappingModelName);
							if(mappingModel != null) mappingModel.deleteSdaiModel();
							mappingModel = expressRepository.createSdaiModel(mappingModelName, SMapping_schema.class);
							mappingModel.startReadWriteAccess();
							armModel = findModel(expressRepository, 
								armId_AST.getText().toUpperCase() + "_DICTIONARY_DATA");
							promoteSdaiModelToRO(armModel);
							aimModel = findModel(expressRepository, 
								aimId_AST.getText().toUpperCase() + "_DICTIONARY_DATA");
							promoteSdaiModelToRO(aimModel);
							constraintFactory = new ConstraintFactory(this);
							armDeclarations = DictionaryDeclaration.create(armModel);
							aimDeclarations = DictionaryDeclaration.create(aimModel);
				// 			aimSchemaInstance = findSchemaInstance(expressRepository, #aimId.getText());
								
							schemaMappingInstance = 
								(ESchema_mapping)mappingModel.createEntityInstance(ESchema_mapping.class);
							schemaMappingInstance.setId(null, mappingId_AST.getText());
							schemaMappingInstance.setSource(null, getSchemaDefinition(armModel));
							schemaMappingInstance.setTarget(null, getSchemaDefinition(aimModel));
							uofInstances = schemaMappingInstance.createUofs(null);
							attributeMappedValues = new HashMap();
				entityMappings = new LinkedHashMap();
				entityExtendedMappings = new HashMap();
							entityMappingInstances = null;
							if(infoFileName != null) {
								AST saveReturnAST = returnAST;
								returnAST = null;
								pushFile(infoFileName, false);
								schemaMappingInfo();
								infoParserTree = getAST();
								popFile();
								returnAST = saveReturnAST;
				
				// 				antlr.debug.misc.ASTFrame frame = 
				// 					new antlr.debug.misc.ASTFrame("AST JTree", infoParserTree);
				// 				frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
				// 				frame.setVisible(true);
							}
						
			}
			mappings();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp6_AST = null;
			tmp6_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_schema_mapping);
			AST tmp7_AST = null;
			tmp7_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				schemaMappingInternal_AST = (AST)currentAST.root;
					schemaMappingInternal_AST = (AST)astFactory.make( (new ASTArray(2)).add(mappingId_AST).add(schemaMappingInternal_AST));
				for(Iterator i = entityExtendedMappings.entrySet().iterator(); i.hasNext(); ) {
				Map.Entry entry = (Map.Entry)i.next();
				Object armEntity = (Object)entry.getKey();
				EntityMappingAST extEntMapAst = (EntityMappingAST)entry.getValue();
				EntityMappingAST entMapAst = (EntityMappingAST)entityMappings.get(armEntity);
				if(entMapAst == null) {
				reportError(new SemanticException("Base mapping not found for extended " +
																	  "entity mapping " + extEntMapAst.getFirstChild()
																	  .getFirstChild().getText(),
																	  extEntMapAst.getFilename(), extEntMapAst.getLine()));
				} else {
									entMapAst = entMapAst.matchAlternative(this, extEntMapAst);
									entMapAst.extended = extEntMapAst;
								}
				}
				for(Iterator i = entityMappings.values().iterator(); i.hasNext(); ) {
				EntityMappingAST entMapAst = (EntityMappingAST)i.next();
				do {
				try {
				new MappingPathWalker(this).entityMapping(entMapAst);
				} catch(MappingSemanticException e) {
				System.err.println("FATAL ERROR! " +
				"MappingSemanticException should be caught before");
				e.printStackTrace();
				System.exit(1);
				}
				entMapAst = entMapAst.nextEntityMapping;
				} while(entMapAst != null);
				}
							EntityAST.setAttributeMappingDomains(this);
							if(infoParserTree != null) {
								pushFile(infoFileName, false);
								new MappingInfoWalker(this).schemaMappingInfo(infoParserTree);
								popFile();
							}
							transaction.commit();
							if(false) { // Debug output
								//AST tree = getAST();
								antlr.debug.misc.ASTFrame frame = (
									new antlr.debug.misc.ASTFrame("AST JTree", schemaMappingInternal_AST));
								//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								final WindowAdapter windowClosed = new WindowAdapter() {
									public synchronized void windowClosed(WindowEvent e) {
										notify();
									}
								};
								frame.addWindowListener(windowClosed);
								frame.setVisible(true);
								try {
									synchronized(windowClosed) { windowClosed.wait(); }
								} catch (InterruptedException e) { }
							}
							mappingModel.endReadWriteAccess();
							armModel.endReadOnlyAccess();
							aimModel.endReadOnlyAccess();
							if(trackChangesFile != null) {
								RepositoryChanges.trackRepositoryChanges(expressRepository, trackChangesFile);
							}
							expressRepository.closeRepository();
							transaction.endTransactionAccessCommit();
							if(reportMissing) {
								new MappingData(sdaiSession).findMissingMappingsOperation(mappingModelName, missingSchemaMatchers);
							}
						
				currentAST.root = schemaMappingInternal_AST;
				currentAST.child = schemaMappingInternal_AST!=null &&schemaMappingInternal_AST.getFirstChild()!=null ?
					schemaMappingInternal_AST.getFirstChild() : schemaMappingInternal_AST;
				currentAST.advanceChildToEnd();
			}
			schemaMappingInternal_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInternal_AST;
	}
	
	public final void mappings() throws RecognitionException, TokenStreamException, SdaiException,FileNotFoundException,IOException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST mappings_AST = null;
		
		try {      // for error handling
			{
			_loop5:
			do {
				switch ( LA(1)) {
				case LITERAL_include:
				{
					includeMapping();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case LITERAL_uof_mapping:
				{
					uofMapping();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case LITERAL_entity_mapping:
				{
					entityMapping();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				default:
				{
					break _loop5;
				}
				}
			} while (true);
			}
			mappings_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = mappings_AST;
	}
	
	public final void includeMapping() throws RecognitionException, TokenStreamException, SdaiException,FileNotFoundException,IOException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST includeMapping_AST = null;
		Token  fileName = null;
		AST fileName_AST = null;
		
		try {      // for error handling
			AST tmp8_AST = null;
			if (inputState.guessing==0) {
				tmp8_AST = (AST)astFactory.create(LT(1));
			}
			match(LITERAL_include);
			fileName = LT(1);
			if (inputState.guessing==0) {
				fileName_AST = (AST)astFactory.create(fileName);
			}
			match(STRING);
			AST tmp9_AST = null;
			if (inputState.guessing==0) {
				tmp9_AST = (AST)astFactory.create(LT(1));
			}
			match(SEP);
			if ( inputState.guessing==0 ) {
				pushFile(fileName_AST.getText(), true);
				
			}
			mappings();
			AST tmp10_AST = null;
			if (inputState.guessing==0) {
				tmp10_AST = (AST)astFactory.create(LT(1));
			}
			match(Token.EOF_TYPE);
			if ( inputState.guessing==0 ) {
				includeMapping_AST = (AST)currentAST.root;
				includeMapping_AST = getAST();
							popFile();
						
				currentAST.root = includeMapping_AST;
				currentAST.child = includeMapping_AST!=null &&includeMapping_AST.getFirstChild()!=null ?
					includeMapping_AST.getFirstChild() : includeMapping_AST;
				currentAST.advanceChildToEnd();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		returnAST = includeMapping_AST;
	}
	
	public final void uofMapping() throws RecognitionException, TokenStreamException, SdaiException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST uofMapping_AST = null;
		Token  id = null;
		CommonLowerCaseAST id_AST = null;
		Token  uofId = null;
		CommonLowerCaseAST uofId_AST = null;
		
		try {      // for error handling
			AST tmp11_AST = null;
			tmp11_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_uof_mapping);
			id = LT(1);
			if (inputState.guessing==0) {
				id_AST = new CommonLowerCaseAST(id);
			}
			match(ID);
			if ( inputState.guessing==0 ) {
				id_AST.setType(UOF_MAPPING);
			}
			AST tmp12_AST = null;
			tmp12_AST = (AST)astFactory.create(LT(1));
			match(OPAREN);
			uofId = LT(1);
			if (inputState.guessing==0) {
				uofId_AST = new CommonLowerCaseAST(uofId);
				astFactory.addASTChild(currentAST, uofId_AST);
			}
			match(ID);
			AST tmp13_AST = null;
			tmp13_AST = (AST)astFactory.create(LT(1));
			match(CPAREN);
			AST tmp14_AST = null;
			tmp14_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				
							EUof_mapping uofMappingInstance = 
								(EUof_mapping)mappingModel.createEntityInstance(EUof_mapping.class);
							uofMappingInstance.setName(null, uofId_AST.getText());
							entityMappingInstances = uofMappingInstance.createMappings(null);
							uofInstances.addUnordered(uofMappingInstance);
						
			}
			{
			_loop8:
			do {
				if ((LA(1)==LITERAL_entity_mapping)) {
					entityMapping();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop8;
				}
				
			} while (true);
			}
			AST tmp15_AST = null;
			tmp15_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_uof_mapping);
			AST tmp16_AST = null;
			tmp16_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				uofMapping_AST = (AST)currentAST.root;
					uofMapping_AST = (AST)astFactory.make( (new ASTArray(2)).add(id_AST).add(uofMapping_AST));
							entityMappingInstances = null;
						
				currentAST.root = uofMapping_AST;
				currentAST.child = uofMapping_AST!=null &&uofMapping_AST.getFirstChild()!=null ?
					uofMapping_AST.getFirstChild() : uofMapping_AST;
				currentAST.advanceChildToEnd();
			}
			uofMapping_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		returnAST = uofMapping_AST;
	}
	
	public final void entityMapping() throws RecognitionException, TokenStreamException, SdaiException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entityMapping_AST = null;
		Token  id = null;
		EntityMappingAST id_AST = null;
		AST e_AST = null;
		Token  flag = null;
		AST flag_AST = null;
		
			int entityMappingLine = LT(1).getLine();
		EntityAST armEntity = null;
			mappingError = false;
		
		
		try {      // for error handling
			AST tmp17_AST = null;
			tmp17_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_entity_mapping);
			id = LT(1);
			if (inputState.guessing==0) {
				id_AST = new EntityMappingAST(id);
			}
			match(ID);
			AST tmp18_AST = null;
			tmp18_AST = (AST)astFactory.create(LT(1));
			match(OPAREN);
			armId();
			if (inputState.guessing==0) {
				e_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp19_AST = null;
			tmp19_AST = (AST)astFactory.create(LT(1));
			match(COMMA);
			if ( inputState.guessing==0 ) {
				armEntity = (EntityAST)e_AST.getFirstChild();
			}
			aimEntity();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp20_AST = null;
			tmp20_AST = (AST)astFactory.create(LT(1));
			match(CPAREN);
			{
			switch ( LA(1)) {
			case ID:
			{
				flag = LT(1);
				if (inputState.guessing==0) {
					flag_AST = (AST)astFactory.create(flag);
				}
				match(ID);
				break;
			}
			case SEP:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp21_AST = null;
			tmp21_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			{
			switch ( LA(1)) {
			case LITERAL_mapping_constraints:
			{
				entityMappingConstraints();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case LITERAL_end_entity_mapping:
			case LITERAL_attribute_mapping:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			_loop13:
			do {
				if ((LA(1)==LITERAL_attribute_mapping)) {
					attributeMapping(armEntity);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					break _loop13;
				}
				
			} while (true);
			}
			AST tmp22_AST = null;
			tmp22_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_entity_mapping);
			AST tmp23_AST = null;
			tmp23_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				entityMapping_AST = (AST)currentAST.root;
					entityMapping_AST = (AST)astFactory.make( (new ASTArray(3)).add(id_AST).add(entityMapping_AST).add(flag_AST));
				EntityMappingAST entMapAst = (EntityMappingAST)(entityMapping_AST/*#id*/);
				entMapAst.setType(ENTITY_MAPPING);
				entMapAst.setFilename(getFilename());
				entMapAst.setLine(entityMappingLine);
				entMapAst.setUofInstances(entityMappingInstances);
							if(!mappingError) {
				if(flag_AST != null) {
				if(flag_AST.getText().equals("extended")) {
				addEntityMappingToMap(entMapAst, armEntity, entityExtendedMappings);
				} else {
				throw new SemanticException("Unsupported entity mapping flag: " + flag_AST.getText(), 
				getFilename(), id.getLine());
				}
				} else {
				addEntityMappingToMap(entMapAst, armEntity, entityMappings);
				}
							}
						
				currentAST.root = entityMapping_AST;
				currentAST.child = entityMapping_AST!=null &&entityMapping_AST.getFirstChild()!=null ?
					entityMapping_AST.getFirstChild() : entityMapping_AST;
				currentAST.advanceChildToEnd();
			}
			entityMapping_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = entityMapping_AST;
	}
	
	public final void armId() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST armId_AST = null;
		Token  id = null;
		EntityAST id_AST = null;
		
		try {      // for error handling
			id = LT(1);
			if (inputState.guessing==0) {
				id_AST = new EntityAST(id);
				astFactory.addASTChild(currentAST, id_AST);
			}
			match(ID);
			if ( inputState.guessing==0 ) {
				id_AST.setIdentifier(this, id, false);
			}
			if ( inputState.guessing==0 ) {
				armId_AST = (AST)currentAST.root;
				armId_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(ARM,"ARM")).add(armId_AST));
				currentAST.root = armId_AST;
				currentAST.child = armId_AST!=null &&armId_AST.getFirstChild()!=null ?
					armId_AST.getFirstChild() : armId_AST;
				currentAST.advanceChildToEnd();
			}
			armId_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		returnAST = armId_AST;
	}
	
	public final void aimEntity() throws RecognitionException, TokenStreamException, SdaiException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aimEntity_AST = null;
		
		try {      // for error handling
			aimEntityElem();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			if ( inputState.guessing==0 ) {
				aimEntity_AST = (AST)currentAST.root;
				aimEntity_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(AIM,"AIM")).add(aimEntity_AST));
				currentAST.root = aimEntity_AST;
				currentAST.child = aimEntity_AST!=null &&aimEntity_AST.getFirstChild()!=null ?
					aimEntity_AST.getFirstChild() : aimEntity_AST;
				currentAST.advanceChildToEnd();
			}
			aimEntity_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		returnAST = aimEntity_AST;
	}
	
	public final void entityMappingConstraints() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entityMappingConstraints_AST = null;
		
			AST last;
		
		
		try {      // for error handling
			AST tmp24_AST = null;
			tmp24_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_mapping_constraints);
			AST tmp25_AST = null;
			tmp25_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			{
			switch ( LA(1)) {
			case EXTENDED_INTO:
			{
				MappingAST tmp26_AST = null;
				tmp26_AST = new MappingAST(LT(1));
				match(EXTENDED_INTO);
				break;
			}
			case ID:
			case OPAREN:
			case LITERAL_end_mapping_constraints:
			case VBAR:
			case OSQBRAC:
			case SLASH:
			case OANBRAC:
			case EXCLAM:
			case OBRACE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case ID:
			case OPAREN:
			case VBAR:
			case OSQBRAC:
			case SLASH:
			case OANBRAC:
			case EXCLAM:
			case OBRACE:
			{
				last=pathElement(PATH_ALL);
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case LITERAL_end_mapping_constraints:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp27_AST = null;
			tmp27_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_mapping_constraints);
			AST tmp28_AST = null;
			tmp28_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			entityMappingConstraints_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		returnAST = entityMappingConstraints_AST;
	}
	
	public final void attributeMapping(
		EntityAST entity
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST attributeMapping_AST = null;
		Token  id = null;
		CommonLowerCaseAST id_AST = null;
		
			AST last = null;
		
		
		try {      // for error handling
			AST tmp29_AST = null;
			tmp29_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_attribute_mapping);
			id = LT(1);
			if (inputState.guessing==0) {
				id_AST = new CommonLowerCaseAST(id);
			}
			match(ID);
			if ( inputState.guessing==0 ) {
				id_AST.setType(ATTRIBUTE_MAPPING);
			}
			AST tmp30_AST = null;
			tmp30_AST = (AST)astFactory.create(LT(1));
			match(OPAREN);
			armAttribute(entity);
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			switch ( LA(1)) {
			case COMMA:
			{
				AST tmp31_AST = null;
				tmp31_AST = (AST)astFactory.create(LT(1));
				match(COMMA);
				{
				switch ( LA(1)) {
				case ID:
				case OPAREN:
				case VBAR:
				case OSQBRAC:
				case SLASH:
				case OANBRAC:
				case MACRO:
				{
					aimAttribute();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case COMMA:
				case CPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case COMMA:
				{
					AST tmp32_AST = null;
					tmp32_AST = (AST)astFactory.create(LT(1));
					match(COMMA);
					armId();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case CPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case CPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp33_AST = null;
			tmp33_AST = (AST)astFactory.create(LT(1));
			match(CPAREN);
			AST tmp34_AST = null;
			tmp34_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				mappedValueUsed = false;
			}
			{
			switch ( LA(1)) {
			case EXTENDED_INTO:
			{
				MappingAST tmp35_AST = null;
				tmp35_AST = new MappingAST(LT(1));
				match(EXTENDED_INTO);
				break;
			}
			case ID:
			case OPAREN:
			case LITERAL_end_attribute_mapping:
			case VBAR:
			case OSQBRAC:
			case SLASH:
			case OANBRAC:
			case EXCLAM:
			case OBRACE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case ID:
			case OPAREN:
			case VBAR:
			case OSQBRAC:
			case SLASH:
			case OANBRAC:
			case EXCLAM:
			case OBRACE:
			{
				last=pathElement(PATH_ALL);
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case LITERAL_end_attribute_mapping:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp36_AST = null;
			tmp36_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_attribute_mapping);
			AST tmp37_AST = null;
			tmp37_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				attributeMapping_AST = (AST)currentAST.root;
					attributeMapping_AST = (mappedValueUsed ? 
								(AST)astFactory.make( (new ASTArray(3)).add(id_AST).add((AST)astFactory.create(ENUM,"mappedValueUsed")).add(attributeMapping_AST)) :
								(AST)astFactory.make( (new ASTArray(2)).add(id_AST).add(attributeMapping_AST))
							);
				if(last != null) {
				attributeMapping_AST.addChild((AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(LAST,"LAST")).add(last)));
				}
						
				currentAST.root = attributeMapping_AST;
				currentAST.child = attributeMapping_AST!=null &&attributeMapping_AST.getFirstChild()!=null ?
					attributeMapping_AST.getFirstChild() : attributeMapping_AST;
				currentAST.advanceChildToEnd();
			}
			attributeMapping_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		returnAST = attributeMapping_AST;
	}
	
	public final AST  pathElement(
		int nextType
	) throws RecognitionException, TokenStreamException {
		AST last = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST pathElement_AST = null;
		AST t_AST = null;
		
		try {      // for error handling
			{
			if (((LA(1)==EXCLAM||LA(1)==OBRACE))&&(nextType == PATH_ALL)) {
				last=constraintConstruct();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
			}
			else if ((_tokenSet_7.member(LA(1)))) {
				{
				switch ( LA(1)) {
				case OPAREN:
				case OANBRAC:
				{
					last=orConstruct(nextType);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case OSQBRAC:
				{
					last=andConstruct();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case SLASH:
				{
					templateCall();
					if (inputState.guessing==0) {
						t_AST = (AST)returnAST;
						astFactory.addASTChild(currentAST, returnAST);
					}
					if ( inputState.guessing==0 ) {
						last = t_AST;
					}
					break;
				}
				default:
					if ((LA(1)==ID||LA(1)==VBAR) && (_tokenSet_8.member(LA(2)))) {
						last=entityConstruct();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
					}
					else if ((LA(1)==ID) && (LA(2)==DOT)) {
						last=attributeConstruct();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
					}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			pathElement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = pathElement_AST;
		return last;
	}
	
	public final void armAttribute(
		EntityAST entity
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST armAttribute_AST = null;
		Token  id = null;
		AttributeAST id_AST = null;
		
		try {      // for error handling
			id = LT(1);
			if (inputState.guessing==0) {
				id_AST = new AttributeAST(id);
				astFactory.addASTChild(currentAST, id_AST);
			}
			match(ID);
			if ( inputState.guessing==0 ) {
				id_AST.setIdentifiers(this, entity, id, false);
			}
			if ( inputState.guessing==0 ) {
				armAttribute_AST = (AST)currentAST.root;
				armAttribute_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(ARM_ATTRIBUTE,"ARM_ATTRIBUTE")).add(armAttribute_AST));
				currentAST.root = armAttribute_AST;
				currentAST.child = armAttribute_AST!=null &&armAttribute_AST.getFirstChild()!=null ?
					armAttribute_AST.getFirstChild() : armAttribute_AST;
				currentAST.advanceChildToEnd();
			}
			armAttribute_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		returnAST = armAttribute_AST;
	}
	
	public final void aimAttribute() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aimAttribute_AST = null;
		
		try {      // for error handling
			aimAttributeElem();
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			if ( inputState.guessing==0 ) {
				aimAttribute_AST = (AST)currentAST.root;
				aimAttribute_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(AIM_ATTRIBUTE,"AIM_ATTRIBUTE")).add(aimAttribute_AST));
				currentAST.root = aimAttribute_AST;
				currentAST.child = aimAttribute_AST!=null &&aimAttribute_AST.getFirstChild()!=null ?
					aimAttribute_AST.getFirstChild() : aimAttribute_AST;
				currentAST.advanceChildToEnd();
			}
			aimAttribute_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		returnAST = aimAttribute_AST;
	}
	
	public final void entity() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entity_AST = null;
		Token  id1 = null;
		EntityAST id1_AST = null;
		Token  id2 = null;
		EntityAST id2_AST = null;
			boolean aggregate = false;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case VBAR:
			{
				AST tmp38_AST = null;
				tmp38_AST = (AST)astFactory.create(LT(1));
				match(VBAR);
				id1 = LT(1);
				if (inputState.guessing==0) {
					id1_AST = new EntityAST(id1);
					astFactory.addASTChild(currentAST, id1_AST);
				}
				match(ID);
				AST tmp39_AST = null;
				tmp39_AST = (AST)astFactory.create(LT(1));
				match(VBAR);
				if ( inputState.guessing==0 ) {
						id1_AST.setIdentifier(this, id1, true);
									id1_AST.exactType = true;
						  		
				}
				break;
			}
			case ID:
			{
				id2 = LT(1);
				if (inputState.guessing==0) {
					id2_AST = new EntityAST(id2);
					astFactory.addASTChild(currentAST, id2_AST);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
						id2_AST.setIdentifier(this, id2, true);
						  		
				}
				{
				boolean synPredMatched28 = false;
				if ((((LA(1)==OSQBRAC) && (LA(2)==INT||LA(2)==LITERAL_i))&&( id2_AST == null || ((EntityAST)id2_AST).declaration.type == DictionaryDeclaration.TYPE ))) {
					int _m28 = mark();
					synPredMatched28 = true;
					inputState.guessing++;
					try {
						{
						match(OSQBRAC);
						{
						switch ( LA(1)) {
						case LITERAL_i:
						{
							match(LITERAL_i);
							break;
						}
						case INT:
						{
							match(INT);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						match(CSQBRAC);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched28 = false;
					}
					rewind(_m28);
					inputState.guessing--;
				}
				if ( synPredMatched28 ) {
					AST tmp40_AST = null;
					tmp40_AST = (AST)astFactory.create(LT(1));
					match(OSQBRAC);
					{
					switch ( LA(1)) {
					case LITERAL_i:
					{
						AST tmp41_AST = null;
						if (inputState.guessing==0) {
							tmp41_AST = (AST)astFactory.create(LT(1));
							astFactory.addASTChild(currentAST, tmp41_AST);
						}
						match(LITERAL_i);
						break;
					}
					case INT:
					{
						MappingAST tmp42_AST = null;
						if (inputState.guessing==0) {
							tmp42_AST = new MappingAST(LT(1));
							astFactory.addASTChild(currentAST, tmp42_AST);
						}
						match(INT);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					AST tmp43_AST = null;
					tmp43_AST = (AST)astFactory.create(LT(1));
					match(CSQBRAC);
					if ( inputState.guessing==0 ) {
						aggregate = true;
					}
				}
				else if ((_tokenSet_10.member(LA(1))) && (_tokenSet_11.member(LA(2)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				entity_AST = (AST)currentAST.root;
					entity_AST = (aggregate ? 
								(AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(ENTITY_AGGREG,"ENTITY_AGGREG")).add(entity_AST)) : 
								(AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(ENTITY,"ENTITY")).add(entity_AST)));
						
				currentAST.root = entity_AST;
				currentAST.child = entity_AST!=null &&entity_AST.getFirstChild()!=null ?
					entity_AST.getFirstChild() : entity_AST;
				currentAST.advanceChildToEnd();
			}
			entity_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		returnAST = entity_AST;
	}
	
	public final void attribute() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST attribute_AST = null;
		Token  id1 = null;
		AttributeAST id1_AST = null;
		Token  id2 = null;
		AST id2_AST = null;
		
		try {      // for error handling
			id1 = LT(1);
			if (inputState.guessing==0) {
				id1_AST = new AttributeAST(id1);
				astFactory.addASTChild(currentAST, id1_AST);
			}
			match(ID);
			AST tmp44_AST = null;
			tmp44_AST = (AST)astFactory.create(LT(1));
			match(DOT);
			id2 = LT(1);
			if (inputState.guessing==0) {
				id2_AST = (AST)astFactory.create(id2);
			}
			match(ID);
			if ( inputState.guessing==0 ) {
				id1_AST.setIdentifiers(this, id1, id2, true);
			}
			{
			boolean synPredMatched34 = false;
			if (((LA(1)==OSQBRAC) && (LA(2)==INT||LA(2)==LITERAL_i))) {
				int _m34 = mark();
				synPredMatched34 = true;
				inputState.guessing++;
				try {
					{
					match(OSQBRAC);
					{
					switch ( LA(1)) {
					case LITERAL_i:
					{
						match(LITERAL_i);
						break;
					}
					case INT:
					{
						match(INT);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(CSQBRAC);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched34 = false;
				}
				rewind(_m34);
				inputState.guessing--;
			}
			if ( synPredMatched34 ) {
				AST tmp45_AST = null;
				tmp45_AST = (AST)astFactory.create(LT(1));
				match(OSQBRAC);
				{
				switch ( LA(1)) {
				case LITERAL_i:
				{
					AST tmp46_AST = null;
					if (inputState.guessing==0) {
						tmp46_AST = (AST)astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp46_AST);
					}
					match(LITERAL_i);
					break;
				}
				case INT:
				{
					MappingAST tmp47_AST = null;
					if (inputState.guessing==0) {
						tmp47_AST = new MappingAST(LT(1));
						astFactory.addASTChild(currentAST, tmp47_AST);
					}
					match(INT);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp48_AST = null;
				tmp48_AST = (AST)astFactory.create(LT(1));
				match(CSQBRAC);
				if ( inputState.guessing==0 ) {
					attribute_AST = (AST)currentAST.root;
					attribute_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(ATTRIBUTE_AGGREG,"ATTRIBUTE_AGGREG")).add(attribute_AST));
					currentAST.root = attribute_AST;
					currentAST.child = attribute_AST!=null &&attribute_AST.getFirstChild()!=null ?
						attribute_AST.getFirstChild() : attribute_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_12.member(LA(1))) && (_tokenSet_11.member(LA(2)))) {
				if ( inputState.guessing==0 ) {
					attribute_AST = (AST)currentAST.root;
					attribute_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(ATTRIBUTE,"ATTRIBUTE")).add(attribute_AST));
					currentAST.root = attribute_AST;
					currentAST.child = attribute_AST!=null &&attribute_AST.getFirstChild()!=null ?
						attribute_AST.getFirstChild() : attribute_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			attribute_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = attribute_AST;
	}
	
	public final void value() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST value_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case STRING:
			{
				AST tmp49_AST = null;
				if (inputState.guessing==0) {
					tmp49_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp49_AST);
				}
				match(STRING);
				value_AST = (AST)currentAST.root;
				break;
			}
			case INT:
			{
				MappingAST tmp50_AST = null;
				if (inputState.guessing==0) {
					tmp50_AST = new MappingAST(LT(1));
					astFactory.addASTChild(currentAST, tmp50_AST);
				}
				match(INT);
				value_AST = (AST)currentAST.root;
				break;
			}
			case FLOAT:
			{
				MappingAST tmp51_AST = null;
				if (inputState.guessing==0) {
					tmp51_AST = new MappingAST(LT(1));
					astFactory.addASTChild(currentAST, tmp51_AST);
				}
				match(FLOAT);
				value_AST = (AST)currentAST.root;
				break;
			}
			case ENUM:
			{
				MappingAST tmp52_AST = null;
				if (inputState.guessing==0) {
					tmp52_AST = new MappingAST(LT(1));
					astFactory.addASTChild(currentAST, tmp52_AST);
				}
				match(ENUM);
				value_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_13);
			} else {
			  throw ex;
			}
		}
		returnAST = value_AST;
	}
	
	public final void templateCall() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST templateCall_AST = null;
		Token  name = null;
		TemplateCallAST name_AST = null;
		Token  id = null;
		EntityAST id_AST = null;
		
		try {      // for error handling
			AST tmp53_AST = null;
			tmp53_AST = (AST)astFactory.create(LT(1));
			match(SLASH);
			name = LT(1);
			if (inputState.guessing==0) {
				name_AST = new TemplateCallAST(name);
				astFactory.makeASTRoot(currentAST, name_AST);
			}
			match(ID);
			if ( inputState.guessing==0 ) {
				name_AST.setType(TEMPLATE_CALL);
			}
			{
			if (((LA(1)==OPAREN) && (LA(2)==ID))&&( name_AST.getName().equals("SUBTYPE") || name_AST.getName().equals("SUPERTYPE") )) {
				AST tmp54_AST = null;
				tmp54_AST = (AST)astFactory.create(LT(1));
				match(OPAREN);
				id = LT(1);
				if (inputState.guessing==0) {
					id_AST = new EntityAST(id);
					astFactory.addASTChild(currentAST, id_AST);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
					id_AST.setIdentifier(this, id, false);
				}
				AST tmp55_AST = null;
				tmp55_AST = (AST)astFactory.create(LT(1));
				match(CPAREN);
			}
			else if ((LA(1)==OPAREN||LA(1)==SLASH) && (_tokenSet_14.member(LA(2)))) {
				{
				switch ( LA(1)) {
				case OPAREN:
				{
					AST tmp56_AST = null;
					tmp56_AST = (AST)astFactory.create(LT(1));
					match(OPAREN);
					templateParam();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					{
					_loop41:
					do {
						if ((LA(1)==COMMA)) {
							AST tmp57_AST = null;
							tmp57_AST = (AST)astFactory.create(LT(1));
							match(COMMA);
							templateParam();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
						}
						else {
							break _loop41;
						}
						
					} while (true);
					}
					AST tmp58_AST = null;
					tmp58_AST = (AST)astFactory.create(LT(1));
					match(CPAREN);
					break;
				}
				case SLASH:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			AST tmp59_AST = null;
			tmp59_AST = (AST)astFactory.create(LT(1));
			match(SLASH);
			templateCall_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = templateCall_AST;
	}
	
	public final void templateParam() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST templateParam_AST = null;
		
		try {      // for error handling
			if ((LA(1)==ID||LA(1)==VBAR) && (_tokenSet_16.member(LA(2)))) {
				entity();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				templateParam_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==ID) && (LA(2)==DOT)) {
				attribute();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				templateParam_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_17.member(LA(1)))) {
				value();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				templateParam_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		returnAST = templateParam_AST;
	}
	
	public final AST  constraintConstruct() throws RecognitionException, TokenStreamException {
		AST last = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraintConstruct_AST = null;
		Token  exclam = null;
		AST exclam_AST = null;
		AST next_AST = null;
		
			AST inner;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case EXCLAM:
			{
				exclam = LT(1);
				if (inputState.guessing==0) {
					exclam_AST = (AST)astFactory.create(exclam);
				}
				match(EXCLAM);
				break;
			}
			case OBRACE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp60_AST = null;
			tmp60_AST = (AST)astFactory.create(LT(1));
			match(OBRACE);
			inner=pathElement(PATH_ALL);
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp61_AST = null;
			tmp61_AST = (AST)astFactory.create(LT(1));
			match(CBRACE);
			if ( inputState.guessing==0 ) {
				constraintConstruct_AST = (AST)currentAST.root;
				constraintConstruct_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(CONSTRAINT,"CONSTRAINT")).add(constraintConstruct_AST));
				currentAST.root = constraintConstruct_AST;
				currentAST.child = constraintConstruct_AST!=null &&constraintConstruct_AST.getFirstChild()!=null ?
					constraintConstruct_AST.getFirstChild() : constraintConstruct_AST;
				currentAST.advanceChildToEnd();
			}
			{
			switch ( LA(1)) {
			case ID:
			case OPAREN:
			case VBAR:
			case OSQBRAC:
			case SLASH:
			case OANBRAC:
			case EXCLAM:
			case OBRACE:
			{
				last=pathElement(PATH_ALL);
				if (inputState.guessing==0) {
					next_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					constraintConstruct_AST = (AST)currentAST.root;
					if(constraintConstruct_AST != null) constraintConstruct_AST.addChild(next_AST);
				}
				break;
			}
			case ENUM:
			case CPAREN:
			case LITERAL_end_mapping_constraints:
			case LITERAL_end_attribute_mapping:
			case CSQBRAC:
			case CANBRAC:
			case CBRACE:
			case LITERAL_end_derived_variant:
			{
				if ( inputState.guessing==0 ) {
					constraintConstruct_AST = (AST)currentAST.root;
					last = constraintConstruct_AST;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				constraintConstruct_AST = (AST)currentAST.root;
					if(exclam != null) {
								exclam_AST.setType(NEGATION);
								constraintConstruct_AST = (AST)astFactory.make( (new ASTArray(2)).add(exclam_AST).add(constraintConstruct_AST));
							}
						
				currentAST.root = constraintConstruct_AST;
				currentAST.child = constraintConstruct_AST!=null &&constraintConstruct_AST.getFirstChild()!=null ?
					constraintConstruct_AST.getFirstChild() : constraintConstruct_AST;
				currentAST.advanceChildToEnd();
			}
			constraintConstruct_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = constraintConstruct_AST;
		return last;
	}
	
	public final AST  entityConstruct() throws RecognitionException, TokenStreamException {
		AST last = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entityConstruct_AST = null;
		AST entity_AST = null;
		AST end_AST = null;
		AST next_AST = null;
		
		try {      // for error handling
			entity();
			if (inputState.guessing==0) {
				entity_AST = (AST)returnAST;
			}
			if ( inputState.guessing==0 ) {
				last = entity_AST;
			}
			{
			boolean synPredMatched51 = false;
			if (((_tokenSet_18.member(LA(1))) && (_tokenSet_19.member(LA(2))))) {
				int _m51 = mark();
				synPredMatched51 = true;
				inputState.guessing++;
				try {
					{
					{
					switch ( LA(1)) {
					case EXCLAM:
					case OBRACE:
					{
						constraintSingle();
						break;
					}
					case SUB:
					case SUPER:
					case TOPOINT:
					case FROMPOINT:
					case EXTENDED_INTO:
					case EXTENSION_OF:
					case EQ:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case FROMPOINT:
					{
						match(FROMPOINT);
						break;
					}
					case TOPOINT:
					{
						match(TOPOINT);
						break;
					}
					case SUPER:
					{
						match(SUPER);
						break;
					}
					case SUB:
					{
						match(SUB);
						break;
					}
					case EQ:
					{
						match(EQ);
						break;
					}
					case EXTENDED_INTO:
					{
						match(EXTENDED_INTO);
						break;
					}
					case EXTENSION_OF:
					{
						match(EXTENSION_OF);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched51 = false;
				}
				rewind(_m51);
				inputState.guessing--;
			}
			if ( synPredMatched51 ) {
				last=entityConstructEnd(entity_AST);
				if (inputState.guessing==0) {
					end_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					entityConstruct_AST = (AST)currentAST.root;
					entityConstruct_AST = end_AST;
					currentAST.root = entityConstruct_AST;
					currentAST.child = entityConstruct_AST!=null &&entityConstruct_AST.getFirstChild()!=null ?
						entityConstruct_AST.getFirstChild() : entityConstruct_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_20.member(LA(1))) && (_tokenSet_21.member(LA(2)))) {
				last=pathElement(PATH_ALL);
				if (inputState.guessing==0) {
					next_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					entityConstruct_AST = (AST)currentAST.root;
						if(entity_AST != null) {
										entityConstruct_AST = entity_AST;
										entityConstruct_AST.addChild(next_AST);
									}
								
					currentAST.root = entityConstruct_AST;
					currentAST.child = entityConstruct_AST!=null &&entityConstruct_AST.getFirstChild()!=null ?
						entityConstruct_AST.getFirstChild() : entityConstruct_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_9.member(LA(1)))) {
				if ( inputState.guessing==0 ) {
					entityConstruct_AST = (AST)currentAST.root;
					entityConstruct_AST = entity_AST;
					currentAST.root = entityConstruct_AST;
					currentAST.child = entityConstruct_AST!=null &&entityConstruct_AST.getFirstChild()!=null ?
						entityConstruct_AST.getFirstChild() : entityConstruct_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = entityConstruct_AST;
		return last;
	}
	
	public final AST  attributeConstruct() throws RecognitionException, TokenStreamException {
		AST last = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST attributeConstruct_AST = null;
		AST attrib_AST = null;
		Token  eq = null;
		AST eq_AST = null;
		Token  neq = null;
		AST neq_AST = null;
		AST or_AST = null;
		AST next_AST = null;
		
		try {      // for error handling
			attribute();
			if (inputState.guessing==0) {
				attrib_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			if ( inputState.guessing==0 ) {
				attributeConstruct_AST = (AST)currentAST.root;
				last = attributeConstruct_AST;
			}
			{
			switch ( LA(1)) {
			case EQ:
			case NEQ:
			{
				{
				switch ( LA(1)) {
				case EQ:
				{
					eq = LT(1);
					if (inputState.guessing==0) {
						eq_AST = (AST)astFactory.create(eq);
						astFactory.makeASTRoot(currentAST, eq_AST);
					}
					match(EQ);
					if ( inputState.guessing==0 ) {
						eq_AST.setType(EQ_ATTRIBUTE);
					}
					break;
				}
				case NEQ:
				{
					neq = LT(1);
					if (inputState.guessing==0) {
						neq_AST = (AST)astFactory.create(neq);
						astFactory.makeASTRoot(currentAST, neq_AST);
					}
					match(NEQ);
					if ( inputState.guessing==0 ) {
						neq_AST.setType(EQ_ATTRIBUTE);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case INT:
				case FLOAT:
				case ENUM:
				case STRING:
				{
					value();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case OPAREN:
				{
					orValue(eq_AST, attrib_AST);
					if (inputState.guessing==0) {
						or_AST = (AST)returnAST;
					}
					if ( inputState.guessing==0 ) {
						attributeConstruct_AST = (AST)currentAST.root;
						attributeConstruct_AST = or_AST;
						currentAST.root = attributeConstruct_AST;
						currentAST.child = attributeConstruct_AST!=null &&attributeConstruct_AST.getFirstChild()!=null ?
							attributeConstruct_AST.getFirstChild() : attributeConstruct_AST;
						currentAST.advanceChildToEnd();
					}
					break;
				}
				case ID:
				{
					attribute();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					attributeConstruct_AST = (AST)currentAST.root;
						last = null;
									if(neq_AST != null) {
										MappingAST negationAst = new MappingAST(neq);
										negationAst.setType(ATTRIBUTE_NEGATION);
										attributeConstruct_AST = (AST)astFactory.make( (new ASTArray(2)).add(negationAst).add(attributeConstruct_AST));
									}
								
					currentAST.root = attributeConstruct_AST;
					currentAST.child = attributeConstruct_AST!=null &&attributeConstruct_AST.getFirstChild()!=null ?
						attributeConstruct_AST.getFirstChild() : attributeConstruct_AST;
					currentAST.advanceChildToEnd();
				}
				break;
			}
			case TOPOINT:
			{
				MappingAST tmp62_AST = null;
				if (inputState.guessing==0) {
					tmp62_AST = new MappingAST(LT(1));
					astFactory.makeASTRoot(currentAST, tmp62_AST);
				}
				match(TOPOINT);
				{
				switch ( LA(1)) {
				case ENUM:
				case CPAREN:
				case LITERAL_end_mapping_constraints:
				case LITERAL_end_attribute_mapping:
				case CSQBRAC:
				case CANBRAC:
				case CBRACE:
				case LITERAL_end_derived_variant:
				{
					if ( inputState.guessing==0 ) {
						attributeConstruct_AST = (AST)currentAST.root;
						last = attributeConstruct_AST;
					}
					break;
				}
				case ID:
				case OPAREN:
				case VBAR:
				case OSQBRAC:
				case SLASH:
				case OANBRAC:
				case EXCLAM:
				case OBRACE:
				{
					last=pathElement(PATH_ALL);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case ID:
			case OPAREN:
			case VBAR:
			case OSQBRAC:
			case SLASH:
			case OANBRAC:
			case EXCLAM:
			case OBRACE:
			{
				last=pathElement(PATH_ALL);
				if (inputState.guessing==0) {
					next_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					attributeConstruct_AST = (AST)currentAST.root;
					if(attributeConstruct_AST != null) attributeConstruct_AST.addChild(next_AST);
				}
				break;
			}
			case ENUM:
			case CPAREN:
			case LITERAL_end_mapping_constraints:
			case LITERAL_end_attribute_mapping:
			case CSQBRAC:
			case CANBRAC:
			case CBRACE:
			case LITERAL_end_derived_variant:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			attributeConstruct_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = attributeConstruct_AST;
		return last;
	}
	
	public final AST  orConstruct(
		int nextType
	) throws RecognitionException, TokenStreamException {
		AST last = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orConstruct_AST = null;
		AST or_AST = null;
		AST next_AST = null;
		
			LinkedList orLastList;
			Boolean realOr[] = new Boolean[] { Boolean.FALSE };
		
		
		try {      // for error handling
			orLastList=or(nextType, realOr);
			if (inputState.guessing==0) {
				or_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			if (((_tokenSet_22.member(LA(1))))&&(orLastList != null)) {
				last=orAndNext((AST)orLastList.getFirst(), nextType);
				if (inputState.guessing==0) {
					next_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
						if(realOr[0].booleanValue()) {
										Iterator orLastIter = orLastList.iterator();
										while(orLastIter.hasNext()) {
											AST orLast = (AST)orLastIter.next();
											orLast.addChild((AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(OR_END,"OR_END")).add(next_AST)));
										}
									} else {
										((AST)orLastList.getFirst()).addChild(next_AST);
									}
								
				}
			}
			else if ((_tokenSet_9.member(LA(1)))) {
				if ( inputState.guessing==0 ) {
						if(orLastList != null) {
										if(realOr[0].booleanValue()) {
											last = (AST)astFactory.make( (new ASTArray(1)).add((AST)astFactory.create(OR_END,"OR_END")));
											Iterator orLastIter = orLastList.iterator();
											while(orLastIter.hasNext()) {
												AST orLast = (AST)orLastIter.next();
												orLast.addChild(last);
											}
										} else {
											last = (AST)orLastList.getFirst();
										}
									}
								
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			orConstruct_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = orConstruct_AST;
		return last;
	}
	
	public final AST  andConstruct() throws RecognitionException, TokenStreamException {
		AST last = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST andConstruct_AST = null;
		AST next_AST = null;
		
			LinkedList andLastList;
			Token andToken = LT(1);
			Boolean realAnd[] = new Boolean[] { Boolean.FALSE };
		
		
		try {      // for error handling
			andLastList=and(realAnd);
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			{
			if (((_tokenSet_22.member(LA(1))))&&(andLastList != null)) {
				last=orAndNext((AST)andLastList.getFirst(), PATH_ALL);
				if (inputState.guessing==0) {
					next_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					andConstruct_AST = (AST)currentAST.root;
						if(realAnd[0].booleanValue()) {
										AndConstructAST andConstructAst = new AndConstructAST(andToken, AND_CONSTRUCT, 
											"AND_CONSTRUCT", andLastList);
										Iterator andLastIter = andLastList.iterator();
										while(andLastIter.hasNext()) {
											AST andLast = (AST)andLastIter.next();
											AndEndAST andEnd = new AndEndAST(AND_END, "AND_END"/* + andNum++*/, andConstructAst);
											andConstructAst.andEnd = andEnd;
											andLast.addChild((AST)astFactory.make( (new ASTArray(2)).add(andEnd).add(next_AST)));
										}
										andConstruct_AST = (AST)astFactory.make( (new ASTArray(2)).add(andConstructAst).add(andConstruct_AST));
									} else {
										((AST)andLastList.getFirst()).addChild(next_AST);
									}
								
					currentAST.root = andConstruct_AST;
					currentAST.child = andConstruct_AST!=null &&andConstruct_AST.getFirstChild()!=null ?
						andConstruct_AST.getFirstChild() : andConstruct_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_9.member(LA(1)))) {
				if ( inputState.guessing==0 ) {
					andConstruct_AST = (AST)currentAST.root;
						if(realAnd[0].booleanValue()) {
										AndConstructAST andConstructAst = new AndConstructAST(andToken, AND_CONSTRUCT, 
											"AND_CONSTRUCT", andLastList);
										if(andLastList != null) {
											last = new AndEndAST(AND_END, "AND_END"/* + andNum++*/, andConstructAst);
											andConstructAst.andEnd = (AndEndAST)last;
											Iterator andLastIter = andLastList.iterator();
											while(andLastIter.hasNext()) {
												AST andLast = (AST)andLastIter.next();
												AndEndAST andEnd = new AndEndAST(AND_END, "AND_END"/* + andNum++*/, andConstructAst);
												andLast.addChild((AST)astFactory.make( (new ASTArray(2)).add(andEnd).add(last)));
											}
										}
										andConstruct_AST = (AST)astFactory.make( (new ASTArray(2)).add(andConstructAst).add(andConstruct_AST));
									} else {
										if(andLastList != null) last = (AST)andLastList.getFirst();
									}
								
					currentAST.root = andConstruct_AST;
					currentAST.child = andConstruct_AST!=null &&andConstruct_AST.getFirstChild()!=null ?
						andConstruct_AST.getFirstChild() : andConstruct_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			andConstruct_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = andConstruct_AST;
		return last;
	}
	
	public final void constraintSingle() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constraintSingle_AST = null;
		Token  exclam = null;
		AST exclam_AST = null;
		
			AST inner;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case EXCLAM:
			{
				exclam = LT(1);
				if (inputState.guessing==0) {
					exclam_AST = (AST)astFactory.create(exclam);
				}
				match(EXCLAM);
				break;
			}
			case OBRACE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp63_AST = null;
			tmp63_AST = (AST)astFactory.create(LT(1));
			match(OBRACE);
			{
			inner=pathElement(PATH_ALL);
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			}
			AST tmp64_AST = null;
			tmp64_AST = (AST)astFactory.create(LT(1));
			match(CBRACE);
			if ( inputState.guessing==0 ) {
				constraintSingle_AST = (AST)currentAST.root;
				constraintSingle_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(CONSTRAINT,"CONSTRAINT")).add(constraintSingle_AST));
				currentAST.root = constraintSingle_AST;
				currentAST.child = constraintSingle_AST!=null &&constraintSingle_AST.getFirstChild()!=null ?
					constraintSingle_AST.getFirstChild() : constraintSingle_AST;
				currentAST.advanceChildToEnd();
			}
			if ( inputState.guessing==0 ) {
				constraintSingle_AST = (AST)currentAST.root;
					if(exclam != null) {
								exclam_AST.setType(NEGATION);
								constraintSingle_AST = (AST)astFactory.make( (new ASTArray(2)).add(exclam_AST).add(constraintSingle_AST));
							}
						
				currentAST.root = constraintSingle_AST;
				currentAST.child = constraintSingle_AST!=null &&constraintSingle_AST.getFirstChild()!=null ?
					constraintSingle_AST.getFirstChild() : constraintSingle_AST;
				currentAST.advanceChildToEnd();
			}
			constraintSingle_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = constraintSingle_AST;
	}
	
	public final AST  entityConstructEnd(
		AST entity
	) throws RecognitionException, TokenStreamException {
		AST last = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entityConstructEnd_AST = null;
		AST constraint_AST = null;
		Token  eq = null;
		AST eq_AST = null;
		AST or_AST = null;
		AST constraint2_AST = null;
		
		try {      // for error handling
			if ( inputState.guessing==0 ) {
				entityConstructEnd_AST = (AST)currentAST.root;
				entityConstructEnd_AST = last = entity;
				currentAST.root = entityConstructEnd_AST;
				currentAST.child = entityConstructEnd_AST!=null &&entityConstructEnd_AST.getFirstChild()!=null ?
					entityConstructEnd_AST.getFirstChild() : entityConstructEnd_AST;
				currentAST.advanceChildToEnd();
			}
			{
			switch ( LA(1)) {
			case EXCLAM:
			case OBRACE:
			{
				constraintSingle();
				if (inputState.guessing==0) {
					constraint_AST = (AST)returnAST;
				}
				break;
			}
			case SUB:
			case SUPER:
			case TOPOINT:
			case FROMPOINT:
			case EXTENDED_INTO:
			case EXTENSION_OF:
			case EQ:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			if (((_tokenSet_23.member(LA(1))))&&( entity == null || entity.getType() == ENTITY )) {
				{
				boolean synPredMatched58 = false;
				if (((LA(1)==EQ) && (_tokenSet_24.member(LA(2))))) {
					int _m58 = mark();
					synPredMatched58 = true;
					inputState.guessing++;
					try {
						{
						match(EQ);
						{
						switch ( LA(1)) {
						case INT:
						case FLOAT:
						case ENUM:
						case STRING:
						{
							value();
							break;
						}
						case OPAREN:
						{
							match(OPAREN);
							value();
							match(CPAREN);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						}
					}
					catch (RecognitionException pe) {
						synPredMatched58 = false;
					}
					rewind(_m58);
					inputState.guessing--;
				}
				if ( synPredMatched58 ) {
					eq = LT(1);
					if (inputState.guessing==0) {
						eq_AST = (AST)astFactory.create(eq);
						astFactory.makeASTRoot(currentAST, eq_AST);
					}
					match(EQ);
					if ( inputState.guessing==0 ) {
						eq_AST.setType(EQ_ENTITY);
					}
					{
					switch ( LA(1)) {
					case INT:
					case FLOAT:
					case ENUM:
					case STRING:
					{
						value();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
						break;
					}
					case OPAREN:
					{
						orValue(eq_AST, entity);
						if (inputState.guessing==0) {
							or_AST = (AST)returnAST;
						}
						if ( inputState.guessing==0 ) {
							entityConstructEnd_AST = (AST)currentAST.root;
							entityConstructEnd_AST = or_AST;
							currentAST.root = entityConstructEnd_AST;
							currentAST.child = entityConstructEnd_AST!=null &&entityConstructEnd_AST.getFirstChild()!=null ?
								entityConstructEnd_AST.getFirstChild() : entityConstructEnd_AST;
							currentAST.advanceChildToEnd();
						}
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					if ( inputState.guessing==0 ) {
						last = null;
					}
				}
				else if ((LA(1)==FROMPOINT)) {
					MappingAST tmp65_AST = null;
					if (inputState.guessing==0) {
						tmp65_AST = new MappingAST(LT(1));
						astFactory.makeASTRoot(currentAST, tmp65_AST);
					}
					match(FROMPOINT);
					{
					if (((LA(1)==EXCLAM||LA(1)==OBRACE) && (_tokenSet_20.member(LA(2))))&&( constraint_AST == null )) {
						constraintSingle();
						if (inputState.guessing==0) {
							constraint2_AST = (AST)returnAST;
						}
						if ( inputState.guessing==0 ) {
							constraint_AST = constraint2_AST;
						}
					}
					else if ((_tokenSet_25.member(LA(1))) && (_tokenSet_26.member(LA(2)))) {
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					
					}
					{
					switch ( LA(1)) {
					case ENUM:
					case CPAREN:
					case LITERAL_end_mapping_constraints:
					case LITERAL_end_attribute_mapping:
					case CSQBRAC:
					case CANBRAC:
					case CBRACE:
					case LITERAL_end_derived_variant:
					{
						if ( inputState.guessing==0 ) {
							entityConstructEnd_AST = (AST)currentAST.root;
							last = entityConstructEnd_AST;
						}
						break;
					}
					case ID:
					case OPAREN:
					case VBAR:
					case OSQBRAC:
					case SLASH:
					case OANBRAC:
					case EXCLAM:
					case OBRACE:
					{
						last=pathElement(PATH_ATTRIBUTE_ONLY);
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else if ((_tokenSet_27.member(LA(1))) && (_tokenSet_25.member(LA(2)))) {
					{
					switch ( LA(1)) {
					case SUPER:
					{
						MappingAST tmp66_AST = null;
						if (inputState.guessing==0) {
							tmp66_AST = new MappingAST(LT(1));
							astFactory.makeASTRoot(currentAST, tmp66_AST);
						}
						match(SUPER);
						break;
					}
					case SUB:
					{
						MappingAST tmp67_AST = null;
						if (inputState.guessing==0) {
							tmp67_AST = new MappingAST(LT(1));
							astFactory.makeASTRoot(currentAST, tmp67_AST);
						}
						match(SUB);
						break;
					}
					case EXTENDED_INTO:
					{
						MappingAST tmp68_AST = null;
						if (inputState.guessing==0) {
							tmp68_AST = new MappingAST(LT(1));
							astFactory.makeASTRoot(currentAST, tmp68_AST);
						}
						match(EXTENDED_INTO);
						break;
					}
					case EXTENSION_OF:
					{
						MappingAST tmp69_AST = null;
						if (inputState.guessing==0) {
							tmp69_AST = new MappingAST(LT(1));
							astFactory.makeASTRoot(currentAST, tmp69_AST);
						}
						match(EXTENSION_OF);
						break;
					}
					case EQ:
					{
						AST tmp70_AST = null;
						if (inputState.guessing==0) {
							tmp70_AST = (AST)astFactory.create(LT(1));
							astFactory.makeASTRoot(currentAST, tmp70_AST);
						}
						match(EQ);
						if ( inputState.guessing==0 ) {
							tmp70_AST.setType(SELECT); tmp70_AST.setText("SELECT");
						}
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case ENUM:
					case CPAREN:
					case LITERAL_end_mapping_constraints:
					case LITERAL_end_attribute_mapping:
					case CSQBRAC:
					case CANBRAC:
					case CBRACE:
					case LITERAL_end_derived_variant:
					{
						if ( inputState.guessing==0 ) {
							entityConstructEnd_AST = (AST)currentAST.root;
							last = entityConstructEnd_AST;
						}
						break;
					}
					case ID:
					case OPAREN:
					case VBAR:
					case OSQBRAC:
					case SLASH:
					case OANBRAC:
					case EXCLAM:
					case OBRACE:
					{
						last=pathElement(PATH_ALL);
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
			}
			else if ((LA(1)==TOPOINT)) {
				MappingAST tmp71_AST = null;
				if (inputState.guessing==0) {
					tmp71_AST = new MappingAST(LT(1));
					astFactory.makeASTRoot(currentAST, tmp71_AST);
				}
				match(TOPOINT);
				{
				switch ( LA(1)) {
				case ENUM:
				case CPAREN:
				case LITERAL_end_mapping_constraints:
				case LITERAL_end_attribute_mapping:
				case CSQBRAC:
				case CANBRAC:
				case CBRACE:
				case LITERAL_end_derived_variant:
				{
					if ( inputState.guessing==0 ) {
						entityConstructEnd_AST = (AST)currentAST.root;
						last = entityConstructEnd_AST;
					}
					break;
				}
				case ID:
				case OPAREN:
				case VBAR:
				case OSQBRAC:
				case SLASH:
				case OANBRAC:
				case EXCLAM:
				case OBRACE:
				{
					last=pathElement(PATH_ALL);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				entityConstructEnd_AST = (AST)currentAST.root;
					if(constraint_AST != null) {
								constraint_AST.addChild(entityConstructEnd_AST);
								entityConstructEnd_AST = constraint_AST;
							}
						
				currentAST.root = entityConstructEnd_AST;
				currentAST.child = entityConstructEnd_AST!=null &&entityConstructEnd_AST.getFirstChild()!=null ?
					entityConstructEnd_AST.getFirstChild() : entityConstructEnd_AST;
				currentAST.advanceChildToEnd();
			}
			entityConstructEnd_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = entityConstructEnd_AST;
		return last;
	}
	
	public final void orValue(
		AST rel, AST var
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orValue_AST = null;
		AST value_AST = null;
		AST nextOr_AST = null;
		
		try {      // for error handling
			AST tmp72_AST = null;
			tmp72_AST = (AST)astFactory.create(LT(1));
			match(OPAREN);
			value();
			if (inputState.guessing==0) {
				value_AST = (AST)returnAST;
			}
			AST tmp73_AST = null;
			tmp73_AST = (AST)astFactory.create(LT(1));
			match(CPAREN);
			if ( inputState.guessing==0 ) {
				orValue_AST = (AST)currentAST.root;
				AST relAST = new MappingAST(rel); orValue_AST = (AST)astFactory.make( (new ASTArray(3)).add(relAST).add(var).add(value_AST));
				currentAST.root = orValue_AST;
				currentAST.child = orValue_AST!=null &&orValue_AST.getFirstChild()!=null ?
					orValue_AST.getFirstChild() : orValue_AST;
				currentAST.advanceChildToEnd();
			}
			{
			switch ( LA(1)) {
			case OPAREN:
			{
				orValue(rel, var);
				if (inputState.guessing==0) {
					nextOr_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					orValue_AST = (AST)currentAST.root;
					orValue_AST = (AST)astFactory.make( (new ASTArray(3)).add((AST)astFactory.create(OR,"OR")).add(orValue_AST).add(nextOr_AST));
					currentAST.root = orValue_AST;
					currentAST.child = orValue_AST!=null &&orValue_AST.getFirstChild()!=null ?
						orValue_AST.getFirstChild() : orValue_AST;
					currentAST.advanceChildToEnd();
				}
				break;
			}
			case ENUM:
			case CPAREN:
			case LITERAL_end_mapping_constraints:
			case LITERAL_end_attribute_mapping:
			case CSQBRAC:
			case CANBRAC:
			case CBRACE:
			case LITERAL_end_derived_variant:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = orValue_AST;
	}
	
	public final LinkedList  or(
		int nextType, Boolean realOr[]
	) throws RecognitionException, TokenStreamException {
		LinkedList lastList = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST or_AST = null;
		AST next_AST = null;
		
			AST last = null;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case OPAREN:
			{
				AST tmp74_AST = null;
				tmp74_AST = (AST)astFactory.create(LT(1));
				match(OPAREN);
				{
				switch ( LA(1)) {
				case EXTENDED_INTO:
				case ID:
				case OPAREN:
				case VBAR:
				case OSQBRAC:
				case SLASH:
				case OANBRAC:
				case EXCLAM:
				case OBRACE:
				{
					{
					switch ( LA(1)) {
					case EXTENDED_INTO:
					{
						MappingAST tmp75_AST = null;
						tmp75_AST = new MappingAST(LT(1));
						match(EXTENDED_INTO);
						break;
					}
					case ID:
					case OPAREN:
					case VBAR:
					case OSQBRAC:
					case SLASH:
					case OANBRAC:
					case EXCLAM:
					case OBRACE:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					last=pathElement(nextType);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					{
					switch ( LA(1)) {
					case ENUM:
					{
						MappingAST tmp76_AST = null;
						if (inputState.guessing==0) {
							tmp76_AST = new MappingAST(LT(1));
							astFactory.addASTChild(currentAST, tmp76_AST);
						}
						match(ENUM);
						if ( inputState.guessing==0 ) {
							mappedValueUsed = true;
						}
						break;
					}
					case CPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					break;
				}
				case CPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp77_AST = null;
				tmp77_AST = (AST)astFactory.create(LT(1));
				match(CPAREN);
				break;
			}
			case OANBRAC:
			{
				AST tmp78_AST = null;
				tmp78_AST = (AST)astFactory.create(LT(1));
				match(OANBRAC);
				{
				switch ( LA(1)) {
				case ID:
				case OPAREN:
				case VBAR:
				case OSQBRAC:
				case SLASH:
				case OANBRAC:
				case EXCLAM:
				case OBRACE:
				{
					last=pathElement(nextType);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					{
					switch ( LA(1)) {
					case ENUM:
					{
						MappingAST tmp79_AST = null;
						if (inputState.guessing==0) {
							tmp79_AST = new MappingAST(LT(1));
							astFactory.addASTChild(currentAST, tmp79_AST);
						}
						match(ENUM);
						if ( inputState.guessing==0 ) {
							mappedValueUsed = true;
						}
						break;
					}
					case CANBRAC:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					break;
				}
				case CANBRAC:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				AST tmp80_AST = null;
				tmp80_AST = (AST)astFactory.create(LT(1));
				match(CANBRAC);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			boolean synPredMatched82 = false;
			if (((LA(1)==OPAREN||LA(1)==OANBRAC) && (_tokenSet_28.member(LA(2))))) {
				int _m82 = mark();
				synPredMatched82 = true;
				inputState.guessing++;
				try {
					{
					switch ( LA(1)) {
					case OPAREN:
					{
						match(OPAREN);
						break;
					}
					case OANBRAC:
					{
						match(OANBRAC);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched82 = false;
				}
				rewind(_m82);
				inputState.guessing--;
			}
			if ( synPredMatched82 ) {
				lastList=or(nextType, null);
				if (inputState.guessing==0) {
					next_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					or_AST = (AST)currentAST.root;
						if(last != null && lastList != null) lastList.add(last);
									if(or_AST == null) {
										or_AST = next_AST;
									} else if(next_AST != null) {
										or_AST = (AST)astFactory.make( (new ASTArray(3)).add((AST)astFactory.create(OR,"OR")).add(or_AST).add(next_AST)); 
									}
									if(realOr != null) realOr[0] = Boolean.TRUE;
								
					currentAST.root = or_AST;
					currentAST.child = or_AST!=null &&or_AST.getFirstChild()!=null ?
						or_AST.getFirstChild() : or_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_8.member(LA(1))) && (_tokenSet_11.member(LA(2)))) {
				if ( inputState.guessing==0 ) {
					if(realOr != null) realOr[0] = Boolean.FALSE;
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
					if(last != null && lastList == null) {
								lastList = new LinkedList();
								lastList.add(last);
							}
						
			}
			or_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = or_AST;
		return lastList;
	}
	
	public final AST  orAndNext(
		AST orAndLast, int nextType
	) throws RecognitionException, TokenStreamException {
		AST last = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orAndNext_AST = null;
		
			AST entity = orAndLast;
		
		
		try {      // for error handling
			{
			boolean synPredMatched72 = false;
			if ((((_tokenSet_18.member(LA(1))) && (_tokenSet_19.member(LA(2))))&&(	orAndLast == null ||
				orAndLast.getType() == ENTITY || orAndLast.getType() == ENTITY_AGGREG
			))) {
				int _m72 = mark();
				synPredMatched72 = true;
				inputState.guessing++;
				try {
					{
					{
					switch ( LA(1)) {
					case EXCLAM:
					case OBRACE:
					{
						constraintSingle();
						break;
					}
					case SUB:
					case SUPER:
					case TOPOINT:
					case FROMPOINT:
					case EXTENDED_INTO:
					case EXTENSION_OF:
					case EQ:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case FROMPOINT:
					{
						match(FROMPOINT);
						break;
					}
					case TOPOINT:
					{
						match(TOPOINT);
						break;
					}
					case SUPER:
					{
						match(SUPER);
						break;
					}
					case SUB:
					{
						match(SUB);
						break;
					}
					case EQ:
					{
						match(EQ);
						break;
					}
					case EXTENDED_INTO:
					{
						match(EXTENDED_INTO);
						break;
					}
					case EXTENSION_OF:
					{
						match(EXTENSION_OF);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched72 = false;
				}
				rewind(_m72);
				inputState.guessing--;
			}
			if ( synPredMatched72 ) {
				if ( inputState.guessing==0 ) {
					entity = MappingPathWalker.dupWholeTree(orAndLast);
				}
				last=entityConstructEnd(entity);
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
			}
			else if ((_tokenSet_20.member(LA(1))) && (_tokenSet_21.member(LA(2)))) {
				last=pathElement(nextType);
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			orAndNext_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = orAndNext_AST;
		return last;
	}
	
	public final LinkedList  and(
		Boolean realAnd[]
	) throws RecognitionException, TokenStreamException {
		LinkedList lastList = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST and_AST = null;
		AST next_AST = null;
		
			AST last;
		
		
		try {      // for error handling
			AST tmp81_AST = null;
			tmp81_AST = (AST)astFactory.create(LT(1));
			match(OSQBRAC);
			last=pathElement(PATH_ALL);
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp82_AST = null;
			tmp82_AST = (AST)astFactory.create(LT(1));
			match(CSQBRAC);
			{
			boolean synPredMatched88 = false;
			if (((LA(1)==OSQBRAC) && (_tokenSet_20.member(LA(2))))) {
				int _m88 = mark();
				synPredMatched88 = true;
				inputState.guessing++;
				try {
					{
					match(OSQBRAC);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched88 = false;
				}
				rewind(_m88);
				inputState.guessing--;
			}
			if ( synPredMatched88 ) {
				lastList=and(null);
				if (inputState.guessing==0) {
					next_AST = (AST)returnAST;
				}
				if ( inputState.guessing==0 ) {
					and_AST = (AST)currentAST.root;
						if(last != null && lastList != null) lastList.add(last);
									and_AST = (AST)astFactory.make( (new ASTArray(3)).add((AST)astFactory.create(AND,"AND")).add(and_AST).add(next_AST));
									if(realAnd != null) realAnd[0] = Boolean.TRUE;
								
					currentAST.root = and_AST;
					currentAST.child = and_AST!=null &&and_AST.getFirstChild()!=null ?
						and_AST.getFirstChild() : and_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_8.member(LA(1))) && (_tokenSet_11.member(LA(2)))) {
				if ( inputState.guessing==0 ) {
					if(realAnd != null) realAnd[0] = Boolean.FALSE;
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
					if(last != null && lastList == null) {
								lastList = new LinkedList();
								lastList.add(last);
							}
						
			}
			and_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = and_AST;
		return lastList;
	}
	
	public final void aimEntitySingleElem() throws RecognitionException, TokenStreamException, SdaiException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aimEntitySingleElem_AST = null;
		Token  id1 = null;
		EntityAST id1_AST = null;
		AST t_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			{
				id1 = LT(1);
				if (inputState.guessing==0) {
					id1_AST = new EntityAST(id1);
					astFactory.addASTChild(currentAST, id1_AST);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
					id1_AST.setIdentifier(this, id1, true);
				}
				break;
			}
			case OSQBRAC:
			{
				aimComplex();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case SLASH:
			{
				templateCall();
				if (inputState.guessing==0) {
					t_AST = (AST)returnAST;
					astFactory.addASTChild(currentAST, returnAST);
				}
				if ( inputState.guessing==0 ) {
					((TemplateCallAST)t_AST).setInAimList(true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			aimEntitySingleElem_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		returnAST = aimEntitySingleElem_AST;
	}
	
	public final void aimComplex() throws RecognitionException, TokenStreamException, SdaiException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aimComplex_AST = null;
		Token  id = null;
		EntityAST id_AST = null;
		AST t_AST = null;
		
			List nameIds = new ArrayList();
			Token nextToken = LT(1);
		boolean templated = false;
		
		
		try {      // for error handling
			{
			int _cnt114=0;
			_loop114:
			do {
				if ((LA(1)==OSQBRAC)) {
					AST tmp83_AST = null;
					tmp83_AST = (AST)astFactory.create(LT(1));
					match(OSQBRAC);
					{
					switch ( LA(1)) {
					case ID:
					{
						id = LT(1);
						if (inputState.guessing==0) {
							id_AST = new EntityAST(id);
							astFactory.addASTChild(currentAST, id_AST);
						}
						match(ID);
						if ( inputState.guessing==0 ) {
							id_AST.setIdentifier(this, id, true); nameIds.add(id_AST);
						}
						break;
					}
					case SLASH:
					{
						templateCall();
						if (inputState.guessing==0) {
							t_AST = (AST)returnAST;
							astFactory.addASTChild(currentAST, returnAST);
						}
						if ( inputState.guessing==0 ) {
							((TemplateCallAST)t_AST).setInAimList(true); templated = true;
						}
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					AST tmp84_AST = null;
					tmp84_AST = (AST)astFactory.create(LT(1));
					match(CSQBRAC);
				}
				else {
					if ( _cnt114>=1 ) { break _loop114; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt114++;
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				aimComplex_AST = (AST)currentAST.root;
					if(templated) {
				TemplateCallAST templateCallAst = new TemplateCallAST(nextToken);
				templateCallAst.setType(TEMPLATE_CALL);
				templateCallAst.setText("/AIMCOMPLEX/");
				templateCallAst.setInAimList(true);
				templateCallAst.setFirstChild(aimComplex_AST);
				aimComplex_AST = templateCallAst;
				} else{
				EntityAST entityAST = new EntityAST(nextToken);
				entityAST.setType(ID);
				entityAST.setIdentifier(this, EntityAST.makeComplexName(nameIds), true);
				aimComplex_AST = entityAST;
				}
					
				currentAST.root = aimComplex_AST;
				currentAST.child = aimComplex_AST!=null &&aimComplex_AST.getFirstChild()!=null ?
					aimComplex_AST.getFirstChild() : aimComplex_AST;
				currentAST.advanceChildToEnd();
			}
			aimComplex_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		returnAST = aimComplex_AST;
	}
	
	public final void aimEntityElem() throws RecognitionException, TokenStreamException, SdaiException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aimEntityElem_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			case OSQBRAC:
			case SLASH:
			{
				aimEntitySingleElem();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case VBAR:
			{
				AST tmp85_AST = null;
				tmp85_AST = (AST)astFactory.create(LT(1));
				match(VBAR);
				aimEntitySingleElem();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp86_AST = null;
				tmp86_AST = (AST)astFactory.create(LT(1));
				match(VBAR);
				if ( inputState.guessing==0 ) {
					aimEntityElem_AST = (AST)currentAST.root;
					((EntityAST)aimEntityElem_AST).exactType = true;
				}
				break;
			}
			case OPAREN:
			{
				{
				int _cnt118=0;
				_loop118:
				do {
					if ((LA(1)==OPAREN)) {
						AST tmp87_AST = null;
						tmp87_AST = (AST)astFactory.create(LT(1));
						match(OPAREN);
						aimEntityElem();
						if (inputState.guessing==0) {
							astFactory.addASTChild(currentAST, returnAST);
						}
						AST tmp88_AST = null;
						tmp88_AST = (AST)astFactory.create(LT(1));
						match(CPAREN);
					}
					else {
						if ( _cnt118>=1 ) { break _loop118; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt118++;
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			aimEntityElem_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		returnAST = aimEntityElem_AST;
	}
	
	public final void aimAttributeSingleElem() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aimAttributeSingleElem_AST = null;
		Token  id1 = null;
		AttributeAST id1_AST = null;
		Token  id2 = null;
		AST id2_AST = null;
		Token  id3 = null;
		EntityAST id3_AST = null;
		
		try {      // for error handling
			{
			boolean synPredMatched123 = false;
			if (((LA(1)==ID) && (LA(2)==DOT))) {
				int _m123 = mark();
				synPredMatched123 = true;
				inputState.guessing++;
				try {
					{
					match(ID);
					match(DOT);
					match(ID);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched123 = false;
				}
				rewind(_m123);
				inputState.guessing--;
			}
			if ( synPredMatched123 ) {
				id1 = LT(1);
				if (inputState.guessing==0) {
					id1_AST = new AttributeAST(id1);
					astFactory.addASTChild(currentAST, id1_AST);
				}
				match(ID);
				AST tmp89_AST = null;
				tmp89_AST = (AST)astFactory.create(LT(1));
				match(DOT);
				id2 = LT(1);
				if (inputState.guessing==0) {
					id2_AST = (AST)astFactory.create(id2);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
					id1_AST.setIdentifiers(this, id1, id2, true);
				}
			}
			else if ((LA(1)==ID) && (_tokenSet_30.member(LA(2)))) {
				id3 = LT(1);
				if (inputState.guessing==0) {
					id3_AST = new EntityAST(id3);
					astFactory.addASTChild(currentAST, id3_AST);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
					id3_AST.setIdentifier(this, id3, true);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			aimAttributeSingleElem_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_30);
			} else {
			  throw ex;
			}
		}
		returnAST = aimAttributeSingleElem_AST;
	}
	
	public final void aimAttributeComplexElem() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aimAttributeComplexElem_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			{
				aimAttributeSingleElem();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case VBAR:
			{
				AST tmp90_AST = null;
				tmp90_AST = (AST)astFactory.create(LT(1));
				match(VBAR);
				aimAttributeSingleElem();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				AST tmp91_AST = null;
				tmp91_AST = (AST)astFactory.create(LT(1));
				match(VBAR);
				break;
			}
			case MACRO:
			{
				AST tmp92_AST = null;
				if (inputState.guessing==0) {
					tmp92_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp92_AST);
				}
				match(MACRO);
				break;
			}
			case OSQBRAC:
			{
				{
				int _cnt128=0;
				_loop128:
				do {
					if ((LA(1)==OSQBRAC)) {
						AST tmp93_AST = null;
						tmp93_AST = (AST)astFactory.create(LT(1));
						match(OSQBRAC);
						{
						switch ( LA(1)) {
						case ID:
						{
							aimAttributeSingleElem();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
							break;
						}
						case MACRO:
						{
							AST tmp94_AST = null;
							if (inputState.guessing==0) {
								tmp94_AST = (AST)astFactory.create(LT(1));
								astFactory.addASTChild(currentAST, tmp94_AST);
							}
							match(MACRO);
							break;
						}
						case CSQBRAC:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						AST tmp95_AST = null;
						tmp95_AST = (AST)astFactory.create(LT(1));
						match(CSQBRAC);
					}
					else {
						if ( _cnt128>=1 ) { break _loop128; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt128++;
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			aimAttributeComplexElem_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_31);
			} else {
			  throw ex;
			}
		}
		returnAST = aimAttributeComplexElem_AST;
	}
	
	public final void aimAttributeElem() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST aimAttributeElem_AST = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			case VBAR:
			case OSQBRAC:
			case MACRO:
			{
				aimAttributeComplexElem();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			case OPAREN:
			{
				{
				int _cnt133=0;
				_loop133:
				do {
					if ((LA(1)==OPAREN)) {
						AST tmp96_AST = null;
						tmp96_AST = (AST)astFactory.create(LT(1));
						match(OPAREN);
						{
						switch ( LA(1)) {
						case ID:
						case OPAREN:
						case VBAR:
						case OSQBRAC:
						case SLASH:
						case OANBRAC:
						case MACRO:
						{
							aimAttributeElem();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
							break;
						}
						case CPAREN:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						AST tmp97_AST = null;
						tmp97_AST = (AST)astFactory.create(LT(1));
						match(CPAREN);
					}
					else {
						if ( _cnt133>=1 ) { break _loop133; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt133++;
				} while (true);
				}
				break;
			}
			case OANBRAC:
			{
				{
				int _cnt136=0;
				_loop136:
				do {
					if ((LA(1)==OANBRAC)) {
						AST tmp98_AST = null;
						tmp98_AST = (AST)astFactory.create(LT(1));
						match(OANBRAC);
						{
						switch ( LA(1)) {
						case ID:
						case OPAREN:
						case VBAR:
						case OSQBRAC:
						case SLASH:
						case OANBRAC:
						case MACRO:
						{
							aimAttributeElem();
							if (inputState.guessing==0) {
								astFactory.addASTChild(currentAST, returnAST);
							}
							break;
						}
						case CANBRAC:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						AST tmp99_AST = null;
						tmp99_AST = (AST)astFactory.create(LT(1));
						match(CANBRAC);
					}
					else {
						if ( _cnt136>=1 ) { break _loop136; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt136++;
				} while (true);
				}
				break;
			}
			case SLASH:
			{
				templateCall();
				if (inputState.guessing==0) {
					astFactory.addASTChild(currentAST, returnAST);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			aimAttributeElem_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_31);
			} else {
			  throw ex;
			}
		}
		returnAST = aimAttributeElem_AST;
	}
	
	public final void schemaMappingInfo() throws RecognitionException, TokenStreamException, SdaiException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfo_AST = null;
		Token  smi = null;
		AST smi_AST = null;
		Token  id = null;
		AST id_AST = null;
			AST top = null;
		
		
		try {      // for error handling
			smi = LT(1);
			if (inputState.guessing==0) {
				smi_AST = (AST)astFactory.create(smi);
			}
			match(LITERAL_schema_mapping_info);
			{
			switch ( LA(1)) {
			case ID:
			{
				id = LT(1);
				if (inputState.guessing==0) {
					id_AST = (AST)astFactory.create(id);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
						id_AST.setType(SCHEMA_MAPPING_INFO);
									if(!mappingModel.getName().equals(id_AST.getText().toUpperCase() + "_MAPPING_DATA")) {
										throw new SemanticException("Mapping info schema name doesn't match", 
											getFilename(), LT(1).getLine());
									}
									top = id_AST;
								
				}
				break;
			}
			case SEP:
			{
				if ( inputState.guessing==0 ) {
						smi_AST.setType(SCHEMA_MAPPING_INFO);
									top = smi_AST;
								
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp100_AST = null;
			tmp100_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			{
			int _cnt141=0;
			_loop141:
			do {
				if ((LA(1)==LITERAL_entity)) {
					schemaMappingInfoEntity();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
				}
				else {
					if ( _cnt141>=1 ) { break _loop141; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt141++;
			} while (true);
			}
			AST tmp101_AST = null;
			tmp101_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_schema_mapping_info);
			AST tmp102_AST = null;
			tmp102_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				schemaMappingInfo_AST = (AST)currentAST.root;
				schemaMappingInfo_AST = (AST)astFactory.make( (new ASTArray(2)).add(top).add(schemaMappingInfo_AST));
				currentAST.root = schemaMappingInfo_AST;
				currentAST.child = schemaMappingInfo_AST!=null &&schemaMappingInfo_AST.getFirstChild()!=null ?
					schemaMappingInfo_AST.getFirstChild() : schemaMappingInfo_AST;
				currentAST.advanceChildToEnd();
			}
			schemaMappingInfo_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfo_AST;
	}
	
	public final void schemaMappingInfoEntity() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfoEntity_AST = null;
		Token  id = null;
		EntityAST id_AST = null;
		
		try {      // for error handling
			AST tmp103_AST = null;
			tmp103_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_entity);
			id = LT(1);
			if (inputState.guessing==0) {
				id_AST = new EntityAST(id);
			}
			match(ID);
			AST tmp104_AST = null;
			tmp104_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
					id_AST.setIdentifier(this, id, false);
							id_AST.setType(ENTITY);
							if(id_AST.declaration == null || id_AST.declaration.type != DictionaryDeclaration.ENTITY) {
								throw new SemanticException("Identifier is not an entity", getFilename(), id.getLine());
							}
						
			}
			{
			int _cnt144=0;
			_loop144:
			do {
				switch ( LA(1)) {
				case LITERAL_attribute:
				{
					schemaMappingInfoAttribute(id_AST);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case LITERAL_entry_point:
				{
					schemaMappingInfoEntryPoint();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case LITERAL_strong_users:
				{
					schemaMappingInfoEntryStrongUsers();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case LITERAL_derived_variant:
				{
					schemaMappingInfoEntryDerivedVariant();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				default:
				{
					if ( _cnt144>=1 ) { break _loop144; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				}
				_cnt144++;
			} while (true);
			}
			AST tmp105_AST = null;
			tmp105_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_entity);
			AST tmp106_AST = null;
			tmp106_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				schemaMappingInfoEntity_AST = (AST)currentAST.root;
				schemaMappingInfoEntity_AST = (AST)astFactory.make( (new ASTArray(2)).add(id_AST).add(schemaMappingInfoEntity_AST));
				currentAST.root = schemaMappingInfoEntity_AST;
				currentAST.child = schemaMappingInfoEntity_AST!=null &&schemaMappingInfoEntity_AST.getFirstChild()!=null ?
					schemaMappingInfoEntity_AST.getFirstChild() : schemaMappingInfoEntity_AST;
				currentAST.advanceChildToEnd();
			}
			schemaMappingInfoEntity_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_32);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfoEntity_AST;
	}
	
	public final void schemaMappingInfoAttribute(
		EntityAST entity
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfoAttribute_AST = null;
		Token  id = null;
		AttributeAST id_AST = null;
		
		try {      // for error handling
			AST tmp107_AST = null;
			tmp107_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_attribute);
			id = LT(1);
			if (inputState.guessing==0) {
				id_AST = new AttributeAST(id);
			}
			match(ID);
			AST tmp108_AST = null;
			tmp108_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
					id_AST.setIdentifiers(this, entity, id, false);
							id_AST.setType(ATTRIBUTE);
						
			}
			{
			int _cnt147=0;
			_loop147:
			do {
				switch ( LA(1)) {
				case LITERAL_strong:
				{
					schemaMappingInfoStrong();
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				case LITERAL_mapped_values:
				{
					schemaMappingInfoMappedValues(id_AST);
					if (inputState.guessing==0) {
						astFactory.addASTChild(currentAST, returnAST);
					}
					break;
				}
				default:
				{
					if ( _cnt147>=1 ) { break _loop147; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				}
				_cnt147++;
			} while (true);
			}
			AST tmp109_AST = null;
			tmp109_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_attribute);
			AST tmp110_AST = null;
			tmp110_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				schemaMappingInfoAttribute_AST = (AST)currentAST.root;
				schemaMappingInfoAttribute_AST = (AST)astFactory.make( (new ASTArray(2)).add(id_AST).add(schemaMappingInfoAttribute_AST));
				currentAST.root = schemaMappingInfoAttribute_AST;
				currentAST.child = schemaMappingInfoAttribute_AST!=null &&schemaMappingInfoAttribute_AST.getFirstChild()!=null ?
					schemaMappingInfoAttribute_AST.getFirstChild() : schemaMappingInfoAttribute_AST;
				currentAST.advanceChildToEnd();
			}
			schemaMappingInfoAttribute_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfoAttribute_AST;
	}
	
	public final void schemaMappingInfoEntryPoint() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfoEntryPoint_AST = null;
		Token  type = null;
		AST type_AST = null;
		Token  id = null;
		EntityAST id_AST = null;
		
		try {      // for error handling
			type = LT(1);
			if (inputState.guessing==0) {
				type_AST = (AST)astFactory.create(type);
				astFactory.addASTChild(currentAST, type_AST);
			}
			match(LITERAL_entry_point);
			if ( inputState.guessing==0 ) {
				type_AST.setType(ENTRY_POINT);
			}
			{
			switch ( LA(1)) {
			case LITERAL_for:
			{
				AST tmp111_AST = null;
				if (inputState.guessing==0) {
					tmp111_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp111_AST);
				}
				match(LITERAL_for);
				id = LT(1);
				if (inputState.guessing==0) {
					id_AST = new EntityAST(id);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
					schemaMappingInfoEntryPoint_AST = (AST)currentAST.root;
						id_AST.setIdentifier(this, id, true);
									schemaMappingInfoEntryPoint_AST = (AST)astFactory.make( (new ASTArray(2)).add(schemaMappingInfoEntryPoint_AST).add(id_AST));
								
					currentAST.root = schemaMappingInfoEntryPoint_AST;
					currentAST.child = schemaMappingInfoEntryPoint_AST!=null &&schemaMappingInfoEntryPoint_AST.getFirstChild()!=null ?
						schemaMappingInfoEntryPoint_AST.getFirstChild() : schemaMappingInfoEntryPoint_AST;
					currentAST.advanceChildToEnd();
				}
				break;
			}
			case SEP:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp112_AST = null;
			tmp112_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			schemaMappingInfoEntryPoint_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfoEntryPoint_AST;
	}
	
	public final void schemaMappingInfoEntryStrongUsers() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfoEntryStrongUsers_AST = null;
		Token  type = null;
		AST type_AST = null;
		Token  id = null;
		EntityAST id_AST = null;
		
		try {      // for error handling
			type = LT(1);
			if (inputState.guessing==0) {
				type_AST = (AST)astFactory.create(type);
				astFactory.addASTChild(currentAST, type_AST);
			}
			match(LITERAL_strong_users);
			if ( inputState.guessing==0 ) {
				type_AST.setType(STRONG_USERS);
			}
			{
			switch ( LA(1)) {
			case LITERAL_for:
			{
				AST tmp113_AST = null;
				if (inputState.guessing==0) {
					tmp113_AST = (AST)astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp113_AST);
				}
				match(LITERAL_for);
				id = LT(1);
				if (inputState.guessing==0) {
					id_AST = new EntityAST(id);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
					schemaMappingInfoEntryStrongUsers_AST = (AST)currentAST.root;
						id_AST.setIdentifier(this, id, true);
									schemaMappingInfoEntryStrongUsers_AST = (AST)astFactory.make( (new ASTArray(2)).add(schemaMappingInfoEntryStrongUsers_AST).add(id_AST));
								
					currentAST.root = schemaMappingInfoEntryStrongUsers_AST;
					currentAST.child = schemaMappingInfoEntryStrongUsers_AST!=null &&schemaMappingInfoEntryStrongUsers_AST.getFirstChild()!=null ?
						schemaMappingInfoEntryStrongUsers_AST.getFirstChild() : schemaMappingInfoEntryStrongUsers_AST;
					currentAST.advanceChildToEnd();
				}
				break;
			}
			case SEP:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp114_AST = null;
			tmp114_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			schemaMappingInfoEntryStrongUsers_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfoEntryStrongUsers_AST;
	}
	
	public final void schemaMappingInfoEntryDerivedVariant() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfoEntryDerivedVariant_AST = null;
		Token  type = null;
		AST type_AST = null;
		
			AST last;
		
		
		try {      // for error handling
			type = LT(1);
			if (inputState.guessing==0) {
				type_AST = (AST)astFactory.create(type);
			}
			match(LITERAL_derived_variant);
			AST tmp115_AST = null;
			tmp115_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				type_AST.setType(DERIVED_VARIANT);
			}
			last=pathElement(PATH_ALL);
			if (inputState.guessing==0) {
				astFactory.addASTChild(currentAST, returnAST);
			}
			AST tmp116_AST = null;
			tmp116_AST = (AST)astFactory.create(LT(1));
			match(LITERAL_end_derived_variant);
			AST tmp117_AST = null;
			tmp117_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			if ( inputState.guessing==0 ) {
				schemaMappingInfoEntryDerivedVariant_AST = (AST)currentAST.root;
					schemaMappingInfoEntryDerivedVariant_AST = (AST)astFactory.make( (new ASTArray(2)).add(type_AST).add(schemaMappingInfoEntryDerivedVariant_AST));
						
				currentAST.root = schemaMappingInfoEntryDerivedVariant_AST;
				currentAST.child = schemaMappingInfoEntryDerivedVariant_AST!=null &&schemaMappingInfoEntryDerivedVariant_AST.getFirstChild()!=null ?
					schemaMappingInfoEntryDerivedVariant_AST.getFirstChild() : schemaMappingInfoEntryDerivedVariant_AST;
				currentAST.advanceChildToEnd();
			}
			schemaMappingInfoEntryDerivedVariant_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_33);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfoEntryDerivedVariant_AST;
	}
	
	public final void schemaMappingInfoStrong() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfoStrong_AST = null;
		Token  type = null;
		MappingAST type_AST = null;
		Token  id = null;
		EntityAST id_AST = null;
		
		try {      // for error handling
			type = LT(1);
			if (inputState.guessing==0) {
				type_AST = new MappingAST(type);
				astFactory.addASTChild(currentAST, type_AST);
			}
			match(LITERAL_strong);
			if ( inputState.guessing==0 ) {
				type_AST.setType(STRONG);
			}
			{
			switch ( LA(1)) {
			case LITERAL_for:
			{
				AST tmp118_AST = null;
				tmp118_AST = (AST)astFactory.create(LT(1));
				match(LITERAL_for);
				id = LT(1);
				if (inputState.guessing==0) {
					id_AST = new EntityAST(id);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
					schemaMappingInfoStrong_AST = (AST)currentAST.root;
						id_AST.setIdentifier(this, id, true);
									schemaMappingInfoStrong_AST = (AST)astFactory.make( (new ASTArray(2)).add(schemaMappingInfoStrong_AST).add(id_AST));
								
					currentAST.root = schemaMappingInfoStrong_AST;
					currentAST.child = schemaMappingInfoStrong_AST!=null &&schemaMappingInfoStrong_AST.getFirstChild()!=null ?
						schemaMappingInfoStrong_AST.getFirstChild() : schemaMappingInfoStrong_AST;
					currentAST.advanceChildToEnd();
				}
				break;
			}
			case SEP:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			AST tmp119_AST = null;
			tmp119_AST = (AST)astFactory.create(LT(1));
			match(SEP);
			schemaMappingInfoStrong_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfoStrong_AST;
	}
	
	public final void schemaMappingInfoMappedValues(
		AttributeAST attribute
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfoMappedValues_AST = null;
		Token  dataType = null;
		EntityAST dataType_AST = null;
			Map mappedValues = null;
		
		
		try {      // for error handling
			AST tmp120_AST = null;
			if (inputState.guessing==0) {
				tmp120_AST = (AST)astFactory.create(LT(1));
			}
			match(LITERAL_mapped_values);
			{
			switch ( LA(1)) {
			case LITERAL_as:
			{
				AST tmp121_AST = null;
				if (inputState.guessing==0) {
					tmp121_AST = (AST)astFactory.create(LT(1));
				}
				match(LITERAL_as);
				dataType = LT(1);
				if (inputState.guessing==0) {
					dataType_AST = new EntityAST(dataType);
				}
				match(ID);
				if ( inputState.guessing==0 ) {
						dataType_AST.setIdentifier(this, dataType, false);
									if(dataType_AST.declaration == null) {
										throw new SemanticException("Identifier not found", 
											getFilename(), LT(1).getLine());
									}
								
				}
				break;
			}
			case ENUM:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
					Map alternativeMappedValues = (Map)attributeMappedValues.get(attribute.attribute);
							if(alternativeMappedValues == null) {
								alternativeMappedValues = new HashMap();
								attributeMappedValues.put(attribute.attribute, alternativeMappedValues);
							}
							ENamed_type dataTypeDefintion = (dataType_AST != null ? 
								dataType_AST.declaration.definition : null);
							mappedValues = (Map)alternativeMappedValues.get(dataTypeDefintion);
							if(mappedValues == null) {
								mappedValues = new HashMap();
								alternativeMappedValues.put(dataTypeDefintion, mappedValues);
							}
						
			}
			schemaMappingInfoOneMappedValue(mappedValues);
			{
			_loop154:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp122_AST = null;
					if (inputState.guessing==0) {
						tmp122_AST = (AST)astFactory.create(LT(1));
					}
					match(COMMA);
					schemaMappingInfoOneMappedValue(mappedValues);
				}
				else {
					break _loop154;
				}
				
			} while (true);
			}
			AST tmp123_AST = null;
			if (inputState.guessing==0) {
				tmp123_AST = (AST)astFactory.create(LT(1));
			}
			match(SEP);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_34);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfoMappedValues_AST;
	}
	
	public final void schemaMappingInfoOneMappedValue(
		Map mappedValues
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST schemaMappingInfoOneMappedValue_AST = null;
		Token  value = null;
		MappingAST value_AST = null;
		AST key_AST = null;
		
		try {      // for error handling
			value = LT(1);
			if (inputState.guessing==0) {
				value_AST = new MappingAST(value);
			}
			match(ENUM);
			AST tmp124_AST = null;
			if (inputState.guessing==0) {
				tmp124_AST = (AST)astFactory.create(LT(1));
			}
			match(COLON);
			value();
			if (inputState.guessing==0) {
				key_AST = (AST)returnAST;
			}
			if ( inputState.guessing==0 ) {
				mappedValues.put(key_AST.getText(), value_AST);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				consume();
				consumeUntil(_tokenSet_35);
			} else {
			  throw ex;
			}
		}
		returnAST = schemaMappingInfoOneMappedValue_AST;
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
	
	private static final long _tokenSet_0_data_[] = { 2L, 0L };
	public static final BitSet _tokenSet_0 = new BitSet(_tokenSet_0_data_);
	private static final long _tokenSet_1_data_[] = { 4503599627370498L, 0L };
	public static final BitSet _tokenSet_1 = new BitSet(_tokenSet_1_data_);
	private static final long _tokenSet_2_data_[] = { 193654783976931330L, 0L };
	public static final BitSet _tokenSet_2 = new BitSet(_tokenSet_2_data_);
	private static final long _tokenSet_3_data_[] = { 265712378014859266L, 0L };
	public static final BitSet _tokenSet_3 = new BitSet(_tokenSet_3_data_);
	private static final long _tokenSet_4_data_[] = { 1688849860263936L, 0L };
	public static final BitSet _tokenSet_4 = new BitSet(_tokenSet_4_data_);
	private static final long _tokenSet_5_data_[] = { 1125899906842624L, 0L };
	public static final BitSet _tokenSet_5 = new BitSet(_tokenSet_5_data_);
	private static final long _tokenSet_6_data_[] = { 2594073385365405696L, 0L };
	public static final BitSet _tokenSet_6 = new BitSet(_tokenSet_6_data_);
	private static final long _tokenSet_7_data_[] = { -9222949824389709824L, 81L, 0L, 0L };
	public static final BitSet _tokenSet_7 = new BitSet(_tokenSet_7_data_);
	private static final long _tokenSet_8_data_[] = { -3457216266123608064L, 134221557L, 0L, 0L };
	public static final BitSet _tokenSet_8 = new BitSet(_tokenSet_8_data_);
	private static final long _tokenSet_9_data_[] = { 5765733422974631936L, 134219908L, 0L, 0L };
	public static final BitSet _tokenSet_9 = new BitSet(_tokenSet_9_data_);
	private static final long _tokenSet_10_data_[] = { -3456653316170186752L, 134221557L, 0L, 0L };
	public static final BitSet _tokenSet_10 = new BitSet(_tokenSet_10_data_);
	private static final long _tokenSet_11_data_[] = { -3436950067775275008L, 134221565L, 0L, 0L };
	public static final BitSet _tokenSet_11 = new BitSet(_tokenSet_11_data_);
	private static final long _tokenSet_12_data_[] = { -3456653442871721984L, 134221813L, 0L, 0L };
	public static final BitSet _tokenSet_12 = new BitSet(_tokenSet_12_data_);
	private static final long _tokenSet_13_data_[] = { 5768548172741738496L, 134219908L, 0L, 0L };
	public static final BitSet _tokenSet_13 = new BitSet(_tokenSet_13_data_);
	private static final long _tokenSet_14_data_[] = { -3438920527903719424L, 134219908L, 0L, 0L };
	public static final BitSet _tokenSet_14 = new BitSet(_tokenSet_14_data_);
	private static final long _tokenSet_15_data_[] = { -3457075663926722560L, 134219908L, 0L, 0L };
	public static final BitSet _tokenSet_15 = new BitSet(_tokenSet_15_data_);
	private static final long _tokenSet_16_data_[] = { 1829587348619264L, 1L, 0L, 0L };
	public static final BitSet _tokenSet_16 = new BitSet(_tokenSet_16_data_);
	private static final long _tokenSet_17_data_[] = { 18014398568202240L, 0L };
	public static final BitSet _tokenSet_17 = new BitSet(_tokenSet_17_data_);
	private static final long _tokenSet_18_data_[] = { 135291469824L, 1568L, 0L, 0L };
	public static final BitSet _tokenSet_18 = new BitSet(_tokenSet_18_data_);
	private static final long _tokenSet_19_data_[] = { -3439202002880430080L, 134221525L, 0L, 0L };
	public static final BitSet _tokenSet_19 = new BitSet(_tokenSet_19_data_);
	private static final long _tokenSet_20_data_[] = { -9222949824389709824L, 1617L, 0L, 0L };
	public static final BitSet _tokenSet_20 = new BitSet(_tokenSet_20_data_);
	private static final long _tokenSet_21_data_[] = { -3457216266123608064L, 134221565L, 0L, 0L };
	public static final BitSet _tokenSet_21 = new BitSet(_tokenSet_21_data_);
	private static final long _tokenSet_22_data_[] = { -9222949689098240000L, 1649L, 0L, 0L };
	public static final BitSet _tokenSet_22 = new BitSet(_tokenSet_22_data_);
	private static final long _tokenSet_23_data_[] = { 126701535232L, 32L, 0L, 0L };
	public static final BitSet _tokenSet_23 = new BitSet(_tokenSet_23_data_);
	private static final long _tokenSet_24_data_[] = { 18295873544912896L, 0L };
	public static final BitSet _tokenSet_24 = new BitSet(_tokenSet_24_data_);
	private static final long _tokenSet_25_data_[] = { -3457216401415077888L, 134221525L, 0L, 0L };
	public static final BitSet _tokenSet_25 = new BitSet(_tokenSet_25_data_);
	private static final long _tokenSet_26_data_[] = { -3454964466309922816L, 134221565L, 0L, 0L };
	public static final BitSet _tokenSet_26 = new BitSet(_tokenSet_26_data_);
	private static final long _tokenSet_27_data_[] = { 109521666048L, 32L, 0L, 0L };
	public static final BitSet _tokenSet_27 = new BitSet(_tokenSet_27_data_);
	private static final long _tokenSet_28_data_[] = { -9221823890123128832L, 1745L, 0L, 0L };
	public static final BitSet _tokenSet_28 = new BitSet(_tokenSet_28_data_);
	private static final long _tokenSet_29_data_[] = { -9222246136947933184L, 0L };
	public static final BitSet _tokenSet_29 = new BitSet(_tokenSet_29_data_);
	private static final long _tokenSet_30_data_[] = { -9221683186994511872L, 132L, 0L, 0L };
	public static final BitSet _tokenSet_30 = new BitSet(_tokenSet_30_data_);
	private static final long _tokenSet_31_data_[] = { 1688849860263936L, 128L, 0L, 0L };
	public static final BitSet _tokenSet_31 = new BitSet(_tokenSet_31_data_);
	private static final long _tokenSet_32_data_[] = { 0L, 49152L, 0L, 0L };
	public static final BitSet _tokenSet_32 = new BitSet(_tokenSet_32_data_);
	private static final long _tokenSet_33_data_[] = { 0L, 117637120L, 0L, 0L };
	public static final BitSet _tokenSet_33 = new BitSet(_tokenSet_33_data_);
	private static final long _tokenSet_34_data_[] = { 0L, 4980736L, 0L, 0L };
	public static final BitSet _tokenSet_34 = new BitSet(_tokenSet_34_data_);
	private static final long _tokenSet_35_data_[] = { 2814749767106560L, 0L };
	public static final BitSet _tokenSet_35 = new BitSet(_tokenSet_35_data_);
	
	}
