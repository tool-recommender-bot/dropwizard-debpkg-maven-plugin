package com.jamierf.dropwizard;

import com.google.common.collect.Collections2;
import com.jamierf.dropwizard.resource.Resource;
import com.jamierf.dropwizard.transforms.ResourceDataProducer;
import org.apache.maven.project.MavenProject;
import org.vafer.jdeb.*;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

public class PackageBuilder {

    private static final Compression COMPRESSION = Compression.GZIP;

    private final MavenProject project;
    private final Console log;

    public PackageBuilder(final MavenProject project, final Console log) {
        this.project = project;
        this.log = log;
    }

    public void createPackage(final Collection<Resource> resources, final File inputDir, final File debFile) throws PackagingException {
        final Collection<DataProducer> dataProducers = Collections2.transform(resources, new ResourceDataProducer(inputDir));
        final DebMaker debMaker = new DebMaker(log, dataProducers, Collections.<DataProducer>emptySet());

        debMaker.setDeb(debFile);
        debMaker.setSignPackage(false);
        debMaker.setControl(new File(inputDir, "control"));
        debMaker.setPackage(project.getArtifactId());
        debMaker.setDescription(project.getDescription());
        debMaker.setHomepage(project.getUrl());
        debMaker.setCompression(COMPRESSION.toString());
        debMaker.validate();
        debMaker.makeDeb();
    }
}
