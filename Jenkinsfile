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
    emailext (
      subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
      body: "${env.BUILD_URL}", 
      recipientProviders: [[$class: 'DevelopersRecipientProvider']], 
    )
  }
}
