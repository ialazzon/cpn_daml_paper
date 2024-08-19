/*******************************************************************************
 * Copyright (c) 2021 Abel Gómez.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Abel Gómez (abgolla@gmail.com) - initial API and implementation
 *******************************************************************************/
package io.github.abelgomez.cpntools.io.serializer;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import io.github.abelgomez.cpntools.Alias;
import io.github.abelgomez.cpntools.Annot;
import io.github.abelgomez.cpntools.Arc;
import io.github.abelgomez.cpntools.Binder;
import io.github.abelgomez.cpntools.Block;
import io.github.abelgomez.cpntools.ColorSet;
import io.github.abelgomez.cpntools.Cpnet;
import io.github.abelgomez.cpntools.Declaration;
import io.github.abelgomez.cpntools.DiagramElement;
import io.github.abelgomez.cpntools.Enumerated;
import io.github.abelgomez.cpntools.Fusion;
import io.github.abelgomez.cpntools.Globbox;
import io.github.abelgomez.cpntools.Initmark;
import io.github.abelgomez.cpntools.Ml;
import io.github.abelgomez.cpntools.Page;
import io.github.abelgomez.cpntools.Place;
import io.github.abelgomez.cpntools.Product;
import io.github.abelgomez.cpntools.SimpleColorSet;
import io.github.abelgomez.cpntools.Trans;
import io.github.abelgomez.cpntools.TransCond;
import io.github.abelgomez.cpntools.TransPriority;
import io.github.abelgomez.cpntools.TransTime;
import io.github.abelgomez.cpntools.Unit;
import io.github.abelgomez.cpntools.Var;


/**
 * 
 * Class used to serialice a {@link Cpnet} into a normative CPN Tools XML file.
 * 
 * @author Abel G�mez (agomezlla@uoc.edu)
 *
 */
public class CpnToolsBuilder {
	
	private static final String TOOL_NAME = "CPN Tools";
	private static final String TOOL_VERSION = "4.0.1";
	private static final String TOOL_FORMAT = "6";
	private static final Float TOKEN_POS_Y = 0.0f;
	private static final Float TOKEN_POS_X = -10.0f;
	private static final Float MARKING_POS_X = 0.0f;
	private static final Float MARKING_POS_Y = 0.0f;
	private static final Float BINDING_POS_X = 7.2f;
	private static final Float BINDING_POS_Y = -3.0f;

	private Document document = null;
	private Cpnet cpnet = null;
	
	/**
	 * Creates a {@link CpnToolsBuilder} out of a {@link Cpnet}. The instance is
	 * immutable, i.e., the {@link Cpnet} attached to this instance is a
	 * deep-copy of the {@link Cpnet} given in the constructor.
	 * 
	 * @param cpnet
	 */
	public CpnToolsBuilder(Cpnet cpnet) {
		this.cpnet = EcoreUtil.copy(cpnet); 
	}
	
	/**
	 * Serializes the {@link Cpnet} attached to this {@link CpnToolsBuilder}
	 * 
	 * @param stream
	 *            the {@link OutputStream} in which to serialize. Client code is
	 *            responsible of closing it when it is no longer needed.
	 * @throws SerializationException
	 *             if an unrecoverable error occurs during the course of the
	 *             serialization.
	 */
	public synchronized void serialize(OutputStream stream) throws SerializationException {
		try {
			if (document == null) {
				build();
			}
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//CPN//DTD CPNXML 1.0//EN");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://cpntools.org/DTD/6/cpn.dtd");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(document), new StreamResult(stream));
		} catch (ParserConfigurationException | TransformerException e) {
			throw new SerializationException(e);
		}
	}

	private void build() throws ParserConfigurationException {
		document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		createRoot();
	}

	private void createRoot() {
		Element root = document.createElement("workspaceElements");
		document.appendChild(root);
		Element generatorNode = document.createElement("generator");
		generatorNode.setAttribute("tool", TOOL_NAME);
		generatorNode.setAttribute("version", TOOL_VERSION);
		generatorNode.setAttribute("format", TOOL_FORMAT);
		root.appendChild(generatorNode);
		root.appendChild(createCpnet());
	}

	private Node createCpnet() {
		Element element = document.createElement("cpnet");
		if (cpnet.getGlobbox() != null) {
			element.appendChild(createGlobbox(cpnet.getGlobbox()));
		}
		if (cpnet.getBinder() != null) {
			for (Page page : cpnet.getBinder().getPages()) { 
				element.appendChild(createPage(page));
			}
			element.appendChild(createInstances(cpnet.getBinder().getPages()));
			element.appendChild(createBinder(cpnet.getBinder()));
		}
		if (cpnet.getFusions() != null) {
			for (Fusion fusion: cpnet.getFusions()) { 
				element.appendChild(createFusion(fusion));
			}
		}
		return element;
	}

	private Node createFusion(Fusion fusion) {
		Element element = document.createElement("fusion");
		element.setAttribute("id", getModelElementId(fusion));
		element.setAttribute("name", fusion.getName());
		for (Place place : fusion.getPlaces()) {
			Element refElement = document.createElement("fusion_elm");
			refElement.setAttribute("idref", getModelElementId(place));
			element.appendChild(refElement);
		}
		return element;
	}
	
	private Node createBinder(Binder binder) {
		Element element = document.createElement("binders");
		Element cpnbinder = document.createElement("cpnbinder");
		cpnbinder.setAttribute("id", getModelElementId(cpnbinder));
		cpnbinder.setAttribute("x", String.valueOf(binder.getPosx()));
		cpnbinder.setAttribute("y", String.valueOf(binder.getPosy()));
		cpnbinder.setAttribute("width", String.valueOf(binder.getWidth()));
		cpnbinder.setAttribute("height", String.valueOf(binder.getHeight()));
		Element sheets = document.createElement("sheets");
		for (Page page : binder.getPages()) {
			sheets.appendChild(createSheet(page));
		}
		cpnbinder.appendChild(sheets);
		element.appendChild(cpnbinder);
		return element;
	}

	private Element createSheet(Page page) {
		Element cpnsheet = document.createElement("cpnsheet");
		cpnsheet.setAttribute("id", getModelElementId(cpnsheet));
		cpnsheet.setAttribute("instance", getModelElementId(page.getName()));
		cpnsheet.setAttribute("zoom", "1.0");
		return cpnsheet;
	}

	private Node createInstances(List<Page> pages) {
		Element element = document.createElement("instances");
		for (Page page : pages) {
			Element instance = createInstance(page);
			element.appendChild(instance);
		}
		return element;
	}

	private Element createInstance(Page page) {
		Element instance = document.createElement("instance");
		instance.setAttribute("id", getModelElementId(page.getName()));
		instance.setAttribute("page", getModelElementId(page));
		return instance;
	}

	private Node createPage(Page page) {
		Element element = document.createElement("page");
		element.setAttribute("id", getModelElementId(page));
		Element pageattr = document.createElement("pageattr");
		pageattr.setAttribute("name", page.getName());
		element.appendChild(pageattr);
		for (Place place : page.getPlaces()) {
			element.appendChild(createPlace(place));
		}
		for (Trans transition : page.getTranss()) {
			element.appendChild(createTransition(transition));
		}
		for (Arc arc : page.getArcs()) {
			element.appendChild(createArc(arc));
		}
		return element;
	}

	private Node createArc(Arc arc) {
		Element element = document.createElement("arc");
		element.setAttribute("id", getModelElementId(arc));
		element.setAttribute("orientation", arc.getOrientation().getLiteral());
		element.setAttribute("order", String.valueOf(arc.getOrder()));
		fillElementAttributesFromDiagramElement(element, arc);
		Element arrowattr = document.createElement("arrowattr");
		arrowattr.setAttribute("headsize", String.valueOf(arc.getHeadsize()));
		arrowattr.setAttribute("currentcyckle", arc.getCurrentcyckle());
		element.appendChild(arrowattr);
		Element transend = document.createElement("transend");
		transend.setAttribute("idref", getModelElementId(arc.getTrans()));
		element.appendChild(transend);
		Element placeend = document.createElement("placeend");
		placeend.setAttribute("idref", getModelElementId(arc.getPlace()));
		element.appendChild(placeend);
		if (arc.getAnnot() != null) {
			element.appendChild(createAnnot(arc.getAnnot()));
		}
		return element;
	}

	private Node createAnnot(Annot annot) {
		Element element = document.createElement("annot");
		element.setAttribute("id", getModelElementId(element));
		fillElementAttributesFromDiagramElement(element, annot);
		Element text = (Element) createText(annot.getText());
		text.setAttribute("tool", TOOL_NAME);
		text.setAttribute("version", TOOL_VERSION);
		element.appendChild(text);
		return element;
	}

	private Node createTransition(Trans transition) {
		Element element = document.createElement("trans");
		element.setAttribute("id", getModelElementId(transition));
		element.setAttribute("explicit", String.valueOf(transition.isExplicit()));
		fillElementAttributesFromDiagramElement(element, transition);
		element.appendChild(createText(transition.getText()));
		element.appendChild(createBox(transition.getWidth(), transition.getHeight()));
		if (transition.getCond() != null) {
			element.appendChild(createCond(transition.getCond()));
		}
		if (transition.getPriority() != null) {
			element.appendChild(createPriority(transition.getPriority()));
		}
		if (transition.getTime() != null) {
			element.appendChild(createTime(transition.getTime()));
		}
		Element binding = document.createElement("binding");
		binding.setAttribute("x", BINDING_POS_X.toString());
		binding.setAttribute("y", BINDING_POS_Y.toString());
		element.appendChild(binding);
		return element;
	}

	private Node createPlace(Place place) {
		Element element = document.createElement("place");
		element.setAttribute("id", getModelElementId(place));
		fillElementAttributesFromDiagramElement(element, place);
		element.appendChild(createText(place.getText()));
		element.appendChild(createEllipse(place.getWidth(), place.getHeight()));
		Element token = document.createElement("token");
		token.setAttribute("x", TOKEN_POS_X.toString());
		token.setAttribute("y", TOKEN_POS_Y.toString());
		element.appendChild(token);
		Element marking = document.createElement("marking");
		marking.setAttribute("x", MARKING_POS_X.toString());
		marking.setAttribute("y", MARKING_POS_Y.toString());
		element.appendChild(marking);
		element.appendChild(createPlaceType(place));
		if (place.getInitmark() != null) {
			place.getInitmark().setPosx(place.getPosx() + place.getWidth());
			place.getInitmark().setPosy(place.getPosy() + place.getHeight());
			element.appendChild(createInitmark(place.getInitmark()));
		}
		if (place.getFusion() != null) {
			element.appendChild(createFusionInfo(place));
		}
		return element;
	}

	private Node createInitmark(Initmark initmark) {
		Element element = document.createElement("initmark");
		element.setAttribute("id", getModelElementId(initmark));
		fillElementAttributesFromDiagramElement(element, initmark);
		Element text = (Element) createText(initmark.getExpression());
		text.setAttribute("tool", TOOL_NAME);
		text.setAttribute("version", TOOL_VERSION);
		element.appendChild(text);
		return element;
	}

	private Node createFusionInfo(Place place) {
		Element element = document.createElement("fusioninfo");
		element.setAttribute("id", getModelElementId(new Object()));
		if (place.getFusion().getName() != null) {
			element.setAttribute("name", place.getFusion().getName());
		}
		Element posattr = document.createElement("posattr");
		posattr.setAttribute("x", String.format((Locale) null, "%.6f", place.getPosx() - place.getWidth() * 0.8));
		posattr.setAttribute("y", String.format((Locale) null, "%.6f", place.getPosy() - place.getHeight() * 0.8));
		Element fillattr = document.createElement("fillattr");
		fillattr.setAttribute("colour", "White");
		fillattr.setAttribute("pattern", "Solid");
		fillattr.setAttribute("filled", "false");
		Element lineattr = document.createElement("lineattr");
		lineattr.setAttribute("colour", "Black");
		lineattr.setAttribute("thick", "0");
		lineattr.setAttribute("type", "Solid");
		Element textattr = document.createElement("textattr");
		textattr.setAttribute("colour", "Black");
		textattr.setAttribute("bold", "false");
		element.appendChild(posattr);
		element.appendChild(fillattr);
		element.appendChild(lineattr);
		element.appendChild(textattr);
		return element;
	}

	private Node createPlaceType(Place place) {
		Element element = document.createElement("type");
		element.setAttribute("id", getModelElementId(element));
		fillElementAttributesFromDiagramElement(element, place);
		((Element) element.getElementsByTagName("posattr").item(0)).setAttribute("x", Float.toString(place.getPosx() + (place.getWidth() / 2)));
		((Element) element.getElementsByTagName("posattr").item(0)).setAttribute("y", Float.toString(place.getPosy() - (place.getHeight() / 2)));
		if (place.getType() != null) {
			Element text = (Element) createText(place.getType().getIdname());
			text.setAttribute("tool", TOOL_NAME);
			text.setAttribute("version", TOOL_VERSION);
			element.appendChild(text);
		}
		return element;
	}

	private Node createEllipse(Integer width, Integer height) {
		Element element = document.createElement("ellipse");
		element.setAttribute("w", width.toString());
		element.setAttribute("h", height.toString());
		return element;
	}

	private Node createBox(Integer width, Integer height) {
		Element element = document.createElement("box");
		element.setAttribute("w", width.toString());
		element.setAttribute("h", height.toString());
		return element;
	}

	private Node createText(String text) {
		Element element = document.createElement("text");
		element.setTextContent(text);
		return element;
	}

	private Node createCond(TransCond cond) {
		Element element = document.createElement("cond");
		fillElementAttributesFromDiagramElement(element, cond);
		element.appendChild(createText(cond.getText()));
		return element;
	}
	
	private Node createPriority(TransPriority priority) {
		Element element = document.createElement("priority");
		fillElementAttributesFromDiagramElement(element, priority);
		element.appendChild(createText(priority.getText()));
		return element;
	}
	
	private Node createTime(TransTime time) {
		Element element = document.createElement("time");
		fillElementAttributesFromDiagramElement(element, time);
		element.appendChild(createText(time.getText()));
		return element;
	}
	
	private Node fillElementAttributesFromDiagramElement(Element element, DiagramElement diagramElement) {
		Element posattr = document.createElement("posattr");
		posattr.setAttribute("x", String.format((Locale) null, "%.6f", Float.valueOf(diagramElement.getPosx())));
		posattr.setAttribute("y", String.format((Locale) null, "%.6f", Float.valueOf(diagramElement.getPosy())));
		Element fillattr = document.createElement("fillattr");
		fillattr.setAttribute("colour", diagramElement.getFillColour().getLiteral());
		fillattr.setAttribute("pattern", diagramElement.getFillPattern());
		fillattr.setAttribute("filled", String.valueOf(diagramElement.isFillFilled()));
		Element lineattr = document.createElement("lineattr");
		lineattr.setAttribute("colour", diagramElement.getLineColour().getLiteral());
		lineattr.setAttribute("thick", String.valueOf(diagramElement.getLineThick()));
		lineattr.setAttribute("type", diagramElement.getLineType());
		Element textattr = document.createElement("textattr");
		textattr.setAttribute("colour", diagramElement.getLineColour().getLiteral());
		textattr.setAttribute("bold", "false");
		element.appendChild(posattr);
		element.appendChild(fillattr);
		element.appendChild(lineattr);
		element.appendChild(textattr);
		return element;
	}

	private Node createGlobbox(Globbox globbox) {
		Element element = document.createElement("globbox");
		for (Declaration declaration : globbox.getDeclarations()) {
			if (declaration instanceof Block) {
				Block block = (Block) declaration;
				element.appendChild(createBlock(block));
			}
		}
		return element;
	}

	private Node createBlock(Block block) {
		Element element = document.createElement("block");
		element.setAttribute("id", getModelElementId(block));
		element.appendChild(createElementId(block.getIdname()));
		for (Declaration declaration : block.getDeclarations()) {
			if (declaration instanceof ColorSet) {
				ColorSet colorSet = (ColorSet) declaration;
				element.appendChild(createColorSet(colorSet));
			} else if (declaration instanceof Ml) {
				Ml ml = (Ml) declaration;
				element.appendChild(createMl(ml));
			} else if (declaration instanceof Var) {
				Var var = (Var) declaration;
				element.appendChild(createVar(var));
			} else if (declaration instanceof Block) {
				Block nestedBlock = (Block) declaration;
				element.appendChild(createBlock(nestedBlock));
			} else {
				throw new RuntimeException(MessageFormat.format("Unexpected declaration type ''{0}''", declaration));
			}
		}
		return element;
	}

	private Node createColorSet(ColorSet colorSet) {
		Element element = document.createElement("color");
		element.setAttribute("id", getModelElementId(colorSet));
		element.appendChild(createElementId(colorSet.getIdname()));
		if (colorSet.isTimed()) {
			element.appendChild(document.createElement("timed"));
		}
		// Simple Color Sets
		if (colorSet instanceof Unit) {
			element.appendChild(createUnit((io.github.abelgomez.cpntools.Unit) colorSet));
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Boolean) {
			element.appendChild(createBoolean((io.github.abelgomez.cpntools.Boolean) colorSet));
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Integer) {
			element.appendChild(createInteger((io.github.abelgomez.cpntools.Integer) colorSet));
		} else if (colorSet instanceof io.github.abelgomez.cpntools.LargeInteger) {
			element.appendChild(createLargeInteger((io.github.abelgomez.cpntools.LargeInteger) colorSet));
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Real) {
			element.appendChild(createReal((io.github.abelgomez.cpntools.Real) colorSet));
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Time) {
			element.appendChild(document.createElement("time"));
		} else if (colorSet instanceof io.github.abelgomez.cpntools.String) {
			element.appendChild(createString((io.github.abelgomez.cpntools.String) colorSet));
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Enumerated) {
			Element enumElt = document.createElement("enum");
			for (String with : ((Enumerated) colorSet).getWith()) {
				enumElt.appendChild(createElementId(with));
			}
			element.appendChild(enumElt);
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Index) {
			throw new UnsupportedOperationException();
		}
		// Compound Color Sets
		else if (colorSet instanceof io.github.abelgomez.cpntools.Product) {
			Element productElt = document.createElement("product");
			for (SimpleColorSet simpleColorSet : ((Product) colorSet).getSimpleColors()) {
				productElt.appendChild(createElementId(simpleColorSet.getIdname()));
			}
			element.appendChild(productElt);
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Record) {
			throw new UnsupportedOperationException();
		} else if (colorSet instanceof io.github.abelgomez.cpntools.List) {
			throw new UnsupportedOperationException();
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Union) {
			throw new UnsupportedOperationException();
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Subset) {
			throw new UnsupportedOperationException();
		} else if (colorSet instanceof io.github.abelgomez.cpntools.Alias) {
			Element aliasElt = document.createElement("alias");
			SimpleColorSet simpleColorSet = ((Alias) colorSet).getSimpleColors().get(0);
			aliasElt.appendChild(createElementId(simpleColorSet.getIdname()));
			element.appendChild(aliasElt);
		}
		return element;
	}

	private Element createUnit(io.github.abelgomez.cpntools.Unit unit) {
		Element element = document.createElement("unit");
		if (unit.getWith() != null) {
			Element withElt = document.createElement("with");
			element.appendChild(withElt);
			Element mlElt = document.createElement("ml");
			mlElt.setTextContent(unit.getWith());
			withElt.appendChild(mlElt);
		}
		return element;
	}
	
	private Element createBoolean(io.github.abelgomez.cpntools.Boolean bool) {
		Element element = document.createElement("bool");
		if (!bool.getWith().isEmpty()) {
			Element withElt = document.createElement("with");
			element.appendChild(withElt);
			for (String ml : bool.getWith()) {
				Element mlElt = document.createElement("ml");
				mlElt.setTextContent(ml);
				withElt.appendChild(mlElt);
			}
		}
		return element;
	}
	
	private Element createInteger(io.github.abelgomez.cpntools.Integer integer) {
		Element element = document.createElement("int");
		if (!integer.getWith().isEmpty()) {
			Element withElt = document.createElement("with");
			element.appendChild(withElt);
			for (String ml : integer.getWith()) {
				Element mlElt = document.createElement("ml");
				mlElt.setTextContent(ml);
				withElt.appendChild(mlElt);
			}
		}
		return element;
	}

	private Element createLargeInteger(io.github.abelgomez.cpntools.LargeInteger largeinteger) {
		Element element = document.createElement("intinf");
		if (!largeinteger.getWith().isEmpty()) {
			Element withElt = document.createElement("with");
			element.appendChild(withElt);
			for (String ml : largeinteger.getWith()) {
				Element mlElt = document.createElement("ml");
				mlElt.setTextContent(ml);
				withElt.appendChild(mlElt);
			}
		}
		return element;
	}

	private Element createReal(io.github.abelgomez.cpntools.Real real) {
		Element element = document.createElement("real");
		if (!real.getWith().isEmpty()) {
			Element withElt = document.createElement("with");
			element.appendChild(withElt);
			for (String ml : real.getWith()) {
				Element mlElt = document.createElement("ml");
				mlElt.setTextContent(ml);
				withElt.appendChild(mlElt);
			}
		}
		return element;
	}
	
	private Element createString(io.github.abelgomez.cpntools.String string) {
		Element element = document.createElement("string");
		if (!string.getWith().isEmpty()) {
			Element withElt = document.createElement("with");
			element.appendChild(withElt);
			for (String ml : string.getWith()) {
				Element mlElt = document.createElement("ml");
				mlElt.setTextContent(ml);
				withElt.appendChild(mlElt);
			}
		}
		return element;
	}
	
	private Node createMl(Ml ml) {
		Element element = document.createElement("ml");
		element.setAttribute("id", getModelElementId(ml));
		element.setTextContent(ml.getExpression());
		return element;
	}

	private Node createVar(Var var) {
		Element element = document.createElement("var");
		element.setAttribute("id", getModelElementId(var));
		Element typeElt = document.createElement("type");
		typeElt.appendChild(createElementId(var.getType().getIdname()));
		element.appendChild(typeElt);
		element.appendChild(createElementId(var.getIdname()));
		return element;
	}
	
	private Node createElementId(String id) {
		Element element = document.createElement("id");
		element.setTextContent(id);
		return element;
	}

	private Map<Object, String> ids = new WeakHashMap<>();
	private Random generator = new Random();

	private String getModelElementId(Object object) {
		String id = ids.get(object);
		if (id == null) {
			do {
				id = String.valueOf(Math.abs(generator.nextInt()));
			} while (ids.containsValue(id));
			ids.put(object, id);
		}
		;
		return "ID" + id;
	}
}