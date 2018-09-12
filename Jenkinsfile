node {
  try {
    stage('Preparation') {
      checkout scm
    }
    stage('Build') {
      sh "./gradlew clean buildXYZ"
    }
    stage('Results') {
      junit 'build/test-results/test/TEST-*.xml'
      archiveArtifacts 'build/libs/*.war'
    }
  } catch(e) {
    emailextrecipients([[$class: 'CulpritsRecipientProvider']])
  }
}
