package com.sufi.prayertimes.quran.data;


import android.util.Log;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class XMLParser {
    @Nullable
    public Document getDomElement(@NonNull String str) {
        try {
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(str));
            return newDocumentBuilder.parse(inputSource);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e2) {
            Log.e("Error: ", e2.getMessage());
            return null;
        } catch (IOException e3) {
            Log.e("Error: ", e3.getMessage());
            return null;
        }
    }

    public String getValue(@NonNull Element element, String str) {
        return getElementValue(element.getElementsByTagName(str).item(0));
    }

    public final String getElementValue(@Nullable Node node) {
        if (node != null && node.hasChildNodes()) {
            for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == (short) 3) {
                    return node.getNodeValue();
                }
            }
        }
        return "";
    }
}
