package io.github.abelgomez.cpntools.examples.simplejava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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


public class DAMLToCPN2 {
	private static Enumerated enumerated;
	private static Unit unit;
	private static Fusion fusion;
	
	public static void main(String args[]) throws SerializationException, IOException{		
		SContract contract = load(args[0]);
		System.out.println(contract.getTemplate().get(0).getName());
//		System.out.println(contract.getTemplate().size());
//		System.out.println(contract.getTemplate().get(0).getSignatory().size());
//		System.out.println(contract.getTemplate().get(1).getReferenced_template().getName());
		
		Cpnet net = buildCPN(contract);
		CpnToolsBuilder builder = new CpnToolsBuilder(net);
		builder.serialize(new FileOutputStream(new File(args[1])));
		
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
		
		HashSet<String> var_names = new HashSet<>();
		for(Template t: contract.getTemplate()) {		
			for(Party p:t.getSignatory()) {
				String s = p.getName();
				if(var_names.contains(s)) continue;
				var_names.add(s);
				v = CpntoolsFactory.eINSTANCE.createVar();
				v.setIdname(p.getName());
				v.setType(enumerated);
				b.getDeclarations().add(v);
				globbox.getDeclarations().add(b);
			}
			for(Choice c:t.getChoice()) {
				for(Party p:c.getController()) {
					String s = p.getName();
					if(var_names.contains(s)) continue;
					var_names.add(s);
					v = CpntoolsFactory.eINSTANCE.createVar();
					v.setIdname(p.getName());
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
		
		HashMap<String,Place> mapping = new HashMap<>();
		for(Template t: contract.getTemplate()) {
			if(t.getReferenced_template()==null)
				transform_template_none_referenced(page, t, mapping );
			else 
				transform_template_has_referenced(page, t, mapping );
		}
		
		
		/*
		 * Execute automatic layout
		 */
		page.layout(500, 500, 5000);
		return net;
	}
	
	public static void transform_template_none_referenced(Page page, Template t, HashMap<String,Place> mapping ) {
		Trans trans = CpntoolsFactory.eINSTANCE.createTrans();
		trans.setText(t.getName());
		page.getTranss().add(trans);
		for(Party p:t.getSignatory()) {
			transform_signatory(page, trans, p, mapping, t.getName());
		}
		
		for(Choice c:t.getChoice()) {
			Place p = transform_controller(page, trans, c.getController().get(0), c.getName(), null);
			linkTemplateWithController(page,trans,c.getController().get(0),p);
		}
		
	}
	
	public static void transform_template_has_referenced(Page page, Template t, HashMap<String,Place> mapping ) {
		Trans trans = CpntoolsFactory.eINSTANCE.createTrans();
		trans.setText(t.getName());
		page.getTranss().add(trans);
		for(Party p:t.getSignatory()) {
			transform_signatory_submit_version(page, trans, p, mapping);
		}
		
		for(Choice c:t.getChoice()) {
			Place p = transform_controller(page, trans, c.getController().get(0), c.getName(), mapping);
			linkTemplateWithController_submit_version(page,trans,c.getController().get(0),p);
		}
		
	}
	
	public static void transform_signatory(Page page, Trans trans, Party signatory, HashMap<String,Place> mapping, String template_name) {
		Cpnet net = page.getBinder().getCpnet();
		
		Place place2 = CpntoolsFactory.eINSTANCE.createPlace();
		place2.setText("P"+(page.getPlaces().size()+1));	
		place2.setType(enumerated);
		page.getPlaces().add(place2);
		
		char firstChar = Character.toLowerCase(template_name.charAt(0));
        String tname =  firstChar + template_name.substring(1);
		mapping.put(tname+"_"+signatory.getName(), place2);
		
		
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
		
		
	}
	
	public static void transform_signatory_submit_version(Page page, Trans trans, Party signatory, HashMap<String,Place> mapping) {
		Cpnet net = page.getBinder().getCpnet();
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P"+(page.getPlaces().size()+1));	
		place1.setType(enumerated);
		page.getPlaces().add(place1);
		
		
		Fusion f = CpntoolsFactory.eINSTANCE.createFusion();
		f.setName("Fusion 1");
		f.getPlaces().add(place1);
		net.getFusions().add(f);
		fusion = f;
		
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
		
//		Place place5 = CpntoolsFactory.eINSTANCE.createPlace();
//		place5.setText("P"+(page.getPlaces().size()+1));	
//		place5.setType(enumerated);
//		page.getPlaces().add(place5);
		
		Annot annot6 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot6.setText(signatory.getName());
		
		Arc arc7 = CpntoolsFactory.eINSTANCE.createArc();
		arc7.setPlace(mapping.get(signatory.getName()));
		arc7.setTrans(trans);
		arc7.setAnnot(annot6);
		arc7.setOrientation(Orientation.TTO_P);
		page.getArcs().add(arc7);
	}
	
	public static Place transform_controller(Page page, Trans trans, Party controller, String choiceName, HashMap<String,Place> mapping) {
		Cpnet net = page.getBinder().getCpnet();
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P"+(page.getPlaces().size()+1));	
		place1.setType(enumerated);
		page.getPlaces().add(place1);
		
		
//		Fusion f = CpntoolsFactory.eINSTANCE.createFusion();
//		f.setName("Fusion 1");
//		f.getPlaces().add(place1);
//		net.getFusions().add(f);
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
		
		if(mapping!=null) {
			Arc arc7 = CpntoolsFactory.eINSTANCE.createArc();
			arc7.setPlace(mapping.get(controller.getName()));
			arc7.setTrans(trans2);
			arc7.setOrientation(Orientation.TTO_P);
			Annot annot6 = CpntoolsFactory.eINSTANCE.createAnnot();
			annot6.setText(controller.getName());
			arc7.setAnnot(annot6);
			page.getArcs().add(arc7);
		}
		
		return place5;
	}
	
	public static void linkTemplateWithController(Page page, Trans trans, Party controller, Place p ) {
		Cpnet net = page.getBinder().getCpnet();
		
//		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
//		place1.setText("P"+(page.getPlaces().size()+1));	
//		place1.setType(enumerated);
//		page.getPlaces().add(place1);
//		
		
//		Fusion f = CpntoolsFactory.eINSTANCE.createFusion();
//		f.setName("Fusion 1");
//		f.getPlaces().add(place1);
//		net.getFusions().add(f);
//		fusion.getPlaces().add(place1);
//		
//		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
//		annot1.setText(controller.getName());
//
//		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
//		arc1.setPlace(place1);
//		arc1.setTrans(trans);
//		arc1.setOrientation(Orientation.PTO_T);
//		arc1.setAnnot(annot1);
//		page.getArcs().add(arc1);
//		
//		Arc arc2 = CpntoolsFactory.eINSTANCE.createArc();
//		arc2.setPlace(place1);
//		arc2.setTrans(trans);
//		arc2.setOrientation(Orientation.TTO_P);
//		Annot annot2 = CpntoolsFactory.eINSTANCE.createAnnot();
//		annot2.setText(controller.getName());
//		arc2.setAnnot(annot2);
//		page.getArcs().add(arc2);
//		
		Arc arc3 = CpntoolsFactory.eINSTANCE.createArc();
		arc3.setPlace(p);
		arc3.setTrans(trans);
		arc3.setOrientation(Orientation.TTO_P);
		Annot annot3 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot3.setText(controller.getName());
		arc3.setAnnot(annot3);
		page.getArcs().add(arc3);
		
		
	}
	
	public static void linkTemplateWithController_submit_version(Page page, Trans trans, Party controller, Place p ) {
		Cpnet net = page.getBinder().getCpnet();
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P"+(page.getPlaces().size()+1));	
		place1.setType(enumerated);
		page.getPlaces().add(place1);
		
		
//		Fusion f = CpntoolsFactory.eINSTANCE.createFusion();
//		f.setName("Fusion 1");
//		f.getPlaces().add(place1);
//		net.getFusions().add(f);
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
