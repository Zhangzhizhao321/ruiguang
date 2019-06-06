package com.dream.common.core.util.wechat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtil {
	public static <K> K xmlToBean(Class<K> k, InputStream is) throws Exception {
		JAXBContext context = JAXBContext.newInstance(k);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (K) unmarshaller.unmarshal(is);

	}
	public static <K> K xmlToBean(Class<K> k, String xmlStr) throws Exception {
		JAXBContext context = JAXBContext.newInstance(k);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader=new StringReader(xmlStr);
		return (K) unmarshaller.unmarshal(reader);

	}

	public static String beanToXml(Object obj) throws Exception {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		return writer.toString();
	}
}
