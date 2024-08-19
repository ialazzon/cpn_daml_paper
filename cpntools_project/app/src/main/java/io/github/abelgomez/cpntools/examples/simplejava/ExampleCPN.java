package io.github.abelgomez.cpntools.examples.simplejava;

import io.github.abelgomez.cpntools.Annot;
import io.github.abelgomez.cpntools.Arc;
import io.github.abelgomez.cpntools.Binder;
import io.github.abelgomez.cpntools.Block;
import io.github.abelgomez.cpntools.Cpnet;
import io.github.abelgomez.cpntools.CpntoolsFactory;
import io.github.abelgomez.cpntools.Enumerated;
import io.github.abelgomez.cpntools.Fusion;
import io.github.abelgomez.cpntools.Globbox;
import io.github.abelgomez.cpntools.Initmark;
import io.github.abelgomez.cpntools.Orientation;
import io.github.abelgomez.cpntools.Page;
import io.github.abelgomez.cpntools.Place;
import io.github.abelgomez.cpntools.Trans;
import io.github.abelgomez.cpntools.TransCond;

public class ExampleCPN {

	public static Cpnet buildSampleNet() {
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
		
		

		Enumerated enumerated = CpntoolsFactory.eINSTANCE.createEnumerated();
		enumerated.setIdname("Party");
		enumerated.setColorSetType("Party");
		enumerated.getWith().add("alice");
		enumerated.getWith().add("bob");
		globbox.getDeclarations().add(enumerated);
		
		Block b = CpntoolsFactory.eINSTANCE.createBlock();
		b.setIdname("Block 1");
		b.getDeclarations().add(enumerated);
		globbox.getDeclarations().add(b);
		
		Initmark im1 = CpntoolsFactory.eINSTANCE.createInitmark();
		im1.setExpression("1`alice");
		
		Place place1 = CpntoolsFactory.eINSTANCE.createPlace();
		place1.setText("P1");	
		place1.setType(enumerated);
		place1.setInitmark(im1);

		Trans trans1 = CpntoolsFactory.eINSTANCE.createTrans();
		trans1.setText("T1");
		TransCond tc = CpntoolsFactory.eINSTANCE.createTransCond();
		tc.setText("[p = alice]");
		trans1.setCond(tc);
		

		Annot annot1 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot1.setText("p");

		Arc arc1 = CpntoolsFactory.eINSTANCE.createArc();
		arc1.setPlace(place1);
		arc1.setTrans(trans1);
		arc1.setOrientation(Orientation.PTO_T);
		arc1.setAnnot(annot1);
		
		page.getPlaces().add(place1);
		page.getTranss().add(trans1);
		page.getArcs().add(arc1);
		
		Place place2 = CpntoolsFactory.eINSTANCE.createPlace();
		place2.setText("P2");	
		place2.setType(enumerated);
		
		Annot annot2 = CpntoolsFactory.eINSTANCE.createAnnot();
		annot2.setText("p");
		
		Arc arc2 = CpntoolsFactory.eINSTANCE.createArc();
		arc2.setPlace(place2);
		arc2.setTrans(trans1);
		arc2.setOrientation(Orientation.TTO_P);
		arc2.setAnnot(annot2);
		
		
		page.getPlaces().add(place2);
		page.getArcs().add(arc2);
	
		Initmark im2 = CpntoolsFactory.eINSTANCE.createInitmark();
		im2.setExpression("1`alice");
		
		Place place3 = CpntoolsFactory.eINSTANCE.createPlace();
//		place1.setText("P");	
		place3.setType(enumerated);
		place3.setInitmark(im2);
		
		Fusion f = CpntoolsFactory.eINSTANCE.createFusion();
		f.setName("Fusion 1");
		f.getPlaces().add(place1);
		f.getPlaces().add(place3);
		page.getPlaces().add(place3);
		net.getFusions().add(f);
		
		/*
		 * Execute automatic layout
		 */
		page.layout(500, 500, 5000);
		return net;
	}

	
}
