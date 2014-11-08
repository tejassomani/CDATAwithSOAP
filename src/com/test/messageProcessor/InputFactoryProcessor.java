package com.test.messageProcessor;

import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;

	public class InputFactoryProcessor
	{
		private static final Logger docLogger = Logger.getLogger(InputFactoryProcessor.class.getName());
		private static final String factoryClassName = "com.ctc.wstx.stax.WstxInputFactory";
		
		private InputFactoryProcessor () 
		{
		}		
				
		public static synchronized XMLInputFactory getInputFactoryInstance ()
		{			
			XMLInputFactory inputFactory = null;
			if (inputFactory == null) {
				InputFactoryProcessor processor = new InputFactoryProcessor();				
				inputFactory = processor.createAndGetInputFactory();		
			}
			return inputFactory;
		}
		
		/**
		 * Create and instance of InputFactory WSTXInputFactory in this case
		 * @return
		 */
		private  XMLInputFactory createAndGetInputFactory ()
		{
			XMLInputFactory inputFactory = null;
			Class classDefinition = null;;
			docLogger.info("Creating instance of factory to handle input");						
			try {
				classDefinition = Class.forName(factoryClassName);
				inputFactory = (XMLInputFactory)classDefinition.newInstance();
				setProperties(inputFactory);
			}
			catch (ClassNotFoundException e) {				
				e.printStackTrace();	
			}catch (InstantiationException e) {				
				e.printStackTrace();
			} catch (IllegalAccessException e) {				
				e.printStackTrace();
			}				 									
			return inputFactory;
		}
		
		/**
		 * Set the properties for your inputFactory
		 * Disabling external entities
		 * and setting colescing to true to read cdata as text stream
		 * @param factory
		 */
		private  void setProperties (XMLInputFactory factory)
		{
			factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
			factory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		}
	}
