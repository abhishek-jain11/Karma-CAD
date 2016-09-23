
import java.util.Properties;

import jsdai.SApplication_context_schema.AProduct_context;
import jsdai.SApplication_context_schema.EProduct_context;
import jsdai.SProduct_definition_schema.AProduct;
import jsdai.SProduct_definition_schema.AProduct_definition_formation;
import jsdai.SProduct_definition_schema.CProduct_definition_formation;
import jsdai.SProduct_definition_schema.CProduct_definition_formation_with_specified_source;
import jsdai.SProduct_definition_schema.EProduct;
import jsdai.SProduct_definition_schema.EProduct_definition_formation;
import jsdai.SProduct_definition_schema.EProduct_definition_formation_with_specified_source;
import jsdai.lang.ASdaiModel;
import jsdai.lang.SdaiException;
import jsdai.lang.SdaiIterator;
import jsdai.lang.SdaiModel;
import jsdai.lang.SdaiRepository;
import jsdai.lang.SdaiSession;
import jsdai.lang.SdaiTransaction;

public class ReadStepFile {

	public static void main(String[] args) throws SdaiException{
	Properties prop = new Properties();
	prop.setProperty("repositories", "/Users/AJ/Documents/repo");
	SdaiSession.setSessionProperties(prop);
	SdaiSession session = SdaiSession.openSession();
	SdaiTransaction transaction = session.startTransactionReadWriteAccess();
	
	SdaiRepository repository = session.importClearTextEncoding("MyRepo3", "/Users/AJ/Documents/Karma-CAD/Circle3_FreeCAD.step", null);
	transaction.commit();
	
	ASdaiModel models = repository.getModels();
	
	SdaiIterator modelIterator = models.createIterator();
	while(modelIterator.next()){
		SdaiModel model = models.getCurrentMember(modelIterator);
		if(model.getMode()== SdaiModel.NO_ACCESS){
			model.startReadOnlyAccess();
		}
		
		System.out.println("Model found "+model.getName());
		
		AProduct products = (AProduct) model.getInstances(EProduct.class);
		
		SdaiIterator productIterator = products.createIterator();
		while(productIterator!=null &&productIterator.next()){
			EProduct product = products.getCurrentMember(productIterator);
			
			System.out.println("Product Instance number "+product.getPersistentLabel());
			System.out.println("ID: "+product.getId(null));
			System.out.println("Name: "+product.getName(null));
			
			if(product.testDescription(null)){
				System.out.println(product.getDescription(null));
			}
			
			AProduct_context productContexts = product.getFrame_of_reference(null);
			
			System.out.println("Frame of reference "+productContexts.getMemberCount());
			
			SdaiIterator productContextIterator = productContexts.createIterator();
			while(productContextIterator.next()){
				EProduct_context productContext  =productContexts.getCurrentMember(productContextIterator);
				System.out.println("Name "+productContext.getName(null));
				System.out.println(" discipline type "+productContext.getDiscipline_type(null));
			}
			
			AProduct_definition_formation formations = new AProduct_definition_formation();
			
			CProduct_definition_formation.usedinOf_product(null, product, null, formations);
			System.out.println("Users of product "+formations.getMemberCount() );
			
			SdaiIterator formationIterator = formations.createIterator();
			while(formationIterator.next()){
				EProduct_definition_formation formation = formations.getCurrentMember(formationIterator);
				System.out.println(" Instance no. "+formation.getPersistentLabel());
				System.out.println(" ID: "+formation.getId(null));
				if(formation.testDescription(null)){
					System.out.println(" Description "+formation.getDescription(null));
				}
				if(formation.isKindOf(CProduct_definition_formation_with_specified_source.class)){
					EProduct_definition_formation_with_specified_source formationWSource =(EProduct_definition_formation_with_specified_source) formation;
					
					System.out.println(" Make or buy "+formationWSource.getMake_or_buy(null));
					
					
				}else{
					System.out.println(" No specified source available");
				}
			}
			
			if(model.getMode() == SdaiModel.READ_ONLY){
				model.endReadOnlyAccess();
			}else if(model.getMode() == SdaiModel.READ_WRITE){
				model.endReadWriteAccess();
			}
		}
		repository.closeRepository();
		repository.deleteRepository();
		transaction.endTransactionAccessCommit();
		session.closeSession();
		System.out.println("Done");
	}
	}
}
