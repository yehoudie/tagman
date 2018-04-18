package de.yehoudie.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.paint.Color;

public class XMLUtil
{
	public static enum Status { SUCCESS, PARSING_ERROR, PARSING_ERROR_SAX, IO_ERROR, FILE_TYPE_ERROR }
	
	private static Status status;
	public static Status getStatus() { return status; }
	
	
	/**
	 * Parse xml Document froum source file.<br>
	 * Fills #status on error.
	 * 
	 * @param	file File the source file
	 * @return	Document the parsed xml document
	 */
	public static Document parseDocument(File file)
	{
		String src = file.getAbsolutePath();

		return parseDocument(src);
	}
	
	/**
	 * Parse xml Document froum source file.<br>
	 * Fills #status on error.
	 * 
	 * @param	src String the source file path
	 * @return	Document the parsed xml document
	 */
	public static Document parseDocument(String src)
	{
		Document doc = null;
		
		DocumentBuilderFactory db_factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder doc_builder;
		try
		{
			doc_builder = db_factory.newDocumentBuilder();
			doc = doc_builder.parse(src);
		}
		catch ( ParserConfigurationException e )
		{
			status = Status.PARSING_ERROR;
			e.printStackTrace();
//			return false;
		}
		catch ( SAXException e )
		{
			status = Status.PARSING_ERROR_SAX;
			e.printStackTrace();
//			return false;
		}
		catch ( IOException e )
		{
			status = Status.IO_ERROR;
			e.printStackTrace();
//			return false;
		}
		doc.getDocumentElement().normalize();
		
		status = Status.SUCCESS;
		return doc;
	}
	
	/**
	 * Create a xml document.
	 * 
	 * @return	Document
	 */
	public static Document createDocument()
	{
		Document doc = null;
		DocumentBuilderFactory doc_factory = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilderFactory db_factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder doc_builder = db_factory.newDocumentBuilder();
			doc_builder = doc_factory.newDocumentBuilder();
			doc = doc_builder.newDocument();
		}
		catch ( ParserConfigurationException e )
		{
			e.printStackTrace();
		}
		
		return doc;
	}
	
	/**
	 * Convert byte[] to xml document.
	 * 
	 * @param	content byte[] the bytes
	 * @return	Document
	 */
	public static Document bytesToDocument(byte[] content)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		
		try
		{
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new ByteArrayInputStream(content));
		}
		catch ( ParserConfigurationException e )
		{
			e.printStackTrace();
		}
		catch ( SAXException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		
		return doc;
	}
	
	/**
	 * Get the value of an attribute of an node.
	 * 
	 * @param	node Node the node with attributes
	 * @param	name String the name of the attribute
	 * @return	String the attribute value
	 */
	public static String getAttributeValue(Node node, String name)
	{
		Node named_item = node.getAttributes().getNamedItem(name);
		return ( named_item != null ) ? named_item.getNodeValue() : null;
	}
	
	/**
	 * Create string representation of a Document.
	 * 
	 * @param	doc Document the document to convert
	 * @param	do_format boolean format the output or not
	 * @return	String
	 */
	public static String toString(Document doc, boolean do_format)
	{
		String do_format_val = (do_format) ? "yes" : "no";
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;

		StringWriter sw = new StringWriter();
		StreamResult sr = new StreamResult(sw);
		try
		{
			DOMSource source = new DOMSource(doc);

			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, do_format_val);
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, sr);
		}
		catch ( TransformerConfigurationException e )
		{
			e.printStackTrace();
		}
		catch ( TransformerException e )
		{
			e.printStackTrace();
		}

		return sw.toString();
	}
	
	/**
	 * Trim white spaces of formated xml doc.
	 * 
	 * @param	node Node the node to trim recursively
	 */
	public static void trimWhitespace(Node node)
	{
		ArrayList<Node> stack = new ArrayList<>();
		stack.add(node);
		
		int children_size;
		
		while ( !stack.isEmpty() )
		{
			Node act_node = stack.get(0);

			// add all children to stack
			NodeList children = act_node.getChildNodes();
			children_size = children.getLength();
			for ( int i = 0; i < children_size; ++i )
			{
				Node child = children.item(i);
				stack.add(child);
			}

			// trim text node
			if ( act_node.getNodeType() == Node.TEXT_NODE )
			{
				act_node.setTextContent(act_node.getTextContent().trim());
			}

			stack.remove(0);
		}
	}

	/**
	 * Get Element of the document by a given tag name.
	 * 
	 * @param	doc Document
	 * @param	tag_name String
	 * @return	Element
	 */
	public static Element getElement(Document doc, String tag_name)
	{
		NodeList list = doc.getElementsByTagName(tag_name);
		if ( list == null || list.getLength()==0 ) return null;
		
		Element el = (Element) list.item(0);
		
		return el;
	}
	
	/**
	 * Delete an (first children) element out of doc, if present.
	 * 
	 * @param	doc Document the parent document
	 * @param	node Node the element to delete
	 */
	public static void delete(Document doc, Node node)
	{
		if ( node == null ) return;

		final Element root = doc.getDocumentElement(); 
		NodeList children = root.getChildNodes();

		final int children_size = children.getLength();
		for ( int i = 0; i < children_size; ++i )
		{
			Node child = children.item(i);
			if ( child.equals(node) )
//			if ( child.isEqualNode(node) )
			{
				root.removeChild(child);
				break;
			}
		}
	}
	
	/**
	 * Get first child element with specified name, or null.
	 * 
	 * @param	element Element the parent Element
	 * @param	name Strint the child element name
	 * @return	Element
	 */
	public static Element getChildElement(Element element, String name)
	{
		if ( element.getElementsByTagName(name) == null || element.getElementsByTagName(name).item(0) == null ) return null;
		return (Element) element.getElementsByTagName(name).item(0);
	}
	
	/**
	 * Get the text value of first/single child node of the element by name.
	 * 
	 * @param	element Element the element with chilren
	 * @param	name String the child name
	 * @return	String the child value
	 */
	public static String getStringContentOfChildByName(Element element, String name)
	{
		if ( element.getElementsByTagName(name) == null || element.getElementsByTagName(name).item(0) == null ) return null;
		return element.getElementsByTagName(name).item(0).getTextContent();
	}
	
	/**
	 * Get the double value of first/single child node of the element by name.
	 * 
	 * @param	element Element the element with chilren
	 * @param	name String the child name
	 * @return	double the child value
	 */
	public static Double getDoubleContentOfChildByName(Element element, String name)
	{
		String value = getStringContentOfChildByName(element, name);
		return ( value == null || value == "" ) ? null : Double.valueOf(value);
	}
	
	/**
	 * Get the float value of first/single child node of the element by name.
	 * 
	 * @param	element Element the element with chilren
	 * @param	name String the child name
	 * @return	float the child value
	 */
	public static Float getFloatContentOfChildByName(Element element, String name)
	{
		String value = getStringContentOfChildByName(element, name);
		return ( value == null || value == "" ) ? null : Float.valueOf(value);
	}
	
	/**
	 * Get the int value of first/single child node of the element by name.
	 * 
	 * @param	element Element the element with chilren
	 * @param	name String the child name
	 * @return	int the child value
	 */
	public static Integer getIntContentOfChildByName(Element element, String name)
	{
		Double d = getDoubleContentOfChildByName(element, name);
		return ( d == null ) ? null : Integer.valueOf( (int)Math.round(d) );
	}
	
	/**
	 * Get the Color value of first/single child node of the element by name.
	 * 
	 * @param	element Element the element with chilren
	 * @param	name String the child name
	 * @return	Color the child value
	 */
	public static Color getColorContentOfChildByName(Element element, String name)
	{
		String value = getStringContentOfChildByName(element, name);
		if ( value == null || value.isEmpty() ) return null;
		return Color.valueOf( value );
	}

	/**
	 * Get the boolean value of first/single child node of the element by name.
	 * 
	 * @param	element Element the element with chilren
	 * @param	name String the child name
	 * @return	boolean the child value
	 */
	public static boolean getBooleanContentOfChildByName(Element element, String name)
	{
		String value = getStringContentOfChildByName(element, name);
		return ( "false".equals(value) || "0".equals(value) ) ? false : true;
	}
	
	/**
	 * Get the text value of an attribute of an element.
	 * 
	 * @param	element Element the element with attributes
	 * @param	name String the attribute name
	 * @return	String the child value
	 */
	public static String getStringContentOfAttributeByName(Element element, String name)
	{
		if ( element.getAttribute(name) == null ) return null;
		return element.getAttribute(name);
	}
	
	/**
	 * Get the int value of an attribute of an element.
	 * 
	 * @param	element Element the element with attributes
	 * @param	name String the attribute name
	 * @return	int the child value
	 */
	public static Integer getIntContentOfAttributeByName(Element element, String name)
	{
		String s = getStringContentOfAttributeByName(element, name);
		return ( s == null ) ? null : Integer.valueOf(s);
	}
	
	/**
	 * Get the long value of an attribute of an element.
	 * 
	 * @param	element Element the element with attributes
	 * @param	name String the attribute name
	 * @return	long the child value
	 */
	public static Long getLongContentOfAttributeByName(Element element, String name)
	{
		String s = getStringContentOfAttributeByName(element, name);
		return ( s == null ) ? null : Long.valueOf(s);
	}

	public static void appendChild(Document doc, Element parent, String tag_name, String text_content)
	{
		Element child = doc.createElement(tag_name);
		child.setTextContent(text_content);
		parent.appendChild(child);
	}

	/**
	 * Set the value of an attribute of an element.
	 * 
	 * @param	item Element the element to change.
	 * @param	name String the attribute name
	 * @param	value String the value to set
	 */
	public static void setContentOfAttributeByName(Element item, String name, String value)
	{
		item.setAttribute(name, value);
	}
	
	/**
	 * Set the value of an attribute of an element.
	 * 
	 * @param	item Element the element to change.
	 * @param	name String the child name
	 * @param	value String the value to set
	 * @return	boolean success
	 */
	public static boolean setContentOfChildByName(Element element, String name, String value)
	{
		if ( element.getElementsByTagName(name) == null || element.getElementsByTagName(name).item(0) == null ) return false;
		
		element.getElementsByTagName(name).item(0).setTextContent(value);
		
		return true;
	}
}
