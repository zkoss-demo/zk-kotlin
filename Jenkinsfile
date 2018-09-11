node {
  stage('Preparation') {
    checkout scm
  }
  stage('Build') {
    sh "bash ./gradlew clean build"
  }
  stage('Results') {
    junit 'build/test-results/test/TEST-*.xml'
    archiveArtifacts 'build/libs/*.war'
  }
}

