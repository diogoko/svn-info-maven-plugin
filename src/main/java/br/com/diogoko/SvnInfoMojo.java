package br.com.diogoko;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Mojo(name = "info", requiresProject = true, defaultPhase = LifecyclePhase.INITIALIZE)
public class SvnInfoMojo extends AbstractMojo {
    @org.apache.maven.plugins.annotations.Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        SVNWCClient client = SVNClientManager.newInstance().getWCClient();
        try {
            SVNInfo info = client.doInfo(project.getBasedir(), SVNRevision.WORKING);

            Properties p = project.getProperties();
            p.setProperty("svnInfo.author", info.getAuthor());
            p.setProperty("svnInfo.commitedDate", formatDate(info.getCommittedDate()));
            p.setProperty("svnInfo.commitedRevision", info.getCommittedRevision().toString());
            p.setProperty("svnInfo.url", info.getURL().toString());
            p.setProperty("svnInfo.revision", info.getRevision().toString());
        } catch (SVNException e) {
            getLog().error("Error getting SVN working copy info", e);
        }
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").format(date);
    }
}
