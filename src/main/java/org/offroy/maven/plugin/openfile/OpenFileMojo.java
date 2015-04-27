package org.offroy.maven.plugin.openfile;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Goal open a file with default system editor (cross).
 * 
 */
@Mojo(name = "open")
public class OpenFileMojo extends AbstractMojo {
    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "openfile.dir", required = true)
    private File fileDirectory;

    /**
     * Name of the file.
     */
    @Parameter(property = "openfile.fileName", required = true)
    private String fileName;

    public void execute() throws MojoExecutionException {
        try {
            getLog().info("Begin opening file" + fileName);
            File f = fileDirectory;

            if (!f.exists()) {
                throw new MojoExecutionException("Invalid directory " + f.getAbsolutePath());
            }

            File inputFile = new File(f, fileName);
            if (inputFile.exists()) {

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(inputFile);
                } else {
                    throw new MojoExecutionException("Awt Desktop is not supported!");
                }

            } else {
                throw new MojoExecutionException("File does not exists! " + inputFile.getAbsolutePath());
            }

            getLog().info("End opening file" + fileName);
        } catch (IOException e) {
            throw new MojoExecutionException("error", e);
        }
    }
}
