def buildLog = new File(basedir, 'build.log')
assert buildLog.text.contains('Error getting SVN working copy info')


def sampleProperties = new Properties()
def sample = new File(basedir, 'target/classes/sample.properties')
sampleProperties.load(new FileInputStream(sample))
assert sampleProperties.getProperty('author', '${svnInfo.author}')
assert sampleProperties.getProperty('commitedDate', '${svnInfo.commitedDate}')
assert sampleProperties.getProperty('commitedRevision', '${svnInfo.commitedRevision}')
assert sampleProperties.getProperty('url', '${svnInfo.url}')
assert sampleProperties.getProperty('revision', '${svnInfo.revision}')
