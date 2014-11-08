package com.test.messageProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class InputDocumentProcessor 
{
	private XMLStreamReader xmlStreamReader = null;	
	private InputStream inputStream = null;
	
	/**
	 * Method to initialize the inputFactory to process the incoming xml message
	 * @param sourceFile
	 * @param params
	 * @throws Exception
	 */
	public void initialize (String sourceFile)
		throws Exception
	{
		try {			
			inputStream = new FileInputStream(sourceFile);
			XMLInputFactory factoryReader = InputFactoryProcessor.getInputFactoryInstance();
			if (factoryReader != null) {
				xmlStreamReader = factoryReader.createXMLStreamReader(inputStream);
			}
			else {
				throw new Exception ("InputFactory not found");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (XMLStreamException e) {		
			e.printStackTrace();
		}
	}
	
	/**
	 * Process the records in the input XML file.
	 * <?xml version="1.0" encoding="UTF-8"?>
		<items>
			<item>
				<name>xyz</name>
				<id>1</id>
				<desc>New Item</desc>
				<accountdetails><![CDATA[more data]]></accountdetails>
			</item>
			<item>
				<name>xyz1</name>
				<id>2</id>
				<desc>New Item1</desc>
				<accountdetails><![CDATA[another account]]></accountdetails>
			</item>
		</items>
	 * The CDATA can also contain embedded xml structure
	 * This returns a list of the item elements.
	 * Each element in the list is a map corresponding to each item
	 * Different switch cases to handle the elements and data
	 * @return
	 */
	public List<Map<String, String>> getRecord ()
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String key = "";
		String value = "";
		Map<String, String> dataMap = null;
		try {
			while (xmlStreamReader.hasNext()) {
				int event = xmlStreamReader.getEventType();
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if(!xmlStreamReader.getLocalName().equals("items")) {
					
						if(xmlStreamReader.getLocalName().equals("item")) {
							dataMap = new HashMap<>();
						}
						else {
							key = xmlStreamReader.getLocalName().trim();
						}
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					if(!xmlStreamReader.getLocalName().equals("items")) {
						if(xmlStreamReader.getLocalName().equals("item")) {
							list.add(dataMap);
						}
						else {
							dataMap.put(key, value);
						}
					}
					break;
				case XMLStreamConstants.CHARACTERS:
						String val = xmlStreamReader.getText();
						if (!val.matches("\\s+")) {
							value = xmlStreamReader.getText().trim();
						}						                                                                    
					break;				
				default:
					System.out.println("Unrecogzied xml element");
				}
				
				if (!xmlStreamReader.hasNext())
                    break;
 
              event = xmlStreamReader.next();
			}
		} catch (XMLStreamException e) {			
			e.printStackTrace();
		}			
		return list;
	}
	
	public static void main (String[] args)
	{
		InputDocumentProcessor processor = new InputDocumentProcessor();
		try {
			processor.initialize("ItemData.xml");
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		List data = processor.getRecord();
		System.out.println("data in list " + data);
	}
}
