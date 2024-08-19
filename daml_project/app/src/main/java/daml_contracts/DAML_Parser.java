package daml_contracts;
import daml.*;
import daml.impl.*;
import daml.util.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.util.Map;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;
import java.util.List;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DAML_Parser {
	
	public static Template current_template;
	public static Choice current_choice;
	public static boolean template_with_clause_open = false;
	
	public static void main(String args[]) {
//		String filePath = "test_1.daml";
//		SContract testModel = load(filePath);
//		System.out.println(testModel.getTemplate().get(0).getName());
//		Template t1 = DamlFactory.eINSTANCE.createTemplate();
//		t1.setName("TestTemplate");
//		testModel.getTemplate().add(t1);
//		save(testModel, filePath);
//		
//		System.out.println("---");
		
		String fileName = args[0];
		SContract sc = parseDAMLText(fileName);
		int dotIndex = fileName.lastIndexOf('.');
		String fileNameWithoutExtension = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
		save(sc, fileNameWithoutExtension+".daml");
		
	}
	
	public static SContract parseDAMLText(String filePath) {
		SContract sc = DamlFactory.eINSTANCE.createSContract();
        try {
            // Create a BufferedReader to read the file
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            // Read each line from the file
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                
                parseLine(sc,line);

            }

            // Close the BufferedReader
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }		
		
		return sc;
	}
	
    
    private static void parseLine(SContract sc,String line) {
    	
    	
    	Pattern pattern = Pattern.compile("[\\s:,]+");
    	String[] words = pattern.split(line.trim());
    	List<String> wordsList = Arrays.asList(words);
    	if(wordsList.contains("template")) {
    		Template t =  DamlFactory.eINSTANCE.createTemplate();
    		t.setName(wordsList.get(1));
    		sc.getTemplate().add(t);
    		current_template = t;
    		template_with_clause_open = true;
    	}
    	if(wordsList.contains("choice")) {
    		Choice c = DamlFactory.eINSTANCE.createChoice();
    		c.setName(wordsList.get(1));
    		current_template.getChoice().add(c);
    		current_choice = c;		
    	}
    	if(wordsList.contains("signatory")) {
    		int num_parties = wordsList.size()-1;
    		for(int i=1; i<= num_parties; i++) {
    			Party p = DamlFactory.eINSTANCE.createParty();
    			p.setName(wordsList.get(i));
    			sc.getParty().add(p);
    			current_template.getSignatory().add(p);
    		}
    	}
    	if(wordsList.contains("observer")) {
    		int num_parties = wordsList.size()-1;
    		for(int i=1; i<= num_parties; i++) {
    			Party p = DamlFactory.eINSTANCE.createParty();
    			p.setName(wordsList.get(i));
    			sc.getParty().add(p);
    			current_template.getObserver().add(p);
    		}
    	}   	
    	if(wordsList.contains("controller")) {
    		int num_parties = wordsList.size()-1;
    		for(int i=1; i<= num_parties; i++) {
    			
    			Party p = search(sc, wordsList.get(i));
    			if(p==null) {
    					p = DamlFactory.eINSTANCE.createParty();
    					p.setName(wordsList.get(i));
    					sc.getParty().add(p);
    			}
    			current_choice.getController().add(p);
    		}
    	}
    	if(wordsList.contains("where")) {
    		template_with_clause_open = false;
    	}
    	if(template_with_clause_open) {/* assumption: the referenced contract is defined before the current contract */
    		if(wordsList.contains("ContractId")) {
    			current_template.getReferenced_template().add(search_template(sc, wordsList.get(wordsList.size()-1)));
    		}
    		if(wordsList.size()==2) {
    			if(search_template(sc,wordsList.get(wordsList.size()-1))!=null && !wordsList.contains("template")) {
    				current_template.getReferenced_template().add(search_template(sc, wordsList.get(wordsList.size()-1)));
    			}
    		}
    	}
    	
    	if(wordsList.contains("exercise")) {
    		current_choice.getExercised_choice().add(search_choice(sc, wordsList.get(wordsList.size()-2)));
    	}
    	
    	if(wordsList.contains("create") && wordsList.size()==2 ) {
    		current_choice.getCreated_template().add(search_template(sc, current_template.getReferenced_template().get(0).getName()));
    	}
    	
    	System.out.println(Arrays.toString(words));
    	
    }
    
    private static Party search(SContract sc,String name) {
    	for(Party p:sc.getParty()) 
    		if(p.getName().equals(name))return p;
    	return null;
    }
    
    public static Template search_template(SContract sc, String name) {
    	for(Template t:sc.getTemplate())
    		if(t.getName().equals(name)) return t;
    	return null;
    }
    
    public static Choice search_choice(SContract sc, String name) {
    	for(Template t:sc.getTemplate())
    		for(Choice c: t.getChoice())
    			if(c.getName().equals(name)) return c;
    	return null;
    }
	
	
	public static SContract load(String filePath) {
		// Initialize the model
		DamlPackage.eINSTANCE.eClass();
		
		// Register the XMI resource factory for the .daml extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("daml", new XMIResourceFactoryImpl());
		
		// Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();
        // Get the resource
        Resource resource = resSet.getResource(URI.createURI(filePath), true);
        
        // Get the first model element and cast it to the right type, in my
        // example everything is hierarchical included in this first node
        SContract root = (SContract) resource.getContents().get(0);
        return root;
	}
	
	public static void save(SContract root, String filePath ) {
		// Register the XMI resource factory for the .daml extension

        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("daml", new XMIResourceFactoryImpl());

        // Obtain a new resource set
        ResourceSet resSet = new ResourceSetImpl();

        // create a resource
        Resource resource = resSet.createResource(URI.createURI("out_"+filePath));
        // Get the first model element and cast it to the right type, in my
        // example everything is hierarchical included in this first node
        resource.getContents().add(root);

        // now save the content.
        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
	}

}
