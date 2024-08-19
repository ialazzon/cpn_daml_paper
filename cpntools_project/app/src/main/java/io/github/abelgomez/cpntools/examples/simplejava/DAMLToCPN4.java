package io.github.abelgomez.cpntools.examples.simplejava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import daml.Choice;
import daml.DamlPackage;
import daml.Party;
import daml.SContract;
import daml.Template;
import io.github.abelgomez.cpntools.Annot;
import io.github.abelgomez.cpntools.Arc;
import io.github.abelgomez.cpntools.Binder;
import io.github.abelgomez.cpntools.Block;
import io.github.abelgomez.cpntools.Cpnet;
import io.github.abelgomez.cpntools.CpntoolsFactory;
import io.github.abelgomez.cpntools.Enumerated;
import io.github.abelgomez.cpntools.Fusion;
import io.github.abelgomez.cpntools.Globbox;
import io.github.abelgomez.cpntools.Orientation;
import io.github.abelgomez.cpntools.Page;
import io.github.abelgomez.cpntools.Place;
import io.github.abelgomez.cpntools.Trans;
import io.github.abelgomez.cpntools.TransCond;
import io.github.abelgomez.cpntools.Unit;
import io.github.abelgomez.cpntools.Var;
import io.github.abelgomez.cpntools.io.serializer.CpnToolsBuilder;
import io.github.abelgomez.cpntools.io.serializer.SerializationException;

public class DAMLToCPN4 {
	private static Enumerated enumerated;
	private static Unit unit;
	private static Fusion fusion;
	private static Map<Template,Trans> map1 = new HashMap<>();//maps a template to its corresponding transaction in the CPN
	private static Map<Map<Template,Party>,Place> map2 = new HashMap<>();
	private static Map<String,Trans> map3 = new HashMap<>();//maps a choice to its corresponding transaction in the CPN
	private static HashSet<String> party_names = new HashSet<>();
	private static boolean alreadyCreated = false;
	private static Map<Choice, Place> map4 = new HashMap<>();//maps a choice to its corresponding input place; used in add_input_place method
	
	public static void main(String args[]) throws SerializationException, IOException{		
		SContract contract = load(args[0]);
		Cpnet net = buildCPN(contract);
		CpnToolsBuilder builder = new CpnToolsBuilder(net);
		builder.serialize(new FileOutputStream(new File(args[1])));
		System.out.println("DONE");
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
	
	public static Cpnet buildCPN(SContract contract) {
		Cpnet net = CpntoolsFactory.eINSTANCE.createCpnet();
		Page page = CpntoolsFactory.eINSTANCE.createPage();
		Globbox globbox = CpntoolsFactory.eINSTANCE.createGlobbox();
		Binder binder = CpntoolsFactory.eINSTANCE.createBinder();
		page.setName("Page");
		binder.setPosx(150);
		binder.setPosy(30);
		binder.setWidth(500);
		binder.setHeight(500);
		binder.getPages().add(page);
		net.setGlobbox(globbox);
		net.setBinder(binder);
		
		enumerated = CpntoolsFactory.eINSTANCE.createEnumerated();
		enumerated.setIdname("Party");
		enumerated.setColorSetType("Party");
		enumerated.getWith().add("alice");
		enumerated.getWith().add("bob");
		globbox.getDeclarations().add(enumerated);
		
		unit = CpntoolsFactory.eINSTANCE.createUnit();
		unit.setIdname("UNIT");
		
		Block b_std = CpntoolsFactory.eINSTANCE.createBlock();
		b_std.setIdname("Standard declarations");
		b_std.getDeclarations().add(unit);
		globbox.getDeclarations().add(b_std);
		
		Block b = CpntoolsFactory.eINSTANCE.createBlock();
		b.setIdname("Block 1");
		b.getDeclarations().add(enumerated);
		Var v = CpntoolsFactory.eINSTANCE.createVar();
		v.setIdname("p");
		v.setType(enumerated);
		b.getDeclarations().add(v);
		
		for(Template t: contract.getTemplate()) {		
			for(Party p:t.getSignatory()) {
				if(!party_names.contains(p.getName())) {
					v = CpntoolsFactory.eINSTANCE.createVar();
					v.setIdname(p.getName());
					v.setType(enumerated);
					b.getDeclarations().add(v);
					globbox.getDeclarations().add(b);
					party_names.add(p.getName());
				}
			}
			if(t.getReferenced_template().size()!=0 || t.getSignatory().size()<2) {
				for(Choice c:t.getChoice()) {
					for(Party p:c.getController()) {
						if(!party_names.contains(p.getName())) {	
							v = CpntoolsFactory.eINSTANCE.createVar();
							v.setIdname(p.getName());
							v.setType(enumerated);
							b.getDeclarations().add(v);
							globbox.getDeclarations().add(b);
							party_names.add(p.getName());
						} 
					}
				}
			}
			if(!isSimpleTemplate(contract, t) && t.getSignatory().size()>1) {
				for(int i=1; i<=t.getSignatory().size();i++) {
					v = CpntoolsFactory.eINSTANCE.createVar();
					v.setIdname("signatory"+i);
					v.setType(enumerated);
					b.getDeclarations().add(v);
					globbox.getDeclarations().add(b);
				}
			}
		}
				
		v = CpntoolsFactory.eINSTANCE.createVar();
		v.setIdname("signatory");
		v.setType(enumerated);
		b.getDeclarations().add(v);
		globbox.getDeclarations().add(b);
		
		v = CpntoolsFactory.eINSTANCE.createVar();
		v.setIdname("controller");
		v.setType(enumerated);
		b.getDeclarations().add(v);
		globbox.getDeclarations().add(b);
		
		
		Fusion f = CpntoolsFactory.eINSTANCE.createFusion();
		f.setName("Fusion 1");
		net.getFusions().add(f);
		fusion = f;		

		
		for(Template t: contract.getTemplate())
			transform_template(page, t, contract );
		
		
		/*
		 * Execute automatic layout
		 */
		page.layout(500, 500, 5000);
		return net;
	}
	
	public static void transform_template(Page page, Template t, SContract contract ) {
		Trans trans = CpntoolsFactory.eINSTANCE.createTrans();
		trans.setText(t.getName());
		page.getTranss().add(trans);
		System.out.println(t.getName()+","+isSimpleTemplate(contract,t));
		map1.put(t, trans);
		if(isSimpleTemplate(contract,t)) {
				Place p1  =transform_signatory(page, trans, t.getSignatory().get(0));
			
			for(Choice c:t.getChoice()) {
				Tuple<Place,Place> tp = transform_controller(page, trans, c.getController().get(0), c.getName());
				linkTemplateWithController(page,trans,c.getController().get(0),tp.getFirst());
				if(c.getCreated_template().size()>0) {
					Party sign = c.getCreated_template().get(0).getSignatory().get(0);
					HashMap<Template,Party> tpm = new HashMap<>();
					tpm.put(c.getCreated_template().get(0),sign);
					map2.put(tpm, p1);
					Party crtl = c.getCreated_template().get(0).getSignatory().get(1);
					tpm = new HashMap<>();
					tpm.put(c.getCreated_template().get(0),crtl);
					map2.put(tpm, tp.getSecond());
					instantiateCreatedTemplate(page, map1.get(c.getCreated_template().get(0)), c.getCreated_template().get(0));
				}
				if(c.getExercised_choice().size()>0) {
//					if(getExecising_choices(c.getExercised_choice().get(0), contract).size()>1) {
					Place p_t = add_input_place(page, c.getExercised_choice().get(0),c.getController().get(0).getName());
					System.out.println("--- "+ p_t.getText()+ " - "+map3.get(c.getExercised_choice().get(0).getName()).getText());
//					}
					linkChoice2Choice(page,tp.getSecond(),p_t,c.getController().get(0).getName());
				}
			}
		}else {/* The case that the template is not simple i.e. it is referenced by another template.*/
			if(t.getSignatory().size()<2) {
				Place p1  =transform_signatory(page, trans, t.getSignatory().get(0));
				for(Choice c:t.getChoice()) {
					List<Choice> execising_choices = getExecising_choices(c,contract);
					if(execising_choices.size()>0) {						
						Choice first_execising_choice = execising_choices.get(0);//assuming that a choice can be exercised by at most one other choice
						String cond = "["+ findParty(first_execising_choice.getController().get(0).getName().substring(t.getName().length()+1),contract).getName() +"= "+ first_execising_choice.getController().get(0).getName() + "]";
						Tuple<Place,Place> tp = transform_controller_multi_control(page, trans, findParty(first_execising_choice.getController().get(0).getName().substring(t.getName().length()+1),contract), c.getName(),cond);
						linkTemplateWithController(page,trans,findParty(first_execising_choice.getController().get(0).getName().substring(t.getName().length()+1),contract),tp.getFirst());
					}
					if(!is_exercised_choice(c,contract)) {
						if(c.getController().size()>1) {
							for(Party p: c.getController()) {
								if(p!=t.getSignatory().get(0)) {
									transform_controller_single(page, map3.get(c.getName()), p);
								}
							}
						}
					}
					
				}
			}else {/* case that there are >1 signatories */
				if(!is_referenced_template(t, contract)) {
					int i=1;
					for(Party p: t.getSignatory()) {
						Place p1  =transform_signatory_single(page, trans, p, "signatory"+i);
						i++;
					}
					for(Choice c:t.getChoice()) {
						Tuple<Place,Place> tp = transform_controller_multi_control(page, trans, c.getController().get(0), c.getName(),"");
						linkTemplateWithController(page,trans,c.getController().get(0),tp.getFirst());
						
					}
				}
			}
		}
		
	}
	
	public static String getCallingChoice(Choice c_called,SContract contract) {
		for(Template t:contract.getTemplate())
			for(Choice c: t.getChoice())
				if(c.getName().equals(c_called.getName()))
					return c.getName();
		return null;
				
	}
	
	public static boolean isSimpleTemplate(SContract contract, Template t) {
		
		for(Template t1:contract.getTemplate()) 
			if(t1.getReferenced_template().size()>0 && t1.getReferenced_template().get(0)==t)
				return false;
		
		if(t.getSignatory().size()>1)
			return false;
		
		for(Choice c: t.getChoice())
			if(c.getController().size()>1)
				return false;
		
		return true;
	}
	
	public static boolean is_exercised_choice(Choice c, SContract contract) {
		for(Template t:contract.getTemplate()) 
			for(Choice ch: t.getChoice())
				if(ch.getExercised_choice().contains(c))
					return true;
		return false;
			
	}
	
	public static boolean is_referenced_template(Template t, SContract contract) {
		for(Template t1:contract.getTemplate()) 
			if(t1.getReferenced_template().contains(t))
				return true;
		return false;
	}
	
	public static void linkChoice2Choice(Page page, Place p_s, Place p_t, String controller_name) {
		
		Trans trans1 = CpntoolsFactory.eINSTANCE.createTrans();
		trans1.setText("T"+(page.getTranss().size()+1));
		page.getTranss().add(trans1);
		
		
		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot1.setText(controller_name);

		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
		arc1.setPlace(p_s);
		arc1.setTrans(trans1);
		arc1.setOrientation(Orientation.PTO_T);
		arc1.setAnnot(annot1);
		page.getArcs().add(arc1);
		
		Annot annot2 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot2.setText(controller_name);
		Arc arc2 = CpntoolsFactory.eINSTANCE.createArc();
		arc2.setPlace(p_t);
		arc2.setTrans(trans1);
		arc2.setOrientation(Orientation.TTO_P);
		arc2.setAnnot(annot2);
		page.getArcs().add(arc2);
		
		
	}
	
	public static List<Choice> getExecising_choices(Choice c, SContract contract){
		List<Choice> list_choices = new ArrayList<>();
		for(Template t:contract.getTemplate()) 
			for(Choice ch: t.getChoice())
				if(ch.getExercised_choice().contains(c))
					list_choices.add(ch);
		return list_choices;
	
	}
	
	public static Party findParty(String name, SContract contract) {
		for(Party p: contract.getParty()) {
			if(p.getName().equals(name))
				return p;
		}
		return null;
	}
	
	public static Place add_input_place(Page page, Choice c, String name) {
		if(!alreadyCreated) {	
			Trans t = map3.get(c.getName());
			Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
			place1.setText("P"+(page.getPlaces().size()+1));	
			place1.setType(enumerated);
			page.getPlaces().add(place1);
			
			Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
			annot1.setText(name);
			Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
			arc1.setPlace(place1);
			arc1.setTrans(t);
			arc1.setOrientation(Orientation.PTO_T);
			arc1.setAnnot(annot1);
			page.getArcs().add(arc1);
			map4.put(c, place1);
			alreadyCreated = true;
			return place1;
		}else
			return map4.get(c);
		
	}
	
	public static void instantiateCreatedTemplate(Page page, Trans trans, Template t) {
		HashMap<Template,Party> tpm = new HashMap<>();
		tpm.put(t, t.getSignatory().get(0));
		Place p1 = map2.get(tpm);
		tpm = new HashMap<>();
		tpm.put(t, t.getSignatory().get(1));
		Place p2 = map2.get(tpm);
		
		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot1.setText("signatory1");

		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
		arc1.setPlace(p1);
		arc1.setTrans(trans);
		arc1.setOrientation(Orientation.PTO_T);
		arc1.setAnnot(annot1);
		page.getArcs().add(arc1);
		
		Annot annot2 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot2.setText("signatory2");

		Arc arc2 = CpntoolsFactory.eINSTANCE.createArc();
		arc2.setPlace(p2);
		arc2.setTrans(trans);
		arc2.setOrientation(Orientation.PTO_T);
		arc2.setAnnot(annot2);
		page.getArcs().add(arc2);
		
		for(Choice c:t.getChoice()) {
			Tuple<Place,Place> tp = transform_controller(page, trans, c.getController().get(0), c.getName());
			linkTemplateWithController(page,trans,c.getController().get(0),tp.getFirst());
		}
	}
	
	public static Place transform_signatory(Page page, Trans trans, Party signatory) {
		Cpnet net = page.getBinder().getCpnet();
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P"+(page.getPlaces().size()+1));	
		place1.setType(enumerated);
		page.getPlaces().add(place1);
		
		
		fusion.getPlaces().add(place1);

		
		Trans trans1 = CpntoolsFactory.eINSTANCE.createTrans();
		trans1.setText("T"+(page.getTranss().size()+1));
		page.getTranss().add(trans1);
		
		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot1.setText("p");

		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
		arc1.setPlace(place1);
		arc1.setTrans(trans1);
		arc1.setOrientation(Orientation.PTO_T);
		arc1.setAnnot(annot1);
		page.getArcs().add(arc1);
		
		Arc arc2 = CpntoolsFactory.eINSTANCE.createArc();
		arc2.setPlace(place1);
		arc2.setTrans(trans1);
		arc2.setOrientation(Orientation.TTO_P);
		Annot annot2 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot2.setText("p");
		arc2.setAnnot(annot2);
		page.getArcs().add(arc2);
		
		Place place2 = CpntoolsFactory.eINSTANCE.createPlace();
		place2.setText("P"+(page.getPlaces().size()+1));	
		place2.setType(enumerated);
		page.getPlaces().add(place2);
		
		Arc arc3 = CpntoolsFactory.eINSTANCE.createArc();
		arc3.setPlace(place2);
		arc3.setTrans(trans1);
		arc3.setOrientation(Orientation.TTO_P);
		Annot annot3 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot3.setText("p");
		arc3.setAnnot(annot3);
		page.getArcs().add(arc3);
		
		Annot annot4 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot4.setText(signatory.getName());
		
		Arc arc4 = CpntoolsFactory.eINSTANCE.createArc();
		arc4.setPlace(place2);
		arc4.setTrans(trans);
		arc4.setOrientation(Orientation.PTO_T);
		arc4.setAnnot(annot4);
		page.getArcs().add(arc4);
		
		TransCond tc = CpntoolsFactory.eINSTANCE.createTransCond();
		tc.setText("[signatory = "+ signatory.getName() + "]");
		trans.setCond(tc);
		
		Place place3 = CpntoolsFactory.eINSTANCE.createPlace();
		place3.setText("P"+(page.getPlaces().size()+1));	
		place3.setType(enumerated);
		page.getPlaces().add(place3);
		
		
		Annot annot5 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot5.setText("signatory");
		
		Arc arc5 = CpntoolsFactory.eINSTANCE.createArc();
		arc5.setPlace(place3);
		arc5.setTrans(trans);
		arc5.setOrientation(Orientation.PTO_T);
		arc5.setAnnot(annot5);
		page.getArcs().add(arc5);
		
		Place place4 = CpntoolsFactory.eINSTANCE.createPlace();
		place4.setText("P"+(page.getPlaces().size()+1));	
		page.getPlaces().add(place4);
		
		Arc arc6 = CpntoolsFactory.eINSTANCE.createArc();
		arc6.setPlace(place4);
		arc6.setTrans(trans1);
		arc6.setOrientation(Orientation.PTO_T);
		page.getArcs().add(arc6);
		
		Place place5 = CpntoolsFactory.eINSTANCE.createPlace();
		place5.setText("P"+(page.getPlaces().size()+1));	
		place5.setType(enumerated);
		page.getPlaces().add(place5);
		
		Annot annot6 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot6.setText(signatory.getName());
		
		Arc arc7 = CpntoolsFactory.eINSTANCE.createArc();
		arc7.setPlace(place5);
		arc7.setTrans(trans);
		arc7.setAnnot(annot6);
		arc7.setOrientation(Orientation.TTO_P);
		page.getArcs().add(arc7);
		
		return place5;
	}
	
	public static Place transform_signatory_single(Page page, Trans trans, Party signatory, String name) {
		Cpnet net = page.getBinder().getCpnet();
		
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P"+(page.getPlaces().size()+1));	
		place1.setType(enumerated);
		page.getPlaces().add(place1);
		
		
		
		
		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot1.setText(name);

		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
		arc1.setPlace(place1);
		arc1.setTrans(trans);
		arc1.setOrientation(Orientation.PTO_T);
		arc1.setAnnot(annot1);
		page.getArcs().add(arc1);
		
		
		
		return place1;
	}

	public static Place transform_controller_single(Page page, Trans trans, Party controller) {
		Cpnet net = page.getBinder().getCpnet();
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P"+(page.getPlaces().size()+1));	
		place1.setType(enumerated);
		page.getPlaces().add(place1);
		
		
		
		
		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot1.setText(controller.getName());

		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
		arc1.setPlace(place1);
		arc1.setTrans(trans);
		arc1.setOrientation(Orientation.PTO_T);
		arc1.setAnnot(annot1);
		page.getArcs().add(arc1);
		
		
		
		return place1;
	}
	
	public static Tuple<Place,Place> transform_controller(Page page, Trans trans, Party controller, String choiceName) {
		Cpnet net = page.getBinder().getCpnet();
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P"+(page.getPlaces().size()+1));	
		place1.setType(enumerated);
		page.getPlaces().add(place1);
		
		
		fusion.getPlaces().add(place1);
		
		Trans trans1 = CpntoolsFactory.eINSTANCE.createTrans();
		trans1.setText("T"+(page.getTranss().size()+1));
		page.getTranss().add(trans1);
		
		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot1.setText("p");

		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
		arc1.setPlace(place1);
		arc1.setTrans(trans1);
		arc1.setOrientation(Orientation.PTO_T);
		arc1.setAnnot(annot1);
		page.getArcs().add(arc1);
		
		Arc arc2 = CpntoolsFactory.eINSTANCE.createArc();
		arc2.setPlace(place1);
		arc2.setTrans(trans1);
		arc2.setOrientation(Orientation.TTO_P);
		Annot annot2 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot2.setText("p");
		arc2.setAnnot(annot2);
		page.getArcs().add(arc2);
		
		Place place2 = CpntoolsFactory.eINSTANCE.createPlace();
		place2.setText("P"+(page.getPlaces().size()+1));	
		place2.setType(enumerated);
		page.getPlaces().add(place2);
		
		Arc arc3 = CpntoolsFactory.eINSTANCE.createArc();
		arc3.setPlace(place2);
		arc3.setTrans(trans1);
		arc3.setOrientation(Orientation.TTO_P);
		Annot annot3 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot3.setText("p");
		arc3.setAnnot(annot3);
		page.getArcs().add(arc3);
		
		Place place4 = CpntoolsFactory.eINSTANCE.createPlace();
		place4.setText("P"+(page.getPlaces().size()+1));	
		page.getPlaces().add(place4);
		
		Arc arc6 = CpntoolsFactory.eINSTANCE.createArc();
		arc6.setPlace(place4);
		arc6.setTrans(trans1);
		arc6.setOrientation(Orientation.PTO_T);
		page.getArcs().add(arc6);
		
		Trans trans2 = CpntoolsFactory.eINSTANCE.createTrans();
		trans2.setText(choiceName);
		page.getTranss().add(trans2);
		map3.put(choiceName, trans2);
		
		Arc arc4 = CpntoolsFactory.eINSTANCE.createArc();
		arc4.setPlace(place2);
		arc4.setTrans(trans2);
		arc4.setOrientation(Orientation.PTO_T);
		Annot annot4 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot4.setText("controller");
		arc4.setAnnot(annot4);
		page.getArcs().add(arc4);
		
		TransCond tc = CpntoolsFactory.eINSTANCE.createTransCond();
		tc.setText("[controller= "+ controller.getName() + "]");
		trans2.setCond(tc);
		
		Place place5 = CpntoolsFactory.eINSTANCE.createPlace();
		place5.setText("P"+(page.getPlaces().size()+1));
		place5.setType(enumerated);
		page.getPlaces().add(place5);
		
		Arc arc5 = CpntoolsFactory.eINSTANCE.createArc();
		arc5.setPlace(place5);
		arc5.setTrans(trans2);
		arc5.setOrientation(Orientation.PTO_T);
		Annot annot5 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot5.setText(controller.getName());
		arc5.setAnnot(annot5);
		page.getArcs().add(arc5);
		
		Place place6 = CpntoolsFactory.eINSTANCE.createPlace();
		place6.setText("P"+(page.getPlaces().size()+1));	
		place6.setType(enumerated);
		page.getPlaces().add(place6);
		
		Arc arc7 = CpntoolsFactory.eINSTANCE.createArc();
		arc7.setPlace(place6);
		arc7.setTrans(trans2);
		arc7.setOrientation(Orientation.TTO_P);
		Annot annot6 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot6.setText(controller.getName());
		arc7.setAnnot(annot6);
		page.getArcs().add(arc7);
		
		return new Tuple(place5,place6);
	}
	
	public static Tuple<Place,Place> transform_controller_multi_control(Page page, Trans trans, Party controller, String choiceName, String cond) {
		Cpnet net = page.getBinder().getCpnet();
				
		Trans trans2 = CpntoolsFactory.eINSTANCE.createTrans();
		trans2.setText(choiceName);
		TransCond tc = CpntoolsFactory.eINSTANCE.createTransCond();
		tc.setText(cond);
		trans2.setCond(tc);
		page.getTranss().add(trans2);
		map3.put(choiceName, trans2);
		
		Place place5 = CpntoolsFactory.eINSTANCE.createPlace();
		place5.setText("P"+(page.getPlaces().size()+1));
		place5.setType(enumerated);
		page.getPlaces().add(place5);
		
		Arc arc5 = CpntoolsFactory.eINSTANCE.createArc();
		arc5.setPlace(place5);
		arc5.setTrans(trans2);
		arc5.setOrientation(Orientation.PTO_T);
		Annot annot5 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot5.setText(controller.getName());
		arc5.setAnnot(annot5);
		page.getArcs().add(arc5);
		
		Place place6 = CpntoolsFactory.eINSTANCE.createPlace();
		place6.setText("P"+(page.getPlaces().size()+1));	
		place6.setType(enumerated);
		page.getPlaces().add(place6);
		
		Arc arc7 = CpntoolsFactory.eINSTANCE.createArc();
		arc7.setPlace(place6);
		arc7.setTrans(trans2);
		arc7.setOrientation(Orientation.TTO_P);
		Annot annot6 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot6.setText(controller.getName());
		arc7.setAnnot(annot6);
		page.getArcs().add(arc7);
		
		return new Tuple(place5,place6);
	}
	
	public static void linkTemplateWithController(Page page, Trans trans, Party controller, Place p ) {
		Cpnet net = page.getBinder().getCpnet();
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P"+(page.getPlaces().size()+1));	
		place1.setType(enumerated);
		page.getPlaces().add(place1);
		
		
		fusion.getPlaces().add(place1);
		
		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot1.setText(controller.getName());

		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
		arc1.setPlace(place1);
		arc1.setTrans(trans);
		arc1.setOrientation(Orientation.PTO_T);
		arc1.setAnnot(annot1);
		page.getArcs().add(arc1);
		
		Arc arc2 = CpntoolsFactory.eINSTANCE.createArc();
		arc2.setPlace(place1);
		arc2.setTrans(trans);
		arc2.setOrientation(Orientation.TTO_P);
		Annot annot2 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot2.setText(controller.getName());
		arc2.setAnnot(annot2);
		page.getArcs().add(arc2);
		
		Arc arc3 = CpntoolsFactory.eINSTANCE.createArc();
		arc3.setPlace(p);
		arc3.setTrans(trans);
		arc3.setOrientation(Orientation.TTO_P);
		Annot annot3 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot3.setText(controller.getName());
		arc3.setAnnot(annot3);
		page.getArcs().add(arc3);
		
		
	}
}

class Tuple<T1, T2> {
    private final T1 first;
    private final T2 second;

    public Tuple(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }


    @Override
    public String toString() {
        return "Triplet{" +
               "first=" + first +
               ", second=" + second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> triplet = (Tuple<?, ?>) o;

        if (!first.equals(triplet.first)) return false;
        if (!second.equals(triplet.second)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

}

