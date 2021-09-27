package com.dandan2611.mcgen.common.maven;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

public class PomReader {

    private final Model model;

    public PomReader() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        this.model = reader.read(new FileReader("pom.xml"));
    }

    public Model getModel() {
        return this.model;
    }

}
