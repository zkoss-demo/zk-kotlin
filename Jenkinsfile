node {
  try {
    stage('Preparation') {
      checkout scm
    }
    stage('Build') {
      sh "./gradlew clean buil d"
    }
    stage('Results') {
      junit 'build/test-results/test/TEST-*.xml'
      archiveArtifacts 'build/libs/*.war'
    }
  } catch(e) {

  }
    emailextrecipients([[$class: 'CulpritsRecipientProvider']])
}
